package com.synway.datastandardmanager.pojo.sourcedata;

import java.util.List;

@Deprecated
public class BackDataConstruct {
      private String value;
      private String label;
      private List<BackDataConstruct> children;

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

    public List<BackDataConstruct> getChildren() {
        return children;
    }

    public void setChildren(List<BackDataConstruct> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "BackDataConstruct{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
