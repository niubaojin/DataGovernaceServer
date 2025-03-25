package com.synway.reconciliation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.common.exception.CommonErrorCode;
import com.synway.common.bean.ServerResponse;
import com.synway.reconciliation.interceptor.IgnoreSecurity;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.service.ReconciliationUIService;
import com.synway.reconciliation.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.*;


/**
 * 数据对账查询controller
 * @author ym
 */
@RestController
@RequestMapping("/reconciliation")
public class ReconciliationUIController {

    private static Logger logger = LoggerFactory.getLogger(ReconciliationUIController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ReconciliationUIService service;


    /**
     * 数据接入链路状况列表
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getAccessList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getAccessList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            Map<String,Object> result = service.getAccessList(getListRequest);
            return new ServerResponse(1, result);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据入库链路状况列表
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerRespongetAccessDirectionse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getStorageList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getStorageList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            Map<String,Object> result = service.getStorageList(getListRequest);
            return new ServerResponse(1, result);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据下发链路状况列表
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerRespongetAccessDirectionse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getIssuedList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getIssuedList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            Map<String,Object> result = service.getIssuedList(getListRequest);
            return new ServerResponse(1, result);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据分发链路状况列表
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerRespongetAccessDirectionse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getDistributionList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getDistributionList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            Map<String,Object> result = service.getDistributionList(getListRequest);
            return new ServerResponse(1, result);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 检验起始时间、结束时间参数是否为空
     * @param getListRequest 查询条件
     * @return boolean
     */
    private boolean checkParam(GetListRequest getListRequest){
        return StringUtils.isEmpty(getListRequest.getEndTime()) || StringUtils.isEmpty(getListRequest.getStartTime());
    }

    /**
     * 分页数据封装map
     * @param pageInfo 分页数据
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> putInfoToMap(PageInfo pageInfo){
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    /**
     * 数据标准化链路状况列表
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getStandardList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getStandardList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            Map<String,Object> result = service.getStandardList(getListRequest);
            return new ServerResponse(1, result);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据链路左侧组织树
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<com.synway.reconciliation.pojo.TreeNode>>
     */
    @RequestMapping(value = "/getTableOrganizationTree")
    @ResponseBody
    public ServerResponse<List<TreeNode>> getTableOrganizationTree(@RequestBody GetListRequest req){
        try{
            List<TreeNode> dataList = service.getOrganizationTree(req);
            return new ServerResponse(1, dataList);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }


    @RequestMapping(value = "/getTableOrganizationTreeForBill")
    @ResponseBody
    public ServerResponse<List<TreeNode>> getTableOrganizationTreeForBill(@RequestBody ReconInfoRequest request){
        try{
            List<TreeNode> treeNodes = service.getOrganizationTreeForBill(request);
            return ServerResponse.asSucessResponse(treeNodes);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 左侧组织树刷新
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<com.synway.reconciliation.pojo.TreeNode>>
     */
    @RequestMapping(value = "/refreshTableOrganizationTree")
    @ResponseBody
    public ServerResponse<List<TreeNode>> refreshTableOrganizationTree(@RequestBody GetTreeReq req){
        try{
            JSONObject obj = restTemplate.postForObject("http://datastandardmanager/dataStandardManager/getTableOrganizationTreeForOrgPage", req, JSONObject.class);
            int status = obj.getInteger("status");
            String msg = obj.getString("message");
            JSONArray dataArray = obj.getJSONArray("data");
            List<TreeNode> dataList = JSONObject.parseArray(dataArray.toJSONString(), TreeNode.class);
            if (1 == status) {
                return ServerResponse.asSucessResponse (dataList);
            } else {
                return ServerResponse.asErrorResponse(msg);
            }
        }catch (Exception e){
            logger.error("刷新表组织失败：", e);
            return ServerResponse.asErrorResponse("刷新表组织失败：" + e.getMessage());
        }
    }
    /**
     * 左侧组织树-数据组织资产
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<com.synway.reconciliation.pojo.TreeNode>>
     */
    @RequestMapping(value = "/getTableOrganizationTreeForOrgPage")
    @ResponseBody
    public ServerResponse<List<TreeNode>> getTableOrganizationTreeForOrgPage(@RequestBody GetTreeReq req){
        try{
            List<TreeNode> treeNodes = service.getOrganizationTreeForOrgPage(req);
            return new ServerResponse(1,treeNodes);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据接入刷新
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<com.synway.reconciliation.pojo.AccessListResponse>
     */
    @RequestMapping(value = "/updateAccess")
    @ResponseBody
    public ServerResponse<AccessListResponse> updateAccess(@RequestBody GetUpdateRequest req){

        try{
            AccessListResponse accessListResponse = service.updateAccess(req.getStartTime(),req.getResourceId(),req.getDirection(),req.getTache(),req.getIsIssued(),req.getUserId());
            return new ServerResponse(1,accessListResponse);
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }

    }

    /**
     * 标准化刷新
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<com.synway.reconciliation.pojo.StandardListResponse>
     */
    @RequestMapping(value = "/updateStandard")
    @ResponseBody
    public ServerResponse<StandardListResponse> updateStandard(@RequestBody GetUpdateRequest req){
        try{
            StandardListResponse standardListResponse = service.updateStandard(req.getStartTime(),req.getResourceId(),req.getDirection(),req.getTache(),req.getUserId());
            return new ServerResponse(1,"");
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 接入系统状况列表
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getAccessAccessStateList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getAccessAccessStateList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            PageInfo pageInfo = service.getAccessAccessStateList(getListRequest);
            return new ServerResponse(1, putInfoToMap(pageInfo));
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 标准化接入系统状况
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getStandardAccessStateList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getStandardAccessStateList(@RequestBody GetListRequest getListRequest){
        try{
            if(checkParam(getListRequest)){
                return new ServerResponse(0,"起始时间结束时间未填写");
            }
            PageInfo pageInfo = service.getStandardStorageStateList(getListRequest);
            return new ServerResponse(1, putInfoToMap(pageInfo));
        }catch (Exception e){
            logger.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 接入接入状况
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getAccessSystemList")
    @ResponseBody
    public ServerResponse<String> getAccessSystemList( @RequestBody GetAccessSystemReq req){
        try{
            List<String> list = service.getAccessSystemList(req.getTache());
            return new ServerResponse(1,list);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 接入去向过滤项获取
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getAccessDirection")
    @ResponseBody
    public ServerResponse<List<String>> getAccessDirection(){
        try{
            List<String> list = service.getAccessDirection();
            return new ServerResponse(1,list);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 入库去向过滤项获取
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getStorageDirection")
    @ResponseBody
    public ServerResponse<List<String>> getStorageDirection(){
        try{
            List<String> list = service.getStorageDirection();
            return new ServerResponse(1,list);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 标准化去向过滤项获取
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getStandardDirection")
    @ResponseBody
    public ServerResponse<List<String>> getStandardDirection(){
        try{
            List<String> list= service.getStandardDirection();
            return new ServerResponse(1,list);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据分发去向过滤项获取
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getDistributionDirection")
    @ResponseBody
    public ServerResponse<List<String>> getDistributionDirection(){
        try{
            List<String> list= service.getDistributionDirection();
            return new ServerResponse(1,list);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 统计链路数据量
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<com.synway.reconciliation.pojo.DataCount>
     */
    @RequestMapping(value = "/getDataCount")
    @ResponseBody
    public ServerResponse<DataCount> getDataCount(@RequestBody GetDataCountReq req){
        try{
            DataCount dataCount = service.getDataCount(req);
            return new ServerResponse(1,dataCount);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 数据分发对账单查询
     * @param req 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @IgnoreSecurity
    @RequestMapping(value = "/queryDataDistributionBillInfo")
    @ResponseBody
    public ServerResponse<Map<String,Object>> queryDataDistributionBillInfo(@RequestBody GetReconciliationListRequest req){
        try{
            Map<String,Object> map = new HashMap<>();
            PageInfo pageInfo = service.queryDataDistributionBillInfo(req);
            map.put("total",pageInfo.getTotal());
            map.put("rows",pageInfo.getList());
            return new ServerResponse(1,map);
        }catch (Exception e){
            logger.info("数据分发对账单查询失败" + ExceptionUtil.getExceptionTrace(e));
            return new ServerResponse(0, e.getMessage());
        }
    }

    /**
     * 历史比对
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<com.synway.reconciliation.pojo.AccessListResponse>>
     */
    @RequestMapping(value = "/getHistoryCompareStatistics")
    @ResponseBody
    public ServerResponse<List<AccessListResponse>> getHistoryCompareStatistics(@RequestBody GetListRequest getListRequest){
        try{
            List<AccessListResponse> resultList =  service.getHistoryCompareStatistics(getListRequest);
            return ServerResponse.asSucessResponse(resultList);
        }catch (Exception e){
            logger.info("历史比对数据获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

}
