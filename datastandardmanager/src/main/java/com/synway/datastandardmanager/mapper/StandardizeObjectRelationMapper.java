package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeObjectRelationEntity;
import org.apache.ibatis.annotations.Param;

public interface StandardizeObjectRelationMapper extends BaseMapper<StandardizeObjectRelationEntity> {
    StandardizeObjectRelationEntity selectSORByParentId(@Param("id") String id);


    /**
     * 插入数据集信息
     * @param objectRelation
     * @return
     */
    int insertOrUpdateStdObjRel(StandardizeObjectRelationEntity objectRelation);

}
