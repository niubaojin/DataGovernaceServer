package com.synway.datastandardmanager.pojo.labelmanage;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * 文件导入以及模板文件需要的对象
 * @author wdw
 * @version 1.0
 * @date 2021/6/9 19:33
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
@NoArgsConstructor
@AllArgsConstructor
public class LabelManageExcel implements Serializable {

    private static final long serialVersionUID = -7983839892933713063L;
    /**
     * 唯一值
     */
    @ExcelIgnore
    private String id;
    /**
     * 标签名称
     */
    @ExcelProperty("*标签名称")
    @NotBlank(message = "【标签名称】不能为空")
    @Size(max=50,message = "【标签名称】长度不能超过50")
    private String labelName="";


    /**
     * 标签级别 代码
     */
    @NotNull(message = "【标签类型代码】不能为空")
    @ExcelProperty("*标签类型代码")
    @ColumnWidth(value=18)
    private Integer labelLevel;


    /**
     * 标签代码
     */
    @ExcelProperty("*标签代码值")
    @Size(max=20,message = "【标签代码】长度不能超过20")
    private String labelCode="";

    /**
     * 标签级别中文名
     */
    @ExcelIgnore
    private String labelLevelStr="";


    /**
     * 标签所属分类代码
     */
    @Size(max=10,message = "【常用组织分类代码】长度不能超过10")
    @ExcelProperty("*常用组织分类代码")
    @NotBlank(message = "【常用组织分类代码】不能为空")
    @ColumnWidth(value=20)
    @ContentStyle(quotePrefix = BooleanEnum.TRUE)
    private String classId="";

    /**
     * 标签所属分类中文名
     */
    @ExcelIgnore
    private String classIdStr="";

    /**
     * 标签说明
     */
    @ExcelProperty("标签说明")
    @Size(max=500,message = "【标签说明】长度不能超过500")
    private String labelDescribe="";



    /**
     * 修订人
     */
    @ExcelProperty("*修订人")
    @NotBlank(message = "【修订人】不能为空")
    @Size(max=100,message = "【修订人】长度不能超过100")
    private String author="";

    @ExcelProperty(index = 6,value="  ")
    @HeadStyle(fillForegroundColor=1,fillBackgroundColor = 1)
    private String memo;

}
