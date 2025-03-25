package com.synway.property.dao;

import com.synway.property.interceptor.AuthorControl;
import com.synway.property.pojo.ClassifyInformation;
import com.synway.property.pojo.FoucsOrganizationFull;
import com.synway.property.pojo.datastoragemonitor.UserAuthorityData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author majia
 */
@Mapper
@Repository
public interface FoucsOrganizationDao {
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<FoucsOrganizationFull>  getAllFoucsOrganizationDao();
    List<ClassifyInformation> getClassifyInformationTableDao(@Param("mainClassifyCh") String mainClassifyCh,
                                                             @Param("primaryClassifyCh") String primaryClassifyCh);
    @AuthorControl(tableNames ={"Focus_Organization_add_monitor"},columnNames = {"resourceid"})
    List<ClassifyInformation> getAllClassifyInformationTableDao();
    @AuthorControl(tableNames ={"Focus_Organization_add_monitor"},columnNames = {"resourceid"})
    List<String> getAddClassifyNameList();
    // 将需要插入的数据插入到对应的表中
    Integer insertClassifyInformationTableDao(List<ClassifyInformation> insertDataList);

    // 插入重点组织数据对应的权限表
    Integer insertUserAuthorityDataListDao(List<UserAuthorityData> insertDataList);

    // 删除所有的重点表监控信息
    Integer delAllClassifyInformationTableDao();

    // 删除所有的重点表监控信息
    Integer delUserAuthorityDataListDao(@Param("userId") int userId);

    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<Map<String,Object>> getDayUseHeatDao(@Param("daysAgo") int daysAgo);
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<Map<String,Object>> getFullDataRankingDao(@Param("daysAgo") int daysAgo);
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<Map<String,Object>> getIncrementalDataRankingDao(@Param("daysAgo") int daysAgo);

    //  获取 左侧tree的相关数据
    List<Map<String,String>> getClassTreeListDao();
}
