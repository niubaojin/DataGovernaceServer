package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.HisVersionInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectVersionEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObjectVersionMapper extends BaseMapper<ObjectVersionEntity> {

    /**
     * 表格查询object_version的数据
     */
    List<ObjectVersionEntity> searchObjectVersionTable(HisVersionInfoDTO parameter);

    /**
     * 大版本号筛选值
     */
    List<KeyValueVO> searchVersionsList(@Param("table") String table);

    /**
     * 修订人筛选值
     */
    List<KeyValueVO> searchAuthorList(@Param("table") String table);

}
