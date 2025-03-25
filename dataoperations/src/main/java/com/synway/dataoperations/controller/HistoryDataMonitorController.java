package com.synway.dataoperations.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.pojo.historyDataMonitor.ReturnResultHDM;
import com.synway.dataoperations.pojo.historyDataMonitor.RequestParameterHDM;
import com.synway.dataoperations.service.HistoryDataMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nbj
 * @description 历史数据监测
 * @date 2022年5月30日11:02:03
 */
@Controller
@RequestMapping(value = "/historyDataMonitor")
public class HistoryDataMonitorController {
    private static Logger logger = LoggerFactory.getLogger(HistoryDataMonitorController.class);

    @Autowired
    HistoryDataMonitorService historyDataMonitorService;

    /**
     * @description 获取历史数据监测公共数据
     * @return
     */
    @RequestMapping("/getHDMCommon")
    @ResponseBody
    public ServerResponse getHDMCommon(){
        ReturnResultHDM returnResultHDM = new ReturnResultHDM();
        logger.info("开始获取历史数据监测-左侧树及卡片数据");
        try {
            returnResultHDM = historyDataMonitorService.getHDMCommon();
        }catch (Exception e){
            logger.error("获取历史数据监测-左侧树及卡片数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取历史数据监测-左侧树及卡片数据出错");
        }
        logger.info("获取历史数据监测-左侧树及卡片数据结束");
        return ServerResponse.asSucessResponse(returnResultHDM);
    }

    @RequestMapping("/getHDMData")
    @ResponseBody
    public ServerResponse getHDMData(@RequestBody RequestParameterHDM requestParameterHDM){
        ReturnResultHDM returnResultHDM = new ReturnResultHDM();
        logger.info("开始获取历史数据监测-图表数据");
        try {
            returnResultHDM = historyDataMonitorService.getHDMData(requestParameterHDM);
        }catch (Exception e){
            logger.error("获取历史数据监测-图表数据出错：\n" +ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取历史数据监测-图表数据出错");
        }
        return ServerResponse.asSucessResponse(returnResultHDM);
    }

    /**
     * 定时推送告警数据至告警中心
     */
//    @RequestMapping("/test")
//    @ResponseBody
    @Scheduled(cron = "0 0 23 * * ?")
    public void sendHDMMsg(){
        try {
            logger.info("定时推送历史数据监测告警数据至告警中心");
            historyDataMonitorService.sendAlarmMsg();
        }catch (Exception e){
            logger.error("定时推送历史数据监测告警数据至告警中心报错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
