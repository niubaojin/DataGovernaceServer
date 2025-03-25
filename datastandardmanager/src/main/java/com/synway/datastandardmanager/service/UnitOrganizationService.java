package com.synway.datastandardmanager.service;


import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationParameter;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationPojo;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationTree;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 单位机构管理Service
 * @author obito
 */
public interface UnitOrganizationService {

    /**
     * 获取左侧树
     * @return
     */
    List<UnitOrganizationTree> getLeftTree();

    /**
     * 查询单位机构管理表格信息
     * @param unitOrganizationParameter
     * @return
     */
    Map<String,Object> searchUnitOrganizationTable(UnitOrganizationParameter unitOrganizationParameter);

    /**
     * 增加一条单位机构信息
     * @param unitOrganizationPojo 单位机构pojo
     * @return
     */
    String addOneUnitOrganization(UnitOrganizationPojo unitOrganizationPojo);

    /**
     * 删除一条单位机构信息
     * @param unitCode 机构代码
     * @return
     */
    String deleteOneUnitOrganization(String unitCode);

    /**
     * 更新一条机构信息
     * @param unitOrganizationPojo
     * @return
     */
    String updateOneUnitOrganization(UnitOrganizationPojo unitOrganizationPojo);

    /**
     * 查询所属地区信息
     * @return
     */
    List<LayuiClassifyPojo> getAreaInfo();

    /**
     * 导出单位机构信息
     * @return
     */
    void downloadUnitOrganization(HttpServletResponse response, List<UnitOrganizationPojo> unitOrganizationList, String fileName,
                                  Object object);

    List<FilterObject> getFilterObject();
}
