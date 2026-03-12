package com.synway.property.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.Common;
import com.synway.property.enums.SysCodeEnum;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.DetectedTable;
import com.synway.property.pojo.LoginUser;
import com.synway.property.pojo.datastoragemonitor.DataResource;
import com.synway.property.pojo.opeartorLog.OperatorLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * RestTempalte请求处理类
 * 调用其他模块(仓库、资产、接入等)接口的组件
 *
 * @author
 * @date 2018/12/4 14:25
 */
@Slf4j
@Component
public class RestTemplateHandle {
    @Autowired()
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired()
    private Environment env;


    /*======================================仓库接口=========================================*/
    /**
     * 20211230 根据数据源ID获取数据源信息
     *
     * @param resId 数据源ID
     * @return
     */
    public DataResource getResourceById(String resId) {
        DataResource dataResource = null;
        try {
            String requestUrl = Common.DATARESOURCE_BASEURL_API + "/getResourceById?resId=" + resId;
            log.info("开始调用仓库getResourceById接口，获取数据源信息");
            JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
            if (jsonObject == null) {
                throw new NullPointerException("从数据仓库获取数据源信息为空");
            }
            Integer status = jsonObject.getInteger("status");
            if (status == 1) {
                dataResource = jsonObject.getObject("data", DataResource.class);
            } else {
                throw new Exception("从数据仓库调用接口获取表的探查信息报错:" + jsonObject.getString("message"));
            }
        } catch (Exception e) {
            log.error("获取数据源出错：\n", e);
        }
        return dataResource;
    }


    /**
     * 获取所有已建表信息，然后这边代码再匹配出指定表信息
     * 20211011 获取数据源、项目空间下所有的表目录，不包括表探查信息  （已完成）
     *
     * @return
     */
    public List<DetectedTable> getTableImformationList() {
        try {
            //获取数据源 2:本地的数据源
            JSONArray jsonArray = getDataResourceByisLocal("2", '0');
            List<DataResource> dataResourceList = JSONObject.parseArray(jsonArray.toJSONString(), DataResource.class);
            //通过数据源id获取所有表信息 再与标准对比筛选出已建表
            List<DetectedTable> list = Collections.synchronizedList(new ArrayList<>());
            dataResourceList.stream().forEach(data -> {
                if (data == null || StringUtils.isBlank(data.getResType())
                        || !com.synway.property.constant.Common.DATA_TYPE_LIST.contains(data.getResType().toUpperCase())) {
                    log.error("【" + data.getResName() + "】,数据仓库不需要查询的数据源信息为{}", JSONObject.toJSONString(data));
                } else {
                    log.info("开始获取数据源为：【" + data.getResName() + "】的表信息");
                    JSONArray jsonArray1 = getTablesIncludeDetectedInfo(data.getResId(), null);
                    List<DetectedTable> detectedTableList = JSONArray.parseArray(jsonArray1.toJSONString(), DetectedTable.class);
                    log.info("获取到的数据源为：【" + data.getResName() + "】表信息数量为：" + detectedTableList.size());
                    for (DetectedTable realData : detectedTableList) {
                        realData.setResType(data.getResType());
                        realData.setResName(data.getResName());
                        realData.setCenterName(data.getCenterName());

                        list.add(realData);
                    }
                }
            });
            log.info("从数据仓库获取到的数据量为：" + list.size());
            return list;
        } catch (Exception e) {
            log.error("从数据仓库中获取所有的表名报错" + ExceptionUtil.getExceptionTrace(e));
            return null;
        }
    }

    /**
     * 获取仓库的数据源信息
     *
     * @param isLocal    1：非本地仓，2：本地仓
     * @param isApproved 0：非审批
     * @return JSONArray
     * @throws Exception
     * @date 2024年5月24日09:53:12
     */
    public JSONArray getDataResourceByisLocal(String isLocal, char isApproved) throws Exception {
        JSONArray resultJsonArray = new JSONArray();
        log.info("开始查询仓库数据源，接口：getDataResourceByisLocal");
        String requestUrl = Common.DATARESOURCE_BASEURL_API + "/getDataResourceByisLocal?isLocal=" + isLocal + "&isApproved=" + isApproved;
        String resultStr = restTemplate.getForObject(requestUrl, String.class);
        if (StringUtils.isBlank(resultStr)) {
            throw new NullPointerException("接口[getDataResourceByisLocal]返回的结果为空");
        }
        JSONObject object = JSONObject.parseObject(resultStr);
        int status = object.getIntValue("status");
        if (status != 1) {
            throw new Exception(String.format("接口[getDataResourceByisLocal]返回了个错误:%s", object.getString("message")));
        }
        resultJsonArray = JSONArray.parseArray(object.getString("data"));
        return resultJsonArray;
    }

    /**
     * 根据数据源id获取获取表信息,项目空间参数为空则获取全部
     *
     * @param resId       数据源id
     * @param projectName 项目名
     * @return
     */
    public JSONArray getTablesIncludeDetectedInfo(String resId, String projectName) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isBlank(projectName) || projectName.toUpperCase().equalsIgnoreCase("ALL")) {
            projectName = "";
        }
        log.info("开始获取仓库表信息，接口：getTablesIncludeDetectedInfo");
        String requestUrl = Common.DATARESOURCE_BASEURL_API + "/getTablesIncludeDetectedInfo?resId=" + resId + "&projectName=" + projectName;
        JSONObject detectedTable = restTemplate.getForObject(requestUrl, JSONObject.class);
        Integer status = detectedTable.getInteger("status");
        if (status == 1) {
            jsonArray = detectedTable.getJSONArray("data");
        }
        return jsonArray;
    }

    /**
     * 获取表的所有探查信息 (完成)
     *
     * @param resourceId  数据源ID
     * @param project     项目空间名
     * @param tableNameEN 表名
     * @return
     */
    public DetectedTable getTableDetectInfo(String resourceId, String project, String tableNameEN) throws Exception {
        String requestUrl = Common.DATARESOURCE_BASEURL_API + "/getTableDetectInfo" + "?resourceId=" + resourceId
                + "&project=" + project + "&tableNameEN=" + tableNameEN;
        log.info("开始获取表的所有探查信息");
        DetectedTable data = null;
        JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
        if (jsonObject == null) {
            throw new NullPointerException("从数据仓库获取表的探查信息为空");
        }
        Integer status = jsonObject.getInteger("status");
        if (status == 1) {
            data = jsonObject.getObject("data", DetectedTable.class);
        } else {
            throw new Exception("从数据仓库调用接口获取表的探查信息报错:" + jsonObject.getString("message"));
        }
        return data;
    }

    public JSONArray excuteSql(String resId, String sql) throws Exception{
        String requesUrl = String.format("%s/excuteSql?resId=%s&sql=%s", Common.DATARESOURCE_BASEURL_API, resId, sql);
        log.info("调用仓库接口执行sql");
        String resultStr = restTemplate.getForObject(requesUrl, String.class);
        JSONObject object = JSONObject.parseObject(resultStr);
        int status = object.getIntValue("status");
        if (status != 1) {
            throw new Exception(String.format("接口[getDataResourceByisLocal]返回了个错误:%s", object.getString("message")));
        }
        return JSONArray.parseArray(object.getString("data"));
    }

    /*======================================操作日志=========================================*/
    // 发送操作日志
    public boolean saveOperatorLog(List<OperatorLog> operatorLogs){
        try {
            String url = Common.DATAOPERATIONS_BASEURL + "/saveOperatorLog";
            log.info("开始调用接口：" + url);
            String result = restTemplate.postForObject(url, operatorLogs, String.class);
            JSONObject object = JSONObject.parseObject(result, JSONObject.class);
            if(object == null){
                return false;
            }
            Integer status = object.getInteger("status");
            return status == 1 ? true : false;
        }catch (Exception e){
            log.error("调用saveOperatorLog接口失败：" + ExceptionUtil.getExceptionTrace(e));
            return false;
        }
    }
    public boolean OperatorLogSend(OperatorLog operatorLog){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateTypeStr = SysCodeEnum.getNameByCodeAndType(Integer.toString(operatorLog.getOperateType()), "OPERATORTYPE");
        String operatorCondition = String.format("%s%s%s", loginUser.getUserName(), operateTypeStr, operatorLog.getOperateCondition());
        List<OperatorLog> operatorLogList = new ArrayList<>();
        operatorLog.setOperateTime(DateUtil.formatDateTimeSimple(new Date()));
        operatorLog.setTerminalId(env.getProperty("server.address"));
        operatorLog.setOperateCondition(operatorCondition);
        operatorLog.setDisplay("");
        operatorLog.setDataLevel(1);
        operatorLog.setUserName(loginUser.getUserName());
        operatorLog.setUserId(loginUser.getIdCard());
        operatorLog.setUserNum(loginUser.getUserId());
        operatorLogList.add(operatorLog);
        return saveOperatorLog(operatorLogList);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>血缘接口>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 获取表使用热度
     */
    public JSONArray getTableHots(int limits){
        JSONArray jsonArray = new JSONArray();
        String requestUrl = String.format("%s?limits=%d", Common.ds_getTableHot, limits);
        log.info(">>>>>>开始调用接口：" + requestUrl);
        JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
        Integer status = jsonObject.getInteger("status");
        if (status == 1) {
            jsonArray = jsonObject.getJSONArray("data");
        }
        return jsonArray;
    }

}
