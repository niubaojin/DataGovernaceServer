package com.synway.datastandardmanager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.entity.dto.DataSetManageDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.DsmSourceFieldInfoEntity;
import com.synway.datastandardmanager.entity.vo.DataResourceRawInformationVO;
import com.synway.datastandardmanager.entity.vo.DataSetManageVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelChildrenVO;

import java.util.List;

public interface DataSetManageService {

    /**
     * 查询数据集管理表格的相关数据
     */
    DataSetManageVO searchSummaryTable(DataSetManageDTO dataSetManageDTO);

    /**
     * 根据选择的数据资源目录的两大分类之一获取对应的一级分类信息
     */
    List<ValueLabelVO> getPrimaryClassifyData(String mainClassify);

    /**
     * 根据选择的数据资源目录的三大分类之一和 一级分类信息 来获取二级分类信息
     *
     * @param mainClassify        主类名称
     * @param primaryClassifyCode 一级分类code
     */
    List<ValueLabelVO> getSecondaryClassifyData(String mainClassify, String primaryClassifyCode);

    /**
     * 根据选择的二级分类来获取三级分类信息
     *
     * @param primaryClassifyCode 二级分类码表
     * @param secondClassifyCode  三级分类码表
     */
    List<ValueLabelVO> getThreeClassifyData(String primaryClassifyCode, String secondClassifyCode);

    /**
     * 获取资源状态的信息
     */
    List<ValueLabelVO> getResourceStatus();

    /**
     * 搜索提示框的内容，支持数据名、真实表名、资源标识模糊匹配
     */
    List<String> queryConditionSuggestion(String searchValue);

    /**
     * 获取字段分类的码表信息
     */
    JSONArray getAllFieldClassList();

    /**
     * 根据源协议，查找目标协议
     */
    String checkAndGetTableID(String sourceID);

    /**
     * 导入原始库的时候需要这个 查询数据库中的字段信息
     */
    List<DsmSourceFieldInfoEntity> initSourceFieldTable(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm, String tableId);

    /**
     * 插入表的字段信息
     */
    String addTableColumnByEtl(String sourceProtocol,
                               String sourceSystem,
                               String sourceFirm,
                               String tableName,
                               String dataName,
                               String tableId,
                               String centerId,
                               String centerName,
                               String project,
                               String resourceId);

    List<ObjectFieldEntity> getSourceFieldColumnList(JSONObject jsonObject);

    /**
     * 获取二级分类的相关信息
     */
    List<ValueLabelChildrenVO> getSecondaryClassLayuiService(String mainClassify, String primaryClassifyCh);

    /**
     * 获取初始化的表名信息
     */
    String getEnFlagByChnType(String organizationClassifys, String sourceClassifys, String dataSourceName, Boolean flag);

    /**
     * 检查表是否存在
     */
    int checTableNamekIsExit(String realTableName, String objectId);

    /**
     * 从数据组织中添加的新的来源
     */
    DataResourceRawInformationVO getOrganizationRelationByTableName(String addTableName);

    /**
     * 获取所有的分类信息
     */
    List<ValueLabelChildrenVO> getAllClassifyLayuiService(String mainClassifyCh);

    String getDatabaseType();

    String getCodeNameByClassifyId(String classifyIds);

    /**
     * 根据页面上的sourceId 来获取到自增id值，来获取最新的sourceid信息
     */
    String getNewSourceIdById(String sourceId, String dataSourceClassify, String code);

    List<ValueLabelVO> searchSecurityLevel();

    List<ValueLabelVO> searchFieldSecurityLevelList(String codeId);


}
