package com.synway.datastandardmanager.config;

import com.synway.datastandardmanager.pojo.buildtable.RefreshCreatedPojo;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 一些监听事件
 * @author wangdongwei
 * @date 2021/12/3 13:49
 */
@Component
@Slf4j
public class EventListenerList {

    @Autowired
    private ObjectStoreInfoService objectStoreInfoServiceImpl;
    /**
     *     之后可以换别的锁 先这样吧
     */
    private  static ReentrantLock LOCK = new ReentrantLock();


    @Async("asyncTaskExecutor")
    @EventListener
    @Order(1)
    public void refreshCreatedListener(RefreshCreatedPojo refreshCreatedPojo){
        LOCK.lock();
        try{
            log.info("刷新相关的已建表信息，开始运行代码");
//            objectStoreInfoServiceImpl.refreshCreateTable(refreshCreatedPojo);
            objectStoreInfoServiceImpl.refreshAllCreateTable(refreshCreatedPojo);
        }catch (Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
        }finally {
            LOCK.unlock();
            log.info("刷新全部的建表信息结束，解锁成功");
        }
    }
}
