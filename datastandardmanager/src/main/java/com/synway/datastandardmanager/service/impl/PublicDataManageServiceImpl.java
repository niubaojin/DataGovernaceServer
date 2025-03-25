package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.dao.master.PublicDataManageDao;
import com.synway.datastandardmanager.dao.master.SynlteFieldDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataField;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataFieldCode;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataPojo;
import com.synway.datastandardmanager.service.PublicDataManageService;
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

/**
 * @author obito
 * @version 1.0
 */
@Service
@Slf4j
public class PublicDataManageServiceImpl implements PublicDataManageService {

    @Autowired
    private PublicDataManageDao publicDataManageDao;

    @Autowired
    private SynlteFieldDao synlteFieldDao;

    @Autowired
    private ResourceManageAddService resourceManageAddServiceImpl;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static HashLock<String> HASH_LOCK = new HashLock<>();


    @Override
    public String addPublicData(PublicDataPojo publicDataPojo) throws Exception{
        if(StringUtils.isBlank(publicDataPojo.getId())){
            log.info("新建公共数据项时传递的参数为:{}",publicDataPojo);
            int name = publicDataManageDao.searchPublicDataGroupName(publicDataPojo.getGroupName());
            if(name != 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR,"公共数据项组名称重复");
            }
            int recno = 1;
            String uuid = UUIDUtil.getUUID();
            publicDataPojo.setId(uuid);
            int count = publicDataManageDao.addOnePublicDataGroup(publicDataPojo);
            log.info("添加公共数据项分组的条数为:{}",count);

            // 添加公共数据项数据后，将信息插入或更新到用户权限表 USER_AUTHORITY 中
            StandardObjectManage standardObjectManage = new StandardObjectManage();
            ObjectPojoTable objectPojoTable = new ObjectPojoTable();
            objectPojoTable.setDataSourceName(publicDataPojo.getMemo());
            standardObjectManage.setObjectPojoTable(objectPojoTable);
            standardObjectManage.setTableId(publicDataPojo.getGroupName());
            resourceManageAddServiceImpl.addUserAuthorityData(standardObjectManage);

            List<PublicDataField> publicDataFieldList = publicDataPojo.getPublicDataFieldList();
            if(publicDataFieldList == null || publicDataFieldList.isEmpty()){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"数据项的值为空，无法插入");
            }
            for(PublicDataField data: publicDataFieldList){
                data.setId(UUIDUtil.getUUID());
                data.setGroupId(uuid);
                data.setRecno(recno);
                recno++;
            }
            int i = publicDataManageDao.insertPublicDataFieldList(publicDataFieldList);
            log.info("添加公共数据项的条数为:{}",i);
            // 发送操作日志
            operateLogServiceImpl.publicDataManageSuccessLog(OperateLogHandleTypeEnum.ADD, "公共数据项管理", publicDataPojo.getGroupName());

            return "添加成功";
        }else {
                HASH_LOCK.lock(publicDataPojo.getId());
                int recno = 1;
                try {
                    int updateCount = publicDataManageDao.updatePublicDataGroup(publicDataPojo);
                    log.info("更新了公共数据项组:{}",updateCount);
                    List<PublicDataField> publicDataFieldList = publicDataPojo.getPublicDataFieldList();
                    int deleteCount = publicDataManageDao.deletePublicDataFieldById(publicDataPojo.getId());
                    log.info("删除的公共数据项条数为:{}",deleteCount);
                    if(!publicDataFieldList.isEmpty() && publicDataFieldList != null){
                        for(PublicDataField data: publicDataFieldList){
                            data.setId(UUIDUtil.getUUID());
                            data.setGroupId(publicDataPojo.getId());
                            data.setRecno(recno);
                            recno++;
                        }
                        int insertCount = publicDataManageDao.insertPublicDataFieldList(publicDataFieldList);
                        log.info("插入的公共数据项条数为:{}",insertCount);
                        // 发送操作日志
                        operateLogServiceImpl.publicDataManageSuccessLog(OperateLogHandleTypeEnum.ALTER, "公共数据项管理", publicDataPojo.getGroupName());
                    }
                }finally {
                    HASH_LOCK.unlock(publicDataPojo.getId());
                }
        }

        return "更新成功";
    }

    @Override
    public String deleteOnePublicDataGroup(String id,String groupName) {
        log.info("开始删除STANDARDIZE_PUBLIC_DATA表的组织名称为:{}的数据",groupName);
        int deletePublicDataFieldCount = publicDataManageDao.deletePublicDataFieldById(id);
        int deleteCount = publicDataManageDao.deletePublicDataGroupById(id, groupName);
        log.info("删除的数据量为:{}",deleteCount);
        // 发送操作日志
        operateLogServiceImpl.publicDataManageSuccessLog(OperateLogHandleTypeEnum.DELETE, "公共数据项管理", groupName);
        return "数据删除成功";
    }

    @Override
    public String updateOnePublicDataGroup(PublicDataPojo publicDataPojo) {
        log.info("需要编辑的公共数据项组的数据为:{}",publicDataPojo);
        int tableCount = publicDataManageDao.searchPublicDataById(publicDataPojo.getId());
        if(tableCount == 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"数据项分组["+publicDataPojo.getGroupName()
                    +"]在数据库中对应的数据不存在，更新失败");
        }
        int count = publicDataManageDao.updatePublicDataGroup(publicDataPojo);
        log.info("更新的公共数据项组数据的条数为:{}",count);

        List<PublicDataField> publicDataFieldList = publicDataPojo.getPublicDataFieldList();
        if(!publicDataFieldList.isEmpty() && publicDataFieldList != null){
            for(PublicDataField data : publicDataFieldList){
                if(data.getUpdateStatus() == 1){
                    publicDataManageDao.insertPublicDataField(data);
                }else{
                    publicDataManageDao.updatePublicDataField(data);
                }
            }
        }
        return "数据更新成功";
    }

    @Override
    public List<PageSelectOneValue> searchFieldChineseList(String searchText) {
        if(StringUtils.isBlank(searchText)){
            searchText = null;
        }
        List<PageSelectOneValue> resultList = publicDataManageDao.searchFieldChineseList(searchText);
        if(resultList == null || resultList.isEmpty()){
            return new ArrayList<>();
        }
        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                .compare(s2.getLabel(), s1.getLabel()))
                .limit(100).collect(Collectors.toList());

        return resultList;
    }

    @Override
    public PublicDataPojo searchPublicDataByGroupName(String groupName) {
        log.info("查询的公共数据项组名称为:{}",groupName);
        PublicDataPojo publicDataPojo = publicDataManageDao.searchPublicDataByName(groupName);
        if(StringUtils.isBlank(publicDataPojo.getId())){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"数据项分组["+groupName
                    +"]在数据库中对应的数据不存在，查询失败");
        }
        List<PublicDataField> publicDataFields = publicDataManageDao.searchPublicDataFieldById(publicDataPojo.getId());
        publicDataFields.stream().forEach(d -> {
            if(d.getMd5Index() != null && d.getMd5Index() != 0){
                d.setMd5IndexStatus(true);
            }
//            SynlteFieldObject synlteFieldObject = synlteFieldDao.searchSynlteFieldById(d.getFieldId());
//            if(synlteFieldObject != null){
//                d.setGadsjFieldId(synlteFieldObject.getGadsjFieldId());
//                d.setLabel(synlteFieldObject.getFieldChineseName());
//                d.setColumnName(synlteFieldObject.getColumnName());
//                d.setFieldLen(synlteFieldObject.getFieldLen());
//                d.setFieldType(synlteFieldObject.getFieldType() != null ?String.valueOf(synlteFieldObject.getFieldType()):"");
//            }
        });
        if(publicDataFields.isEmpty() || publicDataFields == null){
            List<PublicDataField> tempList = new ArrayList<>();
            publicDataPojo.setPublicDataFieldList(tempList);
        }
        publicDataPojo.setPublicDataFieldList(publicDataFields);
        return publicDataPojo;
    }

    @Override
    public List<PageSelectOneValue> searchGroupNameList() {
        log.info("开始查询公共数据项分组列表");
        List<PageSelectOneValue> resultList = publicDataManageDao.searchGroupNameList();
        if(resultList.isEmpty() || resultList == null){
            return new ArrayList<>();
        }
        log.info("查询公共数据项分组列表的信息结束");
        return resultList;
    }

    @Override
    public void downloadPublicDataFieldExcel(HttpServletResponse response, List<PublicDataField> elementList, String name, Object object) {
//        ZipOutputStream zipOutputStream = null;
        try{
//            response.setContentType("application/octet-stream");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            for(PublicDataField data : elementList){
                data.setIsQueryStr(PublicDataFieldCode.getValueById("1_"+data.getIsQuery()));
                data.setIsContorlStr(PublicDataFieldCode.getValueById("1_"+data.getIsContorl()));
                data.setNeedValueStr(PublicDataFieldCode.getValueById("1_"+data.getNeedValue()));
                data.setMd5IndexStr((data.getMd5Index() != null && data.getMd5Index() != 0) ? "是":"否");
            }
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("公共数据项管理").doWrite(elementList);
//            ServletOutputStream outputStream = response.getOutputStream();
//            zipOutputStream = new ZipOutputStream(outputStream);
//
//            ZipEntry zipEntry = new ZipEntry(name);
//            zipOutputStream.putNextEntry(zipEntry);
//            EasyExcel.write(zipOutputStream, object.getClass()).sheet("公共数据项管理").doWrite(elementList);


        }catch (Exception e){
            log.error("下载公共数据项报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<PublicDataField> importPublicDataFieldExcel(MultipartFile file,String id) {
        log.info("=============开始将文件{}导入到数据库中======",file.getName());
        ExcelListener<PublicDataField> listener = new ExcelListener<>();
        List<PublicDataField> list = new ArrayList<>();
        List<PublicDataField> failedList = new ArrayList<>();
        List<PublicDataField> successList = new ArrayList<>();
        try{
            list = EasyExcelUtil.readExcelUtil(file,new PublicDataField(),listener);
            int i = 1;
            // 这里插入每一行数据，需要验证elementcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                PublicDataField elementManage = (PublicDataField) iterator.next();
                try {
                    PublicDataField addPublicDataFieldManage = new PublicDataField();
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(addPublicDataFieldManage, elementManage);
                    addPublicDataFieldManage.setIsQuery((StringUtils.isNotBlank(addPublicDataFieldManage.getIsQueryStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getIsQueryStr())) ? 1 : 0);
                    addPublicDataFieldManage.setIsContorl((StringUtils.isNotBlank(addPublicDataFieldManage.getIsContorlStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getIsContorlStr())) ? 1 : 0);
                    addPublicDataFieldManage.setNeedValue((StringUtils.isNotBlank(addPublicDataFieldManage.getNeedValueStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getNeedValueStr())) ? 1 : 0);
                    if(StringUtils.isNotBlank(addPublicDataFieldManage.getMd5IndexStr())
                            && "是".equalsIgnoreCase(addPublicDataFieldManage.getMd5IndexStr())){
                        addPublicDataFieldManage.setMd5Index(i);
                        i++;
                    }else{
                        addPublicDataFieldManage.setMd5Index(null);
                    }
                    successList.add(addPublicDataFieldManage);
                    iterator.remove();
                } catch (Exception e) {
                    log.error("数据项" + elementManage.getFieldChineseName() + "插入失败" + e.getMessage());
                }
            }
        }catch (Exception e){
            log.error("导入文件报错"+ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException("导入文件报错"+e.getMessage());
        }
        log.info("导入成功的数量为："+successList.size() +"导入失败的数量为：" +failedList);
        return successList;
    }

    private Boolean checkImportElement(String id,String addId,PublicDataField publicDataField){
        if("add".equalsIgnoreCase(id)){
            String uuid = UUIDUtil.getUUID();
            publicDataField.setGroupId(uuid);
            return true;
        }else {
            int groupCount = publicDataManageDao.searchPublicDataById(id);
            if(groupCount == 1){
                publicDataField.setGroupId(id);
                return true;
            }
        }
        return false;
    }

}
