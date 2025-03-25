package com.synway.property.pojo.lifecycle;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流监控的查询参数
 */
public class LifeCyclePageParams implements Serializable {
    private String input;
    private String lastModifiedTimeBegin;
    private String lastModifiedTimeEnd;
    private String lastVisitedTimeBegin;
    private String lastVisitedTimeEnd;
    private String storgaeSizeBegin;
    private String storgaeSizeEnd;
    private String valDensityBegin;
    private String valDensityEnd;
    private String useConutOfMonthBegin;
    private String useConutOfMonthEnd;
    // 组织分类的 filter列表
    private List<String> organizationClassifyFilterList;
    // 平台的 filter列表
    private List<String> platformTypeFilterList;
    // 项目名称的 filter列表
    private List<String> projectNameFilterList;
    // 更新方式的 filter列表
    private List<String> updateTypeFilterList;
    // 是否分区的 filter列表
    private List<String> partitionFilterList;
    private Integer pageNum;
    private Integer pageSize;
    private String sortName;
    private String sortBy;

    //更新价值密度的集合
    private List<LifeCycleInfo> lifeCycleInfos;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getLastModifiedTimeBegin() {
        return lastModifiedTimeBegin;
    }

    public void setLastModifiedTimeBegin(String lastModifiedTimeBegin) {
        this.lastModifiedTimeBegin = lastModifiedTimeBegin;
    }

    public String getLastModifiedTimeEnd() {
        return lastModifiedTimeEnd;
    }

    public void setLastModifiedTimeEnd(String lastModifiedTimeEnd) {
        this.lastModifiedTimeEnd = lastModifiedTimeEnd;
    }

    public String getLastVisitedTimeBegin() {
        return lastVisitedTimeBegin;
    }

    public void setLastVisitedTimeBegin(String lastVisitedTimeBegin) {
        this.lastVisitedTimeBegin = lastVisitedTimeBegin;
    }

    public String getLastVisitedTimeEnd() {
        return lastVisitedTimeEnd;
    }

    public void setLastVisitedTimeEnd(String lastVisitedTimeEnd) {
        this.lastVisitedTimeEnd = lastVisitedTimeEnd;
    }

    public String getStorgaeSizeBegin() {
        return storgaeSizeBegin;
    }

    public void setStorgaeSizeBegin(String storgaeSizeBegin) {
        this.storgaeSizeBegin = storgaeSizeBegin;
    }

    public String getStorgaeSizeEnd() {
        return storgaeSizeEnd;
    }

    public void setStorgaeSizeEnd(String storgaeSizeEnd) {
        this.storgaeSizeEnd = storgaeSizeEnd;
    }

    public String getValDensityBegin() {
        return valDensityBegin;
    }

    public void setValDensityBegin(String valDensityBegin) {
        this.valDensityBegin = valDensityBegin;
    }

    public String getValDensityEnd() {
        return valDensityEnd;
    }

    public void setValDensityEnd(String valDensityEnd) {
        this.valDensityEnd = valDensityEnd;
    }

    public String getUseConutOfMonthBegin() {
        return useConutOfMonthBegin;
    }

    public void setUseConutOfMonthBegin(String useConutOfMonthBegin) {
        this.useConutOfMonthBegin = useConutOfMonthBegin;
    }

    public String getUseConutOfMonthEnd() {
        return useConutOfMonthEnd;
    }

    public void setUseConutOfMonthEnd(String useConutOfMonthEnd) {
        this.useConutOfMonthEnd = useConutOfMonthEnd;
    }

    public List<String> getOrganizationClassifyFilterList() {
        return organizationClassifyFilterList;
    }

    public void setOrganizationClassifyFilterList(List<String> organizationClassifyFilterList) {
        this.organizationClassifyFilterList = organizationClassifyFilterList;
    }

    public List<String> getPlatformTypeFilterList() {
        return platformTypeFilterList;
    }

    public void setPlatformTypeFilterList(List<String> platformTypeFilterList) {
        this.platformTypeFilterList = platformTypeFilterList;
    }

    public List<String> getProjectNameFilterList() {
        return projectNameFilterList;
    }

    public void setProjectNameFilterList(List<String> projectNameFilterList) {
        this.projectNameFilterList = projectNameFilterList;
    }

    public List<String> getUpdateTypeFilterList() {
        return updateTypeFilterList;
    }

    public void setUpdateTypeFilterList(List<String> updateTypeFilterList) {
        this.updateTypeFilterList = updateTypeFilterList;
    }

    public List<String> getPartitionFilterList() {
        return partitionFilterList;
    }

    public void setPartitionFilterList(List<String> partitionFilterList) {
        this.partitionFilterList = partitionFilterList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<LifeCycleInfo> getLifeCycleInfos() {
        return lifeCycleInfos;
    }

    public LifeCyclePageParams setLifeCycleInfos(List<LifeCycleInfo> lifeCycleInfos) {
        this.lifeCycleInfos = lifeCycleInfos;
        return this;
    }
}
