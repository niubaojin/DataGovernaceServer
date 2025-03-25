package com.synway.datarelation.service.monitor;

import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageReturn;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/14 16:56
 */
public interface WorkflowMonitorService {

    BusinessNormalReportPageReturn getBusinessNormalTaskReport(BusinessNormalReportPageParams queryParams);

}
