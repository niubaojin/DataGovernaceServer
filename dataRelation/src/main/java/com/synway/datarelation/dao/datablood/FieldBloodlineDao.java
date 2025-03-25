package com.synway.datarelation.dao.datablood;

import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.databloodline.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangdongwei
 */
@Mapper
@Repository
public interface FieldBloodlineDao extends BaseDAO {

    /**
     *  删除字段信息
     * @param tableName
     * @param tableId
     * @param type
     * @return
     */
    int deleteFieldColumnByNameOrId(@Param("tableName") String tableName,
                                    @Param("tableId") String tableId,
                                    @Param("type") String type);

    /**
     * 向数据库中插入数据
     * @param fieldColumnList
     * @return
     */
    int insertFieldColumn(@Param("rcList") List<FieldColumn> fieldColumnList);


    List<QueryColumnTable> selectColumnRelationById(@Param("nodeId") String nodeId,
                                                    @Param("type") String type);

    List<QueryColumnTable> selectColumnById(@Param("nodeId") String nodeId);


    List<ColumnRelationDB> getAllColumnRelationDBByType(@Param("type") String type);


    List<FieldColumn> getAllColumnAli();

    List<String> getTableColumnRelation(@Param("sourceNodeId") String sourceNodeId,
                                        @Param("targetNodeId") String targetNodeId,
                                        @Param("columnName") String columnName,
                                        @Param("queryType") String queryType,
                                        @Param("flag") int flag);

    /**
     * 删除字段的所有数据
     * @return
     */
    int deleteAllColumnBloodLine();

    int deleteAllColumnBloodLineRelation();
    int deleteAllTableColumn();

    /**
     *  获取 tableId 和 objectId 的相关信息
     * @return
     */
    List<TableClassify> getAllTableIdObjectId();


    /**
     * 获取 synlte.object_store_info 表中的所有数据
     * @return
     */
    List<ObjectStoreColumn> getAllObjectStoreColumn();


    /**
     * 定时清除1个月前的数据
     * @return
     */
    int deleteFlowThree();


    int deleteBusinessInstance();

    /**
     * 删除表 m_node_in_out_table 30天前的数据
     * @return
     */
    int deleteNodeInOutTable();

  
}
