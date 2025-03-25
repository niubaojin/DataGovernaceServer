package com.synway.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.property.pojo.lifecycle.LifeCyclePageParams;
import com.synway.property.pojo.lifecycle.LifeCyclePageReturn;
import com.synway.property.pojo.lifecycle.LifeCycleShowField;
import com.synway.property.pojo.lifecycle.ValDensityPageParam;
import com.synway.property.service.LifeCycleService;
import com.synway.property.util.CacheManager;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2021/2/19 9:43
 */
@RequestMapping("/dataOrganizationMonitoring/lifeCycle")
@Controller
public class LifeCycleController {
    private static Logger logger = LoggerFactory.getLogger(LifeCycleController.class);

    @Autowired
    private LifeCycleService service;

    @Autowired
    private CacheManager cacheManager;

    @PostMapping("/getLifeCycleInfo")
    @ResponseBody
    public ServerResponse<LifeCyclePageReturn> getLifeCycleInfo(@RequestBody LifeCyclePageParams queryParams) {
        logger.info("生命周期的查询参数为：" + JSONObject.toJSONString(queryParams));
        ServerResponse<LifeCyclePageReturn> serverResponse;
        try {
            LifeCyclePageReturn pageReturn = service.getLifeCycleInfo(queryParams);
            serverResponse = ServerResponse.asSucessResponse(pageReturn);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("生命周期查询报错：" + e.getMessage());
            logger.error("生命周期查询报错：", e);
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping("/getLifeCycleFilters")
    @ResponseBody
    public ServerResponse<LifeCyclePageReturn> getLifeCycleFilters(
            @RequestBody LifeCyclePageParams queryParams) {
        logger.info("生命周期的查询参数为：" + JSONObject.toJSONString(queryParams));
        ServerResponse<LifeCyclePageReturn> serverResponse;
        try {
            LifeCyclePageReturn pageReturn = service.getLifeCycleFilters(queryParams);
            serverResponse = ServerResponse.asSucessResponse(pageReturn);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("生命周期查询报错：" + e.getMessage());
            logger.error("生命周期查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping("/getValDensity")
    @ResponseBody
    public ServerResponse<Map> getValDensity(
            @RequestBody ValDensityPageParam queryParams) {
        logger.info("价值密度的查询参数为：" + JSONObject.toJSONString(queryParams));
        ServerResponse<Map> serverResponse;
        try {
            Map density = service.getValDensity(queryParams);
            serverResponse = ServerResponse.asSucessResponse(density);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("价值密度查询报错：" + e.getMessage());
            logger.error("价值密度查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping("/updateAllValDensity")
    @ResponseBody
    public ServerResponse updateAllValDensity() {
        ServerResponse<Map> serverResponse;
        try {
            cacheManager.addOrUpdateCache("updateAllValDensity", true);
            service.updateAllValDensity(true);
            cacheManager.addOrUpdateCache("updateAllValDensity", false);
            serverResponse = ServerResponse.asSucessResponse("更新价值密度成功");
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("更新价值密度报错：" + e.getMessage());
            logger.error("更新价值密度报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============更新价值密度结束===========");
        return serverResponse;
    }

    @RequestMapping("/getUpdateAllValDensityStatus")
    @ResponseBody
    public ServerResponse getUpdateAllValDensityStatus() {
        ServerResponse serverResponse;
        try {
            serverResponse = ServerResponse.asSucessResponse(cacheManager.getValue("updateAllValDensity"));
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("查询更新价值密度状态报错：" + e.getMessage());
            logger.error("查询更新价值密度状态报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询更新价值密度状态结束===========");
        return serverResponse;
    }

    @RequestMapping("/saveValDensity")
    @ResponseBody
    public ServerResponse saveValDensity(
            @RequestBody ValDensityPageParam queryParams) {
        ServerResponse<Map> serverResponse;
        try {
            service.saveValDensity(queryParams);
            serverResponse = ServerResponse.asSucessResponse("更新成功");
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("价值密度查询报错：" + e.getMessage());
            logger.error("价值密度查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping("/updateValDensity")
    @ResponseBody
    public ServerResponse updateValDensity(@RequestBody LifeCyclePageParams queryParams){
        ServerResponse<List<Double>> serverResponse;
        try {
            List<Double> params = service.updateValDensity(queryParams);
            serverResponse = ServerResponse.asSucessResponse("更新成功",params);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("更新价值密度报错：" + e.getMessage());
            logger.error("更新价值密度报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============更新结束===========");
        return serverResponse;
    }

    @Scheduled(cron = "${organizationData}")
    public void getAllOrganizationDataController() {
        logger.info("================开始统计数据组织资产的信息=================");
        cacheManager.addOrUpdateCache("updateAllValDensity", true);
        service.updateAllValDensity(false);
        cacheManager.addOrUpdateCache("updateAllValDensity", false);
        logger.info("================统计数据组织资产的信息结束=================");
    }

    /**
     * 保存生命周期页面的显示字段列表
     * @param lifeCycleShowField
     * @return
     */
    @RequestMapping("/saveLifeCycleShowField")
    @ResponseBody
    public ServerResponse updateLifeCycleShowField(@RequestBody LifeCycleShowField lifeCycleShowField) {
        logger.info("更新生命周期页面显示字段的参数为：" + JSONObject.toJSONString(lifeCycleShowField));
        try{
            String queryParams = StringUtils.join(lifeCycleShowField.getShowFieldList().toArray(),",");
            service.updateLifeCycleShowField(queryParams);
            return ServerResponse.asSucessResponse("显示字段更新成功",queryParams);
        }catch (Exception e){
            logger.error("更新生命周期页面显示字段报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("显示字段更新失败：" + e.getMessage());
        }
    }

    @RequestMapping("/getLifeCycleShowField")
    @ResponseBody
    public ServerResponse getLifeCycleShowField() {
        ServerResponse<List<String>> serverResponse;
        try{
            List<String> resultReturn = service.getLifeCycleShowField();
            if (resultReturn == null){
                serverResponse = ServerResponse.asSucessResponse(new ArrayList<>(0));
            }else {
                serverResponse = ServerResponse.asSucessResponse(resultReturn);
            }
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取显示字段失败：" + e.getMessage());
            logger.error("更新生命周期页面显示字段报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

}
