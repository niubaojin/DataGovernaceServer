package com.synway.reconciliation.pojo.issue;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联任务
 * @author DZH
 */
@Data
public class RelateJob implements Serializable {
    /**
     * jobId
     */
    private String jobId;

    /**
     * 批次id
     */
    private String batchId;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 数据任务开始时间
     */
    private Date dataStartTime;

    /**
     * 数据任务结束时间
     */
    private Date dataEndTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 位置
     */
    private String local;
}
