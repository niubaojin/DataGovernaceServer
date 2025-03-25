package com.synway.datarelation.service.sync.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.util.AsyManager;
import com.synway.datarelation.dao.DAOHelper;
import com.synway.datarelation.dao.taskinstance.TaskDefinitionInstanceDao;
import com.synway.datarelation.dao.taskinstance.TaskQueryV2Dao;
import com.synway.datarelation.pojo.datawork.TaskRunAnalyze;
import com.synway.datarelation.pojo.datawork.v3.TaskRunQueryParameters;
import com.synway.datarelation.pojo.datawork.v2.LogQueryParameters;
import com.synway.datarelation.pojo.modelmonitor.ModelNodeInsInfo;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;
import com.synway.datarelation.pojo.modelmonitor.ResultObj;
import com.synway.datarelation.service.sync.SyncWorkFlowV2Service;
import com.synway.datarelation.util.DataIdeUtil;
import com.synway.datarelation.util.DateUtil;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.v3.TaskRunLogParsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * datawork为2.0版本的接口数据
 * 阿里v2版本同步工作流解析
 */
@Service
public class SyncWorkFlowV2ServiceImpl implements SyncWorkFlowV2Service {
    private static Logger logger = LoggerFactory.getLogger(SyncWorkFlowV2ServiceImpl.class);

    @Autowired
    private DataIdeUtil dataIdeUtil;
    @Autowired
    private TaskQueryV2Dao taskQueryV2Dao;
    @Autowired
    TaskDefinitionInstanceDao taskDefinitionInstanceDao;
    @Autowired
    AsyManager asyManager;

    /**
     *  获取任务信息
     */
    @Override
    public void queryTask(NodeInsQueryParam nodeInsQueryParam , Boolean queryLogFlag) throws Exception {
        logger.info("开始查询同步任务的所有实例信息");
        //查当天类型是同步任务（23）的实例
        if(nodeInsQueryParam == null){
            nodeInsQueryParam = new NodeInsQueryParam();
            nodeInsQueryParam.setDagType(23);
            nodeInsQueryParam.setCreateTime(DateUtil.formatDateTime(DateUtil.getDayBegin(new Date()),DateUtil.DEFAULT_PATTERN_DATETIME));
            nodeInsQueryParam.setLimit(0);
            nodeInsQueryParam.setStart(0);
        }
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API获取工作流实例信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        int start = 0;
        int limit = resultObj.getCount();
        nodeInsQueryParam.setLimit(limit);
        nodeInsQueryParam.setStart(start);
        resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        resultObj = JSON.parseObject(resultStr,ResultObj.class);
        returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API获取工作流实例信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        List<ModelNodeInsInfo> newModelNodeInsInfos = JSON.parseArray(resultObj.getReturnValue(),ModelNodeInsInfo.class);

        // 根据 taskId值清除相同的值
        newModelNodeInsInfos = newModelNodeInsInfos.stream().filter(d -> d.getInstanceId() !=null).collect(
                collectingAndThen(
                        toCollection(() ->new TreeSet<>(comparingLong(ModelNodeInsInfo::getInstanceId))), ArrayList::new)
        );
        List<ModelNodeInsInfo> modelNodeInsInfosList = newModelNodeInsInfos.stream().filter(d -> d.getHistoryId() !=null).collect(
                collectingAndThen(
                        toCollection(() ->new TreeSet<>(comparingLong(ModelNodeInsInfo::getHistoryId))), ArrayList::new)
        );
        List<ModelNodeInsInfo> modelNodeInsInfosList1 = new ArrayList<>();
        modelNodeInsInfosList.forEach(
                item->{
                    //去除项目名中的prod
                    item.setProjectName(item.getProjectName().replaceAll("_prod",""));
                    modelNodeInsInfosList1.add(item);
                }
        );
        newModelNodeInsInfos.addAll(modelNodeInsInfosList1);
        if(newModelNodeInsInfos != null &&newModelNodeInsInfos.size() >0){
            // 删除指定时间对应的已经存储好的数据
            int deleteNum = taskQueryV2Dao.deleteFlow(nodeInsQueryParam.getCreateTime(),23);
            logger.info("删除的数据量："+deleteNum);
            //然后将查询到的数据插入到数据库中
            DAOHelper.insertDelList(newModelNodeInsInfos, taskQueryV2Dao,"insertFlow",500);
        }else{
            logger.info("查询到的数据量为空，不进行删除和插入操作");
        }
        // 判断是否需要查询并解析日志
        if(queryLogFlag){
            // 根据目前查询到的任务实例ID  查询数据库中该任务是否已经是任务结束的任务 如果是已经结束，
            // 不需要重新查询该任务实例的日志信息，
            List<String> taskIdList = newModelNodeInsInfos.parallelStream().filter(d -> d.getInstanceId() !=null).map(o->String.valueOf(o.getInstanceId())).distinct().collect(Collectors.toList());
            List<String> historyIdList =newModelNodeInsInfos.parallelStream().filter(d->d.getHistoryId() !=null).map(o->String.valueOf(o.getHistoryId())).distinct().collect(Collectors.toList());
            historyIdList = historyIdList.size() == 0?null:historyIdList;
            taskIdList = taskIdList.size() == 0?null:taskIdList;
            List<String> needQueryLogTaskList = new ArrayList<>();
            if(historyIdList == null && taskIdList == null){

            }else{
                needQueryLogTaskList = taskDefinitionInstanceDao.getNormalTask(taskIdList,historyIdList);
            }
            int numLog = 0;
            for(ModelNodeInsInfo oneTaskEntity:newModelNodeInsInfos){
                if(oneTaskEntity.getStatus() == 6 ||oneTaskEntity.getStatus() == 4 ||
                        oneTaskEntity.getStatus() == 5 ){
                    Integer id = oneTaskEntity.getHistoryId();
                    Integer taskId = oneTaskEntity.getInstanceId();
                    String idStr = (id == null?"":id) +"|"+(taskId == null?"":taskId);
                    if(!needQueryLogTaskList.contains(idStr)){
                        numLog = numLog+1;
                        LogQueryParameters taskRunQueryParameters = new LogQueryParameters();
                        taskRunQueryParameters.setHistoryInsId(id);
                        taskRunQueryParameters.setNodeInsId(taskId);
                        asyManager.addTask(()->{
                            try{
                                queryLog(taskRunQueryParameters,false);
                            }catch(Exception e){
                                logger.error("任务实例获取日志信息报错"+ ExceptionUtil.getExceptionTrace(e));
                            }
                        });
                    }
                }
            }
            logger.info("需要查询日志数据量"+numLog);
        }
        newModelNodeInsInfos.clear();
        logger.info("=================获取任务实例信息结束===============");
    }

    /**
     * 日志查询
     * @param logQueryParameters
     * @param saveFlag
     * @throws Exception
     */
    @Override
    public void queryLog(LogQueryParameters logQueryParameters, Boolean saveFlag) throws Exception {
        logger.info("开始查询任务实例运行日志信息，参数为："+ JSONObject.toJSONString(logQueryParameters));
        if(logQueryParameters == null ||
                (logQueryParameters.getHistoryInsId() == null && logQueryParameters.getNodeInsId() == null)){
            throw new Exception("传入的参数为空，不能查询日志信息");
        }
        String resultStr = "";
        if(logQueryParameters.getNodeInsId() != null){
            resultStr = dataIdeUtil.getlog(logQueryParameters.getNodeInsId());
        }else if(logQueryParameters.getHistoryInsId()!= null){
            resultStr = dataIdeUtil.getHistorylog(logQueryParameters.getHistoryInsId());
        }else{
            throw new Exception("传入的参数为空，不能查询日志信息");
        }
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        TaskRunAnalyze taskRunAnalyze = null;
        try{
            taskRunAnalyze = TaskRunLogParsing.parsingLog(resultObj.getReturnValue());
        }catch (Exception e){
            logger.error("解析日志报错:"+JSONObject.toJSONString(resultObj)+"\n"+ ExceptionUtil.getExceptionTrace(e));
        }
        if(taskRunAnalyze == null){
            taskRunAnalyze =new TaskRunAnalyze();
        }
        taskRunAnalyze.setId(String.valueOf(logQueryParameters.getHistoryInsId()==null?"":logQueryParameters.getHistoryInsId()));
        taskRunAnalyze.setTaskId(String.valueOf(logQueryParameters.getNodeInsId()==null?"":logQueryParameters.getNodeInsId()));
        logger.info("查询日志解析结果为\n"+JSONObject.toJSONString(taskRunAnalyze));
        //  将解析到的数据插入到数据库中 解析的数据插入到数据库中
        // 以及将日志的原始信息插入到另外一张表中
        // 如果数据库中存在该 taskId对应的数据，则更改该值，如果不存在，则插入该值
        //  表名 task_run_analyze
        TaskRunQueryParameters taskRunQueryParameters = new TaskRunQueryParameters();
        taskRunQueryParameters.setHistoryId(Long.valueOf(logQueryParameters.getHistoryInsId()));
        taskRunQueryParameters.setTaskId(Long.valueOf(logQueryParameters.getNodeInsId()));
        int count = taskDefinitionInstanceDao.getTaskDefinitionCountDao(taskRunQueryParameters);
        if( count>=1){
            taskDefinitionInstanceDao.updateTaskRunAnalyzeDao(taskRunAnalyze);
        }else{
            taskDefinitionInstanceDao.insertTaskRunAnalyzeDao(taskRunAnalyze);
        }
        if(saveFlag){
            int logCount =taskDefinitionInstanceDao.getTaskLogCountDao(taskRunQueryParameters);
            if(logCount>=1){
                taskDefinitionInstanceDao.updateTaskLogMessageDao(taskRunAnalyze.getId(),taskRunAnalyze.getTaskId(), resultObj.getReturnValue());
            }else{

                taskDefinitionInstanceDao.insertTaskLogMessageDao(taskRunAnalyze.getId(),taskRunAnalyze.getTaskId(), resultObj.getReturnValue());
            }
        }
        logger.info("解析任务日志信息结束============================");


    }



}
