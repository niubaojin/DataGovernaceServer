package com.synway.datarelation.service.sync;



import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageParams;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageReturn;
import com.synway.datarelation.pojo.datawork.v3.HistoryTaskQueryParams;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wangdongwei
 */
@Service
public interface SyncWorkFlowV3Service {

    /**
     * 获取数据同步报表的列表信息
     * @param queryParams
     * @return
     * @throws Exception
     */
    DataSynchronizationReportPageReturn getDataSynchronizationReport(DataSynchronizationReportPageParams queryParams) throws Exception;

    /**
     *
     * @param taskId
     * @param id
     * @return
     * @throws Exception
     */
    String queryDataSynchronizationLog(String taskId, String id) throws Exception;

    /**
     *  获取同步报表的日志信息
     * @param historyTaskQueryParams
     * @return
     * @throws Exception
     */
    Map<String,Object> queryHistoryTaskLogService(HistoryTaskQueryParams historyTaskQueryParams) throws Exception;

    /**
     * 导出同步报表的表格信息
     * @param queryParams
     * @param response
     * @param version
     */
    void exportDataTableService(DataSynchronizationReportPageParams queryParams, HttpServletResponse response, int version);

    /**
     *  导出任务历史运行日志信息
     * @param historyTaskQueryParams
     * @param response
     * @param version
     */
    void exportHistoryTaskTable(HistoryTaskQueryParams historyTaskQueryParams, HttpServletResponse response, int version);


    //  datawork 阿里平台 第二版本页面上的查询操作

    /**
     *  页面查询的搜索按钮
     * @param queryParams
     * @return
     * @throws Exception
     */
    DataSynchronizationReportPageReturn getDataSynchronizationReportTwo(DataSynchronizationReportPageParams queryParams) throws Exception;

    /**
     *  查询同步任务运行日志
     * @param taskId
     * @param id
     * @return
     * @throws Exception
     */
    String queryDataSynchronizationTwoLog(String taskId, String id) throws Exception;

    /**
     * 查询历史日志信息 第2版本的
     * @param historyTaskQueryParams
     * @return
     * @throws Exception
     */
    Map<String,Object> queryHistoryTaskTwoLogService(HistoryTaskQueryParams historyTaskQueryParams) throws Exception;
}
