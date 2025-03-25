package com.synway.reconciliation.service;


import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.pojo.issue.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 下发对账
 * @author DZH
 */
public interface IssueReconciliationService {

    /**
     * 分页获取下发对账统计
     * @param request
     * @return
     */
    Map<String,Object> getPageIssueBill(IssueReconciliationRequestExtend request);

    /**
     * 获取下发对账历史账单
     * @param request
     * @return
     */
    List<IssueHistoryResponse> getIssueHistoryBill(IssueHistoryRequest request);

    /**
     * 分页获取基线时间
     * @param request
     * @return
     */
    Map<String,Object> getPageBaseTime(IssueBaseTimeRequest request);

    /**
     * 新增或修改基线时间
     * @param request
     * @return
     */
    Boolean saveOrUpdateBaseTime(IssueBaseTimeRequest request);

    /**
     * 删除一条基线时间
     * @param issueBaseTime
     * @return
     */
    Boolean deleteOneBaseTime(IssueBaseTime issueBaseTime);

    /**
     * 快速操作基线时间 包括 一键启用0、禁用1、清空2
     * @param request
     * @return
     */
    Boolean quickMultiBaseTime(IssueBaseTimeRequest request);

    /**
     * 获取数据接收方过滤项
     * @return
     */
    List<String> getAcceptCity();

    /**
     * 下发对账统计
     * @return
     */
    void issueReconciliationStatistics();

    /**
     * 插入下发对账提供方账单
     * @param bills
     */
    void insertIssueProviderBill(List<DacProviderBill> bills);

    /**
     * 插入下发对账提供方账单
     * @param bills
     */
    void insertIssueAcceptorBill(List<DacAcceptorBill> bills);

    /**
     * 插入任务相关的信息
     * @param jobInfos
     */
    void insertTaskInfos(List<RelateJob> jobInfos);

    /**
     * 导出下发对账统计
     * @param request
     * @return
     */
    List<IssueDayStatisticsExcel> exportIssueStatistics(IssueReconciliationRequestExtend request);

    /**
     * 获取对账信息
     * @return
     */
    RestReconInfo getReconInfo();

    /**
     * 获取下发接入量
     * @return
     */
    List<IssueAccessCount> getIssueAccessCount();
}
