package com.synway.property.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.PlatformType;
import com.synway.property.common.UrlConstants;
import com.synway.property.config.TransactionUtil;
import com.synway.property.dao.*;
import com.synway.property.enums.SysCodeEnum;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.LoginUser;
import com.synway.property.pojo.OperationLog;
import com.synway.property.pojo.dataResource.ResourceOverView;
import com.synway.property.pojo.datastoragemonitor.*;
import com.synway.property.pojo.formorganizationindex.ClassifyInfo;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;
import com.synway.property.pojo.formorganizationindex.PublicDataInfo;
import com.synway.property.pojo.formorganizationindex.SYDMGParam;
import com.synway.property.pojo.alarmsetting.OrganizationAlarmSetting;
import com.synway.property.pojo.homepage.DataBaseState;
import com.synway.property.service.DataStorageMonitorService;
import com.synway.property.util.*;
import groovy.transform.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 数据接入
 */
@Service
public class DataStorageMonitorServiceImpl implements DataStorageMonitorService {
    private static Logger logger = LoggerFactory.getLogger(DataStorageMonitorServiceImpl.class);
    @Autowired
    private DataMonitorDao dataMonitorDao;
    @Autowired
    private DataStorageMonitorDao dataStorageMonitorDao;
    @Autowired
    private OperatorLogDao operatorLogDao;
    @Autowired
    private RestTemplate rest;

    @Autowired
    private RestTemplate restTemplateApi;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private Environment environment;

    @Autowired
    private CacheManager<String> cacheManager;
    @Autowired
    private TransactionUtil transactionUtil;

    @Autowired
    private OrganizationDetailDao organizationDetailDao;


    @Autowired
    @Qualifier(value = "dataSourceForStatisHbase")
    private DruidDataSource dataSourceForStatisHbase;

    /**
     * 调用ckw下的odps/ads接口
     * 统计需要监控的表每天数据量
     * 1：先获取DATA_STORAGE_ADD_TABLE表中需要监控的表信息，然后拼接对应的查询sql来调用ckw接口获取这些表今日分区的数据量
     * 查询odps的数据量时使用 odps的接口 odpsClient 中的函数 getRecordCountByTable 直接获取数据量
     * 查询ads的数据量时使用ads的sql代码
     * 2：调用陈亮表 syndmg_table_all，获取该表的数据总量
     * 3：拼接出对应的实体类，将获取到的数据插入到数据库中
     * 4：在插入数据库时，可能存在一天查询多次的情况，所以每次插入时先删除相同的今日数据量
     * <p>
     * 20190823修改
     * <p>
     * 1.增加多数据源，需实时去数据仓库查询密码，进行连接
     * 2.分区字段改变，修改sql语句
     * 20200318修改 TODO 有空可以抽一下方法
     */
    @Override
    @Synchronized
    public void statisticsTableTodayVolume() {
        try {
            String todayNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            String timeNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE);
            Map<NeedAddRealTimeTable, TableCount> allTableCountMap = new HashMap();
            List<NeedAddRealTimeTable> needAddRealTimeTableList = dataMonitorDao.getAllNeedRealTimeTable();
            Set<String> resourceIds = needAddRealTimeTableList.stream().map(NeedAddRealTimeTable::getDataSourceId).collect(Collectors.toSet());
            /*获取数据源key*/
            Map<String, ResourceKey> keyMap = new ConcurrentHashMap<>();
            resourceIds.parallelStream().forEach(
                item -> {
//                        String tempJsonString = rest.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getDsById?dataId=" + item, String.class);
//                        String tempJsonString = rest.getForObject("http://192.168.71.57:8043/dataresource/api/getResourceById?resId=b1656aba3ffc4e48b79badf49e14e7e0", String.class);
                    String tempJsonString = rest.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getResourceById?resId=" + item, String.class);
                    if (StringUtils.isNotBlank(tempJsonString)) {
                        JSONObject resultJson = JSONObject.parseObject(JSONObject.parseObject(tempJsonString).getObject("data",String.class));
                        if(resultJson!=null){
                            ResourceKey key = JSONObject.parseObject(resultJson.getObject("connectInfo", String.class), ResourceKey.class);
                            key.setResourceId(item);
                            keyMap.put(item, key);
                        }
                    }
                }
            );
            /*获取各表的分区字段*/
            Map<String, List<NeedAddRealTimeTable>> listMap = needAddRealTimeTableList.stream().collect(Collectors.groupingBy(NeedAddRealTimeTable::getDataSourceId));
            /*数据仓库接口参数{"resourceId":"a9b44e186bd846d2b5da2b6da9657e9e","sche":"ymtest","tableNames":["emp1"]}*/
            /*需要三个参数分类查询*/
            /*返回结果是分区字段的集合，没有分区字段就返回{}*/
            for (Map.Entry<String, List<NeedAddRealTimeTable>> resourceEntry : listMap.entrySet()) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("resourceId", resourceEntry.getKey());
                Map<String, List<NeedAddRealTimeTable>> mapFilteredByprojectName = resourceEntry.getValue().stream().collect(Collectors.groupingBy(NeedAddRealTimeTable::getProjectName));
                mapFilteredByprojectName.entrySet().parallelStream().forEach(entry -> {
                    List<String> tableNames = entry.getValue().stream().map(NeedAddRealTimeTable::getTableNameEN).collect(Collectors.toList());
                    paramMap.put("sche", entry.getKey());
                    paramMap.put("tableNames", tableNames);
                    try {
                        for (String tableName:tableNames){
                            String tablePartitionInfo = rest.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getPartitionInfo?resourceId=" + resourceEntry.getKey() + "&project=" + entry.getKey() + "&tableNameEn=" + tableName, String.class);
                            if(StringUtils.isBlank(tablePartitionInfo) || "0".equals(JSONObject.parseObject(tablePartitionInfo).getString("status"))){
                                continue;
                            }
                            JSONObject resultJson = JSONObject.parseObject(JSONObject.parseObject(tablePartitionInfo).getObject("data",String.class));
                            JSONObject partitionFields = JSONObject.parseObject(resultJson.getString("partitionFields"));
                            NeedAddRealTimeTable n = new NeedAddRealTimeTable();
                            n.setDataSourceId(resourceEntry.getKey());
                            n.setTableNameEN(tableName);
                            n.setProjectName(entry.getKey());
                            for (NeedAddRealTimeTable real : entry.getValue()) {
                                if (real.equals(n) && "2".equals(partitionFields.getString("level"))) {
                                    real.setPartitionColumn(partitionFields.getString("fieldName"));
                                }
                            }
                        }
//                        TablePartitionInfo[] tablePartitionInfos = rest.postForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getTablePartitionInfo", paramMap, TablePartitionInfo[].class);
//                        if (tablePartitionInfos != null) {
//                            for (TablePartitionInfo t : tablePartitionInfos) {
//                                NeedAddRealTimeTable n = new NeedAddRealTimeTable();
//                                n.setDataSourceId(t.getResourceId());
//                                n.setTableNameEN(t.getTableName());
//                                n.setProjectName(t.getSche());
//                                for (NeedAddRealTimeTable real : entry.getValue()) {
//                                    if (real.equals(n) && "二级分区".equals(t.getDesc())) {
//                                        real.setPartitionColumn(t.getField());
//                                    }
//                                }
//                            }
//                        }
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionTrace(e));
                    }
                });
            }
            /*等待分区字段获取完成*/
            /*获取指定表的今日数据量*/
            /*存储ads的查询sql*/
            List<String> adsSqlList = new ArrayList<>();
            /*某些阿里平台需要加前缀*/
            String prefix = "";
            if (UrlConstants.SWITCH_ON.equals(environment.getProperty("queryAdsPrefix"))) {
                prefix = "/*+engine=mpp*/";
            }
            /*ads存在分区，查询今日分区的数据量sql*/
            String adsIncrementSqlFormat = prefix + "select ''{0}'' as tableName , count(*) as tableCount from {0} where {1}={2}";
            /*ads不存在分区，查询总数据量的sql*/
            String adsFullSqlFormat = prefix + "select ''{0}'' as tableName , count(*) as tableCount from {0}";
            String adsQuerySqlFormat = prefix + "select * from ( {0} ) t1";
            Map<String, Long> adsSumMap = null;
            Map<String, String> partitionedMap = new HashMap<>();
            for (Map.Entry<String, List<NeedAddRealTimeTable>> entry : listMap.entrySet()) {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(
                            keyMap.get(entry.getKey()).getUrl() + "/hc_db",
                            keyMap.get(entry.getKey()).getUsername(),
                            keyMap.get(entry.getKey()).getPassword());
                    for (NeedAddRealTimeTable oneNeedAddRealTimeTable : entry.getValue()) {
                        try {
                            String projectName = oneNeedAddRealTimeTable.getProjectName().trim();
                            String tableNameEn = oneNeedAddRealTimeTable.getTableNameEN().trim();
                            String partitionColumn = oneNeedAddRealTimeTable.getPartitionColumn();
                            partitionedMap.put(projectName + "." + tableNameEn.toLowerCase(), partitionColumn);
                            if ("ads".equalsIgnoreCase(oneNeedAddRealTimeTable.getDataBaseType())) {
                                String adsTodayQuerySql = "";
                                String adsAllQuerySql = "";
                                if (StringUtils.isNotBlank(partitionColumn)) {
                                    adsTodayQuerySql = MessageFormat.format(adsIncrementSqlFormat, projectName + "." + tableNameEn, partitionColumn, todayNow);
                                }
                                adsAllQuerySql = MessageFormat.format(adsFullSqlFormat, projectName + "." + tableNameEn);
                                TableCount tableCount = new TableCount();
                                logger.info("ADS的查询sql" + adsTodayQuerySql + "\n" + adsAllQuerySql);
                                /*查询ads中表的数据量*/
                                ResultSet rs = null;
                                Statement st = connection.createStatement();
                                rs = st.executeQuery(adsAllQuerySql);
                                while (rs.next()) {
                                    /*tableName 包括 项目名.表名*/
                                    String tableName = rs.getString("tableName");
                                    tableCount.setAllCount(String.valueOf(rs.getLong("tableCount")));
                                    tableCount.setTodayCount(String.valueOf(rs.getLong("tableCount")));
                                }
                                if (StringUtils.isNotBlank(partitionColumn)) {
                                    Statement s = connection.createStatement();
                                    rs = s.executeQuery(adsTodayQuerySql);
                                    while (rs.next()) {
                                        /*tableName 包括 项目名.表名*/
                                        String tableName = rs.getString("tableName");
                                        tableCount.setTodayCount(String.valueOf(rs.getLong("tableCount")));
                                    }
                                }
                                allTableCountMap.put(oneNeedAddRealTimeTable, tableCount);
                            } else {
                                logger.error("类型为【" + oneNeedAddRealTimeTable.getDataBaseType() + "】没有编写对应的查询服务----------");
                            }
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getExceptionTrace(e));
                        }
                    }
                } catch (Exception e) {
                    logger.error("查询sql报错" + ExceptionUtil.getExceptionTrace(e));
                } finally {
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
            }
            logger.info("ads表信息查询结束");
            logger.info("查询到的数据为：" + JSONObject.toJSONString(allTableCountMap));
            /*获取所有需要监控表的数据总量*/
            List<RealTimeTableFullMessage> realTimeTableFullMessageList = new ArrayList<>();
            for (Map.Entry<NeedAddRealTimeTable, TableCount> entry : allTableCountMap.entrySet()) {
                RealTimeTableFullMessage r = new RealTimeTableFullMessage();
                r.setDataBaseType(entry.getKey().getDataBaseType());
                r.setStatisticDay(timeNow);
                r.setStatisticId(UUIDUtil.getUUID());
                r.setTableAllSum(entry.getValue().getAllCount());
                r.setTableNameCH(entry.getKey().getTableNameCH());
                r.setTableNameEN(entry.getKey().getTableNameEN());
                r.setTableProject(entry.getKey().getProjectName());
                r.setTableTodaySum(entry.getValue().getTodayCount());
                realTimeTableFullMessageList.add(r);
            }
            logger.info("要插入的数据为：" + JSONObject.toJSONString(realTimeTableFullMessageList));
            try {
                if (realTimeTableFullMessageList.size() > 0) {
                    int deleteCount = dataMonitorDao.deleteTodayDataStorageTable(todayNow);
                    logger.info("实时数据监控删除了" + deleteCount + "行数据");
                }
                int insertCount = 0;
                if (realTimeTableFullMessageList.size() > 0) {
                    insertCount = dataMonitorDao.insertIntoAllRealTimeMessage(realTimeTableFullMessageList);
                }
                logger.info("实时数据监控插入了" + insertCount + "条统计的数据");
                OperationLog operationLog = operatorLogDao.getOperateLogMin("实时表数据量统计监控");
                insertUpdateoperationLog(insertCount, operationLog, new Date(), "实时表数据量统计监控");
            } catch (Exception e) {
                logger.error("实时数据处理报错" + ExceptionUtil.getExceptionTrace(e));
            }
        } catch (Exception e) {
            logger.error("统计实时数据处理报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 更新资产方式
     * TODO 日增量为0时需处理，注意是否影响到其他值，比如平均日增量
     */
    @Override
    synchronized
    public void updateAssetsInfo() {
        getDataResourceTable();
    }

    private DataResourceTable chooseTable(DataResourceTable item1, DataResourceTable item2) {
        String partitionCount = item2.getPartionCount();
        String partitionSize = item2.getPartionSize();
        String totalCount = item2.getTotalCount();
        String totalSize = item2.getTotalSize();
        String isPartitionTable = item2.getIsPartitionTable();
        if (StringUtils.isNotBlank(partitionCount)) {
            item1.setPartionCount(partitionCount);
        }
        if (StringUtils.isNotBlank(partitionSize)) {
            item1.setPartionSize(partitionSize);
        }
        if (StringUtils.isNotBlank(totalCount)) {
            item1.setTotalCount(totalCount);
        }
        if (StringUtils.isNotBlank(totalSize)) {
            item1.setTotalSize(totalSize);
        }
        if (StringUtils.isBlank(item1.getTableLife()) && StringUtils.isNotBlank(item2.getTableLife())) {
            if ("-1".equals(item2.getTableLife()) && "0".equals(item2.getIsPartitionTable())) {
                item2.setTableLife("永久");
            }
            item1.setTableLife(item2.getTableLife());
        }
        if (StringUtils.isBlank(item1.getTableCreatedTime()) && StringUtils.isNotBlank(item2.getTableCreatedTime())) {
            item1.setTableCreatedTime(item2.getTableCreatedTime());
        }
        if (StringUtils.isBlank(item1.getLastDataModifiedTime()) && StringUtils.isNotBlank(item2.getLastDataModifiedTime())){
            item1.setLastDataModifiedTime(item2.getLastDataModifiedTime());
        }
        if (StringUtils.isNotBlank(item2.getIsPartitionTable())){
            item1.setIsPartitionTable(isPartitionTable);
        }
//        if (item1.getUserId() == 0 && item2.getUserId() !=0){
////            item1.setUserName(item2.getUserName());
////            item1.setId(item2.getId());
//            item1.setUserId(item2.getUserId());
//        }
        if("1".equals(isPartitionTable) || (StringUtils.isBlank(item1.getPartitionNum()) && StringUtils.isBlank(item2.getPartitionNum()))){
            item1.setPartitionNum("");
        }else if(StringUtils.isNotBlank(item2.getPartitionNum()) && Integer.valueOf(item2.getPartitionNum())>0){
            item1.setPartitionNum(item2.getPartitionNum());
        }else{
            item1.setPartitionNum("");
        }
        if (StringUtils.isNotBlank(item2.getResourceId()) && StringUtils.isBlank(item1.getResourceId())){
            item1.setResourceId(item2.getResourceId());
        }
        return item1;
    }

    /**
     * @return void
     * @author mj
     * @description 获取资产的新方式，以数据仓库的表数据打底，若syndmg_table_all中有数据量信息，就取syndmg_table_all中的数据量信息
     * @data 2020年8月16日18:53:46
     */
    private void getDataResourceTable() {
        try {
            // 定时调度前设置管理员用户
            LoginUser loginUser = new LoginUser();
            loginUser.setUserLevel(1);
            AuthorizedUserUtils.getInstance().setAuthor(loginUser);
            // 获取数据源（本地仓）
            JSONArray dataResourceLocal = restTemplateHandle.getDataResourceByisLocal("2", '0');
            //根据数据源从仓库循环获取所有用户数据
            List<DataResourceTable> dataResourceTableList = new ArrayList<>();
            if(StringUtils.isNotBlank(String.valueOf(dataResourceLocal))){
                for (int i=0; i<dataResourceLocal.size(); i++){
                    JSONObject dataResource = JSONObject.parseObject(dataResourceLocal.getString(i));
                    logger.info(String.format("开始获取数据中心为【centerName：%s,centerId：%s】,数据源为【resName：%s,resId：%s】的数据",
                            dataResource.getString("centerName"),dataResource.getString("centerId"),dataResource.getString("resName"),dataResource.getString("resId")));
                    JSONArray detectedTableArray = restTemplateHandle.getTablesIncludeDetectedInfo(dataResource.getString("resId"), "");
                    if(StringUtils.isNotBlank(String.valueOf(detectedTableArray)) && detectedTableArray.size() > 0){
                        for (int j=0; j<detectedTableArray.size(); j++){
                            DataResourceTable dataResourceTable = new DataResourceTable();
                            JSONObject detectedTable = (JSONObject) detectedTableArray.get(j);
                            dataResourceTable.setTableName(detectedTable.getString("tableNameEN"));
                            dataResourceTable.setTableNameCh(detectedTable.getString("tableNameCN"));
                            dataResourceTable.setProjectName(detectedTable.getString("projectName"));
                            dataResourceTable.setIsPartitionTable(detectedTable.getString("isPartitioned").equals("2") ? "0" : "1");
                            dataResourceTable.setBelongSystemCode(detectedTable.getString("manageUnitCode"));
                            dataResourceTable.setUpdateDate(detectedTable.getString("updateCycle"));
                            dataResourceTable.setTableCreatedTime(detectedTable.getString("createTime"));
                            dataResourceTable.setLastDataModifiedTime(detectedTable.getString("lastDMLTime"));
                            dataResourceTable.setResourceId(detectedTable.getString("resId"));
                            dataResourceTable.setIsRegistered(detectedTable.getString("isRegistered"));
                            dataResourceTable.setIsApproved(detectedTable.getString("isApproved"));
                            // 获取数据源类型、数据源名称
                            String resType = dataResource.getString("resType");
                            String resName = dataResource.getString("resName");
                            dataResourceTable.setTableType(resType);
                            dataResourceTable.setResName(resName);
                            // 数据汇总
                            dataResourceTableList.add(dataResourceTable);
                        }
                    }
                }
            }
            logger.info("从仓库获取到的数据量为：" + dataResourceTableList.size());
            /*陈亮的syndmg_table_all表中的数据（除ck外）*/
            List<DataResourceTable> syndmgTableAllData = dataMonitorDao.getSyndmgTableAllDataByDate(0);
            // ck数据单独处理
            syndmgTableAllCkData(syndmgTableAllData);
            logger.info("从syndmg_table_all获取到的数据量为：" + syndmgTableAllData.size());
            // 仓库数据和资产统计数据合并
            dataResourceTableList.addAll(syndmgTableAllData);

            /*获取数据资产告警配置*/
            OrganizationAlarmSetting setting = getAlarmSetting();
            /*获取标准信息*/
            List<TableOrganizationData> classifyList = dataMonitorDao.getClassifyInfo();
            Map<String,List<TableOrganizationData>> classifyMap = classifyList.stream().collect(Collectors.groupingBy(TableOrganizationData::getTableNameEn));

            /*获取平均量*/
            List<DataResourceTable> averageList = dataMonitorDao.getSyndmgTableAverageData(setting);
            Map<DataResourceTable, DataResourceTable> averageMap = averageList.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
            /*获取热度*/
            List<DataResourceTable> heatList = dataMonitorDao.getHeatTableData();
            Map<DataResourceTable, DataResourceTable> heatMap = heatList.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));

            logger.info("合并之前共有[{}]张表",dataResourceTableList.size());
            /*两个集合合并*/
            BigInteger zero = new BigInteger("0");
            Map<DataResourceTable, DataResourceTable> tableAllMap = dataResourceTableList.stream().
                collect(Collectors.toMap(Function.identity(), Function.identity(),
                    (item1, item2) -> {
                        DataResourceTable item;
                        /* 合并两个集合时，操作对象，存入map的对象包含所有的数据 */
                        if ((item2.getPartionCount() != null && item2.getPartionSize() != null) ||
                                (StringUtils.isNotBlank(item2.getPartitionNum()) && new BigInteger(item2.getPartitionNum()).compareTo(zero)>0) ||
                                (StringUtils.isNotBlank(item2.getTotalSize()) && new BigInteger(item2.getTotalSize()).compareTo(zero) > 0)) {
                            item = chooseTable(item1, item2);
                        } else {
                            item = chooseTable(item2, item1);
                        }
                        return item;
                    }));
            logger.info("合并完后总共有[{}]张表",tableAllMap.size());

            /*转为入库对象*/
            List<TableOrganizationData> allTableList = new ArrayList<>();
            tableAllMap.forEach((key, value) -> {
                if(StringUtils.isBlank(value.getResourceId())){
                    return;
                }
                /*注入基本数据*/
                TableOrganizationData t = new TableOrganizationData();
                t.setTableNameEn(value.getTableName());
                t.setName(value.getTableNameCh());
                t.setTableProject(value.getProjectName());
                t.setTableType(value.getTableType().toLowerCase());
                t.setResName(value.getResName());
                t.setPartitionMessage(value.getIsPartitionTable());
                t.setUpdateDate(value.getUpdateDate());
                t.setUpdatePeriod(value.getUpdatePreiod());
                t.setBelongSystemCode(value.getBelongSystemCode());
                t.setLastDataModifiedTime(value.getLastDataModifiedTime());
                t.setResourceId(value.getResourceId());
                t.setLifeCycleStatus(value.getIsApproved());
                if (value.getPartitionNum()!=null){
                    t.setPartitionNum(value.getPartitionNum());
                }else {
                    t.setPartitionNum("");
                }
                if ("-1".equals(value.getTableLife()) && "0".equals(value.getIsPartitionTable())) {
                    t.setLifeCycle("永久");
                } else if ("0".equals(value.getIsPartitionTable())) {
                    t.setLifeCycle(value.getTableLife());
                }
                t.setTableCreatedTime(value.getTableCreatedTime());
                if (value.getPartionCount() != null) {
                    t.setYesterdayCount(new BigInteger(value.getPartionCount()));
                }
                if (value.getTotalCount() != null) {
                    t.setTableAllCount(new BigInteger(value.getTotalCount()));
                }
                if (value.getTotalSize() != null) {
                    t.setTableSize(new BigInteger(value.getTotalSize() != null ? value.getTotalSize() : "0"));
                }
                /*注入协议编号等标准信息*/
                injectStandardInfo(t, classifyMap, value.getTableName());
                /*注入平均量*/
                DataResourceTable averageTableData = averageMap.get(value);
                if (averageTableData != null) {
                    t.setAverageDailySize(averageTableData.getAverageDailySize() != null ? averageTableData.getAverageDailySize() : new BigInteger("0"));
                    t.setAverageDailyVolume(averageTableData.getAverageDailyVolume() != null ? averageTableData.getAverageDailyVolume() : new BigInteger("0"));
                }
                /*注入热度*/
                DataResourceTable heatTableData = heatMap.get(value);
                if (heatTableData != null) {
                    t.setFrequency(heatTableData.getFrequency());
                    t.setLiveType(heatTableData.getLiveType());
                    t.setTableUseCountOfmonth(heatTableData.getTableUseCountOfmonth());
                }
                allTableList.add(t);
            });
            logger.info("allTableList:" + allTableList.size());
            /*处理组织信息，并入库*/
            handleTableOrganizationData(allTableList, setting);

        } catch (Exception e) {
            logger.error("获取仓库资源出错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /*注入协议编号等标准信息*/
    public void injectStandardInfo(TableOrganizationData t, Map<String,List<TableOrganizationData>> classifyMap, String tableName){
//        Map standardMap = classifyMap.get(tableName.toUpperCase());
        if (classifyMap.get(tableName.toUpperCase()) == null){
            return;
        }
        TableOrganizationData standardMap = classifyMap.get(tableName.toUpperCase()).get(0);
        if (standardMap != null) {
            t.setSjxjbm(standardMap.getSjxjbm());
            if (StringUtils.isNotBlank(standardMap.getName())){
                t.setName(standardMap.getName());
            }
//                    t.setObjectId(standardMap.get("OBJECTID").toString());
            t.setObjectId(standardMap.getObjectId() == null ? "":standardMap.getObjectId());
            t.setObjectState(standardMap.getObjectState() == null ? "":standardMap.getObjectState());
            t.setPrimaryDatasourceCh(standardMap.getPrimaryDatasourceCh());
            t.setSecondaryDatasourceCh(standardMap.getSecondaryDatasourceCh());
            /*刘新鹏修改的CLASSIFY_INTERFACE_ALL_DATE表，FIRSTORGANIZATIONCH为二级组织分类，SECONDARYORGANIZATIONCH为三级组织分类*/
            String PriOrg = String.valueOf(standardMap.getPrimaryOrganizationCh());
            String FirOrg = String.valueOf(standardMap.getThreeLevelOrganizationCh()).equalsIgnoreCase("null") ? "" : String.valueOf(standardMap.getThreeLevelOrganizationCh());
            String SecOrg = String.valueOf(standardMap.getSecondaryOrganizationCh()).equalsIgnoreCase("null") ? "" : String.valueOf(standardMap.getSecondaryOrganizationCh());
            if ("原始库".equalsIgnoreCase(PriOrg) || ("主题库".equals(PriOrg) && !PriOrg.equals(FirOrg)) ){
                t.setPrimaryOrganizationCh(PriOrg);
                t.setSecondaryOrganizationCh(FirOrg);
                t.setThreeLevelOrganizationCh(SecOrg);
            }else {
                t.setPrimaryOrganizationCh(PriOrg);
                t.setSecondaryOrganizationCh(SecOrg);
            }
            t.setOrganizationIdLastLevel(standardMap.getOrganizationIdLastLevel());
            t.setDataresourceIdLastLevel(standardMap.getDataresourceIdLastLevel());
        }
    }

    public void syndmgTableAllCkData (List<DataResourceTable> syndmgTableAllData){
        List<DataResourceTable> syndmgTableAllCKData = dataMonitorDao.getSyndmgTableAllCKData();
        if (syndmgTableAllCKData.size()==0){
            return;
        }
        String todayNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        Map<String,List<DataResourceTable>> ckMap = syndmgTableAllCKData.stream().collect(Collectors.groupingBy(d ->
                (d.getTableType() + "_" + d.getProjectName() + "_" + d.getTableName()).toUpperCase()));
        for(String key : ckMap.keySet()) {
            List<DataResourceTable> ckDataList = ckMap.get(key);
            if (ckDataList.size() > 1){
                DataResourceTable ckData0 = ckDataList.get(0);
                DataResourceTable ckData1 = ckDataList.get(1);
                if (ckData0 !=null && ckData0.getPartionDate() !=null && ckData0.getPartionDate().length()==6){  // partitionDate 6位说明按月分区
                    long partitionCount = 0l;
                    if (ckData0.getInsertDataTime().equals(todayNow)){
                        // 今日分区与昨日分区相等时：日增量=今日分区数据量-昨日分区数据量
                        // 今日分区与昨日分区不等时：取今日分区数据量
                        if (ckData0.getPartionDate().equals(ckData1.getPartionDate())){
                            partitionCount = Long.parseLong(ckData0.getPartionCount()) - Long.parseLong(ckData1.getPartionCount());
                            partitionCount = partitionCount > 0 ? partitionCount : 0;
                            ckData0.setPartionCount(String.valueOf(partitionCount));
                            syndmgTableAllData.add(ckData0);
                        }else {
                            syndmgTableAllData.add(ckData0);
                        }
                    }else {
                        partitionCount = Long.parseLong(ckData1.getPartionCount()) - Long.parseLong(ckData0.getPartionCount());
                        partitionCount = partitionCount > 0 ? partitionCount : 0;
                        ckData1.setPartionCount(String.valueOf(partitionCount));
                        syndmgTableAllData.add(ckData1);
                    }
                }else {
                    if (ckData0.getInsertDataTime().equals(todayNow)){
                        syndmgTableAllData.add(ckData0);
                    }else {
                        syndmgTableAllData.add(ckData1);
                    }
                }
            }else {
                syndmgTableAllData.add(ckDataList.get(0));
            }
        }
    }

    /**
     * 递归转换树形json数据
     * @return
     */
    public List<ClassifyInfoTree> convert2Tree(List<ClassifyInfo> tables, String codeId, List<ClassifyInfoTree> array) {
        for (ClassifyInfo table:tables) {
            if(StringUtils.isBlank(table.getCodeIdPar())){
                continue;
            }
            if (table.getCodeIdPar().equalsIgnoreCase(codeId)){
                ClassifyInfoTree classifyInfoTree = new ClassifyInfoTree();
                classifyInfoTree.setValue(table.getCodeId());
                classifyInfoTree.setLabel(table.getCodeText());
                classifyInfoTree.setChildren(convert2Tree(tables,table.getCodeId(),new LinkedList<>()));
                array.add(classifyInfoTree);
            }
        }
        return array;
    }

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

    /**
     * 只适合单机
     *
     * @param allTableOrganizationDataList
     * @param setting
     */
    private void handleTableOrganizationData(List<TableOrganizationData> allTableOrganizationDataList, OrganizationAlarmSetting setting) {
        String todayNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        int insertCount = 0;
        long startTime=System.currentTimeMillis();

        long getOdpsAdsInfoFinishTime=System.currentTimeMillis();
        logger.info("获取标准建表时插入到统计表OdpsAds信息结束，用时：" + ((getOdpsAdsInfoFinishTime-startTime)/1000) + "秒");

//        /*获取所有表生命周期*/
//        getAllTableLifeCycle(allTableOrganizationDataList);
//        long getAllTableLifeCycleFinishTime=System.currentTimeMillis();
//        logger.info("获取所有表生命周期结束，用时：" + ((getAllTableLifeCycleFinishTime-getRegisterListFinishTime)/1000) + "秒");

//        /*获取本地注册审批状态*/
//        List<TableOrganizationData> approvals = dataMonitorDao.getRegisterApprovals();
//        Map<TableOrganizationData, TableOrganizationData> approvalMap = new HashMap<>();
//        for (TableOrganizationData t : approvals) {
//            approvalMap.put(t, t);
//        }
//        List<DataApproval> updateLifeCycleApprovals = dataMonitorDao.getApprovalsByType("updateLifeCycle");
//        long getRegisterApprovalsFinishTime=System.currentTimeMillis();
//        logger.info("获取本地注册审批状态结束，用时：" + ((getRegisterApprovalsFinishTime-getOdpsAdsInfoFinishTime)/1000) + "秒");

        /*获取ObjectId*/
        Map<TableOrganizationData, TableOrganizationData> map = new HashMap<>();
        if (allTableOrganizationDataList.size() > 0) {
            Set<TableOrganizationData> objectList = dataMonitorDao.getObjectList(allTableOrganizationDataList);
            for (TableOrganizationData t : objectList) {
                map.put(t, t);
            }
        }
        long getObjectListFinishTime=System.currentTimeMillis();
        // 获取已注册表信息(用于注入注册状态)
        List<PublicDataInfo> publicDataInfos = dataMonitorDao.getPublicDataInfo();
        // 标准信息(注入对标信息)
        String[] objectTableNames = dataMonitorDao.getObjectTableNames();
        // 建表信息(注入对标信息)
        List<TableOrganizationData> objectStoreInfos = dataMonitorDao.getObjectStoreInfos();
        // 要插入的表组织数据table_organization_assets
        List<TableOrganizationData> insertList = new ArrayList<>();

        // 资产统计数据从数据库依次查询改为统一获取后内存处理
        List<TableOrganizationData> syndmgTables = dataMonitorDao.getPreviousTableAllCount(3);
        Map<String, List<TableOrganizationData>> syndmgTableMaps = syndmgTables.stream().collect(Collectors.groupingBy(TableOrganizationData::getSjxjbm));

        //TODO 如果要并发跑，测一下
        for (TableOrganizationData oneTableOrganizationData : allTableOrganizationDataList){
            /*tablestate,异常信息直接注入到对象中去*/
            String checkStatus = checkStatusData(oneTableOrganizationData, setting);
            oneTableOrganizationData.setTableState(checkStatus);
            if (StringUtils.isBlank(oneTableOrganizationData.getPrimaryOrganizationCh())) {
                oneTableOrganizationData.setPrimaryOrganizationCh("中间表");
                if (StringUtils.isBlank(oneTableOrganizationData.getSecondaryOrganizationCh())) {
                    oneTableOrganizationData.setSecondaryOrganizationCh("中间表");
                }
            }
            if (StringUtils.isBlank(oneTableOrganizationData.getPrimaryDatasourceCh())) {
                oneTableOrganizationData.setPrimaryDatasourceCh("中间表");
                if (StringUtils.isBlank(oneTableOrganizationData.getSecondaryDatasourceCh())) {
                    oneTableOrganizationData.setSecondaryDatasourceCh("中间表");
                }
            }
            /*再次利用hashcode，让objectId和停用启用状态注入到insert到表里的对象中*/
            if (oneTableOrganizationData.getObjectId() == null && map.get(oneTableOrganizationData) != null) {
                oneTableOrganizationData.setObjectId(map.get(oneTableOrganizationData).getObjectId());
                oneTableOrganizationData.setObjectState(map.get(oneTableOrganizationData).getObjectState());
            }
            // 注入对标信息
            getIsStandardTable(oneTableOrganizationData, objectTableNames, objectStoreInfos);
            // 注入注册状态
            getRegisterStatus(oneTableOrganizationData, publicDataInfos);
            /*这里直接查询对账数据相加，减少代码的复杂度，速度有点慢*/
            try {
                getRealDataNum(oneTableOrganizationData, syndmgTableMaps);
            } catch (Exception e) {
                logger.error("增加对账数据报错" + ExceptionUtil.getExceptionTrace(e));
            }
            insertList.add(oneTableOrganizationData);
            // TODO 有值率 空值率 这些内容未知
        }
        long dataConcludeFinishTime=System.currentTimeMillis();
        logger.info("待插入表组织数据整理归纳完成，用时：" + ((dataConcludeFinishTime-getObjectListFinishTime)/1000) + "秒");

        /*如果查询到的数据大于0 ， 先删除数据库表中指定时间的所有数据，然后再将新查询到的数据插入到数据库中*/
        transactionUtil.transaction(s -> {
            if (allTableOrganizationDataList.size() > 0) {
                int deleteCount = dataMonitorDao.deleteAllOrganizationData();
                logger.info("数据表组织在【" + todayNow + "】总共删除" + deleteCount + "条数据");
//                int delAuthCount = dataMonitorDao.deleteAllUserAuthorityData();
//                logger.info("权限表在【" + todayNow + "】总共删除" + delAuthCount + "条数据");
                try {
                    DAOHelper.insertDelList(insertList, dataMonitorDao, "insertAllOrganizationData", 200);
                    logger.info("数据表组织在【" + todayNow + "】新插入" + insertList.size() + "条数据");
//                    DAOHelper.insertDelList(userAuthorityDataInsertListTemp, dataMonitorDao, "insertAllUserAuthorityData", 500);
//                    logger.info("权限表在【" + todayNow + "】新插入" + userAuthorityDataInsertListTemp.size() + "条数据");
                } catch (Exception e) {
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
                long insertDataFinishTime=System.currentTimeMillis();
                logger.info("数据入库完成，用时：" + ((insertDataFinishTime-dataConcludeFinishTime)/1000) + "秒");
            } else {
                logger.info("数据表组织在【" + todayNow + "】查询到的数据量为0");
            }
        });
        try {
            OperationLog operationLog = operatorLogDao.getOperateLogMin("数据表组织");
            insertUpdateoperationLog(insertCount, operationLog, new Date(), "数据表组织");
        } catch (Exception e) {
            logger.error("本次数据表组织任务查询日志记录更新报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 注入注册状态
     * @param tableOrganizationData
     */
    private void getRegisterStatus(TableOrganizationData tableOrganizationData, List<PublicDataInfo> publicDataInfos){
        tableOrganizationData.setRegisterState("1");
        for (int i=0; i<publicDataInfos.size(); i++){
            String tableNameEn = publicDataInfos.get(i).getTableName() !=null ? publicDataInfos.get(i).getTableName() : "";
            String tableId = publicDataInfos.get(i).getTableId() !=null ? publicDataInfos.get(i).getTableId() : "";
            String projectName = publicDataInfos.get(i).getProjectName() !=null ? publicDataInfos.get(i).getProjectName() : "";
            if (StringUtils.isNotBlank(tableOrganizationData.getTableNameEn()) && tableOrganizationData.getTableNameEn().equalsIgnoreCase(tableNameEn) &&
                StringUtils.isNotBlank(tableOrganizationData.getTableProject()) && tableOrganizationData.getTableProject().equalsIgnoreCase(projectName) &&
                StringUtils.isNotBlank(tableOrganizationData.getSjxjbm()) && tableOrganizationData.getSjxjbm().equalsIgnoreCase(tableId)){
                tableOrganizationData.setRegisterState("2");
                break;
            }
        }
    }

    /**
     * 注入是否对标信息
     * @param tableOrganizationData
     */
    private void getIsStandardTable(TableOrganizationData tableOrganizationData, String[] objectTableNames, List<TableOrganizationData> objectStoreInfos){
        // 注入对标信息
        tableOrganizationData.setIsStandard("未对标");
        for (int i=0; i<objectTableNames.length; i++){
            String objectTableName = objectTableNames[i];
            if (tableOrganizationData.getTableNameEn()!=null && objectTableName !=null && tableOrganizationData.getTableNameEn().equalsIgnoreCase(objectTableName)){
                tableOrganizationData.setIsStandard("已对标");
                break;
            }
        }
        for (int i=0; i<objectStoreInfos.size(); i++){
            if ("已对标".equalsIgnoreCase(tableOrganizationData.getIsStandard())){
                break;
            }
            String tableName = objectStoreInfos.get(i).getTableNameEn() !=null ? objectStoreInfos.get(i).getTableNameEn() : "";
            String tableName1 = tableOrganizationData.getTableNameEn() !=null ? tableOrganizationData.getTableNameEn() : "";
            String tableProject = objectStoreInfos.get(i).getTableProject() !=null ? objectStoreInfos.get(i).getTableProject() : "";
            String tableProject1 = tableOrganizationData.getTableProject() !=null ? tableOrganizationData.getTableProject() : "";
            String tableTypeCode = objectStoreInfos.get(i).getTableType() !=null ? objectStoreInfos.get(i).getTableType() : "";
            tableTypeCode = tableTypeCode.equalsIgnoreCase("3") ? "2" : tableTypeCode;
            String tableType = SysCodeEnum.getNameByCodeAndType(tableTypeCode, "TABLETYPE");
            if (StringUtils.isBlank(tableType)){
                break;
            }
            String tableType1 = tableOrganizationData.getTableType() !=null ? tableOrganizationData.getTableType() : "";
            if (tableName.equalsIgnoreCase(tableName1) && tableProject.equalsIgnoreCase(tableProject1) && tableType.equalsIgnoreCase(tableType1)){
                tableOrganizationData.setIsStandard("已对标");
                break;
            }
        }
    }

    /**
     * 增加标准建表时插入到统计表的odps/ads表,这时未统计到odps、ads的信息，所以需要自行添加临时数据。
     * @param allTableOrganizationDataList
     */
//    private void getOdpsAdsInfo(List<TableOrganizationData> allTableOrganizationDataList, Map<String, Map> classifyMap){
//        try {
//            List<TableOrganizationData> insertOdpsAdsTableList = dataMonitorDao.getOdpsAdsTableInfo();
//            List<TableOrganizationData> deleteOdpsAdsTableList = new ArrayList<>();
//            boolean flag = false;
//            for (TableOrganizationData t : insertOdpsAdsTableList) {
//                t.setTableType(t.getTableType().toLowerCase());
//                Iterator<TableOrganizationData> it = allTableOrganizationDataList.iterator();
//                while (it.hasNext()) {
//                    TableOrganizationData tod = it.next();
//                    injectStandardInfo(t, classifyMap, t.getTableNameEn());
//                    flag = StringUtils.isBlank(t.getPrimaryOrganizationCh()) ? true : false;
//                    if (t.getTableNameEn().equalsIgnoreCase(tod.getTableNameEn())) {
//                        if (StringUtils.isBlank(tod.getTableProject()) && StringUtils.isBlank(tod.getTableType())) {
//                            it.remove();
//                            continue;
//                        }
//                        if (t.getTableProject().equalsIgnoreCase(tod.getTableProject()) && t.getTableType().equalsIgnoreCase(tod.getTableType())) {
//                            flag = true;
//                        }
//                    }
//                }
//                if (!flag) {
//                    allTableOrganizationDataList.add(t);
//                } else {
//                    deleteOdpsAdsTableList.add(t);
//                }
//                flag = false;
//            }
//            if (deleteOdpsAdsTableList.size() > 0) {
//                dataMonitorDao.deleteOdpsAdsTableInfo(deleteOdpsAdsTableList);
//            }
//        } catch (Exception e) {
//            logger.error(ExceptionUtil.getExceptionTrace(e));
//        }
//    }

    /**
     * 获取所有表生命周期
     * @param allTableOrganizationDataList
     */
    private void getAllTableLifeCycle(List<TableOrganizationData> allTableOrganizationDataList){
        try {
            allTableOrganizationDataList.parallelStream().forEach(item -> {
                if (!"永久".equals(item.getLifeCycle()) && "0".equals(item.getPartitionMessage())) {
//                    DataResourceImformation info = rest.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getLocalTableImformation?tableType=" + item.getTableType() +
//                            "&tableId=" + item.getTableProject() + "." + item.getTableNameEn(), DataResourceImformation.class);
                    DetailedTableByClassify info = organizationDetailDao.getTableOrganizationInfo(item.getTableProject(), item.getTableNameEn(), item.getTableType());
                    if (info != null && StringUtils.isNotBlank(info.getLifeCycle())) {
                        item.setLifeCycle(info.getLifeCycle());
                    }
                }
            });
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 表记录数加上对账数，若表记录数为0，向前推进获取到对应不为0的数后，加上后面几日的每日对账数
     * TODO 日增量未定好
     *
     * @param t
     */
    private void getRealDataNum(TableOrganizationData t, Map<String, List<TableOrganizationData>> syndmgTableMaps) {
        List<BigInteger> allCount = new ArrayList<>();
        BigInteger zero = new BigInteger("0");
        // 资产统计数据从数据库依次查询改为统一获取后内存处理
        if (t.getTableAllCount() != null && t.getTableAllCount().compareTo(zero) != 0L) {
            allCount.add(t.getTableAllCount() != null ? t.getTableAllCount() : zero);
        } else {
            for (String key : syndmgTableMaps.keySet()){
                String tableType = StringUtils.isNotBlank(t.getTableType()) ? t.getTableType() : "";
                String project = StringUtils.isNoneBlank(t.getTableProject()) ? t.getTableProject() : "";
                String tableName = StringUtils.isNoneBlank(t.getTableNameEn()) ? t.getTableNameEn() : "";
                String sjxjbm = tableType + "." + project + "." + tableName;
                if (sjxjbm.toLowerCase().equalsIgnoreCase(t.getSjxjbm())){
                    for (TableOrganizationData data : syndmgTableMaps.get(key)){
                        allCount.add(data.getTableAllCount() != null ? data.getTableAllCount() : zero);
                    }
                    break;
                }
            }
        }

    }

    @Override
    public void getDataResourceInfo() {
        try {
            String dataResourceString = rest.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/findResourceForLocalBase", String.class);
            Map dataResourceMap = JSON.parseObject(dataResourceString);
            logger.info((String) dataResourceMap.get("msg"));
            Integer code = (Integer) dataResourceMap.get("code");
            if (code == 1) {
                /*直接改对应阿里平台的表，因为是基于陈亮的表，所以只有一个阿里平台数据源，所以可以直接改*/
                List<DataResourceInfo> updateDataResourceInfoList = JSON.parseArray(JSON.toJSONString(dataResourceMap.get("data")), DataResourceInfo.class);
                if (updateDataResourceInfoList.size() > 0) {
                    synchronized (this) {
                        DAOHelper.insertDelList(updateDataResourceInfoList, dataMonitorDao, "updateDataResourceInfo", 50);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取仓库资源出错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public void getDataBaseStatus() {
        List<DataBaseState> dataBaseStates = new ArrayList<>();
        // 统计资产表今日数据量如果为0，则获取昨天数据
        int daysAgo = dataStorageMonitorDao.getTodayAssetsCount() < 100 ? 1 : 0;
        List<DataBaseState> platTableSumList = dataStorageMonitorDao.getPlatTableSum(daysAgo);
        // 获取从syndmg_table_all表里获取各个平台类型中数据总条数
        List<Map<String, Object>> allPlatformCountList = dataStorageMonitorDao.getAllDataBaseCount();

        collectDatabaseStates(dataBaseStates, platTableSumList, allPlatformCountList);

        if (!dataBaseStates.isEmpty()) {
            int deleteCount = dataStorageMonitorDao.deleteDataBaseStates(dataBaseStates);
            logger.info("删除了{}条数据库概况数据", deleteCount);
            int insertCount = dataStorageMonitorDao.insertDataBaseStates(dataBaseStates);
            logger.info("增加了{}条数据库概况数据", insertCount);
        }
    }

    private void collectDatabaseStates(List<DataBaseState> dataBaseStates, List<DataBaseState> platTableSumList, List<Map<String, Object>> allPlatformCountList) {
        // 华为统计主页数据库状况
        List<Object> hiveResIds = getResIdByDatabaseType("hive");
        hiveResIds = removeDuplicatesByName(hiveResIds, "connectInfo");
        if (!hiveResIds.isEmpty()) {
            collectHuaweiDatabaseStates(dataBaseStates, platTableSumList, allPlatformCountList, hiveResIds);
        }

        // 阿里数据库概况统计
        if (PlatformType.ALI.equals(cacheManager.getValue("dataPlatFormType"))) {
            collectAliDatabaseStates(dataBaseStates, platTableSumList, allPlatformCountList);
        }

        // ClickHouse 主页数据库状况
        List<Object> ckResIds = getResIdByDatabaseType("clickhouse");
        if (!ckResIds.isEmpty()) {
            collectClickhouseDatabaseStates(dataBaseStates, platTableSumList, allPlatformCountList, ckResIds);
        }
    }

    private void collectHuaweiDatabaseStates(List<DataBaseState> dataBaseStates,
                                             List<DataBaseState> platTableSumList,
                                             List<Map<String, Object>> allPlatformCountList,
                                             List<Object> resIds) {
        for (Object object : resIds){
            try {
                String resId = JSONObject.parseObject(object.toString()).getString("resId");
                String resName = JSONObject.parseObject(object.toString()).getString("resName");
                String url = UrlConstants.DATARESOURCE_BASEURL_API + "/getResourceOverview?resourceId=" + resId;
                logger.info("hdfs数据库概况统计url：" + url);
                String resultStr = rest.getForObject(url, String.class);
                logger.info("hdfs数据库概况统计返回结果为：\n" + resultStr);
                ResourceOverView resourceOverView = JSONObject.parseObject(resultStr).getObject("data", ResourceOverView.class);

                DataBaseState dataBaseState = new DataBaseState();
                dataBaseState.setName("HDFS_" + resName);
                dataBaseState = getTableSum(platTableSumList, dataBaseState, "hive");
                dataBaseState.setUsedCapacity(NumUtil.handleNumWithUnit(resourceOverView.getUsedSpace() + "MB"));
                dataBaseState.setBareCapacity(NumUtil.handleNumWithUnit(resourceOverView.getTotalSpace() + "MB"));
                setTableCountByPlatformName(dataBaseState, allPlatformCountList, "hive");
                dataBaseStates.add(dataBaseState);
            } catch (Exception e) {
                logger.error("hdfs数据库概况统计报错: {}", e);
            }
        }
    }

    private void collectAliDatabaseStates(List<DataBaseState> dataBaseStates, List<DataBaseState> platTableSumList, List<Map<String, Object>> allPlatformCountList) {
        try {
            List<Map<String, Object>> allOdpsAdsSizelist = "1".equals(environment.getProperty("wushi"))
                    ? dataStorageMonitorDao.getWushiOdpsAdsUsedCapacity()
                    : dataStorageMonitorDao.getOdpsAdsUsedCapacity();
            List<Map<String, Object>> ossFileCount = dataStorageMonitorDao.getOssFileCount();
            // odps/ads
            boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
            for (Map<String, Object> row : allPlatformCountList) {
                String platformType = String.valueOf(row.get("TABLETYPE")).toUpperCase();
                if (isHailiang){
                    platformType = String.valueOf(row.get("tabletype")).toUpperCase();
                }
                if ("ODPS".equals(platformType) || "ADS".equals(platformType)) {
                    addAliDatabaseState(dataBaseStates, platTableSumList, row, allOdpsAdsSizelist);
                }
            }
            // oss/datahub
            addAliOssDatahubDatabaseState(dataBaseStates, allOdpsAdsSizelist, platTableSumList, ossFileCount);
        } catch (Exception e) {
            logger.error("阿里数据库概况统计报错: {}", ExceptionUtil.getExceptionTrace(e));
        }
    }

    private void collectClickhouseDatabaseStates(List<DataBaseState> dataBaseStates,
                                                 List<DataBaseState> platTableSumList,
                                                 List<Map<String, Object>> allPlatformCountList,
                                                 List<Object> resIds) {
        for (Object object : resIds){
            try {
                String resId = JSONObject.parseObject(object.toString()).getString("resId");
                String resName = JSONObject.parseObject(object.toString()).getString("resName");
                String storageInfo = "select sum(total_space) as total_space,\n" +
                        "       sum(free_space) as free_space,\n" +
                        "       (total_space - free_space) / total_space as used_rate\n" +
                        "  from clusterAllReplicas(ck_cluster, system, disks)";
                JSONArray jsonArray = restTemplateHandle.excuteSql(resId, storageInfo);
                JSONObject data = jsonArray.getJSONObject(0);
                if (data != null) {
                    addClickhouseDatabaseState(dataBaseStates, platTableSumList, allPlatformCountList, data, resName);
                }
            } catch (Exception e) {
                logger.error("ClickHouse数据库概况统计报错: {}", ExceptionUtil.getExceptionTrace(e));
            }
        }
    }
    public List<Object> getResIdByDatabaseType(String databaseType) {
        try {
            JSONArray dataResourceLocal = restTemplateHandle.getDataResourceByisLocal("2", '0');
            JSONArray dataResourceNoLocal = restTemplateHandle.getDataResourceByisLocal("1", '0');
            dataResourceLocal.addAll(dataResourceNoLocal);
            return dataResourceLocal.stream().filter(d -> {
                return JSONObject.parseObject(d.toString()).getString("resType").contains(databaseType);
            }).collect(Collectors.toList());
        }catch (Exception e){
            logger.error("根据数据源类型获取数据源ID出错：\n", e);
            return new JSONArray();
        }
    }

    private void addAliDatabaseState(List<DataBaseState> dataBaseStates,
                                     List<DataBaseState> platTableSumList,
                                     Map<String, Object> row,
                                     List<Map<String, Object>> allOdpsAdsSizelist) {
        boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
        String platformType = String.valueOf(isHailiang ? row.get("tabletype") : row.get("TABLETYPE")).toUpperCase();
        DataBaseState dataBaseState = new DataBaseState();
        dataBaseState.setName(platformType);
        dataBaseState = getTableSum(platTableSumList, dataBaseState, platformType);
        dataBaseState.setTableCount(String.valueOf(isHailiang ? row.get("database_count") : row.get("DATABASE_COUNT")));

        Map<String, Object> odpsAdsSize = findOdpsAdsSize(allOdpsAdsSizelist, platformType);
        if (odpsAdsSize != null) {
            dataBaseState.setUsedCapacity(String.valueOf(isHailiang ? odpsAdsSize.get("used_capacity") : odpsAdsSize.get("USED_CAPACITY")));
            dataBaseState.setBareCapacity(String.valueOf(isHailiang ? odpsAdsSize.get("bare_capacity") : odpsAdsSize.get("BARE_CAPACITY")));
            if ("ODPS".equals(platformType)) {
                dataBaseState.setLiveTableRote(dataStorageMonitorDao.getOdpsLiveRote());
            }
        }
        dataBaseStates.add(dataBaseState);
    }
    private void addAliOssDatahubDatabaseState(List<DataBaseState> dataBaseStates, List<Map<String, Object>> allOdpsAdsSizelist, List<DataBaseState> platTableSumList, List<Map<String, Object>> ossFileCount){
        if (allOdpsAdsSizelist != null && !allOdpsAdsSizelist.isEmpty() &&
                (containsNameInList(allOdpsAdsSizelist, "OSS") || containsNameInList(allOdpsAdsSizelist, "DATAHUB"))) {
            boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
            Map<String, DataBaseState> states = new HashMap<>();
            states.put("OSS", new DataBaseState());
            states.put("DATAHUB", new DataBaseState());
            for (Map<String, Object> oneRow1 : allOdpsAdsSizelist) {
                String name = String.valueOf(isHailiang ? oneRow1.get("name") : oneRow1.get("NAME")).toUpperCase();
                if (states.containsKey(name)) {
                    DataBaseState state = states.get(name);
                    state.setName(name);
                    state = getTableSum(platTableSumList, state, name.toLowerCase());
                    state.setUsedCapacity(String.valueOf(isHailiang ? oneRow1.get("used_capacity") : oneRow1.get("USED_CAPACITY")));
                    state.setBareCapacity(String.valueOf(isHailiang ? oneRow1.get("bare_capacity") : oneRow1.get("BARE_CAPACITY")));
                    if ("OSS".equals(name)) {
                        for (Map<String, Object> oneline : ossFileCount) {
                            if ("OSS".equalsIgnoreCase(String.valueOf(isHailiang ? oneline.get("name") : oneline.get("NAME")))) {
                                state.setTableCount(String.valueOf(isHailiang ? oneline.get("filecount") : oneline.get("FILECOUNT")));
                                break;
                            }
                        }
                    }
                    dataBaseStates.add(state);
                }
            }
        }
    }
    private boolean containsNameInList(List<Map<String, Object>> list, String name) {
        boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
        return list.stream()
                .anyMatch(row -> name.equalsIgnoreCase(String.valueOf(isHailiang ? row.get("name") : row.get("NAME"))));
    }

    private void addClickhouseDatabaseState(List<DataBaseState> dataBaseStates,
                                            List<DataBaseState> platTableSumList,
                                            List<Map<String, Object>> allPlatformCountList,
                                            JSONObject data,
                                            String resName) {
        BigDecimal freeSpace = new BigDecimal(data.getDoubleValue("free_space"));
        BigDecimal totalSpace = new BigDecimal(data.getDoubleValue("total_space"));
        //1024*1024*1024 (GB)
        BigDecimal denominator = new BigDecimal("1073741824");

        DataBaseState dataBaseState = new DataBaseState();
        dataBaseState.setName("CLICKHOUSE_" + resName);
        // 表数量
        dataBaseState = getTableSum(platTableSumList, dataBaseState, "clickhouse");
        // 磁盘使用空间、总空间
        dataBaseState.setUsedCapacity(String.valueOf(totalSpace.subtract(freeSpace).divide(denominator, 2, BigDecimal.ROUND_HALF_UP)));
        dataBaseState.setBareCapacity(String.valueOf(totalSpace.divide(denominator, 2, BigDecimal.ROUND_HALF_UP)));

        boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
        for (Map<String, Object> row : allPlatformCountList) {
            String platformType = String.valueOf(isHailiang ? row.get("tabletype") : row.get("TABLETYPE")).toLowerCase();
            if (platformType.contains("clickhouse")) {
                dataBaseState.setTableCount(String.valueOf(isHailiang ? row.get("database_count") : row.get("DATABASE_COUNT")));
                break;
            }
        }
        dataBaseStates.add(dataBaseState);
    }

    private Map<String, Object> findOdpsAdsSize(List<Map<String, Object>> allOdpsAdsSizelist, String platformType) {
        boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
        return allOdpsAdsSizelist.stream()
                .filter(sizeRow -> platformType.equalsIgnoreCase(String.valueOf(isHailiang ? sizeRow.get("name") : sizeRow.get("NAME"))))
                .findFirst()
                .orElse(null);
    }

    private void setTableCountByPlatformName(DataBaseState dataBaseState, List<Map<String, Object>> allPlatformCountList, String platformType) {
        boolean isHailiang = cacheManager.getValue("dsType").equalsIgnoreCase("hailiang");
        for (Map<String, Object> row : allPlatformCountList) {
            String platformName = String.valueOf(isHailiang ? row.get("tabletype") : row.get("TABLETYPE")).toLowerCase();
            if (platformName.contains(platformType)) {
                dataBaseState.setTableCount(String.valueOf(isHailiang ? row.get("database_count") : row.get("DATABASE_COUNT")));
                break;
            }
        }
    }

    public DataBaseState getTableSum(List<DataBaseState> platTableSumList, DataBaseState dataBaseState, String tableType){
        if (platTableSumList != null && platTableSumList.size()>0){
            for (int i = 0; i<platTableSumList.size(); i++){
                if (platTableSumList.get(i).getName().toLowerCase().contains(tableType.toLowerCase())){
                    dataBaseState.setTableSum(platTableSumList.get(i).getTableSum());
                    break;
                }
            }
        }
        return dataBaseState;
    }

    @Override
    public void getHbaseData() {
        try {

            String statisticSql = "SELECT\n" +
                                  "    NAMESPACE AS TABLEPROJECT,\n" +
                                  "    TABLENAME AS TABLENAME,\n" +
                                  "    to_char(ENDHANDLETIME, 'yyyyMMdd') AS PARTITIONDATE,\n" +
                                  "    sum(SUCCESSROWCOUNT) AS TABLEALLCOUNT,\n" +
                                  "    sum(FILESIZEOFMB * 1000000) AS TABLESIZE\n" +
                                  "FROM\n" +
                                  "    bigdata.import_hbase\n" +
                                  "WHERE\n" +
                                  "    ENDHANDLETIME >= trunc(sysdate)" +
                                  "GROUP BY\n" +
                                  "    NAMESPACE,\n" +
                                  "    TABLENAME,\n" +
                                  "    ENDHANDLETIME";

            // ads连接实例
            AdsOrMysqlClient adsOrMysqlClient = new AdsOrMysqlClient();
            JSONArray jsonArray = adsOrMysqlClient.execBySql(dataSourceForStatisHbase, statisticSql);
            List<SYDMGParam> hbaseParams = JSONArray.parseArray(jsonArray.toJSONString(), SYDMGParam.class);
            if (hbaseParams.size() == 0) return;
            for (SYDMGParam sydmgParam : hbaseParams){
                sydmgParam.setDATAID("hbase");
                sydmgParam.setTABLETYPE("4");
                sydmgParam.setLIFECYCL("-1");
                sydmgParam.setTABLECOMMENT(sydmgParam.getTABLENAME());
            }
            /*改为批量入库，如果查询到的数据大于0 ， 先删除数据库表中指定时间的所有数据，然后再将新查询到的数据插入到数据库中*/
            transactionUtil.transaction(s -> {
                int tableType = Integer.valueOf(hbaseParams.get(0).getTABLETYPE());
                int deleteCount = dataMonitorDao.delSyndmgTableAllDataByType(tableType);
                String partitionDate = hbaseParams.get(0).getPARTITIONDATE();
                logger.info("syndmg_table_all【" + partitionDate + "】共删除" + deleteCount + "条hbase数据");
                try {
                    DAOHelper.insertDelList(hbaseParams, dataMonitorDao, "addHbaseDataPatch", 200);
                    logger.info("syndmg_tabble_all【" + partitionDate + "】新插入" + hbaseParams.size() + "条hbase数据");
                } catch (Exception e) {
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            });
        } catch (Exception e) {
            logger.error("获取华为平台资产的数据报错\n", e);
        }
    }

    @Override
    public void getHiveData() {
        if (environment.getProperty("dfWorkUrl") == null) {
            logger.info("dfWorkUrl为空");
            return;
        }
        Map<String, Object> hiveMap;
        List<SYDMGParam> hiveParams;
        List<SYDMGParam> insertDatas = new ArrayList<>();
        String requestDate = DateUtil.addDayStr1(new Date(), -1);   // "yyyy/MM/dd" 格式
        String yesterday = DateUtil.addDayStr(new Date(), -1);      // "yyyyMMdd" 格式
        String todayNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        try {
            String dfworkUrl = environment.getRequiredProperty("dfWorkUrl");
            String dfworkPath = "/df/dfworks/interface/getTableInfo?date=" + requestDate;
            if (dfworkUrl.contains("8080")){
                dfworkPath = "/dfworks/interface/getTableInfo?date=" + requestDate;
            } else if (dfworkUrl.contains("8093")){
                dfworkPath = "/df/dfworks/interface/getTableInfo?date=" + requestDate;
            }
            String url = dfworkUrl + dfworkPath;
            logger.info("请求hive的url地址为：" + url);
            String hiveInterfaceString = restTemplateApi.getForObject(url, String.class);
            hiveMap = JSON.parseObject(hiveInterfaceString);
            logger.info((String) hiveMap.get("returnMessage"));
            String flag = (String) hiveMap.get("returnCode");
            if ("0".equals(flag)) {
                hiveParams = JSON.parseArray(JSON.toJSONString(hiveMap.get("returnValue")), SYDMGParam.class);
//                // 获取数据源（本地仓）
//                JSONArray dataResourceLocal = new JSONArray();
//                String getDataResourceForLocal = rest.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0",String.class);
//                if(StringUtils.isNotBlank(getDataResourceForLocal) && "1".equals(JSONObject.parseObject(getDataResourceForLocal).getString("status"))){
//                    String localData = JSONObject.parseObject(getDataResourceForLocal).getString("data");
//                    dataResourceLocal = JSONArray.parseArray(localData);
//                }
//                // 获取dataid
//                String dataId = "";
//                String dataTypeCode = hiveParams.get(0).getTABLETYPE();
//                for (int i = 0; i< dataResourceLocal.size(); i++){
//                    String dataTypeCk = ((JSONObject) dataResourceLocal.get(i)).getString("resType").toUpperCase();
//                    String dataTypeJk = SysCodeEnum.getNameByCodeAndType(dataTypeCode,"TABLETYPE").toUpperCase();
//                    if (dataTypeCk.contains(dataTypeJk) || dataTypeJk.contains(dataTypeCk)){
//                        dataId = ((JSONObject) dataResourceLocal.get(i)).getString("resId");
//                        break;
//                    }
//                }
                hiveParams.parallelStream().forEach(item -> {
                    item.setDATAID("hive");
                    try {
                        if ("永久".equals(item.getLIFECYCLE())) {
                            item.setLIFECYCLE("-1");
                        } else if (item.getLIFECYCLE().indexOf("天") > 0) {
                            item.setLIFECYCLE(item.getLIFECYCLE().split("天")[0]);
                        } else if (item.getLIFECYCLE().indexOf("年") > 0) {
                            int i = Integer.parseInt(item.getLIFECYCLE().split("年")[0]);
                            item.setLIFECYCLE(String.valueOf(i * 365));
                        }
                        String partitionDateS = item.getPARTITIONDATE();
                        if (item.getISPARTITION().equals("0") && partitionDateS != null) {
                            if (partitionDateS.split("=").length == 2 && StringUtils.isNumeric(partitionDateS.split("=")[1])){
                                String partitionDate = partitionDateS.split("=")[1];
                                if (partitionDate.length() == 8){       // 按天分区
                                    if (yesterday.equals(partitionDate)){
                                        item.setPARTITIONDATE(partitionDate);
                                        insertDatas.add(item);
                                    }
                                }
                                if (partitionDate.length() == 6){       // 按月分区
                                    if (todayNow.substring(6).equals("01")){    // 每个月的第一天特殊处理
                                        // 上个月分区
                                        String lastMonth =  DateUtil.addDayStr(new Date(), -5).substring(0,6);
                                        if (partitionDate.equals(lastMonth)){
                                            item.setPARTITIONDATE(partitionDate);
                                            insertDatas.add(item);
                                        }
                                    }else {
                                        // 本月分区
                                        String thisMonth = todayNow.substring(0,6);
                                        if (partitionDate.equals(thisMonth)){
                                            insertDatas.add(item);
                                        }
                                    }
                                }
                            }
                        }else {
                            item.setPARTITIONDATE(yesterday);
                            insertDatas.add(item);
                        }
//                        dataMonitorDao.addHiveData(item);
                    } catch (Exception e) {
                        logger.error(">>>>>>解析hive数据出错：\n{}", e);
                    }
                });
                // 昨日hive数据
                List<DataResourceTable> yestodayHiveData = dataMonitorDao.getSyndmgTableAllHiveData();
                logger.info("yestodayHiveData：{}", yestodayHiveData.size());
                // 汇总数据量
                for (SYDMGParam data : insertDatas){
                    if (data == null) {
                        continue;
                    }
                    if (data.getTABLEALLCOUNT() != null && !data.getTABLEALLCOUNT().isBlank() && !data.getTABLEALLCOUNT().equalsIgnoreCase("0")){
                        continue;
                    }
                    for (DataResourceTable yestodayData : yestodayHiveData){
                        if (data.getTABLENAME() != null
                                && data.getTABLEPROJECT() != null
                                && yestodayData.getTableName() != null
                                && yestodayData.getProjectName() != null){
                            String tableName = data.getTABLENAME();
                            String ytableName = yestodayData.getTableName();
                            String tableProject = data.getTABLEPROJECT();
                            String ytableProject = yestodayData.getProjectName();
                            if (tableName.equalsIgnoreCase(ytableName) && tableProject.equalsIgnoreCase(ytableProject)){
                                long yestodayTableCount = yestodayData.getPartionCount() != null ? Long.parseLong(yestodayData.getPartionCount()) : 0;
                                long partitionCount = data.getPARTITIONCOUNT() != null ? Long.parseLong(data.getPARTITIONCOUNT()) : 0;
                                data.setTABLEALLCOUNT(String.valueOf(yestodayTableCount + partitionCount));
                            }
                        }
                    }
                }

                // 批量插入数据
                /*改为批量入库，如果查询到的数据大于0 ， 先删除数据库表中指定时间的所有数据，然后再将新查询到的数据插入到数据库中*/
                transactionUtil.transaction(s -> {
                    if (insertDatas.size() > 0) {
                        int tableType = Integer.valueOf(insertDatas.get(0).getTABLETYPE());
                        logger.info("hive平台类型：" + tableType);
                        int deleteCount = dataMonitorDao.delSyndmgTableAllDataByType(tableType);
                        logger.info("syndmg_tabble_all【" + todayNow + "】共删除" + deleteCount + "条hive数据");
                        try {
                            DAOHelper.insertDelList(insertDatas, dataMonitorDao, "addHiveDataPatch", 200);
                            logger.info("syndmg_tabble_all【" + todayNow + "】新插入" + insertDatas.size() + "条hive数据");
                        } catch (Exception e) {
                            logger.error("插入hive数据出错：\n{}", e);
                        }
                    } else {
                        logger.info("hive【" + todayNow + "】查询到的数据量为0");
                    }
                });
                logger.info("开始删除hive的过期数据");
                insertDatas.stream().forEach(sydmgParam -> {
                    delHisData(sydmgParam);
                });

            }
        } catch (Exception e) {
            logger.error("获取华为平台资产的数据报错:\n{}", e);
        }
    }

    // 删除HIVE历史数据
    public void delHisData(SYDMGParam sydmgParam) {
        long minDelDate = 0;
        if (sydmgParam.getISPARTITION().equalsIgnoreCase("0")
                && sydmgParam.getLIFECYCLE() != null
                && !sydmgParam.getLIFECYCLE().equalsIgnoreCase("-1")){

            int lifeCycle = Integer.parseInt(sydmgParam.getLIFECYCLE());
            if (lifeCycle > 0) {
                minDelDate = dateBefore(lifeCycle);    // 当前日期减去生命周期天数
            } else {
                minDelDate = dateBefore(3600);      // 当前日期减去生命周期天数（如果没匹配到，默认3600）
            }
            dataMonitorDao.delHiveHisData(sydmgParam, minDelDate);
        }
    }

    public static long dateBefore(int i) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -i);//
        long d = Integer.parseInt(sdf.format(calendar.getTime()));
        return d;
    }

    @Override
    public void getClickhouseData() {
        List<Object> resIds = getResIdByDatabaseType("clickhouse");
        if (resIds.isEmpty()) {
            return;
        }
        String todayNow = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        String yesterday = DateUtil.addDayStr(new Date(), -1);
        String ckDataSql = buildSqlQuery();
        /**
         * 这个接口获取的是所有的分区和未分区数据，和hive，hbase的不同，且有列名做分区名
         */
        JSONArray jsonArrayAll = new JSONArray();
        for (Object object : resIds){
            try {
                String resId = JSONObject.parseObject(object.toString()).getString("resId");
                JSONArray jsonArray = restTemplateHandle.excuteSql(resId, ckDataSql);
                jsonArrayAll.addAll(jsonArray);
                jsonArrayAll = removeDuplicates(jsonArrayAll);
                List<SYDMGParam> clickhouseParams = JSON.parseArray(JSON.toJSONString(jsonArrayAll), SYDMGParam.class);

                if (!clickhouseParams.isEmpty()) {
                    List<SYDMGParam> filteredData = processClickhouseData(clickhouseParams, todayNow, yesterday);
                    saveCkToDatabase(filteredData, todayNow);
                } else {
                    logger.info("clickhouse【" + todayNow + "】查询到的数据量为0");
                }
            } catch (Exception e) {
                logger.error("获取clickhouse的数据报错\n" + ExceptionUtil.getExceptionTrace(e));
            }
        }
    }

    public static JSONArray removeDuplicates(JSONArray jsonArray) {
        Set<String> seen = new HashSet<>();
        JSONArray result = new JSONArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String objStr = obj.toString();

            if (!seen.contains(objStr)) {
                seen.add(objStr);
                result.add(obj);
            }
        }
        return result;
    }
    public static List<Object> removeDuplicatesByName(List<Object> list, String key) {
        Set<String> seen = new LinkedHashSet<>();
        List<Object> result = new ArrayList<>();
        for (Object obj : list) {
            if (seen.add(JSONObject.parseObject(obj.toString()).getString(key))) {
                result.add(obj);
            }
        }
        return result;
    }

    private String buildSqlQuery() {
        return "select TABLEPROJECT,\n" +
                "       TABLENAME,\n" +
                "       PARTITIONDATE,\n" +
                "       PARTITIONCOUNT,\n" +
                "       PARTITIONSIZE,\n" +
                "       TABLEALLCOUNT,\n" +
                "       TABLESIZE\n" +
                "  from (select database as TABLEPROJECT,\n" +
                "               table as TABLENAME,\n" +
                "               partition as PARTITIONDATE,\n" +
                "               sum(rows) as PARTITIONCOUNT,\n" +
                "               sum(bytes_on_disk) as PARTITIONSIZE\n" +
                "          from cluster(ck_cluster, system, parts)\n" +
                "         where table not like '.inner%'\n" +
                "           and active = 1\n" +
                "         group by database, table, partition) table_part\n" +
                " inner join (select database as TABLEPROJECT,\n" +
                "                    table as TABLENAME,\n" +
                "                    sum(rows) as TABLEALLCOUNT,\n" +
                "                    sum(bytes_on_disk) as TABLESIZE\n" +
                "               from cluster(ck_cluster, system, parts)\n" +
                "              where table not like '.inner%'\n" +
                "                and active = 1\n" +
                "              group by database, table) table_total\n" +
                "    on table_part.TABLENAME = table_total.TABLENAME\n" +
                "   and table_part.TABLEPROJECT = table_total.TABLEPROJECT";
    }
    private List<SYDMGParam> processClickhouseData(List<SYDMGParam> clickhouseParams, String todayNow, String yesterday) {
        return clickhouseParams.stream().map(item -> {
            item.setDATAID("clickhouse");
            item.setTABLECOMMENT("");
            item.setLIFECYCL("-1");
            if (item.getTABLENAME().toLowerCase().endsWith("_local")) {
                item.setTABLENAME(item.getTABLENAME().substring(0, item.getTABLENAME().length() - 6));
            }
            String partitionDate = item.getPARTITIONDATE();
            if (partitionDate != null && partitionDate != "" && !"tuple()".equals(partitionDate)) {
                item.setISPARTITION("0");
            } else {
                item.setISPARTITION("1");
            }
            if ("0".equals(item.getISPARTITION())) {
                if (partitionDate.length() == 8 && yesterday.equals(partitionDate)) {
                    return item;
                }
                if (partitionDate.length() == 6) {
                    if (todayNow.substring(6).equals("01")) {
                        String lastMonth = DateUtil.addDayStr(new Date(), -5).substring(0, 6);
                        if (partitionDate.equals(lastMonth)) {
                            return item;
                        }
                    } else {
                        String thisMonth = todayNow.substring(0, 6);
                        if (partitionDate.equals(thisMonth)) {
                            return item;
                        }
                    }
                }
                // 判断是否日期格式
                if (DateUtil.isValidDate(partitionDate, DateUtil.DEFAULT_PATTERN_DATETIME)){
                    try {
                        String dateStr = DateUtil.formatDate(DateUtil.parseDateTime(partitionDate), DateUtil.DEFAULT_PATTERN_DATETIME);
                        if (yesterday.equals(dateStr)){
                            item.setPARTITIONDATE(dateStr);
                            return item;
                        }
                    } catch (ParseException e) {
                        return null;
                    }
                }

            } else {
                item.setPARTITIONDATE(todayNow);
                return item;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
    private void saveCkToDatabase(List<SYDMGParam> data, String todayNow) {
        /*改为批量入库，如果查询到的数据大于0 ， 先删除数据库表中指定时间的所有数据，然后再将新查询到的数据插入到数据库中*/
        transactionUtil.transaction(s -> {
            int deleteCount = dataMonitorDao.delSyndmgTableAllDataByType(7);
            logger.info("syndmg_tabble_all【" + todayNow + "】共删除" + deleteCount + "条clickhouse数据");
            try {
                DAOHelper.insertDelList(data, dataMonitorDao, "addClickHouseDataPatch", 200);
                logger.info("syndmg_tabble_all【" + todayNow + "】新插入" + data.size() + "条clickhouse数据");
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
        });
    }

    /**
     * 将统计信息插入到数据库中
     *
     * @param tableAllCount
     * @param operationLog
     * @param oneday
     */
    private void insertUpdateoperationLog(long tableAllCount, OperationLog operationLog, Date oneday, String typeName) {
        try {
            if (operationLog == null) {
                OperationLog log = new OperationLog();
                log.setId(UUID.randomUUID().toString().replace("-", ""));
                log.setLogName(typeName);
                log.setLogTime(oneday);
                log.setDataCount(tableAllCount);
                operatorLogDao.insertOperationLog(log);
                logger.info(typeName + "日志记录插入结束。");
            } else {
                operationLog.setLogTime(oneday);
                operationLog.setDataCount(tableAllCount);
                operatorLogDao.updateOperateLog(operationLog);
                logger.info(typeName + "日志记录更新结束。");
            }
        } catch (Exception e) {
            logger.error(typeName + "日志记录更新报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 判断该条数据是否正常
     * 如果是分区表：判断昨日分区数据是否为0，是：数据异常 如果昨日分区数据比平均数据量波动超过30%，表示异常
     * 如果是全量：如果表总数比平均数据量波动超过30%，表示异常
     * <p>
     * 异常信息直接注入到对象中
     *
     * @param oneTableOrganizationData
     * @param setting
     * @return 正常/异常
     */
    private String checkStatusData(TableOrganizationData oneTableOrganizationData, OrganizationAlarmSetting setting) {
        String message = "正常";
        if (setting != null) {
            try {
                // 在全量中昨日分区的数据量也是数据总量
                BigInteger zero = new BigInteger("0");
                BigInteger yesterdayCount = oneTableOrganizationData.getYesterdayCount() != null ? oneTableOrganizationData.getYesterdayCount() : zero;
                // 分区表判断
                if ("0".equalsIgnoreCase(oneTableOrganizationData.getPartitionMessage())) {
                    if ((yesterdayCount == null || yesterdayCount.compareTo(zero) == 0) && setting.getNoDataCheck().equalsIgnoreCase("true")) {
                        message = "异常";
                        oneTableOrganizationData.setExceptionMessage("昨日无数据");
                        oneTableOrganizationData.setAlarmLevel(setting.getNoDataAlarmLevel());
                        return message;
                    }
                    if (setting.getFluctuateCheck().equalsIgnoreCase("true")) {
                        BigInteger averageDailyVolume = oneTableOrganizationData.getAverageDailyVolume() != null ? oneTableOrganizationData.getAverageDailyVolume() : zero;
                        //  判断两个值的环比
                        Double value = DataCheck.divideSequential(yesterdayCount, averageDailyVolume, 4);
                        if (value >= Integer.parseInt(setting.getFluctuateRate()) || value <= -Integer.parseInt(setting.getFluctuateRate())) {
                            message = "异常";
                            oneTableOrganizationData.setExceptionMessage("数据波动异常");
                            oneTableOrganizationData.setAlarmLevel(setting.getFluctuateAlarmLevel());
                            return message;
                        }
                    }
                } else if ("1".equalsIgnoreCase(oneTableOrganizationData.getPartitionMessage())) {
                    if (yesterdayCount.compareTo(zero) == 0 && setting.getNoDataCheck().equalsIgnoreCase("true")) {
                        message = "异常";
                        oneTableOrganizationData.setExceptionMessage("昨日无数据");
                        oneTableOrganizationData.setAlarmLevel(setting.getNoDataAlarmLevel());
                        return message;
                    }
                    if (setting.getFluctuateCheck().equalsIgnoreCase("true")) {
                        BigInteger averageDailyVolume = oneTableOrganizationData.getAverageDailyVolume() != null ? oneTableOrganizationData.getAverageDailyVolume() : zero;
                        //  判断两个值的环比
                        double value = DataCheck.divideSequential(yesterdayCount, averageDailyVolume, 4);
                        if (value >= Integer.parseInt(setting.getFluctuateRate()) || value <= -Integer.parseInt(setting.getFluctuateRate())) {
                            message = "异常";
                            oneTableOrganizationData.setExceptionMessage("数据波动异常");
                            oneTableOrganizationData.setAlarmLevel(setting.getFluctuateAlarmLevel());
                            return message;
                        }
                    }
                } else {
                    message = "正常";
                    return message;
                }
            } catch (Exception e) {
                message = "异常";
                oneTableOrganizationData.setExceptionMessage("数据量格式异常");
                logger.error("判断数据是否异常报错" + ExceptionUtil.getExceptionTrace(e));
                return message;
            }
        }
        return message;

    }

    @Override
    public void deleteOverTimeAssets() {
        // 改为从数据库获取表组织数据保留天数
        OrganizationAlarmSetting setting = getAlarmSetting();
        String assetsStoreDays = setting.getAssetsStoreDays();
        if (StringUtils.isBlank(assetsStoreDays)) {
            assetsStoreDays = "90";
            logger.info("从数据库里获取到天数为空，赋默认值90");
        }
        dataMonitorDao.deleteOverTimeAssets(assetsStoreDays);
    }

}
