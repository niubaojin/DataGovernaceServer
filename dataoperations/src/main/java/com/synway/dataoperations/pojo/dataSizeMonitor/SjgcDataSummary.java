package com.synway.dataoperations.pojo.dataSizeMonitor;

import lombok.Data;

@Data
public class SjgcDataSummary {
    String dataType;        // 标识：（1：数据接入2：数据处理3：数据入库4：数据集成）
    String inTableNameCh;   // 输入数据中文名称
    String inProtocol;      // 输入数据协议名称
    String inSuccessSum;    // 输入数据条数
    String outTableNameCh;  // 输出数据中文名称
    String outTableNameEn;  // 输出数据英文名称
    String outProtocol;     // 输出数据协议名称
    String outSuccessSum;   // 输出成功条数
    String outErrorSum;     // 输出异常条数
    String outStoreType;    // 输出平台
    String outProject;      // 输出平台项目空间
    String pushDate;        // 推送日期
    String pushHour;        // 推送数据时点
    String createTime;      // 入表时间
    String sjzzsjfl;        // 数据组织三级分类
    String storageLocation; // 存储位置
}
