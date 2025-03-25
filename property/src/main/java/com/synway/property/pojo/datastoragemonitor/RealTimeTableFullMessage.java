package com.synway.property.pojo.datastoragemonitor;

import java.io.Serializable;

/**
 * 存储实时表详细信息
 * @author majia
 */
public class RealTimeTableFullMessage implements Serializable {
    private String tableNameEN;
    private String tableNameCH;
    private String tableTodaySum;
    private String tableAllSum;
    private String statisticDay;
    private String tableProject;
    private String statisticId;
    private String dataBaseType;
    private String dataUnit;
    private String todayDataUnit;

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
                ", dataUnit='" + dataUnit + '\'' +
                ", todayDataUnit='" + todayDataUnit + '\'' +
                '}';
    }

    public String getTodayDataUnit() {
        return todayDataUnit;
    }

    public void setTodayDataUnit(String todayDataUnit) {
        this.todayDataUnit = todayDataUnit;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
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
