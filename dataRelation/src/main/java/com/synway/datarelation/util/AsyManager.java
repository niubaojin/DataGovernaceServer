package com.synway.datarelation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName AsyManager
 * @description 异步管理器
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
@Component
public class AsyManager {
    private static Logger LOGGER = LoggerFactory.getLogger(AsyManager.class);

    @Autowired
    private ThreadPool threadPool;

    public void addTask(Runnable runnable){
        threadPool.submit(runnable);
    }

    public Integer getCount(){
        return  threadPool.getActiveCount();
    }

}
