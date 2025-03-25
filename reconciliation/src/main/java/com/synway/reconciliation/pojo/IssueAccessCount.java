package com.synway.reconciliation.pojo;

import lombok.Data;


/**
 * @author dzh
 */
@Data
public class IssueAccessCount {

    /**
     * 下发接入时间
     */
    private String issueTime;

    /**
     * 下发接入数量
     */
    private Long issueCount;

}
