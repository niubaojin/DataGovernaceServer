//package com.synway.datastandardmanager.controller;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * @author wangdongwei
// * @ClassName ResourceManageEditPageController
// * @description 别的项目修改页面
// * @date 2020/9/17 11:06
// */
//@Controller
//@Deprecated
//public class ResourceManageEditPageController {
//    private Logger logger = LoggerFactory.getLogger(ResourceManageEditPageController.class);
//
//    @RequestMapping("/editResourceManage")
//    public String editResourceManage(String moduleName){
//        logger.info("本次的模块名称 moduleName: "+moduleName);
//        if(StringUtils.isEmpty(moduleName)){
//            throw new NullPointerException("parameter \"moduleName\" connot be empty");
//        }else{
//            return "resourceManage.html";
//        }
//
//    }
//
//}
