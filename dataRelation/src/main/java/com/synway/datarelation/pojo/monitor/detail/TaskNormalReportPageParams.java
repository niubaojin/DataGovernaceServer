package com.synway.datarelation.pojo.monitor.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 节点运行状态的查询参数
 */
public class TaskNormalReportPageParams implements Serializable {
    private String input;
    private String taskOpenDateBegin;
    private String taskOpenDateEnd;
    private String taskEndDateBegin;
    private String taskEndDateEnd;
    private String runningTimeBegin;
    private String runningTimeEnd;
    private String outputRecordBegin;
    private String outputRecordEnd;
    private String cpuConsumptionBegin;
    private String cpuConsumptionEnd;
    private String memoryConsumptionBegin;
    private String memoryConsumptionEnd;
    private List<String> projectNameFilterList;
    private List<String> statusFilterList;
    private List<String> bizNameFilterList;
    private List<String> prgTypeFilterList;
    private Integer pageNum;
    private Integer pageSize;
    private String sortName;
    private String sortBy;
    private String taskIds;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
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

    public String getOutputRecordBegin() {
        return outputRecordBegin;
    }

    public void setOutputRecordBegin(String outputRecordBegin) {
        this.outputRecordBegin = outputRecordBegin;
    }

    public String getOutputRecordEnd() {
        return outputRecordEnd;
    }

    public void setOutputRecordEnd(String outputRecordEnd) {
        this.outputRecordEnd = outputRecordEnd;
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

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public List<String> getBizNameFilterList() {
        return bizNameFilterList;
    }

    public void setBizNameFilterList(List<String> bizNameFilterList) {
        this.bizNameFilterList = bizNameFilterList;
    }

    public List<String> getPrgTypeFilterList() {
        return prgTypeFilterList;
    }

    public void setPrgTypeFilterList(List<String> prgTypeFilterList) {
        this.prgTypeFilterList = prgTypeFilterList;
    }

    @Override
    public String toString() {
        return "TaskNormalReportPageParams{" +
                "input='" + input + '\'' +
                ", taskOpenDateBegin='" + taskOpenDateBegin + '\'' +
                ", taskOpenDateEnd='" + taskOpenDateEnd + '\'' +
                ", taskEndDateBegin='" + taskEndDateBegin + '\'' +
                ", taskEndDateEnd='" + taskEndDateEnd + '\'' +
                ", runningTimeBegin='" + runningTimeBegin + '\'' +
                ", runningTimeEnd='" + runningTimeEnd + '\'' +
                ", outputRecordBegin='" + outputRecordBegin + '\'' +
                ", outputRecordEnd='" + outputRecordEnd + '\'' +
                ", cpuConsumptionBegin='" + cpuConsumptionBegin + '\'' +
                ", cpuConsumptionEnd='" + cpuConsumptionEnd + '\'' +
                ", memoryConsumptionBegin='" + memoryConsumptionBegin + '\'' +
                ", memoryConsumptionEnd='" + memoryConsumptionEnd + '\'' +
                ", projectNameFilterList=" + projectNameFilterList +
                ", statusFilterList=" + statusFilterList +
                ", bizNameFilterList=" + bizNameFilterList +
                ", prgTypeFilterList=" + prgTypeFilterList +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortName='" + sortName + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", taskIds='" + taskIds + '\'' +
                '}';
    }
}
