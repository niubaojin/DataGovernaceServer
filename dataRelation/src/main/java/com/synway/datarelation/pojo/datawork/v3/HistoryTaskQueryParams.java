package com.synway.datarelation.pojo.datawork.v3;

/**
 * 历史日志信息的查询参数
 */
public class HistoryTaskQueryParams {
    private Long nodeId;
    private String taskOpenDate;
    private String taskEndDate;
    private Boolean reacquireFlag = false;
    private Integer pageNum = 1;
    private Integer pageSize = 30;
    private String sortName;
    private String sortBy;
    private String type;

    // dataWork 第二版本查询历史数据时需要这个参数
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
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

    public Boolean getReacquireFlag() {
        return reacquireFlag;
    }

    public void setReacquireFlag(Boolean reacquireFlag) {
        this.reacquireFlag = reacquireFlag;
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
