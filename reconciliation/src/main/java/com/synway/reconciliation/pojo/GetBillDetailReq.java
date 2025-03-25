package com.synway.reconciliation.pojo;

/**
 * 账单详情查询条件
 * @author ym
 */
public class GetBillDetailReq {
    private int billTache;
    private int billType;
    private String billNo;
    private String dataTime;
    private String startTime;
    private String endTime;

    public int getBillTache() {
        return billTache;
    }

    public void setBillTache(int billTache) {
        this.billTache = billTache;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

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
}
