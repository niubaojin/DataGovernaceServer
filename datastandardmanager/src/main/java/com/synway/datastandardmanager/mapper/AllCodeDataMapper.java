package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.AllCodeDataEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllCodeDataMapper extends BaseMapper<AllCodeDataEntity> {

    List<KeyValueVO> getSelectObjectByName(@Param("name")String name);

}
