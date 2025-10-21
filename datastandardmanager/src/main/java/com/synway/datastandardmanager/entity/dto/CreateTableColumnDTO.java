package com.synway.datastandardmanager.entity.dto;

import lombok.Data;

/**
 * @author nbj
 * @date 2025年8月8日14:27:04
 */
@Data
public class CreateTableColumnDTO {
    private String rowNum;              // 字段序号
    private String columnEngname;       // 字段英文名
    private String columnChinese;       // 字段中文名
    private String columnType;          // 建表字段类型
    private String columnLen;           // 建表字段长度
    private boolean isPartition;        // 是否分区
    private boolean needAdd = false;
    private String type ;               // left right
}
