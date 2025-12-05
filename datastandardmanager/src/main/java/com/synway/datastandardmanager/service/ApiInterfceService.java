package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.GetTreeReqDTO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.TreeNodeValueVO;

import java.util.List;
import java.util.Map;

/**
 * 对外的接口统一的service层
 *
 * @author wangdongwei
 */
public interface ApiInterfceService {

    /**
     * 数据仓库那边调用的接口
     *
     * @param sourceId
     * @param sourceCode
     * @param sourceFirmCode
     */
    List<KeyValueVO> getStandardOutTableIdBySourceIdService(String sourceId, String sourceCode, String sourceFirmCode);

    /**
     * 质量检测功能需要根据 sourceId 查询对应的 标准表信息
     *
     * @param sourceId
     */
    List<Map<String, String>> getStandardTableBySourceId(String sourceId);

    /**
     *  提供给vue页面的接口，查询分级分类的tree树
     * @param req
     * @param isQueryTable
     * @param showLabel
     */
    List<TreeNodeValueVO> externalgetTableOrganizationTree(GetTreeReqDTO req, Boolean isQueryTable, Boolean showLabel);

}
