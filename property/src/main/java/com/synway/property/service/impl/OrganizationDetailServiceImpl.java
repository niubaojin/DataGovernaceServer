package com.synway.property.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.property.common.UrlConstants;
import com.synway.property.config.AsyManager;
import com.synway.property.dao.DataMonitorDao;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.dao.OrganizationDetailDao;
import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.FieldInfo;
import com.synway.property.pojo.alarmsetting.OrganizationAlarmSetting;
import com.synway.property.pojo.organizationdetail.DataNum;
import com.synway.property.pojo.organizationdetail.DataResourceImformation;
import com.synway.property.pojo.organizationdetail.DataStatistics;
import com.synway.property.pojo.RequestParameter;
import com.synway.property.pojo.organizationdetail.ResourceRegisterInfo;
import com.synway.property.service.OrganizationDetailService;
import com.synway.property.util.DateUtil;
import com.synway.property.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @ClassName OrganizationDetailServiceImpl
 * @Author majia
 * @Date 2020/5/2 12:46
 * @Version 1.0
 **/
@Service
public class OrganizationDetailServiceImpl implements OrganizationDetailService {

    private static Logger logger = LoggerFactory.getLogger(OrganizationDetailServiceImpl.class);

    @Resource(name = "restTemplateBalanced")
    private RestTemplate restTemplate;

    @Autowired
    AsyManager<DataNum> asyManager;

    @Autowired
    private OrganizationDetailDao organizationDetailDao;

    @Autowired
    private DataMonitorDao dataMonitorDao;

    @Autowired
    private DataStorageMonitorDao dataStorageMonitorDao;

    @Override
    public List<JSONObject> getTableExampleData(String tableProject, String tableNameEn, String tableType, String resourceId) {
        List<JSONObject> returnList;
//        String result = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getExampleData?resourceId=414dc6c64fc345689e576ffd29dc0b07&project=SYNDG&tableNameEn=DS_DETECTED_TABLE", String.class);
        String url = UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getExampleData?resourceId=" + resourceId + "&project=" + tableProject + "&tableNameEn=" + tableNameEn + "&numLimit=500";
        String result = restTemplate.getForObject(url, String.class);
        if("1".equals(JSONObject.parseObject(result).getString("status"))){
            returnList = (List<JSONObject>) JSONObject.parseObject(result).get("data");
        }else {
            returnList = null;
        }
        return returnList;
    }

    @Override
    public List getTableStructure(String tableProject, String tableNameEn, String tableType, String resourceId) {
        List<FieldInfo> returnList = null;
        String getTableStructureURL = "http://dataresource/dataresource/api/getTableStructure?resourceId=" + resourceId + "&project=" + tableProject + "&tableNameEn=" + tableNameEn;
        logger.info("请求仓库地址：{}", getTableStructureURL);
        try {
            String responseStr = restTemplate.getForObject(getTableStructureURL, String.class);
            ServerResponse response = JSON.parseObject(responseStr, ServerResponse.class);
            if(response.isSuccess()){
                JSONObject dataObject = (JSONObject) response.getData();
                String fieldInfosStr = dataObject.getString("fieldInfos");
                returnList = JSONArray.parseArray(fieldInfosStr,FieldInfo.class);
                if (returnList.size() > 0){
                    returnList.stream().forEach(item ->{
                        if (StringUtils.isNotBlank(item.getPartitionLevel())){
                            item.setIsPartitionField("是");
                        }
                    });
                }
//            // 获取分区字段值
////            String tablePartitionInfo = restTemplate.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/dataresource/api/getPartitionInfo?resourceId=414dc6c64fc345689e576ffd29dc0b07&project=SYNDG&tableNameEn=DS_DETECTED_TABLE", String.class);
//            String tablePartitionInfo = restTemplate.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/dataresource/api/getPartitionInfo?resourceId=" + resourceId + "&project=" + tableProject + "&tableNameEN=" + tableNameEn, String.class);
//            if(StringUtils.isNotBlank(tablePartitionInfo) && "1".equals(JSONObject.parseObject(tablePartitionInfo).getString("status"))){
//                JSONObject resultJson = JSONObject.parseObject(JSONObject.parseObject(tablePartitionInfo).getObject("data",String.class));
////                JSONObject partitionFields = JSONObject.parseObject(resultJson.getString("partitionInfos"));
////                String partitionValue = partitionFields.getString("partitionValue");
//                if (resultJson.size() > 0){
//                    JSONArray partitionFields = JSONObject.parseArray(resultJson.getString("partitionFields"));
//                    logger.info("partitionFields：" + JSONObject.toJSONString(partitionFields));
//                    if (!partitionFields.isEmpty() && partitionFields != null && partitionFields.size()>0){
//                        JSONObject partitionField = partitionFields.getJSONObject(0);
//                        for (int i=0; i<returnList.size(); i++){
//                            if(returnList.get(i).getFieldName().equalsIgnoreCase(partitionField.getString("fieldName"))){
//                                returnList.get(i).setIsPartitionField("是");
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
            }
        }catch (Exception e){
            logger.error("查询表结构失败：",e);
        }
        return returnList;
    }

    @Override
    public ResourceRegisterInfo getRegisterInfo(String tableProject, String tableNameEN, String tableType) {
        Map paramMap = new HashMap();
        ResourceRegisterInfo returnObj = null;
        paramMap.put("tableName", tableNameEN);
        paramMap.put("projectName", tableProject);
        paramMap.put("clusterName", tableType.toUpperCase());

        String resultStr = restTemplate.postForObject(UrlConstants.RESOURCEREGISTER_BASEURL + "/resourceregister/tableRegister/queryRegisterTableInfo", paramMap, String.class);
        String resultData = JSONObject.parseObject(resultStr).getString("data");
        returnObj = JSON.parseObject(String.valueOf(resultData),ResourceRegisterInfo.class);

        logger.info("=======家产信息已获取=========");
        logger.info("=======开始注入家产信息数据=========");
        return returnObj;
    }

    @Override
    public DataResourceImformation getDataResourceInfo(String tableProject, String tableNameEn, String tableType, String resourceId) {
        // 返回前端数据
        DataResourceImformation dataResourceImformation = new DataResourceImformation();
        DetailedTableByClassify tableAssetsInfo = organizationDetailDao.getTableOrganizationInfo(tableProject, tableNameEn, tableType);
        if (tableAssetsInfo != null) {
            dataResourceImformation.setUpdateDate(tableAssetsInfo.getUpdateDate());
            dataResourceImformation.setLife(tableAssetsInfo.getLifeCycle());
        }
        if (StringUtils.isBlank(resourceId)){
            return dataResourceImformation;
        }
        String getTableMetaInfoUrl = String.format(UrlConstants.DATARESOURCE_BASEURL+"/dataresource/api/getTableMetaInfo?resourceId=%s&project=%s&tableNameEn=%s", resourceId, tableProject, tableNameEn);
        String getTableMetaInfoStr = restTemplate.getForObject(getTableMetaInfoUrl, String.class);
        // 新接口(2023.08.08)
        if(StringUtils.isNotBlank(getTableMetaInfoStr) && "1".equals(JSONObject.parseObject(getTableMetaInfoStr).getString("status"))){
            JSONObject tableInfo = (JSONObject) JSONObject.parseObject(getTableMetaInfoStr).get("data");
            dataResourceImformation.setTableNameEn(tableInfo.getString("tableNameEN"));
            dataResourceImformation.setTableNameChn(tableInfo.getString("tableNameCN"));
            dataResourceImformation.setProjectName(tableInfo.getString("projectName"));
            dataResourceImformation.setIsPartition(tableInfo.getString("isPartitioned"));
            dataResourceImformation.setCreateTime(tableInfo.getString("createTime"));
            dataResourceImformation.setLastDDLModifiedTime(tableInfo.getString("lastDDLTime"));
            dataResourceImformation.setLastDataModifiedTime(tableInfo.getString("lastDMLTime"));
            logger.info("createTime:" +tableInfo.getString("createTime"));
            logger.info("lastDDLTime:" +tableInfo.getString("lastDDLTime"));
            logger.info("lastDMLTime:" +tableInfo.getString("lastDMLTime"));
        }
        // 获取数据中心信息
        JSONArray allDataResource = new JSONArray();
        String getDataResourceForLocal = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0",String.class);
        String getDataResourceForNotLocal = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getDataResourceByisLocal?isLocal=1&isApproved=0",String.class);
        if(StringUtils.isNotBlank(getDataResourceForLocal) && "1".equals(JSONObject.parseObject(getDataResourceForLocal).getString("status"))){
            String localData = JSONObject.parseObject(getDataResourceForLocal).getString("data");
            JSONArray dataResourceLocal = JSONArray.parseArray(localData);
            if (dataResourceLocal.size()>0){
                allDataResource.addAll(dataResourceLocal);
            }
        }
        if(StringUtils.isNotBlank(getDataResourceForNotLocal) && "1".equals(JSONObject.parseObject(getDataResourceForNotLocal).getString("status"))){
            String notLocalData = JSONObject.parseObject(getDataResourceForNotLocal).getString("data");
            JSONArray dataResourceNotLocal = JSONArray.parseArray(notLocalData);
            if (dataResourceNotLocal.size()>0){
                allDataResource.addAll(dataResourceNotLocal);
            }
        }
        if(StringUtils.isNotBlank(String.valueOf(allDataResource))){
            for (int i=0;i<allDataResource.size();i++){
                JSONObject dataResource = JSONObject.parseObject(allDataResource.getString(i));
                if (StringUtils.isNotBlank(resourceId) && resourceId.equals(dataResource.getString("resId"))){
                    dataResourceImformation.setDataCenter(dataResource.getString("centerName"));
                    dataResourceImformation.setPlatForm(dataResource.getString("resType"));
                    break;
                }
            }
        }
//        String tableNameCh = dataStorageMonitorDao.getObjectNameChByEn(tableNameEn);
//        if (dataResourceImformation == null) {
//            dataResourceImformation = new DataResourceImformation();
//        }
//        if (StringUtils.isNotBlank(tableNameCh)) {
//            dataResourceImformation.setTableNameChn(tableNameCh);
//        }
        return dataResourceImformation;
    }

    @Override
    public DataNum getDataNum(String tableProject, String tableNameEn, String tableType) throws Exception {
//        Future<DataNum> future2 = asyManager.addTask(() -> {
//            DataNum dataNum = new DataNum();
//            String table_type = "";
//            if ("odps".equalsIgnoreCase(tableType)) {
//                table_type = "1";
//            } else if ("hc_db".equalsIgnoreCase(tableProject)) {
//                table_type = "2";
//            } else if ("hp_db".equalsIgnoreCase(tableProject)) {
//                table_type = "3";
//            }
//            List<DataNum> recentDataCountAndSize = organizationDetailDao.getRecentDataCountAndSize(tableProject, tableNameEn, table_type);
//            if (recentDataCountAndSize.size() == 0) {
//                dataNum.setAverageDailyCount("0条");
//                dataNum.setAverageDailySize("0G");
//                return dataNum;
//            }
//            DataNum max = Collections.max(recentDataCountAndSize, Comparator.comparing(item -> Double.valueOf(item.getAverageDailySize())));
//            DataNum min = Collections.min(recentDataCountAndSize, Comparator.comparing(item -> Double.valueOf(item.getAverageDailySize())));
//            recentDataCountAndSize.remove(max);
//            recentDataCountAndSize.remove(min);
//            Double averageCount = recentDataCountAndSize.stream().collect(Collectors.averagingDouble(item -> Double.parseDouble(item.getAverageDailyCount())));
//            Double averageSize = recentDataCountAndSize.stream().collect(Collectors.averagingDouble(item -> Double.parseDouble(item.getAverageDailySize())));
//            if (averageCount == 0) {
//                dataNum.setAverageDailyCount("0条");
//            } else {
//                dataNum.setAverageDailyCount(String.format("%.2f", averageCount) + "万条");
//            }
//            if (averageSize == 0) {
//                dataNum.setAverageDailySize("0G");
//            } else {
//                dataNum.setAverageDailySize(String.format("%.2f", averageSize) + "G");
//            }
//            return dataNum;
//        });
        DataNum dataNum = organizationDetailDao.getDataNum(tableProject, tableNameEn, tableType);
//        dataNum.setAverageDailySize(future2.get().getAverageDailySize());
//        dataNum.setAverageDailyCount(future2.get().getAverageDailyCount());
        return dataNum;
    }

    @Override
    public String getResourceId(String tableProject, String tableNameEn, String tableType){
        String resourceId = "";
        resourceId = organizationDetailDao.getResourceId(tableProject, tableNameEn, tableType);

        return resourceId;
    }

    @Override
    public DetailedTableByClassify getTableOrganizationInfo(String tableProject, String tableNameEn, String tableType) {
        return organizationDetailDao.getTableOrganizationInfo(tableProject, tableNameEn, tableType);
    }

    @Override
    public List<DataStatistics> getDataCountStatistics(RequestParameter requestParameter) {
        // 返回结果
        List<DataStatistics> statisticsReturn = new ArrayList<>();
        // 时间参数
        String requestBeginTime = requestParameter.getBeginTime();
        String requestEndTtime = requestParameter.getEndTime();
        String benginTimeFmt = requestBeginTime.split("-")[0] + "年" + requestBeginTime.split("-")[1] + "月" + requestBeginTime.split("-")[2] + "日";
        String endTimeFmt = requestEndTtime.split("-")[0] + "年" + requestEndTtime.split("-")[1] + "月" + requestEndTtime.split("-")[2] + "日";
        // 表名
        String tableName = requestParameter.getTableNameCh();
        if (StringUtils.isBlank(requestParameter.getTableNameCh())){
            tableName = requestParameter.getTableNameEn();
        }
        if (StringUtils.isNotBlank(requestParameter.getBeginTime()) && StringUtils.isNotBlank(requestParameter.getEndTime())) {
            requestParameter.setBeginTime(DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(requestParameter.getBeginTime()), 1)));
            requestParameter.setEndTime(DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(requestParameter.getEndTime()), 2)));
        }
        List<DataStatistics> statisticsAll = organizationDetailDao.getDataCountStatistics(requestParameter);
        List<DataStatistics> statisticsAllTemp = statisticsAll;
        String finalTableName = tableName;
        statisticsAll.parallelStream().forEach(statistics ->{
            String statisticsTime = statistics.getDataTime();
            BigDecimal totalCount = statistics.getTotalCount();
            BigDecimal incrementCount = statistics.getIncrementCount();
            if (DateUtil.parseDate(statisticsTime).compareTo(DateUtil.parseDate(requestBeginTime))>=0){
                // 记录前一日数据量（用于计算环比前一日比例）
                BigDecimal dayBeforeOne = new BigDecimal(0);
                String dayBeforeOneDT = DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(requestParameter.getEndTime()), -3));
                for (int j=0; j< statisticsAllTemp.size(); j++){
                    String daysAgo7 = DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(statisticsAllTemp.get(j).getDataTime()), 7));
                    BigDecimal totalCountDaysAgo7 = statisticsAllTemp.get(j).getTotalCount();
                    if (statisticsTime.equalsIgnoreCase(daysAgo7) ){
                        // 前7天均值(当前日期总量减去7天前数据总量 除以7算平均值)
                        BigDecimal average7 = totalCount.subtract(totalCountDaysAgo7).divide(BigDecimal.valueOf(7),RoundingMode.HALF_UP);
                        // 抖动率
                        BigDecimal fluctuateRate = new BigDecimal(0);
                        if (average7.compareTo(BigDecimal.ZERO) != 0){
                            fluctuateRate = incrementCount.subtract(average7).divide(average7,2,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                        }
                        statistics.setAverage7(average7);
                        statistics.setFluctuateRate(fluctuateRate);
                    }
                    if (dayBeforeOneDT.equalsIgnoreCase(statisticsAllTemp.get(j).getDataTime())){
                        dayBeforeOne = statisticsAllTemp.get(j).getIncrementCount();
                    }
                }
                String dtTemp = DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(requestParameter.getEndTime()), -1));
                if (!statistics.getDataTime().equalsIgnoreCase(dtTemp)){
                    // 态势分析内容
                    if (statistics.getDataTime().equalsIgnoreCase(requestEndTtime)){
                        String situationInfo = "";
                        // 根据设置的波动率阈值，判断数据是否异常
                        OrganizationAlarmSetting setting = getAlarmSetting();
                        String isNormalMsg = "正常";
                        statistics.setIsNormal("1");
                        if (setting != null && setting.getFluctuateCheck().equalsIgnoreCase("true")) {
                            BigDecimal fluctuateRate = BigDecimal.valueOf(Long.parseLong(setting.getFluctuateRate()));
                            if ( statistics.getFluctuateRate().abs().compareTo(fluctuateRate) > -1) {
                                statistics.setIsNormal("2");
                                isNormalMsg = "异常，#数据抖动率超过阈值" + fluctuateRate + "%";
                            }
                        }
                        BigDecimal mom = new BigDecimal(0);
                        if (dayBeforeOne.compareTo(BigDecimal.ZERO) != 0){
                            mom = statistics.getIncrementCount().subtract(dayBeforeOne).divide(dayBeforeOne,2,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                        }
                        String momStr = "";
                        if (mom.signum() >= 0){
                            momStr = "环比上一日上升" + mom + "%";
                        }else {
                            momStr = "环比上一日下降" + mom.abs() + "%";
                        }
                        String messageStr1 = String.format("%s，对%s数据进行态势分析，该数据总量为%s万条，数据存储量为%sGB，最新数据日增量为%s万条，%s，最近7天平均值为%s万条，数据抖动为%s%%。||",
                                                            endTimeFmt, finalTableName,statistics.getTotalCount(),statistics.getTableAllSize(),statistics.getIncrementCount(),momStr,
                                                            statistics.getAverage7(),statistics.getFluctuateRate());
                        String messageStr2 = String.format("经分析，%s数据%s。||",finalTableName,isNormalMsg);
                        String messageStr3 = String.format("%s%s至%s，总量、日增量、抖动如下图：",finalTableName,benginTimeFmt,endTimeFmt);
                        situationInfo = messageStr1 + messageStr2 + messageStr3;
                        statistics.setSituationInfo(situationInfo);
                    }
                    statisticsReturn.add(statistics);
                }
            }
        });
        return statisticsReturn;
    }

    /**
     * @description 获取告警信息
     * @return
     */
    private OrganizationAlarmSetting getAlarmSetting() {
        OrganizationAlarmSetting setting = null;
        try {
            String jsonString = dataMonitorDao.getOrganizationAlarmSetting();
            setting = JSONObject.parseObject(jsonString, OrganizationAlarmSetting.class);
            if (setting.getFluctuateCheck().equalsIgnoreCase("false") && setting.getNoDataCheck().equalsIgnoreCase("false")) {
                setting = null;
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        return setting;
    }
}
