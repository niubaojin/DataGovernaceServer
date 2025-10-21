package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.UnitOrganizationDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeUnitManageEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.UnitOrganizationTreeVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UnitOrganizationService {

    /**
     * 获取左侧树
     */
    List<UnitOrganizationTreeVO> getLeftTree();

    /**
     * 查询单位机构管理表格信息
     * @param dto
     */
    PageVO searchUnitOrganizationTable(UnitOrganizationDTO dto);

    /**
     * 增加一条单位机构信息
     */
    String addOneUnitOrganization(StandardizeUnitManageEntity standardizeUnitManage);

    /**
     * 删除一条单位机构信息
     * @param unitCode 机构代码
     */
    String deleteOneUnitOrganization(String unitCode);

    /**
     * 更新一条机构信息
     */
    String updateOneUnitOrganization(StandardizeUnitManageEntity unitManageEntity);

    /**
     * 查询所属地区信息
     */
    List<ValueLabelVO> getAreaInfo();

    /**
     * 导出单位机构信息
     */
    void downloadUnitOrganization(HttpServletResponse response, List<StandardizeUnitManageEntity> unitOrganizationList, String fileName, Object object);

    List<KeyValueVO> getFilterObject();

}
