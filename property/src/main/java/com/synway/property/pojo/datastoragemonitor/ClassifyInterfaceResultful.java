package com.synway.property.pojo.datastoragemonitor;

import java.io.Serializable;

/**
 * resultful获取到的分级分类翻译后的数据
 * @author 数据接入
 */
public class ClassifyInterfaceResultful  implements Serializable {
    /**
     * 表英文名
     */
    private String tableNameEn;
    /**
     * 表中文名
     */
    private String tableNameCh;
    /**
     * sourceid
     */
    private String sourceId;
    /**
     * 数据来源一级分类中文名
     */
    private String primaryDatasourceCh;
    /**
     * 数据来源二级分类中文名
     */
    private String secondaryDatasourceCh;
    /**
     * 数据组织一级分类中文名
     */
    private String primaryOrganizationCh;
    /**
     * 数据组织二级分类中文名
     */
    private String secondaryOrganizationCh;
    /**
     * 数据资源要素一级分类中文名
     */
    private String factorOneName;
    /**
     * 数据资源要素二级分类中文名
     */
    private String factorTwoName;
    /**
     * 数据组织三级分类中文名(弃用)
     */
    private String threeOrganizationCh;
    /**
     * 6类标签的code值 多个值用英文逗号分隔
     */
//    private String LABELS;
//
//
//    public String getLABELS() {
//        return LABELS;
//    }
//
//    public void setLABELS(String LABELS) {
//        this.LABELS = LABELS;
//    }

    public String getThreeOrganizationCh() {
        return threeOrganizationCh;
    }

    public void setThreeOrganizationCh(String threeOrganizationCh) {
        this.threeOrganizationCh = threeOrganizationCh;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPrimaryDatasourceCh() {
        return primaryDatasourceCh;
    }

    public void setPrimaryDatasourceCh(String primaryDatasourceCh) {
        this.primaryDatasourceCh = primaryDatasourceCh;
    }

    public String getSecondaryDatasourceCh() {
        return secondaryDatasourceCh;
    }

    public void setSecondaryDatasourceCh(String secondaryDatasourceCh) {
        this.secondaryDatasourceCh = secondaryDatasourceCh;
    }

    public String getPrimaryOrganizationCh() {
        return primaryOrganizationCh;
    }

    public void setPrimaryOrganizationCh(String primaryOrganizationCh) {
        this.primaryOrganizationCh = primaryOrganizationCh;
    }

    public String getSecondaryOrganizationCh() {
        return secondaryOrganizationCh;
    }

    public void setSecondaryOrganizationCh(String secondaryOrganizationCh) {
        this.secondaryOrganizationCh = secondaryOrganizationCh;
    }

    public String getFactorOneName() {
        return factorOneName;
    }

    public void setFactorOneName(String factorOneName) {
        this.factorOneName = factorOneName;
    }

    public String getFactorTwoName() {
        return factorTwoName;
    }

    public void setFactorTwoName(String factorTwoName) {
        this.factorTwoName = factorTwoName;
    }
}
