package com.synway.datastandardmanager.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.DataDefinitionDTO;
import com.synway.datastandardmanager.entity.pojo.LoginUser;
import com.synway.datastandardmanager.entity.vo.OperatorLogVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableDataVO;
import com.synway.datastandardmanager.entity.vo.dataprocess.PushMetaInfo;
import com.synway.datastandardmanager.entity.vo.dataprocess.PushMetaPojo;
import com.synway.datastandardmanager.entity.vo.warehouse.*;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.entity.vo.warehouse.TableSimilarInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired()
    private Environment env;


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>仓库接口>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 20211230 根据数据源ID获取数据源信息
     *
     * @param resId 数据源ID
     */
    public DataResource getResourceById(String resId) {
        DataResource dataResource = new DataResource();
        try {
            String requestUrl = String.format("%s?resId=%s", Common.ds_getResourceById, resId);
            log.info(">>>>>>开始调用接口：" + requestUrl);
            dataResource = restTemplate.getForObject(requestUrl, DataResource.class);
            if (dataResource == null) {
                throw new NullPointerException(String.format("根据resId：%s获取数据源信息为空", resId));
            }
        } catch (Exception e) {
            log.error(String.format("获取数据源信息出错：", e));
        }
        return dataResource;
    }

    /**
     * 获取本地仓的相关数据（完成）
     * 20211011：获取本地仓的数据源信息
     */
    public List<DataResource> getDataCenterVersion(String isLocal, String isApproved) {
        String requestUrl = String.format("%s?isLocal=%s&isApproved=%s", Common.ds_getDataResourceByisLocal, isLocal, isApproved);
        log.info(">>>>>>开始调用接口：" + requestUrl);
        String result = restTemplate.getForObject(requestUrl, String.class);
        JSONObject object = JSONObject.parseObject(result);
        int status = object.getIntValue("status");
        if (status != 1) {
            throw new NullPointerException(String.format("接口[获取本地仓的相关数据]返回了个错误:%s", object.getString("message")));
        }
        return JSONObject.parseArray(object.getString("data"), DataResource.class);
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
        String requestUrl = String.format("%s?resourceId=%s&project=%s&tableNameEN=%s", Common.ds_getTableDetectInfo, resourceId, project, tableNameEN);
        log.info(">>>>>>开始调用接口：" + requestUrl);
        JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
        if (jsonObject == null) {
            throw new NullPointerException("从数据仓库获取表的探查信息为空");
        }
        DetectedTable data = new DetectedTable();
        if (jsonObject.getInteger("status") == 1) {
            data = jsonObject.getObject("data", DetectedTable.class);
        } else {
            throw new Exception("从数据仓库调用接口获取表的探查信息报错:" + jsonObject.getString("message"));
        }
        return data;
    }

    /**
     * 获取数据仓库中表的字段信息 (完成)
     *
     * @param dataId      数据源ID
     * @param project
     * @param tableNameEn
     */
    public List<FieldInfo> requestGetTableStructure(String dataId, String project, String tableNameEn) {
        try {
            if (StringUtils.isBlank(dataId) || StringUtils.isBlank(project) || StringUtils.isBlank(tableNameEn)) {
                return new ArrayList<>();
            }
            String requestUrl = String.format("%s?resourceId=%s&project=%s&tableNameEn=%s", Common.ds_getTableStructure, dataId, project, tableNameEn);
            log.info(">>>>>>开始调用接口：" + requestUrl);
            JSONObject response = restTemplate.getForObject(requestUrl, JSONObject.class);
            if (response.getIntValue("status") != 1) {
                throw new Exception(String.format("调用接口报错:%s", response.getString("message")));
            }
            ProjectFieldInfo data = response.getObject("data", ProjectFieldInfo.class);
            if (data != null) {
                return data.getFieldInfos();
            }
        } catch (Exception e) {
            log.error(">>>>>>从数据仓库中获取的表结构信息报错：", e);
        }
        return new ArrayList<>();
    }

    /**
     * 根据数据源Id查询项目空间名  （已完成）
     *
     * @param resId 数据源Id
     * @return
     */
    public List<ProjectInfo> getProjectList(String resId) {
        try {
            String requestUrl = String.format("%s?resId=%s", Common.ds_getProjectList, resId);
            log.info(">>>>>>开始调用接口：" + requestUrl);
            String result = restTemplate.getForObject(requestUrl, String.class);
            if (StringUtils.isBlank(result)) {
                throw new NullPointerException("返回的结果为空");
            }
            JSONObject object = JSONObject.parseObject(result);
            if (object.getIntValue("status") != 1) {
                throw new NullPointerException("调用仓库接口getProjectList失败");
            }
            return JSONObject.parseArray(object.getString("data"), ProjectInfo.class);
        } catch (Exception e) {
            log.info(">>>>>>调用仓库接口getProjectList失败：", e);
            return new ArrayList<>();
        }
    }

    /**
     * 20211230：获取所有已经探查过的表信息
     *
     * @param resId       数据源ID
     * @param projectName 项目空间名称
     * @return
     */
    public List<DetectedTable> getDetectedTables(String resId, String projectName) {
        List<DetectedTable> detectedTableList = new ArrayList<>();
        String requestUrl = String.format("%s?resId=%s&projectName=%s&isApproved=0", Common.ds_getDetectedTables, resId, projectName);
        log.info(">>>>>>开始调用接口：" + requestUrl);
        JSONObject detectedTable = restTemplate.getForObject(requestUrl, JSONObject.class);
        if (detectedTable == null) {
            throw new NullPointerException(String.format("%s：调用接口返回的数据为空", ErrorCodeEnum.CHECK_ERROR));
        }
        if (detectedTable.getInteger("status") == 1) {
            JSONArray result = detectedTable.getJSONArray("data");
            if (result.size() != 0) {
                detectedTableList = result.toJavaList(DetectedTable.class);
                //要回填数据源类型，所以要调用getResourceById接口
                DataResource dataResource = getResourceById(resId);
                detectedTableList.stream().forEach(d -> {
                    d.setResType(dataResource.getResType());
                });
            }
        }
        return detectedTableList;
    }

    /**
     * 根据centerId获取对应数据中心（完成）
     *
     * @param isApproved 0:不需审批 1:审批中 2：审批通过  3、审批不通过 默认为0
     */
    public List<DataResource> getDataCenter(String isApproved) {
        List<DataResource> dataResources = new ArrayList<>();
        try {
            String requestUrl = String.format("%s?isApproved=%s", Common.ds_getDataCenterByApproved, isApproved);
            log.info(">>>>>>开始调用接口：" + requestUrl);
            String result = restTemplate.getForObject(requestUrl, String.class);
            if (StringUtils.isBlank(result)) {
                throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
            }
            JSONObject object = JSONObject.parseObject(result);
            if (object.getIntValue("status") != 1) {
                throw new Exception(String.format("接口[获取本地仓的相关数据]返回了个错误:%s", object.getString("message")));
            }
            dataResources = JSONObject.parseArray(object.getString("data"), DataResource.class);
        } catch (Exception e) {
            log.error(">>>>>>调用getDataCenterByApproved接口失败：", e);
        }
        return dataResources;
    }

    /**
     * 根据数据中心ID获取数据源信息 （完成）
     */
    public List<DataResource> getDataResourceByCenterId(String centerId, String isApproved) throws Exception {
        String requestUrl = String.format("%s?isApproved=%s&centerId=%s", Common.ds_getDataResourceByCenterId, isApproved, centerId);
        log.info(">>>>>>开始调用接口：" + requestUrl);
        String result = restTemplate.getForObject(requestUrl, String.class);
        if (StringUtils.isBlank(result)) {
            throw new NullPointerException("接口[获取本地仓的相关数据]返回的结果为空");
        }
        JSONObject object = JSONObject.parseObject(result);
        if (object.getIntValue("status") != 1) {
            throw new Exception(String.format("接口[获取本地仓的相关数据]返回了个错误:%s", object.getString("message")));
        }
        return JSONObject.parseArray(object.getString("data"), DataResource.class);
    }

    /**
     * 获取所有已建表信息，然后这边代码再匹配出指定表信息
     * 20211011：获取数据源、项目空间下所有的表目录，不包括表探查信息（已完成）
     */
    public List<DetectedTable> getTableImformationList() {
        //通过数据源id获取所有表信息 再与标准对比筛选出已建表
        List<DetectedTable> list = Collections.synchronizedList(new ArrayList<>());
        try {
            //获取数据源 2:本地的数据源
            List<DataResource> dataResourceList = getDataCenterVersion("2", "0");
            dataResourceList.stream().forEach(data -> {
                if (data != null && StringUtils.isNotBlank(data.getResType()) && Common.DATA_TYPE_LIST.contains(data.getResType().toUpperCase())) {
                    log.info(String.format(">>>>>>开始获取数据源为：【%s】的表信息", data.getResName()));
                    List<DetectedTable> detectedTableList = getTablesIncludeDetectedInfo(data.getResId(), null);
                    log.info(String.format(">>>>>>获取到数据源：【%s】的表数量为：%s", data.getResName(), dataResourceList.size()));
                    for (DetectedTable realData : detectedTableList) {
                        realData.setResType(data.getResType());
                        realData.setResName(data.getResName());
                        realData.setCenterName(data.getCenterName());
                        list.add(realData);
                    }
                }
            });
            log.info(">>>>>>从数据仓库获取到的数据量为：" + list.size());
        } catch (Exception e) {
            log.error(">>>>>>从数据仓库中获取所有表名报错：", e);
        }
        return list;
    }

    /**
     * 根据数据源id获取获取表信息,项目空间参数为空
     */
    public List<DetectedTable> getTablesIncludeDetectedInfo(String resId, String projectName) {
        if (StringUtils.isBlank(projectName) || projectName.toUpperCase().equalsIgnoreCase("ALL")) {
            projectName = "";
        }
        List<DetectedTable> detectedTableList = new ArrayList<>();
        try {
            // 获取数据源 2:本地的数据源
            List<DataResource> dataResourceList = getDataCenterVersion("2", "0");
            List<DataResource> dataResourceList1 = getDataCenterVersion("1", "0");
            if (dataResourceList1 != null && dataResourceList1.size() > 0) {
                dataResourceList.addAll(dataResourceList1);
            }
            String requestUrl = String.format("%s?resId=%s&projectName=%s", Common.ds_getTablesIncludeDetectedInfo, resId, projectName);
            log.info(">>>>>>开始调用接口：" + requestUrl);
            JSONObject detectedTable = restTemplate.getForObject(requestUrl, JSONObject.class);
            if (detectedTable.getInteger("status") == 1) {
                JSONArray result = detectedTable.getJSONArray("data");
                if (result.size() != 0) {
                    detectedTableList = result.toJavaList(DetectedTable.class);
                    for (DetectedTable realData : detectedTableList) {
                        for (DataResource resourceDta : dataResourceList) {
                            if (realData.getResId().equalsIgnoreCase(resourceDta.getResId())) {
                                realData.setResType(resourceDta.getResType());
                                realData.setResName(resourceDta.getResName());
                                realData.setCenterName(resourceDta.getCenterName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>获取探查表信息失败：", e);
        }
        return detectedTableList;
    }

    /**
     * 发送新增字段的接口信息  (已完成)
     *
     * @param createTableData
     */
    public String sendAddColumnInfo(CreateTableDataVO createTableData) {
        String requestUrl = Common.ds_addTableColumn;
        log.info(">>>>>>开始调用接口：" + requestUrl);
        String result = restTemplate.postForObject(requestUrl, createTableData, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
        if (jsonObject == null) {
            throw new NullPointerException(String.format("%s：调用接口返回的数据为空", ErrorCodeEnum.CHECK_ERROR));
        }
        if (jsonObject.getIntValue(Common.STATUS) == 1) {
            log.info("数据仓库请求的id值:{},返回的信息为:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.DATA));
            return jsonObject.getString(Common.DATA);
        } else {
            log.error("向数据仓库发送建表请求报错，请求的id值:{}，错误原因:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(ErrorCodeEnum.QUERY_SQL_ERROR, jsonObject.getString(Common.MESSAGE));
        }
    }

    /**
     * 1.8 接口的信息
     * 向数据仓库的接口发送建表请求 （完成）
     *
     * @param createTableData 是否建表成功
     */
    public String sendCreateTableInfo(CreateTableDataVO createTableData) {
        log.info(">>>>>>开始调用接口：" + Common.ds_createTable);
        String result = restTemplate.postForObject(Common.ds_createTable, createTableData, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
        if (jsonObject == null) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "调用接口返回的数据为空");
        }
        if (jsonObject.getIntValue(Common.STATUS) == 1) {
            log.info(">>>>>>数据仓库请求的id值{},返回的信息为：{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.DATA));
            return jsonObject.getString(Common.DATA);
        } else {
            log.error(">>>>>>向数据仓库发送建表请求报错，请求的id值{}，错误原因{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(ErrorCodeEnum.QUERY_SQL_ERROR, jsonObject.getString(Common.MESSAGE));
        }
    }

    /**
     * 获取探查分析推荐标准数据集
     *
     * @param dto 调用仓库所需参数
     */
    public List<TableSimilarInfo> getDataSimilarInfo(DataDefinitionDTO dto) {
        log.info(">>>>>>开始调用接口：" + Common.ds_getDatasetDetectResult);
        JSONObject jsonObject = restTemplate.postForObject(Common.ds_getDatasetDetectResult, dto, JSONObject.class);
        if (jsonObject == null) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "调用接口返回的数据为空");
        }
        if (jsonObject.getIntValue(Common.STATUS) == 1) {
            log.info("数据仓库请求的id值:{},返回的信息为:{}", jsonObject.getString(Common.REQUEST_UUID), jsonObject.getString(Common.DATA));
            TableSimilarInfo[] tableSimilarInfos = jsonObject.getObject("data", TableSimilarInfo[].class);
            return Arrays.asList(tableSimilarInfos);
        } else {
            log.error("向数据仓库发送建表请求报错，请求的id值:{}，错误原因:{}",
                    jsonObject.getString(Common.REQUEST_UUID),
                    jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(ErrorCodeEnum.QUERY_SQL_ERROR, jsonObject.getString(Common.MESSAGE));
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>标准化平台接口>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 每次修改标准表相关数据之后，都要调用标准化平台的修改接口
     */
    public JSONObject requestPushMetaInfo(List<PushMetaInfo> pushMetaInfoList) {
        String typeVersion = env.getProperty("dataProcessingVersion", "1.5");
        String requestUrl = "http://standardizeconfig/pushMetaInfo";
        if (!StringUtils.equalsIgnoreCase(typeVersion, "1.5")) {
            requestUrl = "http://STDCONFIGMNG/pushMetaInfo";
        }
        log.info(">>>>>>开始调用接口：" + requestUrl);
        PushMetaPojo pushMetaPojo = new PushMetaPojo();
        LoginUser user = AuthorizedUserUtils.getInstance().getAuthor();
        pushMetaPojo.setUserId(user == null ? "" : String.valueOf(user.getUserId()));
        pushMetaPojo.setUserName(user == null ? "" : String.valueOf(user.getUserName()));
        pushMetaPojo.setPushMetaInfoList(pushMetaInfoList);
        JSONObject response = restTemplate.postForObject(requestUrl, pushMetaPojo, JSONObject.class);
        return response;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据运维接口>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 发送操作日志
    public boolean saveOperatorLog(List<OperatorLogVO> operatorLogs) {
        try {
            String url = Common.DATAOPERATION_BASEURL + "/saveOperatorLog";
            log.info("开始调用接口：" + url);
            String result = restTemplate.postForObject(url, operatorLogs, String.class);
            JSONObject object = JSONObject.parseObject(result, JSONObject.class);
            if (object == null) {
                return false;
            }
            Integer status = object.getInteger("status");
            return status == 1 ? true : false;
        } catch (Exception e) {
            log.error(">>>>>>调用saveOperatorLog接口失败：", e);
            return false;
        }
    }

}
