package com.synway.reconciliation.pojo.issue;

import lombok.Data;

import java.io.Serializable;

/**
 * 行政区划编码
 * @author Administrator
 */
@Data
public class AdministrativeDivisionCode implements Serializable {

    /**
     * 行政区划编码
     */
    private int code;

    /**
     * 行政名
     */
    private String name;
}
