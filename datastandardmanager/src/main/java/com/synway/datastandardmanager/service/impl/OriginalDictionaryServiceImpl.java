package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.dto.StandardDictionaryDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDFEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDictEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.entity.vo.TreeNodeValueVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.mapper.FieldCodeMapper;
import com.synway.datastandardmanager.mapper.FieldCodeValMapper;
import com.synway.datastandardmanager.mapper.StandardizeOriginalDFMapper;
import com.synway.datastandardmanager.mapper.StandardizeOriginalDictMapper;
import com.synway.datastandardmanager.service.OriginalDictionaryService;
import com.synway.datastandardmanager.service.UserAuthorityService;
import com.synway.datastandardmanager.util.EasyExcelUtil;
import com.synway.datastandardmanager.util.UUIDUtil;
import com.synway.datastandardmanager.util.ValidatorUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OriginalDictionaryServiceImpl implements OriginalDictionaryService {

    @Resource
    StandardizeOriginalDictMapper standardizeOriginalDictMapper;
    @Resource
    StandardizeOriginalDFMapper standardizeOriginalDFMapper;
    @Resource
    FieldCodeMapper fieldCodeMapper;
    @Resource
    FieldCodeValMapper fieldCodeValMapper;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;
    @Autowired
    private UserAuthorityService userAuthorityService;

    @Override
    public List<TreeNodeValueVO> getLeftTreeInfo() {
        log.info(">>>>>>开始获取原始字典管理的左侧树信息");
        List<StandardizeOriginalDictEntity> dictionaryList = standardizeOriginalDictMapper.searchLeftTreeInfo();
        if (dictionaryList.isEmpty() || dictionaryList == null) {
            return new ArrayList<>();
        }
        return dictionaryList.stream()
                .filter(d -> StringUtils.isNotEmpty(d.getFacturer()))
                .collect(Collectors.groupingBy(originalDictionary -> originalDictionary.getFacturer()))
                .entrySet().stream()
                .map(entry -> createLabelTreeNodeVue(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private TreeNodeValueVO createLabelTreeNodeVue(String facturer, List<StandardizeOriginalDictEntity> dictionaryList) {
        TreeNodeValueVO rootNode = new TreeNodeValueVO();
        rootNode.setId(facturer);
        rootNode.setLevel(1);
        rootNode.setSortLevel(1);
        rootNode.setLabel(facturer + "(" + dictionaryList.size() + ")");
        rootNode.setChildren(dictionaryList.stream()
                .map(data -> {
                    TreeNodeValueVO childrenNode = new TreeNodeValueVO();
                    childrenNode.setLabel(data.getDictionaryName());
                    childrenNode.setLevel(2);
                    childrenNode.setId(data.getId());
                    childrenNode.setParent(facturer);
                    return childrenNode;
                })
                .collect(Collectors.toList()));
        return rootNode;
    }

    @Override
    public String addOrUpdateOneOriginalDictionary(StandardizeOriginalDictEntity standardizeOriginalDict) {
        // 是否新增
        boolean isAdd = StringUtils.isBlank(standardizeOriginalDict.getId());
        if (isAdd) {
            return addNewOriginalDictionary(standardizeOriginalDict, isAdd);
        } else {
            return updateOriginalDictionary(standardizeOriginalDict, isAdd);
        }
    }

    private String addNewOriginalDictionary(StandardizeOriginalDictEntity standardizeOriginalDict, boolean isAdd) {
        try {
            log.info(">>>>>>开始新增原始字典表");
            LambdaQueryWrapper<StandardizeOriginalDictEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizeOriginalDictEntity::getDictionaryName, standardizeOriginalDict.getDictionaryName());
            wrapper.eq(StandardizeOriginalDictEntity::getFacturer, standardizeOriginalDict.getFacturer());
            if (standardizeOriginalDictMapper.selectCount(wrapper) != 0) {
                throw new Exception(String.format("%s：该厂商下已存在相同的字典名称", ErrorCodeEnum.CHECK_UNION_ERROR));
            }
            String uuid = UUIDUtil.getUUID();
            standardizeOriginalDict.setId(uuid);
            standardizeOriginalDict.setFacturerId(standardizeOriginalDict.getFacturer());
            standardizeOriginalDict.setCreateDate(new Date());
            standardizeOriginalDict.setCreateTime(new Date());
            standardizeOriginalDict.setUpdateTime(new Date());
            if (standardizeOriginalDictMapper.insert(standardizeOriginalDict) != 1) {
                throw new Exception(String.format("%s：数据新增失败", ErrorCodeEnum.DATA_IS_NULL));
            }
            handleDictionaryFieldList(standardizeOriginalDict, uuid, isAdd);
            insertOrUpdateUserAuthority(standardizeOriginalDict, OperateLogHandleTypeEnum.ADD);
            return Common.ADD_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>新增原始字典表失败：", e);
            return Common.ADD_FAIL;
        }
    }

    private String updateOriginalDictionary(StandardizeOriginalDictEntity standardizeOriginalDict, boolean isAdd) {
        try {
            log.info(">>>>>>开始更新原始字典表");
            if (standardizeOriginalDictMapper.updateOneOriginalDictionary(standardizeOriginalDict) <= 0) {
                throw new Exception(String.format("%s：数据更新失败", ErrorCodeEnum.DATA_IS_NULL));
            }
            handleDictionaryFieldList(standardizeOriginalDict, standardizeOriginalDict.getId(), isAdd);
            insertOrUpdateUserAuthority(standardizeOriginalDict, OperateLogHandleTypeEnum.ALTER);
            return Common.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>更新原始字典表失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    private void handleDictionaryFieldList(StandardizeOriginalDictEntity standardizeOriginalDict, String groupId, boolean isAdd) throws Exception {
        List<StandardizeOriginalDFEntity> dictionaryFieldList = standardizeOriginalDict.getOriginalDictionaryFieldList();
        if (isAdd && (dictionaryFieldList.isEmpty() || dictionaryFieldList == null)) {
            throw new Exception(String.format("%s：数据项的值为空，无法插入", ErrorCodeEnum.CHECK_ERROR));
        }
        if (!isAdd && (!dictionaryFieldList.isEmpty() || dictionaryFieldList != null)) {
            LambdaQueryWrapper<StandardizeOriginalDFEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizeOriginalDFEntity::getGroupId, standardizeOriginalDict.getId());
            standardizeOriginalDFMapper.delete(wrapper);
        }
        int recno = 1;
        for (StandardizeOriginalDFEntity data : dictionaryFieldList) {
            data.setGroupId(groupId);
            data.setId(UUIDUtil.getUUID());
            data.setRecno(recno++);
        }
        int insertCount = standardizeOriginalDFMapper.insertOriginalDictionaryFieldList(dictionaryFieldList);
        log.info(">>>>>>插入的原始字典项数据为:{}条", insertCount);
    }

        private void insertOrUpdateUserAuthority(StandardizeOriginalDictEntity originalDictionaryPojo, OperateLogHandleTypeEnum operationType) throws Exception {
            ObjectManageDTO standardObjectManage = new ObjectManageDTO();
            ObjectEntity objectPojoTable = new ObjectEntity();
            objectPojoTable.setObjectName(StringUtils.isBlank(originalDictionaryPojo.getMemo()) ? "" : originalDictionaryPojo.getMemo());
            standardObjectManage.setObjectPojoTable(objectPojoTable);
            String tableId = originalDictionaryPojo.getFacturer() + "_" + originalDictionaryPojo.getDictionaryName();
            standardObjectManage.setTableId(tableId);
            userAuthorityService.addUserAuthorityData(standardObjectManage);
            operateLogServiceImpl.originalDictSuccessLog(operationType, "原始字典代码集管理", originalDictionaryPojo);
        }

    @Override
    public String deleteOneOriginalDictionary(String id, String dictionaryName) {
        try {
            if (StringUtils.isBlank(id) || StringUtils.isBlank(dictionaryName)) {
                throw new Exception(String.format("%s：参数不能为空", ErrorCodeEnum.DATA_IS_NULL));
            }
            log.info(">>>>>>开始删除原始字典信息");
            LambdaQueryWrapper<StandardizeOriginalDFEntity> wrapperDF = Wrappers.lambdaQuery();
            wrapperDF.eq(StandardizeOriginalDFEntity::getGroupId, id);
            standardizeOriginalDFMapper.delete(wrapperDF);
            LambdaQueryWrapper<StandardizeOriginalDictEntity> wrapperD = Wrappers.lambdaQuery();
            wrapperD.eq(StandardizeOriginalDictEntity::getId, id);
            wrapperD.eq(StandardizeOriginalDictEntity::getDictionaryName, dictionaryName);
            standardizeOriginalDictMapper.delete(wrapperD);
            // 发送操作日志
            StandardizeOriginalDictEntity dictionaryPojo = new StandardizeOriginalDictEntity();
            dictionaryPojo.setFacturer(id);
            dictionaryPojo.setDictionaryName(dictionaryName);
            operateLogServiceImpl.originalDictSuccessLog(OperateLogHandleTypeEnum.DELETE, "原始字典代码集管理", dictionaryPojo);
            return Common.DEL_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>删除原始字典失败：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    public StandardizeOriginalDictEntity searchDictionaryByIdAndName(String id, String dictionaryName) {
        StandardizeOriginalDictEntity originalDict = new StandardizeOriginalDictEntity();
        try {
            log.info(">>>>>>开始查询原始字典信息");
            LambdaQueryWrapper<StandardizeOriginalDictEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizeOriginalDictEntity::getId, id);
            wrapper.eq(StandardizeOriginalDictEntity::getDictionaryName, dictionaryName);
            originalDict = standardizeOriginalDictMapper.selectOne(wrapper);
            if (StringUtils.isBlank(originalDict.getId())) {
                throw new Exception(String.format("%s：原始字典[%s]在数据库中对应的数据不存在", ErrorCodeEnum.CHECK_PARAMETER_ERROR, dictionaryName));
            }
            LambdaQueryWrapper<StandardizeOriginalDFEntity> wrapperDE = Wrappers.lambdaQuery();
            wrapperDE.eq(StandardizeOriginalDFEntity::getGroupId, originalDict.getId());
            List<StandardizeOriginalDFEntity> dictionaryList = standardizeOriginalDFMapper.selectList(wrapperDE);
            if (dictionaryList.isEmpty() || dictionaryList == null) {
                dictionaryList = new ArrayList<>();
            }
            originalDict.setOriginalDictionaryFieldList(dictionaryList);
        } catch (Exception e) {
            log.error(">>>>>>原始字典信息失败：", e);
        }
        return originalDict;
    }

    @Override
    public List<SelectFieldVO> searchStandardDictionaryListInfo(String searchText) {
        try {
            return fieldCodeMapper.getCodeValIdListDao(searchText);
        } catch (Exception e) {
            log.error(">>>>>>查询标准字典下拉框信息出错：", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<KeyValueVO> searchDictionaryValueListByCodeId(String codeId) {
        try {
            return fieldCodeValMapper.queryLabelValueByCodeId(codeId);
        } catch (Exception e) {
            log.error(">>>>>>查询标准字典信息：", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void downloadDictionaryFieldExcel(HttpServletResponse response, StandardDictionaryDTO data, String name, Object object) {
        try {
            log.info("=======开始下载原始字典的相关信息=======");
            List<StandardizeOriginalDFEntity> dictionaryFieldList = new ArrayList<>();
            if (data.getOriginalDictionaryFieldPojoList().isEmpty() || data.getOriginalDictionaryFieldPojoList() == null) {
                LambdaQueryWrapper<StandardizeOriginalDFEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(StandardizeOriginalDFEntity::getGroupId, data.getId());
                dictionaryFieldList = standardizeOriginalDFMapper.selectList(wrapper);
            } else {
                dictionaryFieldList = data.getOriginalDictionaryFieldPojoList();
            }
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("原始字典表管理").doWrite(dictionaryFieldList);
        } catch (Exception e) {
            log.error(">>>>>>下载原始字典表：", e);
        }
    }

    @Override
    public void downloadDictionaryExcelTemplate(HttpServletResponse response, String name) {
        ExcelWriter excelWriter = null;
        try {
            log.info(">>>>>>开始下载原始字典模板文件");
            List<StandardizeOriginalDFEntity> list = new ArrayList<>();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            StandardizeOriginalDFEntity dictionaryTemplate = new StandardizeOriginalDFEntity();
            dictionaryTemplate.setRecno(1);
            dictionaryTemplate.setCodeValText("代码名称1");
            dictionaryTemplate.setCodeValValue("01");
            dictionaryTemplate.setStandardCodeValText("对应标准代码名称");
            dictionaryTemplate.setStandardCodeValValue("01");
            list.add(dictionaryTemplate);
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0, "原始字典模板").head(StandardizeOriginalDFEntity.class)
                    .build();
            excelWriter.write(list, writeSheetTwo);
        } catch (Exception e) {
            log.error(">>>>>>下载原始字典模板报错：", e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public List<StandardizeOriginalDFEntity> importDictionaryFieldExcel(MultipartFile file, String id) {
        log.info(">>>>>>开始将文件[{}]导入到数据库中", file.getName());
        List<StandardizeOriginalDFEntity> importList = new ArrayList<>();
        try {
            ExcelListener<StandardizeOriginalDFEntity> listener = new ExcelListener<>();
            List<StandardizeOriginalDFEntity> list = EasyExcelUtil.readExcelUtil(file, new StandardizeOriginalDFEntity(), listener);
            // 这里插入每一行数据，需要验证elementcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                StandardizeOriginalDFEntity dictionaryFieldPojo = (StandardizeOriginalDFEntity) iterator.next();
                StandardizeOriginalDFEntity addOriginalDictionaryFieldPojo = new StandardizeOriginalDFEntity();
                ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                BeanUtils.copyProperties(addOriginalDictionaryFieldPojo, dictionaryFieldPojo);
                addOriginalDictionaryFieldPojo.setGroupId(id);
                addOriginalDictionaryFieldPojo.setId(UUIDUtil.getUUID());
                //验证格式
                ValidatorUtil.checkObjectValidator(addOriginalDictionaryFieldPojo);
                importList.add(addOriginalDictionaryFieldPojo);
                iterator.remove();
            }
        } catch (Exception e) {
            log.error(">>>>>>导入原始字典项报错：", e);
            throw new NullPointerException("导入文件报错" + e.getMessage());
        }
        return importList;
    }

    @Override
    public List<KeyValueVO> getOriginalDictionaryNameList() {
        List<KeyValueVO> originalDictionaryNameList = new ArrayList<>();
        try {
            log.info(">>>>>>开始获取原始字典的名称下拉信息");
            List<StandardizeOriginalDictEntity> originalDFEntities = standardizeOriginalDictMapper.selectList(Wrappers.lambdaQuery());
            for (StandardizeOriginalDictEntity entity : originalDFEntities) {
                KeyValueVO keyValueVO = new KeyValueVO(entity.getId(), entity.getDictionaryName(), entity.getFacturer());
                originalDictionaryNameList.add(keyValueVO);
            }
            //根据中文名称排序
            originalDictionaryNameList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                    .compare(s2.getLabel(), s1.getLabel())).limit(100).collect(Collectors.toList());
            return originalDictionaryNameList;
        } catch (Exception e) {
            log.error(">>>>>>获取原始字典的名称下拉信息报错：", e);
        }
        return originalDictionaryNameList;
    }

}
