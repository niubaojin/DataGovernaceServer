package com.synway.datarelation.pojo.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/3/10 14:07
 */
public class ApplicationFilterResult implements Serializable {
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
