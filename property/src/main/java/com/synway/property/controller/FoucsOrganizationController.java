package com.synway.property.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.UrlConstants;
import com.synway.property.pojo.*;
import com.synway.property.pojo.datastoragemonitor.DataResource;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;
import com.synway.property.service.FoucsOrganizationService;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.HttpUtil;
import com.synway.common.bean.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * 首页中重点组织监控的相关代码
 * @author majia
 */
@Controller
@RequestMapping("/dataStorageMonitoring")
public class FoucsOrganizationController {
    private static Logger logger = LoggerFactory.getLogger(FoucsOrganizationController.class);
    @Autowired
    private FoucsOrganizationService foucsOrganizationServiceImpl;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private Environment environment;

    /**
     * 展示已经添加监控的重点组织的相关信息
     */
    @RequestMapping("/showFoucsOrganizationFull")
    @ResponseBody
    public ServerResponse<List<FoucsOrganizationFull>> showFoucsOrganizationFull(){
        logger.info("开始查询需要监控的所有重点组织信息=========================");
        ServerResponse<List<FoucsOrganizationFull>> serverResponseList = null;
        serverResponseList = foucsOrganizationServiceImpl.getAllFoucsOrganization();
        logger.info("showFoucsOrganizationFull()方法查询结束==================");
        return serverResponseList;
    }

    /**
     * 从数据仓库获取数据中心、数据源等信息
     *
     * @return
     */
    @RequestMapping(value = "/getTableInfo")
    @ResponseBody
    public ServerResponse<JSONArray> getTableInfo() {
        try {
//            String result = restTemplate.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getDataSourceTree?name=&radioinLine=1", String.class);

            JSONArray returnList = new JSONArray();
            String result = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getAllResources", String.class);
            if(StringUtils.isNotBlank(result) && "1".equals(JSONObject.parseObject(result).getString("status"))){
                String dataStr = JSONObject.parseObject(result).getObject("data",String.class);
                List<DataResource> allDataResource = JSONArray.parseArray(dataStr, DataResource.class);

                // ads、hbase数据中心及数据源
                for (int i=0; i<allDataResource.size(); i++){
                    if ("ads".equals(allDataResource.get(i).getResType()) || "hbase".equals(allDataResource.get(i).getResType())){
                        JSONObject adsResourceTemp = (JSONObject) JSONArray.toJSON(allDataResource.get(i));
                        returnList.add(adsResourceTemp);
                    }
                }
//                adsDataCenters = adsDataResource.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getCenterId()))), ArrayList::new));
            }
            return ServerResponse.asSucessResponse(returnList);
        } catch (Exception e) {
            logger.info("从数据仓库获取数据中心、数据源等信息异常：" + e.getMessage());
            return ServerResponse.asErrorResponse("从数据仓库获取数据中心、数据源等信息异常");
        }
    }

    /**
     * 根据大类名称，一级分类名称 获取表格中所有的分类信息（精确到二级分类）
     * 为空时表示查询所有
     * @param mainClassifyCodeCh   主分类代码
     * @param primaryClassifyCodeCh  一级分类代码
     * @return
     */
    @RequestMapping("/getClassifyInformationTable")
    @ResponseBody
    public ServerResponse<List<ClassifyInformation>> getClassifyInformationTable(
            @RequestParam("mainClassifyCh")String  mainClassifyCodeCh,
            @RequestParam("primaryClassifyCh")String  primaryClassifyCodeCh){
        logger.info("开始查询所有的分类信息 mainClassifyCodeCh:"+mainClassifyCodeCh+" primaryClassifyCodeCh:"+primaryClassifyCodeCh);
        ServerResponse<List<ClassifyInformation>> serverResponseList = null;
        if(StringUtils.isBlank(mainClassifyCodeCh)){
            return ServerResponse.asErrorResponse("参数 mainClassifyCode不能为空");
        }
        serverResponseList = foucsOrganizationServiceImpl.getClassifyInformationTableService(mainClassifyCodeCh,primaryClassifyCodeCh);
        logger.info("getClassifyInformationTable()方法查询结束==================");
        return serverResponseList;
    }


    /**
     * 将选择的需要加入数据库中的重点组织添加到数据库
     */

    @RequestMapping(value = "/insertClassifyInformationTable",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=UTF-8")
    @ResponseBody
    public ServerResponse<String> insertClassifyInformationTable(
            HttpServletRequest request){
        ServerResponse<String> serverResponse = null;
        try{
            JSONObject requeryJson = HttpUtil.getJSONparam(request);
//            String primaryNameCH = requeryJson.getString("primaryNameCH");
            JSONArray paramJson = requeryJson.getJSONArray("dataList");
            List<ClassifyInformation> insertDataList = JSONObject.parseArray(paramJson.toJSONString() ,ClassifyInformation.class);
            logger.info("重点组织要插入的数据为：" + JSONObject.toJSONString(insertDataList));
            serverResponse = foucsOrganizationServiceImpl.insertClassifyInformationTableService(insertDataList);
        }catch(Exception e){
            serverResponse = ServerResponse.asErrorResponse("将需要添加的重点组织信息插入数据库报错");
            logger.error("将需要添加的重点组织信息插入数据库报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    @RequestMapping("/getAllClassifyInformationTable")
    @ResponseBody
    public ServerResponse<List<ClassifyInformation>> getClassifyInformationTableService(HttpServletRequest request){
        ServerResponse<List<ClassifyInformation>> serverResponse = null;
        try{
            logger.info("查询所有分类信息");
            serverResponse = foucsOrganizationServiceImpl.getAllClassifyInformationTableService();
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询所有分类信息报错");
            logger.error("查询所有分类信息" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     * 增量数据排行TOP5柱状图的接口
     *
     */
    @RequestMapping("/getIncrementalDataRankingTop5")
    @ResponseBody
    public ServerResponse<DataRankingTop> getIncrementalDataRanking(){
        ServerResponse<DataRankingTop> serverResponse = null;
        logger.info("开始查询增量数据排行TOP5柱状图的数据");
        serverResponse = foucsOrganizationServiceImpl.getIncrementalDataRankingService();
        logger.info("查询增量数据排行TOP5柱状图的数据结束");
        return serverResponse;
    }

    /**
     * 全量数据排行TOP5柱状图的接口
     * 在表组织资产的表中 昨日数据的top5
     *
     */
    @RequestMapping("/getFullDataRankingTop5")
    @ResponseBody
    public ServerResponse<DataRankingTop> getFullDataRanking(){
        ServerResponse<DataRankingTop> serverResponse = null;
        logger.info("开始查询全量数据排行TOP5柱状图的数据");
        serverResponse = foucsOrganizationServiceImpl.getFullDataRankingService();
        logger.info("查询全量数据排行TOP5柱状图的数据结束");
        return serverResponse;
    }

    /**
     * 获取24小时热度表柱状图TOP8的接口
     */
    @RequestMapping("/getDayUseHeatTop8")
    @ResponseBody
    public ServerResponse<JSONArray> getDayUseHeat(){
        logger.info("开始获取24小时热度表柱状图TOP8的数据");
        return ServerResponse.asSucessResponse(foucsOrganizationServiceImpl.getDayUseHeatService());
    }


    /**
     * 获取添加重点组织中模态框中左侧的选择框
     */
    @RequestMapping("/getClassTreeList")
    @ResponseBody
    public ServerResponse<List<ClassifyInfoTree>> getClassTreeList(){
        logger.info("开始获取添加重点组织中模态框中左侧的选择框的数据");
        ServerResponse<List<ClassifyInfoTree>> serverResponse = foucsOrganizationServiceImpl.getClassTreeList();
        logger.info("获取添加重点组织中模态框中左侧的选择框的数据结束==================");
        return serverResponse;
    }
}
