package com.synway.governace.dao;

import com.synway.governace.entity.pojo.DgnCommonSettingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GeneralManagementDao {

    /*编辑推送设置信息*/
    void delPushSetting(@Param("parentId") String parentId, @Param("ids") String[] ids);

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
    void insertReconciliationAlarmSetting(DgnCommonSettingEntity thresholdConfig);

    /**
     * 根据名称获取数据对账异常告警设置信息
     * @param names 名称集合
     * @return java.util.List<com.synway.reconciliation.pojo.ThresholdConfig>
     */
    List<DgnCommonSettingEntity> getReconciliationAlarmSettingByName(@Param("array") String[] names);
}
