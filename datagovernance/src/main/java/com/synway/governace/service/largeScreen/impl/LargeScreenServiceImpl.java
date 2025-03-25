package com.synway.governace.service.largeScreen.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.common.ApiConstant;
import com.synway.governace.common.Constant;
import com.synway.governace.dao.LargeScreenDao;
import com.synway.governace.enums.SysCodeEnum;
import com.synway.governace.pojo.largeScreen.*;
import com.synway.governace.service.largeScreen.LargeScreenService;
import com.synway.governace.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据大屏业务实现类
 *
 * @author ywj
 * @date 2020/7/22 14:53
 */
@Service
public class LargeScreenServiceImpl implements LargeScreenService {

    @Autowired
    private LargeScreenDao largeScreenDao;

    @Qualifier("loadBalanced")
    @Autowired
    private RestTemplate loadBalanced;


    @NacosInjected
    private NamingService namingServiceImpl;

    private Logger logger = Logger.getLogger(LargeScreenServiceImpl.class);

    @Override
    public List<StatisticsResult> standardDefinedStatistics() {
        List<StatisticsResult> result = new ArrayList<>();
        // 数据定义
        StatisticsResult standardStatistics = largeScreenDao.standardStatistics();
        standardStatistics.setTodayUpdateCount(largeScreenDao.standardUpdateStatistics());
        standardStatistics.setDataName(SysCodeEnum.standard.getName());
        result.add(standardStatistics);
        // 元素编码
        StatisticsResult metadataStatistics = largeScreenDao.metadataStatistics();
        metadataStatistics.setTodayUpdateCount(metadataStatistics.getDataCount().subtract(queryStandardDefinedYesterdayDataCount(SysCodeEnum.metadata.getCode())));
        metadataStatistics.setDataName(SysCodeEnum.metadata.getName());
        result.add(metadataStatistics);
        // 语义类型
        StatisticsResult semanticStatistics = largeScreenDao.semanticStatistics();
        semanticStatistics.setTodayUpdateCount(semanticStatistics.getDataCount().subtract(queryStandardDefinedYesterdayDataCount(SysCodeEnum.semantic.getCode())));
        semanticStatistics.setDataName(SysCodeEnum.semantic.getName());
        result.add(semanticStatistics);
        // 国标字典
        StatisticsResult nationalCodeStatistics = largeScreenDao.nationalCodeStatistics();
        nationalCodeStatistics.setTodayUpdateCount(nationalCodeStatistics.getDataCount().subtract(queryStandardDefinedYesterdayDataCount(SysCodeEnum.nationalCode.getCode())));
        nationalCodeStatistics.setDataName(SysCodeEnum.nationalCode.getName());
        result.add(nationalCodeStatistics);
        return result;
    }

    /**
     * 标准定义昨日数据查询
     * @param dataType 数据种类
     * @return java.math.BigDecimal
     */
    private BigDecimal queryStandardDefinedYesterdayDataCount(String dataType) {
        BigDecimal yesterdayDataCount = new BigDecimal(0);
        LargeScreenData query = new LargeScreenData();
        query.setCreateTime(DateUtil.formatDate(DateUtil.addDay(new Date(), -1), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
        query.setModuleId(SysCodeEnum.standardDefined.getCode());
        // 标准定义昨日数据查询
        List<LargeScreenData> dataList = largeScreenDao.queryLargeScreenData(query);
        if (CollectionUtils.isNotEmpty(dataList)) {
            List<LastUpdateData> list = JSON.parseArray(dataList.get(0).getDataInfo(), LastUpdateData.class);
            if (CollectionUtils.isNotEmpty(list)) {
                list = list.stream().filter(i -> StringUtils.equals(dataType, i.getDataType())).collect(Collectors.toList());
                yesterdayDataCount = list.get(0).getDataCount();
            }
        }
        return yesterdayDataCount;
    }

    @Override
    @Transactional
    public void fetchYesterdayData() {
        // 查询标准定义模块昨日数据是否存在
        LargeScreenData query = new LargeScreenData();
        query.setModuleId(SysCodeEnum.standardDefined.getCode());
        query.setCreateTime(DateUtil.formatDate(DateUtil.addDay(new Date(), -1), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
        List<LargeScreenData> dataList = largeScreenDao.queryLargeScreenData(query);
        // 不存在则统计数据并保存
        if (CollectionUtils.isEmpty(dataList)) {
            saveStandardDefinedData();
        }
    }

    @Override
    @Transactional
    public void refreshLastUpdateData() {
        LargeScreenData query = new LargeScreenData();
        query.setModuleId(SysCodeEnum.standardDefined.getCode());
        query.setCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
        largeScreenDao.deleteLargeScreenData(query);
        saveStandardDefinedData();
    }

    /**
     * 统计数据大屏数据信息
     * @param dataType 数据类型
     * @param dataInfoList 数据信息集合
     * @return void
     */
    private void statisticsLastUpdateData(String dataType, List<LastUpdateData> dataInfoList) {
        LastUpdateData updateData = new LastUpdateData();
        // 标准定义
        updateData.setDataName(SysCodeEnum.getNameByCodeAndType(dataType, "STANDARDTYPE"));
        updateData.setDataType(dataType);
        BigDecimal dataCount = new BigDecimal(0);
        if (StringUtils.equals(SysCodeEnum.metadata.getCode(), dataType)) {
            dataCount = largeScreenDao.metadataStatistics().getDataCount();
        } else if (StringUtils.equals(SysCodeEnum.semantic.getCode(), dataType)) {
            dataCount = largeScreenDao.semanticStatistics().getDataCount();
        } else if (StringUtils.equals(SysCodeEnum.nationalCode.getCode(), dataType)) {
            dataCount = largeScreenDao.nationalCodeStatistics().getDataCount();
        }
        updateData.setDataCount(dataCount);
        dataInfoList.add(updateData);
    }

    /**
     * 统计并保存标准定义最新数据
     * @return void
     */
    private void saveStandardDefinedData() {
        // 统计数据集合
        List<LastUpdateData> dataInfoList = new ArrayList<>();
        // 元素编码最新数据统计
        statisticsLastUpdateData(SysCodeEnum.metadata.getCode(), dataInfoList);
        // 元素编码最新数据统计
        statisticsLastUpdateData(SysCodeEnum.semantic.getCode(), dataInfoList);
        // 元素编码最新数据统计
        statisticsLastUpdateData(SysCodeEnum.nationalCode.getCode(), dataInfoList);
        // DGN_DATA_LARGE_SCREEN记录保存
        saveLargeScreenData(SysCodeEnum.standardDefined.getCode(), dataInfoList);
    }

    /**
     * 保存数据大屏数据信息
     * @param moduleId 模块ID
     * @param dataInfoList 数据信息集合
     * @return void
     */
    private void saveLargeScreenData(String moduleId, List<LastUpdateData> dataInfoList) {
        LargeScreenData data = new LargeScreenData();
        data.setModuleId(moduleId);
        data.setModuleName(SysCodeEnum.getNameByCodeAndType(moduleId, "MODULETYPE"));
        data.setDataInfo(JSON.toJSONString(dataInfoList));
        largeScreenDao.saveLargeScreenData(data);
    }

    @Override
    public List<StatisticsResult> getQualityGovernStatistics() {
        // 近五个月
        Calendar c = Calendar.getInstance();
        List<java.util.Date> dateList = new ArrayList<>();
        for (int i = -4; i <= 0; i++) {
            dateList.add(DateUtils.addMonths(c.getTime(), i));
        }
        List<StatisticsResult> errorList = new ArrayList<>();
        List<StatisticsResult> finishedList = new ArrayList<>();
        for (Date date : dateList) {
            List<DataGovernanceEffect> list = largeScreenDao.getQualityGovernStatistics(DateUtil.getMonthBegin(date), DateUtil.getMonthEnd(date));
            DataGovernanceEffect response = new DataGovernanceEffect();
            if (!CollectionUtils.isEmpty(list)) {
                response = list.get(0);
            }
            // 问题数据
            StatisticsResult error = new StatisticsResult();
            error.setDataCount(response.getErrorNumber());
            error.setDataName(Constant.ERROR_NUMBER);
            error.setDataTime(DateUtil.formatDate(date, "yyyy-MM"));
            errorList.add(error);
            // 治理完成数据
            StatisticsResult finished = new StatisticsResult();
            finished.setDataCount(response.getFinishedNumber());
            finished.setDataName(Constant.GOVERNANCE_FINISHED);
            finished.setDataTime(DateUtil.formatDate(date, "yyyy-MM"));
            finishedList.add(finished);
        }
        List<StatisticsResult> resultList = new ArrayList<>();
        resultList.addAll(errorList);
        resultList.addAll(finishedList);
        return resultList;
    }

    @Override
    public List<StatisticsResult> getHandleNodeStatistics() {
        // 今日处理数据量，单位：亿条
        return largeScreenDao.getHandleNodeStatistics();
    }

    @Override
    public Map<String, Object> getPrimaryClassifyStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<StatisticsResult> statisticsList = largeScreenDao.getPrimaryClassifyStatistics();
        if (CollectionUtils.isNotEmpty(statisticsList)) {
            for (StatisticsResult statistics : statisticsList) {
                String code = SysCodeEnum.getCodeByNameAndType(statistics.getDataName(), "ORGANIZATIONTYPE");
                if(StringUtils.isNotBlank(code)){
                    map.put(code, statistics);
                }
            }
        }
        return map;
    }

    @Override
    public List<StatisticsResult> getSecondClassifyStatistics(QueryInfo queryInfo) {
        return largeScreenDao.getSecondClassifyStatistics(queryInfo);
    }

    @Override
    public List<StatisticsResult> getThirdClassifyStatistics(QueryInfo queryInfo) {
        return largeScreenDao.getThirdClassifyStatistics(queryInfo);
    }

    @Override
    public List<StatisticsResult> getDataInceptStatistics() throws NacosException {
        List<StatisticsResult> result = new ArrayList<>();
        // 接入数据
        StatisticsResult incept = new StatisticsResult();
        incept.setDataName(Constant.INCEPT_DATA);
        // 获取数据仓库信息
        List<Instance> list = namingServiceImpl.getAllInstances("dataresource");
        DataResource[] dataResources = null;
        if (null == list || list.size() == 0) {
            logger.info("未找到服务名为【DATARESOURCE】的实例");
        } else {
//            ApiConstant.DS_GET_DATA_RESOURCE
//            dataResources = loadBalanced.getForObject("http://dataresource/DataResource/getAllDataResource", DataResource[].class);
            dataResources = loadBalanced.getForObject(ApiConstant.RESOURCE_GET_DATA_RESOURCE_URL, DataResource[].class);
        }
        Date now = new Date();
        Date yesterday = DateUtil.addDay(now, -1);
        List<DataInceptInfo> standardList = largeScreenDao.queryStandardResourceId();
        List<DataInceptInfo> inceptInfoList = handleDataInceptInfo(DateUtil.getDayBegin(now), DateUtil.getDayEnd(now), dataResources);
        if(CollectionUtils.isNotEmpty(standardList)){
            List<DataInceptInfo> nowStandardList = standardList.stream().filter(i->DateUtil.formatDate(now).equals(i.getDataTime())).collect(Collectors.toList());
            inceptInfoList.addAll(nowStandardList);
        }
        long typeCount = inceptInfoList.stream().map(i -> i.getSourceId()).distinct().count();
        incept.setTypeCount(new BigDecimal(typeCount));
        List<DataInceptInfo> yesterdayInceptInfoList = handleDataInceptInfo(DateUtil.getDayBegin(yesterday), DateUtil.getDayEnd(yesterday), dataResources);
        if(CollectionUtils.isNotEmpty(standardList)){
            List<DataInceptInfo> yesterdayStandardList = standardList.stream().filter(i->DateUtil.formatDate(yesterday).equals(i.getDataTime())).collect(Collectors.toList());
            yesterdayInceptInfoList.addAll(yesterdayStandardList);
        }
        long yesterdayTypeCount = yesterdayInceptInfoList.stream().map(i -> i.getSourceId()).distinct().count();
        incept.setTodayUpdateCount(incept.getTypeCount().subtract(new BigDecimal(yesterdayTypeCount)));
        result.add(incept);
        // 下发数据
        StatisticsResult distribute = new StatisticsResult();
        distribute.setDataName(Constant.DISTRIBUTE_DATA);
        StatisticsResult distributeToday = largeScreenDao.getDistributeDataStatistics(DateUtil.formatDate(now, "yyyyMMdd"), DateUtil.formatDate(now, "yyyyMMdd"));
        distribute.setTypeCount(distributeToday.getTypeCount());
        StatisticsResult distributeYesterday = largeScreenDao.getDistributeDataStatistics(DateUtil.formatDate(yesterday, "yyyyMMdd"), DateUtil.formatDate(yesterday, "yyyyMMdd"));
        distribute.setTodayUpdateCount(distribute.getTypeCount().subtract(distributeYesterday.getTypeCount()));
        result.add(distribute);
        // 在更数据：增量数据近5天内存在任意一天数据量大于0的/全量数据近5天内存在任意两天数据量不相等的
        StatisticsResult keepUpdate = new StatisticsResult();
        keepUpdate.setDataName(Constant.KEEP_UPDATE_DATA);
        List<DataAssetsInfo> assetsList = largeScreenDao.getUpdateDataStatistics(DateUtil.addDay(now, -5), now);
        keepUpdate.setTypeCount(new BigDecimal(assetsList.stream().filter(i -> i.getIsUpdating() == 1).map(i -> i.getTableName()).distinct().count()));
        List<DataAssetsInfo> yesterdayAssetsList = largeScreenDao.getUpdateDataStatistics(DateUtil.addDay(yesterday, -5), yesterday);
        keepUpdate.setTodayUpdateCount(keepUpdate.getTypeCount().subtract(new BigDecimal(yesterdayAssetsList.stream().filter(i -> i.getIsUpdating() == 1).map(i -> i.getTableName()).distinct().count())));
        result.add(keepUpdate);
        // 停更数据：非在更数据
        StatisticsResult stopUpdate = new StatisticsResult();
        stopUpdate.setDataName(Constant.STOP_UPDATE_DATA);
        stopUpdate.setTypeCount(new BigDecimal(assetsList.stream().filter(i -> i.getIsUpdating() == 0).map(i -> i.getTableName()).distinct().count()));
        stopUpdate.setTodayUpdateCount(stopUpdate.getTypeCount().subtract(new BigDecimal(yesterdayAssetsList.stream().filter(i -> i.getIsUpdating() == 0).map(i -> i.getTableName()).distinct().count())));
        result.add(stopUpdate);
        return result;
    }

    @Override
    public List<StatisticsResult> getInceptNodeStatistics() throws NacosException {
        List<StatisticsResult> result = new ArrayList<>();
        // 获取数据仓库信息
        List<Instance> list = namingServiceImpl.getAllInstances("dataresource");

//        Application application = eurekaClient.getApplication("DATARESOURCE");
        DataResource[] dataResources = null;
        if (null == list || list.size() == 0) {
            logger.info("未找到服务名为【DATARESOURCE】的实例");
        } else {
//            dataResources = loadBalanced.getForObject("http://dataresource/DataResource/getAllDataResource", DataResource[].class);
            dataResources = loadBalanced.getForObject(ApiConstant.RESOURCE_GET_DATA_RESOURCE_URL, DataResource[].class);

        }
        Date now = new Date();
        Date beginTime = DateUtil.getDayBegin(now);
        Date endTime = DateUtil.getDayEnd(now);
        List<DataInceptInfo> standardList =largeScreenDao.queryStandardNodeInstanceId(beginTime, endTime);
        List<DataInceptInfo> inceptInfoList = handleDataInceptInfo(beginTime, endTime, dataResources);
        if(CollectionUtils.isNotEmpty(standardList)){
            inceptInfoList.addAll(standardList);
        }
        // 过滤节点为空的数据
        inceptInfoList = inceptInfoList.stream().filter(l -> StringUtils.isNotBlank(l.getNodeInstanceId())).collect(Collectors.toList());
        Map<String, List<DataInceptInfo>> dataInceptInfoMap =
                inceptInfoList.stream().collect(Collectors.groupingBy(DataInceptInfo::getNodeInstanceId));
        dataInceptInfoMap.entrySet().parallelStream().forEach(entry -> {
            StatisticsResult temp = new StatisticsResult();
            temp.setDataName(entry.getKey());
            temp.setTypeCount(new BigDecimal(entry.getValue().stream().map(i -> i.getSourceId()).distinct().count()));
            // 今日接入数据量，单位：亿条
            temp.setDataCount(new BigDecimal(entry.getValue().stream().mapToLong(inceptInfo -> inceptInfo.getDataCount()).sum()).divide(new BigDecimal(100000000), 2, BigDecimal.ROUND_HALF_EVEN));
            result.add(temp);
        });
        return result;
    }

    @Override
    public List<StatisticsResult> getInceptRecentStatistics() throws NacosException {
        Calendar c = Calendar.getInstance();
        List<Date> dateList = new ArrayList<>();
        for (int i = -6; i <= 0; i++) {
            dateList.add(DateUtils.addDays(c.getTime(), i));
        }
        List<StatisticsResult> inceptList = new ArrayList<>();
        List<StatisticsResult> distributeList = new ArrayList<>();
        // 获取数据仓库信息
        List<Instance> list = namingServiceImpl.getAllInstances("dataresource");
//        Application application = eurekaClient.getApplication("DATARESOURCE");
        DataResource[] dataResources = null;
        if (null == list || list.size() == 0) {
            logger.info("未找到服务名为【DATARESOURCE】的实例");
        } else {
//            dataResources = loadBalanced.getForObject("http://dataresource/DataResource/getAllDataResource", DataResource[].class);
            dataResources = loadBalanced.getForObject(ApiConstant.RESOURCE_GET_DATA_RESOURCE_URL, DataResource[].class);
        }
        List<DataInceptInfo> recentDataCountList = largeScreenDao.queryRecentDataCount();
        for (Date date : dateList) {
            // 接入数据,单位：亿条
            StatisticsResult incept = new StatisticsResult();
            String dateString = DateUtil.formatDate(date);
            Date beginTime = DateUtil.getDayBegin(date);
            Date endTime = DateUtil.getDayEnd(date);
            List<DataInceptInfo> inceptInfoList = handleDataInceptInfo(beginTime, endTime, dataResources);
            List<DataInceptInfo> dataList = recentDataCountList.stream().filter(i->dateString.equals(i.getDataTime())).collect(Collectors.toList());
            long dataCount = inceptInfoList.stream().mapToLong(inceptInfo -> inceptInfo.getDataCount()).sum();
            if(CollectionUtils.isNotEmpty(dataList)){
                dataCount = dataCount + dataList.get(0).getDataCount();
            }
            incept.setDataCount(new BigDecimal(dataCount).divide(new BigDecimal(100000000), 2, BigDecimal.ROUND_HALF_EVEN));
            incept.setDataName(Constant.INCEPT_DATA);
            incept.setDataTime(dateString);
            inceptList.add(incept);
            // 下发数据,单位：亿条
            StatisticsResult distribute = new StatisticsResult();
            StatisticsResult distributeInfo = largeScreenDao.getDistributeDataStatistics(DateUtil.formatDate(date, "yyyyMMdd"), DateUtil.formatDate(date, "yyyyMMdd"));
            distribute.setDataCount(distributeInfo.getDataCount().divide(new BigDecimal(100000000), 2, BigDecimal.ROUND_HALF_EVEN));
            distribute.setDataName(Constant.DISTRIBUTE_DATA);
            distribute.setDataTime(dateString);
            distributeList.add(distribute);
        }
        List<StatisticsResult> resultList = new ArrayList<>();
        resultList.addAll(inceptList);
        resultList.addAll(distributeList);
        return resultList;
    }

    @Override
    public List<StatisticsResult> getHandleRecentStatistics() {
        List<StatisticsResult> resultList = largeScreenDao.getHandleDataStatistics();
        return resultList;
    }

    @Override
    public List<StatisticsResult> getDataHandleStatistics() {
        List<StatisticsResult> resultList = new ArrayList<>();
        Date now = new Date();
        // 源数据协议
        StatisticsResult sourceData = new StatisticsResult();
        sourceData.setTypeCount(largeScreenDao.getSourceDataStatistics(DateUtil.getDayBegin(now), DateUtil.getDayEnd(now)));
        sourceData.setDataName(Constant.HANDLE_SOURCE_DATA);
        resultList.add(sourceData);
        return resultList;
    }

    @Override
    public Map<String, Object> getDataCenterStatistics(String platFormType) throws NacosException {
        Map<String, Object> map = new HashMap<>();
        // 今日
        Date now = new Date();
        // 接入数据,单位：亿条
        StatisticsResult inceptInfo = largeScreenDao.getHandleDataStorageStatistics(DateUtil.getDayBegin(now), DateUtil.getDayEnd(now));
        map.put("inceptInfo", inceptInfo);
        // 下发数据,单位：亿条
        StatisticsResult distributeInfo = largeScreenDao.getDistributeDataStatistics(DateUtil.formatDate(now, "yyyyMMdd"), DateUtil.formatDate(now, "yyyyMMdd"));
        distributeInfo.setDataCount(distributeInfo.getDataCount().divide(new BigDecimal(100000000), 2, BigDecimal.ROUND_HALF_EVEN));
        map.put("distributeInfo", distributeInfo);
        // 存储信息
        List<DataBaseState> storeList = new ArrayList<>();
        // 调用数据资产获取存储信息接口
        List<Instance> list = namingServiceImpl.getAllInstances("property");
        if (null == list || list.size() == 0) {
            logger.info("未找到服务名为【PROPERTY】的实例");
        } else {
//            ServerResponse response = loadBalanced.getForObject("http://property/dataStorageMonitoring/getDataBaseStatus?platFormType=" + platFormType, ServerResponse.class);
            ServerResponse response = loadBalanced.getForObject(ApiConstant.PROPERTY_GET_DB_STATUS + platFormType, ServerResponse.class);
            if (response.getStatus() == 1) {
                storeList = (List<DataBaseState>) response.getData();
            } else {
                logger.error("调用数据资产获取存储信息接口出错");
            }
        }
        map.put("storeInfo", storeList);
        return map;
    }

    private List<DataInceptInfo> handleDataInceptInfo(Date beginTime, Date endTime, DataResource[] dataResources) {
        // 接入数据
        List<DataInceptInfo> inceptInfoList = largeScreenDao.getInceptDataStatistics(beginTime, endTime);
        // json解析得到数据源类型、数据源id、数据量、协议id
        inceptInfoList.stream().forEach(
                item -> {
                    String statisticlocal = StringUtils.isNotBlank(item.getStatisticStr()) ? JSONObject.parseObject(item.getStatisticStr()).getString("statisticlocal") : StringUtils.EMPTY;
                    long dataCount = StringUtils.isNotBlank(statisticlocal) ? JSONObject.parseObject(statisticlocal).getLongValue("writeSucceedRecords") : 0L;
                    item.setDataCount(dataCount);
                    String sourceId = StringUtils.isNotBlank(item.getExtraInfoStr()) ? JSONObject.parseObject(item.getExtraInfoStr()).getString("sourceId") : StringUtils.EMPTY;
                    item.setSourceId(StringUtils.isNotBlank(sourceId) ? sourceId : StringUtils.EMPTY);
                    item.setDataId(StringUtils.isNotBlank(item.getJobDataxJson()) ? JSONObject.parseObject(item.getJobDataxJson()).getString("writer") : StringUtils.EMPTY);
                });
        // 过滤掉接入到质检、接入到标准化的FTP
        if (null != dataResources && dataResources.length > 0) {
            List<DataResource> dataResourceList = Arrays.asList(dataResources);
//            List<DataResource> dataResourceList = Arrays.asList(dataResources).stream().filter(a -> StringUtils.isNotBlank(a.getFtpPurpose())
//                    && (Constant.PURPOSE_ZERO.equalsIgnoreCase(a.getFtpPurpose().trim()) || Constant.PURPOSE_ONE.equalsIgnoreCase(a.getFtpPurpose().trim())))
//                    .collect(Collectors.toList());
            inceptInfoList = inceptInfoList.stream().filter(a -> !dataResourceList.stream().map(b -> b.getResId()).
                    collect(Collectors.toList()).contains(a.getDataId())).collect(Collectors.toList());
        }
        return inceptInfoList.stream().filter(l -> l.getDataCount() > 0).collect(Collectors.toList());
    }
}
