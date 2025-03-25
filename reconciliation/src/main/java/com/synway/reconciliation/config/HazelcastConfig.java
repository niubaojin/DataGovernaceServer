package com.synway.reconciliation.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nacos.NacosDiscoveryProperties;
import com.hazelcast.nacos.NacosDiscoveryStrategyFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author
 */
@Configuration
public class HazelcastConfig {
    private Logger logger = LoggerFactory.getLogger(HazelcastConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public HazelcastInstance hazelcastConfigService(){
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.setProperty("hazelcast.discovery.enabled", "true");
        String serverAddr = environment.getProperty("spring.cloud.nacos.discovery.server-addr");
        String applicationName = environment.getProperty("spring.application.name");
        String namespace = environment.getProperty("nacosServer.namespace");
        String userName = environment.getProperty("spring.cloud.nacos.discovery.username");
        String passWord = environment.getProperty("spring.cloud.nacos.discovery.password");
        logger.info("nacos userName:" + userName + " passWord:" + passWord);
        // 服务发现策略
        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new NacosDiscoveryStrategyFactory())
                .addProperty(NacosDiscoveryProperties.SERVER_ADDR.key(), serverAddr)
                .addProperty(NacosDiscoveryProperties.APPLICATION_NAME.key(), applicationName+"_hazelcast")
                .addProperty(NacosDiscoveryProperties.NAMESPACE.key(), namespace);
        if (StringUtils.isNotBlank(userName)) {
            discoveryStrategyConfig.addProperty(NacosDiscoveryProperties.USERNAME.key(), userName);
        }
        if (StringUtils.isNotBlank(passWord)) {
            discoveryStrategyConfig.addProperty(NacosDiscoveryProperties.PASSWORD.key(), passWord);
        }
        config.getNetworkConfig().getJoin().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);
        return Hazelcast.newHazelcastInstance(config);
    }
}
