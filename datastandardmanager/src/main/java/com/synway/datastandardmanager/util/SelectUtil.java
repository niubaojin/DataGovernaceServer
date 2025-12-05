package com.synway.datastandardmanager.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.mapper.FieldCodeValMapper;
import com.synway.datastandardmanager.mapper.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 常用的数据库查询语句
 *
 * @author nbj
 * @Date 2025年8月14日13:33:08
 */
public class SelectUtil {

    // 根据tableId获取object数据
    public static ObjectEntity getObjectEntityByObjectId(ObjectMapper objectMapper, Integer objectId) {
        LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ObjectEntity::getObjectId, objectId);
        return objectMapper.selectOne(wrapper);
    }

    // 根据tableId获取object数据
    public static ObjectEntity getObjectEntityByTableId(ObjectMapper objectMapper, String tableId) {
        LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ObjectEntity::getTableId, tableId);
        return objectMapper.selectOne(wrapper);
    }

    public static Long countObjectByTableId(ObjectMapper objectMapper, String tableId, String tableName){
        LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ObjectEntity::getTableId, tableId);
        wrapper.eq(ObjectEntity::getRealTablename, tableName);
        return objectMapper.selectCount(wrapper);
    }

    public static List<FieldCodeValEntity> selectCodeValTableByCodeId(FieldCodeValMapper fieldCodeValMapper, String codeId, String valValue, String valText) {
        LambdaQueryWrapper<FieldCodeValEntity> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(codeId)) wrapper.eq(FieldCodeValEntity::getCodeId, codeId);
        if (StringUtils.isNotBlank(valValue)) wrapper.apply("lower(valValue) like lower({0})", "%" + valValue.toLowerCase() + "%");
        if (StringUtils.isNotBlank(valText)) wrapper.apply("lower(valText) like lower({0})", "%" + valText.toLowerCase() + "%");
        return fieldCodeValMapper.selectList(wrapper);
    }

}
