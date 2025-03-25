package com.synway.datastandardmanager.config;

import com.synway.datastandardmanager.pojo.AuthorizedUser;
import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ConfigBean {

    /**
     * 目前存储 是否审批的开关数据
     * @return
     */
    @Bean
    public ConcurrentHashMap<String,Boolean> switchHashMap(){
        ConcurrentHashMap<String,Boolean> chain = new ConcurrentHashMap<>();
        return chain;
    }

    /**
     * 20200716 增加缓存 建表之后如果将数据发送给 数据历程报错 则把数据写入到缓存中
     */
    @Bean
    public ConcurrentHashMap<String,DataProcess> buildTableCourseHashMap(){
        ConcurrentHashMap<String,DataProcess> chainOne = new ConcurrentHashMap<>();
        return chainOne;
    }

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


}
