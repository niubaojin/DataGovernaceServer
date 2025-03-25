package com.synway.property.dao;


import com.synway.property.interceptor.AuthorControl;
import com.synway.property.pojo.*;
import com.synway.property.pojo.approvalinfo.DataApproval;
import com.synway.property.pojo.formorganizationindex.ReceiveTag;
import com.synway.property.pojo.datastoragemonitor.*;
import com.synway.property.pojo.homepage.DataBaseState;
import com.synway.property.pojo.register.RegisterInfo;
import com.synway.property.pojo.register.RegisterState;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据组织资产（根据部标）页面上相关的需求
 * @author majia
 */
@Mapper
@Repository
public interface DataStorageMonitorDao {
    /**
     * 获取odps/ads中数据总行数
     *
     * @return
     */
    List<Map<String, Object>> getAllDataBaseCount();

    List<Map<String, Object>> getOssFileCount();

    /**
     * 获取资产表今日数据量
     * @return
     */
    int getTodayAssetsCount();

    /**
     * 获取平台表数量
     * @param daysAgo
     * @return
     */
    List<DataBaseState> getPlatTableSum(@Param("daysAgo")int daysAgo);

    /**
     * 获取ODPS的活表率
     */
    String getOdpsLiveRote();

    /**
     * 获取odps/ads使用量
     *
     * @return
     */
    List<Map<String, Object>> getOdpsAdsUsedCapacity();

    /**
     * Wushi获取odps/ads使用量
     *
     * @return
     */
    List<Map<String, Object>> getWushiOdpsAdsUsedCapacity();

    /**
     * 获取数据资产中最右上角的页面数据
     */
//    @AuthorControl(tableNames ={"table_organization_assets","detect_task_manage"},columnNames = {"resourceid","taskID"})
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    DataQualitySummary getDataQualitySummary(@Param("dataWorkVersion")String dataWorkVersion,@Param("daysAgo")int daysAgo,@Param("odpsProjectList") List<String> odpsProjectList);

    /**
     * 将需要添加的表名插入到数据库中
     */
    Integer insertOracleAddTable(@Param("item") NeedAddRealTimeTable needAddRealTimeTable, @Param("type") String type);

    /**
     * 获取已经插入的需要监控的实时表名
     *
     * @return
     */
//    @AuthorControl(tableNames ={"DATA_STORAGE_ADD_TABLE"},columnNames = {"table_id"})
    List<NeedAddRealTimeTable> getAllRealTimeTable();

    /**
     * 获取实时表数据的展示数据
     *
     * @param dt
     * @return
     */
//    @AuthorControl(tableNames ={"DATA_STORAGE_ADD_TABLE"},columnNames = {"table_id"})
    List<RealTimeTableFullMessage> getAllRealTimeTableFullMessage(@Param("dt") String dt, @Param("yesToday") String yesToday);

    //根据选择的数据资源目录的三大分类之一获取对应的一级分类信息
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<PageSelectOneValue> getPrimaryClassifyData(@Param("mainClassify") String mainClassify,@Param("daysAgo") int daysAgo);

    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<PageSelectOneValue> getSecondaryClassifyData(@Param("mainClassify") String mainClassify,
                                                      @Param("primaryClassifyCode") String primaryClassifyCode,
                                                      @Param("daysAgo") int daysAgo,
                                                      @Param("isThreeLevel") String isThreeLevel);

    //表组织资产的汇总信息
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<SummaryTableByClassify> getAllSummaryTableByClassifyList(@Param("mainClassify") String mainClassify,
                                                                  @Param("primaryClassifyCh") String primaryClassifyCh,
                                                                  @Param("secondaryClassifyCh") String secondaryClassifyCh,
                                                                  @Param("threeValue") String threeValue,
                                                                  @Param("daysAgo") int daysAgo);


    //  根据三个类别 数据组织，数据资源来源，数据资源分类 来获取一级分类的总数据量和总大小
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<Map<String, Object>> getRecordsStorageByPrimaryClassifyMap(@Param("categoryName") String categoryName,@Param("daysAgo") int daysAgo);


    //  获取ads实时表监控中已经插入的表名
    List<String> getAdsProjectAdd();

//    @AuthorControl(tableNames ={"DATA_STORAGE_ADD_TABLE"},columnNames = {"table_id"})
    List<NeedAddRealTimeTable> getNeedAddRealTimeTableList(@Param("dataBaseType") String dataBaseType,
                                                           @Param("tableName") String tableName);

    @MapKey("TABLENAME")
    Map<String, Map> getStandardTableName();

    int delRealTableMonitorDao(@Param("list") List<NeedAddRealTimeTable> delDataList);


    int updateRegisterInterfaceSuccessData(@Param("list") List<DetailedTableByClassify> tablelist);

    int updateRegisterInterfaceFailedData(@Param("list") List<DetailedTableByClassify> tablelist);

    void updateRegisterState(List<RegisterState> tablelist);
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<DetailedTableByClassify> getAllDataContentByTags(@Param("organizationClassify") List<ReceiveTag> organizationClassify,
                                                          @Param("sourceClassify") List<ReceiveTag> sourceClassify,
                                                          @Param("input") String input,
                                                          @Param("registerState") String registerState,
                                                          @Param("usingTagsState") String usingTagsState,
                                                          @Param("projectList") List<ReceiveTag> projectList,
//                                                          ,@Param("labels") List<String> labels
                                                          @Param("termSetting") List<String> termSetting,
                                                          @Param("startLastModifiedTime") String startLastModifiedTime,
                                                          @Param("endLastModifiedTime") String endLastModifiedTime,
                                                          @Param("startRecordNum") Long startRecordNum,
                                                          @Param("endRecordNum") Long endRecordNum,
                                                          @Param("startStorageSize") Long startStorageSize,
                                                          @Param("endStorageSize") Long endStorageSize,
                                                          @Param("queryTable") DataResourceTable queryTable,
                                                          @Param("daysAgo") int daysAgo,
                                                          @Param("objectStateStatus")       String[] objectStateStatus,
                                                          @Param("productStageStatusList")  List<String> productStageStatusList,
                                                          @Param("registerStateStatus")     String[] registerStateStatus,
                                                          @Param("tableProjectStatus")      String[] tableProjectStatus,
                                                          @Param("tableStateStatus")        String[] tableStateStatus,
                                                          @Param("tableidStatus")           String[] tableidStatus,
                                                          @Param("updatePeriodStatus")      String[] updatePeriodStatus,
                                                          @Param("isStandardStatus")      String[] isStandardStatus
    );

//    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<DetailedTableByClassify> getAllDataContentFilter(@Param("daysAgo") int daysAgo);

    void updateObjectState(@Param("list") List<String> tableIdList, @Param("state") String state);

    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<PageSelectOneValue> getProjectNameByType(@Param("stroageLocation") String stroageLocation);

    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<PageSelectOneValue> getStorageNum();

    List<String> getTableType(@Param("daysAgo") int daysAgo);

    void updateApprovalStatus(@Param("item") DataApproval dataApprovals);

    List<DataApproval> getApprovals();

    List<DataApproval> getApprovalStatus(@Param("approvals") List<DataApproval> approvals);

    List<NeedAddRealTimeTable> getAllUnMonitoredTable();

    String getObjectNameChByEn(@Param("tableName") String tableName);

    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<Map<String, Object>> getSourceStateNum(@Param("daysAgo") int daysAgo);

//    List<Label> getAllLabels();

    List<DataApproval> getApprovalNum(@Param("dataApprovals") List<DataApproval> dataApprovals);

    List<PageSelectOneValue> getLabelNum();

//    List<PageSelectOneValue> getLabelsByType(@Param("label") String label);

//    List<String> getLabelsByName(@Param("list") List<ReceiveTag> labelTagsList);

    List<RegisterInfo> getRegisterInfoBytableIdList(@Param("tablelist") List<String> tablelist, @Param("resourceCode") String resourceCode, @Param("daysAgo") int daysAgo);

    Integer getApprovalStatusBytableId(@Param("tableId")String tableId);

    List<ObjectTable> getAllObjectChName();

    // 获取左侧tree的相关数据
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<StandardTableRelation> getOrganizationZTreeNodes(@Param("name")String name,@Param("dataType")int dataType);
    @AuthorControl(tableNames ={"table_organization_assets"},columnNames = {"resourceid"})
    List<StandardTableRelation> getOrganizationZTreeNodesAll(@Param("name")String name,@Param("dataType")int dataType,@Param("daysAgo") int daysAgo);

    void updateLifeCycleApprovalStatus(@Param("requestParam")RequestParameter requestParam);

    void updateFailLifeCycleApprovalStatus(@Param("requestParam")RequestParameter requestParam);

    String getLifeCycle(@Param("requestParam")RequestParameter requestParam);

    List<DataBaseState> getDataBaseState();

    int deleteDataBaseStates(@Param("dataBaseStates")List<DataBaseState> dataBaseStates);

    int insertDataBaseStates(@Param("dataBaseStates")List<DataBaseState> dataBaseStates);

    void updateTableOrganizationShowField(@Param("tableOrganizationShowFields") String tableOrganizationShowFields, @Param("userName") String userName);

    String getTableOrganizationShowField(@Param("userName") String userName);

    String getDetectId(@Param("detailedTableByClassify") DetailedTableByClassify detailedTableByClassify);
}
