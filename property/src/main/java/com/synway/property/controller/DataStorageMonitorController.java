package com.synway.property.controller;


import com.synway.property.conditional.OdpsConditional;
import com.synway.property.service.DataStorageMonitorService;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定时任务
 * 此类下有大量的入库操作，拉长时间插入
 *
 * @author 数据接入
 */
@Controller
@RequestMapping("/")
public class DataStorageMonitorController {
    private static Logger logger = LoggerFactory.getLogger(DataStorageMonitorController.class);
    @Autowired
    DataStorageMonitorService dataStorageMonitorServiceImpl;

    //实时表数据查询监控，查询今日的数据量
    @Scheduled(cron = "${dataStorageRealTimeStatistical}")
    @Conditional(OdpsConditional.class)
    public void dataStorageRealTimeStatistical() {
        logger.info("================开始统计需要监控的表今日数据量=================");
        dataStorageMonitorServiceImpl.statisticsTableTodayVolume();
        logger.info("================统计需要监控的表今日数据量定时任务结束==========");
    }

    @RequestMapping("dataStorageRealTimeStatisticalController")
    @ResponseBody
    public String dataStorageRealTimeStatisticalController() {
        logger.info("================开始统计需要监控的表今日数据量=================");
        dataStorageMonitorServiceImpl.statisticsTableTodayVolume();
        logger.info("================统计需要监控的表今日数据量定时任务结束==========");
        return "开始重新查询需要监控的实时表信息";
    }

    @GetMapping("refreshSourceData")
    @ResponseBody
    public void getAllClassifyInterfaceData(){
        logger.info("================开始手动刷新数据组织资产的信息=================");
        dataStorageMonitorServiceImpl.updateAssetsInfo();
        logger.info("================手动刷新数据组织资产的信息结束=================");
    }

    @Scheduled(cron = "${organizationData}")
    public void getAllOrganizationDataController() {
        logger.info("================开始统计数据组织资产的信息=================");
        dataStorageMonitorServiceImpl.updateAssetsInfo();
        logger.info("================统计数据组织资产的信息结束=================");
    }


    @GetMapping("getDataBaseStatusForHttp")
    @ResponseBody
    public void getDataBaseStatusForHttp() {
        logger.info("================手动开始统计数据库概况的信息=================");
        dataStorageMonitorServiceImpl.getDataBaseStatus();
        logger.info("================手动统计数据库概况的信息结束=================");
    }

    @Scheduled(cron = "${dataBaseStatus}")
    public void getDataBaseStatus() {
        logger.info("================开始统计数据库概况的信息=================");
        dataStorageMonitorServiceImpl.getDataBaseStatus();
        logger.info("================统计数据库概况的信息结束=================");
    }

    @Scheduled(cron = "${hbaseData}")
    public void getHbaseDataController() {
        logger.info("================开始统计hbase的信息=================");
        dataStorageMonitorServiceImpl.getHbaseData();
        logger.info("================统计hbase的信息结束=================");
    }

    @Scheduled(cron = "${hiveData}")
    public void getHiveDataController() {
        logger.info("================开始统计hive的信息=================");
        dataStorageMonitorServiceImpl.getHiveData();
        logger.info("================统计hive的信息结束=================");
    }

    @Scheduled(cron = "${clickhouseData}")
    @RequestMapping("updateClickhouseData")
    @ResponseBody
    public void getClickhouseDataController() {
        logger.info("================开始统计clickhouse的信息=================");
        dataStorageMonitorServiceImpl.getClickhouseData();
        logger.info("================统计clickhouse的信息结束=================");
    }

    @RequestMapping("updateDataResourceInfo")
    @ResponseBody
    public ServerResponse updateDataResourceInfo() {
        logger.info("================开始获取数据仓库的信息=================");
        try {
            dataStorageMonitorServiceImpl.getDataResourceInfo();
            logger.info("================开始获取数据仓库的信息=================");
            return ServerResponse.asSucessResponse("成功");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("失败");
        }
    }

    @Scheduled(cron = "${deleteOverTimeAssets}")
    public ServerResponse deleteOverTimeAssets(){
        try {
            dataStorageMonitorServiceImpl.deleteOverTimeAssets();
        } catch (Exception e) {
            logger.error("删除表组织管理过期数据失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asSucessResponse("删除表组织管理过期数据失败");
        }
         return ServerResponse.asSucessResponse("删除表组织管理过期数据成功");
    }

}
