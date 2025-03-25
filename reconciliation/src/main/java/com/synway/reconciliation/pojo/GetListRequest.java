package com.synway.reconciliation.pojo;

/**
 * 统计信息查询条件
 * @author ym
 */
public class GetListRequest {
    private int pageNum;
    private int pageSize;
    private String primaryDatasource;
    private String secondaryDataSource;
    private String primaryOrganization;
    private String firstOrganization;
    private String secondaryOrganization;
    private String factorOne;
    private String factorTwo;
    private String startTime;
    private String endTime;
    private String days;
    private String createTimeBegin;
    private String createTimeEnd;
    private String resourceId;
    private String dataName;
    private int abnormal;
    /**
     * 账单环节1.接入2.入库
     */
    private int tache;
    private String sortName;
    private String sortOrder;
    private String direction;
    private String parentId;
    private String alarmType;
    private String isPush;
    private String isIssued;
    private int type;
    private String nodeName;
    private String accessType;
    private String isLocal;
    private String userId;
    private String tableNameEn;

    public String getFirstOrganization() {
        return firstOrganization;
    }

    public void setFirstOrganization(String firstOrganization) {
        this.firstOrganization = firstOrganization;
    }

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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(int abnormal) {
        this.abnormal = abnormal;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPrimaryDatasource() {
        return primaryDatasource;
    }

    public void setPrimaryDatasource(String primaryDatasource) {
        this.primaryDatasource = primaryDatasource;
    }

    public String getSecondaryDataSource() {
        return secondaryDataSource;
    }

    public void setSecondaryDataSource(String secondaryDataSource) {
        this.secondaryDataSource = secondaryDataSource;
    }

    public String getPrimaryOrganization() {
        return primaryOrganization;
    }

    public void setPrimaryOrganization(String primaryOrganization) {
        this.primaryOrganization = primaryOrganization;
    }

    public String getSecondaryOrganization() {
        return secondaryOrganization;
    }

    public void setSecondaryOrganization(String secondaryOrganization) {
        this.secondaryOrganization = secondaryOrganization;
    }

    public String getFactorOne() {
        return factorOne;
    }

    public void setFactorOne(String factorOne) {
        this.factorOne = factorOne;
    }

    public String getFactorTwo() {
        return factorTwo;
    }

    public void setFactorTwo(String factorTwo) {
        this.factorTwo = factorTwo;
    }

    public int getTache() {
        return tache;
    }

    public void setTache(int tache) {
        this.tache = tache;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(String createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}
