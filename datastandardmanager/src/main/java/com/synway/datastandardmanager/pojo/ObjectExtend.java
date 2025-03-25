package com.synway.datastandardmanager.pojo;

//用于新疆交付
public class ObjectExtend {
    private String tableProject;//项目名称
    private String responsiblePerson="";//负责人
    private String powerStatus="";//权限状态
    private String tableSize="";//物理存储(GB)
    private String tableCreatedTime;//表创建时间
    private String tableLastMetaModifiedTime;//表最后DDL时间TABLELASTMETAMODIFIEDTIME
    private String tableLastDataModifiedTime;//表数据最后修改时间
    private String startTime="";//开始时间
    private String endTime="";//结束时间
    private String executeTime="";//执行时长(分钟)
    private String addPartitionTime="";//添加分区时间
    private String partitiondate;//表分区日期
    private String partitionCount;//表分区数据行数

    public void setTableCreatedTime(String tableCreatedTime) {
        this.tableCreatedTime = tableCreatedTime;
    }

    public String getTableCreatedTime() {
        return tableCreatedTime;
    }

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }

    public String getTableSize() {
        if(this.tableSize!=null && tableSize.indexOf(".")!=-1) {
            this.tableSize = tableSize.substring(0, tableSize.indexOf(".") + 2);
        }
        return tableSize;
    }

    public String getTableLastMetaModifiedTime() {
        return tableLastMetaModifiedTime;
    }

    public void setTableLastMetaModifiedTime(String tableLastMetaModifiedTime) {
        this.tableLastMetaModifiedTime = tableLastMetaModifiedTime;
    }

    public String getTableLastDataModifiedTime() {
        return tableLastDataModifiedTime;
    }

    public void setTableLastDataModifiedTime(String tableLastDataModifiedTime) {
        this.tableLastDataModifiedTime = tableLastDataModifiedTime;
    }

    public String getPartitiondate() {
        return partitiondate;
    }

    public void setPartitiondate(String partitiondate) {
        this.partitiondate = partitiondate;
    }

    public String getPartitionCount() {
        return partitionCount;
    }

    public void setPartitionCount(String partitionCount) {
        this.partitionCount = partitionCount;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
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

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getAddPartitionTime() {
        return addPartitionTime;
    }

    public void setAddPartitionTime(String addPartitionTime) {
        this.addPartitionTime = addPartitionTime;
    }
}
