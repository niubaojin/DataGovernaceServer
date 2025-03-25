package com.synway.datastandardmanager.pojo.ExternalInterfce;

import java.util.Date;

public class ResourceTable_old {

    // 表概况信息id
    private String id;

    // 数据源id
    private String resourceId;

    // 表id
    private String tableId;

    // 数据项名称
    private String tableName;

    private String tableNameEn;

    // 协议编码
    private String protocalCode;

    // 组织
    private String organization;

    // 一级
    private String levelOne;

    // 二级
    private String levelTwo;

    // 三级
    private String levelThree;

    // 来源单位
    private String fromUnit;

    // 所属应用系统
    private String belongSystem;

    // 所属应用系统
    private String belongSystemCode;

    // 业务含义描述
    private String bussinessMean;

    // 接入方式
    private String receiveMethod;

    // 数据总记录
    private String totalRecord;

    // 平均增记录
    private String avgRecord;

    // 数据总存储量
    private String totalStore;

    // 平均存储量
    private String avgStore;

    // 最早记录
    private String earliestRecord;

    // 最近记录
    private String recentRecord;

    // 存储周期
    private String storePeriod;

    // 更新周期
    private String updatePeriod;

    // 更新日期
    private String updateDate;

    private int isFloder;

    private String secretLevel;

    private String pid;//ftp使用

    private Date updateTime;

    private int status;

    private String approvalId;

    private String approvalMemo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalMemo() {
        return approvalMemo;
    }

    public void setApprovalMemo(String approvalMemo) {
        this.approvalMemo = approvalMemo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBelongSystemCode() {
        return belongSystemCode;
    }

    public void setBelongSystemCode(String belongSystemCode) {
        this.belongSystemCode = belongSystemCode;
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public int getIsFloder() {
        return isFloder;
    }

    public void setIsFloder(int isFloder) {
        this.isFloder = isFloder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getProtocalCode() {
        return protocalCode;
    }

    public void setProtocalCode(String protocalCode) {
        this.protocalCode = protocalCode;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(String levelOne) {
        this.levelOne = levelOne;
    }

    public String getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(String levelTwo) {
        this.levelTwo = levelTwo;
    }

    public String getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(String levelThree) {
        this.levelThree = levelThree;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getBussinessMean() {
        return bussinessMean;
    }

    public void setBussinessMean(String bussinessMean) {
        this.bussinessMean = bussinessMean;
    }

    public String getReceiveMethod() {
        return receiveMethod;
    }

    public void setReceiveMethod(String receiveMethod) {
        this.receiveMethod = receiveMethod;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getAvgRecord() {
        return avgRecord;
    }

    public void setAvgRecord(String avgRecord) {
        this.avgRecord = avgRecord;
    }

    public String getTotalStore() {
        return totalStore;
    }

    public void setTotalStore(String totalStore) {
        this.totalStore = totalStore;
    }

    public String getAvgStore() {
        return avgStore;
    }

    public void setAvgStore(String avgStore) {
        this.avgStore = avgStore;
    }

    public String getEarliestRecord() {
        return earliestRecord;
    }

    public void setEarliestRecord(String earliestRecord) {
        this.earliestRecord = earliestRecord;
    }

    public String getRecentRecord() {
        return recentRecord;
    }

    public void setRecentRecord(String recentRecord) {
        this.recentRecord = recentRecord;
    }

    public String getStorePeriod() {
        return storePeriod;
    }

    public void setStorePeriod(String storePeriod) {
        this.storePeriod = storePeriod;
    }

    public String getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(String updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
