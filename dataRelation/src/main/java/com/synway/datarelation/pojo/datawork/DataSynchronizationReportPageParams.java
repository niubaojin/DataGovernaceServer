package com.synway.datarelation.pojo.datawork;

import java.io.Serializable;
import java.util.List;

/**
 * vue页面 数据同步报表的查询参数
 */
public class DataSynchronizationReportPageParams implements Serializable{
    private String nodeNameInput;
    private String sourceTableNameInput;
    private String targetTableNameInput;
    private String taskOpenDate;
    private String taskEndDate;
    private List<String> sourceTypeFilterList;
    private List<String> sourceTableProjectFilterList;
    private List<String> targetTypeFilterList;
    private List<String> targetTableProjectFilterList;
    private List<String> synchronizationStatusFilterList;
    private Integer pageNum;
    private Integer pageSize;
    private String sortName;
    private String sortBy;
    private String type = "1";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNodeNameInput() {
        return nodeNameInput;
    }

    public void setNodeNameInput(String nodeNameInput) {
        this.nodeNameInput = nodeNameInput;
    }

    public String getSourceTableNameInput() {
        return sourceTableNameInput;
    }

    public void setSourceTableNameInput(String sourceTableNameInput) {
        this.sourceTableNameInput = sourceTableNameInput;
    }

    public String getTargetTableNameInput() {
        return targetTableNameInput;
    }

    public void setTargetTableNameInput(String targetTableNameInput) {
        this.targetTableNameInput = targetTableNameInput;
    }

    public String getTaskOpenDate() {
        return taskOpenDate;
    }

    public void setTaskOpenDate(String taskOpenDate) {
        this.taskOpenDate = taskOpenDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public List<String> getSourceTypeFilterList() {
        return sourceTypeFilterList;
    }

    public void setSourceTypeFilterList(List<String> sourceTypeFilterList) {
        this.sourceTypeFilterList = sourceTypeFilterList;
    }

    public List<String> getSourceTableProjectFilterList() {
        return sourceTableProjectFilterList;
    }

    public void setSourceTableProjectFilterList(List<String> sourceTableProjectFilterList) {
        this.sourceTableProjectFilterList = sourceTableProjectFilterList;
    }

    public List<String> getTargetTypeFilterList() {
        return targetTypeFilterList;
    }

    public void setTargetTypeFilterList(List<String> targetTypeFilterList) {
        this.targetTypeFilterList = targetTypeFilterList;
    }

    public List<String> getTargetTableProjectFilterList() {
        return targetTableProjectFilterList;
    }

    public void setTargetTableProjectFilterList(List<String> targetTableProjectFilterList) {
        this.targetTableProjectFilterList = targetTableProjectFilterList;
    }

    public List<String> getSynchronizationStatusFilterList() {
        return synchronizationStatusFilterList;
    }

    public void setSynchronizationStatusFilterList(List<String> synchronizationStatusFilterList) {
        this.synchronizationStatusFilterList = synchronizationStatusFilterList;
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
}
