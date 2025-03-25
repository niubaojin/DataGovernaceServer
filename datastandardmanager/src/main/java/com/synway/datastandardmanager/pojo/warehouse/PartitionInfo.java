package com.synway.datastandardmanager.pojo.warehouse;

import java.io.Serializable;

public class PartitionInfo implements Serializable {
    //排序序号
    private int no;
    //分区值，如日期等
    private String partitionValue;
    //当前分区存储数据记录数(条)
    private long numRows;
    //当前分区存储数据物理大小(KB)
    private long physicalSize;
    //分区创建时间
    private String createTime;
    //最后数据更新时间
    private String lastUpdateTime;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPartitionValue() {
        return partitionValue;
    }

    public void setPartitionValue(String partitionValue) {
        this.partitionValue = partitionValue;
    }

    public long getNumRows() {
        return numRows;
    }

    public void setNumRows(long numRows) {
        this.numRows = numRows;
    }

    public long getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(long physicalSize) {
        this.physicalSize = physicalSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
