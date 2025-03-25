package com.synway.property.dao;

import com.synway.property.pojo.alarmsetting.AlarmMessage;
import com.synway.property.pojo.alarmsetting.ThresholdConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AlarmSettingDao
 * @Descroption
 * @Author majia
 * @Date 2020/4/27 10:38
 * @Version 1.0
 **/
@Mapper
@Repository
public interface AlarmSettingDao {

    /**
     * @description 获取昨天资产信息告警信息
     * @author nbj
     * @return
     */
    List<AlarmMessage> getAssetsAlarmMsgs();
}
