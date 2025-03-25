package com.synway.reconciliation.service;


import com.synway.reconciliation.pojo.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface InventoryReconService {

    /**
     * 获取数据链路对账列表
     * @param request
     * @return
     */
    Map<String,Object> getReconciliationStatisticsList(LinkStatisticRequest request);

    /**
     * 获取近8天数据链路对账列表
     * @param request
     * @return
     */
    List<DayStatisticsResponse> getRecentDailyStatisticsList(LinkStatisticRequest request);

    /**
     * 分页获取对账分析列表
     * @param request
     * @return
     */
    Map<String,Object> getReconciliationAnalysisList(ReconAnalysisRequest request);

    /**
     * 分页获取对账信息
     * @param request
     * @return
     * @throws ParseException
     */
    Map<String,Object> getReconciliationList(ReconInfoRequest request) throws ParseException;

    /**
     * 获取数据对账详情
     * @param request
     * @return
     */
    Map<String,Object> getReconciliationDetail(ReconInfoRequest request);

    /**
     * 汇总账单 根据实例汇总
     */
    void summarizeBillByInstance();

    /**
     * 根据实例对账
     */
    void reconByInstance();

    /**
     * 更新某条对账 要传 inceptDataTime、resourceId、type
     * @param reconParam
     */
    void updateOneRecon(ReconParam reconParam);

    /**
     * 数据包对账
     * @param reconParam
     * @return
     */
    List<ReconInfoResponse> dataPacketRecon(ReconParam reconParam);

    /**
     * 按时间范围盘点对账
     */
    void inventoryRecon();

    /**
     * 数据账单统计
     */
    void reconStatistics();

    /**
     * 对账分析
     */
    void reconAnalysis();

    /**
     * 推送异常数据
     */
    void pushAbnormalData();

    /**
     * 获取汇总数据
     * @param request
     */
    void getSummarizeBill(SummarizeBillRequest request);

    /**
     * 首页异常链路数获取
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> getAbnormalReconciliationNum();

    /**
     * 有数据的日期集合获取
     * @param request 查询条件
     * @return java.util.List<java.lang.String>
     */
    List<String> getDataTimeList(ReconInfoRequest request);
}
