package com.synway.dataoperations.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.pojo.dataPiledMonitor.RequestParameterDPM;
import com.synway.dataoperations.pojo.dataPiledMonitor.ReturnResultDPM;
import com.synway.dataoperations.pojo.dataPiledMonitor.TreeAndCartPiled;
import com.synway.dataoperations.service.DataPiledMonitorService;
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
 * @date 2023年6月8日17:13:18
 * @description 数据堆积监测
 */

@Controller
@RequestMapping("/dataPiledMonitor")
public class DataPiledMonitorController {
    private static Logger logger = LoggerFactory.getLogger(DataPiledMonitorController.class);

    @Autowired
    DataPiledMonitorService dataPiledMonitorService;

    @RequestMapping(value = "/getTreeAndCardData")
    @ResponseBody
    public ServerResponse getTreeAndCardData(@RequestParam("searchName") String searchName, @RequestParam("dataType") String dataType){
        try {
            logger.info("开始获取左侧树及卡片数据");
            TreeAndCartPiled treeAndCartPiled = new TreeAndCartPiled();
            treeAndCartPiled = dataPiledMonitorService.getTreeAndCardData(searchName, dataType);
            return ServerResponse.asSucessResponse(treeAndCartPiled);
        }catch (Exception e){
            logger.error("获取左侧树及卡片数据失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取左侧树及卡片数据失败");
        }
    }

    /**
     * @description 获取数据堆积监控图表数据
     * @param requestParameter
     * @return
     */
    @RequestMapping(value = "getChartDataDPM")
    @ResponseBody
    public ServerResponse getChartDataDPM(@RequestBody RequestParameterDPM requestParameter){
        try {
            logger.info("开始获取数据堆积监控-图表数据");
            if (StringUtils.isBlank(requestParameter.getDataName())){
                logger.error("数据名为空!");
                return ServerResponse.asErrorResponse("数据名为空!");
            }
            ReturnResultDPM resultDJM = new ReturnResultDPM();
            resultDJM = dataPiledMonitorService.getChartDataDPM(requestParameter);
            return ServerResponse.asSucessResponse(resultDJM);
        }catch (Exception e){
            logger.error("获取数据堆积监控-图表数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据堆积监控-图表数据出错");
        }
    }

    /**
     * 定时获取kafka消费数据
     */
//    @RequestMapping("/test")
//    @ResponseBody
    @Scheduled(cron = "0 0 * * * ?")
    public void getDataPiledMonitor(){
        try {
            logger.info("定时获取kafka消费数据");
            dataPiledMonitorService.getDataPiledMonitor();
        }catch (Exception e){
            logger.error("定时获取kafka消费数据报错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
