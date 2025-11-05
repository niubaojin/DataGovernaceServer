package com.synway.datastandardmanager.entity.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 数据标准表建表信息
 * @author nbj
 * @date 2025年5月12日20:05:43
 */
@Data
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(15)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
//内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@TableName("SYNLTE.OBJECT_STORE_INFO")
public class ObjectStoreInfoEntity {
    //表存储信息ID
    @ExcelIgnore
    @TableField("TABLEINFOID")
    private String tableInfoId;

    //标准数据项集编码
    @ExcelProperty("数据集编码")
    @TableField("TABLEID")
    private String tableId;

    //表英文名称
    @ExcelProperty("数据英文名")
    @TableField("TABLENAME")
    private String tableName;

    //数据中文名称
    @ExcelProperty("数据中文名")
    @TableField("OBJECTNAME")
    private String objectName;

    //平台类型
    @ExcelIgnore
    @TableField("STORE_TYPE")
    private Integer storeType;

    @ExcelProperty("平台类型")
    @TableField(exist = false)
    private String storeTypeCh;

    //表空间名称
    @ExcelIgnore
    @TableField("PROJECT_NAME")
    private String projectName;

    //表空间中文名称
    @ExcelIgnore
    @TableField("PROJECT_NAME_CN")
    private String projectNameCh;

    //存储描述信息
    @ExcelIgnore
    @TableField("MEMO")
    private String memo;

    //创建者
    @ExcelProperty("创建者")
    @TableField("CREATER")
    private String creater;

    //创建者代码
    @ExcelIgnore
    @TableField("CREATER_ID")
    private String createrId;

    //表创建时间
    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("TABLE_CREATE_TIME")
    private Date tableCreateTime;

    //表结构修改时间
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("TABLE_MOD_TIME")
    private Date tableModTime;

    //是否入库
    @ExcelProperty("是否入库")
    @TableField("IMPORT_FLAG")
    private Integer importFlag;

    @ExcelIgnore
    @TableField(exist = false)
    private String comparison;

    //是否为分区表
    @ExcelIgnore
    @TableField("ISPARTITION")
    private Integer isPartition;

    //是否实时表
    @ExcelIgnore
    @TableField("ISACTIVETABLE")
    private Integer isActiveTable;

    //生命周期
    @ExcelIgnore
    @TableField("LIFECYCLE")
    private Integer lifeCycle;

    @ExcelIgnore
    @TableField(exist = false)
    private String lifeCycleStr;

    //数据源关联字段
    @ExcelIgnore
    @TableField("DATAID")
    private String dataId;

    //服务默认查询标识
    @ExcelIgnore
    @TableField("SEARCHFLAG")
    private Integer searchFlag;

    //分区类型
    @ExcelProperty("分区类型")
    @TableField("PARTITIONTYPE")
    private Integer partitionType;

    //分区数
    @ExcelIgnore
    @TableField("PARTITIONCOUNT")
    private Integer partitionCount;

    /**
     * 建表信息管理页面需要展示的字段信息
     */
    //标准英文名
    @ExcelIgnore
    @TableField(exist = false)
    private String standTableName;
    //数据中心名称
    @ExcelIgnore
    @TableField(exist = false)
    private String centerName;
    @ExcelIgnore
    @TableField(exist = false)
    private String centerId;
    //存储数据源(数据源名称)
    @ExcelProperty("存储数据源")
    @TableField(exist = false)
    private String resName;
    // 数据源id
    @ExcelIgnore
    @TableField(exist = false)
    private String resId;
    //注册资源数
    @ExcelIgnore
    @TableField(exist = false)
    private Integer registerCount;
    //数据组织id
    @ExcelIgnore
    @TableField(exist = false)
    private String organizationClassificationId;
    //数据组织中文
    @ExcelIgnore
    @TableField(exist = false)
    private String organizationClassificationCh;
    //表字段信息
    @ExcelIgnore
    @TableField(exist = false)
    private List<ObjectStoreFieldInfoEntity> objectStoreFieldInfos;
    // 真实表名（物理表名）
    @ExcelProperty("入库表名/主题")
    @TableField(exist = false)
    private String realTableName;
    @ExcelIgnore
    @TableField(exist = false)
    private Integer objectId;

}
