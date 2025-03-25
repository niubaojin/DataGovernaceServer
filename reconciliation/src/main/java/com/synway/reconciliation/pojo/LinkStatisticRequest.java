package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 链路统计请求参数
 * @author Administrator
 */
@Data
public class LinkStatisticRequest {

    /**
     * 数据时间 2023-12-06 00:00:00  具体到天即可
     */
    private String dataTime;

    /**
     * 数据资源名称关键字
     */
    private String dataName;

    /**
     * 协议编码
     */
    private String resourceId;

    /**
     * 告警状态1.正常 2.异常
     */
    private int alarmState;

    /**
     * 排序字段
     */
    private String sortColumn;

    /**
     * 排序顺序
     */
    private String sortType;

    /**
     * 是否推送（暂时不用）
     */
    private int isPush;
}
