package com.synway.reconciliation.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author : chenfei
 * @date : 2023/7/14 15:43
 * @describe :
 */

@Data
public class ReconciliationAnalysis {
    /**
     * 自增主键
     */
    private long rrId;
    /**
     * 协议编码
     */
    private String resourceId;
    /**
     * 数据名
     */
    private String resourceName;
    /**
     * 1: 接入 2：处理  3：入库
     */
    private int linkType;

    /**
     * 0未对账 1.对账成功 2.对账失败 3.已销账
     */
    private int billType;
    /**
     * 1.提供方对账单 2.接入方对账单
     */
    private int checkMethod;

    /**
     * 当日对账账单数
     */
    private long sumCount;
    /**
     * 当日对账成功账单数 1
     */
    private long successCount;
    /**
     * 当日对账成功百分比
     */
    private double successPercent;
    /**
     * 当日对账失败账单数 2
     */
    private long failCount;
    /**
     * 当日未对账账单数
     */
    private long unCheckCount;
    /**
     * 对账日期
     */
    private Date checkTime;
    /**
     * 分析记录入库时间
     */
    private Date createTime;
    /**
     * 分析记录更新时间
     */
    private Date updateTime;


}
