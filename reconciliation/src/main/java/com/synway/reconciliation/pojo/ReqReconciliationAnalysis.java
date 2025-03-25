package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * @author : chenfei
 * @date : 2023/7/14 15:43
 * @describe :
 */

@Data
public class ReqReconciliationAnalysis {
    /**
     * 数据名
     */
    private String resourceName;
    /**
     * 1: 接入 2：处理  3：入库
     */
    private int linkType;
    /**
     * 1.提供方对账单 2.接入方对账单
     */
    private int billType;
    /**
     * 对账开始日期
     */
    private String startCheckTime;
    /**
     * 对账结束日期
     */
    private String endCheckTime;
    /**
     * 数据页数
     */
    private int pageNum;
    /**
     * 总页数
     */
    private int pageSize;
    /**
     * 排序字段
     */
    private String sortColumn;
    /**
     * 排序类型
     */
    private String sortType;
}
