package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据标准表备份表
 * @author nbj
 * @date 2025年5月12日17:33:41
 */
@Data
@TableName("SYNLTE.OBJECT_HISTORY")
public class ObjectHisEntity {
    //标准表版本主键
    @TableField("OBJECTID_VERSION")
    private String objectIdVersion;

    //主键ID
    @TableField("OBJECTID")
    private Integer objectId;

    //表中文名称
    @TableField("OBJECTNAME")
    private String objectName;

    //表描述信息
    @TableField("OBJECTMEMO")
    private String objectMemo;

    //表状态
    @TableField("OBJECTSTATE")
    private Integer objectState;

    @TableField(exist = false)
    private String objectStateVo;

    //表ID
    @TableField("TABLEID")
    private String tableId;

    //表英文名称
    @TableField("TABLENAME")
    private String tableName;

    //最近天数
    @TableField("RECENTDAYS")
    private Integer recentDays;

    //对象标注
    @TableField("OBJECTFLAG")
    private Integer objectFlag;

    //表类型
    @TableField("DATATYPE")
    private Integer dataType;

    @TableField(exist = false)
    private String dataTypeVo;

    //表所在的数据库类型
    @TableField("STORETYPE")
    private Integer storeType;

    @TableField(exist = false)
    private String storeTypeVo;

    //表类型名称
    @TableField("DBSOURCE")
    private String dbSource;

    //表中显示的顺序
    @TableField("SORTINDEX")
    private Integer sortIndext;

    //父表ID
    @TableField("PARENTID")
    private Integer parentId;

    //表名称的别名
    @TableField("TABLENAMEALIAS")
    private String tableNameAlias;

    //表数据来源的系统
    @TableField("DATA_SOURCE")
    private String dataSource;

    //HIVE下存储的EX表名称
    @TableField("EXHIVETABLENAME")
    private String exHiveTableName;

    //HIVE下存储的表名称
    @TableField("HIVETABLENAME")
    private String hiveTableName;

    //数据分级
    @TableField("SECRETLEVEL")
    private Integer secretLevel;

    @TableField(exist = false)
    private String secretLevelVo;

    //对应的数据协议代码
    @TableField("ELLEMENTNAME")
    private String ellenmentName;

    //表字段定义存放的表名
    @TableField("RELATE_TABLENAME")
    private String relateTableName;

    //源表ID
    @TableField("SOURCEID")
    private String sourceId;

    //是否实时表
    @TableField("ISACTIVETABLE")
    private Integer isActiveTable;

    //B表名称
    @TableField("TABLENAME_B")
    private String tableNameB;

    //标签类型
    @TableField("TAG_TYPES")
    private String tagTypes;

    //数据资源标签1
    @TableField("SJZYBQ1")
    private String sjzybq1;

    //数据资源标签2
    @TableField("SJZYBQ2")
    private String sjzybq2;

    //数据资源标签3
    @TableField("SJZYBQ3")
    private String sjzybq3;

    //数据资源标签4
    @TableField("SJZYBQ4")
    private String sjzybq4;

    //数据资源标签5
    @TableField("SJZYBQ5")
    private String sjzybq5;

    //数据资源标签6
    @TableField("SJZYBQ6")
    private String sjzybq6;

    //数据来源分类码值
    @TableField("SJZYLYLXVALUE")
    private String sjzylylxvalue;

    //数据组织一级分类码值
    @TableField("SJZZYJFLVALLUE")
    private String sjzzyjflvallue;

    //数据组织二级分类码值
    @TableField("SJZZEJFLVALUE")
    private String sjzzejflvalue;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
    private Date createTime;

    //最新修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
    private Date updateTime;

    //创建人
    @TableField("CREATOR")
    private String creator;

    //更新人
    @TableField("UPDATER")
    private String updater;

    //版本日期
    @TableField("VERSION")
    private Integer version;

    //版本号
    @TableField("VERSIONS")
    private String versions;

    //标准来源类型
    @TableField("STANDARD_TYPE")
    private Integer standardType;

}
