package com.synway.property.service;


import com.alibaba.fastjson.JSONObject;
import com.synway.property.pojo.*;
import com.synway.property.pojo.approvalinfo.DataApproval;
import com.synway.property.pojo.formorganizationindex.ReceiveTag;
import com.synway.property.pojo.homepage.DataBaseState;
import com.synway.property.pojo.datastoragemonitor.*;
import com.synway.property.pojo.tablemanage.AdsOdpsTableInfo;
import com.synway.common.bean.ServerResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


/**
 * @author 数据接入
 */
public interface DataStorageMonitorIndexService {
    ServerResponse<List<DataBaseState>> getDataBaseStatus(String platFormType);

    ServerResponse<DataQualitySummary> getDataQualitySummary();

    //将需要添加的实时告警表名写入到数据库中
    ServerResponse<String> insertOracleAddTable(List<NeedAddRealTimeTable> allNeedAddRealTimeTableList, String type);

    //查询出需要展示的实时表监控
    ServerResponse<List<RealTimeTableFullMessage>> getAllRealTimeTableFullMessage();

    //根据选择的数据资源目录的三大分类之一获取对应的一级分类信息
    ServerResponse<List<PageSelectOneValue>> getPrimaryClassifyData(String mainClassify);

    //根据选择的数据资源目录的三大分类之一和 一级分类信息 来获取二级分类信息
    ServerResponse<List<PageSelectOneValue>> getSecondaryClassifyData(String mainClassify, String primaryClassifyCode, String isThreeLevel);

    //    List<ClassifyInfoTree> getClassifyListTree(String mainClassify);

    /**
     * 使用resultful接口来调用 tablemanage中的方法
     * 根据项目名（或者数据库名）和表名，模糊匹配查询出相关表信息列表
     *
     * @param stage     平台
     * @param preName   项目名或者是数据库名
     * @param tableName 表名
     * @return 已经分页的数据
     */
    List<AdsOdpsTableInfo> getAdsOdpsTableInfoByRestFul(String stage, String preName, String tableName) throws Exception;

    ServerResponse<List<String>> getProjectNameByRestFul(String stage, String monitorType);

    /**
     * 据分级分类的一级和二级的相关信息查询表组织资产的汇总信息
     * 后端不分页，使用前端分页
     *
     * @param mainClassify        主分类名称
     * @param primaryClassifyCh   一级分类名称  为空表示查询所有
     * @param secondaryClassifyCh 二级分类名称 为空表示查询所有
     * @return
     */
    ServerResponse<List<SummaryTableByClassify>> getSummaryTableByClassify(String mainClassify, String primaryClassifyCh, String secondaryClassifyCh, String threeValue);

    /**
     * 根据 三大类别名称来获取对应一级分类的总记录数和存储大小
     *
     * @param cateGoryName dataOrganizationClassify / dataSourceClassify / dataResourceClassify
     * @return
     */
    ServerResponse<NumStorageByPrimaryClassify> getRecordsStorageByPrimaryClassify(String cateGoryName);

    ServerResponse<String> delRealTableMonitor(List<NeedAddRealTimeTable> delDataList);

    /**
     * 获取的tableid去资源服务平台注册
     *
     * @param tableId
     * @param tableList
     * @param status
     * @return
     */
    ServerResponse updateRegisterState(String[] tableId, JSONObject[] tableList, String status,String userId);

    ServerResponse<List<String>> refreshSourceData(String[] tableIdList);

    // 导出资产管理列表
    void exportOrganizationList(HttpServletResponse response, String type, String incrementDays, String mainClassify, String primaryClassifyCh, String secondaryClassifyCh, String threeValue) throws Exception;

    ServerResponse<DetailedTableResultMap> getDataContent(RequestParameter requestParameter);

    ServerResponse<String> getDetectId(DetailedTableByClassify detailedTableByClassify);

    ServerResponse<List<String>> updateObjectState(List<String> tableIdList, String state);

    void exportDataContent(List<ReceiveTag> classifyTagsList, String input, List<ReceiveTag> registerTagsList,
                           List<ReceiveTag> storageTagsList, List<ReceiveTag> usingTagsList, String type,
                           List<String> termSetting, List<String> lastModifiedTimeList, Long startRecordNum, Long endRecordNum, Long startStorageSize, Long endStorageSize,DataResourceTable quetyTable, HttpServletResponse response
//            , List<ReceiveTag> labelTagsList
    );

    ServerResponse getProjectNameByType(String stroageLocation);

    ServerResponse getSourceStateNum();

    ServerResponse getTableType();

    ServerResponse registerApproval(String toJsonString, String[] tableIdList, JSONObject[] tableList, String userName, String userId, String organId) throws Exception;

    ServerResponse getStorageNum();

    ServerResponse getLabelNum();

//    ServerResponse getLabelsByType(String label);

    ServerResponse updateApprovalStatus(List<DataApproval> dataApprovals);

    ServerResponse<List<NeedAddRealTimeTable>> getNeedAddRealTimeTable(String monitorType,
                                                                       String dataSourceId,
                                                                       String tableName,
                                                                       String dataCenterId,
                                                                       String dataBaseType);

    List<TreeNode> externalgetTableOrganizationTree(
            GetTreeReq req, Boolean isQueryTable, Boolean showLabel, Boolean showAll);

    ServerResponse updateLifeCycleApproval(RequestParameter requestParam) throws Exception;

    ServerResponse updateLifeCycleStatus(RequestParameter requestParam);

    void updateTableOrganizationShowField(String tableOrganizationShowFields);

    List<String> getTableOrganizationShowField();

}
