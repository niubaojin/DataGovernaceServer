package com.synway.datastandardmanager.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * 公共dto
 */
@Data
public class CommonDTO {

    List<String> showField;     // 显示字段控制
    List<String> tableIdList;   // tableId列表
    String dsType;              // 建表数据库类型

}
