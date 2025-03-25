package com.synway.governace.dao;

import com.synway.governace.pojo.generalManagement.ThresholdConfigSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GeneralManagementDao {

    /*保存通用配置：数据资产、数据质量、告警推送*/
    void saveOrUpdateGeneralSetting(@Param("config") ThresholdConfigSetting config);
    /*保存通用配置：数据量配置*/
    void saveOrUpdateGeneralSettingDataVolume(@Param("config") ThresholdConfigSetting config);

    /*新增推送设置信息*/
    void addPushSetting(@Param("config") ThresholdConfigSetting config);

    /*编辑推送设置信息*/
    void editPushSetting(@Param("thresholdConfigSetting") ThresholdConfigSetting thresholdConfigSetting);
    /*编辑推送设置信息*/
    void delPushSetting(@Param("parentId") String parentId, @Param("ids") String[] ids);

    /*获取通用配置：数据资产、数据质量、告警推送*/
    List<ThresholdConfigSetting> getGeneralSetting(@Param("parentId") String parentId);
    List<ThresholdConfigSetting> getGeneralSettingDataVolume(@Param("parentId") String parentId, @Param("name") String name);

    /**
     * 删除数据对账异常告警设置信息
     * @param parentId 父节点ID
     * @return void
     */
    void deleteReconciliationAlarmSetting(@Param("parentId") String parentId);

    /**
     * 新增数据对账异常告警设置信息
     * @param thresholdConfig 设置信息
     * @return void
     */
    void insertReconciliationAlarmSetting(ThresholdConfigSetting thresholdConfig);

    /**
     * 根据id获取数据对账异常告警设置信息
     * @param parentId 父节点ID
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<ThresholdConfigSetting> getReconciliationAlarmSettingByParentId(@Param("parentId") String parentId);

    /**
     * 根据名称获取数据对账异常告警设置信息
     * @param names 名称集合
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<ThresholdConfigSetting> getReconciliationAlarmSettingByName(@Param("array") String[] names);
}
