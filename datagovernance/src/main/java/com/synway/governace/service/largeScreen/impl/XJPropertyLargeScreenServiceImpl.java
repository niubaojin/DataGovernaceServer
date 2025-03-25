package com.synway.governace.service.largeScreen.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.synway.governace.common.ApiConstant;
import com.synway.governace.dao.PropertyLargeScreenDao;
import com.synway.governace.pojo.largeScreen.ForXJ.*;
import com.synway.governace.pojo.largeScreen.PropertyLargeDbData;
import com.synway.governace.service.largeScreen.XJPropertyLargeScreenService;
import com.synway.governace.util.DateUtil;
import com.synway.governace.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author chenfei
 * @Data 2024/7/1 14:24
 * @Description 新疆大屏
 */
@Service
public class XJPropertyLargeScreenServiceImpl implements XJPropertyLargeScreenService {
    private Logger logger = LoggerFactory.getLogger(XJPropertyLargeScreenServiceImpl.class);
    @Autowired
    private PropertyLargeScreenDao propertyLargeScreenDao;
    @Resource
    private Environment environment;
    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Override
    @Scheduled(cron = "0 */10 * * * ?")
    public void scheduledTaskXJ(){
        try {
            logger.info("新疆大屏定时任务开始运行");
            // 301：获取数据接入统计量
            getDataInsert();
            // 302：获取数据接入折线图数据
            getDataInsertChart();
            // 303：获取数据组织数据
            getDataOrganization();
            // 304：获取告警数据
            getWarnData();
            // 305：获取分级分类（中间的环形图）
            getGradeAndClass();
            // 306：获取来源（中间顶部的3个格子）
            getDataSource();
            // 新疆资产大屏：调用资源服务平台接口先不用、使用静态数据，后面需要再打开
            // 307：数据使用热度
            getDataUsageTop();
            // 308：应用使用热度
            getAppUsageTop();
            // 309：数据服务
            getDataServiceInfo();
        }catch (Exception e){
            logger.error("新疆大屏定时任务运行报错：\n" , e);
        }
    }

    @Override
    public JSONObject getPropertyLargeScreenDataForXJ() {
        /**
         * 获取固定数据
         * 301:数据接入
         * 302:数据接入下面的线图
         * 303:数据组织
         * 304:数据告警
         * 305:分级分类（中间的环形图）
         * 306:来源（中间顶部的3个格子）
         * 307:数据使用top5
         * 308:应用使用top5
         * 309:数据服务
         * 310:服务单位
         */
        List<PropertyLargeDbData> list = propertyLargeScreenDao.getPropertyLargeScreenDataPage();
        JSONObject jsonObject = new JSONObject();
        // 数据服务-》服务使用-》服务单位 手动填写数据
        JSONObject jsonObject311 = new JSONObject();
        for (PropertyLargeDbData data : list) {
            if (data.getType() == 311){
                jsonObject311 = JSONObject.parseObject(data.getData());
                break;
            }
        }

        for (PropertyLargeDbData data : list) {
            switch (data.getType()) {
                case 301:
                    JSONArray dataInsert = JSONObject.parseArray(data.getData());
                    jsonObject.put("dataInsert", dataInsert);
                    break;
                case 302:
                    JSONObject dataInsertChart = JSONObject.parseObject(data.getData());
                    jsonObject.put("dataInsertChart", dataInsertChart);
                    break;
                case 303:
                    JSONObject dataOrganization = JSONObject.parseObject(data.getData());
                    jsonObject.put("dataOrganization", dataOrganization);
                    break;
                case 304:
                    JSONArray warnData = JSONArray.parseArray(data.getData());
                    jsonObject.put("warnData", warnData);
                    break;
                case 305:
                    JSONArray gradeAndClass = JSONArray.parseArray(data.getData());
                    jsonObject.put("gradeAndClass", gradeAndClass);
                    break;
                case 306:
                    JSONArray dataSource = JSONArray.parseArray(data.getData());
                    jsonObject.put("dataSource", dataSource);
                    break;
                case 307:
                    JSONArray dataTop5 = JSONArray.parseArray(data.getData());
//                    dataTop5 = jsonArraySort(dataTop5);
                    jsonObject.put("dataTop5", dataTop5);
                    break;
                case 308:
                    JSONArray appTop5 = JSONArray.parseArray(data.getData());
//                    appTop5 = jsonArraySort(appTop5);
                    jsonObject.put("appTop5", appTop5);
                    break;
                case 309:
                    JSONArray dataService = JSONArray.parseArray(data.getData());
                    // 服务单位重新赋值
                    getServerUnitValue(dataService, jsonObject311);
                    jsonObject.put("dataService", dataService);
                    break;
                case 310:
                    JSONArray serviceUnit = JSONArray.parseArray(data.getData());
//                    serviceUnit = jsonArraySort(serviceUnit);
                    jsonObject.put("serviceUnit", serviceUnit);
                    break;
                default:
                    break;
            }
        }
        String dateStr = DateUtil.formatDateTime(new Date());
        jsonObject.put("updateTime", dateStr);
        return jsonObject;
    }

    /**
     * 获取数据接入统计量：301
     */
    public void getDataInsert(){
        try {
            DataInsert dataInsertAll = propertyLargeScreenDao.getDataInsertAll();
            DataInsert dataInsertYestoday = propertyLargeScreenDao.getDataInsert(0);
            DataInsert dataInsertAverage7 = propertyLargeScreenDao.getDataInsert(6);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObjectAll = new JSONObject();    // 数据总量
            JSONObject jsonObjectAve = new JSONObject();    // 近7日平均量
            JSONObject jsonObjectYes = new JSONObject();    // 昨日数据量
            jsonObjectAll.put("name", "数据总量");
            jsonObjectAll.put("num", dataInsertAll.getAllCount());
            jsonObjectAll.put("numUnit", "亿条");
            DataInfo allSize = new DataInfo();
            unitConversion(allSize, Long.parseLong(dataInsertAll.getAllSize()));
            jsonObjectAll.put("volume", String.valueOf(allSize.getValue()));
            jsonObjectAll.put("volumeUnit", allSize.getLabel());

            jsonObjectAve.put("name", "近7日平均量");
            jsonObjectAve.put("num", dataInsertAverage7.getPartitionAllCount());
            jsonObjectAve.put("numUnit", "亿条");
            DataInfo average7 = new DataInfo();
            unitConversion(average7, Long.parseLong(dataInsertAverage7.getPartitionAllSize()));
            jsonObjectAve.put("volume", String.valueOf(average7.getValue()));
            jsonObjectAve.put("volumeUnit", average7.getLabel());

            jsonObjectYes.put("name", "昨日数据量");
            jsonObjectYes.put("num", dataInsertYestoday.getPartitionAllCount());
            jsonObjectYes.put("numUnit", "亿条");
            DataInfo partitionSize = new DataInfo();
            unitConversion(partitionSize, Long.parseLong(dataInsertYestoday.getPartitionAllSize()));
            jsonObjectYes.put("volume", String.valueOf(partitionSize.getValue()));
            jsonObjectYes.put("volumeUnit", partitionSize.getLabel());
            jsonArray.add(jsonObjectAll);
            jsonArray.add(jsonObjectAve);
            jsonArray.add(jsonObjectYes);
            // 更新数据库
            insertDataToDb(JSONObject.toJSONString(jsonArray), 301);
        }catch (Exception e){
            logger.error("定时任务更新数据接入统计量报错:\n" , e);
        }
    }

    // 单位转换
    public void unitConversion(DataInfo sizeUnit, long size){
        if (size > 1024){
            size = size / 1024;
            sizeUnit.setValue(size);
            sizeUnit.setLabel("TB");
        }else {
            sizeUnit.setValue(size);
            sizeUnit.setLabel("GB");
        }
    }

    /**
     * 获取数据接入折线图数据：302
     */
    public void getDataInsertChart(){
        try {
            List<DataInsert> dataInserts = propertyLargeScreenDao.getDataInsertChart(6);
            JSONObject jsonObject = new JSONObject();   // 返回数据
            JSONArray dates = new JSONArray();          // 日期列表
            JSONArray series = new JSONArray();         // series数据列表

            JSONObject size = new JSONObject();         // 数据存储对象
            JSONObject count = new JSONObject();        // 数据量对象
            JSONArray sizes = new JSONArray();          // 数据存储列表
            JSONArray counts = new JSONArray();         // 数据量列表
            dataInserts.stream().forEach(data ->{
                dates.add(data.getInsertDate());
                sizes.add(data.getPartitionAllSize());
                counts.add(data.getPartitionAllCount());
            });
            size.put("data", sizes);
            size.put("name", "数据存储");
            size.put("unit", "存储(GB)");
            series.add(size);
            count.put("data", counts);
            count.put("name", "数据量");
            count.put("unit", "数量(亿条)");
            series.add(count);
            jsonObject.put("date", dates);
            jsonObject.put("series", series);

            // 更新数据库
            insertDataToDb(JSONObject.toJSONString(jsonObject), 302);
        }catch (Exception e){
            logger.error("定时任务更新数据接入折线图数据报错:\n" , e);
        }
    }

    /**
     * 获取数据组织数据：303
     */
    public void getDataOrganization(){
        try {
            List<DataOrganization> dataOrganizations = propertyLargeScreenDao.getDataOrganization();
            JSONObject jsonObject = new JSONObject();   // 返回数据
            jsonObject.put("data", dataOrganizations);
            jsonObject.put("tablesName", "表数据量（张）");
            jsonObject.put("totalNumsName", "数据总量（亿条）");

            // 更新数据库
            insertDataToDb(JSONObject.toJSONString(jsonObject), 303);
        }catch (Exception e){
            logger.error("定时任务更新数据组织数据报错:\n" , e);
        }
    }

    /**
     * 获取数据告警数据：304
     */
    public void getWarnData(){
        try {
            List<WarnData> warnData = propertyLargeScreenDao.getWarnData();

            // 更新数据库
            insertDataToDb(JSONObject.toJSONString(warnData), 304);
        }catch (Exception e){
            logger.error("定时任务更新数据告警数据报错:\n" , e);
        }
    }

    /**
     * 获取分级分类（中间的环形图）数据：305
     */
    public void getGradeAndClass(){
        try {
            JSONArray jsonArray = new JSONArray();
            List<GradeAndClass> gradeAndClass = propertyLargeScreenDao.getGradeAndClass(0);
            Map<String, List<GradeAndClass>> map = gradeAndClass.stream().collect(Collectors.groupingBy(GradeAndClass::getClassName));
            for (String key : map.keySet()){
                List<GradeAndClass> gradeAndClasses = map.get(key);
                gradeAndClasses.stream().forEach(d ->{
                    d.setClassName(null);
                });
                // 排序
                JSONArray gradeAndClassesArr = JSONArray.parseArray(JSONArray.toJSONString(gradeAndClasses));
                gradeAndClassesArr = jsonArraySort(gradeAndClassesArr);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", key);
                jsonObject.put("data", gradeAndClassesArr);
                jsonArray.add(jsonObject);
            }

            // 更新数据库
            insertDataToDb(JSONObject.toJSONString(jsonArray), 305);
        }catch (Exception e){
            logger.error("定时任务更新分级分类（中间的环形图）数据报错:\n" , e);
        }
    }

    /**
     * 获取来源（中间顶部的3个格子）数据：306
     */
    public void getDataSource(){
        try {
            List<GradeAndClass> gradeAndClass = propertyLargeScreenDao.getDataSource(0);
            List<DataOrganization> dataOrganizations = propertyLargeScreenDao.getDataOrganization();

            Integer tables = 0;
            for (int i = 0; i < dataOrganizations.size(); i++){
                if (dataOrganizations.get(i).getLabel().equalsIgnoreCase("原始汇集库")){
                    tables = Integer.parseInt(dataOrganizations.get(i).getTables());
                    break;
                }
            }
            GradeAndClass gradeAndClass1 = new GradeAndClass();
            gradeAndClass1.setLabel("数据表量");
            gradeAndClass1.setUnit("张");
            gradeAndClass1.setValue(tables);
            gradeAndClass.add(gradeAndClass1);

            // 更新数库
            insertDataToDb(JSONObject.toJSONString(gradeAndClass), 306);
        }catch (Exception e){
            logger.error("定时任务更新来源（中间顶部的3个格子）数据报错:\n" , e);
        }
    }

    /**
     * 获取数据使用top并更新：:307
     */
    public void getDataUsageTop() {
        try {
//            String nginxIp = environment.getRequiredProperty("nginxIp");
            String dataUsageTopUrl = ApiConstant.SERVICEFACSRV_BASEURL + "/servicefacsrv/interface/queryDataUsageTop";
            String dataUsageTop = restTemplateHandle.getServicefacsrvInterface(dataUsageTopUrl);
            List<DataInfo> dataInfos = new ArrayList<>();
            if (StringUtils.isNotBlank(dataUsageTop)) {
                List<DataUsageTopInfo> dataUsageTopInfos = JSONArray.parseArray(dataUsageTop, DataUsageTopInfo.class);
                if (CollectionUtils.isNotEmpty(dataUsageTopInfos)) {
                    for (DataUsageTopInfo dataUsageTopInfo : dataUsageTopInfos) {
                        DataInfo dataInfo = new DataInfo();
                        dataInfo.setLabel(dataUsageTopInfo.getDataResourceName());
                        dataInfo.setValue(dataUsageTopInfo.getCount() / 10000);
                        dataInfos.add(dataInfo);
                    }
                }
            }
            // 更新数据库
            insertDataToDb(JSONObject.toJSONString(dataInfos), 307);
        } catch (Exception e) {
            logger.error("定时任务更新服务使用top5报错" , e);
        }
    }

    /**
     * 获取应用使用top并更新：308
     */
    public void getAppUsageTop() {
        try {
            List<DataInfo> dataInfos = new ArrayList<>();
//            String nginxIp = environment.getRequiredProperty("nginxIp");
            String appUsageTopUrl = ApiConstant.SERVICEFACSRV_BASEURL + "/servicefacsrv/interface/queryAppUsageTop";
            String appUsageTop = restTemplateHandle.getServicefacsrvInterface(appUsageTopUrl);
            if (StringUtils.isNotBlank(appUsageTop)) {
                List<AppUsageTopInfo> appUsageTopInfos = JSONArray.parseArray(appUsageTop, AppUsageTopInfo.class);
                if (CollectionUtils.isNotEmpty(appUsageTopInfos)) {
                    for (AppUsageTopInfo appUsageTopInfo : appUsageTopInfos) {
                        DataInfo dataInfo = new DataInfo();
                        dataInfo.setLabel(appUsageTopInfo.getYyxtmc());
                        dataInfo.setValue(appUsageTopInfo.getCount() / 10000);
                        dataInfos.add(dataInfo);
                    }
                }
            }
            //更新数据库
            insertDataToDb(JSONObject.toJSONString(dataInfos), 308);
        } catch (Exception e) {
            logger.error("定时任务更新应用使用top5报错" , e);
        }
    }

    public void getServerUnitValue(JSONArray jsonArray309, JSONObject jsonObject311){
        for (int i = 0; i< jsonArray309.size(); i++){
            JSONObject data = (JSONObject) jsonArray309.get(i);
            if (data.getString("name").equalsIgnoreCase("服务使用")){
                JSONArray serverUseData = (JSONArray) data.get("data");
                for (int j = 0; j < serverUseData.size(); j++){
                    JSONObject serverUnit = (JSONObject) serverUseData.get(j);
                    if (serverUnit.getString("label").equalsIgnoreCase("服务单位")){
                        String serverUnitValue = jsonObject311 != null ? jsonObject311.getString("value") : serverUnit.getString("value");
                        serverUnit.fluentRemove("value");
                        serverUnit.put("value", serverUnitValue);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * 获取数据服务数据并更新数据库：309
     */
    public void getDataServiceInfo() {
        try {
            List<DataServiceInfo> dataServiceInfos = new ArrayList<>();
//            String nginxIp = environment.getRequiredProperty("nginxIp");
            String baseurl = ApiConstant.SERVICEFACSRV_BASEURL;
            // 服务统计
            getServiceStatisticsInfo(dataServiceInfos, baseurl);
            // 服务使用
            getServiceUsageInfo(dataServiceInfos, baseurl);
            // 服务种类
            getServiceKindInfo(dataServiceInfos, baseurl);
            // 将 dataServiceInfos 插入数据库  309 数据服务
            insertDataToDb(JSONObject.toJSONString(dataServiceInfos), 309);
        } catch (Exception e) {
            logger.error("定时任务更新服务数据报错" , e);
        }
    }

    // 排序
    private JSONArray jsonArraySort(JSONArray jsonArray){
        if (jsonArray.size()>0){
            List<JSONObject> list = JSONArray.parseArray(jsonArray.toJSONString(), JSONObject.class);
            Collections.sort(list, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2){
                    int a = Integer.parseInt(o1.getString("value"));
                    int b = Integer.parseInt(o2.getString("value"));
                    if (a > b){
                        return -1;
                    }else if (a == b){
                        return 0;
                    }else {
                        return 1;
                    }
                }
            });
            jsonArray = JSONArray.parseArray(list.toString());
        }
        return jsonArray;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertDataToDb(String data,int type) throws Exception{
        if (StringUtils.isBlank(data) || data.equalsIgnoreCase("[]")){
            logger.info("统计到的资产大屏数据为空，type:" + type);
            return;
        }
        // 可能存在假数据等内容
        logger.info("[资产大屏]开始删除类型为"+type+"数据");
        int delNum =propertyLargeScreenDao.delDataByTypeAndFlag(type,0);
        logger.info("[资产大屏]类型"+type+"删除的数据量为"+delNum);
        String uuidStr = UUID.randomUUID().toString();
        int insertNum =propertyLargeScreenDao.insertData(data,type,uuidStr);
        logger.info("[资产大屏]类型"+type+"插入的数据量为"+insertNum);
    }


    private void getServiceKindInfo(List<DataServiceInfo> dataServiceInfos, String baseurl) {
        DataServiceInfo kindInfo = new DataServiceInfo();
        kindInfo.setName("服务种类");
        try {
            String serviceKindUrl = baseurl + "/servicefacsrv/interface/queryServiceCountByClass";
            String kindData = restTemplateHandle.getServicefacsrvInterface(serviceKindUrl);
            if (StringUtils.isNotBlank(kindData)) {
                ServiceKindInfo serviceKindInfo = JSONObject.parseObject(kindData, ServiceKindInfo.class);
                List<DataInfo> dataInfos = new ArrayList<>();
                DataInfo pushServiceCount = new DataInfo();
                pushServiceCount.setLabel("数据推送");
                pushServiceCount.setValue(serviceKindInfo.getPushServiceCount());
                dataInfos.add(pushServiceCount);
                DataInfo queryServiceCount = new DataInfo();
                queryServiceCount.setLabel("查询检索");
                queryServiceCount.setValue(serviceKindInfo.getQueryServiceCount());
                dataInfos.add(queryServiceCount);
                DataInfo subscribeServiceCount = new DataInfo();
                subscribeServiceCount.setLabel("比对订阅");
                subscribeServiceCount.setValue(serviceKindInfo.getSubscribeServiceCount());
                dataInfos.add(subscribeServiceCount);
                kindInfo.setData(dataInfos);
            }
        } catch (Exception e) {
            logger.error("调用资源服务平台查询服务种类接口错误" , e);
        }
        dataServiceInfos.add(kindInfo);
    }

    private void getServiceUsageInfo(List<DataServiceInfo> dataServiceInfos, String baseurl) {
        DataServiceInfo usageInfo = new DataServiceInfo();
        usageInfo.setName("服务使用");
        try {
            String serviceUsageUrl = baseurl + "/servicefacsrv/interface/queryServiceUsageInfo";
            String usageData = restTemplateHandle.getServicefacsrvInterface(serviceUsageUrl);
            if (StringUtils.isNotBlank(usageData)) {
                ServiceUsageInfo serviceUsageInfo = JSONObject.parseObject(usageData, ServiceUsageInfo.class);
                List<DataInfo> dataInfos = new ArrayList<>();
                DataInfo organCount = new DataInfo();
                organCount.setLabel("服务单位");
                organCount.setValue(serviceUsageInfo.getOrganCount());
                dataInfos.add(organCount);
                DataInfo appCount = new DataInfo();
                appCount.setLabel("平台应用");
                appCount.setValue(serviceUsageInfo.getAppCount());
                dataInfos.add(appCount);
                DataInfo callCount = new DataInfo();
                long callCountValue = 0;
                if (serviceUsageInfo.getCallCount() >= 10000){
                    callCountValue = serviceUsageInfo.getCallCount() / 10000;
                    callCount.setLabel("调用次数（万次）");
                    callCount.setValue(callCountValue);
                }else {
                    callCountValue = serviceUsageInfo.getCallCount();
                    callCount.setLabel("调用次数");
                    callCount.setValue(callCountValue);
                }
                dataInfos.add(callCount);
                usageInfo.setData(dataInfos);
            }
        } catch (Exception e) {
            logger.error("调用资源服务平台查询服务使用接口错误" , e);
        }
        dataServiceInfos.add(usageInfo);
    }

    private void getServiceStatisticsInfo(List<DataServiceInfo> dataServiceInfos, String baseurl) {
        DataServiceInfo statisticsInfo = new DataServiceInfo();
        statisticsInfo.setName("服务统计");
        try {
            String serviceStatisticsUrl = baseurl + "/servicefacsrv/interface/getSharedResourcesCount";
            String statisticsData = restTemplateHandle.getServicefacsrvInterface(serviceStatisticsUrl);
            if (StringUtils.isNotBlank(statisticsData)) {
                ServiceStatisticsInfo serviceStatisticsInfo = JSONObject.parseObject(statisticsData, ServiceStatisticsInfo.class);
                List<DataInfo> dataInfos = new ArrayList<>();
                DataInfo dataCount = new DataInfo();
                dataCount.setLabel("共享数据种类");
                dataCount.setValue(serviceStatisticsInfo.getDataCount());
                dataInfos.add(dataCount);
                DataInfo serviceCount = new DataInfo();
                serviceCount.setLabel("共享服务数");
                serviceCount.setValue(serviceStatisticsInfo.getServiceCount());
                dataInfos.add(serviceCount);
                statisticsInfo.setData(dataInfos);
            }
        } catch (Exception e) {
            logger.error("调用资源服务平台查询服务统计接口错误" , e);
        }
        dataServiceInfos.add(statisticsInfo);
    }
}
