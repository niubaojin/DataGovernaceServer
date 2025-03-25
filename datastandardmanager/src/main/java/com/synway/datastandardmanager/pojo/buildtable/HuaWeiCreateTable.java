package com.synway.datastandardmanager.pojo.buildtable;

public class HuaWeiCreateTable {
    public static final String HBASE_CDH="hbase-cdh";
    public static final String HBASE_HUAWEI="hbase-huawei";
    public static final String HIVE_CDH="hive-cdh";
    public static final String HIVE_HUAWEI="hive-huawei";
    public static final String ODPS="odps";
    public static final String ADS="ads";
    private String sql;
    private String type;
    //  数据源ID
    private String dataId;
    // 项目名.表名
    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
