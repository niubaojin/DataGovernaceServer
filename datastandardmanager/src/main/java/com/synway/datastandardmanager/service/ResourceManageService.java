package com.synway.datastandardmanager.service;



import com.alibaba.fastjson.JSONArray;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.ExternalInterfce.GetTreeReq;
import com.synway.datastandardmanager.pojo.ExternalInterfce.TreeNodeVue;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *
 * 数据定义页面上的所有接口信息
 * @author wangdongwei
 */
public interface ResourceManageService {

//    /**
//     * 获取到object表中的 datatype字段类型
//     * @return
//     */
//	public List<Integer> selectDataTypeByObject();

//    /**
//     *  历史接口 不需要用的了
//     * @param dataType
//     * @return
//     */
//	public List<FieldCodeVal> selectFieldCodeValByDataType(int dataType);

//    /**
//     *  历史接口 不需要用的了
//     * @param dataType
//     * @param codeValId
//     * @return
//     */
//	public List<ObjectPojo> selectObjectPojoByDataTypeAndCodeValId(int dataType, String codeValId);

//    /**
//     * 获取左侧树的相关信息
//     * @return
//     */
//	public String getTreeNode();

	/**
	 * 根据表ID获取表字段信息
	 * @param tableId 表id
	 * @return
	 */
	List<ObjectField> selectObjectFieldByObjectId(String tableId);

	/**
	 * 根据表ID获取表信息  20191014 发生变化
	 * @param tableId 表id
	 * @return
	 */
	ObjectPojoTable selectObjectPojoByTableId(String tableId);


//    /**
//     *  获取左侧树的相关信息 可以查询
//     * @param objectName
//     * @return
//     */
//    public List<TreeNode> queryTreeNode(String objectName);

//    /**
//     *   历史版本 不需要
//     * @param tableId
//     * @param fieldId
//     * @param fieldNameEn
//     * @param fieldNameCh
//     * @param isNeed
//     * @return
//     */
//	public List<ObjectField> queryObjectField(String tableId, String fieldId, String fieldNameEn, String fieldNameCh, String isNeed);

//    /**
//     * 历史接口 不需要用的了
//     * @param tableId
//     * @param fieldId
//     * @return
//     */
//	public ObjectField showObjectField(String tableId, String fieldId);

    /**
     * 保存标准表字段信息
     * @param objectField
     * @return
     */
    ServerResponse<String> saveObjectField(ObjectFieldStandard objectField,int recno) throws Exception;

    /**
     * 删除标准表字段信息
     * @param objectId
     * @param fieldId
     * @return
     */
    ServerResponse<String> deleteObjectField(Long objectId, String fieldId);


//	/**
//	 * 20191010 根据部标标准修改需求 左侧tree的修改
//	 * @param name 搜索的名称
//	 * @return
//	 */
//	List<ZtreeNode>  getResourceManageZTreeNodes(String name,int dataType);

    /**
     * 展示所有的来源关系
     * @param tableId
     * @return
     */
	List<SourceRelationShip> getSourceRelationShip(String tableId);


	/**
	 * 根据大类的id号获取一级分类信息
	 * @param mainValue  '1':组织分类 '2':来源分类   '3'：资源分类
	 * @return
	 */
	List<PageSelectOneValue> getFirstClassModeByMainService(String mainValue);

    /**
     * 根据大类id和一级分类的名称获取二级级分类信息
     * @param mainValue
     * @param firstClassValue
     * @return
     */
	List<PageSelectOneValue> getSecondaryClassModeByFirstService(String mainValue ,String firstClassValue);

	/**
	 *  添加新的数据来源关系
	 * @param tableName  表名
	 * @param addType   添加格式
	 * @return
	 */
	ServerResponse<String> addSourceRelationByTableNameService(String tableName,
															   String addType,
															   String outputTableId,
															   String sourceSystem ,
															   String sourceFirm,
															   String objMemo,
															   String dataId,
															   ObjectPojoTable objectPojoTable);

    /**
     * 开始删除指定的来源关系
     * @param delSourceRelation  需要删除的来源关系列表
     * @param outputDataId      表协议ID
     * @return
     */
    ServerResponse<String> deleteSourceRelationService(List<SourceRelationShip> delSourceRelation,
                                                       String outputDataId);


    /**
     *  创建 表字段 fieldID的相关信息 用于搜索提示框
     * @param type
     * @param condition
     * @return
     */
	ServerResponse<List<OneSuggestValue>> createAddColumnModelService(String type , String condition);

    /**
     * 根据指定的数据获取对应的Synltefield 表中字段信息
     * @param type
     * @param inputValue
     * @return
     */
    ServerResponse<Synltefield> getAddColumnByInputService(String type , String inputValue);

	/**
	 * 保存修改的数据信息
	 * @param standardObjectManage
	 * @return
	 */
	ServerResponse<String> saveResourceManageTableService(StandardObjectManage standardObjectManage);

	/**
	 *
	 * @param sourceProtocol   来源表id JZ_RESOURCE11E4312
	 * @param sourceSystem     来源系统  3G分光这些的代码值
	 * @param sourceFirm      来源厂商中文名  全部/三汇这些
	 * @param tableName       表名 表英文名（中文名这些）
	 * @param tableId         输出协议的表Id
	 * @return
	 */
    ServerResponse<String> addSourceRelationByEtlMessageService(String sourceProtocol,
                                                                String sourceSystem,
                                                                String sourceFirm,
                                                                String tableName,
                                                                String tableId,
																String dataCenterTableId,
																String dataCenterDataId,
																String centerId,
																String objMemo,
																String realTableName);


    /**
     *   字段信息查找
     * @param searchInput
     * @param searchType
     * @param tableId
     * @return
     */
	ServerResponse<Object> searchResourceManageObjectField(String searchInput,
														   String searchType,
														   String tableId);


    /**
     * 获取源应用系统下拉列表
     * @return
     */
	ServerResponse<List<LayuiClassifyPojo>> getAllSysList();


    /**
     * 获取 数据处理页面的相关参数
     * @param tableId 目标表Id
     * @param sourceId 来源表ID
     * @return
     */
	String getDataHandleModelMessageService(String tableId , String sourceId);


    /**
     * 根据tableId获取标准表信息
     * @param tableId
     * @return
     */
	ServerResponse<StandardFieldJson> getAllStandardFieldJson(String tableId);

    /**
     *   给数据处理接口提交修改后的标准表信息
     *   1.6.0版本之后该接口去除
     * @param tableId
     * @return
     */
	ServerResponse<String> pushMetaInfo(String tableId);

    /**
     * 数据提交标准化管理
     * @param sourceRelationShipList
     */
	void pushSourceMetaInfo(List<SourceRelationShip> sourceRelationShipList);

//    /**
//     * 获取数据分类信息  分为组织和来源两列
//     * @param name
//     * @param dataType   1：数据组织  2：数据来源
//     * @param moduleName  第三方模块名称 20200917新增
//     * @return
//     */
//	List<ZtreeNode>  getOrganizationZTreeNodes(String name,int dataType,String moduleName);

    /**
     *  提供给vue页面的接口，查询分级分类的tree树
     * @param req
     * @param isQueryTable
     * @param showLabel
     * @param showAll
     * @return
     */
	List<TreeNodeVue> externalgetTableOrganizationTree(
			GetTreeReq req, Boolean isQueryTable,Boolean showLabel,Boolean showAll);


	/**
	 *  创建搜索提示框
	 * @param mainValue
	 * @param firstValue
	 * @param secondaryValue
	 * @param condition
	 * @return
	 */
	List<String> createObjectTableSuggestService(String mainValue ,
												 String firstValue,
												 String secondaryValue,
												 String condition);


    /**
     * 保存页面上 数据信息/字段定义/来源关系 三种数据
	 * 数据信息/数据项信息/原始数据来源信息/数据集信息(1.9)
     * @param standardObjectManage
     * @return
     * @throws Exception
     */
	String saveResourceFieldRelationService(StandardObjectManage standardObjectManage) throws Exception;

//    /**
//     * 向审批流程发送标准表数据
//     * @param standardObjectManage
//     * @return
//     * @throws Exception
//     */
//	String saveOrUpdateApprovalInfoService(StandardObjectManage standardObjectManage) throws Exception;

    /**
     * 20200511 判断 fieldId fieldName 是否为正常的字段信息
     * @param fieldId
     * @param fieldName
     * @return
     * @throws Exception
     */
	String getIsExitsFiledMessageService(String fieldId,String fieldName) throws Exception;


    /**
     * 根据 数据探查里面该sourceid对应的tableid数据
     * @param standardObjectManage
     * @param switchFlag
     * @return
     * @throws Exception
     */
	Boolean checkTableIdSourceIdIsExistsService(StandardObjectManage standardObjectManage,boolean switchFlag) throws Exception;

    /**
     * 数据组织分类选择选择原始库之后 获取对应的tableId值
     *  规则为   GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
     * @param dataSourceClassify  数据来源分类的中文值
     * @param code   数据协议的6位行政区划代码
     * @return
     * @throws Exception
     */
	String getOrganizationTableIdService(String dataSourceClassify,String code) throws Exception;

	String getDefaultXZQH();

    /**
     * 拼接跳转到表登记的url信息
     * @param tableName
     * @param tableId
     * @param objectId
     * @return
     * @throws Exception
     */
	String getTableRegisterUrlService(String tableName,String tableId,String objectId) throws Exception;

	/**
	 * 审批结束后根据tableId来改变使用状态
	 * @param tableId
	 * @return
	 */
	String updateObjectStatus(String tableId);

	/**
	 * 根据数据源id、项目空间、表英文名查询已探查表全部信息
	 * @param resId 数据源
	 * @param project 项目空间
	 * @param tableName 表英文名
	 * @return
	 */
	DetectedTable getDetectedTableInfo(String resId, String project, String tableName);

}
