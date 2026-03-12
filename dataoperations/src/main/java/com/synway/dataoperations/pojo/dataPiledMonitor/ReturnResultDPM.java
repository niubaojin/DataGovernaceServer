package com.synway.dataoperations.pojo.dataPiledMonitor;

import com.synway.dataoperations.entity.pojo.DoDataPiledMonitorEntity;
import lombok.Data;


@Data
public class ReturnResultDPM {
    private DataPiledChart dataPiledChart;      // 数据堆积折线图数据
    private DoDataPiledMonitorEntity dataPiledSetting;  // 数据堆积表格数据
}
