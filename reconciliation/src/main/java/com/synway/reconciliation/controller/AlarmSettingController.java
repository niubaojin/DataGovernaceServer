package com.synway.reconciliation.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.reconciliation.pojo.AlarmPhoneNumber;
import com.synway.reconciliation.pojo.BillAlarmSetting;
import com.synway.reconciliation.pojo.GetListRequest;
import com.synway.reconciliation.service.AlarmSettingService;
import com.synway.reconciliation.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常告警设置controller
 * @author ywj
 */
@RestController
@RequestMapping("/reconciliation")
public class AlarmSettingController {

    private static Logger logger = LoggerFactory.getLogger(AlarmSettingController.class);

    @Autowired
    AlarmSettingService service;

    /**
     * 数据对账异常告警规则设置
     *
     * @param settingJsonStr 设置信息json字符串
     * @return com.synway.reconciliation.common.ServerResponse<java.lang.String>
     */
    @RequestMapping("/saveOrUpdateReconciliationAlarmSetting")
    @ResponseBody
    public ServerResponse<String> saveOrUpdateRelationCompletion(@RequestBody String settingJsonStr) {
        try {
            JSONObject jsonObject = JSON.parseObject(settingJsonStr);
            if (null != jsonObject) {
                String setting = jsonObject.getString("settingJsonStr");
                String settingId = service.saveOrUpdateReconciliationAlarmSetting(setting);
                return ServerResponse.asSucessResponse(settingId);
            }
            return ServerResponse.asErrorResponse();
        } catch (Exception e) {
            logger.info("新增/编辑数据对账异常告警信息失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("新增/编辑数据对账异常告警信息出错");
        }
    }

    /**
     * 数据对账异常告警规则详情获取
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<com.synway.reconciliation.pojo.BillAlarmSetting>
     */
    @RequestMapping("/getReconciliationAlarmSetting")
    @ResponseBody
    public ServerResponse<BillAlarmSetting> getReconciliationAlarmSetting(@RequestBody GetListRequest getListRequest) {
        try {
            BillAlarmSetting setting = service.getReconciliationAlarmSetting(getListRequest.getParentId());
            return ServerResponse.asSucessResponse(setting);
        } catch (Exception e) {
            logger.info("获取数据对账异常告警信息失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据对账异常告警信息出错");
        }
    }

    /**
     * 告警手机号码列表获取
     * @param getListRequest 查询条件
     * @return com.synway.reconciliation.common.ServerResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/getAlarmPhoneNumberList")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getAlarmPhoneNumberList(@RequestBody GetListRequest getListRequest){
        try{
            Map<String,Object> map = new HashMap<>();
            PageInfo pageInfo = service.getAlarmPhoneNumberList(getListRequest);
            map.put("total", pageInfo.getTotal());
            map.put("rows", pageInfo.getList());
            return ServerResponse.asSucessResponse(map);
        }catch (Exception e){
            logger.info("告警手机号码列表获取失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("告警手机号码列表获取出错");
        }
    }

    /**
     * 新增/编辑告警手机号码
     *
     * @param alarmPhoneNumber 手机号码信息
     * @return com.synway.reconciliation.common.ServerResponse<java.lang.String>
     */
    @RequestMapping("/saveOrUpdateAlarmPhoneNumber")
    @ResponseBody
    public ServerResponse<String> saveOrUpdateAlarmPhoneNumber(@RequestBody AlarmPhoneNumber alarmPhoneNumber) {
        try {
            String numberId = service.saveOrUpdateAlarmPhoneNumber(alarmPhoneNumber);
            return ServerResponse.asSucessResponse("告警手机号码保存成功", numberId);
        } catch (Exception e) {
            logger.info("新增/编辑告警手机号码失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("新增/编辑数告警手机号码出错");
        }
    }

    /**
     * 删除告警手机号码
     *
     * @param ids 手机号码ID集合
     * @return com.synway.reconciliation.common.ServerResponse<java.lang.String>
     */
    @RequestMapping("/deleteAlarmPhoneNumber")
    @ResponseBody
    public ServerResponse<String> deleteAlarmPhoneNumber(@RequestBody String ids) {
        try {
            JSONObject obj = JSONObject.parseObject(ids);
            String deleteIds = obj.getString("ids");
            int line = service.deleteAlarmPhoneNumber(deleteIds);
            if (line > 0) {
                return ServerResponse.asSucessResponse();
            } else {
                return ServerResponse.asErrorResponse();
            }
        } catch (Exception e) {
            logger.info("删除告警手机号码失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("删除数告警手机号码出错");
        }
    }
}
