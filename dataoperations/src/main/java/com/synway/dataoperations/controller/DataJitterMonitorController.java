package com.synway.dataoperations.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.pojo.dataJitterMonitor.RequestParameterDJM;
import com.synway.dataoperations.pojo.dataJitterMonitor.ReturnResultDJM;
import com.synway.dataoperations.pojo.dataJitterMonitor.TreeAndCardJitter;
import com.synway.dataoperations.service.DataJitterMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nbj
 * @description 数据抖动监测
 * @date 2023年5月8日08:34:41
 */
@Controller
@RequestMapping(value = "/dataJitterMonitor")
public class DataJitterMonitorController {
    private static Logger logger = LoggerFactory.getLogger(DataJitterMonitorController.class);

    @Autowired
    DataJitterMonitorService dataJitterMonitorService;

    @RequestMapping(value = "getTreeAndCardData")
    @ResponseBody
    public ServerResponse getTreeAndCardData(@RequestParam("searchName") String searchName){
        TreeAndCardJitter treeAndCardJitter = new TreeAndCardJitter();
        logger.info("开始获取数据抖动监测-左侧列表及卡片数据");
        try {
            treeAndCardJitter = dataJitterMonitorService.getTreeAndCardData(searchName);
            logger.info("获取数据抖动监测-左侧列表及卡片数据结束");
            return ServerResponse.asSucessResponse(treeAndCardJitter);
        }catch (Exception e){
            logger.error("获取数据抖动监测-左侧列表数据及卡片数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据抖动监测-左侧列表数据及卡片数据出错");
        }
    }

    @RequestMapping(value = "getChartDataDJM")
    @ResponseBody
    public ServerResponse getChartDataDJM(@RequestBody RequestParameterDJM requestParameter){
        ReturnResultDJM resultDJM = new ReturnResultDJM();
        logger.info("开始获取数据抖动监测-图表数据");
        try {
            resultDJM = dataJitterMonitorService.getChartDataDJM(requestParameter);
            return ServerResponse.asSucessResponse(resultDJM);
        }catch (Exception e){
            logger.error("获取数据抖动监测-图表数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据抖动监测-图表数据出错");
        }
    }

    /**
     * 定时推送告警数据至告警中心
     */
//    @RequestMapping("/test")
//    @ResponseBody
    @Scheduled(cron = "0 0 23 * * ?")
    public void sendDataJitterMonitorMsg(){
        try {
            logger.info("定时推送数据抖动告警数据至告警中心");
            dataJitterMonitorService.sendAlarmMsg();
        }catch (Exception e){
            logger.error("定时推送数据抖动告警数据至告警中心报错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
