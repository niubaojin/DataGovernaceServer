package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据标准表名管理(OBJECT)(源数据表名定义)
 * @author nbj
 * @Date 2025年5月8日15:19:03
 */
@Data
@TableName("SYNLTE.OBJECT")
public class ObjectEntity {
    //主键ID
    @TableField("OBJECTID")
    private Integer objectId;

    //表中文名称（dataSourceName）                             前端需要替换的项
    @TableField("OBJECTNAME")
    private String objectName;

    //表状态
    @TableField("OBJECTSTATE")
    private Integer objectState;

    //表状态(显示)（存储表状态：storageTableStatus）             前端需要替换的项
    @TableField(exist = false)
    private String objectStateVo;

    //表描述信息
    @TableField("OBJECTMEMO")
    private String objectMemo = "";

    //表ID
    @TableField("TABLEID")
    private String tableId;

    //表英文名称（realTablename）                              前端需要替换的项
    @TableField("TABLENAME")
    private String tableName;

    //[物理表名]长度不能超过80
    @TableField(exist = false)
    private String realTablename="";

    //最近天数
    @TableField("RECENTDAYS")
    private Integer recentDays;

    //对象标注
    @TableField("OBJECTFLAG")
    private Integer objectFlag;

    //表类型
    @TableField("DATATYPE")
    private Integer dataType;

    //表类型（显示）
    @TableField(exist = false)
    private String dataTypeVo;

    //表所在的数据库类型
    @TableField("STORETYPE")
    private Integer storeType;

    //表所在的数据库类型(显示)（存储方式：storageDataMode）       前端需要替换的项
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

    //表数据来源的系统（源应用系统名称二级：codeTextTd/codeTextCh）  前端需要替换的项
    @TableField("DATA_SOURCE")
    private String dataSource;

    //对应的数据协议 系统代码的中文名称（codeTextCh）
    @TableField(exist = false)
    private String dataSourceCh;

    //对应新版数据定义的源应用系统名称的1级（parentCodeTextId）
    @TableField(exist = false)
    private String dataSourceOne;

    //HIVE下存储的EX表名称
    @TableField("EXHIVETABLENAME")
    private String exHiveTableName;

    //HIVE下存储的表名称
    @TableField("HIVETABLENAME")
    private String hiveTableName;

    //DELETED
    @TableField("DELETED")
    private Integer deleted;

    //对应的数据协议代码
    @TableField("ELLEMENTNAME")
    private String ellenmentName;

    //DELETER
    @TableField("DELETER")
    private String deleter;

    //ISPRIVATE
    @TableField("ISPRIVATE")
    private String isPrivate;

    //版本号
    @TableField("VERSIONS")
    private String versions;

    //数据分级（dataLevel）                                       前端需要替换的项
    @TableField("SECRETLEVEL")
    private String secretLevel;

    //20210915，数据分级中文名称：dataLevelCh
    @TableField(exist = false)
    private String secretLevelCh;

    //表字段定义存放的表名
    @TableField("RELATE_TABLENAME")
    private String relateTableName;

    //源表ID
    @TableField("SOURCEID")
    private String sourceId;

    //是否实时表
    @TableField("ISACTIVETABLE")
    private Integer isActiveTable;

    //HD_DBSOURCE
    @TableField("HD_DBSOURCE")
    private String hdDbSource;

    //B表名称
    @TableField("TABLENAME_B")
    private String tableNameB;

    //ISQUERY
    @TableField("ISQUERY")
    private Integer isQuery;

    //GA_SOURCEID
    @TableField("GA_SOURCEID")
    private String gaSourceId;

    //标签类型
    @TableField("TAG_TYPES")
    private String tagTypes;

    //标准来源类型
    @TableField("STANDARD_TYPE")
    private Integer standardType;

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
    private String sjzylylxValue;

    //数据组织一级分类码值
    @TableField("SJZZYJFLVALLUE")
    private String sjzzyjflVallue;

    //数据组织二级分类码值
    @TableField("SJZZEJFLVALUE")
    private String sjzzejflValue;

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

    //版本日期
    @TableField("VERSION")
    private Integer version;

    //创建人
    @TableField("CREATOR")
    private String creator;

    //更新人
    @TableField("UPDATER")
    private String updater;

    //来自标准建表语句（疑似为了之前的字段命名错误）
    @TableField("SJZZYJFLVALLUE")
    private String sjzzyjflvalue;

    // 目标协议对应厂商
    @TableField(exist = false)
    private String ownerFactory;
    @TableField(exist = false)
    private String ownerFactoryCode;

    // 在调用修改表分级分类接口时，需要用到该字段，多个用英文逗号分隔
    // 数据组织分类的ids
    @TableField(exist = false)
    private String classIds="";
    // 数据来源分类的ids
    @TableField(exist = false)
    private String sourceClassIds="";
    // 一级二级拼接在一起，用 / 区分，数据组织：有些存在3级分类，一级/二级/三级
    @TableField(exist = false)
    private String organizationClassify;
    // 一级二级拼接在一起，用 / 区分，数据来源
    @TableField(exist = false)
    private String sourceClassify;

    //  判断是否为流程
    @TableField(exist = false)
    private Boolean flow = false;

}
