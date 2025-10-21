package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizeInputObjectEntity;
import com.synway.datastandardmanager.entity.vo.InputObjectCreateVO;
import org.apache.ibatis.annotations.Param;

public interface StandardizeInputObjectMapper extends BaseMapper<StandardizeInputObjectEntity> {

    /**
     * 根据以下参数获取输入GUID
     *
     * @param inputDataId     来源系统
     * @param inputSourceCode 来源数据协议
     * @param inputSourceFirm 来源厂商
     */
    String getInputGuidById(@Param("inputDataId") String inputDataId, @Param("inputSourceCode") String inputSourceCode, @Param("inputSourceFirm") int inputSourceFirm);

    /**
     * 获取要添加的数据来源
     */
    InputObjectCreateVO getSourceRelationByTableName(@Param("tableName") String tableName);

}
