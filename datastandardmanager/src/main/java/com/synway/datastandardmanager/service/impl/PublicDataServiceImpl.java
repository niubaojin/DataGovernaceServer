package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataFieldEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.KeyStrEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.mapper.StandardizePublicDataFieldMapper;
import com.synway.datastandardmanager.mapper.StandardizePublicDataMapper;
import com.synway.datastandardmanager.mapper.SynlteFieldMapper;
import com.synway.datastandardmanager.service.PublicDataService;
import com.synway.datastandardmanager.service.UserAuthorityService;
import com.synway.datastandardmanager.util.EasyExcelUtil;
import com.synway.datastandardmanager.util.UUIDUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PublicDataServiceImpl implements PublicDataService {

    @Resource
    StandardizePublicDataMapper standardizePublicDataMapper;
    @Resource
    StandardizePublicDataFieldMapper standardizePublicDataFieldMapper;
    @Resource
    SynlteFieldMapper synlteFieldMapper;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Resource
    UserAuthorityService userAuthorityService;

    @Override
    public String addOrUpdateOnePublicData(StandardizePublicDataEntity publicData) {
        try {
            log.info(">>>>>>添加的公共数据项内容为：{}", JSONObject.toJSONString(publicData));
            if (StringUtils.isBlank(publicData.getId())) {
                LambdaQueryWrapper<StandardizePublicDataEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(StandardizePublicDataEntity::getGroupName, publicData.getGroupName());
                if (standardizePublicDataMapper.selectCount(wrapper) != 0) {
                    throw new Exception(String.format("%s：公共数据项组名称重复", ErrorCodeEnum.CHECK_UNION_ERROR));
                }
                String uuid = UUIDUtil.getUUID();
                publicData.setId(uuid);
                standardizePublicDataMapper.insert(publicData);
//                // 添加公共数据项数据后，将信息插入或更新到用户权限表USER_AUTHORITY中
//                ObjectManageDTO objectManageDTO = new ObjectManageDTO();
//                ObjectEntity objectEntity = new ObjectEntity();
//                objectEntity.setObjectName(publicData.getMemo());
//                objectManageDTO.setObjectPojoTable(objectEntity);
//                objectManageDTO.setTableId(publicData.getGroupName());
//                userAuthorityService.addUserAuthorityData(objectManageDTO);

                List<StandardizePublicDataFieldEntity> publicDataFieldList = publicData.getPublicDataFieldList();
                if (publicDataFieldList == null || publicDataFieldList.isEmpty()) {
                    throw new Exception(String.format("%s：数据项列表为空", ErrorCodeEnum.CHECK_ERROR));
                }
                int recno = 1;
                for (StandardizePublicDataFieldEntity data : publicDataFieldList) {
                    data.setId(UUIDUtil.getUUID());
                    data.setGroupId(uuid);
                    data.setRecno(recno);
                    recno++;
                }
                standardizePublicDataFieldMapper.insertPublicDataFieldList(publicDataFieldList);
                // 发送操作日志
                operateLogServiceImpl.publicDataManageSuccessLog(OperateLogHandleTypeEnum.ADD, "公共数据项管理", publicData.getGroupName());
                return Common.ADD_SUCCESS;
            } else {
                standardizePublicDataMapper.updatePublicDataGroup(publicData);
                List<StandardizePublicDataFieldEntity> publicDataFieldList = publicData.getPublicDataFieldList();
                LambdaQueryWrapper<StandardizePublicDataFieldEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(StandardizePublicDataFieldEntity::getGroupId, publicData.getId());
                standardizePublicDataFieldMapper.delete(wrapper);
                int recno = 1;
                if (!publicDataFieldList.isEmpty() && publicDataFieldList != null) {
                    for (StandardizePublicDataFieldEntity data : publicDataFieldList) {
                        data.setId(UUIDUtil.getUUID());
                        data.setGroupId(publicData.getId());
                        data.setRecno(recno);
                        recno++;
                    }
                    standardizePublicDataFieldMapper.insertPublicDataFieldList(publicDataFieldList);
                    // 发送操作日志
                    operateLogServiceImpl.publicDataManageSuccessLog(OperateLogHandleTypeEnum.ALTER, "公共数据项管理", publicData.getGroupName());
                }

            }
            return Common.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>公共数据项更新失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    @Override
    public String deleteOnePublicDataGroup(String id, String groupName) {
        try {
            log.info(">>>>>>开始删除STANDARDIZE_PUBLIC_DATA表的组织名称为:{}的数据", groupName);

            LambdaQueryWrapper<StandardizePublicDataFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizePublicDataFieldEntity::getGroupId, id);
            standardizePublicDataFieldMapper.delete(wrapper);

            LambdaQueryWrapper<StandardizePublicDataEntity> wrapperG = Wrappers.lambdaQuery();
            wrapperG.eq(StandardizePublicDataEntity::getId, id);
            wrapperG.eq(StandardizePublicDataEntity::getGroupName, groupName);
            standardizePublicDataMapper.delete(wrapperG);
            // 发送操作日志
            operateLogServiceImpl.publicDataManageSuccessLog(OperateLogHandleTypeEnum.DELETE, "公共数据项管理", groupName);
            return Common.DEL_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>删除公共数据项失败：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    public List<KeyValueVO> searchFieldChineseList(String searchText) {
        List<KeyValueVO> resultList = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询数据项中文名称列表");
            if (StringUtils.isBlank(searchText)) {
                searchText = null;
            }
            resultList = synlteFieldMapper.searchFieldChineseList(searchText);
            if (resultList == null || resultList.isEmpty()) {
                return resultList;
            }
            resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                            .compare(s2.getLabel(), s1.getLabel()))
                    .limit(100).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(">>>>>>获取数据元中文名称下拉列表信息报错：", e);
        }
        return resultList;
    }

    @Override
    public StandardizePublicDataEntity searchPublicDataByGroupName(String groupName) {
        StandardizePublicDataEntity publicData = new StandardizePublicDataEntity();
        try {
            log.info(">>>>>>要查询的公共数据项组的名称为{}", groupName);
            LambdaQueryWrapper<StandardizePublicDataEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizePublicDataEntity::getGroupName, groupName);
            publicData = standardizePublicDataMapper.selectOne(wrapper);
            if (StringUtils.isBlank(publicData.getId())) {
                throw new Exception(String.format("%s：数据项分组[%s]在数据库中对应的数据不存在，查询失败", ErrorCodeEnum.CHECK_PARAMETER_ERROR, groupName));
            }
            List<StandardizePublicDataFieldEntity> publicDataFields = standardizePublicDataFieldMapper.searchPublicDataFieldById(publicData.getId());
            publicDataFields.stream().forEach(d -> {
                if (d.getMd5Index() != null && d.getMd5Index() != 0) {
                    d.setMd5IndexStatus(true);
                }
            });
            if (publicDataFields.isEmpty() || publicDataFields == null) {
                List<StandardizePublicDataFieldEntity> tempList = new ArrayList<>();
                publicData.setPublicDataFieldList(tempList);
            }
            publicData.setPublicDataFieldList(publicDataFields);
        } catch (Exception e) {
            log.error(">>>>>>根据groupId查询公共数据项失败：", e);
        }
        return publicData;
    }

    @Override
    public List<KeyValueVO> searchGroupNameList() {
        try {
            return standardizePublicDataMapper.searchGroupNameList();
        } catch (Exception e) {
            log.error(">>>>>>查询公共数据项分组名称列表报错：", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void downloadPublicDataFieldExcel(HttpServletResponse response, List<StandardizePublicDataFieldEntity> fieldEntities, Object object) {
        try {
            log.info(">>>>>>开始下载公共数据项的相关信息");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("公共数据项管理表.xlsx", "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            for (StandardizePublicDataFieldEntity data : fieldEntities) {
                data.setIsQueryStr(KeyStrEnum.getValueByKeyAndType("1_" + data.getIsQuery(), Common.DETERMINER_ENUM));
                data.setIsContorlStr(KeyStrEnum.getValueByKeyAndType("1_" + data.getIsContorl(), Common.DETERMINER_ENUM));
                data.setNeedValueStr(KeyStrEnum.getValueByKeyAndType("1_" + data.getNeedValue(), Common.DETERMINER_ENUM));
                data.setMd5IndexStr((data.getMd5Index() != null && data.getMd5Index() != 0) ? "是" : "否");
            }
            EasyExcel.write(response.getOutputStream(), object.getClass())
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("公共数据项管理")
                    .doWrite(fieldEntities);
            log.info(">>>>>>下载公共数据项的相关信息结束");
        } catch (Exception e) {
            log.error(">>>>>>下载公共数据项报错：", e);
        }
    }

    @Override
    public List<StandardizePublicDataFieldEntity> importPublicDataFieldExcel(MultipartFile file, String id) {
        log.info(">>>>>>开始将文件：{}导入到公共数据项", file.getName());
        ExcelListener<StandardizePublicDataFieldEntity> listener = new ExcelListener<>();
        List<StandardizePublicDataFieldEntity> successList = new ArrayList<>();
        try {
            List<StandardizePublicDataFieldEntity> list = EasyExcelUtil.readExcelUtil(file, new StandardizePublicDataFieldEntity(), listener);
            int i = 1;
            // 这里插入每一行数据，需要验证elementcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                StandardizePublicDataFieldEntity elementManage = (StandardizePublicDataFieldEntity) iterator.next();
                StandardizePublicDataFieldEntity addPublicDataFieldManage = new StandardizePublicDataFieldEntity();
                try {
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(addPublicDataFieldManage, elementManage);
                    addPublicDataFieldManage.setIsQuery((StringUtils.isNotBlank(addPublicDataFieldManage.getIsQueryStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getIsQueryStr())) ? 1 : 0);
                    addPublicDataFieldManage.setIsContorl((StringUtils.isNotBlank(addPublicDataFieldManage.getIsContorlStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getIsContorlStr())) ? 1 : 0);
                    addPublicDataFieldManage.setNeedValue((StringUtils.isNotBlank(addPublicDataFieldManage.getNeedValueStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getNeedValueStr())) ? 1 : 0);
                    if (StringUtils.isNotBlank(addPublicDataFieldManage.getMd5IndexStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getMd5IndexStr())) {
                        addPublicDataFieldManage.setMd5Index(i);
                        i++;
                    } else {
                        addPublicDataFieldManage.setMd5Index(null);
                    }
                    successList.add(addPublicDataFieldManage);
                    iterator.remove();
                } catch (Exception e) {
                    log.error(String.format(">>>>>>数据项：%s插入失败，：%s", elementManage.getFieldChineseName(), e.getMessage()));
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>导入文件报错：", e);
            throw new NullPointerException("导入文件报错：" + e.getMessage());
        }
        log.info(">>>>>>导入公共数据项的相关信息结束,导入成功的数据量为：" + successList.size());
        return successList;
    }

}
