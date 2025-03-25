package com.synway.property.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2020/7/22 16:06
 */
@Controller
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("getServiceList")
    @ResponseBody
    public Object getServicesList() {
        List<List<ServiceInstance>> servicesList = new ArrayList<>();
        //获取服务名称
        List<String> serviceNames = discoveryClient.getServices();
        for (String serviceName : serviceNames) {
            //获取服务中的实例列表
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            for (ServiceInstance serviceInstance : serviceInstances) {
                String serviceInstanceStr= JSON.toJSONString(serviceInstance);
                if(serviceInstanceStr!=null){
                    Map<String, Object> serviceInstanceMap = (Map<String, Object>) JSON.parse(serviceInstanceStr);
                    if(serviceInstanceMap!=null){
                        Map<String, Object> instanceInfoMap = (Map<String, Object>)JSON.parse(serviceInstanceMap.get("instanceInfo").toString());
                        String appName = (String)instanceInfoMap.get("appName");
                        String port = String.valueOf(serviceInstance.getPort());
                        String ip = (String) instanceInfoMap.get("iPAddr");
                        String status = (String)instanceInfoMap.get("status");
                    }
                }
            }
            servicesList.add(serviceInstances);
        }
        return servicesList;
    }

}
