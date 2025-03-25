package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.StandardObjectManage;
import com.synway.datastandardmanager.pojo.StandardTableRelation;
import com.synway.datastandardmanager.pojo.ZtreeNode;

import java.util.List;

/**
 * @author wangdongwei
 */
public interface OtherModuleManageService {

    /**
     * 如果 moduleName 不为空，则需要筛选出这个模块创建的表 非该模块创建的
     * @param standardTableRelationList  标准tableId的相关数据
     * @param moduleName  模块名称
     * @return
     */
    List<StandardTableRelation> filterZtreeByModuleName(List<StandardTableRelation> standardTableRelationList, String moduleName);


    /**
     * 第三方模块的创建之后保存
     * @param standardObjectManage
     */
    void addOtherModuleCreated(StandardObjectManage standardObjectManage);
}
