package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.DataSynlteFieldDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据元
 */
public interface SynlteFieldMapper extends BaseMapper<SynlteFieldEntity> {

    /**
     * 生成方式第一行数据元查询 过滤"wb"开头
     * @param searchName 搜索关键字
     * @return
     */
    List<SelectFieldVO> getSelectField(@Param("searchName") String searchName);
    /**
     * 生成方式第一行数据元查询 过滤"wb"开头
     * @param searchName 搜索关键字
     * @return
     */
    List<SelectFieldVO> searchSecondField(@Param("searchName") String searchName);

    List<SynlteFieldEntity> querySynlteFieldList(@Param("dto") DataSynlteFieldDTO dto);

    List<KeyValueVO> getFilterObjectForSF();

    List<String> getSearchNameSuggest(@Param("searchName") String searchName);

    int updateSynlteField(SynlteFieldEntity synlteField);

    List<KeyValueVO> getGadsjFieldByText(@Param("searchText")String searchText,
                                         @Param("fieldType")String fieldType,
                                         @Param("fieldId")String fieldId);

    /**
     * 模糊匹配数据项中文名
     */
    List<KeyValueVO> searchFieldChineseList(@Param("searchText") String searchText);

    List<ObjectFieldEntity> queryCodeClassList();

    List<KeyValueVO> getGadsjFieldByTexts();

}
