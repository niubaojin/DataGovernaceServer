package com.synway.property.pojo.DataProcess;

/**
 *  数据历程的查询参数
 * @author wdw
 */
public class DataProcessRequest {

    // 表英文名
    private String tableNameEn;
    // tableId
    private String tableId;
    // 数据库类型

    private String dataBaseType;
    // 页面上的搜索框里面的值 目前是
    private String searchValue;
    // 操作模块的代码值  多个用英文逗号分隔
    private String moduleId;
    // 查询的开始时间 格式 yyyy-MM-dd HH:mm:ss
    private String startTime;
    // 查询的结束时间 格式 yyyy-MM-dd HH:mm:ss
    private String endTime;

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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
}
