package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;

import java.util.List;

/**
 * @author wangdongwei
 */
public interface DbManageService {

    /**
     * 创建odps/ads/datahub
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    String buildAdsOrOdpsTable(BuildTableInfoVo buildTableInfoVo) throws Exception;

    /**
     * 创建 hive/hbase/clickhouse
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    String createHuaWeiTableService(BuildTableInfoVo buildTableInfoVo) throws Exception;


    /**
     * 获取需要插入的公共字段信息
     * @param allObjectList
     * @return
     * @throws Exception
     */
    List<ObjectField> getCommonColumnService(List<ObjectField> allObjectList) throws Exception;

    /**
     *
     * odps/ads建表，先判断相关参数是否正确，然后向审批流程发送数据
     */
    String buildTableToApprovalInfoService(BuildTableInfoVo buildTableInfoVo) throws Exception;

    /**
     *  hbase/hive 的建表 先判断相关参数是否正确，然后向审批流程发送数据
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    String buildHuaWeiTableToApprovalInfoService(BuildTableInfoVo buildTableInfoVo) throws Exception;

    /**
     * 获取分区类型下拉列表
     */
    List<PageSelectOneValue> getPartitionType();

    /**
     * 查询仓库项目空间下表是否存在
     * @param resId 数据源id
     * @param projectName 项目空间名称
     * @param tableName 表英文名
     * @return
     */
    Boolean searchDataResourceTable(String resId,String projectName,String tableName);

}
