package com.synway.governace.pojo.largeScreen.ningbo;

import lombok.Data;

/**
 * 资产统计类
 * @author nbj
 * @date 2023年10月10日14:00:52
 */

@Data
public class PropertyStatistics {
    private String dataCode;        // 数据代码
    private String dataName;        // 数据名称
    private String dataCount;       // 数据量
    private String statisticsDate;  // 统计日期
}
