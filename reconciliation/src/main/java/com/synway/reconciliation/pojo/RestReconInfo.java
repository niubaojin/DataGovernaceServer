package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * 对外数据对账信息
 * @author DZH
 */
@Data
public class RestReconInfo {
    /**
     * 数据种类数
     */
    private Integer dataTypeCount;

    /**
     * 对账成功数
     */
    private Integer successCount;

    /**
     * 对账失败数
     */
    private Integer failedCount;

    /**
     * 未对账数
     */
    private Integer unReconCount;
}
