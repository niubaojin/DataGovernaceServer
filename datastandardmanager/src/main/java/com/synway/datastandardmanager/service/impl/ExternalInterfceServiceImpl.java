package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.dao.master.StandardResourceManageDao;
import com.synway.datastandardmanager.pojo.ExternalInterfce.TreeNodeVue;
import com.synway.datastandardmanager.pojo.ObjectPojo;
import com.synway.datastandardmanager.pojo.ObjectPojoTable;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.enums.ManufacturerName;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;
import com.synway.datastandardmanager.service.ExternalInterfceService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExternalInterfceServiceImpl implements ExternalInterfceService {
    @Autowired
    StandardResourceManageDao standardResourceManageDao;
    @Autowired
    ResourceManageAddService resourceManageAddServiceImpl;

    @Autowired
    ResourceManageAddDao resourceManageAddDao;

    private Logger logger = LoggerFactory.getLogger(ExternalInterfceServiceImpl.class);


    @Override
    public List<PageSelectOneValue> getStandardOutTableIdBySourceIdService(String sourceId, String sourceCode, String sourceFirmCode) {
        // 来源厂商只能是中文
        Integer sourceFirmNum = ManufacturerName.getIndexByName(sourceFirmCode);
        List<PageSelectOneValue> pageSelectOneValueList = standardResourceManageDao.getStandardOutTableIdBySourceIdDao(sourceId , sourceCode ,String.valueOf(sourceFirmNum) );
        logger.info("查询结果为："+ JSONObject.toJSONString(pageSelectOneValueList));
        return pageSelectOneValueList;
    }

    @Override
    public Integer findStandByDataId(String dataId) {
        Integer useCount = standardResourceManageDao.findStandByDataIdDao(dataId );
        if(useCount >0){
            return 1;
        }else{
            return 0;
        }
    }


    @Override
    public List<Map<String,String>> getStandardTableBySourceId(String sourceId) {
        List<PageSelectOneValue> pageSelectOneValueList = standardResourceManageDao.getStandardOutTableIdBySourceIdDao(sourceId,"","");
        List<Map<String,String>> jsonObjects = new ArrayList<>();
        for(PageSelectOneValue pageSelectOneValue:pageSelectOneValueList){
            Map<String,String> map = new HashMap<>(1);
            map.put(Common.TABLE_ID,pageSelectOneValue.getValue());
            jsonObjects.add(map);
        }
        logger.info("查询结果为："+ JSONObject.toJSONString(jsonObjects));
        return jsonObjects;
    }

    @Override
    public List<ObjectPojo> getObjectByUserId(Integer userId){

        List<ObjectPojo> list = resourceManageAddDao.getObjectPojoByUserId(userId);
        return list;
    }
}
