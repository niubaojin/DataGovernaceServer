package com.synway.datastandardmanager.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.Data;

/**
 * 数据集管理表格数据
 *
 * @author wangdongwei
 * @date 2020/11/27 15:31
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints = 10, bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@Data
public class DataSetTableInfoVO {

    @ExcelProperty("序号")
    private String recno = "";            //序号

    @ExcelProperty("应用系统")
    @ColumnWidth(value = 18)
    private String dataSourceCh = "";      //应用系统中文名

    @ExcelIgnore
    private String dataSourceCode = "";    //应用系统代码值

    @ExcelProperty("中文名称")              //中文名称
    @ColumnWidth(value = 18)
    private String objectName = "";

    @ExcelProperty("真实表名")              //真实表名
    @ColumnWidth(value = 18)
    private String tableName = "";

    @ExcelProperty("资源标识")
    @ColumnWidth(value = 18)
    private String tableId = " ";           //资源标识
    @ExcelIgnore
    private String objectId = "";

    @ExcelProperty("数据组织分类")
    @ColumnWidth(value = 20)
    private String dataOrganizationClassify;//数据组织分类   原始库->公安执法与执勤数据

    @ExcelProperty("数据来源分类")
    @ColumnWidth(value = 20)
    private String dataSourceClassify;      //数据来源分类   公安执法与执勤数据->国内安全保卫

    @ExcelIgnore
    private String objectState = "-1";      //数据状态代码值

    @ExcelProperty("数据状态")
    @ColumnWidth(value = 13)
    private String objectStateStr = "";     //数据状态

    @ExcelProperty("已建物理表")
    @ColumnWidth(value = 13)
    private long createdTables;             //已建物理表

    @ExcelProperty("创建人")
    @ColumnWidth(value = 13)
    private String creator = "";            //创建人

    @ExcelProperty("创建时间")
    @ColumnWidth(value = 20)
    private String createTime = "";         //创建时间

    @ExcelProperty("最后修改人")
    @ColumnWidth(value = 13)
    private String updater = "";            //最后修改人

    @ExcelProperty("最后更新时间")
    @ColumnWidth(value = 20)
    private String updateTime = "";         //最后更新时间

    @ExcelIgnore
    private long knowledgeConfigCount;      //数据质量知识库的数据量

    @ExcelIgnore
    private long processRuleCount;          //数据处理规则的数据量

    @ExcelIgnore
    private String SJZYBQ1;                 //数据资源标签1

    @ExcelIgnore
    private String SJZYBQ2;                 //数据资源标签2

    @ExcelIgnore
    private String SJZYBQ3;                 //数据资源标签3

    @ExcelIgnore
    private String SJZYBQ4;                 //数据资源标签4

    @ExcelIgnore
    private String SJZYBQ5;                 //数据资源标签5

}
