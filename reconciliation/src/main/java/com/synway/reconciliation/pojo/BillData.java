package com.synway.reconciliation.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接收账单数据
 * @author Administrator
 */
@Data
public class BillData implements Serializable {
    /**
     * 账单数据
     */
    private String bills;

    /**
     * 账单类型
     */
    private int type;
}
