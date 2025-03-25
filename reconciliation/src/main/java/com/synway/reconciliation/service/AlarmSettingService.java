package com.synway.reconciliation.service;

import com.github.pagehelper.PageInfo;
import com.synway.reconciliation.pojo.AlarmPhoneNumber;
import com.synway.reconciliation.pojo.BillAlarmSetting;
import com.synway.reconciliation.pojo.GetListRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 异常告警设置service
 * @author ywj
 */
public interface AlarmSettingService {

    /**
     * 编辑保存数据对账异常告警规则设置
     * @param settingJsonStr 设置信息json字符串
     * @return java.lang.String
     */
    String saveOrUpdateReconciliationAlarmSetting(String settingJsonStr);

    /**
     * 数据对账异常告警规则设置详情获取
     * @param parentId 父节点id
     * @return com.synway.reconciliation.pojo.BillAlarmSetting
     */
    BillAlarmSetting getReconciliationAlarmSetting(String parentId);

    /**
     * 数据对账异常告警规则私有设置获取
     * @return java.util.List<com.synway.reconciliation.pojo.BillAlarmSetting>
     */
    List<BillAlarmSetting> getReconciliationAlarmPrivateSetting();

    /**
     * 告警手机号码列表获取
     * @param getListRequest 查询条件
     * @return com.github.pagehelper.PageInfo
     */
    PageInfo getAlarmPhoneNumberList(GetListRequest getListRequest);

    /**
     * 新增/编辑告警手机号码
     * @param alarmPhoneNumber 手机号码信息
     * @return java.lang.String
     */
    String saveOrUpdateAlarmPhoneNumber(AlarmPhoneNumber alarmPhoneNumber);

    /**
     * 删除告警手机号码
     * @param deleteIds 待删除手机号码记录ID集合
     * @return int
     */
    int deleteAlarmPhoneNumber(String deleteIds);
}