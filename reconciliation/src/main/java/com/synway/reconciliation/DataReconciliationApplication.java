package com.synway.reconciliation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * DataReconciliationApplication启动类
 * @author ym
 */
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class DataReconciliationApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataReconciliationApplication.class,args);
    }
}
