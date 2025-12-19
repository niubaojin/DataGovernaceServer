package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.entity.vo.dataprocess.StandardFieldJson;
import com.synway.datastandardmanager.entity.vo.warehouse.DetectedTable;

import java.util.List;

public interface DataSetStandardService {

    /**
     * 根据表tableID获取表字段信息
     *
     * @param tableId 表id
     * @return
     */
    List<ObjectFieldEntity> queryObjectFieldListByTableId(String tableId);

    ObjectEntity queryObjectDetail(String tableId);

    String deleteObjectField(Long objectId, String fieldName);

    /**
     * 展示所有的来源关系
     *
     * @param tableId
     * @return
     */
    List<SourceRelationShipVO> getSourceRelationShip(String tableId);

    /**
     * 根据大类的id号获取一级分类信息，'1':组织分类 '2':来源分类   '3'：资源分类
     *
     * @param mainValue
     * @return
     */
    List<ValueLabelVO> getFirstClassModeByMain(String mainValue);

    /**
     * 根据大类id和一级分类的名称获取二级级分类信息
     *
     * @param mainValue
     * @param firstClassValue
     */
    List<ValueLabelVO> getSecondaryClassModeByFirst(String mainValue, String firstClassValue);

    /**
     * 开始删除指定的来源关系
     *
     * @param delSourceRelation 需要删除的来源关系列表
     * @param outputDataId      表协议ID
     * @return
     */
    String deleteSourceRelation(List<SourceRelationShipVO> delSourceRelation,
                                String outputDataId);

    /**
     * 创建表字段fieldID的相关信息，用于搜索提示框
     *
     * @param type
     * @param condition
     */
    List<SelectFieldVO> createAddColumnModel(String type, String condition);

    /**
     * 根据指定的数据获取对应的Synltefield表中字段信息
     *
     * @param type
     * @param inputValue
     */
    SynlteFieldEntity getAddColumnByInput(String type, String inputValue);

    /**
     * 根据数据探查里面该sourceid对应的tableid数据
     *
     * @param objectManageDTO
     * @param switchFlag
     */
    void checkTableIdSourceIdIsExists(ObjectManageDTO objectManageDTO, boolean switchFlag);

    /**
     * 保存页面上：数据信息/字段定义/来源关系 三种数据
     * 数据信息/数据项信息/原始数据来源信息/数据集信息(1.9)
     *
     * @param objectManageDTO
     */
    boolean saveResourceFieldRelation(ObjectManageDTO objectManageDTO) throws Exception;

    /**
     * 推送给标准化修改后的标准表信息
     *
     * @param tableId
     * @return
     */
    boolean pushMetaInfo(String tableId);

    /**
     * 根据数据源id、项目空间、表英文名查询已探查表全部信息
     */
    DetectedTable getDetectedTableInfo(String resId, String project, String tableName);

    /**
     * 获取源应用系统下拉列表
     *
     * @return
     */
    List<ValueLabelChildrenVO> getAllSysList();

    StandardFieldJson getAllStandardFieldJson(String tableId);

    DataResourceRawInformationVO getDataResourceInformation(String dataId, String project, String tableName);

    List<String> createObjectTableSuggest(String mainValue,
                                                 String firstValue,
                                                 String secondaryValue,
                                                 String condition);

    List<String> getSchemaApproved(String dataId);

    List<ValueLabelVO> getDetectedTablesNameInfo(String resId, String projectName, String type);

    /**
     * 20200511 判断 fieldId fieldName 是否为正常的字段信息
     */
    String getIsExitsFiledMessage(String fieldId,String fieldName);

    String getOrganizationTableId(String dataSourceClassify,String code);

    String getDefaultXZQH();

    List<ValueLabelVO> getDataCenter();

    List<ValueLabelVO> getDataResourceNameByCenterId(String centerId, String type);

    List<ValueLabelVO> getDataResourceNameByType(String type);

    Long searchObjectBySourceId(String tableId);

    Boolean queryTableIsExist(String tableName);

}
