/**
 * @module id:
 * @module name:
 * @version:
 * @relative module: (package.class列表)
 *
 *========History Version=========
 * @version:
 * @modification:
 * @reason:
 * @date:
 * @author:
 *--------------------------------
 * @version:
 * @modification:
 * @reason:
 * @date:
 * @author:
 *================================
 */

package com.synway.datarelation.util;

import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.DownloadSession;
import com.aliyun.odps.tunnel.TableTunnel.UploadSession;
import com.aliyun.odps.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * ODPS客户端
 */
public class OdpsClient {
    private static Logger log = LoggerFactory.getLogger(OdpsClient.class);

    public static final String _ACCESSID = "accessid";
    public static final String _ACCESSKEY = "accesskey";
    public static final String _ODPSURL = "odpsurl";
    public static final String _ODPSPROJECT = "odpstabproject";
    public static final String _ODPSTUNNELURL = "odpstunnelurl";

    /**
     * 配置信息
     */
    private Map<String, String> mapConfinfo;

    public void setMapConfinfo(Map<String, String> mapConfinfo) {
        this.mapConfinfo = mapConfinfo;
    }


    /**
     * 获取表操作通道
     * @return:		 TableTunnel
     * @throws
     */
    private TableTunnel getTableTunnel(){
        TableTunnel tunnel=null;
        Odps odps = getOdps(null);
        try {
            tunnel = new TableTunnel(odps);
			tunnel.setEndpoint(mapConfinfo.get(_ODPSTUNNELURL));

		}catch (Exception e) {
            tunnel=null;
        }
        return tunnel;
    }

    /**
     * 获取指定项目的ODPS对象
     * @param projectName
     * @return
     */
    private Odps getOdps(String projectName){
        String project = null;
        if(null!=projectName){
            project = projectName;
        }else{
            project = mapConfinfo.get(_ODPSPROJECT);
        }
        Odps odps;
        Account account = new AliyunAccount(mapConfinfo.get(_ACCESSID), mapConfinfo.get(_ACCESSKEY));
        odps = new Odps(account);
        odps.setEndpoint(mapConfinfo.get(_ODPSURL));
        odps.setDefaultProject(project);
        return odps;
    }

    /**
     *
     * @param dataList
     * @param table
     * @param partitions
     * @return
     */
	public boolean uploadData(List<Object[]> dataList, String projectName,String table, Map<String, String> partitions) {
		try {
			UploadSession uploadSession = getUploadSession(projectName,table, partitions);
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
			uploadSession.commit(new Long[] { 0L });
			log.info("数据上传完成！共上传【"+dataList.size()+"】条。");
		} catch (Exception e) {
			log.error("数据上传出错！"+e);
			return false;
		}
		return true;
	}

	/**
	 * 执行DDL语句
	 * @param ddlSql
	 * @return:		 boolean
	 * @throws
	 */
	public boolean execute(String ddlSql){
		boolean flag=true;
		try {
			Odps odps=getOdps(null);
			log.info("当前执行的SQL语句："+ddlSql);
			Instance instance = SQLTask.run(odps,ddlSql);
			instance.waitForSuccess();
		} catch (Exception e) {
			log.error("Odps在执行SQL任务时发生错误！"+e);
			flag=false;
		}
		return flag;
	}

	/**
	 * 在同项目中将源表的数据集写入到目标表中
	 * @param dataSource 数据源
	 * @param srcTable 源表
	 * @param destTable 目标表
	 * @param partitions 目标表分区条件
	 * @param fileds 结果集字段顺序
	 * @param isOverWrite 是否覆盖写入
	 * @return:		 boolean
	 * @throws
	 */
	public boolean insertInto(String dataSource,String srcTable,String destTable,String[][] partitions,String fileds,boolean isOverWrite){
		boolean flag =true;
		StringBuffer buf=new StringBuffer();
		buf.append("INSERT ");
		if(isOverWrite){
			buf.append(" OVERWRITE ");
		}else {
			buf.append(" INTO ");
		}
		buf.append(" TABLE "+destTable);
		if(partitions!=null && partitions.length>0){
			buf.append(" PARTITION (");
			for (int i = 0; i < partitions.length; i++) {
				String[] p = partitions[i];
				String field=p[0];
				String value=p[1];
				buf.append(field+"='"+value+"'");
				if(i+1<partitions.length){
					buf.append(",");
				}
			}
			buf.append(") ");
		}
		buf.append(" SELECT "+fileds+" FROM "+srcTable+";");
		flag=execute(buf.toString());
		return flag;
	}

    /**
     *
     * @param tableName
     * @return
     */
	public boolean dropTable(String tableName){
		String sql="DROP TABLE IF EXISTS "+tableName+";";
		return execute(sql);

	}
	/**
	 * 表改名
	 * @param tableName 原表名
	 * @param newTableName 新表名
	 * @return:		 boolean
	 * @throws
	 */
	public boolean renameTable(String tableName,String newTableName){
		String sql="ALTER TABLE "+tableName+" RENAME TO "+newTableName+";";
		return execute(sql);
	}

    /**
     * 按天添加分区，day为0时是建今天的分区
     * @param tableName
     * @param partition 分区名
     * @param day
     * @return
     */
	public boolean addDayPartition(String tableName,String partition,int day){
		boolean flagPart=true;
		try {
			for(int i=0;i<=day;i++){
				String dateStr=DateUtil.formatDate(DateUtil.addDay(new Date(), i), "yyyMMdd");
				PartitionSpec partitionSpec=new PartitionSpec();
				partitionSpec.set(partition, dateStr);
				Table table= getOdps(null).tables().get(tableName);
				Boolean flagPartition=table.hasPartition(partitionSpec);
				if(!flagPartition){
					table.createPartition(partitionSpec);
					log.info("创建表【"+tableName+"】分区【"+dateStr+"成功】");
				}
			}
		} catch (Exception e) {
			log.error("创建分区失败"+e);
			flagPart=false;
		}
		return flagPart;
	}

	/**
	 * 根据提供的已经存在的表名来创建表(T_tableName_tmp)
	 * @param tableName
	 * @return
	 */
	public String createTableByTableModel(String tableName){
		String tempTableName="T_"+tableName+"_tmp";
		String sql = "CREATE TABLE IF NOT EXISTS "+tempTableName+" LIKE "+tableName+";";
		boolean ok = execute(sql);
		if(ok){
			return tempTableName;
		}
		return null;
	}

	/**
	 * 阶段odps表
	 * @param tableName
	 * @return
	 */
	public boolean truncateTable(String projectName,String tableName){
		try {
			Odps odps = getOdps(projectName);
			Tables tables = odps.tables();
			for (Table table : tables) {
				if(table.getName().equals(tableName)){
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
     * @param projectName
     * @param tableName
     * @return
     */
	public boolean isTableExist(String projectName,String tableName){
	    if(null==projectName){
	        projectName = mapConfinfo.get(_ODPSPROJECT);
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
     * @param table
     * @param partitions key-分区名    value-分区值
     * @return
     */
    public UploadSession getUploadSession(String projectName,String table, Map<String, String> partitions){
        if(null==projectName){
            projectName = mapConfinfo.get(_ODPSPROJECT);
        }
        UploadSession uploadSession = null;
        try {
            TableTunnel tunnel = getTableTunnel();
            if(tunnel == null){
            	throw new Exception("tunnel通道为null");
			}
            if(null!=partitions&&partitions.size()>0){
                Odps odps = getOdps(projectName);
                Table tableS=odps.tables().get(table);
                PartitionSpec partitionSpec = new PartitionSpec();
                Set<String> keys = partitions.keySet();
                for(String key:keys){
                    partitionSpec.set(key, partitions.get(key));
                }
                boolean a=tableS.hasPartition(partitionSpec);
                if(!a){
                    tableS.createPartition(partitionSpec);
                }
                uploadSession = tunnel.createUploadSession(projectName, table, partitionSpec);
            }else{
                uploadSession = tunnel.createUploadSession(projectName, table);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return uploadSession;
    }

	/**
	 * 根据提供的分区信息创建下载会话(需要测试如果存在多个分区字段,如果一个存在一个不存在的情况)
	 * @param table
	 * @param partitions
	 * @return
	 */
	private DownloadSession getDownloadSession(String projectName,String table, Map<String, String> partitions){
        if(null==projectName){
            projectName = mapConfinfo.get(_ODPSPROJECT);
        }
	    DownloadSession downloadSession = null;
		try {
			TableTunnel tunnel = getTableTunnel();
			if(tunnel == null){
				throw new Exception("tunnel的数据为null");
			}
			if(null!=partitions&&partitions.size()>0){
				Odps odps = getOdps(projectName);
				PartitionSpec partitionSpec = new PartitionSpec();
				Set<String> keys = partitions.keySet();
				for(String key:keys){
					partitionSpec.set(key, partitions.get(key));
				}
				Table tableS=odps.tables().get(table);
				boolean a=tableS.hasPartition(partitionSpec);
				if(!a){
					tableS.createPartition(partitionSpec);
				}
				downloadSession = tunnel.createDownloadSession(projectName, table, partitionSpec);
			}else{
				downloadSession = tunnel.createDownloadSession(projectName, table);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return downloadSession;
	}

    /**
     * 组织记录对象
     * @param schema 模式
     * @param values 文本行分解得到的列
     * @param record 记录对象
     * @return:		 boolean
     */
    public boolean organizeRecord(TableSchema schema,List<String> colNames,List<String> values,Record record){
        boolean flag=true;
        try {
            for (int i = 0; i < colNames.size(); i++) {
                String colName = colNames.get(i).toLowerCase();
                String value=values.get(i);
                Column column = schema.getColumn(colName);
                switch (column.getTypeInfo().getOdpsType()) {
                    case BIGINT:
                        if(!StringUtils.isEmpty(value)){
                            record.setBigint(colName, Long.valueOf(value));
                        }else{
                            record.setBigint(colName, null);
                        }
                        break;
                    case BOOLEAN:
                        if(!StringUtils.isEmpty(value)){
                            record.setBoolean(colName, Boolean.valueOf(value));
                        }else{
                            record.setBoolean(colName, null);
                        }

                        break;
                    case DATETIME:
                        if(value.length()==10){
                            record.setDatetime(colName, DateUtil.parseDate(value));
                        }else if(value.length()==19){
                            record.setDatetime(colName, DateUtil.parseDateTime(value));
                        }else {
                            //
                        }
                        break;
                    case DOUBLE:
                        if(!StringUtils.isEmpty(value)){
                            record.setDouble(colName, Double.valueOf(value));
                        }
                        break;
                    case STRING:
                        record.setString(colName, value);
                        break;
                    default:
                        log.error("未知类型: " + column.getTypeInfo());
                        flag=false;
                }
                if(!flag) {
                	break;
				}
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return flag;
    }

	/**
	 * 根据DownloadSession获取表记录数,可以输入分区信息或NULL
	 * @param tableName
	 * @param partitions
	 * @return
	 */
	public long getRecordCountByTable(String projectName,String tableName, Map<String, String> partitions){
		long count = 0;
		DownloadSession downloadSession = null;
		try {
			downloadSession = getDownloadSession(projectName,tableName, partitions);
			if(downloadSession != null){
				count = downloadSession.getRecordCount();
			}else{
				count = -1;
			}
		} catch (Exception e) {
			count = -1;
			log.error("Odps在执行SQL任务时发生错误！"+e);
		}
		return count;
	}

	/**
	 * 删除表
	 * @param tableName
	 * @return
	 */
	public boolean deleteTable(String projectName,String tableName){
		try {
			Odps odps = getOdps(projectName);
			Tables tables = odps.tables();
			tables.delete(tableName, true);
			return true;
		} catch (Exception e) {
			log.error("删除表【"+tableName+"】失败"+e);
			return false;
		}
	}

    /**
     * 指定项目名称运行SQL语句，如果不指定项目则用默认配置中的项目
     * @param sql
     * @param projectName
     * @return
     */
    public List<Object[]> execQueryBySql(String projectName,String sql) {
        Odps odps = getOdps(projectName);
        List<Object[]> list = new ArrayList<Object[]>();
        Instance instance;
        try {
            instance = SQLTask.run( odps, sql + ";");
            instance.waitForSuccess();

            Map<String, String> results = instance.getTaskResults();
            Map<String, Instance.TaskStatus> taskStatus = instance.getTaskStatus();

            String strResult = "";
            for (Map.Entry<String, Instance.TaskStatus> status : taskStatus.entrySet()) {
                strResult = results.get(status.getKey());
                strResult = strResult.replace("\"", "");
                String[] strs = strResult.split("\n");
                int j = 0;
                for (String str : strs) {
                    //去除第一行标题头
                    if (j == 0) {
                        j++;
                        continue;
                    }
                    String[] strFields = str.split(",");
                    Object[] obj = new Object[strFields.length];
                    for (int i = 0; i < obj.length; i++) {
                        obj[i] = strFields[i];
                    }
                    list.add(obj);
                }
            }
        } catch (OdpsException e) {
            log.error(e.getMessage());
        }
        return list;
    }

	/**
	 * 获取odps字段名称
	 * @param projectName
	 * @param tableName
	 * @return 表名为key  columnName为value
	 */
	public List<Column> getColumns(String projectName,String tableName){
		String proName = _ODPSPROJECT;
		if(null!=projectName){
			proName = projectName;
		}
		Table table = getOdps(proName).tables().get(proName,tableName);
		TableSchema schema = table.getSchema();
		List<Column> columns =schema.getColumns();
		return columns;
	}

	public static void main(String[] arg) {


	}

}
