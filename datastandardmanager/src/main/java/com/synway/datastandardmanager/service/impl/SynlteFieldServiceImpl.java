package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.ElementCodeSetManageDao;
import com.synway.datastandardmanager.dao.master.FieldCodeValDao;
import com.synway.datastandardmanager.dao.master.SynlteElementDao;
import com.synway.datastandardmanager.dao.master.SynlteFieldDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.enums.ObjectSecurityLevelType;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.pojo.synltefield.*;
import com.synway.datastandardmanager.service.SynlteFieldService;
import com.synway.datastandardmanager.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @date 2021/7/22 11:23
 */
@Service
@Slf4j
public class SynlteFieldServiceImpl implements SynlteFieldService {

    @Autowired
    private SynlteFieldDao synlteFieldDao;
    @Autowired
    private FieldCodeValDao fieldCodeValDao;

    @Autowired
    private ElementCodeSetManageDao elementCodeSetManageDao;

    @Autowired
    private SynlteElementDao synlteElementDao;

    @Autowired()
    private Environment env;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static HashLock<String> HASH_LOCK = new HashLock<>();


    @Override
    public Map<String,Object> searchAllTable(SynlteFieldParameter parameter) {

        if(StringUtils.isBlank(parameter.getSearchName())){
            parameter.setSearchName(null);
        }
        if(parameter.getFieldClassList() != null && parameter.getFieldClassList().contains("")){
            parameter.setFieldClassIsNull(true);
        }
        if(parameter.getSecretClassList() != null && parameter.getSecretClassList().contains("")){
            parameter.setSecretClassIsNull(true);
        }
        if(parameter.getStatusFilterList() != null && parameter.getStatusFilterList().contains("")){
            parameter.setStatusIsNull(true);
        }

        Page<SynlteFieldObject> page = PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());

        List<SynlteFieldObject> list = synlteFieldDao.searchAllTable(parameter);

        // 需要对状态进行翻译
        if(list != null &&  !list.isEmpty()){
            for(SynlteFieldObject synlteFieldObject:list){
                synlteFieldObject.setStatus(SynlteFieldStatusEnum.getValueById(synlteFieldObject.getStatusNum()));
                //回填数据分级中文
                if(StringUtils.isNotBlank(synlteFieldObject.getSecretClass())){
                    synlteFieldObject.setSecretClassCh(ObjectSecurityLevelType.getValueById("2_"+synlteFieldObject.getSecretClass()));
                }
                if(StringUtils.isNotBlank(synlteFieldObject.getCodeId())){
                    synlteFieldObject.setCodeIdStr(elementCodeSetManageDao.searchFieldCodeByCodeId(synlteFieldObject.getCodeId()));
                }
                if(synlteFieldObject.getFieldType() != null ){
                    synlteFieldObject.setFieldTypeCh(SynlteFieldType.getSynlteFieldType(synlteFieldObject.getFieldType()));
                }
                if (StringUtils.isNotBlank(synlteFieldObject.getFieldClass())){
                    synlteFieldObject.setFieldClassCh(SynlteFieldClassEnum.getValueById(synlteFieldObject.getFieldClass()));
                }
            }
        }
        PageInfo<SynlteFieldObject> pageInfo = new PageInfo<>(list);
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());

        return map;
    }


    @Override
    public SynlteFieldFilter getFilterObject() {
        SynlteFieldFilter data = new SynlteFieldFilter();
        List<FilterObject> list = synlteFieldDao.searchAllFilterTable();
        if(list == null || list.isEmpty()){
            data.setStatusFilter(new ArrayList<>());
            data.setFieldClassFilter(new ArrayList<>());
            data.setSecurityLevelFilter(new ArrayList<>());
        }else{
            // text里面为 status的 需要进行枚举类来筛选
            List<FilterObject> listStatus = new ArrayList<>();
            list.stream().filter( d-> StringUtils.equalsIgnoreCase(d.getText(), Common.STATUS))
                    .forEach(d ->{
                        d.setValue(StringUtils.isBlank(d.getValue())?"":d.getValue());
                        d.setText(SynlteFieldStatusEnum.getValueById(d.getValue()));
                        listStatus.add(d);
                    });
            data.setStatusFilter(listStatus);
            // text里面为 fieldClass 的 直接获取就可以了
            List<FilterObject> listFieldClass = new ArrayList<>();
            list.stream().filter( d-> StringUtils.equalsIgnoreCase(d.getText(), Common.FIELDCLASS))
                    .forEach(d ->{
                        d.setValue(StringUtils.isBlank(d.getValue())?"":d.getValue());
                        d.setText(SynlteFieldClassEnum.getValueById(d.getValue()));
                        listFieldClass.add(d);
                    });
            data.setFieldClassFilter(listFieldClass);
            // text里面为 安全级别 的 直接获取就可以了
            List<FilterObject> securityLevelList = new ArrayList<>();
            list.stream().filter( d-> StringUtils.equalsIgnoreCase(d.getText(), Common.SECURITYLEVEL))
                    .forEach(d ->{
                        d.setValue(StringUtils.isBlank(d.getValue())?"":d.getValue());
                        d.setText(ObjectSecurityLevelType.getValueById("2_"+d.getValue()));
                        securityLevelList.add(d);
                    });
            data.setSecurityLevelFilter(securityLevelList);

        }
        return data;
    }


    @Override
    public List<String> getSearchNameSuggest(String searchName) {
        // 如果这个为null 则只提示200条数据
        if(StringUtils.isBlank(searchName)){
            searchName = "";
        }
        List<String> list = synlteFieldDao.getSearchNameSuggest(searchName);
        if(list == null|| list.isEmpty()){
            return new ArrayList<>();
        }
        String finalSearchName = searchName;
        list = list.stream().filter(StringUtils::isNotBlank).sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                .compare(s2.replaceFirst(finalSearchName,""), s1.replaceFirst(finalSearchName,"")))
                .limit(100).collect(Collectors.toList());
        return list;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public String updateSimChinese() {
//        log.info("已有数据元，“标识符”为空的记录，自动生成“标识符”信息");
//        List<SynlteFieldObject> list = synlteFieldDao.getAllNullSimChinese();
//        if(list == null || list.isEmpty()){
//            log.info("数据库中为空，不需要更新标志符");
//            return "数据库中为空，不需要更新标志符";
//        }
//        List<SynlteFieldObject> needEditList = list.stream().filter(d ->StringUtils.isBlank(d.getSimChinese()) &&
//                StringUtils.isNotBlank(d.getFieldChineseName())).collect(Collectors.toList());
//        if(needEditList.isEmpty()){
//            log.info("数据库中为标志符为空的数据为0，不需要更新");
//            return "数据库中为标志符为空的数据为0，不需要更新";
//        }
//        List<String> simChineseList = list.stream().filter(d->StringUtils.isNotBlank(d.getSimChinese()))
//                .map(SynlteFieldObject::getSimChinese).collect(Collectors.toList());
//        needEditList.stream().filter(d->StringUtils.isNotBlank(d.getFieldId())).forEach( d->{
//            String simChinese = getSimChinese(d.getFieldChineseName(),simChineseList);
//            simChineseList.add(simChinese);
//            synlteFieldDao.updateNullSimChinese(simChinese,d.getFieldId());
//        });
//        log.info("更新数据元信息成功");
//        return "更新成功";
//    }
//
//    @Override
//    public String delObjectByFieldId(String fieldId) {
//        log.info("开始删除表synlte. synltefield中fieldId为{}的数据",fieldId);
//        int deleteNum = synlteFieldDao.deleteObjectByFieldId(fieldId);
//        log.info("删除的数据量为{}",deleteNum);
//        return "数据删除成功";
//    }

    @Override
    public String addObject(SynlteFieldObject synlteFieldObject) {
        // 检查 数据是否正确
        checkAddData(synlteFieldObject.getFieldId(),synlteFieldObject.getColumnName(),
                synlteFieldObject.getSameId(),synlteFieldObject.getCodeId());
        // 数据的回填
        String lockId =PinYinUtil.getLockId(synlteFieldObject.getFieldChineseName());
        HASH_LOCK.lock(lockId);
        try{
            dataBackFill(synlteFieldObject,new SynlteFieldObject());
            // 相关事件进行修改
            int num = synlteFieldDao.insertObject(synlteFieldObject);
            log.info("插入synlte.SYNLTEFIELD表中数据量为{}",num);
            if(num == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "插入失败,[内部标识符]或[数据库字段名称]重复,请刷新后重新插入");
            }

            // 发送操作日志
            operateLogServiceImpl.synlteFieldSuccessLog(OperateLogHandleTypeEnum.ADD, "数据元管理", synlteFieldObject);
        }finally {
            HASH_LOCK.unlock(lockId);
        }
        return "新增成功";
    }


    @Override
    public String updateObject(SynlteFieldObject synlteFieldObject) {
        // 检查一些码表值是否正确
        checkUpdateData(synlteFieldObject.getFieldId(),synlteFieldObject.getUpdateTime(),
                synlteFieldObject.getSameId(),synlteFieldObject.getCodeId());

        // 判断是否修改数据元属性值，未做任何改动，直接返回，不更新数据库
        SynlteFieldObject oldSynlteFieldObject = synlteFieldDao.searchSynlteFieldById(synlteFieldObject.getFieldId());
        if(oldSynlteFieldObject != null){
            oldSynlteFieldObject.setStatus(SynlteFieldStatusEnum.getValueById(oldSynlteFieldObject.getStatusNum()));
        }
        if(synlteFieldObject.equals(oldSynlteFieldObject)){
            return "数据元并未改动";
        }

        String lockId =PinYinUtil.getLockId(synlteFieldObject.getFieldChineseName());
        HASH_LOCK.lock(lockId);
        // 数据的回填
        int num = 0;
        try{
            dataBackFill(synlteFieldObject,oldSynlteFieldObject);
            // 相关事件进行修改
            num = synlteFieldDao.updateObject(synlteFieldObject);
            log.info("更新synlte.SYNLTEFIELD表中数据量为{}",num);
            if(num == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "更新失败，请刷新后重新更改数据");
            }

            //生成数据元版本信息
            if("05".equals(oldSynlteFieldObject.getStatusNum())){
                log.info("===========开始生成数据元版本信息=========");
                SynlteFieldVersion synlteFieldVersion = new SynlteFieldVersion();
                String uuid = UUIDUtil.getUUID();
                synlteFieldVersion.setFieldIdVersion(uuid);
                synlteFieldVersion.setFieldId(oldSynlteFieldObject.getFieldId());
                synlteFieldVersion.setColumnName(oldSynlteFieldObject.getColumnName());

                //添加被修改的属性
                StringBuffer stringBuffer = new StringBuffer();
                if(!synlteFieldObject.getFieldChineseName().equals(oldSynlteFieldObject.getFieldChineseName())){
                    stringBuffer.append("中文名称属性、");
                }
                if(!synlteFieldObject.getFieldName().equals(oldSynlteFieldObject.getFieldName())){
                    stringBuffer.append("英文名称属性、");
                }
                if(!(synlteFieldObject.getSimChinese().equals(oldSynlteFieldObject.getSimChinese()))){
                    stringBuffer.append("标识符属性、");
                }
                if(!(synlteFieldObject.getObjectType().equals(oldSynlteFieldObject.getObjectType()))){
                    stringBuffer.append("对象类词属性、");
                }
                if(!(synlteFieldObject.getSameId().equals(oldSynlteFieldObject.getSameId()))){
                    stringBuffer.append("语义类型属性、");
                }
                if(!(synlteFieldObject.getAttributeWord().equalsIgnoreCase(oldSynlteFieldObject.getAttributeWord()))){
                    stringBuffer.append("特性词属性、");
                }
                if(!(synlteFieldObject.getRelate().equals(oldSynlteFieldObject.getRelate()))){
                    stringBuffer.append("关系属性、");
                }
                if(!(synlteFieldObject.getExpressionWord().equals(oldSynlteFieldObject.getExpressionWord()))){
                    stringBuffer.append("表示词属性、");
                }
                if(!(synlteFieldObject.getUnit().equals(oldSynlteFieldObject.getUnit()))){
                    stringBuffer.append("计量单位属性、");
                }
                if(!(synlteFieldObject.getCodeIdDetail().equals(oldSynlteFieldObject.getCodeIdDetail()))){
                    stringBuffer.append("值域描述属性、");
                }
                if(!(synlteFieldObject.getSubOrg().equals(oldSynlteFieldObject.getSubOrg()))){
                    stringBuffer.append("提交机构属性、");
                }
                if(!(synlteFieldObject.getStatusNum().equals(oldSynlteFieldObject.getStatusNum()))){
                    stringBuffer.append("状态属性、");
                }
                if(!(synlteFieldObject.getFuseType().equals(oldSynlteFieldObject.getFuseType()))){
                    stringBuffer.append("融合单位类型属性、");
                }
                if(!(synlteFieldObject.getVersions().equals(oldSynlteFieldObject.getVersions()))){
                    stringBuffer.append("大版本属性、");
                }
                StringBuffer constantStr = null;
                if(stringBuffer.length() != 0){
                    constantStr = new StringBuffer("修改了");
                    constantStr.append(stringBuffer);
                    if(constantStr.indexOf("、",constantStr.length()-1) != -1){
                        synlteFieldVersion.setMemo(constantStr.substring(0,constantStr.length()-1));
                    }
                }else{
                    constantStr = new StringBuffer("未修改内容");
                    synlteFieldVersion.setMemo(constantStr.toString());
                }

                String todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                synlteFieldVersion.setVersion(Integer.valueOf(todayStr));

                synlteFieldVersion.setVersions(synlteFieldObject.getVersions());

                synlteFieldVersion.setAuthor(synlteFieldObject.getUpdater());

                Date date = null;
                String dayStr= null;
                try{
                    dayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME);
                    date = DateUtil.parseDate(dayStr,DateUtil.DEFAULT_PATTERN_DATETIME);
                }catch (Exception e){
                    log.error("解析时间报错"+ExceptionUtil.getExceptionTrace(e));
                    date = new Date();
                }
                synlteFieldVersion.setUpdateTime(date);

                synlteFieldDao.saveSynlteFieldVersion(synlteFieldVersion);
                log.info("===========数据元版本信息生成完成=========");

                //原有记录信息保存至历史版本信息表SYNLTE.SYNLTEFIELD_history中，并且数据元状态改为停用（07）
                log.info("===========数据元历史信息开始生成=========");
                oldSynlteFieldObject.setFieldIdVersion(oldSynlteFieldObject.getFieldId());
                oldSynlteFieldObject.setStatusNum("07");
                oldSynlteFieldObject.setStatus(SynlteFieldStatusEnum.getValueById(oldSynlteFieldObject.getStatusNum()));
                oldSynlteFieldObject.setUpdateTime(date);
                oldSynlteFieldObject.setReleaseDate(Integer.valueOf(todayStr));
                int saveOneOldData = synlteFieldDao.saveOneOldData(oldSynlteFieldObject);
                log.info("===========数据元历史信息生成完成{}=========",saveOneOldData);
            }
            // 发送操作日志
            operateLogServiceImpl.synlteFieldSuccessLog(OperateLogHandleTypeEnum.ALTER, "数据元管理", synlteFieldObject);
        }finally {
            HASH_LOCK.unlock(lockId);
        }
        return "更新成功";
    }

    /**
     * 赋值
     * @param synlteFieldObject  页面上传递的数据元信息
     * @param oldSynlteFieldObject  数据库中的数据元信息
     */
    private void dataBackFill(SynlteFieldObject synlteFieldObject,SynlteFieldObject oldSynlteFieldObject){
        // 获取限定词
        // 如果是编辑 并且数据库中的 限定词和前端传递过来的限定词相同 则不需要更新
        if(oldSynlteFieldObject == null ||
                !StringUtils.equalsIgnoreCase(oldSynlteFieldObject.getSimChinese(),synlteFieldObject.getSimChinese())){
            String simChinese = getSimChinese(synlteFieldObject.getFieldChineseName(),null);
            synlteFieldObject.setSimChinese(simChinese);
        }
        // 批准时间 YYYYMMDD
        Date date = null ;
        String todayStr = null;
        try{
            todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            date = DateUtil.parseDate(todayStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        }catch (Exception e){
            log.error("解析时间报错"+ExceptionUtil.getExceptionTrace(e));
            date = new Date();
        }
        synlteFieldObject.setReleaseDate(Integer.valueOf(todayStr));
        synlteFieldObject.setOnDate(date);
        //  中文全拼
        String fullChinese = PinYinUtil.getPySpell(synlteFieldObject.getFieldChineseName());
        synlteFieldObject.setFullChinese(StringUtils.isBlank(fullChinese)?"":StringUtils.substring(fullChinese,0,200));

        //大版本 从版本管理中读取
        String version = fieldCodeValDao.searchVersion();
        JSONObject parse = (JSONObject) JSON.parse(version);
        String versions = parse.getString("synlteFieldVersions");
        synlteFieldObject.setVersions(versions);
        oldSynlteFieldObject.setFieldClassCh(synlteFieldObject.getFieldClassCh());
        oldSynlteFieldObject.setFieldChineseName(synlteFieldObject.getFieldChineseName());
        oldSynlteFieldObject.setFullChinese(synlteFieldObject.getFullChinese());
        oldSynlteFieldObject.setSimChinese(synlteFieldObject.getSimChinese());
        oldSynlteFieldObject.setSubOrg(synlteFieldObject.getSubOrg());
    }

    /**
     * 新增时的数据检查
     * @param fieldId
     * @param columnName
     * @param sameId
     * @param codeId
     */
    private void checkAddData(String fieldId,String columnName,String sameId,String codeId){
        // 检查 fieldId 和 columnName 是否已经存在，如果存在，则报错
        int countField = synlteFieldDao.getObjectCountById(fieldId);
        if(countField > 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "fieldId的值["+fieldId
                    +"]在数据库表synlte.SYNLTEFIELD中已经存在，请重新填写该值");
        }
        int countColumn = synlteFieldDao.checkColumnExits(columnName);
        if(countColumn > 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "columnName的值["+columnName
                    +"]在数据库表synlte.SYNLTEFIELD中已经存在，请重新填写该值");
        }
        // 检查特性词
        if(StringUtils.isNotBlank(sameId)){
            int count = synlteFieldDao.checkSameId(sameId);
            if(count == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "sameId的值["+sameId
                        +"]在数据库表synlte.sameword不存在,新增失败");
            }
        }
        //  验证codeId 是否符合规范
        if(StringUtils.isNotBlank(codeId)){
            int count = synlteFieldDao.checkCodeIdExits(codeId);
            if(count == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "codeId的值["+codeId
                        +"]在数据库表synlte.FIELDCODE不存在,新增失败");
            }
        }
    }


    /**
     * 编辑时的数据检查
     * @param fieldId
     * @param updateTime
     * @param sameId
     * @param codeId
     */
    private void checkUpdateData(String fieldId,Date updateTime,String sameId,String codeId){
        // 1： 先验证 fieldId是否存在，如果不存在说明被删除
//        List<String> releaseDateList =synlteFieldDao.getReleaseDateCountById(fieldId);
        int objectFiled = synlteFieldDao.getObjectCountById(fieldId);
        if(objectFiled <= 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "内部标识符["+fieldId
                    +"]在数据库中不存在，更新数据失败");
        }
        List<String> updateDateList = synlteFieldDao.getUpdateDateCountById(fieldId);
        // 2：验证更新时间 UPDATETIME 是否相同 不相同说明已经被别人更新，本次更新失败
        if(updateDateList.size() >1){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "内部标识符["+fieldId
                    +"]在数据库存在多个值，更新数据失败");
        }
        if(StringUtils.isBlank(updateDateList.get(0))
                && updateTime == null){
            log.info("数据库中的时间和页面传递的时间都为null，验证通过");
            return;
        }
        try{
            Date dbDate = DateUtil.parseDate(updateDateList.get(0),DateUtil.DEFAULT_PATTERN_DATETIME);
            if(dbDate.compareTo(updateTime) != 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "内部标识符["+fieldId
                        +"]在["+updateDateList.get(0)+"]时被其它页面更新，本次更新失败");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        //
        if(StringUtils.isNotBlank(sameId)){
            int count = synlteFieldDao.checkSameId(sameId);
            if(count == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "sameId的值["+sameId
                        +"]在数据库表synlte.sameword不存在,更新失败");
            }
        }
        //  验证codeId 是否符合规范
        if(StringUtils.isNotBlank(codeId)){
            int count = synlteFieldDao.checkCodeIdExits(codeId);
            if(count == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "codeId的值["+codeId
                        +"]在数据库表synlte.FIELDCODE不存在,更新失败");
            }
        }

    }

    /**
     * 发布指定数据元的相关信息
     *  将状态改成 05
     * @param list
     * @return
     */
    @Override
    public String updateSynlteFieldStatus(List<String> list,String status) {
        if(list == null || list.isEmpty()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR, "未选择需要发布的数据元信息");
        }
        //  版本发布日期 date   YYYYMMDD
        Date date = null ;
        try{
            String todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            date = DateUtil.parseDate(todayStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        }catch (Exception e){
            log.error("解析时间报错"+ ExceptionUtil.getExceptionTrace(e));
            date = new Date();
        }
        list = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        int updateNum = synlteFieldDao.updateSynlteFieldStatus(list,date,status);
        log.info("发布数据成功的数据量为{}",updateNum);
        return "状态修改成功:"+updateNum+"条";
    }

    /**
     * 获取语义类型的相关下拉框信息
     * @param searchName
     * @return
     */
    @Override
    public List<FilterObject> getSameWordList(String searchName) {
        if(StringUtils.isBlank(searchName)){
            searchName = null;
        }
        List<FilterObject> list = synlteFieldDao.getSameWordList(searchName);
        if(list == null || list.isEmpty()){
            return new ArrayList<>();
        }
        list.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        return list;
    }

    /**
     * 标识符是在数据应用中对数据元的统一标识。标识符由该数据元中文名称中每个汉字的汉语拼音首字母
     * 不区分大小写）组成.。不同数据元的标识符若出现重复，则在后面加01～99予以区别
     *
     * @param name  中文名称
     * @return
     */
    @Override
    public String getSimChinese(String name,List<String> listOld) {
        try{
            // 中文转拼音
            String dName = PinYinUtil.getFirstSpell(name);
            if(StringUtils.isBlank(dName)){
                return dName;
            }
            List<String> list = null;
            // 然后根据拼音在数据库中查询相同拼音的有几个 获取最大的那个
            if(listOld == null || listOld.isEmpty()){
                list = synlteFieldDao.findSimChineseList(dName);
            }else{
                list = listOld.stream().filter(d-> StringUtils.startsWithIgnoreCase(d,dName)).collect(Collectors.toList());
            }
            if(list == null || list.isEmpty()){
                // 为空，直接用拼音首字母
                return dName;
            }else{
                Optional<String> maxOptional = list.stream().filter(d ->{
                    if(StringUtils.isNotBlank(d)){
                        String[] list1 = d.toLowerCase().trim().split(dName.toLowerCase(),-1);
                        return (StringUtils.isBlank(list1[1]) ||
                                StringUtils.isNumeric(list1[1])) && list1.length == 2;
                    }else{
                        return false;
                    }
                }).map(d-> {
                    if(!StringUtils.equalsIgnoreCase(d,dName)){
                        return  d.toLowerCase().replaceAll(dName.toLowerCase(),"");
                    }else{
                        return "0";
                    }
                }).max(Comparator.comparingInt(Integer::parseInt));
                if(maxOptional.isPresent() && StringUtils.isNotBlank(maxOptional.get())){
                    int num = Integer.parseInt(maxOptional.get());
                    if(num < 9 ){
                        return (dName.toLowerCase()+"0"+(num+1));
                    }else{
                        return (dName.toLowerCase()+(num+1));
                    }
                }else{
                    return dName;
                }
            }
        }catch (Exception e){
            log.error("从数据库中获取顺序报错："+ ExceptionUtil.getExceptionTrace(e));
            throw  SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"从数据库中获取顺序报错："+ e.getMessage());
        }
    }

    /**
     * 一些选择框需要从码表库里面查询结构
     *
     * @param name EXPRESSION_WORD：表示词
     * @return
     */
    @Override
    public List<FilterObject> getSelectObjectByName(String name) {
        return synlteFieldDao.getSelectObjectByName(name);
    }

    /**
     * 从码表里查询数据安全分级字段的列表
     * @return
     */
    @Override
    public List<PageSelectOneValue> searchDataSecurityLevel() {
        List<PageSelectOneValue> securityLevelList = synlteFieldDao.searchDataSecurityLevelList();
        if(securityLevelList.size() == 0 || securityLevelList.isEmpty()){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "数据安全分类的值["+securityLevelList
                    +"]查询失败");
        }
        return securityLevelList;
    }

    @Override
    public SynlteFieldObject searchSynlteFieldById(String fieldId) {
        if(StringUtils.isBlank(fieldId)){
           throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR, "查询数据元相关信息的参数异常");
        }
        log.info("开始查询id为:{}的数据元信息",fieldId);
        SynlteFieldObject synlteFieldObject = synlteFieldDao.searchSynlteFieldById(fieldId);
        log.info("查询结束=====");
        return synlteFieldObject;
    }

    @Override
    public List<PageSelectOneValue> getGadsjFieldByText(String searchText,String fieldType) {
        log.info("查询数据元下拉框的参数为:{}",searchText);
        if(StringUtils.isBlank(searchText)){
            searchText = null;
        }
        if(StringUtils.isNotBlank(fieldType) && fieldType.indexOf(",") != -1){
            fieldType = null;
        }

        List<PageSelectOneValue> resultList = synlteFieldDao.getGadsjFieldByText(searchText,fieldType,null);
        if(resultList == null || resultList.isEmpty()){
            return new ArrayList<>();
        }
        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                .compare(s2.getLabel(), s1.getLabel()))
                .limit(100).collect(Collectors.toList());
        log.info("========查询数据元下拉框结束，条数为:{}=====",resultList.size());

        return resultList;
    }

    @Override
    public void downloadSynlteFieldExcel(HttpServletResponse response, SynlteFieldParameter data, String name, Object object) {
        if(data.getSynlteFieldObjectList().size() != 0 || !data.getSynlteFieldObjectList().isEmpty() ){
            try{
                List<SynlteFieldObject> synlteFieldObjectList = data.getSynlteFieldObjectList();
                List<SynlteFieldExcel> synlteFieldExcelList = new ArrayList<>();

                synlteFieldObjectList.stream().forEach( d-> {
                    SynlteFieldExcel synlteFieldExcel = new SynlteFieldExcel();
                    try {
                        ConvertUtils.register(new DateConverter(null),java.util.Date.class);
                        BeanUtils.copyProperties(synlteFieldExcel,d);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    synlteFieldExcelList.add(synlteFieldExcel);
                    });
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
                response.setHeader("Content-disposition", "attachment;filename*=utf-8''" +fileName);
                EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                        .sheet("标准数据元").doWrite(synlteFieldExcelList);

            }catch (Exception e){
                log.error("下载数据元错", e);
            }
        }else{
            //保持于页面一致的筛选条件
            if(StringUtils.isBlank(data.getSearchName())){
                data.setSearchName(null);
            }
            if(data.getFieldClassList() != null && data.getFieldClassList().contains("")){
                data.setFieldClassIsNull(true);
            }
            if(data.getSecretClassList() != null && data.getSecretClassList().contains("")){
                data.setSecretClassIsNull(true);
            }
            if(data.getStatusFilterList() != null && data.getStatusFilterList().contains("")){
                data.setStatusIsNull(true);
            }
            //查询数据库里的数据元
            List<SynlteFieldObject> list = synlteFieldDao.searchAllTable(data);
            List<SynlteFieldExcel> synlteFieldExcelList = new ArrayList<>();
            list.stream().forEach( d-> {
                SynlteFieldExcel synlteFieldExcel = new SynlteFieldExcel();
                try {
                    ConvertUtils.register(new DateConverter(null),java.util.Date.class);
                    BeanUtils.copyProperties(synlteFieldExcel,d);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                synlteFieldExcelList.add(synlteFieldExcel);
            });
            try{
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
                response.setHeader("Content-disposition", "attachment;filename*=utf-8''" +fileName);
                EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                        .sheet("标准数据元").doWrite(synlteFieldExcelList);
            }catch (Exception e){
                log.error("下载数据元报错"+ ExceptionUtil.getExceptionTrace(e));
            }
        }


    }

    @Override
    public String importSynlteFieldExcel(MultipartFile file) {
        log.info("=============开始将文件{}导入到数据库中======",file.getName());
        ExcelListener<SynlteFieldExcel> listener = new ExcelListener<>();
        List<SynlteFieldExcel> list = new ArrayList<>();
        ArrayList<SynlteFieldExcel> failedList = new ArrayList<>();
        ArrayList<SynlteFieldExcel> successList = new ArrayList<>();
        ArrayList<SynlteFieldExcel> versionList = new ArrayList<>();
        int fieldTotal=0;
        try{
            list = EasyExcelUtil.readExcelUtil(file,new SynlteFieldExcel(),listener);
            fieldTotal = list.size();
            // 这里插入每一行数据，需要验证必填项是否为空，fieldId是否唯一
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                SynlteFieldExcel synlteFieldExcel = (SynlteFieldExcel) iterator.next();
                boolean flag = checkSynlteFieldRule(synlteFieldExcel);
                if(flag){
                    try{
                        //验证格式
                        boolean checkFlag = ValidatorUtil.checkObjectValidator(synlteFieldExcel);

                        if(!checkFlag){
                            failedList.add(synlteFieldExcel);
                            throw new NullPointerException("导入文件报错");
                        }

                        //通过sameId回填同义词名称
                        if(StringUtils.isNotBlank(synlteFieldExcel.getSameId())){
                            synlteFieldExcel.setSynonyName(synlteElementDao.searchSameWord(synlteFieldExcel.getSameId()));
                        }
                        //通过codeId回填值域信息描述
                        if(StringUtils.isNotBlank(synlteFieldExcel.getCodeId())){
                            synlteFieldExcel.setCodeIdDetail(elementCodeSetManageDao.searchFieldCodeByCodeId(synlteFieldExcel.getCodeId()));
                        }

                        int fieldCount = synlteFieldDao.searchSynlteFieldCount(synlteFieldExcel.getFieldId(), synlteFieldExcel.getColumnName());
                        if(fieldCount == 1){
                            //数据元已存在，比较版本日期，新数据元小于等于旧数据元版本日期不处理，大于的进行更新
                            Integer oldReleaseDate = synlteFieldDao.getReleaseDateCountById(synlteFieldExcel.getFieldId());
                            if(oldReleaseDate != null){
                                if(oldReleaseDate >= synlteFieldExcel.getReleaseDate()){
                                    versionList.add(synlteFieldExcel);
                                    iterator.remove();
                                }else{
                                    //将旧数据插入到历史库中
                                    SynlteFieldObject oldSynlteFieldObject = synlteFieldDao.searchSynlteFieldById(synlteFieldExcel.getFieldId());
                                    log.info("===========数据元历史信息开始生成=========");
                                    Date date = null;
                                    String dayStr= null;
                                    String todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                                    try{
                                        dayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME);
                                        date = DateUtil.parseDate(dayStr,DateUtil.DEFAULT_PATTERN_DATETIME);
                                    }catch (Exception e){
                                        log.error("解析时间报错"+ExceptionUtil.getExceptionTrace(e));
                                        date = new Date();
                                    }
                                    oldSynlteFieldObject.setFieldIdVersion(synlteFieldExcel.getFieldId());
                                    oldSynlteFieldObject.setStatusNum("07");
                                    oldSynlteFieldObject.setStatus(SynlteFieldStatusEnum.getValueById(oldSynlteFieldObject.getStatusNum()));
                                    oldSynlteFieldObject.setUpdateTime(date);
                                    oldSynlteFieldObject.setReleaseDate(Integer.valueOf(todayStr));

                                    int saveOneOldData = synlteFieldDao.saveOneOldData(oldSynlteFieldObject);
                                    log.info("插入数据元历史库的数据为:{}条",saveOneOldData);
                                    //更新数据元
                                    SynlteFieldObject updateSynlteField = new SynlteFieldObject();
                                    ConvertUtils.register(new DateConverter(null),java.util.Date.class);
                                    BeanUtils.copyProperties(updateSynlteField,synlteFieldExcel);
                                    dataBackFill(updateSynlteField,null);
                                    int updateCount = synlteFieldDao.updateObject(updateSynlteField);
                                    log.info("更新的数据元为:{}",updateCount);
                                    if(updateCount == 1){
                                        iterator.remove();
                                        successList.add(synlteFieldExcel);
                                    }else {
                                        log.info("数据元"+synlteFieldExcel.getColumnName()+"插入失败");
                                        failedList.add(synlteFieldExcel);
                                    }
                                }

                            }
                        }else {
                            //不存在直接插入
                            SynlteFieldObject addSynlteField = new SynlteFieldObject();
                            ConvertUtils.register(new DateConverter(null),java.util.Date.class);
                            BeanUtils.copyProperties(addSynlteField,synlteFieldExcel);
                            dataBackFill(addSynlteField,null);
                            // 相关事件进行修改
                            int addFlag = synlteFieldDao.insertObject(addSynlteField);
                            log.info("插入synlte.SYNLTEFIELD表中数据量为{}",addFlag);
                            if(addFlag == 0){
                                failedList.add(synlteFieldExcel);
                                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "插入失败,[内部标识符]或[数据库字段名称]重复,请刷新后重新插入");
                            }
                            if(addFlag == 1){
                                iterator.remove();
                                successList.add(synlteFieldExcel);
                            }else {
                                log.info("数据元"+synlteFieldExcel.getColumnName()+"插入失败");
                                failedList.add(synlteFieldExcel);
                            }
                        }

                    }catch (Exception e){
                        failedList.add(synlteFieldExcel);
                        log.error("数据元"+synlteFieldExcel.getColumnName()+"插入失败"+e.getMessage());
                        throw new Exception("字段数据异常");
                    }
                }else {
                    failedList.add(synlteFieldExcel);
                }
            }
        }catch (Exception e){
            log.error("导入文件报错"+ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException("导入文件报错"+e.getMessage());
        }
        return "文件处理完成，读取记录数"+fieldTotal+"条；入库成功记录："+successList.size()+"条；忽略低版本记录"+versionList.size()+"条，" +
                "处理失败："+failedList.size()+"条。";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SynlteFieldExcel> clearImportSynlteFieldExcel(MultipartFile file) {
        //先将所有数据元导入到版本库中，再清空导入
        List<SynlteFieldObject> allSynlteField = synlteFieldDao.searchAllSynlteField();
        log.info("查出旧的数据元共有{}条",allSynlteField.size());
        // 时间 YYYYMMDD
        Date date = null ;
        String todayStr = null;
        try{
            todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            date = DateUtil.parseDate(todayStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        }catch (Exception e){
            log.error("解析时间报错"+ExceptionUtil.getExceptionTrace(e));
            date = new Date();
        }
        //回填数据元历史数据
        for(SynlteFieldObject data : allSynlteField){
            data.setFieldIdVersion(UUIDUtil.getUUID());
            data.setStatusNum("07");
            data.setStatus(SynlteFieldStatusEnum.getValueById(data.getStatusNum()));
            data.setUpdateTime(date);
            data.setReleaseDate(Integer.valueOf(todayStr));
            synlteFieldDao.saveOneOldData(data);
        }
//        int insertCount = synlteFieldDao.insertAllSynlteFieldToHistory(allSynlteField);
//        log.info("插入历史库的数据为:{}",insertCount);
        int deleteCount = synlteFieldDao.deleteAllSynlteField();
        log.info("清空的数据为:{}",deleteCount);

        //开始导入数据元
        log.info("=============开始将文件{}导入到数据库中======",file.getName());
        ExcelListener<SynlteFieldExcel> listener = new ExcelListener<>();
        List<SynlteFieldExcel> list = new ArrayList<>();
        List<SynlteFieldExcel> failedList = new ArrayList<>();

        try{
            //开始导入数据
            list = EasyExcelUtil.readExcelUtil(file,new SynlteFieldExcel(),listener);
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                SynlteFieldExcel synlteField = (SynlteFieldExcel) iterator.next();
                //这里导入的每一条数据都要验证必填项是否为空
                boolean flag = checkSynlteFieldRule(synlteField);
                if(flag){
                    try{
                        SynlteFieldObject synlteFieldObject = new SynlteFieldObject();
                        BeanUtils.copyProperties(synlteFieldObject,synlteField);
                        //验证格式
                        ValidatorUtil.checkObjectValidator(synlteFieldObject);

                        int addFlag = synlteFieldDao.insertObject(synlteFieldObject);
                        if(addFlag == 1){
                            iterator.remove();
                        }else {
                            log.info("数据元"+synlteFieldObject.getColumnName()+"插入失败");
                        }
                    }catch (Exception e){
                        log.error("数据元"+synlteField.getColumnName()+"插入失败"+e.getMessage());
                    }
                }else {
                    failedList.add(synlteField);
                }
            }
        }catch (Exception e){
            log.error("导入文件报错"+ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException("导入文件报错"+e.getMessage());
        }
        log.info("导入成功的数量为："+list.size() +"导入失败的数量为：" +failedList);
        return list;
    }


    @Override
    public void downloadSynlteFieldSql(SynlteFieldParameter parameter, HttpServletResponse response,String fileName) {
        List<SynlteFieldObject> synlteFieldObjectList = new ArrayList<SynlteFieldObject>();

        StringBuffer sql = new StringBuffer();
        //设置响应信息
        setServletResponse(response,fileName);

        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        //判断是导出页面勾选的数据还是库中全部数据
        if(parameter.getSynlteFieldObjectList().size() != 0){
            //页面选中的数据元导出
            synlteFieldObjectList = parameter.getSynlteFieldObjectList();

        }else{
            //保持于页面一致的筛选条件
            if(StringUtils.isBlank(parameter.getSearchName())){
                parameter.setSearchName(null);
            }
            if(parameter.getFieldClassList() != null && parameter.getFieldClassList().contains("")){
                parameter.setFieldClassIsNull(true);
            }
            if(parameter.getSecretClassList() != null && parameter.getSecretClassList().contains("")){
                parameter.setSecretClassIsNull(true);
            }
            if(parameter.getStatusFilterList() != null && parameter.getStatusFilterList().contains("")){
                parameter.setStatusIsNull(true);
            }
            //查询数据库里的数据元
            synlteFieldObjectList = synlteFieldDao.searchAllTable(parameter);
        }
        //拼接sql
        for(SynlteFieldObject data : synlteFieldObjectList){
            sql.append(jointSql(data));
        }
        log.info("sql语句为:{}",sql.toString());
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(sql.toString().getBytes("UTF-8"));
            buff.flush();
        }catch (Exception e) {
            log.info("下载数据元sql信息出错");
        }finally {
            try {
                buff.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outStr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageInfo<relationTableInfo> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId,String searchName) {
        log.info("开始查询关联表信息");
        if((pageIndex!=0) && (pageSize!=0)){
            PageHelper.startPage(pageIndex,pageSize);
        }
        if(StringUtils.isBlank(searchName)){
            searchName = null;
        }
            List<relationTableInfo> tableNameList = synlteFieldDao.getAllTableNameByFieldId(fieldId,searchName);
        log.info("查询关联表信息结束");
        return new PageInfo<>(tableNameList);
    }

    @Override
    public List<relationTableInfo> getAllTableNameByFieldId(String fieldId) {
        log.info("开始查询关联表信息");
        if(StringUtils.isBlank(fieldId)){
            throw new NullPointerException("传递的参数为空");
        }
        List<relationTableInfo> tableNameList = synlteFieldDao.getAllTableNameByFieldId(fieldId, null);
        return tableNameList;
    }

    private HttpServletResponse setServletResponse(HttpServletResponse response,String fileName){
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename*=utf-8''" +fileName);
        return response;
    }

    private StringBuffer jointSql(SynlteFieldObject data){
        String databaseType = env.getProperty("database.type");
        StringBuffer baseSql = new StringBuffer();
        if(Common.DAMENG.equalsIgnoreCase(databaseType)){
            baseSql =  new StringBuffer("insert into synlte.synltefield(FIELDID,FIELDNAME,FIELDCHINESENAME,FIELDDESCRIBE," +
                    "FIELDTYPE,FIELDLEN,DEFAULTVALUE,MEMO,COLUMNNAME,CODEID,SAMEID,IS530NEW,FIELDLEN2,FIELD_CLASS," +
                    "SECRET_CLASS,SIM_CHINESE,\"VERSIONS\",RELEASEDATE,SYNONY_NAME,OBJECT_TYPE,EXPRESSION_WORD,CODEID_DETAIL," +
                    "ISNORMAL,RELATE,UNIT,FUSE_TYPE,FUSE_FIELDID,SUB_ORG,SUB_AUTHOR,ON_DATE,REG_ORG,FIELD_STANDARD,FACTURER," +
                    "\"CONTEXT\",\"CONSTRAINT\",CREATETIME,UPDATETIME,CREATOR,UPDATER,STATUS,FULL_CHINSEE,ATTRIBUTE_WORD,GADSJ_FIELDID)\n" +
                    "values('");
        }else {
            baseSql =  new StringBuffer("insert into synlte.synltefield(FIELDID,FIELDNAME,FIELDCHINESENAME,FIELDDESCRIBE," +
                    "FIELDTYPE,FIELDLEN,DEFAULTVALUE,MEMO,COLUMNNAME,CODEID,SAMEID,IS530NEW,FIELDLEN2,FIELD_CLASS," +
                    "SECRET_CLASS,SIM_CHINESE,VERSIONS,RELEASEDATE,SYNONY_NAME,OBJECT_TYPE,EXPRESSION_WORD,CODEID_DETAIL," +
                    "ISNORMAL,RELATE,UNIT,FUSE_TYPE,FUSE_FIELDID,SUB_ORG,SUB_AUTHOR,ON_DATE,REG_ORG,FIELD_STANDARD,FACTURER," +
                    "CONTEXT,CONSTRAINT,CREATETIME,UPDATETIME,CREATOR,UPDATER,STATUS,FULL_CHINSEE,ATTRIBUTE_WORD,GADSJ_FIELDID)\n" +
                    "values('");
        }
//        StringBuffer baseSql =  new StringBuffer("insert into synlte.synltefield(FIELDID,FIELDNAME,FIELDCHINESENAME,FIELDDESCRIBE," +
//                "FIELDTYPE,FIELDLEN,DEFAULTVALUE,MEMO,COLUMNNAME,CODEID,SAMEID,IS530NEW,FIELDLEN2,FIELD_CLASS," +
//                "SECRET_CLASS,SIM_CHINESE,VERSIONS,RELEASEDATE,SYNONY_NAME,OBJECT_TYPE,EXPRESSION_WORD,CODEID_DETAIL," +
//                "ISNORMAL,RELATE,UNIT,FUSE_TYPE,FUSE_FIELDID,SUB_ORG,SUB_AUTHOR,ON_DATE,REG_ORG,FIELD_STANDARD,FACTURER," +
//                "CONTEXT,CONSTRAINT,CREATETIME,UPDATETIME,CREATOR,UPDATER,STATUS,FULL_CHINSEE,ATTRIBUTE_WORD,GADSJ_FIELDID)\n" +
//                "values('");

        baseSql.append(data.getFieldId()+"','"+data.getFieldName()+"','"+data.getFieldChineseName()+"','");
        baseSql.append(StringUtils.isNotBlank(data.getFieldDescribe())?(data.getFieldDescribe()+"',"):"',");
        baseSql.append((data.getFieldType() != null)?(data.getFieldType()+","):"null,");
        baseSql.append(StringUtils.isNotBlank(data.getFieldLen())?("'"+data.getFieldLen()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getDefaultValue())?("'"+data.getDefaultValue()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getMemo())?("'"+data.getMemo()+"',"):"'',");
        baseSql.append("'"+data.getColumnName()+"',");
        baseSql.append(StringUtils.isNotBlank(data.getCodeId())?("'"+data.getCodeId()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSameId())?("'"+data.getSameId()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getIs530New())?("'"+data.getIs530New()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldLen2())?("'"+data.getFieldLen2()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldClass())?("'"+data.getFieldClass()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSecretClass())?("'"+data.getSecretClass()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSimChinese())?("'"+data.getSimChinese()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getVersions())?("'"+data.getVersions()+"',"):"'',");
        baseSql.append((data.getReleaseDate() != null)?(data.getReleaseDate()+","):"null,");
        baseSql.append(StringUtils.isNotBlank(data.getSynonyName())?("'"+data.getSynonyName()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getObjectType())?("'"+data.getObjectType()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getExpressionWord())?("'"+data.getExpressionWord()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getCodeIdDetail())?("'"+data.getCodeIdDetail()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(String.valueOf(data.getIsNorMal()))?("'"+data.getIsNorMal()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getRelate())?("'"+data.getRelate()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getUnit())?("'"+data.getUnit()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFuseType())?("'"+data.getFuseType()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFuseFieldId())?("'"+data.getFuseFieldId()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSubOrg())?("'"+data.getSubOrg()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSubAuthor())?("'"+data.getSubAuthor()+"',"):"'',");
        if(data.getOnDate() != null){
            String onDate = DateUtil.formatDate(data.getOnDate(), DateUtil.DEFAULT_PATTERN_NEW_TIME);
            baseSql.append("to_date('"+ onDate+"','dd-MM-yyyy'),");
        }else{
            baseSql.append("'',");
        }
        baseSql.append(StringUtils.isNotBlank(data.getRegOrg())?("'"+data.getRegOrg()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(String.valueOf(data.getFieldStandard()))?("'"+data.getFieldStandard()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFacturer())?("'"+data.getFacturer()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getContext())?("'"+data.getContext()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getConstraint())?("'"+data.getConstraint()+"',"):"'',");
        baseSql.append("sysdate,"+"null,");

        baseSql.append(StringUtils.isNotBlank(data.getCreator())?("'"+data.getCreator()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getUpdater())?("'"+data.getUpdater()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getStatusNum())?("'"+data.getStatusNum()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFullChinese())?("'"+data.getFullChinese()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getAttributeWord())?("'"+data.getAttributeWord()+"',"):"'',");
        baseSql.append(StringUtils.isNotBlank(data.getGadsjFieldId())?("'"+data.getGadsjFieldId()+"');"):"'');");
        baseSql.append("\n");

        return baseSql;
    }


    private boolean checkSynlteFieldRule(SynlteFieldExcel synlteFieldExcel) {
        //判断必填项是否为空
        if(StringUtils.isBlank(synlteFieldExcel.getFieldId()) && StringUtils.isBlank(synlteFieldExcel.getFieldName())
           && StringUtils.isBlank(synlteFieldExcel.getFieldChineseName())
           && StringUtils.isBlank(synlteFieldExcel.getFieldLen()) && StringUtils.isBlank(synlteFieldExcel.getStatusNum())
           && StringUtils.isBlank(synlteFieldExcel.getGadsjFieldId()) && StringUtils.isBlank(synlteFieldExcel.getColumnName())
           && StringUtils.isBlank(synlteFieldExcel.getFullChinese()) && StringUtils.isBlank(synlteFieldExcel.getVersions())
           && StringUtils.isBlank(String.valueOf(synlteFieldExcel.getReleaseDate())) && StringUtils.isBlank(synlteFieldExcel.getFieldClass())){
            return false;
        }
        return true;
    }

}
