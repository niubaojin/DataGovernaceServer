package com.synway.datastandardmanager.pojo;

/**
 * @author wangdongwei
 * @ClassName FilterObject
 * @description TODO
 * @date 2020/12/15 9:51
 */
public class FilterObject {

    private String text;
    private String value;
    public  FilterObject(){

    }

    public  FilterObject(String text,String value){
        this.text = text;
        this.value = value;
    }

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
