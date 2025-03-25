package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 数据链路对账
 * @author Administrator
 */
@Data
public class BillLinkStatistics {

    /**
     * 协议编码
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 接入接收量
     */
    private int accAccessCount;

    /**
     * 接入提供量
     */
    private int proAccessCount;

    /**
     * 标准化接收量
     */
    private int accStandardCount;

    /**
     * 标准化提供量
     */
    private int proStandardCount;

    /**
     * 入库接收量
     */
    private int accStorageCount;

    /**
     * 入库提供量
     */
    private int proStorageCount;

    /**
     * 接入同比
     */
    private String accessSamePeriod;

    /**
     * 标准化环比
     */
    private String standardChainRatio;

    /**
     * 入库环比
     */
    private String storageChainRatio;

    /**
     * 数据时间
     */
    private String dataTime;

    /**
     * 统计时间
     */
    private String statisticTime;

    /**
     * 告警状态
     */
    private int alarmState;

    /**
     * 是否推送
     */
    private int isPush;

    /**
     * 接入前一天接收量
     */
    private int accAccessCountPre;

    /**
     * 接入前一天提供量
     */
    private int proAccessCountPre;

}
