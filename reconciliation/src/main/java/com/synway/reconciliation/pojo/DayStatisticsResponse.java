package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 日统计响应结果
 * @author Administrator
 */
@Data
public class DayStatisticsResponse {

    /**
     * 数据时间
     */
    private String dataTime;

    /**
     * 环节类型  接入 处理 入库 （tache）
     */
    private String linkType;

    /**
     * 提供方数量 （outputNum ）
     */
    private int providerCount;
}
