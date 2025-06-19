package com.synway.property.controller;


import com.alibaba.fastjson.JSONObject;
import com.synway.property.pojo.DataProcess.*;
import com.synway.property.pojo.RequestParameter;
import com.synway.property.pojo.datastoragemonitor.TableOrganizationData;
import com.synway.property.service.DataProcessService;
import com.synway.property.util.CacheManager;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *  数据历程的相关接口数据
 *  页面查询数据历程的接口
 *  保存指定对象的
 * @author wdw
 */
@RequestMapping("/interface")
@Controller
public class DataProcessController {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessController.class);
    @Autowired
    private DataProcessService dataProcessServiceImpl;

    @Autowired
    private CacheManager cacheManager;

    private Integer userIdGlobal = -100;

    /**
     *  保存数据历程的相关数据
     * @param dataProcess  数据历程的实体类
     * @return  保存失败的结果 {"message":"保存数据历程数据报错For input string: \"20200722162331878\"","status":0,"success":false}
     *         保存成功的结果  {"message":"","status":1,"success":true}
     */
    @RequestMapping(value = "/saveDataProcess" ,method = RequestMethod.POST)
    @ResponseBody
    public String saveDataProcess(@RequestBody DataProcess dataProcess) {
        ServerResponse serverResponse = null;
        try{
            if(dataProcess == null){
                throw new Exception("传入的参数为空");
            }
            logger.info("请求的数据参数为："+JSONObject.toJSONString(dataProcess));
            Boolean flag = dataProcessServiceImpl.saveDataProcess(dataProcess);
            serverResponse = ServerResponse.asSucessResponse(flag);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("保存数据历程数据报错");
            logger.error("保存数据历程数据报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return JSONObject.toJSONString(serverResponse);
    }

//    /**
//     * 页面上查询 数据历程 数据，(20250617：废弃)
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/searchDataProcess" )
//    @ResponseBody
//    public ServerResponse<ProcessPage> searchDataProcess(DataProcessRequest request) {
//        ServerResponse<ProcessPage> serverResponse = null;
//        try{
//            ProcessPage processPage = dataProcessServiceImpl.searchDataProcess(request);
//            serverResponse = ServerResponse.asSucessResponse(processPage);
//        }catch (Exception e){
//            logger.error("查询数据历程数据报错"+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("查询数据历程数据报错【"+e.getMessage()+"】");
//        }
//
//        return serverResponse;
//    }


    /**
     * 获取所有操作模块id值和 对应的中文名称
     * @return
     */
    @RequestMapping(value = "/getAllModuleId" )
    @ResponseBody
    public ServerResponse<List<ModuleIdSelect>> getAllModuleId() {
        ServerResponse<List<ModuleIdSelect>> serverResponse = null;
        try{
            List<ModuleIdSelect> moduleIdSelectList = dataProcessServiceImpl.getAllModuleId();
            serverResponse = ServerResponse.asSucessResponse(moduleIdSelectList);
        }catch (Exception e){
            logger.error("获取操作模块的数据报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取操作模块的数据报错【"+e.getMessage()+"】");
        }
        return serverResponse;
    }


    /**
     *  搜索提示框
     */
    @RequestMapping(value = "/searchValuePrompt" )
    @ResponseBody
    public ServerResponse<List<String>> searchValuePrompt(DataProcessRequest request) {
        ServerResponse<List<String>> serverResponse = null;
        try{
            List<String> result = dataProcessServiceImpl.searchValuePrompt(request);
            serverResponse = ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("获取提示信息报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取提示信息报错【"+e.getMessage()+"】");
        }

        return serverResponse;
    }


    /**
     *  从dubbo接口中获取 用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserMesageByDubbo" )
    @ResponseBody
    public OrganUser getUserMesageByDubbo(Integer userId) {
        // 缓存过期的时候回用到这个变量
        userIdGlobal = userId;
        OrganUser result = null;
        try{
            result = (OrganUser)cacheManager.getValue(String.valueOf(userId));
            if(result == null){
                result = dataProcessServiceImpl.getUserMesageByDubbo(userId);
                cacheManager.addOrUpdateCache(String.valueOf(userId),result);
            }
        }catch (Exception e){
            logger.error("从dubbo中获取用户信息报错"+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("从dubbo中获取用户信息报错【"+e.getMessage()+"】");
        }
        return result;
    }

//    /**
//     * 定期从dubbo更新用户信息
//     * @return
//     */
//    @Scheduled(cron = "${getUserMessage}")
//    public void getUserMessageByDubboCron(){
//        OrganUser result = null;
//        try {
//            result = (OrganUser)cacheManager.getValue(String.valueOf(userIdGlobal));
//            if(userIdGlobal != -100){
//                cacheManager.evictCache(String.valueOf(userIdGlobal));
//                cacheManager.addOrUpdateCache(String.valueOf(userIdGlobal),result);
//            }
//        }catch (Exception e){
//            logger.error("从dubbo中获取用户信息报错"+ExceptionUtil.getExceptionTrace(e));
//        }
//    }

}
