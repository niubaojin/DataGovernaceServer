package com.synway.datastandardmanager.pojo.standardpojo;


/**
 * STANDARDIZE.SYS 标准化系统的表
 */
public class Sys {
    private String sysID;
    private String sysEngName;
    private String sysChiName;
    private String sysMemo;
    private String sysStatus;
    private String sysAllMatch;

    public String getSysID() {
        return sysID;
    }

    public void setSysID(String sysID) {
        this.sysID = sysID;
    }

    public String getSysEngName() {
        return sysEngName;
    }

    public void setSysEngName(String sysEngName) {
        this.sysEngName = sysEngName;
    }

    public String getSysChiName() {
        return sysChiName;
    }

    public void setSysChiName(String sysChiName) {
        this.sysChiName = sysChiName;
    }

    public String getSysMemo() {
        return sysMemo;
    }

    public void setSysMemo(String sysMemo) {
        this.sysMemo = sysMemo;
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus;
    }

    public String getSysAllMatch() {
        return sysAllMatch;
    }

    public void setSysAllMatch(String sysAllMatch) {
        this.sysAllMatch = sysAllMatch;
    }
}
