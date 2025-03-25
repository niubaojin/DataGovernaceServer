package com.synway.datastandardmanager.pojo;

import lombok.Data;

/**
 * 来源关系的展示的实体类
 */
@Data
public class SourceRelationShip {
    // 数据组织
    public static final String ORGANIZATIONAL="organizational";
    // 数据仓库
    public static final String DATABASE="database";
    //来源数据名
    private String dataSourceName;
    //真实表名
    private String realTableName;
    //来源数据协议
    private String sourceSystem;
    //来源系统代码
    private String sourceProtocol;
    //来源系统代码
    private String sourceProtocolCh;
    //来源厂商
    private String sourceFirm;
    //存储表状态
    private String storageTableStatus;

    //20200226 添加的该来源关系是通过数据组织还是数据仓库添加的字段
    private String addType="organizational";

    // 数据仓库的信息 data_id  table_id
    private String dataId="";

    private String resourceId;

    /**
     * 数据源中文名
     */
    private String dataIdCh;

    private String tableId="";
    // 20200415 新增 centerId
    private String centerId = "";

    /**
     * 数据中心中文
     */
    private String centerName;


    //数据名称 1.9原始数据来源信息中的数据名称
    private String dataName;

    /**
     * 数据源类型
     */
    private String resType;

    /**
     * 数据源名称
     */
    private String resName;

    //项目空间
    private String project;

    private String sourceId;

    // 原始数据来源信息的：数据名称、数据表名
    private String tableNameCN;
    private String tableNameEN;
}
