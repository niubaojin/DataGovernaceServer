package com.synway.governace.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.governace.common.ApiConstant;
import com.synway.governace.pojo.generalManagement.DetectedTable;
import com.synway.governace.pojo.generalManagement.FontOption;
import com.synway.governace.pojo.largeScreen.DataBaseState;
import com.synway.governace.pojo.largeScreen.DataResource;
import com.synway.governace.pojo.operatorLog.OperatorLog;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * RestTempalte请求处理类
 * @author wdw
 * @date 2018/12/4 14:25
 */
@Component
public class RestTemplateHandle {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHandle.class);
    @Qualifier("loadBalanced")
    @Autowired
    private RestTemplate loadBalanced;

    @Resource(name="restTemplateNoIncepter")
    RestTemplate restTemplateNoIncepter;


    /**
     * 获取 大数据库的相关 存储总量和 平台使用存储大小
     * @return
     */
    public List<DataBaseState>  getDataBaseStatus(){
        try {
//            String requestUrl = "http://property/dataStorageMonitoring/getDataBaseStatus?platFormType=阿里";
            String requestUrl = ApiConstant.PROPERTY_GET_DB_STATUS + "阿里";
            String result = loadBalanced.getForObject(requestUrl, String.class);
            logger.info("调用getDataBaseStatus返回的数据为：{}", JSONObject.toJSONString(result));
            if (StringUtils.isBlank(result)) {
                throw new NullPointerException("接口[获取存储总量信息]返回的结果为空");
            }
            JSONObject object = JSONObject.parseObject(result);
            int status = object.getIntValue("status");
            if (status != 1) {
                logger.error(String.format("接口[获取存储总量信息]返回错误:%s", object.getString("message")));
                return null;
            }
            List<DataBaseState> list = JSONObject.parseArray(object.getString("data"), DataBaseState.class);
            return list;
        } catch (Exception e) {
            logger.error("从property接口获取数据报错：" , e);
            return null;
        }
    }


    /**
     * 调用服务平台接口（新疆大屏用）
     * @param url
     * @return
     */
    public String getServicefacsrvInterface(String url){
        try{
            logger.info("url:" + url);
            String result = restTemplateNoIncepter.getForObject(url, String.class);
            if(StringUtils.isBlank(result)){
                logger.error("从资源服务平台获取到的接口返回数据为空");
                return null;
            }
            JSONObject resultMap = JSONObject.parseObject(result);
            boolean flag = (boolean) resultMap.get("success");
            if(flag){
                String data = String.valueOf(resultMap.get("data"));
                if(StringUtils.isBlank(data)){
                    logger.error("从资源服务平台获取到的接口返回数据为空");
                    return null;
                }
                return data;
            }else{
                logger.error("报错原因"+resultMap.get("errorMsg"));
                return null;
            }
        }catch (Exception e){
            logger.error("从资源服务平台接口获取数据报错：" , e);
            return null;
        }
    }


    /**
     * 资源服务平台的相关接口
     * @param url  相关的接口
     *
     * @return
     */
    public List<JSONObject> getClassifyServerInterfance(String url){
        try{
            logger.info("url:"+url);
            String result = restTemplateNoIncepter.getForObject(url,String.class);
            if(StringUtils.isBlank(result)){
                logger.error("从资源服务平台获取到的接口返回数据为空");
                return null;
            }
            JSONObject resultMap = JSONObject.parseObject(result);
            boolean flag = (boolean) resultMap.get("isSuccess");
            if(flag){
                String dataStr = String.valueOf(resultMap.get("data"));
                if(StringUtils.isBlank(dataStr)){
                    logger.error("从资源服务平台获取到的接口返回数据为空");
                    return null;
                }
                List<JSONObject> data = JSONObject.parseArray(dataStr,JSONObject.class);
                return data;
            }else{
                logger.error("报错原因"+resultMap.get("msg"));
                return null;
            }
        }catch (Exception e){
            logger.error("从资源服务平台接口获取数据报错：", e);
            return null;
        }
    }

    /**
     * serverfacsrv-sysManage接口-tgj
     * @param url  相关的接口
     *
     * @return
     */
    public JSONObject getSysManageInterface(String url) {
        try {
            logger.info("url:" + url);
            JSONObject result = restTemplateNoIncepter.getForObject(url, JSONObject.class);
            if (null == result) {
                logger.error("从serverfacsrv-sysManage获取到的接口返回数据为空");
                return null;
            }
            boolean flag = (boolean) result.get("success");
            if (flag) {
                JSONObject jo = result.getJSONObject("data");
                if (null == jo) {
                    logger.error("从serverfacsrv-sysManage获取到的接口返回数据为空");
                    return null;
                }
                return jo;
            } else {
                logger.error("报错原因" + result.get("errorMsg"));
                return null;
            }
        } catch (Exception e) {
            logger.error("从serverfacsrv-sysManage接口获取数据报错：" , e);
            return null;
        }
    }

    /**
     * serverfacsrv-sysManage接口-tgj
     * @param url  相关的接口
     *
     * @return
     */
    public List<JSONObject> querySysManageInterface(String url) {
        try {
            logger.info("url:" + url);
            JSONObject result = restTemplateNoIncepter.getForObject(url, JSONObject.class);
            if (null == result) {
                logger.error("从serverfacsrv-sysManage获取到的接口返回数据为空");
                return null;
            }
            boolean flag = (boolean) result.get("success");
            if (flag) {
                JSONArray ja = result.getJSONArray("data");
                if(null == ja || ja.size() == 0) {
                    logger.error("从serverfacsrv-sysManage获取到的接口返回数据为空");
                    return null;
                }
                List<JSONObject> data = JSONObject.parseArray(JSONObject.toJSONString(ja), JSONObject.class);
                return data;
            } else {
                logger.error("报错原因" + result.get("errorMsg"));
                return null;
            }
        } catch (Exception e) {
            logger.error("从serverfacsrv-sysManage接口获取数据报错：" , e);
            return null;
        }
    }


    /**
     * 调用数据魔方的接口信息
     * 接口返回的值 {"success":true,"message": null,"data": Object}
     * @param url  相关的地址
     * @return  具体的返回数据 string 类型
     */
    public String getTagServerInterface(String url){
        try{
            logger.info("请求的url信息为:"+url);
            JSONObject jsonObject = new JSONObject();
            JSONObject resultMap = restTemplateNoIncepter.postForObject(url,jsonObject,JSONObject.class);
            if(resultMap == null){
                logger.error("接口返回信息为空");
                return null;
            }
            boolean flag = resultMap.getBoolean("success");
            if(flag){
                return JSONObject.toJSONString(resultMap.get("data"));
            }else{
                logger.error("调用接口报错，报错原因"+resultMap.getString("message"));
                return null;
            }
        }catch (Exception e){
            logger.error("调用数据魔方报错：", e);
            return null;
        }
    }


    /**
     * 获取本地仓的相关数据
     * @return
     * @throws Exception
     */
    public List<DataResource> getDataCenterVersion() throws Exception{
        logger.info("开始查询本地仓库的类型");
//        String requestUrl = "http://dataresource/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0";
        String requestUrl = ApiConstant.RESOURCE_GET_LOCAL_DATA_RESOURCE;
        String result = loadBalanced.getForObject(requestUrl,String.class);
        if(StringUtils.isBlank(result)){
            throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
        }
        JSONObject object = JSONObject.parseObject(result);
        int status = object.getIntValue("status");
        if(status!=1){
            throw new RuntimeException(String.format("接口[获取本地仓的相关数据]返回了个错误:%s,错误id为:%s",
                    object.getString("message"),object.getString("requestUuid")));
        }
        return JSONObject.parseArray(object.getString("data"),DataResource.class);
    }

    /**
     * 1.8 接口的信息
     * 根据centerId获取对应数据中心 （完成）
     * @param isApproved  0:不需审批 1:审批中 2：审批通过  3、审批不通过 默认为0
     * @return
     */
    public List<DataResource> getDataCenter(String isApproved){
        JSONObject object = null;
        try {
            String requestUrl = ApiConstant.RESOURCE_SERVICE_API_URL+"/getDataCenterByApproved?isApproved="+isApproved;
            String result = loadBalanced.getForObject(requestUrl,String.class);
            if(StringUtils.isBlank(result)){
                throw new NullPointerException("接口[获取数据中心列表]返回的结果为空");
            }
            object = JSONObject.parseObject(result);
            int status = object.getIntValue("status");
            if(status!=1){
                throw new Exception(String.format("接口[获取数据中心列表]返回了个错误:%s",object.getString("message")));
            }
        }catch (Exception e){
            logger.error("获取数据中心列表出错：\n" , e);
        }
        return JSONObject.parseArray(object.getString("data"),DataResource.class);
    }

    /**
     * 1.8 接口的信息
     * 根据数据中心ID获取数据源信息 （完成）
     * @param centerId
     * @param isApproved
     * @return
     */
    public List<DataResource> getDataResourceByCenterId(String centerId,String isApproved){
        try{
            String requestUrl = ApiConstant.RESOURCE_SERVICE_API_URL+"/getDataResourceByCenterId?isApproved="+isApproved+ "&centerId="+ centerId;
            String result = loadBalanced.getForObject(requestUrl,String.class);
            if(StringUtils.isBlank(result)){
                throw new NullPointerException("接口[获取数据源列表]返回的结果为空");
            }
            JSONObject object = JSONObject.parseObject(result);
            int status = object.getIntValue("status");
            if(status !=1 ){
                throw new Exception(String.format("接口[获取数据源列表]返回了个错误:%s",object.getString("message")));
            }
            return JSONObject.parseArray(object.getString("data"),DataResource.class);
        }catch (Exception e){
            logger.error("获取数据源列表报错：\n" , e);
            return null;
        }
    }
    /**
     * 根据数据中心获取所有的数据源,
     * @param centerId
     * @return
     * @throws Exception
     */
    public List<DataResource> getDataResourceByCenterIdAndType(String centerId, String resType,String isApproved){
        try{
            String requestUrl = ApiConstant.RESOURCE_SERVICE_API_URL+"/getDataResourceByCenterIdAndType?centerId="+centerId + "&resType=" + resType + "&isApproved=" + isApproved;
            String result = loadBalanced.getForObject(requestUrl,String.class);
            if(StringUtils.isBlank(result)){
                throw new NullPointerException("接口[获取数据源列表]返回的结果为空");
            }
            JSONObject object = JSONObject.parseObject(result);
            int status = object.getIntValue("status");
            if(status !=1 ){
                throw new Exception(String.format("接口[获取数据源列表]返回了个错误:%s",object.getString("message")));
            }
            return JSONObject.parseArray(object.getString("data"),DataResource.class);
        }catch (Exception e){
            logger.error("获取数据源列表报错：\n" , e);
            return null;
        }
    }

    /**
     * 20211230 根据数据源ID获取数据源信息
     * @param resId 数据源ID
     * @return
     */
    public DataResource getResourceById(String resId){
        DataResource dataResource = null;
        try{
            String requestUrl = ApiConstant.RESOURCE_SERVICE_API_URL+"/getResourceById?resId="+resId;
            logger.info("开始调用仓库getResourceById接口，获取数据源信息,url:" + requestUrl);
            JSONObject jsonObject = loadBalanced.getForObject(requestUrl, JSONObject.class);
            if(jsonObject == null){
                throw new NullPointerException("从数据仓库获取数据源信息为空");
            }
            Integer status = jsonObject.getInteger("status");
            if(status == 1){
                dataResource = jsonObject.getObject("data", DataResource.class);
            }else{
                throw new Exception("从数据仓库调用接口获取表的探查信息报错:"+jsonObject.getString("message"));
            }
        }catch (Exception e ){
            logger.error("获取数据源信息失败：\n" , e);
        }
        return dataResource;
    }

    /**
     * 根据数据源id获取获取表信息,项目空间参数为空
     * @return
     */
    public List<DetectedTable> getTablesIncludeDetectedInfo(String resId, String projectName){
        if (StringUtils.isBlank(projectName) || projectName.toUpperCase().equalsIgnoreCase("ALL")){
            projectName = "";
        }
        logger.info("开始调用仓库getTablesIncludeDetectedInfo接口");
        List<DetectedTable> detectedTableList = null;
        String requestUrl = ApiConstant.RESOURCE_SERVICE_API_URL+"/getTablesIncludeDetectedInfo?resId="+resId+"&projectName=" + projectName;
        JSONObject detectedTable = loadBalanced.getForObject(requestUrl, JSONObject.class);
        Integer status = detectedTable.getInteger("status");
        if(status == 1){
            JSONArray result = detectedTable.getJSONArray("data");
            if(result.size() != 0){
                detectedTableList = result.toJavaList(DetectedTable.class);
            }
        }
        return detectedTableList;
    }
    /**
     * 根据数据源id获取项目信息列表
     */
    public List<FontOption> getProjectList(String resId){
        logger.info("开始调用仓库getProjectList接口");
        List<FontOption> projects = new ArrayList<>();
        String requestUrl = ApiConstant.RESOURCE_SERVICE_API_URL+"/getProjectList?resId="+resId+"&type=";
        JSONObject detectedTable = loadBalanced.getForObject(requestUrl, JSONObject.class);
        Integer status = detectedTable.getInteger("status");
        if(status == 1){
            JSONArray result = detectedTable.getJSONArray("data");
            if(result.size() != 0){
                result.stream().forEach(item ->{
                    FontOption fontOption = new FontOption();
                    String projectName = JSON.parseObject(item.toString()).getString("projectName");
                    fontOption.setLabel(projectName);
                    fontOption.setValue(projectName);
                    projects.add(fontOption);
                });
            }
        }
        return projects;
    }

    // 发送操作日志
    public boolean saveOperatorLog(List<OperatorLog> operatorLogs){
        try {
            String url = ApiConstant.DATAOPERATIONS_BASEURL + "/saveOperatorLog";
            logger.info("开始调用接口：" + url);
            String result = loadBalanced.postForObject(url, operatorLogs, String.class);
            JSONObject object = JSONObject.parseObject(result, JSONObject.class);
            if(object == null){
                return false;
            }
            Integer status = object.getInteger("status");
            return status == 1 ? true : false;
        }catch (Exception e){
            logger.error("调用saveOperatorLog接口失败：{}", e);
            return false;
        }
    }

}
