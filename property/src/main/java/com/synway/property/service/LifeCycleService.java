package com.synway.property.service;

import com.synway.property.pojo.lifecycle.*;

import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2021/2/19 9:48
 */
public interface LifeCycleService {
    LifeCyclePageReturn getLifeCycleInfo(LifeCyclePageParams queryParams);

    Map getValDensity(ValDensityPageParam queryParams);

    void updateAllValDensity(boolean flag);

    void saveValDensity(ValDensityPageParam queryParams);

    LifeCyclePageReturn getLifeCycleFilters(LifeCyclePageParams queryParams);

    List<Double> updateValDensity(LifeCyclePageParams queryParams);

    void updateLifeCycleShowField(String lifeCycleShowFields);

    List<String> getLifeCycleShowField();
}
