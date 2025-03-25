package com.synway.property.pojo;


/**
 * 监控 重点组织的相关配置信息
 * @author majia
 */
public class FoucsOrganizationFull {
    private String mainClassifyCode;
    private String mainClassifyCH;
    private String primaryName;
    private String secondaryName;
    private String tableCount;
    private String tableCountUnit;
    private String yesterdayRecordsSum;
    private String yesterdayUnit;
    private String allRecordsSum;
    private String dataUnit;
    private String secondaryClassifyCode;
    private String primarycodeid;


    @Override
    public String toString() {
        return "FoucsOrganizationFull{" +
                "mainClassifyCode='" + mainClassifyCode + '\'' +
                ", mainClassifyCH='" + mainClassifyCH + '\'' +
                ", primaryName='" + primaryName + '\'' +
                ", secondaryName='" + secondaryName + '\'' +
                ", tableCount='" + tableCount + '\'' +
                ", tableCountUnit='" + tableCountUnit + '\'' +
                ", yesterdayRecordsSum='" + yesterdayRecordsSum + '\'' +
                ", yesterdayUnit='" + yesterdayUnit + '\'' +
                ", allRecordsSum='" + allRecordsSum + '\'' +
                ", dataUnit='" + dataUnit + '\'' +
                '}';
    }

    public String getPrimarycodeid() {
        return primarycodeid;
    }

    public void setPrimarycodeid(String primarycodeid) {
        this.primarycodeid = primarycodeid;
    }

    public String getSecondaryClassifyCode() {
        return secondaryClassifyCode;
    }

    public void setSecondaryClassifyCode(String secondaryClassifyCode) {
        this.secondaryClassifyCode = secondaryClassifyCode;
    }

    public String getMainClassifyCode() {
        return mainClassifyCode;
    }

    public void setMainClassifyCode(String mainClassifyCode) {
        this.mainClassifyCode = mainClassifyCode;
    }

    public String getMainClassifyCH() {
        return mainClassifyCH;
    }

    public void setMainClassifyCH(String mainClassifyCH) {
        this.mainClassifyCH = mainClassifyCH;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public String getTableCount() {
        return tableCount;
    }

    public void setTableCount(String tableCount) {
        this.tableCount = tableCount;
    }

    public String getTableCountUnit() {
        return tableCountUnit;
    }

    public void setTableCountUnit(String tableCountUnit) {
        this.tableCountUnit = tableCountUnit;
    }

    public String getYesterdayRecordsSum() {
        return yesterdayRecordsSum;
    }

    public void setYesterdayRecordsSum(String yesterdayRecordsSum) {
        this.yesterdayRecordsSum = yesterdayRecordsSum;
    }

    public String getYesterdayUnit() {
        return yesterdayUnit;
    }

    public void setYesterdayUnit(String yesterdayUnit) {
        this.yesterdayUnit = yesterdayUnit;
    }

    public String getAllRecordsSum() {
        return allRecordsSum;
    }

    public void setAllRecordsSum(String allRecordsSum) {
        this.allRecordsSum = allRecordsSum;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }
}