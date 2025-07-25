package com.synway.governace.service.largeScreen.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.synway.governace.common.ApiConstant;
import com.synway.governace.common.Constant;
import com.synway.governace.dao.PropertyLargeScreenDao;
import com.synway.governace.enums.PropertyLargeDetailedEnum;
import com.synway.governace.interceptor.IgnoreSecurity;
import com.synway.governace.pojo.largeScreen.*;
import com.synway.governace.pojo.largeScreen.ForXJ.AppUsageTopInfo;
import com.synway.governace.pojo.largeScreen.ForXJ.DataInfo;
import com.synway.governace.pojo.largeScreen.ForXJ.DataServiceInfo;
import com.synway.governace.pojo.largeScreen.ForXJ.DataUsageTopInfo;
import com.synway.governace.service.largeScreen.LargeScreenService;
import com.synway.governace.service.largeScreen.PropertyLargeScreenService;
import com.synway.governace.util.DateUtil;
import com.synway.governace.util.PinYinUtil;
import com.synway.governace.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @date 2021/4/25 13:27
 */
@Service
public class PropertyLargeScreenServiceImpl implements PropertyLargeScreenService {
    private Logger logger = LoggerFactory.getLogger(PropertyLargeScreenServiceImpl.class);
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final DecimalFormat TEN_MILLION_FORMAT = new DecimalFormat("00000000");
    private static final DecimalFormat TEN_THOUSAND_FORMAT = new DecimalFormat("0000");

    private static final BigInteger TEN_MILLION = BigInteger.valueOf(10000000);
    private static final BigInteger TEN_THOUSAND = BigInteger.valueOf(10000);
    private static final BigInteger BILLION = BigInteger.valueOf(100000000);

    @Autowired
    private PropertyLargeScreenDao propertyLargeScreenDao;
    @Autowired
    private LargeScreenService largeScreenServiceImpl;
    @Autowired
    private RestTemplateHandle restTemplateHandle;
    @Resource
    private Environment environment;
    @Value("${provinceName}")
    private String provinceName;
    @Value("${assets.large.screen.version}")
    private String assetsLargeScreenVersion;

    @IgnoreSecurity
    @Override
    @Scheduled(cron = "0 0/30 * * * ?")
    public void scheduledTask(){
        try{
            logger.info("定时任务开始运行");
            // 原始库日增量
            getOperatorData();
            getFiledClassData();
            getOriginalDataList();
            totalDataPropertyScheduled();
            // 资产使用情况
            getPropertyUsage();
            getDataShareMapList();
            // 对外共享-服务工厂
            getPublishInfo();
            getPropertyData();
            // 标签的定时任务
            getLabelData();
            // 原始业务库资产情况
            getOriginalBusinessData();
            // 获取大存储量数据排行榜
            getLargeStorageData();
            // 普元信息总线的相关接口（对外共享-服务总线）
            getPuYuanInformationBus();
            // 获取数据地图资产
            getDataMapAssets();
            logger.info("定时任务结束运行");
        }catch (Exception e){
            logger.error("定时任务运行报错", e);
        }
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void deleteTable(){
        try{
            logger.info("开始删除表 inspect_organization_stat");
            propertyLargeScreenDao.deleteOldTable1();
        }catch (Exception e){
            logger.error("删除inspect_organization_stat失败：", e);
        }
        try{
            logger.info("开始删除表 DGN_INSPECT_OPERATORS");
            propertyLargeScreenDao.deleteOldTable2();
        }catch (Exception e){
            logger.error("删除DGN_INSPECT_OPERATORS失败：", e);
        }
        try{
            logger.info("开始删除表 DGN_DISTRIBUTION_STATISTIC");
            propertyLargeScreenDao.deleteOldTable3();
        }catch (Exception e){
            logger.error("删除DGN_DISTRIBUTION_STATISTIC失败：", e);
        }
    }

    /**
     * 页面上获取数据库表 DGN_PROPERTY_LARGE里面的数据
     *
     * @return
     */
    @Override
    public PropertyLargeScreenData getPropertyLargeScreenDataPage() {

        /**
         * 获取所有的定时任务统计的数据
         * 0:数据总资产(包含水位图)1:原始库日接入情况2:资源库资产情况3:主题库资产情况4:标签库资产情况
         * 5:数据日接入情况(分为jz版本和tz版本)
         * 6:字段分类的数据7:资产使用情况8:对外共享9:地图数据
         */
        List<PropertyLargeDbData> list = propertyLargeScreenDao.getPropertyLargeScreenDataPage();
        PropertyLargeScreenData propertyLargeScreenData = new PropertyLargeScreenData();
        for(PropertyLargeDbData data:list){
            switch (data.getType()){
                case 0:
                    // 数据总资产
                    TotalDataProperty totalDataProperty = JSONObject.parseObject(data.getData(),TotalDataProperty.class);
                    propertyLargeScreenData.setTotalDataProperty(totalDataProperty);
                    break;
                case 1:
                    // 原始库接入情况
                    List<StandardLabelData> list1 = JSONObject.parseArray(data.getData(),StandardLabelData.class);
                    propertyLargeScreenData.setOriginalDataList(list1);
                    long tableSum = list1.stream().mapToLong(StandardLabelData::getTableCount).sum();
                    propertyLargeScreenData.setOriginalTableCount(tableSum);
                    break;
                case 2:
                    //资源库资产情况
                    List<StandardLabelData> list2 = JSONObject.parseArray(data.getData(),StandardLabelData.class);
                    propertyLargeScreenData.setResourceLibraryList(list2);
                    long tableSum1 = list2.stream().mapToLong(StandardLabelData::getStandTableCount).sum();
                    propertyLargeScreenData.setResourceLibraryTableCount(tableSum1);
                    break;
                case 3:
                    // 3:主题库资产情况
                    List<StandardLabelData> list3 = JSONObject.parseArray(data.getData(),StandardLabelData.class);
                    propertyLargeScreenData.setThemeLibraryList(list3);
                    long tableSum2 = list3.stream().mapToLong(StandardLabelData::getStandTableCount).sum();
                    propertyLargeScreenData.setThemeLibraryTableCount(tableSum2);
                    break;
                case 4:
                    // 标签库资产情况
                    LabelProperty labelProperty= JSONObject.parseObject(data.getData(),LabelProperty.class);
                    propertyLargeScreenData.setLabelProperty(labelProperty);
                    break;
                case 5:
                    // 数据日接入情况  jz版本
                    List<OperatorData> list4 = JSONObject.parseArray(data.getData(),OperatorData.class);
                    propertyLargeScreenData.setOperatorDataList(list4);
                    break;
                case 6:
                    // 字段分类的数据
                    List<FiledClassData> list5 = JSONObject.parseArray(data.getData(),FiledClassData.class);
                    propertyLargeScreenData.setFiledClassDataList(list5);
                    break;
                case 7:
                    // 资产使用情况
                    PropertyUsage propertyUsage = JSONObject.parseObject(data.getData(),PropertyUsage.class);
                    propertyLargeScreenData.setPropertyUsage(propertyUsage);
                    break;
                case 8:
                    //对外共享
                    PublishInfo publishInfo = JSONObject.parseObject(data.getData(),PublishInfo.class);
                    propertyLargeScreenData.setPublishInfo(publishInfo);
                    break;
                case 9:
                    //地图数据
                    List<DataShareMap> list6 = JSONObject.parseArray(data.getData(),DataShareMap.class);
                    propertyLargeScreenData.setDataShareMaps(list6);
                    break;
                case 10:
                    Map<String,List<Object>> map = JSONObject.parseObject(data.getData(),Map.class);
                    propertyLargeScreenData.setOriginalDataMap(map);
                    break;
                case 11:
                    // 原始业务库
                    List<StandardLabelData> list7 = JSONObject.parseArray(data.getData(),StandardLabelData.class);
                    propertyLargeScreenData.setOriginalBusinessDataList(list7);
                    long tableSum3 = list7.stream().mapToLong(StandardLabelData::getTableCount).sum();
                    propertyLargeScreenData.setOriginalBusinessTableCount(tableSum3);
                    break;
                case 12:
                    int count = Integer.parseInt(data.getData());
                    propertyLargeScreenData.setClassifyTableCount(count);
                    break;
                case 13:
                    List<LargeStorageData> list11 = JSONObject.parseArray(data.getData(),LargeStorageData.class);
                    propertyLargeScreenData.setLargeStorageDataList(list11);
                    break;
                case 14:
                    PyPublishInfo pyPublishInfo = JSONObject.parseObject(data.getData(),PyPublishInfo.class);
                    propertyLargeScreenData.setPyPublishInfo(pyPublishInfo);
                    break;
                case 15:
                    DataMapAssets dataMapAssets = JSONObject.parseObject(data.getData(),DataMapAssets.class);
                    propertyLargeScreenData.setDataMapAssets(dataMapAssets);
                    break;
                default:
                    logger.error("类型"+data.getType()+"没有配置对应的前端渲染类");
                    break;
            }
        }
        return propertyLargeScreenData;
    }

//    // 排序
//    public JSONArray jsonArraySort(JSONArray jsonArray){
//        if (jsonArray.size()>0){
//            List<JSONObject> list = JSONArray.parseArray(jsonArray.toJSONString(), JSONObject.class);
//            Collections.sort(list, new Comparator<JSONObject>() {
//                @Override
//                public int compare(JSONObject o1, JSONObject o2){
//                    int a = Integer.parseInt(o1.getString("value"));
//                    int b = Integer.parseInt(o2.getString("value"));
//                    if (a > b){
//                        return -1;
//                    }else if (a == b){
//                        return 0;
//                    }else {
//                        return 1;
//                    }
//                }
//            });
//            jsonArray = JSONArray.parseArray(list.toString());
//        }
//        return jsonArray;
//    }

    @Override
    public ProvinceCity getProvinceCityName() throws Exception{
        ProvinceCity provinceCity = new ProvinceCity();
        provinceCity.setProvinceName(provinceName);
        //中文转拼音
        provinceCity.setProvinceNamePy(PinYinUtil.getPySpell(provinceName));
        // 根据 provinceName 名称来获取  这个省的 市
        String cityName = propertyLargeScreenDao.getProvinceCity(provinceName);
        provinceCity.setCapitalCity(cityName);
        logger.info("查询到的省市名称为："+JSONObject.toJSONString(provinceCity));
        return provinceCity;
    }

    /**
     * 获取普元信息总线的相关接口
     * 查询表  DGN_PUYUANMESSAGEBUS
     */
    private void  getPuYuanInformationBus(){
        logger.info("======开始查询普元信息总线的相关数据======");
        PyPublishInfo pyPublishInfo = null;
        if(StringUtils.equalsIgnoreCase("tgj", assetsLargeScreenVersion)) {
            pyPublishInfo = getPyPublishInfoTgj();
        } else {
            pyPublishInfo = getPyPublishInfo();
        }
        try{
            insertDataToDb(JSONObject.toJSONString(pyPublishInfo),14);
        }catch (Exception e){
            logger.error("定时任务[查询普元信息总线]插入到数据库报错：", e);
        }

    }
    /**
     * 通管局大屏数据地图资产
     */
    private void getDataMapAssets(){
        DataMapAssets dataMapAssets = new DataMapAssets();
        Integer getDIJobCounts = propertyLargeScreenDao.getDIJobCounts();
        Integer standardCount = propertyLargeScreenDao.getStandardCount();
        Map<String, Object> result = largeScreenServiceImpl.getPrimaryClassifyStatistics();
        BigDecimal dataProcessCount = new BigDecimal(0);
        for (String key : result.keySet()){
            if (key.equalsIgnoreCase("origin") ||
                key.equalsIgnoreCase("resource") ||
                key.equalsIgnoreCase("theme") ||
                key.equalsIgnoreCase("business") ||
                key.equalsIgnoreCase("knowledge") ){
                StatisticsResult statisticsResult = (StatisticsResult) result.get(key);
                dataProcessCount = dataProcessCount.add(statisticsResult.getTypeCount());
            }
        }
        dataMapAssets.setAccessCount(getDIJobCounts.toString());
        dataMapAssets.setStandardCount(standardCount.toString());
        dataMapAssets.setDataProcessCount(dataProcessCount.toString());

        List<PropertyLargeDbData> list = propertyLargeScreenDao.getPropertyLargeScreenDataPage();
        PropertyLargeDbData data = list.stream().filter(item -> item.getType() == 309).findFirst().orElse(null);
        List<DataServiceInfo> dataServices = JSONArray.parseArray(data.getData(), DataServiceInfo.class);
        DataServiceInfo dataService = dataServices.stream().filter(item -> item.getName().equalsIgnoreCase("服务使用")).findFirst().orElse(null);
        dataService.getData();
        for (DataInfo dataInfo : dataService.getData()){
            if (dataInfo.getLabel().equalsIgnoreCase("平台应用")){
                dataMapAssets.setApplicationCount(dataInfo.getValue().toString());
            }
        }
        try{
            insertDataToDb(JSONObject.toJSONString(dataMapAssets),15);
        }catch (Exception e){
            logger.error("定时任务[查询普元信息总线]插入到数据库报错：", e);
        }
    }

    private PyPublishInfo getPyPublishInfoTgj() {
        PyPublishInfo pyPublishInfo = new PyPublishInfo();
        try {
            // 查看共享的资源数量
            // {
            //    "data": {
            //        "appliedDataCount": 申请通过的数据数量(int),
            //        "appliedServiceCount": 申请通过的服务数量(int),
            //        "serviceCallCount": 服务半年内的调用次数(int)
            //    },
            //    "errorCode": "",
            //    "errorMsg": "",
            //    "errorStack": null,
            //    "success": true
            //}
            JSONObject jsonObject = restTemplateHandle.getSysManageInterface(ApiConstant.SERVICEFACSRV_BASEURL + "/servicefacsrv/interface/getSharedResourcesCount");
            if (null == jsonObject) {
                pyPublishInfo.setPublishServiceInfoCountStr("0");
                pyPublishInfo.setRegisterPlatformCountStr("0");
                pyPublishInfo.setRegisterServiceCountStr("0");
            } else {
                pyPublishInfo.setRegisterPlatformCountStr(jsonObject.getString("dataCount"));
                pyPublishInfo.setRegisterServiceCountStr(jsonObject.getString("serviceCount"));
                // 单位：次
                pyPublishInfo.setPublishServiceInfoCountStr(getNumStrConversion(BigInteger.valueOf(jsonObject.getLong("serviceCallCount")), "次"));
            }
        }catch (Exception e) {
            logger.error("定时任务[查询服务总线]查询报错：" , e);
        }
        return pyPublishInfo;
    }

    private PyPublishInfo getPyPublishInfo() {
        PyPublishInfo pyPublishInfo = new PyPublishInfo();
        try {
            pyPublishInfo = propertyLargeScreenDao.getPuYuanInformationBus();
            pyPublishInfo.setPublishServiceInfoCountStr(String.valueOf(pyPublishInfo.getPublishServiceInfoCount()));
            pyPublishInfo.setRegisterPlatformCountStr(String.valueOf(pyPublishInfo.getRegisterPlatformCount()));
            pyPublishInfo.setRegisterServiceCountStr(String.valueOf(pyPublishInfo.getRegisterServiceCount()));
        } catch (Exception e) {
            logger.error("定时任务[查询普元信息总线]查询报错：" , e);
        }
        return pyPublishInfo;
    }

    /**
     * 获取大存储量数据排行榜 从 统计表 table_organization_assets
     * 统计当天的数据 然后获取表的数据总数
     */
    private void getLargeStorageData(){
        try{
            logger.info("---------------获取大存储量数据排行榜定时任务开始运行----------");
            List<LargeStorageData> list = propertyLargeScreenDao.getLargeStorageData(assetsLargeScreenVersion);
            if(CollectionUtils.isEmpty(list)){
                throw new NullPointerException("数据库中表table_organization_assets中不存在今日数据");
            }
            BigDecimal unit = new BigDecimal(1024);
            for(LargeStorageData largeStorageData:list){
                try{
                    if(StringUtils.isBlank(largeStorageData.getTableNameCh())){
                        largeStorageData.setTableNameCh(largeStorageData.getTableNameEn());
                    }
                    String tableCount = StringUtils.isBlank(largeStorageData.getTableCount()) ? "0" : largeStorageData.getTableCount();
                    String str = String.format("%.2f", Long.parseLong(tableCount) *1.0 / 100000000);
                    largeStorageData.setTableCountStr(str);
                    largeStorageData.setTableCount(str);
                    // 存储大小
                    String tableSize = StringUtils.isBlank(largeStorageData.getTableSize()) ? "0" : largeStorageData.getTableSize();
                    String size = String.format("%.2f", new BigDecimal(tableSize).divide(unit).divide(unit).divide(unit).divide(unit));
                    largeStorageData.setTableSizeStr(size);
                    largeStorageData.setTableSize(size);
                }catch (Exception e){
                    largeStorageData.setTableCountStr(largeStorageData.getTableCount());
                    largeStorageData.setTableSizeStr(largeStorageData.getTableSize());
                    logger.error("获取统计信息异常：", e);
                }
            }
            try{
                insertDataToDb(JSONObject.toJSONString(list),13);
            }catch (Exception e){
                logger.error("定时任务[获取大存储量数据排行榜]插入到数据库报错：", e);
            }
        }catch (Exception e){
            logger.error("获取统计信息异常：", e);
        }
    }

    @Override
    public List<PropertyLargeDetailed> getPropertyLargeDetailed(String moduleName,
                                                                String searchName) {
        logger.info("查询的模块："+moduleName+" 二级分类的信息："+searchName);
        return PropertyLargeDetailedEnum.getInstance(moduleName).execute(searchName);
    }

    /**
     * 新疆特有 原始业务库资产情况
     * 根据资源目录表 synlte.public_data_info 表标签5分类,获取8种分类的数据信息表名，并且组织分类时业务生产库的表信息
     * jz_resource 开头 对应的odps存储空间是 ysk_开头(synlte.OBJECT_STORE_INFO)
     * 结合资产统计表(只查询odps的表)，汇总统计对应分类的数据总量信息  不是分区的 是总量
     */
    private void getOriginalBusinessData(){
        List<StandardLabelData> result = new ArrayList<>();
        try{
            logger.info("---------------原始库业务资产状况定时任务开始运行----------");
            // 先获取 标签表 synlte.labels 中标签5对应的的标签信息 用于码表信息  主要用code和name
            List<LabelManageData> list = propertyLargeScreenDao.getAllLabelDataByLevel(5);
            if(list == null || list.isEmpty()){
                throw new NullPointerException("数据库中表synlte.labels中不存在分类5的数据");
            }
            List<String> labelNameList = list.stream().filter(d -> StringUtils.isNotBlank(d.getLabelName()))
                    .map(d -> d.getLabelName().toLowerCase()).collect(Collectors.toList());
            List<StandardLabelData> listData = propertyLargeScreenDao.getOriginalBusinessData();
            if(listData != null && !listData.isEmpty()){
                listData.stream().filter(d -> StringUtils.isNotBlank(d.getName()) &&
                        labelNameList.contains(d.getName().toLowerCase())).sorted(
                        Comparator.comparing(StandardLabelData::getTableDataVolume).reversed()).forEach(
                        d->{
                            String str = getNumStrConversion(d.getTableDataVolume(), "条");
                            d.setTableDataVolumeStr(str);
                            result.add(d);
                        }
                );
                // 如果标签没有，则设置为 0
                for(String label:labelNameList){
                    if(listData.stream().noneMatch(d -> StringUtils.equalsIgnoreCase(d.getName(),label))){
                        StandardLabelData standardLabelData = new StandardLabelData();
                        standardLabelData.setName(label);
                        standardLabelData.setTableCount(0);
                        standardLabelData.setTableDataVolume(BigInteger.valueOf(0));
                        standardLabelData.setTableDataVolumeStr("0条");
                        result.add(standardLabelData);
                    }
                }
            }else{
                for(String label:labelNameList){
                    StandardLabelData standardLabelData = new StandardLabelData();
                    standardLabelData.setName(label);
                    standardLabelData.setTableCount(0);
                    standardLabelData.setTableDataVolume(BigInteger.valueOf(0));
                    standardLabelData.setTableDataVolumeStr("0条");
                    result.add(standardLabelData);
                }
            }
            logger.info("---------------原始库业务资产状况定时任务结束----------");
        }catch (Exception e){
            logger.error("定时任务[原始库业务资产状况]报错：", e);
        }
        try{
            insertDataToDb(JSONObject.toJSONString(result),11);
        }catch (Exception e){
            logger.error("定时任务[原始库业务资产状况]插入到数据库报错：", e);
        }

    }

    /**
     *
     *  标签库资产情况
     *  查询的是标签组的相关接口
     *   1)标签种类：调用标签组提供接口查询标签种类数。
     *   2)标签数据量：调用标签组提供查询标签存储的离线库表，结合资产统计功能统计。
     *   3)标签使用热度：调用标签组提供接口查询top10标签名称和使用次数。
     */
    public void getLabelData(){
        try{
            logger.info("[资产大屏][标签库的需求]未做");
            LabelProperty labelProperty = new LabelProperty();
            String tagPropertyUrl = environment.getProperty("tagServiceUrl","");
            String data = restTemplateHandle.getTagServerInterface(tagPropertyUrl);
            if(StringUtils.isNotBlank(data)){
                List<LabelProperty.UseHeat> list = new ArrayList<>();
                TagProperty tagProperty = JSONObject.parseObject(data,TagProperty.class);
                labelProperty.setLabelTypes(tagProperty.getTagCount());
                labelProperty.setLabelTableNumbersStr(getNumStrConversionStr(String.valueOf(tagProperty.getDataCount())));
                labelProperty.setLabelTableNumbers(tagProperty.getDataCount());
                List<TagProperty.TagUses> tagUsesList = tagProperty.getTagUses();
                if(tagUsesList != null && !tagUsesList.isEmpty()){
                    tagUsesList.forEach( d->{
                        LabelProperty.UseHeat useHeat = new LabelProperty.UseHeat();
                        useHeat.setLabelName(d.getName());
                        useHeat.setLabelTableNumbers(d.getTagUseCount());
                        list.add(useHeat);
                    });
                }
                labelProperty.setLabelUseHeatList(list.stream().sorted(Comparator.comparingLong(
                        LabelProperty.UseHeat::getLabelTableNumbers).reversed()).collect(Collectors.toList()));
            }
            insertDataToDb(JSONObject.toJSONString(labelProperty),4);
        }catch (Exception e){
            logger.error("查询标签组的相关接口异常：", e);
        }

    }

    /**
     *
     *  资源库资产情况和主题库资产情况 的统计信息
     *  查询的是 胡平安汇总统计后的表 orgain_table_stat  同步到本地数据库
     */
    public void getPropertyData(){
        try{
            logger.info("[资产大屏] [获取资源库]的资产情况-------");
            // 获取资源库的资产情况
            List<StandardLabelData> resultList = new ArrayList<>();
            List<StandardLabelData> list = propertyLargeScreenDao.getOrgainTableStatDao("02");
            if(list != null && !list.isEmpty()){
                list.stream().filter(d-> StringUtils.isNotBlank(d.getName())).sorted(
                        Comparator.comparing(StandardLabelData::getTableDataVolume).reversed()).forEach(
                        d->{
                            String str = getNumStrConversion(d.getTableDataVolume(), "条");
                            d.setTableDataVolumeStr(str);
                            resultList.add(d);
                        }
                );

            }else{
                logger.error("从表orgain_table_stat中获取到的资源库资产情况报错,表中数据量为空");
            }
            insertDataToDb(JSONObject.toJSONString(resultList),2);
        }catch (Exception e){
            logger.error("获取资源库资产情况报错", e);
        }

        try{
            logger.info("[资产大屏][获取主题库]的资产情况-------");
            // 获取资源库的资产情况
            List<StandardLabelData> resultList = new ArrayList<>();
            List<StandardLabelData> list = propertyLargeScreenDao.getOrgainTableStatDao("03");
            if(list != null && !list.isEmpty()){
                list.stream().filter(d-> StringUtils.isNotBlank(d.getName())).sorted(
                        Comparator.comparing(StandardLabelData::getTableDataVolume).reversed()).forEach(
                        d->{
                            String str = getNumStrConversion(d.getTableDataVolume(), "条");
                            d.setTableDataVolumeStr(str);
                            resultList.add(d);
                        }
                );
            }else{
                logger.error("从表orgain_table_stat中获取到的主题库资产情况报错,表中数据量为空");
            }
            insertDataToDb(JSONObject.toJSONString(resultList),3);
        }catch (Exception e){
            logger.error("获取主题库资产情况报错", e);
        }
    }


    /**
     *  表名是
     *  获取 运营商数据日接入情况(jz版本) 是 电信、移动、联通的数据日接入情况
     *       电围数据、电查数据、4G分光数据：按照三种运营商统计最新分区(t-1)数据量，通过工作流模型统计出结果同步到oracle数据表中。
     *       统计具体包含数据种类根据现场数据进行调整。
     *   tz 版本 是 数据资产一级分类的 的数据近7天接入情况
     */
    public void getOperatorData(){
        try{
            logger.info("开始查询jz版本的运营商数据");
            getJzOperatorData();
        }catch (Exception e){
            logger.error("获取jz版本的运营商数据报错：", e);
        }

        try{
            logger.info("开始查询kx版本的运营商数据");
            getKxOperatorData();
        }catch (Exception e){
            logger.error("获取kx版本的运营商数据报错：", e);
        }

    }

    /**
     * 生成的结构体是这个样子  查询资产表近7天的数据量
     * "originalDataMap": {"dateList": ["10:00","11:00","12:00","13:00","14:00","15:00","16:00"],"公安执法": [80,60,40,80,70,90,100],
     *             "电信": [80,60,40,80,70,90,100],"互联网": [80,60,40,80,70,90,100],"行业": [80,60,40,80,70,90,100],
     *             "物联网": [80,60,40,80,70,90,100]
     *         }
     */
    private void getKxOperatorData(){
        // 如果查询报错，则也要获取相关的数据，只是数据量为0
        // 1: 先插入 横坐标的近7天
        Date nowDate = new Date();
        List<String> dateList = new ArrayList<>();
        // 广西大屏查询从昨天开始的近7天
        int start = StringUtils.equalsIgnoreCase("gxzd", assetsLargeScreenVersion) ? -7: -6;
        int end = StringUtils.equalsIgnoreCase("gxzd", assetsLargeScreenVersion) ? -1 : 0;
        for(int i = start ;i <= end; i++){
            String dateStr = DateUtil.formatDate(DateUtil.addDay(nowDate, i), DateUtil.DEFAULT_PATTERN_DAY);
            dateList.add(dateStr);
        }
        Map<String,List<Double>> map = new HashMap<>(6);
        // 2:获取所有的标签值 包含id和 中文名
        List<String> dataLabels = propertyLargeScreenDao.getAllClassifyCh();
        if(dataLabels == null || dataLabels.isEmpty()){
            dataLabels = Constant.LABEL_LIST;
        }
        for(String label:dataLabels){
            map.put(label,new ArrayList<Double>());
        }
        try{
            // 3:循环时间，查询这7天的数据量情况
            for(int i = start ;i <= end; i++){
                String dateStr = DateUtil.formatDate(DateUtil.addDay(nowDate, i), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                List<StandardLabelData> dataList = propertyLargeScreenDao.getOperatorDataByDate(dateStr);
                for(String label:dataLabels){
                    //这边直接除以亿
                    long records = getLabelRecords(label,dataList);
                    map.get(label).add(Double.parseDouble(df.format((records*1.0)/100000000)));
                }
            }
        }catch (Exception e){
            logger.error("获取近7天的数据量报错", e);
        }
        Map<String,List> mapResult = new HashMap<>(2);
        mapResult.put("dateList",dateList);
        mapResult.putAll(map);
        //将数据写入到数据库中
        try{
            insertDataToDb(JSONObject.toJSONString(mapResult),10);
        }catch (Exception e){
            logger.error("近7天数据插入到数据库报错：", e);
        }

    }

    /**
     * 名称相似或者相同都可
     * @param label
     * @param dataList
     * @return
     */
    private long getLabelRecords(String label, List<StandardLabelData> dataList ){
        for(StandardLabelData data: dataList){
            if(StringUtils.equalsIgnoreCase(data.getName(),label)
                    || data.getName().contains(label)){
                return data.getTableCount();
            }
        }
        return 0;
    }

    /**
     * 运营商数据的函数
     */
    private void getJzOperatorData(){
        // 这个是 jz 版本  PROVIDER_TABLE_STAT
        logger.info("开始查询jz版本的运营商数据日接入情况");
        List<OperatorData> operatorDataList = new ArrayList<>();
        List<ProviderTableStat> list = propertyLargeScreenDao.getProviderTableStatDao();
        // 因为要按照  电信  移动  联通 这个顺序 插入数据 所以
        if(list == null || list.isEmpty()){
            logger.error("表PROVIDER_TABLE_STAT里面今日的数据为空，没有运营商数据");
            operatorDataList.add(new OperatorData(Constant.DIAN_XIN, BigInteger.valueOf(0),BigInteger.valueOf(0),BigInteger.valueOf(0)));
            operatorDataList.add(new OperatorData(Constant.YI_DONG,BigInteger.valueOf(0),BigInteger.valueOf(0),BigInteger.valueOf(0)));
            operatorDataList.add(new OperatorData(Constant.LIAN_TONG,BigInteger.valueOf(0),BigInteger.valueOf(0),BigInteger.valueOf(0)));
        }else{
            // 插入 电信类型数据
            Map<String,List<ProviderTableStat>>  map = list.stream().filter( d-> StringUtils.isNotBlank(d.getOperatorNet())
                    && StringUtils.isNotBlank(d.getDataType())).collect(Collectors.groupingBy(ProviderTableStat::getOperatorNet));
            // 电信的数据
            OperatorData data1 = getOperatorDataByType(map, Constant.DIAN_XIN);
            operatorDataList.add(data1);

            // 移动
            OperatorData data2 = getOperatorDataByType(map, Constant.YI_DONG);
            operatorDataList.add(data2);

            // 联通
            OperatorData data3 = getOperatorDataByType(map, Constant.LIAN_TONG);
            operatorDataList.add(data3);
        }
        logger.info("运营商数据日接入情况汇总结束");
        //将数据写入到数据库中
        try{
            insertDataToDb(JSONObject.toJSONString(operatorDataList),5);
        }catch (Exception e){
            logger.error("运营商数据日接入情况报错：", e);
        }
    }

    /**
     * 获取 页面上运营商数据的相关格式，
     * 当数据库中不存在时其中的数据量为空
     * @param map
     * @param type
     * @return
     */
    private OperatorData getOperatorDataByType(Map<String,List<ProviderTableStat>>  map,String type){
        OperatorData operatorData = null;
        List<ProviderTableStat> list = map.getOrDefault(type,null);
        if(list == null || list.isEmpty()){
            operatorData = new OperatorData(type,BigInteger.valueOf(0),BigInteger.valueOf(0),BigInteger.valueOf(0));
            return operatorData;
        }
        // 电围 ，电查  分光
        operatorData = new OperatorData();
        operatorData.setName(type);
        // 电围 数据
        Optional<ProviderTableStat> data1 = list.stream().filter( d -> StringUtils.equalsIgnoreCase(d.getDataType(), Constant.DIAN_WEI)).findFirst();
        operatorData.setMemNumbers(new BigInteger(data1.map(ProviderTableStat::getRecordsAll).orElse("0")));

        // 电查 数据
        Optional<ProviderTableStat> data2 = list.stream().filter( d -> StringUtils.equalsIgnoreCase(d.getDataType(), Constant.DIAN_CHA)).findFirst();
        operatorData.setMedNumbers(new BigInteger(data2.map(ProviderTableStat::getRecordsAll).orElse("0")));

        // 4G分光 数据
        Optional<ProviderTableStat> data3 = list.stream().filter( d -> StringUtils.equalsIgnoreCase(d.getDataType(), Constant.FEN_GUANG)).findFirst();
        operatorData.setSmsNumbers(new BigInteger(data3.map(ProviderTableStat::getRecordsAll).orElse("0")));

        return operatorData;
    }


    /**
     * 下发的数据量
     * 新疆的是查询的 工作流同步的表  DGN_DISTRIBUTION_STATISTIC
     * 其它省是使用 jz版本  查询的是标准化组的统计结果
     * 需要查询  bscdata用户下的province和city两个表 来获取 到省会城市的名称
     * 目前只有下发的数据量
     * // 20210721 新疆查询  DGN_NOSTD_DISTRIBUTE_STATISTIC
     * @TODO 上传的数据量是什么
     */
    public void getDataShareMapList(){
        // 先查询出配置参数 是哪个省的数据  新疆是 tz  其它省市是 jz
        List<DataShareMap> listData = new ArrayList<>();
        String todayStr = DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        try{
            if(StringUtils.equalsIgnoreCase(Constant.XINJIANG,provinceName)){
                // 查询表中 DGN_DISTRIBUTION_STATISTIC 的今天的数据
                // 20210721 新疆查询  DGN_NOSTD_DISTRIBUTE_STATISTIC
                List<DataDistributionStatistic> list = propertyLargeScreenDao.getDataDistributionList(todayStr);
                if(list == null || list.isEmpty()){
                    throw new NullPointerException("下发数据量统计表DGN_NOSTD_DISTRIBUTE_STATISTIC获取到的数据量为空");
                }
                // dz 里面是一个 码值 需要翻译 存储在数据库表 DGN_CITY_ABB 中 方便以后新增
                List<CityAbbPojo> cityList = propertyLargeScreenDao.getCityAbbList();
                if(cityList == null || cityList.isEmpty()){
                    throw new NullPointerException("表DGN_CITY_ABB的数据量为空，请确定码表值是否已经插入");
                }
                Map<String,List<CityAbbPojo>> dataMap = cityList.stream().
                        collect(Collectors.groupingBy(d->d.getCityAbb().toLowerCase()));
                for(DataDistributionStatistic data:list){
                    DataShareMap dataShareMap = new DataShareMap();
                    dataShareMap.setSendNumbers(data.getTotalcount());
                    // getNumStrConversion
                    dataShareMap.setSendNumbersStr(getNumStrConversionStr(data.getTotalcount()));
                    dataShareMap.setSendTypes(data.getTotalTypes());
                    dataShareMap.setReportNumbers("0");
                    dataShareMap.setReportNumbersStr("0条");
                    dataShareMap.setReportTypes("0");
                    List<CityAbbPojo> listAbb =dataMap.getOrDefault(data.getDz().toLowerCase(),null);
                    if(listAbb == null || listAbb.isEmpty()){
                        logger.error(data.getDz()+"没有在表DGN_CITY_ABB中配置码表，请查看相关表数据");
                        dataShareMap.setCityCode("");
                    }else{
                        dataShareMap.setCityCode(listAbb.get(0).getCityCode());
                    }
                    dataShareMap.setName(data.getDz());
                    listData.add(dataShareMap);
                }
            }else{
                //  查询数据分发统计表  STANDARDIZE. STATINFODIS 其中 city_code可能是乱写的
                //  如何与地图数据进行匹配或者  dz的值目前暂定为 城市的代码
                List<DataDistributionStatistic> list = propertyLargeScreenDao.getStatInfoDisList(todayStr);
                if(list == null || list.isEmpty()){
                    throw new NullPointerException("表STANDARDIZE. STATINFODIS今日"+todayStr+"没有统计数据");
                }
                for(DataDistributionStatistic data:list){
                    DataShareMap dataShareMap = new DataShareMap();
                    dataShareMap.setSendNumbers(data.getTotalcount());
                    dataShareMap.setSendNumbersStr(getNumStrConversionStr(data.getTotalcount()));
                    dataShareMap.setSendTypes(data.getTotalTypes());
                    dataShareMap.setReportNumbers("0");
                    dataShareMap.setReportTypes("0");
                    dataShareMap.setReportNumbersStr("0条");
                    dataShareMap.setCityCode(data.getDz());
                    dataShareMap.setName(data.getDz());
                    listData.add(dataShareMap);
                }
            }
        }catch (Exception e){
            logger.error("保存地图数据信息报错：", e);
        }

        try{
            insertDataToDb(JSONObject.toJSONString(listData),9);
        }catch (Exception e){
            logger.error("保存地图数据信息报错：", e);
        }

    }




    /**
     *   资产使用情况 主要是数据使用热度 top5  和应用使用热度 top5
     */
    public void getPropertyUsage(){

        PropertyUsage propertyUsage = new PropertyUsage();
        try{
            logger.info("[资产大屏][资产使用情况]定时任务开始运行");
            handleUseHeatListTgj(propertyUsage);
//            // 数据使用热度TOP5
//            List<UseHeatProperty> list = propertyLargeScreenDao.getUseHeatList();
//            propertyUsage.setTableUseHeatList(list);
//            if(StringUtils.equalsIgnoreCase("tgj", assetsLargeScreenVersion)) {
//                handleApplicationUseHeatListTgj(propertyUsage);
//            } else {
//                handleApplicationUseHeatList(propertyUsage);
//            }
            insertDataToDb(JSONObject.toJSONString(propertyUsage),7);
        }catch (Exception e){
            logger.error("[资产大屏][资产使用情况]报错", e);
        }

    }

    private void handleApplicationUseHeatList(PropertyUsage propertyUsage) {
        try {
            // 应用使用热度TOP5  使用的是 dblink 连接到数据治理的数据库 然后再创建同义词，
            //  该同义词的名称为 V_SYSOPER_LOG_INCR  查询的时间是今天的
            List<UseHeatProperty> list = propertyLargeScreenDao.getApplicationUseHeat();
            propertyUsage.setApplicationUseHeatList(list);
        } catch (Exception e) {
            throw e;
        }
    }

    private void handleApplicationUseHeatListTgj(PropertyUsage propertyUsage) {
        try {
            // 应用使用热度TOP5  使用的是资源服务平台接口
            List<UseHeatProperty> list = new ArrayList<>();
            String registerInterfaceUrl = environment.getRequiredProperty("nginxIp");
            List<JSONObject> resultList = restTemplateHandle.querySysManageInterface(registerInterfaceUrl + "/serverfacsrv-sysManage/interface/queryHotOfServiceUsing");
            if (!CollectionUtils.isEmpty(resultList)) {
                for (JSONObject jo : resultList) {
                    UseHeatProperty uhp = new UseHeatProperty();
                    uhp.setShowName(jo.getString("name"));
                    uhp.setUseHeatCount(jo.getLong("count"));
                    list.add(uhp);
                }
            }
            propertyUsage.setApplicationUseHeatList(list);
        } catch (Exception e) {
            throw e;
        }
    }

    private void handleUseHeatListTgj(PropertyUsage propertyUsage) {
        // 数据使用热度TOP5  使用的是资源服务平台接口
        List<UseHeatProperty> dataUsageList = new ArrayList<>();
        String dataUsageTopUrl = ApiConstant.SERVICEFACSRV_BASEURL + "/servicefacsrv/interface/queryDataUsageTop";
        String dataUsageTop = restTemplateHandle.getServicefacsrvInterface(dataUsageTopUrl);
        if (StringUtils.isNotBlank(dataUsageTop)) {
            List<DataUsageTopInfo> dataUsageTopInfos = JSONArray.parseArray(dataUsageTop, DataUsageTopInfo.class);
            if (CollectionUtils.isNotEmpty(dataUsageTopInfos)) {
                for (DataUsageTopInfo dataUsageTopInfo : dataUsageTopInfos) {
                    UseHeatProperty uhp = new UseHeatProperty();
                    uhp.setShowName(dataUsageTopInfo.getDataResourceName());
                    uhp.setTableNameEn(dataUsageTopInfo.getDataResourceId());
                    uhp.setUseHeatCount(dataUsageTopInfo.getCount());
                    dataUsageList.add(uhp);
                }
            }
        }
        propertyUsage.setTableUseHeatList(dataUsageList);
        // 应用使用热度TOP5  使用的是资源服务平台接口
        String appUsageTopUrl = ApiConstant.SERVICEFACSRV_BASEURL + "/servicefacsrv/interface/queryAppUsageTop";
        String appUsageTop = restTemplateHandle.getServicefacsrvInterface(appUsageTopUrl);
        List<UseHeatProperty> appUsageList = new ArrayList<>();
        if (StringUtils.isNotBlank(appUsageTop)) {
            List<AppUsageTopInfo> appUsageTopInfos = JSONArray.parseArray(appUsageTop, AppUsageTopInfo.class);
            if (CollectionUtils.isNotEmpty(appUsageTopInfos)) {
                for (AppUsageTopInfo appUsageTopInfo : appUsageTopInfos) {
                    UseHeatProperty uhp = new UseHeatProperty();
                    uhp.setShowName(appUsageTopInfo.getYyxtmc());
                    uhp.setTableNameEn(appUsageTopInfo.getAppId());
                    uhp.setUseHeatCount(appUsageTopInfo.getCount());
                    appUsageList.add(uhp);
                }
            }
        }
        propertyUsage.setApplicationUseHeatList(appUsageList);
    }

    /**
     *  获取对外分享的数据信息
     *  调用资源服平台的接口
     *  需要共享数据种类   共享数据量   数据服务接口数  接口被调用次数
     */
    public void getPublishInfo(){
        logger.info("[资产大屏][对外分享数据]开始获取对外分享数据");
        PublishInfo publishInfo = new PublishInfo();
        try{
            //  资源服务平台 放在同一个 nginx里面
            String registerInterfaceUrl = ApiConstant.SERVICEFACSRV_BASEURL;
            if(StringUtils.equalsIgnoreCase("tgj", assetsLargeScreenVersion)) {
                handlePublicInfoTgj(publishInfo, registerInterfaceUrl);
            } else {
                handlePublicInfo(publishInfo, registerInterfaceUrl);
            }
        }catch (Exception e){
            logger.error("[资产大屏][对外分享数据]获取对外分享数据报错:", e);
        }

        try{
            insertDataToDb(JSONObject.toJSONString(publishInfo),8);
        }catch (Exception e){
            logger.error("[资产大屏][获取对外分享数据]数据保存到数据库中失败", e);
        }
    }

    private void handlePublicInfo(PublishInfo publishInfo, String registerInterfaceUrl) {
        try {
            // 1: 获取已发布数据推送量接口
            // [{"ID":"JZ_RESOURCE_98334","TABLENAME":"NB_TAB_XZQGLSJDHYXX","TABLECNNAME":"中文名","RECORDCOUNT":546}]
            List<JSONObject> tableArray = restTemplateHandle.getClassifyServerInterfance(registerInterfaceUrl + "/classifyserver/interface/publishDataInfo");

            if (tableArray == null) {
                publishInfo.setPublishDataInfoCount(0);
                publishInfo.setPublishDataTypes(0);
                publishInfo.setPublishDataInfoCountStr("0条");
            } else {
                long publishDataInfoCount = tableArray.stream().filter(d -> !StringUtils.isBlank(d.getString("RECORDCOUNT")))
                        .mapToLong(d -> d.getLong("RECORDCOUNT")).sum();
                publishInfo.setPublishDataInfoCountStr(getNumStrConversion(BigInteger.valueOf(publishDataInfoCount), "条"));
                publishInfo.setPublishDataInfoCount(publishDataInfoCount);
                publishInfo.setPublishDataTypes(tableArray.size());
            }

            // 2: 已发布服务调用次数接口
            //  [{"SERVICENAME":"similar_people_analysis","SERVICECNNAME":"类人分析","CALLCOUNT":1000}]
            List<JSONObject> serviceArray = restTemplateHandle.getClassifyServerInterfance(registerInterfaceUrl + "/classifyserver/interface/publishServiceInfo");
            if (serviceArray == null) {
                publishInfo.setPublishServiceTypes(0);
                publishInfo.setPublishServiceInfoCount(0);
                publishInfo.setPublishServiceInfoCountStr("0");
            } else {
                long publishServiceInfoCount = serviceArray.stream().filter(d -> !StringUtils.isBlank(d.getString("CALLCOUNT")))
                        .mapToLong(d -> d.getLong("CALLCOUNT")).sum();
                // 单位：次
                publishInfo.setPublishServiceInfoCountStr(getNumStrConversion(BigInteger.valueOf(publishServiceInfoCount), "次"));
                publishInfo.setPublishServiceInfoCount(publishServiceInfoCount);
                publishInfo.setPublishServiceTypes(serviceArray.size());

            }
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     *  获取服务工厂信息-tgj版本
     */
    private void handlePublicInfoTgj(PublishInfo publishInfo, String registerInterfaceUrl) {
        try {
            // 查询资源数量(数据，服务，应用)
            // {
            //    "data": {
            //        "appCount": 应用资源数量(int)
            //        "dataCount": 数据资源数量(int)
            //        "serviceCount": 服务资源数量(int)
            //    },
            //    "errorCode": "",
            //    "errorMsg": "",
            //    "errorStack": null,
            //    "success": true
            //}
            JSONObject jsonObject = restTemplateHandle.getSysManageInterface(registerInterfaceUrl + "/servicefacsrv/interface/getResourcesCount");
            if (null == jsonObject) {
                publishInfo.setPublishDataTypes(0);
                publishInfo.setPublishServiceTypes(0);
                publishInfo.setPublishServiceInfoCountStr("0");
            } else {
                publishInfo.setPublishDataTypes(jsonObject.getLong("dataCount"));
                publishInfo.setPublishServiceTypes(jsonObject.getLong("serviceCount"));
                publishInfo.setPublishServiceInfoCount(jsonObject.getLong("appCount"));
                publishInfo.setPublishServiceInfoCountStr(String.valueOf(publishInfo.getPublishServiceInfoCount()));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *  字段分类的定时任务
     */
    public void getFiledClassData(){
        logger.info("[资产大屏] [定时获取字段分类的相关数据]任务开始运行=================");
        List<FiledClassData> list = new ArrayList<>();
        try {
            list = propertyLargeScreenDao.getFiledClassDataList();
            if (list == null || list.isEmpty()) {
                // 如果是 null  说明数据库中没有数据，只查询出所有分类信息，表的数据量为0，
                // 如果分类信息的码表都没有，则输出报错信息
                list = propertyLargeScreenDao.getFiledCodeClassDataList();
                // 排序　
                if (list == null || list.isEmpty()) {
                    logger.error("[资产大屏] [定时获取字段分类的相关数据]没有在表synlte.fieldcodeval中配置分类信息的码表");
                    return;
                }
            } else {
                int tableCount = propertyLargeScreenDao.getAllCountFiledClassDataList();
                try {
                    insertDataToDb(String.valueOf(tableCount), 12);
                } catch (Exception e) {
                    logger.error("[资产大屏][定时获取字段分类的相关数据]数据保存到数据库中失败" , e);
                }
            }
            list = list.stream().filter(d -> StringUtils.isNotBlank(d.getFieldClassCh())).sorted((s1, s2)
                    -> Collator.getInstance(Locale.CHINA).
                    compare(s1.getFieldClassCh(), s2.getFieldClassCh())).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("[资产大屏][定时获取字段分类的相关数据]失败" , e);
        }
        try{
            insertDataToDb(JSONObject.toJSONString(list),6);
        }catch (Exception e){
            logger.error("[资产大屏][定时获取字段分类的相关数据]数据保存到数据库中失败", e);
        }

    }



    /**
     * 原始库的数据总量，不要日接入量 表的总量
     *
     */
    public void getOriginalDataList(){
        logger.info("[资产大屏] [定时获取原始库资产情况]任务开始运行=================");
        List<StandardLabelData> result = new ArrayList<>();
        try {
            // 直接从数据库中获取 表为  table_organization_assets
            // 这个是数据库中表的数据
            List<StandardLabelData> data = propertyLargeScreenDao.getAllOriginalDataList();

            // 获取 到 所有原始库的 分类的信息 去除掉
            List<String> dataLabels = propertyLargeScreenDao.getAllClassifyCh();
            if (data == null || data.isEmpty()) {
                // 从数据库中获取到的数据为空，所有的标签的数据量为空
                for (String label : dataLabels) {
                    StandardLabelData standardLabelData = new StandardLabelData();
                    standardLabelData.setName(label);
                    standardLabelData.setTableCount(0);
                    standardLabelData.setTableDataVolume(BigInteger.valueOf(0));
                    standardLabelData.setTableDataVolumeStr("0条");
                    result.add(standardLabelData);
                }
            } else {
                data.stream().filter(d -> dataLabels.contains(d.getName())).sorted(
                        Comparator.comparing(StandardLabelData::getTableDataVolume).reversed()).forEach(
                        d -> {
                            String str = getNumStrConversion(d.getTableDataVolume(), "条");
                            d.setTableDataVolumeStr(str);
                            result.add(d);
                        }
                );
                // 如果标签没有，则设置为 0
                for (String label : dataLabels) {
                    if (data.stream().noneMatch(d -> StringUtils.equalsIgnoreCase(d.getName(), label))) {
                        StandardLabelData standardLabelData = new StandardLabelData();
                        standardLabelData.setName(label);
                        standardLabelData.setTableCount(0);
                        standardLabelData.setTableDataVolume(BigInteger.valueOf(0));
                        standardLabelData.setTableDataVolumeStr("0条");
                        result.add(standardLabelData);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("[资产大屏][定时获取原始库日接入情况]失败", e);
        }
        logger.info("数据查询结束,开始将数据写入到数据库中");
        // 将数据插入到数据库中
        try{
            insertDataToDb(JSONObject.toJSONString(result),1);
        }catch (Exception e){
            logger.error("[资产大屏][定时获取原始库日接入情况]数据保存到数据库中失败", e);
        }
    }


    /**
     * 定时任务
     * 获取 总资产状况，  日增量：（t-1）数据总量- （t-2）数据总量
     * 数据总量：离线库记录数+在线库记录数+非结构化记录数
     * 已用存储总量：离线库已用物理存储+在线库已用物理存储+非结构化已用物理存储
     * 总存储：离线库总物理存储+在线库总物理存储+非结构化总物理存储。
     *
     *  这个是条数，页面上是 亿条 小数点只保留2位
     * 2)平台分类
     * 离线库：ODPS\Hive
     * 在线库：ads\hbase\ck
     * 非结构化：oss（是否展现非结构化信息，可配置）
     */
    public void totalDataPropertyScheduled(){
        logger.info("[资产大屏] [定时获取数据总资产]任务开始运行=================");
        TotalDataProperty totalDataProperty = new TotalDataProperty();
        try {
            // 1: 获取日增量、数据总量，直接查资产表table_organization_assets
            totalDataProperty = propertyLargeScreenDao.getYesterdayCount();
            totalDataProperty.setDailyIncrement(String.format("%.2f", Long.parseLong(totalDataProperty.getDailyIncrement()) * 1.0 / 100000000));
            double dataTotalVolume = Double.parseDouble(df.format(totalDataProperty.getDataTotalVolume() / 100000000));
            totalDataProperty.setDataTotalVolume(dataTotalVolume);
            // 数据量的百分比
            totalDataProperty.setRatioOfData(dataTotalVolume == 0d ? "0%" : Double.parseDouble(df.format(
                    Double.parseDouble(totalDataProperty.getDailyIncrement()) * 100 / dataTotalVolume)) + "%");

            // 2: 数据总量  查询property的接口  /dataStorageMonitoring/getDataBaseStatus  参数没有用
            List<DataBaseState> list = restTemplateHandle.getDataBaseStatus();
            if (list != null && !list.isEmpty()) {
                // 已使用的存储 GB
                double totalStorageUsed = list.stream().collect(Collectors.summarizingDouble(d
                        -> Double.parseDouble(StringUtils.isBlank(d.getUsedCapacity()) ? "0" : d.getUsedCapacity()))).getSum();
                totalDataProperty.setTotalStorageUsed(Double.parseDouble(df.format(totalStorageUsed)));
                // 总存储量 GB
                double totalStorage = list.stream().collect(Collectors.summarizingDouble(d
                        -> Double.parseDouble(StringUtils.isBlank(d.getBareCapacity()) ? "0" : d.getBareCapacity()))).getSum();
                totalDataProperty.setTotalStorage(Double.parseDouble(df.format(totalStorage)));
                // 存储的百分比
                totalDataProperty.setRatioOfStorage(totalStorage == 0d ? "0%" : Double.parseDouble(df.format(totalStorageUsed * 100 / totalStorage)) + "%");

                // 是否展现非结构化信息
                List<DataBaseState> data = list.stream().filter(d -> StringUtils.isNotBlank(d.getName()) &&
                        Constant.UNSTRUCTURED.contains(d.getName().toUpperCase())).collect(Collectors.toList());
                // 然后再获取 水位图 的相关信息
                totalDataProperty.setUnstructuredShow(!data.isEmpty());
                // 非结构化数据
                TotalDataProperty.DataBaseState data1 = getLibraryData(data, 0);
                totalDataProperty.setUnstructuredLibrary(data1);

                // 实时库的 水位图信息
                List<DataBaseState> list2 = list.stream().filter(d -> StringUtils.isNotBlank(d.getName()) &&
                        Constant.REALTIME_BS.contains(d.getName().toUpperCase())).collect(Collectors.toList());
                TotalDataProperty.DataBaseState data2 = getLibraryData(list2, 0);
//                // tgj定制
//                if(StringUtils.equalsIgnoreCase("tgj", assetsLargeScreenVersion)) {
//                    data2.setName("在线库");
//                }
                totalDataProperty.setRealTimeLibrary(data2);

                // 离线库 数据
                List<DataBaseState> list3 = list.stream().filter(d -> StringUtils.isNotBlank(d.getName()) &&
                        Constant.OFFLINE_BS.contains(d.getName().toUpperCase())).collect(Collectors.toList());
                TotalDataProperty.DataBaseState data3 = getLibraryData(list3, 0);
//                // tgj定制
//                if(StringUtils.equalsIgnoreCase("tgj", assetsLargeScreenVersion)) {
//                    data3.setName("离线库");
//                }
                totalDataProperty.setOfflineLibrary(data3);
            } else {
                totalDataProperty.setUnstructuredShow(false);
                logger.error("[资产大屏]从property中获取到的数据量为空，无法展示数据总资产信息");
            }
        }catch (Exception e){
            logger.error("定时任务[获取数据总资产]报错：", e);
        }
        // 将数据插入到数据库中
        try{
            insertDataToDb(JSONObject.toJSONString(totalDataProperty),0);
        }catch (Exception e){
            logger.error("[资产大屏]数据保存到数据库中失败", e);
        }

    }


    /**
     * 将定时任务获取到的数据写入到数据库中
     * @param data   具体的json数据
     * @param type   0:数据总资产(包含水位图)
     *               1:原始库日接入情况
     *               2:资源库资产情况
     *               3:主题库资产情况
     *               4:标签库资产情况
     *               5:数据日接入情况(分为jz版本和kx版本)
     *               6:字段分类的数据
     *               7:资产使用情况
     *               8:对外共享
     *               9:地图数据
     *               10:数据日接入 kx版本
     *               11: 原始业务库
     *               12: 字段分类表的总数
     *               13: 大存储量数据排行
     *
     */
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

    /**
     *
     * @param data
     * @param type  1: 表示 非结构化数据   tablecount是文件数量  整数
     * @return
     */
    private TotalDataProperty.DataBaseState getLibraryData(List<DataBaseState> data,int type){
        TotalDataProperty.DataBaseState library = null;
        if(!data.isEmpty()){
            // 使用的存储大小 GB
            double usedCapacity = data.parallelStream().collect(Collectors.summarizingDouble(d
                    -> Double.parseDouble(StringUtils.isBlank(d.getUsedCapacity())?"0":d.getUsedCapacity()))).getSum();
            // 总存储大小 GB
            double bareCapacity= data.parallelStream().collect(Collectors.summarizingDouble(d
                    -> Double.parseDouble(StringUtils.isBlank(d.getBareCapacity())?"0":d.getBareCapacity()))).getSum();
            // 使用率
//            double usageRate= usedCapacity/bareCapacity;
            double usageRate = 0;
            if(bareCapacity != 0) {
                usageRate=  Double.parseDouble(df.format(usedCapacity/bareCapacity));
            }
            // 数据量  单位是 亿条
            double tableCount = data.parallelStream().collect(Collectors.summarizingDouble(d
                    -> Double.parseDouble(StringUtils.isBlank(d.getTableCount())?"0":d.getTableCount()))).getSum();
            library = new TotalDataProperty.DataBaseState();
            library.setUsedCapacity(String.format("%.2f",usedCapacity));
            library.setBareCapacity(String.format("%.2f",bareCapacity));
            library.setUsageRate(String.format("%.2f",usageRate));
            if(type == 1){
                library.setTableCount(String.format("%.0f",tableCount));
            }else{
                library.setTableCount(String.format("%.2f",tableCount));
            }
            library.setName(data.get(0).getName());
//            //  调用数据仓库那边 获取 本地仓的相关信息 然后再连接获取表的数量
//            try{
//                DataBaseHandle dataBaseHandle = DbFactory.getHandler(library.getName());
//                String dataOther = detailedLargeScreenServiceImpl.getLargeTableSum(library.getName());
//                logger.info("本地仓的连接信息为："+dataOther);
//                library.setTableSum(String.valueOf(dataBaseHandle.getTableAllSum(dataOther)));
//            }catch (Exception e){
//                logger.error(ExceptionUtil.getExceptionTrace(e));
//            }
            // 资产接口获取表数量
            long tableSum = data.parallelStream().collect(Collectors.summarizingLong(d -> Long.parseLong(StringUtils.isBlank(d.getTableSum()) ? "0" : d.getTableSum()))).getSum();
            library.setTableSum(String.valueOf(tableSum));
        }else{
            library = new TotalDataProperty.DataBaseState();
        }
        return library;
    }


    /**
     * 如果大于 10亿  则用亿表示
     *  如果大于 100万  则 用万表示
     *  保留 3个小数点
     * @param num
     * @return
     */
    private String getNumUnitConversionStr(long num){
        String str ="0";
        if(num >= Long.parseLong("1000000000")){
            str = String.format("%.2f", num *1.0 / 1000000000)+" 亿";
        }else if(num >= Long.parseLong("1000000")){
            str = String.format("%.2f", num *1.0 / 1000000)+" 万";
        }else{
            str = String.valueOf(num);
        }
        return str;
    }


    /**
     * 如果数据有千万级别，则使用 亿条/次 的单位 ，
     * 如果不能达到千万级别并且已经达到了万，则单位使用万条/次
     * 如果是万以下，则使用条/次
     * @param num
     * @param unit
     * @return
     */
    private String getNumStrConversion(BigInteger num, String unit){
        StringBuilder str = new StringBuilder();
        // 可以使用  亿条的单位
        if(num.subtract(TEN_MILLION).compareTo(BigInteger.ZERO)>=0){
            BigInteger[] d = num.divideAndRemainder(BILLION);
            String dStr = TEN_MILLION_FORMAT.format(d[1]);
            return str.append(d[0]).append(".").append((dStr), 0, 2).append("亿").append(unit).toString();
        }else if(num.subtract(TEN_THOUSAND).compareTo(BigInteger.ZERO)>=0){
            BigInteger[] d = num.divideAndRemainder(TEN_THOUSAND);
            String dStr = TEN_THOUSAND_FORMAT.format(d[1]);
            return str.append(d[0]).append(".").append((dStr), 0, 2).append("万").append(unit).toString();
        }else{
            return str.append(num).append(unit).toString();
        }
    }


    private String getNumStrConversionStr(String str){
        try{
            BigInteger data = BigInteger.valueOf(Long.parseLong(str));
            return getNumStrConversion(data, "条");
        }catch (Exception e){
            logger.error("number转string失败：", e);
            return "0条";
        }
    }

}
