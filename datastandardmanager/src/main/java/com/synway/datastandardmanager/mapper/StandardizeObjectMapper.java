package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeObjectEntity;
import org.apache.ibatis.annotations.Param;

public interface StandardizeObjectMapper extends BaseMapper<StandardizeObjectEntity> {

    int getStandardizeObjectCount(@Param("tableId") String tableId);

    String getObjGuid(@Param("tableId") String tableId, @Param("sysId") String sysId);

    String getOutputGuidNotInInput(@Param("tableId") String tableId);

    String getObjGuidByTreeParam(
            @Param("sourceProtocol") String sourceProtocol,
            @Param("sourceSystem") String sourceSystem,
            @Param("sourceFirm") int sourceFirm);

}
