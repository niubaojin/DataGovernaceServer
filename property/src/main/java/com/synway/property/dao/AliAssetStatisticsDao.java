package com.synway.property.dao;

import com.synway.property.pojo.aliAssetStatistics.SyndmgTableAll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 获取阿里资产信息
 * @Author
 * @Date 2021年12月3日16:13:00
 */
@Mapper
@Repository
public interface AliAssetStatisticsDao extends BaseDAO {

    /**
     * 插入数据至表：synlte.syndmg_table_all
     * @param syndmgTableAlls
     */
    void insertTableInfo(List<SyndmgTableAll> syndmgTableAlls);

    /**
     * 删除当天数据
     * @param syndmgTableAll
     */
    void delTodayData(@Param("syndmgTableAll") SyndmgTableAll syndmgTableAll);
    int delTodayDataAll(@Param("syndmgTableAll") SyndmgTableAll syndmgTableAll);
    int delTodayDataAllByOdps(@Param("syndmgTableAll") SyndmgTableAll syndmgTableAll);

    /**
     * 删除历史数据：syndmgTableAll
     */
    void delHisData(@Param("syndmgTableAll") SyndmgTableAll syndmgTableAll, @Param("minDelDate") String minDelDate);

    /**
     * 更新数据
     * @param syndmgTableAll
     */
    void updateSyndmgTableAll(@Param("syndmgTableAll") SyndmgTableAll syndmgTableAll);

    List<SyndmgTableAll> getTableAllCount(@Param("syndmgTableAll") SyndmgTableAll syndmgTableAll, @Param("dBefore") long dBefore);
}
