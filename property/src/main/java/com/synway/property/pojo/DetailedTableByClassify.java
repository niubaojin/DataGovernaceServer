package com.synway.property.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 根据分级分类查询表组织资产的详细信息
 *
 * @author 数据接入
 */
public class DetailedTableByClassify implements Serializable {
    private String mainClassify;
    private String primaryClassifyCh;
    private String secondaryClassifyCh;

    //一级二级组织分类、来源分类
    private String primaryDataSourceCh;
    private String secondaryDataSourceCh;
    private String primaryOrganizationCh;
    private String secondaryOrganizationCh;

    //表的项目名
    private String tableProject;
    // 表中文名
    private String name;
    // 表英文名
    private String tableNameEn;
    //表数据总行数 到昨天分区
    private String tableAllCount;
    // 日均数据量
    private String averageDailyVolume;
    // 昨天分区的数据量
    private String yesterdayCount;
    // 表的生命周期
    private String lifeCycle;
    //  有值率
    private Double valueRate;
    // 空值率
    private Double nullRate;
    //  状态
    private String tableState;
    //  全量/增量
    private String updateType;
    //   统计时间
    private Date statisticsTime;

    private String statisticsTimeStr;
    // 数据库类型
    private String tableType;
    // 频次(前一个月次数/总次数)
    private String frequency;
    // 活表类型  LIVING/NONLIVING  活表/死表
    private String liveType;
    //协议编码
    private String tableid;
    //注册状态 -1:未注册 1:已注册
    private String registerState;
    //object的objectid
    private String objectId;
    //物理存储
    private String tableSize;
    //启用/停用
    private String objectState;
    //二级分类
    private String classifyCh;

    //数据组织分类
    private String organizationClassifyCh;
    //数据来源分类
    private String dataSourceClassifyCh;

    //备注
    private String remarks;
    //标签
//    private String labels;

    //更新周期
    private String updateDate;
    //更新批次
    private String updatePeriod;

    //生产阶段
    private String productStage;

    //异常信息
    private String exceptionMessage;

    //存储周期状态
    private String lifeCycleStatus;

    //数据源id，用于关联权限表的仓库数据id
    private String resourceId;
    // 是否对标
    private String isStandard;

    @Override
    public String toString() {
        return "DetailedTableByClassify{" +
                "mainClassify='" + mainClassify + '\'' +
                ", primaryClassifyCh='" + primaryClassifyCh + '\'' +
                ", secondaryClassifyCh='" + secondaryClassifyCh + '\'' +
                ", primaryDataSourceCh='" + primaryDataSourceCh + '\'' +
                ", secondaryDataSourceCh='" + secondaryDataSourceCh + '\'' +
                ", primaryOrganizationCh='" + primaryOrganizationCh + '\'' +
                ", secondaryOrganizationCh='" + secondaryOrganizationCh + '\'' +
                ", tableProject='" + tableProject + '\'' +
                ", name='" + name + '\'' +
                ", tableNameEn='" + tableNameEn + '\'' +
                ", tableAllCount=" + tableAllCount +
                ", averageDailyVolume=" + averageDailyVolume +
                ", yesterdayCount=" + yesterdayCount +
                ", lifeCycle='" + lifeCycle + '\'' +
                ", valueRate=" + valueRate +
                ", nullRate=" + nullRate +
                ", tableState='" + tableState + '\'' +
                ", updateType='" + updateType + '\'' +
                ", statisticsTime=" + statisticsTime +
                ", statisticsTimeStr='" + statisticsTimeStr + '\'' +
                ", tableType='" + tableType + '\'' +
                ", frequency='" + frequency + '\'' +
                ", liveType='" + liveType + '\'' +
                ", tableid='" + tableid + '\'' +
                ", registerState='" + registerState + '\'' +
                ", objectId='" + objectId + '\'' +
                ", tableSize='" + tableSize + '\'' +
                ", objectState='" + objectState + '\'' +
                ", classifyCh='" + classifyCh + '\'' +
                ", organizationClassifyCh='" + organizationClassifyCh + '\'' +
                ", dataSourceClassifyCh='" + dataSourceClassifyCh + '\'' +
                ", remarks='" + remarks + '\'' +
//                ", labels='" + labels + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", updatePeriod='" + updatePeriod + '\'' +
                ", productStage='" + productStage + '\'' +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", lifeCycleStatus='" + lifeCycleStatus + '\'' +
                '}';
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getResourceId() { return resourceId; }

    public void setResourceId(String resourceId) { this.resourceId = resourceId; }

    public String getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(String lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getOrganizationClassifyCh() {
        return organizationClassifyCh;
    }

    public void setOrganizationClassifyCh(String organizationClassifyCh) {
        this.organizationClassifyCh = organizationClassifyCh;
    }

    public String getDataSourceClassifyCh() {
        return dataSourceClassifyCh;
    }

    public void setDataSourceClassifyCh(String dataSourceClassifyCh) {
        this.dataSourceClassifyCh = dataSourceClassifyCh;
    }

    public String getProductStage() {
        return productStage;
    }

    public void setProductStage(String productStage) {
        this.productStage = productStage;
    }

//    public String getLabels() {
//        return labels;
//    }
//
//    public void setLabels(String labels) {
//        this.labels = labels;
//    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClassifyCh() {
        return classifyCh;
    }

    public void setClassifyCh(String classifyCh) {
        this.classifyCh = classifyCh;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }

    public String getMainClassify() {
        return mainClassify;
    }

    public void setMainClassify(String mainClassify) {
        this.mainClassify = mainClassify;
    }

    public String getPrimaryClassifyCh() {
        return primaryClassifyCh;
    }

    public void setPrimaryClassifyCh(String primaryClassifyCh) {
        this.primaryClassifyCh = primaryClassifyCh;
    }

    public String getSecondaryClassifyCh() {
        return secondaryClassifyCh;
    }

    public void setSecondaryClassifyCh(String secondaryClassifyCh) {
        this.secondaryClassifyCh = secondaryClassifyCh;
    }

    public String getPrimaryDataSourceCh() {
        return primaryDataSourceCh;
    }

    public void setPrimaryDataSourceCh(String primaryDataSourceCh) {
        this.primaryDataSourceCh = primaryDataSourceCh;
    }

    public String getSecondaryDataSourceCh() {
        return secondaryDataSourceCh;
    }

    public void setSecondaryDataSourceCh(String secondaryDataSourceCh) {
        this.secondaryDataSourceCh = secondaryDataSourceCh;
    }

    public String getPrimaryOrganizationCh() {
        return primaryOrganizationCh;
    }

    public void setPrimaryOrganizationCh(String primaryOrganizationCh) {
        this.primaryOrganizationCh = primaryOrganizationCh;
    }

    public String getSecondaryOrganizationCh() {
        return secondaryOrganizationCh;
    }

    public void setSecondaryOrganizationCh(String secondaryOrganizationCh) {
        this.secondaryOrganizationCh = secondaryOrganizationCh;
    }

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

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

    public String getTableAllCount() {
        return tableAllCount;
    }

    public void setTableAllCount(String tableAllCount) {
        this.tableAllCount = tableAllCount;
    }

    public String getAverageDailyVolume() {
        return averageDailyVolume;
    }

    public void setAverageDailyVolume(String averageDailyVolume) {
        this.averageDailyVolume = averageDailyVolume;
    }

    public String getYesterdayCount() {
        return yesterdayCount;
    }

    public void setYesterdayCount(String yesterdayCount) {
        this.yesterdayCount = yesterdayCount;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public Double getValueRate() {
        return valueRate;
    }

    public void setValueRate(Double valueRate) {
        this.valueRate = valueRate;
    }

    public Double getNullRate() {
        return nullRate;
    }

    public void setNullRate(Double nullRate) {
        this.nullRate = nullRate;
    }

    public String getTableState() {
        return tableState;
    }

    public void setTableState(String tableState) {
        this.tableState = tableState;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public Date getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(Date statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public String getStatisticsTimeStr() {
        return statisticsTimeStr;
    }

    public void setStatisticsTimeStr(String statisticsTimeStr) {
        this.statisticsTimeStr = statisticsTimeStr;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getRegisterState() {
        return registerState;
    }

    public void setRegisterState(String registerState) {
        this.registerState = registerState;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTableSize() {
        return tableSize;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }


    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(String updatePeriod) {
        this.updatePeriod = updatePeriod;
    }
}
