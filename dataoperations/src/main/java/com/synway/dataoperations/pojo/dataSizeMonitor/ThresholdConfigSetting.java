package com.synway.dataoperations.pojo.dataSizeMonitor;

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
    private DataVolumeMonitorSetting dataVolumeMonitorSetting;
}
