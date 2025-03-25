//package com.synway.datarelation.controller.datablood;
//
//
//
//import com.synway.datarelation.config.HazecastLock;
//import com.synway.datarelation.constant.Common;
//import com.synway.datarelation.constant.DataBaseType;
//import com.synway.datarelation.service.workflow.process.DataInterfaceQuery;
//import com.synway.datarelation.pojo.taskdefine.v3.NodeQueryParameters;
//import com.synway.datarelation.pojo.taskdefine.v3.TaskQueryParameters;
//import com.synway.datarelation.util.DataIdeUtil;
//import com.synway.datarelation.util.v3.DataWorksTaskApi;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
//
///**
// * @author wangdongwei
// * @date 2021/3/17 12:06
// */
//@Controller
//@RequestMapping(value = "/test")
//public class TestController {
//
//    @Autowired
//    private DataWorksTaskApi dataWorksTaskApi;
////    @Autowired
////    private DataIdeUtil dataIdeUtil;
//
//
//
//    @GetMapping(value = "/test112")
//    @ResponseBody
//    public void test112(long nodeId) throws Exception{
//
//        String ss = dataWorksTaskApi.getNodeCode(nodeId);
//        System.out.println(ss);
////        TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
////        taskQueryParameters.setPageStart(0);
////        taskQueryParameters.setPageSize(500);
////        taskQueryParameters.setPrgType(10);
////        taskQueryParameters.setStartRunTimeFrom("2021-06-20 00:00:00");
////        taskQueryParameters.setNodeId(Long.valueOf("19369"));
////        String resultStr = dataWorksTaskApi.queryTask(taskQueryParameters);
////        System.out.println(resultStr);
////        NodeQueryParameters nodeQueryParameters = new NodeQueryParameters();
////        nodeQueryParameters.setPageStart(1);
////        nodeQueryParameters.setPageSize(20);
////        nodeQueryParameters.setPrgType(99);
////        nodeQueryParameters.setPrgType(10);
////        nodeQueryParameters.setNodeId(Long.valueOf("19198"));
////        nodeQueryParameters.setNodeId(Long.valueOf("6448"));
////        nodeQueryParameters.setNodeId(Long.valueOf("10259"));
//
//        //  98 和 23已经有定时任务获取，本次定时任务不需要获取
//        // 6：shell节点     10：odps_sql  11: odps_mr  221: pyodps  23：cdp  99:virtual虚节点  98:Combined Node、
////        String handleClaStr = DataBaseType.getCla("aliyun", "3");
////        DataInterfaceQuery dataInterfaceQuery = (DataInterfaceQuery) Class.forName(handleClaStr).newInstance();
////        dataInterfaceQuery.getNode(nodeQueryParameters, true);
//
////        String url = "http://baseapi.res.kunlun.xjtz/v1.0/node/PROD?executeMethod=SEARCH_NODE_COUNT";
////
////        String aa = dataWorksTaskApi.queryDataWorksApi(url);
////        System.out.println(resultStr);
//    }
//
//
//
//}
