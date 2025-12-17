package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.CommonDTO;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.dto.ObjectStoreInfoDTO;
import com.synway.datastandardmanager.entity.dto.RefreshCreateTableDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.ObjectStoreInfoEntity;
import com.synway.datastandardmanager.entity.vo.BuildTableFilterVO;
import com.synway.datastandardmanager.entity.vo.DetectedFieldInfoVO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.warehouse.DetectedTable;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface BuildTableInfoManageService {

    /**
     * @param flag 1: 表示新增字段，只更新时间  2：表示是更新 是否自动入库 需要更新时间和是否自动入库的字段
     */
    void updateColumnToInfo(CreateTableInfoDTO data, int flag);

    /**
     * 刷新所有的已建表信息
     */
    void refreshCreateTableAll(RefreshCreateTableDTO refreshCreateTableDTO);

    void refreshCreateTableProject(RefreshCreateTableDTO refreshCreateTableDTO) throws Exception;

    void refreshCreateTableOneTable(RefreshCreateTableDTO refreshCreateTableDTO);

    /**
     * 获取建表信息管理的表格数据
     */
    PageVO searchTableInfo(ObjectStoreInfoDTO objectStoreInfoDTO);

    /**
     * 根据数据中心获取数据源信息
     */
    List<KeyValueVO> getDataResource(String dataCenterId, String storeType);

    List<String> getProjectList(String resId) throws Exception;

    BuildTableFilterVO getFilterInfo();

    void updateImportFlag(ObjectStoreInfoEntity objectStoreInfo);

    List<ObjectStoreInfoEntity> getStandardDataSet();

    // 获取仓库表数据
    List<DetectedTable> getDetectedTableList(String resId, String projectName);

    // 获取标准数据项
    List<ObjectFieldEntity> getStandardDataItem(String objectId);

    DetectedFieldInfoVO getDetectedFieldInfo(ObjectStoreInfoEntity objectStoreInfo);

    // 保存建表信息
    String saveCreateTableInfo(ObjectStoreInfoEntity objectStoreInfo);

    // 获取建表信息
    ObjectStoreInfoEntity getCreateTableInfo(ObjectStoreInfoEntity objectStoreInfo);

    // 保存显示字段
    boolean updateBuildTableShowField(CommonDTO showField);

    // 获取显示字段
    List<String> getBuildTableShowField();

    // 获取平台类型列表
    List<KeyValueVO> getStoreTypeList();

    // 删除ObjectStore相关信息
    String deleteObjectStore(ObjectStoreInfoEntity objectStoreInfo);

    /**
     * 建表成功之后将相关信息存储在表OBJECT_STORE_INFO中
     */
    void saveObjectStoreInfoFromAliyun(BuildTableInfoVO buildTableInfoVo);

    /**
     * 建表成功之后将相关信息存储在表OBJECT_STORE_INFO中
     */
    void saveObjectStoreInfoFromHuaWei(BuildTableInfoVO buildTableInfoVo);

    void downloadObjectStoreInfo(HttpServletResponse response);

}
