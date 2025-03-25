package com.synway.dataoperations;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class NacosConfig implements ApplicationRunner {
    @Autowired(required = false)
    private NacosAutoServiceRegistration registration;
    //本项目中的端口配置项和web容器端口需保持一致
    @Value("${server.port}")
    Integer port;

    //重新注册服务
    @Override
    public void run(ApplicationArguments args) {

        if (registration != null && port != null) {
            registration.setPort(port);
            registration.start();
        }
    }
}
