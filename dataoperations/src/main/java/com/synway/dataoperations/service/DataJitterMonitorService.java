package com.synway.dataoperations.service;

import com.synway.dataoperations.pojo.dataJitterMonitor.RequestParameterDJM;
import com.synway.dataoperations.pojo.dataJitterMonitor.ReturnResultDJM;
import com.synway.dataoperations.pojo.dataJitterMonitor.TreeAndCardJitter;

public interface DataJitterMonitorService {

    TreeAndCardJitter getTreeAndCardData(String searchName);

    ReturnResultDJM getChartDataDJM(RequestParameterDJM requestParameter);

    void sendAlarmMsg();

}
