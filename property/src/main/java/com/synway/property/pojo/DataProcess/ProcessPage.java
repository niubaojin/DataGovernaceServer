package com.synway.property.pojo.DataProcess;

import java.io.Serializable;
import java.util.List;

/**
 *  数据历程页面的返回结果
 * @author wdw
 */
public class ProcessPage implements Serializable{
    // 本周的数据历程的数据
    private List<ProcessPageShow> lastweek;
    // 上一个月之后的数据
    private List<ProcessPageShow> longago;
    //  上周 -  上一个月的数据
    private List<ProcessPageShow> month;
    // 上周的数据历程数据
    private List<ProcessPageShow> week;

    public List<ProcessPageShow> getLastweek() {
        return lastweek;
    }

    public void setLastweek(List<ProcessPageShow> lastweek) {
        this.lastweek = lastweek;
    }

    public List<ProcessPageShow> getLongago() {
        return longago;
    }

    public void setLongago(List<ProcessPageShow> longago) {
        this.longago = longago;
    }

    public List<ProcessPageShow> getMonth() {
        return month;
    }

    public void setMonth(List<ProcessPageShow> month) {
        this.month = month;
    }

    public List<ProcessPageShow> getWeek() {
        return week;
    }

    public void setWeek(List<ProcessPageShow> week) {
        this.week = week;
    }
}
