package com.synway.datarelation.service.workflow.impl;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.SpringBeanUtil;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.DAOHelper;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.dao.datablood.LineageColumnParsingDao;
import com.synway.datarelation.dao.taskinstance.TaskDefinitionInstanceDao;
import com.synway.datarelation.service.workflow.WorkFlowService;
import com.synway.datarelation.pojo.datawork.v3.NodeQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskQueryParameters;
import com.synway.datarelation.pojo.dfwork.NodeInfoParam;
import com.synway.datarelation.pojo.dfwork.NodeInfoReturnValue;
import com.synway.datarelation.pojo.dfwork.SqlCodeMessage;
import com.synway.datarelation.pojo.modelmonitor.DfWorkModelNodeInsInfo;
import com.synway.datarelation.pojo.modelmonitor.ModelProject;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;
import com.synway.datarelation.pojo.modelmonitor.ResultObj;
import com.synway.datarelation.pojo.modelrelation.DataBloodlineInOutTable;
import com.synway.datarelation.pojo.modelrelation.ModelLinkData;
import com.synway.datarelation.service.datablood.TaskService;
import com.synway.datarelation.service.heat.impl.UseHeatServiceImpl;
import com.synway.datarelation.util.DateUtil;
import com.synway.datarelation.util.DfWorkRestTemplateHandle;
import com.synway.datarelation.util.v3.TableBloodlineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wangdongwei
 * @ClassName DfWorkServiceProcess
 * @description 这个是dfwork的调度程序，相关的程序调用在这里
 *        在dfwork中总共需要查询2个定时任务的相关接口
 *        1： 查询工作流的运行情况  getTask
 *        2: 获取 数据加工血缘的表信息和字段信息  searchDfWorkNodeCodeToDb
 *
 *        dfwork中没有 同步任务监控/模型血缘 不需要解析项目那些 所以其它接口都为空
 * @date 2020/12/2 10:18
 */
public class DfWorkServiceService implements WorkFlowService {
    private static final Logger logger = LoggerFactory.getLogger(DfWorkServiceService.class);

    private DataBloodlineDao dataBloodlineDao;
    private DfWorkRestTemplateHandle dfWorkRestTemplateHandle;
    private LineageColumnParsingDao lineageColumnParsingDao;
    private TaskService taskServiceImpl;
    private TaskDefinitionInstanceDao taskDefinitionInstanceDao;
    private UseHeatServiceImpl useHeatServiceImpl;
    /**
     *需要进行热点数据处理的ProjectName类型  "生产环境"则表示只处理生产环境数据  "开发环境"则表示只处理开发环境数据
     * "all"表示开发和生产环境都处理 没有该配置项则默认"生产环境"数据
     */
    private String useHeatProjectName;


    /**
     * 定时任务统一运行的方法
     */
    @Override
    public void runInterfaceQuery() {
        // 这个是解析数据血缘关系的任务 将dfwork上节点的代码下载下来 然后再解析出表血缘关系
        // 给数据加工血缘使用
        logger.info("开始查询代码数据。获取出表的血缘关系=====");
        try{
            // 先查询出sql节点和同步任务节点的代码值 并将数据插入到数据库中
            searchDfWorkNodeCodeToDb(null,"10");
            searchDfWorkNodeCodeToDb(null,"23");
        }catch (Exception e){
            logger.error("查询dfwork中节点的表血缘和字段血缘报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 这个是工作流运行情况的调度信息
     */
    @Override
    public void modelMonitorStatistic() {
        try{
            logger.info("华为云版本 开始查询dfwork中任务实例信息");
            NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
            String bizDate = DateUtil.formatDate(DateUtil.addDay(new Date(),-1),"yyyy-MM-dd 00:00:00");
            nodeInsQueryParam.setBizdate(bizDate);
            //这个是解析的工作流监控的任务
            getTask(nodeInsQueryParam);
            logger.info("==========华为云版本获取任务实例信息结束=====================");
        }catch (Exception e){
            logger.error("dfwork中查询工作流任务的运行状况报错"+ExceptionUtil.getExceptionTrace(e));
        }

    }

    /**
     * 这个是同步工作流任务运行状况以及运行日志解析的定时任务
     */
    @Override
    public void dataWorkTaskScheduled() {
        logger.info("华为云版本dfwork中不需要查询同步任务的运行状况信息");
    }

    public DfWorkServiceService(){
        dataBloodlineDao = SpringBeanUtil.getBean(DataBloodlineDao.class);
        dfWorkRestTemplateHandle = SpringBeanUtil.getBean(DfWorkRestTemplateHandle.class);
        lineageColumnParsingDao = SpringBeanUtil.getBean(LineageColumnParsingDao.class);
        taskServiceImpl = SpringBeanUtil.getBean(TaskService.class);
        taskDefinitionInstanceDao = SpringBeanUtil.getBean(TaskDefinitionInstanceDao.class);
        useHeatServiceImpl = SpringBeanUtil.getBean(UseHeatServiceImpl.class);
        Environment environment = SpringBeanUtil.getBean(Environment.class);
        useHeatProjectName = environment.getProperty("useHeatProjectName", "生产环境");
        logger.info("需要处理热点数据的ProjectName类型  useHeatProjectName:[{}]", useHeatProjectName);
    }


    /**
     *   从 dfwork中查询出 节点的代码值信息 然后解析出sql里面的表信息/字段信息，将解析出的结果插入到数据库中
     * @param nodeIdList    节点主键 为空 dfwork里面为空  以nodeId为主，nodeId为空时，nodeType方可生效；
     * @param nodeType       节点类型  节点类型(仅支持sql(10)、sjtb(23)) ， 当为空时表示查询这2个类型的参数
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void searchDfWorkNodeCodeToDb(List<String> nodeIdList, String nodeType) throws Exception {
        logger.info("节点代码中查询参数为"+ JSONObject.toJSONString(nodeIdList)+" nodeType:"+nodeType);
        NodeInfoParam nodeInfoParam = new NodeInfoParam();
        String type = "10";
        if("10".equals(nodeType)){
            nodeType = "sql";
            type = "10";
        }else if("23".equals(nodeType)){
            nodeType = "sjtb";
            type = "23";
        }
        nodeInfoParam.setNodeType(nodeType);
        nodeInfoParam.setStart(1);
        nodeInfoParam.setLimit(300);
        List<NodeInfoReturnValue> allList = new ArrayList();
        ResultObj resultObj = dfWorkRestTemplateHandle.getNodeInfo(nodeInfoParam);
        if("0".equals(resultObj.getReturnCode()) && resultObj.getCount() > 0){
            int dataCount = resultObj.getCount();
            int pageCount = 1 + (dataCount/300);
            List<NodeInfoReturnValue> data = JSONObject.parseArray(resultObj.getReturnValue(),NodeInfoReturnValue.class);
            allList.addAll(data);
            for(int i = 2; i<=pageCount ;i++){
                nodeInfoParam.setStart(i);
                resultObj = dfWorkRestTemplateHandle.getNodeInfo(nodeInfoParam);
                if("0".equals(resultObj.getReturnCode()) && resultObj.getCount() > 0){
                    List<NodeInfoReturnValue> data1 = JSONObject.parseArray(resultObj.getReturnValue(),NodeInfoReturnValue.class);
                    allList.addAll(data1);
                }else{
                    logger.info("从dfwork接口中获取代码信息报错："+resultObj.getReturnMessage());
                }
            }
        }else{
            logger.info("从dfwork接口中获取代码信息报错："+resultObj.getReturnMessage());
            logger.error("从dfwork接口中获取代码信息报错："+resultObj.getReturnMessage());
            throw new NullPointerException("从dfwork上获取数据报错");
        }
        logger.info("【查询dfwork节点代码任务】开始删除表 M_NODE_CODE_THREE中 nodeType为"+type+"所有的数据");
        int delNum= dataBloodlineDao.deleteInOutTablesByType(type);
        logger.info("【解析数据加工血缘】开始删除数据"+delNum);
        if(Common.SQL.equalsIgnoreCase(nodeType)){
            logger.info("开始删除字段血缘的信息");
            lineageColumnParsingDao.deleteColumnLineage();
        }
        List<DataBloodlineInOutTable> dataBloodlineInOutTables = new ArrayList<>();
        Map<String,Integer> countTableUseMap = new HashMap<>(100);
        for(NodeInfoReturnValue nodeInfoReturnValue:allList){
            List<ModelLinkData> modelLinkDatas = null;
            try{
                String oldProjectName = nodeInfoReturnValue.getProjectName();
                String projectName = oldProjectName.replaceAll("_生产环境","").replaceAll("_开发环境","_dev");
                if(Common.SQL.equalsIgnoreCase(nodeType)){
                    boolean needHandleUseHeat = false;
                    //20230117 根据配置项 useHeatProjectName 和 数据的projectName信息 来判断是否进行热点逻辑处理。
                    if("all".equalsIgnoreCase(useHeatProjectName)){
                        needHandleUseHeat = true;
                    }
                    if(oldProjectName.contains(useHeatProjectName)){
                        needHandleUseHeat = true;
                    }
                    if(!needHandleUseHeat){
                        continue;
                    }

                    modelLinkDatas = TableBloodlineUtil.getTableRelation(nodeInfoReturnValue.getSqlCode(),
                            nodeInfoReturnValue.getNodeId(),projectName,"10", JdbcConstants.HIVE);
                    //  解析字段信息
                    taskServiceImpl.insert(nodeInfoReturnValue.getSqlCode(),JdbcConstants.HIVE,projectName,nodeInfoReturnValue.getNodeId());
                    // 解析出使用次数
                    useHeatServiceImpl.getUseHeatTable(nodeInfoReturnValue.getSqlCode(),JdbcConstants.HIVE, projectName,countTableUseMap);
                }else{
                    if(StringUtils.isEmpty(nodeInfoReturnValue.getSqlCode())){
                        continue;
                    }
                    SqlCodeMessage sqlCodeMessage = JSONObject.parseObject(nodeInfoReturnValue.getSqlCode()
                            ,SqlCodeMessage.class);
                    String sourceTableName = sqlCodeMessage.getSourceConnect().contains(".")?
                            sqlCodeMessage.getSourceConnect().toLowerCase().replaceAll("odps_","")
                                    .replaceAll("ads_","").replaceAll("hive_","").replaceAll("hbase_","")
                                    .replaceAll("oracle_","").replaceAll("mysql_","")
                            .replaceAll("_product","")
                            :projectName+"."+sqlCodeMessage.getSourceConnect();
                    String targetTableName = sqlCodeMessage.getTargetConnect().contains(".")?
                            sqlCodeMessage.getTargetConnect().toLowerCase().replaceAll("odps_","")
                                    .replaceAll("ads_","").replaceAll("hive_","").replaceAll("hbase_","")
                                    .replaceAll("oracle_","").replaceAll("mysql_","")
                                    .replaceAll("_product","")
                            :projectName+"."+sqlCodeMessage.getTargetConnect();
                    // 在研究中发现 存在这种表名  hc_db.jz.nb_app_positionsitestatnew
                    // 清洗掉这种表 只获取最后的数据
                    if(targetTableName != null &&
                            targetTableName.split("\\.").length >2){
                        String[] targetTableNameList = targetTableName.split("\\.");
                        targetTableName = targetTableNameList[targetTableNameList.length -2]
                                +"."+targetTableNameList[targetTableNameList.length -1];
                    }
                    ModelLinkData modelLinkData = new ModelLinkData();
                    modelLinkData.setParentnode(sourceTableName);
                    modelLinkData.setChildnode(targetTableName);
                    modelLinkDatas = new ArrayList<>();
                    modelLinkDatas.add(modelLinkData);
                }
                if(modelLinkDatas.size() >0){
                    modelLinkDatas.forEach(modelLinkData -> {
                        DataBloodlineInOutTable oneData = new DataBloodlineInOutTable();
                        // 表名中存在 nodeid 值 先去除掉
                        // 表名可能存在多个 . 比如 syntag.syntag.tableName
                        oneData.setFlowName(projectName+"."+nodeInfoReturnValue.getFlowName());
                        oneData.setInputTableName(modelLinkData.getParentnode().replaceAll(nodeInfoReturnValue.getNodeId()+"_",""));

                        if(!StringUtils.isEmpty(oneData.getInputTableName()) &&
                                oneData.getInputTableName().split("\\.").length > 2){
                            String[] tableNames = oneData.getInputTableName().split("\\.");
                            oneData.setInputTableName(tableNames[tableNames.length -2]+"."+tableNames[tableNames.length -1]);
                        }
                        oneData.setNodeId(nodeInfoReturnValue.getNodeId());
                        oneData.setNodeName(projectName+"."+nodeInfoReturnValue.getNodeName());
                        oneData.setNodeType(nodeInfoReturnValue.getNodeType().equalsIgnoreCase("sql")?"10":"23");
                        oneData.setOutputTableName(modelLinkData.getChildnode().replaceAll(nodeInfoReturnValue.getNodeId()+"_",""));
                        if(!StringUtils.isEmpty(oneData.getOutputTableName()) &&
                                oneData.getOutputTableName().split("\\.").length > 2){
                            String[] tableNames = oneData.getOutputTableName().split("\\.");
                            oneData.setOutputTableName(tableNames[tableNames.length -2]+"."+tableNames[tableNames.length -1]);
                        }
                        dataBloodlineInOutTables.add(oneData);
                    });
                }
            }catch (Exception e){
                logger.error("【华为平台解析数据加工血缘】 出现异常", e);
            }
            if(dataBloodlineInOutTables.size() >=100){
                logger.info("【华为平台解析数据加工血缘】将解析数据插入到数据库中");
                dataBloodlineDao.insertInOutTables(dataBloodlineInOutTables);
                dataBloodlineInOutTables.clear();
            }
        }
        if(dataBloodlineInOutTables.size() > 0){
            logger.info("【华为平台解析数据加工血缘】将解析数据插入到数据库中");
            dataBloodlineDao.insertInOutTables(dataBloodlineInOutTables);
            dataBloodlineInOutTables.clear();
        }

        try{
            useHeatServiceImpl.inertUseTableToOracle(countTableUseMap);
        }catch (Exception e){
            logger.error("表的使用次数插入报错："+ExceptionUtil.getExceptionTrace(e));
        }

        allList.clear();
    }


    /**
     *  其中 projectName 在这个函数中获取(该值为必填的数据)，其它参数传入即可
     * @param nodeInsQueryParam
     */
    @Override
    public void getTask(NodeInsQueryParam nodeInsQueryParam) throws Exception{
        logger.info("开始查询dfwork中任务实例信息，参数信息为"+ JSONObject.toJSONString(nodeInsQueryParam));
        // 开始查询project信息
        List<ModelProject> modelProjects = dfWorkRestTemplateHandle.getProjectByDfWorks();
        if(modelProjects != null && modelProjects.size() >0){
            for(int i =0; i <modelProjects.size();i++){
                try{
                    ModelProject modelProject = modelProjects.get(i);
                    nodeInsQueryParam.setProjectName(modelProject.getName().replaceAll("_生产环境","").
                            replaceAll("开发环境","dev"));
                    List<DfWorkModelNodeInsInfo> modelNodeInsInfoList = dfWorkRestTemplateHandle.getTaskInstanceInfo(nodeInsQueryParam);
                    if(modelNodeInsInfoList != null && modelNodeInsInfoList.size() >0){
                        logger.info("项目名为："+modelProject.getName()+" 查询到的任务实例信息数据量为"+ modelNodeInsInfoList.size());
                        // 将查询到的任务实例信息保存到数据库中 将
                        insertIntoFlowToOracle(modelNodeInsInfoList);
                        modelNodeInsInfoList.clear();
                    }else{
                        logger.info("项目名为："+modelProject.getName()+" 查询到的任务实例信息数据量为0");
                    }
                }catch (Exception e){
                    logger.error("获取任务实例信息报错"+ExceptionUtil.getExceptionTrace(e));
                }
            }
        }else{
            logger.info("从dfwork查询到的项目名的数据量为0");
        }
        modelProjects.clear();
    }

    private void insertIntoFlowToOracle(List<DfWorkModelNodeInsInfo> modelNodeInsInfoList){
        try{
            for(int i =0; i<modelNodeInsInfoList.size();i++){
                try{
                    DfWorkModelNodeInsInfo oneDfWorkModelNodeInsInfo = modelNodeInsInfoList.get(i);
                    // 运行状态  status 是英文数据 failed 是5 其它都算是6
                    logger.debug(oneDfWorkModelNodeInsInfo.getStatus());
                    if(org.apache.commons.lang3.StringUtils.isEmpty(oneDfWorkModelNodeInsInfo.getStatus())){
                        oneDfWorkModelNodeInsInfo.setStatus("5");
                    }else if("failed".equalsIgnoreCase(oneDfWorkModelNodeInsInfo.getStatus())){
                        oneDfWorkModelNodeInsInfo.setStatus("5");
                    }else{
                        oneDfWorkModelNodeInsInfo.setStatus("6");
                    }
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(oneDfWorkModelNodeInsInfo.getBizdate())){
                        oneDfWorkModelNodeInsInfo.setBizTimeDate(DateUtil.parseDate(oneDfWorkModelNodeInsInfo.getBizdate(),DateUtil.DEFAULT_PATTERN_DATETIME));
                    }
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(oneDfWorkModelNodeInsInfo.getCreateTime())){
                        oneDfWorkModelNodeInsInfo.setCreateTimeDate(DateUtil.parseDate(oneDfWorkModelNodeInsInfo.getCreateTime(),DateUtil.DEFAULT_PATTERN_DATETIME));
                    }
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(oneDfWorkModelNodeInsInfo.getBizBeginHour())){
                        oneDfWorkModelNodeInsInfo.setBizBeginHourDate(DateUtil.parseDate(oneDfWorkModelNodeInsInfo.getBizBeginHour(),DateUtil.DEFAULT_PATTERN_DATETIME));
                    }
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(oneDfWorkModelNodeInsInfo.getBizEndHour())){
                        oneDfWorkModelNodeInsInfo.setBizEndHourDate(DateUtil.parseDate(oneDfWorkModelNodeInsInfo.getBizEndHour(),DateUtil.DEFAULT_PATTERN_DATETIME));
                    }
                    oneDfWorkModelNodeInsInfo.setProjectName(oneDfWorkModelNodeInsInfo.getProjectName()
                            .replaceAll("开发环境","dev").replaceAll("_生产环境",""));

                }catch (Exception e){
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
            // 需要插入的数据量为
            logger.info("需要插入的数据量为："+modelNodeInsInfoList.size());
            if(modelNodeInsInfoList != null && !modelNodeInsInfoList.isEmpty()){
                DAOHelper.insertList(modelNodeInsInfoList,taskDefinitionInstanceDao,"insertDfWorkTaskList");

            }
            logger.info("数据插入结束");
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<Long> getNode(NodeQueryParameters nodeQueryParameters, Boolean queryTask) throws Exception {
        return null;
    }

    @Override
    public void getTask(TaskQueryParameters taskQueryParameters, boolean queryLog) throws Exception {

    }

    @Override
    public String getTaskTest(TaskQueryParameters taskQueryParameters) throws Exception {
        return null;
    }
}
