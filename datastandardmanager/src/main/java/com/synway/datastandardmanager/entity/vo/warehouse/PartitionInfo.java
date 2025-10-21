package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;

@Data
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

}
