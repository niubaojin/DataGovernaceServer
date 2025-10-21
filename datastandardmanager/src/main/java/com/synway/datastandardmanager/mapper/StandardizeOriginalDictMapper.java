package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDictEntity;
import com.synway.datastandardmanager.interceptor.AuthorControl;

import java.util.List;

public interface StandardizeOriginalDictMapper extends BaseMapper<StandardizeOriginalDictEntity> {

    /**
     * 获取左侧树信息
     *
     * @description: columnName为拼接唯一值id
     */
    @AuthorControl(tableNames ={"STANDARDIZE_ORIGINAL_DICT"},columnNames = {"FACTURER || '_' || l11.DICTIONARY_NAME"})
    List<StandardizeOriginalDictEntity> searchLeftTreeInfo();

    /**
     * 更新一条外部字典信息
     */
    int updateOneOriginalDictionary(StandardizeOriginalDictEntity originalDictionaryPojo);

}
