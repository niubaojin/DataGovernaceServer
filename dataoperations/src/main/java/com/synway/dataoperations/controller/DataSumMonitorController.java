package com.synway.dataoperations.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.pojo.dataSizeMonitor.RequestParameterDSM;
import com.synway.dataoperations.pojo.dataSizeMonitor.ReturnResultDSM;
import com.synway.dataoperations.pojo.dataSizeMonitor.TreeAndCard;
import com.synway.dataoperations.service.DataSumMonitorService;
import org.apache.commons.lang3.StringUtils;
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
 * @description 数据量监测
 * @date 2023年4月28日13:40:27
 */
@Controller
@RequestMapping(value = "/dataSumMonitor")
public class DataSumMonitorController {
    private static Logger logger = LoggerFactory.getLogger(DataSumMonitorController.class);

    @Autowired
    DataSumMonitorService dataSumMonitorService;


    /**
     * 左侧树及卡片数据
     * @return
     */
    @RequestMapping("/getTreeAndCardData")
    @ResponseBody
    public ServerResponse getTreeAndCardData(@RequestParam("searchName") String searchName){
        TreeAndCard returnResult = new TreeAndCard();
        logger.info("开始获取数据量监测-左侧树及卡片数据");
        try {
            returnResult = dataSumMonitorService.getTreeAndCardData(searchName);
            logger.info("获取数据量监测-左侧树及卡片数据结束");
        }catch (Exception e){
            logger.error("获取数据量监测-左侧树及卡片数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据量监测-左侧树及卡片数据出错");
        }
        return ServerResponse.asSucessResponse(returnResult);
    }

    /**
     * 图表数据
     * @return
     */
    @RequestMapping("/getChartData")
    @ResponseBody
    public ServerResponse getChartData(@RequestBody RequestParameterDSM requestParameter){
        ReturnResultDSM returnResultDSM = new ReturnResultDSM();
        logger.info("开始获取数据量监测-图表数据");
        try {
            if (StringUtils.isBlank(requestParameter.getTableId())){
                throw new Exception("tableId为空!");
            }
            returnResultDSM = dataSumMonitorService.getChartData(requestParameter);
        }catch (Exception e){
            logger.error("获取数据量监测-图表数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据量监测-图表数据出错");
        }
        return ServerResponse.asSucessResponse(returnResultDSM);
    }

    /**
     * 定时推送告警数据至告警中心
     */
//    @RequestMapping("/test")
//    @ResponseBody
    @Scheduled(cron = "0 0 * * * ?")
    public void sendDataSumMonitorMsg(){
        try {
            logger.info("定时推送告警数据至告警中心");
            dataSumMonitorService.sendAlarmMsg();
        }catch (Exception e){
            logger.error("定时推送告警数据至告警中心报错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
