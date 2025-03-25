package com.synway.datarelation.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nacos.NacosDiscoveryProperties;
import com.hazelcast.nacos.NacosDiscoveryStrategyFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
/**
 * Hazelcast 的相关配置信息
 * @author wangdongwei
 * @date 2021/3/17 10:38
 */
@Configuration
public class HazelcastConfig {

    @Autowired
    private Environment environment;

    @Bean
    public HazelcastInstance hazelcastConfigSertvice(){
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.setProperty("hazelcast.discovery.enabled", "true");
        String serverAddr = environment.getProperty("nacosServer.serverAddr");
        String applicationName = environment.getProperty("spring.application.name");
        String namespace = environment.getProperty("nacosServer.namespace");
        String username = environment.getProperty("nacosServer.username");
        String password = environment.getProperty("nacosServer.password");
        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new NacosDiscoveryStrategyFactory())
                .addProperty(NacosDiscoveryProperties.SERVER_ADDR.key(), serverAddr)
                .addProperty(NacosDiscoveryProperties.APPLICATION_NAME.key(), applicationName+"_hazelcast")
                .addProperty(NacosDiscoveryProperties.NAMESPACE.key(), namespace);
        if(StringUtils.isNotEmpty(username)){
            discoveryStrategyConfig.addProperty(NacosDiscoveryProperties.USERNAME.key(), username);
        }
        if(StringUtils.isNotEmpty(password)){
            discoveryStrategyConfig.addProperty(NacosDiscoveryProperties.PASSWORD.key(), password);
        }
        config.getNetworkConfig().getJoin().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        return Hazelcast.newHazelcastInstance(config);
    }


}
