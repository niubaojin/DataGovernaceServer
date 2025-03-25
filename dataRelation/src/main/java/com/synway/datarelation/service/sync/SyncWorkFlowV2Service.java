package com.synway.datarelation.service.sync;


import com.synway.datarelation.pojo.datawork.v2.LogQueryParameters;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;
import org.springframework.stereotype.Service;

/**
 * datawork第2.0版本的api接口
 * @author wangdongwei
 */
@Service
public interface SyncWorkFlowV2Service {


    /**
     * datawork 第2版本查询任务信息
     * @param nodeInsQueryParam
     * @param queryLogFlag
     * @throws Exception
     */
    void queryTask(NodeInsQueryParam nodeInsQueryParam, Boolean queryLogFlag) throws Exception;

    /**
     * 查询日志信息
     * @param logQueryParameters
     * @param saveFlag
     * @throws Exception
     */
    void queryLog(LogQueryParameters logQueryParameters, Boolean saveFlag) throws Exception;

}
