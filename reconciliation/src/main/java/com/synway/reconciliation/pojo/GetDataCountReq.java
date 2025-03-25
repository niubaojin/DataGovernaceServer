package com.synway.reconciliation.pojo;

/**
 * 数据量查询条件
 * @author ym
 */
public class GetDataCountReq {
    private String startTime;
    private String endTime;
    private String dataName;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
