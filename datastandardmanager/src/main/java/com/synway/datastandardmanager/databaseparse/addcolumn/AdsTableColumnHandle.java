package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName AdsTableColumnHandle
 * @description  ads数据库中建表以及新增字段的处理类
 * @date 2020/9/26 13:38
 */
public class AdsTableColumnHandle  implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(AdsTableColumnHandle.class);
    private static final String ADD_COLUMN_SQL = "alter table %s add columns(%s);";
    /**
     * ads新增字段的语句为：
     *    alter table db_name.tablename add columns(column1 string comment '1',column2 string comment '2')
     *  与odps的一样
     * @param data
     * @return
     */
    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        logger.info("开始拼接ads新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if(data.getColumnList().isEmpty()){
            logger.info("数据库中的新增的建表字段为空，无法生成新增字段语句");
            return "";
        }
        List<String> list = data.getColumnList().stream().filter(d -> !d.getPartition()).map( d ->
                d.getColumnEngname() +"  "+d.getColumnType() +"  comment '"+ d.getColumnChinese()+"' ").collect(Collectors.toList());
        String sqlData = StringUtils.join(list,", ").toLowerCase();
        return String.format(ADD_COLUMN_SQL , tableNameEn,sqlData);
    }

    @Override
    public String addColumnByData(SaveColumnComparision data) throws Exception {

        String sqlStr = this.getAddColumnSql(data);
        logger.info("在ads中生成的sql语句为："+sqlStr);
        if(StringUtils.isBlank(sqlStr)){
            throw new NullPointerException("生成的sql语句为空");
        }
        if(StringUtils.isBlank(data.getCreatedTableData().getDataId())){
            logger.info("表["+data.getCreatedTableData().getTableNameEn()+"]对应的参数dataId为空，不能新增字段信息");
            logger.error("表["+data.getCreatedTableData().getTableNameEn()+"]对应的参数dataId为空，不能新增字段信息");
            throw new NullPointerException("表["+data.getCreatedTableData().getTableNameEn()+"]" +
                    "对应的参数dataId为空，不能新增字段信息");
        }
        CreateTableData aliyunAddColumn = new CreateTableData();
        aliyunAddColumn.setTableId(data.getCreatedTableData().getTableNameEn());
        aliyunAddColumn.setResId(data.getCreatedTableData().getDataId());
        aliyunAddColumn.setData(sqlStr);
        aliyunAddColumn.setType(data.getCreatedTableData().getTableBase());
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        //  返回结果是咋样 之后再判定    "创建"+tableId+"表的字段成功"
        return restTemplateHandle.sendAddColumnInfo(aliyunAddColumn);
    }

    /**
     *  获取ads的建表信息
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception{
        List<ObjectField> columnList = buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        String partitionFirst = buildTableInfoVo.getPartitionFirst();
        String partitionFirstNum = buildTableInfoVo.getPartitionFirstNum();
        String partitionSecond = buildTableInfoVo.getPartitionSecond();
        String partitionSecondNum = buildTableInfoVo.getPartitionSecondNum();
        String tableNameCH = buildTableInfoVo.getTableNameCH();
        String isActiveTable = "";
        String sql = "";
        try{
            ResourceManageDao resourceManageDao = SpringBeanUtil.getBean(ResourceManageDao.class);
            isActiveTable = resourceManageDao.getActiveTableByName(tableName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        sql = "CREATE TABLE " +projectName+"."+tableName +"(\n";
        //添加字段
        for(ObjectField tc : columnList){
            sql += "\t"+ tc.getColumnName() + "   " + tc.getCreateColumnType();
            if(StringUtils.isEmpty(tc.getCreateColumnType())){
                throw new NullPointerException(String.format("字段[%s]对应的类型[%s]，请配置对应的建表类型",tc.getColumnName(),tc.getCreateColumnType()));
            }
            if(StringUtils.isNotEmpty(tc.getFieldChineseName())){
                sql += "   comment  '" + tc.getFieldChineseName() + "',"+"\n";
            }else{
                sql +=","+"\n";
            }
        }
        //添加主键
        String keySql = "";
        String clustSql = "";
        for(ObjectField tc : columnList){
            if(tc.getPkRecno() != null && tc.getPkRecno() != 0){
                keySql += tc.getColumnName()+",";
            }
            if(tc.getClustRecno() != null && tc.getClustRecno() != 0) {
                clustSql += tc.getColumnName()+",";
            }
        }
        if(!StringUtils.isEmpty(keySql))
        {
            keySql = "\tprimary key("+keySql.substring(0,keySql.length()-1)+"))\n";
            sql = sql + keySql;
        }else{
            sql = sql.substring(0,sql.length()-1) + ")\n";
        }
        //添加分区
        if(!StringUtils.isEmpty(partitionFirst)){
            String partitionSql = " partition by hash key("+partitionFirst+")  partition num "+partitionFirstNum+"\n";
            if(!StringUtils.isEmpty(partitionSecond)){
                partitionSql =partitionSql + " subpartition by list key ("+partitionSecond+")";
            }
            if(StringUtils.isNotBlank(partitionSecondNum)){
                partitionSql += "SUBPARTITION OPTIONS(available_Partition_Num="+partitionSecondNum+")";
            }
            sql = sql + partitionSql;
        }

        if(!StringUtils.isEmpty(clustSql)){
            clustSql = "\tCLUSTERED BY ("+clustSql.substring(0,clustSql.length()-1)+")\n";
            sql = sql + clustSql;
        }
        if("0".equals(isActiveTable)){
            sql = sql + " tablegroup data_table options(updateType='batch') ";
        }else {
            sql = sql + " tablegroup data_table options(updateType='realtime') ";
        }
        if(StringUtils.isNotBlank(tableNameCH) ){
            sql = sql + " comment '" + tableNameCH + "' ;";
        }else{
            sql = sql + " ;";
        }
        logger.info("ADS建表语句: {}", sql);
        return sql;
    }

    /**
     * 更新表名
     * @param oldTableName  需要更新的表名
     * @param newTableName  更新后的表名
     * @return   返回更新结果
     * @throws Exception
     */
    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        boolean flag = true;




        return true;
    }
}
