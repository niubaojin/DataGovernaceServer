package com.synway.reconciliation.controller;

import com.alibaba.fastjson.JSON;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.CommonErrorCode;
import com.synway.reconciliation.conditional.NonIssueCondition;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.service.InventoryReconService;
import com.synway.reconciliation.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 盘点对账接口
 * @author Administrator
 */
@Slf4j
@Controller
@RequestMapping("/reconciliation")
@Conditional(NonIssueCondition.class)
public class InventoryReconController {

    @Autowired
    private InventoryReconService inventoryReconService;

    /**
     * 获取数据链路对账数据列表  对应数据链路对账页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReconciliationStatisticsList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getReconciliationStatisticsList(@RequestBody LinkStatisticRequest request){
        try{
            Map<String,Object> resultMap = inventoryReconService.getReconciliationStatisticsList(request);
            return ServerResponse.asSucessResponse(resultMap);
        }catch (Exception e){
            log.info("数据链路对账列表获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("数据链路对账列表获取出错");
        }
    }

    /**
     * 分页获取对账分析数据列表 对应数据对账分析页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReconciliationAnalysisInfo")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getReconciliationAnalysisInfo(@RequestBody ReconAnalysisRequest request) {
        try {
            Map<String,Object> resultMap = inventoryReconService.getReconciliationAnalysisList(request);
            return ServerResponse.asSucessResponse(resultMap);
        } catch (Exception e) {
            log.info("数据对账分析列表获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("数据对账分析获取出错");
        }
    }

    /**
     * 分页获取对账信息 对应数据对账详单页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReconciliationList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getReconciliationList(@RequestBody ReconInfoRequest request){
        System.out.println(JSON.toJSONString(request));
        try{
            if(request.getDZZDHJ() == 0 || StringUtils.isEmpty(request.getSCSJ_RQSJ())){
                return ServerResponse.asErrorResponse("账单环节和日期必填");
            }
            Map<String,Object> resultMap = inventoryReconService.getReconciliationList(request);
            System.out.println(JSON.toJSONString(ServerResponse.asSucessResponse(resultMap)));
            return ServerResponse.asSucessResponse(resultMap);
        }catch (Exception e){
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }


    /**
     * 获取数据对账详情 对应数据对账详单页面 点击列表数据弹窗
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReconciliationDetail")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getReconciliationDetail(@RequestBody ReconInfoRequest request){
        try{
            Map<String,Object> resultMap = inventoryReconService.getReconciliationDetail(request);
            return ServerResponse.asSucessResponse(resultMap);
        }catch (Exception e){
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 刷新某个对账 数据对账详单数据列表后有一个刷新按钮
     * 需要前端传 requestParam: BZSJXJBM XXRWBH DZZDHJ
     */
    @RequestMapping(value = "/updateReconciliation", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Boolean> updateReconciliation(@RequestBody ReconParam reconParam) {
        try {
            if (StringUtils.isEmpty(reconParam.getXXRWBH()) || StringUtils.isEmpty(reconParam.getBZSJXJBM())) {
                log.error("对账字段或标准数据项集编码为空");
            }
            inventoryReconService.updateOneRecon(reconParam);
            return ServerResponse.asSucessResponse(true);
        } catch (Exception e) {
            log.error("刷新按照实例对账报错：" + e.toString());
            return ServerResponse.asErrorResponse(e.getMessage());
        }
    }

    /**
     * 数据包对账 数据对账详单数据列表后有一个数据包对账按钮
     * 需要前端传 requestParam: BZSJXJBM XXRWBH
     */
    @RequestMapping(value = "/dataPacketRecon", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<ReconInfoResponse>> dataDetailRecon(@RequestBody ReconParam reconParam) {
        if (Objects.isNull(reconParam) || StringUtils.isEmpty(reconParam.getBZSJXJBM()) || StringUtils.isEmpty(reconParam.getXXRWBH())) {
            log.error("数据包对账传递的参数为空");
        }
        inventoryReconService.dataPacketRecon(reconParam);
        return null;
    }

    /**
     * 获取对账汇总数据
     * 暂时不用 暂没有数据返回
     */
    @RequestMapping(value = "/getSummarizeBill", method = RequestMethod.POST)
    @ResponseBody
    public void getSummarizeBill(@RequestBody SummarizeBillRequest request) {
        if (Objects.isNull(request) || StringUtils.isEmpty(request.getDataTime())) {
            log.error("获取对账汇总数据 请求参数为空");
        }
        int linkType = request.getLinkType();
        if (linkType < 1 || linkType >3) {
            log.error("获取对账汇总数据 对账环节错误");
        }
        inventoryReconService.getSummarizeBill(request);
    }

    /**
     * 获取近8的统计数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRecentDailyStatisticsList")
    @ResponseBody
    public ServerResponse<List<DayStatisticsResponse>> getRecentDailyStatisticsList(@RequestBody LinkStatisticRequest request){
        try{
            if (request == null || StringUtils.isEmpty(request.getResourceId()) || StringUtils.isEmpty(request.getDataTime())) {
                return ServerResponse.asErrorResponse("参数错误");
            }
            List<DayStatisticsResponse> resultList =  inventoryReconService.getRecentDailyStatisticsList(request);
            return ServerResponse.asSucessResponse(resultList);
        }catch (Exception e){
            log.info("近8天数据获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("近8天数据获取出错");
        }
    }

    /**
     * 首页异常链路数获取
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getAbnormalReconciliationNum")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getAbnormalReconciliationNum(@RequestBody GetListRequest getListRequest){
        try{
            Map<String, Object> map = inventoryReconService.getAbnormalReconciliationNum();
            return ServerResponse.asSucessResponse(map);
        }catch (Exception e){
            log.info("首页异常链路数获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("首页异常链路数获取出错:" + e.getMessage());
        }
    }

    /**
     * 根据对账环节获取数据对账详单页面有效日期
     * @param request 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getDataTimeList")
    @ResponseBody
    public ServerResponse<List<String>> getDataTimeList(@RequestBody ReconInfoRequest request){
        try{
            List<String> resultList =  inventoryReconService.getDataTimeList(request);
            return ServerResponse.asSucessResponse(resultList);
        }catch (Exception e){
            log.info("有数据的日期集合获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }
}
