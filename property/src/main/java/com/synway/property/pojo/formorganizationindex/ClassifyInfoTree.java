package com.synway.property.pojo.formorganizationindex;

import java.util.List;

public class ClassifyInfoTree {
    private String value;
    private String label;
    private List<ClassifyInfoTree> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ClassifyInfoTree> getChildren() {
        return children;
    }

    public void setChildren(List<ClassifyInfoTree> children) {
        this.children = children;
    }
}
