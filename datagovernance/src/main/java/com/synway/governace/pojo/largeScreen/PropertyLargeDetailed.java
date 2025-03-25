package com.synway.governace.pojo.largeScreen;

import lombok.*;

import java.io.Serializable;

/**
 *
 * 数据资产大屏二级
 * @author wdw
 * @version 1.0
 * @date 2021/6/7 11:28
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PropertyLargeDetailed implements Serializable {
    private static final long serialVersionUID = 7723132372555004909L;

    /**
     * 数据名称/服务接口名称
     */
    private String dataName;
    /**
     * 物理表名
     */
    private String tableName;
    /**
     * 具体的数据量/被调用的次数
     */
    private long dataCount;
    /**
     * 页面展示的数据量(带单位)
     */
    private String dataCountStr;
}
