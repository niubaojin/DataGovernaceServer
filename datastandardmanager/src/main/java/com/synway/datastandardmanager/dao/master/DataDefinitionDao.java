package com.synway.datastandardmanager.dao.master;


import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 数据定义管理的DAO
 * @author obito
 * @version 1.0
 * @date
 */
@Mapper
@Repository
public interface DataDefinitionDao {

    /**
     * 查询数据定义管理的主页面
     * @param parameter
     * @return
     */
    @AuthorControl(tableNames ={"classify_interface_all_date","synlte.\"OBJECT\"","synlte.object"},columnNames = {"sjxjbm","tableid","tableid"})
    List<DataDefinitionPojo> searchDataDefinitionTable(DataDefinitionParameter parameter);

    /**
     * 插入一条数据集信息
     * @param objectRelation
     * @return
     */
    int insertObjectRelation(ObjectRelation objectRelation);

    /**
     * 根据Id查询数据项对标信息
     * @param objectId
     * @return
     */
    ObjectRelation searchObjectRelationByObjectId(@Param("objectId") String objectId);

    /**
     * 根据id查询数据项信息
     * @param id
     * @return
     */
    List<ObjectFieldRelation> searchObjectFieldById(@Param("id")String id);

    /**
     * 插入数据项对标信息
     * @param objectFieldRelationList
     * @return
     */
    int insertObjectFieldRelationList(@Param("objectFieldRelationList") List<ObjectFieldRelation> objectFieldRelationList);

    /**
     * 插入一条数据项对标信息
     * @param objectFieldRelation
     * @return
     */
    int insertObjectFieldRelation(ObjectFieldRelation objectFieldRelation);

    /**
     * 修改一条数据集信息
     * @param objectRelation
     * @return
     */
    int updateObjectRelation(ObjectRelation objectRelation);

    /**
     * 修改一条数据项信息
     * @param objectFieldRelation
     * @return
     */
    int updateObjectFieldRelation(ObjectFieldRelation objectFieldRelation);

    /**
     * 根据id，字段名称查询字段是否存在
     * @param setId
     * @param columnName
     * @return
     */
    int getColumnCountByIdAndName(@Param("setId") String setId,@Param("columnName") String columnName);

    /**
     * 根据id 删除数据项信息
     * @param id
     * @return
     */
    int deleteObjectFieldRelationById(@Param("id") String id);

    /**
     * 根据parentId删除标准层的数据项
     * @param parentId
     * @return
     */
    int deleteStandardFieldRelationByParentId(@Param("parentId")String parentId);

    /**
     * 根据id 查找对应数据集标准层信息
     * @param id
     * @return
     */
    ObjectRelation getStandardObjectRelationByParentId(@Param("id")String id);

    /**
     * 根据id和objectId 删除对应的数据集信息
     * @param id
     * @param objectId
     * @return
     */
    int deleteObjectRelation(@Param("id")String id,@Param("objectId")Long objectId);

    /**
     * 数据集对标中，根据右侧卡片的objectId获取相应数据项信息
     * @param objectId 数据集objectId
     * @return
     */
    List<ObjectFieldRelation> searchFieldByObject(@Param("objectId")Long objectId);


    /**
     * 数据集对标中，根据右侧卡片的objectId获取相应数据项信息
     * @param objectId 数据集objectId
     * @return
     */
    DataSetMark getObjectById(@Param("objectId")String objectId);

    /**
     * 模糊搜素字段名称(字段描述)
     * @param searchText 关键字
     * @param objectId 标准id
     * @return
     */
    List<PageSelectOneValue> getColumnNameList(@Param("objectId") String objectId,String searchText);


}
