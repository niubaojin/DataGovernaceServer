package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;

/**
 * 查询出各个血缘的数据量
 */
public class AllBloodCount implements Serializable {
    // 数据处理的血缘数据量
    private long standardCount = 0;
    // 数据接入血缘关系的数据量
    private Integer accessCount = 0;
    // 数据加工血缘
    private Integer  dataProcessCount = 0;
    // 数据应用血缘
    private Integer applicationCount = 0;

    public long getStandardCount() {
        return standardCount;
    }

    public void setStandardCount(long standardCount) {
        this.standardCount = standardCount;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public Integer getDataProcessCount() {
        return dataProcessCount;
    }

    public void setDataProcessCount(Integer dataProcessCount) {
        this.dataProcessCount = dataProcessCount;
    }

    public Integer getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(Integer applicationCount) {
        this.applicationCount = applicationCount;
    }
}
