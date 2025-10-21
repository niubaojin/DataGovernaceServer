package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.HisVersionInfoDTO;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldVersionEntity;

import java.util.List;

public interface SynlteFieldVersionMapper extends BaseMapper<SynlteFieldVersionEntity> {

    /**
     * 表格查询数据元_version的数据
     */
    List<SynlteFieldVersionEntity> searchSynlteFieldVersionTable(HisVersionInfoDTO parameter);

}
