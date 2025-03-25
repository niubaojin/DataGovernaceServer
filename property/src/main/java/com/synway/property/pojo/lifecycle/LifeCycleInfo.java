package com.synway.property.pojo.lifecycle;

/**
 * @author majia
 * @version 1.0
 * @date 2021/2/19 13:28
 */
public class LifeCycleInfo {
    /**
     * 数据资源名称
     */
    private String name;
    /**
     * 数据资源表名
     */
    private String tableNameEn;
    /**
     * 组织分类
     */
    private String organizationClassify;
    /**
     * 二级组织分类
     */
    private String secOrganizationClassify;
    /**
     * 平台类型
     */
    private String platformType;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 占用存储
     */
    private String storageSize;
    /**
     * 更新方式
     */
    private String updateType;
    /**
     * 是否分区
     */
    private String partition;
    /**
     * 已建分区数量
     */
    private String partitionNum;
    /**
     * 存储周期
     */
    private String lifeCycle;
    /**
     * 最后更新时间
     */
    private String lastDataModifiedTime;
    /**
     * 最后访问时间
     */
    private String lastVisitedTime;
    /**
     * 月访问次数
     */
    private String useCountOfMonth;

    /**
     * 数据价值密度
     */
    private String valDensity;

    /**
     * 平均日存储量
     */
    private String averageDailySize;

    /**
     * 修改生命周期状态
     */
    private String lifeCycleStatus;

    /**
     * 更新价值密度状态
     */
    private String updateValDensityStatus;

    //数据源id，用于关联权限表的仓库数据id
    private String resourceId;
    private String resName;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResourceId() { return resourceId; }

    public void setResourceId(String resourceId) { this.resourceId = resourceId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getOrganizationClassify() {
        return organizationClassify;
    }

    public void setOrganizationClassify(String organizationClassify) {
        this.organizationClassify = organizationClassify;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getPartitionNum() {
        return partitionNum;
    }

    public void setPartitionNum(String partitionNum) {
        this.partitionNum = partitionNum;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public String getLastDataModifiedTime() {
        return lastDataModifiedTime;
    }

    public void setLastDataModifiedTime(String lastDataModifiedTime) {
        this.lastDataModifiedTime = lastDataModifiedTime;
    }

    public String getLastVisitedTime() {
        return lastVisitedTime;
    }

    public void setLastVisitedTime(String lastVisitedTime) {
        this.lastVisitedTime = lastVisitedTime;
    }

    public String getUseCountOfMonth() {
        return useCountOfMonth;
    }

    public void setUseCountOfMonth(String useCountOfMonth) {
        this.useCountOfMonth = useCountOfMonth;
    }

    public String getValDensity() {
        return valDensity;
    }

    public void setValDensity(String valDensity) {
        this.valDensity = valDensity;
    }

    public String getAverageDailySize() {
        return averageDailySize;
    }

    public void setAverageDailySize(String averageDailySize) {
        this.averageDailySize = averageDailySize;
    }

    public String getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(String lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
    }

    public String getUpdateValDensityStatus() {
        return updateValDensityStatus;
    }

    public void setUpdateValDensityStatus(String updateValDensityStatus) {
        this.updateValDensityStatus = updateValDensityStatus;
    }

    public String getSecOrganizationClassify() {
        return secOrganizationClassify;
    }

    public LifeCycleInfo setSecOrganizationClassify(String secOrganizationClassify) {
        this.secOrganizationClassify = secOrganizationClassify;
        return this;
    }
}
