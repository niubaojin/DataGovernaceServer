package com.synway.reconciliation.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 对账信息
 * @author Administrator
 */
@Data
public class ReconInfo {
    private String providerBillNo;
    private String providerNo;
    private String proResourceId;
    private int proDataCount;
    private int proDataSize;
    private String proDataFingerprint;
    private String proFingerPrintType;
    private String proStartNo;
    private String proEndNo;
    private String proSourceLocation;
    private String proCheckMethod;
    private String proLastFailBill;
    private int proDataSendTime;
    private String proDataSourceName;
    private String proDataAdministrator;
    private String proAdministratorTel;
    private int proDataSourceType;
    private String proServiceIp;
    private String proDataDirection;
    private String proDataSource;
    private String proPlatform;
    private String proInceptDataTime;
    private String proInceptDataId;

    private String acceptorBillNo;
    private String acceptorNo;
    private String accResourceId;
    private int accDataCount;
    private int accDataSize;
    private String accDataFingerprint;
    private String accFingerprintType;
    private String accStartNo;
    private String accEndNo;
    private String accSourceLocation;
    private String accCheckMethod;
    private String accLastFailBill;
    private int accDataArriveTime;
    private String accDataSourceName;
    private String accDataAdministrator;
    private String accAdministratorTel;
    private int accDataSourceType;
    private String accServiceIp;
    private String accDataDirection;
    private String accDataSource;
    private String accPlatform;
    private String accInceptDataTime;
    private String accInceptDataId;
}
