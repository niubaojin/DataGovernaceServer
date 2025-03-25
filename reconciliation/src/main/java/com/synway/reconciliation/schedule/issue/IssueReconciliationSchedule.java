package com.synway.reconciliation.schedule.issue;

import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.conditional.IssueCondition;
import com.synway.reconciliation.conditional.KafkaMqCondition;
import com.synway.reconciliation.config.HazelcastLock;
import com.synway.reconciliation.service.CacheManageService;
import com.synway.reconciliation.service.IssueReconciliationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * @author DZH
 */
@Slf4j
@Component
@Conditional({IssueCondition.class, KafkaMqCondition.class})
public class IssueReconciliationSchedule {

    @Value("${isScheduledEnable}")
    private String isScheduledEnable;

    @Value("${isIssue}")
    private String isIssue;

    @Autowired
    private IssueKafkaManage issueKafkaManage;

    @Autowired
    private CacheManageService cacheManageService;

    @Autowired
    private IssueReconciliationService issueReconciliationService;

    /**
     * 下发对账统计
     * 每小时的40分钟
     */
    @HazelcastLock(lockName = Constants.ISSUE_RECON_STATISTICS, methodValue = "下发对账统计")
    @Scheduled(cron = "${issueReconStatisticsCron}")
    public void issueReconStatisticsSchedule() {
        if (!StringUtils.equalsIgnoreCase(isScheduledEnable, Boolean.TRUE.toString())) {
            return;
        }
        issueReconciliationService.issueReconciliationStatistics();
    }

    /**
     * 定时发送任务相关信息
     * 每小时的10分钟
     */
    @HazelcastLock(lockName = Constants.SEND_TASK_INFO, methodValue = "定时发送任务相关信息")
    @Scheduled(cron = "${sendTaskInfoCron}")
    public void sendTaskInfo(){
        if (!StringUtils.equalsIgnoreCase(isScheduledEnable, Boolean.TRUE.toString())) {
            return;
        }
        issueKafkaManage.sendTaskInfo();
    }

    /**
     * 定时接收任务相关信息
     * 每小时的20分钟
     */
    @HazelcastLock(lockName = Constants.CONSUMER_TASK_INFO, methodValue = "定时接收任务相关信息")
    @Scheduled(cron = "${consumerTaskInfoCron}")
    public void consumerTaskInfo(){
        if (!StringUtils.equalsIgnoreCase(isScheduledEnable, Boolean.TRUE.toString())) {
            return;
        }
        issueKafkaManage.consumeTaskInfo();
    }

    /**
     * 定时接收下发对账单
     * 每小时的30分钟
     */
    @HazelcastLock(lockName = Constants.CONSUMER_ISSUE_BILL, methodValue = "定时接收下发对账单")
    @Scheduled(cron = "${consumerIssueBillCron}")
    public void consumerIssueBill(){
        if (!StringUtils.equalsIgnoreCase(isScheduledEnable, Boolean.TRUE.toString())) {
            return;
        }
        issueKafkaManage.consumerIssueProviderBill();
        issueKafkaManage.consumerIssueAcceptorBill();
    }

    @Scheduled(cron = "${updateDataNameCache}")
    @HazelcastLock(lockName = Constants.UPDATE_DATA_NAME_CACHE, methodValue = "更新源表信息")
    public void updateDataNameCache(){
        if (!StringUtils.equalsIgnoreCase(isScheduledEnable, Boolean.TRUE.toString())) {
            return;
        }
        log.debug("更新缓存开始");
        cacheManageService.cacheSourceTable();
        log.debug("更新缓存结束");
    }
}
