package com.synway.datarelation.pojo.common;

import com.synway.datarelation.pojo.databloodline.ColumnRelationDB;
import com.synway.datarelation.pojo.databloodline.NodeColumnRelation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 *  标准化，数据接入血缘的字段信息
 */
public class DataColumnCache implements Serializable {
    // 因为一个页面只能显示一个页面
    @NotNull
    private String pageId;
    // 查询参数解析出来的 uuid  页面上组装的查询id
    private String queryId=" ";
    private List<NodeColumnRelation> nodeColumnRelationList;
    // key为nodeid  具有唯一值
    private Map<String, List<ColumnRelationDB>> columnDBMap;
    private Instant queryDate;
    // 是否已经显示了字段详细信息的页面
    private Boolean showColumnPage =false;
    // 字段血缘缓存保存 20分钟 即 720秒,如果显示了详细页面，即保存 秒
    private Integer saveTime=620;

    public Boolean getShowColumnPage() {
        return showColumnPage;
    }

    public void setShowColumnPage(Boolean showColumnPage) {
        this.showColumnPage = showColumnPage;
    }

    public Integer getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Integer saveTime) {
        this.saveTime = saveTime;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public List<NodeColumnRelation> getNodeColumnRelationList() {
        return nodeColumnRelationList;
    }

    public void setNodeColumnRelationList(List<NodeColumnRelation> nodeColumnRelationList) {
        this.nodeColumnRelationList = nodeColumnRelationList;
    }

    public Map<String, List<ColumnRelationDB>> getColumnDBMap() {
        return columnDBMap;
    }

    public void setColumnDBMap(Map<String, List<ColumnRelationDB>> columnDBMap) {
        this.columnDBMap = columnDBMap;
    }

    public Instant getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Instant queryDate) {
        this.queryDate = queryDate;
    }
}
