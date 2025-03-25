package com.synway.reconciliation.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.CommonErrorCode;
import com.synway.reconciliation.conditional.IssueCondition;
import com.synway.reconciliation.conditional.KafkaMqCondition;
import com.synway.reconciliation.interceptor.IgnoreSecurity;
import com.synway.reconciliation.pojo.issue.*;
import com.synway.reconciliation.schedule.issue.IssueKafkaManage;
import com.synway.reconciliation.service.IssueReconciliationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 下发对账controller
 * @author DZH
 */
@Slf4j
@Controller
@RequestMapping("/reconciliation")
@Conditional({IssueCondition.class, KafkaMqCondition.class})
public class IssueReconciliationController {

    @Autowired
    private IssueReconciliationService issueReconciliationService;

    @Autowired
    private IssueKafkaManage issueKafkaManage;

    @Value("${local}")
    private String local;

    /**
     * test
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/testStatistics", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testStatistics() {
        System.out.println(local);
        issueReconciliationService.issueReconciliationStatistics();
        return ServerResponse.asSucessResponse("testStatistics");
    }

    @IgnoreSecurity
    @RequestMapping(value = "/testConsumer", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testConsumer() {
        issueKafkaManage.consumerIssueAcceptorBill();
        issueKafkaManage.consumerIssueProviderBill();
        return ServerResponse.asSucessResponse("testConsumer");
    }

    @IgnoreSecurity
    @RequestMapping(value = "/testSendTaskInfo", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testSendTaskInfo() {
        issueKafkaManage.sendTaskInfo();
        return ServerResponse.asSucessResponse("testSendTaskInfo");
    }

    @IgnoreSecurity
    @RequestMapping(value = "/testConsumeTaskInfo", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testConsumeTaskInfo() {
        issueKafkaManage.consumeTaskInfo();
        return ServerResponse.asSucessResponse("testConsumeTaskInfo");
    }

    /**
     * 获取下发对账统计分页列表
     * @param request
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/getPageIssueBill", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map<String, Object>> getPageIssueBill(@RequestBody IssueReconciliationRequest request) {
        try {
            if (null == request || request.getPageNum() < 0 || request.getPageSize() < 0) {
                return ServerResponse.asErrorResponse("传递参数错误，请检查");
            }
            IssueReconciliationRequestExtend requestExtend = new IssueReconciliationRequestExtend();
            BeanUtils.copyProperties(request, requestExtend);
            Map<String, Object> resultMap = issueReconciliationService.getPageIssueBill(requestExtend);
            System.out.println(JSONObject.toJSONString(resultMap));
            return ServerResponse.asSucessResponse(resultMap);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 获取下发历史账单
     *
     * @param request
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/getIssueHistoryBill", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<IssueHistoryResponse>> getIssueHistoryBill(@RequestBody IssueHistoryRequest request) {
        try {
            if (null == request || StringUtils.isBlank(request.getCheckTime()) || StringUtils.isBlank(request.getJobId()) || StringUtils.isBlank(request.getDataNameEn())) {
                return ServerResponse.asErrorResponse("传递参数错误，请检查");
            }
            List<IssueHistoryResponse> issueHistoryBill = issueReconciliationService.getIssueHistoryBill(request);
            return ServerResponse.asSucessResponse(issueHistoryBill);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 分页获取基线时间
     *
     * @param request
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/getPageBaseTime", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map<String, Object>> getPageBaseTime(@RequestBody IssueBaseTimeRequest request) {
        try {
            if (null == request || request.getPageNum() < 0 || request.getPageSize() < 0) {
                return ServerResponse.asErrorResponse("传递参数错误，请检查");
            }
            Map<String, Object> resultMap = issueReconciliationService.getPageBaseTime(request);
            return ServerResponse.asSucessResponse(resultMap);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 保存或修改基线时间
     *
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/saveOrUpdateBaseTime", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Boolean> saveOrUpdateBaseTime(@RequestBody IssueBaseTimeRequest request) {
        try {
            if (null == request) {
                return ServerResponse.asErrorResponse("传递参数为空");
            }
            Boolean result = issueReconciliationService.saveOrUpdateBaseTime(request);
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 删除一条基线时间
     *
     * @param issueBaseTime
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/deleteOneBaseTime", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Boolean> deleteOneBaseTime(@RequestBody IssueBaseTime issueBaseTime) {
        try {
            if (null == issueBaseTime) {
                return ServerResponse.asErrorResponse("传递参数为空");
            } else if (StringUtils.isBlank(issueBaseTime.getJobId()) || StringUtils.isBlank(issueBaseTime.getDataNameEn())) {
                return ServerResponse.asErrorResponse("传递参数错误，请检查");
            }
            Boolean result = issueReconciliationService.deleteOneBaseTime(issueBaseTime);
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 快速操作基线时间 包括 一键禁用0、启用1、清空2
     *
     * @param request
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/quickMultiBaseTime", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Boolean> quickMultiBaseTime(@RequestBody IssueBaseTimeRequest request) {
        try {
            if (request.getType() == null) {
                return ServerResponse.asErrorResponse("传递类型为空");
            } else if (request.getType() < 0 || request.getType() > 2) {
                return ServerResponse.asErrorResponse("传递类型错误，请检查");
            }
            Boolean result = issueReconciliationService.quickMultiBaseTime(request);
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 获取数据接收方过滤项
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/getAcceptCity", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<String>> getAcceptCity() {
        List<String> acceptCity;
        try {
            acceptCity = issueReconciliationService.getAcceptCity();
            if (!"总队".equals(local)) {
                acceptCity = acceptCity.stream().filter(t -> local.equals(t) || "总队".equals(t)).collect(Collectors.toList());
            }
            return ServerResponse.asSucessResponse(acceptCity);
        } catch (Exception e) {
            log.error(e.toString());
            return ServerResponse.asErrorResponse(CommonErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * 导出下发对账统计信息
     * @param response
     * @param request
     */
    @IgnoreSecurity
    @RequestMapping(value = "/exportIssueStatistics", method = RequestMethod.POST)
    @ResponseBody
    public void exportIssueStatistics(HttpServletResponse response, @RequestBody IssueReconciliationRequest request) {
        IssueReconciliationRequestExtend requestExtend = new IssueReconciliationRequestExtend();
        BeanUtils.copyProperties(request, requestExtend);
        if (!"总队".equals(local)) {
            requestExtend.setLocal(local);
        }
        List<IssueDayStatisticsExcel> statisticsList = issueReconciliationService.exportIssueStatistics(requestExtend);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;fileName=" + System.currentTimeMillis() + ".xlsx");
        try {
            EasyExcel.write(response.getOutputStream(), IssueDayStatisticsExcel.class).sheet("下发对账统计信息").doWrite(statisticsList);
        } catch (IOException exception) {
            log.error("导出excel文件失败", exception);
        }
    }
}
