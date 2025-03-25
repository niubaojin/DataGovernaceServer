package com.synway.governace.pojo.generalManagement;

import lombok.Data;

/**
 * @author nbj
 */
@Data
public class ThresholdConfigSetting {
    private String id;
    private String parentId;
    private String name;
    private String logicalJudgment;
    private String treeType;
    private String isActive;
    private String thresholdValue;
    private PropertyAlarmSetting thresholdValueProperty;
    private QualityAlarmSetting thresholdValueQuality;
    private AlarmPushSetting thresholdValueAlarmPush;
    private DataPiledSetting dataPiledSetting;
    private DataVolumeMonitorSetting dataVolumeMonitorSetting;
}
