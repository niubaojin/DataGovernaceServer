package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.SourceFieldInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SourceFieldInfoMapper extends BaseMapper<SourceFieldInfoEntity> {

    void insertSourceFieldInfo(@Param("sourceFieldInfo") List<SourceFieldInfoEntity> sourceFieldInfo);

}
