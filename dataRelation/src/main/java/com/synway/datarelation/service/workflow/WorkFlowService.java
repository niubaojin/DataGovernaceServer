package com.synway.datarelation.service.workflow;

import com.synway.datarelation.pojo.datawork.v3.NodeQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskRunQueryParameters;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;

import java.util.List;

/**
 *  在数据血缘 模型血缘的接口中 定时任务调度的相关接口
 * @author wangdongwei
 */
public interface WorkFlowService {
//  以下为相关的调度程序
    /**
     *  这个是数据血缘/模型血缘的 调度方法 每个类型的接口统一从这个方法中调用
     */
    public void runInterfaceQuery();

    /**
     *  这个是工作流运行情况的调度信息
     */
    public void modelMonitorStatistic();


    /**
     *  这个是同步工作流任务运行状况以及运行日志解析的定时任务
     */
    public void dataWorkTaskScheduled();


    /**
     * 这个是 dfwork上特有的接口
     * @param nodeIdList
     * @param nodeType
     * @throws Exception
     */
    default void searchDfWorkNodeCodeToDb(List<String> nodeIdList, String nodeType) throws Exception{

    };

    /**
     * 获取节点信息
     * @param nodeQueryParameters
     * @param queryTask
     * @return
     * @throws Exception
     */
    List<Long> getNode(NodeQueryParameters nodeQueryParameters, Boolean queryTask) throws Exception;

    /**
     * 获取任务信息
     * @param taskQueryParameters
     * @param queryLog  是否实时查询日志
     * @throws Exception
     */
    void getTask(TaskQueryParameters taskQueryParameters, boolean queryLog) throws Exception;

    /**
     * 获取任务的运行日志
     * @param taskRunQueryParameters
     * @param saveLog
     * @throws Exception
     * @return
     */
    default String getTaskRunLog(TaskRunQueryParameters taskRunQueryParameters, Boolean saveLog) throws Exception{
        return null;
    }

    /**
     * 获取task的测试任务
     * @param taskQueryParameters
     * @return
     * @throws Exception
     */
    String getTaskTest(TaskQueryParameters taskQueryParameters) throws Exception;

    /**
     * dfWork的查询任务实例的接口信息
     * @param nodeInsQueryParam
     * @throws Exception
     */
    default void getTask(NodeInsQueryParam nodeInsQueryParam) throws Exception{

    }

    /**
     * 获取代码信息
     * @param nodeIdList
     * @param nodeType
     * @throws Exception
     */
    default void searchNodeCodeToDb(List<Long> nodeIdList, String nodeType) throws Exception{

    };

    default void getDag() {

    }

    default void getdataWorksMonitor() {

    }

    default void insertTask() {

    }
    default void queryLog() {

    }
}
