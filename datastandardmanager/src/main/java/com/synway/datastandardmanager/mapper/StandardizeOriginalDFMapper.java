package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDFEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardizeOriginalDFMapper extends BaseMapper<StandardizeOriginalDFEntity> {

    /**
     * 批量插入原始字典项
     *
     * @param standardizeOriginalDFEntities 原始字典项列表
     */
    int insertOriginalDictionaryFieldList(@Param("standardizeOriginalDFEntities") List<StandardizeOriginalDFEntity> standardizeOriginalDFEntities);
}
