package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.PageSelectOneValue;

import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
public interface FieldCodeValService {

    /**
     * 获取厂商信息
     * @return
     */
    List<PageSelectOneValue> searchValtext();
}
