package com.synway.datastandardmanager.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName FieldComparisionDao
 * @description TODO
 * @date 2020/9/29 13:36
 */
@Mapper
@Repository
public interface FieldComparisionDao {

    /**
     * 根据tableName 来判断是有工作流的节点名称
     * @param tableName
     * @param type  0：表示是表m_node_in_out_table    1：表示是SYNDT_IN_OUT_TABLE_ANALYZE
     * @return
     */
    List<String> getNodeNameByTableName(@Param("tableName")String tableName,@Param("type")int type);

    /**
     * storecycleanduseheat表中指定表的使用次数
     * @param tableName
     * @return
     */
    int getTableUsedCount(@Param("tableName")String tableName);
}
