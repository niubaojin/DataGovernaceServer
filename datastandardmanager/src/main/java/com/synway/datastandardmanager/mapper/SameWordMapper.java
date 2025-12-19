package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.SameWordEntity;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SameWordMapper extends BaseMapper<SameWordEntity> {

    List<ValueLabelVO> getSameWordList(@Param("searchName") String searchName);

    int deleteOneSemanticTableDao(@Param("sameId") String sameId, @Param("uuid") String uuid);

    int uploadSemanticFileDao(@Param("rcList") List<SameWordEntity> samewordList);

}
