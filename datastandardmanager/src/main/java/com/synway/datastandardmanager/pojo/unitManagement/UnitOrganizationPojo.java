package com.synway.datastandardmanager.pojo.unitManagement;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName UnitOrganizationPojo
 * @description  单位机构pojo
 * @author obito
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
public class UnitOrganizationPojo implements Serializable {
    private static final long serialVersionUID = 425659529341611233L;

    /**
     * 序号
     */
    @ExcelProperty(index = 0,value="序号")
    private Integer recno;

    /**
     * 机构编码
     */
    @NotBlank
    @ExcelProperty(index = 2,value="机构代码")
    private String unitCode;

    /**
     * 机构名称
     */
    @ExcelProperty(index = 1,value="机构名称")
    private String unitName;

    /**
     * 机构类型 1：政府机构 2:非政府机构
     */
    @ExcelIgnore
    private Integer unitType;

    /**
     * 机构类型中文
     */
    @ExcelProperty(index = 3,value="机构类型")
    private String unitTypeCh;

    /**
     * 机构级别
     */
    @ExcelProperty(index = 4,value="机构级别")
    private Integer unitLevel;

    /**
     * 所属地区
     */
    @ExcelProperty(index = 5,value="所属地区行政区划")
    private String unitArea;

    /**
     * 所属地区id
     */
    @ExcelIgnore
    private String unitAreaId;

    /**
     * 父级机构编码
     */
    @ExcelIgnore
    private String parentUnitCode;

    /**
     * 父级机构名称
     */
    @ExcelProperty(index = 6,value="上级机构名称")
    private String parentUnitName;

    /**
     * 机构信息描述
     */
    @ExcelProperty(index = 7,value="机构描述信息")
    private String memo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date updateTime;

}
