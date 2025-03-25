package com.synway.governace.pojo.generalManagement;

/**
 * @Descroption
 * @Author majia
 * @Date 2020/4/27 10:53
 * @Version 1.0
 **/
public class PropertyAlarmSetting {
    /*数据资产*/
    private String noDataCheck;             // 是否数据量为零检查
    private String noDataAlarmLevel;        // 数据量为零告警级别
    private String fluctuateCheck;          // 是否波动异常检查
    private String fluctuateAlarmLevel;     // 波动率异常告警级别
    private String fluctuateDays;           // 日均日期
    private String fluctuateRate;           // 波动率
    private String assetsStoreDays;         // 表组织数据最大保留天数

    public String getFluctuateDays() {
        return fluctuateDays;
    }

    public void setFluctuateDays(String fluctuateDays) {
        this.fluctuateDays = fluctuateDays;
    }

    public String getFluctuateRate() {
        return fluctuateRate;
    }

    public void setFluctuateRate(String fluctuateRate) {
        this.fluctuateRate = fluctuateRate;
    }

    public String getNoDataCheck() {
        return noDataCheck;
    }

    public void setNoDataCheck(String noDataCheck) {
        this.noDataCheck = noDataCheck;
    }

    public String getNoDataAlarmLevel() {
        return noDataAlarmLevel;
    }

    public void setNoDataAlarmLevel(String noDataAlarmLevel) {
        this.noDataAlarmLevel = noDataAlarmLevel;
    }

    public String getFluctuateCheck() {
        return fluctuateCheck;
    }

    public void setFluctuateCheck(String fluctuateCheck) {
        this.fluctuateCheck = fluctuateCheck;
    }

    public String getFluctuateAlarmLevel() {
        return fluctuateAlarmLevel;
    }

    public void setFluctuateAlarmLevel(String fluctuateAlarmLevel) {
        this.fluctuateAlarmLevel = fluctuateAlarmLevel;
    }

    public String getAssetsStoreDays() {
        return assetsStoreDays;
    }

    public void setAssetsStoreDays(String assetsStoreDays) {
        this.assetsStoreDays = assetsStoreDays;
    }
}
