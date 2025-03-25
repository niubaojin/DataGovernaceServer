package com.synway.datastandardmanager.pojo.summaryobjectpage;

import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName SummaryTable
 * @description TODO
 * @date 2020/12/14 16:49
 */
public class SummaryTable {
    /**
     * 总共有多少张表
     */
    private int tableCount = 0;
    /**
     * 表格中的数据
     */
    private List<SummaryObjectTable> summaryObjectTable;
//    /**
//     * 资源状况  启用   停用
//     */
//    private List<PageSelectOneValue> usingTags;
    /**
     *  应用系统的筛选
     */
    private List<FilterObject>  dataSourceFilter;
    /**
     * 数据组织分类的筛选
     */
    private List<FilterObject> dataOrganizationClassifyFilter;
    /**
     * 数据来源分类的筛选
     */
    private List<FilterObject> dataSourceClassifyFilter;
    /**
     * 数据状态的筛选
     */
    private List<FilterObject>  objectStatesFilter;
    /**
     * 创建人的筛选
     */
    private List<FilterObject>  creatorFilter;
    /**
     * 最后修改人的筛选
     */
    private List<FilterObject>  updaterFilter;



    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public List<SummaryObjectTable> getSummaryObjectTable() {
        return summaryObjectTable;
    }

    public void setSummaryObjectTable(List<SummaryObjectTable> summaryObjectTable) {
        this.summaryObjectTable = summaryObjectTable;
    }

    //    public List<PageSelectOneValue> getUsingTags() {
//        return usingTags;
//    }
//
//    public void setUsingTags(List<PageSelectOneValue> usingTags) {
//        this.usingTags = usingTags;
//    }

    public List<FilterObject> getDataSourceFilter() {
        return dataSourceFilter;
    }

    public void setDataSourceFilter(List<FilterObject> dataSourceFilter) {
        this.dataSourceFilter = dataSourceFilter;
    }

    public List<FilterObject> getDataOrganizationClassifyFilter() {
        return dataOrganizationClassifyFilter;
    }

    public void setDataOrganizationClassifyFilter(List<FilterObject> dataOrganizationClassifyFilter) {
        this.dataOrganizationClassifyFilter = dataOrganizationClassifyFilter;
    }

    public List<FilterObject> getDataSourceClassifyFilter() {
        return dataSourceClassifyFilter;
    }

    public void setDataSourceClassifyFilter(List<FilterObject> dataSourceClassifyFilter) {
        this.dataSourceClassifyFilter = dataSourceClassifyFilter;
    }

    public List<FilterObject> getObjectStatesFilter() {
        return objectStatesFilter;
    }

    public void setObjectStatesFilter(List<FilterObject> objectStatesFilter) {
        this.objectStatesFilter = objectStatesFilter;
    }

    public List<FilterObject> getCreatorFilter() {
        return creatorFilter;
    }

    public void setCreatorFilter(List<FilterObject> creatorFilter) {
        this.creatorFilter = creatorFilter;
    }

    public List<FilterObject> getUpdaterFilter() {
        return updaterFilter;
    }

    public void setUpdaterFilter(List<FilterObject> updaterFilter) {
        this.updaterFilter = updaterFilter;
    }
}
