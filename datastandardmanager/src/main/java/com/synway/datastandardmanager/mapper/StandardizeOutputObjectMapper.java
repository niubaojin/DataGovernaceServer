package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeOutputObjectEntity;
import com.synway.datastandardmanager.entity.vo.InputObjectCreateVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardizeOutputObjectMapper extends BaseMapper<StandardizeOutputObjectEntity> {

    List<InputObjectCreateVO> getAllInputObject(@Param("tableId") String tableId);

    String getOutputGuidByTableId(@Param("tableId")String tableId);

    String getOutPutObjGuidByTableId(@Param("tableId") String tableId,
                                     @Param("sysId") String sysId,
                                     @Param("ownerFactoryNum") int ownerFactoryNum);

    String getOObjGuidByTableId(@Param("tableId") String tableId);

    List<InputObjectCreateVO> getAllInputObjects(@Param("tableIds") List<String> tableIds);

}
