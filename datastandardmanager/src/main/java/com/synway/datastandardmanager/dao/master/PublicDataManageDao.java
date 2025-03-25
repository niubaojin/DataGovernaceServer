package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataField;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
@Mapper
@Repository
public interface PublicDataManageDao {

    /**
     *  插入一条公共数据项分组信息
     * @param publicDataPojo
     * @return
     */
    int addOnePublicDataGroup(PublicDataPojo publicDataPojo);

    /**
     *  删除一条公共数据项分组信息
     * @param id
     * @param groupName
     * @return
     */
    int deletePublicDataGroupById(@Param("id") String id,@Param("groupName") String groupName);

    /**
     *  更新一条公共数据项分组信息
     * @param publicDataPojo
     * @return
     */
    int updatePublicDataGroup(PublicDataPojo publicDataPojo);

    /**
     *  增加公共数据项信息
     * @param publicDataFieldList
     * @return
     */
    int insertPublicDataFieldList(@Param("publicDataFieldList") List<PublicDataField> publicDataFieldList);

    /**
     *  增加公共数据项信息
     * @param publicDataField
     * @return
     */
    int insertPublicDataField(PublicDataField publicDataField);

    /**
     * 删除一条公共数据项信息
     * @param id
     * @param fieldId
     * @return
     */
    int deletedPublicDataFieldByIdAndFieldId(@Param("id") String id,@Param("fieldId") String fieldId);

    /**
     * 根据id删除所有的数据项信息
     * @param id
     * @return
     */
    int deletePublicDataFieldById(@Param("id") String id);

    /**
     * 修改一条公共数据项信息
     * @param publicDataField
     * @return
     */
    int updatePublicDataField(PublicDataField publicDataField);

    /**
     * 模糊匹配数据项中文名
     * @param searchText
     * @return
     */
    List<PageSelectOneValue> searchFieldChineseList(@Param("searchText") String searchText);

    /**
     * 查询分组名称是否重复
     * @param groupName
     * @return
     */
    int searchPublicDataGroupName(@Param("groupName") String groupName);

    /**
     * 根据id查询分组是否重复
     * @param id
     * @return
     */
    int searchPublicDataById(@Param("id") String id);

    /**
     * 根据分组名称查询公共数据项组信息
     * @param groupName
     * @return
     */
    PublicDataPojo searchPublicDataByName(@Param("groupName") String groupName);

    /**
     * 根据id查询公共数据项信息
     * @param groupId
     * @return
     */
    List<PublicDataField> searchPublicDataFieldById(@Param("groupId") String groupId);

    /**
     * 查询公共数据项分组名称列表
     * @return
     */
    @AuthorControl(tableNames ={"synlte.STANDARDIZE_PUBLIC_DATA"},columnNames = {"group_name"})
    List<PageSelectOneValue> searchGroupNameList();

}
