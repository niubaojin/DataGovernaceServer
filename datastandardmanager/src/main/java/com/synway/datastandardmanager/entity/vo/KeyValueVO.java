package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

/**
 * @author wangdongwei
 * @date 2020/12/15 9:51
 */
@Data
public class KeyValueVO {

    private String value = "";
    private String label = "";
    private String memo = "";

    public KeyValueVO() {}

    public KeyValueVO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public KeyValueVO(String value, String label, String memo) {
        this.value = value;
        this.label = label;
        this.memo = memo;
    }

}
