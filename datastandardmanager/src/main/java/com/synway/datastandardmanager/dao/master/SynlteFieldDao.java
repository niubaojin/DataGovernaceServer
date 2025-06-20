package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataField;
import com.synway.datastandardmanager.pojo.relationTableInfo;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldParameter;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldVersion;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author wangdongwei
 */
@Mapper
@Repository
public interface SynlteFieldDao {


    /**
     * 获取表中所有的数据 用于筛选
     * @return
     */
    List<FilterObject>  searchAllFilterTable();

    /**
     * 获取数据元的信息 页面表格
     * @param parameter
     * @return
     */
    List<SynlteFieldObject> searchAllTable(SynlteFieldParameter parameter);

    /**
     * 获取搜索提示信息
     * @param searchName 关键字
     * @return
     */
    List<String> getSearchNameSuggest(@Param("searchName")String searchName);

//    /**
//     * 将数据改成删除掉
//     * @param fieldId
//     * @return
//     */
//    int deleteObjectByFieldId(@Param("fieldId")String fieldId);

    /**
     * 修改数据元的状态
     * @param list 数据元fieldId
     * @param onDate 时间
     * @param status 状态
     * @return
     */
    int updateSynlteFieldStatus(@Param("list")List<String> list,
                               @Param("onDate") Date onDate,
                                @Param("status")String status);

    /**
     * 获取数据元id在数据库中的数量
     * @param fieldId
     * @return
     */
    int getObjectCountById(@Param("fieldId")String fieldId);


    /**
     * 获取最新版本号
     * @param fieldId
     * @return
     */
    Integer getReleaseDateCountById(@Param("fieldId")String fieldId);

    /**
     * 获取更新时间
     * @param fieldId
     * @return
     */
    List<String> getUpdateDateCountById(@Param("fieldId")String fieldId);

    /**
     * 获取语义类型下拉框
     * @param searchName
     * @return
     */
    List<FilterObject> getSameWordList(@Param("searchName")String searchName);


    /**
     * 获取所有的标识符信息
     * @param dName  指定的标识符
     * @return
     */
    List<String> findSimChineseList(String dName);

    /**
     * 更新相关数据
     * @param object
     * @return
     */
    int updateObject(SynlteFieldObject object);

    /**
     * 插入数据
     * @param object
     * @return
     */
    int insertObject(SynlteFieldObject object);

    /**
     * 检查元数据语义ID是否存在
     * @param sameId
     * @return
     */
    int checkSameId(@Param("sameId")String sameId);


    /**
     * 检查 FIELDCODE表的主键ID
     * @param sameId
     * @return
     */
    int checkCodeIdExits(@Param("codeId")String sameId);


    /**
     * 检查 column的值是否存在
     * @param column
     * @return
     */
    int checkColumnExits(@Param("column")String column);

    /**
     * 一些选择框需要从码表库里面查询结构
     * @param name
     * @return
     */
    List<FilterObject> getSelectObjectByName(@Param("name")String name);

//    /**
//     * 批量更新数据
//     * @param simChinese
//     * @param fieldId
//     * @return
//     */
//    int updateNullSimChinese(@Param("simChinese")String simChinese,
//                             @Param("fieldId")String fieldId);


    /**
     * 根据ID查询数据元
     * @param fieldId
     * @return
     */
    SynlteFieldObject searchSynlteFieldById(@Param("fieldId") String fieldId);

    /**
     * 在版本表中新增数据元版本信息
     * @param synlteFieldVersion
     * @return
     */
    int saveSynlteFieldVersion(SynlteFieldVersion synlteFieldVersion);

    /**
     * 将更新前的数据元存到历史表中
     * @param synltefield
     * @return
     */
    int saveOneOldData(SynlteFieldObject synltefield);

    /**
     * 根据fieldId查询object的Id集合
     * @param fieldId
     * @return
     */
    List<String> searchObjectIdByFieldId(@Param("fieldId") String fieldId);

    /**
     * 查询数据安全分级列表
     * @return
     */
    List<PageSelectOneValue> searchDataSecurityLevelList();

    /**
     * 关键词搜索数据元信息
     * @param searchText
     * @param fieldType 融合单位类型
     * @param fieldId 唯一标识符(数据项定义中用于回显,该字段作为查询关键字)
     * @return
     */
    List<PageSelectOneValue> getGadsjFieldByText(@Param("searchText")String searchText,@Param("fieldType")String fieldType,
                                                 @Param("fieldId")String fieldId);
    List<PageSelectOneValue> getGadsjFieldByTexts();

    /**
     * 查询数据元是否存在
     * @param fieldId
     * @param columnName
     * @return
     */
    int searchSynlteFieldCount(@Param("fieldId") String fieldId,@Param("columnName")String columnName);

    /**
     * 查询全部的数据元
     * @return
     */
    List<SynlteFieldObject> searchAllSynlteField();

//    /**
//     * 将所有的数据元插入到历史库中
//     * @return
//     */
//    int insertAllSynlteFieldToHistory(@Param("synlteFieldObjectList") List<SynlteFieldObject> synlteFieldObjectList);

    /**
     * 清空所有的数据元
     * @return
     */
    int deleteAllSynlteField();


    /**
     * 数据元管理页面 关联数据集
     * @param fieldId 数据元唯一标识
     * @param searchName 模糊搜索关键字
     * @return
     */
    List<relationTableInfo> getAllTableNameByFieldId(@Param("fieldId") String fieldId,@Param("searchName")String searchName);
}
