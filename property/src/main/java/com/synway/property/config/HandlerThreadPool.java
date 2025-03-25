package com.synway.property.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author majia
 */
public class HandlerThreadPool extends ThreadPoolExecutor {
    private Logger LOGGER = LoggerFactory.getLogger(HandlerThreadPool.class);

    public HandlerThreadPool(int corePoolSize,
                             int maximumPoolSize,
                             long keepAliveTime,
                             TimeUnit unit,
                             BlockingQueue<Runnable> workQueue){
        super(corePoolSize, maximumPoolSize,keepAliveTime,unit,workQueue);
    }
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try{
            String threadInfo = String.format("线程池当前状态,core:[%d],max[%d]",getActiveCount(),getMaximumPoolSize());
            LOGGER.info(threadInfo);

        }finally {

        }
    }



    @Override
    protected void terminated() {
        super.terminated();
        LOGGER.error("线程池退出...");
    }

    public Integer getActiveCountAll(){
        return getActiveCount();
    }
}
