package com.synway.datarelation.dao.datablood;

import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.lineage.ColumnLineDb;
import com.synway.datarelation.pojo.lineage.ColumnLineageEdges;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LineageColumnParsingDao extends BaseDAO {

    /**
     * 删除字段血缘表中所有的数据信息
     * @return
     */
    int deleteColumnLineage();

    /**
     * 插入数据到字段血缘信息
     * @return
     */
    int insertColumnLineage(@Param("rcList")List<ColumnLineDb> columnLineDbs);

    /**
     *  从数据库中获取建表字段信息
     * @param tableName
     * @return
     */
    List<String> getDbColumnByTable(@Param("tableName") String tableName);

    /**
     *
     * @param inputTableName  输入的表名
     * @param outputTableName 输出的表名
     * @param columnName  字段名
     * @param type  这个字段是哪个类型  input   output
     * @return
     */
    List<ColumnLineageEdges> getColumnRelationByTableName(@Param("inputTableName") String inputTableName,
                                                          @Param("outputTableName") String outputTableName,
                                                          @Param("columnName") String columnName,
                                                          @Param("type") String type);


    List<ColumnLineDb> getAllColumnDb();
}
