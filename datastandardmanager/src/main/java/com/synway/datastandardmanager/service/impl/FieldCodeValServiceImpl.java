package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.dao.master.FieldCodeValDao;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.service.FieldCodeValService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.List;

/**
 * @ClassName
 * @author obito
 * @version 1.0
 * @date
 */
@Service
@Slf4j
public class FieldCodeValServiceImpl implements FieldCodeValService {

    @Autowired
    private FieldCodeValDao fieldCodeValDao;

    @Override
    public List<PageSelectOneValue> searchValtext(){
        List<PageSelectOneValue> valtextList = fieldCodeValDao.searchValtext();
        if(valtextList.isEmpty()){
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,"synlte.fieldcodeval表中没有数据");
        }
        valtextList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(),s2.getLabel()));
        return valtextList;
    }
}
