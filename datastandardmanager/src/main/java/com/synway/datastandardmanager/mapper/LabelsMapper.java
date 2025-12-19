package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.LabelsDTO;
import com.synway.datastandardmanager.entity.pojo.LabelsEntity;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelChildrenVO;
import com.synway.datastandardmanager.interceptor.AuthorControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelsMapper extends BaseMapper<LabelsEntity> {

    /**
     * 获取左侧标签汇总树的相关信息
     */
    List<SelectFieldVO> getAllLabelTreeDataDao();

    /**
     * 根据标签等级查询其下所有标签的中文名称和id
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<ValueLabelChildrenVO> searchSecondLabelList(@Param("labelLevel") String labelLevel);

    /**
     * 查询出
     */
    List<LabelsEntity> getAllLabelManageData(LabelsDTO queryParames);

    List<ValueLabelVO> getLabelManageDataByClassId(@Param("classId") String classId, @Param("labelLevel") Integer labelLevel);

    List<LabelsEntity> getLabelManageByLabelCode(@Param("list")List<String> list);

}
