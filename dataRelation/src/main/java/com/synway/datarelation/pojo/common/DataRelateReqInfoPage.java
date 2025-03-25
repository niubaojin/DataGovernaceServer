package com.synway.datarelation.pojo.common;


import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;

import java.util.List;

/**
 * 分页查询数据血缘信息
 */
public class DataRelateReqInfoPage {

    /**ok、error*/
    private String reqRet;
    /**异常信息*/
    private String error;
    private List<QueryBloodlineRelationInfo> reqInfo;
    private String queryId;
    private int total;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getReqRet() {
        return reqRet;
    }

    public void setReqRet(String reqRet) {
        this.reqRet = reqRet;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<QueryBloodlineRelationInfo> getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(List<QueryBloodlineRelationInfo> reqInfo) {
        this.reqInfo = reqInfo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
