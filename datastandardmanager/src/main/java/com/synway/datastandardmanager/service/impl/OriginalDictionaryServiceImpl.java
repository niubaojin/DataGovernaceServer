package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.dao.master.ElementCodeSetManageDao;
import com.synway.datastandardmanager.dao.master.OriginalDictionaryDao;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryFieldPojo;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryParameter;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryPojo;
import com.synway.datastandardmanager.service.OriginalDictionaryService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author obito
 * @version 1.0
 */
@Service
@Slf4j
public class OriginalDictionaryServiceImpl implements OriginalDictionaryService {

    @Autowired
    private OriginalDictionaryDao originalDictionaryDao;

    @Autowired
    private ElementCodeSetManageDao elementCodeSetManageDao;

    @Autowired
    private ResourceManageAddDao resourceManageAddDao;

    @Autowired
    private ResourceManageAddService resourceManageAddServiceImpl;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static HashLock<String> HASH_LOCK = new HashLock<>();

    @Override
    public List<LabelTreeNodeVue> getLeftTreeInfo() {
        log.info("=====开始获取原始字典管理的左侧树信息=====");
        List<OriginalDictionaryPojo> dictionaryList = originalDictionaryDao.searchLeftTreeInfo();
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
    private LabelTreeNodeVue createLabelTreeNodeVue(String facturer, List<OriginalDictionaryPojo> dictionaryList) {
        LabelTreeNodeVue rootNode = new LabelTreeNodeVue();
        rootNode.setId(facturer);
        rootNode.setLevel(1);
        rootNode.setSortLevel(1);
        rootNode.setLabel(facturer + "(" + dictionaryList.size() + ")");
        rootNode.setChildren(dictionaryList.stream()
                .map(data -> {
                    LabelTreeNodeVue childrenNode = new LabelTreeNodeVue();
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
    public String addOrUpdateOneOriginalDictionary(OriginalDictionaryPojo originalDictionaryPojo) throws Exception {
        // 是否新增
        boolean isAdd = StringUtils.isBlank(originalDictionaryPojo.getId());
        if (isAdd) {
            return addNewOriginalDictionary(originalDictionaryPojo, isAdd);
        } else {
            return updateOriginalDictionary(originalDictionaryPojo, isAdd);
        }
    }
    private String addNewOriginalDictionary(OriginalDictionaryPojo originalDictionaryPojo, boolean isAdd) throws Exception {
        log.info("开始新增原始字典表");
        int count = originalDictionaryDao.searchDictionaryByFacturer(originalDictionaryPojo.getDictionaryName(), originalDictionaryPojo.getFacturer());
        if (count != 0) {
            throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR, "该厂商下已存在相同的字典名称");
        }
        String uuid = UUIDUtil.getUUID();
        originalDictionaryPojo.setId(uuid);
        originalDictionaryPojo.setFacturerId(originalDictionaryPojo.getFacturer());

        if (!insertDictionary(originalDictionaryPojo)) {
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "数据新增失败");
        }
        handleDictionaryFieldList(originalDictionaryPojo, uuid, isAdd);
        insertOrUpdateUserAuthority(originalDictionaryPojo, OperateLogHandleTypeEnum.ADD);
        return "数据添加成功";
    }
    private String updateOriginalDictionary(OriginalDictionaryPojo originalDictionaryPojo, boolean isAdd) throws Exception {
        log.info("开始更新字典表");
        HASH_LOCK.lock(originalDictionaryPojo.getId());
        try {
            if (!updateDictionary(originalDictionaryPojo)) {
                throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "数据更新失败");
            }
            handleDictionaryFieldList(originalDictionaryPojo, originalDictionaryPojo.getId(), isAdd);
            insertOrUpdateUserAuthority(originalDictionaryPojo, OperateLogHandleTypeEnum.ALTER);
            return "数据更新成功";
        } finally {
            HASH_LOCK.unlock(originalDictionaryPojo.getId());
        }
    }
    private boolean insertDictionary(OriginalDictionaryPojo originalDictionaryPojo) {
        int insertDictionaryCount = originalDictionaryDao.addOneOriginalDictionary(originalDictionaryPojo);
        return insertDictionaryCount == 1;
    }
    private boolean updateDictionary(OriginalDictionaryPojo originalDictionaryPojo) {
        int updateDictionaryCount = originalDictionaryDao.updateOneOriginalDictionary(originalDictionaryPojo);
        log.info("更新的原始字典项为{}条", updateDictionaryCount);
        return updateDictionaryCount > 0;
    }
    private void handleDictionaryFieldList(OriginalDictionaryPojo originalDictionaryPojo, String groupId, boolean isAdd){
        List<OriginalDictionaryFieldPojo> dictionaryFieldList = originalDictionaryPojo.getOriginalDictionaryFieldList();
        if (isAdd && (dictionaryFieldList.isEmpty() || dictionaryFieldList == null)) {
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "数据项的值为空，无法插入");
        }
        if (!isAdd && (!dictionaryFieldList.isEmpty() || dictionaryFieldList != null)) {
            int deleteCount = originalDictionaryDao.deleteAllDictionaryFieldByGroupId(originalDictionaryPojo.getId());
            log.info("删除的原始字典项为{}条", deleteCount);
        }

        int recno = 1;
        for (OriginalDictionaryFieldPojo data : dictionaryFieldList) {
            data.setGroupId(groupId);
            data.setId(UUIDUtil.getUUID());
            data.setRecno(recno++);
        }
        int insertCount = originalDictionaryDao.insertOriginalDictionaryFieldList(dictionaryFieldList);
        log.info("插入的原始字典项数据为:{}条", insertCount);
    }
    private void insertOrUpdateUserAuthority(OriginalDictionaryPojo originalDictionaryPojo, OperateLogHandleTypeEnum operationType) throws Exception {
        StandardObjectManage standardObjectManage = new StandardObjectManage();
        ObjectPojoTable objectPojoTable = new ObjectPojoTable();
        objectPojoTable.setDataSourceName(StringUtils.isBlank(originalDictionaryPojo.getMemo()) ? "" : originalDictionaryPojo.getMemo());
        standardObjectManage.setObjectPojoTable(objectPojoTable);
        String tableId = originalDictionaryPojo.getFacturer() + "_" + originalDictionaryPojo.getDictionaryName();
        standardObjectManage.setTableId(tableId);
        resourceManageAddServiceImpl.addUserAuthorityData(standardObjectManage);
        operateLogServiceImpl.originalDictSuccessLog(operationType, "原始字典代码集管理", originalDictionaryPojo);
    }

    @Override
    public String deleteOneOriginalDictionary(String id, String dictionaryName) {
        log.info("开始删除原始字典信息");
        int deleteFieldCount = originalDictionaryDao.deleteAllDictionaryFieldByGroupId(id);
        log.info("删除的原始字典项为{}条", deleteFieldCount);
        int deleteDictionaryCount = originalDictionaryDao.deleteOneOriginalDictionary(id, dictionaryName);
        if (deleteDictionaryCount != 1) {
            throw SystemException.asSystemException(ErrorCode.DELETE_ERROR, "数据项分组名称为:{" + dictionaryName + "}的数据项删除失败");
        }
        log.info("删除原始字典结束");
        // 发送操作日志
        OriginalDictionaryPojo dictionaryPojo = new OriginalDictionaryPojo();
        dictionaryPojo.setFacturer(id);
        dictionaryPojo.setDictionaryName(dictionaryName);
        operateLogServiceImpl.originalDictSuccessLog(OperateLogHandleTypeEnum.DELETE, "原始字典代码集管理", dictionaryPojo);

        return "删除数据成功";
    }

    @Override
    public OriginalDictionaryPojo searchDictionaryByIdAndName(String id, String dictionaryName) {
        log.info("开始查询原始字典信息");
        OriginalDictionaryPojo originalDictionaryPojo = originalDictionaryDao.searchOneData(id, dictionaryName);
        if (StringUtils.isBlank(originalDictionaryPojo.getId())) {
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR, "原始字典[" + dictionaryName
                    + "]在数据库中对应的数据不存在，查询失败");
        }
        List<OriginalDictionaryFieldPojo> dictionaryList = originalDictionaryDao.searchDictionaryFieldByGroupId(originalDictionaryPojo.getId());
        if (dictionaryList.isEmpty() || dictionaryList == null) {
            dictionaryList = new ArrayList<>();
            originalDictionaryPojo.setOriginalDictionaryFieldList(dictionaryList);
        } else {
            originalDictionaryPojo.setOriginalDictionaryFieldList(dictionaryList);
        }
        log.info("查询原始字典信息结束");
        return originalDictionaryPojo;
    }

    @Override
    public List<OneSuggestValue> searchStandardDictionaryListInfo(String searchText) {
        log.info("开始查询标准字典下拉框信息");
        List<OneSuggestValue> dictionaryList = elementCodeSetManageDao.getCodeValIdListDao(searchText);
        if (dictionaryList.isEmpty() || dictionaryList == null) {
            dictionaryList = new ArrayList<>();
        }
        log.info("查询标准字典下拉框信息结束");
        return dictionaryList;
    }

    @Override
    public List<PageSelectOneValue> searchDictionaryValueListByCodeId(String codeId) {
        log.info("=====开始查询标准字典码表===");
        List<PageSelectOneValue> dictionaryValueList = resourceManageAddDao.searchFieldCodeValByCodeId(codeId);
        if (dictionaryValueList.isEmpty() || dictionaryValueList == null) {
            dictionaryValueList = new ArrayList<>();
        }
        log.info("=====查询标准字典信息结束=====");
        return dictionaryValueList;
    }

    @Override
    public void downloadDictionaryFieldExcel(HttpServletResponse response, OriginalDictionaryParameter data,
                                             String name, Object object) {
        List<OriginalDictionaryFieldPojo> dictionaryFieldList = new ArrayList<>();
        if (data.getOriginalDictionaryFieldPojoList().isEmpty() || data.getOriginalDictionaryFieldPojoList() == null) {
            dictionaryFieldList = originalDictionaryDao.searchDictionaryFieldByGroupId(data.getId());
        } else {
            dictionaryFieldList = data.getOriginalDictionaryFieldPojoList();
        }
        try {

            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("原始字典表管理").doWrite(dictionaryFieldList);

        } catch (Exception e) {
            log.error("下载原始字典表" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<OriginalDictionaryFieldPojo> importDictionaryFieldExcel(MultipartFile file, String id) {
        log.info("=============开始将文件{}导入到数据库中======", file.getName());
        ExcelListener<OriginalDictionaryFieldPojo> listener = new ExcelListener<>();
        List<OriginalDictionaryFieldPojo> list = new ArrayList<>();
        List<OriginalDictionaryFieldPojo> importList = new ArrayList<>();
        try {
            list = EasyExcelUtil.readExcelUtil(file, new OriginalDictionaryFieldPojo(), listener);
            // 这里插入每一行数据，需要验证elementcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                OriginalDictionaryFieldPojo dictionaryFieldPojo = (OriginalDictionaryFieldPojo) iterator.next();
                try {
                    OriginalDictionaryFieldPojo addOriginalDictionaryFieldPojo = new OriginalDictionaryFieldPojo();

                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(addOriginalDictionaryFieldPojo, dictionaryFieldPojo);
                    addOriginalDictionaryFieldPojo.setGroupId(id);
                    addOriginalDictionaryFieldPojo.setId(UUIDUtil.getUUID());
                    //验证格式
                    ValidatorUtil.checkObjectValidator(addOriginalDictionaryFieldPojo);
                    importList.add(addOriginalDictionaryFieldPojo);

//                    int addFlag = originalDictionaryDao.insertOneDictionaryField(addOriginalDictionaryFieldPojo);
                    iterator.remove();
//                    if (addFlag == 1) {
//                    } else {
//                        log.info("原始字典项" + addOriginalDictionaryFieldPojo.getCodeValText() + "插入失败");
//                    }
                } catch (Exception e) {
                    log.error("原始字典" + dictionaryFieldPojo.getCodeValText() + "插入失败");
                }
            }
        } catch (Exception e) {
            log.error("导入文件报错" + ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException("导入文件报错" + e.getMessage());
        }
        return importList;
    }

    @Override
    public void downloadDictionaryExcelTemplate(HttpServletResponse response, List<OriginalDictionaryFieldPojo> list, String name, OriginalDictionaryFieldPojo originalDictionaryFieldPojo) {
        ExcelWriter excelWriter = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" + fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            OriginalDictionaryFieldPojo dictionaryTemplate = new OriginalDictionaryFieldPojo();
            dictionaryTemplate.setRecno(1);
            dictionaryTemplate.setCodeValText("代码名称1");
            dictionaryTemplate.setCodeValValue("01");
            dictionaryTemplate.setStandardCodeValText("对应标准代码名称");
            dictionaryTemplate.setStandardCodeValValue("01");
            list.add(dictionaryTemplate);
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0, "原始字典模板").head(OriginalDictionaryFieldPojo.class)
                    .build();
            excelWriter.write(list, writeSheetTwo);
        } catch (Exception e) {
            log.error("下载原始字典模板报错" + ExceptionUtil.getExceptionTrace(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public List<PageSelectOneValue> getOriginalDictionaryNameList() {
        List<PageSelectOneValue> originalDictionaryNameList = originalDictionaryDao.getOriginalDictionaryNameList();
        //根据中文名称排序
        originalDictionaryNameList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                .compare(s2.getLabel(), s1.getLabel())).limit(100).collect(Collectors.toList());
        return originalDictionaryNameList;
    }

}
