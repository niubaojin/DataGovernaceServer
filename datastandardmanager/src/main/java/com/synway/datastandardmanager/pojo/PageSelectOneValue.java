package com.synway.datastandardmanager.pojo;

public class PageSelectOneValue {
    private String value = "";
    private String label = "";
    private String memo = "";

    public PageSelectOneValue(String value , String label){
        this.value = value ;
        this.label = label;
    }
    public PageSelectOneValue(String value , String label , String memo){
        this.value = value ;
        this.label = label;
        this.memo = memo;
    }
    @Override
    public String toString() {
        return "PageSelectOneValue{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
