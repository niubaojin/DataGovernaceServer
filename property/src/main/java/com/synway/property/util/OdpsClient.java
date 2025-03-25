/**
 * @module id:
 * @module name:
 * @version:
 * @relative module: (package.class列表)
 * <p>
 * ========History Version=========
 * @version:
 * @modification:
 * @reason:
 * @date:
 * @author: --------------------------------
 * @version:
 * @modification:
 * @reason:
 * @date:
 * @author: ================================
 */

package com.synway.property.util;

import com.alibaba.fastjson.JSON;
import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.data.ResultSet;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.DownloadSession;
import com.aliyun.odps.tunnel.TableTunnel.UploadSession;
import com.aliyun.odps.tunnel.io.TunnelRecordReader;
import com.aliyun.odps.utils.StringUtils;
import com.synway.property.pojo.tablemanage.AdsOdpsTableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * ODPS客户端
 * @author 数据接入
 */
public class OdpsClient {
    private static Logger log = LoggerFactory.getLogger(OdpsClient.class);

    public static final String ACCESS_ID = "accessid";
    public static final String ACCESS_KEY = "accesskey";
    public static final String ODPS_URL = "odpsurl";
    public static final String ODPS_PROJECT = "odpstabproject";
    public static final String ODPS_TUNNEL_URL = "odpstunnelurl";

    /**
     * 配置信息
     */
    private Map<String, String> mapConfinfo;

    public void setMapConfinfo(Map<String, String> mapConfinfo) {
        this.mapConfinfo = mapConfinfo;
    }

    /**
     * 获取表操作通道
     *
     * @throws
     * @return: TableTunnel
     */
    private TableTunnel getTableTunnel() {
        TableTunnel tunnel;
        Odps odps = getOdps(null);
        try {
            tunnel = new TableTunnel(odps);
            tunnel.setEndpoint(mapConfinfo.get(ODPS_TUNNEL_URL));
        } catch (Exception e) {
            tunnel = null;
        }
        return tunnel;
    }

    /**
     * 获取指定项目的ODPS对象
     *
     * @param projectName
     * @return
     */
    private Odps getOdps(String projectName) {
        String project = null;
        if (null != projectName) {
            project = projectName;
        } else {
            project = mapConfinfo.get(ODPS_PROJECT);
        }
        Odps odps;
        Account account = new AliyunAccount(mapConfinfo.get(ACCESS_ID), mapConfinfo.get(ACCESS_KEY));
        odps = new Odps(account);
        odps.setEndpoint(mapConfinfo.get(ODPS_URL));
        odps.setDefaultProject(project);
        return odps;
    }

    /**
     * 执行DDL语句
     *
     * @param ddlSql
     * @throws
     * @return: boolean
     */
    public boolean execute(String ddlSql) {
        boolean flag = true;
        try {
            Odps odps = getOdps(null);
            log.info("当前执行的SQL语句：" + ddlSql);
            Instance instance = SQLTask.run(odps, ddlSql);
            instance.waitForSuccess();
        } catch (Exception e) {
            log.error("Odps在执行SQL任务时发生错误！" + e);
            flag = false;
        }
        return flag;
    }

    /**
     * 在同项目中将源表的数据集写入到目标表中
     *
     * @param dataSource  数据源
     * @param srcTable    源表
     * @param destTable   目标表
     * @param partitions  目标表分区条件
     * @param fileds      结果集字段顺序
     * @param isOverWrite 是否覆盖写入
     * @throws
     * @return: boolean
     */
    public boolean insertInto(String dataSource, String srcTable, String destTable, String[][] partitions, String fileds, boolean isOverWrite) {
        boolean flag = true;
        StringBuffer buf = new StringBuffer();
        buf.append("INSERT ");
        if (isOverWrite) {
            buf.append(" OVERWRITE ");
        } else {
            buf.append(" INTO ");
        }
        buf.append(" TABLE " + destTable);
        if (partitions != null && partitions.length > 0) {
            buf.append(" PARTITION (");
            for (int i = 0; i < partitions.length; i++) {
                String[] p = partitions[i];
                String field = p[0];
                String value = p[1];
                buf.append(field + "='" + value + "'");
                if (i + 1 < partitions.length) {
                    buf.append(",");
                }
            }
            buf.append(") ");
        }
        buf.append(" SELECT " + fileds + " FROM " + srcTable + ";");
        flag = execute(buf.toString());
        return flag;
    }

    /**
     * @param tableName
     * @return
     */
    public boolean dropTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName + ";";
        return execute(sql);

    }

    /**
     * 表改名
     *
     * @param tableName    原表名
     * @param newTableName 新表名
     * @throws
     * @return: boolean
     */
    public boolean renameTable(String tableName, String newTableName) {
        String sql = "ALTER TABLE " + tableName + " RENAME TO " + newTableName + ";";
        return execute(sql);
    }

    /**
     * 按天添加分区，day为0时是建今天的分区
     *
     * @param tableName
     * @param partition 分区名
     * @param day
     * @return
     */
    public boolean addDayPartition(String tableName, String partition, int day) {
        boolean flagPart = true;
        try {
            for (int i = 0; i <= day; i++) {
                String dateStr = DateUtil.formatDate(DateUtil.addDay(new Date(), i), "yyyMMdd");
                PartitionSpec partitionSpec = new PartitionSpec();
                partitionSpec.set(partition, dateStr);
                Table table = getOdps(null).tables().get(tableName);
                Boolean flagPartition = table.hasPartition(partitionSpec);
                if (!flagPartition) {
                    table.createPartition(partitionSpec);
                    log.info("创建表【" + tableName + "】分区【" + dateStr + "成功】");
                }
            }
        } catch (Exception e) {
            log.error("创建分区失败" + e);
            flagPart = false;
        }
        return flagPart;
    }

    /**
     * 根据提供的已经存在的表名来创建表(T_tableName_tmp)
     *
     * @param tableName
     * @return
     */
    public String createTableByTableModel(String tableName) {
        String tempTableName = "T_" + tableName + "_tmp";
        String sql = "CREATE TABLE IF NOT EXISTS " + tempTableName + " LIKE " + tableName + ";";
        boolean ok = execute(sql);
        if (ok) {
            return tempTableName;
        }
        return null;
    }

    /**
     * 阶段odps表
     *
     * @param tableName
     * @return
     */
    public boolean truncateTable(String projectName, String tableName) {
        try {
            Odps odps = getOdps(projectName);
            Tables tables = odps.tables();
            for (Table table : tables) {
                if (table.getName().equals(tableName)) {
                    table.truncate();
                    break;
                }
            }
            return true;
        } catch (OdpsException e) {
            return false;
        }
    }

    /**
     * 判断表是否存在
     *
     * @param projectName
     * @param tableName
     * @return
     */
    public boolean isTableExist(String projectName, String tableName) {
        if (null == projectName) {
            projectName = mapConfinfo.get(ODPS_PROJECT);
        }
        try {
            Odps odps = getOdps(projectName);
            Tables tables = odps.tables();
            boolean isExist = tables.exists(projectName, tableName);
            return isExist;
        } catch (Exception e) {
            log.error("判断表存在失败!!!");
            return false;
        }
    }

    /**
     * 根据传入的分区字段名创建指定分区字段和值的分区
     * 如果为null，则默认创建没有分区的上传会话
     *
     * @param table
     * @param partitions key-分区名    value-分区值
     * @return
     */
    public UploadSession getUploadSession(String projectName, String table, Map<String, String> partitions) {
        if (null == projectName) {
            projectName = mapConfinfo.get(ODPS_PROJECT);
        }
        UploadSession uploadSession = null;
        try {
            TableTunnel tunnel = getTableTunnel();
            if (null != partitions && partitions.size() > 0) {
                Odps odps = getOdps(projectName);
                Table tableS = odps.tables().get(table);
                PartitionSpec partitionSpec = new PartitionSpec();
                Set<String> keys = partitions.keySet();
                for (String key : keys) {
                    partitionSpec.set(key, partitions.get(key));
                }
                Boolean a = tableS.hasPartition(partitionSpec);
                if (!a) {
                    tableS.createPartition(partitionSpec);
                }
                if (tunnel != null) {
                    uploadSession = tunnel.createUploadSession(projectName, table, partitionSpec);
                }
            } else {
                if (tunnel != null) {
                    uploadSession = tunnel.createUploadSession(projectName, table);
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            return null;
        }
        return uploadSession;
    }

    /**
     * @param dataList
     * @param table
     * @param partitions
     * @return
     */
    public boolean uploadData(List<Object[]> dataList, String projectName, String table, Map<String, String> partitions) {
        try {
            UploadSession uploadSession = getUploadSession(projectName, table, partitions);
            //打开数据写入组件
            RecordWriter recordWriter = uploadSession.openRecordWriter(0);
            Record record = null;
            //遍历数据集写入
            for (Object[] row : dataList) {
                record = uploadSession.newRecord();
                record.set(row);
                recordWriter.write(record);
            }
            //关闭写入组件
            recordWriter.close();
            //提交数据
            uploadSession.commit(new Long[]{0L});
            log.info("数据上传完成！共上传【" + dataList.size() + "】条。");
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("数据上传出错！" + e);
            return false;
        }
        return true;
    }

    /**
     * 根据提供的分区信息创建下载会话(需要测试如果存在多个分区字段,如果一个存在一个不存在的情况)
     *
     * @param tableName
     * @param partitions
     * @return
     */
    private DownloadSession getDownloadSession(String projectName, String tableName, Map<String, String> partitions) {
        if (null == projectName) {
            projectName = mapConfinfo.get(ODPS_PROJECT);
        }
        DownloadSession downloadSession = null;
        try {
            TableTunnel tunnel = getTableTunnel();
            if (null != partitions && partitions.size() > 0) {
                Odps odps = getOdps(projectName);
                PartitionSpec partitionSpec = new PartitionSpec();
                Set<String> keys = partitions.keySet();
                for (String key : keys) {
                    partitionSpec.set(key, partitions.get(key));
                }
                Table tableS = odps.tables().get(tableName);
                Boolean a = tableS.hasPartition(partitionSpec);
                if (!a) {
                    tableS.createPartition(partitionSpec);
                }
                if (tunnel != null) {
                    downloadSession = tunnel.createDownloadSession(projectName, tableName, partitionSpec);
                }
            } else {
                if (tunnel != null) {
                    downloadSession = tunnel.createDownloadSession(projectName, tableName);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return downloadSession;
    }

    /**
     * 获取表的数据量
     * @param tableProject
     * @param tableName
     * @param partition
     * @return
     * @throws Exception
     */
    public long getTableCount(String tableProject, String tableName, String partition, boolean isPartitionTable) throws Exception{
        long count=0l;
        try{
            Odps odps = getOdps(tableProject);
            TableTunnel tunnel = new TableTunnel(odps);
            tunnel.setEndpoint(mapConfinfo.get(ODPS_TUNNEL_URL));

            if (isPartitionTable){
                PartitionSpec partitionSpec = new PartitionSpec(partition);
                DownloadSession session = tunnel.createDownloadSession(tableProject, tableName, partitionSpec);
//                DownloadSession downloadSession = tunnel.getDownloadSession(tableProject,tableName,session.getId());
//                count = downloadSession.getRecordCount();
                count = session.getRecordCount();
            }else {
                DownloadSession session = tunnel.createDownloadSession(tableProject, tableName);
                DownloadSession downloadSession = tunnel.getDownloadSession(tableProject,tableName,session.getId());
                count = downloadSession.getRecordCount();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 下载ODPS表分区数据
     *
     * @param projectName
     * @param tableName
     * @param columnNames
     * @param partitions
     * @return
     */
    public List downloadData(String projectName, String tableName, List<String> columnNames, String partitions) {
        List<Record> list = new ArrayList<>();
        TunnelRecordReader recordReader = null;
        PartitionSpec partitionSpec = new PartitionSpec(partitions);
        try {
            TableTunnel tunnel = getTableTunnel();
            if(tunnel ==null) {
                return null;
            }
            DownloadSession downloadSession = tunnel.createDownloadSession(projectName, tableName, partitionSpec);
            List<Column> columns = getColumnTypes(projectName, tableName, columnNames);
            log.info("查询到的列信息：" + JSON.toJSONString(columns));
            System.out.println("Session Status is: "
                    + downloadSession.getStatus().toString());
            long count = downloadSession.getRecordCount();
            System.out.println("RecordCount is: " + count);
            recordReader = downloadSession.openRecordReader(0, count,false,columns);
            Record record;
            while ((record = recordReader.read()) != null) {
                list.add(record);
            }
        } catch (Exception e) {
            log.error("下载表【" + tableName + "】数据出错。" + ExceptionUtil.getExceptionTrace(e));
            return null;
        } finally {
            try {
                if (null != recordReader) {
                    recordReader.close();
                }
            } catch (Exception e) {
                log.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
        return list;
    }

    /**
     * 组织记录对象
     *
     * @param schema 模式
     * @param values 文本行分解得到的列
     * @param record 记录对象
     * @return: boolean
     */
    public boolean organizeRecord(TableSchema schema, List<String> colNames, List<String> values, Record record) {
        boolean flag = true;
        try {
            for (int i = 0; i < colNames.size(); i++) {
                String colName = colNames.get(i).toLowerCase();
                String value = values.get(i);
                Column column = schema.getColumn(colName);
                switch (column.getTypeInfo().getOdpsType()) {
                    case BIGINT:
                        if (!StringUtils.isEmpty(value)) {
                            record.setBigint(colName, Long.valueOf(value));
                        } else {
                            record.setBigint(colName, null);
                        }
                        break;
                    case BOOLEAN:
                        if (!StringUtils.isEmpty(value)) {
                            record.setBoolean(colName, Boolean.valueOf(value));
                        } else {
                            record.setBoolean(colName, null);
                        }

                        break;
                    case DATETIME:
                        if (value.length() == 10) {
                            record.setDatetime(colName, DateUtil.parseDate(value));
                        } else if (value.length() == 19) {
                            record.setDatetime(colName, DateUtil.parseDateTime(value));
                        } else {
                            //
                        }
                        break;
                    case DOUBLE:
                        if (!StringUtils.isEmpty(value)) {
                            record.setDouble(colName, Double.valueOf(value));
                        }
                        break;
                    case STRING:
                        record.setString(colName, value);
                        break;
                    default:
                        log.error("未知类型: " + column.getTypeInfo());
                        flag = false;
                }
                if (!flag) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            return false;
        }
        return flag;
    }

    /**
     * 根据DownloadSession获取表记录数,可以输入分区信息或NULL
     *
     * @param tableName
     * @param partitions
     * @return
     */
    public long getRecordCountByTable(String projectName, String tableName, Map<String, String> partitions) {
        long count = 0;
        DownloadSession downloadSession = null;
        try {
            downloadSession = getDownloadSession(projectName, tableName, partitions);
            if (downloadSession != null) {
                count = downloadSession.getRecordCount();
            }
        } catch (Exception e) {
            count = -1;
            log.error("Odps在执行SQL任务时发生错误！" + e);
        }
        return count;
    }

    /**
     * 删除表
     *
     * @param tableName
     * @return
     */
    public boolean deleteTable(String projectName, String tableName) {
        try {
            Odps odps = getOdps(projectName);
            Tables tables = odps.tables();
            tables.delete(tableName, true);
            return true;
        } catch (Exception e) {
            log.error("删除表【" + tableName + "】失败" + e);
            return false;
        }
    }

    /**
     * 指定项目名称运行SQL语句，如果不指定项目则用默认配置中的项目
     * 这个只为 查询 使用热度使用
     *
     * @param sql
     * @param projectName
     * @return
     */
    public List<Object[]> execQueryBySql(String projectName, String sql) {
        Odps odps = getOdps(projectName);
        List<Object[]> list = new ArrayList<Object[]>();
        Instance instance;
        try {
            instance = SQLTask.run(odps, sql + ";");
            instance.waitForSuccess();
            ResultSet rs = SQLTask.getResultSet(instance);
            log.info("sql语句【" + sql + "】查询出来的数据量为：" + rs.getRecordCount());
            int objectLen = rs.getTableSchema().getColumns().size();
            for (Record r : rs) {
                Object[] obj = new Object[objectLen];
                for (int col = 0; col < objectLen; col++) {
                    try {
                        Object oneColumnData = r.get(col);
                        if (oneColumnData == null) {
                            obj[col] = null;
                        } else {
                            obj[col] = r.getString(col);
                        }
                    } catch (Exception e) {
                        obj[col] = r.get(col);
                    }
                }
                list.add(obj);
            }
            rs.remove();
            System.gc();
        } catch (Exception e) {
            log.error("查询sql报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        return list;
    }

    /**
     * 根据列名获取列的类型
     *
     * @param projectName
     * @param tableName
     * @param columnNames
     * @return 表名为key  columnName为value
     */
    public List<Column> getColumnTypes(String projectName, String tableName, List<String> columnNames) {
        String proName = ODPS_PROJECT;
        if (null != projectName) {
            proName = projectName;
        }
        Table table = getOdps(proName).tables().get(proName, tableName);
        int columnSize = columnNames.size();
        List<Column> columns = new ArrayList<>();
        TableSchema schema = table.getSchema();
        String tmpColumnName;
        for (int i = 0; i < columnSize; i++) {
            tmpColumnName = columnNames.get(i);
            columns.add(schema.getColumn(tmpColumnName));
        }
        return columns;
    }

    public List<String> getTableList(String projectName, String tableNameLike) {
        String proName = ODPS_PROJECT;
        if (null != projectName) {
            proName = projectName;
        }
        Tables tables = getOdps(proName).tables();
        Iterator<Table> tableIter = tables.iterator();
        List<String> adsOdpsTableInfoList = new ArrayList<>();
        while (tableIter.hasNext()) {
            Table table = tableIter.next();
            String tableName = table.getName();
            if (tableName != null && tableName.length() > 0) {
                if (tableName.indexOf(tableNameLike) != -1) {
                    AdsOdpsTableInfo adsOdpsTableInfo = new AdsOdpsTableInfo();
                    adsOdpsTableInfo.setProjectName(projectName);
                    adsOdpsTableInfo.setTableName(tableName);
                    adsOdpsTableInfo.setTableCreater(table.getOwner());
                    adsOdpsTableInfo.setCreateTime(table.getCreatedTime() == null ? "" : DateUtil.formatDate(table.getCreatedTime(), DateUtil.DEFAULT_PATTERN_DATETIME));
                    adsOdpsTableInfo.setLastDDLModifiedTime(table.getLastMetaModifiedTime() == null ? "" : DateUtil.formatDate(table.getLastMetaModifiedTime(), DateUtil.DEFAULT_PATTERN_DATETIME));
                    adsOdpsTableInfo.setMemo(table.getComment());
                    adsOdpsTableInfo.setLife(table.getLife());
                    try {
                        adsOdpsTableInfo.setPartitioned(table.isPartitioned());
                    } catch (OdpsException e) {
                        log.error(ExceptionUtil.getExceptionTrace(e));
                    }
                    adsOdpsTableInfo.setLastDataModifiedTime(table.getLastDataModifiedTime() == null ? "" : DateUtil.formatDate(table.getLastDataModifiedTime(), DateUtil.DEFAULT_PATTERN_DATETIME));
                    adsOdpsTableInfoList.add(adsOdpsTableInfo.toString());
                }
            }
        }
        return adsOdpsTableInfoList;
    }

    public List<Record> getODPSTableData(String projectName, String tableName) throws Exception {
        Tables tables = getOdps(projectName).tables();
        Table table = tables.get(tableName);
        List<Record> records = new ArrayList<>();
        RecordReader recordReader = table.read(100);
        for (int i = 0; i < 100; i++) {
            Record record = recordReader.read();
            if (record == null) {
                break;
            }
            records.add(record);
        }
        return records;
    }

    public Object executeOdpsSql(String sql1) {
        Odps odps = getOdps("tianshan");
        List<Object[]> list = new ArrayList<Object[]>();
        Instance instance;
        try {
            instance = SQLTask.run(odps, sql1);
            instance.waitForSuccess();
            System.out.println("测试res" + "--------------------------------------------------------------------");
            return 1;
        } catch (Exception e) {
            log.info("sql在odps平台上执行失败" + e.getMessage());
        }
        return 1;
    }

    public Object updateTableCycle(String projectname, String tablename, String tableCycle) throws OdpsException {
        Odps odps = getOdps(projectname);
        Instance instance;
        //查询odps的结果
        String sql = "alter table " + tablename + "  set lifecycle " + tableCycle;
        instance = SQLTask.run(odps, sql + " ;");
        instance.waitForSuccess();
        return "1";
    }


    public ResultSet getResultSet(String projectName, String sql) {
        Odps odps = getOdps(projectName);
        List<Object[]> list = new ArrayList<Object[]>();
        Instance instance;
        ResultSet rs = null;
        try {
            instance = SQLTask.run(odps, sql + ";");
            instance.waitForSuccess();
            rs = SQLTask.getResultSet(instance);
        } catch (Exception e) {
            log.error("查询sql报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        return rs;
    }

    public Tables getOdpsTables(String projectName){
        Tables tables = null;
        Odps odps = getOdps(projectName);
        tables = odps.tables();

        return tables;
    }

    public static void main(String[] arg) {


    }

}
