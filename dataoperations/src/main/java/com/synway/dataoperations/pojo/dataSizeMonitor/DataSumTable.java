package com.synway.dataoperations.pojo.dataSizeMonitor;

import lombok.Data;

import java.util.List;

@Data
public class DataSumTable {
    private String tableNameCh; // 数据中文名
    private String tableNameEn; // 数据英文名
    private String tableId;     // 数据协议id
    private String accessSum;   // 接入量
    private String handlerSum;  // 处理量
    private List<StorageLocationDataSize> insertSumList; // 入库量列表
}
