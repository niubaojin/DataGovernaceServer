package com.synway.datastandardmanager;

import com.thebeastshop.aspectlog.enhance.AspectLogEnhance;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@Slf4j
//@MapperScan(basePackages = {"com.synway.datastandardmanager.mapper"})
public class DataStandardManagerApplication extends SpringBootServletInitializer {

    static {
        AspectLogEnhance.enhance();
    }

    public static void main(String[] args) {
        SpringApplication.run(DataStandardManagerApplication.class,args);
        log.info("程序运行成功!");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DataStandardManagerApplication.class);
    }

}
