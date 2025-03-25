package com.synway.reconciliation.pojo;

import lombok.Data;

import java.io.Serializable;


/**
 * 源数据表缓存
 * @author Administrator
 */
@Data
public class SourceTableCache implements Serializable {

    private String tableName;

    private String objectName;

    private Integer partitionRecon;
}
