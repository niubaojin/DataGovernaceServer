package com.synway.dataoperations.dao;

import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.DataGFMsg;
import com.synway.dataoperations.pojo.OperatorLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @description 告警信息相关操作
 * @author niubaojin
 * @date 2022年2月18日11:05:07
 */
@Mapper
@Repository
public interface AlarmMessageDao {

    /**
     * @description 告警信息入库
     */
    void insertAlarmMessage(@Param("alarmMessages") List<AlarmMessage> alarmMessages);

    /**
     * @description 获取告警数据
     * @return
     */
    List<AlarmMessage> getAlarmData(@Param("searchTime") String searchTime,
                                    @Param("searchName") String searchName,
                                    @Param("alarmModule") String alarmModule,
                                    @Param("alarmStatus") String alarmStatus,
                                    @Param("alarmStatusFilter") String[] alarmStatusFilter,
                                    @Param("alarmModuleFilter") String[] alarmModuleFilter);

    void insertOperatorLog(@Param("ol")OperatorLog operatorLog);

    List<OperatorLog> getOperatorLogList(@Param("om")String opeModule ,@Param("ot") String opeType ,
                                         @Param("op")String opePerson ,@Param("obt") Date opeBeginTime,
                                         @Param("oet") Date opeEndTime ,@Param("iso") Boolean ifSendOut );

    int updateOperatorLogs(@Param("otls") List<OperatorLog> operatorLogs);

    /**
     * @description 获取告警统计信息
     * @param searchTime
     * @return
     */
    List<AlarmMessage> getAlarmStatisticsInfo(@Param("searchTime") String searchTime);

    /**
     * @description 获取治理跟踪数据
     * @param searchName
     * @param linkFilter
     * @param sponsorFilter
     * @param managerFilter
     * @return
     */
    List<DataGFMsg> getDGFData(@Param("searchName")     String searchName,
                               @Param("linkFilter")     String[] linkFilter,
                               @Param("sponsorFilter")  String[] sponsorFilter,
                               @Param("managerFilter")  String[] managerFilter);

    // 插入治理跟踪数据
    void insertDFGData(@Param("dataGFMsg") DataGFMsg dataGFMsg);
    // 更新治理跟踪数据
    void updateDFGData(@Param("dataGFMsg") DataGFMsg dataGFMsg);

    // 获取结论信息
    String getConclusion(@Param("id") String id);

    // 更新结论信息
    void updateConclusion(@Param("id") String id,
                          @Param("type") String type,
                          @Param("nowTime") String nowTime,
                          @Param("conclusion") String conclusion,
                          @Param("status") String status);

    // 删除按钮操作
    void deleteDFGData(@Param("idList") String[] idList);

    // 导出按钮操作
    List<DataGFMsg> getExportData(@Param("idList") String[] idList);

    // 定期清理告警中心数据
    void deleteOverTimeAlarmData(int days);
    // 定期清理治理跟踪数据
    void deleteOverTimeDGFData(int days);

    String[] getAlarmPushSetting();

}
