package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.FieldCodeEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FieldCodeMapper extends BaseMapper<FieldCodeEntity> {

    /**
     * 根据codeId查询字典中文名称
     *
     * @param codeId
     */
    String searchFieldCodeByCodeId(@Param("codeId") String codeId);

    FieldCodeEntity selectOneSysName(@Param("value") String value);

    List<FieldCodeEntity> selectOneSysNames();

    /**
     * 查询标准字典下拉框
     *
     * @param condition 关键字
     */
    List<SelectFieldVO> getCodeValIdListDao(@Param("condition") String condition);

    /**
     * 查询引用数据字段的下拉框
     */
    List<KeyValueVO> searchDictionaryList(@Param("searchText") String searchText);

    /**
     * 查询所属地区的信息
     */
    List<FieldCodeEntity> getAreaInfo();

    List<SelectFieldVO> getClassidTypeList(@Param("sjzzflCodeId") String sjzzflCodeId);

}
