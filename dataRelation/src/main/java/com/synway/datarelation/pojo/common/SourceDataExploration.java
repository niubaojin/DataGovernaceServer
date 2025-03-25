package com.synway.datarelation.pojo.common;

import lombok.Data;
import lombok.ToString;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/17 17:22
 */
@Data
@ToString
public class SourceDataExploration {
    // 协议id
    private String sourceId;
    // 数据源id
    private String dataId;
    // 数据仓库那边的 表自增id
    private String tableId;
}
