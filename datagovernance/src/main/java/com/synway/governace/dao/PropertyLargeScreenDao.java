package com.synway.governace.dao;

import com.synway.governace.pojo.largeScreen.*;
import com.synway.governace.pojo.largeScreen.ForXJ.DataInsert;
import com.synway.governace.pojo.largeScreen.ForXJ.DataOrganization;
import com.synway.governace.pojo.largeScreen.ForXJ.GradeAndClass;
import com.synway.governace.pojo.largeScreen.ForXJ.WarnData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据资源大屏
 * @author wdw
 */
@Mapper
@Repository
public interface PropertyLargeScreenDao {

    /**
     * 获取表昨日数据量
     * @return
     */
    TotalDataProperty getYesterdayCount();

    /**
     * 删除表 DGN_PROPERTY_LARGE 的数据 是否存在，存在则
     * @param type
     * @param saveFlag
     * @return
     */
    int delDataByTypeAndFlag(@Param("type") int type, @Param("saveFlag") int saveFlag);


    /**
     * 将数据插入到表 DGN_PROPERTY_LARGE 中
     * @param data   string类型的数据
     * @param type
     * @param uuidStr
     * @return
     */
    int insertData(@Param("data") String data,
                   @Param("type") int type,
                   @Param("id") String uuidStr);

    /**
     * 获取 原始库日接入情况
     *  @param  dateStr
     * @return
     */
    List<StandardLabelData> getOriginalDataList(
            @Param("dateStr") String dateStr);

    /**
     * 获取原始库的所有二级分类信息
     * @return
     */
    List<String> getAllClassifyCh();

    /**
     *  获取分类的相关信息
     * @return
     */
    List<FiledClassData> getFiledClassDataList();

    /**
     * 只获取码表的分类信息
     * @return
     */
    List<FiledClassData> getFiledCodeClassDataList();


    /**
     * 获取使用热度的
     * @return
     */
    List<UseHeatProperty> getUseHeatList();

    /**
     * 获取应用的使用热度
     * @return
     */
    List<UseHeatProperty> getApplicationUseHeat();


    /**
     * 获取今日的下发数据量
     * @param todayStr  今天的时间  20210429
     * @return
     */
    List<DataDistributionStatistic> getDataDistributionList(@Param("todayStr")String todayStr);


    /**
     * 获取今日下发的数据量
     * @param todayStr
     * @return
     */
    List<DataDistributionStatistic> getStatInfoDisList(@Param("todayStr")String todayStr);

    /**
     * 根据省名称获取对应的市
     * @param provinceName
     * @return
     */
    String getProvinceCity(@Param("provinceName")String provinceName);

    // 数据接入任务数
    Integer getDIJobCounts();
    // 获取数据处理数据量
    Integer getStandardCount();

    /**
     * 获取新疆下发数据量城市简称对应的 城市代码
     * @return
     */
    List<CityAbbPojo> getCityAbbList();

    /**
     * 获取定时任务统计的结果数据
     * @return
     */
    List<PropertyLargeDbData> getPropertyLargeScreenDataPage();

    // 获取数据接入统计量
    DataInsert getDataInsertAll();
    DataInsert getDataInsert(@Param("days") int days);
    // 获取数据接入折线图
    List<DataInsert> getDataInsertChart(@Param("days") int days);
    // 获取数据组织数据
    List<DataOrganization> getDataOrganization();
    // 获取告警数据
    List<WarnData> getWarnData();
    // 获取分级分类数据
    List<GradeAndClass> getGradeAndClass(@Param("days") int days);
    // 获取来源（中间顶部的3个格子）
    List<GradeAndClass> getDataSource(@Param("days") int days);

    /**
     *   获取 运营商资产表中的数据
     * @return
     */
    List<ProviderTableStat> getProviderTableStatDao();

    /**
     * 获取 资源库/主题库的 资产情况
     * @param type   01-原始库、02-资源库、03-主题库、04-知识库、05-业务库、06-业务索引库
     * @return
     */
    List<StandardLabelData> getOrgainTableStatDao(@Param("type")String type, @Param("sjzzflCodeId")String sjzzflCodeId);


    /**
     * 获取原始库指定日期的数据量
     * @param dateStr  时间
     * @return
     */
    List<StandardLabelData> getOperatorDataByDate(@Param("dateStr")String dateStr);


    /**
     * 删除数据
     * @return
     */
    int deleteOldTable1();
    /**
     * 删除数据
     * @return
     */
    int deleteOldTable2();
    /**
     * 删除数据
     * @return
     */
    int deleteOldTable3();


    /**
     * 根据标签级别获取所有的标签信息
     * @param level
     * @return
     */
    List<LabelManageData> getAllLabelDataByLevel(@Param("level") int level);


    /**
     * 获取原始库资产情况的数据总数
     * @return
     */
    List<StandardLabelData> getAllOriginalDataList();


    /**
     * 获取 原始业务库资产情况
     * @return
     */
    List<StandardLabelData> getOriginalBusinessData(@Param("sjzzflCodeId")String sjzzflCodeId);


    /**
     * 获取所有的分类信息总数
     * @return
     */
    int getAllCountFiledClassDataList();


    /**
     *  大存储量数据排行榜
     * @return
     */
    List<LargeStorageData> getLargeStorageData(@Param("version") String version);


    /**
     * 获取　普元表的统计数据
     * @return
     */
    PyPublishInfo getPuYuanInformationBus();


}
