package com.synway.datarelation.pojo.monitor.page;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流监控的查询参数
 */
public class BusinessNormalReportPageParams implements Serializable {
    private String projectName;
    private String nodeNameInput;
    private String bizDateBegin;
    private String bizDateEnd;
    private String taskOpenDateBegin;
    private String taskOpenDateEnd;
    private String taskEndDateBegin;
    private String taskEndDateEnd;
    private String runningTimeBegin;
    private String runningTimeEnd;
    private String cpuConsumptionBegin;
    private String cpuConsumptionEnd;
    private String memoryConsumptionBegin;
    private String memoryConsumptionEnd;
    private List<String> projectNameFilterList;
    private List<String> statusFilterList;
    private Integer pageNum;
    private Integer pageSize;
    private String sortName;
    private String sortBy;
    private String flowName;

    public String getFlowName() { return flowName; }

    public void setFlowName(String flowName) { this.flowName = flowName; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getNodeNameInput() {
        return nodeNameInput;
    }

    public void setNodeNameInput(String nodeNameInput) {
        this.nodeNameInput = nodeNameInput;
    }

    public String getTaskOpenDateBegin() {
        return taskOpenDateBegin;
    }

    public void setTaskOpenDateBegin(String taskOpenDateBegin) {
        this.taskOpenDateBegin = taskOpenDateBegin;
    }

    public String getTaskOpenDateEnd() {
        return taskOpenDateEnd;
    }

    public void setTaskOpenDateEnd(String taskOpenDateEnd) {
        this.taskOpenDateEnd = taskOpenDateEnd;
    }

    public String getTaskEndDateBegin() {
        return taskEndDateBegin;
    }

    public void setTaskEndDateBegin(String taskEndDateBegin) {
        this.taskEndDateBegin = taskEndDateBegin;
    }

    public String getTaskEndDateEnd() {
        return taskEndDateEnd;
    }

    public void setTaskEndDateEnd(String taskEndDateEnd) {
        this.taskEndDateEnd = taskEndDateEnd;
    }

    public String getRunningTimeBegin() {
        return runningTimeBegin;
    }

    public void setRunningTimeBegin(String runningTimeBegin) {
        this.runningTimeBegin = runningTimeBegin;
    }

    public String getRunningTimeEnd() {
        return runningTimeEnd;
    }

    public void setRunningTimeEnd(String runningTimeEnd) {
        this.runningTimeEnd = runningTimeEnd;
    }

    public String getCpuConsumptionBegin() {
        return cpuConsumptionBegin;
    }

    public void setCpuConsumptionBegin(String cpuConsumptionBegin) {
        this.cpuConsumptionBegin = cpuConsumptionBegin;
    }

    public String getCpuConsumptionEnd() {
        return cpuConsumptionEnd;
    }

    public void setCpuConsumptionEnd(String cpuConsumptionEnd) {
        this.cpuConsumptionEnd = cpuConsumptionEnd;
    }

    public String getMemoryConsumptionBegin() {
        return memoryConsumptionBegin;
    }

    public void setMemoryConsumptionBegin(String memoryConsumptionBegin) {
        this.memoryConsumptionBegin = memoryConsumptionBegin;
    }

    public String getMemoryConsumptionEnd() {
        return memoryConsumptionEnd;
    }

    public void setMemoryConsumptionEnd(String memoryConsumptionEnd) {
        this.memoryConsumptionEnd = memoryConsumptionEnd;
    }

    public List<String> getProjectNameFilterList() {
        return projectNameFilterList;
    }

    public void setProjectNameFilterList(List<String> projectNameFilterList) {
        this.projectNameFilterList = projectNameFilterList;
    }

    public List<String> getStatusFilterList() {
        return statusFilterList;
    }

    public void setStatusFilterList(List<String> statusFilterList) {
        this.statusFilterList = statusFilterList;
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

    public String getBizDateBegin() {
        return bizDateBegin;
    }

    public void setBizDateBegin(String bizDateBegin) {
        this.bizDateBegin = bizDateBegin;
    }

    public String getBizDateEnd() {
        return bizDateEnd;
    }

    public void setBizDateEnd(String bizDateEnd) {
        this.bizDateEnd = bizDateEnd;
    }
}
