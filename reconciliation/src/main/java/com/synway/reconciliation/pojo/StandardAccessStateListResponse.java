package com.synway.reconciliation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标准化接入系统状况
 * @author ym
 */
public class StandardAccessStateListResponse {
    /**
     * 接入系统
     */
    private String AccessSystem;
    /**
     * 来源
     */
    private String dataSourceName;
    /**
     * 数据名称
     */
    private String dataName;
    private String resourceId;
    private BigDecimal accessProvide;
    private BigDecimal accessAccept;
    private int status;
    private String beginNo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    private String userId;
    private String userName;

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAccessSystem() {
        return AccessSystem;
    }

    public void setAccessSystem(String accessSystem) {
        AccessSystem = accessSystem;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
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

    public String getBeginNo() {
        return beginNo;
    }

    public void setBeginNo(String beginNo) {
        this.beginNo = beginNo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
