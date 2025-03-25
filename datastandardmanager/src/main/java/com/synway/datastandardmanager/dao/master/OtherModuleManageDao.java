package com.synway.datastandardmanager.dao.master;


import com.synway.datastandardmanager.pojo.sourcedata.OtherModuleManageCreated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OtherModuleManageDao {

    List<String> getOtherModuleObjectCreated(@Param("moduleName")String moduleName);

    int getObjectCreatedById(@Param("tableId")String tableId, @Param("moduleName")String moduleName);


    int addOtherModuleCreated(OtherModuleManageCreated data);
}
