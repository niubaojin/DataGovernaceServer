package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.InputObjectCreate;
import com.synway.datastandardmanager.pojo.ObjectPojoTable;
import com.synway.datastandardmanager.pojo.ObjectVersion;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StandardResourceManageDao {

	List<InputObjectCreate> getAllInputObject(@Param("tableId") String tableId);


	List<InputObjectCreate> getAllInputObjectRelation(@Param("tableId") String tableId);

	/**
	 * 获取要添加的数据来源
	 */
	InputObjectCreate getSourceRelationByTableName(@Param("tableName") String tableName);

	/**
	 * 获取 o_guid 的信息
	 */
	String getOutputGuidbYId(@Param("outputTableId") String outputTableId,
                             @Param("sysId") String sysId,
                             @Param("ownerFactoryNum") int ownerFactoryNum);

	/**
	 * 将获取到的输入-输出的关系信息插入到数据库中
	 * @param inputObjGuid  输入协议唯一标识
	 * @param outputObjGuid 输出协议唯一标识
	 * @param inputIobjSource  输入协议数据来源
	 * @return
	 */
	int insertInputObjectRelate(@Param("inputObjGuid") String inputObjGuid,
								@Param("outputObjGuid") String outputObjGuid,
								@Param("inputIobjSource") int inputIobjSource);

	/**
	 * 更新关系
	 * @param inputObjGuid 输入协议唯一标识
	 * @param outputObjGuid  输出协议唯一标识
	 * @param inputIobjSource
	 * @return
	 */
	Integer updateInputObjectRelate(@Param("inputObjGuid") String inputObjGuid,
									@Param("outputObjGuid") String outputObjGuid,
									@Param("inputIobjSource") int inputIobjSource);

	/**
	 * 根据以下参数获取输入GUID
	 * @param inputDataId      来源系统
	 * @param inputSourceCode   来源数据协议
	 * @param inputSourceFirm   来源厂商
	 * @return
	 */
	String getInputGuidById(@Param("inputDataId") String inputDataId,
							@Param("inputSourceCode") String inputSourceCode,
							@Param("inputSourceFirm") int inputSourceFirm);


	/**
	 * 将输入阶段-对象（协议）关系表中指定guid的数据标识为禁用状态
	 * @param outputGuid
	 * @param inputGuid
	 * @return
	 */
	int updateInputObjectRelateDao(@Param("outputGuid") String outputGuid,
								   @Param("inputGuid") String inputGuid);

	/**
	 *  查询表中是否有存在的数据
	 */
	Map<String,Integer> selectDataIsExist(@Param("outputGuid") String outputGuid,
										  @Param("inputGuid") String inputGuid,
										  @Param("inputIobjSource") int inputIobjSource);


	/**
	 * 在outputobject表中添加数据
	 */
	int insertOutputObject(@Param("objGuid") String objGuid,
                           @Param("xml") String xml,
                           @Param("oobjType") int oobjType,
                           @Param("oobjStatus") int oobjStatus,
                           @Param("oobjSource") String oobjSource);

    /**
     * 在STANDARDIZE_object表中获取 OBJ_GUID
     */
    String getObjGuidByTableId(@Param("tableId") String tableId);

    /**
     * 在object表中插入
     * @param
     * @return
     */
    int insertObjectDao(@Param("codeTextTd") String codeTextTd,@Param("tableId") String tableId,@Param("dataSourceName") String dataSourceName,
						@Param("ownerFactory") String ownerFactory,@Param("dataId") String dataId,@Param("centerId") String centerId,@Param("objMemo") String objMemo);

    /**
     * 获取最大objectId
     * @return
     */
    String getMaxObjectId();

    /**
     * 添加一条object信息
     * @param objectPojoTable
     * @return
     */
    int addObjectMessageDao(ObjectPojoTable objectPojoTable);

    /**
     * 更新一条记录
     * @param objectPojoTable
     * @return
     */
    int updateObjectMessageDao(ObjectPojoTable objectPojoTable);

    int updateObjectDao(ObjectPojoTable objectPojoTable);

    String getObjGuidByTreeParam(
			@Param("sourceProtocol") String sourceProtocol,
			@Param("sourceSystem") String sourceSystem,
			@Param("sourceFirm") int sourceFirm);

    int insertStandardizeObjectDao(@Param("sourceProtocol") String sourceProtocol,
								   @Param("sourceSystem") String sourceSystem,
								   @Param("sourceFirm") int sourceFirm,
								   @Param("tableName") String tableName,
								   @Param("dataId") String dataId,
	                               @Param("tableId") String tableId,
								   @Param("centerId") String center_id,
								   @Param("objMemo") String objMemo);

	int insertInputObjectDao(@Param("objGuid") String objGuid,
							 @Param("objType") int objType,
							 @Param("objStatus") int objStatus,
							 @Param("objSource") int objSource);


	int getCountSourceRelationByInpuObject(@Param("inputObjGuid") String inputObjGuid,
									  @Param("tableId") String tableId);

	String getOObjGuidByTableId(@Param("tableId") String tableId);

	//在 CLASSIFY_INTERFACE_ALL_DATE 的数据中找到对应的tableId
	String getTableIdByNameClassify(@Param("tableName") String tableName);

	int getCountObjectByTableId(@Param("tableId") String tableId);
	int updateObjectMessageByTableId(ObjectPojoTable objectPojoTable);

	String getTableIdByObjectId(@Param("objectId") String objectId);
	// 获取表id是否已经存在
	int getObjectCountByObjectId(@Param("objectId") Long objectId);

	int updateObjectTableId(@Param("oldTableId") String oldTableId,@Param("newTableId") String newTableId);

	String getOutPutObjGuidByTableId(@Param("tableId") String tableId,
									 @Param("sysId") String sysId,
									 @Param("ownerFactoryNum") int ownerFactoryNum);

	int updateStandardizeObjectByGuid(@Param("newTableId") String newTableId,@Param("objGuid") String objGuid);

	String getOutputGuidbYTableId(@Param("tableId")String tableId );


	String getOutputGuidNotInInput(@Param("tableId")String tableId);

	int selectCountStandardizeCount(
			@Param("tableId")String tableId
	);

	int deleteStandardInputObjectDao(@Param("inputGuid")String inputGuid);

	String getObjGuidByObject(@Param("tableId")String tableId,@Param("sysId") String sysId);

	//  根据tableId和真实表名判断这个是否存在
	int getCountObjectByIdName(@Param("tableId")String tableId,@Param("tableName") String tableName);

	String getOldTableNameByObjectIdDao(@Param("objectId")String objectId);

	/**
	 *  根据输入协议获取到输出协议的相关信息
	 */
	@AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
	List<PageSelectOneValue> getStandardOutTableIdBySourceIdDao(@Param("sourceId")String sourceId,
																@Param("sourceCode")String sourceCode,
																@Param("sourceFirmCode")String sourceFirmCode);


	Integer findStandByDataIdDao(@Param("dataId")String dataId);

	/**
	 * 检查object是否更新
	 * @param pojoTable
	 * @return
	 */
	int checkIsUpdate(ObjectPojoTable pojoTable);

	/**
	 * 查询一条object信息
	 * @param objectId
	 * @return
	 */
	ObjectPojoTable searchOneData(String objectId);

	/**
	 *  生成一条标准版本信息
	 * @param objectVersion
	 * @return
	 */
	int saveOneDataVersion(ObjectVersion objectVersion);

	/**
	 *  生成一条历史信息
	 * @param objectPojoTable
	 * @return
	 */
	int saveOldData(ObjectPojoTable objectPojoTable);




}
