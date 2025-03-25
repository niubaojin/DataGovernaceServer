package com.synway.datastandardmanager.pojo.ExternalInterfce;

import java.util.List;

public class TreeNodeVue {
    private String label;
    private String id;
    private int showIcon;
    private List<TreeNodeVue> children;
    private String parent;
    private int level;
    private String grandpar;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNodeVue> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVue> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getShowIcon() {
        return showIcon;
    }

    public void setShowIcon(int showIcon) {
        this.showIcon = showIcon;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGrandpar() {
        return grandpar;
    }

    public void setGrandpar(String grandpar) {
        this.grandpar = grandpar;
    }
}