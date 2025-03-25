package com.synway.dataoperations.pojo.dataPiledMonitor;

import lombok.Data;

import java.util.List;

@Data
public class DataPiledChart {
    private List<String> piledRate; // 堆积率
    private List<String> pushHours; // 推送数据时点
}
