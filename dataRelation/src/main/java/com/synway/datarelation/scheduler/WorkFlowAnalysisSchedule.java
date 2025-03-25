package com.synway.datarelation.scheduler;


import com.synway.datarelation.config.HazecastLock;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.constant.DataBaseType;
import com.synway.datarelation.service.workflow.WorkFlowService;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 模型血缘的相关定时调度任务
 *  对于datawork
 *     1：v2版本中可以直接查询阿里的后台数据库
 *     2：v3版本需要先将所有的节点信息下载查询到 m_node_three 表中，之后再调用v3版本的【向上查询父节点】和【向下查询子节点】查询节点的关系
 *         对于节点中表的关系 调用陈亮组的映射表 SYNDT_IN_OUT_TABLE_ANALYZE 根据表名映射(只有生产环境)
 */
@Component
public class WorkFlowAnalysisSchedule {
    private static Logger logger = LoggerFactory.getLogger(WorkFlowAnalysisSchedule.class);

    @Autowired
    private DataBloodlineDao dataBloodlineDao;
    @Autowired
    private CacheManageDataBloodlineService  cacheManageDataBloodlineService;
    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;

    /**
     * 同步任务的调用情况
     * 支持 阿里（V2、V3）
     */
    @Scheduled(cron = "${dataWorkTaskScheduled}")
    @HazecastLock(lockName = Common.DATA_WORK_TASK_SCHEDULED, methodValue = "同步任务的调用情况")
    public void dataWorkTaskScheduled() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            workFlowService.dataWorkTaskScheduled();
        } catch (Exception e) {
            logger.error("定时获取同步任务的运行情况报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 定时获取工作流的运行情况
     * 支持 阿里(V2) huawei
     */
    @Scheduled(cron = "${modelMonitorStatistic}")
    @HazecastLock(lockName = Common.MODEL_MONITOR_STATISTIC, methodValue = "定时获取工作流的运行情况")
    public void getTaskDfWork() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            logger.info("class地址为：" + handleClaStr);
            workFlowService.modelMonitorStatistic();
        } catch (Exception e) {
            logger.error("定时获取工作流的运行情况报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 查询所有的节点信息下载到 m_node_three表中  这个主要查询数据血缘/模型血缘的相关内容
     * 支持 阿里（V2、v3） huawei
     */
    @Scheduled(cron = "${modelRelationGetAllNode}")
    @HazecastLock(lockName = Common.MODEL_RELATION_GET_ALLNODE, methodValue = "查询所有的节点信息")
    public void modelRelationGetAllNode() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            workFlowService.runInterfaceQuery();
        } catch (Exception e) {
            logger.error("定时查询工作流报错", e);
        }
    }

    /**
     * 插入任务
     * 支持 阿里（V3）
     */
    @Scheduled(cron = "${insertTaskCron}")
    @HazecastLock(lockName = Common.INSERT_TASK_CRON, methodValue = "获取所有类型节点的运行任务信息")
    public void insertTaskCron() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            workFlowService.insertTask();
        } catch (Exception e) {
            logger.error("定时查询工作流报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 查询所有的节点信息下载到 m_dag_three表中  这个主要查询数据血缘/模型血缘的相关内容
     * 支持 阿里（V3）
     */
    @Scheduled(cron = "${getDag}")
    @HazecastLock(lockName = Common.GET_DAG, methodValue = "查询所有的节点信息下载到m_dag_three表中")
    public void getDag() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            workFlowService.getDag();
        } catch (Exception e) {
            logger.error("定时查询工作流日志报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 组合工作流监控
     * 支持 阿里（V3）
     */
    @Scheduled(cron = "${getdataWorksMonitor}")
    @HazecastLock(lockName = Common.GET_DATA_WORKS_MONITOR, methodValue = "组合工作流监控")
    public void getDataWorksMonitor() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            workFlowService.getdataWorksMonitor();
        } catch (Exception e) {
            logger.error("定时调度程序报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 日志解析
     * 支持 阿里（V3）
     */
    @Scheduled(cron = "${queryLog}")
    @HazecastLock(lockName = Common.QUERY_LOG, methodValue = "日志解析")
    public void queryLog() {
        try {
            String handleClaStr = DataBaseType.getCla(parameterMap);
            WorkFlowService workFlowService = (WorkFlowService) Class.forName(handleClaStr).newInstance();
            workFlowService.queryLog();
        } catch (Exception e) {
            logger.error("定时调度程序报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }


    /**
     * 这个是程序启动时判断数据是否存在 不存在则查询相关接口
     */
    public void getTableRelation() {
        try{
            // 目前只有dataWork 先不做判断，当需要从华为平台查询时再更改
            int tableCount = dataBloodlineDao.getTableRelationCount();
            if (tableCount == 0) {
                logger.info("集群缓存无数据&工作流血缘表中没有数据，开始查询第三方接口获取血缘数据...");
                this.modelRelationGetAllNode();
                logger.info("查询第三方接口获取工作流信息完成");
            } else {
                logger.info("集群缓存无数据&工作流血缘表中有数据，不需要查询第三方接口获取血缘数据...");
            }
        }catch (Exception e){
            logger.error("启动时查询血缘关系报错", e);
        }
    }

    /**
     * 获取所有表的分类信息，每隔30分钟获取一次
     */
    @Scheduled(cron = "0 */30 * * * ?")
    @HazecastLock(lockName = Common.GET_ALL_TABLE_CLASSIFY, methodValue = "获取所有表的分类信息")
    public void getAllTableClassifyScheduled() {
        cacheManageDataBloodlineService.getAllTableClassifyCache();
    }

}
