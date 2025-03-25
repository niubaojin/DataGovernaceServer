package com.synway.property.pojo.tablemanage;

import java.util.Map;

/**
 * @author 数据接入
 */
public class AdsOdpsTableInfoReq {
    Map<String,Class> columns;
    String tableName;

    public Map<String, Class> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Class> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    String schema;
}
