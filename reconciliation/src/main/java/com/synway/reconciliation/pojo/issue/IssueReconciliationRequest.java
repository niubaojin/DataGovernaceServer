package com.synway.reconciliation.pojo.issue;


import lombok.Data;


/**
 * 下发对账请求列表
 * @author DZH
 */
@Data
public class IssueReconciliationRequest {
    /**
     * 账单日期start
     */
    private String startTime;

    /**
     * 账单日期end
     */
    private String endTime;

    /**
     * 页码数
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 搜索内容（数据名称表名）
     */
    private String searchContent;

    /**
     * 是否基线任务 0不是 1是
     */
    private Integer isBaseJob;

    /**
     * 是否异常任务 0不是 1是
     */
    private Integer isExceptionJob;

    /**
     * 排序字段
     */
    private String sortFiled;

    /**
     * 排序顺序 ase desc
     */
    private String sortType;

    /**
     * 表类型 0不是分区表 1是分区表
     */
    private Integer isPartition;

    /**
     * 数据接收方
     */
    private String acceptCity;

    /**
     * 任务状态 0失败 1成功 2进行 两位数 比如00 就是总队任务地市任务都失败
     */
    private String taskState;

    /**
     * 更新结果 0异常 1正常
     */
    private Integer updateResult;
}


