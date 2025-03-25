package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class PartitionInfo {

    private String partitionName;

    private String highValue;

    private String subpartitionName;

    private int subpartitionNum;

}
