package com.synway.datastandardmanager.dao.master;

import java.util.List;
import java.util.Map;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.DataSetMark;
import com.synway.datastandardmanager.pojo.sourcedata.SensitivityLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ResourceManageDao {

	/**
	 * 根据表的tableId查询表的信息
	 * @param tableId 表Id
	 * @return
	 */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
	ObjectPojo selectObjectPojoByTableId(@Param("tableId") String tableId);

	@AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
	List<ObjectPojo> selectObjectPojoByTableIds(@Param("tableIds") List<String> tableIds);

	/**
	 * 根据表ID获取表字段信息
	 * @param objectId 表id
	 * @return
	 */
	List<ObjectField> selectObjectFieldByObjectId(@Param("objectId") long objectId);

	List<ObjectField> selectObjectFieldByObjectIds();


    List<ObjectField> selectObjectFieldByObjectIdQuery(@Param("objectId") long objectId,
                                                       @Param("searchInput") String searchInput,
                                                       @Param("searchType") String searchType);

	public List<ObjectField> queryObjectField(@Param("tableId") String tableId, @Param("fieldId") String fieldId,
											  @Param("fieldNameEn") String fieldNameEn,
											  @Param("fieldNameCh") String fieldNameCh,
											  @Param("isNeed") String isNeed);

	public ObjectField showObjectField(@Param("tableId") String tableId, @Param("fieldId") String fieldId);
	
	public List<Map<String,String>> getCodeTextAndCodeidByObjectField(@Param("fieldId") String fieldId);

	public List<Synltefield> getCodeTextAndCodeidByObjectFields();


    void addObjectField(ObjectField objectField);


	// 根据主类信息获取一级分类信息
	List<PageSelectOneValue> getFirstClassModeByMainDao(@Param("mainValue") String mainValue);

	// 获取二级信息
	List<PageSelectOneValue> getSecondaryClassModeByFirstDao(@Param("mainValue") String mainValue,
															 @Param("firstClassValue") String firstClassValue);


	//在增加字段中有两个搜索查询框，在数据库中查询该值
	List<OneSuggestValue> getFieldIdByCondition(@Param("condition") String condition);

	List<OneSuggestValue> getColumnByCondition(@Param("condition") String condition);

	// 查询相关的字段信息
	Synltefield getAddColumnByInputDao(@Param("type") String type,
									   @Param("inputValue") String inputValue);


    ObjectPojoTable getClassifyByTableid(@Param("tableId") String tableId);

    List<ObjectPojoTable> getClassifyByTableids();


	/**
	 * 获取源应用系统名称得下拉列表
	 * @return
	 */
	List<FieldCodeVal> selectSysObjectSelect();

	/**
	 * 根据源应用系统二级查询一级信息
	 * @param value 源应用系统二级value
	 * @return
	 */
	FieldCodeVal selectOneSysName(@Param("value") String value);

	List<FieldCodeVal> selectOneSysNames();

	String getSysChiName(@Param("sysCode") String sysCode);

	// 获取左侧tree的相关数据
	List<StandardTableRelation> getOrganizationZTreeNodes(@Param("name")String name,@Param("dataType")int dataType);

	List<StandardTableRelation> getOrganizationZTreeNodesAll(@Param("name")String name,@Param("dataType")int dataType);

	/**
	 * 获取搜索提示框的表名信息
	 * @param mainValue
	 * @param firstValue
	 * @param secondaryClassifyValue
	 * @param threeClassifyValue
	 * @param condition
	 * @return
	 */
	List<String> createObjectTableSuggestDao(@Param("mainValue")String mainValue, @Param("firstValue")String firstValue,
											 @Param("secondaryClassifyValue")String secondaryClassifyValue,
											 @Param("threeClassifyValue")String threeClassifyValue,
											 @Param("condition")String condition);

    String getActiveTableByName(@Param("tableName")String tableName);

    // 查询的数据组织分类和数据来源分类为代码
	ObjectPojoTable getClassifyIdByTableid(@Param("tableId") String tableId);

	// 通过objectId获取建表字段信息
	List<String> getColumnNameByObjectId(@Param("objectId") String objectId);

	Synltefield getStandardFieldByFieldCode(@Param("fieldId") String fieldId);

	@AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
	List<String> getTableNameByTableId(@Param("tableId") String tableId);

	/**
	 * 根据tableId 来修改Object的使用状态
	 * @param tableId
	 * @return
	 */
	int updateObjectStatus(@Param("tableId") String tableId);

	/**
	 * 数据定义页面安全分级的下拉信息
	 * @return
	 */
	List<PageSelectOneValue> searchSecurityLevel();

	/**
	 * 字段定义的数据安全级别信息
	 * @param codeId
	 * @return
	 */
	List<PageSelectOneValue> searchFieldSecurityLevel(@Param("codeId") String codeId);

	/**
	 * 根据字段英文名获取字段信息
	 * @param columnName
	 * @return
	 */
	ObjectField searchObjectFieldByFieldName(@Param("columnName")String columnName,@Param("objectId")String objectId);


	/**
	 * 根据object.tableId查询关联的字段信息
	 * @param tableId object表中的tableId
	 * @return
	 */
	List<ObjectField> searchObjectFieldByTableId(@Param("tableId") String tableId);

	/**
	 * 获取全部的数据集标准信息
	 * @param searchText 关键字查询
	 * @return
	 */
	List<PageSelectOneValue> searchAllDataStandard(@Param("searchText") String searchText);

	/**
	 * 根据objectId获取标准数据项信息
	 * @param searchText 关键字查询
	 * @return
	 */
    List<ObjectField> getColumnNameList(String objectId, String searchText);

	/**
	 * 查询全部标准的英文表名
	 * @return
	 */
    List<ObjectPojo> searchAllObjectTable();

	/**
	 * 根据英文表名查询标准信息
	 * @return
	 */
    ObjectPojo searchObjectInfo (@Param("tableName") String tableName);
}
