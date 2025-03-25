package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.dao.standard.ObjectDao;
import com.synway.datastandardmanager.dao.standard.TableOrganizationDao;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName HiveAddColumnHandle
 * @description hive里面相关的处理类  默认已经验证过是否为空
 * @date 2020/9/25 13:56
 */
public class HiveTableColumnHandle implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(HiveTableColumnHandle.class);

    // Partition
    private static final String ADD_COLUMN_SQL_PARTITION = "alter table %s add columns(%s) cascade";
    private static final String ADD_COLUMN_SQL = "alter table %s add columns(%s)";

    /**
     * 生成hive新增字段的相关sql信息  cascade（除了更新元数据 更新所有分区的元数据 ）
     *  如果是分区表 则需要 cascade 反之不需要 cascade
     * eg: alter table detail_flow_test add columns(column1 string comment '1',column2 string comment '2') cascade;
     * 如果是分区字段，新增分区 会涉及到时静态分区还是动态分区，@20200925 分区字段不能进行新增操作
     *    如果是静态分区，默认是 某个值
     *      如果是动态分区，目前还不知道如何做
     * 添加
     * @param data
     * @return
     */
    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        logger.info("开始拼接hive新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if(data.getColumnList().isEmpty()){
            logger.info("数据库中的建表字段为空，无法生成新增字段语句");
            return "";
        }
        List<String> list = data.getColumnList().stream().filter(d -> !d.getPartition()).map( d ->
                d.getColumnEngname() +"  "+d.getColumnType() +"  comment '"+ d.getColumnChinese()+"' ").collect(Collectors.toList());
        String sqlData = StringUtils.join(list,", ").toLowerCase();
        if(data.getCreatedTableIsPartition()){
            return String.format(ADD_COLUMN_SQL_PARTITION , tableNameEn,sqlData);
        }else{
            return String.format(ADD_COLUMN_SQL , tableNameEn,sqlData);
        }

    }

    /**
     *  hive里新增字段调用 建表的接口 只是 sql不一样
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public String addColumnByData(SaveColumnComparision data) throws Exception{

        String sqlStr = this.getAddColumnSql(data);
        logger.info("在hive中生成的sql语句为："+sqlStr);
        CreateTableData huaWeiCreateTable = new CreateTableData();
        huaWeiCreateTable.setTableId(data.getCreatedTableData().getTableNameEn());
        huaWeiCreateTable.setResId(data.getCreatedTableData().getDataId());
        huaWeiCreateTable.setData(sqlStr);
        huaWeiCreateTable.setType(data.getCreatedTableData().getTableBase());
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        return restTemplateHandle.sendAddColumnInfo(huaWeiCreateTable);

    }

    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception{
        List<ObjectField> columnList = buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        String sql = "";
        sql = "CREATE TABLE IF NOT EXISTS " +projectName+"."+tableName +"(\n";
        //添加字段
        boolean havePartition = false;
        for(ObjectField tc : columnList){
            // dt 一般作为分区表 所以在建表时不能有dt这个字段信息
            if(tc.getColumnName().equalsIgnoreCase("dt")){
                havePartition = true;
                continue;
            }
            sql+="\t"+ tc.getColumnName() + "   " + tc.getCreateColumnType();
            if(StringUtils.isEmpty(tc.getCreateColumnType())){
                throw new NullPointerException(String.format("字段[%s]对应的类型[%s]，请配置对应的建表类型",tc.getColumnName(),tc.getCreateColumnType()));
            }
            if(StringUtils.isNotBlank(tc.getFieldChineseName())){
                sql += "   comment  '" + tc.getFieldChineseName() + "',"+"\n";
            }else{
                sql +=","+"\n";
            }
        }
        // tableNameCh
        String tableNameCh = buildTableInfoVo.getTableNameCH();
        if(StringUtils.isEmpty(tableNameCh)){
            tableNameCh = " ";
        }
        if(havePartition){
            sql = sql.substring(0,sql.trim().length()-1)+") \n  comment  '"+tableNameCh+"' \n partitioned by (dt string COMMENT '日期分区列')";
        }else{
            sql = sql.substring(0,sql.trim().length()-1)+") \n  comment  '"+tableNameCh+"' ";
        }

        // 需要添加 表的存储格式
        sql = sql + "\n  stored as orc tblproperties (\"orc.compress\"=\"SNAPPY\")";

        return sql;
    }


    /**
     *  创建hive平台的相关数据
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String createTableByPage(BuildTableInfoVo buildTableInfoVo) throws Exception{
        logger.info("hive建表的相关信息为："+JSONObject.toJSONString(buildTableInfoVo));
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        this.createTableBeforeCheck(buildTableInfoVo);
        String dataId = buildTableInfoVo.getDataId();

        String createSql = this.getCreateSql(buildTableInfoVo);
        logger.info("HIVE生成的建表语句为： "+createSql);
        CreateTableData huaWeiCreateTable = new CreateTableData();
        huaWeiCreateTable.setData(createSql);
        huaWeiCreateTable.setType(buildTableInfoVo.getDsType());
        huaWeiCreateTable.setResId(dataId);
        huaWeiCreateTable.setTableId(buildTableInfoVo.getTableId());
        huaWeiCreateTable.setTableName(buildTableInfoVo.getSchema()+"."+buildTableInfoVo.getTableName());
        String message;
        try{
            message= restTemplateHandle.sendCreateTableInfo(huaWeiCreateTable);
            logger.info("----------------开始将数据写入到已建表中---------------");
            TableOrganizationDao tableOrganizationDao = SpringBeanUtil.getBean(TableOrganizationDao.class);
//            if(StringUtils.isBlank(buildTableInfoVo.getLifeCycle())){
//                buildTableInfoVo.setLifeCycle("永久");
//            }
            try{
                tableOrganizationDao.insertInfo(buildTableInfoVo);
                tableOrganizationDao.insertInfoTemp(buildTableInfoVo);
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw SystemException.asSystemException(ErrorCode.CREATE_TABLE_ERROR,e.getMessage());
        }
        return message;
    }


    /**
     * 建表之前的参数检查
     * @param buildTableInfoVo
     * @throws Exception
     */
    @Override
    public  String createTableBeforeCheck(BuildTableInfoVo buildTableInfoVo) throws Exception{
        logger.info("======开始对hive的参数进行检查=====");
        String tableId = buildTableInfoVo.getTableId();
        ObjectDao objectDao = SpringBeanUtil.getBean(ObjectDao.class);
        int temp = objectDao.countObjectByTableId(tableId,buildTableInfoVo.getTableName());
        if(temp==0) {
            throw new NullPointerException("标准中无表名或协议编号信息!!!");
        }
        String dataId = buildTableInfoVo.getDataId();
        if(StringUtils.isEmpty(dataId)){
            throw new NullPointerException("数据源ID为空，请选择具体的数据源信息");
        }
        String createSql = this.getCreateSql(buildTableInfoVo);
        if(StringUtils.isBlank(createSql)){
            throw new NullPointerException("生成的建表sql为空");
        }
        return tableId;
    }

    /**
     *
     * @param oldTableName  需要更新的表名
     * @param newTableName  更新后的表名
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        return false;
    }
}
