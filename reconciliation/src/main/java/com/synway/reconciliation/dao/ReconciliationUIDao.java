package com.synway.reconciliation.dao;

import com.synway.reconciliation.interceptor.AuthorControl;
import com.synway.reconciliation.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数据对账查询dao
 * @author ym
 */
@Mapper
@Repository
public interface ReconciliationUIDao {

    /**
     * 数据组织获取
     * @param nodeName 节点名称
     * @return java.util.List<com.synway.reconciliation.pojo.Relation>
     */
    List<Relation> getOrganizationList(@Param("nodeName")String nodeName);

    /**
     * 数据来源获取
     * @param nodeName 节点名称
     * @return java.util.List<com.synway.reconciliation.pojo.Relation>
     */
    List<Relation> getDataSourceList(@Param("nodeName")String nodeName);

    /**
     * 数据资源获取
     * @param nodeName 节点名称
     * @return java.util.List<com.synway.reconciliation.pojo.Relation>
     */
    List<Relation> getFactorList(@Param("nodeName")String nodeName);

    /**
     * 接入列表
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.AccessListResponse>
     */
    List<AccessListResponse> getAccessList(GetListRequest getListRequest);

    /**
     * 入库列表
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.AccessListResponse>
     */
    List<AccessListResponse> getStorageList(GetListRequest getListRequest);

    /**
     * 接入更新
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param resourceId 源协议ID
     * @return com.synway.reconciliation.pojo.AccessListResponse
     */
    AccessListResponse getAccessUpdate(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("resourceId") String resourceId);

    /**
     * 入库更新
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param resourceId 源协议ID
     * @return com.synway.reconciliation.pojo.AccessListResponse
     */
    AccessListResponse getStorageUpdate(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("resourceId") String resourceId);

    /**
     * 标准化列表
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.StandardListResponse>
     */
    List<StandardListResponse> getStandardList(GetListRequest getListRequest);

    /**
     * 标准化入库更新
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param resourceId 源协议ID
     * @param direction 去向
     * @return com.synway.reconciliation.pojo.StandardListResponse
     */
    StandardListResponse getStandardStorageUpdate(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("resourceId") String resourceId, @Param("direction")String direction, @Param("userId")String userId);

    /**
     * 标准化接入更新
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param resourceId 源协议ID
     * @param direction 去向
     * @return com.synway.reconciliation.pojo.StandardListResponse
     */
    StandardListResponse getStandardAccessUpdate(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("resourceId") String resourceId, @Param("direction")String direction, @Param("userId")String userId);

    /**
     * 接入提供方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getAccessProviderList(GetReconciliationListRequest req);

    /**
     * 计算接入提供方账单总数
     * @param req
     * @return
     */
    Long countAccessProviderList(GetReconciliationListRequest req);

    /**
     * 接入接收方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getAccessAcceptorList(GetReconciliationListRequest req);

    /**
     * 入库提供方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getStorageProviderList(GetReconciliationListRequest req);

    /**
     * 计算入库提供方账单总数
     * @param req
     * @return
     */
    Long countStorageProviderList(GetReconciliationListRequest req);

    /**
     * 入库接收方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getStorageAcceptorList(GetReconciliationListRequest req);

    /**
     * 计算入库接收方账单总数
     * @param req
     * @return
     */
    Long countStorageAcceptorList(GetReconciliationListRequest req);

    /**
     * 标准化接入接收方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getStandardAcceptorList(GetReconciliationListRequest req);

    /**
     * 标准化接入提供方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getStandardProviderList(GetReconciliationListRequest req);

    /**
     * 标准化入库提供方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getStandardStorageProviderList(GetReconciliationListRequest req);

    /**
     * 计算标准化入库提供方账单总数
     * @param req
     * @return
     */
    Long countStandardStorageProviderList(GetReconciliationListRequest req);

    /**
     * 标准化入库接收方账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> getStandardStorageAcceptorList(GetReconciliationListRequest req);

    /**
     * 计算标准化入库接收方账单总数
     * @param req
     * @return
     */
    Long countStandardStorageAcceptorList(GetReconciliationListRequest req);

    /**
     * 接入提供方账单详情
     * @param req 对账单编号、账单时间
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getAccessProviderBill(GetBillDetailReq req);

    /**
     * 接入接收方账单详情
     * @param billNo 对账单编号
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getAccessAcceptorBill(@Param("billNo") String billNo);

    /**
     * 接入入库提供方账单详情
     * @param req 对账单编号、账单时间
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getStorageProviderBill(GetBillDetailReq req);

    /**
     * 接入入库接收方账单详情
     * @param req 对账单编号、账单时间
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getStorageAcceptorBill(GetBillDetailReq req);

    /**
     * 标准化接入接收方账单详情
     * @param billNo 对账单编号
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getStandardAcceptorBill(@Param("billNo") String billNo);

    /**
     * 标准化接入提供方账单详情
     * @param billNo 对账单编号
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getStandardProviderBill(@Param("billNo") String billNo);

    /**
     * 标准化入库接收方账单详情
     * @param req 对账单编号、账单时间
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getStandardStorageAcceptorBill(GetBillDetailReq req);

    /**
     * 标准化入库提供方账单详情
     * @param req 对账单编号、账单时间
     * @return java.util.List<com.synway.reconciliation.pojo.Detail>
     */
    List<Detail> getStandardStorageProviderBill(GetBillDetailReq req);

    /**
     * 数据接入接入状态
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.AccessAccessStateListResponse>
     */
    List<AccessAccessStateListResponse> getAccessAccessStateList(GetListRequest getListRequest);

    /**
     * 标准化接入接入状态
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.StandardAccessStateListResponse>
     */
    List<StandardAccessStateListResponse> getStandardAccessStateList(GetListRequest getListRequest);

    /**
     * 标准化入库接入状态
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.StandardAccessStateListResponse>
     */
    List<StandardAccessStateListResponse> getStandardStorageStateList(GetListRequest getListRequest);

    /**
     * 接入系统获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_ACCEPTOR_BILL","DAC_ACCESS_STORAGE_INSIDE_BILL"}, columnNames={"USER_ID","USER_ID"})
    List<String> getAccessSystemList1();

    /**
     * 接入系统获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_PROVIDER_BILL"}, columnNames={"USER_ID"})
    List<String> getAccessSystemList2();

    /**
     * 接入系统获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_PROVIDER_BILL","DAC_STORAGE_ACCEPTOR_BILL","DAC_ACCESS_ACCEPTOR_BILL","DAC_ACCESS_STORAGE_INSIDE_BILL"}, columnNames={"USER_ID","USER_ID","USER_ID","USER_ID"})
    List<String> getAccessSystemList3();

    /**
     * 接入系统获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_PROVIDER_BILL","DAC_STORAGE_ACCEPTOR_BILL"}, columnNames={"USER_ID","USER_ID"})
    List<String> getAccessSystemList4();

    /**
     * 接入环节数据量
     * @param tache 环节
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dataName 数据名称
     * @return int
     */
    int getAccessDataCount(@Param("tache")int tache,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("dataName") String dataName);

    /**
     * 标准化环节数据量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dataName 数据名称
     * @return int
     */
    int getStandardDataCount(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("dataName") String dataName);

    /**
     * 接入统计resourceId获取
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return java.util.List<java.lang.String>
     */
    List<String> getAccessStatisticResourceIds(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 标准化统计resourceId获取
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return java.util.List<java.lang.String>
     */
    List<String> getStandardStatisticResourceIds(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 统计接入入库
     * @param date1
     * @param date2
     * @param date3
     * @param resourceId 源协议ID
     * @param dataDirection 去向
     * @return java.util.List<com.synway.reconciliation.pojo.AccessBillStatistics>
     */
    List<AccessBillStatistics> statisticsAcccessStorage(@Param("date1") String date1,@Param("date2") String date2,@Param("date3") String date3,@Param("resourceId") String resourceId,@Param("dataDirection")String dataDirection,@Param("userId")String userId);

    /**
     * 统计数据接入
     * @param date1
     * @param date2
     * @param resourceId 源协议ID
     * @param dataDirection 去向
     * @return java.util.List<com.synway.reconciliation.pojo.AccessBillStatistics>
     */
    List<AccessBillStatistics> statisticsAcccess(@Param("date1") String date1,@Param("date2") String date2,@Param("resourceId") String resourceId,@Param("dataDirection")String dataDirection);

    /**
     * 删除数据接入统计信息
     * @param resourceId 源协议ID
     * @param direction 去向
     * @param tache 环节
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return void
     */
    void deleteFromAccessBillStatics(@Param("resourceId")String resourceId,@Param("direction")String direction,@Param("tache")int tache,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("userId")String userId);

    /**
     * 获取接入更新数据
     * @param resourceId 源协议ID
     * @param direction 去向
     * @param tache 环节
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return com.synway.reconciliation.pojo.AccessListResponse
     */
    AccessListResponse getAccessUpdateData(@Param("resourceId")String resourceId,@Param("direction")String direction,@Param("tache")int tache,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("userId")String userId);

    /**
     * 新增统计更新记录
     * @param bsh 统计历史记录
     * @return void
     */
    void insertStatisticsUpdateHistory(BillStatisticsHistory bsh);

    /**
     * 更新统计更新记录
     * @param id
     * @param status
     * @return void
     */
    void updateStatisticsUpdateHistory(@Param("id")String id,@Param("status")int status);

    /**
     * 获取统计历史记录
     * @param system 来源系统
     * @param dataTime 数据时间
     * @return java.util.List<com.synway.reconciliation.pojo.BillStatisticsHistory>
     */
    List<BillStatisticsHistory> getStatisticsHistory(@Param("system") int system,@Param("dataTime")String dataTime);

    /**
     * 数据接入去向
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_BILL_STATISTICS"}, columnNames={"USER_ID"})
    List<String> getAccessDirection();

    /**
     * 数据入库去向
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_BILL_STATISTICS","DAC_STANDARD_BILL_STATISTICS a"}, columnNames={"USER_ID","a.USER_ID"})
    List<String> getStorageDirection();

    /**
     * 标准化去向
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_BILL_STATISTICS"}, columnNames={"USER_ID"})
    List<String> getStandardDirection();

    /**
     * 统计标准化接入
     * @param date1
     * @param date2
     * @param date3
     * @param direction 去向
     * @param resourceId 源协议ID
     * @return java.util.List<com.synway.reconciliation.pojo.AccessBillStatistics>
     */
    List<AccessBillStatistics> statisticsStandardAccess(@Param("date1")String date1, @Param("date2")String date2, @Param("date3")String date3, @Param("direction")String direction, @Param("resourceId")String resourceId, @Param("userId")String userId);

    /**
     * 统计标准化入库
     * @param date1
     * @param date2
     * @param direction 去向
     * @param resourceId 源协议ID
     * @return java.util.List<com.synway.reconciliation.pojo.AccessBillStatistics>
     */
    List<AccessBillStatistics> statisticsStandardStorage(@Param("date1")String date1, @Param("date2")String date2, @Param("direction")String direction, @Param("resourceId")String resourceId, @Param("userId")String userId);

    /**
     * 删除标准化账单统计
     * @param direction 去向
     * @param resourceId 源协议ID
     * @param tache 环节
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return void
     */
    void deleteFromStandardBillStatics(@Param("resourceId")String resourceId, @Param("direction")String direction, @Param("tache")int tache, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("userId")String userId);

    /**
     * 核账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> queryCheckBillList(GetReconciliationListRequest req);

    /**
     * 销账单查询
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.ReconciliationListResponse>
     */
    List<ReconciliationListResponse> queryCancelBillList(GetReconciliationListRequest req);

    /**
     * 数据时间集合获取
     * @return
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_BILL_STATISTICS"}, columnNames={"USER_ID"})
    List<String> getAccessDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_BILL_STATISTICS"}, columnNames={"USER_ID"})
    List<String> getStandardDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_BILL_STATISTICS","DAC_STANDARD_BILL_STATISTICS a"}, columnNames={"USER_ID","a.USER_ID"})
    List<String> getStorageDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_ACCEPTOR_BILL"}, columnNames={"USER_ID"})
    List<String> getAccessOutsideDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_STORAGE_INSIDE_BILL"}, columnNames={"USER_ID"})
    List<String> getAccessInsideDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_PROVIDER_BILL"}, columnNames={"USER_ID"})
    List<String> getStandardOutsideDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_PROVIDER_BILL","DAC_ACCESS_ACCEPTOR_BILL"}, columnNames={"USER_ID","USER_ID"})
    List<String> getStorageOutsideDataTimeList();

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STORAGE_ACCEPTOR_BILL","DAC_ACCESS_STORAGE_INSIDE_BILL"}, columnNames={"USER_ID","USER_ID"})
    List<String> getStorageInsideDataTimeList();

    /**
     * 接入历史比对统计
     * @return com.synway.reconciliation.pojo.AccessListResponse
     */
    @AuthorControl(tableNames ={"DAC_ACCESS_BILL_STATISTICS"}, columnNames={"USER_ID"})
    List<AccessListResponse> getAccessHistoryCompareStatistics(GetListRequest getListRequest);

    /**
     * 数据处理历史比对统计
     * @return com.synway.reconciliation.pojo.AccessListResponse
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_BILL_STATISTICS"}, columnNames={"USER_ID"})
    List<AccessListResponse> getStandardHistoryCompareStatistics(GetListRequest getListRequest);

    /**
     * 数据下发列表
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.StandardListResponse>
     */
    List<AccessListResponse> getIssuedList(GetListRequest getListRequest);

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    List<String> getIssuedDataTimeList();

    /**
     * 数据分发列表
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.StandardListResponse>
     */
    List<StandardListResponse> getDistributionList(GetListRequest getListRequest);

    /**
     * 数据时间集合获取
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_BILL_STATISTICS a"}, columnNames={"a.USER_ID"})
    List<String> getDistributionDataTimeList();

    /**
     * 数据分发去向
     * @return java.util.List<java.lang.String>
     */
    @AuthorControl(tableNames ={"DAC_STANDARD_BILL_STATISTICS a"}, columnNames={"a.USER_ID"})
    List<String> getDistributionDirection();

}
