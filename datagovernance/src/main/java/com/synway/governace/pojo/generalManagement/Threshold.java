package com.synway.governace.pojo.generalManagement;

import lombok.Data;

/**
 * 阈值
 */
@Data
public class Threshold {

    /** 是否开启 完整性、有效性、正确性、唯一性、业务合理性告警阈值 */
    public boolean integrityCheck;
    /** 及时性警告阈值 */
    public boolean realTimeCheck;

    /** 一般 */
    public Integer normalLow;
    public Integer normalUp;

    /** 警告 */
    public Integer alarmLow;
    public Integer alarmUp;

    /** 严重 */
    public Integer seriousLow;
    public Integer seriousUp;

    /** 及时性一般阈值 */
    public Integer realTimeNormalLow;
    public String realTimeNormalLowUnit;
    public Integer realTimeNormalUp;
    public String realTimeNormalUpUnit;

    /** 及时性告警阈值 */
    public Integer realTimeAlarmLow;
    public String realTimeAlarmLowUnit;
    public Integer realTimeAlarmUp;
    public String realTimeAlarmUpUnit;

    /** 及时性严重阈值 */
    public Integer realTimeSeriousLow;
    public String realTimeSeriousLowUnit;

    public boolean hadConfig(){
        return normalLow != null || normalUp != null || alarmLow != null || alarmUp != null
                || seriousLow != null || seriousUp != null || realTimeNormalLow != null || realTimeNormalUp != null
                || realTimeAlarmLow != null || realTimeAlarmUp != null || realTimeSeriousLow != null ;
    }



}
