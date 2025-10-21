package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.dto.DeterminerDTO;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FieldDeterminerMapper extends BaseMapper<FieldDeterminerEntity> {

    List<FieldDeterminerEntity> getFieldDeterminerTable(DeterminerDTO parameter);

    int upOneData(FieldDeterminerEntity data);

    int updateDeterminerState(@Param("id") String id,
                              @Param("state") String state,
                              @Param("modDate") String modDate,
                              @Param("releaseDate") Integer releaseDate);

    int getCountByIdModDate(@Param("id") String id,
                            @Param("modDate") String modDate);

    List<String> findAllDBNameList(@Param("name") String name);

    /**
     * 获取所有的限定词内部编码
     */
    List<String> getDeterminerIdList();

    /**
     * 查询状态为05的限定词名称列表
     */
    List<KeyValueVO> searchFieldDeterminerNameList(@Param("searchName") String searchName);

}
