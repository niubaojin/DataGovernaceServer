package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @description TODO
 * @date 2021/3/3 10:52
 */
public class ImpactAnalysisProperty implements Serializable {

    /**
     * 这张表涉及到的工作流名称 数量
     */
    private long workFlowCount;
    /**
     * 这张表直接涉及到的应用血缘信息 数量
     */
    private long applicationBloodlineCount;

    public long getWorkFlowCount() {
        return workFlowCount;
    }

    public void setWorkFlowCount(long workFlowCount) {
        this.workFlowCount = workFlowCount;
    }

    public long getApplicationBloodlineCount() {
        return applicationBloodlineCount;
    }

    public void setApplicationBloodlineCount(long applicationBloodlineCount) {
        this.applicationBloodlineCount = applicationBloodlineCount;
    }
}
