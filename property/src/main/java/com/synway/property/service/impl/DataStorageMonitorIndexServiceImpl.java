package com.synway.property.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.property.common.PlatformType;
import com.synway.property.common.UrlConstants;
import com.synway.property.config.AsyManager;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.enums.SysCodeEnum;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.DataProcess.DataProcess;
import com.synway.property.pojo.*;
import com.synway.property.pojo.approvalinfo.ApprovalInfoParams;
import com.synway.property.pojo.approvalinfo.DataApproval;
import com.synway.property.pojo.datastoragemonitor.*;
import com.synway.property.pojo.formorganizationindex.ReceiveTag;
import com.synway.property.pojo.homepage.DataBaseState;
import com.synway.property.pojo.homepage.DataResourceParam;
import com.synway.property.pojo.opeartorLog.OperatorLog;
import com.synway.property.pojo.register.RegisterInfo;
import com.synway.property.pojo.register.RegisterState;
import com.synway.property.pojo.tablemanage.AdsOdpsTableInfo;
import com.synway.property.service.DataProcessService;
import com.synway.property.service.DataStorageMonitorIndexService;
import com.synway.property.service.DataStorageMonitorService;
import com.synway.property.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author 数据接入
 */
@Configuration
@Service
public class DataStorageMonitorIndexServiceImpl implements DataStorageMonitorIndexService {
    private static Logger logger = LoggerFactory.getLogger(DataStorageMonitorIndexServiceImpl.class);
    @Autowired
    DataStorageMonitorDao dataStorageMonitorDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    AsyManager asyManager;
    @Autowired
    private DataStorageMonitorService dataStorageMonitorService;
    @Autowired
    private DataProcessService dataProcessService;
    @Resource
    private Environment environment;
    @Autowired(required = false)
    private OdpsClient odpsClient;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    // 获取页面中 数据库状况的展示数据
    @Override
    public ServerResponse<List<DataBaseState>> getDataBaseStatus(String platFormType) {
        ServerResponse<List<DataBaseState>> serverResponse;
        try {
        List<DataBaseState> dataBaseStates = dataStorageMonitorDao.getDataBaseState();
//        for(int i=0;i<dataBaseStates.size();i++){
//            DataBaseState dataBaseState=dataBaseStates.get(i);
//            if (dataBaseState.getUsedCapacity()!=null){
//                dataBaseState.setUsedCapacity(Double.valueOf(dataBaseState.getUsedCapacity()).toString());
//            }
//            if (dataBaseState.getBareCapacity()!=null){
//                dataBaseState.setBareCapacity(Double.valueOf(dataBaseState.getBareCapacity()).toString());
//            }
//        }
            serverResponse = ServerResponse.asSucessResponse(dataBaseStates);
        } catch (Exception e) {
            logger.error("查询数据组织监控首页中数据库状况出错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询数据组织监控首页中数据库状况出错");
        }
        return serverResponse;
    }

    /**
     * 数据资源报表右上角的汇总信息
     *
     * @return
     */
    @Override
    public ServerResponse<DataQualitySummary> getDataQualitySummary() {
        ServerResponse<DataQualitySummary> serverResponse;
        // 质量检测
        DataQualitySummary dataQualitySummary;
        // 对于异常链路对账数，需要查询数据对账那边的接口，获取到数据
        String abnormalBillsSum;
        String statisticTime = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE);
        try {
            Map<String, Object> queryDict = new HashMap<>();
            queryDict.put("startTime", statisticTime + " 00:00:00");
            queryDict.put("endTime", statisticTime + " 23:59:59");
            int total;
            String url = UrlConstants.RECONCILIATION_BASEURL + "/reconciliation/getAbnormalReconciliationNum";
            JSONObject returnAccessJson = restTemplate.postForObject(url, queryDict, JSONObject.class);
            int status = returnAccessJson.getInteger("status");
            if (status == 1) {
                total = returnAccessJson.getJSONObject("data").getInteger("total");
                statisticTime = returnAccessJson.getJSONObject("data").getString("statisticTime");
            } else {
                total = 0;
            }
            abnormalBillsSum = String.valueOf(total);

        } catch (Exception e) {
            abnormalBillsSum = "0";
            logger.error("查询数据对账的接口报错" + ExceptionUtil.getExceptionTrace(e));
        }
        try {
            // 获取odps的版本号
            String dataWorkVersion = environment.getProperty("odps.version", "3");
            logger.info("baseApiVersion:" + dataWorkVersion);
            String dataPlatFormType = (String) cacheManager.getValue("dataPlatFormType");
            if (!PlatformType.ALI.equalsIgnoreCase(dataPlatFormType)) {
                dataWorkVersion = "3";
            }
            // 统计资产表今日数据量如果为0，则获取昨天数据
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
//            List<String> odpsProjectList = getProjectList();
            List<String> odpsProjectList = null;
            dataQualitySummary = dataStorageMonitorDao.getDataQualitySummary(dataWorkVersion,daysAgo,odpsProjectList);
            dataQualitySummary.setAbnormalBillsSum(abnormalBillsSum);
            dataQualitySummary.setAbnormalBillsTime(statisticTime);
            serverResponse = ServerResponse.asSucessResponse(dataQualitySummary);
        } catch (Exception e) {
            logger.error("查询数据组织资产汇总信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询数据组织资产汇总信息报错");
        }
        return serverResponse;
    }

    /**
     * 获取仓库 项目列表
     * @return
     */
    public List<String> getProjectList(){
        //根据仓库接口过滤项目名
        List<String> odpsProjectList = null;
        try{
            // 获取数据中心信息（本地仓）
            JSONArray dataResourceLocal = new JSONArray();
            String getDataResourceForLocal = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0",String.class);
            if(StringUtils.isNotBlank(getDataResourceForLocal) && "1".equals(JSONObject.parseObject(getDataResourceForLocal).getString("status"))){
                String localData = JSONObject.parseObject(getDataResourceForLocal).getString("data");
                dataResourceLocal = JSONArray.parseArray(localData);
            }
            // 根据数据源查询项目空间
            if(StringUtils.isNotBlank(String.valueOf(dataResourceLocal))){
                for (int i=0;i<dataResourceLocal.size();i++){
                    JSONObject dataResource = JSONObject.parseObject(dataResourceLocal.getString(i));
                    if ("odps".equals(dataResource.getString("resType").toLowerCase())){
                        String resourceId = dataResource.getString("resId");
                        String getProjectListStr = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getProjectList?resId=" + resourceId,String.class);
                        if(StringUtils.isNotBlank(getProjectListStr) && "1".equals(JSONObject.parseObject(getProjectListStr).getString("status"))){
                            String projectsData = JSONObject.parseObject(getProjectListStr).getString("data");
                            JSONArray projects = JSONArray.parseArray(projectsData);
                            if (projects.size()>0){
                                for (int j = 0; j<projects.size(); j++){
                                    odpsProjectList.add(projects.getString(j));
                                }
                            }
                        }
                    }
                }
            }

//            String requestUrl = TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/outputProjectNamesByInterface";
//            JSONObject returnObj = restTemplate.getForObject(requestUrl, JSONObject.class);
//            if(returnObj!=null){
//                JSONObject jsonObject = returnObj.getJSONObject("data");
//                odpsProjectList = JSONArray.parseArray(jsonObject.getString("odps"), String.class);
//            }
        } catch (Exception e) {
            logger.error("仓库过滤项目名接口报错");
            return null;
        }
        return odpsProjectList;
    }

    @Override
    public ServerResponse<String> insertOracleAddTable(List<NeedAddRealTimeTable> allNeedAddRealTimeTableList, String type) {
        ServerResponse<String> serverResponse;
        try {
            logger.info("开始插入需要监控的实时表名");
            allNeedAddRealTimeTableList.parallelStream().forEach(
                    item -> {
                        item.setTableUuid(UUIDUtil.getUUID());
                        dataStorageMonitorDao.insertOracleAddTable(item, type);
                    }
            );
            String message = "数据成功插入";
            serverResponse = ServerResponse.asSucessResponse(message);
        } catch (Exception e) {
            logger.error("将需要添加的表名加入到数据库中报错" + ExceptionUtil.getExceptionTrace(e));
            String message = "数据插入报错";
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        return serverResponse;
    }

    @Override
    public ServerResponse<List<RealTimeTableFullMessage>> getAllRealTimeTableFullMessage() {
        List<RealTimeTableFullMessage> realTimeTableFullMessageList;
        ServerResponse<List<RealTimeTableFullMessage>> serverResponse;
        String todayNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        String yesToday = DateUtil.formatDate(calendar.getTime(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        try {
            realTimeTableFullMessageList = dataStorageMonitorDao.getAllRealTimeTableFullMessage(todayNow,yesToday);
            for (RealTimeTableFullMessage oneRealTimeTableFullMessage : realTimeTableFullMessageList) {
                Long allRecordsSum = Long.valueOf(oneRealTimeTableFullMessage.getTableAllSum());
                Long tableTodaySum = Long.valueOf(oneRealTimeTableFullMessage.getTableTodaySum());
                if (allRecordsSum > Long.parseLong("100000000")) {
                    oneRealTimeTableFullMessage.setTableAllSum(String.format("%.2f", allRecordsSum * 1.0 / 100000000));
                    oneRealTimeTableFullMessage.setDataUnit("亿");
                } else if (allRecordsSum > Long.parseLong("10000")) {
                    oneRealTimeTableFullMessage.setTableAllSum(String.format("%.2f", allRecordsSum * 1.0 / 10000));
                    oneRealTimeTableFullMessage.setDataUnit("万");
                } else {
                    oneRealTimeTableFullMessage.setTableAllSum(String.valueOf(allRecordsSum));
                    oneRealTimeTableFullMessage.setDataUnit("");
                }
                if (tableTodaySum > Long.parseLong("100000000")) {
                    oneRealTimeTableFullMessage.setTableTodaySum(String.format("%.2f", tableTodaySum * 1.0 / 100000000));
                    oneRealTimeTableFullMessage.setTodayDataUnit("亿");
                } else if (tableTodaySum > Long.parseLong("10000")) {
                    oneRealTimeTableFullMessage.setTableTodaySum(String.format("%.2f", tableTodaySum * 1.0 / 10000));
                    oneRealTimeTableFullMessage.setTodayDataUnit("万");
                } else {
                    oneRealTimeTableFullMessage.setTableTodaySum(String.valueOf(tableTodaySum));
                    oneRealTimeTableFullMessage.setTodayDataUnit("");
                }
            }
            serverResponse = ServerResponse.asSucessResponse(realTimeTableFullMessageList);
        } catch (Exception e) {
            logger.error("查询实时表数据信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询实时表数据信息报错");
        }
        return serverResponse;
    }

    //根据选择的数据资源目录的三大分类之一获取对应的一级分类信息

    @Override
    public ServerResponse<List<PageSelectOneValue>> getPrimaryClassifyData(String mainClassify) {
        ServerResponse<List<PageSelectOneValue>> serverResponse;
        String errorMessage;
        List<PageSelectOneValue> oneResultList;
        try {
            int daysAgo = 0;
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            daysAgo = todayAssetsCount<100?1:0;
            if (mainClassify.equalsIgnoreCase(UrlConstants.DATA_ORGANIZATION_CLASSIFY)) {
                /* 数据组织分类 */
                oneResultList = dataStorageMonitorDao.getPrimaryClassifyData(mainClassify,daysAgo);
            } else if (mainClassify.equalsIgnoreCase(UrlConstants.DATA_SOURCE_CLASSIFY)) {
                /* 数据来源分类 */
                oneResultList = dataStorageMonitorDao.getPrimaryClassifyData(mainClassify,daysAgo);
            } else if (mainClassify.equalsIgnoreCase(UrlConstants.DATA_RESOURCE_CLASSIFY)) {
                /* 数据资源分类 */
                oneResultList = dataStorageMonitorDao.getPrimaryClassifyData(mainClassify,daysAgo);
            } else {
                errorMessage = "主分类名【" + mainClassify + "】错误";
                logger.error("主分类名【" + mainClassify + "】错误");
                serverResponse = ServerResponse.asErrorResponse(errorMessage);
                return serverResponse;
            }
            if (oneResultList.size() == 1 && oneResultList.contains(null)) {
                oneResultList = new ArrayList<>();
            }
            serverResponse = ServerResponse.asSucessResponse(oneResultList);
        } catch (Exception e) {
            errorMessage = "查询一级分类的信息报错";
            logger.error("查询一级分类的信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(errorMessage);
        }
        return serverResponse;
    }

    //根据选择的数据资源目录的三大分类之一 和 一级分类信息 来获取二级分类信息
    @Override
    public ServerResponse<List<PageSelectOneValue>> getSecondaryClassifyData(String mainClassify, String primaryClassifyCode, String isThreeLevel) {
        ServerResponse<List<PageSelectOneValue>> serverResponse;
        List<PageSelectOneValue> oneResultList;
        String errorMessage = "";
        try {
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            if (mainClassify.equalsIgnoreCase(UrlConstants.DATA_ORGANIZATION_CLASSIFY)) {
                /*数据组织分类*/
                oneResultList = dataStorageMonitorDao.getSecondaryClassifyData(mainClassify, primaryClassifyCode,daysAgo,isThreeLevel);
                if (oneResultList.size() == 1 && UrlConstants.NULL.equals(oneResultList.get(0).getValue())) {
                    oneResultList = null;
                }
            } else if (mainClassify.equalsIgnoreCase(UrlConstants.DATA_SOURCE_CLASSIFY)) {
                /*数据来源分类*/
                oneResultList = dataStorageMonitorDao.getSecondaryClassifyData(mainClassify, primaryClassifyCode,daysAgo,isThreeLevel);
                if (oneResultList.size() == 1 && UrlConstants.NULL.equals(oneResultList.get(0).getValue())) {
                    oneResultList = null;
                }
            } else if (mainClassify.equalsIgnoreCase(UrlConstants.DATA_RESOURCE_CLASSIFY)) {
                /*数据资源分类*/
                oneResultList = dataStorageMonitorDao.getSecondaryClassifyData(mainClassify, primaryClassifyCode,daysAgo,isThreeLevel);
            } else {
                errorMessage = "查询条件中主分类名【" + mainClassify + "】错误";
                logger.error("查询条件中主分类名【" + mainClassify + "】错误");
                serverResponse = ServerResponse.asErrorResponse(errorMessage);
                return serverResponse;
            }
            serverResponse = ServerResponse.asSucessResponse(oneResultList);
        } catch (Exception e) {
            errorMessage = "查询二级分类的信息报错" + ExceptionUtil.getExceptionTrace(e);
            logger.error(errorMessage);
            serverResponse = ServerResponse.asErrorResponse("查询二级分类的信息报错");
        }
        return serverResponse;
    }

    @Override
    public List<AdsOdpsTableInfo> getAdsOdpsTableInfoByRestFul(String stage, String preName, String tableName) throws Exception {
        logger.info("开始查询根据项目名以及模糊表名，来获取表的列表信息=================================");
        AdsOdpsTableInfo[] result = restTemplate.getForObject(UrlConstants.DATAGOVERNANCE_BASEURL + "/datagovernance/dataOperation/tableList?stage={stage}" +
                "&preName={preName}&tableName={tableName}", AdsOdpsTableInfo[].class, stage, preName, tableName);
        List<AdsOdpsTableInfo> AdsOdpsTableInfoList = Arrays.asList(result);
        logger.info("查询根据项目名以及模糊表名，来获取表的列表信息结束=================================");
        return AdsOdpsTableInfoList;
    }

    /**
     * 查询tablemange的接口 查询指定数据库对应的所有项目名
     *
     * @param stage
     * @return
     */
    @Override
    public ServerResponse<List<String>> getProjectNameByRestFul(String stage, String monitorType) {
        logger.info("查询【" + monitorType + "】表名数据库【" + stage + "】所有项目名=================================");
        ServerResponse<List<String>> serverResponse;
        List<String> projectNameList = new ArrayList<>();
        try {
            if (monitorType.equalsIgnoreCase(UrlConstants.MONITOR_NO)) {
                //不知道为什么要这个逻辑，未改
//                adsTableUtil.
                String[] projectNameArray;
                projectNameArray = restTemplate.getForObject(
                        UrlConstants.DATAGOVERNANCE_BASEURL + "/datagovernance/dataOperation/getProjectName?stage={stage}",
                        String[].class, stage);
                if (projectNameArray != null) {
                    projectNameList = Arrays.asList(projectNameArray);
                }
                serverResponse = ServerResponse.asSucessResponse(projectNameList);
            } else if (monitorType.equalsIgnoreCase(UrlConstants.MONITOR_OK)) {
                //获取已经插入的项目名称
                projectNameList = dataStorageMonitorDao.getAdsProjectAdd();
                if (projectNameList != null && projectNameList.size() > 0) {
                    projectNameList.add("全部");
                }
                serverResponse = ServerResponse.asSucessResponse(projectNameList);
            } else {
                serverResponse = ServerResponse.asErrorResponse("传入的参数monitorType为【" + monitorType + "】不正确");
            }
        } catch (Exception e) {
            logger.error("根据tablemange接口查询数据库【" + stage + "】所有项目名报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("根据tablemange接口查询数据库【" + stage + "】所有项目名报错");
        }
        if (projectNameList != null) {
            logger.info("根据tablemange接口查询数据库【" + stage + "】所有项目名为：\n" + projectNameList.toString());
        }
        logger.info("根据tablemange接口查询数据库【" + stage + "】所有项目名结束=================================");
        return serverResponse;
    }

    /**
     * 据分级分类的一级和二级的相关信息查询表组织资产的汇总信息
     * 后端不分页，使用前端分页
     *
     * @param mainClassify        主分类名称
     * @param primaryClassifyCh   一级分类名称  为空表示查询所有
     * @param secondaryClassifyCh 二级分类名称 为空表示查询所有
     * @return
     */
    @Override
    public PageVO<SummaryTableByClassify> getSummaryTableByClassify(String mainClassify, String primaryClassifyCh, String secondaryClassifyCh, String threeValue) {
        PageVO<SummaryTableByClassify> pageVO = new PageVO<>();
        try {
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            List<SummaryTableByClassify> allSummaryTableByClassifyList = dataStorageMonitorDao.getAllSummaryTableByClassifyList(
                    mainClassify, primaryClassifyCh, secondaryClassifyCh,threeValue, daysAgo);
            List<String> filterSec = allSummaryTableByClassifyList.stream().map(d -> d.getSecondaryClassifyCh()).distinct().collect(toList());
            pageVO.setRows(allSummaryTableByClassifyList);
            pageVO.setFilterSec(filterSec);
        } catch (Exception e) {
            logger.error("获取表组织资产的汇总信息报错\n" + ExceptionUtil.getExceptionTrace(e));
        }
        return pageVO;
    }

    @Override
    public ServerResponse<DetailedTableResultMap> getDataContent(RequestParameter requestParameter) {
        ServerResponse<DetailedTableResultMap> serverResponse;

        // 先获取今日数据,如果今日无数据，则获取昨日数据
        int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
        int daysAgo = todayAssetsCount<100?1:0;
        // 获取过滤列表返回前端
        List<DetailedTableByClassify> allDataContentFilter = dataStorageMonitorDao.getAllDataContentFilter(daysAgo);

        // 参数解析
        List<ReceiveTag> classifyTagsList = requestParameter.getClassifyTags();
        String input = requestParameter.getInput();
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
        int pageIndex = requestParameter.getPageIndex();
        int pageSize = requestParameter.getPageSize();
        String sortName = requestParameter.getSortName();
        String sortOrder = requestParameter.getSortOrder();
        // 获取前端传过来的过滤列表
        String[] objectStateStatus = StringUtils.isBlank(requestParameter.getObjectStateStatus())?null:requestParameter.getObjectStateStatus().split(",");
        String[] productStageStatus = StringUtils.isBlank(requestParameter.getProductStageStatus())?null: requestParameter.getProductStageStatus().split(",");
        String[] registerStateStatus = StringUtils.isBlank(requestParameter.getRegisterStateStatus()) ? null: requestParameter.getRegisterStateStatus().split(",");
        String[] tableProjectStatus =StringUtils.isBlank(requestParameter.getTableProjectStatus())?null: requestParameter.getTableProjectStatus().split(",");
        String[] tableStateStatus =StringUtils.isBlank(requestParameter.getTableStateStatus())?null: requestParameter.getTableStateStatus().split(",");
        String[] tableidStatus = StringUtils.isBlank(requestParameter.getTableidStatus())?null:requestParameter.getTableidStatus().split(",");
        String[] updatePeriodStatus = StringUtils.isBlank(requestParameter.getUpdatePeriodStatus())?null:requestParameter.getUpdatePeriodStatus().split(",");
        String[] isStandardStatus = StringUtils.isBlank(requestParameter.getIsStandardStatus())?null:requestParameter.getIsStandardStatus().split(",");
        List<String> productStageStatusList = null;
        if(productStageStatus!=null){
            productStageStatusList = Arrays.stream(productStageStatus).map(
                    e-> SysCodeEnum.getCodeByNameAndType(e, "PRODUCTSTAGE")
            ).collect(toList());
        }

        // 排序
        Page page = PageHelper.startPage(pageIndex, pageSize);
        String orderBy = "";
        if (StringUtils.isNotEmpty(sortName)) {
            if (StringUtils.isEmpty(sortOrder)){
                sortOrder = "desc";
            }
            if ("tableSize".equals(sortName)){
                orderBy = "TABLE_SIZE " + sortOrder;
            }
            if ("yesterdayCount".equals(sortName)){
                orderBy = "YESTERDAY_COUNT " + sortOrder;
            }
            if ("tableAllCount".equals(sortName)){
                orderBy = "TABLE_ALL_COUNT " + sortOrder;
            }
        }
        if (StringUtils.isNotEmpty(sortName)) {
            if ("tableSize".equals(sortName) || "yesterdayCount".equals(sortName) || "tableAllCount".equals(sortName)) {
                sortName = "to_number(" + sortName + ")";
            }
            if (StringUtils.isEmpty(sortOrder)){
                sortOrder = "desc";
            }
            orderBy = sortName + " " + sortOrder;
            page.setUnsafeOrderBy(orderBy);
        }
        DetailedTableResultMap detailedTableResultMap = new DetailedTableResultMap();
        List<ReceiveTag> organizationClassify = new ArrayList<>();
        List<ReceiveTag> sourceClassify = new ArrayList<>();
        String registerState = "";
        String usingTagsState = "";
        String startLastModifiedTime = "";
        String endLastModifiedTime = "";
//        List<String> labels = new ArrayList<>();
        List<String> projectList = new ArrayList<>();
        List<String> productStageList = new ArrayList<>();
        List<String> updatePeriodList = new ArrayList<>();
        try {
            for (ReceiveTag rt : classifyTagsList) {
                if (rt != null) {
                    if ("dataOrganizationClassify".equals(rt.getMainClassify())) {
                        organizationClassify.add(rt);
                    } else if ("dataSourceClassify".equals(rt.getMainClassify())) {
                        sourceClassify.add(rt);
                    }
                }
            }
//            if (labelTagsList.size() > 0) {
//                labels = dataStorageMonitorDao.getLabelsByName(labelTagsList);
//            }
            if (registerTagsList.size() > 0) {
                if (registerTagsList.size() < 2) {
                    registerState = registerTagsList.get(0).getLabel();
                }
            }
            if (usingTagsList.size() > 0) {
                if (usingTagsList.size() < 2) {
                    usingTagsState = usingTagsList.get(0).getLabel();
                }
            }
            if (lastModifiedTimeList.size() > 0) {
                startLastModifiedTime = lastModifiedTimeList.get(0);
                endLastModifiedTime = lastModifiedTimeList.get(1);
            }
            // 获取数据
            List<DetailedTableByClassify> allDataContentByTags = dataStorageMonitorDao.getAllDataContentByTags(
                    organizationClassify, sourceClassify, input, registerState, usingTagsState, storageTagsList
//                    , labels
                    , termSetting, startLastModifiedTime, endLastModifiedTime, startRecordNum, endRecordNum, startStorageSize, endStorageSize, queryTable, daysAgo
                    , objectStateStatus,productStageStatusList,registerStateStatus,tableProjectStatus,tableStateStatus,tableidStatus,updatePeriodStatus,isStandardStatus,
                    orderBy
            );

            PageInfo<DetailedTableByClassify> pageInfo = new PageInfo<>(allDataContentByTags);
//            List<Label> labelList = dataStorageMonitorDao.getAllLabels();
//            Map<String, String> labelMap = labelList.parallelStream().collect(Collectors.toMap(item -> item.getLabelCode(), item -> item.getLabelName()));

            // 组装给前端的过滤列表，先这么写，后面要单独拎出来写一个接口
            allDataContentFilter.forEach(detailedTableByClassify -> {
                if (StringUtils.isNotBlank(detailedTableByClassify.getProductStage())) {
                    detailedTableByClassify.setProductStage(
                            SysCodeEnum.getNameByCodeAndType(detailedTableByClassify.getProductStage(), "PRODUCTSTAGE")
                    );
                }
                if (!projectList.contains(detailedTableByClassify.getTableProject())) {
                    projectList.add(detailedTableByClassify.getTableProject());
                }
                if (StringUtils.isNotBlank(detailedTableByClassify.getProductStage()) && !productStageList.contains(detailedTableByClassify.getProductStage().trim())) {
                    productStageList.add(detailedTableByClassify.getProductStage());
                }
                if (StringUtils.isNotBlank(detailedTableByClassify.getUpdatePeriod()) && !updatePeriodList.contains(detailedTableByClassify.getUpdatePeriod().trim())) {
                    updatePeriodList.add(detailedTableByClassify.getUpdatePeriod());
                }
            });
            allDataContentByTags.forEach(detailedTableByClassify -> {
//                List<String> label;
//                if (detailedTableByClassify.getLabels() != null) {
//                    StringBuilder sb = new StringBuilder();
//                    label = Arrays.asList(detailedTableByClassify.getLabels().split(","));
//                    label.stream().forEach(item -> sb.append(labelMap.get(item) + " "));
//                    detailedTableByClassify.setLabels(sb.toString());
//                }
                if (StringUtils.isNotBlank(detailedTableByClassify.getProductStage())) {
                    detailedTableByClassify.setProductStage(
                            SysCodeEnum.getNameByCodeAndType(detailedTableByClassify.getProductStage(), "PRODUCTSTAGE")
                    );
                }
                if (!projectList.contains(detailedTableByClassify.getTableProject())) {
                    projectList.add(detailedTableByClassify.getTableProject());
                }
                if (StringUtils.isNotBlank(detailedTableByClassify.getProductStage()) && !productStageList.contains(detailedTableByClassify.getProductStage().trim())) {
                    productStageList.add(detailedTableByClassify.getProductStage());
                }
                if (StringUtils.isNotBlank(detailedTableByClassify.getUpdatePeriod()) && !updatePeriodList.contains(detailedTableByClassify.getUpdatePeriod().trim())) {
                    updatePeriodList.add(detailedTableByClassify.getUpdatePeriod());
                }
            });
            Map<String, String> projectMap;
            List<Map<String, String>> paramList = new ArrayList();
            for (String s : projectList) {
                projectMap = new HashMap<>();
                projectMap.put("text", s);
                projectMap.put("value", s);
                paramList.add(projectMap);
            }
            List<Map<String, String>> paramList1 = new ArrayList();
            for (String s : productStageList) {
                projectMap = new HashMap<>();
                projectMap.put("text", s);
                projectMap.put("value", s);
                paramList1.add(projectMap);
            }
            List<Map<String, String>> paramList2 = new ArrayList();
            for (String s : updatePeriodList) {
                projectMap = new HashMap<>();
                projectMap.put("text", s);
                projectMap.put("value", s);
                paramList2.add(projectMap);
            }
            paramList.sort(Comparator.comparing(o -> o.get("text")));
            paramList1.sort(Comparator.comparing(o -> o.get("text")));
            paramList2.sort(Comparator.comparing(o -> o.get("text")));
            detailedTableResultMap.setProjectList(paramList);
            detailedTableResultMap.setProductStageList(paramList1);
            detailedTableResultMap.setUpdatePeriodList(paramList2);
            detailedTableResultMap.setData(allDataContentByTags);
            detailedTableResultMap.setTotal(pageInfo.getTotal());
            serverResponse = ServerResponse.asSucessResponse(detailedTableResultMap);
        } catch (Exception e) {
            logger.error("获取表组织资产的详细信息报错:", e);
            serverResponse = ServerResponse.asErrorResponse("获取表组织资产的详细信息报错");
        }
        return serverResponse;
    }

    public ServerResponse<String> getDetectId(DetailedTableByClassify detailedTableByClassify){
        ServerResponse<String> serverResponse;
        try {
            String detectId = dataStorageMonitorDao.getDetectId(detailedTableByClassify);
            serverResponse = ServerResponse.asSucessResponse(detectId);
        }catch (Exception e){
            logger.error("获取DetectId失败： \n" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asSucessResponse("获取DetectId失败");
        }
        return serverResponse;
    }

    @Override
    public ServerResponse<NumStorageByPrimaryClassify> getRecordsStorageByPrimaryClassify(String cateGoryName) {
        ServerResponse<NumStorageByPrimaryClassify> serverResponse;
        try {
            if (cateGoryName.equalsIgnoreCase(UrlConstants.DATA_ORGANIZATION_CLASSIFY)
                    || cateGoryName.equalsIgnoreCase(UrlConstants.DATA_SOURCE_CLASSIFY)
//                    || cateGoryName.equalsIgnoreCase(TableOrganizationConstant.DATA_LABELS_CLASSIFY)
            ) {
                // 数据组织分类
                // 统计资产表今日数据量如果为0，则获取昨天数据
                int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
                int daysAgo = todayAssetsCount<100?1:0;
                List<Map<String, Object>> oneResult = dataStorageMonitorDao.getRecordsStorageByPrimaryClassifyMap(cateGoryName,daysAgo);
                if (oneResult != null) {
                    NumStorageByPrimaryClassify numStorageByPrimaryClassify = new NumStorageByPrimaryClassify();
                    List<String> primaryClassNameList = new ArrayList<>();
                    List<Double> recordsNumberList = new ArrayList<>();
                    List<Double> storageSizeList = new ArrayList<>();
                    boolean isHailiang = cacheManager.getValue("dsType").toString().equalsIgnoreCase("hailiang");
                    for (Map<String, Object> oneMap : oneResult) {
                        String primaryClassName = String.valueOf(oneMap.getOrDefault("PRIMARY_NAME", "未知"));
                        Double recordsNumberOne = Double.valueOf(String.valueOf(oneMap.getOrDefault("RECORDS", "0")));
                        Double storageSizeOne = Double.valueOf(String.valueOf(oneMap.getOrDefault("TABLESIZE", "0.0")));
                        if (isHailiang){
                            primaryClassName = String.valueOf(oneMap.getOrDefault("primary_name", "未知"));
                            recordsNumberOne = Double.valueOf(String.valueOf(oneMap.getOrDefault("records", "0")));
                            storageSizeOne = Double.valueOf(String.valueOf(oneMap.getOrDefault("tablesize", "0.0")));
                        }
                        primaryClassNameList.add(primaryClassName);
                        recordsNumberList.add(recordsNumberOne);
                        storageSizeList.add(storageSizeOne);
                    }
                    numStorageByPrimaryClassify.setCateGoryName(cateGoryName);
                    numStorageByPrimaryClassify.setPrimaryClassifyList(primaryClassNameList);
                    numStorageByPrimaryClassify.setRecordsNumber(recordsNumberList);
                    numStorageByPrimaryClassify.setStorageSize(storageSizeList);
                    numStorageByPrimaryClassify.setRecordsUnit(UrlConstants.RECORDS_UNIT);
                    numStorageByPrimaryClassify.setStorageUnit(UrlConstants.STORAGE_UNIT);
                    serverResponse = ServerResponse.asSucessResponse(numStorageByPrimaryClassify);
                } else {
                    serverResponse = ServerResponse.asErrorResponse("从数据库中查询到的数据量为空");
                }
            } else {
                //  传入的值错误
                serverResponse = ServerResponse.asErrorResponse("传入的大类名称【" + cateGoryName + "】不对");
            }
        } catch (Exception e) {
            logger.error("查询柱状图报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询柱状图报错");
        }
        return serverResponse;
    }

    /**
     * 删除选定的ADS实时表监控的表
     */
    @Override
    public ServerResponse<String> delRealTableMonitor(List<NeedAddRealTimeTable> delDataList) {
        int delNum = dataStorageMonitorDao.delRealTableMonitorDao(delDataList);
//        String message = "本次需要删除【" + delDataList.size() + "】项数据" + "成功删除【" + delNum + "】项数据";
        String message = "成功删除数据";
        return ServerResponse.asSucessResponse(message);
    }

    /**
     * 加载数据资产统计的左侧树
     *
     * @param req
     * @param isQueryTable
     * @param showLabel
     * @param showAll
     * @return
     */
    @Override
    public List<TreeNode> externalgetTableOrganizationTree(GetTreeReq req, Boolean isQueryTable, Boolean showLabel, Boolean showAll) {
        logger.info("查询的参数为：" + req.toString() + " 是否精确到表:  " + isQueryTable);
        List<TreeNode> treeNodes = new ArrayList<>();
        List<StandardTableRelation> standardRelationList;
        //  数据组织资产那边需要查询所有的分类 标准和非标准都要
        int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
        int daysAgo = todayAssetsCount<100?1:0;
        if (showAll) {
            standardRelationList = dataStorageMonitorDao.getOrganizationZTreeNodesAll(req.getNodeName(), req.getType(),daysAgo);
        } else {
            standardRelationList = dataStorageMonitorDao.getOrganizationZTreeNodes(req.getNodeName(), req.getType());
        }
        List<String> parimaryClassList = standardRelationList.stream().map(StandardTableRelation::getPrimaryClassifyCh).distinct().collect(toList());
        Map<String, List<StandardTableRelation>> parimaryClassMap = standardRelationList.stream().collect(Collectors.groupingBy(StandardTableRelation::getPrimaryClassifyCh));
        for (String parent : parimaryClassList) {
            //  一级分类的内容
            TreeNode levelOne = new TreeNode();
            levelOne.setId(parent);
            // 排序对应
            if ("原始库".equalsIgnoreCase(parent) || "公安执法与执勤数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(1);
            }else if ("资源库".equalsIgnoreCase(parent) || "互联网数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(2);
            }else if ("主题库".equalsIgnoreCase(parent) || "电信数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(3);
            }else if ("知识库".equalsIgnoreCase(parent) || "物联网数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(4);
            }else if ("业务库".equalsIgnoreCase(parent) || "视频数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(5);
            }else if ("业务要素索引库".equalsIgnoreCase(parent) || "行业数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(6);
            }else if ("其他".equalsIgnoreCase(parent) || "其它数据".equalsIgnoreCase(parent)){
                levelOne.setSortLevel(7);
            }
            List<StandardTableRelation> levelOneList = parimaryClassMap.get(parent);
            if (showLabel) {
                levelOne.setLabel(parent + "(" + levelOneList.size() + ")");
            } else {
                levelOne.setLabel(parent);
            }
            levelOne.setLevel(1);
            // 一级分类之后的子节点数组
            List<TreeNode> chlidOne = new ArrayList<>();
            List<String> secondaryClassList = levelOneList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh())).map(StandardTableRelation::getSecondaryClassifyCh).distinct().collect(toList());
            Map<String, List<StandardTableRelation>> secondaryClassMap = levelOneList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh())).collect(Collectors.groupingBy(StandardTableRelation::getSecondaryClassifyCh));
            for (String child : secondaryClassList) {
                // 二级分类的数据
                TreeNode levelTwo = new TreeNode();
                levelTwo.setId(child);
                levelTwo.setParent(parent);
                List<StandardTableRelation> levelTwoList = secondaryClassMap.get(child);
                if (showLabel) {
                    levelTwo.setLabel(child + "(" + levelTwoList.size() + ")");
                } else {
                    levelTwo.setLabel(child);
                }
                levelTwo.setLevel(2);
                List<TreeNode> chlidTwo = new ArrayList<>();
                // 对于三级分类，有些表id存在三级分类，有些表id不存在三级分类，所以需要区分
                // 存在三级分类的数据
                List<String> threeNotNullClassList = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
                        .map(StandardTableRelation::getThreeClassifyCh).distinct().collect(toList());
                Map<String, List<StandardTableRelation>> threeClassMap = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
                        .collect(Collectors.groupingBy(StandardTableRelation::getThreeClassifyCh));
                for (String twoChild : threeNotNullClassList) {
                    //  三级分类
                    TreeNode levelThree = new TreeNode();
                    levelThree.setId(twoChild);
                    levelThree.setParent(child);
                    levelThree.setGrandpar(parent);
                    List<StandardTableRelation> levelThreeList = threeClassMap.get(twoChild);
                    if (showLabel) {
                        levelThree.setLabel(twoChild + "(" + String.valueOf(levelThreeList.size()) + ")");
                    } else {
                        levelThree.setLabel(twoChild);
                    }
                    levelThree.setLevel(3);
                    List<TreeNode> chlidThree = new ArrayList<>();
                    if (isQueryTable || 1==req.getType()) {
                        for (StandardTableRelation s1 : levelThreeList) {
                            // 以下为 具体的表信息
                            TreeNode tableTreeNode = new TreeNode();
                            tableTreeNode.setId(s1.getTableId());
                            tableTreeNode.setLabel(s1.getTableNameCh());
                            tableTreeNode.setLevel(4);
                            tableTreeNode.setParent(twoChild);
                            chlidThree.add(tableTreeNode);
                        }
                    }
                    levelThree.setChildren(chlidThree);
                    chlidTwo.add(levelThree);
                }
                // 存储三级分类是空的数据
                if (isQueryTable || 1==req.getType()) {
                    for (StandardTableRelation standardTableRelation : levelTwoList) {
                        if (StringUtils.isEmpty(standardTableRelation.getThreeClassifyCh())) {
                            TreeNode levelTwoTable = new TreeNode();
                            levelTwoTable.setId(standardTableRelation.getTableId());
                            levelTwoTable.setLabel(standardTableRelation.getTableNameCh());
                            levelTwoTable.setLevel(3);
                            levelTwoTable.setParent(child);
                            chlidTwo.add(levelTwoTable);
                        }
                    }
                }
                levelTwo.setChildren(chlidTwo);
                chlidOne.add(levelTwo);
            }
            levelOne.setChildren(chlidOne);
            treeNodes.add(levelOne);
        }
        logger.info("查询到的数据为：" + treeNodes.toString());
        Collections.sort(treeNodes, new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                if (o1.getSortLevel()>o2.getSortLevel())
                {
                    return 1;
                }else if (o1.getSortLevel()<o2.getSortLevel()){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        return treeNodes;
    }

    private Boolean getApprovalStatus() {
        String json;
        //缓存name
        String cacheName = "approval";
        try {
            json = restTemplate.getForObject(UrlConstants.DATAGOVERNANCE_BASEURL + "/datagovernance/navbar/getNavStatusByName?name={name}", String.class, "审批中心");
            cacheManager.addOrUpdateCache(cacheName, JSONObject.parseObject(json).get("data"));
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            cacheManager.addOrUpdateCache(cacheName, false);
        }
        return (Boolean) cacheManager.getValue(cacheName);
    }

    @Override
    synchronized
    public ServerResponse registerApproval(String toJsonString, String[] tableIds, JSONObject[] tableList, String userName, String userId, String organId) throws Exception {
        logger.info("传递的参数为：" + toJsonString);
        asyManager.addTask(() -> dataStorageMonitorService.getDataResourceInfo());
        if (!getApprovalStatus()) {
            return updateRegisterState(tableIds, tableList, "3",userId);
        }
        /*拼接传递给 审批流程 的接口*/
        StringBuilder applicationInfo = new StringBuilder();
        applicationInfo.append("【").append(userName).append("】申请向资源服务平台注册");
        List<DataApproval> dataApprovals = new ArrayList<>();
        for (JSONObject dtc : tableList) {
            DetailedTableByClassify index = JSONObject.toJavaObject(dtc, DetailedTableByClassify.class);
            DataApproval dataApproval = new DataApproval();
            if (StringUtils.isNotBlank(index.getTableid())) {
                dataApproval.setTableId(index.getTableid());
            }
            if (StringUtils.isNotBlank(index.getName())) {
                applicationInfo.append("【").append(index.getName()).append("】");
            }
            if (StringUtils.isNotBlank(index.getTableNameEn())) {
                dataApproval.setTableNameEn(index.getTableNameEn());
            }
            dataApproval.setObjectId(index.getObjectId());
            dataApproval.setApprovalType("register");
            dataApprovals.add(dataApproval);
        }
        applicationInfo.append("数据资源。");
        ServerResponse serverResponse = validateApprovals(dataApprovals);
        if (serverResponse != null) {
            return serverResponse;
        }
        ApprovalInfoParams approvalInfoParams = buildApprovalInfoParams("资产管理", "register",
                "注册", applicationInfo.toString(), toJsonString,
                "http://" + environment.getProperty("server.address") + ":" +
                        environment.getProperty("server.port") +
                        "/dataOrganizationMonitoring/ClassifyInterface/updateRegisterState");
        Map paramMap = startApproval(approvalInfoParams, dataApprovals);
        List tableIdList = dataApprovals.stream().map(DataApproval::getTableNameEn).collect(toList());
        paramMap.put("data", tableIdList);
        return ServerResponse.asSucessResponse(paramMap);
    }

    private ServerResponse validateApprovals(List<DataApproval> dataApprovals) throws Exception {
        try {
            List<DataApproval> errorApprovals = dataStorageMonitorDao.getApprovalNum(dataApprovals);
            if (errorApprovals.size() != 0) {
//                return ServerResponse.asErrorResponse("含有在审批中的项", errorApprovals);
                return ServerResponse.asErrorResponse("含有在审批中的项");
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw new Exception("查询审批信息失败");
        }
        return null;
    }

    @Override
    public ServerResponse updateLifeCycleApproval(RequestParameter requestParam) throws Exception {
        String applicationInfo = "【" + requestParam.getUserName() + "】申请将表【" +
                requestParam.getTableProject().split("->")[1] +
                "." + requestParam.getTableNameEn() + "】的生命周期天数由【" +
                requestParam.getOldValue() + "】改为【" + requestParam.getValue() + "】";
        if (!getApprovalStatus()) {
            requestParam.setApplicationInfo(applicationInfo);
            requestParam.setStatus("3");
            return updateLifeCycleStatus(requestParam);
        }
        String nowValue = dataStorageMonitorDao.getLifeCycle(requestParam);
        if (!requestParam.getOldValue().equalsIgnoreCase(nowValue)) {
            throw new Exception(",数据过期");
        }
        /*拼接传递给 审批流程 的接口*/
        DataApproval dataApproval = new DataApproval();
        dataApproval.setTableProject(requestParam.getTableProject());
        dataApproval.setTableNameEn(requestParam.getTableNameEn());
        dataApproval.setApprovalType("updateLifeCycle");
        List<DataApproval> dataApprovals = new ArrayList();
        dataApprovals.add(dataApproval);
        ServerResponse serverResponse = validateApprovals(dataApprovals);
        if (serverResponse != null) {
            return serverResponse;
        }

        ApprovalInfoParams approvalInfoParams = buildApprovalInfoParams("资产管理", "updateLifeCycle",
                "更新生命周期", applicationInfo, JSON.toJSONString(requestParam),
                "http://" + environment.getProperty("server.address") + ":" +
                        environment.getProperty("server.port") +
                        "/dataOrganizationMonitoring/ClassifyInterface/updateLifeCycleStatus");
        Map paramMap = startApproval(approvalInfoParams, dataApprovals);
        return ServerResponse.asSucessResponse(paramMap);
    }

    @Override
    public ServerResponse updateLifeCycleStatus(RequestParameter requestParam) {
        ServerResponse serverResponse = null;
        String platFormType = (String)cacheManager.getValue("dataPlatFormType");
        if ("3".equals(requestParam.getStatus())) {
            try {
                String tableProjectString = requestParam.getTableProject();
                String tableType = tableProjectString.split("->")[0];
                String tableProject = tableProjectString.split("->")[1];
//                if (PlatformType.ALI.equalsIgnoreCase(platFormType)) {
                if ("ODPS".equalsIgnoreCase(tableType)) {
                    odpsClient.updateTableCycle(tableProject, requestParam.getTableNameEn(), requestParam.getValue());
                }
//                    else if ("ADS".equalsIgnoreCase(tableType)) {
//                        /*某些阿里平台需要加前缀*/
//                        String prefix = "";
//                        if (TableOrganizationConstant.SWITCH_ON.equals(environment.getProperty("queryAdsPrefix"))) {
//                            prefix = "/*+engine=mpp*/";
//                        }
//                        String adsSql = prefix + "alter table {0} subpartition_available_partition_num = {1}";
//                        String querySql = MessageFormat.format(adsSql, tableProject + "." + requestParam.getTableNameEn(),
//                                requestParam.getValue());
//                        logger.info("ADS的运行sql:" + querySql);
//                        Connection connection = null;
//                        Statement st = null;
//                        try {
//                            connection = adsDataSource.getConnection();
//                            st = connection.createStatement();
//                            st.executeQuery(querySql);
//                        } finally {
//                            if (st != null) {
//                                st.close();
//                            }
//                            if (connection != null) {
//                                connection.close();
//                            }
//                        }
//                    }
//                } else if (PlatformType.HUAWEI.equalsIgnoreCase(platFormType)) {
                if ("HBASE".equalsIgnoreCase(tableType)) {
                    //TODO 等hbase的修改生命周期接口
                } else if ("HIVE".equalsIgnoreCase(tableType)) {
                    String retrunJsonStr = restTemplate.getForObject(environment.getProperty("dfWorkUrl") +
                                    "/dfworks/interface/updateTableDataLife?dbType=Hive&dbName=" + tableProject +
                                    "&tbName=" + requestParam.getTableNameEn() + "&lifeCycle=" + requestParam.getValue() + "天",
                            String.class);
                    logger.info("修改生命周期返回参数为" + retrunJsonStr);
                    if (StringUtils.isNotBlank(retrunJsonStr)) {
                        Map paramMap = JSON.parseObject(retrunJsonStr);
                        if (paramMap != null && !"0".equalsIgnoreCase((String) paramMap.get("returnCode"))) {
                            throw new Exception((String) paramMap.get("returnMessage"));
                        }
                    } else {
                        throw new Exception("dfworks修改生命周期返回异常");
                    }
                }
                dataStorageMonitorDao.updateLifeCycleApprovalStatus(requestParam);
                DataProcess dataProcess = new DataProcess();
                dataProcess.setUserId(Integer.valueOf(requestParam.getUserId()));
                dataProcess.setTableNameEn(requestParam.getTableProject().split("->")[1] + requestParam.getTableNameEn());
                dataProcess.setOperateTime(DateUtil.formatDateTime(new Date()));
                dataProcess.setIp(environment.getProperty("server.address", StringUtils.EMPTY));
                dataProcess.setModuleId("ZCGL");
                dataProcess.setLogType("ZCGL001");
                dataProcess.setDigest(requestParam.getApplicationInfo() == null ? "修改源表的生命周期" : requestParam.getApplicationInfo());
                dataProcess.setDataBaseType(requestParam.getTableProject().split("->")[0]);
                dataProcessService.saveDataProcess(dataProcess);
                sendOperatorLogLC(3, requestParam);
                serverResponse = ServerResponse.asSucessResponse("修改成功", requestParam);
            } catch (Exception e) {
                dataStorageMonitorDao.updateFailLifeCycleApprovalStatus(requestParam);
                logger.error(ExceptionUtil.getExceptionTrace(e));
                serverResponse = ServerResponse.asErrorResponse("修改生命周期失败");
            }
        }
        return serverResponse;
    }

    public void sendOperatorLogLC(int operateType, RequestParameter requestParameter) {
        // 发送操作日志
        OperatorLog operatorLog = new OperatorLog();
        operatorLog.setOperateType(operateType);
        operatorLog.setOperateResult("1");
        operatorLog.setErrorCode("0000");
        operatorLog.setOperateName("生命周期管理");
        String operatorCondition = String.format("在%s>%s的[%s]。", requestParameter.getTableType(), requestParameter.getTableProject(), requestParameter.getTableNameEn());
        operatorLog.setOperateCondition(operatorCondition);
        if (restTemplateHandle.OperatorLogSend(operatorLog)) {
            logger.info("===============生命周期管理操作日志发送成功");
        } else {
            logger.error("===============生命周期管理操作日志发送失败");
        }
    }

    private ApprovalInfoParams buildApprovalInfoParams(String moduleName, String moduleId, String operationName,
                                                       String applicationInfo, String callbackData, String callbackUrl) {
        ApprovalInfoParams approvalInfoParams = new ApprovalInfoParams();
        approvalInfoParams.setApprovalId("");
        approvalInfoParams.setOperationName(operationName);
        approvalInfoParams.setModuleName(moduleName);
        approvalInfoParams.setModuleId(moduleId);
        approvalInfoParams.setApplicationInfo(applicationInfo);
        approvalInfoParams.setCallbackData(callbackData);
        approvalInfoParams.setCallbackUrl(callbackUrl);
        return approvalInfoParams;
    }

    private Map startApproval(ApprovalInfoParams approvalInfoParams, List<DataApproval> dataApprovals) throws Exception {
        Map paramMap = new HashMap();
        String approvalId;
        JSONObject returnObject = restTemplate.postForObject(UrlConstants.DATAGOVERNANCE_BASEURL + "/datagovernance/process/saveOrUpdateApprovalInfo",
                approvalInfoParams, JSONObject.class);
        logger.info("审批流程返回的数据为：" + JSONObject.toJSONString(JSONObject.toJSONString(returnObject)));
        if (returnObject.getInteger("status") == 1) {
            /*表示调用审批流程成功*/
            approvalId = returnObject.getString("result");
            dataApprovals.forEach(item -> {
                item.setApprovalId(approvalId);
                item.setApprovalStatus("2");
            });
            paramMap.put("approvalId", approvalId);
            paramMap.put("dataApprovals", dataApprovals);
        } else {
            throw new Exception(returnObject.getString("message"));
        }
        return paramMap;
    }

    @Override
    public ServerResponse updateApprovalStatus(List<DataApproval> dataApprovals) {
        try {
            dataApprovals.parallelStream().forEach(
                    item -> dataStorageMonitorDao.updateApprovalStatus(item)
            );
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("更新审批状态失败");
        }

        return ServerResponse.asSucessResponse("更新审批状态成功", dataApprovals);
    }

    @Override
    public ServerResponse<List<NeedAddRealTimeTable>> getNeedAddRealTimeTable(String monitorType,
                                                                              String dataSourceId,
                                                                              String tableName,
                                                                              String dataCenterId,
                                                                              String dataBaseType) {
        List<NeedAddRealTimeTable> needAddRealTimeTableList = Collections.synchronizedList(new ArrayList<>());
        ServerResponse serverResponse = null;
        if (monitorType.equalsIgnoreCase(UrlConstants.MONITOR_NO)) {
            List<NeedAddRealTimeTable> allExistTable = dataStorageMonitorDao.getAllRealTimeTable();
            //先判断项目名称是否能模糊匹配到
//            String[] projectNames = restTemplate.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getSchemaByResourceId?resourceId={dataSourceId}", String[].class, dataSourceId);
            List<JSONObject> projectNames = null;
            List<String> projectTemp = new ArrayList<>();
            String result = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getProjectList?resId=" + dataSourceId, String.class);
            if(StringUtils.isNotBlank(result) && "1".equals(JSONObject.parseObject(result).getString("status"))) {
                String dataStr = JSONObject.parseObject(result).getObject("data", String.class);
                projectNames = JSONArray.parseArray(dataStr, JSONObject.class);
                if (projectNames.size()>0){
                    for (int i=0; i<projectNames.size(); i++){
                        projectTemp.add(projectNames.get(i).toString());
                    }
                }
            }

            List<DataResourceParam> resultList = Collections.synchronizedList(new ArrayList<>());
            List<NeedAddRealTimeTable> unMonitoredTable = dataStorageMonitorDao.getAllUnMonitoredTable();
            List<ObjectTable> objectTable = dataStorageMonitorDao.getAllObjectChName();
            Map<String, String> objectTableMap = objectTable.parallelStream().collect(HashMap::new, (n, v) -> n.put(v.getTABLENAME(), v.getOBJECTNAME()), HashMap::putAll);
            Map<NeedAddRealTimeTable, String> tempMap = unMonitoredTable.parallelStream().collect(HashMap::new, (n, v) -> n.put(v, v.getTableNameCH()), HashMap::putAll);
//            List<String> projectTemp = new ArrayList<>();
//            if (projectNames != null) {
//                projectTemp = Arrays.stream(projectNames).collect(toList());
//            }
            /*获取标准表名*/
//            Map<String, Map> standardTableName = dataStorageMonitorDao.getStandardTableName();
            projectTemp.parallelStream().forEach(
                    item -> {
                        try {
//                            DataResourceParam[] dps = restTemplate.getForObject(
//                                    TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getAllTableNameByCondition?resourceId={dataSourceId}&sche={projectName}",
//                                    DataResourceParam[].class, dataSourceId, item);
//                            DataResourceParam[] dps = null;
//                            List<DataResourceParam> paramList = Arrays.stream(dps).collect(toList());

                            // 获取项目下的表信息
                            List<DataResourceParam> paramList = null;
                            String dpsStr = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getTablesIncludeDetectedInfo?resId={dataSourceId}&projectName={projectName}", String.class, dataSourceId, item);
                            if (StringUtils.isNotBlank(dpsStr) && "1".equals(JSONObject.parseObject(dpsStr).getString("status"))){
                                String dataStr = JSONObject.parseObject(dpsStr).getString("data");
                                paramList = JSONArray.parseArray(dataStr,DataResourceParam.class);
                            }
                            // 获取数据源
                            String getAllResources = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getAllResources", String.class);
                            List<JSONObject> allDataResrouce = null;
                            if(StringUtils.isNotBlank(getAllResources) && "1".equals(JSONObject.parseObject(getAllResources).getString("status"))){
                                String str = JSONObject.parseObject(getAllResources).getObject("data",String.class);
                                allDataResrouce = JSONArray.parseArray(str, JSONObject.class);
                            }
                            List<JSONObject> finalAllDataResrouce = allDataResrouce;
                            paramList.parallelStream().forEach(dp -> {
                                try {
                                    String tableNameCh = "";
                                    if (StringUtils.isNotBlank(dp.getTableNameEN()) && "null".equals(dp.getTableNameEN().trim())) {
                                        tableNameCh = objectTableMap.get(dp.getTableNameEN().toUpperCase());
                                    }
//                                    else if ("".equals(tableNameCh) && standardTableName.get(dp.getTableName().toUpperCase())!=null){
//                                        // 根据英文表名从标准库获取中文表名后反填
//                                        tableNameCh = standardTableName.get(dp.getTableName().toUpperCase()).get("OBJECTNAME").toString();
//                                    }
                                    if (StringUtils.isNotBlank(tableNameCh)) {
                                        dp.setTableNameCN(tableNameCh);
                                        dp.setIsStandard("1");
                                    } else {
                                        NeedAddRealTimeTable n = new NeedAddRealTimeTable();
                                        n.setTableNameEN(dp.getTableNameEN());
                                        n.setDataSourceId(dataSourceId);
                                        n.setProjectName(item);
                                        if (StringUtils.isNotBlank(tempMap.get(n))) {
                                            dp.setTableNameCN(tempMap.get(n));
                                        }
                                        dp.setIsStandard("0");
                                    }
                                    // 回填数据源信息
                                    for(int i = 0; i< finalAllDataResrouce.size(); i++){
                                        if (finalAllDataResrouce.get(i).getString("resId").equals(dataSourceId)){
                                            dp.setCenterName(finalAllDataResrouce.get(i).getString("centerName"));
                                            dp.setDataName(finalAllDataResrouce.get(i).getString("resName"));
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.error(dp.toString());
                                    logger.error(ExceptionUtil.getExceptionTrace(e));
                                }
                            });
                            try {
                                if (StringUtils.isBlank(tableName) || item.toUpperCase().contains(tableName.toUpperCase())) {
                                    resultList.addAll(paramList);
                                    return;
                                }
                            } catch (Exception e) {
                                logger.error(paramList.toString());
                            }
                            paramList.parallelStream().forEach(dp -> {
                                try {
                                    if (StringUtils.isNotBlank(dp.getTableNameEN()) && dp.getTableNameEN().toUpperCase().contains(tableName.toUpperCase())) {
                                        resultList.add(dp);
                                    } else if (StringUtils.isNotBlank(dp.getTableNameCN()) && dp.getTableNameCN().contains(tableName)) {
                                        resultList.add(dp);
                                    }
                                } catch (Exception e) {
                                    logger.error(dp.toString());
                                    logger.error(ExceptionUtil.getExceptionTrace(e));
                                }
                            });
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getExceptionTrace(e));
                        }
                    });
            List<NeedAddRealTimeTable> finalNeedAddRealTimeTableList = needAddRealTimeTableList;
            resultList.parallelStream().forEach(item -> {
                try {
                    NeedAddRealTimeTable n = new NeedAddRealTimeTable();
                    n.setDataSourceId(dataSourceId);
                    n.setTableNameEN(item.getTableNameEN());
                    n.setProjectName(item.getProjectName());
                    if (allExistTable.contains(n)) {
                        return;
                    }
                    n.setDataCenterId(dataCenterId);
                    n.setDataBaseType(item.getDataType().toUpperCase());
                    n.setDataCenterName(item.getCenterName());
                    n.setDataSourceName(item.getDataName());
                    n.setTableNameCH(item.getTableNameCN());
                    n.setIsStandard(item.getIsStandard());
                    n.setPartitioned(item.getIsPartitioned());
                    n.setIsDelete("0");
                    n.setIsAddMonitor("待控");
                    finalNeedAddRealTimeTableList.add(n);
                } catch (Exception e) {
                    logger.error(item.toString());
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            });
            serverResponse = ServerResponse.asSucessResponse(needAddRealTimeTableList);
        } else if (monitorType.equalsIgnoreCase(UrlConstants.MONITOR_OK)) {
            needAddRealTimeTableList = dataStorageMonitorDao.getNeedAddRealTimeTableList(
                    dataBaseType, tableName);
            needAddRealTimeTableList.parallelStream().forEach(dp -> {
                String tableNameCh = dataStorageMonitorDao.getObjectNameChByEn(dp.getTableNameEN());
                if (StringUtils.isNotBlank(tableNameCh)) {
                    dp.setIsStandard("1");
                } else {
                    dp.setIsStandard("0");
                }
            });
            serverResponse = ServerResponse.asSucessResponse(needAddRealTimeTableList);
        } else {
            logger.error("monitorType类别不正确为" + monitorType);
            serverResponse = ServerResponse.asErrorResponse("monitorType类别不正确为" + monitorType);
        }
        return serverResponse;
    }

    /**
     * 获取的tableid去资源服务平台注册
     *
     * @param tableIdList 注册的tableId集合
     * @param tableList
     * @param status
     * @return
     */
    @Override
    public ServerResponse<List<String>> updateRegisterState(String[] tableIdList, JSONObject[] tableList, String status,String userId) {
        Map<String, Object> registerInterfaceMap;
        List<String> tablelist = Arrays.asList(tableIdList);
        Map<String, List> paramMap = new HashMap<>();
        if ("3".equals(status)) {
            try {
                Date todayNow = DateUtil.parseDate(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE), DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE);
                logger.info("=======开始向资源服务平台注册数据=========");
                if(StringUtils.isBlank(userId)){
                    userId = String.valueOf(AuthorizedUserUtils.getInstance().getAuthor().getUserId());
                }
                logger.info("userId==========" + userId);
                if (UrlConstants.SWITCH_ON.equals(environment.getProperty("dataResourceInfo"))) {
                    // 统计资产表今日数据量如果为0，则获取昨天数据
                    int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
                    int daysAgo = todayAssetsCount<100?1:0;
                    List<RegisterInfo> list = dataStorageMonitorDao.getRegisterInfoBytableIdList(tablelist, environment.getRequiredProperty("resourceCode"),daysAgo);
                    for (int i=0;i<list.size();i++){
                        list.get(i).setUserid(userId);
                    }
                    paramMap.put("tableIdList", list);
                } else {
//                    paramMap.put("tableIdList", tablelist);
                    logger.error("dataResourceInfo值不为1，注册失败");
                    return ServerResponse.asErrorResponse("dataResourceInfo值不为1，注册失败");
                }
                String registerInterfaceUrl = environment.getRequiredProperty("nginxIp");
                logger.info("开始调用注册接口，注册列表为：" + JSONObject.toJSON(paramMap).toString());
                String registerInterfaceString = restTemplate.postForObject(registerInterfaceUrl + "/classifyserver/interface/registerResource", paramMap, String.class);
                logger.info("注册返回信息为："+registerInterfaceString);
                registerInterfaceMap = JSON.parseObject(registerInterfaceString);
                logger.info("=======注册完成=========");
                logger.info("=======开始更新本地注册状态数据=========");
                Boolean flag = (Boolean) registerInterfaceMap.get("isSuccess");
                List<DetailedTableByClassify> allUpdateList = new ArrayList<>();
                for (JSONObject dtc : tableList) {
                    DetailedTableByClassify index = JSONObject.toJavaObject(dtc, DetailedTableByClassify.class);
                    DetailedTableByClassify detailedTableByClassify = new DetailedTableByClassify();
                    detailedTableByClassify.setStatisticsTime(todayNow);
                    detailedTableByClassify.setTableid(index.getTableid());
                    detailedTableByClassify.setTableNameEn(index.getTableNameEn());
                    if (flag) {
                        detailedTableByClassify.setRegisterState("1");
                    } else {
                        detailedTableByClassify.setRegisterState("4");
                    }
                    allUpdateList.add(detailedTableByClassify);
                }
                if (flag) {
                    if (allUpdateList.size() > 0) {
                        asyManager.addTask(()->dataStorageMonitorDao.updateRegisterInterfaceSuccessData(allUpdateList));
                    }
                } else {
                    if (allUpdateList.size() > 0) {
                        asyManager.addTask(()->dataStorageMonitorDao.updateRegisterInterfaceFailedData(allUpdateList));
//                        return ServerResponse.asErrorResponse("注册失败",tablelist);
                        return ServerResponse.asErrorResponse("注册失败");
                    }
                }
                logger.info("=======更新本地注册状态完成=========");
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
                logger.error("注册失败");
                return ServerResponse.asErrorResponse("注册失败" + e.toString());
            }
        } else if ("4".equals(status)) {
            try {
                Date todayNow = DateUtil.parseDate(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE), DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE);
                List<DetailedTableByClassify> allUpdateList = new ArrayList<>();
                for (JSONObject dtc : tableList) {
                    DetailedTableByClassify index = JSONObject.toJavaObject(dtc, DetailedTableByClassify.class);
                    DetailedTableByClassify detailedTableByClassify = new DetailedTableByClassify();
                    detailedTableByClassify.setStatisticsTime(todayNow);
                    detailedTableByClassify.setTableid(index.getTableid());
                    detailedTableByClassify.setTableNameEn(index.getTableNameEn());
                    detailedTableByClassify.setRegisterState("-1");
                    allUpdateList.add(detailedTableByClassify);
                }
                if (allUpdateList.size() > 0) {
                    asyManager.addTask(()->dataStorageMonitorDao.updateRegisterInterfaceFailedData(allUpdateList));
                }
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
                return ServerResponse.asErrorResponse(e.toString());
            }
            return ServerResponse.asSucessResponse("审批取消", tablelist);
        }
        return ServerResponse.asSucessResponse("注册成功",tablelist);
    }

    @Override
    public ServerResponse refreshSourceData(String[] tableIdList) {
        StringBuilder tableIds = new StringBuilder();
        for (int i = 0; i < tableIdList.length; i++) {
            tableIds.append(tableIdList[i]);
            if (i != tableIdList.length - 1) {
                tableIds.append(",");
            }
        }
        Map<String, Object> registerInterfaceMap;
        List<RegisterState> tablelist;
        MultiValueMap<String, Object> paramap = new LinkedMultiValueMap<>();
        paramap.add("tableIds", tableIds.toString());
        try {
            logger.info("=======开始更新注册状态=========");
            String registerInterfaceUrl = environment.getRequiredProperty("nginxIp");
            String registerInterfaceString = restTemplate.getForObject(registerInterfaceUrl + "/classifyserver/interface/validateService?tableIds={tableIds}", String.class, tableIds.toString());
            logger.info("获取接口返回为:" + registerInterfaceString);
            registerInterfaceMap = JSON.parseObject(registerInterfaceString);
            logger.info("=======获取注册状态完成=========");
            logger.info("=======开始更新本地注册状态数据=========");
            Boolean flag = (Boolean) registerInterfaceMap.get("isSuccess");
            if (flag) {
                Map<String, Object> map = JSON.parseObject(JSON.toJSONString(registerInterfaceMap.get("data")));
                tablelist = JSON.parseArray(JSON.toJSONString(map.get("tableList")), RegisterState.class);
                logger.info("=======更新本地注册状态数据完成=========");
            } else {
                logger.info("=======更新本地注册状态数据完成=========");
                return ServerResponse.asErrorResponse("注册状态更新失败");
            }
            tablelist.parallelStream().filter(item -> item.getTYPE() != 1).forEach(item -> {
                item.setTYPE(dataStorageMonitorDao.getApprovalStatusBytableId(item.getTABLEID()));
                if (item.getTYPE() == null) {
                    item.setTYPE(-1);
                }
            });
            if (tablelist.size() > 0) {
                asyManager.addTask(()->dataStorageMonitorDao.updateRegisterState(tablelist));
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("注册状态更新失败");
        }
        return ServerResponse.asSucessResponse(tablelist);
    }

    @Override
    public ServerResponse<List<String>> updateObjectState(List<String> tableIdList, String state) {
        try {
            logger.info("=======开始更新使用状态=========");
            dataStorageMonitorDao.updateObjectState(tableIdList, state);
            logger.info("=======更新使用状态完成=========");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("使用状态更新失败");
        }

        return ServerResponse.asSucessResponse(tableIdList);
    }

//    @Override
//    public ServerResponse getLabelsByType(String label) {
//        List<PageSelectOneValue> labels = new ArrayList();
//        try {
//            logger.info("=======开始获取标签=========");
//            labels = dataStorageMonitorDao.getLabelsByType(label);
//            logger.info("=======获取标签完成=========");
//            Iterator<PageSelectOneValue> it = labels.iterator();
//            while (it.hasNext()) {
//                PageSelectOneValue ps = it.next();
//                if (ps.getValue() == null || "".equals(ps.getValue().trim())) {
//                    it.remove();
//                }
//            }
//        } catch (Exception e) {
//            logger.error(ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asErrorResponse("获取项目名失败");
//        }
//        return ServerResponse.asSucessResponse(labels);
//    }

    @Override
    public ServerResponse getProjectNameByType(String stroageLocation) {
        List<PageSelectOneValue> projectList;
        try {
            logger.info("=======开始获取项目名=========");
            projectList = dataStorageMonitorDao.getProjectNameByType(stroageLocation);
            logger.info("=======获取项目名完成=========");
            projectList.removeIf(ps -> ps.getValue() == null || "".equals(ps.getValue().trim()));
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取项目名失败");
        }
        return ServerResponse.asSucessResponse(projectList);
    }

    @Override
    public ServerResponse getStorageNum() {
        List<PageSelectOneValue> countList;
        try {
            logger.info("=======开始获取存储位置数量=========");
            countList = dataStorageMonitorDao.getStorageNum();
            logger.info("=======获取存储位置数量结束=========");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取存储位置数量失败");
        }
        return ServerResponse.asSucessResponse(countList);
    }

    @Override
    public ServerResponse getLabelNum() {
        List<PageSelectOneValue> countList = null;
        try {
            logger.info("=======开始获取存储位置数量=========");
            countList = dataStorageMonitorDao.getLabelNum();
            logger.info("=======获取存储位置数量结束=========");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取存储位置数量失败");
        }
        return ServerResponse.asSucessResponse(countList);
    }

    @Override
    public ServerResponse getSourceStateNum() {
        List<Map<String, Object>> countList;
        try {
            logger.info("=======开始获取存储位置数量=========");
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            countList = dataStorageMonitorDao.getSourceStateNum(daysAgo);
            logger.info("=======获取存储位置数量结束=========");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取存储位置数量失败");
        }
        return ServerResponse.asSucessResponse(countList);
    }


    @Override
    public ServerResponse getTableType() {
        List<String> tableTypeList = new ArrayList<>();
        try {
            logger.info("=======开始获表类型=========");
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            List<String> tableTypeList1 = dataStorageMonitorDao.getTableType(daysAgo);
            for (String tableType : tableTypeList1){
                if (tableType.contains("HIVE")){
                    tableTypeList.add("HIVE");
                }else {
                    tableTypeList.add(tableType);
                }
            }
            logger.info("=======获取表类型完成=========");
        } catch (Exception e) {
            logger.error("获取表类型失败:", e);
            return ServerResponse.asErrorResponse("获取表类型失败");
        }
        return ServerResponse.asSucessResponse(tableTypeList);
    }

    @Override
    public void exportDataContent(List<ReceiveTag> classifyTagsList, String input, List<ReceiveTag> registerTagsList, List<ReceiveTag> storageTagsList, List<ReceiveTag> usingTagsList, String type, List<String> termSetting, List<String> lastModifiedTimeList, Long startRecordNum, Long endRecordNum, Long startStorageSize, Long endStorageSize,DataResourceTable queryTable, HttpServletResponse response
//            , List<ReceiveTag> labelTagsList
    ) {
        //文件名称
        String name = "表资产统计信息";
        //表标题
        String[] titles = {"数据资源名称", "数据资源表名", "协议名称", "数据组织分类", "数据来源分类", "存储位置", "占用存储（GB）", "总量（万条）", "日增量（万条）", "使用数（月/总）", "存储周期（天）", "更新类型", "运行状态", "异常信息", "使用状态", "注册状态",
//                , "数据资源标签"
                "生产阶段", "更新周期", "下发批次", "统计日期", "备注"};
        //列对应字段
        String[] fieldName = new String[]{"name", "tableNameEn", "tableid", "organizationClassifyCh", "dataSourceClassifyCh", "tableProject", "tableSize", "tableAllCount", "yesterdayCount", "frequency", "lifeCycle", "updateType", "tableState", "exceptionMessage", "objectState", "registerState"
//                , "labels"
                , "productStage", "updateDate", "updatePeriod", "statisticsTimeStr", "remarks"};
        //查询数据集
        List<ReceiveTag> organizationClassify = new ArrayList<>();
        List<ReceiveTag> sourceClassify = new ArrayList<>();
        String registerState = "";
        String usingTagsState = "";
        String startLastModifiedTime = "";
        String endLastModifiedTime = "";
        List<String> projectList = new ArrayList<>();
//        List<String> labels = new ArrayList<>();
        List<DetailedTableByClassify> allDataContentByTags;
        try {
            if (classifyTagsList != null) {
                for (ReceiveTag rt : classifyTagsList) {
                    if ("dataOrganizationClassify".equals(rt.getMainClassify())) {
                        organizationClassify.add(rt);
                    } else if ("dataSourceClassify".equals(rt.getMainClassify())) {
                        sourceClassify.add(rt);
                    }
                }
            }
            if (registerTagsList.size() == 1) {
                registerState = registerTagsList.get(0).getLabel();
            }
            if (usingTagsList.size() == 1) {
                usingTagsState = usingTagsList.get(0).getLabel();
            }
            if (lastModifiedTimeList.size() > 0) {
                startLastModifiedTime = lastModifiedTimeList.get(0);
                endLastModifiedTime = lastModifiedTimeList.get(1);
            }
//            if (labelTagsList.size() > 0) {
//                labels = dataStorageMonitorDao.getLabelsByName(labelTagsList);
//            }
//            allDataContentByTags = dataStorageMonitorDao.getAllDataContentByTags(
//                    organizationClassify, sourceClassify, input, registerState, usingTagsState, storageTagsList,
////                    , labels
//                    termSetting, startLastModifiedTime, endLastModifiedTime, startRecordNum, endRecordNum, startStorageSize, endStorageSize, queryTable,0);

            allDataContentByTags = dataStorageMonitorDao.getAllDataContentByTags(
                    organizationClassify, sourceClassify, input, registerState, usingTagsState, storageTagsList,
//                    , labels
                    termSetting, startLastModifiedTime, endLastModifiedTime, startRecordNum, endRecordNum, startStorageSize, endStorageSize, queryTable,0,
                    null,null,null,null,null,null,null,null
                    , null);

//            List<Label> labelList = dataStorageMonitorDao.getAllLabels();
//            Map<String, String> labelMap = labelList.parallelStream().collect(Collectors.toMap(item -> item.getLabelCode(), item -> item.getLabelName()));

            for (DetailedTableByClassify dtc : allDataContentByTags) {
//                if (dtc.getLabels() != null) {
//                    StringBuilder sb = new StringBuilder();
//                    labels = Arrays.asList(dtc.getLabels().split(","));
//                    labels.stream().forEach(item -> sb.append(labelMap.get(item) + " "));
//                    dtc.setLabels(sb.toString());
//                }
                if (StringUtils.isNotBlank(dtc.getProductStage())) {
                    dtc.setProductStage(SysCodeEnum.getNameByCodeAndType(dtc.getProductStage(), "PRODUCTSTAGE"));
                }
                if ("1".equals(dtc.getObjectState())) {
                    dtc.setObjectState("启用");
                } else if (dtc.getObjectState() == null) {
                    dtc.setObjectState("非标准");
                } else {
                    dtc.setObjectState("停用");
                }
                if ("1".equals(dtc.getRegisterState())) {
                    dtc.setRegisterState("已注册");
                } else if ("-1".equals(dtc.getRegisterState())) {
                    dtc.setRegisterState("未注册");
                } else if ("2".equals(dtc.getRegisterState())) {
                    dtc.setRegisterState("审批中");
                } else {
                    dtc.setRegisterState("非标准");
                }
            }
            //响应
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/binary;charset=UTF-8");
            switch (type) {
                case "1":
                    // 导出excel
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xlsx", "UTF-8"));
                    List<Object> listNew = new ArrayList<>(allDataContentByTags);
                    ExcelHelper.export(new DetailedTableByClassify(), titles, name, listNew, fieldName, out);
                    break;
                case "2":
                    // 导出csv
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".csv", "UTF-8"));
                    List<Object> csvList = new ArrayList<>(allDataContentByTags);
                    ExportUtil.exportToCsv(out, csvList, name, titles, fieldName);
                    break;
                case "3":
                    // 导出word
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".doc", "UTF-8"));
                    List<Object> wordList = new ArrayList<>(allDataContentByTags);
                    int[] colWidths = {1534, 1534, 1334, 1334, 1334, 1334, 1334, 1334, 1334, 1234, 1234, 1234, 1234, 1334, 1334};
                    ExportUtil.exportToWord(out, wordList, name, titles, fieldName, "A3", colWidths);
                    break;
                default:

            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            logger.error("表资产导出失败");
        }

    }

    @Override
    public void exportOrganizationList(HttpServletResponse response, String type, String incrementDays, String mainClassify, String primaryClassifyCh, String secondaryClassifyCh, String threeValue) throws Exception {
        String platformType = (String)cacheManager.getValue("dataPlatFormType");
        //文件名称
        String name = "数据组织资产统计信息";
        //表标题
        String[] titles = null;
        //列对应字段
        String[] fieldName = null;
        if (PlatformType.ALI.equalsIgnoreCase(platformType)) {
            titles = new String[]{"一级分类", "二级分类", "表数量", "ADS数据量(万条)", "ADS存储空间(GB)", "ODPS数据量(万条)", "ODPS存储空间(GB)", "日增数据量(万条)", "日增存储空间(GB)", "统计日期"};
            fieldName = new String[]{"primaryClassifyCh", "secondaryClassifyCh", "tableNumberSum", "adsSumW", "adsSize", "odpsSumW", "odpsSize", "yesterdayAllSumW", "averageAllDailySize", "statisticsDay"};
            if (incrementDays.equalsIgnoreCase("7")){
                titles = new String[]{"一级分类", "二级分类", "表数量", "ADS数据量(万条)", "ADS存储空间(GB)", "ODPS数据量(万条)", "ODPS存储空间(GB)",
                        "日增数据量-6(万条)", "日增数据量-5(万条)", "日增数据量-4(万条)", "日增数据量-3(万条)", "日增数据量-2(万条)", "日增数据量-1(万条)", "日增数据量(万条)", "日增存储空间(GB)", "统计日期"};
                fieldName = new String[]{"primaryClassifyCh", "secondaryClassifyCh", "tableNumberSum", "adsSumW", "adsSize", "odpsSumW", "odpsSize",
                        "yesterdayAllSumW_6", "yesterdayAllSumW_5", "yesterdayAllSumW_4", "yesterdayAllSumW_3", "yesterdayAllSumW_2", "yesterdayAllSumW_1", "yesterdayAllSumW", "averageAllDailySize", "statisticsDay"};
            }
        } else if (PlatformType.HUAWEI.equalsIgnoreCase(platformType)) {
            titles = new String[]{"一级分类", "二级分类", "表数量", "HBASE数据量(万条)", "HBASE存储空间(GB)", "HIVE数据量(万条)", "HIVE存储空间(GB)", "日增数据量(万条)", "日增存储空间(GB)", "统计日期"};
            fieldName = new String[]{"primaryClassifyCh", "secondaryClassifyCh", "tableNumberSum", "hbaseSumW", "hbaseSize", "hiveSumW", "hiveSize", "yesterdayAllSumW", "averageAllDailySize", "statisticsDay"};
            if (incrementDays.equalsIgnoreCase("7")){
                titles = new String[]{"一级分类", "二级分类", "表数量", "ADS数据量(万条)", "ADS存储空间(GB)", "ODPS数据量(万条)", "ODPS存储空间(GB)",
                        "日增数据量-6(万条)", "日增数据量-5(万条)", "日增数据量-4(万条)", "日增数据量-3(万条)", "日增数据量-2(万条)", "日增数据量-1(万条)", "日增数据量(万条)", "日增存储空间(GB)", "统计日期"};
                fieldName = new String[]{"primaryClassifyCh", "secondaryClassifyCh", "tableNumberSum", "adsSumW", "adsSize", "odpsSumW", "odpsSize",
                        "yesterdayAllSumW_6", "yesterdayAllSumW_5", "yesterdayAllSumW_4", "yesterdayAllSumW_3", "yesterdayAllSumW_2", "yesterdayAllSumW_1", "yesterdayAllSumW", "averageAllDailySize", "statisticsDay"};
            }
        }
        //查询数据集
        int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
        int daysAgo = todayAssetsCount<100?1:0;
        List<SummaryTableByClassify> list = dataStorageMonitorDao.getAllSummaryTableByClassifyList(mainClassify, primaryClassifyCh, secondaryClassifyCh,threeValue, daysAgo);

        if (incrementDays.equalsIgnoreCase("7")){
            daysAgo = 6;
            List<SummaryTableByClassify> listAll = dataStorageMonitorDao.getAllSummaryTableByClassifyList(mainClassify, primaryClassifyCh, secondaryClassifyCh,threeValue, daysAgo);

            // 注入前七天日增量
            injectIncrementDays(daysAgo, listAll, list);
        }

        //响应
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/binary;charset=UTF-8");
        switch (type) {
            case "1":
                // 导出excel
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xlsx", "UTF-8"));
                List<Object> listNew = new ArrayList<>(list);
                ExcelHelper.export(new SummaryTableByClassify(), titles, name, listNew, fieldName, out);
                break;
            case "2":
                // 导出csv
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".csv", "UTF-8"));
                List<Object> csvList = new ArrayList<>(list);
                ExportUtil.exportToCsv(out, csvList, name, titles, fieldName);
                break;
            case "3":
                // 导出word
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".doc", "UTF-8"));
                List<Object> wordList = new ArrayList<>(list);
                int[] colWidths = {1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450, 1450};
                ExportUtil.exportToWord(out, wordList, name, titles, fieldName, "A3", colWidths);
                break;
            default:

        }
    }

    public void injectIncrementDays(int days, List<SummaryTableByClassify> listAll, List<SummaryTableByClassify> list){
        Map<String, List<SummaryTableByClassify>> listMap = listAll.stream().collect(Collectors.groupingBy(SummaryTableByClassify::getStatisticsDay));
        for (String key : listMap.keySet()){
            List<SummaryTableByClassify> listTemp = listMap.get(key);
            for (int i=1; i <= days; i++){
                String date =  DateUtil.addDayStr(new Date(), -i);
                if (key.equalsIgnoreCase(date)){
                    for (SummaryTableByClassify data : list){
                        for (SummaryTableByClassify dataT : listTemp){
                            if (data.getPrimaryClassifyCh().equalsIgnoreCase(dataT.getPrimaryClassifyCh())
                                    && data.getSecondaryClassifyCh().equalsIgnoreCase(dataT.getSecondaryClassifyCh())){
                                if (i==1){
                                    data.setYesterdayAllSumW_1(dataT.getYesterdayAllSumW());
                                    break;
                                }
                                if (i==2){
                                    data.setYesterdayAllSumW_2(dataT.getYesterdayAllSumW());
                                    break;
                                }
                                if (i==3){
                                    data.setYesterdayAllSumW_3(dataT.getYesterdayAllSumW());
                                    break;
                                }
                                if (i==4){
                                    data.setYesterdayAllSumW_4(dataT.getYesterdayAllSumW());
                                    break;
                                }
                                if (i==5){
                                    data.setYesterdayAllSumW_5(dataT.getYesterdayAllSumW());
                                    break;
                                }
                                if (i==6){
                                    data.setYesterdayAllSumW_6(dataT.getYesterdayAllSumW());
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void updateTableOrganizationShowField(String tableOrganizationShowFields) {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        if (StringUtils.isBlank(tableOrganizationShowFields)){
            tableOrganizationShowFields = "notNull";
        }
        dataStorageMonitorDao.updateTableOrganizationShowField(tableOrganizationShowFields, authorizedUser.getUserName());
    }

    @Override
    public List<String> getTableOrganizationShowField() {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        List<String> listReturn = null;
        String result = dataStorageMonitorDao.getTableOrganizationShowField(authorizedUser.getUserName());
        if (StringUtils.isNotEmpty(result) || result != null){
            listReturn = Arrays.asList(result.split(","));
        }
        return listReturn;
    }

}