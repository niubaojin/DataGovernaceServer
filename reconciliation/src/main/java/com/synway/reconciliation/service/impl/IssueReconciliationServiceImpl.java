package com.synway.reconciliation.service.impl;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hazelcast.core.HazelcastInstance;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.common.ErrorCode;
import com.synway.reconciliation.common.SystemException;
import com.synway.reconciliation.conditional.IssueCondition;
import com.synway.reconciliation.dao.IssueReconciliationDao;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.pojo.issue.*;
import com.synway.reconciliation.service.IssueReconciliationService;
import com.synway.reconciliation.util.DateParamUtil;
import com.synway.reconciliation.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * 下发对账
 * @author DZH
 */
@Slf4j
@Service
@Conditional(IssueCondition.class)
public class IssueReconciliationServiceImpl implements IssueReconciliationService {

    @Autowired
    private IssueReconciliationDao issueReconciliationDao;


    @Autowired
    private DruidDataSource oracleDataSource;

    @Value("${local}")
    private String local;

    @Value("${localCode}")
    private String localCode;

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public IssueReconciliationServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public Map<String,Object> getPageIssueBill(IssueReconciliationRequestExtend request) {
        HashMap<String, Object> resultMap = new HashMap<>();
        //处理分页参数
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        if (pageNum > 0 && pageSize > 0 ) {
            request.setPageNum((pageNum - 1) * pageSize);
            request.setPageSize(pageSize * pageNum);
        }
        String searchContent = request.getSearchContent();
        if (StringUtils.isNotBlank(searchContent)) {
            searchContent = "%" + StringUtils.upperCase(searchContent) + "%";
        }
        request.setSearchContent(searchContent);
        if (request.getAcceptCity() == null && !"总队".equals(local)) {
            request.setLocal(local);
            request.setAcceptCity("总队");
        }
        if (request.getStartTime() != null) {
            request.setStartTime(request.getStartTime() + " 00:00:00");
        }
        if (request.getEndTime() != null) {
            request.setEndTime(request.getEndTime() + " 23:59:59");
        }
        List<IssueDayStatistics> pageIssueBill = issueReconciliationDao.getPageIssueBill(request);
        long total = issueReconciliationDao.countIssueBill(request);
        resultMap.put("rows", pageIssueBill);
        resultMap.put("total", total);
        return resultMap;
    }

    @Override
    public List<IssueHistoryResponse> getIssueHistoryBill(IssueHistoryRequest request) {
        List<IssueHistoryResponse> issueHistoryBill = new ArrayList<>();
        try {
            //处理账单日期 得到需要的日期账单
            Date checkDate = DateUtil.parseDate(request.getCheckTime());
            Date endDate = DateUtil.addDay(checkDate, -7);
            String endTime = DateUtil.formatDate(endDate);
            request.setEndTime(endTime);
            issueHistoryBill = issueReconciliationDao.getIssueHistoryBill(request);
        } catch (Exception e) {
            log.error("获取历史账单错误：", e);
        }
        return issueHistoryBill;
    }

    @Override
    public Map<String, Object> getPageBaseTime(IssueBaseTimeRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();
        //处理分页参数
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        if (pageNum > 0 && pageSize > 0 ) {
            request.setPageNum((pageNum - 1) * pageSize);
            request.setPageSize(pageSize * pageNum);
        }
        //如果有搜索内容处理搜索内容
        String searchContent = request.getSearchContent();
        if (StringUtils.isNotBlank(searchContent)) {
            searchContent = "%" + StringUtils.upperCase(searchContent) + "%";
        }
        request.setSearchContent(searchContent);
        List<IssueBaseTimeResponse> pageIssueBaseTime = issueReconciliationDao.getPageIssueBaseTime(request);
        long total = issueReconciliationDao.countIssueBaseTime(request);
        resultMap.put("rows", pageIssueBaseTime);
        resultMap.put("total", total);
        return resultMap;
    }

    @Override
    public Boolean saveOrUpdateBaseTime(IssueBaseTimeRequest request) {
        List<IssueBaseTime> baseTimes = request.getBaseTimes();
        if (baseTimes == null || baseTimes.size() == 0) {
            return false;
        }
        for (IssueBaseTime baseTime : baseTimes) {
            if (StringUtils.isBlank(baseTime.getJobId()) || StringUtils.isBlank(baseTime.getDataNameEn())) {
                continue;
            }
            ConcurrentMap<String, IssueBaseTime> issueBaseTimeMap = hazelcastInstance.getMap(Constants.ISSUE_BASE_TIME_MAP);
            String key = baseTime.getJobId() +"_"+ baseTime.getDataNameEn();
            IssueBaseTime issueBaseTime = issueBaseTimeMap.get(key);
            if (baseTime.getBaseTime() == null) {
                issueReconciliationDao.deleteIssueBaseTime(baseTime);
                issueBaseTimeMap.remove(key);
            } else {
                if (null == issueBaseTime) {
                    issueReconciliationDao.insertIssueBaseTime(baseTime);
                } else {
                    issueReconciliationDao.updateIssueBaseTime(baseTime);
                }
                issueBaseTimeMap.put(key, baseTime);
            }
        }
        return true;
    }

    @Override
    public Boolean deleteOneBaseTime(IssueBaseTime issueBaseTime) {
        int i = issueReconciliationDao.deleteIssueBaseTime(issueBaseTime);
        ConcurrentMap<String, IssueBaseTime> issueBaseTimeMap = hazelcastInstance.getMap(Constants.ISSUE_BASE_TIME_MAP);
        issueBaseTimeMap.remove(issueBaseTime.getJobId() + "_" + issueBaseTime.getDataNameEn());
        return i > 0;
    }

    @Override
    public Boolean quickMultiBaseTime(IssueBaseTimeRequest request) {
        Integer type = request.getType();
        List<IssueBaseTime> baseTimes = request.getBaseTimes();
        ConcurrentMap<String, IssueBaseTime> issueBaseTimeMap = hazelcastInstance.getMap(Constants.ISSUE_BASE_TIME_MAP);
        int num = -1;
        if (null != baseTimes) {
            baseTimes = baseTimes.stream().filter(Objects::nonNull).filter(t -> t.getBaseTime() != null).collect(Collectors.toList());
        }
        if (null != baseTimes && baseTimes.size() > 0) {
            if (type == 2) {
                for (IssueBaseTime baseTime : baseTimes) {
                    num = issueReconciliationDao.deleteIssueBaseTime(baseTime);
                    issueBaseTimeMap.remove(baseTime.getJobId() + "_" + baseTime.getDataNameEn());
                }
            } else if (type == 0){
                for (IssueBaseTime baseTime : baseTimes) {
                    baseTime.setBaseTimeEnable(0);
                    num = issueReconciliationDao.updateIssueBaseTime(baseTime);
                    String key = baseTime.getJobId() +"_"+ baseTime.getDataNameEn();
                    issueBaseTimeMap.put(key, baseTime);
                }
            } else if (type == 1){
                for (IssueBaseTime baseTime : baseTimes) {
                    baseTime.setBaseTimeEnable(1);
                    num = issueReconciliationDao.updateIssueBaseTime(baseTime);
                    String key = baseTime.getJobId() +"_"+ baseTime.getDataNameEn();
                    issueBaseTimeMap.put(key, baseTime);
                }
            }
        }
        return num >= 0;
    }

    @Override
    public List<String> getAcceptCity() {
        return issueReconciliationDao.getAcceptCity();
    }

    @Override
    public void insertIssueProviderBill(List<DacProviderBill> bills) {
        Connection connection = null;
        try{
            List<DacProviderBill> mainIssueProvider = new ArrayList<>();
            List<DacProviderBill> cityIssueProvider = new ArrayList<>();
            connection = oracleDataSource.getConnection();
            if (Constants.MAIN_DIVISION_CODE.equalsIgnoreCase(localCode)) {
                for (DacProviderBill bill : bills) {
                    if (bill == null || StringUtils.isBlank(bill.getSJTGF_DZDBH())) {
                        continue;
                    }
                    if (bill.getSJTGF_DZDBH().startsWith(localCode)) {
                        cityIssueProvider.add(bill);
                    } else {
                        mainIssueProvider.add(bill);
                    }
                }
            } else {
                for (DacProviderBill bill : bills) {
                    if (bill == null || StringUtils.isBlank(bill.getSJTGF_DZDBH())) {
                        continue;
                    }
                    if (bill.getSJTGF_DZDBH().startsWith(localCode)) {
                        cityIssueProvider.add(bill);
                    } else if (bill.getSJTGF_DZDBH().startsWith(Constants.MAIN_DIVISION_CODE)) {
                        mainIssueProvider.add(bill);
                    }
                }
            }
            insertMainIssueProviderBill(mainIssueProvider, connection);
            insertCityIssueProviderBill(cityIssueProvider, connection);
        } catch (Exception e) {
            log.error("insert issue provider bill error" + e.toString());
            if (e instanceof SystemException) {
                throw (SystemException) e;
            } else {
                throw SystemException.asSystemException(ErrorCode.DB_CONNECT_ERROR, e);
            }
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
            }catch (Exception ignored){

            }
        }
    }

    @Override
    public void insertIssueAcceptorBill(List<DacAcceptorBill> bills) {
        Connection connection = null;
        try{
            List<DacAcceptorBill> mainIssueAcceptor = new ArrayList<>();
            List<DacAcceptorBill> cityIssueAcceptor = new ArrayList<>();
            connection = oracleDataSource.getConnection();
            // 如果是总队的 入全部，地市入地市和总队的
            if (Constants.MAIN_DIVISION_CODE.equalsIgnoreCase(localCode)) {
                for (DacAcceptorBill bill : bills) {
                    if (bill == null || StringUtils.isBlank(bill.getSJJRF_DZDBH())) {
                        continue;
                    }
                    if (bill.getSJJRF_DZDBH().startsWith(localCode)) {
                        cityIssueAcceptor.add(bill);
                    } else {
                        mainIssueAcceptor.add(bill);
                    }
                }
            } else {
                for (DacAcceptorBill bill : bills) {
                    if (bill == null || StringUtils.isBlank(bill.getSJJRF_DZDBH())) {
                        continue;
                    }
                    if (bill.getSJJRF_DZDBH().startsWith(localCode)) {
                        cityIssueAcceptor.add(bill);
                    } else if (bill.getSJJRF_DZDBH().startsWith(Constants.MAIN_DIVISION_CODE)){
                        mainIssueAcceptor.add(bill);
                    }
                }
            }

            insertMainIssueAcceptorBill(mainIssueAcceptor, connection);
            insertCityIssueAcceptorBill(cityIssueAcceptor, connection);
        } catch (Exception e) {
            log.error("insert issue acceptor bill" + e.toString());
            if (e instanceof SystemException) {
                throw (SystemException) e;
            } else {
                throw SystemException.asSystemException(ErrorCode.DB_CONNECT_ERROR, e);
            }
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
            }catch (Exception ignored){

            }
        }
    }

    @Override
    public void insertTaskInfos(List<RelateJob> jobInfos) {
        Connection connection = null;
        try{
            connection = oracleDataSource.getConnection();
            if (jobInfos.size() > 0) {
                insertTaskInfo(jobInfos, connection);
            }
        } catch (Exception e) {
            log.error("insert task info error" + e.toString());
            if (e instanceof SystemException) {
                throw (SystemException) e;
            } else {
                throw SystemException.asSystemException(ErrorCode.DB_CONNECT_ERROR, e);
            }
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    public void issueReconciliationStatistics() {
        try {
            long start = System.currentTimeMillis();
            // 获取近两天入库的账单的发送天
            Calendar c = Calendar.getInstance();
            List<Long> times = new ArrayList<>();
            times.add(c.getTime().getTime() / 1000);
            c.add(Calendar.DAY_OF_MONTH, -1);
            times.add(c.getTime().getTime() / 1000);
            //重新统计入库近两天账单
            for (Long time : times) {
                Date date = new Date(time * 1000L);
                String startTime = DateParamUtil.getDayStart(date);
                int startT = DateParamUtil.getDayStartInt(date);
                int endT= DateParamUtil.getDayEndInt(date);
                List<IssueReconBill> allBills = buildIssueReconBill(startT, endT);
                List<IssueReconBill> reconBills = getEndBills(allBills);
                if (reconBills.size() > 0) {
                    List<IssueDayStatistics> dayStatistics = reconBills.stream().filter(Objects::nonNull).map(t -> {
                        try {
                            return doStatistics(t, startTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                    if (dayStatistics.size() > 0) {
                        deleteAndInsertDayStatistics(dayStatistics, startTime);
                    }
                }
            }
            long end = System.currentTimeMillis();
            log.info("下发对账统计耗时" + (end - start));
        } catch (SQLException e) {
            log.error("下发对账统计报错：" + e.toString());
        }
    }

    @Override
    public List<IssueDayStatisticsExcel> exportIssueStatistics(IssueReconciliationRequestExtend request) {
        request.setPageNum(0);
        request.setPageSize(Integer.MAX_VALUE);
        String searchContent = request.getSearchContent();
        if (StringUtils.isNotBlank(searchContent)) {
            StringUtils.upperCase(searchContent);
            searchContent = "%" + searchContent + "%";
        }
        request.setSearchContent(searchContent);
        List<IssueDayStatistics> issueDayStatistics = issueReconciliationDao.getPageIssueBill(request);
        List<IssueDayStatisticsExcel> issueDayStatisticsExcels = new ArrayList<>();
        for (IssueDayStatistics issueDayStatistic : issueDayStatistics) {
            IssueDayStatisticsExcel issueDayStatisticsExcel = new IssueDayStatisticsExcel();
            BeanUtils.copyProperties(issueDayStatistic, issueDayStatisticsExcel);
            issueDayStatisticsExcel.setCheckTime(DateUtil.formatDate(issueDayStatistic.getCheckTime()));
            if (issueDayStatistic.getIsPartition() != null && issueDayStatistic.getIsPartition().equals(1)) {
                issueDayStatisticsExcel.setIsPartition("分区表");
            } else if (issueDayStatistic.getIsPartition() != null && issueDayStatistic.getIsPartition().equals(0)) {
                issueDayStatisticsExcel.setIsPartition("非分区表");
            }
            if (issueDayStatistic.getBaseTimeEnable() != null && issueDayStatistic.getBaseTimeEnable().equals(1)) {
                issueDayStatisticsExcel.setBaseTimeEnable("启用");
            } else if (issueDayStatistic.getBaseTimeEnable() != null && issueDayStatistic.getBaseTimeEnable().equals(0)) {
                issueDayStatisticsExcel.setBaseTimeEnable("禁用");
            }
            if (issueDayStatistic.getUpdateResult() != null && issueDayStatistic.getUpdateResult().equals(1)) {
                issueDayStatisticsExcel.setUpdateResult("正常");
            } else if (issueDayStatistic.getUpdateResult() != null && issueDayStatistic.getUpdateResult().equals(0)) {
                issueDayStatisticsExcel.setUpdateResult("异常");
            }
            if (issueDayStatistic.getTaskState() != null) {
                String taskState = issueDayStatistic.getTaskState();
                String taskStateStr = "";
                if ("00".equals(taskState)) {
                    taskStateStr = "总队推送失败，地市入库失败";
                } else if ("10".equals(taskState)) {
                    taskStateStr = "总队推送成功，地市入库失败";
                } else if ("20".equals(taskState)) {
                    taskStateStr = "总队推送进行";
                } else if ("11".equals(taskState)) {
                    taskStateStr = "总队推送成功，地市入库成功";
                }
                issueDayStatisticsExcel.setTaskState(taskStateStr);
            }

            issueDayStatisticsExcels.add(issueDayStatisticsExcel);
        }
        return issueDayStatisticsExcels;
    }

    @Override
    public RestReconInfo getReconInfo() {
        Calendar c = Calendar.getInstance();
        String startDate = DateParamUtil.getDayStart(c.getTime());
        String endDate = DateParamUtil.getDayEnd(c.getTime());
        RestReconInfo restReconInfo = new RestReconInfo();
        RestReconInfo reconInfo = issueReconciliationDao.getReconInfo(startDate, endDate);
        Integer dataTypeCount = issueReconciliationDao.getDataTypeCount();
        Integer unReconCount = issueReconciliationDao.getUnReconCount(startDate, endDate);
        if (reconInfo != null) {
            if (reconInfo.getSuccessCount() != null) {
                restReconInfo.setSuccessCount(reconInfo.getSuccessCount());
            } else {
                restReconInfo.setSuccessCount(0);
            }
            if (reconInfo.getFailedCount() != null) {
                restReconInfo.setFailedCount(reconInfo.getFailedCount());
            } else {
                restReconInfo.setFailedCount(0);
            }
        } else {
            restReconInfo.setSuccessCount(0);
            restReconInfo.setFailedCount(0);
        }
        restReconInfo.setDataTypeCount(dataTypeCount);
        restReconInfo.setUnReconCount(unReconCount);
        if (unReconCount >= restReconInfo.getFailedCount()) {
            restReconInfo.setUnReconCount(unReconCount - restReconInfo.getFailedCount());
        }
        return restReconInfo;
    }

    @Override
    public List<IssueAccessCount> getIssueAccessCount() {
        Calendar c = Calendar.getInstance();

        // 初始化7日接入
        List<IssueAccessCount> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            String date = DateParamUtil.getDayStart(calendar.getTime()).substring(0, 10);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            IssueAccessCount issueAccessCount = new IssueAccessCount();
            issueAccessCount.setIssueTime(date);
            issueAccessCount.setIssueCount(0L);
            result.add(issueAccessCount);
        }

        List<String> issueTimes = result.stream().map(IssueAccessCount::getIssueTime).collect(Collectors.toList());
        String endDate = DateParamUtil.getDayEnd(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, -6);
        String startDate = DateParamUtil.getDayStart(c.getTime());
        List<IssueAccessCount> issueAccessCount = issueReconciliationDao.getIssueAccessCount(startDate, endDate);

        //处理有接入的天
        for (IssueAccessCount accessCount : issueAccessCount) {
            String issueTime = accessCount.getIssueTime().substring(0, 10);
            if (issueTimes.contains(issueTime)) {
                result.removeIf(t -> t.getIssueTime().equals(issueTime));
                accessCount.setIssueTime(accessCount.getIssueTime().substring(0, 10));
                result.add(accessCount);
            }
        }
        return result;
    }

    /**
     * 要区分下发还是上报
     * 如果是地市
     * 下发
     * DAC_ISSUE_PROVIDER_BILL.SJTGF_DZDBH = DAC_ACCESS_ACCEPTOR_BILL.SJTGF_DZDBH
     * and DAC_ISSUE_ACCEPTOR_BILL.SJTGF_DZDBH is null
     * 上报
     * DAC_ACCESS_PROVIDER_BILL.SJTGF_DZDBH = DAC_ISSUE_ACCEPTOR_BILL.SJTGF_DZDBH
     * and DAC_ACCESS_ACCEPTOR_BILL.SJTGF_DZDBH is null
     * 如果是总队
     * 下发
     * DAC_ACCESS_PROVIDER_BILL.SJTGF_DZDBH = DAC_ISSUE_ACCEPTOR_BILL.SJTGF_DZDBH
     * and DAC_ACCESS_ACCEPTOR_BILL.SJTGF_DZDBH is null
     * 上报
     * DAC_ISSUE_PROVIDER_BILL.SJTGF_DZDBH = DAC_ACCESS_ACCEPTOR_BILL.SJTGF_DZDBH
     * and DAC_ISSUE_ACCEPTOR_BILL.SJTGF_DZDBH is null
     * @param startDate
     * @param endDate
     * @return
     */
    private List<IssueReconBill> buildIssueReconBill (int startDate, int endDate) {
        List<IssueReconBill> allBills = new ArrayList<>();
        // 分别从四张账单表根据 对账单编号 分组获取对账单
        List<AccountBill> allIssueAcceptorBill = issueReconciliationDao.getAllIssueAcceptorBill(startDate, endDate);
        List<AccountBill> allIssueProviderBill = issueReconciliationDao.getAllIssueProviderBill(startDate, endDate);
        List<AccountBill> allAccessProviderBill = issueReconciliationDao.getAllAccessProviderBill(startDate, endDate);
        List<AccountBill> allAccessAcceptorBill = issueReconciliationDao.getAllAccessAcceptorBill(startDate, endDate);

        // 分组成map并添加上local 后边用于根据key获取对账单
        Map<String, List<AccountBill>> ipBillMap = buildBillMap(allIssueProviderBill);
        Map<String, List<AccountBill>> apBillMap = buildBillMap(allAccessProviderBill);

        // 接收方账单根据提供方账单编号分组
        Map<String, AccountBill> issueMap = Maps.newHashMap();
        Map<String, AccountBill> accessMap = Maps.newHashMap();
        List<AccountBill> issueBills = new ArrayList<>();
        List<AccountBill> accessBills = new ArrayList<>();

        // 找到接入方账单为空的  当入口
        allIssueAcceptorBill.stream().filter(Objects::nonNull).forEach(t -> {
            // 添加上local
            setLocal(t);
            if (StringUtils.isBlank(t.getProviderBillNo())) {
                // 地市是下发 总队是上报
                issueBills.add(t);
            } else {
                issueMap.put(t.getProviderBillNo(), t);
            }
        });

        allAccessAcceptorBill.stream().filter(Objects::nonNull).forEach(t -> {
            // 添加上local
            setLocal(t);
            if (StringUtils.isBlank(t.getProviderBillNo())) {
                // 地市是上报 总队是下发
                accessBills.add(t);
            } else {
                accessMap.put(t.getProviderBillNo(), t);
            }
        });

        // 构建下发对账单
        if (!CollectionUtils.isEmpty(issueBills)) {
            for (AccountBill issueAcceptorBill : issueBills) {
                IssueReconBill bill = buildIssueBill(ipBillMap, apBillMap, accessMap, issueAcceptorBill);
                allBills.add(bill);
            }
        }

        if (!CollectionUtils.isEmpty(accessBills)) {
            for (AccountBill accessAcceptorBill : accessBills) {
                IssueReconBill bill = buildIssueBill(apBillMap, ipBillMap, issueMap, accessAcceptorBill);
                allBills.add(bill);
            }
        }
        return allBills;
    }

    private IssueReconBill buildIssueBill(Map<String, List<AccountBill>> ipBilMap, Map<String, List<AccountBill>> apBillMap, Map<String, AccountBill> accessMap, AccountBill issueAcceptorBill) {
        IssueReconBill bill = new IssueReconBill();
        // 在总队为上报 在地市为下发
        // 赋值提供方信息
        bill.setProviderBatchId(issueAcceptorBill.getBatchId());
        bill.setProviderReadCount(issueAcceptorBill.getDataCount());
        bill.setProviderJobId(issueAcceptorBill.getJobId());
        bill.setDataSourceName(issueAcceptorBill.getSourceLocation());
        // 获取对账单编号做关联
        String billNo = issueAcceptorBill.getBillNo();
        if (StringUtils.isNotBlank(billNo)) {
            List<AccountBill> bills = ipBilMap.get(billNo);
            if (!CollectionUtils.isEmpty(bills)) {
                AccountBill issueProviderBill = bills.get(0);
                if (!Objects.isNull(issueProviderBill)) {
                    bill.setProviderWriteCount(issueProviderBill.getDataCount());
                    bill.setSourceLocation(issueProviderBill.getSourceLocation());
                }
            }
        }

        // 赋值接收方信息
        AccountBill accessAcceptorBill = accessMap.get(billNo);
        if (!Objects.isNull(accessAcceptorBill)) {
            bill.setAcceptorJobId(accessAcceptorBill.getJobId());
            bill.setAcceptorBatchId(accessAcceptorBill.getBatchId());
            bill.setAcceptorReadCount(accessAcceptorBill.getDataCount());
            bill.setLocal(accessAcceptorBill.getLocal());
            List<AccountBill> billList = apBillMap.get(accessAcceptorBill.getBillNo());
            if (!CollectionUtils.isEmpty(billList)) {
                AccountBill accessProviderBill = billList.get(0);
                if (!Objects.isNull(accessProviderBill)) {
                    bill.setAcceptorWriteCount(accessProviderBill.getDataCount());
                }
            }
        }
        // 赋值 batchId 为后边统计添加任务时间和状态做准备
        List<String> providerBatchIds = new ArrayList<>();
        List<String> acceptorBatchIds = new ArrayList<>();
        String acceptorBatchId = bill.getAcceptorBatchId();
        String providerBatchId = bill.getProviderBatchId();
        if (StringUtils.isNotBlank(acceptorBatchId)) {
            String[] acceptors = acceptorBatchId.split(",");
            acceptorBatchIds.addAll(Arrays.asList(acceptors));
            bill.setAcceptorBatchId(JSONObject.toJSONString(acceptorBatchIds));
        }
        if (StringUtils.isNotBlank(providerBatchId)) {
            String[] providers = providerBatchId.split(",");
            providerBatchIds.addAll(Arrays.asList(providers));
            bill.setProviderBatchId(JSONObject.toJSONString(providerBatchIds));
        }
        return bill;
    }

    private Map<String, List<AccountBill>> buildBillMap(List<AccountBill> bills) {
        if (!CollectionUtils.isEmpty(bills)) {
            return bills.stream().filter(Objects::nonNull).peek(this::setLocal).collect(Collectors.groupingBy(AccountBill::getBillNo));
        } else {
            return new HashMap<>();
        }
    }

    private void setLocal (AccountBill accountBill) {
        // 从缓存查询local并赋值
        int code = Integer.parseInt(accountBill.getBillNo().substring(0,6));
        Map<Integer, List<AdministrativeDivisionCode>> divisionCodeMap = hazelcastInstance.getMap(Constants.ADMINISTRATIVE_DIVISION_CODE);
        List<AdministrativeDivisionCode> administrativeDivisionCodes = divisionCodeMap.get(code);
        if (!CollectionUtils.isEmpty(administrativeDivisionCodes)) {
            accountBill.setLocal(administrativeDivisionCodes.get(0).getName());
        }
    }

    private List<IssueReconBill> getEndBills(List<IssueReconBill> allBills) {
        List<IssueReconBill> reconBills = new ArrayList<>();
        Map<String, List<IssueReconBill>> collect = allBills.stream().collect(Collectors.groupingBy(IssueReconBill::getProviderJobId));
        for (Map.Entry<String, List<IssueReconBill>> entry : collect.entrySet()) {
            List<IssueReconBill> billList = entry.getValue();
            if (CollectionUtils.isEmpty(billList)) {
                continue;
            }
            if (billList.size() == 1) {
                reconBills.add(billList.get(0));
            } else {
                IssueReconBill reconBill = new IssueReconBill();
                IssueReconBill issueReconBill = billList.get(0);
                BeanUtils.copyProperties(issueReconBill, reconBill);
                reconBill.setProviderReadCount(0L);
                reconBill.setProviderWriteCount(0L);
                reconBill.setAcceptorReadCount(0L);
                reconBill.setAcceptorWriteCount(0L);
                List<String> providerBatchIds = new ArrayList<>();
                List<String> acceptorBatchIds = new ArrayList<>();
                for (IssueReconBill bill : billList) {
                    if (bill.getProviderReadCount() != null) {
                        reconBill.setProviderReadCount(reconBill.getProviderReadCount() + bill.getProviderReadCount());
                    }
                    if (bill.getProviderWriteCount() != null) {
                        reconBill.setProviderWriteCount(reconBill.getProviderWriteCount() + bill.getProviderWriteCount());
                    }
                    if (bill.getAcceptorReadCount() != null) {
                        reconBill.setAcceptorReadCount(reconBill.getAcceptorReadCount() + bill.getAcceptorReadCount());
                    }
                    if (bill.getAcceptorWriteCount() != null) {
                        reconBill.setAcceptorWriteCount(reconBill.getAcceptorWriteCount() + bill.getAcceptorWriteCount());
                    }
                    String providerBatchId = bill.getProviderBatchId();
                    if (StringUtils.isNotBlank(providerBatchId)) {
                        List<String> proBatchIds = JSONArray.parseArray(providerBatchId, String.class);
                        providerBatchIds.addAll(proBatchIds);
                    }
                    String acceptorBatchId = bill.getAcceptorBatchId();
                    if (StringUtils.isNotBlank(acceptorBatchId)) {
                        List<String> accBatchIds = JSONArray.parseArray(acceptorBatchId, String.class);
                        acceptorBatchIds.addAll(accBatchIds);
                    }
                }
                reconBill.setProviderBatchId(JSONArray.toJSONString(providerBatchIds));
                reconBill.setAcceptorBatchId(JSONArray.toJSONString(acceptorBatchIds));
                reconBills.add(reconBill);
            }

        }
        return reconBills;
    }

    private void insertTaskInfo(List<RelateJob> jobInfos, Connection connection) {
        PreparedStatement pstmt = null;
        if (jobInfos.size() == 0) {
            return;
        }
        try {
            String SQLString = "insert into DAC_RELATE_JOB (JOB_ID,BATCH_ID,STATUS,DATA_START_TIME,DATA_END_TIME,CREATE_TIME,LOCAL) values (?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = 0;
            for (RelateJob entry : jobInfos) {
                pstmt.setString(1, entry.getJobId());
                pstmt.setString(2, entry.getBatchId());
                pstmt.setString(3, entry.getStatus());
                pstmt.setTimestamp(4, new Timestamp(entry.getDataStartTime().getTime()));
                pstmt.setTimestamp(5, new Timestamp(entry.getDataEndTime().getTime()));
                pstmt.setTimestamp(6, new Timestamp(entry.getCreateTime().getTime()));
                pstmt.setString(7, entry.getLocal());
                pstmt.addBatch();
                count++;
            }
            pstmt.executeBatch();
            connection.commit();
            //更新缓存
            ConcurrentMap<String, List<RelateJob>> taskInfoMap = hazelcastInstance.getMap(Constants.TASK_INFO_MAP);
            Map<String, List<RelateJob>> collect = jobInfos.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(RelateJob::getJobId));
            taskInfoMap.putAll(collect);
            log.info("成功插入任务相关信息：" + count + "条。");
        } catch (Exception e) {
            try {
                connection.rollback();
                log.error("插入任务相关信息报错：" + e.getMessage());
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
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

    private void insertMainIssueProviderBill(List<DacProviderBill> bills, Connection connection) {
        PreparedStatement pstmt = null;
        if (bills.size() == 0) {
            return;
        }
        try {
            String SQLString = "INSERT INTO DAC_ISSUE_PROVIDER_BILL (SJTGF_DZDBH,SJJSWZ_JYQK,SJCCXSDM,SJ_DZWJMC,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJ_DZWJDX,SJQSBH,SJJWBH,SJJYZ," +
                    "SJJYSFMC,SCSB_DZDBH,SCSJ_RQSJ,SJTGFGLY_XM,SJTGFGLY_LXDH,XXRWBH,JOB_ID,BATCH_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = setProviderStatement(pstmt, bills);
            pstmt.executeBatch();
            connection.commit();
            log.info("成功插入总队下发提供方对账单：" + count + "条。");
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
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

    private void insertCityIssueProviderBill(List<DacProviderBill> bills, Connection connection) {
        PreparedStatement pstmt = null;
        if (bills.size() == 0) {
            return;
        }
        try {
            String SQLString = "INSERT INTO DAC_ACCESS_PROVIDER_BILL (SJTGF_DZDBH,SJJSWZ_JYQK,SJCCXSDM,SJ_DZWJMC,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJ_DZWJDX,SJQSBH,SJJWBH,SJJYZ," +
                "SJJYSFMC,SCSB_DZDBH,SCSJ_RQSJ,SJTGFGLY_XM,SJTGFGLY_LXDH,XXRWBH,JOB_ID,BATCH_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = setProviderStatement(pstmt, bills);
            pstmt.executeBatch();
            connection.commit();
            log.info("成功插入地市下发提供方对账单：" + count + "条。");
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
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

    private void insertMainIssueAcceptorBill(List<DacAcceptorBill> bills, Connection connection) {
        PreparedStatement pstmt = null;
        if (bills.size() == 0) {
            return;
        }
        try {
            String SQLString = "INSERT INTO DAC_ISSUE_ACCEPTOR_BILL (SJJRF_DZDBH,SJJSWZ_JYQK,SJCCXSDM,SJ_DZWJMC,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJ_DZWJDX,SJQSBH,SJJWBH,SJJYZ," +
                    "SJJYSFMC,SCSB_DZDBH,SCSJ_RQSJ,SJJRFGLY_XM,SJJRFGLY_LXDH,XXRWBH,DZDLXDM,DZDZTDM,JOB_ID,BATCH_ID,SJTGF_DZDBH) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = setAcceptorStatement(pstmt, bills);
            pstmt.executeBatch();
            connection.commit();
            log.info("成功插入总队下发接收方对账单：" + count + "条。");
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
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

    private void insertCityIssueAcceptorBill(List<DacAcceptorBill> bills, Connection connection) {
        PreparedStatement pstmt = null;
        if (bills.size() == 0) {
            return;
        }
        try {
            String SQLString = "INSERT INTO DAC_ACCESS_ACCEPTOR_BILL (SJJRF_DZDBH,SJJSWZ_JYQK,SJCCXSDM,SJ_DZWJMC,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJ_DZWJDX,SJQSBH,SJJWBH,SJJYZ," +
                    "SJJYSFMC,SCSB_DZDBH,SCSJ_RQSJ,SJJRFGLY_XM,SJJRFGLY_LXDH,XXRWBH,DZDLXDM,DZDZTDM,JOB_ID,BATCH_ID,SJTGF_DZDBH) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = setAcceptorStatement(pstmt, bills);
            pstmt.executeBatch();
            connection.commit();
            log.info("成功插入地市下发接收方对账单：" + count + "条。");
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
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

    private int setAcceptorStatement(PreparedStatement pstmt, List<DacAcceptorBill> bills) throws SQLException {
        int count = 0;
        for (DacAcceptorBill entry : bills) {
            pstmt.setString(1, entry.getSJJRF_DZDBH());
            pstmt.setString(2, entry.getSJJSWZ_JYQK());
            if (StringUtils.isNotBlank(entry.getSJCCXSDM())) {
                pstmt.setInt(3, Integer.parseInt(entry.getSJCCXSDM()));
            } else {
                pstmt.setInt(3, 0);
            }
            pstmt.setString(4, entry.getSJ_DZWJMC());
            pstmt.setString(5, entry.getSJZYBSF());
            pstmt.setString(6, entry.getSJZYMC());
            pstmt.setString(7, entry.getBZSJXJBM());
            pstmt.setLong(8, entry.getSJTS());
            pstmt.setLong(9, entry.getSJ_DZWJDX());
            pstmt.setString(10, entry.getSJQSBH());
            pstmt.setString(11, entry.getSJJWBH());
            pstmt.setString(12, entry.getSJJYZ());
            pstmt.setString(13, entry.getSJJYSFMC());
            pstmt.setString(14, entry.getSCSB_DZDBH());
            pstmt.setInt(15, Integer.parseInt(entry.getSCSJ_RQSJ()));
            pstmt.setString(16, entry.getSJJRFGLY_XM());
            pstmt.setString(17, entry.getSJJRFGLY_LXDH());
            pstmt.setString(18, entry.getXXRWBH());
            pstmt.setInt(19, 0);
            pstmt.setInt(20, 0);
            pstmt.setString(21, entry.getJobId());
            pstmt.setString(22, entry.getBatchId());
            pstmt.setString(23, entry.getSJTGF_DZDBH());
            pstmt.addBatch();
            count++;
        }
        return count;
    }

    private int setProviderStatement(PreparedStatement pstmt, List<DacProviderBill> bills) throws SQLException {
        int count = 0;
        for (DacProviderBill entry : bills) {
            pstmt.setString(1, entry.getSJTGF_DZDBH());
            pstmt.setString(2, entry.getSJJSWZ_JYQK());
            if (StringUtils.isNotBlank(entry.getSJCCXSDM())) {
                pstmt.setInt(3, Integer.parseInt(entry.getSJCCXSDM()));
            } else {
                pstmt.setInt(3, 0);
            }
            pstmt.setString(4, entry.getSJ_DZWJMC());
            pstmt.setString(5, entry.getSJZYBSF());
            pstmt.setString(6, entry.getSJZYMC());
            pstmt.setString(7, entry.getBZSJXJBM());
            pstmt.setLong(8, entry.getSJTS());
            pstmt.setLong(9, entry.getSJ_DZWJDX());
            pstmt.setString(10, entry.getSJQSBH());
            pstmt.setString(11, entry.getSJJWBH());
            pstmt.setString(12, entry.getSJJYZ());
            pstmt.setString(13, entry.getSJJYSFMC());
            pstmt.setString(14, entry.getSCSB_DZDBH());
            pstmt.setInt(15, Integer.parseInt(entry.getSCSJ_RQSJ()));
            pstmt.setString(16, entry.getSJTGFGLY_XM());
            pstmt.setString(17, entry.getSJTGFGLY_LXDH());
            pstmt.setString(18, entry.getXXRWBH());
            pstmt.setString(19, entry.getJobId());
            pstmt.setString(20, entry.getBatchId());
            pstmt.addBatch();
            count++;
        }
        return count;
    }

    private void deleteAndInsertDayStatistics(List<IssueDayStatistics> dayStatistics, String startDate) throws SQLException {
        Connection connection = null;
        try {
            connection = oracleDataSource.getConnection(3000);
            connection.setAutoCommit(false);
            //先删除后入库
            deleteStatistics(connection, startDate);
            insertStatistics(connection, dayStatistics);
            connection.commit();
            //同步到缓存
            ConcurrentMap<String, List<IssueDayStatistics>> issueDayStatisticsMap = hazelcastInstance.getMap(Constants.ISSUE_DAY_STATISTICS);
            for (IssueDayStatistics dayStatistic : dayStatistics) {
                String key = dayStatistic.getJobId() + "_" + dayStatistic.getDataNameEn();
                List<IssueDayStatistics> issueDayStatistics = null;
                issueDayStatistics = issueDayStatisticsMap.get(key);
                if (issueDayStatistics != null) {
                    issueDayStatistics.removeIf(t -> startDate.equals(DateParamUtil.getDayStart(t.getCheckTime())));
                    issueDayStatistics.add(dayStatistic);
                } else {
                    List<IssueDayStatistics> dayStatisticsList = new ArrayList<>();
                    issueDayStatistics = dayStatisticsList;
                    issueDayStatistics.add(dayStatistic);
                    issueDayStatisticsMap.put(key, issueDayStatistics);
                }
            }
            log.info("已批量插入 下发对账统计记录[" + dayStatistics.size() + "]条");
        } catch (SQLException e) {
            log.error("插入对账分析记录失败，原因：{}" + e.getMessage());
            if (connection != null){
                connection.rollback();
            }
            throw e;
        }finally {
            if (connection != null){
                connection.close();
            }
        }
    }

    private void insertStatistics(Connection connection, List<IssueDayStatistics> dayStatistics) throws SQLException {
        PreparedStatement pstmt = null;
        try{
            String insertSql = "insert into DAC_ISSUE_DAY_STATISTICS(JOB_ID,CHECK_TIME,DATA_NAME_ZH,DATA_NAME_EN,IS_PARTITION,ACCEPT_CITY,EXCHANGE_POSITION,SOURCE_COUNT,PROVIDER_READ_COUNT,PROVIDER_WRITE_COUNT," +
                    "ACCEPTOR_READ_COUNT,ACCEPTOR_WRITE_COUNT,DEST_COUNT,COURSE_CHAIN_RATIO,HISTORICAL_COMPARISON,PROVIDER_START_TIME,PROVIDER_END_TIME,ACCEPTOR_START_TIME,ACCEPTOR_END_TIME,PROVIDER_DURATION," +
                    "ACCEPTOR_DURATION,ALL_DURATION,TASK_STATE,UPDATE_RESULT,UPDATE_RESULT_REASON,BASE_TIME,BASE_TIME_ENABLE,EXCEPTION_FIELD) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(insertSql);

            int count = 0;
            for (IssueDayStatistics entry : dayStatistics) {
                //赋值
                pstmt.setString(1, entry.getJobId());
                pstmt.setDate(2, new java.sql.Date(entry.getCheckTime().getTime()));
                pstmt.setString(3, entry.getDataNameZh());
                pstmt.setString(4, entry.getDataNameEn());
                if (entry.getIsPartition() != null) {
                    pstmt.setInt(5, entry.getIsPartition());
                } else {
                    pstmt.setInt(5, 0);
                }
                pstmt.setString(6, entry.getAcceptCity());
                pstmt.setString(7, entry.getExchangePosition());
                if (entry.getSourceCount() != null) {
                    pstmt.setLong(8, entry.getSourceCount());
                } else {
                    pstmt.setNull(8, Types.BIGINT);
                }
                if (entry.getProviderReadCount() != null) {
                    pstmt.setLong(9, entry.getProviderReadCount());
                } else {
                    pstmt.setNull(9, Types.BIGINT);
                }
                if (entry.getProviderWriteCount() != null) {
                    pstmt.setLong(10, entry.getProviderWriteCount());
                } else {
                    pstmt.setNull(10, Types.BIGINT);
                }
                if (entry.getAcceptorReadCount() != null) {
                    pstmt.setLong(11, entry.getAcceptorReadCount());
                } else {
                    pstmt.setNull(11, Types.BIGINT);
                }
                if (entry.getAcceptorWriteCount() != null) {
                    pstmt.setLong(12, entry.getAcceptorWriteCount());
                } else {
                    pstmt.setNull(12, Types.BIGINT);
                }
                if (entry.getDestCount() != null) {
                    pstmt.setLong(13, entry.getDestCount());
                } else {
                    pstmt.setNull(13, Types.BIGINT);
                }
                pstmt.setString(14, entry.getCourseChainRatio());
                pstmt.setString(15, entry.getHistoricalComparison());
                if (entry.getProviderStartTime() != null) {
                    pstmt.setTimestamp(16, new Timestamp(entry.getProviderStartTime().getTime()));
                } else {
                    pstmt.setTimestamp(16, null);
                }
                if (entry.getProviderEndTime() != null) {
                    pstmt.setTimestamp(17, new Timestamp(entry.getProviderEndTime().getTime()));
                } else {
                    pstmt.setTimestamp(17, null);
                }
                if (entry.getAcceptorStartTime() != null) {
                    pstmt.setTimestamp(18, new Timestamp(entry.getAcceptorStartTime().getTime()));
                } else {
                    pstmt.setTimestamp(18, null);
                }
                if (entry.getAcceptorEndTime() != null) {
                    pstmt.setTimestamp(19, new Timestamp(entry.getAcceptorEndTime().getTime()));
                } else {
                    pstmt.setTimestamp(19, null);
                }
                if (entry.getProviderDuration() != null) {
                    pstmt.setLong(20, entry.getProviderDuration());
                } else {
                    pstmt.setNull(20, Types.BIGINT);
                }
                if (entry.getAcceptorDuration() != null) {
                    pstmt.setLong(21, entry.getAcceptorDuration());
                } else {
                    pstmt.setNull(21, Types.BIGINT);
                }
                if (entry.getAllDuration() != null) {
                    pstmt.setLong(22, entry.getAllDuration());
                } else {
                    pstmt.setNull(22, Types.BIGINT);
                }
                pstmt.setString(23, entry.getTaskState());
                pstmt.setInt(24, entry.getUpdateResult());
                pstmt.setString(25, entry.getUpdateResultReason());
                if (entry.getBaseTime() != null) {
                    pstmt.setString(26, entry.getBaseTime());
                } else {
                    pstmt.setString(26, null);
                }
                if (entry.getBaseTimeEnable() != null) {
                    pstmt.setInt(27, entry.getBaseTimeEnable());
                } else {
                    pstmt.setInt(27, 0);
                }
                if (entry.getExceptionField() != null) {
                    pstmt.setString(28, entry.getExceptionField());
                } else {
                    pstmt.setString(28, null);
                }
                pstmt.addBatch();
                count++;
                if (count % 1000 == 0) {
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
        }catch (SQLException e){
            throw e;
        }finally {
            if (pstmt != null){
                pstmt.close();
            }
        }
    }

    private void deleteStatistics(Connection connection,String startDate) throws SQLException {
        Statement statement = null;
        try {
            String deleteSql = String.format("DELETE FROM DAC_ISSUE_DAY_STATISTICS WHERE CHECK_TIME=TO_DATE('%s', 'yyyy-MM-dd HH24:mi:ss')", startDate);
            statement = connection.createStatement();
            statement.execute(deleteSql);

            log.info("成功清空日期[{}]的下发对账统计记录[{}]条",startDate, statement.getUpdateCount());
        }catch (SQLException e){
            log.error("删除{}日期的下发对账统计记录失败，原因：{}", startDate, e);
            throw e;
        }finally {
            if (statement != null){
                statement.close();
            }
        }
    }

    private IssueDayStatistics doStatistics(IssueReconBill issueReconBill, String startDate) throws ParseException {
        IssueDayStatistics issueDayStatistics = new IssueDayStatistics();
        issueDayStatistics.setCheckTime(DateUtil.parseDateTime(startDate));
        issueDayStatistics.setJobId(issueReconBill.getProviderJobId());
        issueDayStatistics.setDataNameEn(issueReconBill.getDataSourceName());
        issueDayStatistics.setAcceptCity(issueReconBill.getLocal());
        issueDayStatistics.setExchangePosition(issueReconBill.getSourceLocation());
        //基线时间 定时缓存
        IssueBaseTime issueBaseTime = new IssueBaseTime();
        issueBaseTime.setJobId(issueReconBill.getProviderJobId());
        issueBaseTime.setDataNameEn(issueReconBill.getDataSourceName());
        ConcurrentMap<String, IssueBaseTime> issueBaseTimeMap = hazelcastInstance.getMap(Constants.ISSUE_BASE_TIME_MAP);
        String key = issueBaseTime.getJobId() + "_" + issueBaseTime.getDataNameEn();
        IssueBaseTime baseTime = issueBaseTimeMap.get(key);
        if (null != baseTime) {
            issueDayStatistics.setBaseTime(baseTime.getBaseTime());
            issueDayStatistics.setBaseTimeEnable(baseTime.getBaseTimeEnable());
        }
        Long sourceCount = issueReconBill.getProviderReadCount();
        Long providerReadCount = issueReconBill.getProviderReadCount();
        Long providerWriteCount = issueReconBill.getProviderWriteCount();
        Long acceptorReadCount = issueReconBill.getAcceptorReadCount();
        Long acceptorWriteCount = issueReconBill.getAcceptorWriteCount();
        Long destCount = issueReconBill.getAcceptorWriteCount();
        //数据名及分区表
        setDataInfo(issueReconBill, issueDayStatistics);
        //环比同比  sourceCount destCount
        setCount(startDate, issueDayStatistics, issueBaseTime, sourceCount, providerReadCount, providerWriteCount, acceptorReadCount, acceptorWriteCount, destCount);
        //任务状态
        ConcurrentMap<String, List<RelateJob>> taskInfoMap = hazelcastInstance.getMap(Constants.TASK_INFO_MAP);
        RelateJob providerRelateJob = null;
        RelateJob acceptorRelateJob = null;
        List<RelateJob> providerInfos = null;
        List<RelateJob> acceptorInfos = null;
        if(issueReconBill.getProviderJobId() != null) {
            providerInfos = taskInfoMap.get(issueReconBill.getProviderJobId());
        }
        if(issueReconBill.getAcceptorJobId() != null) {
            acceptorInfos = taskInfoMap.get(issueReconBill.getAcceptorJobId());
        }

        if (providerInfos != null) {
            String providerBatchId = issueReconBill.getProviderBatchId();
            if (StringUtils.isNotBlank(providerBatchId)) {
                if (providerBatchId.contains("[")) {
                    Date maxProviderDate = null;
                    Date minProviderDate = null;
                    String status = null;
                    List<RelateJob> relateJobs = new ArrayList<>();
                    List<String> strings = JSONObject.parseArray(providerBatchId, String.class);
                    for (String s : strings) {
                        RelateJob relateJob = getRelateJob(providerInfos, s);
                        relateJobs.add(relateJob);
                    }
                    Optional<RelateJob> min = relateJobs.stream().filter(Objects::nonNull).filter(t -> t.getDataStartTime() != null).min(Comparator.comparing(s -> s.getDataStartTime().getTime()));
                    Optional<RelateJob> max = relateJobs.stream().filter(Objects::nonNull).filter(t -> t.getDataEndTime() != null).max(Comparator.comparing(s -> s.getDataEndTime().getTime()));
                    providerRelateJob = new RelateJob();
                    if (min.isPresent()) {
                        minProviderDate = min.get().getDataStartTime();
                    }
                    if (max.isPresent()) {
                        maxProviderDate = max.get().getDataEndTime();
                        status = max.get().getStatus();
                    }
                    if (maxProviderDate != null) {
                        providerRelateJob.setDataEndTime(maxProviderDate);
                    }
                    if (minProviderDate != null) {
                        providerRelateJob.setDataStartTime(minProviderDate);
                    }
                    if (StringUtils.isNotBlank(status)) {
                        providerRelateJob.setStatus(status);
                    }
                } else {
                    providerRelateJob = getRelateJob(providerInfos, providerBatchId);
                }
            }
        }
        if (acceptorInfos != null) {
            String acceptorBatchId = issueReconBill.getAcceptorBatchId();
            if (StringUtils.isNotBlank(acceptorBatchId)) {
                if (acceptorBatchId.contains("[")) {
                    Date maxAcceptorDate = null;
                    Date minAcceptorDate = null;
                    String status = null;
                    List<RelateJob> relateJobs = new ArrayList<>();
                    List<String> strings = JSONObject.parseArray(acceptorBatchId, String.class);
                    for (String s : strings) {
                        RelateJob relateJob = getRelateJob(acceptorInfos, s);
                        relateJobs.add(relateJob);
                    }
                    Optional<RelateJob> min = relateJobs.stream().filter(Objects::nonNull).filter(t -> t.getDataStartTime() != null).min(Comparator.comparing(s -> s.getDataStartTime().getTime()));
                    Optional<RelateJob> max = relateJobs.stream().filter(Objects::nonNull).filter(t -> t.getDataStartTime() != null).max(Comparator.comparing(s -> s.getDataStartTime().getTime()));
                    acceptorRelateJob = new RelateJob();
                    if (min.isPresent()) {
                        minAcceptorDate = min.get().getDataStartTime();
                    }
                    if (max.isPresent()) {
                        maxAcceptorDate = max.get().getDataEndTime();
                        status = max.get().getStatus();
                    }
                    if (maxAcceptorDate != null) {
                        acceptorRelateJob.setDataEndTime(maxAcceptorDate);
                    }
                    if (minAcceptorDate != null) {
                        acceptorRelateJob.setDataStartTime(minAcceptorDate);
                    }
                    if (StringUtils.isNotBlank(status)) {
                        acceptorRelateJob.setStatus(status);
                    }
                } else {
                    acceptorRelateJob = getRelateJob(acceptorInfos, acceptorBatchId);
                }
            }
        }
        Date providerStartTime = null;
        Date providerEndTime = null;
        Date acceptorStartTime = null;
        Date acceptorEndTime = null;
        if (providerRelateJob != null) {
            providerStartTime = providerRelateJob.getDataStartTime();
            providerEndTime = providerRelateJob.getDataEndTime();
        }
        if (acceptorRelateJob != null) {
            acceptorStartTime = acceptorRelateJob.getDataStartTime();
            acceptorEndTime = acceptorRelateJob.getDataEndTime();
        }
        setTaskState(issueDayStatistics, providerRelateJob, acceptorRelateJob);
        //计算时长
        calDateDuration(issueDayStatistics, providerStartTime, providerEndTime, acceptorStartTime, acceptorEndTime);

        //获取完整的基线时间
        String substring = startDate.substring(0, 10);
        Date endBaseTime = null;
        if (null != baseTime && baseTime.getBaseTimeEnable() != null && baseTime.getBaseTimeEnable().equals(1)) {
            endBaseTime  = DateUtil.parseDate(substring + " " + baseTime.getBaseTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
        }
        // 更新结果 结果详情 字段
        setUpdateResult(issueDayStatistics, sourceCount, providerReadCount, providerWriteCount, acceptorReadCount, acceptorWriteCount, destCount, providerEndTime, acceptorEndTime, endBaseTime);
        // 添加异常字段
        setExceptionField(issueDayStatistics, sourceCount, providerReadCount, providerWriteCount, acceptorReadCount, acceptorWriteCount, destCount, providerStartTime, providerEndTime, acceptorStartTime, acceptorEndTime, endBaseTime);
        return issueDayStatistics;
    }

    private RelateJob getRelateJob(List<RelateJob> providerInfos, String batchId) {
        RelateJob relateJob = new RelateJob();
        Map<String, List<RelateJob>> providerCollect = providerInfos.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(RelateJob::getBatchId));
        List<RelateJob> providerRelateJobs = providerCollect.get(batchId);
        if (providerRelateJobs != null && providerRelateJobs.size() > 0) {
            relateJob = providerRelateJobs.get(0);
        }
        return relateJob;
    }

    private void setDataInfo(IssueReconBill issueReconBill, IssueDayStatistics issueDayStatistics) {
        Map<String, List<SourceTableCache>> sourceTableMap = hazelcastInstance.getMap(Constants.SOURCE_TABLE_INFO);
        List<SourceTableCache> sourceTableCaches = null;
        if (issueReconBill.getDataSourceName() != null) {
            sourceTableCaches  = sourceTableMap.get(issueReconBill.getDataSourceName());
        }
        if (null != sourceTableCaches && sourceTableCaches.size() > 0) {
            SourceTableCache sourceTableCache = sourceTableCaches.get(0);
            issueDayStatistics.setDataNameZh(sourceTableCache.getObjectName());
            Integer partitionRecon = sourceTableCache.getPartitionRecon();
            issueDayStatistics.setIsPartition(0);
            if (partitionRecon == null) {
                issueDayStatistics.setIsPartition(partitionRecon);
            } else if (partitionRecon == 1 || partitionRecon == 2) {
                issueDayStatistics.setIsPartition(1);
            }
        }
    }

    private void setCount(String startDate, IssueDayStatistics issueDayStatistics, IssueBaseTime issueBaseTime, Long sourceCount, Long providerReadCount, Long providerWriteCount, Long acceptorReadCount, Long acceptorWriteCount, Long destCount) throws ParseException {
        issueDayStatistics.setSourceCount(sourceCount);
        issueDayStatistics.setProviderReadCount(providerReadCount);
        issueDayStatistics.setProviderWriteCount(providerWriteCount);
        issueDayStatistics.setAcceptorReadCount(acceptorReadCount);
        issueDayStatistics.setAcceptorWriteCount(acceptorWriteCount);
        issueDayStatistics.setDestCount(destCount);
        DecimalFormat df = new DecimalFormat("0.00");
        if (issueDayStatistics.getDestCount() != null && issueDayStatistics.getDestCount() != null) {
            float ratio = issueDayStatistics.getDestCount().floatValue() / issueDayStatistics.getSourceCount().floatValue();
            String format = df.format(ratio * 100);
            issueDayStatistics.setCourseChainRatio(format + "%");
        }
        if (issueDayStatistics.getSourceCount() != null) {
            ConcurrentMap<String, List<IssueDayStatistics>> issueDayStatisticsMap = hazelcastInstance.getMap(Constants.ISSUE_DAY_STATISTICS);
            String key = issueDayStatistics.getJobId() + "_" + issueDayStatistics.getDataNameEn();
            List<IssueDayStatistics> statisticsList = issueDayStatisticsMap.get(key);
            Date startTime = DateUtil.parseDateTime(startDate);
            Date endTime = DateUtil.addDay(startTime, -7);
            Long count = 0L;
            Long days = 0L;
            if (statisticsList != null && statisticsList.size() > 0) {
                for (IssueDayStatistics dayStatistics : statisticsList) {
                    if (dayStatistics.getCheckTime() == null || dayStatistics.getCheckTime().getTime() >= startTime.getTime() || dayStatistics.getCheckTime().getTime() < endTime.getTime()) {
                        continue;
                    }
                    count+=dayStatistics.getSourceCount();
                    days++;
                }
            }
            if (count != 0 && days != 0) {
                float ratio = issueDayStatistics.getSourceCount().floatValue() / (count.floatValue()/days);
                String format = df.format(ratio * 100);
                issueDayStatistics.setHistoricalComparison(format + "%");
            }
        }
    }

    private void calDateDuration(IssueDayStatistics issueDayStatistics, Date providerStartTime, Date providerEndTime, Date acceptorStartTime, Date acceptorEndTime) {
        issueDayStatistics.setProviderStartTime(providerStartTime);
        issueDayStatistics.setProviderEndTime(providerEndTime);
        issueDayStatistics.setAcceptorStartTime(acceptorStartTime);
        issueDayStatistics.setAcceptorEndTime(acceptorEndTime);
        if (providerStartTime != null && providerEndTime != null) {
           issueDayStatistics.setProviderDuration((providerEndTime.getTime() - providerStartTime.getTime())/1000);
        }
        if (acceptorStartTime != null && acceptorEndTime != null) {
            issueDayStatistics.setAcceptorDuration((acceptorEndTime.getTime() - acceptorStartTime.getTime())/1000);
        }
        if (providerStartTime != null && acceptorEndTime != null) {
            issueDayStatistics.setAllDuration((acceptorEndTime.getTime() - providerStartTime.getTime())/1000);
        }
    }

    private void setTaskState(IssueDayStatistics issueDayStatistics, RelateJob providerRelateJob, RelateJob acceptorRelateJob) {
        String acceptorStatus = "2";
        String providerStatus = "2";
        if (null != providerRelateJob) {
            providerStatus = providerRelateJob.getStatus();
            if (StringUtils.isNotBlank(providerStatus)) {
                if ("SUCCESS".equalsIgnoreCase(providerStatus)) {
                    providerStatus = "1";
                } else if ("FAIL".equalsIgnoreCase((providerStatus))) {
                    providerStatus = "0";
                }
            } else {
                providerStatus = "2";
            }
        }
        if (null != acceptorRelateJob) {
            acceptorStatus = acceptorRelateJob.getStatus();
            if(StringUtils.isNotBlank(acceptorStatus)) {
                if ("SUCCESS".equalsIgnoreCase(acceptorStatus)) {
                    acceptorStatus = "1";
                } else if ("FAIL".equalsIgnoreCase(acceptorStatus)) {
                    acceptorStatus = "0";
                }
            } else {
                acceptorStatus = "2";
            }
        }
        issueDayStatistics.setTaskState(providerStatus + acceptorStatus);
    }

    private void setUpdateResult(IssueDayStatistics issueDayStatistics, Long sourceCount, Long providerReadCount, Long providerWriteCount, Long acceptorReadCount, Long acceptorWriteCount, Long destCount, Date providerEndTime, Date acceptorEndTime, Date endBaseTime) {
        StringBuilder updateResultReason = new StringBuilder();
        Integer updateResult = 1;
        if (sourceCount != null && !sourceCount.equals(providerReadCount)) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("总队读取数据量异常");
            updateResult = 0;
        }
        if (providerReadCount != null && !providerReadCount.equals(providerWriteCount)) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("总队推送数据量异常");
            updateResult = 0;
        }
        if (providerWriteCount != null && !providerWriteCount.equals(acceptorReadCount)) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("地市接收数据量异常");
            updateResult = 0;
        }
        if (acceptorReadCount != null && !acceptorReadCount.equals(acceptorWriteCount)) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("地市入库数据量异常");
            updateResult = 0;
        }
        if (acceptorWriteCount != null && !acceptorWriteCount.equals(destCount)) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("地市写入数据量异常");
            updateResult = 0;
        }
        if (providerEndTime != null && endBaseTime != null && providerEndTime.getTime() > endBaseTime.getTime()) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("总队推送延迟");
            updateResult = 0;
        }
        if (acceptorEndTime != null && endBaseTime != null && acceptorEndTime.getTime() > endBaseTime.getTime()) {
            if (updateResultReason.length() > 0) {
                updateResultReason.append(",");
            }
            updateResultReason.append("地市入库延迟");
            updateResult = 0;
        }
        issueDayStatistics.setUpdateResult(updateResult);
        issueDayStatistics.setUpdateResultReason(updateResultReason.toString());
    }

    private void setExceptionField(IssueDayStatistics issueDayStatistics, Long sourceCount, Long providerReadCount, Long providerWriteCount, Long acceptorReadCount, Long acceptorWriteCount, Long destCount, Date providerStartTime, Date providerEndTime, Date acceptorStartTime, Date acceptorEndTime, Date endBaseTime) {
        StringBuffer exceptionField = new StringBuffer();
        if (!sourceCount.equals(providerReadCount)) {
            exceptionField.append("providerReadCount").append(",providerWriteCount").append(",acceptorReadCount").append(",acceptorWriteCount").append(",destCount");
        }
        if (sourceCount.equals(providerReadCount) && !providerReadCount.equals(providerWriteCount)) {
            exceptionField.append("providerWriteCount").append(",acceptorReadCount").append(",acceptorWriteCount").append(",destCount");
        }
        if (sourceCount.equals(providerReadCount) && providerReadCount.equals(providerWriteCount) && !providerWriteCount.equals(acceptorReadCount)) {
            exceptionField.append("acceptorReadCount").append(",acceptorWriteCount").append(",destCount");
        }
        if (sourceCount.equals(providerReadCount) && providerReadCount.equals(providerWriteCount) && providerWriteCount.equals(acceptorReadCount)
                && !acceptorReadCount.equals(acceptorWriteCount)) {
            exceptionField.append("acceptorWriteCount").append(",destCount");
        }
        if (sourceCount.equals(providerReadCount) && providerReadCount.equals(providerWriteCount) && providerWriteCount.equals(acceptorReadCount)
                && acceptorReadCount.equals(acceptorWriteCount) && !acceptorWriteCount.equals(destCount)) {
            exceptionField.append("destCount");
        }
        if (null != endBaseTime) {
            if (providerStartTime != null && providerStartTime.getTime() > endBaseTime.getTime()) {
                exceptionField.append("providerStartTime");
            }
            if (providerEndTime != null && providerEndTime.getTime() > endBaseTime.getTime()) {
                exceptionField.append("providerEndTime");
            }
            if (acceptorStartTime != null && acceptorStartTime.getTime() > endBaseTime.getTime()) {
                exceptionField.append("acceptorStartTime");
            }
            if (acceptorEndTime != null && acceptorEndTime.getTime() > endBaseTime.getTime()) {
                exceptionField.append("acceptorEndTime");
            }
        }
        if (!"100.00%".equals(issueDayStatistics.getCourseChainRatio())) {
            exceptionField.append("courseChainRatio");
        }
        issueDayStatistics.setExceptionField(exceptionField.toString());
    }
}
