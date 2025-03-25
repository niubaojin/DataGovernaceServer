package com.synway.dataoperations.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonMap {
    private Integer key;
    private String value;

    public CommonMap(Integer key, String value){
        this.key=key;
        this.value = value;
    }

}
