package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.DsmSourceInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface DsmSourceInfoMapper extends BaseMapper<DsmSourceInfoEntity> {

    DsmSourceInfoEntity findSourceInfo(@Param("sourceProtocol") String sourceProtocol,
                                       @Param("tableName") String tableName,
                                       @Param("sourceSystem") String sourceSystem,
                                       @Param("dataName") String dataName);

}
