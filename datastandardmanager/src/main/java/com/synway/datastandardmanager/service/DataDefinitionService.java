package com.synway.datastandardmanager.service;


import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.StandardObjectManage;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.*;
import com.synway.datastandardmanager.pojo.warehouse.DataSimilarParameter;

import java.util.List;
import java.util.Map;

/**
 *
 * 数据定义管理Service接口
 * @author obito
 * @version 1.0
 * @date
 */
public interface DataDefinitionService {

    /**
     * 获取数据定义管理页面的数据 页面表格
     * @param dataDefinitionParameter
     * @return
     */
    Map<String,Object> searchDataDefinitionTable(DataDefinitionParameter dataDefinitionParameter);

    /**
     * 根据数据元内部标识符获取字典中文名
     * @param gadsjFieldId
     * @return
     */
    String getDictionaryNameById(String gadsjFieldId);

    /**
     * 关键字搜索全部的数据标准集信息
     * @param searchText
     * @return
     */
    List<PageSelectOneValue> searchAllDataStandard(String searchText);

    /**
     * 获取探查分析推荐标准数据集
     * @param dataSimilarParameter 调用仓库所需参数
     * @return
     */
    List<PageSelectOneValue> getDataSetDetectSimilarResult(DataSimilarParameter dataSimilarParameter);

    /**
     * 根据数据集id获取数据集对标信息
     * @param objectId 数据集id
     * @return
     */
    ObjectRelationManage getDataSetMapping(String objectId);

//    /**
//     * 根据数据集id获取右侧数据
//     * @param objectId 数据集id
//     * @return
//     */
//    DataSetMark getDataSetField(String objectId);

    /**
     * 数据集对标
     * @param objectRelationManage 数据集id
     * @return
     */
    ObjectRelationManage getObjectRelation(ObjectRelationManage objectRelationManage);

    /**
     * 模糊搜素字段名称(字段描述)
     * @param searchText 关键字
     * @param tableId 标准协议id
     * @return
     */
    List<ObjectFieldRelation> getColumnNameList(String searchText,String tableId);

    /**
     * 探查推荐映射/数据元映射/序号映射
     * @param objectRelationManage 数据集对标参数
     * @return
     */
    ObjectRelationManage dataFieldMapping(ObjectRelationManage objectRelationManage);


}
