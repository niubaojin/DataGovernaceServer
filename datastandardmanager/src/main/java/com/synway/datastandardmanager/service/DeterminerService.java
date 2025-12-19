package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.DeterminerDTO;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerEntity;
import com.synway.datastandardmanager.entity.vo.FieldDeterminerFilterVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;

import java.util.List;

public interface DeterminerService {

    List<FieldDeterminerEntity> getFieldDeterminerTable(DeterminerDTO determinerDTO);

    FieldDeterminerFilterVO getFilterObject();

    String addOneData(FieldDeterminerEntity data);

    String upOneData(FieldDeterminerEntity data);

    String updateDeterminerState(String id,
                                 String state,
                                 String modDate);

    String getDNameByChines(String chineseName);

    String getDeterminerId();

    List<ValueLabelVO> searchDeterminerNameList(String searchName);

}
