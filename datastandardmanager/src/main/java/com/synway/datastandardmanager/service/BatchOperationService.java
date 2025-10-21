package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.BatchOperationDTO;

/**
 * @author wangdongwei
 */
public interface BatchOperationService {

    /**
     * 修改标准表的状态 启用/停用
     *
     * @param dto
     */
    String objectStatusEdit(BatchOperationDTO dto);

    /**
     * 批量设置框，对获取方式、组织分类、来源分类、资源标签等分类属性进行批量修改。
     *
     * @param dto
     */
    String objectClassifyEdit(BatchOperationDTO dto);

    /**
     * 数据定义详情批量操作
     *
     * @param editPojo
     */
    String objectFieldEdit(BatchOperationDTO editPojo) throws Exception;

}
