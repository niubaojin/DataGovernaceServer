package com.synway.datarelation.dao.datablood;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wangdongwei
 */
@Mapper
@Repository
public interface StandardTableDao {

    /**
     * 根据tableId 获取表英文名
     * @param tableId
     * @return
     */
    String getTableNameById(@Param("tableId") String tableId);
}
