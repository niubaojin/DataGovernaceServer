package com.synway.reconciliation.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 异常告警设置信息
 * @author ywj
 */
public class BillAlarmSetting {
    private String id;
    private String parentId;
    private String acceptError;
    private String acceptErrorRate;
    private String standardError;
    private String standardErrorRate;
    private String storageError;
    private String storageErrorRate;
    private String samePeriodType;
    private String chainRatioType;
    private String acceptStr;
    private String standardStr;
    private String storageStr;
    private String billExpireDay;
    private String alarmExpireDay;
    private String billExpireMaxDay;
    private String errorExpireDay;
    private String autoOffDay;
    private String settingTache;
    private String acceptTimingReconValue;
    private String storageTimingReconValue;
    private String cleanReconAnalysisValue;

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
