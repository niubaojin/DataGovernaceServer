package com.synway.governace.enums;

import com.alibaba.druid.util.StringUtils;

/**
 * 异常告警设置枚举
 * @author ywj
 */

public enum BillAlarmSettingEnum {
    settingTache("settingTache", "对账汇总启用环节"),
    autoOffDay("autoOffDay", "自动进入销账时间"),
    alarmExpireDay("alarmExpireDay", "告警数据保留周期设置"),
    billExpireDay("billExpireDay", "账单保留周期设置"),
    errorExpireDay("errorExpireDay", "对账失败账单保留周期设置"),
    acceptStr("acceptStr", "同比接入设置"),
    standardStr("standardStr", "环比数据处理设置"),
    storageStr("storageStr", "环比入库设置"),
    acceptError("acceptError", "接入误差"),
    acceptErrorRate("acceptErrorRate", "接入误差比例"),
    acceptErrorAlarmLevel("acceptErrorAlarmLevel", "接入环节告警级别"),
    standardError("standardError", "数据处理误差"),
    standardErrorRate("standardErrorRate", "数据处理误差比例"),
    standardErrorAlarmLevel("standardErrorAlarmLevel", "数据处理环节告警级别"),
    storageError("storageError", "入库误差"),
    storageErrorRate("storageErrorRate", "入库误差比例"),
    storageErrorAlarmLevel("storageErrorAlarmLevel", "入库环节告警级别"),
    acceptTimingReconValue("acceptTimingReconValue", "数据接入定时对账周期"),
    storageTimingReconValue("storageTimingReconValue", "数据入库定时对账周期"),
    cleanReconAnalysisValue("cleanReconAnalysisValue", "对账分析保留周期设置");

    String code;
    String name;

    BillAlarmSettingEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }

    public static String getCodeByName(String name) {
        for (BillAlarmSettingEnum setting : BillAlarmSettingEnum.values()) {
            if (StringUtils.equals(name, setting.name)) {
                return setting.code;
            }
        }
        return null;
    }

// --Commented out by Inspection START (2024/6/26 16:15):
//    public static String getNameByCode(String code) {
//        for (BillAlarmSettingEnum setting : BillAlarmSettingEnum.values()) {
//            if (StringUtils.equals(code, setting.code)) {
//                return setting.name;
//            }
//        }
//        return null;
//    }
// --Commented out by Inspection STOP (2024/6/26 16:15)
}
