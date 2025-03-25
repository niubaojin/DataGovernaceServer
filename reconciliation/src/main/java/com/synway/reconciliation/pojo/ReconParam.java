package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 对账参数
 * @author Administrator
 */
@Data
public class ReconParam {
    private int startDate;
    private int endDate;
    private String BZSJXJBM;
    private String XXRWBH;
    /**
     * 对账类型 3.（入库） 入库接入方与标准化提供方对账 2.（标准化） 标准化接入方与接入提供方对账
     */
    private int DZZDHJ;

    /**
     * 对账单环节类型 1.接入 2.标准化 3.入库 前端不用填
     */
    private int providerLink;
    private int acceptorLink;

    /**
     * 是否是数据包对账 true是  默认是false 前端不用填
     */
    private boolean detail;

}
