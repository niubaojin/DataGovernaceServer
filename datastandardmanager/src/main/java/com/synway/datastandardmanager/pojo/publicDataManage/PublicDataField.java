package com.synway.datastandardmanager.pojo.publicDataManage;

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

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName PublicDataField
 * @description  公共数据项管理pojo
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
public class PublicDataField implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 426549979798313757L;

    //公共数据项分组id
    @ExcelIgnore
    private String id;

    //公共数据项分组
    @NotNull
    @ExcelIgnore
    private String groupId;

    //数据项序号值
    @NotNull
    @ExcelProperty("*序号")
    private Integer recno;

    //数据项中文名称
    @NotNull
    @ExcelProperty("*数据项中文名")
    private String fieldChineseName;

    //数据项唯一编码
    @NotNull
    @ExcelProperty("*数据项唯一编码")
    private String fieldId;

    //数据元
    @ExcelIgnore
    private String gadsjFieldId;

    //数据项英文名
    @NotNull
    @ExcelProperty("*数据项英文名")
    private String fieldName;

    //字段名称(与fieldName值相同)
    @ExcelIgnore
    private String columnName;

    //限定词id
    @ExcelProperty("限定词代码值")
    private String determinerId;

    //限定词中文名称
    @ExcelIgnore
    private String determinerName;

    //是否可查询
    @ExcelIgnore
    private Integer isQuery;

    //是否可查询中文
    @ExcelProperty("是否可查询")
    private String isQueryStr;

    //是否可布控
    @ExcelIgnore
    private Integer isContorl;

    //是否可布控中文
    @ExcelProperty("是否可布控")
    private String isContorlStr;

    //是否必填项
    @ExcelIgnore
    private Integer needValue;

    //是否必填项中文
    @ExcelProperty("是否必填")
    private String needValueStr;

    @ExcelProperty("是否参与md5运算")
    private String md5IndexStr;

    //是否参与md5运算
    @ExcelIgnore
    private Integer md5Index;

    //用于页面的字段
    @ExcelIgnore
    private Boolean md5IndexStatus;

    //数据项说明
    @ExcelProperty("数据项说明")
    private String memo;

    //表头说明
    @ExcelProperty("表头说明")
    private String headMemo;

    //创建时间
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //更新状态 1:新增 2:修改
    @NotNull
    @ExcelIgnore
    private Integer updateStatus;

    //字段类型
    @ExcelIgnore
    private String fieldType;

    //字段长度
    @ExcelIgnore
    private String fieldLen;

    //用于数据元回限
    @ExcelIgnore
    private String label;
    @ExcelIgnore
    private String gadsjFieldMemo;


}
