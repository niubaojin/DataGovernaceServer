package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.batchoperation.ObjectClassifyEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectFieldEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectStatusEditPojo;

/**
 * @author wangdongwei
 */
public interface BatchOperationService {

    /**
     * 修改标准表的状态 启用/停用
     * @param objectStatusEditPojo
     * @return
     */
    String objectStatusEdit(ObjectStatusEditPojo objectStatusEditPojo);

    /**
     * 批量设置框，对获取方式、组织分类、来源分类、资源标签等分类属性进行批量修改。
     * @param editPojo
     * @return
     */
    String objectClassifyEdit(ObjectClassifyEditPojo editPojo);

    /**
     * 数据定义详情批量操作
     * @param editPojo
     * @return
     */
    String objectFieldEdit(ObjectFieldEditPojo editPojo);
}
