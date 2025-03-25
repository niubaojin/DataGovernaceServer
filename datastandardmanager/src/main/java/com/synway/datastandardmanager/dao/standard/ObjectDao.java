package com.synway.datastandardmanager.dao.standard;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ObjectDao {
    int countObjectByTableId(@Param("tableId")String tableId, @Param("tableName")String tableName);
}
