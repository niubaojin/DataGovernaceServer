package com.synway.datarelation.service.workflow.impl;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.service.heat.UseHeatService;
import com.synway.datarelation.service.workflow.WorkFlowService;
import com.synway.datarelation.util.SpringBeanUtil;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.pojo.databloodline.QueryNodeParams;
import com.synway.datarelation.pojo.datawork.v3.NodeQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskQueryParameters;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;
import com.synway.datarelation.pojo.modelmonitor.ResultObj;
import com.synway.datarelation.pojo.modelrelation.DataBloodlineInOutTable;
import com.synway.datarelation.pojo.modelrelation.ModelLinkData;
import com.synway.datarelation.service.datablood.TaskService;
import com.synway.datarelation.service.heat.impl.UseHeatServiceImpl;
import com.synway.datarelation.service.sync.SyncWorkFlowV2Service;
import com.synway.datarelation.service.monitor.WorkFlowInstanceService;
import com.synway.datarelation.service.monitor.impl.WorkFlowInstanceV2ServiceImp;
import com.synway.datarelation.util.DataIdeUtil;
import com.synway.datarelation.util.DateUtil;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.v3.TableBloodlineUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wangdongwei
 * @ClassName DataWorkV2Interface
 * @description 这个是阿里云平台v2版本的相关接口以及定时调度
 *      v2版本中 数据血缘是查询的陈亮的后台表 所以不需要查询这些内容
 *      模型血缘查询的是阿里的后台数据库 这些内容也不需要定时调度
 *      工作流监控需要调用api接口来查询数据  统计程序不在这里
 *     20201223 数据血缘v2版本使用自己解析
 * @date 2020/12/2 10:40
 */
public class DataWorkV2Service implements WorkFlowService {
    private static final Logger logger = LoggerFactory.getLogger(DataWorkV2Service.class);

    private DataBloodlineDao dataBloodlineDao;
    private DataIdeUtil dataIdeUtil;
    private TaskService taskServiceImpl;
    private UseHeatService useHeatService;

    public DataWorkV2Service(){
        dataBloodlineDao = SpringBeanUtil.getBean(DataBloodlineDao.class);
        dataIdeUtil = SpringBeanUtil.getBean(DataIdeUtil.class);
        taskServiceImpl = SpringBeanUtil.getBean(TaskService.class);
        useHeatService = SpringBeanUtil.getBean(UseHeatServiceImpl.class);
    }

    /**
     * 数据血缘的解析程序
     */
    @Override
    public void runInterfaceQuery() {
        try{
            logger.info("这个是阿里V2平台的数据血缘定时调度方法");
            getNodeInOutTable("10");
        }catch (Exception e){
            logger.error("阿里V2平台里面查询数据血缘的相关信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        try{
            logger.info("这个是阿里V2平台的数据血缘定时调度方法");
            getNodeInOutTable("23");
        }catch (Exception e){
            logger.error("阿里V2平台里面查询数据血缘的相关信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void getNodeInOutTable(String nodeType) throws Exception{
        // 因为这个需要 项目名 工作流名 节点名  先从数据库中获取到所有的这些信息，然后再遍历获取所有的sql信息
        // 只需要 有运行实例的 节点 查询m_flow的节点 如果m_flow里面没有数据，则不需要查询对应的血缘信息
        List<QueryNodeParams> queryNodeParams =dataBloodlineDao.getQueryNodeParams(nodeType);
        if(queryNodeParams == null || queryNodeParams.isEmpty()){
            // 如果数据库中这个为空，则需要继续查询这个数据 但是可能存在
            logger.error("表m_node存储的【"+nodeType+"】节点的数据量为空，无法查询表血缘关系");
            return;
        }
        logger.info("【解析数据加工血缘】需要查询的【"+nodeType+"】节点数据量为: "+queryNodeParams.size());
        List<DataBloodlineInOutTable> dataBloodlineInOutTables = new ArrayList<>();
        int delNum= dataBloodlineDao.deleteInOutTablesByType(nodeType);
        logger.info("【解析数据加工血缘】开始删除数据"+delNum);
        // 存储使用次数的信息
        Map<String,Integer> countTableUseMap = new HashMap<>(100);
        for(QueryNodeParams queryNodeParam: queryNodeParams){
            try{
                Thread.sleep(10);
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            String resultStr =  dataIdeUtil.queryCode(queryNodeParam.getProjectName(),queryNodeParam.getFlowName(),queryNodeParam.getNodeName());
            ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
            String returnCode = resultObj.getReturnCode();
            if(!"0".equals(returnCode)){
                logger.info("查询条件"+ JSONObject.toJSONString(queryNodeParam) +"代码信息报错，报错原因+【"+resultObj.getReturnMessage()+"】");
                continue;
            }
            logger.info("开始解析【"+JSONObject.toJSONString(queryNodeParam)+"】表血缘关系和字段血缘关系");
            String nodeSqlValue = resultObj.getReturnValue();
            if(StringUtils.isBlank(nodeSqlValue)){
                continue;
            }
            List<ModelLinkData> modelLinkDatas = TableBloodlineUtil.getTableRelation(nodeSqlValue,queryNodeParam.getFlowName()
                            +"@@"+queryNodeParam.getProjectName(),
                    queryNodeParam.getProjectName(),nodeType, JdbcConstants.ODPS);
            // 20200810 添加解析字段血缘的功能
            if("10".equalsIgnoreCase(nodeType)){
                taskServiceImpl.insert(nodeSqlValue,JdbcConstants.ODPS,queryNodeParam.getProjectName(),queryNodeParam.getFlowName()
                        +"@@"+queryNodeParam.getProjectName());
                // 解析出使用次数
                useHeatService.getUseHeatTable(nodeSqlValue,JdbcConstants.ODPS, queryNodeParam.getProjectName(),countTableUseMap);
            }
            if(!modelLinkDatas.isEmpty()){
                modelLinkDatas.forEach(modelLinkData -> {
                    DataBloodlineInOutTable oneData = new DataBloodlineInOutTable();
                    // 表名中存在 nodeid 值 先去除掉
                    oneData.setFlowName(queryNodeParam.getProjectName()+"."+queryNodeParam.getFlowName());
                    oneData.setInputTableName(modelLinkData.getParentnode().replaceAll(queryNodeParam.getFlowName()
                            +"@@"+queryNodeParam.getProjectName()+"_",""));
                    oneData.setNodeId(queryNodeParam.getFlowName()+"@@"+queryNodeParam.getProjectName());
                    oneData.setNodeName(queryNodeParam.getProjectName()+"."+queryNodeParam.getNodeName());
                    oneData.setNodeType(nodeType);
                    oneData.setOutputTableName(modelLinkData.getChildnode().replaceAll(
                            queryNodeParam.getFlowName()+"@@"+queryNodeParam.getProjectName()+"_",""));
                    oneData.setFileVersion(queryNodeParam.getFileVersion());
                    dataBloodlineInOutTables.add(oneData);
                });
            }
            if(dataBloodlineInOutTables.size() >=200){
                logger.info("【解析数据加工血缘】将解析数据插入到数据库中");
                dataBloodlineDao.insertInOutTables(dataBloodlineInOutTables);
                dataBloodlineInOutTables.clear();
            }
        }
        if(!dataBloodlineInOutTables.isEmpty()){
            logger.info("【解析数据加工血缘】将解析数据插入到数据库中");
            dataBloodlineDao.insertInOutTables(dataBloodlineInOutTables);
            dataBloodlineInOutTables.clear();
        }
        try{
            useHeatService.inertUseTableToOracle(countTableUseMap);
        }catch (Exception e){
            logger.error("表的使用次数插入报错：", e);
        }
        logger.info("【解析数据加工血缘】任务结束");
    }

    /**
     * 查询工作流的相关运行情况
     */
    @Override
    public void modelMonitorStatistic() {
        try{
            logger.info("=====开始查询阿里云V2工作流任务运行状况=====");
            WorkFlowInstanceService workFlowInstanceServiceV2 = SpringBeanUtil.getBean(WorkFlowInstanceV2ServiceImp.class);
            try{
                workFlowInstanceServiceV2.statisticProject();
            }catch (Exception e1){
                logger.error("查询阿里云V2工作流任务的运行状况报错", e1);
            }
            try{
                logger.info("开始查询所有的节点");
                workFlowInstanceServiceV2.statisticNode();
            }catch (Exception e2){
                logger.error("查询阿里云V2工作流任务的运行状况报错",e2);
            }
            logger.info("开始查询所有的工作流信息");
            workFlowInstanceServiceV2.statisticFlowIns();
        }catch (Exception e){
            logger.error("查询阿里云V2工作流任务的运行状况报错", e);
        }
    }

    @Override
    public void dataWorkTaskScheduled() {
        try{
            logger.info("=====开始查询阿里云V2同步任务运行状况=====");
            SyncWorkFlowV2Service syncWorkFlowV2ServiceImpl = SpringBeanUtil.getBean(SyncWorkFlowV2Service.class);
            //  datawork2.0版本的获取所有同步任务的定时触发信息
            NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
            nodeInsQueryParam.setDagType(23);
            nodeInsQueryParam.setCreateTime(DateUtil.formatDateTime(DateUtil.getDayBegin(new Date()),DateUtil.DEFAULT_PATTERN_DATETIME));
            nodeInsQueryParam.setStart(0);
            nodeInsQueryParam.setLimit(1);
            syncWorkFlowV2ServiceImpl.queryTask(nodeInsQueryParam,true);
        }catch (Exception e){
            logger.error("查询阿里云V2同步任务的运行状况报错", e);
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
