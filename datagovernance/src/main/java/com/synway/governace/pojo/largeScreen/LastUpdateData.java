package com.synway.governace.pojo.largeScreen;


import java.math.BigDecimal;

/**
 * 数据大屏最新数据信息
 *
 * @author ywj
 * @date 2020/7/23 11:06
 */
public class LastUpdateData {
    // 类型简拼
    private String dataType;
    // 数据名称
    private String dataName;
    // 数据量
    private BigDecimal dataCount;
    // 数据时间
    private String dataTime;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
