package com.synway.dataoperations.pojo;

public class RequestParameter {
    // 告警中心
    private String searchTime;          // 查询时间
    private String searchName;          // 查询名称
    private String alarmModule;         // 告警模块
    private String alarmStatus;         // 告警状态
    private String alarmStatusFilter;   // 告警状态筛选
    private String alarmModuleFilter;   // 告警模块筛选

    // 治理跟踪
    private String linkFilter;          // 环节筛选
    private String sponsorFilter;       // 发起人员筛选
    private String managerFilter;       // 治理人员筛选
    // 治理审核小对话框
    private String id;                  // 主键id
    private String title;               // 治理操作/审核操作
    private String pass;                // 通过/不通过
    private String conclusion;          // 结论
    private String status;              // 跟踪状态

    private Integer pageNum;
    private Integer pageSize;
    private String sortName;
    private String sortBy;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAlarmStatusFilter() {
        return alarmStatusFilter;
    }

    public void setAlarmStatusFilter(String alarmStatusFilter) {
        this.alarmStatusFilter = alarmStatusFilter;
    }

    public String getAlarmModuleFilter() {
        return alarmModuleFilter;
    }

    public void setAlarmModuleFilter(String alarmModuleFilter) {
        this.alarmModuleFilter = alarmModuleFilter;
    }

    public String getLinkFilter() {
        return linkFilter;
    }

    public void setLinkFilter(String linkFilter) {
        this.linkFilter = linkFilter;
    }

    public String getSponsorFilter() {
        return sponsorFilter;
    }

    public void setSponsorFilter(String sponsorFilter) {
        this.sponsorFilter = sponsorFilter;
    }

    public String getManagerFilter() {
        return managerFilter;
    }

    public void setManagerFilter(String managerFilter) {
        this.managerFilter = managerFilter;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getAlarmModule() {
        return alarmModule;
    }

    public void setAlarmModule(String alarmModule) {
        this.alarmModule = alarmModule;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}
