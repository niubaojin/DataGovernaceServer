package com.synway.datarelation.controller.workflow;

import com.synway.datarelation.pojo.monitor.detail.TaskNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.detail.TaskReportPageReturn;
import com.synway.datarelation.pojo.monitor.table.InOutTable;
import com.synway.datarelation.service.monitor.WorkflowDetailService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/16 11:05
 * 工作流监控 工作流详情
 */
@Controller
//@RequestMapping("/dataNormalInstance/detailPage")
@RequestMapping("/workflow/detail")
public class WorkflowDetailController {
    private static Logger logger = LoggerFactory.getLogger(WorkflowDetailController.class);

    @Autowired
    private WorkflowDetailService detailService;

    @RequestMapping("/getTaskInfo")
    @ResponseBody
    public ServerResponse<TaskReportPageReturn> getTaskInfo(
            @RequestBody TaskNormalReportPageParams params) {
        logger.info("节点运行状态的查询参数为：" + params);
        ServerResponse<TaskReportPageReturn> serverResponse;
        try {
            // 查询版本3对应的数据,需求2版本不需要做
            TaskReportPageReturn taskReportPageReturn = detailService.getTaskInfo(params);
            serverResponse = ServerResponse.asSucessResponse(taskReportPageReturn);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("节点运行状态查询报错：" + e.getMessage());
            logger.error("节点运行状态查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping("/getTaskInOutRecord")
    @ResponseBody
    public ServerResponse<List<InOutTable>> getTaskInOutRecord(@RequestParam("taskId") String taskId) {
        logger.info("节点输入输出查询taskId为：" + taskId);
        ServerResponse<List<InOutTable>> serverResponse;
        try {
            // 查询版本3对应的数据,需求2版本不需要做
            List<InOutTable> record = detailService.getTaskInOutRecord(taskId);
            serverResponse = ServerResponse.asSucessResponse(record);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("节点输入输出查询报错：" + e.getMessage());
            logger.error("节点输入输出查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

    @RequestMapping("/getTaskLog")
    @ResponseBody
    public ServerResponse getTaskLog(@RequestParam("taskId") String taskId) {
        logger.info("节点日志查询taskId为：" + taskId);
        ServerResponse serverResponse;
        try {
            // 查询版本3对应的数据,需求2版本不需要做
            String log = detailService.getTaskLog(taskId);
            serverResponse = ServerResponse.asSucessResponse(log);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("节点日志查询报错：" + e.getMessage());
            logger.error("节点日志查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }


}
