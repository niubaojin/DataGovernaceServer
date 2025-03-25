package com.synway.reconciliation.dao;

import com.synway.reconciliation.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 异常告警设置dao
 * @author ywj
 */
@Mapper
@Repository
public interface AlarmSettingDao {

    /**
     * 根据id获取数据对账异常告警设置信息
     * @param parentId 父节点ID
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<ThresholdConfig> getReconciliationAlarmSettingByParentId(@Param("parentId") String parentId);

    /**
     * 获取数据对账异常告警设置信息
     * @param config 配置信息
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<ThresholdConfig> getReconciliationAlarmSetting(ThresholdConfig config);

    /**
     * 根据名称获取数据对账异常告警设置信息
     * @param names 名称集合
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<ThresholdConfig> getReconciliationAlarmSettingByName(@Param("array") String[] names);

    /**
     * 新增数据对账异常告警设置信息
     * @param thresholdConfig 设置信息
     * @return void
     */
    void insertReconciliationAlarmSetting(ThresholdConfig thresholdConfig);

    /**
     * 删除数据对账异常告警设置信息
     * @param parentId 父节点ID
     * @return void
     */
    void deleteReconciliationAlarmSetting(@Param("parentId") String parentId);

    /**
     * 告警手机号码列表获取
     * @param getListRequest 查询条件
     * @return java.util.List<com.synway.reconciliation.pojo.AlarmPhoneNumber>
     */
    List<AlarmPhoneNumber> getAlarmPhoneNumberList(GetListRequest getListRequest);

    /**
     * 根据id获取告警手机号码
     * @param id 手机号码记录ID
     * @return com.synway.reconciliation.pojo.AlarmPhoneNumber
     */
    AlarmPhoneNumber getAlarmPhoneNumberById(@Param("id") String id);

    /**
     * 新增告警手机号码
     * @param number 手机号码信息
     * @return void
     */
    void insertAlarmPhoneNumber(AlarmPhoneNumber number);

    /**
     * 编辑告警手机号码
     * @param number 手机号码信息
     * @return void
     */
    void updateAlarmPhoneNumber(AlarmPhoneNumber number);

    /**
     * 删除告警手机号码
     * @param ids 待删除手机号码记录ID集合
     * @return int
     */
    int deleteAlarmPhoneNumber(@Param("array") String[] ids);

    /**
     * 根据名称获取私有数据对账异常告警设置信息
     * @param names 名称集合
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<ThresholdConfig> getPrivateReconciliationAlarmSettingByName(@Param("array") String[] names);
}
