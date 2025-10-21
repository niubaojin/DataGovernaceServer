package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.UnitOrganizationDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeUnitManageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardizeUnitManageMapper extends BaseMapper<StandardizeUnitManageEntity> {

    /**
     * 根据父级单位机构编码查询单位机构信息
     *
     * @param parentUnitCode：父级单位机构编码
     */
    List<StandardizeUnitManageEntity> searchUnitOrganizationListByParentId(@Param("parentUnitCode") String parentUnitCode,
                                                                           @Param("searchText") String searchText);

    /**
     * 表格查询相关的数据
     */
    List<StandardizeUnitManageEntity> searchUnitOrganizationTable(UnitOrganizationDTO unitOrganizationDTO);

    /**
     * 更新一条单位机构信息
     */
    int updateOneUnitOrganization(StandardizeUnitManageEntity unitManageEntity);

}
