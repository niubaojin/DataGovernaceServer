package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryFieldPojo;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryPojo;
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
public interface OriginalDictionaryDao {
    /**
     * 新增一条外部字典信息
     *
     * @param originalDictionaryPojo 外部字典
     * @return
     */
    int addOneOriginalDictionary(OriginalDictionaryPojo originalDictionaryPojo);

    /**
     * 删除一条外部字典信息
     *
     * @param id 外部字典id
     * @param dictionaryName 外部字典名称
     * @return
     */
    int deleteOneOriginalDictionary(@Param("id") String id,@Param("dictionaryName")String dictionaryName);

    /**
     * 更新一条外部字典信息
     *
     * @param originalDictionaryPojo 外部字典信息
     * @return
     */
    int updateOneOriginalDictionary(OriginalDictionaryPojo originalDictionaryPojo);

    /**
     * 根据id和名称查询一条外部字典名称
     *
     * @param id 字典id
     * @param dictionaryName 外部字典名称
     * @return
     */
    OriginalDictionaryPojo searchOneData(@Param("id")String id,@Param("dictionaryName") String dictionaryName);

    /**
     * 批量插入原始字典项
     *
     * @param originalDictionaryFieldList 原始字典项列表
     * @return
     */
    int insertOriginalDictionaryFieldList(@Param("originalDictionaryFieldList") List<OriginalDictionaryFieldPojo> originalDictionaryFieldList);

    /**
     * 增加一条原始字典项
     *
     * @param originalDictionaryFieldPojo 原始字典项
     * @return
     */
    int insertOneDictionaryField(OriginalDictionaryFieldPojo originalDictionaryFieldPojo);

    /**
     * 根据组id删除该id下的全部外部字典
     *
     * @param groupId 原始字典表id
     * @return
     */
    int deleteAllDictionaryFieldByGroupId(@Param("groupId")String groupId);

    /**
     * 根据原始字典表id查询原始字典项
     *
     * @param groupId 原始字典表id
     * @return
     */
    List<OriginalDictionaryFieldPojo> searchDictionaryFieldByGroupId(@Param("groupId")String groupId);

    /**
     * 获取左侧树信息
     * @description: columnName为拼接唯一值id
     * @return
     */
    @AuthorControl(tableNames ={"STANDARDIZE_ORIGINAL_DICT"},columnNames = {"FACTURER || '_' || l11.DICTIONARY_NAME"})
    List<OriginalDictionaryPojo> searchLeftTreeInfo();

    /**
     * 查询某一厂商下字典名称是否重复
     *
     * @param dictionaryName 字典名称
     * @param facturerId 厂商
     * @return
     */
    int searchDictionaryByFacturer(@Param("dictionaryName") String dictionaryName,@Param("facturer") String facturer);

    /**
     * 仓库探查分析中字段探查原始字典的下拉内容
     *
     * @return
     */
    List<PageSelectOneValue> getOriginalDictionaryNameList();

}
