package com.synway.datarelation.service.sync.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datarelation.service.sync.DfWorkTaskPage;
import com.synway.datarelation.util.SpringBeanUtil;
import com.synway.datarelation.util.AsyManager;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.taskinstance.TaskPageQueryDao;
import com.synway.datarelation.service.workflow.impl.DataWorkV3Service;
import com.synway.datarelation.pojo.datawork.DataReportPageReturn;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageParams;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageReturn;
import com.synway.datarelation.pojo.datawork.v3.HistoryTaskQueryParams;
import com.synway.datarelation.pojo.datawork.v3.TaskQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskRunQueryParameters;
import com.synway.datarelation.util.ExcelHelper;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.ExportUtil;
import com.synway.datarelation.util.v3.CronExpParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangdongwei
 * @date 2021/5/12 14:35
 */
public class DataWorkV3TaskPage implements DfWorkTaskPage {
    private static final Logger logger = LoggerFactory.getLogger(DataWorkV3TaskPage.class);
    private TaskPageQueryDao taskPageQueryDao;
    private AsyManager asyManager;
    private Environment environment;

    public DataWorkV3TaskPage(){
        taskPageQueryDao = SpringBeanUtil.getBean(TaskPageQueryDao.class);
        asyManager = SpringBeanUtil.getBean(AsyManager.class);
        environment = SpringBeanUtil.getBean(Environment.class);
    }

    @Override
    public DataSynchronizationReportPageReturn getDataSynchronizationReport(DataSynchronizationReportPageParams queryParams) {
        DataSynchronizationReportPageReturn dataSynchronizationReportPageReturn = new DataSynchronizationReportPageReturn();
        Page page=  PageHelper.startPage(queryParams.getPageNum(),queryParams.getPageSize());
        if(StringUtils.isNotEmpty(queryParams.getSortName())&&StringUtils.isNotEmpty(queryParams.getSortBy())){
            String sortStr = queryParams.getSortName()+" "+queryParams.getSortBy();
            String msgMethod = environment.getProperty("database.type", Common.ORACLE);
            if(StringUtils.equalsIgnoreCase(msgMethod,Common.ORACLE)){
                sortStr += "  nulls last";
            }
            page.setOrderBy(sortStr);
        }
        queryParams.setTaskOpenDate(queryParams.getTaskOpenDate()+" 00:00:00");
        queryParams.setTaskEndDate(queryParams.getTaskEndDate()+" 23:59:59");
        // 获取数据同步报表的列表信息
        List<DataReportPageReturn> lists =taskPageQueryDao.getDataSynchronizationReportDao(queryParams);
        for(DataReportPageReturn dataReportPageReturn:lists){
            String cron = dataReportPageReturn.getCronExpress();
            dataReportPageReturn.setCronExpress(CronExpParser.descCronChinese(cron));
        }
        PageInfo<DataReportPageReturn> pageInfo = new PageInfo<DataReportPageReturn>(lists);
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        logger.info("数据同步报表查询到的数据为："+ JSONObject.toJSONString(map));
        dataSynchronizationReportPageReturn.setPageInfoMap(map);
        // 获取筛选表头的所有需要的信息
        List<Map<String,String>> filterList = taskPageQueryDao.getFilterList(queryParams);
        List<DataSynchronizationReportPageReturn.FilterObject> sourceTypeList = new ArrayList<>();
        List<DataSynchronizationReportPageReturn.FilterObject> sourceTableProjectList = new ArrayList<>();
        List<DataSynchronizationReportPageReturn.FilterObject> targetTypeList = new ArrayList<>();
        List<DataSynchronizationReportPageReturn.FilterObject> targetTypeProjectList = new ArrayList<>();
        DataSynchronizationReportPageReturn.FilterObject filterObject1 = new DataSynchronizationReportPageReturn.FilterObject();
        filterObject1.setText("空");
        filterObject1.setValue(null);
        sourceTypeList.add(filterObject1);
        sourceTableProjectList.add(filterObject1);
        targetTypeList.add(filterObject1);
        targetTypeProjectList.add(filterObject1);
        for(Map<String,String> element:filterList){
            DataSynchronizationReportPageReturn.FilterObject filterObject = new DataSynchronizationReportPageReturn.FilterObject();
            String text = element.getOrDefault("TYPEVALUE",null);
            if(text!= null && StringUtils.isNotEmpty(text)){
                filterObject.setText(text);
                filterObject.setValue(text);
            }else{
                continue;
            }
            switch (element.getOrDefault("TYPE","")){
                case "sourceType":
                    sourceTypeList.add(filterObject);
                    break;
                case "sourceTableProject":
                    sourceTableProjectList.add(filterObject);
                    break;
                case "targetType":
                    targetTypeList.add(filterObject);
                    break;
                case "targetTypeProject":
                    targetTypeProjectList.add(filterObject);
                    break;
                default:
                    logger.error(JSONObject.toJSONString(element)+":没有对应的switch类型");
            }
        }
        dataSynchronizationReportPageReturn.setSourceTableProjectShowList(sourceTableProjectList);
        dataSynchronizationReportPageReturn.setSourceTypeShowList(sourceTypeList);
        dataSynchronizationReportPageReturn.setTargetTableProjectShowList(targetTypeProjectList);
        dataSynchronizationReportPageReturn.setTargetTypeShowList(targetTypeList);
        logger.info("查询到的数据为："+JSONObject.toJSONString(dataSynchronizationReportPageReturn));
        return dataSynchronizationReportPageReturn;
    }

    @Override
    public String queryDataSynchronizationLog(String taskId, String id) throws Exception {
        String logMessage = taskPageQueryDao.queryDataSynchronizationLogDao(taskId,id);
        if(StringUtils.isEmpty(logMessage)){
            TaskRunQueryParameters taskRunQueryParameters = new TaskRunQueryParameters();
            if(StringUtils.isNotEmpty(taskId)){
                taskRunQueryParameters.setTaskId(Long.valueOf(taskId));
            }
            if(StringUtils.isNotEmpty(id)){
                taskRunQueryParameters.setHistoryId(Long.valueOf(id));
            }
            // 这个地方是 阿里V3版本查询日志信息 20201202 改成这个样子
            DataWorkV3Service dataWorkV3ClientService = new DataWorkV3Service();
            dataWorkV3ClientService.getTaskRunLog(taskRunQueryParameters,true);
            logMessage = taskPageQueryDao.queryDataSynchronizationLogDao(taskId,id);
        }
        return  logMessage;
    }

    @Override
    public Map<String, Object> queryHistoryTaskLog(HistoryTaskQueryParams historyTaskQueryParams) throws Exception {
        //  如果查询flag为true，表示需要从 api 中重新查询数据
        //  如果为false 表示直接从数据库中获取
        if(historyTaskQueryParams.getNodeId() == null){
            throw new Exception("传入的nodeId为空，不能查询历史信息");
        }
        if(StringUtils.isEmpty(historyTaskQueryParams.getTaskOpenDate())||
                StringUtils.isEmpty(historyTaskQueryParams.getTaskEndDate())){
            throw new Exception("查询的开始时间和结束时间不能为空");
        }
        historyTaskQueryParams.setTaskOpenDate(historyTaskQueryParams.getTaskOpenDate() + " 00:00:00");
        historyTaskQueryParams.setTaskEndDate(historyTaskQueryParams.getTaskEndDate() + " 23:59:59");
        if(historyTaskQueryParams.getReacquireFlag()){
            TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
            taskQueryParameters.setPageStart(0);
            taskQueryParameters.setPageSize(1000);
            taskQueryParameters.setPrgType(23);
            taskQueryParameters.setNodeId(historyTaskQueryParams.getNodeId());
            taskQueryParameters.setStartRunTimeFrom(historyTaskQueryParams.getTaskOpenDate());
            taskQueryParameters.setStartRunTimeTo(historyTaskQueryParams.getTaskEndDate());
            // 20201202  使用方式发生变化
            DataWorkV3Service dataWorkV3ClientService = new DataWorkV3Service();
            dataWorkV3ClientService.getTask(taskQueryParameters , true);
            Boolean flag = true;
            Integer queryCount = 0;
            Thread.sleep(1000);
            while(flag){
                logger.info("任务池中正在运行的数据量为："+asyManager.getCount());
                if(asyManager.getCount() == 0){
                    flag = false;
                }
                if(queryCount == 120){
                    flag = false;
                }
                queryCount = queryCount+1;
                Thread.sleep(500);
            }
            logger.info("任务池中数据运行结束，表示日志解析结束：");
        }
        // 获取数据库中的数据
        historyTaskQueryParams.setTaskOpenDate(historyTaskQueryParams.getTaskOpenDate());
        historyTaskQueryParams.setTaskEndDate(historyTaskQueryParams.getTaskEndDate());
        Page page=  PageHelper.startPage(historyTaskQueryParams.getPageNum(),historyTaskQueryParams.getPageSize());
        if(StringUtils.isNotEmpty(historyTaskQueryParams.getSortName())&&StringUtils.isNotEmpty(historyTaskQueryParams.getSortBy())){
            page.setOrderBy(historyTaskQueryParams.getSortName()+" "+historyTaskQueryParams.getSortBy());
        }
        List<DataReportPageReturn> returnList = taskPageQueryDao.queryHistoryTaskLogDao(historyTaskQueryParams);
        PageInfo<DataReportPageReturn> pageInfo = new PageInfo<DataReportPageReturn>(returnList);
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        logger.info("数据同步报表查询到的数据为："+ JSONObject.toJSONString(map));
        return map;
    }

    @Override
    public void exportDataTable(DataSynchronizationReportPageParams queryParams, HttpServletResponse response) {
        try{
            //文件名称
            String name = "数据同步报表统计信息";
            //表标题
            String[] titles = {"任务实例ID","历史任务自增ID", "工作流节点ID","工作流节点名称","工作流所属项目","同步状态","源表名","源表存储位置",
                    "源表所在库","目标表名","目标表存储位置","目标表所在库","同步周期","任务创建时刻","任务启动时刻","任务结束时刻","任务运行耗时",
                    "任务平均流量","任务写入速度","读出记录总数","读写失败总数","写入成功总数"};
            //列对应字段
            String[] fieldName = new String[]{"taskId","id","nodeId","nodeName","nodeFlowName","status","sourceTableName", "sourceType",
                    "sourceTableProject","targetTableName","targetType","targetTableProject","cronExpress","createTime","beginRunningTime","finishTime","taskTotalTime",
                    "taskAverageTraffic","recordWritingSpeed","readRecords","readFailureRecords","writeRecords"};

            // 获取数据同步报表的列表信息
            queryParams.setTaskOpenDate(queryParams.getTaskOpenDate()+" 00:00:00");
            queryParams.setTaskEndDate(queryParams.getTaskEndDate()+" 23:59:59");
            List<DataReportPageReturn> lists =taskPageQueryDao.getDataSynchronizationReportDao(queryParams);
            for(DataReportPageReturn dataReportPageReturn:lists){
                if(dataReportPageReturn.getSynchronizationStatus() == 6){
                    dataReportPageReturn.setStatus("运行成功");
                }else if(dataReportPageReturn.getSynchronizationStatus() == 5){
                    dataReportPageReturn.setStatus("运行失败");
                }else if(dataReportPageReturn.getSynchronizationStatus() == 4){
                    dataReportPageReturn.setStatus("运行中");
                }else if(dataReportPageReturn.getSynchronizationStatus() == 3){
                    dataReportPageReturn.setStatus("等待资源");
                }else if(dataReportPageReturn.getSynchronizationStatus() == 2){
                    dataReportPageReturn.setStatus("等待时间");
                }else if(dataReportPageReturn.getSynchronizationStatus() == 1){
                    dataReportPageReturn.setStatus("未运行");
                }else{
                    dataReportPageReturn.setStatus(String.valueOf(dataReportPageReturn.getSynchronizationStatus()));
                }
            }
            //响应
            ServletOutputStream out=response.getOutputStream();
            response.setContentType("application/json;charset=UTF-8");
            logger.info("查询结束");
            switch (queryParams.getType()){
                case "1":
                    // 导出excel
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
                    List<Object> listNew=new ArrayList<Object>();
                    listNew.addAll(lists);
                    ExcelHelper.export(new DataReportPageReturn(), titles, name, listNew, fieldName, out);
                    break;
                case "2":
                    // 导出csv
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".csv", "UTF-8"));
                    List<Object> csvList=new ArrayList<Object>();
                    csvList.addAll(lists);
                    ExportUtil.exportToCsv(out, csvList, name, titles, fieldName);
                    break;
                case "3":
                    // 导出word
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".doc", "UTF-8"));
                    List<Object> wordList=new ArrayList<Object>();
                    wordList.addAll(lists);
                    int[] colWidths = {953,953,953,1253,953,953,953,953,953,953,953,953,953,953,953,953,953,953,853,853,853};
                    ExportUtil.exportToWord(out, wordList, name, titles, fieldName, "A3", colWidths);
                    break;
                default:
            }
            logger.info("===================导出结束===================");
        }catch (Exception e){
            logger.error("导出报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public void exportHistoryTaskTable(HistoryTaskQueryParams historyTaskQueryParams, HttpServletResponse response) {
        try{
            //文件名称
            String name = "历史日志查询结果";
            //表标题
            String[] titles = {"工作流节点名称","源表名","源表存储位置","源表所在库","目标表名","目标表存储位置","目标表所在库",
                    "任务创建时刻","任务启动时刻","任务结束时刻","任务运行耗时","任务平均流量","任务写入速度",
                    "读出记录总数","读写失败总数","写入成功总数"};
            //列对应字段
            String[] fieldName = new String[]{"nodeName","sourceTableName","sourceType","sourceTableProject","targetTableName",
                    "targetType", "targetTableProject", "createTime","beginRunningTime","finishTime","taskTotalTime",
                    "taskAverageTraffic","recordWritingSpeed","readRecords","readFailureRecords","writeRecords"};
            // 获取数据同步报表的列表信息
            historyTaskQueryParams.setTaskOpenDate(historyTaskQueryParams.getTaskOpenDate()+" 00:00:00");
            historyTaskQueryParams.setTaskEndDate(historyTaskQueryParams.getTaskEndDate()+" 23:59:59");
            List<DataReportPageReturn> lists = taskPageQueryDao.queryHistoryTaskLogDao(historyTaskQueryParams);
            //响应
            ServletOutputStream out=response.getOutputStream();
            response.setContentType("application/json;charset=UTF-8");
            logger.info("查询结束");
            switch (historyTaskQueryParams.getType()){
                case "1":
                    // 导出excel
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
                    List<Object> listNew=new ArrayList<Object>();
                    listNew.addAll(lists);
                    ExcelHelper.export(new DataReportPageReturn(), titles, name, listNew, fieldName, out);
                    break;
                case "2":
                    // 导出csv
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".csv", "UTF-8"));
                    List<Object> csvList=new ArrayList<Object>();
                    csvList.addAll(lists);
                    ExportUtil.exportToCsv(out, csvList, name, titles, fieldName);
                    break;
                case "3":
                    // 导出word
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".doc", "UTF-8"));
                    List<Object> wordList=new ArrayList<Object>();
                    wordList.addAll(lists);
                    int[] colWidths = {1550,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1150,1150,1150};
                    ExportUtil.exportToWord(out, wordList, name, titles, fieldName, "A3", colWidths);
                    break;
                default:
            }
            logger.info("===================导出结束===================");
        }catch (Exception e){
            logger.error("导出报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }
}
