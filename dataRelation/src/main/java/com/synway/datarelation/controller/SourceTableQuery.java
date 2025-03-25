package com.synway.datarelation.controller;

import com.synway.datarelation.service.SourceTableQueryImpl;
import com.synway.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @ClassName SourceTableQuery
 * @description 根据指定应用血缘使用的表，查询出这张表在数据加工中的源头
 * @date 2021/2/20 14:21
 * 未查到前端使用
 */
@Controller
@RequestMapping(value = "/sourceTableQuery")
public class SourceTableQuery {
    private static Logger logger = LoggerFactory.getLogger(SourceTableQuery.class);

    @Autowired
    private SourceTableQueryImpl sourceTableQueryImpl;

    @RequestMapping(value="/start")
    @ResponseBody
    public String start(){
        try{
            return sourceTableQueryImpl.start();
        }catch (Exception e){
            logger.error("获取源头表信息报错："+ ExceptionUtil.getExceptionTrace(e));
            return "获取源头表信息报错："+ ExceptionUtil.getExceptionTrace(e);
        }
    }


    @RequestMapping(value="/queryTable")
    @ResponseBody
    public void queryTable(String tableName){
        try{
            sourceTableQueryImpl.queryTable(tableName);
        }catch (Exception e){
            logger.error("获取源头表信息报错："+ ExceptionUtil.getExceptionTrace(e));
        }
    }

}
