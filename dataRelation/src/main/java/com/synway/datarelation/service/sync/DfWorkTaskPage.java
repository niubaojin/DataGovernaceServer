package com.synway.datarelation.service.sync;

import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageParams;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageReturn;
import com.synway.datarelation.pojo.datawork.v3.HistoryTaskQueryParams;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 同步工作流的相关查询接口
 * @author wangdongwei
 */
public interface DfWorkTaskPage {

    /**
     * 从数据库中获取同步工作流的相关数据
     * @param queryParams
     * @return
     */
    DataSynchronizationReportPageReturn getDataSynchronizationReport(DataSynchronizationReportPageParams queryParams);


    /**
     * 查询日志信息
     * @param taskId
     * @param id
     * @return
     * @throws Exception
     */
    String queryDataSynchronizationLog(String taskId, String id) throws Exception;


    /**
     * 获取历史日志信息
     * @param historyTaskQueryParams
     * @return
     * @throws Exception
     */
    Map<String,Object>  queryHistoryTaskLog(HistoryTaskQueryParams historyTaskQueryParams) throws Exception;


    /**
     * 导出同步报表的表格信息
     * @param queryParams
     * @param response
     */
    void exportDataTable(DataSynchronizationReportPageParams queryParams, HttpServletResponse response);


    /**
     *  导出任务历史运行日志信息
     * @param historyTaskQueryParams
     * @param response
     */
    void exportHistoryTaskTable(HistoryTaskQueryParams historyTaskQueryParams, HttpServletResponse response);
}
