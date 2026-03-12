package com.synway.property.service;


import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.organizationdetail.DataNum;
import com.synway.property.pojo.organizationdetail.DataResourceImformation;
import com.synway.property.pojo.organizationdetail.DataStatistics;
import com.synway.property.pojo.RequestParameter;
import com.synway.property.pojo.organizationdetail.ResourceRegisterInfo;

import java.util.List;

/**
 * @author majia
 */
public interface OrganizationDetailService {
    List getTableExampleData(String tableProject, String tableNameEn, String tableType, String resourceId);

    List<DataStatistics> getDataCountStatistics(RequestParameter requestParameter);

    List getTableStructure(String tableProject, String tableNameEn, String tableType, String resourceId);

    DataNum getDataNum(String tableProject, String tableNameEn, String tableType) throws Exception;

    String getResourceId(String tableProject, String tableNameEn, String tableType) throws Exception;

    DataResourceImformation getDataResourceInfo(String tableProject, String tableNameEn, String tableType, String resourceId);

    DetailedTableByClassify getTableOrganizationInfo(String tableProject, String tableNameEn, String tableType);
}
