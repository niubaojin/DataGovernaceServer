package com.synway.datarelation.config;

import com.alibaba.fastjson.JSON;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.util.OdpsClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName OdpsConfig
 * @description ODPS的连接信息
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
@Configuration
public class OdpsConfig {

    private static Logger logger = LoggerFactory.getLogger(OdpsConfig.class);
    @Autowired()private Environment env;

    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;

    @Bean
    public OdpsClient odpsClient(){
        OdpsClient odpsClient = new OdpsClient();
        Map<String,String> map = new HashMap<>(12);
        String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
        if(StringUtils.isNotEmpty(dataPlatFormType) && Constant.ALI_YUN.equalsIgnoreCase(dataPlatFormType)){
            map.put(OdpsClient._ODPSURL,env.getProperty("odps.url"));
            map.put(OdpsClient._ACCESSID,env.getProperty("odps.accessId"));
            map.put(OdpsClient._ACCESSKEY,env.getProperty("odps.accessKey"));
            map.put(OdpsClient._ODPSPROJECT,env.getProperty("odps.project"));
            logger.info("获取到ODPS配置信息"+ JSON.toJSON(map));
            odpsClient.setMapConfinfo(map);
        }else{
            logger.info("华为平台，不需要创建odpsClient的客户端");
        }
        return odpsClient;
    }

}
