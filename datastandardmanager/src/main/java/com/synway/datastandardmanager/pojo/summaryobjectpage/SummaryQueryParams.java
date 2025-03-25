package com.synway.datastandardmanager.pojo.summaryobjectpage;

import com.synway.datastandardmanager.valid.ListValue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;


/**
 * @author wangdongwei
 * @ClassName SummaryQueryParams
 * @description 汇总页面的相关查询参数
 * @date 2020/11/27 15:59
 */
public class SummaryQueryParams implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final  String CREATE_TIME_TYPE="createTimeType";
    public static final  String UPDATE_TIME_TYPE="updateTimeType";

    public static final  String MORE_THAN="大于";
    public static final  String LESS_THAN="小于";
    public static final  String EQUAL="等于";


    /**
     * 所有选择的  数据组织分类/数据来源分类的 分类信息
     * [{type: "organizationClassify", label: "公安执法与执勤数据(142)", mainClassify: "dataOrganizationClassify"
     * ,"primaryClassifyCh":"原始库","secondaryClassifyCh":"公安执法与执勤数据"，“type“："organizationClassify"}]
     */
    private List<ReceiveTag> classifyTags;

    /**
     * 资源状态的 筛选内容  启用/停用
     */
    private List<ReceiveTag> usingTags;

    /**
     * 排序的相关参数
     * 字段顺序  desc / asc
     */
    private String sortOrder = "desc";
    /**
     * 字段名称
     */
    private String sort;

    /**
     * 表协议id
     */
    private String tableId;
    /**
     * 搜索框中输入的查询内容 like
     */
    private String searchCode;
    /**
     * createTimeType:创建时间   updateTimeType:最后更新时间
     */
    private String timeTypeSummary;
    /**
     * 起始时间   2020-10-01
     */
    private String startTimeText;
    /**
     *  截止时间   2020-10-02
     */
    private String endTimeText;

    /**
     * 已建物理表的条件  等于/大于/小于
     */
    @NotNull
    @ListValue(vals = {"等于","大于","小于"},message = " createTableCondition的值必须是[等于/大于/小于]")
    private String createTableCondition;

    /**
     * 已建物理表的数量  为空时表示不使用这个查询条件
     */
    @Pattern(regexp = "[1-9]\\d*|0|\\s*" ,message = " 已建物理表的数量必须是正整数、0或空字符串")
    private String createTableTotal;


    /**
     * 应用系统的筛选内容  这个是应用系统中文名
     */
    private List<String> dataSourceFilter;

    /**
     *数据组织分类的筛选
     */
    private List<String> dataOrganizationClassifyFilter;
    /**
     * 数据来源分类的筛选
      */
    private List<String> dataSourceClassifyFilter;
    /**
     *  数据状态的筛选
     */
    private List<String>  objectStatesFilter;
    /**
     * 创建人的筛选
     */
    private List<String>  creatorFilter;
    /**
     * 最后修改人的筛选
     */
    private List<String>  updaterFilter;

    public String getCreateTableCondition() {
        return createTableCondition;
    }

    public void setCreateTableCondition(String createTableCondition) {
        this.createTableCondition = createTableCondition;
    }

    public String getCreateTableTotal() {
        return createTableTotal;
    }

    public void setCreateTableTotal(String createTableTotal) {
        this.createTableTotal = createTableTotal;
    }

    public List<String> getDataSourceFilter() {
        return dataSourceFilter;
    }

    public void setDataSourceFilter(List<String> dataSourceFilter) {
        this.dataSourceFilter = dataSourceFilter;
    }

    public List<String> getDataOrganizationClassifyFilter() {
        return dataOrganizationClassifyFilter;
    }

    public void setDataOrganizationClassifyFilter(List<String> dataOrganizationClassifyFilter) {
        this.dataOrganizationClassifyFilter = dataOrganizationClassifyFilter;
    }

    public List<String> getDataSourceClassifyFilter() {
        return dataSourceClassifyFilter;
    }

    public void setDataSourceClassifyFilter(List<String> dataSourceClassifyFilter) {
        this.dataSourceClassifyFilter = dataSourceClassifyFilter;
    }

    public List<String> getObjectStatesFilter() {
        return objectStatesFilter;
    }

    public void setObjectStatesFilter(List<String> objectStatesFilter) {
        this.objectStatesFilter = objectStatesFilter;
    }

    public List<String> getCreatorFilter() {
        return creatorFilter;
    }

    public void setCreatorFilter(List<String> creatorFilter) {
        this.creatorFilter = creatorFilter;
    }

    public List<String> getUpdaterFilter() {
        return updaterFilter;
    }

    public void setUpdaterFilter(List<String> updaterFilter) {
        this.updaterFilter = updaterFilter;
    }

    public List<ReceiveTag> getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(List<ReceiveTag> classifyTags) {
        this.classifyTags = classifyTags;
    }

    public List<ReceiveTag> getUsingTags() {
        return usingTags;
    }

    public void setUsingTags(List<ReceiveTag> usingTags) {
        this.usingTags = usingTags;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getTimeTypeSummary() {
        return timeTypeSummary;
    }

    public void setTimeTypeSummary(String timeTypeSummary) {
        this.timeTypeSummary = timeTypeSummary;
    }

    public String getStartTimeText() {
        return startTimeText;
    }

    public void setStartTimeText(String startTimeText) {
        this.startTimeText = startTimeText;
    }

    public String getEndTimeText() {
        return endTimeText;
    }

    public void setEndTimeText(String endTimeText) {
        this.endTimeText = endTimeText;
    }
}
