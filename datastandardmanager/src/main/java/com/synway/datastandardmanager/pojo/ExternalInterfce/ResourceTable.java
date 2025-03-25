package com.synway.datastandardmanager.pojo.ExternalInterfce;

import java.util.Date;

public class ResourceTable {

    // 表概况信息id
    private String id;

    // 数据源id
    private String resourceId;

    // 表id
    private String tableId;

    // 表中文名 (协议编码中文名)
    private String tableName;

    //表英文名
    private String tableNameEn;

    // 源应用系统名称
    private String protocalCode;

    // 数据事权资源管理单位名称
    private String organization;

    //行政区划
    private String administrativeDivision;

    // 源应用系统建设公司
    private String levelOne;

    // 源应用系统管理单位
    private String levelTwo;

    // 单位代码
    private String levelThree;

    // 事权单位联系电话
    private String fromUnit;

    // 数据资源管理单位名称
    private String belongSystem;

    // 单位代码
    private String belongSystemCode;

    // 事权单位联系人
    private String bussinessMean;

    // 数据资源存储分中心
    private String receiveMethod;

    // 数据总条数
    private String totalRecord;

    // 增长量
    private String avgRecord;

    // 数据总存储量(M)
    private String totalStore;

    // 数据来源描述
    private String avgStore;

    // 源应用系统编号
    private String earliestRecord;

    // 协议编码
    private String recentRecord;

    // 元数据存储周期
    private String storePeriod;

    // 下发批次
    private String updatePeriod;

    // 更新周期
    private String updateDate;

    private int isFloder;

    private String secretLevel;

    private String pid;//ftp使用

    private Date updateTime;

    private int status;

    private String approvalId;

    private String approvalMemo;

    //问题数据探查保存的原因
    private String  errorMessage;

    //数据获取方式 (01 侦控  02 管控 03 管理 04 公开)
    private String acquisitionMode;

    /*******以下保存odps 获取到的表的基本信息*******/
    //表所占磁盘的物理大小
    private String physicalSize;

    //表创建时间
    private String createTime;

    //表数据所占的的盘古文件数
    private String  panguFileData;

    //数据的最后修改时间
    private String  lastDataModifiedTime;

    //DLL最后修改时间
    private  String  lastDDLModifiedTime;

    //表的生命周期
    private  String life;

    //表的所属用户
    private  String belongUser;

    //分区个数
    private String partitionCount;

    //是否是分区表
    private String  isPartition;

    //数据源来源
    private  String dataResourceOrigin;


    /****************/

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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getAcquisitionMode() {
        return acquisitionMode;
    }

    public void setAcquisitionMode(String acquisitionMode) {
        this.acquisitionMode = acquisitionMode;
    }

    public String getAdministrativeDivision() {
        return administrativeDivision;
    }

    public void setAdministrativeDivision(String administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }


    public String getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(String physicalSize) {
        this.physicalSize = physicalSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPanguFileData() {
        return panguFileData;
    }

    public void setPanguFileData(String panguFileData) {
        this.panguFileData = panguFileData;
    }

    public String getLastDataModifiedTime() {
        return lastDataModifiedTime;
    }

    public void setLastDataModifiedTime(String lastDataModifiedTime) {
        this.lastDataModifiedTime = lastDataModifiedTime;
    }

    public String getLastDDLModifiedTime() {
        return lastDDLModifiedTime;
    }

    public void setLastDDLModifiedTime(String lastDDLModifiedTime) {
        this.lastDDLModifiedTime = lastDDLModifiedTime;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(String belongUser) {
        this.belongUser = belongUser;
    }

    public String getPartitionCount() {
        return partitionCount;
    }

    public void setPartitionCount(String partitionCount) {
        this.partitionCount = partitionCount;
    }

    public String getIsPartition() {
        return isPartition;
    }

    public void setIsPartition(String isPartition) {
        this.isPartition = isPartition;
    }

    public String getDataResourceOrigin() {
        return dataResourceOrigin;
    }

    public void setDataResourceOrigin(String dataResourceOrigin) {
        this.dataResourceOrigin = dataResourceOrigin;
    }
}
