package com.synway.governace.service.generalManagement;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.entity.dto.DgnCommonSettingDTO;
import com.synway.governace.entity.pojo.DgnCommonSettingEntity;
import com.synway.governace.pojo.generalManagement.*;
import com.synway.governace.pojo.largeScreen.DataResource;

import java.util.List;

public interface GeneralManagementService {

    /*保存通用配置：数据资产、数据质量、告警推送*/
    void saveOrUpdateGeneralSetting(DgnCommonSettingDTO setting);
    /*获取通用配置：数据资产、数据质量、告警推送*/
    void saveOrUpdateReconciliationAlarmSetting(List<DgnCommonSettingEntity> settings);

    /*保存对账通用配置*/
    BillAlarmSetting getReconciliationAlarmSetting(DgnCommonSettingEntity setting);
    /*获取对账通用配置*/
    ServerResponse getGeneralSetting(DgnCommonSettingDTO setting);

    /*编辑告警推送设置信息*/
    ServerResponse editPushSetting(AlarmPushSetting alarmPushSetting);
    /*删除告警推送信息*/
    void delPushSetting(DgnCommonSettingEntity setting);

    /**
     * 数据堆积相关
     */
    /*获取数据中心列表*/
    List<FontOption> getAllDataCenter();

    /*根据数据中心获取数据源信息*/
    List<DataResource> getDataResourceByDataCenterId(String dataCenterId);

    /*根据数据中心ID 和 数据源类型 查询数据源列表*/
    List<FontOption> getDataResourceByDataCenterIdAndType(String dataCenterId, String dataResourceType);

    /*根据数据源ID获取数据源信息*/
    DataResource getDataResourceById(String resId);

    /*根据数据源id获取项目信息列表*/
    List<FontOption> getProjectList(String resId);

    List<FontOption> getTableNamesByDataResourceIdAndProjectName(String resId, String projectName);
}
