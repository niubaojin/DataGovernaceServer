package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.config.CacheManager;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.buildtable.*;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.pojo.warehouse.ProjectInfo;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 建表管理相关接口
 * @author wangdongwei
 * @ClassName ObjectStoreInfoController
 * @description
 * @date 2021/1/28 11:19
 */
@EnableTransactionManagement
@Controller
public class ObjectStoreInfoController {
    private Logger logger = LoggerFactory.getLogger(ObjectStoreInfoController.class);
    @Autowired
    private ObjectStoreInfoService objectStoreInfoServiceImpl;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    CacheManager cacheManager;

    /**
     * 更新 是否自动入库的字段信息
     * @param saveColumnComparision
     * @return
     */
    @RequestMapping(value="/editImportFlagSave",method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> editImportFlagSave(@RequestBody SaveColumnComparision saveColumnComparision){
        try{
            logger.info("修改是否自动入库的参数请求信息："+ JSONObject.toJSONString(saveColumnComparision));
            objectStoreInfoServiceImpl.updateColumnToInfo(saveColumnComparision,2);
            return ServerResponse.asSucessResponse("更新数据成功","更新数据成功");
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("更新数据失败"+e.getMessage());
        }
    }

//    /**
//     *  刷新 已建表信息
//     * @param refreshCreatedPojo
//     * @return
//     */
//    @RequestMapping(value="/refreshCreateTable",method = {RequestMethod.POST})
//    @ResponseBody
//    public ServerResponse<String> refreshCreateTable(@RequestBody RefreshCreatedPojo refreshCreatedPojo){
//        try{
//            logger.info("刷新已建表信息的相关参数为："+ JSONObject.toJSONString(refreshCreatedPojo));
//            if(StringUtils.isNotBlank(refreshCreatedPojo.getTableName())){
//                objectStoreInfoServiceImpl.refreshCreateTable(refreshCreatedPojo);
//            }else{
//                logger.info("刷新所有，异步执行");
//                applicationEventPublisher.publishEvent(refreshCreatedPojo);
//                logger.info("事件发布成功");
//            }
//            return ServerResponse.asSucessResponse("刷新成功","刷新成功");
//        }catch (Exception e){
//            logger.error(ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asErrorResponse("刷新失败："+e.getMessage());
//        }
//    }

    /**
     *  刷新 已建表信息
     * @param refreshCreatedPojo
     * @return
     */
    @RequestMapping(value="/refreshCreateTable",method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> refreshCreateTable(@RequestBody RefreshCreatedPojo refreshCreatedPojo){
        try{
            if (StringUtils.isBlank(refreshCreatedPojo.getDataCenterId()) && StringUtils.isBlank(refreshCreatedPojo.getResId())){
                logger.info("刷新所有，异步执行");
                applicationEventPublisher.publishEvent(refreshCreatedPojo);
                logger.info("事件发布成功");
                return ServerResponse.asSucessResponse("刷新成功","刷新成功");
            }else {
                logger.info("刷新单个项目，同步执行");
                cacheManager.addOrUpdateCache("refreshCreateTableSync", true);
                objectStoreInfoServiceImpl.refreshTableSync(refreshCreatedPojo);
                cacheManager.addOrUpdateCache("refreshCreateTableSync", false);
                return ServerResponse.asSucessResponse("刷新成功","刷新成功");
            }
        }catch (Exception e){
            cacheManager.addOrUpdateCache("refreshCreateTableSync", false);
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("刷新失败："+e.getMessage());
        }
    }

    /**
     *  刷新 已建表信息（单表刷新）
     * @param refreshCreatedPojo
     * @return
     */
    @RequestMapping(value="/refreshCreateTableOneTable",method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> refreshCreateTableOneTable(@RequestBody RefreshCreatedPojo refreshCreatedPojo){
        try{
            logger.info("刷新单个表");
            objectStoreInfoServiceImpl.refreshCreateTableOneTable(refreshCreatedPojo);
            return ServerResponse.asSucessResponse("刷新成功","刷新成功");
        }catch (Exception e){
            logger.error("刷新失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("刷新失败："+e.getMessage());
        }
    }

    @RequestMapping("/getRefreshCreateTableStatus")
    @ResponseBody
    public ServerResponse getRefreshCreateTableStatus() {
        ServerResponse serverResponse;
        try {
            serverResponse = ServerResponse.asSucessResponse(cacheManager.getValue("refreshCreateTableSync"));
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("查询刷新建表信息状态报错：" + e.getMessage());
            logger.error("查询刷新建表信息状态报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询刷新建表信息状态结束===========");
        return serverResponse;
    }

    /**
     * 数据集建表管理页面数据
     * @param objectStoreInfoParameter
     * @return
     */
    @RequestMapping(value = "/searchTableInfo",method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<Map<String,Object>> searchTableInfo(@RequestBody  @Valid ObjectStoreInfoParameter objectStoreInfoParameter,
                                                                 BindingResult bindingResult){
        logger.info("查询建表管理表的请求参数为{}", JSONObject.toJSONString(objectStoreInfoParameter));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Map<String, Object> data = objectStoreInfoServiceImpl.searchTableInfo(objectStoreInfoParameter);
        logger.info("========查询建表管理表结束=========");
        return ServerResponse.asSucessResponse(data);
    }

    /**
     * 通过数据中心id获取数据源信息
     * @param dataCenterId 数据中心id
     * @return
     */
    @RequestMapping(value = "/getDataResource",method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getDataResource(String dataCenterId, String storeType){
        List<PageSelectOneValue> dataResource = objectStoreInfoServiceImpl.getDataResource(dataCenterId, storeType);
        return ServerResponse.asSucessResponse(dataResource);
    }

    /**
     * 根据数据源Id查找项目空间信息
     * @return
     */
    @RequestMapping(value = "/getProjectNameList",method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<String>> getProjectNameList(@RequestParam("resId") String resId) {
        List<String> projectList = objectStoreInfoServiceImpl.getProjectList(resId);
        return ServerResponse.asSucessResponse(projectList);
    }

    /**
     * 获取建表信息管理页面的筛选框
     * @return
     */
    @RequestMapping(value = "/getFilterInfo",method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<BuildTableFilterObject> getFilterInfo(){
        BuildTableFilterObject buildTableFilterObject = objectStoreInfoServiceImpl.getFilterInfo();
        return ServerResponse.asSucessResponse(buildTableFilterObject);
    }

    /**
     * 更新是否输出入库状态
     * @param objectStoreInfo
     * @return
     */
    @RequestMapping(value = "/updateImportFlag")
    @ResponseBody
    public ServerResponse updateImportFlag(@RequestBody ObjectStoreInfo objectStoreInfo){
        logger.info("开始更新是否输出入库状态：" + objectStoreInfo.getImportFlag());
        try {
            objectStoreInfoServiceImpl.updateImportFlag(objectStoreInfo);
        }catch (Exception e){
            logger.error("更新是否输出入库状态失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("更新是否输出入库状态失败");
        }
        return ServerResponse.asSucessResponse("更新输出入库状态成功");
    }

    /**
     * 获取标准数据集数据
     * @return
     */
    @RequestMapping(value = "/getStandardDataSet")
    @ResponseBody
    public ServerResponse getStandardDataSet(){
        logger.info("开始获取标准数据集");
        List<ObjectInfo> objectInfos = new ArrayList<>();
        try {
            objectInfos = objectStoreInfoServiceImpl.getStandardDataSet();
            logger.info("获取标准数据集结束");
        }catch (Exception e){
            logger.error("获取标准数据集失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取标准数据集失败");
        }
        return ServerResponse.asSucessResponse(objectInfos);
    }

    /**
     * 获取物理表数据列表
     * @param resId
     * @param projectName
     * @return
     */
    @RequestMapping(value = "/getDetectedTableList")
    @ResponseBody
    public ServerResponse getDetectedTableList(@RequestParam("resId") String resId, @RequestParam("projectName") String projectName){
        logger.info("开始获取物理表数据列表");
        List<DetectedTable> detectedTables = new ArrayList<>();
        try {
            detectedTables = objectStoreInfoServiceImpl.getDetectedTableList(resId, projectName);
        }catch (Exception e){
            logger.error("获取物理表数据列表失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取物理表数据集失败");
        }
        return ServerResponse.asSucessResponse(detectedTables);
    }

    /**
     * 获取标准表信息
     * @param objectId
     * @return
     */
    @RequestMapping(value = "/getStandardDataItem")
    @ResponseBody
    public ServerResponse getStandardDataItem(@RequestParam("objectId") String objectId){
        List<ObjectStoreFieldInfo> objectStoreFieldInfos = new ArrayList<>();
        logger.info("开始获取标准数据项");
        try {
            objectStoreFieldInfos = objectStoreInfoServiceImpl.getStandardDataItem(objectId);
        }catch (Exception e){
            logger.error("获取标准数据项失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取标准数据项失败");
        }
        return ServerResponse.asSucessResponse(objectStoreFieldInfos);
    }

    @RequestMapping(value = "/getDetectedFieldInfo")
    @ResponseBody
    public ServerResponse getDetectedFieldInfo(@RequestBody ObjectStoreInfo objectStoreInfo){
        logger.info("开始获取仓库字段信息");
        ReturnDetectedFieldInfo returnDetectedFieldInfo = new ReturnDetectedFieldInfo();
        List<FieldInfo> fieldInfos = new ArrayList<>();
        try {
            String tableName = objectStoreInfo.getTableName();
            String projectName = objectStoreInfo.getProjectName();
            String resId = objectStoreInfo.getResId();
            String tableId = objectStoreInfo.getTableId();
            boolean tableInfoId = objectStoreInfoServiceImpl.existTableInfoId(tableName, projectName, resId, tableId);
            returnDetectedFieldInfo.setExistTableInfoId(tableInfoId);
//            if (tableInfoId){
//                returnDetectedFieldInfo.setFieldInfos(fieldInfos);
//                return ServerResponse.asSucessResponse("表：" + tableName + "已经创建", returnDetectedFieldInfo);
//            }
            fieldInfos = objectStoreInfoServiceImpl.getDetectedFieldInfo(resId, projectName, tableName);
            returnDetectedFieldInfo.setFieldInfos(fieldInfos);
        }catch (Exception e){
            logger.error("获取仓库字段信息失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取仓库字段信息失败");
        }
        return ServerResponse.asSucessResponse(returnDetectedFieldInfo);
    }

    @RequestMapping(value = "/saveCreateTableInfo")
    @ResponseBody
    public ServerResponse saveCreateTableInfo(@RequestBody ObjectStoreInfo objectStoreInfo){
        String msg = "";
        logger.info("开始保存建表信息");
        try {
            msg = objectStoreInfoServiceImpl.saveCreateTableInfo(objectStoreInfo);
        }catch (Exception e){
            logger.error("保存建表信息出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("保存建表信息出错");
        }
        return ServerResponse.asSucessResponse(msg);
    }

    @RequestMapping(value = "/getCreateTableInfo")
    @ResponseBody
    public ServerResponse getCreateTableInfo(@RequestBody ObjectStoreInfo objectStoreInfo){
        try {
            objectStoreInfo = objectStoreInfoServiceImpl.getCreateTableInfo(objectStoreInfo);
        }catch (Exception e){
            logger.error("获取建表信息出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取建表信息出错");
        }
        return ServerResponse.asSucessResponse(objectStoreInfo);
    }

    /**
     * 保存建表管理页面的显示字段列表
     * @param showField
     * @return
     */
    @RequestMapping("/updateBuildTableShowField")
    @ResponseBody
    public ServerResponse updateBuildTableShowField(@RequestBody ShowField showField) {
        logger.info("更新建表信息管理页面显示字段的参数为：" + JSONObject.toJSONString(showField));
        try{
            String queryParams = StringUtils.join(showField.getShowField().toArray(),",");
            objectStoreInfoServiceImpl.updateBuildTableShowField(queryParams);
            return ServerResponse.asSucessResponse("显示字段更新成功",queryParams);
        }catch (Exception e){
            logger.error("更新建表信息管理页面显示字段报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("显示字段更新失败：" + e.getMessage());
        }
    }

    @RequestMapping("/getBuildTableShowField")
    @ResponseBody
    public ServerResponse getBuildTableShowField() {
        ServerResponse<List<String>> serverResponse;
        try{
            List<String> resultReturn = objectStoreInfoServiceImpl.getBuildTableShowField();
            if (resultReturn == null){
                serverResponse = ServerResponse.asSucessResponse(new ArrayList<>(0));
            }else {
                serverResponse = ServerResponse.asSucessResponse(resultReturn);
            }
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取显示字段失败：" + e.getMessage());
            logger.error("更新建表信息管理页面显示字段报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    @RequestMapping("/getStoreTypeList")
    @ResponseBody
    public ServerResponse getStoreTypeList(){
        logger.info("开始获取平台类型列表");
        List<PageSelectOneValue> storeTypeList = new ArrayList<>();
        try {
            storeTypeList = objectStoreInfoServiceImpl.getStoreTypeList();
        }catch (Exception e){
            logger.error("获取平台类型列表出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取平台类型列表失败");
        }
        return ServerResponse.asSucessResponse(storeTypeList);
    }

    @RequestMapping("/deleteObjectStore")
    @ResponseBody
    public ServerResponse deleteObjectStore(@RequestBody ObjectStoreInfo objectStoreInfo){
        logger.info("开始删除objectStoreInfo相关操作");
        ServerResponse serverResponse = objectStoreInfoServiceImpl.deleteObjectStore(objectStoreInfo);
        return serverResponse;
    }

}
