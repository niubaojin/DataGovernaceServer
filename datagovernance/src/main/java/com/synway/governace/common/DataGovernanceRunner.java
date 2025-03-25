package com.synway.governace.common;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.synway.governace.service.largeScreen.LargeScreenService;
import com.synway.governace.service.largeScreen.PropertyLargeScreenService;
import com.synway.governace.service.largeScreen.XJPropertyLargeScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

/**
 * 程序启动执行类
 *
 * @author ywj
 * @date 2020/7/23 10:58
 */
@Order(1)
public class DataGovernanceRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(DataGovernanceRunner.class);

    @Autowired
    private LargeScreenService largeScreenServiceImpl;

    @Autowired
    private PropertyLargeScreenService propertyLargeScreenService;
    @Autowired
    private XJPropertyLargeScreenService xjPropertyLargeScreenService;

    @Autowired(required = false)
    private NacosAutoServiceRegistration registration;

    // web容器端口
    @Value("${server.port}")
    Integer port;

    @Override
    public void run(String... args) throws Exception {

        if (registration != null && port != null) {
            registration.setPort(port);
            registration.start();
        }

        largeScreenServiceImpl.fetchYesterdayData();
        logger.info("获取数据大屏昨日数据完成");

        logger.info("开始获取通用资产大屏数据");
        propertyLargeScreenService.scheduledTask();

        //新疆资产大屏
        logger.info("开始获取新疆资产大屏数据");
        xjPropertyLargeScreenService.scheduledTaskXJ();
    }
}
