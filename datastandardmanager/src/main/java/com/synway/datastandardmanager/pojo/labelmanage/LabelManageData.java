package com.synway.datastandardmanager.pojo.labelmanage;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.usermodel.FillPatternType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 表格中的数据对象
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 19:14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = false)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
public class LabelManageData implements Serializable {
    private static final long serialVersionUID = -7874329041084711973L;

    /**
     * 唯一值
     */
    @ExcelIgnore
    private String id;
    /**
     * 标签名称
     */
    @ExcelProperty("标签名称")
    @NotBlank(message = "【标签名称】不能为空")
    @Size(max=50,message = "【标签名称】长度不能超过50")
    private String labelName="";


    /**
     * 标签代码
     */
    @ExcelProperty("标签代码")
    @Size(max=20,message = "【标签代码】长度不能超过20")
    private String labelCode="";

    /**
     * 标签级别中文名
     */
    @ExcelProperty("标签类型")
    private String labelLevelStr="";
    /**
     * 标签级别 代码
     */
    @NotNull(message = "【标签类型代码】不能为空")
    @ExcelProperty("标签类型代码")
    @ColumnWidth(value=18)
    private Integer labelLevel;


    /**
     * 标签所属分类代码
     */
//    @Size(min = 2, max=10,message = "【常用组织分类代码】长度不能超过10")
//    @ExcelProperty("常用组织分类代码")
    @ExcelIgnore
    @ContentStyle(quotePrefix = true)
    private String classId="";

    /**
     * 标签所属分类中文名
     */
    @ExcelProperty("常用组织分类")
    @ColumnWidth(value=18)
    private String classIdStr="";

    /**
     * 标签说明
     */
    @ExcelProperty("标签说明")
    @Size(max=500,message = "【标签说明】长度不能超过500")
    private String labelDescribe="";

    /**
     * 关联的表数据量
     */
    @ExcelProperty("关联数据")
    private Integer tableCount;

    /**
     * 修订人
     */
    @ExcelProperty("修订人")
    @NotBlank(message = "【修订人】不能为空")
    @Size(max=100,message = "【修订人】长度不能超过100")
    private String author="";

    /**
     * 修订时间
     */
    @ExcelIgnore
    private Date modDate;


    @ExcelProperty("修改时间")
    private String modDateStr="";

    /**
     * 更新标志：add表示新增，update表示编辑
     */
    @ExcelIgnore
    private String addUpdateTag;


}
