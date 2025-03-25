package com.synway.governace.pojo.largeScreen;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangdongwei
 * @date 2021/8/21 10:45
 */
@Data
public class LargeStorageData implements Serializable {

    private static final long serialVersionUID = -5084620439792120913L;
    /**
     * 表英文名
     */
    private String tableNameEn;

    /**
     * 表中文名
     */
    private String tableNameCh;
    /**
     * 表数据总量
     */
    private String tableCount;

    /**
     * 表数据量 亿条
     */
    private String tableCountStr;
    /**
     * 存储大小
     */
    private String tableSize;

    /**
     * 存储大小 TB
     */
    private String tableSizeStr;
}
