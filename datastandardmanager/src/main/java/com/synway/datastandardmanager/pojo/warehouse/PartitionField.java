package com.synway.datastandardmanager.pojo.warehouse;

import java.io.Serializable;

public class PartitionField implements Serializable {
    //排序序号
    private int no;
    //分区字段名
    private String fieldName;
    //分区级别
    private String level;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
