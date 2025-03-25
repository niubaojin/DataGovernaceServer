package com.synway.datastandardmanager.pojo.originalDictionary;


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
import java.io.Serializable;
import java.util.Date;

/**
 * @author obito
 * @ClassName originalDictionaryFieldPojo
 * @description 外部字典管理中字典代码管理pojo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(15)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = false)
//内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
public class OriginalDictionaryFieldPojo implements Serializable {
    @ExcelIgnore
    private static final long serialVersionUID = 568746146142342L;

    /**
     * 原始字典表id
     */
    @ExcelIgnore
    private String groupId;

    /**
     * 字典代码项id
     */
    @ExcelIgnore
    private String id;

    /**
     * 字段序号值
     */
    @ExcelProperty("序号")
    private Integer recno;

    /**
     * 代码名称
     */
    @ExcelProperty("代码名称")
    private String codeValText;

    /**
     * 代码编码
     */
    @ExcelProperty("代码编码")
    private String codeValValue;

    /**
     * 对应标准代码名称
     */
    @ExcelProperty("对应标准代码名称")
    private String standardCodeValText;

    /**
     * 对应标准代码编码
     */
    @ExcelProperty("对应标准代码编码")
    private String standardCodeValValue;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date updateTime;
}
