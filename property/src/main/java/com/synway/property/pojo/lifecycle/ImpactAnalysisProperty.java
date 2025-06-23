package com.synway.property.pojo.lifecycle;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangdongwei
 * @description
 * @date 2021/3/3 10:52
 */
@Data
public class ImpactAnalysisProperty implements Serializable {

    /**
     * 这张表涉及到的工作流名称 数量
     */
    private int workFlowCount;
    /**
     * 这张表直接涉及到的应用血缘信息 数量
     */
    private int appCount;

}
