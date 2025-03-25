package com.synway.datarelation.config;


import com.synway.datarelation.pojo.databloodline.ObjectStoreColumn;
import com.synway.datarelation.pojo.datawork.v3.NodeEntity;
import com.synway.datarelation.util.ThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName ConfigBean
 * @description 数据血缘中各个缓存的配置类
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
@Configuration
public class ConfigBean {

    /**
     * 存储程序内部的相关信息, key值的相关数据
     *     dataPlatFormVersion : 本地仓的版本
     *     dataPlatFormType ： 本地仓的类型
     * @return
     */
    @Bean
    public ConcurrentHashMap<String,String> parameterMap(){
        ConcurrentHashMap<String,String> chain = new ConcurrentHashMap<>(10);
        return chain;
    }

    /**
     * 每隔 30 分钟查询一次
     * tableId 和 obejctId 的全局缓存
     * key 值是 tableId
     * value 值是 objectId
     * @return
     */
    @Bean
    public ConcurrentHashMap<String,String> synlteObjectIdMap(){
        ConcurrentHashMap<String,String> chain1 = new ConcurrentHashMap<>(100);
        return chain1;
    }

    /**
     * key值是 不带项目名的表英文名，全部小写
     * @return
     */
    @Bean
    public ConcurrentHashMap<String,List<ObjectStoreColumn>> storeInfoMap(){
        ConcurrentHashMap<String, List<ObjectStoreColumn>> chain2 = new ConcurrentHashMap<>(100);
        return chain2;
    }

    @Bean
    public ThreadPool getPool(){//线程池
        ThreadPool threadPoolExecutor = new ThreadPool(5,50,5000,TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        return threadPoolExecutor;
    }


    /**
     *     配置阻塞队列
     */
    @Bean
    public QueueBean queueBean(){
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(5000);
        QueueBean queueBean = new QueueBean();
        queueBean.setQueue(blockingQueue);
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                15,
                240L,
                TimeUnit.SECONDS,
                blockingQueue
        );
        queueBean.setExecutorService(executorService);
        return queueBean;
    }

	/**
     *
     */
    @Bean
    public LinkedList<NodeEntity> nodeInfoCache(){
        //  存储 sql 解析出来的字段血缘信息，其中 key 表示  dataprocess 这个固定值
        LinkedList<NodeEntity> list = new LinkedList<>();
        return list;
    }


    /**
     *  查询 数据仓库的 dataId 与 项目名相关的关系，
     *  缓存下来 不每次都查询该接口
     *  hive 可能不存在项目名，如果为空，则都要查询
     * @return
     */
    @Bean
    public ConcurrentMap<String,List<String>> dataIdProjectsMap(){
        return new ConcurrentHashMap<>(12);
    }

}
