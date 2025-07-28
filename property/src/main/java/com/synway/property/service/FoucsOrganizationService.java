package com.synway.property.service;


import com.alibaba.fastjson.JSONArray;
import com.synway.property.pojo.ClassifyInformation;
import com.synway.property.pojo.DataRankingTop;
import com.synway.property.pojo.FoucsOrganizationFull;
import com.synway.common.bean.ServerResponse;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;

import java.util.List;

/**
 * 重点组织监控的相关代码
 * @author 数据接入
 */
public interface FoucsOrganizationService {
    ServerResponse<List<FoucsOrganizationFull>> getAllFoucsOrganization();

    ServerResponse<List<ClassifyInformation>> getClassifyInformationTableService(String mainClassifyCh,String primaryClassifyCh);

    ServerResponse<String> insertClassifyInformationTableService(List<ClassifyInformation> insertDataList);

    ServerResponse<List<ClassifyInformation>> getAllClassifyInformationTableService();

    JSONArray getDayUseHeatService();


    ServerResponse<DataRankingTop> getFullDataRankingService();

    ServerResponse<DataRankingTop> getIncrementalDataRankingService();

    ServerResponse<List<ClassifyInfoTree>> getClassTreeList();
}
