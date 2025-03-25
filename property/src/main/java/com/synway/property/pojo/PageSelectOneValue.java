package com.synway.property.pojo;

/**
 * @author majia
 */
public class PageSelectOneValue {
    private String value;
    private String label;
    private Boolean classShow = true;

    public PageSelectOneValue(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return "PageSelectOneValue{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", classShow='" + classShow + '\'' +
                '}';
    }

    public Boolean isClassShow() {
        return classShow;
    }

    public void setClassShow(Boolean classShow) {
        this.classShow = classShow;
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
}
