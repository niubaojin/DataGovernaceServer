package com.synway.reconciliation.schedule.consumer.api;

import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.conditional.NonIssueCondition;
import com.synway.reconciliation.config.HazelcastLock;
import com.synway.reconciliation.service.ReceiveBillService;
import com.synway.reconciliation.util.DateUtil;
import com.synway.reconciliation.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;

/**
 * API接收账单方式 读取文件并账单入库 定时处理
 * @author DZH
 */
@Component
@Conditional(NonIssueCondition.class)
public class ApiConsumerSchedule {

    /**
     * 账单根路径
     */
    @Value("${billPath}")
    private String billPath;

    /**
     * 账单文件超时天数
     */
    @Value("${billFileExpireTime}")
    private int billFileExpireTime;

    @Autowired
    private ReceiveBillService receiveBillService;

    /**
     * 定时获取账单
     */
    @Scheduled(cron = "${consumerBillSchedule}")
    @HazelcastLock(lockName = Constants.CONSUMER_BILL_SCHEDULE, methodValue = "定时读取账单文件入库")
    public void consumerBillSchedule() {
        Calendar calendar = Calendar.getInstance();
        String dateDir = DateUtil.formatDate(calendar.getTime(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        receiveBillService.consumerBill(billPath, dateDir);
    }

    /**
     * 处理跨天有可能产生的文件数据
     */
    @Scheduled(cron = "${handelHistoryBillData}")
    @HazelcastLock(lockName = Constants.HANDEL_HISTORY_BILL_DATA, methodValue = "处理实例账单数据")
    public void handelHistoryBillData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String dateDir = DateUtil.formatDate(calendar.getTime(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        receiveBillService.consumerBill(billPath, dateDir);
    }

    /**
     * 定时清理磁盘账单文件
     */
    @Scheduled(cron = "0 0 */3 * * ?")
    @HazelcastLock(lockName = Constants.CLEAN_BILL_FILE, methodValue = "定时清理账单文件")
    public void cleanBillFile() {
        Calendar calendar = Calendar.getInstance();
        // 天数可配
        calendar.add(Calendar.DAY_OF_WEEK, -billFileExpireTime);
        String limitDate = DateUtil.formatDate(calendar.getTime(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        File file = new File(billPath);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f.getName().compareTo(limitDate) < 0) {
                    String dir = String.format("%s%s%s", billPath, File.separator, f.getName());
                    FileUtil.delDir(dir, true);
                }
            }
        }
    }
}
