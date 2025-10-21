package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.ZcConfigFieldControlEntity;

public interface ZcConfigFieldControlMapper extends BaseMapper<ZcConfigFieldControlEntity> {

    void updateBuildTableShowField(String showField, String userName);

}
