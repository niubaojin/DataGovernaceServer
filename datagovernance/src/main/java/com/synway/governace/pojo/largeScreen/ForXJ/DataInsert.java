package com.synway.governace.pojo.largeScreen.ForXJ;

import lombok.Data;

/**
 * 数据接入
 */

@Data
public class DataInsert {
    String allCount;            // 数据总条数（单位：亿条）
    String allSize;             // 数据总大小（单位：TB）
    String partitionAllCount;   // 今日数据总条数（单位：亿条）
    String partitionAllSize;    // 今日数据总大小（单位：TB）
    String insertDate;          // 插入时间
}
