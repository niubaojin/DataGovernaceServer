package com.synway.datastandardmanager.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class LayuiClassifyPojo {
    private String value;
    private String label;
    private List<LayuiClassifyPojo> children;

    @Override
    public String toString() {
        return "LayuiClassifyPojo{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + JSONObject.toJSONString(children) +
                '}';
    }

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

    public List<LayuiClassifyPojo> getChildren() {
        return children;
    }

    public void setChildren(List<LayuiClassifyPojo> children) {
        this.children = children;
    }
}
