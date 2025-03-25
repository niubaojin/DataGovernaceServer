package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationParameter;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationPojo;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
@Mapper
@Repository
public interface UnitOrganizationDao {

    /**
     * 获取左侧树信息
     *
     * @param unitType 单位机构类型
     * @return
     */
    List<UnitOrganizationTree> getLeftTreeInfo(@Param("unitType") Integer unitType);

    /**
     * 表格查询相关的数据
     *
     * @param organizationParameter 相关参数
     * @return
     */
    List<UnitOrganizationPojo> searchUnitOrganizationTable(UnitOrganizationParameter organizationParameter);

    /**
     * 增加一条单位机构信息
     *
     * @param unitOrganizationPojo 单位机构pojo
     * @return
     */
    int addOneUnitOrganization(UnitOrganizationPojo unitOrganizationPojo);

    /**
     * 删除一条单位机构信息
     *
     * @param unitCodes 机构编码
     * @return
     */
    int deleteOneUnitOrganization(@Param("unitCodes") List<String> unitCodes);

    /**
     * 更新一条单位机构信息
     *
     * @param unitOrganizationPojo 单位机构Pojo
     * @return
     */
    int updateOneUnitOrganization(UnitOrganizationPojo unitOrganizationPojo);

    /**
     * 根据机构代码查询单位机构信息是否存在
     *
     * @param unitCode 机构编码
     * @return
     */
    int searchUnitOrganizationCountById(@Param("unitCode") String unitCode);

    /**
     * 查询所属地区的信息
     *
     * @return
     */
    List<FieldCodeVal> getAreaInfo();

    /**
     * 根据父级单位机构编码查询单位机构信息
     *
     * @param parentUnitCode 父级单位机构编码
     * @return
     */
    List<UnitOrganizationPojo> searchUnitOrganizationListByParentId(@Param("parentUnitCode") String parentUnitCode);

    /**
     * 获取表格的筛选内容
     * @return
     */
    List<FilterObject> searchAllFilterTable();
}
