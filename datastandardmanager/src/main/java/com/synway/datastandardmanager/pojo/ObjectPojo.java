package com.synway.datastandardmanager.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 数据库表名管理（OBJECT）（源数据表名定义）实体类
 *
 * @author admin
 */
@Data
@Setter
@Getter
@ToString
public class ObjectPojo {
    //主键ID
    private Long objectId;

    //表中文名称
    private String objectName;

    //表描述信息
    private String objectMemo;
    //表状态
    private Integer objectState;
    //表状态(显示)
    private String objectStateVo;

    //更新表类型
    private Integer isActiveTable;

    //表ID
    private String tableId;

    private String tableName;//表英文名称

    private Long recentDays;//最近天数

    private Short objectFlag;//对象标注

    private Integer dataType;//表类型
    private String dataTypeVo;//表类型（显示）

    private Integer storeType;//表所在的数据库类型
    private String storeTypeVo;//表所在的数据库类型(显示)

    private String dbsource;//表类型名称

    private Long sortIndex;//表中显示的顺序

    private Long parentId;//父表ID

    private String tableNameaLias;//表名称的别名

    private String dataSource;//表数据来源的系统
    private String dataSourceVo;//表数据来源的系统(显示)

    private String exhiveTableName;//HIVE下存储的EX表名称

    private Long secretLevel;//数据种类敏感级别

    private String hiveTableName;//HIVE下存储的表名称

    private Long deleter;//

    private Byte deleted;//

    private String ellementName;//对应的数据协议代码

    private String relateTableName;//表字段定义存放的表名

    private String sourceId;//源表ID

    //小版本
    private Integer version;//

    //大版本
    private String versions;

    private int isprivate;//

    private FieldCodeVal fieldCodeVal;//

    private ObjectExtend extendField;//扩展字段，用于新疆交付

    // 20190408 新疆交付需要的字段
    private String versionNumber; // 版本号

    // 20201009 新增4个字段
    // 标准新增时间，默认sysdate  CREATETIME
    private String createTime;
    // 标准最新修改时间 UPDATETIME
    private Date updateTime;
    private String updateTimeStr;
    // 标准创用户名称  CREATOR  标准创用户名称，新建定义时登录用户信息，第三方调用页面时保存“源应用系统建设公司”信息
    private String creator;
    // 标准最新修改用户名称 UPDATER  编辑操作时登录用户信息，第三方调用页面时保存“源应用系统建设公司”信息。
    private String updater;

    /**
     * 登陆的用户id值
     */
    private String userId;
    /**
     * 登陆的用户名
     */
    private String userName;

    /**
     * 数据分级
     */
    private String dataLevel;

    /**
     * 数据分级 页面显示
     */
    private String dataLevelVo;

    /**
     * 数据资源标签1
     */
    private String sjzybq1;

    /**
     * 数据资源标签2
     */
    private String sjzybq2;

    /**
     * 数据资源标签3
     */
    private String sjzybq3;

    /**
     * 数据资源标签4
     */
    private String sjzybq4;

    /**
     * 数据资源标签5
     */
    private String sjzybq5;

    /**
     * 数据资源标签6
     */
    private String sjzybq6;

    /**
     * 标准来源类型
     */
    private Integer standardType;

    /**
     * 数据组织一级分类
     */
    private String SJZZYJFLVALLUE;

    /**
     * 数据组织二级分类
     */
    private String SJZZEJFLVALUE;

    /**
     * 数据来源分类
     */
    private String SJZYLYLXVALUE;
    /**
    * 数据来源二级分类
    */
    private String sjzylylxejValue;

}
