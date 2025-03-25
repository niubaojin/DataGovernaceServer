package com.synway.property.pojo.organizationdetail;

/**
 * @author majia
 */
public class DataResourceImformation {

    //表名
    private String tableNameEn;

    //表中文名
    private String tableNameChn;

    //数据中心
    private String dataCenter;

    //平台类型
    private String platForm;

    //项目名称
    private String projectName;

    // 表的生命周期
    private String life;

    //是否是分区表
    private String isPartition;

    //表创建时间
    private String createTime;

    //DDL最后变更时间
    private String lastDDLModifiedTime;

    //数据最后变更时间
    private String lastDataModifiedTime;

    //更新周期
    private String updateDate;

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableNameChn() {
        return tableNameChn;
    }

    public void setTableNameChn(String tableNameChn) {
        this.tableNameChn = tableNameChn;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getIsPartition() {
        return isPartition;
    }

    public void setIsPartition(String isPartition) {
        this.isPartition = isPartition;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastDDLModifiedTime() {
        return lastDDLModifiedTime;
    }

    public void setLastDDLModifiedTime(String lastDDLModifiedTime) {
        this.lastDDLModifiedTime = lastDDLModifiedTime;
    }

    public String getLastDataModifiedTime() {
        return lastDataModifiedTime;
    }

    public void setLastDataModifiedTime(String lastDataModifiedTime) {
        this.lastDataModifiedTime = lastDataModifiedTime;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
