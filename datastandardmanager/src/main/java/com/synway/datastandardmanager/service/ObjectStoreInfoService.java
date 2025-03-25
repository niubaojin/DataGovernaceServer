package com.synway.datastandardmanager.service;


import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.pojo.ExternalInterfce.RegisterTableInfo;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.StandardTableCreated;
import com.synway.datastandardmanager.pojo.buildtable.*;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.pojo.warehouse.ProjectInfo;

import java.util.List;
import java.util.Map;

/**
 * 物理表存储表的操作接口
 * @author
 */
public interface ObjectStoreInfoService {

    /**
     * 建表成功之后将相关信息存储在表OBJECT_STORE_INFO中
     * @param buildTableInfoVo
     */
    void saveObjectStoreInfoFromAliyun(BuildTableInfoVo buildTableInfoVo) throws Exception;

    /**
     * 建表成功之后将相关信息存储在表OBJECT_STORE_INFO中
     * @param buildTableInfoVo
     */
    void saveObjectStoreInfoFromHuaWei(BuildTableInfoVo buildTableInfoVo) throws Exception;

    /**
     *
     * @param data
     * @param flag 1: 表示新增字段，只更新时间  2：表示是更新 是否自动入库 需要更新时间和是否自动入库的字段
     */
    void updateColumnToInfo(SaveColumnComparision data,int flag) throws Exception;

    /**
     *  刷新 已建表信息
     * @param refreshCreatedPojo
     * @throws Exception
     */
    void refreshCreateTable(RefreshCreatedPojo refreshCreatedPojo) throws Exception;

    /**
     * 刷新所有的已建表信息
     * @param
     * @throws Exception
     */
    void  refreshAllCreateTable(RefreshCreatedPojo refreshCreatedPojo) throws Exception;

    /**
     * 获取是否自动更新
     * @return
     */
    int getImportFlagByTableName(StandardTableCreated standardTableCreated);

    /**
     * 查询object_store_info是否存在，存在则返回，不存在则增加且返回
     * @param registerTableInfos
     * @return
     */
    List<RegisterTableInfo> searchOrCreateObjectStoreInfo(List<RegisterTableInfo> registerTableInfos) throws Exception;

    /**
     * 获取建表信息管理的表格数据
     * @param objectStoreInfoParameter
     * @return
     */
    Map<String,Object> searchTableInfo(ObjectStoreInfoParameter objectStoreInfoParameter);

    /**
     * 刷新建表存储位置(新版刷新表)
     * @return
     */
    void refreshTable(RefreshCreatedPojo refreshCreatedPojo);

    /**
     * 根据数据中心获取数据源信息
     * @param dataCenterId 数据中心
     * @return
     */
    List<PageSelectOneValue> getDataResource(String dataCenterId, String storeType);

    List<String> getProjectList(String resId);

    BuildTableFilterObject getFilterInfo();

    // 更新输出入库状态
    void updateImportFlag(ObjectStoreInfo objectStoreInfo);
    // 获取标准数据集
    List<ObjectInfo> getStandardDataSet();
    // 获取仓库表数据
    List<DetectedTable> getDetectedTableList(String resId, String projectName);

    // 获取标准数据项
    List<ObjectStoreFieldInfo> getStandardDataItem(String objectId);
    // 获取仓库表字段信息
    List<FieldInfo> getDetectedFieldInfo(String resId, String projectName, String tableName);
    // 判断tableInfoId是否存在
    boolean existTableInfoId(String tableName, String projectName, String resId, String tableId);

    // 保存建表信息
    String saveCreateTableInfo(ObjectStoreInfo objectStoreInfo);
    // 获取建表信息
    ObjectStoreInfo getCreateTableInfo(ObjectStoreInfo objectStoreInfo);

    void refreshTableSync(RefreshCreatedPojo refreshCreatedPojo);

    void refreshCreateTableOneTable(RefreshCreatedPojo refreshCreatedPojo);

    // 保存显示字段
    void updateBuildTableShowField(String queryParams);
    // 获取显示字段
    List<String> getBuildTableShowField();
    // 获取平台类型列表
    List<PageSelectOneValue> getStoreTypeList();

    // 删除ObjectStore相关信息
    ServerResponse deleteObjectStore(ObjectStoreInfo objectStoreInfo);

}
