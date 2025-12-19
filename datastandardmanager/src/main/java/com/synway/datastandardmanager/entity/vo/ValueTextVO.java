package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

@Data
public class ValueTextVO {

    private String value = "";
    private String text = "";

    public ValueTextVO(String value, String text){
        this.value = value;
        this.text = text;
    }

}
