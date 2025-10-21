package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;

@Data
public class PartitionField implements Serializable {
    //排序序号
    private int no;
    //分区字段名
    private String fieldName;
    //分区级别
    private String level;
}
