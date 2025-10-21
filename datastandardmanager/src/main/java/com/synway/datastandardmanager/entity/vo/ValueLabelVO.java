package com.synway.datastandardmanager.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueLabelVO {
    private String value;
    private String label;
    private List<ValueLabelVO> children;

    public  ValueLabelVO(String value, String label){
        this.value = value;
        this.label = label;
    }

}
