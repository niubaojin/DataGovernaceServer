package com.synway.datarelation.pojo.databloodline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * 应用血缘管理页面的筛选信息
 * 这个只筛选数据加工血缘信息
 * 数据过滤 组织分类(相关分类信息)
 * @author wangdongwei
 * @date 2021/3/12 10:40
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BloodlineFilterPage implements Serializable {

    /**
     * 页面上节点的相关信息，用于筛选的原始数据
     */
    private DataBloodlineNode pageData;

    /**
     * 前端查询的页面的数据
     */
    private String pageDataStr;


    /**
     * 增加数据过滤信息 组织分类/非组织分类/全部
     */
    private String  classifyFilter;

    /**
     * 这个只有 当classifyFilter 为组织分类的时候才有用
     * 组织分类标注  原始库/业务库/资源库/主题库/其他
     */
    private List<String> organizationClassifyList;

    /**
     *  数据轨迹筛选  dataFlow:数据流向  processFlow : 加工流向
     */
    private String dataTrackFilter;

    public String getPageDataStr() {
        return pageDataStr;
    }

    public void setPageDataStr(String pageDataStr) {
        this.pageDataStr = pageDataStr;
    }

    public String getDataTrackFilter() {
        return dataTrackFilter;
    }

    public void setDataTrackFilter(String dataTrackFilter) {
        this.dataTrackFilter = dataTrackFilter;
    }

    public DataBloodlineNode getPageData() {
        return pageData;
    }

    public void setPageData(DataBloodlineNode pageData) {
        this.pageData = pageData;
    }

    public String getClassifyFilter() {
        return classifyFilter;
    }

    public void setClassifyFilter(String classifyFilter) {
        this.classifyFilter = classifyFilter;
    }

    public List<String> getOrganizationClassifyList() {
        return organizationClassifyList;
    }

    public void setOrganizationClassifyList(List<String> organizationClassifyList) {
        this.organizationClassifyList = organizationClassifyList;
    }
}
