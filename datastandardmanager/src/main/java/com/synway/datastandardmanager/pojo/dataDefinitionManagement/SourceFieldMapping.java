package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import lombok.Data;

/**
 * 数据集对标探查映射的字段实体类
 * @author obito
 * @version 1.0
 */
@Data
public class SourceFieldMapping {
    //来源字段英文名
    private String sourceField;

    //映射字段英文名
    private String targetField;
}
