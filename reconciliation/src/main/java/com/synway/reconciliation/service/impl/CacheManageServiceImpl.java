package com.synway.reconciliation.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.dao.CacheManageDao;
import com.synway.reconciliation.dao.IssueReconciliationDao;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.pojo.issue.*;
import com.synway.reconciliation.service.CacheManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author DZH
 */
@Slf4j
@Service
public class CacheManageServiceImpl implements CacheManageService {

    @Autowired
    private CacheManageDao cacheManageDao;

    @Autowired
    private IssueReconciliationDao issueReconciliationDao;

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public CacheManageServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public void cacheBillRelateInfo() {
        List<DsDetectedTable> allDetectedTableInfo = cacheManageDao.getAllDetectedTableInfo();
        ConcurrentMap<String, DsDetectedTable> billRelateInfoMap = hazelcastInstance.getMap(Constants.BILL_RELATE_INFO);
        if (!CollectionUtils.isEmpty(allDetectedTableInfo)) {
            Map<String, List<DsDetectedTable>> collect = allDetectedTableInfo.stream().filter(Objects::nonNull).filter(t -> t.getSourceCode() != null).collect(Collectors.groupingBy(DsDetectedTable::getSourceCode));
            for (Map.Entry<String, List<DsDetectedTable>> entry : collect.entrySet()) {
                if (entry.getValue() != null) {
                    billRelateInfoMap.put(entry.getKey(), entry.getValue().get(0));
                }
            }
        }
    }

    @Override
    public void cacheTaskInfoTag() {
        List<String> tags = issueReconciliationDao.getTaskInfoTag();
        String taskInfoTag = null;
        if (null != tags && tags.size() > 0) {
            taskInfoTag = tags.get(0);
        }
        ConcurrentMap<String, String> taskInfoTagMap = hazelcastInstance.getMap(Constants.TASK_INFO_TAG_MAP);
        if (StringUtils.isNotBlank(taskInfoTag)) {
            taskInfoTagMap.put("taskInfoTag", taskInfoTag);
        }
    }

    @Override
    public void cacheRelateJob() {
        List<RelateJob> relateJob = issueReconciliationDao.getRelateJob();
        ConcurrentMap<String, List<RelateJob>> taskInfoMap = hazelcastInstance.getMap(Constants.TASK_INFO_MAP);
        if (!CollectionUtils.isEmpty(relateJob)) {
            Map<String, List<RelateJob>> collect = relateJob.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(RelateJob::getJobId));
            taskInfoMap.putAll(collect);

        }
    }

    @Override
    public void cacheIssueHistoryBill() {
        List<IssueDayStatistics> allIssueBill = issueReconciliationDao.getAllIssueBill();
        ConcurrentMap<String, List<IssueDayStatistics>> issueDayStatisticsMap = hazelcastInstance.getMap(Constants.ISSUE_DAY_STATISTICS);
        if (!CollectionUtils.isEmpty(allIssueBill)) {
            Map<String, List<IssueDayStatistics>> collect = allIssueBill.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(t -> t.getJobId() + "_" + t.getDataNameEn()));
            issueDayStatisticsMap.putAll(collect);
        }
    }

    @Override
    public void cacheIssueBaseTime() {
        List<IssueBaseTime> allIssueBaseTime = issueReconciliationDao.getAllIssueBaseTime();
        ConcurrentMap<String, IssueBaseTime> issueBaseTimeMap = hazelcastInstance.getMap(Constants.ISSUE_BASE_TIME_MAP);
        if (!CollectionUtils.isEmpty(allIssueBaseTime)) {
            for (IssueBaseTime issueBaseTime : allIssueBaseTime) {
                String key = issueBaseTime.getJobId() + "_" + issueBaseTime.getDataNameEn();
                issueBaseTimeMap.put(key, issueBaseTime);
            }
        }
    }

    @Override
    public void cacheSourceTable() {
        ConcurrentMap<String, List<SourceTableCache>> sourceTableMap = hazelcastInstance.getMap(Constants.SOURCE_TABLE_INFO);
        List<SourceTableCache> sourceTable = cacheManageDao.getSourceTable();
        if (!CollectionUtils.isEmpty(sourceTable)) {
            Map<String, List<SourceTableCache>> collect = sourceTable.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(SourceTableCache::getTableName));
            sourceTableMap.clear();
            sourceTableMap.putAll(collect);
        }

    }

    @Override
    public void getAllCodes() {
        Map<Integer, List<AdministrativeDivisionCode>> divisionCodeMap = hazelcastInstance.getMap(Constants.ADMINISTRATIVE_DIVISION_CODE);
        List<AdministrativeDivisionCode> allCodes = cacheManageDao.getAllCodes();
        if (!CollectionUtils.isEmpty(allCodes)) {
            Map<Integer, List<AdministrativeDivisionCode>> collect = allCodes.stream().collect(Collectors.groupingBy(AdministrativeDivisionCode::getCode));
            divisionCodeMap.putAll(collect);
        }
    }
}
