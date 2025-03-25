package com.synway.governace.service.systemConfig.impl;

import com.synway.governace.dao.ThresholdConfigDao;
import com.synway.governace.pojo.*;
import com.synway.governace.service.systemConfig.ThresholdConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ThresholdConfigServiceImpl implements ThresholdConfigService {
    private Logger logger = Logger.getLogger(ThresholdConfigServiceImpl.class);
    @Autowired
    private ThresholdConfigDao thresholdConfigDao;

    @Override
    public List<ThresholdConfig> getThresholdConfigInitInfo() {
        return thresholdConfigDao.getThresholdConfigInitInfo();
    }

}
