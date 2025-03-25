package com.synway.property.service;

import com.synway.property.pojo.dataOrganization.DataOrganization;
import com.synway.property.pojo.dataOrganization.ReturnResult;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;

import java.util.List;
import java.util.Set;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/15 13:47
 */
public interface DataOrganizationService {
    List getPageSecondaryClassify(String dataOrganizationType);

    // 获取数据组织左侧树数据
    List<ClassifyInfoTree> getPageSecondaryClassifyTree(String dataOrganizationType);

    List getAllManufacturers();

    List getAuthorities();

    ReturnResult getDataOrganization(String dataOrganizationType, String classify, String classifyid, String manufacturer, String authority, String search, String dataSet);
}
