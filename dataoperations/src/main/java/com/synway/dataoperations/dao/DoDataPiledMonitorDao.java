package com.synway.dataoperations.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.dataoperations.entity.pojo.DoDataPiledMonitorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DoDataPiledMonitorDao extends BaseMapper<DoDataPiledMonitorEntity> {
}
