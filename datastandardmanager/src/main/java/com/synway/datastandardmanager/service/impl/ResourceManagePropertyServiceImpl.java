//package com.synway.datastandardmanager.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.synway.datastandardmanager.pojo.ExampleDataColumn;
//import com.synway.datastandardmanager.pojo.QueryExampleDataPojo;
//import com.synway.datastandardmanager.service.ResourceManagePropertyService;
//import com.synway.datastandardmanager.util.ExceptionUtil;
//import com.synway.datastandardmanager.util.RestTemplateHandle;
//import com.synway.common.bean.ServerResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author wangdongwei
// */
//@Service
//public class ResourceManagePropertyServiceImpl implements ResourceManagePropertyService {
//    @Autowired()private Environment env;
//
//    @Autowired
//    private RestTemplateHandle restTemplateHandle;
//
//    private Logger logger = LoggerFactory.getLogger(ResourceManagePropertyServiceImpl.class);
//
//    @Override
//    public void createQualityConfigByTableId(String tableId) {
//        try{
////            logger.info("开始调用质量检测接口自动生成标准表"+tableId+"的质量检测规则");
//            restTemplateHandle.createQualityConfigByTableId(tableId);
//            logger.info("调用质量检测接口自动生成标准表"+tableId+"的质量检测规则程序结束");
//        }catch (Exception e){
//            logger.error("保存质量检测规则报错"+ ExceptionUtil.getExceptionTrace(e));
//        }
//
//    }
//}
