package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

/**
 * @author wangdongwei
 * @date 2020/12/15 9:51
 */
@Data
public class ValueLabelVO {

    private String value = "";
    private String label = "";
    private String memo = "";

    public ValueLabelVO() {}

    public ValueLabelVO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public ValueLabelVO(String value, String label, String memo) {
        this.value = value;
        this.label = label;
        this.memo = memo;
    }

}
