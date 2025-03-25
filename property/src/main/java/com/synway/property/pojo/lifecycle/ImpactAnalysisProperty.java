package com.synway.property.pojo.lifecycle;

import java.io.Serializable;

/**
 * @author wangdongwei
 * @description
 * @date 2021/3/3 10:52
 */
public class ImpactAnalysisProperty implements Serializable {

    /**
     * 这张表涉及到的工作流名称 数量
     */
    private int workFlowCount;
    /**
     * 这张表直接涉及到的应用血缘信息 数量
     */
    private int applicationBloodlineCount;

    public int getWorkFlowCount() {
        return workFlowCount;
    }

    public void setWorkFlowCount(int workFlowCount) {
        this.workFlowCount = workFlowCount;
    }

    public int getApplicationBloodlineCount() {
        return applicationBloodlineCount;
    }

    public void setApplicationBloodlineCount(int applicationBloodlineCount) {
        this.applicationBloodlineCount = applicationBloodlineCount;
    }
}
