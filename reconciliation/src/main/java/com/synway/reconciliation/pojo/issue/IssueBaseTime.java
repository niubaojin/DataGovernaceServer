package com.synway.reconciliation.pojo.issue;

import lombok.Data;

import java.io.Serializable;

/**
 * 基线时间存储
 * @author DZH
 */
@Data
public class IssueBaseTime implements Serializable {

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 数据表名
     */
    private String dataNameEn;

    /**
     * 基线时间
     */
    private String baseTime;

    /**
     * 是否启用
     */
    private Integer baseTimeEnable;
}
