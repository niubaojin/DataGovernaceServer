package com.synway.property.config;

import com.synway.property.common.UrlConstants;
import com.synway.property.dao.LifeCycleDao;
import com.synway.property.service.DataStorageMonitorService;
import com.synway.property.service.impl.LifeCycleServiceImpl;
import com.synway.property.util.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author majia
 * @version 1.0
 * @date 2020/9/14 14:21
 */
@Component
public class InitData implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(InitData.class);

    @Autowired
    private AsyManager asyManager;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private Environment environment;

    @Autowired
    private LifeCycleServiceImpl service;

    @Autowired
    private LifeCycleDao lifeCycleDao;

    @Autowired
    private DataStorageMonitorService dataStorageMonitorService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     */
    @Override
    public void run(ApplicationArguments args) {
        //获取数据组织的存储位置
        String storeOrganizationLocation = environment.getProperty("storeOrganizationLocation");
        if (StringUtils.isNotBlank(storeOrganizationLocation)) {
            asyManager.addTask(() -> {
                //格式为 y=x|s=t
                String[] locations = storeOrganizationLocation.split("\\|");
                for (String location : locations) {
                    String[] temp = location.split("=");
                    cacheManager.addOrUpdateCache("storeOrganizationLocation_" + temp[0], temp[1]);
                }
            });
        }
        dataStorageMonitorService.getDataBaseStatus();
        asyManager.addTask(() -> {
            int num = lifeCycleDao.getCount();
            if (num == 0) {
                cacheManager.addOrUpdateCache("updateAllValDensity", true);
                service.updateAllValDensity(true);
                cacheManager.addOrUpdateCache("updateAllValDensity", false);
            }
        });
        // 获取数据中心信息（本地仓）
        String result = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0", String.class);
        if(StringUtils.isNotBlank(result) && result.toLowerCase().contains("odps")){
            cacheManager.addOrUpdateCache("dataPlatFormType", "aliyun");
        } else {
            cacheManager.addOrUpdateCache("dataPlatFormType", "huaweiyun");
        }

        // 数据库类型
        cacheManager.addOrUpdateCache("dsType", environment.getProperty("database.type"));
    }

}
