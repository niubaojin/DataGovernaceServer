package com.synway.datarelation.service.monitor;

import com.synway.datarelation.pojo.monitor.detail.TaskNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.detail.TaskReportPageReturn;
import com.synway.datarelation.pojo.monitor.table.InOutTable;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/16 11:08
 */
public interface WorkflowDetailService {

    TaskReportPageReturn getTaskInfo(TaskNormalReportPageParams params);

    String getTaskLog(String taskId);

    List<InOutTable> getTaskInOutRecord(String taskId);

}
