package com.synway.governace.pojo.generalManagement;

import lombok.Data;

/**
 * 数据堆积配置
 */
@Data
public class DataPiledSetting {
    private String id;                  // 主键id
    private String dataName;            // 数据名称
    private String dataCenterID;        // 数据中心ID
    private String dataCenterName;      // 数据中心名称
    private String dataWarehouseID;     // 数据仓库ID
    private String dataWarehouseName;   // 数据仓库名称
    private String consumTopic;         // 消费主题
    private String consumGroup;         // 消费组名
    private String yiban;               // 偏移量告警阈值（一般）
    private String jinggao;             // 偏移量告警阈值（告警）
    private String yanzhong;            // 偏移量告警阈值（严重）
    private String isEnable;            // 是否启用
    private String remark;              // 备注
    private String connectInfo;         // 数据源连接信息
}
