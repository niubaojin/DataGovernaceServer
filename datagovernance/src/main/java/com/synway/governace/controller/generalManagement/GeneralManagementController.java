package com.synway.governace.controller.generalManagement;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.entity.dto.DgnCommonSettingDTO;
import com.synway.governace.entity.pojo.DgnCommonSettingEntity;
import com.synway.governace.enums.OperateLogFailReasonEnum;
import com.synway.governace.enums.OperateLogHandleTypeEnum;
import com.synway.governace.pojo.generalManagement.*;
import com.synway.governace.pojo.largeScreen.DataResource;
import com.synway.governace.service.generalManagement.GeneralManagementService;
import com.synway.governace.service.operateLog.OperateLogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nbj
 * @date 2022年4月13日16:11:14
 * @description 原首页导航栏设置功能，现后端整合到一起
 */
@RequestMapping("/configManage/generalManagement")
@Controller
public class GeneralManagementController {
    private static Logger logger = LoggerFactory.getLogger(GeneralManagementController.class);

    @Autowired
    GeneralManagementService generalManagementService;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    /**
     * @description 保存通用配置信息：数据资产、数据质量、告警推送、数据堆积
     * @param setting
     */
    @RequestMapping(value = "/saveOrUpdateGeneralSetting")
    @ResponseBody
    public ServerResponse saveOrUpdateGeneralSetting(@RequestBody DgnCommonSettingDTO setting){
        generalManagementService.saveOrUpdateGeneralSetting(setting);
        return ServerResponse.asSucessResponse(String.format("保存%s配置数据成功",setting.getParentId()));
    }

    /**
     * @description 获取通用配置：数据资产、数据质量、告警推送、数据堆积
     * @param dto
     */
    @RequestMapping(value = "/getGeneralSetting")
    @ResponseBody
    public ServerResponse getGeneralSetting(@RequestBody DgnCommonSettingDTO dto){
        return generalManagementService.getGeneralSetting(dto);
    }

    /**
     * @description 推送设置编辑
     * @param alarmPushSetting
     * @return
     */
    @RequestMapping(value = "/editPushSetting")
    @ResponseBody
    public ServerResponse editPushSetting(@RequestBody AlarmPushSetting alarmPushSetting){
        try {
            ServerResponse serverResponse = generalManagementService.editPushSetting(alarmPushSetting);
            operateLogServiceImpl.updateGeneralSettingSuccessLog(OperateLogHandleTypeEnum.ALTER, "通用配置", "告警推送配置");
            return serverResponse;
        }catch (Exception e){
            logger.error("编辑推送设置出错：", e);
            operateLogServiceImpl.updateGeneralSettingFailLog(OperateLogHandleTypeEnum.ALTER, OperateLogFailReasonEnum.YYXTFM, "通用配置", "告警推送配置");
            return ServerResponse.asErrorResponse("编辑推送设置出错");
        }
    }

    /**
     * @description 推送设置删除
     * @param setting
     * @return
     */
    @RequestMapping(value = "/delPushSetting")
    @ResponseBody
    public ServerResponse delPushSetting(@RequestBody DgnCommonSettingEntity setting){
        try {
            generalManagementService.delPushSetting(setting);
            operateLogServiceImpl.updateGeneralSettingSuccessLog(OperateLogHandleTypeEnum.ALTER, "通用配置", "告警推送配置");
            return ServerResponse.asSucessResponse("删除数据成功");
        }catch (Exception e){
            logger.error("删除推送设置失败：", e);
            operateLogServiceImpl.updateGeneralSettingFailLog(OperateLogHandleTypeEnum.ALTER, OperateLogFailReasonEnum.YYXTFM, "通用配置", "告警推送配置");
            return ServerResponse.asErrorResponse("删除推送设置失败");
        }
    }

    /*保存数据对账告警配置*/
    @RequestMapping(value = "/saveOrUpdateReconciliationAlarmSetting")
    @ResponseBody
    public ServerResponse saveOrUpdateReconciliationAlarmSetting(@RequestBody List<DgnCommonSettingEntity> settings){
        try {
            logger.info("开始保存对账数据");
            generalManagementService.saveOrUpdateReconciliationAlarmSetting(settings);
            logger.info("保存数据对账完成");
            operateLogServiceImpl.updateGeneralSettingSuccessLog(OperateLogHandleTypeEnum.ALTER, "通用配置", "数据对账配置");
            return ServerResponse.asSucessResponse("保存数据对账告警配置成功");
        }catch (Exception e){
            logger.error("保存数据对账告警配置出错：", e);
            operateLogServiceImpl.updateGeneralSettingFailLog(OperateLogHandleTypeEnum.ALTER, OperateLogFailReasonEnum.YYXTFM, "通用配置", "数据对账配置");
            return ServerResponse.asErrorResponse("保存数据对账告警配置出错");
        }
    }

    /*获取数据对账告警配置*/
    @RequestMapping(value = "/getReconciliationAlarmSetting")
    @ResponseBody
    public ServerResponse getReconciliationAlarmSetting(@RequestBody DgnCommonSettingEntity setting){
        try {
            BillAlarmSetting billAlarmSetting = generalManagementService.getReconciliationAlarmSetting(setting);
            return ServerResponse.asSucessResponse(billAlarmSetting);
        }catch (Exception e){
            logger.error("获取数据对账告警配置出错：", e);
            return ServerResponse.asErrorResponse("获取数据对账告警配置出错");
        }
    }

    /*获取数据中心列表*/
    @RequestMapping(value = "/getDataCenter")
    @ResponseBody
    public ServerResponse<List<FontOption>> getDataCenter() {
        try {
            logger.info("开始获取数据中心列表");
            List<FontOption> allDataCenter = generalManagementService.getAllDataCenter();
            return ServerResponse.asSucessResponse(allDataCenter);
        } catch (Exception e) {
            logger.error("获取数据中心列表出错：", e);
            return null;
        }
    }

    /*根据数据中心id及数据源类型，获取数据源列表*/
    @RequestMapping(value = "/getDataResourceByDataCenterIdAndType")
    @ResponseBody
    public ServerResponse<List<FontOption>> getDataResourceByDataCenterIdAndType(@RequestParam("centerId") String centerId, @RequestParam("resType") String resType){
        try {
            logger.info("根据数据中心id及数据源类型，获取数据源列表");
            List<FontOption> dataResources = generalManagementService.getDataResourceByDataCenterIdAndType(centerId, resType);
            return ServerResponse.asSucessResponse(dataResources);
        } catch (Exception e) {
            logger.error("获取数据源列表出错：", e);
            return null;
        }
    }

    /*根据数据源id，获取数据源信息*/
    @RequestMapping(value = "/getDataResourceById")
    @ResponseBody
    public ServerResponse<DataResource> getDataResourceById(@RequestParam("resId") String resId){
        try {
            logger.info("根据数据中心id及数据源类型，获取数据源信息");
            DataResource dataResource = generalManagementService.getDataResourceById(resId);
            return ServerResponse.asSucessResponse(dataResource);
        } catch (Exception e) {
            logger.error("根据数据中心id及数据源类型，获取数据源信息出错：", e);
            return null;
        }
    }

    /*根据数据源id获取项目名列表（主题）*/
    @RequestMapping(value = "/getProjectList")
    @ResponseBody
    public ServerResponse<List<FontOption>> getProjectList(@RequestParam("resId") String resId){
        try {
            logger.info("根据数据源id获取项目名列表");
            List<FontOption> projects = generalManagementService.getProjectList(resId);
            return ServerResponse.asSucessResponse(projects);
        }catch (Exception e){
            logger.error("根据数据源id获取项目名列表出错：", e);
            return ServerResponse.asErrorResponse("根据数据源id获取项目名列表出错");
        }
    }

}
