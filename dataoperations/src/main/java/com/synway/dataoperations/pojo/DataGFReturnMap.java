package com.synway.dataoperations.pojo;

import java.util.List;
import java.util.Map;

public class DataGFReturnMap {
    // 环节过滤列表
    private List<Map<String,String>> linkFilterList;
    // 发起人员过滤列表
    private List<Map<String,String>> sponsorFilterList;
    // 治理人员过滤列表
    private List<Map<String,String>> managerFilterList;
    // 告警数据
    private List<DataGFMsg> rows;
    //数据总量
    private long total;

    public List<Map<String, String>> getLinkFilterList() {
        return linkFilterList;
    }

    public void setLinkFilterList(List<Map<String, String>> linkFilterList) {
        this.linkFilterList = linkFilterList;
    }

    public List<Map<String, String>> getSponsorFilterList() {
        return sponsorFilterList;
    }

    public void setSponsorFilterList(List<Map<String, String>> sponsorFilterList) {
        this.sponsorFilterList = sponsorFilterList;
    }

    public List<Map<String, String>> getManagerFilterList() {
        return managerFilterList;
    }

    public void setManagerFilterList(List<Map<String, String>> managerFilterList) {
        this.managerFilterList = managerFilterList;
    }

    public List<DataGFMsg> getRows() {
        return rows;
    }

    public void setRows(List<DataGFMsg> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
