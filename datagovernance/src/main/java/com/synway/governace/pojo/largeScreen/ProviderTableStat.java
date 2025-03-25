package com.synway.governace.pojo.largeScreen;

/**
 * @author wangdongwei
 * @date 2021/5/7 11:21
 */
public class ProviderTableStat {

    /**
     * 运营商  移动、联通、电信
     */
    private String operatorNet;

    /**
     * 数据来源分类
     */
    private String dataType;

    /**
     * 数据量大小
     */
    private String recordsAll;

    public String getOperatorNet() {
        return operatorNet;
    }

    public void setOperatorNet(String operatorNet) {
        this.operatorNet = operatorNet;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRecordsAll() {
        return recordsAll;
    }

    public void setRecordsAll(String recordsAll) {
        this.recordsAll = recordsAll;
    }
}
