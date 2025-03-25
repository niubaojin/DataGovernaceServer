package com.synway.governace;

import com.synway.governace.common.DataGovernanceRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@EnableCaching
@MapperScan(basePackages = {"com.synway.governace.dao"})
public class DataGovernanceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataGovernanceApplication.class,args);
    }

    @Bean
    public DataGovernanceRunner dataGovernanceRunner(){
        return new DataGovernanceRunner();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DataGovernanceApplication.class);
    }
}
