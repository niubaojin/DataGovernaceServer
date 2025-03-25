package com.synway.property.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * 异步管理器
 * @author majia
 * @date 2020/06/02
 */
@Component
public class AsyManager<T>{
    private static Logger LOGGER = LoggerFactory.getLogger(AsyManager.class);
    @Autowired private HandlerThreadPool handlerThreadPool;

    public void addTask(Runnable runnable){
        handlerThreadPool.submit(runnable);
    }

    public Future<T> addTask(Callable callable){
        return handlerThreadPool.submit(callable);
    }

    public Integer getCount(){
        return  handlerThreadPool.getActiveCount();
    }




}
