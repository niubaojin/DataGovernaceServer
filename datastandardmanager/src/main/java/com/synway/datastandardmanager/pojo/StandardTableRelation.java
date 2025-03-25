package com.synway.datastandardmanager.pojo;

/**
 *  分级分类发生变化，目前只有数据组织分类
 */
public class StandardTableRelation {
    private String  mainClassifyId="";
    private String  mainClassifyCh="数据组织分类";
    // 一级分类id号
    private String  primaryClassifyId;
    // 一级分类中文名
    private String  primaryClassifyCh;
    // 二级分类id号
    private String  secondaryClassifyId;
    // 二级分类中文名
    private String  secondaryClassifyCh;
    // 三级分类id号
    private String  threeClassifyId;
    // 三级分类中文名
    private String  threeClassifyCh;
    // 表id
    private String  tableId;
    //表英文名
    private String  tableNameEn;
    //表中文名
    private String  tableNameCh;
    //
    private String newTableProject="";
    private String newTableNameEn="";

    public String getNewTableProject() {
        return newTableProject;
    }

    public void setNewTableProject(String newTableProject) {
        this.newTableProject = newTableProject;
    }

    public String getNewTableNameEn() {
        return newTableNameEn;
    }

    public void setNewTableNameEn(String newTableNameEn) {
        this.newTableNameEn = newTableNameEn;
    }

    public String getMainClassifyId() {
        return mainClassifyId;
    }

    public void setMainClassifyId(String mainClassifyId) {
        this.mainClassifyId = mainClassifyId;
    }

    public String getMainClassifyCh() {
        return mainClassifyCh;
    }

    public void setMainClassifyCh(String mainClassifyCh) {
        this.mainClassifyCh = mainClassifyCh;
    }

    public String getPrimaryClassifyId() {
        return primaryClassifyId;
    }

    public void setPrimaryClassifyId(String primaryClassifyId) {
        this.primaryClassifyId = primaryClassifyId;
    }

    public String getPrimaryClassifyCh() {
        return primaryClassifyCh;
    }

    public void setPrimaryClassifyCh(String primaryClassifyCh) {
        this.primaryClassifyCh = primaryClassifyCh;
    }

    public String getSecondaryClassifyId() {
        return secondaryClassifyId;
    }

    public void setSecondaryClassifyId(String secondaryClassifyId) {
        this.secondaryClassifyId = secondaryClassifyId;
    }

    public String getSecondaryClassifyCh() {
        return secondaryClassifyCh;
    }

    public void setSecondaryClassifyCh(String secondaryClassifyCh) {
        this.secondaryClassifyCh = secondaryClassifyCh;
    }

    public String getThreeClassifyId() {
        return threeClassifyId;
    }

    public void setThreeClassifyId(String threeClassifyId) {
        this.threeClassifyId = threeClassifyId;
    }

    public String getThreeClassifyCh() {
        return threeClassifyCh;
    }

    public void setThreeClassifyCh(String threeClassifyCh) {
        this.threeClassifyCh = threeClassifyCh;
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

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }
}
