package com.synway.reconciliation.pojo;

import lombok.Data;


/**
 * 对账信息结果
 * @author Administrator
 */
@Data
public class ReconInfoResult {
    private String resourceId;
    private String inceptDataTime;
    private String acceptorBillNo;
    private long accDataCount;
    private String providerBillNo;
    private long proDataCount;
    private int accBillLink;

    private int billState;
    private int billType;
    private int reconMethod;
    private int reconTime;
    private String reconResult;
}
