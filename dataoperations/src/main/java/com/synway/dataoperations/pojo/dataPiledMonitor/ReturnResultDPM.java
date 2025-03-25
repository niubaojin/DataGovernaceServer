package com.synway.dataoperations.pojo.dataPiledMonitor;

import lombok.Data;


@Data
public class ReturnResultDPM {
    private DataPiledChart dataPiledChart;      // 数据堆积折线图数据
    private DataPiledSetting dataPiledSetting;  // 数据堆积表格数据
}
