package com.synway.dataoperations.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.pojo.AlarmReturnResultMap;
import com.synway.dataoperations.pojo.DataGFMsg;
import com.synway.dataoperations.pojo.DataGFReturnMap;
import com.synway.dataoperations.pojo.RequestParameter;
import com.synway.dataoperations.service.AlarmCenterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/alarmcenter")
public class AlarmCenterController {
    private static Logger logger = LoggerFactory.getLogger(AlarmCenterController.class);

    @Autowired
    AlarmCenterService alarmCenterService;

    /**
     * @description 获取告警中心数据
     * @param requestParameter
     * @return
     */
    @RequestMapping(value = "/getAlarmData")
    @ResponseBody
    public ServerResponse getAlarmData(@RequestBody RequestParameter requestParameter){
        try {
            AlarmReturnResultMap alarmReturnResultMap = alarmCenterService.getAlarmData(requestParameter);
            return ServerResponse.asSucessResponse(alarmReturnResultMap);
        }catch (Exception e){
            logger.error("获取告警数据报错：\n"+ ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取告警数据报错："+e.getMessage());
        }
    }

    @RequestMapping(value = "/getAlarmList")
    @ResponseBody
    public ServerResponse getAlarmList(@RequestBody RequestParameter requestParameter){
        return ServerResponse.asSucessResponse(alarmCenterService.getAlarmList(requestParameter));
    }

    /**
     * @description 获取数据治理跟踪数据
     * @param requestParameter
     * @return
     */
    @RequestMapping(value = "/getDGFData")
    @ResponseBody
    public ServerResponse getDGFData(@RequestBody RequestParameter requestParameter){
        try {
            logger.info("开始获取治理跟踪数据");
            DataGFReturnMap dataGFReturnMap = alarmCenterService.getDGFData(requestParameter);
            logger.info("开始获取治理跟踪数据");
            return ServerResponse.asSucessResponse(dataGFReturnMap);
        }catch (Exception e){
            logger.error("获取治理跟踪数据报错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取治理跟踪数据报错：" + e.getMessage());
        }
    }

    /**
     * @description 获取环节和治理人员的下拉菜单数据
     * @return
     */
    @RequestMapping(value = "/getSelectList")
    @ResponseBody
    public ServerResponse getSelectList(){
        Map<String,JSONArray> result = new HashMap<String,JSONArray>();
        try {
            logger.info("开始获取环节和治理人员的下拉菜单数据");
            JSONArray linkSelect = alarmCenterService.getDGFSelect("link");
            JSONArray managerSelect = alarmCenterService.getDGFSelect("manager");
            result.put("linkSelect",linkSelect);
            result.put("managerSelect",managerSelect);
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("获取环节和治理人员的下拉菜单数据失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取环节和治理人员的下拉菜单数据失败");
        }
    }

    /**
     * @description 添加治理跟踪数据（添加、编辑按钮）
     * @param dataGFMsg
     * @return
     */
    @RequestMapping(value = "/saveDGFData")
    @ResponseBody
    public ServerResponse saveDGFData(@RequestBody DataGFMsg dataGFMsg){
        try {
            alarmCenterService.saveDGFData(dataGFMsg);
            return ServerResponse.asSucessResponse("保存治理跟踪数据成功");
        }catch (Exception e){
            logger.info("保存治理跟踪数据失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("保存治理跟踪数据失败");
        }
    }

    /**
     * @description 更新治理跟踪数据（数据治理、治理审核小对话框）
     * @param requestParameter
     * @return
     */
    @RequestMapping(value = "/updateConclusion")
    @ResponseBody
    public ServerResponse updateConclusion(@RequestBody RequestParameter requestParameter){
        JSONObject dataGFMsg = null;
        try {
            if (StringUtils.isBlank(requestParameter.getId())){
                return ServerResponse.asErrorResponse("数据主键id为空");
            }
            dataGFMsg = alarmCenterService.updateConclusion(requestParameter);
        }catch (Exception e){
            logger.error("更新治理结论出错：\n" + ExceptionUtil.getExceptionTrace(e));
            ServerResponse.asErrorResponse("更新治理结论出错");
        }
        return ServerResponse.asSucessResponse(dataGFMsg);
    }

    /**
     * @description 删除数据（删除按钮）
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteDFGData")
    @ResponseBody
    public ServerResponse deleteDFGData(@RequestBody JSONObject ids){
        try {
            if (StringUtils.isBlank(ids.getString("ids"))){
                logger.info("Id值为空");
                return ServerResponse.asErrorResponse("Id值为空");
            }
            logger.info("开始删除治理跟踪数据");
            alarmCenterService.deleteDFGData(ids);
            logger.info("删除治理跟踪数据完成");
        }catch (Exception e){
            logger.error("删除治理跟踪数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("删除治理跟踪数据出错");
        }
        return ServerResponse.asSucessResponse("删除治理跟踪数据成功");
    }

    /**
     * @description 治理跟踪 导出按钮
     * @param jsonObject
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportDFGData",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void exportDFGData(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws IOException {
        logger.info("开始导出数据");
        alarmCenterService.exportDFGData(jsonObject,response);
        logger.info("导出数据完成");
    }

}
