package com.synway.datarelation.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author wangdongwei
 */
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

    @Autowired private ThreadPoolTaskScheduler threadPoolTaskScheduler;


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //初始化环境变量
        init(event);
        //初始化变量完成
        //应用初始化
        refresh(event);
        //应用启动完成
        afterStart(event);
        //应用启动 start()方法启动时触发该事件
        preStart(event);
        //应用停止 stop()方法启动时触发该事件
        preStop(event);
        //应用关闭
        preClose(event);
    }

    /**
     * 应用关闭
     * @param event 事件
     */
    public void preClose(ApplicationEvent event){
        if(!(event instanceof ContextClosedEvent)) {
            return;
        }
        logger.info("接受到退出命令，等待正在执行的线程退出...");
        threadPoolTaskScheduler.shutdown();
    }

    /**
     * 应用启动完成
     * @param event
     */
    public void afterStart(ApplicationEvent event){
        if(!(event instanceof ApplicationReadyEvent)){
            return;
        }
//        Lifecycle
        logger.info("应用启动完成");
    }

    /**
     * 应用刷新，启动应用的真实动作
     * @param event
     */
    public void refresh(ApplicationEvent event){
        if(!(event instanceof ContextRefreshedEvent)){
            return;
        }
        logger.info("启动应用");

    }

    /**
     * 应用启动 start()方法启动时触发该事件
     * @param event
     */
    public void preStart(ApplicationEvent event){
        if(!(event instanceof ContextStartedEvent)){
            return;
        }
        logger.info("start()方法启动时触发该事件");

    }

    /**
     * 应用停止 stop()方法启动时触发该事件
     * @param event
     */
    public void preStop(ApplicationEvent event){
        if(!(event instanceof ContextStoppedEvent)){
            return;
        }
        logger.info("stop()方法启动时触发该事件");
    }

    /**
     * 初始化环境变量
     * @param event
     */
    public void init(ApplicationEvent event){
        if(!(event instanceof ApplicationEnvironmentPreparedEvent)){
            return;
        }
        logger.info("初始化环境变量");
    }




}
