package com.synway.datastandardmanager.pojo.publicDataManage;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

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
public class PublicDataFieldExcel implements Serializable {
    private static final long serialVersionUID = 4214554515764547L;

    /**
     * 唯一值
     */
    @ExcelIgnore
    private String id;

    @ExcelProperty("*序号")
    @NotNull(message = "序号不能为空")
    @ColumnWidth(value=18)
    private Integer cecno;

    @ExcelProperty("*数据项中文名")
    @NotBlank(message = "【数据项中文名】不能为空")
    @Size(max=50,message = "【数据项中文名】长度不能超过50")
    private String fieldChineseName;

    @ExcelProperty("*数据项唯一编码")
    @NotBlank(message = "【数据项唯一编码】不能为空")
    @Size(max=50,message = "【数据项唯一编码】长度不能超过50")
    private String fieldId;

    @ExcelProperty("*数据项英文名")
    @NotBlank(message = "【数据项英文名】不能为空")
    @Size(max=50,message = "【数据项英文名】长度不能超过50")
    private String fieldName;

    @ExcelProperty("限定词id")
    @Size(max=50,message = "【限定词id】长度不能超过50")
    private String determinerId;

    @ExcelProperty("限定词中文名称")
    @NotBlank(message = "【限定词中文名称】不能为空")
    @Size(max=50,message = "【限定词中文名称】长度不能超过50")
    private String determinerName;

    @ExcelIgnore
    private Integer isQuery;

    @ExcelProperty("是否可查询")
    @NotBlank(message = "【是否可查询】不能为空")
    @Size(max=50,message = "【是否可查询】长度不能超过50")
    private String isQueryStr;

    @ExcelIgnore
    private Integer isContorl;

    @ExcelProperty("是否可布控")
    @NotBlank(message = "【是否可布控】不能为空")
    @Size(max=50,message = "【是否可布控】长度不能超过50")
    private String isContorlStr;

    @ExcelIgnore
    private Integer needValue;

    @ExcelProperty("是否必填项")
    @NotBlank(message = "【是否必填项】不能为空")
    @Size(max=50,message = "【是否必填项】长度不能超过50")
    private String needValueStr;

    @ExcelIgnore
    private String memo;

    @ExcelIgnore
    private String headMemo;
}
