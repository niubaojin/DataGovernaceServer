package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeObjectfieldRelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardizeObjectfieldRelMapper extends BaseMapper<StandardizeObjectfieldRelEntity> {

    /**
     * 插入数据项对标信息
     * @param objectFieldRelationList
     * @return
     */
    int insertList(@Param("objectFieldRelationList") List<StandardizeObjectfieldRelEntity> objectFieldRelationList);

    /**
     * 更新数据项信息
     * @param objectFieldRelation
     * @return
     */
    int updateStdObjectfieldRel(StandardizeObjectfieldRelEntity objectFieldRelation);

}
