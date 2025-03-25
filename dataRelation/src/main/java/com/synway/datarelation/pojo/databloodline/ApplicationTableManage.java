package com.synway.datarelation.pojo.databloodline;

import java.util.List;

/**
 * 应用系统管理的返回数据
 * @author wangdongwei
 * @ClassName ApplicationTableManage
 * @date 2020/12/21 13:56
 */
public class ApplicationTableManage {

    /**
     * 查询到的表数据量
     */
    private int tableCount;

    /**
     * 表格中的数据
     */
    private List<ApplicationSystemTable> applicationSystemTableList;

    /**
     * 应用系统筛选内容
     */
    private List<String> applicationFilter;

    /**
     * 一级模块筛选内容
     */
    private List<String> oneLevelModuleFilter;

    /**
     * 二级模块筛选内容
     */
    private List<String> twoLevelModuleFilter;
    /**
     * 三级模块筛选内容
     */
    private List<String> threeLevelModuleFilter;

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public List<ApplicationSystemTable> getApplicationSystemTableList() {
        return applicationSystemTableList;
    }

    public void setApplicationSystemTableList(List<ApplicationSystemTable> applicationSystemTableList) {
        this.applicationSystemTableList = applicationSystemTableList;
    }

    public List<String> getApplicationFilter() {
        return applicationFilter;
    }

    public void setApplicationFilter(List<String> applicationFilter) {
        this.applicationFilter = applicationFilter;
    }

    public List<String> getOneLevelModuleFilter() {
        return oneLevelModuleFilter;
    }

    public void setOneLevelModuleFilter(List<String> oneLevelModuleFilter) {
        this.oneLevelModuleFilter = oneLevelModuleFilter;
    }

    public List<String> getTwoLevelModuleFilter() {
        return twoLevelModuleFilter;
    }

    public void setTwoLevelModuleFilter(List<String> twoLevelModuleFilter) {
        this.twoLevelModuleFilter = twoLevelModuleFilter;
    }

    public List<String> getThreeLevelModuleFilter() {
        return threeLevelModuleFilter;
    }

    public void setThreeLevelModuleFilter(List<String> threeLevelModuleFilter) {
        this.threeLevelModuleFilter = threeLevelModuleFilter;
    }
}
