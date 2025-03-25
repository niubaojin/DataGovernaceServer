package com.synway.datarelation.pojo.databloodline;

/**
 * 字段血缘的字段信息，用于插入到数据库中
 */
public class FieldColumn {
    public final static String ODPS="odps";
    public final static String ADS="ads";
    public final static String HIVE="hive";
    public final static String HBASE="hbase";
    private String tableNameEn=""; // 表名
    private String fieldName="";//字段名称
    private String fieldValue="";//字段内容
    private String fieldType="";//字段类型
    private String fieldLen = ""; // 字段长度
    private String fieldFormat="";//字段格式
    private String tableId="";  // 表协议ID
    private String targetCode=""; //目标 系统代号英文名
    private String dataType="ODPS"; // 数据库类型
    private String nodeId = "";
    private String taskUuid = "";  //  任务的uuid

    public String getTaskUuid() {
        return taskUuid;
    }

    public void setTaskUuid(String taskUuid) {
        this.taskUuid = taskUuid;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getFieldLen() {
        return fieldLen;
    }

    public void setFieldLen(String fieldLen) {
        this.fieldLen = fieldLen;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }
}
