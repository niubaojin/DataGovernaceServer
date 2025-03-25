package com.synway.property.config;

import com.alibaba.fastjson.JSON;
import com.synway.property.conditional.OdpsConditional;
import com.synway.property.util.OdpsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author majia
 */
@Configuration
@Conditional(OdpsConditional.class)
public class OdpsConfig {


    @Autowired()
    private Environment env;

    private static Logger logger = LoggerFactory.getLogger(OdpsConfig.class);

    @Bean
    public OdpsClient odpsClient() {
        OdpsClient odpsClient = new OdpsClient();
        Map<String, String> map = new HashMap<>();
        map.put(OdpsClient.ODPS_URL, env.getProperty("odps.url"));
        map.put(OdpsClient.ACCESS_ID, env.getProperty("odps.accessId"));
        map.put(OdpsClient.ACCESS_KEY, env.getProperty("odps.accessKey"));
        map.put(OdpsClient.ODPS_PROJECT, env.getProperty("odps.project"));
        logger.info("获取到ODPS配置信息" + JSON.toJSON(map));
        odpsClient.setMapConfinfo(map);
        return odpsClient;
    }

    @Bean
    public OdpsClient useHeatClient() {
        OdpsClient odpsClient = new OdpsClient();
        Map<String, String> map = new HashMap<>();
        map.put(OdpsClient.ODPS_URL, env.getProperty("odps.useheat.end_point"));
        map.put(OdpsClient.ACCESS_ID, env.getProperty("odps.useheat.access_id"));
        map.put(OdpsClient.ACCESS_KEY, env.getProperty("odps.useheat.access_key"));
        map.put(OdpsClient.ODPS_PROJECT, env.getProperty("odps.useheat.project_name"));
        map.put(OdpsClient.ODPS_TUNNEL_URL, env.getProperty("odps.useheat.tunnel_server"));
        logger.info("获取到ODPS配置信息" + JSON.toJSON(map));
        odpsClient.setMapConfinfo(map);
        return odpsClient;
    }
}
