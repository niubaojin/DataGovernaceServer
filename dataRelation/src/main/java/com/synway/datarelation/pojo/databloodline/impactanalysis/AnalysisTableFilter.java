package com.synway.datarelation.pojo.databloodline.impactanalysis;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName AnalysisTableFilter
 * @description 表格筛选需要的一些内容
 * @date 2020/12/3 17:00
 */
public class AnalysisTableFilter {
    //上游表的层级筛选
    private List<Integer> upLevesFilter;
    //上游表的项目名筛选
    private List<String> upTableProjectsFilter;

    //下游表的层级筛选
    private List<Integer> downLevesFilter;
    //下游表的项目名筛选
    private List<String> downTableProjectsFilter;

    // 工作流的项目名筛选
    private List<String> workflowProjectFilter;

    // 应用系统中 应用系统名称的筛选
    private List<String> applicationNameFilter;

    /**
     * 上游表 生产阶段 的过滤筛选
     */
    private List<String> upStageFilter;

    /**
     * 下游表 生产阶段 的过滤筛选
     */
    private List<String> downStageFilter;

    public List<String> getUpStageFilter() {
        return upStageFilter;
    }

    public void setUpStageFilter(List<String> upStageFilter) {
        this.upStageFilter = upStageFilter;
    }

    public List<String> getDownStageFilter() {
        return downStageFilter;
    }

    public void setDownStageFilter(List<String> downStageFilter) {
        this.downStageFilter = downStageFilter;
    }

    public List<Integer> getUpLevesFilter() {
        return upLevesFilter;
    }

    public void setUpLevesFilter(List<Integer> upLevesFilter) {
        this.upLevesFilter = upLevesFilter;
    }

    public List<String> getUpTableProjectsFilter() {
        return upTableProjectsFilter;
    }

    public void setUpTableProjectsFilter(List<String> upTableProjectsFilter) {
        this.upTableProjectsFilter = upTableProjectsFilter;
    }

    public List<Integer> getDownLevesFilter() {
        return downLevesFilter;
    }

    public void setDownLevesFilter(List<Integer> downLevesFilter) {
        this.downLevesFilter = downLevesFilter;
    }

    public List<String> getDownTableProjectsFilter() {
        return downTableProjectsFilter;
    }

    public void setDownTableProjectsFilter(List<String> downTableProjectsFilter) {
        this.downTableProjectsFilter = downTableProjectsFilter;
    }

    public List<String> getWorkflowProjectFilter() {
        return workflowProjectFilter;
    }

    public void setWorkflowProjectFilter(List<String> workflowProjectFilter) {
        this.workflowProjectFilter = workflowProjectFilter;
    }

    public List<String> getApplicationNameFilter() {
        return applicationNameFilter;
    }

    public void setApplicationNameFilter(List<String> applicationNameFilter) {
        this.applicationNameFilter = applicationNameFilter;
    }
}
