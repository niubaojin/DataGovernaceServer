package com.synway.datarelation.pojo.common;

import java.io.Serializable;
import java.util.List;

/**
 *  应用血缘管理 模块的筛选信息 的查询参数
 * @author wangdongwei
 * @date 2021/3/10 14:00
 */
public class ApplicationFilterQuery implements Serializable {
    /**
     * 应用系统的筛选内容 点击的值
     */
    private String applicationFilter;

    /**
     * 一级模块 点击的值
     */
    private String oneLevelModuleFilter;

    /**
     *  二级模块 点击的值
     */
    private String twoLevelModuleFilter;

    public String getApplicationFilter() {
        return applicationFilter;
    }

    public void setApplicationFilter(String applicationFilter) {
        this.applicationFilter = applicationFilter;
    }

    public String getOneLevelModuleFilter() {
        return oneLevelModuleFilter;
    }

    public void setOneLevelModuleFilter(String oneLevelModuleFilter) {
        this.oneLevelModuleFilter = oneLevelModuleFilter;
    }

    public String getTwoLevelModuleFilter() {
        return twoLevelModuleFilter;
    }

    public void setTwoLevelModuleFilter(String twoLevelModuleFilter) {
        this.twoLevelModuleFilter = twoLevelModuleFilter;
    }
}
