package com.synway.reconciliation.schedule.recon;

import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.conditional.NonIssueCondition;
import com.synway.reconciliation.config.HazelcastLock;
import com.synway.reconciliation.service.CacheManageService;
import com.synway.reconciliation.service.InventoryReconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 盘点对账
 * @author Administrator
 */
@Component
@Conditional(NonIssueCondition.class)
public class InventoryReconSchedule {

    @Autowired
    private InventoryReconService inventoryReconService;

    @Autowired
    private CacheManageService cacheManageService;

    /**
     * 定时汇总账单表
     */
    @HazelcastLock(lockName = Constants.SUMMARIZE_BILL, methodValue = "定时汇总原始账单到账单表")
    @Scheduled(cron = "${summarizeBill}")
    public void summarizeBillByInstance() {
        inventoryReconService.summarizeBillByInstance();
    }

    /**
     * 定时按实例对账
     */
    @HazelcastLock(lockName = Constants.DATA_RECON_BY_INSTANCE, methodValue = "定时按实例对账")
    @Scheduled(cron = "${dataReconByInstance}")
    public void reconByInstance() {
        inventoryReconService.reconByInstance();
    }

    /**
     * 定时生成盘点对账单
     */
    @HazelcastLock(lockName = Constants.BUILD_INVENTORY_BILL, methodValue = "定时生成盘点对账单")
    @Scheduled(cron = "${buildInventoryBill}")
    public void inventoryRecon() {
        inventoryReconService.inventoryRecon();
    }

    /**
     * 数据账单统计
     */
    @HazelcastLock(lockName = Constants.BILL_DATA_STATISTICS, methodValue = "定时统计账单数据")
    @Scheduled(cron = "${billDataStatistics}")
    public void dataBillStatistics() {
        inventoryReconService.reconStatistics();
    }

    /**
     * 对账分析
     */
    @HazelcastLock(lockName = Constants.BILL_DATA_ANALYSIS, methodValue = "定时分析账单数据")
    @Scheduled(cron = "${billDataAnalysis}")
    public void reconAnalysis() {
        inventoryReconService.reconAnalysis();
    }

    /**
     * 定时更新账单相关信息
     * @return void
     */
    @HazelcastLock(lockName = Constants.UPDATE_DS_DETECTED_TABLE, methodValue = "定时更新账单相关信息")
    @Scheduled(cron = "${updateDsDetectedTable}")
    public void updateDsDetectedTable(){
        cacheManageService.cacheBillRelateInfo();
    }
}
