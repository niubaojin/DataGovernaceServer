package com.synway.datastandardmanager.entity.vo.importDownload;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * 标准表信息
 *
 * @author obito
 * @description 这个是 excel里面 标准表的 sheet页面存储的信息
 * @date 2022/04/02
 */
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints = 9, bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 9)
@Data
public class ObjectTableSheetVO {

    /**
     * 标准id
     */
    @ExcelProperty(index = 0, value = "OBJECTID*")
    private Integer objectId;

    /**
     * 表英文名
     */
    @ExcelProperty(index = 1, value = "推荐表名1（通用）*")
    private String tableName;

    /**
     * hive下的存储的表名
     */
    @ExcelProperty(index = 2, value = "推荐表名2(原HIVE中表名)*")
    private String hiveTableName;

    /**
     * 标准协议id
     */
    @ExcelProperty(index = 3, value = "行为日志库ID*")
    private String tableId;

    /**
     * 来源协议id
     */
    @ExcelProperty(index = 4, value = "源数据表ID*")
    private String sourceId;

    /**
     * 标准中文名
     */
    @ExcelProperty(index = 5, value = "资源中文名称*")
    private String objectName;

    /**
     * 表数据来源系统
     */
    @ExcelProperty(index = 6, value = "表数据来源的系统")
    private String dataSource;

    /**
     * 数据来源1级
     */
    @ExcelProperty(index = 7, value = "数据来源分类一级")
    private String sjzylylxValue;

    /**
     * 数据来源2级
     */
    @ExcelProperty(index = 8, value = "数据来源分类二级")
    private String sjzylylxejValue;

    /**
     * 数据分级
     */
    @ExcelProperty(index = 9, value = "数据级别")
    private String dataLevel;

    /**
     * 数据组织1级
     */
    @ExcelProperty(index = 10, value = "数据组织一级分类")
    private String sjzzyjflValue;

    /**
     * 数据组织2级
     */
    @ExcelProperty(index = 11, value = "数据组织二级分类")
    private String sjzzejflValue;

    /**
     * 资源标签1
     */
    @ExcelProperty(index = 12, value = "数据资源标签1")
    private String sjzybq1;

    /**
     * 资源标签2
     */
    @ExcelProperty(index = 13, value = "数据资源标签2")
    private String sjzybq2;

    /**
     * 资源标签3
     */
    @ExcelProperty(index = 14, value = "数据资源标签3")
    private String sjzybq3;

    /**
     * 资源标签4
     */
    @ExcelProperty(index = 15, value = "数据资源标签4")
    private String sjzybq4;

    /**
     * 小版本yyyyMMdd
     * (2022年11月29日14:21:23 version换成createTime)
     */
    @ExcelProperty(index = 16, value = "启用时间*")
    private String createTime;

    /**
     * 是否是实时表 0 1
     */
    @ExcelProperty(index = 17, value = "是否实时表")
    private Integer isActiveTable;

    /**
     * 标准来源类型
     */
    @ExcelProperty(index = 18, value = "标准来源类型")
    private Integer standardType;

    /**
     * 字段映射表
     */
    @ExcelProperty(index = 19, value = "字段映射表")
    private String relateTableName = "objectfield";

    /**
     * 备注
     */
    @ExcelProperty(index = 20, value = "备注")
    private String objectMemo;

    /**
     * 数据协议代码
     */
    @ExcelProperty(index = 21, value = "portotal协议代码")
    private String ellementName;

    /**
     * 使用状态 1 0 -1
     */
    @ExcelProperty(index = 22, value = "使用状态*")
    private Integer objectState;

    /**
     * 创建人
     */
    @ExcelProperty(index = 23, value = "定义创建人")
    private String creator;

    /**
     * 修改人
     */
    @ExcelProperty(index = 24, value = "最新修改人")
    private String updater;

    @ExcelIgnore
    private List<TableColumnSheetVO> objectField;

}
