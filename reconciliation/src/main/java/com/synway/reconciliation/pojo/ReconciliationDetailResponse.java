package com.synway.reconciliation.pojo;

/**
 * 账单详情
 * @author ym
 */
public class ReconciliationDetailResponse {

    private String name;
    private Detail data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Detail getData() {
        return data;
    }

    public void setData(Detail data) {
        this.data = data;
    }
}
