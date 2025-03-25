package com.synway.datastandardmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步任务线程池管理器
 * @author wangdongwei
 * @date 2021/12/3 13:39
 */
@Configuration
public class AsyncConfig {
    private static final int MAX_POOL_SIZE = 6;

    private static final int CORE_POOL_SIZE = 3;

    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setThreadNamePrefix("异步执行任务-");
        taskExecutor.initialize();
        return taskExecutor;
    }


}
