package com.synway.reconciliation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ywj
 */
@Configuration
public class ConfigBean {

    /**
     * 缓存标准表名
     *
     * @return
     */
    @Bean
    public ConcurrentHashMap<String, String> sourceDataNameHashMap() {
        ConcurrentHashMap<String, String> sourceDataNameHashMap = new ConcurrentHashMap<>();
        return sourceDataNameHashMap;
    }

    /**
     * 缓存标准表名
     *
     * @return
     */
    @Bean
    public ConcurrentHashMap<String, String> targetDataNameHashMap() {
        ConcurrentHashMap<String, String> targetDataNameHashMap = new ConcurrentHashMap<>();
        return targetDataNameHashMap;
    }

    /**
     * 缓存源应用系统名称
     *
     * @return
     */
    @Bean
    public ConcurrentHashMap<String, String> sourceNameHashMap() {
        ConcurrentHashMap<String, String> sourceNameHashMap = new ConcurrentHashMap<>();
        return sourceNameHashMap;
    }
}
