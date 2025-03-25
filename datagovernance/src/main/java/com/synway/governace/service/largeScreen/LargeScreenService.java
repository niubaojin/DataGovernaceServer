package com.synway.governace.service.largeScreen;

import com.alibaba.nacos.api.exception.NacosException;
import com.synway.governace.pojo.largeScreen.QueryInfo;
import com.synway.governace.pojo.largeScreen.StatisticsResult;

import java.util.List;
import java.util.Map;

/**
 * 数据大屏接口
 *
 * @author ywj
 * @date 2020/7/22 14:53
 */
public interface LargeScreenService {

    // 标准定义统计信息获取
    List<StatisticsResult> standardDefinedStatistics();

    // 拉取昨日数据以得到今日更新数
    void fetchYesterdayData();

    // 更新数据大屏更新数据信息
    void refreshLastUpdateData();

    // 数据质量治理统计
    List<StatisticsResult> getQualityGovernStatistics();

    // 数据处理节点统计
    List<StatisticsResult> getHandleNodeStatistics();

    // 数据组织一级分类统计
    Map<String, Object> getPrimaryClassifyStatistics();

    //数据组织二级分类统计
    List<StatisticsResult> getSecondClassifyStatistics(QueryInfo queryInfo);

    // 数据组织三级分类统计
    List<StatisticsResult> getThirdClassifyStatistics(QueryInfo queryInfo);

    // 数据接入概况统计
    List<StatisticsResult> getDataInceptStatistics() throws NacosException;

    // 数据接入节点信息
    List<StatisticsResult> getInceptNodeStatistics() throws NacosException;

    // 近7日数据接入量
    List<StatisticsResult> getInceptRecentStatistics() throws NacosException;

    // 近7日数据处理总量趋势
    List<StatisticsResult> getHandleRecentStatistics();

    // 数据处理概况信息
    List<StatisticsResult> getDataHandleStatistics();

    // 数据中心统计
    Map<String, Object> getDataCenterStatistics(String platFormType) throws NacosException;
}
