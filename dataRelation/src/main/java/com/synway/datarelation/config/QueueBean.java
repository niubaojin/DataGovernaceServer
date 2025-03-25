package com.synway.datarelation.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author wangdongwei
 * @ClassName QueueBean
 * @description   阻塞队列，线程池的配置类
 * @date 2020/9/18 11:38
 */
public class QueueBean {
    private BlockingQueue<Runnable> queue;
    private ExecutorService executorService;

    public BlockingQueue<Runnable> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
