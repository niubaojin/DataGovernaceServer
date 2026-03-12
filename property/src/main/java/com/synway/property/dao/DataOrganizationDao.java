package com.synway.property.dao;

import com.synway.property.entity.dto.DataOrganizationDTO;
import com.synway.property.interceptor.AuthorControl;
import com.synway.property.pojo.PageSelectOneValue;
import com.synway.property.pojo.dataOrganization.DataOrganization;
import com.synway.property.pojo.formorganizationindex.PublicDataInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/15 13:59
 */
@Mapper
@Repository
public interface DataOrganizationDao {


//    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    List<PageSelectOneValue> getPageSecondaryClassify(@Param("dataOrganizationType") String dataOrganizationType);

    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    List<PublicDataInfo> getCIAD();

    List<String> getAllManufacturers();

    List<String> getAuthorities();

    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    Set<DataOrganization> getDataOrganizationTable(@Param("primaryClassifyCh") String primaryClassifyCh,
                                                   @Param("secondaryClassifyCh") String secondaryClassifyCh,
                                                   @Param("classifyid") String classifyid,
                                                   @Param("search") String search,
                                                   @Param("sjzzflCodeId") String sjzzflCodeId);

//    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    Set<DataOrganization> getDataInfoTable(@Param("manufacturer") String manufacturer,
                                           @Param("authority") String authority);

//    Set<DataOrganization> getOrganizationAssetsTable(@Param("primaryClassifyCh")String primaryClassifyCh,
//                                                     @Param("secondaryClassifyCh") String secondaryClassifyCh,
//                                                     @Param("tableType") String tableType,
//                                                     @Param("tableProject") String tableProject);
    Set<DataOrganization> getOrganizationAssetsTable(@Param("primaryClassifyCh")String primaryClassifyCh,
                                                     @Param("secondaryClassifyCh") String secondaryClassifyCh,
                                                     @Param("daysAgo") int daysAgo,
                                                     @Param("dataOrgLevel") int dataOrgLevel);

    @AuthorControl(tableNames ={"synlte.OBJECT_STORE_INFO"},columnNames = {"tableid"})
    Set<DataOrganization> getObjectInfos(@Param("level") int level,
                                         @Param("levelCode") String levelCode,
                                         @Param("search") String search);

    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    Set<DataOrganization> getDataOrganizationTableInfo(DataOrganizationDTO dto);

}
