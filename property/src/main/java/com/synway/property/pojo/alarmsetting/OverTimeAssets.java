package com.synway.property.pojo.alarmsetting;

import java.io.Serializable;

public class OverTimeAssets implements Serializable {
    private Integer overTimeDays;

    public Integer getOverTimeDays() {
        return overTimeDays;
    }

    public void setOverTimeDays(Integer overTimeDays) {
        this.overTimeDays = overTimeDays;
    }
}
