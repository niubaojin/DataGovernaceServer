package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 盘点对账单
 * @author Administrator
 */
@Data
public class InventoryBill {
    private String acceptorBillNo;
    private String providerBillNo;
    private String dataResourceTag;
    private String dataResourceName;
    private String resourceId;
    private int dataCount;
    private int dataSize;
    private String startNo;
    private String endNo;
    private String dataFingerprint;
    private String fingerprintType;
    private int riseTime;
    private int checkTime;
    private int checkMethod;
    private int billType;
    private int billState;
    private String resultDes;
    private String lastFailBillNo;
    private String adminName;
    private String adminTel;
    private int reconStartTime;
    private int reconEndTime;

    private String acceptorNo;
    private String providerNo;
    private String sourceLocation;
    private String dataSourceName;
    private int dataSourceType;
    private String serviceIp;
    private String inceptDataTime;
    private String inceptDataId;
}
