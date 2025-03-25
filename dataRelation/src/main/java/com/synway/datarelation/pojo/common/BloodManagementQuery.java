package com.synway.datarelation.pojo.common;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 应用血缘管理的查询参数 后端分页
 * @author wangdongwei
 * @ClassName BloodManagementQuery
 * @description
 * @date 2020/12/17 17:15
 */
public class BloodManagementQuery implements Serializable {
    /**
     * 请输入关键字的相关信息
     */
    private String inputSearch;

    /**
     * 排序的相关参数
     * 字段顺序  desc / asc
     */
    private String sortOrder = "desc";
    /**
     * 字段名称
     */
    private String sort;


    /**
     * 应用系统的筛选内容
     */
    private List<String> applicationFilter;

    /**
     * 一级模块
     */
    private List<String> oneLevelModuleFilter;

    /**
     *  二级模块
     */
    private List<String> twoLevelModuleFilter;

    /**
     *  三级模块
     */
    private List<String> threeLevelModuleFilter;


    public String getInputSearch() {
        return inputSearch;
    }

    public void setInputSearch(String inputSearch) {
        this.inputSearch = inputSearch;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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
