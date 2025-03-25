package com.synway.dataoperations.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.interceptor.IgnoreSecurity;
import com.synway.dataoperations.pojo.AlarmPushSetting;
import com.synway.dataoperations.pojo.CommonMap;
import com.synway.dataoperations.pojo.OperatorLog;
import com.synway.dataoperations.service.AlarmMessageService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AlarmMessageController {

    private static Logger logger = LoggerFactory.getLogger(AlarmMessageController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AlarmMessageService service;

    @Autowired()
    private Environment env;

    @RequestMapping("/pushAlarmInfo")
    @IgnoreSecurity
    @ResponseBody
    public ServerResponse pushAlarmInfo(@RequestBody String jsonString) {
        logger.info("================发送异常告警信息开始=================");
        logger.info(jsonString);
        try {
            if (StringUtils.isNotBlank(jsonString)) {
                // 告警信息入库
                service.insertAlarmMessage(jsonString);
                // 只发对账和质检的告警信息给赵佳巍
                if (jsonString.contains("数据对账") || jsonString.contains("数据质量")){
                    List<AlarmPushSetting> alarmPushSettings = service.getAlarmSettings();
                    for (AlarmPushSetting alarmPushSetting : alarmPushSettings){
                        if (alarmPushSetting.getPushMode().contains("http")){
                            String infoUrl = alarmPushSetting.getPushAlarmInfoUrl();
                            restTemplate.postForObject(infoUrl, jsonString, JSONObject.class);
                            logger.info("================发送异常告警信息成功=================");
                        }else if (alarmPushSetting.getPushMode().contains("kafka")){

                        }
                    }
                }
                return ServerResponse.asSucessResponse();
            } else {
                logger.info("================发送异常告警信息失败=================");
                return ServerResponse.asErrorResponse("告警信息为空");
            }
        } catch (Exception e) {
            logger.info("================发送异常告警信息失败=================\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("发送异常告警信息失败");
        }
    }

    @PostMapping("/saveOperatorLog")
    @ResponseBody
    public ServerResponse<String> saveOperatorLog(@Valid @RequestBody List<OperatorLog> operatorLog) {
        try {
            logger.info("操作日志为:" + JSONObject.toJSONString(operatorLog));
            //直接入库
            service.insertOperatorLogs(operatorLog);
            return ServerResponse.asSucessResponse(StringUtils.EMPTY);
        } catch (Exception ex) {
            return ServerResponse.asErrorResponse(String.format("保存操作日志失败，失败原因为:%s",ExceptionUtil.getExceptionTrace(ex)));
        }
    }


    @GetMapping("/getOpeType")
    @ResponseBody
    public ServerResponse<List<CommonMap>> getOpeType() {
        return ServerResponse.asSucessResponse(Arrays.asList(
            new CommonMap(1,"查询"),
            new CommonMap(2,"新增"),
            new CommonMap(3,"修改"),
            new CommonMap(4,"删除"),
            new CommonMap(5,"登出"),
            new CommonMap(6,"导入"),
            new CommonMap(7,"注册"),
            new CommonMap(8,"建表")
        ));
    }

    @GetMapping("/getOperatorLogList")
    @ResponseBody
    public ServerResponse<PageInfo<OperatorLog>> getOperatorLogList(@RequestParam("currentPage") Integer currentPage,
                                                                    @RequestParam("pageSize") Integer pageSize,
                                                                    @RequestParam("sortName") String sortName,
                                                                    @RequestParam("sortOrder") String sortOrder,
                                                                    @RequestParam("opeModule") String opeModule,
                                                                    @RequestParam("opeType") String opeType,
                                                                    @RequestParam("opePerson") String opePerson,
                                                                    @RequestParam("opeBeginTime") String opeBeginTime,
                                                                    @RequestParam("opeEndTime") String opeEndTime ) {
        try {
            PageInfo<OperatorLog> list = service.getOperatorLogList(currentPage,pageSize,sortName,sortOrder,opeModule,
                    opeType,opePerson,opeBeginTime,opeEndTime);
            return ServerResponse.asSucessResponse(list);
        } catch (Exception ex) {
            return ServerResponse.asErrorResponse(String.format("获取日志列表失败，失败原因为:%s",ex.getMessage()));
        }
    }


    @RequestMapping("/pushBusinessData")
    @IgnoreSecurity
    @ResponseBody
    public ServerResponse pushBusinessData(@RequestBody String jsonString) {
        logger.info("================发送业务数据开始=================");
        logger.info(jsonString);
        try {
            if (StringUtils.isNotBlank(jsonString)) {
                String dataUrl = env.getProperty("pushBusinessDataUrl");
                restTemplate.postForObject(dataUrl, jsonString, JSONObject.class);
                logger.info("================发送业务数据成功=================");
                return ServerResponse.asSucessResponse("发送业务数据成功");
            } else {
                logger.info("================发送业务数据失败=================");
                return ServerResponse.asErrorResponse("业务数据为空");
            }
        } catch (Exception e) {
            logger.info("================发送业务数据失败=================" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("发送业务数据失败");
        }
    }
}