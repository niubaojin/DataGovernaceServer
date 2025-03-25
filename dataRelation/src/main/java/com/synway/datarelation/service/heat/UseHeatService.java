package com.synway.datarelation.service.heat;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface UseHeatService {

    void getUseHeatTable(String sqlValue,
                         String dbType,
                         String nodeProjectName,
                         Map<String, Integer> countTableUseMap);

    @Transactional(rollbackFor = Exception.class)
    void inertUseTableToOracle(Map<String, Integer> countTableUseMap) throws Exception;
}
