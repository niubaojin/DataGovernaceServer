package com.synway.datastandardmanager.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueLabelChildrenVO {
    private String value;
    private String label;
    private List<ValueLabelChildrenVO> children;

    public ValueLabelChildrenVO(String value, String label){
        this.value = value;
        this.label = label;
    }

}
