package com.synway.datarelation.service.common.impl;

import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.dao.datablood.StandardTableDao;
import com.synway.datarelation.service.common.StandardTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangdongwei
 * @date 2021/9/15 13:39
 */
@Service
@Slf4j
public class StandardTableServiceImpl implements StandardTableService {

    @Autowired
    private StandardTableDao standardTableDao;

    @Override
    public String getTableNameById(String tableId) {
        try{
            return  standardTableDao.getTableNameById(tableId);
        }catch (Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return "";
    }
}
