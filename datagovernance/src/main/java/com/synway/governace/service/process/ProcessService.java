package com.synway.governace.service.process;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.process.*;

import java.util.List;
import java.util.Map;

/**
 * @author ywj
 */
public interface ProcessService {

    /**
     * 新增/编辑申请信息
     * @param info
     * @return ProcessServerResponse<String>
     */
    ServerResponse<String> saveOrUpdateApprovalInfo(ApprovalInfo info);

    /**
     * 获取申请信息详情
     * @param id
     * @return ApprovalInfo
     */
    ApprovalInfo getApprovalInfoDetail(String id);

    /**
     * 流程启动
     * @param param
     * @param serverUrl
     * @return JSONObject
     */
    JSONObject startWorkFlowForRoll(WorkFlowParams param, String serverUrl);

    /**
     * 流程启动并发送下一步
     * @param param
     * @param serverUrl
     * @return JSONObject
     */
    JSONObject startAndSendNextStep(WorkFlowParams param, String serverUrl);

    /**
     * 流程终止
     * @param jsonStr
     * @return ProcessServerResponse<String>
     */
    ServerResponse<String> endProcess(String jsonStr);

    /**
     * 标准管理页面获取
     * @param info
     * @param serverUrl
     * @param standardUrl
     * @return JSONObject
     */
    JSONObject getStandardPageByApprovalInfo(ApprovalInfo info, String serverUrl, String standardUrl);

    /**
     * 各模块审批信息查询
     * @param info
     * @return List<ApprovalInfo>
     */
    List<ApprovalInfo> queryApprovalInfoForModule(ApprovalInfo info);

    /**
     * 审批事务操作类型获取
     * @return List<String>
     */
    List<String> getOperationTypeData();

    /**
     * 在办事务列表获取
     * @param queryString
     * @param url
     * @return Map<String, Object>
     */
    Map<String, Object> getDoingAffairList(String queryString, String url);

    /**
     * 待办事务列表获取
     * @param queryString
     * @param url
     * @return Map<String, Object>
     */
    Map<String, Object> getToDoAffairList(String queryString, String url);

    /**
     * 已办事务列表获取
     * @param queryString
     * @param url
     * @return Map<String, Object>
     */
    Map<String, Object> getDoneAffairList(String queryString, String url);

    /**
     * 召回事务
     * @param params
     * @param url
     * @return JSONObject
     */
    JSONObject callbackAffair(String params, String url);

    /**
     * 终止事务
     * @param params
     * @param url
     * @return JSONObject
     */
    JSONObject endAffair(String params, String url);
}
