package com.synway.property.pojo.lifecycle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 返回页面查询出来的数据
 * @author majia
 */
public class LifeCyclePageReturn implements Serializable {
    // 页面查询出来的结果
    private Map<String,Object> pageInfoMap;
    // 组织分类的 filter列表
    private List<FilterObject> organizationClassifyShowList;
    // 平台的 filter列表
    private List<FilterObject> platformTypeShowList;
    // 项目名称的 filter列表
    private List<FilterObject> projectNameShowList;
    // 更新方式的 filter列表
    private List<FilterObject> updateTypeShowList;
    // 是否分区的 filter列表
    private List<FilterObject> partitionShowList;

    public static class FilterObject{
        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public Map<String, Object> getPageInfoMap() {
        return pageInfoMap;
    }

    public void setPageInfoMap(Map<String, Object> pageInfoMap) {
        this.pageInfoMap = pageInfoMap;
    }

    public List<FilterObject> getOrganizationClassifyShowList() {
        return organizationClassifyShowList;
    }

    public void setOrganizationClassifyShowList(List<FilterObject> organizationClassifyShowList) {
        this.organizationClassifyShowList = organizationClassifyShowList;
    }

    public List<FilterObject> getPlatformTypeShowList() {
        return platformTypeShowList;
    }

    public void setPlatformTypeShowList(List<FilterObject> platformTypeShowList) {
        this.platformTypeShowList = platformTypeShowList;
    }

    public List<FilterObject> getProjectNameShowList() {
        return projectNameShowList;
    }

    public void setProjectNameShowList(List<FilterObject> projectNameShowList) {
        this.projectNameShowList = projectNameShowList;
    }

    public List<FilterObject> getUpdateTypeShowList() {
        return updateTypeShowList;
    }

    public void setUpdateTypeShowList(List<FilterObject> updateTypeShowList) {
        this.updateTypeShowList = updateTypeShowList;
    }

    public List<FilterObject> getPartitionShowList() {
        return partitionShowList;
    }

    public void setPartitionShowList(List<FilterObject> partitionShowList) {
        this.partitionShowList = partitionShowList;
    }
}
