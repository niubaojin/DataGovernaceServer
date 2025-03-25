package com.synway.dataoperations.service;

import com.synway.dataoperations.pojo.historyDataMonitor.RequestParameterHDM;
import com.synway.dataoperations.pojo.historyDataMonitor.ReturnResultHDM;

/**
 * @author nbj
 * @description 历史数据监测
 * @date 2022年5月30日11:02:03
 */
public interface HistoryDataMonitorService {
    ReturnResultHDM getHDMCommon();

    ReturnResultHDM getHDMData(RequestParameterHDM requestParameterHDM);

    void sendAlarmMsg();

}
