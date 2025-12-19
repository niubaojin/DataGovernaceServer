package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.DsmAllCodeDataEntity;
import com.synway.datastandardmanager.entity.vo.ValueTextVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DsmAllCodeDataMapper extends BaseMapper<DsmAllCodeDataEntity> {

    List<ValueTextVO> getSelectObjectByName(@Param("name")String name);

}
