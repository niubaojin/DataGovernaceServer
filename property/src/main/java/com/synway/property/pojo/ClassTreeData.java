package com.synway.property.pojo;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 模态框中左侧选择框对应的
 *
 * @author majia
 * @date 2020/06/02
 */
public class ClassTreeData {
    private String label;
    private List<ClassTreeData> children;

    @Override
    public String toString() {
        return "ClassTreeData{" +
                "label='" + label + '\'' +
                ", children=" + JSONObject.toJSONString(children) +
                '}';
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ClassTreeData> getChildren() {
        return children;
    }

    public void setChildren(List<ClassTreeData> children) {
        this.children = children;
    }
}
