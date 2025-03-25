package com.synway.reconciliation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对账查询列表
 * @author ym
 */
public class ReconciliationListResponse {
    private String jobName;
    private String dataName;
    private String billNo;
    private String resourceId;
    private BigDecimal dataCount;
    private BigDecimal receiveDataCount;
    private String beginNo;
    private String endNo;
    private int billStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date arriveTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date checkTime;
    private String dataSourceName;
    /**
     * 1.数据包2数据文件3数据库
     */
    private String dataSourceType;
    private String accessSystem;
    private String primaryDatasourceCh;
    private String secondaryDatasourceCh;
    private String primaryOrganizationCh;
    private String secondaryOrganizationCh;
    private String firstOrganizationCh;
    private String billType;
    private String userId;
    private String userName;

    public String getFirstOrganizationCh() {
        return firstOrganizationCh;
    }

    public void setFirstOrganizationCh(String firstOrganizationCh) {
        this.firstOrganizationCh = firstOrganizationCh;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public BigDecimal getReceiveDataCount() {
        return receiveDataCount;
    }

    public void setReceiveDataCount(BigDecimal receiveDataCount) {
        this.receiveDataCount = receiveDataCount;
    }

    public String getBeginNo() {
        return beginNo;
    }

    public void setBeginNo(String beginNo) {
        this.beginNo = beginNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getAccessSystem() {
        return accessSystem;
    }

    public void setAccessSystem(String accessSystem) {
        this.accessSystem = accessSystem;
    }

    public String getPrimaryDatasourceCh() {
        return primaryDatasourceCh;
    }

    public void setPrimaryDatasourceCh(String primaryDatasourceCh) {
        this.primaryDatasourceCh = primaryDatasourceCh;
    }

    public String getSecondaryDatasourceCh() {
        return secondaryDatasourceCh;
    }

    public void setSecondaryDatasourceCh(String secondaryDatasourceCh) {
        this.secondaryDatasourceCh = secondaryDatasourceCh;
    }

    public String getPrimaryOrganizationCh() {
        return primaryOrganizationCh;
    }

    public void setPrimaryOrganizationCh(String primaryOrganizationCh) {
        this.primaryOrganizationCh = primaryOrganizationCh;
    }

    public String getSecondaryOrganizationCh() {
        return secondaryOrganizationCh;
    }

    public void setSecondaryOrganizationCh(String secondaryOrganizationCh) {
        this.secondaryOrganizationCh = secondaryOrganizationCh;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
