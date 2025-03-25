package com.synway.property.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.UrlConstants;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.pojo.DataQualitySummary;
import com.synway.property.pojo.GetTreeReq;
import com.synway.property.pojo.NumStorageByPrimaryClassify;
import com.synway.property.pojo.TreeNode;
import com.synway.property.pojo.datastoragemonitor.NeedAddRealTimeTable;
import com.synway.property.pojo.datastoragemonitor.RealTimeTableFullMessage;
import com.synway.property.pojo.homepage.DataBaseState;
import com.synway.property.service.DataStorageMonitorIndexService;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.HttpUtil;
import com.synway.common.bean.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页的相关代码
 * @author majia
 */
@Controller
@RequestMapping("/dataStorageMonitoring")
public class DataStorageMonitoringController {
    private static Logger logger = LoggerFactory.getLogger(DataStorageMonitoringController.class);
    @Autowired
    DataStorageMonitorIndexService dataStorageMonitorIndexServiceImpl;
    @Autowired
    DataStorageMonitorDao dataStorageMonitorDao;

    @RequestMapping("/getDataBaseStatus")
    @ResponseBody
    public ServerResponse<List<DataBaseState>> getDataBaseStatus(@RequestParam("platFormType")String platFormType) {
        logger.info("查询数据库的汇总信息");
        ServerResponse<List<DataBaseState>> serverResponse;
        serverResponse = dataStorageMonitorIndexServiceImpl.getDataBaseStatus(platFormType);
        logger.info("getDataBaseStatus()查询结束，结果为\n" + JSON.toJSONString(serverResponse));
        logger.info("===================================================");
        return serverResponse;
    }

    /**
     * 数据资源报表右上角的汇总信息
     *
     * @return
     */
    @RequestMapping("/getDataQualitySummary")
    @ResponseBody
    public ServerResponse<DataQualitySummary> getDataQualitySummary() {
        ServerResponse<DataQualitySummary> serverResponse = null;
        serverResponse = dataStorageMonitorIndexServiceImpl.getDataQualitySummary();
        logger.info("getDataQualitySummary()查询结束，结果为\n" + JSON.toJSONString(serverResponse));
        logger.info("===================================================");
        return serverResponse;
    }

    /**
     * 获取需要监控的表名，从陈开伟编写的大数据中心表结构获取 目前
     *
     * @param dataBaseType ADS
     * @param tableName    表名，模糊查询使用
     * @param monitorType  已监控/未监控
     * @return
     */
    @RequestMapping("/getNeedAddRealTimeTable")
    @ResponseBody
    public ServerResponse<List<NeedAddRealTimeTable>> getNeedAddRealTimeTable(@RequestParam("dataBaseType") String dataBaseType,
                                                                              @RequestParam("tableName") String tableName,
                                                                              @RequestParam("monitorType") String monitorType,
                                                                              @RequestParam("dataCenterId") String dataCenterId,
                                                                              @RequestParam("dataCenterName") String dataCenterName,
                                                                              @RequestParam("dataSourceId") String dataSourceId,
                                                                              @RequestParam("dataSourceName") String dataSourceName) {
        logger.info("数据库类别为【" + dataBaseType + "】,查询数据源id为【" + dataSourceId + "】，查询监控类型【" + monitorType + "】");
        ServerResponse<List<NeedAddRealTimeTable>> serverResponse = null;
        if (StringUtils.isBlank(dataBaseType)) {
            logger.info("数据库类别存在空值，不进行查询！！！");
            serverResponse = ServerResponse.asErrorResponse("数据库类别存在空值，不进行查询！！！");
            return serverResponse;
        }
        if (!"ADS".equalsIgnoreCase(dataBaseType) && !"HBASE".equalsIgnoreCase(dataBaseType)) {
            logger.info("数据库类别错误");
            serverResponse = ServerResponse.asErrorResponse("数据库类别错误");
            return serverResponse;
        }
        try {
            serverResponse =
                    dataStorageMonitorIndexServiceImpl.getNeedAddRealTimeTable(monitorType,dataSourceId,tableName,dataCenterId,dataBaseType);
        } catch (Exception e) {
            logger.error("获取需要监控的表名报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取需要监控的表名报错");
        }
        return serverResponse;
    }


    /**
     * 获取指定数据库类型下的所有项目名
     *
     * @param dataBaseType 数据库类型  ads
     * @param monitorType  已监控/未监控
     * @return
     */
    @RequestMapping("/getProjectName")
    @ResponseBody
    public ServerResponse<List<String>> getProjectName(@RequestParam("dataBaseType") String dataBaseType,
                                                       @RequestParam("monitorType") String monitorType) {
        ServerResponse<List<String>> serverResponse = null;
        if ("ADS".equalsIgnoreCase(dataBaseType)) {
            serverResponse = dataStorageMonitorIndexServiceImpl.getProjectNameByRestFul(dataBaseType, monitorType);
        } else {
            serverResponse = ServerResponse.asErrorResponse("查询的数据库类型是" + dataBaseType + ",目前只支持ADS");
        }
        return serverResponse;
    }


    /**
     * 点击添加按钮后向数据库中添加选定的表名
     */
    @RequestMapping(value = "/addNeedTables", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse<String> addNeedTables(HttpServletRequest request) {
        ServerResponse<String> message = null;
        try {
            JSONObject requeryJson = HttpUtil.getJSONparam(request);
            JSONArray paramJson = requeryJson.getJSONArray("dataList");
            String monitorType = requeryJson.getString("monitorType");
            logger.info("实时表监控要插入的数据为：" + JSONObject.toJSONString(paramJson) + "," + JSONObject.toJSONString(monitorType));
            List<NeedAddRealTimeTable> allNeedAddRealTimeTableList = JSON.parseArray(paramJson.toJSONString(), NeedAddRealTimeTable.class);
            message = dataStorageMonitorIndexServiceImpl.insertOracleAddTable(allNeedAddRealTimeTableList, monitorType);
        } catch (Exception e) {
            logger.error("添加选定的表名报错" + ExceptionUtil.getExceptionTrace(e));
            message = ServerResponse.asErrorResponse("添加选定的表名报错");
        }
        return message;
    }


    /**
     * 展示实时表数据今日的记录数和表记录总数
     */
    @RequestMapping("/showRealTableFull")
    @ResponseBody
    public ServerResponse<List<RealTimeTableFullMessage>> showRealTableFull() {
        ServerResponse<List<RealTimeTableFullMessage>> serverResponse;
        serverResponse = dataStorageMonitorIndexServiceImpl.getAllRealTimeTableFullMessage();
        logger.info("查询showRealTableFull()方法结束，结果为" + JSON.toJSONString(serverResponse));
        logger.info("=========================================================");
        return serverResponse;
    }


    // ----------------------  首页中按照一级来源（数据组织，数据资源来源，数据资源分类）区分的三种柱状图，查询总记录数和总存储数

    /**
     * 获取按照一级分类（数据组织）查询的记录数/存储数
     */
    @RequestMapping("/getRecordsStorageByDataorganization")
    @ResponseBody
    public ServerResponse<NumStorageByPrimaryClassify> getRecordsStorageByDataorganization() {
        ServerResponse<NumStorageByPrimaryClassify> serverResponse = null;
        serverResponse = dataStorageMonitorIndexServiceImpl.getRecordsStorageByPrimaryClassify(
                UrlConstants.DATA_ORGANIZATION_CLASSIFY);
        logger.info("查询到的数据为" + JSONObject.toJSONString(serverResponse));
        return serverResponse;
    }

    /**
     * 获取按照一级分类（数据来源分类）查询的记录数/存储数
     */
    @RequestMapping("/getRecordsStorageByDataSource")
    @ResponseBody
    public ServerResponse<NumStorageByPrimaryClassify> getRecordsStorageByDataSource() {
        ServerResponse<NumStorageByPrimaryClassify> serverResponse = null;
        serverResponse = dataStorageMonitorIndexServiceImpl.getRecordsStorageByPrimaryClassify(
                UrlConstants.DATA_SOURCE_CLASSIFY);
        logger.info("查询到的数据为" + JSONObject.toJSONString(serverResponse));
        return serverResponse;
    }

    /**
     * 获取按照一级分类（数据资源分类）查询的记录数/存储数
     */
//    @RequestMapping("/getRecordsStorageByDataLabels")
//    @ResponseBody
//    public ServerResponse<NumStorageByPrimaryClassify> getRecordsStorageByDataLabels() {
//        ServerResponse<NumStorageByPrimaryClassify> serverResponse = null;
//        serverResponse = dataStorageMonitorIndexServiceImpl.getRecordsStorageByPrimaryClassify(
//                TableOrganizationConstant.DATA_LABELS_CLASSIFY);
//        logger.info("查询到的数据为" + JSONObject.toJSONString(serverResponse));
//        return serverResponse;
//    }

//    @RequestMapping("/test")
//    @ResponseBody
//    public String test(){
//        String tableManageUrl = environment.getRequiredProperty("toolsUrl");
//        String resultMessage =  restTemplate.getForObject(tableManageUrl+"/dataStorageRealTimeStatisticalController",String.class);
//        logger.info("返回的结果为"+resultMessage);
//        return "sdsd";
//    }

    /**
     * 删除选定的ADS实时表监控的表
     */
    @RequestMapping(value = "/delRealTableMonitor", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse<String> delRealTableMonitor(
            HttpServletRequest request) {
        ServerResponse<String> serverResponse = null;
        try {
            JSONObject requeryJson = HttpUtil.getJSONparam(request);
            JSONArray paramJson = requeryJson.getJSONArray("delDataList");
            List<NeedAddRealTimeTable> delDataList = JSONObject.parseArray(paramJson.toJSONString(), NeedAddRealTimeTable.class);
            if (delDataList.size() > 0) {
                serverResponse = dataStorageMonitorIndexServiceImpl.delRealTableMonitor(delDataList);
            } else {
                serverResponse = ServerResponse.asErrorResponse("传入的值为空，不进行删除");
            }
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("删除选定的表信息报错");
            logger.error("删除选定的表信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 左侧组织树-数据组织资产
     *
     * @return
     */
    @RequestMapping(value = "/getTableOrganizationTreeForOrgPage")
    @ResponseBody
    public ServerResponse<List<TreeNode>> getTableOrganizationTreeForOrgPage(@RequestBody GetTreeReq req) {
        ServerResponse<List<TreeNode>> serverResponse = null;
        logger.info("开始获取分级分类的相关信息，只精确到最后一级分级分类");
        try{
//            GetTreeReq req = new GetTreeReq();
            List<TreeNode> treeNodeList = dataStorageMonitorIndexServiceImpl.externalgetTableOrganizationTree(req,false,true,true);
            serverResponse = ServerResponse.asSucessResponse(treeNodeList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("程序报错，查看标准管理程序");
            logger.error("获取分级分类的相关信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }
}
