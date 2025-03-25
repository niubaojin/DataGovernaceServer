package com.synway.dataoperations.pojo;


public class DataGFMsg {
    private String id;           // 主键id
    private String link;         // 环节
    private String dataName;     // 数据名称
    private String tableName;    // 数据表名
    private String tableId;      // 数据协议
    private String message;      // 告警消息
    private String updateTime;   // 最新时间
    private String sponsor;      // 发起人员（审核人员）
    private String sponsorTime;  // 流程发起时间
    private String manager;      // 治理人员
    private String managerTime;  // 治理时间
    private String reviewerTime; // 审核时间
    private String conclusion;   // 治理、审核结论
    private String status;       // 状态

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSponsorTime() {
        return sponsorTime;
    }

    public void setSponsorTime(String sponsorTime) {
        this.sponsorTime = sponsorTime;
    }

    public String getManagerTime() {
        return managerTime;
    }

    public void setManagerTime(String managerTime) {
        this.managerTime = managerTime;
    }

    public String getReviewerTime() {
        return reviewerTime;
    }

    public void setReviewerTime(String reviewerTime) {
        this.reviewerTime = reviewerTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
