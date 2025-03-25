package com.synway.governace.dao;

import com.synway.governace.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ThresholdConfigDao {

    List<ThresholdConfig> getThresholdConfigInitInfo();

}
