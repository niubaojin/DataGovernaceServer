package com.synway.datastandardmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DsProjectTableMapper {

    /**
     * 查询仓库的该项目空间下是否存在该表
     *
     * @param resId       数据源id
     * @param projectName 项目空间
     * @param tableName   表英文名
     */
    @Select("select count(*) from ds_project_table dp where upper(dp.table_name_en) = upper(#{tableName}) and dp.project_name = #{projectName} and dp.res_id = #{resId}")
    Integer searchDataResourceTable(@Param("resId") String resId, @Param("projectName") String projectName, @Param("tableName") String tableName);

}
