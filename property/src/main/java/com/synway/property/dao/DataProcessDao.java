package com.synway.property.dao;


import com.synway.property.pojo.DataProcess.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wdw
 */
@Repository
@Mapper
public interface DataProcessDao {

    int saveDataProcess(DataProcess dataProcess);

    List<ProcessPageShow> searchDataProcess(DataProcessRequest request);

    List<ModuleIdSelect> getAllModuleId();

    List<PromptValue> searchValuePrompt(DataProcessRequest request);

    List<String> getInputSourceIdByTableId(@Param("tableId")String tableId);
}
