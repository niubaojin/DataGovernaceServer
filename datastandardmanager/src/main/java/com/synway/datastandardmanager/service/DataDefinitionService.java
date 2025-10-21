package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.DataDefinitionDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeObjectfieldRelEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.ObjectRelationManageVO;
import com.synway.datastandardmanager.entity.vo.PageVO;

import java.util.List;

/**
 * 数据定义管理Service接口
 *
 * @author obito
 * @version 1.0
 * @date
 */
public interface DataDefinitionService {

    /**
     * 获取数据定义管理页面的数据 页面表格
     *
     * @param dataDefinitionParameter
     */
    PageVO searchDataDefinitionTable(DataDefinitionDTO dataDefinitionParameter);

    /**
     * 根据数据元内部标识符获取字典中文名
     *
     * @param gadsjFieldId
     */
    String getDictionaryNameById(String gadsjFieldId);

    /**
     * 关键字搜索全部的数据标准集信息
     *
     * @param searchText
     */
    List<KeyValueVO> searchAllDataStandard(String searchText);

    /**
     * 获取探查分析推荐标准数据集
     *
     * @param dataSimilarParameter 调用仓库所需参数
     */
    List<KeyValueVO> getDataSetDetectSimilarResult(DataDefinitionDTO dataSimilarParameter) throws Exception;

    /**
     * 根据数据集id获取数据集对标信息
     *
     * @param objectId 数据集id
     */
    ObjectRelationManageVO getDataSetMapping(String objectId) throws Exception;

//    /**
//     * 根据数据集id获取右侧数据
//     * @param objectId 数据集id
//     * @return
//     */
//    DataSetMark getDataSetField(String objectId);

    /**
     * 数据集对标
     *
     * @param objectRelationManage 数据集id
     * @return
     */
    ObjectRelationManageVO getObjectRelation(ObjectRelationManageVO objectRelationManage);

    /**
     * 模糊搜素字段名称(字段描述)
     *
     * @param searchText 关键字
     * @param tableId    标准协议id
     */
    List<StandardizeObjectfieldRelEntity> getColumnNameList(String searchText, String tableId);

    /**
     * 探查推荐映射/数据元映射/序号映射
     *
     * @param objectRelationManage 数据集对标参数
     */
    ObjectRelationManageVO dataFieldMapping(ObjectRelationManageVO objectRelationManage);

}
