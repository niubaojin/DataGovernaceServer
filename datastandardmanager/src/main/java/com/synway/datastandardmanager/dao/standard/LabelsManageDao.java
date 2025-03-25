package com.synway.datastandardmanager.dao.standard;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.OneSuggestValue;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.labelmanage.LabelManageData;
import com.synway.datastandardmanager.pojo.labelmanage.LabelSelect;
import com.synway.datastandardmanager.pojo.labelmanage.QueryParames;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 17:59
 */
@Mapper
@Repository
public interface LabelsManageDao {

    /**
     *获取左侧标签汇总树的相关信息
     * @return
     */
    List<OneSuggestValue> getAllLabelTreeDataDao();

    /**
     *  查询出
     * @param queryParames
     * @return
     */
    List<LabelManageData> getAllLabelManageData(QueryParames queryParames);


    /**
     *
     * @return
     */
    List<LabelSelect> getLabelTypeList();

    /**
     *
     * @return
     */
    List<LabelSelect> getClassidTypeList();


    /**
     * 更新标签库中指定标签的信息
     * @param labelManageData
     * @return
     */
    int updateLabelsManageById(LabelManageData labelManageData);

    /**
     *  新增一条标签的数据
     * @param labelManageData
     * @return
     */
    int insertLabelsManage(LabelManageData labelManageData);


    /**
     * 检查需要更新/新增的数据
     * @param id
     * @param labelCode
     * @return
     */
    int checkExitsCode(@Param("id")String id,@Param("labelCode") String labelCode);

    int checkExitsCodeOrName(@Param("labelCode") String labelCode, @Param("labelName")String labelName);


    int delLabelById(@Param("id")String id);


    List<String> getLabelCodeByLevel(@Param("level")int level);


    int getLabelUsedCount(@Param("labelCode") String labelCode);


    LabelManageData getLabelManageData(@Param("labelCode") String labelCode);


    List<PageSelectOneValue> getLabelManageDataByClassId(@Param("classId") String classId,
                                                         @Param("labelLevel") Integer labelLevel);


    List<LabelManageData> getLabelManageByLabelCode(@Param("list")List<String> list);

    /**
     * 查询标签名称的统计列表
     * @return
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<LabelSelect> searchLabelTotalList();

    /**
     * 根据标签等级查询其下所有标签的中文名称和id
     * @param labelLevel
     * @return
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<LayuiClassifyPojo> searchSecondLabelList(@Param("labelLevel") String labelLevel);

    /**
     * 查询标签等级下是否存在当前的标签代码值
     * @param labelCode 标签代码值
     * @param labelLevel 标签等级
     * @return
     */
    int checkLabelCodeIsExist(@Param("labelCode") String labelCode,@Param("labelLevel")Integer labelLevel);


}
