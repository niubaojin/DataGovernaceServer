package com.synway.reconciliation.dao;

import com.synway.reconciliation.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 盘点对账
 * @author Administrator
 */
@Mapper
@Repository
public interface InventoryReconDao {

    /**
     * 获取组织树相关信息
     * @param request
     * @return
     */
    List<OrganizationInfo> getOrganizationTreeForBill(ReconInfoRequest request);

    /**
     * 获取数据链路对账数据列表
     * @param request
     * @return
     */
    List<BillLinkStatistics> getReconciliationStatisticsList(LinkStatisticRequest request);

    /**
     * 获取数据链路对账有效时间
     * @return
     */
    List<String> getReconciliationStatisticsDataTimeList();

    /**
     * 获取近8天内数据链路对账
     * @param resourceId
     * @param dataTime
     * @return
     */
    List<BillLinkStatistics> getRecentDailyStatisticsList(@Param("resourceId")String resourceId, @Param("dataTime")String dataTime);

    /**
     * 分页获取数据对账分析
     * @param request
     * @return
     */
    List<ReconciliationAnalysis> getReconciliationAnalysisList(ReconAnalysisRequest request);

    /**
     * 获取对账分析总数
     * @param request
     * @return
     */
    int countReconciliationAnalysis(ReconAnalysisRequest request);

    /**
     * 分页获取对账信息列表
     * @param request
     * @return
     */
    List<ReconInfoResponse> getReconciliationList(ReconInfoRequest request);

    /**
     * 获取对账信息列表总数
     * @param request
     * @return
     */
    int countReconciliationList(ReconInfoRequest request);

    /**
     * 获取数据包对账列表
     * @param reconParam
     * @return
     */
    List<ReconInfoResponse> getDataPacketReconList(ReconParam reconParam);

    /**
     * 根据账单编号获取账单
     * @param SJJRF_DZDBH
     * @return
     */
    List<DacAcceptorBill> getAcceptorByNo(@Param("SJJRF_DZDBH") String SJJRF_DZDBH);

    /**
     * 根据账单标号获取账单
     * @param SJTGF_DZDBH
     * @return
     */
    List<DacProviderBill> getProviderByNo(@Param("SJTGF_DZDBH") String SJTGF_DZDBH);
    /**
     * 汇总接入接收方账单
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    void summarizeAccessAcceptor(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 汇总接入提供方账单
     * @param startDate
     * @param endDate
     */
    void summarizeAccessProvider(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 汇总标准化接收方账单
     * @param startDate
     * @param endDate
     */
    void summarizeStandardAcceptor(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 汇总标准化提供方账单
     * @param startDate
     * @param endDate
     */
    void summarizeStandardProvider(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 汇总入库接收方账单
     * @param startDate
     * @param endDate
     */
    void summarizeStorageAcceptor(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 汇总入库提供方账单
     * @param startDate
     * @param endDate
     */
    void summarizeStorageProvider(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 获取对账需要的信息
     * 如果时间不为空 resourceId为空 获取的是 未对账或对账失败的列表 给定时对账使用
     * 如果时间为空  resourceId不为空 获取的是 某条对账数据 给重新对账接口使用
     * @param reconParam 对账参数
     * @return ReconInfoResult
     */
    List<ReconInfoResult> getReconInfo(ReconParam reconParam);

    /**
     * 对账标准化提供方和入库接入方
     * @param reconParam 对账参数
     * @return ReconInfo
     */
    List<ReconInfo> reconStandardToStorage(ReconParam reconParam);

    /**
     * 对账接入提供方和标准化接入方
     * @param reconParam 对账参数
     * @return ReconInfo
     */
    List<ReconInfo> reconAccessToStandard(ReconParam reconParam);

    /**
     * 按天接入统计数据账单
     * @param dateTime
     * @return
     */
    List<BillLinkStatistics> statisticsAccessDataBill(@Param("dateTime") int dateTime);

    /**
     * 按天标准化统计数据账单
     * @param dateTime
     * @return
     */
    List<BillLinkStatistics> statisticsStandardDataBill(@Param("dateTime") int dateTime);

    /**
     * 获取链路统计
     * @param dateTime
     * @return
     */
    List<BillLinkStatistics> getLinkStatistics(@Param("dateTime") String dateTime);

    /**
     * 对账分析
     * @param linkType
     * @param startDate
     * @param endDate
     * @return
     */
    List<ReconciliationAnalysis> reconAnalysis(@Param("linkType")int linkType, @Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 根据环节类型和时间获取汇总账单  从盘点账单表里获取
     * @param request
     * @return
     */
    List<SummarizeBillResponse> getSummarizeBill(SummarizeBillRequest request);

    /**
     * 首页展示异常数据链路
     * @param dataTime
     * @return
     */
    int getAbnormalReconciliationList(@Param("dataTime") String dataTime);

    /**
     * 根据对账环节获取数据对账详单页面有效日期
     * @param DZZDHJ
     * @return
     */
    List<String> getDataTimeList(@Param("DZZDHJ") int DZZDHJ);
}
