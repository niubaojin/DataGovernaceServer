package com.synway.datastandardmanager.dao.standard;

import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.pojo.StandardTableCreated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TableOrganizationDao {
    void insertInfo(@Param("buildTableInfoVo") BuildTableInfoVo buildTableInfoVo);
    void insertInfoTemp(@Param("buildTableInfoVo")BuildTableInfoVo buildTableInfoVo);
    // 在 表 STANDARD_TABLE_CREATED中插入对应的数据
    int insertCreatedMessage(@Param("objectId")String objectId,
                             @Param("tableName")String tableName,
                             @Param("tableProject")String tableProject,
                             @Param("tableBase")String tableBase,
                             @Param("id")String id,
                             @Param("tableNameCh")String tableNameChzs,
                             @Param("dataCenterName")String dataCenterName,
                             @Param("dataResourceName")String dataResourceName,
                             @Param("dataId")String dataId);
    int deleteCreatedTable(
            @Param("objectId")String objectId,
            @Param("tableName")String tableName,
            @Param("tableProject")String tableProject,
            @Param("tableBase")String tableBase
    );

    List<StandardTableCreated> getAllStandardTableCreatedList(
            @Param("tableName")String tableName
    );

    List<String> getCreatedUserByTableName(@Param("tableName")String tableName);

    List<StandardTableCreated> getCreatedTableListByTableId(@Param("tableId")String tableId);

    /**
     * 获取创建时间
     * @param tableName
     * @return
     */
    String getcreatedTimeStrByTableName(@Param("tableName")String tableName);



}
