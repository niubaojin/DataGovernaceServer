package com.synway.reconciliation.pojo.issue;

import lombok.Data;

/**
 * 下发基线时间响应
 * @author DZH
 */
@Data
public class IssueBaseTimeResponse {

    /**
     * 同一任务的唯一标识
     */
    private String jobId;

    /**
     * 数据中文名称；
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
     * 基线时间
     */
    String baseTime;

    /**
     * 是否启用
     */
    Integer baseTimeEnable;
}
