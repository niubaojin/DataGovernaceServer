//package com.synway.datastandardmanager.controller;
//
//
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.synway.datastandardmanager.annotation.Resubmit;
//import com.synway.datastandardmanager.dao.standard.TableOrganizationDao;
//import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
//import com.synway.common.bean.ServerResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//
//@RequestMapping("/test11")
//@Controller
//public class TestController {
//    @Autowired()private Environment env;
//
//    @Autowired private TableOrganizationDao tableOrganizationDao;
//
//    @RequestMapping("/test11")
//    @ResponseBody
//    @IgnoreSecurity
//    @Resubmit(delaySeconds = 10)
//    public ServerResponse<String> test11(String aaa) throws InterruptedException {
//
//
//
//        return ServerResponse.asSucessResponse("111");
//    }
//
//
//}
