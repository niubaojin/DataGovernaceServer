package com.synway.datarelation.pojo.databloodline;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.FillPatternType;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  页面展示应用血缘的表格形式
 * @author wangdongwei
 * @ClassName ApplicationSystemTable
 * @description TODO
 * @date 2020/12/17 10:53
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = false)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
public class ApplicationSystemTable implements Serializable {
    /**
     *   应用系统
     */
    @NotNull(message = "应用系统不能为空")
    @ExcelProperty("应用系统名称")
    @ColumnWidth(value=18)
    @Max(800)
    private String applicationSystem;
    /**
     * 一级模块
     */
    @NotNull(message = "一级模块不能为空")
    @ExcelProperty("一级模块")
    @ColumnWidth(value=18)
    @Max(800)
    private String oneLevelModule;
    /**
     * 二级模块
     */
    @ExcelProperty("二级模块")
    @ColumnWidth(value=18)
    @Max(800)
    private String twoLevelModule="";
    /**
     * 三级模块
     */
    @ExcelProperty("三级模块")
    @ColumnWidth(value=18)
    @Max(800)
    private String threeLevelModule="";
    /**
     * 物理表名
     */
    @NotNull(message = "物理表名不能为空")
    @ExcelProperty("物理表名")
    @ColumnWidth(value=18)
    @Max(50)
    private String tableNameEn;
    /**
     * 中文表名
     */
    @ExcelProperty("中文表名")
    @ColumnWidth(value=18)
    @Max(800)
    private String tableNameCh="";
    /**
     * 资产编码
     */
    @ExcelProperty("资产编码")
    @ColumnWidth(value=18)
    private String tableId="";

    public String getApplicationSystem() {
        return applicationSystem;
    }

    public void setApplicationSystem(String applicationSystem) {
        this.applicationSystem = applicationSystem;
    }

    public String getOneLevelModule() {
        return oneLevelModule;
    }

    public void setOneLevelModule(String oneLevelModule) {
        this.oneLevelModule = oneLevelModule;
    }

    public String getTwoLevelModule() {
        return twoLevelModule;
    }

    public void setTwoLevelModule(String twoLevelModule) {
        this.twoLevelModule = twoLevelModule;
    }

    public String getThreeLevelModule() {
        return threeLevelModule;
    }

    public void setThreeLevelModule(String threeLevelModule) {
        this.threeLevelModule = threeLevelModule;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
