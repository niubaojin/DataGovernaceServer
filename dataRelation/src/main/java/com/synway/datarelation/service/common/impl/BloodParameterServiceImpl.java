package com.synway.datarelation.service.common.impl;

import com.synway.datarelation.pojo.databloodline.impactanalysis.DetailedTableByClassify;
import com.synway.datarelation.service.common.BloodParameterService;
import com.synway.datarelation.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/4 19:25
 */
@Service
@Slf4j
public class BloodParameterServiceImpl  implements BloodParameterService {

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Override
    public boolean checkTableIsExist(String tableName, String baseType) {

//        List<DetailedTableByClassify> data = restTemplateHandle.getDetailedTable(paramList);

        return false;
    }

}
