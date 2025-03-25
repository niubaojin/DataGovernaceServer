package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.synway.datastandardmanager.SpringBeanUtil;
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
 * @ClassName OdpsAddColumnHandle
 * @description odps里面新增字段的相关处理类
 * @date 2020/9/25 13:57
 */
public class OdpsTableColumnHandle implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(OdpsTableColumnHandle.class);
    private static final String ADD_COLUMN_SQL = "alter table %s add columns(%s);";

    /**
     * odps中不管是分区字段还是非分区字段 都只需要使用该方式来新增字段
     * @param data
     * @return
     */
    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        logger.info("开始拼接odps新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if(data.getColumnList().isEmpty()){
            logger.info("数据库中的建表字段为空，无法生成新增字段语句");
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
        logger.info("在odps中生成的sql语句为："+sqlStr);
        if(StringUtils.isBlank(sqlStr)){
            throw new NullPointerException("生成的sql语句为空");
        }
        if(StringUtils.isBlank(data.getCreatedTableData().getDataId())){
            logger.info("表["+data.getCreatedTableData().getTableNameEn()+"]对应的参数dataId为空，不能新增字段信息");
            logger.error("表["+data.getCreatedTableData().getTableNameEn()+"]对应的参数dataId为空，不能新增字段信息");
            throw new NullPointerException( "表["+data.getCreatedTableData().getTableNameEn()+"]对应的参数dataId为空，" +
                    "不能新增字段信息");
        }
        CreateTableData aliyunAddColumn = new CreateTableData();
        aliyunAddColumn.setTableId(data.getCreatedTableData().getTableNameEn());
        aliyunAddColumn.setResId(data.getCreatedTableData().getDataId());
        aliyunAddColumn.setData(sqlStr);
        aliyunAddColumn.setType(data.getCreatedTableData().getTableBase());
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        return restTemplateHandle.sendAddColumnInfo(aliyunAddColumn);
    }

    /**
     * 创建建表sql的相关语句
     * 20200927 新增该内容 以前的弃用
     * @param buildTableInfoVo
     * @return
     */
    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception{
        List<ObjectField> columnList= buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        Integer lifeDays = buildTableInfoVo.getLifeCycle();
        Integer isPartition = buildTableInfoVo.getIsPartition(); // 0：分区表，1：全量表
        String odpsSql = "";
        odpsSql = "CREATE TABLE IF NOT EXISTS " +projectName+"."+tableName +"(\n";
        //添加字段
        for(ObjectField tc : columnList){
            if (isPartition == 1){
                odpsSql = buildField(odpsSql, tc);
            }else {
                if (!"dt".equalsIgnoreCase(tc.getColumnName().toUpperCase())){
                    odpsSql = buildField(odpsSql, tc);
                }
            }
        }
        odpsSql = odpsSql.substring(0,odpsSql.trim().length()-1) + ")\n";
        if (StringUtils.isNotBlank(buildTableInfoVo.getTableNameCH())){
            odpsSql += "comment '" + buildTableInfoVo.getTableNameCH() + "'\n";
        }
        if (isPartition == 0){
            odpsSql += "partitioned by (DT STRING  COMMENT '分区列')\n";
        }
        if(lifeDays != null && lifeDays > 0){
            odpsSql += "lifecycle " + lifeDays + "\n";
        }
        odpsSql += ";";
        logger.info("odps create table sql: \n" + odpsSql);
        return odpsSql;
    }

    public String buildField(String odpsSql, ObjectField tc){
        odpsSql += "\t" + tc.getColumnName() + "   " + tc.getCreateColumnType();
        if(StringUtils.isEmpty(tc.getCreateColumnType())){
            throw new NullPointerException(String.format("字段[%s]对应的类型[%s]，请配置对应的建表类型",tc.getColumnName(),tc.getCreateColumnType()));
        }
        if(StringUtils.isNotEmpty(tc.getFieldChineseName())){
            odpsSql += "   comment  '" + tc.getFieldChineseName() + "',"+"\n";
        }else{
            odpsSql += "," + "\n";
        }
        return odpsSql;
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
