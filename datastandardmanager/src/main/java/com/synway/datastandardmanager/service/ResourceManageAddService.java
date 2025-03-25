package com.synway.datastandardmanager.service;

import com.alibaba.fastjson.JSONArray;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;

import java.util.List;

/**
 * @author wangdongwei
 */
public interface ResourceManageAddService {

    /**
     * 根据源协议，查找目标协议
     * @param sourceID
     * @return
     */
    String checkAndGetTableID(String sourceID);

    /**
     *  导入原始库的时候需要这个 查询数据库中的字段信息
     * @param sourceProtocol
     * @param tableName
     * @param sourceSystem
     * @param sourceFirm
     * @param tableID
     * @return
     */
    List<SourceFieldInfo> initSourceFieldTable(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm,String tableID);

    /**
     * 根据tableID，查找出标准表结构
     * @param tableID
     * @return
     */
    List<ObjectField> initStandardFieldTable(String tableID);

    /**
     * 保存来源信息的数据
     * @param sourceFieldInfos
     * @param tableID
     */
    void saveSrcField(List<SourceFieldInfo> sourceFieldInfos,String tableID );

    /**
     *   插入表的字段信息
     * @param sourceProtocol
     * @param sourceSystem
     * @param sourceFirm
     * @param tableName
     * @param tableId
     * @param centerId
     * @return
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


    /**
     * 导入原始库的字段信息，筛选出需要插入的数据
     * @param sourceFieldInfos
     * @param objectID
     * @param objectFieldList
     * @return
     */
    List<ObjectField> getSourceFieldColumnListService(List<SourceFieldInfo> sourceFieldInfos,
                                                      String objectID,
                                                      List<ObjectField> objectFieldList);

    /**
     * 获取二级分类的相关信息
     * @param mainClassify
     * @param primaryClassifyCh
     * @return
     */
    List<LayuiClassifyPojo> getSecondaryClassLayuiService(String mainClassify,String primaryClassifyCh);

    /**
     *  获取初始化的表名信息
     * @param organizationClassifys
     * @param sourceClassifys
     * @param dataSourceName
     * @param flag
     * @return
     * @throws Exception
     */
    String getEnFlagByChnType(String organizationClassifys,
                                    String sourceClassifys,
                                    String dataSourceName,
                                    Boolean flag) throws Exception;

    /**
     * 检查表是否存在
     * @param realTableName
     * @param objectId
     * @return
     */
    int  checTableNamekIsExit(String realTableName,String objectId);

    /**
     * 从数据组织中添加的新的来源
     * @param addTableName
     * @return
     */
    DataResourceRawInformation getOrganizationRelationByTableName(String addTableName);


    /**
     * 获取所有的分类信息
     * @param mainClassifyCh
     * @return
     */
    List<LayuiClassifyPojo> getAllClassifyLayuiService(String mainClassifyCh);

    /**
     * 从数据仓库中查询数据来源的信息
     * @param dataId
     * @param tableName
     * @return
     * @throws Exception
     */
    PublicDataInfo getResourceTableDataService(String dataId,String project,String tableName) throws Exception;


    /**
     *   根据页面上的sourceId 来获取到自增id值，来获取最新的sourceid信息
     *  附带验证功能
     * @param sourceId
     * @param dataSourceClassify
     * @param code
     * @return
     */
    String getNewSourceIdById(String sourceId,String dataSourceClassify,String code) throws Exception;


    /**
     *  将用户的权限信息插入到数据库表 USER_AUTHORITY 中
     * @param standardObjectManage
     * @throws Exception
     */
    String  addUserAuthorityData(StandardObjectManage standardObjectManage) throws Exception;


    /**
     * 获取字段分类的码表信息
     * @return
     */
    JSONArray getAllFieldClassList();

    /**
     * 更新 SYNLTEFIELD表中的码表信息
     * @param objectField
     * @return
     */
    String updateFieldClass(ObjectFieldStandard objectField);

}
