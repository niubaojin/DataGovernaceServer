package com.synway.reconciliation.pojo;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 告警电话号码
 * @author ywj
 */
public class AlarmPhoneNumber {
    private String id;
    private String name;
    private String phoneNumber;
    private String alarmType;
    private boolean billAlarmCheck;
    private boolean qualityAlarmCheck;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public boolean getBillAlarmCheck() {
        return (StringUtils.isNotBlank(this.alarmType) && this.alarmType.indexOf("1") != -1) ? true : false;
    }

    public boolean getQualityAlarmCheck() {
        return (StringUtils.isNotBlank(this.alarmType) && this.alarmType.indexOf("2") != -1) ? true : false;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
