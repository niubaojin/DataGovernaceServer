package com.synway.property.pojo.homepage;
/**
 *
 *
 * @author majia
 * @date 2020/06/02
 */
public class DataBaseState {
    private String name;            //名字
    private String liveTableRote;   //活表率
    private String usedCapacity;    //已使用物理储存
    private String bareCapacity;    //总物理储存
    private String tableCount;      //总记录数
    private String tableSum;        //表数量

    public String getTableSum() {
        return tableSum;
    }

    public void setTableSum(String tableSum) {
        this.tableSum = tableSum;
    }

    public String getTableCount() {
        return tableCount;
    }

    public void setTableCount(String tableCount) {
        this.tableCount = tableCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiveTableRote() {
        return liveTableRote;
    }

    public void setLiveTableRote(String liveTableRote) {
        this.liveTableRote = liveTableRote;
    }

    public String getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(String usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public String getBareCapacity() {
        return bareCapacity;
    }

    public void setBareCapacity(String bareCapacity) {
        this.bareCapacity = bareCapacity;
    }
}
