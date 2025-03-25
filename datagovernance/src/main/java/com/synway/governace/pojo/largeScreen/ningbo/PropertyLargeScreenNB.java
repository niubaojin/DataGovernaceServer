package com.synway.governace.pojo.largeScreen.ningbo;

import com.synway.governace.pojo.ReconInfo;
import com.synway.governace.pojo.largeScreen.TotalDataProperty;
import com.synway.governace.pojo.largeScreen.UseHeatProperty;
import lombok.Data;

import java.util.List;

/**
 * 宁波资产大屏返回数据
 * @author nbj
 * @date 2023年10月12日14:08:26
 */

@Data
public class PropertyLargeScreenNB {
    private ReconInfo dataRecon;                            // 数据对账
    private AllBloodCount  dataBlood;                       // 数据血缘
    private List<UseHeatProperty> dataUseHots;              // 数据热度
    private List<PropertyStatisticsChart> dataAccessSums;   // 数据接入量
    private List<PropertyStatistics> dataAlarms;            // 数据告警
    private List<PropertyStatistics> dataResources;         // 数据资源
    private TotalDataProperty platformAssets;               // 平台资产
    private ExternalServerData externalServer;              // 对外服务
}
