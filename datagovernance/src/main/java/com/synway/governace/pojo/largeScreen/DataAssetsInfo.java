package com.synway.governace.pojo.largeScreen;

/**
 * 数据资产
 *
 * @author ywj
 * @date 2020/8/12 10:31
 */
public class DataAssetsInfo {
    // 项目名称
    private String tableProject;
    // 表名
    private String tableName;
    // 是否在更(1:是;0:否)
    private int isUpdating;

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getIsUpdating() {
        return isUpdating;
    }

    public void setIsUpdating(int isUpdating) {
        this.isUpdating = isUpdating;
    }
}
