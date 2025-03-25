package com.synway.datarelation.controller.workflow;

import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageReturn;
import com.synway.datarelation.service.monitor.WorkflowMonitorService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/14 16:37
 * 工作流监控
 */
@Controller
//@RequestMapping("/dataNormalInstance/hostpage")
@RequestMapping("/workflow/monitor")
public class WorkflowMonitorController {
    private static Logger logger = LoggerFactory.getLogger(WorkflowMonitorController.class);

    @Autowired
    private WorkflowMonitorService workflowMonitorService;

    /**
     * TODO 后期工作流监控需要加入华为的项目时需要修改
     */
    @RequestMapping(value = "/getBusinessNormalReport", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<BusinessNormalReportPageReturn> getBusinessNormalReport(
            @RequestBody BusinessNormalReportPageParams queryParams) {
        logger.info("工作流监控的查询参数为：" + JSONObject.toJSONString(queryParams));
        ServerResponse<BusinessNormalReportPageReturn> serverResponse;
        try {
            BusinessNormalReportPageReturn businessNormalReportPageReturn
                    = workflowMonitorService.getBusinessNormalTaskReport(queryParams);
            serverResponse = ServerResponse.asSucessResponse(businessNormalReportPageReturn);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("工作流监控查询报错：" + e.getMessage());
            logger.error("工作流监控查询报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("==============查询结束===========");
        return serverResponse;
    }

}
