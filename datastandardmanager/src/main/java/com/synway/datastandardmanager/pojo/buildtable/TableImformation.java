package com.synway.datastandardmanager.pojo.buildtable;

public class TableImformation {
    //表的项目名
    private  String projectName;

    //表的生命周期
    private  String tableLife;

    //表的生命周期
    private  String tableName;

    //表的类型
    private  String  tableType;

    //是否分区表
    private String isPartitionTable;

    //更新批次
    private String updatePreiod;

    //资源服务平台组织机构代码
    private String  belongSystemCode;

    //分区日期
    private String  partionDate;

    //分区size
    private String  partionSize;

    //总size
    private String  totalSize;

    //分区量
    private String  partionCount;

    //总的量
     private String  tatalCount;

    //更新周期
    private String updateDate;

    //协议编码
    private String sjxjbm;

    //表创建时间
    private String tableCreatedTime;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableLife() {
        return tableLife;
    }

    public void setTableLife(String tableLife) {
        this.tableLife = tableLife;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getIsPartitionTable() {
        return isPartitionTable;
    }

    public void setIsPartitionTable(String isPartitionTable) {
        this.isPartitionTable = isPartitionTable;
    }

    public String getUpdatePreiod() {
        return updatePreiod;
    }

    public void setUpdatePreiod(String updatePreiod) {
        this.updatePreiod = updatePreiod;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getBelongSystemCode() {
        return belongSystemCode;
    }

    public void setBelongSystemCode(String belongSystemCode) {
        this.belongSystemCode = belongSystemCode;
    }

    public String getPartionDate() {
        return partionDate;
    }

    public void setPartionDate(String partionDate) {
        this.partionDate = partionDate;
    }

    public String getPartionSize() {
        return partionSize;
    }

    public void setPartionSize(String partionSize) {
        this.partionSize = partionSize;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getPartionCount() {
        return partionCount;
    }

    public void setPartionCount(String partionCount) {
        this.partionCount = partionCount;
    }

    public String getTatalCount() {
        return tatalCount;
    }

    public void setTatalCount(String tatalCount) {
        this.tatalCount = tatalCount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSjxjbm() {
        return sjxjbm;
    }

    public void setSjxjbm(String sjxjbm) {
        this.sjxjbm = sjxjbm;
    }

    public String getTableCreatedTime() {
        return tableCreatedTime;
    }

    public void setTableCreatedTime(String tableCreatedTime) {
        this.tableCreatedTime = tableCreatedTime;
    }

    @Override
    public String toString() {
        return "TableImformation{" +
                "projectName='" + projectName + '\'' +
                ", tableLife='" + tableLife + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableType='" + tableType + '\'' +
                ", isPartitionTable='" + isPartitionTable + '\'' +
                ", updatePreiod='" + updatePreiod + '\'' +
                ", belongSystemCode='" + belongSystemCode + '\'' +
                ", partionDate='" + partionDate + '\'' +
                ", partionSize='" + partionSize + '\'' +
                ", totalSize='" + totalSize + '\'' +
                ", partionCount='" + partionCount + '\'' +
                ", tatalCount='" + tatalCount + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", sjxjbm='" + sjxjbm + '\'' +
                ", tableCreatedTime='" + tableCreatedTime + '\'' +
                '}';
    }
}
