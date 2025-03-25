package com.synway.reconciliation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标准化列表信息
 * @author ym
 */
public class StandardListResponse {
    private String dataName;
    private String resourceId;
    private BigDecimal accessProvide;
    private BigDecimal accessAccept;
    private String beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 状态 1正常 2异常
     */
    private int status;
    /**
     * 环节 1接入时2接入后
     */
    private int tache;
    private String dataDirection;
    private String primaryDatasourceCh;
    private String secondaryDatasourceCh;
    private String primaryOrganizationCh;
    private String firstOrganizationCh;
    private String secondaryOrganizationCh;
    private int isUpdating;
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

    public int getIsUpdating() {
        return isUpdating;
    }

    public void setIsUpdating(int isUpdating) {
        this.isUpdating = isUpdating;
    }

    public String getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(String dataDirection) {
        this.dataDirection = dataDirection;
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

    public int getTache() {
        return tache;
    }

    public void setTache(int tache) {
        this.tache = tache;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
