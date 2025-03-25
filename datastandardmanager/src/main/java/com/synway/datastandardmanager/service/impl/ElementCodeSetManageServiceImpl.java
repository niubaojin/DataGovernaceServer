package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.dao.master.DAOHelper;
import com.synway.datastandardmanager.dao.master.ElementCodeSetManageDao;
import com.synway.datastandardmanager.enums.OperateLogFailReasonEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.datastandDictionary.DataStandardDictionaryParameter;
import com.synway.datastandardmanager.service.ElementCodeSetManageService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.StringUtilsMatcher;

import com.synway.datastandardmanager.util.ValidatorUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ElementCodeSetManageServiceImpl implements ElementCodeSetManageService {
    private Logger logger = LoggerFactory.getLogger(ElementCodeSetManageServiceImpl.class);
    @Autowired
    private ElementCodeSetManageDao elementCodeSetManageDao;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Override
    public PageInfo<Fieldcode> selectCodeTable(int pageIndex, int pageSize, String codeName, String codeText, String codeId) {
        // TODO Auto-generated method stub
        if ((pageIndex != 0) && (pageSize != 0)) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Fieldcode> fieldcodeList;
        codeName = codeName == null ? "" : codeName;
        codeText = codeText == null ? "" : codeText;
        if (StringUtils.isEmpty(codeId)) {
            fieldcodeList = elementCodeSetManageDao.selectCodeTable(codeName, codeText);
        } else {
            fieldcodeList = elementCodeSetManageDao.selectCodeTableByCodeIdInVal(codeId, codeName, codeText);
        }
        // 序号
        int pageNum = (pageIndex - 1) * pageSize;
        for (Fieldcode fieldcode : fieldcodeList) {
            pageNum = pageNum + 1;
            fieldcode.setSerialNum(pageNum);
        }
        return new PageInfo<Fieldcode>(fieldcodeList);
    }

    @Override
    public PageInfo<FieldCodeVal> selectCodeValTable(int pageIndex, int pagesize, String valValue, String valText, String codeId) {
        // TODO Auto-generated method stub
        if ((pageIndex != 0) && (pagesize != 0)) {
            PageHelper.startPage(pageIndex, pagesize);
        }
        List<FieldCodeVal> fieldCodeValList = new ArrayList<FieldCodeVal>();
        if (codeId == null || codeId == "") {
            fieldCodeValList = elementCodeSetManageDao.selectCodeValTable(valValue, valText);
        } else {
            fieldCodeValList = elementCodeSetManageDao.selectCodeValTableByCodeId(codeId, valValue, valText);
        }
        return new PageInfo<FieldCodeVal>(fieldCodeValList);
    }

    /**
     * @param codeId
     * @return
     */
    @Override
    public List<FieldCodeVal> selectCodeValTableNew(String codeId) {
        List<FieldCodeVal> fieldCodeValList = new ArrayList<>();
        if (StringUtils.isEmpty(codeId)) {
            return fieldCodeValList;
        } else {
            fieldCodeValList = elementCodeSetManageDao.selectCodeValTableByCodeId(codeId, null, null);
        }
        return fieldCodeValList;
    }

    /**
     * 删除选择的指定的代码集基本信息
     *
     * @param deleteFieldCodeVal
     * @return
     */
    @Override
    public String delCodeValTableServiceImpl(FieldCodeVal deleteFieldCodeVal) throws Exception {
        String message = "";
        if (StringUtils.isEmpty(deleteFieldCodeVal.getCodeValId())) {
            logger.error("删除指定的数据中codeValId为空");
            throw new Exception("删除指定的数据中【codeValId】为空");
        }
        if (StringUtils.isEmpty(deleteFieldCodeVal.getValValue())) {
            logger.error("删除指定的数据中【代码集值】为空");
            throw new Exception("删除指定的数据中【代码集值】为空");
        }
        int delStatus = elementCodeSetManageDao.delCodeValTableDao(deleteFieldCodeVal);
        if (delStatus >= 1) {
            message = "删除" + deleteFieldCodeVal.getCodeValId() + "代码集基本信息成功";
        } else {
            message = "删除" + deleteFieldCodeVal.getCodeValId() + "代码集基本信息失败";
        }
        return message;
    }

    /**
     * 新增/修改 指定的代码集值信息
     *
     * @param addFieldCodeVal
     * @return
     * @throws Exception
     */
    @Override
    public String addCodeValTableService(FieldCodeVal addFieldCodeVal) throws Exception {
        if (StringUtils.isEmpty(addFieldCodeVal.getValText()) ||
                StringUtils.isEmpty(addFieldCodeVal.getValValue())) {
            logger.error("代码集值/代码集名不能为空");
            throw new Exception("代码集值/代码集名不能为空");
        }
        if (StringUtils.isEmpty(addFieldCodeVal.getCodeId())) {
            logger.error("代码值的主键ID不能为空");
            throw new Exception("代码值的主键ID不能为空");
        }
        //  判断codeid在表 fieldcode 中是否存在，不存在表示没有保存
        int codeIdCount = elementCodeSetManageDao.getCodeIdCount(addFieldCodeVal.getCodeId());

        if (codeIdCount == 0) {
            logger.error(addFieldCodeVal.getCodeId() + "对应的数据没有保存");
            throw new Exception(addFieldCodeVal.getCodeId() + "对应的数据没有保存");
        }
        if (StringUtils.isEmpty(addFieldCodeVal.getMemo())) {
            addFieldCodeVal.setMemo("");
        }
        if (StringUtils.isEmpty(addFieldCodeVal.getValTextTitle())) {
            addFieldCodeVal.setValTextTitle("");
        }
        if (StringUtils.isEmpty(addFieldCodeVal.getSortIndex())) {
            addFieldCodeVal.setSortIndex("");
        }
        // 判断唯一性的数据
        String message = "";
        int addUpdateCount = 0;
        // 校验对象的长度等内容是否合规
        ValidatorUtil.checkObjectValidator(addFieldCodeVal);
        // 如果为空 表示是新增的代码集信息，如果存在，表示为修改原有数据
        if (StringUtils.isEmpty(addFieldCodeVal.getCodeValId())) {
            addFieldCodeVal.setCodeValId(addFieldCodeVal.getCodeId() + addFieldCodeVal.getValValue());
            addUpdateCount = elementCodeSetManageDao.addCodeValTable(addFieldCodeVal);
        } else {
            addFieldCodeVal.setOldCodeValId(addFieldCodeVal.getCodeId() + addFieldCodeVal.getValValue());
            addUpdateCount = elementCodeSetManageDao.updateCodeValTable(addFieldCodeVal);
        }

        if (addUpdateCount > 0) {
            message = addFieldCodeVal.getValText() + "新增/编辑成功";
        } else {
            message = addFieldCodeVal.getValText() + "新增/编辑失败";
        }
        return message;
    }

    /**
     * suggest提示框
     *
     * @param condition
     * @return
     */
    @Override
    public List<OneSuggestValue> getCodeValIdListService(String condition) throws Exception {
        List<OneSuggestValue> resultList = elementCodeSetManageDao.getCodeValIdListDao(condition);
        return resultList;
    }

    @Override
    public String addOneCodeMessageService(Fieldcode addFieldcode) throws Exception {
        // 先进行逻辑判断 codeId 是否为非中文
        if (StringUtilsMatcher.isContainsPattern(addFieldcode.getCodeId(), "[\u4E00-\u9FA5]")) {
            logger.error("主键ID中存在中文，不能用中文");
            throw new Exception("主键ID中存在中文，不能用中文");
        }
        if (StringUtilsMatcher.isContainsPattern(addFieldcode.getCodeName(), "[\u4E00-\u9FA5]")) {
            logger.error("代码英文名称存在中文，不能用中文");
            throw new Exception("代码英文名称存在中文，不能用中文");
        }
        if (StringUtils.isEmpty(addFieldcode.getCodeId())) {
            logger.error("主键ID不能为空");
            throw new Exception("主键ID不能为空");
        }
        if (StringUtils.isEmpty(addFieldcode.getCodeName())) {
            logger.error("代码英文名称不能为空");
            throw new Exception("代码英文名称不能为空");
        }
        if (StringUtils.isEmpty(addFieldcode.getCodeText())) {
            logger.error("代码中文名称不能为空");
            throw new Exception("代码中文名称不能为空");
        }
        String message = "";
        // 校验对象的长度等内容是否合规
        ValidatorUtil.checkObjectValidator(addFieldcode);
        // 判断codeId是否已经存在
        int countCodeId = elementCodeSetManageDao.getCodeIdCount(addFieldcode.getCodeId());
        // 不存在表示是插入新的数据，存在表示是更新数据
        if (countCodeId == 0) {
            int count = elementCodeSetManageDao.insertCodeSetManage(addFieldcode);
            if (count > 0) {
                message = "数据插入成功";
                operateLogServiceImpl.elementCodeSuccessLog(OperateLogHandleTypeEnum.ADD, "标准字典代码集管理", addFieldcode);
            }
        } else {
            int count = elementCodeSetManageDao.updateCodeSetManage(addFieldcode);
            if (count > 0) {
                operateLogServiceImpl.elementCodeSuccessLog(OperateLogHandleTypeEnum.ALTER, "标准字典代码集管理", addFieldcode);
                message = "数据更新成功";
            }
        }
        return message;
    }

    @Override
    public String delCodeTableServiceImpl(Fieldcode deleteFieldCode) throws Exception {
        String message = "";
        if (StringUtils.isEmpty(deleteFieldCode.getCodeId())) {
            logger.error("元素代码信息的主键ID为空，不能被删除");
            throw new Exception("元素代码信息的主键ID为空，不能被删除");
        }
        int deleteCount = elementCodeSetManageDao.delCodeTableByIdDao(deleteFieldCode.getCodeId());
        if (deleteCount > 0) {
            operateLogServiceImpl.elementCodeSuccessLog(OperateLogHandleTypeEnum.DELETE, "标准字典代码集管理", deleteFieldCode);
            message = "代码" + deleteFieldCode.getCodeText() + "删除成功";
        } else {
            operateLogServiceImpl.elementCodeFailLog(OperateLogHandleTypeEnum.DELETE, OperateLogFailReasonEnum.YYXTFM, "标准字典代码集管理", deleteFieldCode);
            message = "代码" + deleteFieldCode.getCodeText() + "删除失败";
        }
        return message;
    }

    /**
     * 导入excel文件
     *
     * @param addList
     * @return
     * @throws Exception
     */
    @Override
    public List<FieldCodeVal> uploadCodeValXlsFile(List<Map> addList) throws Exception {
        List<FieldCodeVal> allFieldCodeValList = new ArrayList<>();
        for (Map element : addList) {
            FieldCodeVal oneFieldCodeVal = new FieldCodeVal();
//            String codevalId = String.valueOf(element.getOrDefault("主键ID",""));
            String codeId = String.valueOf(element.getOrDefault("主键ID*", ""));
            String valText = String.valueOf(element.getOrDefault("代码集名*", ""));
            String memo = String.valueOf(element.getOrDefault("备注", ""));
            String valValue = String.valueOf(element.getOrDefault("代码集值*", ""));
            String sortIndex = String.valueOf(element.getOrDefault("代码值顺序", ""));
            String valTextTitle = String.valueOf(element.getOrDefault("英文缩写", ""));
            if (!StringUtils.isNumeric(sortIndex)) {
                throw new Exception("代码值顺序存在非数字的值，导入数据失败");
            }
//            String id = String.valueOf(element.getOrDefault("ID",""));
            // 判断 该代码值是否已经存在，如果存在，不需要重新插入数据
            if (StringUtils.isNotEmpty(codeId.trim()) && StringUtils.isNotEmpty(valValue)) {
                int numCount = elementCodeSetManageDao.getCountCodeVal(codeId.trim(), valValue);
                if (numCount > 0) {
                    continue;
                }
                oneFieldCodeVal.setCodeValId(codeId + valValue);
                oneFieldCodeVal.setCodeId(codeId);
                oneFieldCodeVal.setValText(valText);
                oneFieldCodeVal.setMemo(memo);
                oneFieldCodeVal.setValValue(valValue);
                oneFieldCodeVal.setSortIndex(sortIndex);
                oneFieldCodeVal.setValTextTitle(valTextTitle);
                allFieldCodeValList.add(oneFieldCodeVal);
            }
        }
        logger.info("导入的数据为：" + JSONObject.toJSONString(allFieldCodeValList));
        return allFieldCodeValList;
//        Boolean insertFlag = DAOHelper.insertList(allFieldCodeValList, elementCodeSetManageDao, "uploadCodeValXlsFileDao");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOneFullCodeService(FieldFullcode addFieldFullcode) throws Exception {
        // 对于Code 有现成的代码
        String message = addOneCodeMessageService(addFieldFullcode.getOneFieldcode());
        List<FieldCodeVal> newFieldCodeValList = addFieldFullcode.getOneFieldCodeVal();
        // 分成几个列表 区分成需要删除的 需要新增 需要更新的
        String codeId = addFieldFullcode.getOneFieldcode().getCodeId();
        List<FieldCodeVal> oldFieldCodeValList = elementCodeSetManageDao.selectCodeValTableByCodeId(codeId, "", "");
        List<String> oldCodeValIdList = oldFieldCodeValList.stream().filter(e -> StringUtils.isNotEmpty(e.getCodeValId())).
                map(e -> e.getCodeValId().toLowerCase()).distinct().collect(Collectors.toList());
        List<String> newCodeValIdList = newFieldCodeValList.stream().filter(e -> StringUtils.isNotEmpty(e.getCodeValId())).
                map(e -> e.getCodeValId().toLowerCase()).distinct().collect(Collectors.toList());
        List<FieldCodeVal> deleteFieldCodeValList = new ArrayList<>();
        List<FieldCodeVal> insertFieldCodeValList = new ArrayList<>();
        List<FieldCodeVal> updateFieldCodeValList = new ArrayList<>();
        for (FieldCodeVal fieldCodeVal : newFieldCodeValList) {
            if (!oldCodeValIdList.contains(fieldCodeVal.getCodeValId().toLowerCase())) {
                //  需要新增的
                // 校验对象的长度等内容是否合规
                ValidatorUtil.checkObjectValidator(fieldCodeVal);
                insertFieldCodeValList.add(fieldCodeVal);
            } else {
                //  需要更新的
                updateFieldCodeValList.add(fieldCodeVal);
            }
        }

        for (FieldCodeVal oldCodeVal : oldFieldCodeValList) {
            if (!newCodeValIdList.contains(oldCodeVal.getCodeValId().toLowerCase())) {
                deleteFieldCodeValList.add(oldCodeVal);
            }
        }
        // 新增新添加的数据
        DAOHelper.insertList(insertFieldCodeValList, elementCodeSetManageDao, "uploadCodeValXlsFileDao");
        // 删除需要删除的数据
        for (FieldCodeVal deleteFieldCode : deleteFieldCodeValList) {
            delCodeValTableServiceImpl(deleteFieldCode);
        }
        //编辑需要编辑的数据
        for (FieldCodeVal fieldCodeVal : updateFieldCodeValList) {
            addCodeValTableService(fieldCodeVal);
        }
        message = message + "   元素代码集值信息保存成功";
        return message;
    }

    @Override
    public List<PageSelectOneValue> searchDictionaryList(String searchText) {
        if (StringUtils.isBlank(searchText)) {
            searchText = null;
        }
        logger.info("开始查询外部字典的信息");
        List<PageSelectOneValue> resultList = elementCodeSetManageDao.searchDictionaryList(searchText);
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }

        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                .compare(s2.getLabel(), s1.getLabel())).collect(Collectors.toList());
        logger.info("======查询外部字典信息结束======");
        return resultList;
    }

    @Override
    public void downloadStandardDictionaryFieldExcel(HttpServletResponse response, DataStandardDictionaryParameter data, String name, Object object) {
        List<FieldCodeVal> dictionaryFieldList = new ArrayList<>();
        if (data.getStandardDictionaryFieldPojoList().isEmpty() || data.getStandardDictionaryFieldPojoList() == null) {
            dictionaryFieldList = elementCodeSetManageDao.selectCodeValTableByCodeId(data.getId(), null, null);
        } else {
            dictionaryFieldList = data.getStandardDictionaryFieldPojoList();
        }
        try {
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE).sheet("标准字典表管理").doWrite(dictionaryFieldList);
        } catch (Exception e) {
            logger.error("下载标准字典表出错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
