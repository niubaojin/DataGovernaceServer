package com.synway.datastandardmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.BatchOperationDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.PublicDataInfoEntity;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.BatchOperationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
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

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectFieldMapper objectFieldMapper;
    @Resource
    private PublicDataInfoMapper publicDataInfoMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;

    @Resource
    private Environment env;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String objectStatusEdit(BatchOperationDTO dto) {
        // 先全部大写
        if (dto.getTableIdList() == null || dto.getTableIdList().isEmpty()) {
            log.info(">>>>>>需要更新的标准表数量为空");
            return Common.NOT_CHANGED;
        }
        dto.setTableIdList(dto.getTableIdList().stream().map(String::toUpperCase).collect(Collectors.toList()));
        // 在 public_data_info 中 0是弃用  1是启用
        log.info(">>>>>>开始更新标准表synlte.public_data_info的使用状态");
        LambdaUpdateWrapper<PublicDataInfoEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(PublicDataInfoEntity::getZyzt, dto.getStatus())
                .set(PublicDataInfoEntity::getGxsj, new Date())
                .in(PublicDataInfoEntity::getSjxjbm, dto.getTableIdList());
        publicDataInfoMapper.update(updateWrapper);

        // 在object表中，-1是弃用，0是临时启用，1是正式启用，而数据里面的值为 0 1 所有需要修改
        log.info(">>>>>>开始更新标准表synlte.object的使用状态");
        LambdaUpdateWrapper<ObjectEntity> updateWrapper1 = Wrappers.lambdaUpdate();
        Integer objectState = StringUtils.equals("0", dto.getStatus()) ? -1 : Integer.valueOf(dto.getStatus());
        updateWrapper1.set(ObjectEntity::getObjectState, objectState)
                .set(ObjectEntity::getUpdateTime, new Date())
                .in(ObjectEntity::getTableId, dto.getTableIdList());
        objectMapper.update(updateWrapper1);

        return Common.UPDATE_SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String objectClassifyEdit(BatchOperationDTO editPojo) {
        // 主要修改 synlte.object 里面的组织分类和来源分类、标签信息、更新时间和更新人
        // 数据组织分类的中文名 如果classIds不为空，则直接用classIds，否则用中文查询代码值
        String classIds = editPojo.getClassIds();
        //1级
        String parClassIds = "";
        //2级
        String secondClassIds = "";
        //3级
        String threeClassIds = "";

        if (StringUtils.isNotBlank(classIds)) {
            if (classIds.contains(",")) {
                //一、二、三级的组织分类数组
                String[] list = classIds.split(",");
                String sjzzflCodeId = env.getProperty("sjzzflCodeId");
                if (list[0].equalsIgnoreCase(sjzzflCodeId + "01") || list[0].equalsIgnoreCase(sjzzflCodeId + "03")){
                    parClassIds = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                    secondClassIds = list[1];
                    threeClassIds = list[2].split(secondClassIds)[1];
                    if (StringUtils.isNotBlank(parClassIds) && StringUtils.isNotBlank(threeClassIds)) {
                        editPojo.setSjzzyjfl(parClassIds);
                        editPojo.setSjzzejfl(threeClassIds);
                    }
                } else {
                    //非原始库时
                    if (list.length != 0) {
                        parClassIds = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                        secondClassIds = list[1].split(list[0])[1];
                        editPojo.setSjzzyjfl(StringUtils.isBlank(parClassIds) ? "" : parClassIds);
                        editPojo.setSjzzejfl(StringUtils.isBlank(secondClassIds) ? "" : secondClassIds);
                    }
                }
            } else {
                //当组织分类为其他的时候
                editPojo.setSjzzyjfl(classIds.split(Common.DATA_ORGANIZATION_CODE)[1]);
            }
        } else {
            log.info("数据组织分类为空，不需要存储这个的数据");
        }
        // 数据来源分类的中文名
        String sourceClassIds = editPojo.getSourceClassIds();
        if (StringUtils.isNotEmpty(sourceClassIds)) {
            List<String> sourceIdList = Arrays.asList(sourceClassIds.split(","));
            String sourceIds = sourceIdList.get(sourceIdList.size() - 1).split(sourceIdList.get(0))[1];
            editPojo.setSjzylylx(sourceIds);
        } else {
            log.info("数据来源分类为空，不需要存储这个的数据");
        }
        objectMapper.updateObjectClassifyAndSourceIfy(editPojo);

        return Common.UPDATE_SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String objectFieldEdit(BatchOperationDTO dto) throws Exception {
        if (dto.getColumnList() == null || dto.getColumnList().isEmpty()) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.CHECK_PARAMETER_ERROR, "建表字段不能为空"));
        }
        dto.setColumnList(dto.getColumnList().stream().filter(StringUtils::isNotBlank).map(String::toUpperCase).collect(Collectors.toList()));
        // 先修改synlte.objectfield表、字段可用状态、是否查询
        objectFieldMapper.updateObjectClassify(dto);

        // 字段分类还需要更新 synlte.SYNLTEFIELD
        LambdaUpdateWrapper<SynlteFieldEntity> update = Wrappers.lambdaUpdate();
        update.set(SynlteFieldEntity::getFieldClass, dto.getFieldClassId())
                .in(SynlteFieldEntity::getColumnName, dto.getColumnList());
        synlteFieldMapper.update(update);

        return Common.UPDATE_SUCCESS;
    }


}
