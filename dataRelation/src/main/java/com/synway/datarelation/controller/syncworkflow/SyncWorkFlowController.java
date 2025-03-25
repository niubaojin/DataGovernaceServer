package com.synway.datarelation.controller.syncworkflow;

import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.service.sync.DfWorkTaskPage;
import com.synway.datarelation.constant.TaskPageType;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageParams;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageReturn;
import com.synway.datarelation.pojo.datawork.v3.HistoryTaskQueryParams;
import com.synway.common.exception.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
//import com.synway.datarelation.service.sync.SyncWorkFlowV3Service;
//import com.synway.datarelation.service.sync.SyncWorkFlowV2Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * vue页面的对应的请求参数
 * @author wangdongwei
 * 同步工作流
 */
@Controller
@RequestMapping("/synWorkflow")
public class SyncWorkFlowController {
    private static Logger logger = LoggerFactory.getLogger(SyncWorkFlowController.class);

//    @Autowired
//    private SyncWorkFlowV3Service syncWorkFlowV3ServiceImpl;
//    @Autowired
//    private SyncWorkFlowV2Service syncWorkFlowV2ServiceImpl;

    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;

    @RequestMapping(value = "/getDataSynchronizationReport" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<DataSynchronizationReportPageReturn> getDataSynchronizationReport(
            @RequestBody DataSynchronizationReportPageParams queryParams){
        logger.info("数据同步报表的查询参数为："+JSONObject.toJSONString(queryParams));
        ServerResponse<DataSynchronizationReportPageReturn> serverResponse = null;
        try{
            if(StringUtils.isEmpty(queryParams.getTaskOpenDate())||StringUtils.isEmpty(queryParams.getTaskEndDate())){
                throw  new NullPointerException("传入的任务启动时刻/任务结束时刻为空");
            }
            String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION, "3");
            String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE, "");
            String handleClaStr = TaskPageType.getCla(dataPlatFormType, version);
            DfWorkTaskPage dataInterfaceQuery = (DfWorkTaskPage) Class.forName(handleClaStr).newInstance();
            DataSynchronizationReportPageReturn  dataSynchronizationReportPageReturn =
                    dataInterfaceQuery.getDataSynchronizationReport(queryParams);
//            // 如果版本是3 则查询版本3对应的数据
//            if("3".equalsIgnoreCase(version) || StringUtils.equalsIgnoreCase(dataPlatFormType, Constant.ALI_YUN)){
//                dataSynchronizationReportPageReturn = taskPageQueryServiceImpl.getDataSynchronizationReport(queryParams);
//            }else{
//                dataSynchronizationReportPageReturn = taskPageQueryServiceImpl.getDataSynchronizationReportTwo(queryParams);
//            }
            serverResponse = ServerResponse.asSucessResponse(dataSynchronizationReportPageReturn);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("数据同步报表查询报错："+e.getMessage());
            logger.error("数据同步报表查询报错："+ ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping(value = "/queryDataSynchronizationLog" )
    @ResponseBody
    public ServerResponse<String> queryDataSynchronizationLog(String taskId, String id){
        ServerResponse<String> serverResponse = null;
        try{
            logger.info("查询的参数为taskId："+taskId+" id:"+id);
            if(StringUtils.isEmpty(taskId)&&StringUtils.isEmpty(id)){
                logger.error("任务ID和历史任务ID都为空，不能查询到运行日志信息");
                return ServerResponse.asErrorResponse("任务ID和历史任务ID都为空，不能查询到运行日志信息");
            }
            String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION,"3");
            String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
            String handleClaStr = TaskPageType.getCla(dataPlatFormType, version);
            DfWorkTaskPage dataInterfaceQuery = (DfWorkTaskPage) Class.forName(handleClaStr).newInstance();
            String logMessage = dataInterfaceQuery.queryDataSynchronizationLog(taskId,id);
//            // 如果版本是3 则查询版本3对应的数据
//            if(version.equalsIgnoreCase("3") || StringUtils.equalsIgnoreCase(dataPlatFormType, Constant.ALI_YUN)){
//                logMessage = taskPageQueryServiceImpl.queryDataSynchronizationLog(taskId,id);
//            }else{
//                logMessage = taskPageQueryServiceImpl.queryDataSynchronizationTwoLog(taskId,id);
//            }
            serverResponse = ServerResponse.asSucessResponse(logMessage);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取日志信息报错"+ExceptionUtil.getExceptionTrace(e).substring(0,500));
            logger.error("获取日志信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    @RequestMapping(value = "/queryHistoryTaskLog" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<Map<String,Object>> queryHistoryTaskLog(@RequestBody HistoryTaskQueryParams historyTaskQueryParams){
        ServerResponse<Map<String,Object>> serverResponse = null;
        try{
            logger.info("传入参数为："+JSONObject.toJSONString(historyTaskQueryParams));
            String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION,"3");
            String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
            String handleClaStr = TaskPageType.getCla(dataPlatFormType, version);
            DfWorkTaskPage dataInterfaceQuery = (DfWorkTaskPage) Class.forName(handleClaStr).newInstance();
            Map<String,Object> returnObject = dataInterfaceQuery.queryHistoryTaskLog(historyTaskQueryParams);
//            Map<String,Object> returnObject = new HashMap<>();
//            if(version.equals("3")){
//                returnObject = taskPageQueryServiceImpl.queryHistoryTaskLogService(historyTaskQueryParams);
//            }else{
//                returnObject = taskPageQueryServiceImpl.queryHistoryTaskTwoLogService(historyTaskQueryParams);
//            }
            serverResponse = ServerResponse.asSucessResponse(returnObject);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            logger.error("查询历史日志信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    @RequestMapping(value = "/exportDataTable", produces="application/json;charset=utf-8")
    @ResponseBody
    public void exportDataTable(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody DataSynchronizationReportPageParams queryParams){
        try{
            logger.info("导出数据同步报表传入的参数为："+JSONObject.toJSONString(queryParams));
            String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION,"3");
            String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
            String handleClaStr = TaskPageType.getCla(dataPlatFormType, version);
            DfWorkTaskPage dataInterfaceQuery = (DfWorkTaskPage) Class.forName(handleClaStr).newInstance();
            dataInterfaceQuery.exportDataTable(queryParams,response);
//            // 如果版本是3 则查询版本3对应的数据
//            if("3".equalsIgnoreCase(version) || StringUtils.equalsIgnoreCase(dataPlatFormType,Constant.HUA_WEI_YUN)){
//                taskPageQueryServiceImpl.exportDataTableService(queryParams,response,3);
//            }else{
//                taskPageQueryServiceImpl.exportDataTableService(queryParams,response,2);
//            }
        }catch (Exception e){
            logger.error("导出数据同步报表传入的参数为报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }

    @RequestMapping(value = "/exportHistoryTaskTable", produces="application/json;charset=utf-8")
    @ResponseBody
    public void exportHistoryTaskTable(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody HistoryTaskQueryParams historyTaskQueryParams){
        try{
            logger.info("导出历史数据同步报表传入的参数为："+JSONObject.toJSONString(historyTaskQueryParams));
            String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION,"3");
            String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
            String handleClaStr = TaskPageType.getCla(dataPlatFormType, version);
            DfWorkTaskPage dataInterfaceQuery = (DfWorkTaskPage) Class.forName(handleClaStr).newInstance();
            dataInterfaceQuery.exportHistoryTaskTable(historyTaskQueryParams,response);
//            if(version.equalsIgnoreCase("3")){
//                taskPageQueryServiceImpl.exportHistoryTaskTable(historyTaskQueryParams,response,3);
//            }else{
//                taskPageQueryServiceImpl.exportHistoryTaskTable(historyTaskQueryParams,response,2);
//            }
        }catch (Exception e){
            logger.error("导出历史数据同步报表报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }
}
