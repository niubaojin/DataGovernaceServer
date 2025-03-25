package com.synway.datarelation.pojo.source_table_query;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.FillPatternType;

import javax.validation.constraints.NotNull;

/**
 * @author wangdongwei
 * @ClassName
 * @description 应用系统单独的需求
 * @date 2021/2/20 17:15
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
public class ApplicationSystemSource {
    /**
     *   应用系统
     */
    @NotNull(message = "应用系统不能为空")
    @ExcelProperty("应用系统名称")
    @ColumnWidth(value=18)
    private String applicationSystem;
    /**
     * 一级模块
     */
    @NotNull(message = "一级模块不能为空")
    @ExcelProperty("一级模块")
    @ColumnWidth(value=18)
    private String oneLevelModule;
    /**
     * 二级模块
     */
    @ExcelProperty("二级模块")
    @ColumnWidth(value=18)
    private String twoLevelModule="";
    /**
     * 三级模块
     */
    @ExcelProperty("三级模块")
    @ColumnWidth(value=18)
    private String threeLevelModule="";
    /**
     * 物理表名
     */
    @NotNull(message = "物理表名不能为空")
    @ExcelProperty("物理表名")
    @ColumnWidth(value=18)
    private String tableNameEn;
    /**
     * 中文表名
     */
    @ExcelProperty("中文表名")
    @ColumnWidth(value=18)
    private String tableNameCh="";
    /**
     * 资产编码
     */
    @ExcelProperty("资产编码")
    @ColumnWidth(value=18)
    private String tableId="";

    /**
     * 流水源表资源编码
     */
    @ExcelProperty("流水源表资源编码")
    @ColumnWidth(value=20)
    private String sourceId="";

    /**
     * 流水源表英文表名
     */
    @ExcelProperty("流水源表英文表名")
    @ColumnWidth(value=20)
    private String sourceTableNameEn="";

    /**
     * 流水源表中文表名
     */
    @ExcelProperty("流水源表中文表名")
    @ColumnWidth(value=20)
    private String sourceTableNameCh="";


    public ApplicationSystemSource(){

    }
    public ApplicationSystemSource(String applicationSystem,
                                  String oneLevelModule,
                                  String twoLevelModule,
                                  String threeLevelModule,
                                  String tableNameEn,
                                  String tableNameCh,
                                  String tableId){
        this.applicationSystem = applicationSystem;
        this.oneLevelModule = oneLevelModule;
        this.twoLevelModule = twoLevelModule;
        this.threeLevelModule = threeLevelModule;
        this.tableNameEn = tableNameEn;
        this.tableNameCh = tableNameCh;
        this.tableId = tableId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceTableNameEn() {
        return sourceTableNameEn;
    }

    public void setSourceTableNameEn(String sourceTableNameEn) {
        this.sourceTableNameEn = sourceTableNameEn;
    }

    public String getSourceTableNameCh() {
        return sourceTableNameCh;
    }

    public void setSourceTableNameCh(String sourceTableNameCh) {
        this.sourceTableNameCh = sourceTableNameCh;
    }

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
