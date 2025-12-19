package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.vo.ValueLabelVO;

import java.util.List;

public interface CommonService {

    /**
     * 获取厂商信息
     */
    List<ValueLabelVO> searchValtext();

}
