package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.dao.master.SynlteElementDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.OneSuggestValue;
import com.synway.datastandardmanager.pojo.OperatorLog;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.labelmanage.TemplateCodeValue;
import com.synway.datastandardmanager.pojo.synlteelement.*;
import com.synway.datastandardmanager.service.SynlteElementService;
import com.synway.datastandardmanager.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.Collator;
import java.util.*;


/**
 * @author obito
 * @version 1.0
 * @date 2021-08-06
 */
@Service
@Slf4j
public class SynlteElementServiceImpl implements SynlteElementService {

    @Autowired
    private SynlteElementDao synlteElementDao;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static HashLock<String> HASH_LOCK = new HashLock<>();
    @Override
    public List<SynlteElementVO> searchAllElement(SynlteElementParameter parameter) {
        // 搜索内容如果存在空值，将其变为 null值
        if(StringUtils.isBlank(parameter.getSearchText())){
            parameter.setSearchText(null);
        }
        if (parameter.getIsElementList() == null ||parameter.getIsElementList().isEmpty()){
            parameter.setIsElementList(null);
        }
        if(parameter.getElementObject() == null || parameter.getElementObject().isEmpty()){
            parameter.setElementObject(null);
        }
        if(parameter.getSort() == null || parameter.getSort().isEmpty()){
            parameter.setSort("modDate");
        }
        if("isElementType".equals(parameter.getSort())){
            parameter.setSort("iselement");
        }
        if(parameter.getSort() == null || parameter.getSort().isEmpty()){
            parameter.setSort("modDate");
        }
        List<SynlteElementVO> elementList = synlteElementDao.searchElementTable(parameter);
        if(elementList != null && !elementList.isEmpty()){
            int num = 1;
            for(SynlteElementVO data:elementList){
                data.setNum(num++);
                data.setElementObjectType(SynlteElementCode.getValueById("3_"+data.getElementObject()));
                data.setIsElementType(SynlteElementCode.getValueById("2_"+data.getIsElement()));
            }
        }else{
            elementList = new ArrayList<>();
        }
        return elementList;
    }


    @Override
    public String addOneData(SynlteElement data) {
        //检查数据是否正确
        int count = synlteElementDao.getCountById(data.getElementCode());
        if(count > 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR,"要素标识符["+data.getElementCode()+"]重复");
        }
        log.info("插入到数据要素的数据为{}", JSONObject.toJSONString(data));
        data.setSameId(synlteElementDao.setSameId(data.getDataElementId()));
        synlteElementDao.addOneElement(data);
        // 发送操作日志
        operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.ADD, "数据要素管理", data.getElementChname());
        return "添加数据要素成功";
    }


    @Override
    public String upOneData(SynlteElementVO data) {
        //检查数据是否正确
        checkUpdateData(data.getElementCode(),data.getAuthor());
        //判断数据要素是否更新，未做任何改动，直接返回，不更新数据库
        SynlteElement oldSynlteElement = synlteElementDao.searchOneDataByCode(data.getElementCode());
        if(oldSynlteElement == null){
            return "数据要素不存在，更新失败";
        }
        oldSynlteElement.setElementObjectType(SynlteElementCode.getValueById("3_"+oldSynlteElement.getElementObject()));
        if(oldSynlteElement.equals(data)){
            return "数据要素没改变，更新失败";
        }
        HASH_LOCK.lock(data.getElementCode());
        try {
            // 相关事件进行修改
            int num = 0;
            num = synlteElementDao.updateElement(data);
            log.info("更新synlte.element表中数据量为{}",num);
            if(num == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "更新失败，请刷新后重新更改数据");
            }
            // 发送操作日志
            operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.ALTER, "数据要素管理", data.getElementChname());
            return "更新成功";
        }finally {
            HASH_LOCK.unlock(data.getElementCode());
        }
    }

    @Override
    public List<LabelTreeNodeVue> searchElementObject() {
        log.info("--------------开始查询数据要素的汇总树信息--------------------");
        List<OneSuggestValue> searchList = synlteElementDao.selectElementObject();
        List<LabelTreeNodeVue> elementTreeNodeList = new ArrayList<>();
        LabelTreeNodeVue rootNode = new LabelTreeNodeVue();
        rootNode.setId("");
        rootNode.setLevel(1);
        rootNode.setSortLevel(1);
        if(searchList == null || searchList.isEmpty()){
            rootNode.setLabel("主体(0)");
        }else{
            int count = searchList.stream().mapToInt(d -> Integer.parseInt(d.getValue())).sum();
            rootNode.setLabel("主体(" + count + ")");
            List<LabelTreeNodeVue> childrenTree = new ArrayList<>();
            for(OneSuggestValue data : searchList){
                LabelTreeNodeVue treeNode = new LabelTreeNodeVue();
                treeNode.setId(data.getId());
                treeNode.setLevel(2);
                treeNode.setParent("");
                treeNode.setLabel(data.getId() + "(" + data.getValue() + ")");
                childrenTree.add(treeNode);
            }
            rootNode.setChildren(childrenTree);
        }
        elementTreeNodeList.add(rootNode);
        log.info("--------------查询数据要素的汇总树信息结束--------------------");
        return elementTreeNodeList;
    }

    @Override
    public String deleteElementByCode(String elementCode) {
        SynlteElement synlteElement = synlteElementDao.searchOneDataByCode(elementCode);
        log.info("开始删除表synlte.element中elementCode为{}的数据",elementCode);
        int deleteNum = synlteElementDao.deleteCountById(elementCode);
        log.info("删除的数据量为{}",deleteNum);
        operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.DELETE, "数据要素管理", synlteElement.getElementChname());
        return deleteNum < 1 ? "数据删除失败" : "数据删除成功";
    }

    /**
     * 新增时第一行下拉框的数据元信息
     */
    @Override
    public List<SelectField> searchSynlteField(String searchName) {
        if(StringUtils.isBlank(searchName)){
            searchName = "";
        }
        List<SelectField> fieldList = synlteElementDao.searchField(searchName);
        if(fieldList == null || fieldList.isEmpty()){
            return new ArrayList<>();
        }
        List<SelectField> newList = new ArrayList<>();
        try{
            //过滤前100条信息
            String finalSearchName = searchName;
            fieldList.stream().filter(d -> StringUtils.isBlank(finalSearchName)
                    || StringUtils.containsIgnoreCase(d.getValue(), finalSearchName))
                    .sorted(Comparator.comparing(SelectField::getValue)).limit(100).forEach(d ->{
                SelectField field = new SelectField(d.getId(),d.getValue()+"("+d.getId()+")",d.getSameId(),"");
                newList.add(field);
            });
        }catch (ArrayIndexOutOfBoundsException e){
            log.info("数组下标越界:{}"+e);
        }
        log.info("查询到的数据量为{}",newList.size());
        newList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getValue(),s2.getValue()));
        return newList;
    }

    @Override
    public List<SelectField> searchSecondField(String searchName) {
        if(StringUtils.isBlank(searchName)){
            searchName = "";
        }
        List<SelectField> selectSecondFields = synlteElementDao.searchSecondField(searchName);
        if(selectSecondFields.isEmpty()){
            return new ArrayList<>();
        }
        List<SelectField> responseList = new ArrayList<>();
        //过滤前100条信息
        String finalSearchName = searchName;
        selectSecondFields.stream().filter(d -> StringUtils.isBlank(finalSearchName)
                || StringUtils.containsIgnoreCase(d.getValue(), finalSearchName))
                .sorted(Comparator.comparing(SelectField::getValue)).limit(100).forEach(d ->{
            SelectField field = new SelectField(d.getId(),d.getValue()+"("+d.getId()+")","","");
            responseList.add(field);
        });
        log.info("查询到的数据量为{}",responseList.size());
        responseList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getValue(),s2.getValue()));
        return responseList;
    }

    @Override
    public List<SelectField> searchAnotherField(String codeId) {
        if(StringUtils.isBlank(codeId)){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"该数据字典未查到有子元素，请更换数据字典");
        }
        List<SelectField> fieldList = synlteElementDao.searchAnotherField(codeId);
        if(fieldList.isEmpty()){
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,"根据codeId查询元素代码取值表时结果为空");
        }
        List<SelectField> responseList = new ArrayList<>();
        for(SelectField data: fieldList){
            SelectField field = new SelectField();
            field.setId(data.getId());
            field.setValue(data.getValue()+"("+data.getId()+")");
            responseList.add(data);
        }
        responseList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getValue(),s2.getValue()));
        return responseList;
    }

    @Override
    public Boolean checkIsRelevance(String elementCode) {
        if(StringUtils.isBlank(elementCode)){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"codeId值为空");
        }
        int count = synlteElementDao.checkIsRelevance(elementCode);
        return count <= 0;
    }

    @Override
    public List<SelectField> searchAllObject() {
        List<SelectField> elementObjectInfo = synlteElementDao.selectObjectList();
        if(elementObjectInfo.isEmpty()){
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,"synlte.fieldcodeval表中没有数据");
        }
        elementObjectInfo.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getId(),s2.getId()));
        return elementObjectInfo;
    }

    @Override
    public List<String> searchElementChname(String searchName) {
        if(StringUtils.isBlank(searchName)){
            searchName = null;
        }
        List<String> fieldList = synlteElementDao.searchElementChname(searchName);
        if(fieldList == null || fieldList.isEmpty()){
            return new ArrayList<>();
        }
        fieldList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1,s2));
        return fieldList;
    }

    @Override
    public void downloadAllElementTableExcel(HttpServletResponse response, List<SynlteElement> elementList, String name,
                                          Object object) {
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            for(SynlteElement data : elementList){
                data.setIsElementType(SynlteElementCode.getValueById("2_"+data.getIsElement()));
                data.setElementObjectType(SynlteElementCode.getValueById("3_"+data.getElementObject()));
                data.setCreateModeType(SynlteElementCode.getValueById("1_"+data.getCreateMode()));
            }
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("数据要素管理").doWrite(elementList);

        }catch (Exception e){
            log.error("下载数据要素报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }


    @Override
    public List<SynlteElement> importElementTableExcel(MultipartFile file) {
        log.info("=============开始将文件{}导入到数据库中======",file.getName());
        ExcelListener<SynlteElement> listener = new ExcelListener<>();
        List<SynlteElement> list = new ArrayList<>();
        ArrayList<SynlteElement> failedList = new ArrayList<>();
        List<String> elementChnames = new ArrayList<>();
        try{
            list = EasyExcelUtil.readExcelUtil(file,new SynlteElement(),listener);
            // 这里插入每一行数据，需要验证elementcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                SynlteElement elementManage = (SynlteElement) iterator.next();
                boolean flag = checkElementCodeConformRule(elementManage.getElementObject());
                if(flag){
                    try{
                        SynlteElement addElementManage = new SynlteElement();
                        BeanUtils.copyProperties(addElementManage,elementManage);
                        //验证格式
                        ValidatorUtil.checkObjectValidator(addElementManage);
                        int addFlag = synlteElementDao.addOneElement(addElementManage);
                        if(addFlag == 1){
                            elementChnames.add(addElementManage.getElementChname());
                            iterator.remove();
                        }else {
                            log.info("数据要素"+addElementManage.getElementChname()+"插入失败");
                        }
                    }catch (Exception e){
                        log.error("要素"+elementManage.getElementChname()+"插入失败"+e.getMessage());
                    }
                }else {
                    failedList.add(elementManage);
                    log.info("数据要素"+elementManage.getElementCode()+"已在数据库中存在");
                }
            }
        }catch (Exception e){
            log.error("导入文件报错"+ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException("导入文件报错"+e.getMessage());
        }
        // 发送操作日志
        if (elementChnames.size() > 0){
            operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.ADD, "数据要素管理", String.join(",", elementChnames));
        }
        log.info("导入成功的数量为："+list.size() +"导入失败的数量为：" +failedList);
        return list;
    }

    @Override
    public void downloadErrorElementTableExcel(HttpServletResponse response, List<SynlteElement> list,
                                         String name, SynlteElement object) {
        ExcelWriter excelWriter = null;
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0,"数据要素管理信息").head(SynlteElement.class)
                    .build();
            excelWriter.write(list,writeSheetTwo);

        }catch (Exception e){
            log.error("下载标签管理数据报错"+ ExceptionUtil.getExceptionTrace(e));
        }finally {
            if(excelWriter != null){
                excelWriter.finish();
            }
        }
    }

    @Override
    public void downloadElmentTemplateTableExcel(HttpServletResponse response, List<SynlteElement> summaryObjectTableList,
                                         String name, SynlteElement object) {
        ExcelWriter excelWriter = null;
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0,"数据要素模板管理信息").head(SynlteElement.class)
                    .build();
            excelWriter.write(summaryObjectTableList,writeSheetTwo);
            WriteSheet writeSheetThree = EasyExcel.writerSheet(1,"数据要素码值").head(TemplateCodeValue.class)
                    .build();
            excelWriter.write(ElementTemplateExcelUtils.getListData(),writeSheetThree);
        }catch (Exception e){
            log.error("下载数据要素管理数据报错"+ ExceptionUtil.getExceptionTrace(e));
        }finally {
            if(excelWriter != null){
                excelWriter.finish();
            }
        }
    }

    @Override
    public List<FilterObject> searchIsElement() {
        List<String> isElementList = synlteElementDao.searchIsElement();
        if(isElementList.isEmpty()){
            return new ArrayList<>();
        }
        else {
            List<FilterObject> lists = new ArrayList<>();
            for(String data : isElementList){
                FilterObject field = new FilterObject(SynlteElementCode.getValueById("2_"+data),data);
                lists.add(field);
            }
            return lists;
        }
    }

    @Override
    public String searchSameWord(String sameId) {
        log.info("==========开始根据sameId查询语义类型=========");
        String wordName = synlteElementDao.searchSameWord(sameId);
        log.info("查询出的语义类型是:{}",wordName);
        return wordName;
    }

    @Override
    public List<LayuiClassifyPojo> searchElementTotal() {
        log.info("--------------开始查询数据要素的统计信息--------------------");
        List<SelectField> elementTotalList = synlteElementDao.searchElementTotal();
        if(elementTotalList ==null|| elementTotalList.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<LayuiClassifyPojo> listData = new ArrayList<>();
        for(SelectField data:elementTotalList){
            List<LayuiClassifyPojo> secondElementList = synlteElementDao.searchSecondElementName(data.getId());
            LayuiClassifyPojo parentElement = new LayuiClassifyPojo(data.getId(), data.getName() + "(" + data.getValue() + ")",
                                            secondElementList);
            listData.add(parentElement);
        }
        log.info("--------------查询数据要素的统计信息结束--------------------");
        return listData;
    }


    /**
     * 检查更新的数据是否合规
     * @param elementCode 数据要素id
     * @param author
     * @return
     */
    private void checkUpdateData(String elementCode,String author){
        int num = synlteElementDao.findCountByElementCode(elementCode);
        if(num != 1){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "要素标识符["+elementCode
                    +"]在数据库中不存在，更新数据失败");
        }
        if(StringUtils.isBlank(author)){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "author的值["+author
                    +"]为空，更新失败");
        }

    }

    /**
     * 检查数据要素id
     * @param elementCode 数据要素id
     * @return
     */
    private boolean checkElementCodeConformRule(String elementCode){
        if(StringUtils.isBlank(elementCode)){
            return true;
        }
        if(!StringUtils.isNumeric(elementCode)){
            return false;
        }
        // 数据库中是否存在
        int flag = synlteElementDao.getCountById(elementCode);
        if(flag >0){
            return false;
        }
        return true;
    }

}
