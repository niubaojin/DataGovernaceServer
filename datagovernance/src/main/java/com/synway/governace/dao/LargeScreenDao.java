package com.synway.governace.dao;

import com.synway.governace.pojo.largeScreen.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据大屏dao
 *
 * @author ywj
 * @date 2020/7/23 9:07
 */
@Mapper
@Repository
public interface LargeScreenDao {

    // 数据定义统计
    StatisticsResult standardStatistics();

    // 数据定义更新统计
    BigDecimal standardUpdateStatistics();

    // 元素编码统计
    StatisticsResult metadataStatistics();

    // 语义类型统计
    StatisticsResult semanticStatistics();

    // 国标字典统计
    StatisticsResult nationalCodeStatistics();

    // 查询数据大屏数据信息
    List<LargeScreenData> queryLargeScreenData(LargeScreenData query);

    // 保存数据大屏更新数据信息
    void saveLargeScreenData(LargeScreenData data);

    // 删除数据大屏更新数据信息
    void deleteLargeScreenData(LargeScreenData data);

    // 数据治理节点统计
    List<StatisticsResult> getHandleNodeStatistics();

    // 数据组织一级分类统计
    List<StatisticsResult> getPrimaryClassifyStatistics(@Param("sjzzflCodeId") String sjzzflCodeId);

    // 数据组织二级分类统计
    List<StatisticsResult> getSecondClassifyStatistics(QueryInfo queryInfo);

    // 数据组织三级分类统计
    List<StatisticsResult> getThirdClassifyStatistics(QueryInfo queryInfo);

    // 数据治理效果统计
    List<DataGovernanceEffect> getQualityGovernStatistics(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 接入数据统计
    List<DataInceptInfo> getInceptDataStatistics(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 下发数据统计
    StatisticsResult getDistributeDataStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime);

    // 在更/停更数据统计
    List<DataAssetsInfo> getUpdateDataStatistics(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 标准接入数据统计
    List<DataInceptInfo> getStandardDataStatistics(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 数据处理总量统计
    List<StatisticsResult> getHandleDataStatistics();

    // 数据处理源协议数据统计
    BigDecimal getSourceDataStatistics(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 数据处理入库统计
    StatisticsResult getHandleDataStorageStatistics(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 近七天接入数据量
    List<DataInceptInfo> queryRecentDataCount();

    // 根据数据协议分组
    List<DataInceptInfo> queryStandardResourceId();

    // 根据节点、数据协议分组
    List<DataInceptInfo> queryStandardNodeInstanceId(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
