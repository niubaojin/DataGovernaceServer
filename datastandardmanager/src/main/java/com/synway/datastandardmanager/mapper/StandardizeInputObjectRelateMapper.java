package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeInputObjectRelateEntity;
import com.synway.datastandardmanager.entity.vo.CommonVO;
import com.synway.datastandardmanager.entity.vo.InputObjectCreateVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.interceptor.AuthorControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardizeInputObjectRelateMapper extends BaseMapper<StandardizeInputObjectRelateEntity> {

    List<InputObjectCreateVO> getAllInputObjectRelation(@Param("tableId") String tableId);

    /**
     * 查询表中是否有存在的数据
     */
    CommonVO queryStdInputObjRelExist(@Param("outputGuid") String outputGuid,
                                      @Param("inputGuid") String inputGuid,
                                      @Param("inputIobjSource") int inputIobjSource);

    int getStdInputObjRelCount(@Param("inputObjGuid") String inputObjGuid,
                               @Param("tableId") String tableId);

    /**
     *  根据输入协议获取到输出协议的相关信息
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<ValueLabelVO> getStandardOutTableIdBySourceIdDao(@Param("sourceId")String sourceId,
                                                          @Param("sourceCode")String sourceCode,
                                                          @Param("sourceFirmCode")String sourceFirmCode);

}
