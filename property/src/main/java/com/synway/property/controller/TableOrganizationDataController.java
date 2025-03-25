package com.synway.property.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.UrlConstants;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.*;
import com.synway.property.pojo.datastoragemonitor.DataResourceTable;
import com.synway.property.pojo.formorganizationindex.ReceiveTag;
import com.synway.property.pojo.formorganizationindex.TableOrganizationShowField;
import com.synway.property.service.DataStorageMonitorIndexService;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.HttpUtil;
import com.synway.common.bean.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * 根据分级分类的相关信息，获取表组织资产的相关数据
 * @author majia
 */
@Controller
@RequestMapping("/dataOrganizationMonitoring/ClassifyInterface")
public class TableOrganizationDataController {
    private static Logger logger = LoggerFactory.getLogger(TableOrganizationDataController.class);
    @Autowired
    DataStorageMonitorIndexService dataStorageMonitorIndexServiceImpl;
    //编写表数据组织详细页面中三个选择框

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据选择的数据资源目录的三大分类之一获取对应的一级分类信息
     * 2020/2/21 只有数据组织分类，数据来源分类已并入原始库下 update by majia
     * @param mainClassify 三个主分类的英文信息 dataOrganizationClassify/dataSourceClassify/dataResourceClassify
     * @return 对应的所有一级分类信息  null：查询失败
     */
    @RequestMapping("/getPrimaryClassifyData")
    @ResponseBody
    public  ServerResponse<List<PageSelectOneValue>>  getPrimaryClassifyData(@RequestParam("mainClassify")String  mainClassify){
        logger.info("开始查询【"+mainClassify+"】对应的一级分类信息");
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        if("null".equalsIgnoreCase(mainClassify)){
            List<PageSelectOneValue> pageSelectOneValueList = new ArrayList<>();
            pageSelectOneValueList.add(new PageSelectOneValue("null","未知"));
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValueList);
            return serverResponse;
        }else if(StringUtils.isBlank(mainClassify)){
            serverResponse = ServerResponse.asErrorResponse("查询的参数条件【"+mainClassify+"】为空，不能进行查询");
            logger.error("查询的参数条件【"+mainClassify+"】为空，不能进行查询");
            logger.info("getPrimaryClassifyData()方法运行结束，返回结果为"+JSON.toJSONString(serverResponse));
            return serverResponse;
        }else{
            serverResponse = dataStorageMonitorIndexServiceImpl.getPrimaryClassifyData(mainClassify);
            logger.info("getPrimaryClassifyData()方法运行结束，返回结果为"+JSON.toJSONString(serverResponse));
            return serverResponse;
        }
    }

    /**
     * 根据选择的数据资源目录的三大分类之一 和 一级分类信息 来获取二级分类信息
     * 2020/2/21 一级分类标识改变，获取二级分类改变 update by majia
     * @param mainClassify 三个主分类的英文信息 dataOrganizationClassify/dataSourceClassify/dataResourceClassify
     * @param primaryClassifyCode 一级分类的代码值
     * @return 对应的所有一级分类信息
     */
    @RequestMapping("/getSecondaryClassifyData")
    @ResponseBody
    public  ServerResponse<List<PageSelectOneValue>> getSecondaryClassifyData(@RequestParam("mainClassify")String  mainClassify,
                                                                              @RequestParam("primaryClassifyCode")String  primaryClassifyCode,
                                                                              @RequestParam("isThreeLevel")String isThreeLevel){
        logger.info("开始查询主类别为【"+mainClassify+"】和一级分类为【"+primaryClassifyCode+"】对应的二级分类信息");
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        if("null".equalsIgnoreCase(mainClassify) && "null".equalsIgnoreCase(primaryClassifyCode)){
            List<PageSelectOneValue> pageSelectOneValueList = new ArrayList<>();
            pageSelectOneValueList.add(new PageSelectOneValue("null","未知"));
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValueList);
            return serverResponse;
        }
        if(StringUtils.isBlank(mainClassify)){
            logger.error("查询的参数条件 mainClassify【"+mainClassify+"】为空，不能进行查询");
            serverResponse = ServerResponse.asErrorResponse("查询的参数条件 mainClassify【"+mainClassify+"】为空，不能进行查询");
            logger.info("getSecondaryClassifyData()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
            return serverResponse;
        }if(StringUtils.isBlank(primaryClassifyCode)){
            logger.error("查询的参数条件primaryClassifyCode【"+primaryClassifyCode+"】为空，不能进行查询");
            List<PageSelectOneValue> oneResultList = new ArrayList<>();
            oneResultList.add(new PageSelectOneValue("",""));
            serverResponse = ServerResponse.asSucessResponse(oneResultList);
            logger.info("getSecondaryClassifyData()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
            return serverResponse;
        } else{
            serverResponse = dataStorageMonitorIndexServiceImpl.getSecondaryClassifyData(mainClassify ,primaryClassifyCode, isThreeLevel);
            logger.info("getSecondaryClassifyData()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
            return serverResponse;
        }
    }


    /**
     * 根据分级分类的一级和二级的相关信息查询表组织资产的汇总信息
     * 后端不分页，使用前端分页
     * @param mainClassify   主分类英文名称
     * @param primaryClassifyCh  一级分类名称(中文)  为空表示查询所有
     * @param secondaryClassifyCh 二级分类名称(中文)  为空表示查询所有
     * @return  {"data":null,"errorMessage":"","status":"400"}
     *
     */
    @RequestMapping("/getSummaryTableByClassify")
    @ResponseBody
    public ServerResponse<List<SummaryTableByClassify>> getSummaryTableByClassify(@RequestParam("mainClassify")String  mainClassify,
                                                                                  @RequestParam("primaryClassifyCh")String  primaryClassifyCh,
                                                                                  @RequestParam("secondaryClassifyCh")String  secondaryClassifyCh,
                                                                                  @RequestParam("threeValue")String threeValue){
        logger.info(String.format("开始查询表组织资产的汇总信息，查询条件为 mainClassify：%s ,primaryClassifyCh: %s ,secondaryClassifyCh: %s"
                ,mainClassify,primaryClassifyCh,secondaryClassifyCh));
        // 返回的接口信息
        ServerResponse<List<SummaryTableByClassify>> serverResponse;
        if(!(mainClassify.equalsIgnoreCase(UrlConstants.DATA_ORGANIZATION_CLASSIFY)
                || mainClassify.equalsIgnoreCase(UrlConstants.DATA_SOURCE_CLASSIFY)
                   || mainClassify.equalsIgnoreCase(UrlConstants.DATA_RESOURCE_CLASSIFY)
                     || "null".equalsIgnoreCase(mainClassify))){
            String ss = UrlConstants.DATA_ORGANIZATION_CLASSIFY+"|"+
                    UrlConstants.DATA_SOURCE_CLASSIFY+"|"+
                    UrlConstants.DATA_RESOURCE_CLASSIFY;
            String errorMessage = "传入的参数mainClassify值【"+mainClassify+"】不在规定的数值【"+ss+"】中";
            serverResponse = ServerResponse.asErrorResponse(errorMessage);
            logger.info("getSummaryTableByClassify()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
            logger.info("============================================");
            return serverResponse;
        }
        if(StringUtils.isBlank(primaryClassifyCh)){
            primaryClassifyCh = "";
        }
        if(StringUtils.isBlank(secondaryClassifyCh)){
            secondaryClassifyCh = "";
        }
        // 在后台查询具体的数值
        serverResponse = dataStorageMonitorIndexServiceImpl.getSummaryTableByClassify(mainClassify,primaryClassifyCh,secondaryClassifyCh, threeValue);
        logger.info("getSummaryTableByClassify()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
        logger.info("============================================");
        return serverResponse;
    }

    /**
     * 注册前审批
     * @return
     */
    @RequestMapping(value = "/register",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse register(HttpServletRequest request) {
        JSONObject jsonObject = HttpUtil.getJSONparam(request);
        JSONArray TABLEJson = jsonObject.getJSONArray("TABLE");
        JSONArray TABLEIDJson = jsonObject.getJSONArray("TABLEID");
        String userName = jsonObject.getObject("userName",String.class);
        String userId = jsonObject.getObject("userId",String.class);
        String organId = jsonObject.getObject("organId",String.class);
        JSONObject[] tableList = Arrays.stream(TABLEJson.toArray()).toArray(JSONObject[]::new);
        String[] tableIdList = Arrays.stream(TABLEIDJson.toArray()).toArray(String[]::new);
        // 返回的接口信息
        ServerResponse serverResponse = null;
        logger.info(String.format("开始提交审批，条件为 tableIdList：%s "
                , Arrays.toString(tableIdList)));

        userId = String.valueOf(AuthorizedUserUtils.getInstance().getAuthor().getUserId());
        try{
            serverResponse = dataStorageMonitorIndexServiceImpl.registerApproval(jsonObject.toJSONString(),tableIdList,tableList,userName,userId,organId);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("审批申请失败");
        }
        logger.info("register()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
        logger.info("============================================");
        return serverResponse;
    }

    /**
     * 更新生命周期
     * @param requestParam
     * @return
     */
    @RequestMapping("/updateLifeCycle")
    @ResponseBody
    public ServerResponse updateLifeCycle(@RequestBody RequestParameter requestParam){
        ServerResponse serverResponse = null;
        logger.info(String.format("开始提交更新生命周期，参数为%s "
                , requestParam.toString()));
        try{
            serverResponse = dataStorageMonitorIndexServiceImpl.updateLifeCycleApproval(requestParam);
        }catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("提交生命周期修改失败");
        }
        return serverResponse;
    }


    @RequestMapping("/updateApprovalStatus")
    @ResponseBody
    public ServerResponse updateApprovalStatus(@RequestBody RequestParameter requestParam) {
        ServerResponse serverResponse;
        try{
            serverResponse = dataStorageMonitorIndexServiceImpl.updateApprovalStatus(requestParam.getDataApprovals());
        }catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("更新审批状态失败");
        }
        return serverResponse;
    }

    @RequestMapping("/getApprovalStatus")
    @ResponseBody
    public ServerResponse getApprovalStatus() {
        ServerResponse serverResponse = null;
        try{
            serverResponse = restTemplate.getForObject(
                    UrlConstants.DATAGOVERNANCE_BASEURL+"/datagovernance/navbar/getNavStatusByName?name=审批中心",
                    ServerResponse.class);
            return serverResponse;
        }catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse( "获取审批状态失败");
        }
    }

    @RequestMapping("/updateLifeCycleStatus")
    @ResponseBody
    public ServerResponse updateLifeCycleStatus(@RequestBody RequestParameter requestParam){
        logger.info(String.format("开始更新生命周期，参数为%s "
                , requestParam.toString()));
        // 返回的接口信息
        ServerResponse serverResponse;
        try{
            serverResponse = dataStorageMonitorIndexServiceImpl.updateLifeCycleStatus(requestParam);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("更新存储周期失败");
        }
        logger.info("updateRegisterState()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
        logger.info("============================================");
        return serverResponse;
    }

    /**
     * 去数据资源平台注册
     * 注册完后落库到本地数据库
     * @return
     */
    @RequestMapping(value = "/updateRegisterState",produces="application/json;charset=UTF-8")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<String>> updateRegisterState(HttpServletRequest request) {
        JSONObject jsonObject = HttpUtil.getJSONparam(request);
        JSONArray TABLEJson = jsonObject.getJSONArray("TABLE");
        JSONArray TABLEIDJson = jsonObject.getJSONArray("TABLEID");
        String status = jsonObject.getString("status");
        JSONObject[] tableList = Arrays.stream(TABLEJson.toArray()).toArray(JSONObject[]::new);
        String[] tableIdList = Arrays.stream(TABLEIDJson.toArray()).toArray(String[]::new);
        String userId = jsonObject.getString("userId");
        logger.info(String.format("开始注册，注册条件为 tableIdList：%s "
                , Arrays.toString(tableIdList)));
        // 返回的接口信息
        ServerResponse<List<String>> serverResponse = null;
        serverResponse = dataStorageMonitorIndexServiceImpl.updateRegisterState(tableIdList,tableList,status,userId);
        logger.info("updateRegisterState()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
        logger.info("============================================");
        return serverResponse;
    }




    /**
     * 刷新字段信息
     */
    @RequestMapping(value = "/refreshSourceData",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse refreshSourceData(HttpServletRequest request){
        JSONObject jsonObject = HttpUtil.getJSONparam(request);
        JSONArray TABLEIDJson = jsonObject.getJSONArray("TABLEID");
        String[] tableIdList = Arrays.stream(TABLEIDJson.toArray()).toArray(String[]::new);
        logger.info(String.format("开始更新注册状态，更新条件为 tableIdList：%s "
                , Arrays.toString(tableIdList)));
        // 返回的接口信息
        ServerResponse<List<String>> serverResponse = null;
        serverResponse = dataStorageMonitorIndexServiceImpl.refreshSourceData(tableIdList);
        logger.info("refreshSourceData()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
        logger.info("============================================");
        return serverResponse;
    }

    /**
     * 更改启用状态
     */
    @RequestMapping(value = "/updateObjectState",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse updateObjectState(HttpServletRequest request){
        JSONObject jsonObject = HttpUtil.getJSONparam(request);
        JSONArray TABLEIDJson = jsonObject.getJSONArray("TABLEID");
        List<String> tableIdList = JSONObject.parseArray(TABLEIDJson.toJSONString(),String.class);
        String state = jsonObject.getString("STATE");
        logger.info(String.format("开始更改启用状态，更新条件为 tableIdList：%s "
                ,tableIdList.toString()));
        // 返回的接口信息
        ServerResponse<List<String>> serverResponse = null;
        serverResponse = dataStorageMonitorIndexServiceImpl.updateObjectState(tableIdList,state);
        logger.info("refreshSourceData()方法运行结束，返回结果为\n"+JSON.toJSONString(serverResponse));
        logger.info("============================================");
        return serverResponse;
    }

    //导出资产管理列表
    @RequestMapping(value="/exportOrganizationList" ,produces="application/json;charset=utf-8")
    @ResponseBody
    public void exportOrganizationList(HttpServletResponse response, @RequestBody RequestParameter requestParameter
//                                       @RequestParam("type") String type,
//                                       @RequestParam("mainClassify")String  mainClassify,
//                                       @RequestParam("primaryClassifyCh")String  primaryClassifyCh,
//                                       @RequestParam("secondaryClassifyCh")String  secondaryClassifyCh,
//                                       @RequestParam("isThreeLevel")String isThreeLevel
    ) {
        try{
            String type = requestParameter.getType();
            String incrementDays = requestParameter.getIncrementDays();
            String mainClassify = requestParameter.getMainClassify();
            String primaryClassifyCh = requestParameter.getPrimaryClassifyCh();
            String secondaryClassifyCh = requestParameter.getSecondaryClassifyCh();
            String threeValue = requestParameter.getThreeValue();
            dataStorageMonitorIndexServiceImpl.exportOrganizationList(response, type, incrementDays, mainClassify, primaryClassifyCh, secondaryClassifyCh, threeValue);
        }catch(Exception e){
            logger.info("导出资产管理列表异常"+e);
        }
    }

    /**
     * 导出表资产内容
     */
    @RequestMapping(value = "/exportDataContent",produces="application/json;charset=UTF-8")
    @ResponseBody
    public void exportDataContent(@RequestBody RequestParameter requestParameter,HttpServletResponse response
    ){
        List<ReceiveTag> classifyTagsList = requestParameter.getClassifyTags();
        String input = requestParameter.getInput();
        String type = requestParameter.getType();
        List<ReceiveTag> registerTagsList = requestParameter.getRegisterTags();
        List<ReceiveTag> storageTagsList = requestParameter.getStorageTags();
//        List<ReceiveTag> labelTagsList = requestParameter.getLabelTags();
        List<ReceiveTag> usingTagsList = requestParameter.getUsingTags();
        List<String> termSetting = requestParameter.getTermSetting();
        List<String> lastModifiedTimeList = requestParameter.getLastModifiedTime();
        Long startRecordNum = requestParameter.getStartRecordNum();
        Long endRecordNum = requestParameter.getEndRecordNum();
        Long startStorageSize = requestParameter.getStartStorageSize();
        Long endStorageSize = requestParameter.getEndStorageSize();
        DataResourceTable queryTable = requestParameter.getQueryTable();
        dataStorageMonitorIndexServiceImpl.exportDataContent(classifyTagsList,input,registerTagsList,storageTagsList,usingTagsList,type,
                termSetting,lastModifiedTimeList,startRecordNum,endRecordNum,startStorageSize,endStorageSize,queryTable
                ,response
//                ,labelTagsList

        );

        logger.info("exportDataContent()方法运行结束");
        logger.info("============================================");
    }

    /**
     * 获取对应存储位置的项目名
     */
    @RequestMapping("/getStorageLocationSecondaryClassifyData")
    @ResponseBody
    public ServerResponse getStorageLocationSecondaryClassifyData(@RequestParam("storageLocation")String  storageLocation){
        ServerResponse serverResponse = dataStorageMonitorIndexServiceImpl.getProjectNameByType(storageLocation);
        return serverResponse;
    }

    /**
     * 获取对应级别的标签
     */
//    @RequestMapping("/getDataLabelSecondaryClassifyData")
//    @ResponseBody
//    public ServerResponse getDataLabelSecondaryClassifyData(@RequestParam("label")String  label){
//        ServerResponse serverResponse = dataStorageMonitorIndexServiceImpl.getLabelsByType(label);
//        return serverResponse;
//    }

    /**
     * 获取对应存储位置的数量
     */
    @RequestMapping("/getStorageNum")
    @ResponseBody
    public ServerResponse getStorageNum(){
        ServerResponse serverResponse = dataStorageMonitorIndexServiceImpl.getStorageNum();
        return serverResponse;
    }

    /**
     * 获取对应存储位置的数量
     */
    @RequestMapping("/getLabelNum")
    @ResponseBody
    public ServerResponse getLabelNum(){
        ServerResponse serverResponse = dataStorageMonitorIndexServiceImpl.getLabelNum();
        return serverResponse;
    }

    /**
     * 获取对应存储位置的数量
     */
    @RequestMapping("/getSourceStateNum")
    @ResponseBody
    public ServerResponse getSourceStateNum(){
        ServerResponse serverResponse = dataStorageMonitorIndexServiceImpl.getSourceStateNum();
        return serverResponse;
    }

    /**
     * 获取表类型
     */
    @RequestMapping("/getTableType")
    @ResponseBody
    public ServerResponse getTableType(){
        ServerResponse serverResponse = dataStorageMonitorIndexServiceImpl.getTableType();
        return serverResponse;
    }

    /**
     * 获取表内容
     */
    @RequestMapping(value = "/getDataContent")
    @ResponseBody
    public ServerResponse getDataContent(@RequestBody RequestParameter requestParameter){
        ServerResponse<DetailedTableResultMap> serverResponse = dataStorageMonitorIndexServiceImpl.getDataContent(requestParameter);
        logger.info("getDataContent()方法运行结束");
        return serverResponse;
    }

    @RequestMapping(value = "/getDetectId")
    @ResponseBody
    public ServerResponse getDetectId(@RequestBody DetailedTableByClassify detailedTableByClassify){
        ServerResponse<String> serverResponse = dataStorageMonitorIndexServiceImpl.getDetectId(detailedTableByClassify);
        return serverResponse;
    }

    @RequestMapping("/saveTableOrganizationShowField")
    @ResponseBody
    public ServerResponse saveTableOrganizationShowField(@RequestBody TableOrganizationShowField tableOrganizationShowField) {
        logger.info("更新表组织页面显示字段的参数为：" + JSONObject.toJSONString(tableOrganizationShowField));
        try{
            String queryParams = StringUtils.join(tableOrganizationShowField.getShowFieldList().toArray(),",");
            dataStorageMonitorIndexServiceImpl.updateTableOrganizationShowField(queryParams);
            return ServerResponse.asSucessResponse("显示字段更新成功",queryParams);
        }catch (Exception e){
            logger.error("更新表组织页面显示字段报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("显示字段更新失败：" + e.getMessage());
        }
    }

    @RequestMapping("/getTableOrganizationShowField")
    @ResponseBody
    public ServerResponse getTableOrganizationShowField() {
        ServerResponse<List<String>> serverResponse;
        try{
            List<String> resultReturn = dataStorageMonitorIndexServiceImpl.getTableOrganizationShowField();
            if (resultReturn == null){
                serverResponse = ServerResponse.asSucessResponse(new ArrayList<>(0));
            }else {
                serverResponse = ServerResponse.asSucessResponse(resultReturn);
            }
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取显示字段失败：" + e.getMessage());
            logger.error("更新表组织页面显示字段报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

}
