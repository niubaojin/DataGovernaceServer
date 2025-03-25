package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.dao.master.StandardResourceManageDao;
import com.synway.datastandardmanager.dao.master.VersionManageDao;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.enums.*;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerCode;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldStatusEnum;
import com.synway.datastandardmanager.pojo.versionManage.*;
import com.synway.datastandardmanager.service.VersionManageService;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author obito
 * @version 1.0
 * @date
 */
@Service
@Slf4j
public class VersionManageServiceImpl implements VersionManageService {

    private static Logger logger = LoggerFactory.getLogger(VersionManageServiceImpl.class);

    @Autowired
    private VersionManageDao versionManageDao;

    @Autowired
    private StandardResourceManageDao standardResourceManageDao;

    @Autowired
    private ResourceManageDao resourceManageDao;


    @Override
    public List<ObjectVersionVo> searchAllObjectVersion(VersionManageParameter parameter) {
        //设置传递的参数
        VersionManageParameter newParameter = setVersionParameter(parameter);
        List objectVersionList = versionManageDao.searchObjectVersionTable(newParameter);
        //过滤起始日期 截止日期的数据
        List<Object> filterObjectVersionList = filterVersionList(objectVersionList, newParameter, "version");

        List<ObjectVersionVo> list = filterObjectVersionList.stream().map(d -> (ObjectVersionVo) d).collect(Collectors.toList());
        int num = 1;
        ArrayList<ObjectVersionVo> tableList = new ArrayList<>();
        for(ObjectVersionVo data :list){
            data.setNum(num++);
            tableList.add(data);
        }

        if(tableList.isEmpty()){
            logger.error("从数据库中获取到的数据为空");
        }
        return tableList;
    }

    @Override
    public List<SynlteFieldVersionVo> searchAllSynlteFieldVersion(VersionManageParameter parameter) {
        //设置传递的参数
        VersionManageParameter newParameter = setVersionParameter(parameter);
        List synlteFieldVersionList = versionManageDao.searchSynlteFieldVersionTable(newParameter);
        //过滤起始日期 截止日期的数据
        List<Object> filterSynlteFieldVersionList = filterVersionList(synlteFieldVersionList, newParameter, "version");
        List<SynlteFieldVersionVo> list = filterSynlteFieldVersionList.stream().map(d -> (SynlteFieldVersionVo) d).collect(Collectors.toList());
        int num = 1;
        ArrayList<SynlteFieldVersionVo> tableList = new ArrayList<>();
        for(SynlteFieldVersionVo data :list){
            data.setNum(num++);
            tableList.add(data);
        }
        if(tableList.isEmpty()){
            logger.error("从数据库中获取到的数据为空");
        }
        return tableList;
    }

    @Override
    public List<FieldDeterminerVersionVo> searchAllFieldDeterminerVersion(VersionManageParameter parameter) {
        //设置传递的参数
        VersionManageParameter newParameter = setVersionParameter(parameter);
        List fieldDeterminerVersionList = versionManageDao.searchFieldDeterminerVersionTable(newParameter);
        //过滤起始日期 截止日期的数据
        List<Object> filterFieldDeterminerVersionList = filterVersionList(fieldDeterminerVersionList, newParameter, "version");
        List<FieldDeterminerVersionVo> list = filterFieldDeterminerVersionList.stream().map(d ->
                                                            (FieldDeterminerVersionVo) d).collect(Collectors.toList());
        int num = 1;
        ArrayList<FieldDeterminerVersionVo> tableList = new ArrayList<>();
        for(FieldDeterminerVersionVo data :list){
            data.setNum(num++);
            tableList.add(data);
        }
        if(tableList.isEmpty()){
            logger.error("从数据库中获取到的数据为空");
        }
        return tableList;
    }

    @Override
    public StandardObjectManage searchOldObject(ObjectVersionVo objectVersionVo) {
        ObjectPojoTable oneObjectPojoTable = new ObjectPojoTable();
        StandardObjectManage standardObjectManage = new StandardObjectManage();
        log.info("查询标准历史信息的参数是{}",objectVersionVo);
        try{
            ObjectPojo objectPojo = versionManageDao.searchOldObjectById(objectVersionVo);
            if(objectPojo == null){
                throw new NullPointerException("查询出的数据有误或是参数异常");
            }
            // 获取输入和输出的对应关系
            List<InputObjectCreate> allInpuObjectList = standardResourceManageDao.getAllInputObject(objectPojo.getTableId());
            //翻译表状态、表所在数据库的类型、表类型、数据分级
            objectPojo.setObjectStateVo(ObjectStateType.getObjectStateType(objectPojo.getObjectState()));
            objectPojo.setStoreTypeVo(StoreType.getStoreType(objectPojo.getStoreType()));
            objectPojo.setDataTypeVo(ObjectDataType.getDataType(objectPojo.getDataType()));
            if(!StringUtils.isEmpty(objectPojo.getDataLevel())){
                objectPojo.setDataLevelVo(ObjectSecurityLevelType.getValueById("1_"+objectPojo.getDataLevel()));
            }
            // 拼接对应的数据 object 这个表里面存在的数据
            // objectId
            oneObjectPojoTable.setObjectId(String.valueOf(objectPojo.getObjectId()));
            // 数据名
            oneObjectPojoTable.setDataSourceName(objectPojo.getObjectName());
            // 真实表名
            oneObjectPojoTable.setRealTablename(objectPojo.getTableName());

            oneObjectPojoTable.setCodeTextTd(String.valueOf(objectPojo.getDataSource()));
            oneObjectPojoTable.setTableId(objectPojo.getTableId());
            // 存储表状态
            oneObjectPojoTable.setStorageTableStatus(objectPojo.getObjectStateVo());
            // 存储方式
            oneObjectPojoTable.setStorageDataMode(objectPojo.getStoreTypeVo());
            //更新表类型 20200507 majia添加
            oneObjectPojoTable.setIsActiveTable(objectPojo.getIsActiveTable());
            // 厂商 存储方式 存储的数据源
            List<String> outOobjSourceCodeList = new ArrayList<>();
            // 源表ID  sourceId 的值 20191118号新增需求
            oneObjectPojoTable.setSourceId(objectPojo.getSourceId());
            // 注释的字段信息
            if(!StringUtils.isBlank(objectPojo.getObjectMemo())){
                objectPojo.setObjectMemo("");
            }
            oneObjectPojoTable.setObjectMemo(objectPojo.getObjectMemo());
            //数据分级
            if(!StringUtils.isEmpty(objectPojo.getDataLevel()) && objectPojo.getDataLevel() != null){
                oneObjectPojoTable.setDataLevel(objectPojo.getDataLevel());
            }
            if(!StringUtils.isEmpty(objectPojo.getDataLevelVo()) && objectPojo.getDataLevelVo() != null){
                oneObjectPojoTable.setDataLevelCh(objectPojo.getDataLevelVo());
            }

            List<String> outOobjSourceList = new ArrayList<>();
            for(InputObjectCreate inputObjectCreate :allInpuObjectList){
                Integer outOobjSource = inputObjectCreate.getOutOobjSource();
                outOobjSourceList.add(ManufacturerName.getNameByIndex(outOobjSource));
                outOobjSourceCodeList.add(String.valueOf(outOobjSource));
            }
            if(outOobjSourceCodeList.size() >= 1){
                oneObjectPojoTable.setOwnerFactoryCode(outOobjSourceCodeList.get(0));
                oneObjectPojoTable.setOwnerFactory(outOobjSourceList.get(0));
            }else{
                oneObjectPojoTable.setOwnerFactoryCode("0");
                oneObjectPojoTable.setOwnerFactory("全部");
            }

            //  根据 codeTextTd的值获取对应的中文翻译
            if(StringUtils.isEmpty(oneObjectPojoTable.getCodeTextTd())){
                oneObjectPojoTable.setCodeTextCh("");
            }else{
                String sysChi = resourceManageDao.getSysChiName(oneObjectPojoTable.getCodeTextTd());
                if(StringUtils.isEmpty(sysChi)){
                    oneObjectPojoTable.setCodeTextCh("错误协议代码");
                }else{
                    oneObjectPojoTable.setCodeTextCh(sysChi);
                }

            }
            //  获取这个tableId在 数据组织,数据来源的分级分类信息。
            ObjectPojoTable classifyOne = resourceManageDao.getClassifyByTableid(oneObjectPojoTable.getTableId());
            if(classifyOne != null){
                oneObjectPojoTable.setOrganizationClassify(classifyOne.getOrganizationClassify());
                oneObjectPojoTable.setSourceClassify(classifyOne.getSourceClassify());
                oneObjectPojoTable.setClassIds(classifyOne.getClassIds());
                oneObjectPojoTable.setSourceClassIds(classifyOne.getSourceClassIds());
            }else{
                oneObjectPojoTable.setOrganizationClassify("未知/未知");
                oneObjectPojoTable.setSourceClassify("未知/未知");
                oneObjectPojoTable.setClassIds("");
                oneObjectPojoTable.setSourceClassIds("");
            }
            if(!StringUtils.isEmpty(objectPojo.getCreateTime())){
                oneObjectPojoTable.setCreateTime(objectPojo.getCreateTime());
            }
            if(!StringUtils.isEmpty(objectPojo.getCreator())){
                oneObjectPojoTable.setCreator(objectPojo.getCreator());
            }
            if(!StringUtils.isEmpty(objectPojo.getUpdater())){
                oneObjectPojoTable.setUpdater(objectPojo.getUpdater());
            }


            log.info(JSONObject.toJSONString(oneObjectPojoTable));
            //状态翻译
//        objectPojoTable.setStorageTableStatus(ObjectStateType.getObjectStateType(Integer.valueOf(objectPojoTable.getStorageTableStatus())));

            //根据objectId 查询所对应的objectField信息
            List<ObjectField> objectFieldList = versionManageDao.searchOldField(oneObjectPojoTable.getObjectId());
            if(objectFieldList.size() == 0){
                log.error("查询出的表字段信息为空");
            }

            standardObjectManage.setObjectId(oneObjectPojoTable.getObjectId());
            standardObjectManage.setTableId(oneObjectPojoTable.getTableId());
            standardObjectManage.setObjectPojoTable(oneObjectPojoTable);
            standardObjectManage.setObjectFieldList(objectFieldList);
            log.info("返回页面的数据为{}",standardObjectManage);
        }catch (Exception e){
            log.error("获取对象详细信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return standardObjectManage;
    }


    @Override
    public SynlteFieldObject searchOldSynlteField(SynlteFieldVersionVo synlteFieldVersionVo) {
        //查询数据元历史信息
        SynlteFieldObject oldSynlteField = versionManageDao.searchOldSynlteField(synlteFieldVersionVo);
        if(oldSynlteField == null){
            log.error("查询数据元历史信息出错");
        }
        oldSynlteField.setStatus(SynlteFieldStatusEnum.getValueById(oldSynlteField.getStatusNum()));
        return oldSynlteField;
    }

    @Override
    public FieldDeterminer searchOldFieldDeterminer(FieldDeterminerVersionVo fieldDeterminerVersionVo) {
        FieldDeterminer oldFieldDeterminer = versionManageDao.searchOldFieldDeterminer(fieldDeterminerVersionVo);
        if(oldFieldDeterminer == null){
            log.error("查询限定词历史信息出错");
        }
        oldFieldDeterminer.setDeterminerState(FieldDeterminerCode.getValueById(oldFieldDeterminer.getDeterminerStateNum()));
        oldFieldDeterminer.setDeterminerType(FieldDeterminerCode.getValueById("2_"+oldFieldDeterminer.getDeterminerTypeNum()));

        return oldFieldDeterminer;
    }

    @Override
    public FilterList searchVersionAndAuthor(String table) {
        if(StringUtils.isBlank(table)){
            log.error("传递的表名参数为空");
        }
        log.info("开始查询过滤信息:");
        List<FilterObject> versionsList = versionManageDao.searchVersionsList(table);
        versionsList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        List<FilterObject> authorList = versionManageDao.searchAuthorList(table);
        authorList.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        FilterList filterList = new FilterList();
        filterList.setVersionsList(versionsList);
        filterList.setAuthorList(authorList);
        log.info("结束查询过滤：{}",filterList);
        return filterList;
    }

    /**
     * 设置过滤的属性
     */
    private VersionManageParameter setVersionParameter(VersionManageParameter parameter){
        // 搜索内容如果存在空值，将其变为 null值
        if(StringUtils.isBlank(parameter.getSearchText())){
            parameter.setSearchText(null);
        }
        if(parameter.getVersionsList() == null || parameter.getVersionsList().isEmpty()){
            parameter.setVersionsList(null);
        }
        if(parameter.getAuthorList() == null || parameter.getAuthorList().isEmpty()){
            parameter.setAuthorList(null);
        }
        if(parameter.getSort() == null || parameter.getSort().isEmpty()){
            parameter.setSort("updateTime");
        }
        if(parameter.getSortOrder() == null || parameter.getSortOrder().isEmpty()){
            parameter.setSortOrder("desc");
        }
        return parameter;
    }

    /**
     * 过滤数据起始日期 截止日期的数据
     */
    private List<Object> filterVersionList(List<Object> list, VersionManageParameter parameter,
                                           String filterName){
        list = list.parallelStream().filter(d ->{
            try {
                Field field =  d.getClass().getDeclaredField(filterName);
                field.setAccessible(true);
                Integer dbTime = (Integer) field.get(d);
                String dbTimeStr = String.valueOf(dbTime);
                if(StringUtils.isNotBlank(parameter.getStartTimeText())){
                    if(StringUtils.isBlank(dbTimeStr)){
                        return false;
                    }else if(DateUtil.parseDate(dbTimeStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).before(DateUtil.parseDate(parameter.getStartTimeText(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))){
                        return false;
                    }
                }
                if(StringUtils.isNotBlank(parameter.getEndTimeText())){
                    if(StringUtils.isBlank(dbTime.toString())){
                        return false;
                    }else if(DateUtil.parseDate(dbTimeStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).after(DateUtil.parseDate(parameter.getEndTimeText(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))){
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                logger.error("数据筛选报错"+e.getMessage());
                return false;
            }
        }).collect(Collectors.toList());
        return list;
    }

}
