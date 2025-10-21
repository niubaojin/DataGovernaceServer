package com.synway.datastandardmanager.entity.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据资源标签
 * @author nbj
 * @date 2025年5月12日16:53:03
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@Data
@TableName("SYNLTE.LABELS")
public class LabelsEntity {
    //资源标签ID
    @ExcelIgnore
    @TableId(type = IdType.NONE)
    @TableField("ID")
    private String id;

    //资源标签中文名称
    @ExcelProperty("*标签名称")
    @NotBlank(message = "【标签名称】不能为空")
    @Size(max=50,message = "【标签名称】长度不能超过50")
    @TableField("LABELNAME")
    private String labelName;

    //资源标签码值
    @ExcelProperty("*标签代码值")
    @Size(max=20,message = "【标签代码】长度不能超过20")
    @TableField("LABELCODE")
    private String labelCode;

    //资源标签层级
    @NotNull(message = "【标签类型代码】不能为空")
    @ExcelProperty("*标签类型代码")
    @ColumnWidth(value=18)
    @TableField("LABELLEVEL")
    private Integer labelLevel;

    @ExcelProperty("标签类型")
    @TableField(exist = false)
    private String LabelLevelStr;

    //常用组织分类
    @Size(max=10,message = "【常用组织分类代码】长度不能超过10")
    @ExcelProperty("*常用组织分类代码")
    @NotBlank(message = "【常用组织分类代码】不能为空")
    @ColumnWidth(value=20)
    @ContentStyle(quotePrefix = BooleanEnum.TRUE)
    @TableField("CLASSID")
    private Integer classId;

    @ExcelProperty("常用组织分类")
    @TableField(exist = false)
    private String ClassIdStr;

    //标签说明
    @ExcelProperty("标签说明")
    @Size(max=500,message = "【标签说明】长度不能超过500")
    @TableField("LABELDESCRIBE")
    private String labelDescribe;

    //修订人
    @ExcelProperty("*修订人")
    @NotBlank(message = "【修订人】不能为空")
    @Size(max=100,message = "【修订人】长度不能超过100")
    @TableField("AUTHOR")
    private String author;

    @ExcelProperty(index = 6,value="  ")
    @HeadStyle(fillForegroundColor=1,fillBackgroundColor = 1)
    @TableField(exist = false)
    private String memo;

    //修订时间
    @ExcelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("MODDATE")
    private Date modDate;

    @ExcelIgnore
    @TableField(exist = false)
    private String modDateStr="";

    //更新标志：add表示新增，update表示编辑
    @ExcelIgnore
    @TableField(exist = false)
    private String addUpdateTag;

    //关联数据
    @ExcelProperty("关联数据")
    @TableField(exist = false)
    private Integer tableCount;

}
