package com.synway.datarelation.service.monitor;

/**
 * 统计模型监控接口
 * @author
 * @date
 */
public interface WorkFlowInstanceService {


    /**
     * 统计项目名称
     * @throws Exception
     */
    void statisticProject()throws Exception;

    /**
     * 统计节点
     * @throws Exception
     */
    void statisticNode()throws Exception;

    /**
     * 统计实例
     * @throws Exception
     */
    void statisticFlowIns()throws Exception;



}
