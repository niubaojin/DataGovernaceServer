package com.synway.datastandardmanager.entity.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.synway.datastandardmanager.valid.ListValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据要素库
 * @author nbj
 * @Date 2025年5月12日15:17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(15)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@TableName("SYNLTE.ENTITY_ELEMENT")
public class EntityElementEntity {

    @ExcelIgnore
    @TableField(exist = false)
    private int num;

    //要素标识符
    @NotNull(message = "【主键ID】不能为空")
    @Size(max=50,message = "【主键ID】长度不能超过50")
    @ExcelProperty("*要素标识符")
    @TableField("ELEMNTCODE")
    private String elementCode;

    //要素名称
    @NotNull(message = "【要素名称】不能为空")
    @Size(max=200,message = "【要素名称】长度不能超过200")
    @ExcelProperty("*要素名称")
    @TableField("ELEMNTCHNAME")
    private String elementChname;

    //是否内部要素标识符
    @NotNull(message = "【是否内标标识符】不能为空")
    @Size(max=4,message = "【是否内标标识符】长度不能超过4")
    @ExcelProperty("*要素来源代码")
    @TableField("ISELEMNT")
    private String isElement;

    @ExcelProperty("要素来源")
    @TableField(exist = false)
    private String isElementType;

    //语义
    @ExcelProperty("语义")
    @Size(max=50,message = "【语义】长度不能超过50")
    @TableField("SAMEID")
    private String sameId;

    //关联主体
    @ListValue(vals = {"1","2","3","4","5","6","7"}, message = "[主体]的值只能为1/2/3/4/5/6/7")
    @Size(max=50,message = "【关联主体】长度不能超过50")
    @NotNull
    @ExcelProperty("*要素主体代码")
    @TableField("ELEMNTOBJECT")
    private String elementObject;

    //主体信息翻译
    @ExcelProperty("要素主体")
    @TableField(exist = false)
    private String elementObjectType;

    //规则
    @ExcelProperty("要素描述")
    @TableField("ELEMNTRULE")
    private String elementRule;

    //生成方式
    @ExcelProperty("*生成方式代码值")
    @TableField("CREATEMODE")
    private Integer createMode;

    @ExcelProperty("生成方式")
    @TableField(exist = false)
    private String createModeType;

    //修订人
    @ExcelProperty("*修订人")
    @Size(max=50,message = "【修订人】长度不能超过50")
    @TableField("AUTHOR")
    private String author;

    //修订时间
    @ExcelProperty("修订时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("MODDATE")
    private Date modDate;

    //数据元的fieldId
    @ExcelIgnore
    @TableField(exist = false)
    private String dataElementId="";

    @ExcelProperty(index = 12,value="  ")
    @HeadStyle(fillForegroundColor=1,fillBackgroundColor = 1)
    @TableField(exist = false)
    private String memo;
}
