package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.ObjectStoreInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectStoreInfoEntity;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.interceptor.AuthorControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObjectStoreInfoMapper extends BaseMapper<ObjectStoreInfoEntity> {

     /**
     * 查询object_store_info表中是否存在记录
     *
     * @param tableName   表英文名
     * @param projectName 项目空间
     * @param dataId      数据源id
     */
    String searchObjectStoreInfoId(@Param("tableName") String tableName,
                                   @Param("projectName") String projectName,
                                   @Param("dataId") String dataId);

    /**
     * 查询object_store_info表中是否存在记录(针对KAFKA/FTP)
     *
     * @param tableName 表英文名
     * @param tableId   项目空间
     * @param dataId    数据源id
     * @return
     */
    String searchObjectStoreInfoIdByTableId(@Param("tableName") String tableName,
                                            @Param("tableId") String tableId,
                                            @Param("dataId") String dataId);

    /**
     * 获取建表信息管理表格数据
     */
    @AuthorControl(tableNames ={"synlte.OBJECT_STORE_INFO"},columnNames = {"tableid"})
    List<ObjectStoreInfoEntity> searchTableInfo(ObjectStoreInfoDTO objectStoreInfoDTO);

    List<ValueLabelVO> getFilterInfo();

    /**
     * 更新建表信息
     */
    int updateObjectStoreInfo(@Param("item") ObjectStoreInfoEntity column);

    /**
     * 查询所有已建表的tableId
     */
    List<String> searchAllTableId();

}
