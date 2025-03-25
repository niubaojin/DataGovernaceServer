package com.synway.property.dao;

import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.datastoragemonitor.TableOrganizationData;
import com.synway.property.pojo.interfacePojo.ReceiveTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/6/23 11:16
 */
@Mapper
@Repository
public interface InterfaceDao {

    void updateDataCenterName(@Param("dataCenterId") String dataCenterId, @Param("dataCenterName") String dataCenterName);

    void updateDataSourceName(@Param("dataSourceId") String dataSourceId, @Param("dataSourceName") String dataSourceName);
	
    void updateTableOrganization(TableOrganizationData oldTableOrganizationData, TableOrganizationData newTableOrganizationData);

    List<DetailedTableByClassify> getTableOrganization(@Param("tables") List<ReceiveTable> tables);
}
