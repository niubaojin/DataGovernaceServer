package com.synway.property.pojo;

import lombok.Data;

@Data
public class FieldInfo {

    private String resId;
    private String projectName;
    private String tableName;

    //字段序号
    private int no;
    //字段名
    private String fieldName;
    //字段类型
    private String type;
    //字段名或备注
    private String comments;
    //是否分区字段
    private String isPartitionField;
    //字段长度
    private int length;
    //是否是主键，0：否（默认）  1：是
    private int isPrimaryKey;
    //是否是索引，0：否（默认）  1：是
    private int isIndex;
    //是否是为空，0：否（默认）  1：是
    private int nullAble;
    // 分区级别：一级分区，二级分区
    public String partitionLevel;

}
