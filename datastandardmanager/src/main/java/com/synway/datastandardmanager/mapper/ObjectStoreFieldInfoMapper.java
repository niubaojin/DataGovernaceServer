package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.ObjectStoreFieldInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObjectStoreFieldInfoMapper extends BaseMapper<ObjectStoreFieldInfoEntity> {

    void deleteObjectStoreFieldInfos();

    /**
     * 将建表字段信息插入到数据库OBJECT_STORE_FIELDINFO中
     */
    int saveObjectStoreFieldInfo(@Param("list") List<ObjectStoreFieldInfoEntity> objectStoreFieldInfoEntities);

}
