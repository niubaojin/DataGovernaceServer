package com.synway.reconciliation.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.synway.reconciliation.common.ErrorCode;
import com.synway.reconciliation.common.SystemException;
import com.synway.reconciliation.conditional.NonIssueCondition;
import com.synway.reconciliation.dao.InventoryReconDao;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.service.InventoryReconService;
import com.synway.reconciliation.util.DateParamUtil;
import com.synway.reconciliation.util.DateUtil;
import com.synway.reconciliation.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@Service
@Conditional(NonIssueCondition.class)
public class InventoryReconServiceImpl implements InventoryReconService {

    @Autowired
    private InventoryReconDao inventoryReconDao;

    @Autowired
    private DruidDataSource oracleDataSource;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Override
    public Map<String,Object> getReconciliationStatisticsList(LinkStatisticRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        List<BillLinkStatistics> statisticsList = inventoryReconDao.getReconciliationStatisticsList(request);
        for (BillLinkStatistics billLinkStatistics : statisticsList) {
            String dataTime = billLinkStatistics.getDataTime();
            String statisticTime = billLinkStatistics.getStatisticTime();
            if (!StringUtils.isEmpty(dataTime)) {
                billLinkStatistics.setDataTime(dataTime.substring(0, 19));
            }
            if (!StringUtils.isEmpty(statisticTime)) {
                billLinkStatistics.setStatisticTime(statisticTime.substring(0, 19));
            }
        }
        resultMap.put("total", statisticsList.size());
        resultMap.put("rows", statisticsList);
        // 有数据的日期获取
        List<String> dataTimes = inventoryReconDao.getReconciliationStatisticsDataTimeList();
        List<String> dataTimeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataTimes)) {
            dataTimeList =  dataTimes.stream().distinct().map(t -> t.substring(0, 19)).collect(Collectors.toList());
        }
        resultMap.put("dataTimeList", dataTimeList);
        return resultMap;
    }

    @Override
    public List<DayStatisticsResponse> getRecentDailyStatisticsList(LinkStatisticRequest request) {
        // 处理时间
        String resourceId = request.getResourceId();
        String dataTime = request.getDataTime();
        dataTime = dataTime.substring(0, 10);
        List<BillLinkStatistics> statisticsList = inventoryReconDao.getRecentDailyStatisticsList(resourceId, dataTime);
        return handleStatisticsList(statisticsList);
    }

    @Override
    public Map<String,Object> getReconciliationAnalysisList(ReconAnalysisRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        if (pageNum > 0 && pageSize > 0 ) {
            request.setPageNum((pageNum - 1) * pageSize);
            request.setPageSize(pageSize * pageNum);
        }
        List<ReconciliationAnalysis> reconciliationAnalysisList = inventoryReconDao.getReconciliationAnalysisList(request);
        int total = inventoryReconDao.countReconciliationAnalysis(request);
        resultMap.put("total", total);
        resultMap.put("rows", reconciliationAnalysisList);
        return resultMap;
    }

    @Override
    public Map<String, Object> getReconciliationList(ReconInfoRequest request) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String dataTime = request.getSCSJ_RQSJ();
        Date date = DateUtil.parseDate(dataTime);
        int startTime = DateParamUtil.getDayStartInt(date);
        int endTime = DateParamUtil.getDayEndInt(date);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        //处理参数
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        if (pageNum > 0 && pageSize > 0 ) {
            request.setPageNum((pageNum - 1) * pageSize);
            request.setPageSize(pageSize * pageNum);
        }

        List<ReconInfoResponse> reconciliationList = inventoryReconDao.getReconciliationList(request);
        // 处理时间
        for (ReconInfoResponse reconInfoResponse : reconciliationList) {
            if (reconInfoResponse == null) {
                continue;
            }
            if (!StringUtils.isEmpty(reconInfoResponse.getSCSJ_RQSJ())) {
                Date riseTime = new Date(Long.parseLong(reconInfoResponse.getSCSJ_RQSJ()) * 1000);
                reconInfoResponse.setSCSJ_RQSJ(DateUtil.formatDate(riseTime, DateUtil.DEFAULT_PATTERN_DATETIME));
            }
            if (!StringUtils.isEmpty(reconInfoResponse.getDZSJ_RQSJ())) {
                Date reconTime = new Date(Long.parseLong(reconInfoResponse.getDZSJ_RQSJ()) * 1000);
                reconInfoResponse.setDZSJ_RQSJ(DateUtil.formatDate(reconTime, DateUtil.DEFAULT_PATTERN_DATETIME));
            }
        }
        int total = inventoryReconDao.countReconciliationList(request);
        resultMap.put("total", total);
        resultMap.put("rows", reconciliationList);
        return resultMap;
    }

    @Override
    public Map<String, Object> getReconciliationDetail(ReconInfoRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        String SJJRF_DZDBH = request.getSJJRF_DZDBH();
        DacAcceptorBill acceptorBill = null;
        DacProviderBill providerBill = null;
        List<DacAcceptorBill> acceptorBills = inventoryReconDao.getAcceptorByNo(SJJRF_DZDBH);
        if (!CollectionUtils.isEmpty(acceptorBills)) {
            acceptorBill = acceptorBills.get(0);
            if (acceptorBill != null) {
                if (!StringUtils.isEmpty(acceptorBill.getDZSJ_RQSJ())) {
                    Date reconTime = new Date(Long.parseLong(acceptorBill.getDZSJ_RQSJ()) * 1000);
                    acceptorBill.setDZSJ_RQSJ(DateUtil.formatDate(reconTime, DateUtil.DEFAULT_PATTERN_DATETIME));
                }
                if (!StringUtils.isEmpty(acceptorBill.getSCSJ_RQSJ())) {
                    Date riseTime = new Date(Long.parseLong(acceptorBill.getSCSJ_RQSJ()) * 1000);
                    acceptorBill.setSCSJ_RQSJ(DateUtil.formatDate(riseTime, DateUtil.DEFAULT_PATTERN_DATETIME));
                }
                String SJTGF_DZDBH = acceptorBill.getSJTGF_DZDBH();
                if (!StringUtils.isEmpty(SJTGF_DZDBH)) {
                    List<DacProviderBill> providerBills = inventoryReconDao.getProviderByNo(SJTGF_DZDBH);
                    if (!CollectionUtils.isEmpty(providerBills)) {
                        providerBill = providerBills.get(0);
                        if (providerBill != null && !StringUtils.isEmpty(acceptorBill.getSCSJ_RQSJ())) {
                            Date riseTime = new Date(Long.parseLong(providerBill.getSCSJ_RQSJ()) * 1000);
                            providerBill.setSCSJ_RQSJ(DateUtil.formatDate(riseTime, DateUtil.DEFAULT_PATTERN_DATETIME));
                        }
                    }
                }
            }
        }
        resultMap.put("acceptorBill", acceptorBill);
        resultMap.put("providerBill", providerBill);
        return resultMap;
    }

    @Override
    public void summarizeBillByInstance() {
        // 暂时汇总前两个小时的
        Calendar calendar = Calendar.getInstance();
        int endDate = (int) (calendar.getTime().getTime() / 1000);
        calendar.add(Calendar.HOUR, -2);
        int startDate = (int) (calendar.getTime().getTime() / 1000);
        inventoryReconDao.summarizeStorageProvider(startDate, endDate);
        inventoryReconDao.summarizeStorageAcceptor(startDate, endDate);
        inventoryReconDao.summarizeStandardProvider(startDate, endDate);
        inventoryReconDao.summarizeStandardAcceptor(startDate, endDate);
        inventoryReconDao.summarizeAccessProvider(startDate, endDate);
        inventoryReconDao.summarizeAccessAcceptor(startDate, endDate);
    }

    @Override
    public void reconByInstance() {
        // 对账近一天内 未对账和历史对账失败的  按实例（inceptDataTime和resourceId）
        Calendar calendar = Calendar.getInstance();
        int endDate = (int) (calendar.getTime().getTime() / 1000);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        int startDate = (int) (calendar.getTime().getTime() / 1000);
        ReconParam reconParam = new ReconParam();
        reconParam.setStartDate(startDate);
        reconParam.setEndDate(endDate);
        reconParam.setDetail(false);
        // 对账入库接入方和标准化提供方
        reconParam.setDZZDHJ(3);
        reconByType(reconParam);
        // 对账标准化接入方和接入提供方
        reconParam.setDZZDHJ(2);
        reconByType(reconParam);
    }

    @Override
    public void updateOneRecon(ReconParam reconParam) {
        if (StringUtils.isEmpty(reconParam.getXXRWBH()) || StringUtils.isEmpty(reconParam.getBZSJXJBM())) {
            log.error("对账字段inceptDataTime或标准数据项集编码为空");
        }
        // 刷新某条对账 共用一个方法  值由前端传递
        // 确保startDate为空
        reconParam.setStartDate(0);
        reconParam.setDetail(false);
        reconByType(reconParam);
    }

    @Override
    public List<ReconInfoResponse> dataPacketRecon(ReconParam reconParam) {
        if (Objects.isNull(reconParam) || StringUtils.isEmpty(reconParam.getBZSJXJBM()) || StringUtils.isEmpty(reconParam.getXXRWBH())) {
            log.error("数据包对账传递的参数为空");
        }
        // 先根据 inceptDataTime、resourceId、type
        // 获取原始表的账单  单位是数据包
        reconParam.setDetail(true);
        List<ReconInfoResponse> dataPacketReconList = inventoryReconDao.getDataPacketReconList(reconParam);
        if (!CollectionUtils.isEmpty(dataPacketReconList)) {
            return dataPacketReconList;
        }
        // 构建账单
        List<InventoryBill> inventoryBills = reconByType(reconParam);

        return null;
    }

    @Override
    public void inventoryRecon() {
        //汇总近两天或三天 根据 resourceId 从  DAC_PROVIDER_BILL 和 DAC_ACCEPTOR_BILL
        Calendar calendar = Calendar.getInstance();
        int startDate = DateParamUtil.getDayStartInt(calendar.getTime());
        int endDate = DateParamUtil.getDayEndInt(calendar.getTime());
        // 先删除 后插入
        Connection connection = null;
        try {
            connection = oracleDataSource.getConnection();
            // 生成盘点对账单
            inventoryBill(connection, startDate, endDate, 1);
            inventoryBill(connection, startDate, endDate, 2);
            // 对账

        } catch (Exception ex) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception e) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e);
            }
            log.error(ex.toString());
            if (ex instanceof SystemException) {
                throw (SystemException)ex;
            } else {
                throw SystemException.asSystemException(ErrorCode.DB_CONNECT_ERROR, ex);
            }
        } finally {
            if(connection != null){
                try{
                    connection.commit();
                    connection.close();
                } catch (Exception ignored){

                }
            }
        }

    }

    @Override
    public void reconAnalysis() {
        try {
            Calendar calendar = Calendar.getInstance();
            int startDate = DateParamUtil.getDayStartInt(calendar.getTime());
            int endDate = DateParamUtil.getDayEndInt(calendar.getTime());
            List<ReconciliationAnalysis> reconAnalyses = new ArrayList<>();
            Date checkTime = new Date(startDate * 1000L);
            List<ReconciliationAnalysis> standardAnalyses = inventoryReconDao.reconAnalysis(2, startDate, endDate);
            List<ReconciliationAnalysis> storageAnalyses = inventoryReconDao.reconAnalysis(3, startDate, endDate);
            if (!CollectionUtils.isEmpty(standardAnalyses)) {
                for (ReconciliationAnalysis standardAnalysis : standardAnalyses) {
                    standardAnalysis.setSuccessPercent((standardAnalysis.getSuccessCount() * 1.0 / standardAnalysis.getSumCount() * 100));
                    standardAnalysis.setBillType(2);
                    standardAnalysis.setCheckTime(checkTime);
                    reconAnalyses.add(standardAnalysis);
                }
            }
            if (!CollectionUtils.isEmpty(storageAnalyses)) {
                for (ReconciliationAnalysis storageAnalysis : storageAnalyses) {
                    storageAnalysis.setSuccessPercent( (storageAnalysis.getSuccessCount() * 1.0 / storageAnalysis.getSumCount()) * 100);
                    storageAnalysis.setBillType(2);
                    storageAnalysis.setCheckTime(checkTime);
                    reconAnalyses.add(storageAnalysis);
                }
            }
            updateReconAnalysis(reconAnalyses, checkTime);
        } catch (Exception e) {
            log.error("进行对账分析时报错："  + e.getMessage());
        }
    }

    @Override
    public void reconStatistics() {
        try {
            //获取血缘关系
            Map<String, String> resourceMap = Maps.newHashMap();
            List<QueryBloodlineRelationInfo> relationInfos = restTemplateHandle.getAllStandardBlood();
            if (!CollectionUtils.isEmpty(relationInfos)) {
                resourceMap = relationInfos.stream().
                        collect(Collectors.toMap(QueryBloodlineRelationInfo::getSourceEngName, QueryBloodlineRelationInfo::getTargetEngName, (old, newValue) -> newValue));
            }

            // 统计
            Calendar calendar = Calendar.getInstance();
            int dateTime = DateParamUtil.getDayStartInt(calendar.getTime());
            // 因为 标准化提供方和接收方 协议编码不同 需要分开统计后整合
            // 接入统计     包含 接入接收方、接入提供方、标准化接收方
            List<BillLinkStatistics> billStatistics = inventoryReconDao.statisticsAccessDataBill(dateTime);
            // 标准化统计   包含 标准化提供方、入库接收方、入库提供方
            List<BillLinkStatistics> standardStatisticsList = inventoryReconDao.statisticsStandardDataBill(dateTime);
            Map<String, List<BillLinkStatistics>> standardStatisticsMap = standardStatisticsList.stream().collect(Collectors.groupingBy(BillLinkStatistics::getResourceId));

            // 获取前一天的接入数据
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            int dateTimePre = DateParamUtil.getDayStartInt(calendar.getTime());
            List<BillLinkStatistics> billLinkStatistics = inventoryReconDao.statisticsAccessDataBill(dateTimePre);
            Map<String, List<BillLinkStatistics>> linkStatisticsPreMap = billLinkStatistics.stream().filter(Objects::nonNull).filter(t -> !StringUtils.isEmpty(t.getResourceId())).collect(Collectors.groupingBy(BillLinkStatistics::getResourceId));

            // 统计逻辑 包含整合标准化链路数据
            for (BillLinkStatistics billStatistic : billStatistics) {

                String resourceId = billStatistic.getResourceId();

                // 整合标准化统计
                String targetResourceId = resourceMap.get(resourceId);
                if (!StringUtils.isEmpty(targetResourceId)) {
                    if (standardStatisticsMap.containsKey(targetResourceId)) {
                        BillLinkStatistics standardStatistics = standardStatisticsMap.get(targetResourceId).get(0);
                        if (standardStatistics != null) {
                            billStatistic.setProStandardCount(standardStatistics.getProStandardCount());
                            billStatistic.setAccStorageCount(standardStatistics.getAccStorageCount());
                            billStatistic.setProStorageCount(standardStatistics.getProStorageCount());
                        }
                    }
                }

                // 计算接入同比  如果昨天有接入数据的话
                BillLinkStatistics billLinkStatisticsPre = null;
                double accessRate = 0;
                String accessRateStr = null;
                if (linkStatisticsPreMap.containsKey(resourceId)) {
                    billLinkStatisticsPre = linkStatisticsPreMap.get(resourceId).get(0);
                    billStatistic.setAccAccessCountPre(billLinkStatisticsPre.getAccAccessCount());
                    billStatistic.setProAccessCountPre(billLinkStatisticsPre.getProAccessCount());
                    accessRate = billStatistic.getProAccessCount() == 0 ? 0 : (Math.round(10000 * (billStatistic.getProAccessCount() - billStatistic.getProAccessCountPre()) / billStatistic.getProAccessCount()) / 10000.0) * 100;
                    accessRateStr = String.format("%.2f", accessRate);
                    billStatistic.setAccessSamePeriod(accessRateStr);
                }
                // 标准化环比
                double standardRate = 0;
                String standardRateStr = null;
                standardRate = billStatistic.getProAccessCount() == 0 || billStatistic.getProStandardCount() == 0? 0 : (Math.round(10000 * (billStatistic.getProStandardCount() - billStatistic.getProAccessCount()) / billStatistic.getProStandardCount()) / 10000.0) * 100;
                standardRateStr = String.format("%.2f", standardRate);
                billStatistic.setStandardChainRatio(standardRateStr);
                // 入库环比
                double storageRate = 0;
                String storageRateStr = null;
                storageRate = billStatistic.getProStandardCount() == 0 || billStatistic.getProStorageCount() == 0? 0 : (Math.round(10000 * (billStatistic.getProStorageCount() - billStatistic.getProStandardCount()) / billStatistic.getProStorageCount()) / 10000.0) * 100;
                storageRateStr = String.format("%.2f", storageRate);
                billStatistic.setStorageChainRatio(storageRateStr);
                billStatistic.setIsPush(2);
                // 是否异常
                if ("100.00".equalsIgnoreCase(accessRateStr) && "100.00".equalsIgnoreCase(standardRateStr) && "100.00".equalsIgnoreCase(storageRateStr)) {
                    billStatistic.setAlarmState(1);
                } else {
                    billStatistic.setAlarmState(2);
                }

            }

            // 更新统计表  先删后插入
            updateBillLinkStatistics(dateTime, billStatistics);
        } catch (Exception e) {
            log.error("进行对账统计时报错："  + e.getMessage());
        }

    }

    @Override
    public void pushAbnormalData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String dateTime = DateParamUtil.getDayStart(calendar.getTime());
        List<BillLinkStatistics> needPushLinkStatistics = new ArrayList<>();
        List<BillLinkStatistics> linkStatistics = inventoryReconDao.getLinkStatistics(dateTime);
        for (BillLinkStatistics linkStatistic : linkStatistics) {

        }
    }

    @Override
    public void getSummarizeBill(SummarizeBillRequest request) {
        // 根据类型获取汇总数据 1.接入2.标准化3.入库 //return data to 前端
        List<SummarizeBillResponse> summarizeBill = inventoryReconDao.getSummarizeBill(request);
    }

    @Override
    public Map<String, Object> getAbnormalReconciliationNum() {
        Map<String, Object> map = new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        String dataTime = DateParamUtil.getDayStart(c.getTime());
        int total = inventoryReconDao.getAbnormalReconciliationList(dataTime);
        map.put("total", total);
        map.put("statisticTime", DateUtil.formatDate(c.getTime()));
        return map;
    }

    @Override
    public List<String> getDataTimeList(ReconInfoRequest request) {
        int DZZDHJ = request.getDZZDHJ();
        List<String> dataTimeList = inventoryReconDao.getDataTimeList(DZZDHJ);
        List<String> resultList = new ArrayList<>();
        if (dataTimeList != null && !CollectionUtils.isEmpty(dataTimeList)) {
            for (String data : dataTimeList) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(data)) {
                    String dateString = DateUtil.formatDate(new Date(Long.parseLong(data) * 1000), DateUtil.DEFAULT_PATTERN_DATE);
                    resultList.add(dateString);
                }
            }
        }
        return resultList;
    }

    private List<DayStatisticsResponse> handleStatisticsList(List<BillLinkStatistics> billLinkStatistics) {
        List<DayStatisticsResponse> resultList = new ArrayList<>();
        List<DayStatisticsResponse> accessList = new ArrayList<>();
        List<DayStatisticsResponse> standardList = new ArrayList<>();
        List<DayStatisticsResponse> storageList = new ArrayList<>();
        if (CollectionUtils.isEmpty(billLinkStatistics)) {
            return resultList;
        }
        for (BillLinkStatistics response : billLinkStatistics) {
            DayStatisticsResponse access = new DayStatisticsResponse();
            access.setProviderCount(response.getProAccessCount());
            access.setLinkType("接入");
            access.setDataTime(response.getDataTime());
            accessList.add(access);
            DayStatisticsResponse standard = new DayStatisticsResponse();
            standard.setProviderCount(response.getProStandardCount());
            standard.setLinkType("处理");
            standard.setDataTime(response.getDataTime());
            standardList.add(standard);
            DayStatisticsResponse storage = new DayStatisticsResponse();
            storage.setProviderCount(response.getProStorageCount());
            storage.setLinkType("入库");
            storage.setDataTime(response.getDataTime());
            storageList.add(storage);
        }
        resultList.addAll(accessList);
        resultList.addAll(standardList);
        resultList.addAll(storageList);
        return resultList;
    }

    private void updateReconAnalysis(List<ReconciliationAnalysis> reconAnalyses, Date checkTime) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        PreparedStatement deleteStatement = null;
        try{
            connection = oracleDataSource.getConnection();
            String deleteSql = "delete from DAC_RECONCILIATION_ANALYSIS where CHECK_TIME = ?";
            String insertSql = "insert into DAC_RECONCILIATION_ANALYSIS(RESOURCE_ID,RESOURCE_NAME,LINK_TYPE,BILL_TYPE,CHECK_TIME,SUM_COUNT,SUCCESS_COUNT,SUCCESS_PERCENT,FAILD_COUNT,CREATE_TIME) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
            deleteStatement = connection.prepareStatement(deleteSql);
            insertStatement = connection.prepareStatement(insertSql);

            // 先删除
            deleteStatement.setTimestamp(1, new Timestamp(checkTime.getTime()));
            deleteStatement.executeUpdate();

            // 后插入
            int count = 0;
            for (ReconciliationAnalysis entry : reconAnalyses) {
                insertStatement.setString(1, entry.getResourceId());
                insertStatement.setString(2, entry.getResourceName());
                insertStatement.setInt(3, entry.getLinkType());
                insertStatement.setInt(4, entry.getBillType());
                insertStatement.setTimestamp(5, new Timestamp(entry.getCheckTime().getTime()));
                insertStatement.setLong(6, entry.getSumCount());
                insertStatement.setLong(7, entry.getSuccessCount());
                insertStatement.setDouble(8, entry.getSuccessPercent());
                insertStatement.setLong(9, entry.getFailCount());
                insertStatement.addBatch();
                count++;
                if (count % 1000 == 0) {
                    insertStatement.executeBatch();
                }
            }
            insertStatement.executeBatch();
            connection.commit();
        }catch (SQLException e){
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                if (insertStatement != null){
                    insertStatement.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    private void updateBillLinkStatistics(int dateTime, List<BillLinkStatistics> billStatistics) {

        Connection connection = null;
        PreparedStatement insertStatement = null;
        PreparedStatement deleteStatement = null;
        if (!CollectionUtils.isEmpty(billStatistics)) {
            try {
                connection = oracleDataSource.getConnection();
                String deleteSQLString = "delete from DAC_BILL_LINK_STATISTIC WHERE DATA_TIME = ?";
                String insertSQLString = "INSERT INTO DAC_BILL_LINK_STATISTIC(RESOURCE_ID,RESOURCE_NAME,ACCESS_ACCEPTOR,ACCESS_PROVIDER,STANDARD_ACCEPTOR,STANDARD_PROVIDER," +
                        "STORAGE_ACCEPTOR,STORAGE_PROVIDER,ACCESS_SAME_PERIOD,STANDARD_CHAIN_RATIO,STORAGE_CHAIN_RATIO,DATA_TIME,STATISTIC_TIME,ALARM_STATE,IS_PUSH,ACCESS_ACCEPTOR_PRE," +
                        "ACCESS_PROVIDER_PRE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                connection.setAutoCommit(false);
                // 先删除
                try {
                    deleteStatement = connection.prepareStatement(deleteSQLString);
                    deleteStatement.setTimestamp(1, new Timestamp(dateTime * 1000L));
                    deleteStatement.executeUpdate();
                } catch (Exception e) {
                    throw new SystemException(ErrorCode.DATA_DELETE_ERROR, e.getMessage());
                }
                // 后插入
                insertStatement = connection.prepareStatement(insertSQLString);
                int count = 0;
                for (BillLinkStatistics billStatistic : billStatistics) {
                    insertStatement.setString(1, billStatistic.getResourceId());
                    insertStatement.setString(2, billStatistic.getResourceName());
                    insertStatement.setInt(3, billStatistic.getAccAccessCount());
                    insertStatement.setInt(4, billStatistic.getProAccessCount());
                    insertStatement.setInt(5, billStatistic.getAccStandardCount());
                    insertStatement.setInt(6, billStatistic.getProStandardCount());
                    insertStatement.setInt(7, billStatistic.getAccStorageCount());
                    insertStatement.setInt(8, billStatistic.getProStorageCount());
                    insertStatement.setString(9, billStatistic.getAccessSamePeriod());
                    insertStatement.setString(10, billStatistic.getStandardChainRatio());
                    insertStatement.setString(11, billStatistic.getStorageChainRatio());
                    insertStatement.setTimestamp(12, new Timestamp(dateTime * 1000L));
                    insertStatement.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
                    insertStatement.setInt(14, billStatistic.getAlarmState());
                    insertStatement.setInt(15, billStatistic.getIsPush());
                    insertStatement.setInt(16, billStatistic.getAccAccessCountPre());
                    insertStatement.setInt(17, billStatistic.getProAccessCountPre());
                    insertStatement.addBatch();
                    count++;
                }
                insertStatement.executeBatch();
                connection.commit();
                log.info("成功插入数据对账链路统计：" + count + "条。");
            } catch (Exception e) {
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (Exception e2) {
                    throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
                }
                log.error(e.toString());
                throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
            } finally {
                if (deleteStatement != null) {
                    try {
                        deleteStatement.close();
                    } catch (SQLException e3) {
                        log.error("关闭deleteStatement错误" + e3);
                    }
                }
                if (insertStatement != null) {
                    try {
                        insertStatement.close();
                    } catch (SQLException e1) {
                        log.error("insertStatement" + e1);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error("关闭connection错误" + e);
                    }
                }
            }
        }
    }

    private void inventoryBill(Connection connection, int startDate, int endDate, int type) {
        if (type != 1 && type != 2) {
            log.error("盘点账单时账单类型错误");
            return;
        }
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;
        try {
            String deleteProviderSql = "DELETE FROM DAC_PROVIDER_INVENTORY_BILL WHERE DZYQSJFW_KSSJ = ?";
            String deleteAcceptorSql = "DELETE FROM DAC_ACCEPTOR_INVENTORY_BILL WHERE DZYQSJFW_KSSJ = ?";
            String insertProviderSql = "INSERT INTO DAC_PROVIDER_INVENTORY_BILL(SJTGF_DZDBH,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJQSBH,SJJWBH,SJJYZ,SJJYSFMC,SCSB_DZDBH,SJTGFGLY_XM,SJTGFGLY_LXDH,DZZDHJ,SCSJ_RQSJ,DZYQSJFW_KSSJ,DZYQSJFW_JSSJ)" +
                    "  SELECT MAX( SJTGF_DZDBH ) SJTGF_DZDBH, MAX( SJZYBSF ) SJZYBSF,MAX( SJZYMC ) SJZYMC,BZSJXJBM,SUM( SJTS ) SJTS,MIN( SJQSBH ) SJQSBH,MAX( SJJWBH ) SJJWBH,MAX( SJJYZ ) SJJYZ," +
                    "  MAX( SJJYSFMC ) SJJYSFMC,MAX( SCSB_DZDBH ) SCSB_DZDBH,MAX( SJTGFGLY_XM ) SJTGFGLY_XM,MAX( SJTGFGLY_LXDH ) SJTGFGLY_LXDH,DZZDHJ,?,? DZYQSJFW_KSSJ , ? DZYQSJFW_JSSJ  FROM DAC_PROVIDER_BILL " +
                    "  WHERE SJ_DZWJMC IS NULL AND XXRWBH >= ? AND  XXRWBH < ? GROUP BY BZSJXJBM,DZZDHJ";
            String insertAcceptorSql = "INSERT INTO DAC_ACCEPTOR_INVENTORY_BILL(SJJRF_DZDBH ,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJQSBH,SJJWBH,SJJYZ,SJJYSFMC,SCSB_DZDBH,SJJRFGLY_XM,SJJRFGLY_LXDH,DZZDHJ,SCSJ_RQSJ,DZYQSJFW_KSSJ,DZYQSJFW_JSSJ,DZDZTDM)" +
                    "  SELECT MAX( SJJRF_DZDBH ) SJJRF_DZDBH, MAX( SJZYBSF ) SJZYBSF,MAX( SJZYMC ) SJZYMC,BZSJXJBM,SUM( SJTS ) SJTS,MIN( SJQSBH ) SJQSBH,MAX( SJJWBH ) SJJWBH,MAX( SJJYZ ) SJJYZ," +
                    "  MAX( SJJYSFMC ) SJJYSFMC,MAX( SCSB_DZDBH ) SCSB_DZDBH,MAX( SJJRFGLY_XM ) SJJRFGLY_XM,MAX( SJJRFGLY_LXDH ) SJJRFGLY_LXDH,DZZDHJ,?,? DZYQSJFW_KSSJ , ? DZYQSJFW_JSSJ , 0 DZDZTDM FROM DAC_ACCEPTOR_BILL " +
                    "  WHERE SJ_DZWJMC IS NULL AND XXRWBH >= ? AND  XXRWBH < ? GROUP BY BZSJXJBM,DZZDHJ";
            connection.setAutoCommit(false);
            if (type == 1) {
                deleteStatement = connection.prepareStatement(deleteProviderSql);
                insertStatement = connection.prepareStatement(insertProviderSql);
            } else {
                deleteStatement = connection.prepareStatement(deleteAcceptorSql);
                insertStatement = connection.prepareStatement(insertAcceptorSql);
            }
            deleteStatement.setInt(1, startDate);

            insertStatement.setInt(1, (int) (System.currentTimeMillis() /1000));
            insertStatement.setString(2, String.valueOf(startDate));
            insertStatement.setString(3, String.valueOf(endDate));
            insertStatement.setString(4, String.valueOf(startDate));
            insertStatement.setString(5, String.valueOf(endDate));
            deleteStatement.executeUpdate();
            insertStatement.executeUpdate();
            log.info("刷新盘点对账完成");
        } catch (SQLException e) {
            log.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR, e);
        } finally {
            closeStatement(deleteStatement, insertStatement);
        }
    }

    private void closeStatement(PreparedStatement deleteStatement, PreparedStatement insertStatement) {
        if (deleteStatement != null) {
            try {
                deleteStatement.close();
            } catch (SQLException e1) {
                log.error("" + e1);
            }
        }
        if (insertStatement != null) {
            try {
                insertStatement.close();
            } catch (SQLException e1) {
                log.error("" + e1);
            }
        }
    }

    /**
     * 更新按照实例对账结果
     * @param connection
     * @param reconInfos
     * @param linkType
     */
    private void updateSummarizeBill(Connection connection, List<ReconInfo> reconInfos, int linkType) {
        ReconInfoResult reconInfoResult = new ReconInfoResult();
        reconInfoResult.setProviderBillNo(reconInfos.get(0).getProviderBillNo());
        reconInfoResult.setBillType(2);
        reconInfoResult.setReconMethod(3);
        reconInfoResult.setReconTime((int) (System.currentTimeMillis() / 1000));
        reconInfoResult.setResourceId(reconInfos.get(0).getAccResourceId());
        reconInfoResult.setInceptDataTime(reconInfos.get(0).getAccInceptDataTime());
        reconInfoResult.setAccBillLink(linkType);
        int accDataCount = 0;
        int proDataCount = 0;
        for (ReconInfo reconInfo : reconInfos) {
            accDataCount += reconInfo.getAccDataCount();
            proDataCount += reconInfo.getProDataCount();
        }
        if (accDataCount == proDataCount) {
            reconInfoResult.setBillState(1);
            reconInfoResult.setReconResult("对账成功");
        } else {
            reconInfoResult.setBillState(2);
            reconInfoResult.setReconResult("数据条数不同");
        }
        List<ReconInfoResult> reconInfoResults = new ArrayList<>();
        reconInfoResults.add(reconInfoResult);
        updateBill(connection, reconInfoResults);
    }

    /**
     * 数据包对账
     */
    private void reconciliationDetail(List<ReconInfo> reconInfos, List<InventoryBill> providerInventoryBills, List<InventoryBill> acceptorInventoryBills) {
        for (ReconInfo reconInfo : reconInfos) {
            if (Objects.isNull(reconInfo)) {
                continue;
            }
            InventoryBill providerInventoryBill = new InventoryBill();
            InventoryBill acceptorInventoryBill = new InventoryBill();

            // 提供方盘点对账单赋值
            providerInventoryBill.setProviderBillNo(reconInfo.getProviderBillNo());
            providerInventoryBill.setDataResourceTag("");
            providerInventoryBill.setDataResourceName(reconInfo.getProDataSourceName());
            providerInventoryBill.setResourceId(reconInfo.getProResourceId());
            providerInventoryBill.setDataCount(reconInfo.getProDataCount());
            providerInventoryBill.setDataSize(reconInfo.getProDataSize());
            providerInventoryBill.setStartNo(reconInfo.getProStartNo());
            providerInventoryBill.setEndNo(reconInfo.getProEndNo());
            providerInventoryBill.setDataFingerprint(reconInfo.getProDataFingerprint());
            providerInventoryBill.setFingerprintType(reconInfo.getProFingerPrintType());
            providerInventoryBill.setRiseTime(reconInfo.getProDataSendTime());
            providerInventoryBill.setLastFailBillNo(reconInfo.getProLastFailBill());
            providerInventoryBill.setAdminName(reconInfo.getProDataAdministrator());
            providerInventoryBill.setAdminTel(reconInfo.getProAdministratorTel());
            providerInventoryBill.setReconStartTime(0);
            providerInventoryBill.setReconEndTime(0);
            providerInventoryBill.setBillType(1);
            providerInventoryBill.setSourceLocation(reconInfo.getProSourceLocation());
            providerInventoryBill.setDataSourceName(reconInfo.getProDataSourceName());
            providerInventoryBill.setDataSourceType(reconInfo.getProDataSourceType());
            providerInventoryBill.setInceptDataTime(reconInfo.getProInceptDataTime());
            providerInventoryBill.setInceptDataId(reconInfo.getProInceptDataId());

            // 接入方盘点对账单赋值
            acceptorInventoryBill.setAcceptorBillNo(reconInfo.getAcceptorBillNo());
            acceptorInventoryBill.setProviderBillNo(reconInfo.getProviderBillNo());
            acceptorInventoryBill.setDataResourceTag("");
            acceptorInventoryBill.setDataResourceName(reconInfo.getAccDataSourceName());
            acceptorInventoryBill.setResourceId(reconInfo.getAccResourceId());
            acceptorInventoryBill.setDataCount(reconInfo.getAccDataCount());
            acceptorInventoryBill.setDataSize(reconInfo.getAccDataSize());
            acceptorInventoryBill.setStartNo(reconInfo.getAccStartNo());
            acceptorInventoryBill.setEndNo(reconInfo.getAccEndNo());
            acceptorInventoryBill.setDataFingerprint(reconInfo.getAccDataFingerprint());
            acceptorInventoryBill.setFingerprintType(reconInfo.getAccFingerprintType());
            acceptorInventoryBill.setRiseTime(reconInfo.getAccDataArriveTime());
            acceptorInventoryBill.setBillType(0);
            acceptorInventoryBill.setLastFailBillNo(reconInfo.getAccLastFailBill());
            acceptorInventoryBill.setAdminName(reconInfo.getAccDataAdministrator());
            acceptorInventoryBill.setAdminTel(reconInfo.getAccAdministratorTel());
            acceptorInventoryBill.setInceptDataTime(reconInfo.getAccInceptDataTime());
            acceptorInventoryBill.setReconStartTime(0);
            acceptorInventoryBill.setReconEndTime(0);
            acceptorInventoryBill.setSourceLocation(reconInfo.getAccSourceLocation());
            acceptorInventoryBill.setDataSourceName(reconInfo.getAccDataSourceName());
            acceptorInventoryBill.setDataSourceType(reconInfo.getAccDataSourceType());
            acceptorInventoryBill.setInceptDataId(reconInfo.getAccInceptDataId());

            // 对账相关赋值
            providerInventoryBill.setCheckMethod(3);
            acceptorInventoryBill.setCheckMethod(3);
            providerInventoryBill.setBillState(1);
            acceptorInventoryBill.setBillState(1);
            providerInventoryBill.setResultDes("对账成功");
            acceptorInventoryBill.setResultDes("对账成功");
            providerInventoryBill.setCheckTime((int) (System.currentTimeMillis() / 1000));
            acceptorInventoryBill.setCheckTime((int) (System.currentTimeMillis() / 1000));
            if (reconInfo.getAccDataCount() != reconInfo.getAccDataCount()) {
                providerInventoryBill.setBillState(2);
                acceptorInventoryBill.setBillState(2);
                providerInventoryBill.setResultDes("数据条数不同");
                acceptorInventoryBill.setResultDes("数据条数不同");
            }
            // 添加到集合
            if (!StringUtils.isEmpty(providerInventoryBill.getResourceId()) && !StringUtils.isEmpty(providerInventoryBill.getInceptDataTime())) {
                providerInventoryBills.add(providerInventoryBill);
            }
            if (!StringUtils.isEmpty(acceptorInventoryBill.getResourceId()) && !StringUtils.isEmpty(acceptorInventoryBill.getInceptDataTime())) {
                acceptorInventoryBills.add(acceptorInventoryBill);
            }
        }
    }

    /**
     * 对账通过类型
     */
    private List<InventoryBill> reconByType(ReconParam reconParam) {
        Connection connection = null;
        try {
            // 数据库连接
            connection = oracleDataSource.getConnection();
            // 查出对账信息
            if (reconParam.getDZZDHJ() == 3) {
                reconParam.setAcceptorLink(3);
                reconParam.setProviderLink(2);
            } else if (reconParam.getDZZDHJ() == 2) {
                reconParam.setAcceptorLink(2);
                reconParam.setProviderLink(1);
            } else {
                log.error("对账类型错误");
                return null;
            }
            // 数据包对账
            if (reconParam.isDetail()) {
                List<ReconInfo> reconInfos = null;
                if (reconParam.getDZZDHJ() == 3) {
                    reconInfos = inventoryReconDao.reconStandardToStorage(reconParam);
                } else if (reconParam.getDZZDHJ() == 2) {
                    reconInfos = inventoryReconDao.reconAccessToStandard(reconParam);
                }
                List<InventoryBill> providerInventoryBills = new ArrayList<>();
                List<InventoryBill> acceptorInventoryBills = new ArrayList<>();
                if (!CollectionUtils.isEmpty(reconInfos)) {
                    // 进行数据包对账并构建新账单
                    reconciliationDetail(reconInfos, providerInventoryBills, acceptorInventoryBills);
                    // 更新按照实例对账结果
                    updateSummarizeBill(connection, reconInfos, reconParam.getAcceptorLink());
                    // 插入数据包数据 inceptDataId一定要有
                    insertPacketBill(connection, providerInventoryBills, acceptorInventoryBills, reconParam);
                    return acceptorInventoryBills;
                }
            } else {
                // 按照实例对账
                List<ReconInfoResult> reconInfos = inventoryReconDao.getReconInfo(reconParam);
                if (!CollectionUtils.isEmpty(reconInfos)) {
                    // 对账操作
                    reconciliation(reconInfos);
                    // 更新 账单表
                    updateBill(connection, reconInfos);
                }
            }
            return null;
        } catch (Exception ex) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception e) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e);
            }
            log.error(ex.toString());
            if (ex instanceof SystemException) {
                throw (SystemException)ex;
            } else {
                throw SystemException.asSystemException(ErrorCode.DB_CONNECT_ERROR, ex);
            }
        } finally {
            if(connection != null){
                try{
                    connection.commit();
                    connection.close();
                } catch (Exception ignored){

                }
            }
        }
    }

    private void insertPacketBill(Connection connection, List<InventoryBill> providerInventoryBills, List<InventoryBill> acceptorInventoryBills, ReconParam reconParam) {
        if (!CollectionUtils.isEmpty(providerInventoryBills)) {
            insertProviderPacketBill(connection, providerInventoryBills, reconParam.getProviderLink());
        }
        if (!CollectionUtils.isEmpty(acceptorInventoryBills)) {
            insertAcceptorPacketBill(connection, acceptorInventoryBills, reconParam.getAcceptorLink());
        }
    }

    private void insertProviderPacketBill(Connection connection, List<InventoryBill> providerInventoryBills, int linkType) {
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;
        try {
            String deleteSql = "DELETE FROM DAC_PROVIDER_BILL WHERE BZSJXJBM = ? AND XXRWBH = ? AND DZZDHJ = ? AND SJ_DZWJMC IS NOT NULL";
            String insertSql = "INSERT INTO DAC_PROVIDER_BILL (SJTGF_DZDBH, SJJSWZ_JYQK, SJCCXSDM, SJ_DZWJMC, SJZYBSF, SJZYMC, BZSJXJBM, SJTS, SJ_DZWJDX, SJQSBH, " +
                    "SJJWBH, SJJYZ, SJJYSFMC, SCSB_DZDBH, SCSJ_RQSJ, SJTGFGLY_XM, SJTGFGLY_LXDH, XXRWBH, DZZDHJ )" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            // 先删除
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, providerInventoryBills.get(0).getResourceId());
            deleteStatement.setString(2, providerInventoryBills.get(0).getInceptDataTime());
            deleteStatement.setInt(3, linkType);
            deleteStatement.executeUpdate();
            // 后插入
            insertStatement = connection.prepareStatement(insertSql);
            int count = 0;
            for (InventoryBill bill : providerInventoryBills) {
                insertStatement.setString(1, bill.getProviderBillNo());
                insertStatement.setString(2, bill.getSourceLocation());
                insertStatement.setInt(3, bill.getDataSourceType());
                insertStatement.setString(4, bill.getInceptDataId());
                insertStatement.setString(5, bill.getDataResourceTag());
                insertStatement.setString(6, bill.getDataSourceName());
                insertStatement.setString(7, bill.getResourceId());
                insertStatement.setInt(8, bill.getDataCount());
                insertStatement.setInt(9, bill.getDataSize());
                insertStatement.setString(10, bill.getStartNo());
                insertStatement.setString(11, bill.getEndNo());
                insertStatement.setString(12, bill.getDataFingerprint());
                insertStatement.setString(13, bill.getFingerprintType());
                insertStatement.setString(14, bill.getLastFailBillNo());
                insertStatement.setInt(15, (int) (System.currentTimeMillis() / 1000));
                insertStatement.setString(16, bill.getAdminName());
                insertStatement.setString(17, bill.getAdminTel());
                insertStatement.setString(18, bill.getInceptDataTime());
                insertStatement.setInt(19, linkType);
                insertStatement.addBatch();
                count++;
            }
            insertStatement.executeBatch();
            log.info("成功插入数据包对账单：" + count + "条。");
        } catch (Exception e) {
            log.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR, e);
        } finally {
            closeStatement(deleteStatement, insertStatement);
        }
    }

    private void insertAcceptorPacketBill(Connection connection, List<InventoryBill> acceptorInventoryBills, int linkType) {
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;
        try {
            String deleteSql = "DELETE FROM DAC_ACCEPTOR_BILL WHERE BZSJXJBM = ? AND XXRWBH = ? AND DZZDHJ = ? AND SJ_DZWJMC IS NOT NULL";
            String insertSql = "INSERT INTO DAC_ACCEPTOR_BILL (SJJRF_DZDBH, SJTGF_DZDBH, SJJSWZ_JYQK, SJCCXSDM, SJ_DZWJMC, SJZYBSF, SJZYMC, BZSJXJBM, SJTS, SJ_DZWJDX, SJQSBH, " +
                    "SJJWBH, SJJYZ, SJJYSFMC, SCSJ_RQSJ, DZSJ_RQSJ, DZFFDM,  DZDLXDM, DZDZTDM, DZJF_JYQK, SCSB_DZDBH, SJJRFGLY_XM, SJJRFGLY_LXDH, FWQDZ,DZZDHJ)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            // 先删除
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, acceptorInventoryBills.get(0).getResourceId());
            deleteStatement.setString(2, acceptorInventoryBills.get(0).getInceptDataTime());
            deleteStatement.setInt(3, linkType);
            deleteStatement.executeUpdate();
            // 后插入
            insertStatement = connection.prepareStatement(insertSql);
            int count = 0;
            for (InventoryBill bill : acceptorInventoryBills) {
                insertStatement.setString(1, bill.getAcceptorBillNo());
                insertStatement.setString(2, bill.getProviderBillNo());
                insertStatement.setString(3, bill.getSourceLocation());
                insertStatement.setInt(4, bill.getDataSourceType());
                insertStatement.setString(5, bill.getInceptDataId());
                insertStatement.setString(6, bill.getDataResourceTag());
                insertStatement.setString(7, bill.getDataSourceName());
                insertStatement.setString(8, bill.getResourceId());
                insertStatement.setInt(9, bill.getDataCount());
                insertStatement.setInt(10, bill.getDataSize());
                insertStatement.setString(11, bill.getStartNo());
                insertStatement.setString(12, bill.getEndNo());
                insertStatement.setString(13, bill.getDataFingerprint());
                insertStatement.setString(14, bill.getFingerprintType());
                insertStatement.setInt(15, bill.getRiseTime());
                insertStatement.setInt(16, bill.getCheckTime());
                insertStatement.setInt(17, bill.getCheckMethod());
                insertStatement.setInt(18, bill.getBillType());
                insertStatement.setInt(19, bill.getBillState());
                insertStatement.setString(20, bill.getResultDes());
                insertStatement.setString(21, bill.getLastFailBillNo());
                insertStatement.setString(22, bill.getAdminName());
                insertStatement.setString(23, bill.getAdminTel());
                insertStatement.setString(24, bill.getInceptDataTime());
                insertStatement.setInt(25, linkType);
                insertStatement.addBatch();
                count++;
            }
            insertStatement.executeBatch();
            log.info("成功插入数据包对账单：" + count + "条。");
        } catch (Exception e) {
            log.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR, e);
        } finally {
            closeStatement(deleteStatement, insertStatement);
        }
    }

    /**
     * 具体的对账操作
     * @param reconInfos
     */
    private void reconciliation(List<ReconInfoResult> reconInfos) {
        // 过滤有空对象的
        reconInfos = reconInfos.stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 对账
        for (ReconInfoResult reconInfo : reconInfos) {
            // 接入方账单type为2
            reconInfo.setBillType(0);
            // 盘点对账
            reconInfo.setReconMethod(3);
            reconInfo.setReconTime((int) (System.currentTimeMillis() / 1000));
            // 通过数据条数对账  state 1.成功 2.失败
            if (reconInfo.getAccDataCount() == reconInfo.getProDataCount()) {
                reconInfo.setBillState(1);
                reconInfo.setReconResult("对账成功");
            } else {
                reconInfo.setBillState(2);
                reconInfo.setReconResult("数据条数不同");
            }
        }
    }

    /**
     * 对账更新账单 只改接入方账单
     * @param connection
     * @param reconInfos
     */
    private void updateBill(Connection connection, List<ReconInfoResult> reconInfos) {
        PreparedStatement pstmt = null;
        try {
            String sqlString = "UPDATE DAC_ACCEPTOR_BILL SET SJTGF_DZDBH = ?, DZDZTDM = ?, DZDLXDM = ?, DZFFDM = ?, DZSJ_RQSJ = ?, DZJG_JYQK = ? WHERE BZSJXJBM = ? AND XXRWBH = ? AND DZZDHJ = ?";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(sqlString);
            int count = 0;
            for (ReconInfoResult reconInfo : reconInfos) {
                pstmt.setString(1, reconInfo.getProviderBillNo());
                pstmt.setInt(2, reconInfo.getBillState());
                pstmt.setInt(3, reconInfo.getBillType());
                pstmt.setInt(4, reconInfo.getReconMethod());
                pstmt.setInt(5, reconInfo.getReconTime());
                pstmt.setString(6, reconInfo.getReconResult());
                pstmt.setString(7, reconInfo.getResourceId());
                pstmt.setString(8, reconInfo.getInceptDataTime());
                pstmt.setInt(9, reconInfo.getAccBillLink());
                count++;
                pstmt.addBatch();
                //1000条提交一次
                if (count % 1000 == 0) {
                    pstmt.executeBatch();
                    log.info("已经对账[" + count + "]条记录");
                }
            }
            pstmt.executeBatch();
            log.info("成功对账：" + count + "条。");
        } catch (Exception e) {
            log.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e1) {
                    log.error("" + e1);
                }
            }
        }
    }
}
