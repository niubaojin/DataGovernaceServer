package com.synway.reconciliation.common;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.synway.reconciliation.service.CacheManageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 程序启动后执行业务处理
 * @author ywj
 */
@Order(1)
@Component
public class ReconciliationRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ReconciliationRunner.class);

    @Autowired
    private CacheManageService cacheManageService;

    @Autowired(required = false)
    private NacosAutoServiceRegistration registration;

    /**
     * web容器端口
     */
    @Value("${server.port}")
    Integer port;

    @Value("${isIssue}")
    private String isIssue;

    @Override
    public void run(String... args) throws Exception {

        if (registration != null && port != null) {
            registration.setPort(port);
            registration.start();
        }

        if (StringUtils.equalsIgnoreCase(isIssue, Boolean.TRUE.toString())) {
            cacheManageService.cacheSourceTable();
            logger.info("缓存标准表完成");
            cacheManageService.cacheIssueBaseTime();
            logger.info("缓存下发基线时间完成");
            cacheManageService.cacheIssueHistoryBill();
            logger.info("缓存下发统计账单完成");
            cacheManageService.cacheRelateJob();
            logger.info("缓存任务相关信息完成");
            cacheManageService.cacheTaskInfoTag();
            logger.info("缓存任务执行实例标志");
            cacheManageService.getAllCodes();
            logger.info("缓存数据下发行政区划编码完成");
        } else {
            cacheManageService.cacheBillRelateInfo();
            logger.info("缓存账单相关信息完成");
        }

    }
}
