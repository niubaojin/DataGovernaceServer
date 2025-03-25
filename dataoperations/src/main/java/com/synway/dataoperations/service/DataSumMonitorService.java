package com.synway.dataoperations.service;

import com.synway.dataoperations.pojo.dataSizeMonitor.RequestParameterDSM;
import com.synway.dataoperations.pojo.dataSizeMonitor.ReturnResultDSM;
import com.synway.dataoperations.pojo.dataSizeMonitor.TreeAndCard;

/**
 * @author nbj
 * @description 数据量监测
 * @date 2023年4月28日13:47:53
 */
public interface DataSumMonitorService {

    TreeAndCard getTreeAndCardData(String searchName);

    ReturnResultDSM getChartData(RequestParameterDSM requestParameter);

    void sendAlarmMsg();
}
