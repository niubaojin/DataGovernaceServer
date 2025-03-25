package com.synway.reconciliation.pojo;

import java.util.List;

/**
 * 从 根据协议名英文名（jz_xx）、协议中文名、表英文名(不包含项目名)、查询，返回对于匹配数据的血缘关系
 * @author ywj
 */
public class RelationInfoRestTemolate {
    /**
     * ok 表示成功
     */
    private String reqRet;
    private String error;
    private List<QueryBloodlineRelationInfo> reqInfo;

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
}
