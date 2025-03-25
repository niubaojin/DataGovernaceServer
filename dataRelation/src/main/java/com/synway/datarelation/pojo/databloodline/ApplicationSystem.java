package com.synway.datarelation.pojo.databloodline;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.io.Serializable;
import java.util.List;

/**
 *  应用系统的血缘关系
 *  20210318 模板中去除掉子模块名称 使用 一级/二级/三级模块
 * @author wangdongwei
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
public class ApplicationSystem implements Serializable{
    /**
     * 应用系统名称
      */
    @ExcelProperty("应用系统名称")
    @ColumnWidth(value=18)
    @HeadFontStyle(color = 10,fontHeightInPoints =  10,bold = false)
    private String applicationName = "";

    @ExcelProperty("一级模块")
    @ColumnWidth(value=18)
    @HeadFontStyle(color = 10,fontHeightInPoints =  10,bold = false)
    private String oneModuleName = "";

    @ExcelProperty("二级模块")
    @ColumnWidth(value=18)
    private String twoModuleName = "";

    @ExcelProperty("三级模块")
    @ColumnWidth(value=18)
    private String threeModuleName = "";

    /**
     *数据库类型
     */
    @ExcelProperty("数据库类型")
    @ColumnWidth(value=18)
    private String dataBaseType = "";
    /**
     * 数据库或账户
     */
    @ExcelProperty("数据库或账户")
    @ColumnWidth(value=18)
    private String project = "";
    /**
     * 表名
     */
    @ExcelProperty("表名")
    @ColumnWidth(value=18)
    @HeadFontStyle(color = 10,fontHeightInPoints =  10,bold = false)
    private String tableNameEn = "";


    /**
     * 添加表中文名 信息
     */
    @ExcelProperty("表中文名")
    @ColumnWidth(value=18)
    @HeadFontStyle(color = 10,fontHeightInPoints =  10,bold = false)
    private String tableNameCh = "";

    /**
     * 子模块名称
     */
    @ExcelIgnore
    private String subModuleName = "";

    /**
     *     子模块在使用时要切成多个，用string[]存储，当子模块为空时不存储
     */
    @ExcelIgnore
    private List<String> subModuleNameLists;

    public String getOneModuleName() {
        return oneModuleName;
    }

    public void setOneModuleName(String oneModuleName) {
        this.oneModuleName = oneModuleName;
    }

    public String getTwoModuleName() {
        return twoModuleName;
    }

    public void setTwoModuleName(String twoModuleName) {
        this.twoModuleName = twoModuleName;
    }

    public String getThreeModuleName() {
        return threeModuleName;
    }

    public void setThreeModuleName(String threeModuleName) {
        this.threeModuleName = threeModuleName;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public List<String> getSubModuleNameLists() {
        return subModuleNameLists;
    }

    public void setSubModuleNameLists(List<String> subModuleNameLists) {
        this.subModuleNameLists = subModuleNameLists;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }
}
