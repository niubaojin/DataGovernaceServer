package com.synway.reconciliation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 接入列表信息
 * @author ym
 */
public class AccessListResponse {
    private String dataName;
    private String resourceId;
    private BigDecimal thirdpartyProvide;
    private BigDecimal accessProvide;
    private BigDecimal accessAccept;
    /**
     * 数据去向 1.入库2.标准化
     */
    private String dataDirection;
    /**
     * 环节 1接入 2入库
     */
    private int tache;
    private int status;
    private String beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    private String dataTime;
    private int isUpdating;
    private String primaryDatasourceCh;
    private String secondaryDatasourceCh;
    private String primaryOrganizationCh;
    private String firstOrganizationCh;
    private String secondaryOrganizationCh;
    private String isIssued;
    private String isLocal;
    private String userId;
    private String userName;
    private String tableNameEn;

    public String getFirstOrganizationCh() {
        return firstOrganizationCh;
    }

    public void setFirstOrganizationCh(String firstOrganizationCh) {
        this.firstOrganizationCh = firstOrganizationCh;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
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

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public int getIsUpdating() {
        return isUpdating;
    }

    public void setIsUpdating(int isUpdating) {
        this.isUpdating = isUpdating;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public BigDecimal getThirdpartyProvide() {
        return thirdpartyProvide;
    }

    public void setThirdpartyProvide(BigDecimal thirdpartyProvide) {
        this.thirdpartyProvide = thirdpartyProvide;
    }

    public BigDecimal getAccessProvide() {
        return accessProvide;
    }

    public void setAccessProvide(BigDecimal accessProvide) {
        this.accessProvide = accessProvide;
    }

    public BigDecimal getAccessAccept() {
        return accessAccept;
    }

    public void setAccessAccept(BigDecimal accessAccept) {
        this.accessAccept = accessAccept;
    }

    public int getTache() {
        return tache;
    }

    public void setTache(int tache) {
        this.tache = tache;
    }

    public String getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(String dataDirection) {
        this.dataDirection = dataDirection;
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

    public String getIsIssued() {
        return isIssued;
    }

    public void setIsIssued(String isIssued) {
        this.isIssued = isIssued;
    }
}
