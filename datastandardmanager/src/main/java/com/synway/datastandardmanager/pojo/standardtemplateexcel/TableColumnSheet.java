package com.synway.datastandardmanager.pojo.standardtemplateexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * @author wangdongwei
 * @ClassName TableColumnSheet
 * @description 这个是 excel里面 表字段的 sheet页面存储的信息
 * 表字段
 * @date 2020/12/8 15:51
 */
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints = 9, bold = false)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 9)
@Data
public class TableColumnSheet {

    /**
     * 标准表id
     */
    @ExcelIgnore
    private Long objectId;

    /**
     * 字段名
     */
    @ExcelProperty(index = 0,value = "字段名")
    private String fieldName;

    /**
     * 元素编码
     */
    @ExcelProperty(index = 1,value = "元素编码")
    private String fieldId;

    /**
     * 数据元内部标识符
     */
    @ExcelProperty(index = 2,value = "映射部标内部标识符")
    private String gadsjFieldId;

    /**
     * 限定词id
     */
    @ExcelProperty(index = 3,value = "映射部标数据元限定词")
    private String determinerId;

    /**
     * 字段映射表
     */
    @ExcelProperty(index = 4,value = "字段类型")
    private String fieldType;

    /**
     * 字段映射表
     */
    @ExcelIgnore
    private String needValue;

    /**
     * 必填中文
     */
    @ExcelProperty(index = 5,value = "必填")
    private String needValueCh;

    /**
     * 字段映射表
     */
    @ExcelIgnore
    private String isIndex;

    /**
     * 索引中文
     */
    @ExcelProperty(index = 6,value = "索引")
    private String isIndexCh;

    /**
     * 字段映射表
     */
    @ExcelProperty(index = 7,value = "序列")
    private Integer recno;

    /**
     * 字段映射表
     */
    @ExcelProperty(index = 8,value = "中文名称")
    private String fieldChineseName;

    /**
     * 字段分类
     */
    @ExcelProperty(index = 9,value = "字段分类")
    private String fieldClassification;

    /**
     * 安全级别
     */
    @ExcelProperty(index = 10,value = "安全级别")
    private String securityLevel;

    /**
     * 字段映射表
     */
    @ExcelIgnore
    private String isContorl;

    /**
     * 是否可以布控中文
     */
    @ExcelProperty(index = 11,value = "是否可以布控")
    private String isContorlCh;

    /**
     * 字段映射表
     */
    @ExcelProperty(index = 12,value = "备注")
    private String memo;

    /**
     * 字段映射表
     */
    @ExcelIgnore
    private String isPrivate;

    /**
     * 私有协议中文
     */
    @ExcelProperty(index = 13,value = "私有协议")
    private String isPrivateCh;

    /**
     * 字段映射表
     */
    @ExcelProperty(index = 14,value = "是否MD5")
    private Integer md5Index;

    /**
     * 主键
     */
    @ExcelIgnore
    private Integer pkRecno;

    /**
     * 分区列
     */
    @ExcelProperty("分区列")
    private Integer partitionRecno;

    /**
     * 聚集列
     */
    @ExcelProperty("聚集列")
    private Integer clustRecno;

    /**
     * 标准中的索引
     */
    @ExcelProperty(index = 15,value = "标准中的索引")
    private String tableIndex;

    /**
     * 添加时间
     */
    @ExcelProperty(index = 16,value = "添加时间")
    private Integer version;

    /**
     * 标准中的源名
     */
    @ExcelProperty(index = 17,value = "标准中的源名")
    private String originalColumn;

    /**
     * 标准中的顺序
     */
    @ExcelProperty(index = 18,value = "标准中的顺序")
    private Integer standardRecno;

    /**
     * 显示
     */
    @ExcelProperty(index = 19,value = "显示")
    private String columnNameState;

    /**
     * 近线显示
     */
    private Integer oraShow;

    /**
     * 近线显示中文
     */
    @ExcelProperty(index = 20,value = "近线显示")
    private String oraShowCh;

}
