package com.synway.property.dao;

import com.synway.property.pojo.approvalinfo.DataApproval;
import com.synway.property.pojo.formorganizationindex.ClassifyInfo;
import com.synway.property.pojo.formorganizationindex.PublicDataInfo;
import com.synway.property.pojo.formorganizationindex.SYDMGParam;
import com.synway.property.pojo.alarmsetting.OrganizationAlarmSetting;
import com.synway.property.pojo.datastoragemonitor.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author majia
 */
@Repository
@Mapper
public interface DataMonitorDao extends BaseDAO {

    List<NeedAddRealTimeTable> getAllNeedRealTimeTable();

    List<RealTimeTableFullMessage> getAllRealTimeTableFullMessage();

    int deleteTodayDataStorageTable(@Param("dt") String dt);

    int insertIntoAllRealTimeMessage(List<RealTimeTableFullMessage> list);

    // 获取表组织资产的相关数据
    List<TableOrganizationData> getAllOrganizationData(@Param("setting") OrganizationAlarmSetting setting);

    //将统计的表组织资产数据插入到数据库中
    int insertAllOrganizationData(List<TableOrganizationData> tableOrganizationDataList);

    //将统计的权限表数据插入到数据库中
    int insertAllUserAuthorityData(List<UserAuthorityData> userAuthorityDataInsertList);

    //删除今日的数据
    int deleteAllOrganizationData();

    //删除权限表今日数据
    int deleteAllUserAuthorityData();

    Set<TableOrganizationData> getObjectList(List<TableOrganizationData> allTableOrganizationDataList);

    List<TableOrganizationData> getOdpsAdsTableInfo();

    void deleteOdpsAdsTableInfo(List<TableOrganizationData> deleteOdpsAdsTableList);

    //添加hbase数据
    void addHbaseData(@Param("hbaseParam") SYDMGParam hbaseParam);
    void addHbaseDataPatch(List<SYDMGParam> hbaseParams);

    //添加hive数据
    void addHiveData(@Param("hiveParam") SYDMGParam hiveParam);
    // 批量入库hive数据
    void addHiveDataPatch(List<SYDMGParam> hiveDatas);

    //添加clickhousedata
    void addClickHouseData(@Param("clickhouseParam") SYDMGParam clickhouseParam);
    // 批量入库ck数据
    int addClickHouseDataPatch(List<SYDMGParam> clickhouseDatas);

    // 删除syndmgTableAll当天数据
    int delSyndmgTableAllDataByType(@Param("tableType") int tableType);

    List<TableOrganizationData> getRegisterApprovals();

    void updateDataResourceInfo(@Param("list") List<DataResourceInfo> updateDataResourceInfoList);

    String getOrganizationAlarmSetting();

    List<DataApproval> getApprovalsByType(@Param("approvalType") String approvalType);

    List<DataResourceTable> getSyndmgTableAllDataByDate(@Param("days") int days);

    List<DataResourceTable> getSyndmgTableAllCKData();

    List<DataResourceTable> getSyndmgTableAllHiveData();
    void delHiveHisData(@Param("sydmgParam") SYDMGParam sydmgParam, @Param("minDelDate") long minDelDate);

//    @MapKey("TABLENAME")
    List<TableOrganizationData> getClassifyInfo(@Param("sjzzflCodeId") String sjzzflCodeId);

    List<DataResourceTable> getSyndmgTableAverageData(@Param("setting") OrganizationAlarmSetting setting);

    List<DataResourceTable> getHeatTableData();

    void deleteOverTimeAssets(@Param("item")String assetsStoreDays);

    List<String> getStandardBillNum(@Param("tableName") String tableName, @Param("i") int i);

    List<TableOrganizationData> getPreviousTableAllCount(@Param("i") int i);

    List<TableOrganizationData> getBillDatas(@Param("i") int i);

    List<String> getAccessBillNum(@Param("tableName") String tableName, @Param("i") int i);

    // 查询分类信息
    List<ClassifyInfo> getClassInfo(@Param("classCode") String classCode);

    // 查询public_data_info
    List<PublicDataInfo> getPublicDataInfo();

    // 查询objet表名，用于对标
    String[] getObjectTableNames();

    // 查询objet_store_info表名，用于对标
    List<TableOrganizationData> getObjectStoreInfos();

}
