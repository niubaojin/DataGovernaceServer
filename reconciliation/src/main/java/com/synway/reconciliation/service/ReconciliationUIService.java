package com.synway.reconciliation.service;

import com.github.pagehelper.PageInfo;
import com.synway.reconciliation.pojo.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据对账查询service
 * @author ym
 */
public interface ReconciliationUIService {
    /**
     * 数据组织树获取
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.TreeNode>
     */
    List<TreeNode> getOrganizationTree(GetListRequest req);

    /**
     * 接入列表
     * @param getListRequest 查询条件
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> getAccessList(GetListRequest getListRequest);

    /**
     * 标准化列表
     * @param getListRequest 查询条件
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> getStandardList(GetListRequest getListRequest);


    /**
     * 接入系统状况
     * @param getListRequest 查询条件
     * @return com.github.pagehelper.PageInfo<com.synway.reconciliation.pojo.AccessAccessStateListResponse>
     */
    PageInfo<AccessAccessStateListResponse> getAccessAccessStateList(GetListRequest getListRequest);

    /**
     * 标准化接入系统状况
     * @param req 查询条件
     * @return com.github.pagehelper.PageInfo<com.synway.reconciliation.pojo.StandardAccessStateListResponse>
     */
    PageInfo<StandardAccessStateListResponse> getStandardStorageStateList(GetListRequest req);

    /**
     * 更新接入列表
     * @param startTime 开始时间
     * @param resourceId 源协议ID
     * @param direction 去向
     * @param tache 环节
     * @param isIssued 是否下发数据
     * @param userId 用户ID
     * @return com.synway.reconciliation.pojo.AccessListResponse
     */
    AccessListResponse updateAccess(Date startTime,String resourceId,String direction, int tache, String isIssued, String userId);

    /**
     * 更新标准化列表
     * @param startTime 开始时间
     * @param resourceId 源协议ID
     * @param direction 去向
     * @param tache 环节
     * @param userId 用户ID
     * @return com.synway.reconciliation.pojo.StandardListResponse
     */
    StandardListResponse updateStandard(Date startTime,String resourceId,String direction, int tache, String userId);

    /**
     * 接入系统获取
     * @param tache 环节
     * @return java.util.List<java.lang.String>
     */
    List<String> getAccessSystemList(int tache);

    /**
     * 数据量获取
     * @param req 查询条件
     * @return com.synway.reconciliation.pojo.DataCount
     */
    DataCount getDataCount(GetDataCountReq req);

    /**
     * 接入去向
     * @return java.util.List<java.lang.String>
     */
    List<String> getAccessDirection();

    /**
     * 入库去向
     * @return java.util.List<java.lang.String>
     */
    List<String> getStorageDirection();

    /**
     * 标准化去向
     * @return java.util.List<java.lang.String>
     */
    List<String> getStandardDirection();

    /**
     * 组织树获取
     * @param req 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.TreeNode>
     */
    List<TreeNode> getOrganizationTreeForOrgPage(GetTreeReq req);


    /**
     * 数据对账详情组织树
     * @param request
     * @return
     * @throws ParseException
     */
    List<TreeNode> getOrganizationTreeForBill(ReconInfoRequest request) throws ParseException;

    /**
     * 下发账单信息获取
     * @param req 查询条件
     * @return com.github.pagehelper.PageInfo
     */
    PageInfo queryDataDistributionBillInfo(GetReconciliationListRequest req);

    /**
     * 入库列表
     * @param getListRequest 查询条件
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> getStorageList(GetListRequest getListRequest);

    /**
     * 历史比对数据获取
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.AccessListResponse>
     */
    List<AccessListResponse> getHistoryCompareStatistics(GetListRequest getListRequest);

    /**
     * 数据下发列表
     * @param getListRequest 查询条件
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> getIssuedList(GetListRequest getListRequest);

    /**
     * 数据分发列表
     * @param getListRequest 查询条件
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> getDistributionList(GetListRequest getListRequest);

    /**
     * 数据分发去向
     * @return java.util.List<java.lang.String>
     */
    List<String> getDistributionDirection();


}
