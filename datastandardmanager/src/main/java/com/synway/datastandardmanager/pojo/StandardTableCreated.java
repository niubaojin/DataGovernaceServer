package com.synway.datastandardmanager.pojo;

// 展示创建表后存储的信息
public class StandardTableCreated {
    private String tableName;
    private String tableProject;
    private String tableBase;
    private String objectId;
    private String tableNameCh;
    // 包含项目名的表名
    private String tableNameEn;
    // 创建时间
    private String createTime;
    //创建人
    private String createUser;
    private String tableId;
    // 因为有后续操作，所以需要 dataId;
    private String dataId;
    // 数据源名称
    private String resName;
    private int importFlag = 0;

    @Override
    public String toString() {
        return "StandardTableCreated{" +
                "tableName='" + tableName + '\'' +
                ", tableProject='" + tableProject + '\'' +
                ", tableBase='" + tableBase + '\'' +
                ", objectId='" + objectId + '\'' +
                ", tableNameCh='" + tableNameCh + '\'' +
                ", tableNameEn='" + tableNameEn + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", tableId='" + tableId + '\'' +
                ", dataId='" + dataId + '\'' +
                ", importFlag=" + importFlag +
                '}';
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(int importFlag) {
        this.importFlag = importFlag;
    }


    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

    public String getTableBase() {
        return tableBase;
    }

    public void setTableBase(String tableBase) {
        this.tableBase = tableBase;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }
}
