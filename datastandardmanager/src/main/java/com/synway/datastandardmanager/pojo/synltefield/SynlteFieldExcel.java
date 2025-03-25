package com.synway.datastandardmanager.pojo.synltefield;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = false)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SynlteFieldExcel implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 4465465524112419292L;

    @NotBlank(message="[唯一标识符]不能为空")
    @ExcelProperty("唯一标识符")
    private String fieldId;

    @NotBlank(message="[标准英文名]不能为空")
    @ExcelProperty("标准英文名")
    private String fieldName;

    @NotBlank(message="[中文名]不能为空")
    @ExcelProperty("中文名")
    private String fieldChineseName;

    @ExcelProperty("字段类型")
    private Integer fieldType;

    @ExcelIgnore
    private String fieldTypeCh;

    @NotBlank(message="[字段长度]不能为空")
    @ExcelProperty("字段长度")
    private String fieldLen;

    @ExcelProperty("浮点长度2")
    private String fieldLen2;

    @NotNull(message="[状态]不能为空")
    @ExcelProperty("状态")
    private String statusNum;

    @ExcelIgnore
    private String status;

    @ExcelProperty("内部标识符")
    private String gadsjFieldId;

    @NotBlank(message="[建表字段]不能为空")
    @ExcelProperty("建表字段英文名")
    private String columnName;

    @ExcelProperty("中文全拼")
    private String fullChinese;

    @ExcelProperty("版本")
    private String versions;

    @ExcelProperty("版本发布日期")
    private Integer releaseDate;

    @ExcelProperty("字典代码集")
    private String codeId;

    @ExcelProperty("字段分类")
    private String fieldClass;

    @ExcelProperty("字段安全级别")
    private String secretClass;


    @ExcelIgnore
    private String secretClassCh;

    @ExcelProperty("归一化标识")
    private int isNormal;

    /**
     * 标识符
     */
    @ExcelIgnore
    private String simChinese;

    @ExcelProperty("标准类型")
    private int fieldStandard;

    @ExcelProperty("语义类型")
    private String sameId;

    @ExcelProperty("同义名称")
    private String synonyName;

    @ExcelProperty("对象类词")
    private String objectType;

    @ExcelProperty("表示词")
    private String expressionWord;

    @ExcelProperty("特性词")
    private String attributeWord;

    @ExcelProperty("计量单位")
    private String unit;

//    @NotNull(message="[关系]不能为空")
    @ExcelProperty("关系")
    private String relate;

    @ExcelProperty("关系关联数据元")
    private String fieldRelates;

    @ExcelProperty("值域描述信息")
    private String codeIdDetail;

    @ExcelProperty("融合单位类型")
    private String fuseType;

    @ExcelProperty("融合单位数据元内部标识符")
    private String fuseFieldId;

    @NotNull(message="[提交机构]不能为空")
    @ExcelProperty("提交机构")
    private String subOrg;

    @ExcelProperty("主要起草人")
    private String subAuthor;

    @ExcelProperty("批准日期")
    @JsonFormat(pattern = "dd-mm-yyyy",timezone = "GMT+8")
    @DateTimeFormat(pattern ="dd-mm-yyyy")
    private Date onDate;

    @ExcelProperty("注册机构")
    private String regOrg;

    @ExcelProperty("厂商信息")
    private String facturer;

    @ExcelProperty("语境")
    private String context;

    @ExcelProperty("应用约束")
    private String constraint;

    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("备注")
    private String memo;


}
