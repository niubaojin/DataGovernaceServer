package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.HisVersionInfoDTO;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerVersionEntity;

import java.util.List;

public interface FieldDeterminerVersionMapper extends BaseMapper<FieldDeterminerVersionEntity> {

    /**
     * 表格查询限定词_version的数据
     */
    List<FieldDeterminerVersionEntity> searchFieldDeterminerVersionTable(HisVersionInfoDTO parameter);

}
