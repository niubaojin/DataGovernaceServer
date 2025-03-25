package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 对账汇总请求参数
 * @author Administrator
 */
@Data
public class SummarizeBillRequest {

    /**
     * 页码数
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 关键字检索
     */
    private String searchContent;

    /**
     * 排序字段
     */
    private String sortFiled;

    /**
     * 排序顺序
     */
    private String sortType;

    /**
     * 数据时间
     */
    private String dataTime;

    /**
     * 环节类型  1.接入 2.标准化 3.入库
     */
    private int linkType;
}
