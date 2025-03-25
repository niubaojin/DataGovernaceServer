package com.synway.reconciliation.pojo.issue;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 下发数据按天统计表
 * @author DZH
 */
@Data
public class IssueDayStatistics implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 同一任务的唯一标识
     */
    private String jobId;

    /**
     * 账单日期（必填）：统计账单核账日期；
     */
    private Date checkTime;

    /**
     * 数据中文名称
     */
    private String dataNameZh;

    /**
     * 数据表名：数据英文名称；
     */
    private String dataNameEn;

    /**
     * 是否分区表；
     */
    private Integer isPartition;

    /**
     * 数据推送的目标地市
     */
    private String acceptCity;

    /**
     * 数据推送位置
     */
    private String exchangePosition;

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
    private Long providerDuration;

    /**
     * 地市入库时长
     */
    private Long acceptorDuration;

    /**
     * 总时长
     */
    private Long allDuration;

    /**
     * 任务状态
     */
    private String taskState;

    /**
     * 更新结果
     */
    private Integer updateResult;

    /**
     * 更新结果描述
     */
    private String updateResultReason;

    /**
     * 基线时间
     */
    private String baseTime;

    /**
     * 基线时间是否启用
     */
    private Integer baseTimeEnable;

    /**
     * 异常字段（用逗号分隔）
     */
    private String exceptionField;
}
