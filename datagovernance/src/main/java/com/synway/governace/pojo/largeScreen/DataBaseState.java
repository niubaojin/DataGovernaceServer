package com.synway.governace.pojo.largeScreen;
/**
 * 数据库存储信息
 * @author majia
 * @date 2020/06/02
 */
public class DataBaseState {
    /**
     * 数据库名称
     */
    private String name;

    /**
     * 活表率
     */
    private String liveTableRote;

    /**
     * 已使用物理储存
     */
    private String usedCapacity;
    /**
     * 总物理储存
     */
    private String bareCapacity;
    /**
     * 总记录数
     */
    private String tableCount;
    /**
     * 表数量
     */
    private String tableSum;

    public String getLiveTableRote() {
        return liveTableRote;
    }

    public void setLiveTableRote(String liveTableRote) {
        this.liveTableRote = liveTableRote;
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

    public String getTableSum() {
        return tableSum;
    }

    public void setTableSum(String tableSum) {
        this.tableSum = tableSum;
    }
}
