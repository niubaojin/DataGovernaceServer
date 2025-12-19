package com.synway.datastandardmanager.service;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.DsmStandardTableCreatedEntity;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableVO;

import java.util.List;

public interface CreateTableService {

    /**
     * 创建odps/ads/datahub
     */
    CreateTableVO buildAdsOrOdpsTable(BuildTableInfoVO buildTableInfoVo);

    List<ValueLabelVO> getColumnType(String dataBaseType);

    List<ObjectFieldEntity> columnCorrespondClick(JSONObject jsonObject);

    String showCreateTableSql(BuildTableInfoVO buildTableInfoVo);

    List<DsmStandardTableCreatedEntity> getAllStandardTableCreatedList(String tableId);

    /**
     * 创建 hive/hbase/clickhouse
     */
    CreateTableVO createHuaWeiTableService(BuildTableInfoVO buildTableInfoVo);

    /**
     * 获取需要插入的公共字段信息
     */
    List<ObjectFieldEntity> getCommonColumnService(List<ObjectFieldEntity> objectFieldEntities);

    /**
     * 获取分区类型下拉列表
     */
    List<ValueLabelVO> getPartitionType();

}
