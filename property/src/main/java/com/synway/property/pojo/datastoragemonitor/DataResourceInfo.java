package com.synway.property.pojo.datastoragemonitor;

/**
 * @author majia
 */
public class DataResourceInfo {
    private String resourceId;
    //项目名.表名
    private String tableId;
    //更新周期
    private String updateDate;
    //更新批次
    private String updatePeriod;
    //项目名称
    private String projectName;
    //表名
    private String tableName;
    //ads,odps
    private String organization;
    //单位代码
    private String belongSystemCode;
    //数据变更时间
    private String lastDataModifiedTime ;

    public String getBelongSystemCode() {
        return belongSystemCode;
    }

    public void setBelongSystemCode(String belongSystemCode) {
        this.belongSystemCode = belongSystemCode;
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
        if(tableId!=null) {
            this.projectName = tableId.split("\\.")[0];
            this.tableName = tableId.split("\\.")[1];
        }
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLastDataModifiedTime() {
        return lastDataModifiedTime;
    }

    public void setLastDataModifiedTime(String lastDataModifiedTime) {
        this.lastDataModifiedTime = lastDataModifiedTime;
    }
}
