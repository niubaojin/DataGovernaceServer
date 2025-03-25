package com.synway.governace.pojo;


public class ThresholdConfig {
    private String id;
    private String parent_id;
    private String name;
    private String logical_judgment;
    private String threshold_value;
    private String tree_type;
    private String is_active;
    private String page_url;

    @Override
    public String toString() {
        return "ThresholdConfig{" +
                "id='" + id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", name='" + name + '\'' +
                ", logical_judgment='" + logical_judgment + '\'' +
                ", threshold_value='" + threshold_value + '\'' +
                ", tree_type='" + tree_type + '\'' +
                ", is_active='" + is_active + '\'' +
                ", page_url='" + page_url + '\'' +
                '}';
    }

    public String getThreshold_value() {
        return threshold_value;
    }

    public void setThreshold_value(String threshold_value) {
        this.threshold_value = threshold_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogical_judgment() {
        return logical_judgment;
    }

    public void setLogical_judgment(String logical_judgment) {
        this.logical_judgment = logical_judgment;
    }



    public String getTree_type() {
        return tree_type;
    }

    public void setTree_type(String tree_type) {
        this.tree_type = tree_type;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }
}
