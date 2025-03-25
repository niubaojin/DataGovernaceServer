package com.synway.property.dao;

import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.organizationdetail.DataNum;
import com.synway.property.pojo.organizationdetail.DataStatistics;
import com.synway.property.pojo.RequestParameter;
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
public interface OrganizationDetailDao {

    List<DataStatistics> getDataCountStatistics(RequestParameter requestParameter);

    DataNum getDataNum(@Param("tableProject") String tableProject,
                       @Param("tableNameEn") String tableNameEn,
                       @Param("tableType") String tableType);

    String getResourceId(@Param("tableProject") String tableProject,
                         @Param("tableNameEn") String tableNameEn,
                         @Param("tableType") String tableType);

    List<DataNum> getRecentDataCountAndSize(@Param("tableProject") String tableProject,
                                            @Param("tableNameEn") String tableNameEn,
                                            @Param("tableType") String tableType);

    String getRegisterTableNameChByEn(@Param("tableNameEn") String tableNameEn);


    DetailedTableByClassify getTableOrganizationInfo(@Param("tableProject") String tableProject,
                                                     @Param("tableNameEn") String tableNameEn,
                                                     @Param("tableType") String tableType);
}
