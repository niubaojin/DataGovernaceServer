//package com.synway.datastandardmanager.controller;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.synway.datastandardmanager.pojo.batchoperation.ObjectStatusEditPojo;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BatchOperationControllerTest {
//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//    private final HttpHeaders httpHeaders = new HttpHeaders();
//
//    @Before
//    public void setUp(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//
//    @Test
//    public void whenQuery() throws Exception{
////        ObjectStatusEditPojo objectStatusEditPojo = new ObjectStatusEditPojo();
////        List<String> list = new ArrayList<>();
////        objectStatusEditPojo.setStatus("1");
////        objectStatusEditPojo.setTableIdList(list);
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/dataStandardManager/BatchOperation/objectStatusEdit")
////        .contentType(MediaType.APPLICATION_JSON_UTF8).headers(httpHeaders).content(JSONObject.toJSONString(objectStatusEditPojo))).andReturn();
////        System.out.println(result.getResponse().getContentAsString());
////        int i = 1;
////        i = i++;
////        int j = i++;
////        int k = i+ ++i * i++;
////        System.out.println(i);
////        System.out.println(j);
////        System.out.println(k);
//
//    }
//
//
//}