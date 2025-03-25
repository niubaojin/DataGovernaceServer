package com.synway.reconciliation.pojo.issue;

import lombok.Data;

import java.util.List;

/**
 * 基线列表请求接口
 * @author DZH
 */
@Data
public class IssueBaseTimeRequest {

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
     * 表类型 0不是分区表 1是分区表
     */
    private Integer tableType;

    /**
     * 数据接收方
     */
    private String acceptCity;

    /**
     * 是否启用基线时间
     */
    private Integer baseTimeEnable;

    /**
     * 批量操作的基线时间(请求列表不用传)
     */
    private List<IssueBaseTime> baseTimes;

    /**
     * 启用0、禁用1、清空2
     */
    private Integer type;

}
