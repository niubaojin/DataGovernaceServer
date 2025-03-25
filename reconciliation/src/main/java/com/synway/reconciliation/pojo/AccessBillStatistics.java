package com.synway.reconciliation.pojo;

import java.sql.Timestamp;

/**
 * 接入账单统计
 * @author ym
 */
public class AccessBillStatistics {
    private String accessSystem;
    private String dataSource;
    private String resourceId;
    private String tableNameEn;
    private long outSideProvide;
    private long insideProvide;
    private long insideAccess;
    private int tache;
    private String direction;
    private Timestamp dataTime;
    private Timestamp statisticTime;
    private String startNo;
    private String sysCode;
    private String dataSourceCode;
    private String isIssued;
    private String tableName;
    private String jobName;
    private String isLocal;
    private String userId;
    private String userName;
    private Timestamp synInceptDataTime;
    private long outsideAccept;
    private String inceptDataTime;

    public String getInceptDataTime() {
        return inceptDataTime;
    }

    public void setInceptDataTime(String inceptDataTime) {
        this.inceptDataTime = inceptDataTime;
    }

    public long getOutsideAccept() {
        return outsideAccept;
    }

    public void setOutsideAccept(long outsideAccept) {
        this.outsideAccept = outsideAccept;
    }

    public Timestamp getSynInceptDataTime() {
        return synInceptDataTime;
    }

    public void setSynInceptDataTime(Timestamp synInceptDataTime) {
        this.synInceptDataTime = synInceptDataTime;
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

    public Timestamp getStatisticTime() {
        return statisticTime;
    }

    public void setStatisticTime(Timestamp statisticTime) {
        this.statisticTime = statisticTime;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getAccessSystem() {
        return accessSystem;
    }

    public void setAccessSystem(String accessSystem) {
        this.accessSystem = accessSystem;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public long getOutSideProvide() {
        return outSideProvide;
    }

    public void setOutSideProvide(long outSideProvide) {
        this.outSideProvide = outSideProvide;
    }

    public long getInsideProvide() {
        return insideProvide;
    }

    public void setInsideProvide(long insideProvide) {
        this.insideProvide = insideProvide;
    }

    public long getInsideAccess() {
        return insideAccess;
    }

    public void setInsideAccess(long insideAccess) {
        this.insideAccess = insideAccess;
    }

    public int getTache() {
        return tache;
    }

    public void setTache(int tache) {
        this.tache = tache;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
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

    public String getIsIssued() {
        return isIssued;
    }

    public void setIsIssued(String isIssued) {
        this.isIssued = isIssued;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }
}
