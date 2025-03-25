package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResourceManageAddDao extends BaseDAO{


    void insertSourceInfo(@Param("sourceInfo") SourceInfo sourceInfo);

    /**
     * 新增一条原始来源关系
     * @param sourceInfo
     * @return
     */
    void addSourceInfo(@Param("sourceInfo") SourceInfo sourceInfo);

    void insertSourceFieldInfo(@Param("sourceFieldInfo") List<SourceFieldInfo> sourceFieldInfo);

    SourceInfo findSourceInfo(@Param("sourceProtocol") String sourceProtocol, @Param("tableName") String tableName,
                              @Param("sourceSystem") String sourceSystem, @Param("dataName") String dataName);

    /**
     * 查找原始来源关系的全部信息
     * @param sourceProtocol 来源协议
     * @param tableName      来源协议表英文名
     * @param sourceSystem   源应用系统
     * @return
     */
    SourceInfo findSourceAllInfo(@Param("sourceProtocol") String sourceProtocol, @Param("tableName") String tableName,
                              @Param("sourceSystem") String sourceSystem);

    /**
     * 查询来源关系是否存在
     * @param sourceProtocol
     * @param tableName
     * @param dataId
     * @return
     */
    int searchSourceInfoIsExist(@Param("sourceProtocol") String sourceProtocol,@Param("tableName") String tableName,
                                @Param("dataId") String dataId);



    void deleteSourceFieldInfoBySourceInfoID(@Param("sourceInfoID") String DWParamID);

    SrcTagMapping findSrcTagMapping(@Param("sourceInfoID")String sourceInfoID,@Param("tableID")String tableID);

    List<SourceFieldInfo> findSourceFieldInfoBySourceInfoID(@Param("SourceInfoID")String SourceInfoID);

    void insertObjectfieldextend(@Param("sourceFieldInfos") List<SourceFieldInfo> sourceFieldInfos);

    Integer standardizeObjecttFieldWeb(@Param("objectId") String objectId);

    String getObjectIDByTableID(@Param("tableID") String tableID);

    @AuthorControl(tableNames ={"CLASSIFY_INTERFACE_ALL_DATE"},columnNames = {"sjxjbm"})
    List<StandardTableRelation> getClassifyObject(@Param("mainClassifyCh") String mainClassifyCh,
                                                  @Param("primaryClassifyCh") String primaryClassifyCh);

//    List<String> findDataOrginialType(@Param("classType") String classType);

    List<String> findCheckTableNameImformationTwo(@Param("classType") String classType,@Param("subClassType") String subClassType);

    String findCheckTableNameImformationThree(@Param("midClassType") String midClassType,@Param("type")Integer type);

    List<String> checTableNamekIsExit(@Param("realTableName") String realTableName);


    DataResourceRawInformation getOrganizationRelationDao(@Param("addTableName") String addTableName);

    List<StandardTableRelation> getAllClassify(@Param("mainClassifyCh") String mainClassifyCh,
                                               @Param("primaryClassifyCh") String primaryClassifyCh);

    String findObjectId(@Param("realTableName") String realTableName);

    String getTableNameByObjectId(@Param("objectId") String objectId);

    List<TagData> getAllTagDataList();

    // 从 表中查询数据
    Integer getTableCountByAssets(@Param("tableName") String tableName);

    Integer getTableCountByCreated(@Param("tableName") String tableName);

    Integer updateAssetsClassify(@Param("oneSourceClassifyCh") String oneSourceClassifyCh,
                                 @Param("twoSourceClassifyCh") String twoSourceClassifyCh,
                                 @Param("oneOrganizationClassifyCh") String oneOrganizationClassifyCh,
                                 @Param("twoOrganizationClassifyCh") String twoOrganizationClassifyCh,
                                 @Param("threeOrganizationClassifyCh")String threeOrganizationClassifyCh,
                                 @Param("tableName") String tableName);

    Integer updateAssetsTempClassify(@Param("oneSourceClassifyCh") String oneSourceClassifyCh,
                                     @Param("twoSourceClassifyCh") String twoSourceClassifyCh,
                                     @Param("oneOrganizationClassifyCh") String oneOrganizationClassifyCh,
                                     @Param("twoOrganizationClassifyCh") String twoOrganizationClassifyCh,
                                     @Param("threeOrganizationClassifyCh") String threeOrganizationClassifyCh,
                                     @Param("tableName") String tableName);

    //   表名回填的相关查询语句
    // 查询第一级分类对应的表名
    String findCheckTableNameImformationMain(@Param("mainClassName") String mainClassName,@Param("type")int type);


    Integer getCountByFiledId(@Param("fieldId")String fieldId);
    Integer getCountByFiledName(@Param("fieldName")String fieldName);

    // 根据数据来源一级分类 获取数据组织一级分类  用英文 / 分隔 一级分类/二级分类
    String getOrganizationClass(String classifyStr);

    /**
     * 根据数据来源二级code查出数据来源一二级的中文名和code
     * @param secondClassIds
     * @return
     */
    PageSelectOneValue getDataRDataResourceOrigin(@Param("secondClassIds") String secondClassIds);

    /**
     * 根据一级二级中文名 来获取对应的id值
     * @param type  1： 数据组织分类  2：数据来源分类
     * @param firstClassCh
     * @param secondClassCh
     * @return
     */
    String getClassIdByCh(@Param("type")String type,
                          @Param("firstClassCh")String firstClassCh,
                          @Param("secondClassCh")String secondClassCh);

    List<String> getAllTableIdDao();

    @AuthorControl(tableNames ={"synlte.PUBLIC_DATA_INFO"},columnNames = {"SJXJBM"})
    List<PublicDataInfo> getSourceRelationDataDao(@Param("tableId")String tableId);

    String getCodeNameCn(@Param("codeId")String codeId);

    String getCodeIdByName(@Param("codeText")String codeText);

    String getCodeIdByAllName(@Param("parentText")String parentText,@Param("childText")String childText,
                              @Param("queryType")int queryType);


    String getCodeNameByClassifyIdDao(@Param("id")String id,@Param("type")int type);

    /**
     * 根据codeId去synlte.fieldCodeVal表中查询数据
     * @param codeId
     * @return
     */
    List<PageSelectOneValue> searchFieldCodeValByCodeId(@Param("codeId")String codeId);


    // 获取 v_classify_info 中父分类的的名称
    List<String> getParCodeTextById(@Param("codeId")String codeId);

    // 获取 PUBLIC_DATA_INFO表中 所有的sourceId信息
    List<String> getAllSourceId();

    /**
     *  根据sourceId的值来获取 PUBLIC_DATA_INFO 表中数据量
     * @param sourceId
     * @return
     */
    int querySumBySourceId(@Param("sourceId")String sourceId);


    /**
     * 根据userid获取objetc表信息
     * @param userId
     * @return
     */
    List<ObjectPojo> getObjectPojoByUserId(@Param("userId")Integer userId);


    /**
     * 以下为权限控制表的相关 方法
     * @param id   tableId
     * @param userId  用户id信息
     * @return
     */
    int checkUserAuthorityExit(@Param("id")String id, @Param("userId")String userId);


    /**
     * 插入用户权限信息的内容
     * @param userAuthority
     * @return
     */
    int insertUserAuthority(UserAuthority userAuthority);


    /**
     * 更新用户权限信息的内容
     * @param userAuthority
     * @return
     */
    int updateUserAuthority(UserAuthority userAuthority);


    /**
     * 获取字段分类的码表信息
     * @return
     */
    List<FieldCodeVal> getAllFieldClassList();

    /**
     * 更新字段分类信息
     * @param data
     * @return
     */
    int updateFieldClassByFieldId(ObjectFieldStandard data);


    /**
     * 判断数据是否已经被更新
     * @param tableId
     * @param  updateStr
     * @return
     */
    String getUpdateTimeStr(@Param("tableId")String tableId,
                            @Param("updateStr")String updateStr);

    /**
     * 根据tableId判断object是否存在
     * @param tableId
     * @return
     */
    Integer countObjectTableId(@Param("tableId") String tableId);

    /**
     * 根据sourceId查询标准是否存在
     * @param tableId
     * @return
     */
    int searchObjectBySourceId(@Param("tableId")String tableId);


}
