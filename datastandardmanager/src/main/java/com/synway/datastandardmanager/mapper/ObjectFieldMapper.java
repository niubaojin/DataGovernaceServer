package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.BatchOperationDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;

public interface ObjectFieldMapper extends BaseMapper<ObjectFieldEntity> {

    int updateObjectField(ObjectFieldEntity objectField);

    /**
     * 更新objectfield指定字段表的分类信息
     * @param dto
     */
    int updateObjectClassify(BatchOperationDTO dto);

}
