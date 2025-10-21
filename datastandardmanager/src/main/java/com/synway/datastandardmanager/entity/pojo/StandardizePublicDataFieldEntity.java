package com.synway.datastandardmanager.entity.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 公共字段信息
 * @author nbj
 * @date 2025年5月12日20:26:29
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(15)
// 设置excel头的背景色为：暗板岩蓝、浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@Data
@TableName("SYNLTE.STANDARDIZE_PUBLIC_DATAFIELD")
public class StandardizePublicDataFieldEntity {

    //公共数据项id
    @ExcelIgnore
    @TableField("ID")
    private String id;

    //公共数据分组id
    @ExcelIgnore
    @TableField("GROUP_ID")
    private String groupId;

    //字段序号值
    @ExcelProperty("*序号")
    @TableField("RECNO")
    private Integer recno;

    //数据元中文名
    @ExcelProperty("*数据项中文名")
    @TableField("FIELD_CHINESE_NAME")
    private String fieldChineseName;

    //数据元编码
    @ExcelProperty("*数据项唯一编码")
    @TableField("FIELD_ID")
    private String fieldId;

    @ExcelIgnore
    @TableField(exist = false)
    private String gadsjFieldId;

    //数据元英文名
    @ExcelProperty("*数据项英文名")
    @TableField("FIELD_NAME")
    private String fieldName;

    //字段名称(与fieldName值相同)
    @ExcelIgnore
    @TableField(exist = false)
    private String columnName;

    //限定词ID
    @ExcelProperty("限定词代码值")
    @TableField("DETERMINER_ID")
    private String determinerId;

    //限定词中文名
    @ExcelIgnore
    @TableField("DETERMINER_NAME")
    private String determinerName;

    //可查询
    @ExcelIgnore
    @TableField("IS_QUERY")
    private Integer isQuery;

    //是否可查询中文
    @ExcelProperty("是否可查询")
    @TableField(exist = false)
    private String isQueryStr;

    //可布控
    @ExcelIgnore
    @TableField("IS_CONTORL")
    private Integer isContorl;

    //是否可布控中文
    @ExcelProperty("是否可布控")
    @TableField(exist = false)
    private String isContorlStr;

    //必填
    @ExcelIgnore
    @TableField("NEED_VALUE")
    private Integer needValue;

    //是否必填项中文
    @ExcelProperty("是否必填")
    @TableField(exist = false)
    private String needValueStr;

    //是否作为MD5值提取
    @ExcelIgnore
    @TableField("ISMD5")
    private Integer isMd5;

    @ExcelProperty("是否参与md5运算")
    @TableField(exist = false)
    private String md5IndexStr;

    //是否参与md5运算
    @ExcelIgnore
    @TableField(exist = false)
    private Integer md5Index;

    //用于页面的字段
    @ExcelIgnore
    @TableField(exist = false)
    private Boolean md5IndexStatus;

    //数据项说明
    @ExcelProperty("数据项说明")
    @TableField("MEMO")
    private String memo;

    //公共头说明
    @ExcelProperty("表头说明")
    @TableField("HEAD_MEMO")
    private String headMemo;

    @ExcelIgnore
    @TableField(exist = false)
    private String fieldType;

    @ExcelIgnore
    @TableField(exist = false)
    private String fieldLen;

}
