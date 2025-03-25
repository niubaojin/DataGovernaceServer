package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.dao.master.OtherModuleManageDao;
import com.synway.datastandardmanager.pojo.StandardObjectManage;
import com.synway.datastandardmanager.pojo.StandardTableRelation;
import com.synway.datastandardmanager.pojo.sourcedata.OtherModuleManageCreated;
import com.synway.datastandardmanager.service.OtherModuleManageService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName OtherModuleManageServiceImpl
 * @description TODO
 * @date 2020/9/17 15:47
 */
@Service
public class OtherModuleManageServiceImpl  implements OtherModuleManageService {
    private Logger logger = LoggerFactory.getLogger(OtherModuleManageServiceImpl.class);
    @Autowired
    private OtherModuleManageDao otherModuleManageDao;

    /**
     * 如果 moduleName 不为空，则需要筛选出这个模块创建的表 非该模块创建的
     * @param standardTableRelationList
     * @param moduleName  模块名称
     * @return
     */
    @Override
    public List<StandardTableRelation> filterZtreeByModuleName(List<StandardTableRelation> standardTableRelationList, String moduleName) {
        // 如果第三方应用名称不为空，则需要筛选出只是该应用创建的标准表 id值为tableId的信息
        if(StringUtils.isBlank(moduleName)){
            return standardTableRelationList;
        }
        List<String> list = otherModuleManageDao.getOtherModuleObjectCreated(moduleName);
        if(standardTableRelationList.isEmpty()){
            return standardTableRelationList;
        }else if(list.isEmpty()){
            return new ArrayList<>();
        }else{
            List<StandardTableRelation> list1 = new ArrayList<>();
            for(StandardTableRelation tableRelation: standardTableRelationList){
                if(list.contains(tableRelation.getTableId().toUpperCase())){
                    list1.add(tableRelation);
                }
            }
            return list1;
        }
    }

    /**
     *  数据对表MODULE_CREATED_OBJECT 插入数据
     * @param standardObjectManage
     */
    @Override
    public void addOtherModuleCreated(StandardObjectManage standardObjectManage) {
        try{
            String moduleName =  URLDecoder.decode(standardObjectManage.getModuleName(),"UTF8");
            if(StringUtils.isNotBlank(moduleName)){
                OtherModuleManageCreated otherModuleManageCreated = new OtherModuleManageCreated();
                otherModuleManageCreated.setModuleName(moduleName);
                otherModuleManageCreated.setTableId(standardObjectManage.getTableId());
                otherModuleManageCreated.setObjectId(standardObjectManage.getObjectId());
                int countObject = otherModuleManageDao.getObjectCreatedById(standardObjectManage.getTableId(),moduleName);
                if(countObject == 0){
                    otherModuleManageDao.addOtherModuleCreated(otherModuleManageCreated);
                }
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }
}
