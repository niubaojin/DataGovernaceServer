package com.synway.datastandardmanager.pojo.sourcedata;

import java.util.List;

@Deprecated
public class DubboBackData {
         private  boolean  isSuccess ;
         private List<OrganDataDubbo> data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<OrganDataDubbo> getData() {
        return data;
    }

    public void setData(List<OrganDataDubbo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DubboBackData{" +
                "isSuccess=" + isSuccess +
                ", data=" + data +
                '}';
    }
}
