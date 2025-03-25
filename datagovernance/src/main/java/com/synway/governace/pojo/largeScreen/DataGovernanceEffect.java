package com.synway.governace.pojo.largeScreen;

import java.math.BigDecimal;

/**
 * 数据治理效果
 *
 * @author ywj
 * @date 2020/7/30 9:47
 */
public class DataGovernanceEffect {
    // 问题数据数量
    private BigDecimal errorNumber;
    // 完成治理数据数量
    private BigDecimal finishedNumber;

    public BigDecimal getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(BigDecimal errorNumber) {
        this.errorNumber = errorNumber;
    }

    public BigDecimal getFinishedNumber() {
        return finishedNumber;
    }

    public void setFinishedNumber(BigDecimal finishedNumber) {
        this.finishedNumber = finishedNumber;
    }
}
