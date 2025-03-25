package com.synway.governace.pojo.dataStorageMonitor;

/**
 * 存储实时表详细信息
 */
public class RealTimeTableFullMessage {
    private String tableNameEN;
    private String tableNameCH;
    private String tableTodaySum;
    private String tableAllSum;
    private String statisticDay;
    private String tableProject;
    private String statisticId;
    private String dataBaseType;


    @Override
    public String toString() {
        return "RealTimeTableFullMessage{" +
                "tableNameEN='" + tableNameEN + '\'' +
                ", tableNameCH='" + tableNameCH + '\'' +
                ", tableTodaySum='" + tableTodaySum + '\'' +
                ", tableAllSum='" + tableAllSum + '\'' +
                ", statisticDay='" + statisticDay + '\'' +
                ", tableProject='" + tableProject + '\'' +
                ", statisticId='" + statisticId + '\'' +
                ", dataBaseType='" + dataBaseType + '\'' +
                '}';
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

    public String getTableTodaySum() {
        return tableTodaySum;
    }

    public void setTableTodaySum(String tableTodaySum) {
        this.tableTodaySum = tableTodaySum;
    }

    public String getTableAllSum() {
        return tableAllSum;
    }

    public void setTableAllSum(String tableAllSum) {
        this.tableAllSum = tableAllSum;
    }

    public String getStatisticDay() {
        return statisticDay;
    }

    public void setStatisticDay(String statisticDay) {
        this.statisticDay = statisticDay;
    }

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

    public String getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(String statisticId) {
        this.statisticId = statisticId;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }
}
