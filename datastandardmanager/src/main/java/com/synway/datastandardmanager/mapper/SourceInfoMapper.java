package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.SourceInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface SourceInfoMapper extends BaseMapper<SourceInfoEntity> {

    SourceInfoEntity findSourceInfo(@Param("sourceProtocol") String sourceProtocol,
                                    @Param("tableName") String tableName,
                                    @Param("sourceSystem") String sourceSystem,
                                    @Param("dataName") String dataName);

    void insertSourceInfo(@Param("sourceInfo") SourceInfoEntity sourceInfo);

}
