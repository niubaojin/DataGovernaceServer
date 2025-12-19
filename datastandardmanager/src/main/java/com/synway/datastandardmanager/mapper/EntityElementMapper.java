package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.EntityElementDTO;
import com.synway.datastandardmanager.entity.pojo.EntityElementEntity;
import com.synway.datastandardmanager.entity.vo.EntityElementVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelChildrenVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据要素
 */
public interface EntityElementMapper extends BaseMapper<EntityElementEntity> {

    List<EntityElementEntity> queryEntityElementList(@Param("searchText") String searchText);

    List<EntityElementVO> getDataElementList(EntityElementDTO elementDTO);

    List<SelectFieldVO> selectElementObject();

    List<SelectFieldVO> searchElementTotal();

    List<ValueLabelChildrenVO> searchSecondElementName(@Param("elementObject") String elementObject);

}
