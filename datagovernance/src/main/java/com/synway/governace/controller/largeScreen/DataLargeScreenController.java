package com.synway.governace.controller.largeScreen;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.largeScreen.QueryInfo;
import com.synway.governace.pojo.largeScreen.StatisticsResult;
import com.synway.governace.service.largeScreen.LargeScreenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据大屏
 *
 * @author ywj
 * @date 2020/7/22 14:51
 */
@Controller
@RequestMapping("/largeScreen")
public class DataLargeScreenController {
    // 日志
    private Logger logger = Logger.getLogger(DataLargeScreenController.class);

    @Autowired
    private LargeScreenService largeScreenServiceImpl;

    @Value("${largeScreenRefreshInterval.second}")
    private String intervalSec;

    @Value("${largeScreenRefreshInterval.minute}")
    private String intervalMin;

    /**
     * @description 定时刷新时间间隔配置获取 单位：秒、分
     * @return ServerResponse<Map<String, Object>>
     * @author ywj
     * @date 2020/7/22 14:51
     */
    @RequestMapping(value = "/getRefreshInterval")
    @ResponseBody
    public ServerResponse<Map<String, Object>> getRefreshInterval() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("sec", intervalSec);
            map.put("min", intervalMin);
            return ServerResponse.asSucessResponse(map);
        } catch (Exception e) {
            logger.error("定时刷新时间间隔配置获取异常:" , e);
            return ServerResponse.asErrorResponse("定时刷新时间间隔配置获取报错" + e.getMessage());
        }
    }

    /**
     * @description 标准定义统计信息获取
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/22 14:52
     */
    @RequestMapping(value = "/getStandardStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getStandardStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.standardDefinedStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("标准定义统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("标准定义统计信息获取异常" + e.getMessage());
        }
    }

    /**
     * @description 定时更新LARGE_SCREEN_UPDATE_DATA
     * @param
     * @return void
     * @author ywj
     * @date 2020/7/23 14:25
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void refreshLargeScreenUpdateData() {
        largeScreenServiceImpl.refreshLastUpdateData();
    }


    /**
     * @description 数据质量治理效果统计
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/24 14:12
     */
    @RequestMapping(value = "/getQualityGovernStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getQualityGovernStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getQualityGovernStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据质量治理统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据质量治理统计信息获取异常" + e.getMessage());
        }
    }

    /**
     * @description 数据处理节点信息集合
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/24 14:12
     */
    @RequestMapping(value = "/getHandleNodeStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getHandleNodeStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getHandleNodeStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据处理节点统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据处理节点统计信息获取异常" + e.getMessage());
        }
    }

    /**
     * @description 数据组织一级分类统计
     * @param
     * @return ServerResponse<Map<String, Object>>
     * @author ywj
     * @date 2020/7/29 9:03
     */
    @RequestMapping(value = "/getPrimaryClassifyStatistics")
    @ResponseBody
    public ServerResponse<Map<String, Object>> getPrimaryClassifyStatistics() {
        try {
            Map<String, Object> result = largeScreenServiceImpl.getPrimaryClassifyStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据组织一级分类统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据组织一级分类统计信息获取报错" + e.getMessage());
        }
    }

    /**
     * @description 数据组织二级分类统计
     * @param queryInfo
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/29 9:07
     */
    @RequestMapping(value = "/getSecondClassifyStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getSecondClassifyStatistics(@RequestBody QueryInfo queryInfo) {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getSecondClassifyStatistics(queryInfo);
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据组织二级分类统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据组织二级分类统计信息获取报错" + e.getMessage());
        }
    }

    /**
     * @description 数据组织三级分类统计
     * @param queryInfo
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/29 9:08
     */
    @RequestMapping(value = "/getThirdClassifyStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getThirdClassifyStatistics(@RequestBody QueryInfo queryInfo) {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getThirdClassifyStatistics(queryInfo);
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据组织三级分类统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据组织三级分类统计信息获取异常" + e.getMessage());
        }
    }

    /**
     * @description 数据接入概况统计
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/31 9:32
     */
    @RequestMapping(value = "/getDataInceptStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getDataInceptStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getDataInceptStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据接入概况统计信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据接入概况统计信息获取报错" + e.getMessage());
        }
    }

    /**
     * @description 数据接入节点信息
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/31 9:33
     */
    @RequestMapping(value = "/getInceptNodeStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getInceptNodeStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getInceptNodeStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据接入节点信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据接入节点信息获取报错" + e.getMessage());
        }
    }

    /**
     * @description 近7日数据接入量
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/31 9:34
     */
    @RequestMapping(value = "/getInceptRecentStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getInceptRecentStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getInceptRecentStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("近7日数据接入量获取异常:" , e);
            return ServerResponse.asErrorResponse("近7日数据接入量获取报错" + e.getMessage());
        }
    }

    /**
     * @description 近7日数据处理总量趋势
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/31 10:22
     */
    @RequestMapping(value = "/getHandleRecentStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getHandleRecentStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getHandleRecentStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("近7日数据处理总量趋势获取异常:" , e);
            return ServerResponse.asErrorResponse("近7日数据处理总量趋势获取报错" + e.getMessage());
        }
    }

    /**
     * @description 数据处理概况信息
     * @param
     * @return ServerResponse<List<StatisticsResult>>
     * @author ywj
     * @date 2020/7/31 10:22
     */
    @RequestMapping(value = "/getDataHandleStatistics")
    @ResponseBody
    public ServerResponse<List<StatisticsResult>> getDataHandleStatistics() {
        try {
            List<StatisticsResult> result = largeScreenServiceImpl.getDataHandleStatistics();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("数据处理概况信息获取异常:" , e);
            return ServerResponse.asErrorResponse("数据处理概况信息获取报错" + e.getMessage());
        }
    }

    /**
     * @description 数据中心统计
     * @param
     * @return com.synway.data.governace.pojo.process.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @author ywj
     * @date 2020/8/19 15:08
     */
    @RequestMapping(value = "/getDataCenterStatistics")
    @ResponseBody
    public ServerResponse<Map<String, Object>> getDataCenterStatistics(@RequestParam("platFormType")String platFormType) {
        try {
            Map<String, Object> map = largeScreenServiceImpl.getDataCenterStatistics(platFormType);
            return ServerResponse.asSucessResponse(map);
        } catch (Exception e) {
            logger.error("数据中心统计异常:" , e);
            return ServerResponse.asErrorResponse("数据中心统计报错" + e.getMessage());
        }
    }

}
