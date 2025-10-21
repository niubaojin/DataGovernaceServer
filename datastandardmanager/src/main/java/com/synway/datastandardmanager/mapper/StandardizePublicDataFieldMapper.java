package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataFieldEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardizePublicDataFieldMapper extends BaseMapper<StandardizePublicDataFieldEntity> {

    int insertPublicDataFieldList(@Param("publicDataFieldList") List<StandardizePublicDataFieldEntity> publicDataFieldList);

    /**
     * 根据id查询公共数据项信息
     */
    List<StandardizePublicDataFieldEntity> searchPublicDataFieldById(@Param("groupId") String groupId);

}
