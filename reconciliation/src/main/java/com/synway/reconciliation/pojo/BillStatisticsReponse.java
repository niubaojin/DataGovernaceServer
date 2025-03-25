package com.synway.reconciliation.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 数据对账监控统计
 * @author ywj
 */
public class BillStatisticsReponse {
    private String dataName;
    private String resourceId;
    private Timestamp dataTime;
    private String dataTimeStr;
    private Long accessInput;
    private Long accessInputPrev;
    private Long accessOutput;
    private Long accessOutputPrev;
    private Long standardInput;
    private Long standardOutput;
    private Long storageInput;
    private Long storageOutput;
    private String accessSamePeriod;
    private String standardChainRatio;
    private String storageChainRatio;
    private Timestamp createTime;
    private String createTimeStr;
    private String alarmType;
    private String isPush;
    private String dataDirection;
    private String sysCode;
    private String dataSourceCode;
    private String isSummary;
    private String isPrivateSetting;
    private BigDecimal outputNum;
    private String tache;
    private String userId;
    private String userName;
    private String tableNameEn;
    private String isIssued;

    public String getIsIssued() {
        return isIssued;
    }

    public void setIsIssued(String isIssued) {
        this.isIssued = isIssued;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataTimeStr() {
        return dataTimeStr;
    }

    public void setDataTimeStr(String dataTimeStr) {
        this.dataTimeStr = dataTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getAccessInput() {
        return accessInput;
    }

    public void setAccessInput(Long accessInput) {
        this.accessInput = accessInput;
    }

    public Long getAccessOutput() {
        return accessOutput;
    }

    public void setAccessOutput(Long accessOutput) {
        this.accessOutput = accessOutput;
    }

    public Long getAccessOutputPrev() {
        return accessOutputPrev;
    }

    public void setAccessOutputPrev(Long accessOutputPrev) {
        this.accessOutputPrev = accessOutputPrev;
    }

    public Long getStandardInput() {
        return standardInput;
    }

    public void setStandardInput(Long standardInput) {
        this.standardInput = standardInput;
    }

    public Long getStandardOutput() {
        return standardOutput;
    }

    public void setStandardOutput(Long standardOutput) {
        this.standardOutput = standardOutput;
    }

    public Long getStorageInput() {
        return storageInput;
    }

    public void setStorageInput(Long storageInput) {
        this.storageInput = storageInput;
    }

    public Long getStorageOutput() {
        return storageOutput;
    }

    public void setStorageOutput(Long storageOutput) {
        this.storageOutput = storageOutput;
    }

    public String getAccessSamePeriod() {
        return accessSamePeriod;
    }

    public void setAccessSamePeriod(String accessSamePeriod) {
        this.accessSamePeriod = accessSamePeriod;
    }

    public String getStandardChainRatio() {
        return standardChainRatio;
    }

    public void setStandardChainRatio(String standardChainRatio) {
        this.standardChainRatio = standardChainRatio;
    }

    public String getStorageChainRatio() {
        return storageChainRatio;
    }

    public void setStorageChainRatio(String storageChainRatio) {
        this.storageChainRatio = storageChainRatio;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(String dataDirection) {
        this.dataDirection = dataDirection;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getIsSummary() {
        return isSummary;
    }

    public void setIsSummary(String isSummary) {
        this.isSummary = isSummary;
    }

    public String getIsPrivateSetting() {
        return isPrivateSetting;
    }

    public void setIsPrivateSetting(String isPrivateSetting) {
        this.isPrivateSetting = isPrivateSetting;
    }

    public Long getAccessInputPrev() {
        return accessInputPrev;
    }

    public void setAccessInputPrev(Long accessInputPrev) {
        this.accessInputPrev = accessInputPrev;
    }

    public BigDecimal getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(BigDecimal outputNum) {
        this.outputNum = outputNum;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }
}
