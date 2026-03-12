package com.synway.property.controller;

import com.synway.property.service.AliAssetStatisticsService;
import com.synway.property.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 定时任务
 * 阿里平台资产统计
 *
 * @author nbj
 */
@Controller
public class AliAssetStatisticsController {
    private static Logger logger = LoggerFactory.getLogger(AliAssetStatisticsController.class);

    @Autowired
    AliAssetStatisticsService aliAssetStatisticsService;

    @Scheduled(cron = "${runAliAssetStatistics}")
    public void runAliAssetStatistics() {
        try{
            logger.info("====开始统计阿里平台odps/ads表信息====");
            aliAssetStatisticsService.runAliAssetStatistics();
            logger.info("====统计阿里平台odps/ads表信息结束====");
        }catch (Exception e){
            logger.error("统计阿里平台odps/ads表信息出错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    @GetMapping("/runAliAssetStatistics")
    public void runAliAssetStatisticsForHttp() {
        try{
            logger.info("====手动统计阿里平台odps/ads表信息====");
            aliAssetStatisticsService.runAliAssetStatistics();
            logger.info("====手动统计阿里平台odps/ads表信息结束====");
        }catch (Exception e){
            logger.error("统计阿里平台odps/ads表信息出错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }


}
