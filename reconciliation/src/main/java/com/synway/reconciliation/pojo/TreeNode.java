package com.synway.reconciliation.pojo;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 树状图节点
 * @author ym
 */
public class TreeNode {
    private String label;
    private String id;
    private int showIcon;
    private List<TreeNode> children;
    private String parent;
    private String grandPar;
    private int level;
    private boolean isLeaf;
    private List<String> values;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getGrandPar() {
        return grandPar;
    }

    public void setGrandPar(String grandPar) {
        this.grandPar = grandPar;
    }

    public boolean getIsLeaf() {
        return CollectionUtils.isEmpty(children);
    }

    public void setIsLeaf(boolean isLeaf) {
        isLeaf = isLeaf;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
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
}
