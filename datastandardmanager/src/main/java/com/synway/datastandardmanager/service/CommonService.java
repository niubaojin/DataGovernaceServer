package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.vo.KeyValueVO;

import java.util.List;

public interface CommonService {

    /**
     * 获取厂商信息
     */
    List<KeyValueVO> searchValtext();

}
