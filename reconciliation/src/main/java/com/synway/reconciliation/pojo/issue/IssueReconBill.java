package com.synway.reconciliation.pojo.issue;

import lombok.Data;

import java.util.Date;


/**
 * 数据下发对账单
 * @author DZH
 */
@Data
public class IssueReconBill {

    /**
     * 接入时间戳 关联账单 以及判断账单是否是今天
     */
    private String inceptDataTime;

    /**
     * 提供方（总队）读总数
     */
    private Long providerReadCount;

    /**
     * 提供方（总队）写总数
     */
    private Long providerWriteCount;

    /**
     * 接收方读总数
     */
    private Long acceptorReadCount;

    /**
     * 接收方写总数
     */
    private Long acceptorWriteCount;

    /**
     * 交换位置 topic
     */
    private String sourceLocation;

    /**
     * 接收位置
     */
    private String local;

    /**
     * 提供方任务id 用来查找表信息
     */
    private String providerJobId;

    /**
     * 接收方任务id 用来查找表信息
     */
    private String acceptorJobId;

    /**
     * 数据来源名称 英文表名
     */
    private String dataSourceName;

    /**
     * 提供方批次id
     */
    private String providerBatchId;

    /**
     * 接收方批次id
     */
    private String acceptorBatchId;

}
