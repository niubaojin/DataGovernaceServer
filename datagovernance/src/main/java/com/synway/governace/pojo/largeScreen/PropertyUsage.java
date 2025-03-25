package com.synway.governace.pojo.largeScreen;

import java.io.Serializable;
import java.util.List;

/**
 * 资产使用情况
 * 数据使用热度TOP5 应用使用热度TOP5
 * @author wangdongwei
 * @date 2021/4/25 14:02
 */
public class PropertyUsage implements Serializable {

    /**
     * 数据使用热度TOP5 根据使用次数从大到小排序
     */
    private List<UseHeatProperty> tableUseHeatList;

    /**
     * 应用使用热度TOP5 根据使用次数从大到小排序
     */
    private List<UseHeatProperty> applicationUseHeatList;

    public List<UseHeatProperty> getTableUseHeatList() {
        return tableUseHeatList;
    }

    public void setTableUseHeatList(List<UseHeatProperty> tableUseHeatList) {
        this.tableUseHeatList = tableUseHeatList;
    }

    public List<UseHeatProperty> getApplicationUseHeatList() {
        return applicationUseHeatList;
    }

    public void setApplicationUseHeatList(List<UseHeatProperty> applicationUseHeatList) {
        this.applicationUseHeatList = applicationUseHeatList;
    }

}

