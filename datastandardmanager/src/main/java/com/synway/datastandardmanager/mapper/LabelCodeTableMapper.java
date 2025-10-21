package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.LabelCodeTableEntity;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;

import java.util.List;

public interface LabelCodeTableMapper extends BaseMapper<LabelCodeTableEntity> {

    /**
     * 查询标签名称的统计列表
     */
//    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<SelectFieldVO> searchLabelTotalList();

    List<SelectFieldVO> getLabelTypeList();

}
