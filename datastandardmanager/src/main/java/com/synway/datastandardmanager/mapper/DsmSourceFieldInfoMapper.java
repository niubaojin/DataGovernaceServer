package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.DsmSourceFieldInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DsmSourceFieldInfoMapper extends BaseMapper<DsmSourceFieldInfoEntity> {

    void insertSourceFieldInfo(@Param("sourceFieldInfo") List<DsmSourceFieldInfoEntity> sourceFieldInfo);

}
