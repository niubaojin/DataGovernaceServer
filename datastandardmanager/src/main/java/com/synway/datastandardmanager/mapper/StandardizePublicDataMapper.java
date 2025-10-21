package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.interceptor.AuthorControl;

import java.util.List;

public interface StandardizePublicDataMapper extends BaseMapper<StandardizePublicDataEntity> {

    int updatePublicDataGroup(StandardizePublicDataEntity publicData);

    /**
     * 查询公共数据项分组名称列表
     */
    @AuthorControl(tableNames ={"synlte.STANDARDIZE_PUBLIC_DATA"},columnNames = {"group_name"})
    List<KeyValueVO> searchGroupNameList();

}
