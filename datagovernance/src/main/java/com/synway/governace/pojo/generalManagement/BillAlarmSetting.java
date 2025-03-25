package com.synway.governace.pojo.generalManagement;

import com.alibaba.fastjson.JSONObject;
import com.synway.governace.enums.BillAlarmSettingEnum;

/**
 * 异常告警设置信息
 * @author ywj
 */
public class BillAlarmSetting {
    private String id;                      // 主键id
    private String parentId;                // 父类id
    private String acceptError;             // 告警阈值：接入环节条数
    private String acceptErrorRate;         // 告警阈值：接入环节波动率
    private String acceptErrorAlarmLevel;   // 告警阈值：接入环节告警级别
    private String standardError;           // 告警阈值：处理环节条数
    private String standardErrorRate;       // 告警阈值：处理环节波动率
    private String standardErrorAlarmLevel; // 告警阈值：处理环节告警级别
    private String storageError;            // 告警阈值：入库环节条数
    private String storageErrorRate;        // 告警阈值：入库环节波动率
    private String storageErrorAlarmLevel;  // 告警阈值：入库环节告警级别
    private String samePeriodType;          // 用于接入环节选择框判断
    private String chainRatioType;          // 用于处理、入库环节选择框判断
    private String acceptStr;               // 接入配置json字符串
    private String standardStr;             // 处理配置json字符串
    private String storageStr;              // 入库配置json字符串
    private String billExpireDay;           // 销账状态账单存储周期
    private String alarmExpireDay;          // 对账单存储周期
    private String billExpireMaxDay;        // 对账数据最大保留天数
    private String errorExpireDay;          // 对账失败账单存储周期
    private String autoOffDay;              // 对账成功账单
    private String settingTache;            // 启用环节
    private String acceptTimingReconValue;  //接入定时对账
    private String storageTimingReconValue; //入库定时对账
    private String cleanReconAnalysisValue; //对账分析存储周期

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcceptError() {
        return acceptError;
    }

    public void setAcceptError(String acceptError) {
        this.acceptError = acceptError;
    }

    public String getAcceptErrorRate() {
        return acceptErrorRate;
    }

    public void setAcceptErrorRate(String acceptErrorRate) {
        this.acceptErrorRate = acceptErrorRate;
    }

    public String getStandardError() {
        return standardError;
    }

    public void setStandardError(String standardError) {
        this.standardError = standardError;
    }

    public String getStandardErrorRate() {
        return standardErrorRate;
    }

    public void setStandardErrorRate(String standardErrorRate) {
        this.standardErrorRate = standardErrorRate;
    }

    public String getStorageError() {
        return storageError;
    }

    public void setStorageError(String storageError) {
        this.storageError = storageError;
    }

    public String getStorageErrorRate() {
        return storageErrorRate;
    }

    public void setStorageErrorRate(String storageErrorRate) {
        this.storageErrorRate = storageErrorRate;
    }

    public String getSamePeriodType() {
        return samePeriodType;
    }

    public void setSamePeriodType(String samePeriodType) {
        this.samePeriodType = samePeriodType;
    }

    public String getChainRatioType() {
        return chainRatioType;
    }

    public void setChainRatioType(String chainRatioType) {
        this.chainRatioType = chainRatioType;
    }

    public String getAcceptStr() {
        return acceptStr;
    }

    public void setAcceptStr(String acceptStr) {
        this.acceptStr = acceptStr;
        JSONObject jsonObject = JSONObject.parseObject(acceptStr);
        if (null != jsonObject) {
            this.acceptError = jsonObject.getString(BillAlarmSettingEnum.acceptError.getCode());
            this.acceptErrorRate = jsonObject.getString(BillAlarmSettingEnum.acceptErrorRate.getCode());
            this.acceptErrorAlarmLevel = jsonObject.getString(BillAlarmSettingEnum.acceptErrorAlarmLevel.getCode());
        }
    }

    public String getStandardStr() {
        return standardStr;
    }

    public void setStandardStr(String standardStr) {
        this.standardStr = standardStr;
        JSONObject jsonObject = JSONObject.parseObject(standardStr);
        if (null != jsonObject) {
            this.standardError = jsonObject.getString(BillAlarmSettingEnum.standardError.getCode());
            this.standardErrorRate = jsonObject.getString(BillAlarmSettingEnum.standardErrorRate.getCode());
            this.standardErrorAlarmLevel = jsonObject.getString(BillAlarmSettingEnum.standardErrorAlarmLevel.getCode());
        }
    }

    public String getStorageStr() {
        return storageStr;
    }

    public void setStorageStr(String storageStr) {
        this.storageStr = storageStr;
        JSONObject jsonObject = JSONObject.parseObject(storageStr);
        if (null != jsonObject) {
            this.storageError = jsonObject.getString(BillAlarmSettingEnum.storageError.getCode());
            this.storageErrorRate = jsonObject.getString(BillAlarmSettingEnum.storageErrorRate.getCode());
            this.storageErrorAlarmLevel = jsonObject.getString(BillAlarmSettingEnum.storageErrorAlarmLevel.getCode());
        }
    }

    public String getBillExpireDay() {
        return billExpireDay;
    }

    public void setBillExpireDay(String billExpireDay) {
        this.billExpireDay = billExpireDay;
    }

    public String getAlarmExpireDay() {
        return alarmExpireDay;
    }

    public void setAlarmExpireDay(String alarmExpireDay) {
        this.alarmExpireDay = alarmExpireDay;
    }

    public String getBillExpireMaxDay() {
        return billExpireMaxDay;
    }

    public void setBillExpireMaxDay(String billExpireMaxDay) {
        this.billExpireMaxDay = billExpireMaxDay;
    }

    public String getErrorExpireDay() {
        return errorExpireDay;
    }

    public void setErrorExpireDay(String errorExpireDay) {
        this.errorExpireDay = errorExpireDay;
    }

    public String getAutoOffDay() {
        return autoOffDay;
    }

    public void setAutoOffDay(String autoOffDay) {
        this.autoOffDay = autoOffDay;
    }

    public String getSettingTache() {
        return settingTache;
    }

    public void setSettingTache(String settingTache) {
        this.settingTache = settingTache;
    }

    public String getAcceptErrorAlarmLevel() {
        return acceptErrorAlarmLevel;
    }

    public void setAcceptErrorAlarmLevel(String acceptErrorAlarmLevel) {
        this.acceptErrorAlarmLevel = acceptErrorAlarmLevel;
    }

    public String getStandardErrorAlarmLevel() {
        return standardErrorAlarmLevel;
    }

    public void setStandardErrorAlarmLevel(String standardErrorAlarmLevel) {
        this.standardErrorAlarmLevel = standardErrorAlarmLevel;
    }

    public String getStorageErrorAlarmLevel() {
        return storageErrorAlarmLevel;
    }

    public void setStorageErrorAlarmLevel(String storageErrorAlarmLevel) {
        this.storageErrorAlarmLevel = storageErrorAlarmLevel;
    }

    public String getAcceptTimingReconValue() {
        return acceptTimingReconValue;
    }

    public void setAcceptTimingReconValue(String acceptTimingReconValue) {
        this.acceptTimingReconValue = acceptTimingReconValue;
    }

    public String getStorageTimingReconValue() {
        return storageTimingReconValue;
    }

    public void setStorageTimingReconValue(String storageTimingReconValue) {
        this.storageTimingReconValue = storageTimingReconValue;
    }

    public String getCleanReconAnalysisValue() {
        return cleanReconAnalysisValue;
    }

    public void setCleanReconAnalysisValue(String cleanReconAnalysisValue) {
        this.cleanReconAnalysisValue = cleanReconAnalysisValue;
    }
}
