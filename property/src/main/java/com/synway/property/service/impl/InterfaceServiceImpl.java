package com.synway.property.service.impl;

import com.synway.property.dao.InterfaceDao;
import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.datastoragemonitor.TableOrganizationData;
import com.synway.property.pojo.interfacePojo.ReceiveTable;
import com.synway.property.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 借口对应的service
 *
 * @author majia
 * @version 1.0
 * @date 2020/6/23 11:13
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private InterfaceDao interfaceDao;

    @Override
    public void updateDataCenterName(String dataCenterId, String dataCenterName) {
        interfaceDao.updateDataCenterName(dataCenterId,dataCenterName);
    }

    @Override
    public void updateDataSourceName(String dataSourceId, String dataSourceName) {
        interfaceDao.updateDataSourceName(dataSourceId,dataSourceName);
    }
	
    @Override
    public void updateTableOrganization(TableOrganizationData oldTableOrganizationData, TableOrganizationData newTableOrganizationData) {
        interfaceDao.updateTableOrganization(oldTableOrganizationData,newTableOrganizationData);
    }

    @Override
    public List<DetailedTableByClassify> getTableOrganization(List<ReceiveTable> tables) {
        return interfaceDao.getTableOrganization(tables);
    }

}
