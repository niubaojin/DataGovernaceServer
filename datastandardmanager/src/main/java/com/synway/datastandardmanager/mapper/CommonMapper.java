package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.vo.StandardTableRelationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper extends BaseMapper {

    List<StandardTableRelationVO> getOrganizationZTreeNodesAll(@Param("name")String name, @Param("dataType")int dataType);

}
