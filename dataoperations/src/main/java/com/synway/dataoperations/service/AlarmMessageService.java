package com.synway.dataoperations.service;


import com.github.pagehelper.PageInfo;
import com.synway.dataoperations.pojo.AlarmPushSetting;
import com.synway.dataoperations.pojo.OperatorLog;

import java.util.List;

/**
 * @description 告警信息处理(从datagovernance移植过来)
 * @author niubaojin
 */
public interface AlarmMessageService {
    void insertAlarmMessage(String jsonString);

    List<AlarmPushSetting> getAlarmSettings();

    void insertOperatorLogs(List<OperatorLog> operatorLogs) throws Exception;

    PageInfo<OperatorLog> getOperatorLogList(Integer currentPage, Integer pageSize,
                                             String sortName, String sortOrder,
                                             String opeModule, String opeType,
                                             String opePerson,
                                             String opeBeginTime, String opeEndTime);

}
