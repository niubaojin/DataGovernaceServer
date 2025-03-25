package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.standard.ObjectDao;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.entity.CkColumn;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * oracle建表的相关接口信息
 * @author niubaojin
 * @date 2023年4月6日09:32:56
 */
public class OracleTableColumnHandle implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(OracleTableColumnHandle.class);
    /**
     *     相关的样例代码为
     */
    private static final String ADD_COLUMN_SQL = "alter table %s add (%s %s)";

    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        logger.info("开始拼接oracle新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if(data.getColumnList().isEmpty()){
            logger.info("数据库中的建表字段为空，无法生成新增字段语句");
            throw new NullPointerException("新增字段信息为空，无法生成新增字段语句");
        }
        List<String> list = new ArrayList<>();
        data.getColumnList().forEach( d-> {
            String columnType = d.getColumnType();
            if (columnType.equalsIgnoreCase("varchar2") || columnType.equalsIgnoreCase("float")){
                if (d.getColumnLen() != null){
                    columnType = columnType + "(" + d.getColumnLen() + ")";
                }
            }
            list.add(String.format(ADD_COLUMN_SQL,tableNameEn,d.getColumnEngname(),columnType));
            list.add(String.format("comment on column %s.%s is '%s'",tableNameEn,d.getColumnEngname(),StringUtils.isBlank(d.getColumnChinese())?"":d.getColumnChinese()));
        });
        return StringUtils.join(list,"; ");
    }

    @Override
    public String addColumnByData(SaveColumnComparision data) throws Exception {
        String sqlStr = this.getAddColumnSql(data);
        logger.info("在oracle中生成的sql语句为："+sqlStr);
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
     * ck的建表格式 需要创建分布式的表
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception {
        // 字段列表
        List<ObjectField> columnList = buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        // 建表语句
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("create table ").append(projectName.toLowerCase()).append(".").append(tableName.toLowerCase()).append("( \n");
        for(ObjectField tc : columnList){
            if(StringUtils.isBlank(tc.getColumnName())){
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR,"存在建表字段为空值的情况");
            }
            // 字段类型
            String createColumnType = tc.getCreateColumnType();
            // 字段长度
            int createColumnLen = tc.getCreateColumnLen();
            if(StringUtils.isBlank(createColumnType)){
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR,"字段"+tc.getColumnName()+"没有配置建表类型");
            }
            if (createColumnLen <= 0 && createColumnType.equalsIgnoreCase("varchar2")){
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR,"字段"+tc.getColumnName()+"没有配置字段长度");
            }
            // 字段类型
            stringBuilder.append("    ").append(tc.getColumnName().toLowerCase()).append("    ").append(createColumnType);
            if (createColumnType.equalsIgnoreCase("varchar2") || createColumnType.equalsIgnoreCase("float")){
                stringBuilder.append("(").append(tc.getCreateColumnLen()).append(")");
            }
            // 是否必填
            if (tc.getNeedValue()==1){
                stringBuilder.append(" not null");
            }
            // 主键
            if (tc.getPkRecnoStatus() !=null && tc.getPkRecnoStatus()){
                stringBuilder.append(" primary key");
            }
            stringBuilder.append(",\n");
        }
        stringBuilder.delete(stringBuilder.length()-2,stringBuilder.length());
        stringBuilder.append(");\n");
        // 表注释
        if (StringUtils.isNotBlank(buildTableInfoVo.getTableNameCH())){
            stringBuilder.append(String.format("comment on table %s.%s is '%s'",projectName,tableName,buildTableInfoVo.getTableNameCH())).append(";\n");
        }
        // 字段注释、索引
        String indexSql = "";
        for(ObjectField tc : columnList){
            String fieldChineseName = tc.getFieldChineseName();
            if(StringUtils.isNotBlank(fieldChineseName)){
                stringBuilder.append(String.format("comment on column %s.%s.%s is '%s'",projectName,tableName,tc.getColumnName(),fieldChineseName)).append(";\n");
            }
            if (tc.getIsIndex() != 0 && tc.getIndexType() != 0){
                String indexSqlTemp = String.format("create unique index %s.%s_%s on %s.%s(%s);\n",projectName,tableName,tc.getColumnName(),projectName,tableName,tc.getColumnName());
                indexSql += indexSqlTemp;
            }
        }
        // 建立索引
        stringBuilder.append(indexSql);

        return stringBuilder.toString();
    }

    private List<String> getPkColumnList(List<CkColumn> list,int size,String type){
        List<String> strList = new ArrayList<>(size);
        for (CkColumn ckColumn : list) {
            if(StringUtils.equalsIgnoreCase(ckColumn.getPartitionType(),Common.COLUMN)){
                strList.add(ckColumn.getColumnName().toLowerCase());
            }else if(StringUtils.equalsIgnoreCase(ckColumn.getPartitionType(),Common.DATE_FUNCTION)){
                strList.add(ckColumn.getPartitionFunction()+"("+ckColumn.getColumnName().toLowerCase()+")");
            }else{
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR,type+" 字段["+ckColumn.getColumnName()
                        +"]类型为["+ckColumn.getPartitionType()+"]，没有配置处理类");
            }

        }
        return strList;
    }

    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        return false;
    }

    /**
     *  创建oracle表信息
     */
    @Override
    public String createTableByPage(BuildTableInfoVo buildTableInfoVo) throws Exception{
        logger.info("oracle建表的相关信息为："+JSONObject.toJSONString(buildTableInfoVo));
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        this.createTableBeforeCheck(buildTableInfoVo);
        String dataId = buildTableInfoVo.getDataId();
        String createSql = this.getCreateSql(buildTableInfoVo);
        logger.info("生成的sql语句为：\n"+createSql);
        CreateTableData huaWeiCreateTable = new CreateTableData();
        huaWeiCreateTable.setData(createSql.replaceAll("\\n"," "));
        huaWeiCreateTable.setType(Common.ORACLETYPE);
        huaWeiCreateTable.setResId(dataId);
        huaWeiCreateTable.setTableId(buildTableInfoVo.getTableId());
        huaWeiCreateTable.setTableName(buildTableInfoVo.getSchema()+"."+buildTableInfoVo.getTableName());
        String message;
        try{
            message= restTemplateHandle.sendCreateTableInfo(huaWeiCreateTable);
            logger.info("----------------建表结束---------------");
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw SystemException.asSystemException(ErrorCode.CREATE_TABLE_ERROR,e.getMessage());
        }
        return message;
    }

    /**
     * 建表之前的参数检查 并返回tableId
     * @param buildTableInfoVo
     * @throws Exception
     */
    @Override
    public  String createTableBeforeCheck(BuildTableInfoVo buildTableInfoVo) throws Exception{
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

        // 检查必填参数是否存在
        String createSql = this.getCreateSql(buildTableInfoVo);
        if(StringUtils.isBlank(createSql)){
            throw new NullPointerException("生成的建表sql为空");
        }

        return tableId;
    }


}
