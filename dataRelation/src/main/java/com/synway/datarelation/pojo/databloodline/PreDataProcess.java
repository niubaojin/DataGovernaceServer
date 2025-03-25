package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;

/**
 * 数据处理节点上的 前置节点
 * @author wangdongwei
 * @date 2021/3/29 9:48
 */
public class PreDataProcess implements Serializable {
    /**
     * 展示的内容
     */
    private String showName;
    /**
     * 表协议id值
     */
    private String tableId;

    /**
     * objectId的相关信息
     */
    private String objectId;

    /**
     * 协议中文名
     */
    private String tableIdCh;

    public String getTableIdCh() {
        return tableIdCh;
    }

    public void setTableIdCh(String tableIdCh) {
        this.tableIdCh = tableIdCh;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
