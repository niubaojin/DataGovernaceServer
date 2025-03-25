package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 对账分析请求参数
 * @author Administrator
 */
@Data
public class ReconAnalysisRequest {

    /**
     * 数据名
     */

    private String resourceName;
    /**
     * 1: 接入 2：处理  3：入库
     */
    private int linkType;

    /**
     * 分析开始日期
     */

    private String startCheckTime;
    /**
     * 分析结束日期
     */

    private String endCheckTime;

    /**
     * 数据页数
     */
    private int pageNum;

    /**
     * 分页大小
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
