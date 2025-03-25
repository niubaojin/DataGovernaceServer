package com.synway.governace.pojo.generalManagement;

import lombok.Data;

@Data
public class QualityAlarmSetting {
    /*数据质量*/
    private QualityScore qualityScore;      // 质量评分
    public Threshold threshold;             // 质量阈值
    public String abnormalDataPersistNums;  // 异常数据保留条数
    public String abnormalDataPersistDays;  // 异常数据保留条数
    public String qualityReportPersisttDays;// 异常数据保留条数

}
