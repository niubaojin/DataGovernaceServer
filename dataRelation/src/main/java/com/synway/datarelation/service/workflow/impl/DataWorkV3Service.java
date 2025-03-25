package com.synway.datarelation.service.workflow.impl;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.synway.datarelation.pojo.datawork.v3.NodeQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.ResponseBody;
import com.synway.datarelation.service.workflow.WorkFlowService;
import com.synway.datarelation.util.FileUtil;
import com.synway.datarelation.util.SpringBeanUtil;
import com.synway.datarelation.util.AsyManager;
import com.synway.datarelation.dao.DAOHelper;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.dao.datablood.LineageColumnParsingDao;
import com.synway.datarelation.dao.monitor.NormalInstanceDao;
import com.synway.datarelation.dao.taskinstance.TaskDefinitionInstanceDao;
import com.synway.datarelation.dao.modelrelation.ModelRelationServiceImplDataWorkV3Dao;
import com.synway.datarelation.pojo.monitor.*;
import com.synway.datarelation.pojo.monitor.dag.DagEntity;
import com.synway.datarelation.pojo.monitor.DagRunQueryParameters;
import com.synway.datarelation.pojo.monitor.business.BusinessEntity;
import com.synway.datarelation.pojo.monitor.node.MaxVersionNodeId;
import com.synway.datarelation.pojo.monitor.page.NormalBusinessInfo;
import com.synway.datarelation.pojo.monitor.table.InOutTable;
import com.synway.datarelation.pojo.datawork.TaskRunAnalyze;
import com.synway.datarelation.pojo.datawork.v3.*;
import com.synway.datarelation.constant.TaskStatus;
import com.synway.datarelation.pojo.modelrelation.DataBloodlineInOutTable;
import com.synway.datarelation.pojo.modelrelation.DataBloodlineRaw;
import com.synway.datarelation.pojo.modelrelation.ModelLinkData;
import com.synway.datarelation.service.datablood.TaskService;
import com.synway.datarelation.service.heat.impl.UseHeatServiceImpl;
import com.synway.datarelation.util.DateUtil;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.v3.DataWorksApi;
import com.synway.datarelation.util.v3.PopBaseApi;
import com.synway.datarelation.util.v3.TableBloodlineUtil;
import com.synway.datarelation.util.v3.TaskRunLogParsing;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author wangdongwei
 * @ClassName DataWorkV3Interface
 * @description 这个是阿里云平台v3版本的相关接口以及定时调度  v3版本调用的接口程序较多
 * 1： 模型血缘 需要查询各种节点信息插入到数据库  getNode
 * 2： 查询所有任务的运行状况  getTask
 * 3： 查询同步任务的日志信息，来解析出任务的运行状况 生成同步任务监控
 * 4： 查询出所有节点的代码信息，然后再获取出节点的代码，并将代码信息插入到数据库中
 * 5： 获取数据库中节点的代码信息 解析出表血缘和字段血缘信息
 * <p>
 * runInterfaceQuery
 * @date 2020/12/2 10:41
 */
public class DataWorkV3Service implements WorkFlowService {
    private static final Logger logger = LoggerFactory.getLogger(DataWorkV3Service.class);

    @Value("${workFlowsFilePath}")
    private String workFlowsFilePath;

    private DataWorksApi dataWorksApi;
    private AsyManager asyManager;
    private ModelRelationServiceImplDataWorkV3Dao modelRelationServiceImplDataWorkV3Dao;
    private TaskService taskServiceImpl;
    private UseHeatServiceImpl useHeatServiceImpl;
    private PopBaseApi popBaseApi;
    private List<NodeEntity> nodeInfoCache;
    private DataBloodlineDao dataBloodlineDao;
    private LineageColumnParsingDao lineageColumnParsingDao;
    private TaskDefinitionInstanceDao taskDefinitionInstanceDao;
    private NormalInstanceDao normalInstanceDao;

    public DataWorkV3Service() {
        dataWorksApi = SpringBeanUtil.getBean(DataWorksApi.class);
        asyManager = SpringBeanUtil.getBean(AsyManager.class);
        taskServiceImpl = SpringBeanUtil.getBean(TaskService.class);
        useHeatServiceImpl = SpringBeanUtil.getBean(UseHeatServiceImpl.class);
        popBaseApi = SpringBeanUtil.getBean(PopBaseApi.class);
        nodeInfoCache = SpringBeanUtil.getBean("nodeInfoCache", LinkedList.class);
        lineageColumnParsingDao = SpringBeanUtil.getBean(LineageColumnParsingDao.class);
        modelRelationServiceImplDataWorkV3Dao = SpringBeanUtil.getBean(ModelRelationServiceImplDataWorkV3Dao.class);
        dataBloodlineDao = SpringBeanUtil.getBean(DataBloodlineDao.class);
        normalInstanceDao = SpringBeanUtil.getBean(NormalInstanceDao.class);
        taskDefinitionInstanceDao = SpringBeanUtil.getBean(TaskDefinitionInstanceDao.class);
    }

    /**
     * 这个是数据血缘/模型血缘需要对应的数据
     * 不要想着可以分页请求所有的task，已经试过了，不行!!!
     */
    @Override
    public void runInterfaceQuery() {
        try {
            nodeInfoCache.clear();
            // 6：shell节点     10：odps_sql  11: odps_mr  221: pyodps  23：cdp  99:virtual虚节点  98:Combined Node、
            logger.info("【模型血缘定时任务】开始请求 odps_sql类型的所有节点信息");
            NodeQueryParameters nodeQueryParameters = new NodeQueryParameters();
            nodeQueryParameters.setPageStart(1);
            nodeQueryParameters.setPageSize(10);
            nodeQueryParameters.setPrgType(10);
            //  98 和 23已经有定时任务获取，本次定时任务不需要获取
            // 6：shell节点     10：odps_sql  11: odps_mr  221: pyodps  23：cdp  99:virtual虚节点  98:Combined Node、
            getNode(nodeQueryParameters, true);
            logger.info("【模型血缘定时任务】完成请求 odps_sql类型的所有节点信息");

            logger.info("【模型血缘定时任务】开始请求 cdp类型的所有节点信息");
            nodeQueryParameters.setPrgType(23);
            nodeQueryParameters.setPageStart(1);
            nodeQueryParameters.setPageSize(10);
            //  98 和 23已经有定时任务获取，本次定时任务不需要获取
            // 6：shell节点     10：odps_sql  11: odps_mr  221: pyodps  23：cdp  99:virtual虚节点  98:Combined Node、
            getNode(nodeQueryParameters, true);
            logger.info("【模型血缘定时任务】完成请求 cdp类型的所有节点信息");

            logger.info("【模型血缘定时任务】开始请求 98工作流的所有节点信息");
            nodeQueryParameters.setPrgType(98);
            nodeQueryParameters.setPageStart(1);
            nodeQueryParameters.setPageSize(10);
            getNode(nodeQueryParameters, true);
            logger.info("【模型血缘定时任务】完成请求 98工作流的所有节点信息");

            logger.info("【模型血缘定时任务】开始组合业务流程");
            //获取业务流程的名字
            getBusiness(nodeInfoCache);
            logger.info("【模型血缘定时任务】组合业务流程结束");

            logger.info("【模型血缘定时任务】开始加入节点代码");
            //加入节点的code
            searchNodeCodeToDb(nodeInfoCache);
            logger.info("【模型血缘定时任务】加入节点代码结束");

            nodeInfoCache.clear();
        } catch (Exception e) {
            logger.error("获取ali数据加工工作流数据报错", e);
        }
        try {
            logger.info("【模型血缘定时任务】开始请求血缘信息");
            getNodeInOutTable();
            logger.info("【模型血缘定时任务】请求血缘信息结束");
        } catch (Exception e) {
            logger.error("【模型血缘定时任务】请求血缘信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public void insertTask() {
        // 6：shell节点     10：odps_sql  11: odps_mr  221: pyodps  23：cdp  99:virtual虚节点  98:Combined Node、
        // 从数据库查询node对应类型的nodeID，并把数据插入数据库
        logger.info("查询 odps_sql类型的所有节点信息，并把插入库里");
        List<Long> nodeIdList = taskDefinitionInstanceDao.getNodeIdList("10");
        insertFlow(nodeIdList, 10, true);
//        logger.info("查询 cdp类型的所有节点信息，并把插入库里");
//        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("23");
//        insertFlow(nodeIdList, 23, true);
        logger.info("查询 shell类型的所有节点信息，并把插入库里");
        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("6");
        insertFlow(nodeIdList, 6, false);
        logger.info("查询 odps_mr类型的所有节点信息，并把插入库里");
        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("11");
        insertFlow(nodeIdList, 11, false);
        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("97");
        insertFlow(nodeIdList, 97, false);
        logger.info("查询 Combined Node类型的所有节点信息，并把插入库里");
        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("98");
        insertFlow(nodeIdList, 98, false);
        logger.info("查询 virtual虚节点类型的所有节点信息，并把插入库里");
        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("99");
        insertFlow(nodeIdList, 99, false);
        logger.info("查询 pyodps类型的所有节点信息，并把插入库里");
        nodeIdList = taskDefinitionInstanceDao.getNodeIdList("221");
        insertFlow(nodeIdList, 221, false);
        //查询日志
        queryLog();
    }

    private void insertFlow(List<Long> nodeIdList, int prgType, boolean queryLog) {
        List<Long> longList = new ArrayList<>();
        String createTimeFrom = DateUtil.formatDate(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
        String createTimeTo = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        for (int i = 0; i < nodeIdList.size(); i++) {
            if (i % 20 == 0 && i != 0) {
                TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
                taskQueryParameters.setPageStart(0);
                taskQueryParameters.setPageSize(5000);
                taskQueryParameters.setStartRunTimeFrom(createTimeFrom + " 00:00:00");
                taskQueryParameters.setStartRunTimeTo(createTimeTo + " 23:59:59");
                String nodeIds = StringUtils.join(longList, ",");
                taskQueryParameters.setPrgType(prgType);
                taskQueryParameters.setNodeIds(nodeIds);
                try {
                    getTask(taskQueryParameters, queryLog);
                } catch (Exception e) {
                    logger.error("getTask失败,prg_type为{},错误信息{}" , prgType,ExceptionUtil.getExceptionTrace(e));
                }
                longList.clear();
            } else if (i == nodeIdList.size() - 1) {
                TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
                taskQueryParameters.setPageStart(0);
                taskQueryParameters.setPageSize(5000);
                taskQueryParameters.setStartRunTimeFrom(createTimeFrom + " 00:00:00");
                taskQueryParameters.setStartRunTimeTo(createTimeTo + " 23:59:59");
                String nodeIds = StringUtils.join(longList, ",");
                taskQueryParameters.setPrgType(prgType);
                taskQueryParameters.setNodeIds(nodeIds);
                try {
                    getTask(taskQueryParameters, queryLog);
                } catch (Exception e) {
                    logger.error("getTask失败,prg_type为{},错误信息{}" , prgType,ExceptionUtil.getExceptionTrace(e));
                }
            } else {
                longList.add(nodeIdList.get(i));
            }
        }

    }

    @Override
    public void getDag() {
        try {
            logger.info("=====开始查询阿里云V3工作流状况=====");
            List<DagEntity> dagIds = normalInstanceDao.getDistinctDags();
            List<DagEntity> dagEntities = new ArrayList<>();
            List<String> outputList = new ArrayList<>();
            for (DagEntity dag : dagIds) {
                DagRunQueryParameters dagRunQueryParameters = new DagRunQueryParameters();
                dagRunQueryParameters.setDagId(String.valueOf(dag.getDagId()));
                String resultStr = dataWorksApi.queryDag(dagRunQueryParameters);
                outputList.add(resultStr);
                NormalResponseBody<DagEntity> resultObj = JSON.parseObject(resultStr, new TypeReference<NormalResponseBody<DagEntity>>() {
                });
                List<DagEntity> dagEntityList = resultObj.getReturnValue();
                dagEntityList.removeIf(Objects::isNull);
                dagEntityList.parallelStream().forEach(item -> {
                    item.setCpuConsumption(dag.getCpuConsumption());
                    item.setMemoryConsumption(dag.getMemoryConsumption());
                });
                dagEntities.addAll(dagEntityList);
            }
            // 将查询到的数据去重
            try {
                List<Long> dagIdList = dagEntities.stream().filter(d -> d.getDagId() != null).map(DagEntity::getDagId).
                        distinct().collect(Collectors.toList());
                if (dagIdList.size() > 0) {
                    DAOHelper.insertDelList(dagIdList, normalInstanceDao, "deleteDagList", 20000);
                }
            } catch (Exception e) {
                logger.error("【工作流流程实例插入定时任务】删除失败" + ExceptionUtil.getExceptionTrace(e));
            }
            DAOHelper.insertDelList(dagEntities, normalInstanceDao, "insertDagList", 500);

            this.dataOutputFileCF(outputList, "getDag");
        } catch (Exception e) {
            logger.error("【工作流流程实例插入定时任务】报错" + ExceptionUtil.getExceptionTrace(e));
        }

    }

    private void dataOutputFileCF(List<String> outputList, String name ){
        if (outputList.size() > 0){
            try {
                String filePath = workFlowsFilePath + File.separator + name + ".txt";
                File outFile = new File(filePath);
                if (!outFile.exists()){
                    FileUtils.writeLines(outFile, outputList);
                }
            }catch (IOException e){
                logger.error("dataOutputFileCF-{}数据写文件失败：", name, e);
            }
        }
    }

    @Override
    public void getdataWorksMonitor() {
        try {
            List<BusinessEntity> entities = normalInstanceDao.getBusinesses();
            List<NormalBusinessInfo> returns = new ArrayList<>();
            entities.parallelStream().forEach(
                    item -> {
                        try {
                            //去除项目名中的prod
                            String projectName = item.getProjectName().replaceAll("_prod","");
                            if (item.getFlowId() != 1) {
                                //旧工作流获取监控信息
                                List<TaskEntity> flowEntities = normalInstanceDao.getTaskInfoByFlowId(item.getFlowId());
                                for (TaskEntity flowEntity : flowEntities) {
                                    List<String> tasks = normalInstanceDao.getTaskIdsByDagId(flowEntity.getDagId());
                                    String taskIds = StringUtils.join(tasks, ",");
                                    NormalBusinessInfo temp = new NormalBusinessInfo()
                                            .setAppId(item.getAppId())
                                            .setFlowId(item.getFlowId())
                                            .setName(item.getBizName())
                                            .setProjectName(projectName)
                                            .setNodeIds(item.getNodeIds())
                                            .setStartTime(flowEntity.getBeginWaitTimeTime())
                                            .setFinishTime(flowEntity.getFinishTime())
                                            .setCpuConsumption(flowEntity.getCpuConsumption())
                                            .setMemoryConsumption(flowEntity.getMemoryConsumption())
                                            .setStatus(TaskStatus.getValue(flowEntity.getStatus()))
                                            .setBizDate(flowEntity.getBizdate())
                                            .setTaskIds(taskIds);
                                    if (temp.getStartTime() != null && temp.getFinishTime() != null) {
                                        temp.setRunningTime((temp.getFinishTime().getTime() - temp.getStartTime().getTime()) / 1000);
                                    }
                                    returns.add(temp);
                                }
                            } else if (item.getFlowId() == 1 && item.getBizId() != null) {
                                //新业务流程获取监控信息
                                List<String> dagIds = normalInstanceDao.getTaskInfoByBizId(item.getBizId());
                                for (String dagId : dagIds) {
                                    TaskEntity taskEntity = normalInstanceDao.getTaskInfoByDagIdAndBizId(dagId, item.getBizId());
                                    List<String> tasks = normalInstanceDao.getTaskIdsByDagIdAndBizId(dagId, item.getBizId());
                                    String businessName;
                                    if (tasks.size() == 1 && String.valueOf(item.getBizId()).equals(item.getBizName())) {
                                        businessName = taskEntity.getNodeName();
                                    } else if(StringUtils.isBlank(item.getBizName()) && StringUtils.isNotBlank(taskEntity.getNodeName())){
                                        businessName = taskEntity.getNodeName();
                                    }else{
                                        businessName = item.getBizName();
                                    }
                                    String taskIds = StringUtils.join(tasks, ",");
                                    NormalBusinessInfo temp = new NormalBusinessInfo()
                                            .setAppId(item.getAppId())
                                            .setBizId(item.getBizId())
                                            .setFlowId(item.getFlowId())
                                            .setName(businessName)
                                            .setProjectName(projectName)
                                            .setNodeIds(item.getNodeIds())
                                            .setStartTime(taskEntity.getBeginWaitTimeTime())
                                            .setFinishTime(taskEntity.getFinishTime())
                                            .setCpuConsumption(taskEntity.getCpuConsumption())
                                            .setMemoryConsumption(taskEntity.getMemoryConsumption())
                                            .setStatus(TaskStatus.getValue(taskEntity.getStatus()))
                                            .setBizDate(taskEntity.getBizdate())
                                            .setTaskIds(taskIds);
                                    if (temp.getStartTime() != null && temp.getFinishTime() != null) {
                                        temp.setRunningTime((temp.getFinishTime().getTime() - temp.getStartTime().getTime()) / 1000);
                                    }
                                    returns.add(temp);
                                }
                            } else if (StringUtils.isNotBlank(item.getNodeIds()) && !item.getNodeIds().contains(",")) {
                                //单节点任务,一个任务显示一个
                                List<TaskEntity> taskEntities = normalInstanceDao.getTaskInfoByNodeId(item.getNodeIds());
                                for (TaskEntity taskEntity : taskEntities) {
                                    NormalBusinessInfo temp = new NormalBusinessInfo()
                                            .setAppId(item.getAppId())
                                            .setBizId(item.getBizId())
                                            .setFlowId(item.getFlowId())
                                            .setName(item.getBizName())
                                            .setBizDate(taskEntity.getBizdate())
                                            .setProjectName(projectName)
                                            .setNodeIds(item.getNodeIds())
                                            .setStartTime(taskEntity.getBeginWaitTimeTime())
                                            .setFinishTime(taskEntity.getFinishTime())
                                            .setCpuConsumption(taskEntity.getCpuConsumption())
                                            .setMemoryConsumption(taskEntity.getMemoryConsumption())
                                            .setStatus(TaskStatus.getValue(taskEntity.getStatus()))
                                            .setTaskIds(String.valueOf(taskEntity.getTaskId()));
                                    if (temp.getStartTime() != null && temp.getFinishTime() != null) {
                                        temp.setRunningTime((temp.getFinishTime().getTime() - temp.getStartTime().getTime()) / 1000);
                                    }
                                    returns.add(temp);
                                }
                            } else {
                                logger.error("【工作流监控数据不符合所有业务流程特征】" + item);
                            }
                        } catch (Exception e) {
                            logger.error("【工作流监控数据插入失败】" + item + "\n" + ExceptionUtil.getExceptionTrace(e));
                        }
                    }
            );

            if (returns == null || returns.size() <=0){
                return;
            }
            int updateFlowBusinessNum = normalInstanceDao.updateTaskBusinessName(returns);
            logger.info("更新{}条task", updateFlowBusinessNum);
            Map map = new HashMap();
            returns.removeIf(item -> {
                if ("未运行".equals(item.getStatus())) {
                    if (map.get(item) == null) {
                        map.put(item, true);
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            });
            int delNum = normalInstanceDao.deleteBusinessInstance();
            logger.info("【工作流监控数据删除{}条数据】", delNum);
            if (returns.size() > 0) {
                DAOHelper.insertDelList(returns, normalInstanceDao, "insertBusinessInstance", 1000);
            }
            logger.info("【工作流监控数据插入{}条数据】", returns.size());
        } catch (Exception e) {
            logger.error("【工作流监控数据插入失败】" + ExceptionUtil.getExceptionTrace(e));
        }
    }


    /**
     * 这个是 98 工作流任务的运行状况的汇总程序 用于给触发器定时调用
     */
    @Override
    public void modelMonitorStatistic() {
//        try{
//            logger.info("=====开始查询阿里云V3工作流任务运行状况=====");
//            NodeQueryParameters nodeQueryParameters = new NodeQueryParameters();
//            nodeQueryParameters.setPageStart(1);
//            nodeQueryParameters.setPageSize(1);
////            nodeQueryParameters.setPrgType(98);
//            List<Long> nodeIdList =  getNode(nodeQueryParameters,true);
//            if(null==nodeIdList){
//                logger.error("查询节点数据为空");
//                return;
//            }
//            logger.info(String.format("查询到node的数量：[%s]",nodeIdList.size()));
//            if(nodeIdList.size()>0){
//                /**开始统计实例*/
//                statisticFlowIns(nodeIdList);
//            }
//            logger.info("=====查询阿里云V3工作流任务运行状况结束=====");
//        }catch (Exception e){
//            logger.error("查询阿里云V3工作流任务的运行状况报错"+ExceptionUtil.getExceptionTrace(e));
//        }
    }
    /**
     * 根据节点ID查询运行nodeType为98的实例
     * @param nodeIdList
     * @throws Exception
     */
//    private void statisticFlowIns(List<Long> nodeIdList)throws Exception{
//        String createTime = DateUtil.formatDate(DateUtil.addDay(new Date(),-1),"yyyy-MM-dd");
//        String createTimeTo = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
//        int nodeIdSize = nodeIdList.size();
//        /**接口bug：实例每次查询数量只能查出500数据量，可以考虑把step的值设置的小一点*/
//        int step = 100;
//        int queryCount = (int) Math.ceil((double) nodeIdSize/step);
//        int fromIndex;
//        int toIndex;
//        List<Long> queryIdList;
//        for (int i = 0; i < queryCount; i++) {
//            fromIndex = i*step;
//            toIndex = (i == (queryCount-1) ? nodeIdSize : (i+1)*step);
//            queryIdList = nodeIdList.subList(fromIndex,toIndex);
//
//            String nodeIds = StringUtils.join(queryIdList,",");
//            TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
//            taskQueryParameters.setPageStart(0);
//            taskQueryParameters.setPageSize(500);
//            taskQueryParameters.setCreateTimeTo(createTimeTo+" 23:59:59");
//            taskQueryParameters.setCreateTimeFrom(createTime+" 00:00:00");
//            taskQueryParameters.setNodeIds(nodeIds);
//            /**统计98类型的任务实例*/
////            taskQueryParameters.setPrgType(98);
//            getTask(taskQueryParameters,true);
//        }
//    }

    /**
     * 同步任务的运行状况查询统一触发程序
     */
    @Override
    public void dataWorkTaskScheduled() {
        try {
            logger.info("【阿里云V3版本】开始查询同步任务的运行状况");
            String createTimeFrom = DateUtil.formatDate(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
            String createTimeTo = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
            NodeQueryParameters nodeQueryParameters = new NodeQueryParameters();
            nodeQueryParameters.setPageStart(1);
            nodeQueryParameters.setPageSize(1);
            nodeQueryParameters.setPrgType(23);
            List<Long> nodeIdList = getNode(nodeQueryParameters, true);
            logger.info("需要查询的所有nodeId的任务实例信息为：" + JSONObject.toJSONString(nodeIdList));
            if (nodeIdList != null && nodeIdList.size() > 0) {
                logger.info("需要查询nodeId的数据量为：" + nodeIdList.size());
                // 每次最多查询400个node节点对应的task信息
                List<Long> longList = new ArrayList<>();
                for (Long nodeId : nodeIdList) {
                    if (longList.size() <= 20) {
                        longList.add(nodeId);
                    } else {
                        try {
                            String nodeIds = StringUtils.join(longList, ",");
                            TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
                            taskQueryParameters.setPageStart(0);
                            taskQueryParameters.setPageSize(5000);
                            taskQueryParameters.setStartRunTimeFrom(createTimeFrom + " 00:00:00");
                            taskQueryParameters.setStartRunTimeTo(createTimeTo + " 23:59:59");
                            taskQueryParameters.setNodeIds(nodeIds);
                            taskQueryParameters.setPrgType(23);
//                               taskQueryParameters.setTaskTypes("0,1,2");
                            getTask(taskQueryParameters, false);
                            longList.clear();
                            Thread.sleep(50);
                        } catch (Exception e) {
                            longList.clear();
                            logger.error(ExceptionUtil.getExceptionTrace(e));
                        }
                    }
                }
                if (!longList.isEmpty()) {
                    String nodeIds = StringUtils.join(longList, ",");
                    TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
                    taskQueryParameters.setPageStart(0);
                    taskQueryParameters.setPageSize(5000);
                    taskQueryParameters.setStartRunTimeFrom(createTimeFrom + " 00:00:00");
                    taskQueryParameters.setStartRunTimeTo(createTimeTo + " 23:59:59");
                    taskQueryParameters.setNodeIds(nodeIds);
                    taskQueryParameters.setPrgType(23);
                    getTask(taskQueryParameters, false);
                    longList.clear();
                }
            }
            logger.info("==============【阿里云V3版本】开始查询同步任务的运行状况结束==============");
        } catch (Exception e) {
            logger.error("【阿里云V3版本】中获取同步任务运行状况报错" + ExceptionUtil.getExceptionTrace(e));
        }

    }

    /**
     * 如果传入的参数信息为null，表示需要使用默认的参数查询所有，反之按照传入的参数查询下载数据
     * 获取所有的节点信息
     *
     * @param nodeQueryParameters
     * @param queryTask           是否查询任务实例信息
     * @return 是  返回所有的 nodeId列表信息
     * @throws Exception
     */
    @Override
    public List<Long> getNode(NodeQueryParameters nodeQueryParameters, Boolean queryTask) throws Exception {
        // 如果传入的参数信息为null，表示需要使用默认的参数查询所有，反之按照传入的参数查询
        logger.info("开始查询指定参数对应的节点信息:\n" + JSONObject.toJSONString(nodeQueryParameters));
        if (nodeQueryParameters == null) {
            nodeQueryParameters = new NodeQueryParameters();
            nodeQueryParameters.setPageStart(0);
            nodeQueryParameters.setPageSize(1);
            nodeQueryParameters.setPrgType(23);
        }
        String resultStr = dataWorksApi.queryNode(nodeQueryParameters);
        ResponseBody resultObj = JSON.parseObject(resultStr, ResponseBody.class);
        String returnCode = resultObj.getReturnCode();
        if (!"0".equals(returnCode)) {
            logger.error("根据阿里api获取节点信息报错:" + resultObj);
            throw new Exception("根据阿里api获取节点信息报错:" + resultObj);
        }
        Set<NodeEntity> allReturnValue = ConcurrentHashMap.newKeySet();
        int count = resultObj.getCount();
        nodeQueryParameters.setPageStart(0);
        int pageAll = count / 100;
        nodeQueryParameters.setPageSize(100);
        // 20200529 发现不能查询全部 使用分页查询
        if (count > 0) {
            for (int i = 0; i <= pageAll; i++) {
                try {
                    nodeQueryParameters.setPageStart(i * 100);
                    String result = dataWorksApi.queryNode(nodeQueryParameters);
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionTrace(e));
                    }
                    logger.info("分页查询任务实例pageStart：" + i + "PageSize:" + 100);
                    ResponseBody resultQuery = JSON.parseObject(result, ResponseBody.class);
                    String returnQuery = resultQuery.getReturnCode();
                    if ("0".equals(returnQuery)) {
                        List<NodeEntity> nodeEntities = JSON.parseArray(resultQuery.getReturnValue(), NodeEntity.class);
                        allReturnValue.addAll(nodeEntities);
                    } else {
                        logger.info("分页查询节点信息pageStart：" + i + "PageSize:" + 100 + "报错：" + resultQuery.getReturnMessage());
                    }
                } catch (Exception e) {
                    logger.error("分页查询节点信息报错：" + ExceptionUtil.getExceptionTrace(e));
                }
            }

            if (allReturnValue.size() > 0){
                List<String> outputList = new ArrayList<>();
                for (NodeEntity entity : allReturnValue){
                    outputList.add(JSON.toJSONString(entity));
                }
                this.dataOutputFileCF(outputList, "getNode");
            }
        } else {
            logger.error("查询到的数据量为0");
            return new ArrayList<>();
        }

        if (allReturnValue.size() > 0) {
            List<NodeEntity> nodes = new ArrayList<>();
            List<NodeEntity> nodeWithChildNodes = new ArrayList<>();
            allReturnValue.forEach(
                    item -> {
                        //去除项目名中的prod
                        item.setOdpsProjectName(item.getOdpsProjectName().replaceAll("_prod",""));
                        try {
                            List<NodeEntity> nodeEntities =
                                    JSON.parseObject(dataWorksApi.getChildNode(String.valueOf(item.getNodeId()), 1),
                                            new TypeReference<NormalResponseBody<NodeEntity>>() {}).getReturnValue();
                            if (nodeEntities.size() > 0) {
                                String jsonString = JSON.toJSONString(item);
                                nodeEntities.forEach(
                                        i -> {
                                            if (!item.getNodeId().equals(i.getNodeId())) {
                                                NodeEntity newItem = JSON.parseObject(jsonString, NodeEntity.class);
                                                newItem.setNextNodeId(i.getNodeId());
                                                newItem.setNextNodeName(i.getNodeName());
                                                nodeWithChildNodes.add(newItem);
                                            }
                                        }
                                );
                            }
                        } catch (Exception e) {
                            logger.error("根据阿里api获取子节点信息报错:{}", item.getNodeId(), e);
                        }
                        nodes.add(item);
                    }
            );
            // 将查询到的数据 插入到数据库中表 m_node_three
            int prgType = nodeQueryParameters.getPrgType();

            List<MaxVersionNodeId> paramList = taskDefinitionInstanceDao.getMaxVersionNodeByPrgtype(prgType,allReturnValue);
            if (paramList!= null && !paramList.isEmpty()) {
                taskDefinitionInstanceDao.delMaxVersionNode(paramList);
                logger.info("类型{},删除的数据量为{}",prgType,paramList.size());
            }
//            int delNum = taskDefinitionInstanceDao.delNodeByPrgtype(prgType);
            logger.info("类型{}需要插入的数据量为{}",nodeQueryParameters.getPrgType(),nodes.size());
            DAOHelper.insertDelList(nodes, taskDefinitionInstanceDao, "insertNodeList", 500);
            DAOHelper.insertDelList(nodeWithChildNodes, taskDefinitionInstanceDao, "insertNodeWithChildNodes", 500);
            //处理其他数据入库
            nodeInfoCache.addAll(allReturnValue);
            if (queryTask) {
                // 只查询生产环境的数据
                List<Long> nodeIdList = allReturnValue.parallelStream().filter(d -> d.getNodeId() != null && d.getNodeType() == 0 && StringUtils.isNotEmpty(d.getOdpsProjectName())
                        ).map(NodeEntity::getNodeId).distinct().collect(Collectors.toList());
                return nodeIdList;
            } else {
                return null;
            }
        } else {
            logger.error("保存数据报错，解析出来的数据量为0");
            return null;
        }
    }

    /**
     * 添加业务流程
     *
     * @param allReturnValue
     */
    private void getBusiness(List<NodeEntity> allReturnValue) {
        Set<BusinessEntity> entities = ConcurrentHashMap.newKeySet();
        Map<Integer, BusinessEntity> businessMap = new ConcurrentHashMap<>();
        Map<Long, BusinessEntity> oldFlowMap = new ConcurrentHashMap<>();
        //获取prg_type为98的nodeEntity
        //这些nodeEntity的nodename，relatedflowid为旧工作流的名字和flowId
        //若其他节点的flowId与此relatedflowid相同，则为此旧工作流的节点
        allReturnValue.parallelStream().forEach(
                item -> {
                    if (item.getPrgType() == 98) {
                        //去除项目名中的prod
                        String projectName = item.getOdpsProjectName().replaceAll("_prod","");
                        BusinessEntity businessEntity = new BusinessEntity()
                                .setAppId(item.getAppId())
                                .setBizId(item.getBizId())
                                .setProjectName(projectName)
                                .setBizName(item.getNodeName())
                                .setFlowId(item.getRelatedFlowId());
                        oldFlowMap.put(item.getRelatedFlowId(), businessEntity);
                    }
                }
        );
        allReturnValue.forEach(
                item -> {
                    BusinessEntity businessEntity = null;
                    if (item.getRelatedFlowId() != null) {
                        return;
                    }
                    //旧工作流节点
                    if (item.getFlowId() != 1) {
                        businessEntity = oldFlowMap.get(item.getFlowId());
                        if (businessEntity != null) {
                            String nodeIds = businessEntity.getNodeIds();
                            if (StringUtils.isNotBlank(nodeIds) && !nodeIds.contains(String.valueOf(item.getNodeId()))) {
                                businessEntity.setNodeIds(nodeIds + "," + item.getNodeId());
                            } else {
                                businessEntity.setNodeIds(String.valueOf(item.getNodeId()));
                            }
                        } else {
                            return;
                        }
                    } else if (item.getAppId() != null && item.getBizId() != null) {
                        //去除项目名中的prod
                        String projectName = item.getOdpsProjectName().replaceAll("_prod","");
                        businessEntity = new BusinessEntity()
                                .setAppId(item.getAppId())
                                .setBizId(item.getBizId())
                                .setProjectName(projectName);
                        //新工作流节点
                        if (item.getFlowId() == 1L && item.getBizId() != null) {
                            BusinessEntity temp = businessMap.get(item.getBizId());
                            if (temp == null) {
                                String name = popBaseApi.getBizName(String.valueOf(item.getBizId()), String.valueOf(item.getAppId()));
                                businessEntity
                                        .setBizName(name)
                                        .setNodeIds(String.valueOf(item.getNodeId()))
                                        .setFlowId(item.getFlowId());
                                businessMap.put(item.getBizId(), businessEntity);
                            } else {
                                if (!temp.getNodeIds().contains(String.valueOf(item.getNodeId()))) {
                                    temp.setNodeIds(temp.getNodeIds() + "," + item.getNodeId());
                                }
                                return;
                            }
                        } else {
                            businessEntity
                                    .setBizName(item.getNodeName())
                                    .setNodeIds(String.valueOf(item.getNodeId()))
                                    .setFlowId(item.getFlowId());
                        }
                    }
                    if (businessEntity != null) {
                        entities.add(businessEntity);
                    }
                }
        );

        if (entities.size() > 0) {
            List<String> outputList = new ArrayList<>();
            for (BusinessEntity entity : entities){
                outputList.add(JSON.toJSONString(entity));
            }
            this.dataOutputFileCF(outputList, "getBusiness");

            int delNum = normalInstanceDao.deleteBusiness();
            logger.info("删除{}条业务流程", delNum);
            int insertBusinessNum = normalInstanceDao.insertBusiness(entities);
            logger.info("插入{}条业务流程", insertBusinessNum);
        }
    }

    /**
     * 对于任务类型  因为数据量太大时，所以只对同步任务查询日志信息  查询参数条件必须有  nodeId  nodeIds
     * 对于日志信息的解析
     * 因为需要存储日志以及解析后的信息很消耗资源,如何判断该任务实例已经解析了日志，并且任务已经运行完成
     * 如果createTime是今天， 根据 createTime参数，获取数据库中指定时间内所有的已经完成的任务实例信息（通过看是否有任务结束时间）
     * 并且是有日志信息的任务实例（如果需要查询日志），否则不用考虑日志的信息  然后再从接口查询到的数据中排除掉这些
     * 查询到后需要在数据库中删除存储日志的信息和解析的信息
     * 如果createTime不是今天，不用排查数据
     * 20200119 查所有task
     *
     * @20210621 使用 startRunTimeFrom
     *
     * @param taskQueryParameters
     * @param
     * @throws Exception
     */
    @Override
    public void getTask(TaskQueryParameters taskQueryParameters, boolean queryLog) throws Exception {
        logger.info("开始查询任务实例信息===============");
        if (taskQueryParameters == null) {
            taskQueryParameters = new TaskQueryParameters();
            taskQueryParameters.setPageStart(1);
            taskQueryParameters.setPageSize(5000);
            taskQueryParameters.setPrgType(23);
            taskQueryParameters.setStartRunTimeFrom(DateUtil.formatDateTime(DateUtil.getDayBegin(new Date()), DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE));
        }
        if (taskQueryParameters.getNodeId() == null && StringUtils.isEmpty(taskQueryParameters.getNodeIds())) {
            logger.info("查询的节点信息为空,不能查询任务实例信息");
            throw new NullPointerException("查询的节点信息为空,不能查询任务实例信息");
        }
        String resultStr = dataWorksApi.queryTask(taskQueryParameters);
        ResponseBody resultObj = JSON.parseObject(resultStr, ResponseBody.class);
        String returnCode = resultObj.getReturnCode();
        //  根据查询的 任务实例创建时间和类型 删除在数据库中的数据，然后再插入到数据库中
        if (!"0".equals(returnCode)) {
            logger.error("根据阿里api获取任务实例信息报错:" + resultObj.getReturnMessage());
            throw new NullPointerException("根据阿里api获取任务实例信息报错:" + resultObj.getReturnMessage());
        }
        // 存储所有的返回信息
        int count = resultObj.getCount();
        List<TaskEntity> returnValue = JSON.parseArray(resultObj.getReturnValue(), TaskEntity.class);
        logger.info("本次查询接口返回的数据量为：" + count);
        List<TaskEntity> allTaskEntitiesList = new ArrayList<>(returnValue);

        // 将查询到的数据去重
        allTaskEntitiesList = allTaskEntitiesList.stream().filter(d -> d.getTaskId() != null).collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(TaskEntity::getTaskId))), ArrayList::new)
        );
        allTaskEntitiesList.forEach(
                item ->{
                    //去除项目名中的prod
                    if(StringUtils.isNotBlank(item.getOdpsProjectName())){
                        item.setOdpsProjectName(item.getOdpsProjectName().toLowerCase().replaceAll("_prod",""));
                    }
                }
        );
        int allQuerySize = allTaskEntitiesList.size();
        logger.info("本次查询到的数据量为：" + allQuerySize);
        if (allQuerySize > 0) {
            // 根据返回值中的id号，先删除指定id号的数据，然后再将数据插入到数据库中
            // 可能会存在还没有删除出错，造成插入重复，所以在引用时需要自己去重。 根据ID去重
            List<Long> delIdList = allTaskEntitiesList.stream().filter(d -> d.getId() != null).map(TaskEntity::getId).distinct().
                    collect(Collectors.toList());
            List<Long> delTaskIdList = allTaskEntitiesList.stream().filter(d -> d.getTaskId() != null).map(TaskEntity::getTaskId).distinct().
                    collect(Collectors.toList());
            if (delIdList.size() > 0) {
                DAOHelper.insertDelList(delIdList, taskDefinitionInstanceDao, "delIdOldList", 20000);
            }
            if (delTaskIdList.size() > 0) {
                DAOHelper.insertDelList(delTaskIdList, taskDefinitionInstanceDao, "delTaskOldList", 20000);
            }
            logger.info("删除历史数据成功===============");
            // 删除之后就开始插入最新查询到的数据
            DAOHelper.insertDelList(allTaskEntitiesList, taskDefinitionInstanceDao, "insertTaskList", 500);
        }
        if(queryLog){
            queryLogByTask(allTaskEntitiesList);
        }
        logger.info("=================获取任务实例信息结束===============");
    }

    @Override
    public void queryLog() {
        // 根据目前查询到的任务实例ID  查询数据库中该任务是否已经是任务结束的任务 如果是已经结束，
        // 不需要重新查询该任务实例的日志信息，
        // 需要查询日志的任务信息
        //  查询 任务类型：0： 正常 1：一次性任务 的日志 暂停的任务不需要查询日志信息
        List<TaskEntity> entities = taskDefinitionInstanceDao.getTaskEntity();
        queryLogByTask(entities);
    }


    /**
     * 查询日志的具体实现
     * @param entities
     */
    private void queryLogByTask(List<TaskEntity> entities){
        List<String> taskIdList = entities.parallelStream().filter(d -> d.getTaskId() != null).map(o -> String.valueOf(o.getTaskId())).distinct().collect(Collectors.toList());
        List<String> idList = entities.parallelStream().filter(d -> d.getId() != null).map(o -> String.valueOf(o.getId())).distinct().collect(Collectors.toList());
        idList = idList.size() == 0 ? null : idList;
        taskIdList = taskIdList.size() == 0 ? null : taskIdList;
        List<String> needQueryLogTaskList = new ArrayList<>();
        if (idList == null && taskIdList == null) {

        } else {
            needQueryLogTaskList = taskDefinitionInstanceDao.getNormalTask(taskIdList, idList);
        }
        //  阿里推荐的线程池数据  ThreadPoolExecutor 获取任务实例的日志信息
        int numLog = 0;
        for (TaskEntity oneTaskEntity : entities) {
            if (oneTaskEntity.getStatus() == 6 || oneTaskEntity.getStatus() == 4
                    || oneTaskEntity.getStatus() == 5) {
                Long id = oneTaskEntity.getId();
                Long taskId = oneTaskEntity.getTaskId();
                String ids = (id == null ? "" : id) + "|" + (taskId == null ? "" : taskId);
                if (!needQueryLogTaskList.contains(ids)) {
                    numLog = numLog + 1;
                    TaskRunQueryParameters taskRunQueryParameters = new TaskRunQueryParameters();
                    taskRunQueryParameters.setHistoryId(id);
                    taskRunQueryParameters.setTaskId(taskId);
                    asyManager.addTask(() -> {
                        try {
                            if (oneTaskEntity.getPrgType() == 23) {
                                getTaskRunLog(taskRunQueryParameters, false);
                            } else {
                                getNormalTaskRunLog(taskRunQueryParameters, false);
                            }
                        } catch (Exception e) {
                            logger.error("任务实例获取日志信息报错" + taskId + ExceptionUtil.getExceptionTrace(e));
                        }
                    });
                }
            }
        }
        logger.info("需要查询日志数据量" + numLog);
    }

    private void updateFlowStatus(Long taskId) throws Exception {
        TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
        String createTimeFrom = DateUtil.formatDate(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
        String createTimeTo = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        taskQueryParameters.setPageStart(0);
        taskQueryParameters.setPageSize(500);
        taskQueryParameters.setStartRunTimeFrom(createTimeFrom + " 00:00:00");
        taskQueryParameters.setStartRunTimeTo(createTimeTo + " 23:59:59");
        taskQueryParameters.setTaskId(taskId);
        String str = dataWorksApi.queryTask(taskQueryParameters);
        ResponseBody resultObj = JSON.parseObject(str, ResponseBody.class);
        String returnCode = resultObj.getReturnCode();
        if (!"0".equals(returnCode)) {
            logger.error("根据阿里api获取任务信息报错:" + resultObj);
        } else {
            List<TaskEntity> returnValue = JSON.parseArray(resultObj.getReturnValue(), TaskEntity.class);
            returnValue = returnValue.stream().filter(d -> d.getTaskId() != null).collect(
                    collectingAndThen(
                            toCollection(() -> new TreeSet<>(comparingLong(TaskEntity::getTaskId))), ArrayList::new)
            );
            List<Long> delTaskIdList = returnValue.stream().filter(d -> d.getTaskId() != null).map(TaskEntity::getTaskId).distinct().
                    collect(Collectors.toList());
            List<TaskEntity> returnValue1 = new ArrayList<>();
            returnValue.forEach(
                    item->{
                        //去除项目名中的prod
                        item.setOdpsProjectName(item.getOdpsProjectName().replaceAll("_prod",""));
                        returnValue1.add(item);
                    }
            );
            if (delTaskIdList.size() > 0) {
                taskDefinitionInstanceDao.delTaskOldList(delTaskIdList);
            }
            taskDefinitionInstanceDao.insertTaskList(returnValue1);
        }
        List<String> statusList = normalInstanceDao.getDagStatus(taskId);
        //未运行：业务流程包含的所有节点都是未运行状态。
        //运行中：运行中+未运行、运行中+等待中+未运行、未运行+成功。
        //等待中：至少一个节点是“等待中”，其余节点的状态不包含“运行中”。
        //运行成功： 业务流程包含的所有节点都是运行成功。
        //运行失败：至少一个节点的状态是“运行失败”。
        //TODO 需要测试
        String status = "运行成功";
        boolean flag = false;
        boolean waitStatus = false;
        boolean runstatus = true;
        for (int i = 0; i < statusList.size(); i++) {
            String temp = statusList.get(i);
            String tempStatus = TaskStatus.getValue(Integer.parseInt(temp));
            if ("未运行".equals(tempStatus) && !flag) {
                flag = true;
                status = "未运行";
            } else if ("运行失败".equals(tempStatus)) {
                status = "运行失败";
                break;
            } else if (("等待时间".equals(tempStatus) || "等待资源".equals(tempStatus)) && !waitStatus) {
                waitStatus = true;
                runstatus = false;
            } else if ("运行中".equals(tempStatus)) {
                status = tempStatus;
            }
        }
        if (flag && waitStatus && !"运行中".equals(status) && !"运行失败".equals(status)) {
            status = "等待中";
        } else if (flag && runstatus && !"运行中".equals(status) && !"运行失败".equals(status)) {
            status = "运行中";
        }
        normalInstanceDao.updateBusinessStatus(taskId, status);
    }

    /**
     * 获取指定任务实例的日志信息并解析日志，将获取的信息插入到数据库中
     *
     * @param taskRunQueryParameters
     * @return
     * @throws Exception
     */
    @Override
    public String getTaskRunLog(TaskRunQueryParameters taskRunQueryParameters, Boolean saveLog) throws Exception {
        List<InOutTable> resultList = new ArrayList<>();
        logger.info("开始查询任务实例运行日志信息，参数为：" + JSONObject.toJSONString(taskRunQueryParameters));
        if (taskRunQueryParameters == null ||
                (taskRunQueryParameters.getTaskId() == null && taskRunQueryParameters.getHistoryId() == null)) {
            logger.error("查询信息中存在空值，不能进行查询");
            throw new Exception("查询信息中存在空值，不能进行查询");
        }
        String resultStr = dataWorksApi.getTaskRunLog(taskRunQueryParameters);
        ResponseBody resultBody = JSON.parseObject(resultStr, ResponseBody.class);
        TaskRunAnalyze taskRunAnalyze = null;
//        logger.info("日志信息为："+JSONObject.toJSONString(resultBody));
        try {
            taskRunAnalyze = TaskRunLogParsing.parsingLog(resultBody.getReturnValue());
        } catch (Exception e) {
            logger.error("解析日志报错:" + JSONObject.toJSONString(resultBody) + "\n" + ExceptionUtil.getExceptionTrace(e));
        }
        // 查看 在task_run_log 表中数据是否存在
        boolean flag = false;
        if (taskRunAnalyze == null) {
            taskRunAnalyze = new TaskRunAnalyze();
            flag = true;
        }
        taskRunAnalyze.setId(String.valueOf(taskRunQueryParameters.getHistoryId() == null ? "" : taskRunQueryParameters.getHistoryId()));
        taskRunAnalyze.setTaskId(String.valueOf(taskRunQueryParameters.getTaskId() == null ? "" : taskRunQueryParameters.getTaskId()));
        logger.info("查询日志解析结果为\n" + JSONObject.toJSONString(taskRunAnalyze));
        //  将解析到的数据插入到数据库中 解析的数据插入到数据库中
        // 以及将日志的原始信息插入到另外一张表中
        // 如果数据库中存在该 taskId对应的数据，则更改该值，如果不存在，则插入该值
        //  表名 task_run_analyze
        int count = taskDefinitionInstanceDao.getTaskDefinitionCountDao(taskRunQueryParameters);
        if (count >= 1) {
            taskDefinitionInstanceDao.updateTaskRunAnalyzeDao(taskRunAnalyze);
        } else {
            taskDefinitionInstanceDao.insertTaskRunAnalyzeDao(taskRunAnalyze);
        }
        if (saveLog) {
            int logCount = taskDefinitionInstanceDao.getTaskLogCountDao(taskRunQueryParameters);
            if (logCount >= 1) {
                taskDefinitionInstanceDao.updateTaskLogMessageDao(taskRunAnalyze.getId(), taskRunAnalyze.getTaskId(), resultBody.getReturnValue());
            } else {
                taskDefinitionInstanceDao.insertTaskLogMessageDao(taskRunAnalyze.getId(), taskRunAnalyze.getTaskId(), resultBody.getReturnValue());
            }
        }

        if (flag) {
            return resultBody.getReturnValue();
        }

        InOutTable table = new InOutTable();
        table.setId(String.valueOf(taskRunQueryParameters.getTaskId()));
        table.setInputTableName(taskRunAnalyze.getSourceTableProject() + '.' + taskRunAnalyze.getSourceTableName());
        table.setInputNum(String.valueOf(taskRunAnalyze.getReadRecords()));
        table.setOutputTableName(taskRunAnalyze.getTargetTableProject() + '.' + taskRunAnalyze.getTargetTableName());
        table.setOutputNum(String.valueOf(taskRunAnalyze.getReadRecords() - taskRunAnalyze.getReadFailureRecords()));
        table.setTaskId(taskRunQueryParameters.getTaskId());
        resultList.add(table);
        //插入前先删除
        int delNum = normalInstanceDao.deleteTaskInOutRecord(taskRunQueryParameters.getTaskId());
        logger.info("删除了{}条M_FLOW_IN_OUT_RECORD表中TASK_ID为{}的记录", delNum, taskRunQueryParameters.getTaskId());
        int updateNum;
        if (resultList.size() > 0) {
            updateNum = normalInstanceDao.insertTaskInOutRecord(resultList);
            logger.info("增加了{}条M_FLOW_IN_OUT_RECORD表中TASK_ID为{}的记录", updateNum, taskRunQueryParameters.getTaskId());
        }
        //更新输出表数，最后输出记录数
        updateNum = normalInstanceDao.updateOutTableInfo(taskRunQueryParameters.getTaskId(), 1, String.valueOf(taskRunAnalyze.getReadRecords() - taskRunAnalyze.getReadFailureRecords()));
        logger.info("更新输出表数和输出记录数{}条", updateNum);

        logger.info("解析日志成功，修改M_FLOW_THREE和M_BUSINESS_INSTANCE的状态");

        updateFlowStatus(taskRunQueryParameters.getTaskId());

        //        System.gc();
        logger.info("解析任务日志信息结束============================");
        return resultBody.getReturnValue();
    }

    /**
     * 获取指定任务实例的日志信息并解析日志，将获取的信息插入到数据库中
     *
     * @param taskRunQueryParameters
     * @throws Exception
     */
    public String getNormalTaskRunLog(TaskRunQueryParameters taskRunQueryParameters, Boolean saveLog) throws Exception {

        logger.info("开始查询任务实例运行日志信息，参数为：" + JSONObject.toJSONString(taskRunQueryParameters));
        if (taskRunQueryParameters == null ||
                (taskRunQueryParameters.getTaskId() == null && taskRunQueryParameters.getHistoryId() == null)) {
            logger.error("查询信息中存在空值，不能进行查询");
            throw new Exception("查询信息中存在空值，不能进行查询");
        }
        String resultStr = dataWorksApi.getTaskRunLog(taskRunQueryParameters);
        ResponseBody resultBody = JSON.parseObject(resultStr, ResponseBody.class);
        String resultLog = "";
        try {
            resultLog = resultBody.getReturnValue();
        } catch (Exception e) {
            logger.error("解析日志报错:" + JSONObject.toJSONString(resultBody) + "\n" + ExceptionUtil.getExceptionTrace(e));
        }
        String patternResult = "Summary:(\\\\r)?\\\\n.*?GB \\* Min";
        Pattern r = Pattern.compile(patternResult);
        Matcher m = r.matcher(resultStr);
        double maxCpuConsumption = 0;
        double maxMemoryConsumption = 0;
        while (m.find()) {
            //示例：
            //Summary:
            //resource cost: cpu 247.13 Core * Min, memory 247.13 GB * Min
            //inputs:
            //	syndw.zt_wd_xzqh_new: 3318 (73016 bytes)
            //	syndw.ztk_airport_division: 1923 (43088 bytes)
            //	synods.nb_tab_mh_mhzjdzsjxx/dt=20201129,synods.nb_tab_mh_mhzjdzsjxx/dt=20201128,synods.nb_tab_mh_mhzjdzsjxx/dt=20201127,...(62): 544550656 (26866173984 bytes)
            //outputs:
            //	tianshan.ztk_wsrygj_mhdzsj: 1026572 (51360664 bytes)
            //Job run time
            String result = m.group();
            int cpuBeginIndex = result.indexOf("cpu ");
            int cpuEndIndex = result.indexOf(" Core * Min");
            int memoryBeginIndex = result.indexOf("memory ");
            int memoryEndIndex = result.indexOf(" GB * Min");
            maxCpuConsumption = Double.max(maxCpuConsumption, Double.parseDouble(result.substring(cpuBeginIndex + 4, cpuEndIndex)));
            maxMemoryConsumption = Double.max(maxMemoryConsumption, Double.parseDouble(result.substring(memoryBeginIndex + 7, memoryEndIndex)));
        }
        int updateNum=0;

        String tableResult = "([Ii])nputs:(\\\\r)?\\\\n(\\\\t.*?\\)(\\\\r)?\\\\n)*" +
                "([Oo])utputs:(\\\\r)?\\\\n(\\\\t.*?\\)(\\\\r)?\\\\n)*";
        String tablePatternResult = "\\\\n\\\\t.*?\\)";
        r = Pattern.compile(tableResult);
        m = r.matcher(resultStr);
        int k = 0;
        List<InOutTable> resultList = new ArrayList<>();
        String outNum = null;
        //保存方式根据显示方式保存
        //例： 输入  输出
        //     a      b
        //     c
        while (m.find()) {
            String result = m.group();
            int inputNum = result.indexOf("nputs:");
            int outputNum = result.indexOf("utputs:");
            String inputTableString = result.substring(inputNum, outputNum);
            Pattern p = Pattern.compile(tablePatternResult);
            String outputTableString = result.substring(outputNum);
            Matcher matcher = p.matcher(outputTableString);
            List<InOutTable> inTableList = new ArrayList<>();
            List<InOutTable> outTableList = new ArrayList<>();
            k++;
            int i = 0;
            //先找出输出表，需求上显示每段sql输出表只有一个
            while (matcher.find()) {
                String table = matcher.group();
                int colonIndex = table.indexOf(":");
                //名字最后的index
                int nameEndIndex = table.contains("/") ? table.indexOf("/") : colonIndex;
                String tableName = table.substring(4, nameEndIndex);
                String num = table.substring(colonIndex + 2, table.indexOf("(") - 1).trim();
                InOutTable outTable = new InOutTable();
                outTable.setId(taskRunQueryParameters.getTaskId() + String.format("%02d", k) + String.format("%02d", ++i));
                outTable.setOutputTableName(tableName);
                outTable.setOutputNum(num);
                outTableList.add(outTable);
                outNum = num;
            }
            //再找输入表
            i = 0;
            matcher = p.matcher(inputTableString);
            while (matcher.find()) {
                String table = matcher.group();
                int colonIndex = table.indexOf(":");
                //名字最后的index
                int nameEndIndex = table.contains("/") ? table.indexOf("/") : colonIndex;
                String tableName = table.substring(4, nameEndIndex);
                String num = table.substring(colonIndex + 2, table.indexOf("(") - 1).trim();
                InOutTable inTable = new InOutTable();
                inTable.setId(taskRunQueryParameters.getTaskId() + String.format("%02d", k) + String.format("%02d", ++i));
                inTable.setInputTableName(tableName);
                inTable.setInputNum(num);
                inTableList.add(inTable);
            }
            //插入
            int maxLength = Integer.max(inTableList.size(), outTableList.size());
            for (int j = 0; j < maxLength; j++) {
                InOutTable table = new InOutTable();
                if (j < inTableList.size()) {
                    InOutTable inTable = inTableList.get(j);
                    table.setId(inTable.getId());
                    table.setInputTableName(inTable.getInputTableName());
                    table.setInputNum(inTable.getInputNum());
                }
                if (j < outTableList.size()) {
                    InOutTable outTable = outTableList.get(j);
                    table.setId(outTable.getId());
                    table.setOutputTableName(outTable.getOutputTableName());
                    table.setOutputNum(outTable.getOutputNum());
                }
                table.setTaskId(taskRunQueryParameters.getTaskId());
                if (j == 0) {
                    //为了前端显示，更简单
                    table.setRowSpan(maxLength);
                }
                resultList.add(table);
            }

        }
        //插入前先删除
        int delNum = normalInstanceDao.deleteTaskInOutRecord(taskRunQueryParameters.getTaskId());
        logger.info("删除了{}条M_FLOW_IN_OUT_RECORD表中TASK_ID为{}的记录", delNum, taskRunQueryParameters.getTaskId());
        if (!resultList.isEmpty()) {
            updateNum = normalInstanceDao.insertTaskInOutRecord(resultList);
            logger.info("增加了{}条M_FLOW_IN_OUT_RECORD表中TASK_ID为{}的记录", updateNum, taskRunQueryParameters.getTaskId());
        }
        updateFlowStatus(taskRunQueryParameters.getTaskId());

        logger.info("taskId: {} ,historyId: {} ,cpuConsumption: {} Core * Min,memoryConsumption: {} GB * Min",
                taskRunQueryParameters.getTaskId(), taskRunQueryParameters.getHistoryId() == null ? "null" : taskRunQueryParameters.getHistoryId(),
                maxCpuConsumption, maxMemoryConsumption);
        //只需最大峰值耗用，所以直接存flow表中，查询更简单
        updateNum = normalInstanceDao.updateCpuAndMemoryConsumption(taskRunQueryParameters.getTaskId(), String.valueOf(maxCpuConsumption),
                String.valueOf(maxMemoryConsumption));
        if (updateNum > 0) {
            logger.info("taskId: {} ,cpu和内存峰值耗用已更新至表中============================", taskRunQueryParameters.getTaskId());
        } else {
            logger.info("taskId: {} ,cpu和内存峰值耗用未插入至表中============================", taskRunQueryParameters.getTaskId());
        }
        //更新输出表数，最后输出记录数
        updateNum = normalInstanceDao.updateOutTableInfo(taskRunQueryParameters.getTaskId(), k, outNum);
        logger.info("更新输出表数和输出记录数{}条", updateNum);
        return resultLog;
    }

    @Override
    public String getTaskTest(TaskQueryParameters taskQueryParameters) throws Exception {
        String resultStr = dataWorksApi.queryTask(taskQueryParameters);
        return resultStr;
    }

    @Override
    public void searchNodeCodeToDb(List<Long> nodeIdList, String nodeType) throws Exception {

    }


    /**
     * v3版本的 查询节点对应的所有代码  解析odps的sql
     * 陈亮那边只有生产环节的代码信息 对于模型血缘，也需要展示开发环境的血缘关系，所以需要重新下载代码信息
     *
     * @param nodeList 节点列表
     */
    public void searchNodeCodeToDb(List<NodeEntity> nodeList) throws Exception {
        if (nodeList == null || nodeList.size() == 0) {
            throw new Exception("【查询节点代码任务】要查询的节点id列表为空");
        }
        // 开始删除该表里面所有的值
        logger.info("【查询节点代码任务】开始删除表 M_NODE_CODE_THREE中所有的数据");
        List<MaxVersionNodeId> paramList = modelRelationServiceImplDataWorkV3Dao.getMaxVersionNodeCodeByNodetype();
        if (paramList.size() > 0) {
            int delNum = modelRelationServiceImplDataWorkV3Dao.delMaxVersionNodeCode(paramList);
            logger.info("【查询节点代码任务】删除表M_NODE_CODE_THREE中的数据量为" + delNum);
        }
        Map<Long, Integer> map = new HashMap<>();
        paramList.forEach(item -> map.put(item.getNodeId(), item.getMaxVersion()));
        logger.info("【查询节点代码任务】需要查询的节点数据量为" + nodeList.size());
        List<NodeCode> nodeCodeList = new ArrayList<>();
        String resultStr = null;
        ResponseBody resultObj = null;
        for (NodeEntity nodeEntity : nodeList) {
            try {
                //开始查询节点的代码信息
                resultStr = dataWorksApi.getNodeCode(nodeEntity.getNodeId());
                resultObj = JSON.parseObject(resultStr, ResponseBody.class);
                if (resultObj != null && resultObj.getReturnCode().equalsIgnoreCase("0")) {
                    NodeCode nodeCode = new NodeCode();
                    nodeCode.setNodeId(nodeEntity.getNodeId());
                    nodeCode.setNodeCode(resultObj.getReturnValue());
                    nodeCode.setNodeType(String.valueOf(nodeEntity.getPrgType()));
                    nodeCode.setFileVersion(map.getOrDefault(nodeEntity.getNodeId(),1));
                    nodeCodeList.add(nodeCode);
                }
                if (nodeCodeList.size() >= 20) {
                    // 将数据插入到数据库中
                    logger.info("【查询节点代码任务】开始将查询到的节点代码插入到数据库中");
                    DAOHelper.insertDelList(nodeCodeList, modelRelationServiceImplDataWorkV3Dao, "insertNodeCode", 20);
                    nodeCodeList.clear();
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                logger.error("【查询节点代码任务】节点id为" + nodeEntity.getNodeId() + "的代码报错" + ExceptionUtil.getExceptionTrace(e));
            }
        }

        if (nodeCodeList.size() > 0) {
            //cfTest
            List<String> nodeCodeStrList = new ArrayList<>();
            for (NodeCode entity : nodeCodeList){
                nodeCodeStrList.add(JSON.toJSONString(entity));
            }
            this.dataOutputFileCF(nodeCodeStrList, "searchNodeCodeToDb");

            // 将数据插入到数据库中
            logger.info("【查询节点代码任务】开始将查询到的节点代码插入到数据库中");
            DAOHelper.insertDelList(nodeCodeList, modelRelationServiceImplDataWorkV3Dao, "insertNodeCode", 100);
            nodeCodeList.clear();
        }
        nodeCodeList = null;
        logger.info("【查询节点代码任务】查询节点代码结束");
    }


    /**
     * 解析数据加工血缘 查询表 m_node_code_three 的sql语句
     * 解析出数据加工血缘 并将数据信息插入到表  m_node_IN_OUT_TABLE
     */
    @Transactional(rollbackFor = Exception.class)
    public void getNodeInOutTable() {
        try {
            logger.info("【解析数据加工血缘】开始解析数据加工血缘的相关信息");
            // 先从数据库中获取获取所有的sql信息
            List<DataBloodlineRaw> allData = modelRelationServiceImplDataWorkV3Dao.selectDataBloodlineRawList();
            // 存储使用次数的信息
            Map<String, Integer> countTableUseMap = new HashMap<>(100);
            if (allData != null && allData.size() > 0) {
                logger.info("【解析数据加工血缘】从数据库中获取的数据量为" + allData.size());
                List<MaxVersionNodeId> paramList = dataBloodlineDao.getMaxVersionNode();
                if (paramList.size() > 0) {
                    int delNum = dataBloodlineDao.delMaxVersionInOutTable(paramList);
                    logger.info("【解析数据加工血缘】开始删除数据" + delNum);
                }

                try{
                    logger.info("开始删除字段血缘的信息");
                    int delNum = lineageColumnParsingDao.deleteColumnLineage();
                    logger.info(String.format("表M_NODE_IN_OUT_COLUMN删除的数据量为%d", delNum));
                }catch (Exception e){
                    logger.error("字段血缘信息删除失败"+ExceptionUtil.getExceptionTrace(e));
                }
                List<DataBloodlineInOutTable> dataBloodlineInOutTables = new ArrayList<>();
                Map<String, List<DataBloodlineInOutTable>> outputTableNameMap = new HashMap<>();
                // 删除数据加工血缘信息
                for (DataBloodlineRaw dataBloodlineRaw : allData) {
                    if (StringUtils.isNotBlank(dataBloodlineRaw.getProjectName())
                            && dataBloodlineRaw.getProjectName().toLowerCase().contains("_dev")
                            && StringUtils.equalsIgnoreCase("10", dataBloodlineRaw.getNodeType())) {
                        continue;
                    }
                    String projectName = dataBloodlineRaw.getProjectName().replaceAll("_prod", "").replaceAll("_dev", "");
                    List<ModelLinkData> modelLinkDatas = TableBloodlineUtil.getTableRelation(dataBloodlineRaw.getNodeCode(), dataBloodlineRaw.getNodeId(), projectName, dataBloodlineRaw.getNodeType(), JdbcConstants.ODPS);
                    // 20200810 添加解析字段血缘的功能
                    taskServiceImpl.insert(dataBloodlineRaw.getNodeCode(), JdbcConstants.ODPS, projectName, dataBloodlineRaw.getNodeId());
                    // 解析出使用次数
                    useHeatServiceImpl.getUseHeatTable(dataBloodlineRaw.getNodeCode(), JdbcConstants.ODPS, projectName, countTableUseMap);

                    if (modelLinkDatas.size() > 0) {
                        //已排序好，从上至下排序
                        modelLinkDatas.forEach(modelLinkData -> {
                            DataBloodlineInOutTable oneData = new DataBloodlineInOutTable();
                            // 表名中存在 nodeid 值 先去除掉
                            oneData.setFlowName(projectName + "." + dataBloodlineRaw.getFlowName());
                            oneData.setInputTableName(modelLinkData.getParentnode().replaceAll(dataBloodlineRaw.getNodeId() + "_", ""));
                            oneData.setNodeId(dataBloodlineRaw.getNodeId());
                            oneData.setNodeName(projectName + "." + dataBloodlineRaw.getNodeName());
                            oneData.setNodeType(dataBloodlineRaw.getNodeType());
                            oneData.setOutputTableName(modelLinkData.getChildnode().replaceAll(dataBloodlineRaw.getNodeId() + "_", ""));
                            oneData.setFileVersion(dataBloodlineRaw.getFileVersion());
                            String inputKey = oneData.getNodeId() + "-" + oneData.getInputTableName();
                            String outputKey = oneData.getNodeId() + "-" + oneData.getOutputTableName();
                            List<DataBloodlineInOutTable> inputChange = outputTableNameMap.get(inputKey);
                            int level = 1;
                            //输出层级取最大
                            if (inputChange != null) {
                                for (DataBloodlineInOutTable dataBloodlineInOutTable : inputChange) {
                                    if ("输出表".equals(dataBloodlineInOutTable.getInOutLocation())) {
                                        dataBloodlineInOutTable.setInOutLocation("中间表");
                                    }
                                    if (dataBloodlineInOutTable.getInOutLevel() >= level) {
                                        level = dataBloodlineInOutTable.getInOutLevel() + 1;
                                    }
                                }
                                oneData.setInOutLocation("输出表");
                            } else {
                                inputChange = new ArrayList<>();
                                oneData.setInOutLocation("输入表");
                            }
                            oneData.setInOutLevel(level);
                            inputChange.add(oneData);
                            outputTableNameMap.put(outputKey, inputChange);
                            dataBloodlineInOutTables.add(oneData);
                        });
                    }
                    if (dataBloodlineInOutTables.size() >= 200) {
                        logger.info("【解析数据加工血缘】将解析数据插入到数据库中");
                        dataBloodlineDao.insertInOutTables(dataBloodlineInOutTables);
                        dataBloodlineInOutTables.clear();
                    }
                }
                if (dataBloodlineInOutTables.size() > 0) {
                    logger.info("【解析数据加工血缘】将解析数据插入到数据库中");
                    dataBloodlineDao.insertInOutTables(dataBloodlineInOutTables);
                    dataBloodlineInOutTables.clear();
                }
                allData.clear();
            } else {
                logger.info("【解析数据加工血缘】从数据库中获取到的数据为空，不能解析数据血缘信息");
            }
            try {
                useHeatServiceImpl.inertUseTableToOracle(countTableUseMap);
            } catch (Exception e) {
                logger.error("表的使用次数插入报错：" + ExceptionUtil.getExceptionTrace(e));
            }
            System.gc();
        } catch (Exception e) {
            logger.error("【解析数据加工血缘】报错" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("【解析数据加工血缘】定时任务运行结束");
    }
}
