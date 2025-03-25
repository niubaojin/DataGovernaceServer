package com.synway.property.config;

import com.synway.property.pojo.DBOperatorMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author majia
 * @version 1.0
 * @date 2020/6/9 15:04
 */
@Configuration
public class Queue {

    @Bean("blockedQueue")
    public LinkedBlockingQueue<List<DBOperatorMonitor>> getBlockedQueue(){
        return new LinkedBlockingQueue<List<DBOperatorMonitor>>();
    }
}
