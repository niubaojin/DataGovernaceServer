package com.synway.governace.pojo.dataStorageMonitor;

/**
 * 获取需要监控的实时表名
 */
public class NeedAddRealTimeTable {
    private String dataBaseType;
    private String projectName;
    private String tableNameEN;
    private String tableNameCH;
    private boolean partitioned;//是否为分区

    @Override
    public String toString() {
        return "NeedAddRealTimeTable{" +
                "dataBaseType='" + dataBaseType + '\'' +
                ", projectName='" + projectName + '\'' +
                ", tableNameEN='" + tableNameEN + '\'' +
                ", tableNameCH='" + tableNameCH + '\'' +
                ", partitioned=" + partitioned +
                '}';
    }

    public boolean isPartitioned() {
        return partitioned;
    }

    public void setPartitioned(boolean partitioned) {
        this.partitioned = partitioned;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableNameEN() {
        return tableNameEN;
    }

    public void setTableNameEN(String tableNameEN) {
        this.tableNameEN = tableNameEN;
    }

    public String getTableNameCH() {
        return tableNameCH;
    }

    public void setTableNameCH(String tableNameCH) {
        this.tableNameCH = tableNameCH;
    }
}
