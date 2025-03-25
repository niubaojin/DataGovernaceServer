package com.synway.reconciliation.pojo.issue;

import lombok.Data;

import java.util.Date;

/**
 * 下发对账历史账单响应结果
 * @author DZH
 */
@Data
public class IssueHistoryResponse {

    /**
     * 账单日期（必填）：统计账单核账日期；
     */
    private Date checkTime;

    /**
     * 源表数据量
     */
    private Long sourceCount;

    /**
     * 总队读出数据量
     */
    private Long providerReadCount;

    /**
     * 总队推送数据量
     */
    private Long providerWriteCount;

    /**
     * 地市接收数据量
     */
    private Long acceptorReadCount;

    /**
     * 地市入库数据量
     */
    private Long acceptorWriteCount;

    /**
     * 目标表数据量
     */
    private Long destCount;

    /**
     * 历程环比
     */
    private String courseChainRatio;

    /**
     * 历史同比
     */
    private String historicalComparison;

    /**
     * 总队开始读出时间
     */
    private Date providerStartTime;

    /**
     * 总队推送完成时间
     */
    private Date providerEndTime;

    /**
     * 地市开始接收时间
     */
    private Date acceptorStartTime;

    /**
     * 地市入库完成时间
     */
    private Date acceptorEndTime;

    /**
     * 总队推送时长
     */
    private Integer providerDuration;

    /**
     * 地市入库时长
     */
    private Integer acceptorDuration;

    /**
     * 总时长
     */
    private Integer allDuration;

    /**
     * 基线时间
     */
    private String baseTime;

    /**
     * 更新结果
     */
    private Integer updateResult;

    /**
     * 更新结果描述
     */
    private String updateResultReason;
}
