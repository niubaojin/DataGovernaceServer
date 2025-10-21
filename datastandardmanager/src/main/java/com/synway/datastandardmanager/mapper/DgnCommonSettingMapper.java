package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.DgnCommonSettingEntity;

public interface DgnCommonSettingMapper extends BaseMapper<DgnCommonSettingEntity> {

    // 查询版本
    String searchVersion();

}
