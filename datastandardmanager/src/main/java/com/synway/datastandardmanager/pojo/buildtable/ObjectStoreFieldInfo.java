package com.synway.datastandardmanager.pojo.buildtable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author obito
 * @date 2021/10/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectStoreFieldInfo implements Serializable {
    private static final long serialVersionUID = 4241231541351428431L;

    /**
     *id
     */
    private String id;

    /**
     * 表存储信息ID
     */
    private String tableInfoId;

    /**
     * 数据元内部标识符
     */
    private String fieldId;

    /**
     * 建表字段英文称
     */
    private String columnName;

    /**
     * 建表字段中文名称
     */
    private String fieldChineeName="";

    /**
     * 建表字段类型
     */
    private Integer fieldType;

    /**
     * 建表字段类型中文
     */
    private String fieldTypeCh;
    /**
     * 建表字段长度
     */
    private Integer fieldLen;

    /**
     * 建表字段默认值
     */
    private String defaultValue;

    /**
     * 建表字段索引
     */
    private Integer tableIndex;

    /**
     * 字段索引类型
     */
    private Integer indexType;

    /**
     * 是否必填项
     */
    private Integer needValue;

    /**
     * 字段顺序
     */
    private Integer recno;

    //分区列标识
    private Integer partitionRecno;
    private String odpsPattition;

    //聚集列
    private Integer clustRecno;

    // 仓库获取的字段类型
    private String columnFieldType;
    // 仓库获取的字段长度
    private Integer columnFieldLen;
    // 仓库获取的建表字段默认值
    private Integer columnDefaultValue;

    // 对标字段
    private String toStandardField;
}
