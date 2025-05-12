package com.synway.datastandardmanager.pojo.summaryobjectpage;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;

/**
 * @author wangdongwei
 * @ClassName SummaryObjectTable
 * @description 汇总页面上的表格
 * @date 2020/11/27 15:31
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
public class SummaryObjectTable {
    /**
     * 序号
     */
    @ExcelProperty("序号")
    private String recno="";
    /**
     * 应用系统中文名
     */
    @ExcelProperty("应用系统")
    @ColumnWidth(value=18)
    private String dataSourceCh="";
    /**
     * 应用系统代码值
     */
    @ExcelIgnore
    private String dataSourceCode="";
    /**
     * 中文名称
     */
    @ExcelProperty("中文名称")
    @ColumnWidth(value=18)
    private String objectName="";
    /**
     * 真实表名
     */
    @ExcelProperty("真实表名")
    @ColumnWidth(value=18)
    private String tableName="";
    /**
     * 资源标识
     */
    @ExcelProperty("资源标识")
    @ColumnWidth(value=18)
    private String tableId=" ";
    @ExcelIgnore
    private String objectId="";
    /**
     * 数据组织分类   原始库->公安执法与执勤数据
     *
     */
    @ExcelProperty("数据组织分类")
    @ColumnWidth(value=20)
    private String dataOrganizationClassify;
    /**
     *数据来源分类   公安执法与执勤数据->国内安全保卫
     */
    @ExcelProperty("数据来源分类")
    @ColumnWidth(value=20)
    private String dataSourceClassify;

    /**
     * 数据状态代码值
     */
    @ExcelIgnore
    private String objectState="";
    /**
     * 数据状态
     */
    @ExcelProperty("数据状态")
    @ColumnWidth(value=13)
    private String objectStateStr="";
    /**
     * 已建物理表
     */
    @ExcelProperty("已建物理表")
    @ColumnWidth(value=13)
    private long createdTables;
    /**
     * 创建人
     */
    @ExcelProperty("创建人")
    @ColumnWidth(value=13)
    private String creator="";
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @ColumnWidth(value=20)
    private String createTime="";
    /**
     * 最后修改人
     */
    @ExcelProperty("最后修改人")
    @ColumnWidth(value=13)
    private String updater="";
    /**
     * 最后更新时间
     */
    @ExcelProperty("最后更新时间")
    @ColumnWidth(value=20)
    private String updateTime="";


    /**
     * 数据质量知识库的数据量
     */
    @ExcelIgnore
    private long knowledgeConfigCount;


    /**
     * 数据处理规则的数据量
     */
    @ExcelIgnore
    private long processRuleCount;

    /**
     * 数据资源标签1
     */
    @ExcelIgnore
    private String SJZYBQ1;

    /**
     * 数据资源标签2
     */
    @ExcelIgnore
    private String SJZYBQ2;
    /**
     * 数据资源标签3
     */
    @ExcelIgnore
    private String SJZYBQ3;
    /**
     * 数据资源标签4
     */
    @ExcelIgnore
    private String SJZYBQ4;
    /**
     * 数据资源标签5
     */
    @ExcelIgnore
    private String SJZYBQ5;

    public String getSJZYBQ1() {
        return SJZYBQ1;
    }

    public void setSJZYBQ1(String SJZYBQ1) {
        this.SJZYBQ1 = SJZYBQ1;
    }

    public String getSJZYBQ2() {
        return SJZYBQ2;
    }

    public void setSJZYBQ2(String SJZYBQ2) {
        this.SJZYBQ2 = SJZYBQ2;
    }

    public String getSJZYBQ3() {
        return SJZYBQ3;
    }

    public void setSJZYBQ3(String SJZYBQ3) {
        this.SJZYBQ3 = SJZYBQ3;
    }

    public String getSJZYBQ4() {
        return SJZYBQ4;
    }

    public void setSJZYBQ4(String SJZYBQ4) {
        this.SJZYBQ4 = SJZYBQ4;
    }

    public String getSJZYBQ5() {
        return SJZYBQ5;
    }

    public void setSJZYBQ5(String SJZYBQ5) {
        this.SJZYBQ5 = SJZYBQ5;
    }

    public long getKnowledgeConfigCount() {
        return knowledgeConfigCount;
    }

    public void setKnowledgeConfigCount(long knowledgeConfigCount) {
        this.knowledgeConfigCount = knowledgeConfigCount;
    }

    public long getProcessRuleCount() {
        return processRuleCount;
    }

    public void setProcessRuleCount(long processRuleCount) {
        this.processRuleCount = processRuleCount;
    }

    public String getDataOrganizationClassify() {
        return dataOrganizationClassify;
    }

    public void setDataOrganizationClassify(String dataOrganizationClassify) {
        this.dataOrganizationClassify = dataOrganizationClassify;
    }

    public String getDataSourceClassify() {
        return dataSourceClassify;
    }

    public void setDataSourceClassify(String dataSourceClassify) {
        this.dataSourceClassify = dataSourceClassify;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }

    public String getObjectStateStr() {
        return objectStateStr;
    }

    public void setObjectStateStr(String objectStateStr) {
        this.objectStateStr = objectStateStr;
    }

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno;
    }

    public String getDataSourceCh() {
        return dataSourceCh;
    }

    public void setDataSourceCh(String dataSourceCh) {
        this.dataSourceCh = dataSourceCh;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }



    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getCreatedTables() {
        return createdTables;
    }

    public void setCreatedTables(long createdTables) {
        this.createdTables = createdTables;
    }
}
