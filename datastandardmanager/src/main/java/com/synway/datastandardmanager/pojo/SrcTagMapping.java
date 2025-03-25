package com.synway.datastandardmanager.pojo;

public class SrcTagMapping {
    public String ID          ;
    public String createTime;
    public String updateTime ;
    public String tableID     ;
    public String sourceInfoID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getSourceInfoID() {
        return sourceInfoID;
    }

    public void setSourceInfoID(String sourceInfoID) {
        this.sourceInfoID = sourceInfoID;
    }
}
