package com.synway.datastandardmanager.entity.vo.createTable;

import lombok.Data;

@Data
public class TableColumnVO {

    private String cloumnName;  //字段名
    private String cloumnNameCn;//字段备注
    private String cloumnType;  //字段类型
    private int cloumnLength;   //字段长度
    private int no;             //顺序
    private Boolean isKey;      //主键

}
