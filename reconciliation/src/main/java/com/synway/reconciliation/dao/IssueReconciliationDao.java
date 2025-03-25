package com.synway.reconciliation.dao;

import com.synway.reconciliation.pojo.IssueAccessCount;
import com.synway.reconciliation.pojo.RestReconInfo;
import com.synway.reconciliation.pojo.issue.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 下发对账
 * @author DZH
 */
@Mapper
@Repository
public interface IssueReconciliationDao {

    /**
     * 分页获取下发对账统计
     * @param request
     * @return
     */
    List<IssueDayStatistics> getPageIssueBill(IssueReconciliationRequestExtend request);

    /**
     * 获取所有下发对账统计
     * @return
     */
    List<IssueDayStatistics> getAllIssueBill();

    /**
     * 获取下发对账统计总数
     * @param request
     * @return
     */
    long countIssueBill(IssueReconciliationRequestExtend request);

    /**
     * 获取下发对账历史账单
     * @param request
     * @return
     */
    List<IssueHistoryResponse> getIssueHistoryBill(IssueHistoryRequest request);

    /**
     * 获取所有基线时间
     * @return
     */
    List<IssueBaseTime> getAllIssueBaseTime();

    /**
     * 分页获取下发对账基线时间
     * @param request
     * @return
     */
    List<IssueBaseTimeResponse> getPageIssueBaseTime(IssueBaseTimeRequest request);

    /**
     * 获取下发对账基线时间总数
     * @param request
     * @return
     */
    long countIssueBaseTime(IssueBaseTimeRequest request);

    /**
     * 插入基线时间
     * @param issueBaseTime
     * @return
     */
    int insertIssueBaseTime(IssueBaseTime issueBaseTime);

    /**
     * 更新基线时间
     * @param issueBaseTime
     * @return
     */
    int updateIssueBaseTime(IssueBaseTime issueBaseTime);


    /**
     * 删除一条基线时间
     * @param issueBaseTime
     * @return
     */
    int deleteIssueBaseTime(IssueBaseTime issueBaseTime);

    /**
     * 获取数据接收方过滤项
     * @return
     */
    List<String> getAcceptCity();

    /**
     * 获取下发总队提供方账单
     * @param startDate
     * @param endDate
     * @return
     */
    List<AccountBill> getAllIssueProviderBill(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 获取下发总队接收方账单
     * @param startDate
     * @param endDate
     * @return
     */
    List<AccountBill> getAllIssueAcceptorBill(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 获取下发地市提供方账单
     * @param startDate
     * @param endDate
     * @return
     */
    List<AccountBill> getAllAccessProviderBill(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 获取下发地市接收方账单
     * @param startDate
     * @param endDate
     * @return
     */
    List<AccountBill> getAllAccessAcceptorBill(@Param("startDate") int startDate, @Param("endDate") int endDate);

    /**
     * 获取所有任务相关信息
     * @param tag
     * @return
     */
    List<RelateJob> getAllRelateJob(@Param("tag") String tag);

    /**
     * 获取已入库任务相关信息
     * @return
     */
    List<RelateJob> getRelateJob();

    /**
     * 获取TaskInfoTag
     * @return
     */
    List<String> getTaskInfoTag();

    /**
     * 删除任务信息tag
     * @return
     */
    int deleteTaskInfoTag();

    /**
     * 插入任务信息tag
     * @param tag
     * @return
     */
    int insertTaskInfoTag(@RequestParam("tag") String tag);

    /**
     * 获取未对账
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getUnReconCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取数据种类
     * @return
     */
    Integer getDataTypeCount();

    /**
     * 获取对账信息
     * @param startDate
     * @param endDate
     * @return
     */
    RestReconInfo getReconInfo(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取下发接入情况
     * @param startDate
     * @param endDate
     * @return
     */
    List<IssueAccessCount> getIssueAccessCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
