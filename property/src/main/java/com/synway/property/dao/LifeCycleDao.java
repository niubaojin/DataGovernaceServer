package com.synway.property.dao;

import com.synway.property.interceptor.AuthorControl;
import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.RequestParameter;
import com.synway.property.pojo.lifecycle.LifeCycleInfo;
import com.synway.property.pojo.lifecycle.LifeCyclePageParams;
import com.synway.property.pojo.lifecycle.ValDensity;
import com.synway.property.pojo.lifecycle.ValDensityPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2021/2/19 9:50
 */
@Mapper
@Repository
public interface LifeCycleDao {
    @AuthorControl(tableNames ={"DP_TABLE_ORGANIZATION_ASSETS"},columnNames = {"resourceid"})
    List<LifeCycleInfo> getLifeCycleInfo(@Param("queryParams") LifeCyclePageParams queryParams,@Param("daysAgo") int daysAgo);

    @AuthorControl(tableNames ={"DP_TABLE_ORGANIZATION_ASSETS"},columnNames = {"resourceid"})
    List<Map<String, String>> getLifeCycleInfoFilter(@Param("queryParams") LifeCyclePageParams queryParams,@Param("daysAgo") int daysAgo);

    ValDensity getOldValDensity(@Param("queryParams") ValDensityPageParam queryParams);

    void updateValDensity(@Param("queryParams") ValDensityPageParam queryParams, @Param("density") ValDensity density);

    void insertValDensity(@Param("queryParams") ValDensityPageParam queryParams, @Param("density") ValDensity density);

    List<Map> getClassifyNum(@Param("tables") List<DetailedTableByClassify> tables);

    @AuthorControl(tableNames ={"DP_TABLE_ORGANIZATION_ASSETS"},columnNames = {"resourceid"})
    List<DetailedTableByClassify> getAssets();

    @AuthorControl(tableNames ={"DP_TABLE_ORGANIZATION_ASSETS"},columnNames = {"resourceid"})
    Map<String, String> getOrganizationClassify(@Param("queryParams") ValDensityPageParam queryParams);

    void changeValDensityStatus();

    int getCount();

    void updateLifeCycleShowField(@Param("lifeCycleShowFields") String lifeCycleShowFields, @Param("userName") String userName);
    void delLifeCycleShowField(@Param("lifeCycleShowFields") String lifeCycleShowFields, @Param("userName") String userName);

    String getLifeCycleShowField(@Param("userName") String userName);

    void updateLifeCycleForTOA(@Param("requestParam") RequestParameter requestParam);
}
