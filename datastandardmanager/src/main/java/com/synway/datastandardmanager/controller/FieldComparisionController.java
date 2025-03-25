package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.service.FieldComparisionService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;


/**
 * @author wangdongwei
 * @ClassName FieldComparisionController
 * @description 字段对比的相关操作
 * @date 2020/9/24 13:48
 */
@Controller
public class FieldComparisionController {
    private static final Logger logger = LoggerFactory.getLogger(FieldComparisionController.class);
    @Autowired
    private FieldComparisionService fieldComparisionServiceImpl;
    @Autowired
    ConcurrentHashMap<String,Boolean> switchHashMap;


    /**
     *  已建表的信息 字段比对获取相关信息
     * @param query
     * @return
     */
    @RequestMapping(value="/getCreatedFieldComparision" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ColumnComparisionPage> getCreatedFieldComparision(@RequestBody ColumnComparisionSearch query){
        logger.info("开始查询 已建表信息中指定表的字段对比相关信息");
        ServerResponse<ColumnComparisionPage> serverResponse = null;
        try{
            ColumnComparisionPage columnComparisionPage = fieldComparisionServiceImpl.getColumnComparisionPage(query);
            serverResponse = ServerResponse.asSucessResponse(columnComparisionPage);
        }catch (TimeoutException e1){
            logger.error("获取字段对比的数据报错"+ ExceptionUtil.getExceptionTrace(e1));
            serverResponse = ServerResponse.asErrorResponse("查询字段信息超时，无法获取字段信息,请去数据仓库查看本地仓的配置是否正确");
        } catch (Exception e){
            logger.error("获取字段对比的数据报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    /**
     *  在数据库中修改需要新增的字段信息  对比后保存的字段信息
     * @param data
     * @return
     */
    @RequestMapping(value="/saveFieldComparison" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map<String,String>> saveFieldComparison(@RequestBody SaveColumnComparision data){
        logger.info("开始在数据库中创建需要新增的字段信息，相关信息为"+JSONObject.toJSONString(data));
        ServerResponse<Map<String,String>> serverResponse = null;
        Map<String,String> returnMap = null;
        try{
            boolean switchFlag = switchHashMap.getOrDefault("approvalInfo",false);
            if(switchFlag){
                // 在审批中保存数据，然后再回调审批的页面
                String returnMsg = fieldComparisionServiceImpl.saveOrUpdateApprovalInfoService(data);
                returnMap = new HashMap<>();
                returnMap.put("approvalInfo","true");
                returnMap.put("message",returnMsg);
                serverResponse = ServerResponse.asSucessResponse(returnMap);
            }else{
                String returnMsg = fieldComparisionServiceImpl.saveFieldComparison(data);
                returnMap = new HashMap<>();
                returnMap.put("approvalInfo","false");
                returnMap.put("message",returnMsg);
                serverResponse = ServerResponse.asSucessResponse(returnMap);
            }

        }catch (Exception e){
            logger.error("获取字段对比的数据报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }


    /**
     *  给审批流程使用  新增字段信息
     * @param data
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value="/saveFieldComparisonStr")
    @ResponseBody
    public ServerResponse<String> saveFieldComparisonStr(@RequestBody String data){
        logger.info("开始在数据库中创建需要新增的字段信息，相关信息为"+data);
        ServerResponse<String> serverResponse = null;
        try{
            SaveColumnComparision saveColumnComparision = JSONObject.parseObject(data, SaveColumnComparision.class);
            if("4".equals(saveColumnComparision.getStatus())){
                logger.error("流程的返回状态是中止状态，禁止新增字段");
                return ServerResponse.asSucessResponse("流程的返回状态是中止状态，禁止新增字段",
                        "流程的返回状态是中止状态，禁止新增字段");
            }
            String returnMsg = fieldComparisionServiceImpl.saveFieldComparison(saveColumnComparision);
            serverResponse = ServerResponse.asSucessResponse(returnMsg,returnMsg);
        }catch (Exception e){
            logger.error("进行新增字段报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }



    @RequestMapping(value="/test1111")
    @ResponseBody
    public void test() throws Exception {
        SaveColumnComparision saveColumnComparision = new SaveColumnComparision();
        List<TableColumnPage> columnList = new ArrayList<>();
        TableColumnPage tableColumnPage = new TableColumnPage();
        tableColumnPage.setColumnEngname("column1");
        tableColumnPage.setColumnChinese("测试数据");
        tableColumnPage.setColumnType("String");
        tableColumnPage.setRowNum("1");
        columnList.add(tableColumnPage);
        saveColumnComparision.setColumnList(columnList);
        StandardTableCreated createdTableData = new StandardTableCreated();
        createdTableData.setTableNameEn("jz.nb_app_identity15_local");
        createdTableData.setTableProject("jz");
        createdTableData.setTableBase("clickhouse");
        createdTableData.setDataId("70bdbd1d0f4a4355a0e6c7dd9b3c3140");
        saveColumnComparision.setCreatedTableData(createdTableData);
        saveColumnComparision.setTableId("nb_app_virtualnumbertempfinal");
        fieldComparisionServiceImpl.saveFieldComparison(saveColumnComparision);
    }

}
