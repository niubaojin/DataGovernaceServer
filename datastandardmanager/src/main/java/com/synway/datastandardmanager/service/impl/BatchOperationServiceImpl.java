package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.BatchOperationDao;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectClassifyEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectFieldEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectStatusEditPojo;
import com.synway.datastandardmanager.service.BatchOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @date 2021/7/15 10:21
 */
@Slf4j
@Service
public class BatchOperationServiceImpl implements BatchOperationService {

    @Autowired
    private BatchOperationDao batchOperationDao;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String objectStatusEdit(ObjectStatusEditPojo objectStatusEditPojo) {
        // 先全部大写
        if( objectStatusEditPojo.getTableIdList() == null ||
                objectStatusEditPojo.getTableIdList().isEmpty()){
            log.info("需要更新的标准表数量为空");
            return "需要更新的标准表数量为空";
        }else{
            objectStatusEditPojo.setTableIdList(objectStatusEditPojo.getTableIdList()
                    .stream().map(String::toUpperCase).collect(Collectors.toList()));
        }
        // 在 public_data_info 中 0是弃用  1是启用
        log.info("开始更新标准表synlte.public_data_info的使用状态");
        int updateNum = batchOperationDao.updatePublicDataInfoStatus(objectStatusEditPojo.getTableIdList(),objectStatusEditPojo.getStatus());
        log.info("表synlte.public_data_info中{}条记录被更新为{}",updateNum,objectStatusEditPojo.getStatus());
        // 在object表中  -1 是弃用 0是临时启用  1是正式启用  而数据里面的值为 0 1 所有需要修改
        log.info("开始更新标准表synlte.object的使用状态");
        int updateNum1 =batchOperationDao.updateObjectStatus(objectStatusEditPojo.getTableIdList(),
                StringUtils.equals("0",objectStatusEditPojo.getStatus())?"-1":objectStatusEditPojo.getStatus());
        log.info("表synlte.object中{}条记录被更新为{}",updateNum1,objectStatusEditPojo.getStatus());
        return "更新状态成功";
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public String objectClassifyEdit(ObjectClassifyEditPojo editPojo) {
        // 主要修改 synlte.object 里面的组织分类和来源分类、标签信息、更新时间和更新人
        // 数据组织分类的中文名 如果classIds不为空，则直接用classIds，否则用中文查询代码值
        String classIds = editPojo.getClassIds();
        //1级
        String parClassIds = "";
        //2级
        String secondClassIds = "";
        //3级
        String threeClassIds = "";

        if(StringUtils.isNotBlank(classIds)){
            if(classIds.contains(",")){
                //一、二、三级的组织分类数组
                String[] list = classIds.split(",");
                if("JZCODEGASJZZFL01".equals(list[0]) || "JZCODEGASJZZFL03".equals(list[0])){
                    parClassIds = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                    secondClassIds = list[1];
                    threeClassIds = list[2].split(secondClassIds)[1];
                    if(StringUtils.isNotBlank(parClassIds) && StringUtils.isNotBlank(threeClassIds)){
                        editPojo.setSjzzyjfl(parClassIds);
                        editPojo.setSjzzejfl(threeClassIds);
                    }
                }else {
                    //非原始库时
                    if (list.length != 0) {
                        parClassIds = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                        secondClassIds = list[1].split(list[0])[1];
                        editPojo.setSjzzyjfl(StringUtils.isBlank(parClassIds) ? "" : parClassIds);
                        editPojo.setSjzzejfl(StringUtils.isBlank(secondClassIds) ? "" : secondClassIds);
                    }
                }
            }else {
                //当组织分类为其他的时候
                editPojo.setSjzzyjfl(classIds.split(Common.DATA_ORGANIZATION_CODE)[1]);
            }
        }
        else{
            log.info("数据组织分类为空，不需要存储这个的数据");
        }
        // 数据来源分类的中文名
        String sourceClassIds = editPojo.getSourceClassIds();
        if(StringUtils.isNotEmpty(sourceClassIds)){
            List<String> sourceIdList = Arrays.asList(sourceClassIds.split(","));
            String sourceIds = sourceIdList.get(sourceIdList.size()-1).split(sourceIdList.get(0))[1];
            editPojo.setSjzylylx(sourceIds);
        }
        else{
            log.info("数据来源分类为空，不需要存储这个的数据");
        }

        int updateNum1 = batchOperationDao.updateObjectClassifyAndSourceIfy(editPojo);
        log.info("表synlte.OBJECT中{}条记录被更新",updateNum1);
        return "分类信息更新成功";
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String objectFieldEdit(ObjectFieldEditPojo editPojo) {
        if(editPojo.getColumnList() == null || editPojo.getColumnList().isEmpty()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    "建表字段不能为空");
        }
        editPojo.setColumnList(editPojo.getColumnList().stream().filter(StringUtils::isNotBlank)
                .map(String::toUpperCase).collect(Collectors.toList()));
        // 1:先修改 synlte.objectfield 表  字段可用状态、是否查询
        log.info("开始修改synlte.objectfield指定字段表的分类信息");
        int num1 = batchOperationDao.updateObjectClassify(editPojo);
        log.info("表synlte.FIELD_RESOURCE_INFO里面更新的条数为{}",num1);

        //2：修改 synlte.FIELD_RESOURCE_INFO 里面的 字段分类、敏感度分类，是否订阅，是否查询 表
        log.info("开始修改指定字段表的分类信息");
        int num = batchOperationDao.updateFieldResourceInfoClassify(editPojo);
        log.info("表synlte.FIELD_RESOURCE_INFO里面更新的条数为{}",num);

        // 3:字段分类还需要更新 synlte.SYNLTEFIELD
        int num2 = batchOperationDao.updateSynlteField(editPojo);
        log.info("表synlte.SYNLTEFIELD里面更新的条数为{}",num);

        return "数据更新成功";
    }


}
