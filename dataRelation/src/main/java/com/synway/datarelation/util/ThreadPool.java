package com.synway.datarelation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool extends ThreadPoolExecutor {
    private Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    public ThreadPool(int corePoolSize,
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
            logger.info("线程池中接受到的错误"+t);
            super.afterExecute(r, t);//先执行父类的操作
            try {
                if(t!=null){
                    //输出线程执行的错误信息
                    logger.info("线程1秒后重新启动");
                    Thread.sleep(1000);
                    this.execute(r);//重启线程
                }
            }catch(Exception e){
                logger.error(e.getMessage());
            }
            String threadInfo = String.format("线程池当前状态,runnable:[%d],max[%d]",getActiveCount(),getMaximumPoolSize());
            logger.info(threadInfo+"\n单个任务执行结束");
        }finally {

        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        logger.error("线程池退出...");
    }
}
