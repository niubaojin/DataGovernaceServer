package com.synway.datastandardmanager.pojo.synlteelement;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据要素
 * @author obito
 * @version 1.0
 * @date 2021/08/05
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
public class SynlteElement implements Serializable {

    @ExcelIgnore
    private int num;
    @ExcelIgnore
    private static final long serialVersionUID = 4245646487351319972L;

    /**
     * 要素标识符
     */
    @NotNull(message = "【主键ID】不能为空")
    @Size(max=50,message = "【主键ID】长度不能超过50")
    @ExcelProperty("*要素标识符")
    private String elementCode;

    /**
     * 要素名称
     */
    @NotNull(message = "【要素名称】不能为空")
    @Size(max=200,message = "【要素名称】长度不能超过200")
    @ExcelProperty("*要素名称")
    private String elementChname;



    @ExcelProperty("要素来源")
    private String isElementType;

    /**
     * 是否内部要素标识符
     * 1.是(公司内部定义) 2.否(客户现场定义)
     */
    @NotNull(message = "【是否内标标识符】不能为空")
    @Size(max=4,message = "【是否内标标识符】长度不能超过4")
    @ExcelProperty("*要素来源代码")
    private String isElement;

    /**
     * 语义
     */
    @ExcelProperty("语义")
    @Size(max=50,message = "【语义】长度不能超过50")
    private String sameId="";

    /**
     * 主体信息翻译
     */

    @ExcelProperty("要素主体")
    private String elementObjectType;

    /**
     * 关联主体
     * 1.人员 2.物 3.组织 4.地 5.事 6.时间 7.信息
     */
    @ListValue(vals = {"1","2","3","4","5","6","7"},
            message = "[主体]的值只能为1/2/3/4/5/6/7")
    @Size(max=50,message = "【关联主体】长度不能超过50")
    @NotNull
    @ExcelProperty("*要素主体代码")
    private String elementObject;

    /**
     * 规则
     */
    @ExcelProperty("要素描述")
    private String elementRule;



    @ExcelProperty("生成方式")
    private String createModeType;

    /**
     * 生成方式
     * 1：数据元编码
     * 2：数据源编码+Z字典码值
     */
    @ExcelProperty("*生成方式代码值")
    private Integer createMode;

    /**
     * 修订人
     */
    @ExcelProperty("*修订人")
    @Size(max=50,message = "【修订人】长度不能超过50")
    private String author;

    /**
     * 修订时间 采用YYYYMMDD HH:mm:ss的格式
     */
    @ExcelProperty("修订时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date modDate;

    /**
     * 数据元的fieldId
     */
    @ExcelIgnore
    private String dataElementId="";

    @ExcelProperty(index = 12,value="  ")
    @HeadStyle(fillForegroundColor=1,fillBackgroundColor = 1)
    private String memo;

}
