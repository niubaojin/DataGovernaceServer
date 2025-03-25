package com.synway.property.pojo.formorganizationindex;

import lombok.Data;

@Data
public class PublicDataInfo {
    // 表名
    private String tableName;
    // tableId
    private String tableId;
    // 项目空间
    private String projectName;
    // 数据组织一级分类（样例：JZCODEGASJZZFL05）
    private String sjzzyjfl;
    private String sjzzejfl;
    private String sjzzsjfl;
    // 数据组织一级分类（样例：原始库）
    private String sjzzyjflText;
    private String sjzzejflText;
    private String sjzzsjflText;
}
