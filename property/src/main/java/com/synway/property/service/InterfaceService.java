package com.synway.property.service;

import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.datastoragemonitor.TableOrganizationData;
import com.synway.property.pojo.interfacePojo.ReceiveTable;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/6/23 11:13
 */
public interface InterfaceService {
    void updateDataCenterName(String dataCenterId, String dataCenterName);

    void updateDataSourceName(String dataSourceId, String dataSourceName);
	
    void updateTableOrganization(TableOrganizationData oldTableOrganizationData, TableOrganizationData newTableOrganizationData);

    List<DetailedTableByClassify> getTableOrganization(List<ReceiveTable> tables);
}
