package com.synway.governace.pojo.largeScreen.ningbo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询出各个血缘的数据量
 */
@Data
public class AllBloodCount implements Serializable {
    // 数据处理的血缘数据量
    private long standardCount = 0;
    // 数据接入血缘关系的数据量
    private Integer accessCount = 0;
    // 数据加工血缘
    private Integer  dataProcessCount = 0;
    // 数据应用血缘
    private Integer applicationCount = 0;
}
