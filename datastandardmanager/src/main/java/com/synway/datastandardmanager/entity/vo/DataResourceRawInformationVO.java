package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 从数据仓库接口获取到的原始数据的详细信息
 */
@Data
public class DataResourceRawInformationVO {
    //表中文名
    private String tableNameCh;
    //object表中真实存在的表名
    private String tableNameObject;
    // objectId获取
    private String objectId;
    //数据仓库id（数据源Id）
    private String dataBaseId;
    //数据名称(表英文名)
    private String dataSourceName;
    //来源协议编码
    private String sourceId;
    //来源单位中文名
    private String sourceFirmCh;
    //来源单位代码
    private String sourceFirmCode;
    //所属应用系统中文名
    private String sourceProtocolCh;
    //所属应用系统代码
    private String sourceProtocolCode;
    //业务含义描述
    private String memo;
    // 来源表名
    private String sourceTableName = "";
    // 2020518 添加字段回填数据仓库那边的 数据来源分类信息
    private String dataResourceOrigin = "";
    private String dataOrganizationStr = "";

    //数据组织分类和数据来源分类的 id值 用 / 分隔
    private String dataResourceOriginIds = "";
    private String dataOrganizationIds = "";

    //数据安全分级
    private String dataLevel = "";
    //项目空间
    private String project = "";
    //数据中心id
    private String centerId;
    //数据中心名称
    private String centerName;
    //数据源名称
    private String resName;
    //数据源类型
    private String resType;
    //原始字段的相关信息
    private List<FieldColumn> FieldList;

    @Data
    public static class FieldColumn {
        // 字段名
        private String fieldName;
        // 字段中文注释
        private String fieldDescription;
        // 字段类型 （不包括长度）
        private String fieldType;
        //字段类型(长度)
        private String iFieldType;
        // 字段长度
        private String fieldLength;
        // 字段是否为空
        private String isNonnull;
        // 是否为主键
        private String isPrimarykey;
    }

}
