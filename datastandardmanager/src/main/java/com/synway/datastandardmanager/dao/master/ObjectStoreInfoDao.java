package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.ExternalInterfce.RegisterTableInfo;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.buildtable.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表存贮信息 的 数据库操作类
 * @author
 */
@Mapper
@Repository
public interface ObjectStoreInfoDao extends BaseDAO{
    /**
     * 将建表信息插入到数据库中
     * @param column
     * @param updateFlag
     * @return
     */
    int saveObjectStoreInfo(@Param("item") ObjectStoreInfo column, @Param("flag") int updateFlag);
    // 更新建表信息
    int updateObjectStoreInfo(@Param("item") ObjectStoreInfo column);
    /*刷新已建表时将建表信息批量插入数据库*/
    int saveObjectStoreInfos(@Param("objectStoreInfos") List<ObjectStoreInfo> objectStoreInfos);

    /**
     * 当新增字段的时候 需要修改更新时间
     * @param column
     * @param updateFlag
     * @return
     */
    int updateObjectStoreInfoTime(@Param("item") ObjectStoreInfo column, @Param("flag") int updateFlag);

    /**
     * 获取object所有的表名
     * @return
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<ObjectInfo> getAllTableNameByObject();

    /**
     * 包括项目名的表名
     * @param tableName
     * @return
     */
    @AuthorControl(tableNames ={"synlte.OBJECT_STORE_INFO"},columnNames = {"tableid"})
    List<String> getOldTableName(@Param("tableName")String tableName);

    /**
     * 包括项目名的表名
     * @param tableName
     * @return
     */
    int deleteOldTableName(@Param("tableName")String tableName);


    /**
     * 查询 IMPORT_FLAG = 1 的指定表数据量
     * @param storeType
     * @param tableName
     * @return
     */
    int queryCountByTableName(@Param("storeType")int storeType, @Param("tableName") String tableName);


    /**
     * 更新插入标志
     * @param storeType
     * @param tableName
     * @param importFlag
     * @return
     */
    int updateImportFlagByName(@Param("storeType")int storeType,
                               @Param("tableName") String tableName,
                               @Param("dataId") String dataId,
                               @Param("importFlag") int importFlag);

    /**
     *
     * @param storeType
     * @param tableName
     * @return
     */
    Integer getImportFlagByTableName(@Param("storeType")int storeType,
                                 @Param("tableName") String tableName,
                                 @Param("tableProject") String tableProject,
                                 @Param("dataId") String dataId);


    /**
     * 将建表字段信息插入到数据库OBJECT_STORE_FIELDINFO中
     * @param objectStoreFieldInfo
     * @return
     */
    int saveObjectStoreFieldInfo(@Param("list") List<ObjectStoreFieldInfo> objectStoreFieldInfo);
    // 获取建表信息
    ObjectStoreInfo getObjectStoreInfo(@Param("tableInfoId")String tableInfoId);
    List<ObjectStoreFieldInfo> getObjectStoreFieldInfo(@Param("tableInfoId")String tableInfoId);

    /**
     * 查询object_store_info表中是否存在记录
     * @param tableName 表英文名
     * @param projectName 项目空间
     * @param dataId 数据源id
     * @return
     */
    String searchObjectStoreInfoId(@Param("tableName")String tableName,@Param("projectName")String projectName,
                                   @Param("dataId")String dataId);

    /**
     * 查询object_store_info表中是否存在记录(针对KAFKA/FTP)
     * @param tableName 表英文名
     * @param tableId 项目空间
     * @param dataId 数据源id
     * @return
     */
    String searchObjectStoreInfoIdByTableId(@Param("tableName")String tableName,@Param("tableId")String tableId,
                                            @Param("dataId")String dataId);

    /**
     * 批量查询object_store_info信息
     * @return
     */
    List<RegisterTableInfo> searchObjectStoreInfoList(@Param("list")List<RegisterTableInfo> registerTableInfoList);

    /**
     * 查询所有的object_store_info信息
     * @return
     */
    List<ObjectStoreInfo> searchAllData();
    // 查询object_store_fieldinfo的tableInfoId列表
    List<String> getFieldInfoTableInfoId();

    /**
     * 根据tableInfoId删除对应的object_store_fieldInfo信息
     * @param tableInfoId
     * @return
     */
    int deleteObjectStoreFieldInfo(@Param("tableInfoId")String tableInfoId);
    // 删除object_store_fieldInfo中tableInfoId不存在于object_store_info中的无效数据
    void deleteObjectStoreFieldInfos();

    /**
     * 获取建表信息管理表格数据
     * @param objectStoreInfoParameter 获取表格数据的参数
     * @return
     */
    @AuthorControl(tableNames ={"synlte.OBJECT_STORE_INFO"},columnNames = {"tableid"})
    List<ObjectStoreInfo> searchTableInfo(ObjectStoreInfoParameter objectStoreInfoParameter);

    /**
     * 根据英文表名查询表是否存在
     * @param tableName 表英文名
     * @return
     */
    String searchObjectStoreInfoIdByName(@Param("tableName") String tableName);

    /**
     * 查询所有已建表的tableId
     * @return
     */
    List<String> searchAllTableId();

    /**
     * 查询仓库的该项目空间下是否存在该表
     * @param resId 数据源id
     * @param projectName 项目空间
     * @param tableName 表英文名
     * @return
     */
    int searchDataResourceTable(@Param("resId") String resId,@Param("projectName") String projectName,@Param("tableName") String tableName);

    List<FilterObject> getFilterInfo();

    void updateImportFlag(@Param("objectStoreInfo") ObjectStoreInfo objectStoreInfo);

    List<ObjectInfo> getStandardDataSet();

    List<ObjectStoreFieldInfo> getStandardDataItem(@Param("objectId") String objectId);

    int getCreateTableCount(@Param("tableId") String tableId, @Param("tableName") String tableName);

    void updateBuildTableShowField(String showField, String userName);
    String getBuildTableShowField(String userName);
    List<PageSelectOneValue> getStoreTypeList();

    List<String> getSjzybsfList(@Param("objectStoreInfo") ObjectStoreInfo objectStoreInfo);
    // 删除ObjectStoreInfo
    void delObjectStoreInfoByTableInfoId(@Param("objectStoreInfo") ObjectStoreInfo objectStoreInfo);
    // 删除ObjectStoreFieldInfo
    void delObjectStoreFieldInfoByTableInfoId(@Param("objectStoreInfo") ObjectStoreInfo objectStoreInfo);
    // 获取objectStoreFieldInfo字段个数
    int getOldOSFInfoSum(@Param("tableInfoId") String tableInfoId);

}
