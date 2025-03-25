package com.synway.datarelation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 因为odps数据源是自定义的
 * @author wangdongwei
 */

@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class DataRelationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DataRelationApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DataRelationApplication.class);
	}
}
