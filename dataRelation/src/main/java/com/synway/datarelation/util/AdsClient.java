package com.synway.datarelation.util;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;


/**
 * @author wangdongwei
 */
@Service
public class AdsClient {
    public static final  Logger logger = LoggerFactory.getLogger(AdsClient.class);
    @Resource
    private Environment environment;
    /**
     * 获取到ads中指定表字段信息，只查字段名称
     * @param project      项目名
     * @param tableName  表名
     * @return
     */
    public List<String> getAdsTableColumnList(String project , String tableName) {
        logger.info("ADS中需要查询表字段的表名为："+tableName);
        Connection connection = null;
        ResultSet resultSet = null;
        List<String> columnList = new ArrayList<>();
        try{
            String url = environment.getRequiredProperty("ads.url");
            String username = environment.getRequiredProperty("ads.accessId");
            String password = environment.getRequiredProperty("ads.accessKey");
            connection = DriverManager.getConnection(url, username, password);
            //1、查询数据，获取全部的数据
            DatabaseMetaData dbmd = connection.getMetaData();
            resultSet = dbmd.getColumns(connection.getCatalog(), project, tableName, "%");
            while (resultSet.next()){
                String columnName = resultSet.getString("COLUMN_NAME").toUpperCase();
                columnList.add(columnName);
            }
        }catch(Exception e){
            logger.error("查询sql报错"+ ExceptionUtil.getExceptionTrace(e));
        }finally {
            closeConn(connection);
        }
        logger.info("查询字段的返回结果为"+ JSONObject.toJSONString(columnList));
        return columnList;
    }



    private boolean closeConn(Connection connection){
        try{
            if(connection!=null && !connection.isClosed()){
                connection.close();
            }
        }catch(Exception e){
            logger.info(e.getMessage());
        }
        return true;
    }

}
