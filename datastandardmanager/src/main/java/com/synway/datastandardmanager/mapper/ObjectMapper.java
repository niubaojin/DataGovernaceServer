package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.BatchOperationDTO;
import com.synway.datastandardmanager.entity.dto.DataDefinitionDTO;
import com.synway.datastandardmanager.entity.dto.DataSetManageDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.ObjectStoreInfoEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.interceptor.AuthorControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObjectMapper extends BaseMapper<ObjectEntity> {

    /**
     * 查询数据集管理列表数据
     */
    @AuthorControl(tableNames ={"classify_interface_all_date","synlte.\"OBJECT\"","synlte.object"},columnNames = {"sjxjbm","tableid","tableid"})
    List<DataSetTableInfoVO> queryDataSetManageList(@Param("summaryQueryParams") DataSetManageDTO summaryQueryParams,
                                                    @Param("dataOrganizationClassifyList") List<String> dataOrganizationClassifyList,
                                                    @Param("dataSourceClassifyList") List<String> dataSourceClassifyList,
                                                    @Param("dataLabelClassifyList1") List<String> dataLabelClassifyList1,
                                                    @Param("dataLabelClassifyList2") List<String> dataLabelClassifyList2,
                                                    @Param("dataLabelClassifyList3") List<String> dataLabelClassifyList3,
                                                    @Param("dataLabelClassifyList4") List<String> dataLabelClassifyList4,
                                                    @Param("dataLabelClassifyList5") List<String> dataLabelClassifyList5,
                                                    @Param("dataLabelClassifyList6") List<String> dataLabelClassifyList6,
                                                    @Param("useTageValueList") List<String> useTageValueList,
                                                    @Param("dataObjectClassifyList") List<Integer> dataObjectClassifyList);

    /**
     * 查询数据定义管理的主页面
     * @param parameter
     * @return
     */
    @AuthorControl(tableNames ={"classify_interface_all_date","synlte.\"OBJECT\"","synlte.object"},columnNames = {"sjxjbm","tableid","tableid"})
    List<DataDefinitionVO> searchDataDefinitionTable(DataDefinitionDTO parameter);

    /**
     * 数据元管理页面 关联数据集
     *
     * @param fieldId    数据元唯一标识
     * @param searchName 模糊搜索关键字
     */
    List<TableInfoVO> getObjectTableNameByFieldId(@Param("fieldId") String fieldId, @Param("searchName") String searchName);

    ObjectEntity getClassifyByTableid(@Param("tableId") String tableId);

    List<KeyValueVO> getFirstClassModeByMain(@Param("mainValue") String mainValue);

    List<KeyValueVO> getSecondaryClassModeByFirst(@Param("mainValue") String mainValue,
                                                  @Param("firstClassValue") String firstClassValue);

    int updateObjectByTableId(ObjectEntity objectEntity);

    int updateObjectByObjectId(ObjectEntity objectEntity);

    /**
     * 获取最大objectId
     *
     * @return
     */
    Integer getMaxObjectId();

    Integer updateAssetsClassify(@Param("oneSourceClassifyCh") String oneSourceClassifyCh,
                                 @Param("twoSourceClassifyCh") String twoSourceClassifyCh,
                                 @Param("oneOrganizationClassifyCh") String oneOrganizationClassifyCh,
                                 @Param("twoOrganizationClassifyCh") String twoOrganizationClassifyCh,
                                 @Param("threeOrganizationClassifyCh") String threeOrganizationClassifyCh,
                                 @Param("tableName") String tableName);

    Integer updateAssetsTempClassify(@Param("oneSourceClassifyCh") String oneSourceClassifyCh,
                                     @Param("twoSourceClassifyCh") String twoSourceClassifyCh,
                                     @Param("oneOrganizationClassifyCh") String oneOrganizationClassifyCh,
                                     @Param("twoOrganizationClassifyCh") String twoOrganizationClassifyCh,
                                     @Param("threeOrganizationClassifyCh") String threeOrganizationClassifyCh,
                                     @Param("tableName") String tableName);

    /**
     * 获取搜索提示框的表名信息
     */
    List<String> createObjectTableSuggest(@Param("mainValue") String mainValue,
                                          @Param("firstValue") String firstValue,
                                          @Param("secondaryClassifyValue") String secondaryClassifyValue,
                                          @Param("threeClassifyValue") String threeClassifyValue,
                                          @Param("condition") String condition);

    String findCheckTableNameImformationThree(@Param("midClassType") String midClassType, @Param("type") Integer type);

    List<ObjectStoreInfoEntity> getStandardDataSet();

    /**
     * 获取资源状态
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<KeyValueVO> getResourceStatus();

    /**
     * 搜索提示内容
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<String> queryConditionSuggestion(@Param("searchValue") String searchValue);

    //    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    List<StandardTableRelationVO> getClassifyObject(@Param("mainClassifyCh") String mainClassifyCh,
                                                    @Param("primaryClassifyCh") String primaryClassifyCh);

    DataResourceRawInformationVO getOrganizationRelationDao(@Param("addTableName") String addTableName);

    List<StandardTableRelationVO> getAllClassify(@Param("mainClassifyCh") String mainClassifyCh,
                                                 @Param("primaryClassifyCh") String primaryClassifyCh);

    String getCodeNameByClassifyIdDao(@Param("id") String id, @Param("type") int type);

    List<ObjectEntity> getClassifyByTableids();

    /**
     * 更新object 里面的分类信息
     * @param dto
     */
    int updateObjectClassifyAndSourceIfy(BatchOperationDTO dto);

}
