package com.synway.reconciliation.pojo.issue;

import lombok.Data;

/**
 * 下发对账历史账单请求参数
 * @author DZH
 */
@Data
public class IssueHistoryRequest {

    /**
     * 账单日期
     */
    private String checkTime;

    /**
     * jobId
     */
    private String jobId;

    /**
     * 数据表名
     */
    private String dataNameEn;

    /**
     * 数据接收方
     */

    private String acceptCity;

    /**
     * 数据推送位置
     */
    private String exchangePosition;

    /**
     * 七天前日期 前端不用传 后端处理
     */
    private String endTime;

}
