package com.synway.reconciliation.pojo.issue;

import lombok.Data;

/**
 * 数据下发对账统计请求扩展（为了增加地市）
 * @author DZH
 */
@Data
public class IssueReconciliationRequestExtend extends IssueReconciliationRequest{

    /**
     * local
     */
    private String local;
}
