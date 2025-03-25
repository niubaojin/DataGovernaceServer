package com.synway.datastandardmanager.util;

import com.alibaba.fastjson.JSONArray;
import com.synway.datastandardmanager.config.CacheManager;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.enums.SysCodeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.DataProcess.OrganUser;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;
import com.synway.datastandardmanager.pojo.approvalInfo.ApprovalInfoParams;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.pojo.standardpojo.PushMetaInfo;
import com.synway.datastandardmanager.pojo.standardpojo.PushMetaPojo;
import com.synway.datastandardmanager.pojo.warehouse.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import java.util.*;

/**
 * RestTempalte请求处理类
 * 调用其他模块(仓库、资产、接入等)接口的组件
 * @author
 * @date 2018/12/4 14:25
 */
@Component
public class RestTemplateHandle {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHandle.class);
    @Autowired()
    private RestTemplate restTemplate;
    @Autowired()
    private Environment env;
    @Resource
    CacheManager cacheManager;

    //======================================仓库接口=========================================
    /**
     * 1.8 接口的信息
     * 向数据仓库的接口发送建表请求 （完成）
     * @param createTableData  是否建表成功
     * @return
     */
    public String sendCreateTableInfo(CreateTableData createTableData){
        String requestUrl = Common.DATARESOURCE_BASEURL+"/createTable";
        String result = restTemplate.postForObject(requestUrl,createTableData,String.class);
        JSONObject jsonObject = JSONObject.parseObject(result,JSONObject.class);
        if(jsonObject == null){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"调用接口返回的数据为空");
        }
        if(jsonObject.getIntValue(Common.STATUS) == 1){
            logger.info("数据仓库请求的id值{},返回的信息为：{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.DATA));
            return jsonObject.getString(Common.DATA);
        }else{
            logger.error("向数据仓库发送建表请求报错，请求的id值{}，错误原因{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,jsonObject.getString(Common.MESSAGE));
        }
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
            String requestUrl = Common.DATARESOURCE_BASEURL + "/getDataCenterByApproved?isApproved=" + isApproved;
            logger.info("开始调用接口：" + requestUrl);
            String result = restTemplate.getForObject(requestUrl,String.class);
            if(StringUtils.isBlank(result)){
                throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
            }
            object = JSONObject.parseObject(result);
            int status = object.getIntValue("status");
            if(status!=1){
                throw new Exception(String.format("接口[获取本地仓的相关数据]返回了个错误:%s",object.getString("message")));
            }
        }catch (Exception e){
            logger.error("getDataCenterByApproved接口失败：\n" + ExceptionUtil.getExceptionTrace(e));
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
    public List<DataResource> getDataResourceByCenterId(String centerId,String isApproved) throws Exception {
        String requestUrl=Common.DATARESOURCE_BASEURL+"/getDataResourceByCenterId?isApproved="+isApproved+ "&centerId="+ centerId;
        logger.info("开始调用接口：" + requestUrl);
        String result = restTemplate.getForObject(requestUrl,String.class);
        if(StringUtils.isBlank(result)){
            throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
        }
        JSONObject object = JSONObject.parseObject(result);
        int status = object.getIntValue("status");
        if(status!=1){
            throw new Exception(String.format("接口[获取本地仓的相关数据]返回了个错误:%s",object.getString("message")));
        }
        return JSONObject.parseArray(object.getString("data"),DataResource.class);
    }

    /**
     * 获取 数据仓库中表的字段信息 (完成)
     * @param dataId 数据源ID
     * @param project
     * @param tableNameEn
     * @return
     */
    public List<FieldInfo> requestGetTableStructure(String dataId, String project, String tableNameEn){
        try{
            logger.info(String.format("开始查询数据：%s.%s在数据仓库那边的字段信息", project, tableNameEn));
            if(StringUtils.isBlank(dataId) || StringUtils.isBlank(project) || StringUtils.isBlank(tableNameEn)){
                return new ArrayList<>();
            }
            String requestUrl = Common.DATARESOURCE_BASEURL+"/getTableStructure" + "?resourceId=" + dataId + "&project=" + project +"&tableNameEn=" +tableNameEn;
            logger.info("开始调用接口：" + requestUrl);
            JSONObject response = restTemplate.getForObject(requestUrl ,JSONObject.class);
            int status = response.getIntValue("status");
            if(status != 1){
                throw new Exception(String.format("接口[获取本地仓的相关数据]返回了个错误:%s",response.getString("message")));
            }
            ProjectFieldInfo data = response.getObject("data", ProjectFieldInfo.class);
            if(data == null){
                return new ArrayList<>();
            }else{
                return data.getFieldInfos();
            }
        }catch (Exception e){
            logger.error("从数据仓库中获取的表结构信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return new ArrayList<>();
    }

    /**
     * 获取指定数据源下所有表信息
     * 20211012 获取数据源、项目空间下所有的表目录，不包括表探查信息  （未在前端发现使用了该接口）
     * @param resId 数据源Id
     * @param projectName 项目空间名
     * @return
     */
    public DetectedTable[] requestGetAllTableName(String resId,String projectName){
        String url = Common.DATARESOURCE_BASEURL+"/getProjectTables?resId=" +resId+"&projectName="+projectName;
        logger.info("开始调用接口：" + url);
        DetectedTable[] result =restTemplate.getForObject(url,DetectedTable[].class);
        return result;
    }

    /**
     * 获取本地仓的相关数据 （完成）
     * 20211011 获取本地仓的数据源信息
     * @return
     * @throws Exception
     */
    public List<DataResource> getDataCenterVersion(String isLocal,String isApproved) throws Exception{
        String requestUrl = Common.DATARESOURCE_BASEURL+"/getDataResourceByisLocal?isLocal="+isLocal+"&isApproved="+isApproved;
        logger.info("开始调用接口：" + requestUrl);
        String result = restTemplate.getForObject(requestUrl,String.class);
        if(StringUtils.isBlank(result)){
            throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
        }
        JSONObject object = JSONObject.parseObject(result);
        int status = object.getIntValue("status");
        if(status!=1){
            throw new Exception(String.format("接口[获取本地仓的相关数据]返回了个错误:%s",object.getString("message")));
        }
        return JSONObject.parseArray(object.getString("data"),DataResource.class);
    }

    /**
     * 发送新增字段的接口信息  (已完成)
     * @param createTableData
     * @return
     */
    public String sendAddColumnInfo(CreateTableData createTableData){
        String requestUrl = Common.DATARESOURCE_BASEURL+"/addTableColumn";
        logger.info("开始调用接口：" + requestUrl);
        String result = restTemplate.postForObject(requestUrl,createTableData,String.class);
        JSONObject jsonObject = JSONObject.parseObject(result,JSONObject.class);
        if(jsonObject == null){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"调用接口返回的数据为空");
        }
        if(jsonObject.getIntValue(Common.STATUS) == 1){
            logger.info("数据仓库请求的id值:{},返回的信息为:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.DATA));
            return jsonObject.getString(Common.DATA);
        }else{
            logger.error("向数据仓库发送建表请求报错，请求的id值:{}，错误原因:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,jsonObject.getString(Common.MESSAGE));
        }
    }

    /**
     * 获取探查分析推荐标准数据集
     * @param dataSimilarParameter 调用仓库所需参数
     * @return
     */
    public List<TableSimilarInfo> getDataSimilarInfo(DataSimilarParameter dataSimilarParameter){
        String requestUrl = Common.DATARESOURCE_BASEURL+"/getDatasetDetectResult";
        logger.info("开始调用接口：" + requestUrl);
        JSONObject jsonObject = restTemplate.postForObject(requestUrl, dataSimilarParameter, JSONObject.class);
        if(jsonObject == null){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"调用接口返回的数据为空");
        }
        if(jsonObject.getIntValue(Common.STATUS) == 1){
            logger.info("数据仓库请求的id值:{},返回的信息为:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.DATA));
            TableSimilarInfo[] tableSimilarInfos = jsonObject.getObject("data", TableSimilarInfo[].class);
            List<TableSimilarInfo> tableSimilarInfoList = Arrays.asList(tableSimilarInfos);
            return tableSimilarInfoList;
        }else{
            logger.error("向数据仓库发送建表请求报错，请求的id值:{}，错误原因:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,jsonObject.getString(Common.MESSAGE));
        }
    }

    /**
     * 获取表的所有探查信息 (完成)
     * @param resourceId   数据源ID
     * @param project  项目空间名
     * @param tableNameEN 表名
     * @return
     */
    public DetectedTable getTableDetectInfo(String resourceId,String project,String tableNameEN) throws Exception {
        String requestUrl = Common.DATARESOURCE_BASEURL+"/getTableDetectInfo"+"?resourceId="+resourceId +"&project="+project+"&tableNameEN="+tableNameEN;
        logger.info("开始调用接口：" + requestUrl);
        DetectedTable data = null;
        JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
        if(jsonObject == null){
            throw  new NullPointerException("从数据仓库获取表的探查信息为空");
        }
        Integer status = jsonObject.getInteger("status");
        if(status == 1){
            data = jsonObject.getObject("data", DetectedTable.class);
        }else{
            throw new Exception("从数据仓库调用接口获取表的探查信息报错:"+jsonObject.getString("message"));
        }
        return data;
    }

    /**
     * 获取已对标表的所有信息
     * @param resId   数据源ID
     * @return
     */
    public List<DetectedTable> getProjectStandardTables(String resId){
        List<DetectedTable> detectedTableList = new ArrayList<>();
        String requestUrl = Common.DATARESOURCE_BASEURL+"/getProjectStandardTables?resId="+resId;
        logger.info("开始调用接口：" + requestUrl);
        JSONObject standardTables = restTemplate.getForObject(requestUrl, JSONObject.class);
        if(standardTables == null && standardTables.getInteger("status") != 1){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"调用接口返回的数据为空");
        }
        JSONArray result = standardTables.getJSONArray("data");
        if(result.size() != 0){
            detectedTableList = result.toJavaList(DetectedTable.class);
        }
        logger.info("调用仓库getProjectStandardTables接口结束,结果为:{}",detectedTableList.size());
        return detectedTableList;
    }

    /**
     * 根据数据源Id查询项目空间名  （已完成）
     * @param resId 数据源Id
     * @return
     */
    public List<ProjectInfo> getProjectList(String resId){
        JSONObject object = null;
        try {
            String requestUrl=Common.DATARESOURCE_BASEURL+"/getProjectList?resId="+resId;
            logger.info("开始调用接口：" + requestUrl);
            String result =  restTemplate.getForObject(requestUrl,String.class);
            if(StringUtils.isBlank(result)){
                throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
            }
            object = JSONObject.parseObject(result);
            int status = object.getIntValue("status");
            if(status != 1){
                logger.info("调用仓库接口getProjectList失败");
            }
        }catch (Exception e){
            logger.info("调用仓库接口getProjectList失败");
        }
        return JSONObject.parseArray(object.getString("data"),ProjectInfo.class);
    }

    /**
     * 20211230 获取所有已经探查过的表信息
     * @param resId 数据源ID
     * @param projectName 项目空间名称
     * @return
     */
    public List<DetectedTable> getDetectedTables(String resId,String projectName) throws Exception{
        List<DetectedTable> detectedTableList = new ArrayList<>();
        String requestUrl = Common.DATARESOURCE_BASEURL+"/getDetectedTables?resId="+resId+"&projectName="+projectName+"&isApproved=0";
        logger.info("开始调用接口：" + requestUrl);
        JSONObject detectedTable = restTemplate.getForObject(requestUrl, JSONObject.class);
        if(detectedTable == null){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"调用接口返回的数据为空");
        }
        Integer status = detectedTable.getInteger("status");
        if(status == 1){
            JSONArray result = detectedTable.getJSONArray("data");
            if(result.size() != 0){
                detectedTableList = result.toJavaList(DetectedTable.class);
                //要回填数据源类型，所以要调用getResourceById接口
                DataResource dataResource = getResourceById(resId);
                detectedTableList.stream().forEach(d->{
                    d.setResType(dataResource.getResType());
                });
            }
        }
        logger.info("调用仓库getDetectedTables接口结束,结果为{}",detectedTableList);
        return detectedTableList;
    }

    /**
     * 20211230 根据数据源ID获取数据源信息
     * @param resId 数据源ID
     * @return
     */
    public DataResource getResourceById(String resId){
        DataResource dataResource = null;
        try{
            String requestUrl = Common.DATARESOURCE_BASEURL+"/getResourceById?resId="+resId;
            logger.info("开始调用接口：" + requestUrl);
            JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
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

        }
        return dataResource;
    }


    /**
     * 获取所有已建表信息，然后这边代码再匹配出指定表信息
     * 20211011 获取数据源、项目空间下所有的表目录，不包括表探查信息  （已完成）
     * @return
     */
    public List<DetectedTable> getTableImformationList(){
        //通过数据源id获取所有表信息 再与标准对比筛选出已建表
        List<DetectedTable> list = Collections.synchronizedList(new ArrayList<>());
        try{
            //获取数据源 2:本地的数据源
            List<DataResource> dataResourceList = getDataCenterVersion("2", "0");
            dataResourceList.stream().forEach(data ->{
                if(data == null || StringUtils.isBlank(data.getResType()) || !Common.DATA_TYPE_LIST.contains(data.getResType().toUpperCase())){
                    logger.error(String.format("【%s】,数据仓库不需要查询的数据源信息为:%s",data.getResName(), JSONObject.toJSONString(data)));
                }else{
                    logger.info(String.format("开始获取数据源为：【%s】的表信息", data.getResName()));
                    List<DetectedTable> detectedTableList = getTablesIncludeDetectedInfo(data.getResId(),null);
                    logger.info(String.format("获取到数据源：【%s】的表数量为：%s", data.getResName(), dataResourceList.size()));
                    for (DetectedTable realData : detectedTableList) {
                        realData.setResType(data.getResType());
                        realData.setResName(data.getResName());
                        realData.setCenterName(data.getCenterName());
                        list.add(realData);
                    }
                }
            });
            logger.info("从数据仓库获取到的数据量为："+list.size());
            return list;
        }catch (Exception e){
            logger.error("从数据仓库中获取所有的表名报错"+ExceptionUtil.getExceptionTrace(e));
            return list;
        }
    }

    /**
     * 根据数据源id获取获取表信息,项目空间参数为空
     * @return
     */
    public List<DetectedTable> getTablesIncludeDetectedInfo(String resId,String projectName){
        if (StringUtils.isBlank(projectName) || projectName.toUpperCase().equalsIgnoreCase("ALL")){
            projectName = "";
        }
        List<DataResource> dataResourceList = new ArrayList<>();
        try {
            // 获取数据源 2:本地的数据源
            dataResourceList = getDataCenterVersion("2", "0");
            List<DataResource> dataResourceList1 = getDataCenterVersion("1", "0");
            if (dataResourceList1 != null && dataResourceList1.size()>0){
                dataResourceList.addAll(dataResourceList1);
            }
        }catch (Exception e){
            logger.error("获取数据源失败");
        }
        List<DetectedTable> detectedTableList = null;
        String requestUrl = Common.DATARESOURCE_BASEURL+"/getTablesIncludeDetectedInfo?resId="+resId+"&projectName=" + projectName;
        JSONObject detectedTable = restTemplate.getForObject(requestUrl, JSONObject.class);
        Integer status = detectedTable.getInteger("status");
        if(status == 1){
            JSONArray result = detectedTable.getJSONArray("data");
            if(result.size() != 0){
                detectedTableList = result.toJavaList(DetectedTable.class);
                for (DetectedTable realData : detectedTableList) {
                    for (DataResource resourceDta : dataResourceList){
                        if (realData.getResId().equalsIgnoreCase(resourceDta.getResId())) {
                            realData.setResType(resourceDta.getResType());
                            realData.setResName(resourceDta.getResName());
                            realData.setCenterName(resourceDta.getCenterName());
                        }
                    }
                }
            }else {
                return new ArrayList<>();
            }
            return detectedTableList;
        }
        return new ArrayList<>();
    }

    //===================================标准化平台接口==================================
    /**
     * 每次修改标准表相关数据之后，都要调用标准化平台的修改接口
     */
    public JSONObject requestPushMetaInfo(List<PushMetaInfo> pushMetaInfoList){
        String typeVersion = env.getProperty("dataProcessingVersion","1.5");
//        String requestUrl = "http://synStdCfgSvrImpl/pushMetaInfo";
        String requestUrl = "http://standardizeconfig/pushMetaInfo";
        if(!StringUtils.equalsIgnoreCase(typeVersion,"1.5")){
            requestUrl = "http://STDCONFIGMNG/pushMetaInfo";
        }
        logger.info("开始调用接口：" + requestUrl);
        PushMetaPojo pushMetaPojo = new PushMetaPojo();
        LoginUser user = AuthorizedUserUtils.getInstance().getAuthor();
        pushMetaPojo.setUserId(user == null?"":String.valueOf(user.getUserId()));
        pushMetaPojo.setUserName(user == null?"":String.valueOf(user.getUserName()));
        pushMetaPojo.setPushMetaInfoList(pushMetaInfoList);
        JSONObject response = restTemplate.postForObject(requestUrl,pushMetaPojo, JSONObject.class);
        return response;
    }

    //====================================datagovernance接口=================================
    /**
     * 向 审批流程中创建一个新的流程
     */
    public JSONObject saveOrUpdateApprovalInfo(ApprovalInfoParams approvalInfoParams){
        // 20200608 审批流程的后台通过注册中心获取
        String url = Common.DATAGOVERNANCE_BASEURL + "/process/saveOrUpdateApprovalInfo";
        logger.info("开始调用接口：" + url);
        JSONObject object = restTemplate.postForObject(url,approvalInfoParams,JSONObject.class);
        return object;
    }

    /**
     *  获取业务系统数据获取
     */
    public JSONObject  getApprovalDetail(String approvalId){
        String url = Common.DATAGOVERNANCE_BASEURL + "/process/getApprovalDetail?approvalId="+approvalId;
        logger.info("开始调用接口：" + url);
        JSONObject object = restTemplate.postForObject(url,null,JSONObject.class);
        return object;
    }

    /**
     * 从审批流程中获取所有指定类型的审批数据
     * @param status  状态(0:初始化;1:审批中;2:退回;3:结束;4:手动终止) 多个用逗号分隔
     * @param moduleId  "createTable"//业务模块标识(dataDefinition:数据定义;createTable:建表;standardTable:新建标准表;register:注册)
     * @return
     */
    public List<ApprovalInfoParams> queryApprovalInfoForModule(String status,String moduleId) throws Exception{
        String url = Common.DATAGOVERNANCE_BASEURL + "/process/queryApprovalInfoForModule";
        logger.info("开始调用接口：" + url);
        Map<String,String> map = new HashMap<>();
        map.put("status",status);
        map.put("moduleId",moduleId);
        JSONObject object = restTemplate.postForObject(url,map,JSONObject.class);
        if(object == null){
            throw new Exception("从审批接口调用的数据为空");
        }
        Integer statusReturn = object.getInteger("status");
        if(statusReturn == 1){
            ApprovalInfoParams[] list = object.getObject("data",ApprovalInfoParams[].class);
            return Arrays.asList(list);
        }else{
            throw new Exception("从审批接口调用数据报错："+object.getString("message"));
        }
    }


    /**
     *  判断指定名称的数据在nav中是否存在
     *  报错之后默认值为 true
     * @param name
     * @return
     */
    public Boolean getNavStatusByName(String name){
        try{
            String url = Common.DATAGOVERNANCE_BASEURL + "/navbar/getNavStatusByName?name=" + name;
            logger.info("开始调用接口：" + url);
            JSONObject result = restTemplate.getForObject(url, JSONObject.class);
            if(result == null){
                throw new Exception("查询是否需要审批报错，返回数据为空");
            }
            return result.getBoolean("data");
        }catch (Exception e){
            logger.error("获取是否启用审批报错"+ExceptionUtil.getExceptionTrace(e));
            return false;
        }
    }


    /**
     *  将建表的信息发送到数据历程里面
     */
    public Boolean sendDataHistoryRecord(DataProcess dataProcess){
        String requestUrl = Common.PROPERTY_BASEURL + "/interface/saveDataProcess";
        logger.info("开始调用接口：" + requestUrl);
        Boolean flag = false;
        try{
            String resultStr = restTemplate.postForObject(
                    requestUrl,
                    dataProcess,
                    String.class);
            JSONObject object = JSONObject.parseObject(resultStr,JSONObject.class);
            if(object == null){
                throw new Exception("从数据历程接口调用的数据为空");
            }
            Integer statusReturn = object.getInteger("status");
            if(statusReturn == 1){
                flag = object.getObject("data",Boolean.class);
            }else{
                throw new Exception("调用保存数据历程的接口报错："+object.getString("message"));
            }
        }catch (Exception e){
            logger.error("调用保存数据历程的接口报错"+ExceptionUtil.getExceptionTrace(e));
            flag = false;
        }
        return flag;
    }

    /**
     *  从dubbo接口中获取 用户信息数据
     */
    public OrganUser getUserMesageByDubbo(Integer userId){
        String requestUrl = Common.PROPERTY_BASEURL + "/interface/getUserMesageByDubbo?userId="+userId;
        logger.info("开始调用接口：" + requestUrl);
        OrganUser result = null;
        try{
            result = restTemplate.getForObject(
                    requestUrl,
                    OrganUser.class);
        }catch (Exception e){
            logger.error("从dubbo中获取用户信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return result;
    }

    // 发送操作日志
    public boolean saveOperatorLog(List<OperatorLog> operatorLogs){
        try {
            String url = Common.DATAOPERATION_BASEURL + "/saveOperatorLog";
            logger.info("开始调用接口：" + url);
            String result = restTemplate.postForObject(url, operatorLogs, String.class);
            JSONObject object = JSONObject.parseObject(result, JSONObject.class);
            if(object == null){
                return false;
            }
            Integer status = object.getInteger("status");
            return status == 1 ? true : false;
        }catch (Exception e){
            logger.error("调用saveOperatorLog接口失败：" + ExceptionUtil.getExceptionTrace(e));
            return false;
        }
    }

}
