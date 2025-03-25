package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;

/**
 * 数据库中存储的字段血缘关系
 */
public class ColumnRelationDB implements Serializable {
    private String id = "";
    // 协议厂商代码
    private String factoryId= "";
    // 字段名
    private String columnName= "";
    // 协议英文名
    private String tableId= "";
    // 目标系统代号
    private String tableCode= "";
    // TABLE_NAME
    private String tableName= "";
    // 标准化（datastandard）/数据接入（dataaccess）
    private String type= "";
    //字段中文名
    private String columnChiName= "";
    // 节点id号
    private String nodeId= "";
    // TABLE_NAME_CH
    private String tableNameCh= "";
    // TABLE_ID_CH
    private String tableIdCh= "";
    // DB_ENGNAME   输出平台的英文名称 例如 ADS-HC、ADS-HP、ODPS
    private String dbEngName = "";
    // TASK_UUID  任务id
    private String taskUuid = "";
    // 数据血缘的节点名称
    private String nodeName = "";

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getDbEngName() {
        return dbEngName;
    }

    public void setDbEngName(String dbEngName) {
        this.dbEngName = dbEngName;
    }

    public String getTaskUuid() {
        return taskUuid;
    }

    public void setTaskUuid(String taskUuid) {
        this.taskUuid = taskUuid;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getTableIdCh() {
        return tableIdCh;
    }

    public void setTableIdCh(String tableIdCh) {
        this.tableIdCh = tableIdCh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumnChiName() {
        return columnChiName;
    }

    public void setColumnChiName(String columnChiName) {
        this.columnChiName = columnChiName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
