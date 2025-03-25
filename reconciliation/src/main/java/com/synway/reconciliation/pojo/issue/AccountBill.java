package com.synway.reconciliation.pojo.issue;


import lombok.Data;

/**
 * 对账单
 * @author DZH
 */
@Data
public class AccountBill {
    /**
     * ELT时间 关联标识
     */
    private String inceptDataTime;

    /**
     * 提供方读总数
     */
    private Long dataCount;

    /**
     * 数据来源位置
     */
    private String sourceLocation;

    /**
     * 地市位置标识
     */
    private String local;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务执行的批次id
     */
    private String batchId;

    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 接收方账单里的 提供方账单编号
     */
    private String providerBillNo;
}
