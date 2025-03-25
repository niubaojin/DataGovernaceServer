package com.synway.reconciliation.pojo;


import com.synway.reconciliation.util.DateUtil;

import java.sql.Timestamp;

/**
 * 对账告警统计
 * @author ywj
 */
public class BillAlarmStatistics {
    private String resourceName;
    private String resourceId;
    private int tache;
    private String tacheStr;
    private Long samePeriodInput;
    private Long samePeriodOutput;
    private String samePeriodError;
    private Long chainRatioOne;
    private Long chainRatioTwo;
    private String chainRatioError;
    private Timestamp dataTime;
    private String dataTimeStr;
    private String alarmType;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getTache() {
        return tache;
    }

    public void setTache(int tache) {
        this.tache = tache;
    }

    public Long getSamePeriodInput() {
        return samePeriodInput;
    }

    public void setSamePeriodInput(Long samePeriodInput) {
        this.samePeriodInput = samePeriodInput;
    }

    public Long getSamePeriodOutput() {
        return samePeriodOutput;
    }

    public void setSamePeriodOutput(Long samePeriodOutput) {
        this.samePeriodOutput = samePeriodOutput;
    }

    public String getSamePeriodError() {
        return samePeriodError;
    }

    public void setSamePeriodError(String samePeriodError) {
        this.samePeriodError = samePeriodError;
    }

    public Long getChainRatioOne() {
        return chainRatioOne;
    }

    public void setChainRatioOne(Long chainRatioOne) {
        this.chainRatioOne = chainRatioOne;
    }

    public Long getChainRatioTwo() {
        return chainRatioTwo;
    }

    public void setChainRatioTwo(Long chainRatioTwo) {
        this.chainRatioTwo = chainRatioTwo;
    }

    public String getChainRatioError() {
        return chainRatioError;
    }

    public void setChainRatioError(String chainRatioError) {
        this.chainRatioError = chainRatioError;
    }

    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataTimeStr() {
        return DateUtil.formatDateTime(this.dataTime, "yyyy-MM-dd");
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getTacheStr() {
        String str = "";
        switch(this.tache){
            case 1:
                str = "接入";
                break;
            case 2:
                str = "入库";
                break;
            case 3:
                str = "数据处理接入";
                break;
            case 4:
                str = "数据处理入库";
                break;
            default:
                break;
        }
        return str;
    }
}
