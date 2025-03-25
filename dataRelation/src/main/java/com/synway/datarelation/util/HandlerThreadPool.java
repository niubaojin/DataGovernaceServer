package com.synway.datarelation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerThreadPool extends ThreadPoolExecutor {
    private Logger logger = LoggerFactory.getLogger(HandlerThreadPool.class);

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
//        super.afterExecute(r, t);
        try{
            //线程退出时，需要重新启动
//            if(t!=null) {
//                LOGGER.error(String.format("线程[%s]在运行过程中，因为[%s]而退出，重启线程",Thread.currentThread().getName(),t.getCause()));
//                this.execute(r);
//            }
//            this.execute(this.getThreadFactory().newThread(r));
            String threadInfo = String.format("线程池当前状态,runnable:[%d],max[%d]",getActiveCount(),getMaximumPoolSize());
            logger.info(threadInfo);

        }finally {

        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        logger.error("线程池退出...");
    }

    public Integer getActiveCountAll(){
        return getActiveCount();
    }
}
