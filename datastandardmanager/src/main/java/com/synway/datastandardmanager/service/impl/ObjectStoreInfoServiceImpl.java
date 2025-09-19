package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.ObjectStoreInfoDao;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.enums.SysCodeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.ExternalInterfce.RegisterTableInfo;
import com.synway.datastandardmanager.pojo.buildtable.*;
import com.synway.datastandardmanager.pojo.enums.FieldType;
import com.synway.datastandardmanager.pojo.enums.OrganizationClassification;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.pojo.warehouse.ProjectInfo;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 是否自动入库，如果大数据平台(hive/odps) 则只能入库一个
 * @author
 * @ClassName ObjectStoreInfoServiceImpl
 * @date 2021/1/27 15:45
 */
@Service
public class ObjectStoreInfoServiceImpl implements ObjectStoreInfoService {
    private static Logger logger= LoggerFactory.getLogger(ObjectStoreInfoServiceImpl.class);
    @Autowired
    private ObjectStoreInfoDao objectStoreInfoDao;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    private ResourceManageDao resourceManageDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @javax.annotation.Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private  static ReentrantLock lock = new ReentrantLock();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveObjectStoreInfoFromAliyun(BuildTableInfoVo buildTableInfoVo) {
        if(Common.ODPS.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.ADS.equalsIgnoreCase(buildTableInfoVo.getDsType())){
            ObjectStoreInfo objectStoreInfo = new ObjectStoreInfo();
            //查询建表信息是否存在
            String objectStoreInfoId = objectStoreInfoDao.searchObjectStoreInfoId(buildTableInfoVo.getTableName(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getDataId());
            String uuid = UUIDUtil.getUUID();
            if(StringUtils.isNotBlank(objectStoreInfoId)){
                objectStoreInfo.setTableInfoId(objectStoreInfoId);
            }else{
                objectStoreInfo.setTableInfoId(uuid);
            }
            objectStoreInfo.setTableId(buildTableInfoVo.getTableId());
            objectStoreInfo.setTableName(buildTableInfoVo.getTableName());
            objectStoreInfo.setObjectName(buildTableInfoVo.getTableNameCH());
            objectStoreInfo.setStoreType(getStoreType(buildTableInfoVo.getDsType(),buildTableInfoVo.getSchema()));
            objectStoreInfo.setProjectName(buildTableInfoVo.getSchema());
            objectStoreInfo.setMemo(buildTableInfoVo.getCreateTableMemo());
            objectStoreInfo.setCreater(buildTableInfoVo.getUserName());
            objectStoreInfo.setCreaterId(buildTableInfoVo.getUserId());
            //存储分区信息、是否实时表、生命周期
            Integer isActiveTable = buildTableInfoVo.getIsActiveTable() == null || StringUtils.isBlank(buildTableInfoVo.getIsActiveTable()) ? 1 : Integer.valueOf(buildTableInfoVo.getIsActiveTable());
            objectStoreInfo.setIsActiveTable(isActiveTable);
            //必填项 默认是分区表 0
            if (buildTableInfoVo.getIsPartition() == null){
                buildTableInfoVo.setIsPartition(0);
            }
            objectStoreInfo.setIsPartition(buildTableInfoVo.getIsPartition());
            objectStoreInfo.setPartitionType(0 == buildTableInfoVo.getIsPartition() ? buildTableInfoVo.getPartitionType() : null);
            objectStoreInfo.setPartitionCount(buildTableInfoVo.getPartitionCount());
            objectStoreInfo.setLifeCycle(buildTableInfoVo.getLifeCycle());
            objectStoreInfo.setDataId(StringUtils.isNotBlank(buildTableInfoVo.getDataId())?buildTableInfoVo.getDataId():" ");
            objectStoreInfo.setTableCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
            // 先查询数据库中，如果数据库中这个表的数据量(IMPORT_FLAG = 1)为0，则这个值为1，如果为1 ，则该值为0
            int tableCount = objectStoreInfoDao.queryCountByTableName(objectStoreInfo.getStoreType(),objectStoreInfo.getTableName());
            objectStoreInfo.setImportFlag(tableCount > 0 ? 0 : 1);
            if (objectStoreInfo.getStoreType() == 2 || objectStoreInfo.getStoreType() == 3){
                objectStoreInfo.setImportFlag(0);
            }
            // 可能在插入时数据库中已经存在了这一条数据
            logger.info("表存储的信息为："+JSONObject.toJSONString(objectStoreInfo));
            int flag = objectStoreInfoDao.saveObjectStoreInfo(objectStoreInfo,1);
            logger.info("该条记录插入到数据库成功条数:"+flag);

            logger.info("开始将字段信息插入object_store_fieldInfo表中");
            //2023-12-05 将字段信息插入object_store_fieldInfo表中
            insertObjectStoreFieldinfoByCreateTable(buildTableInfoVo, objectStoreInfo.getTableInfoId());
        }else{
            logger.info("数据库类型为"+buildTableInfoVo.getDsType()+"不需要将数据插入到表OBJECT_STORE_INFO中");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveObjectStoreInfoFromHuaWei(BuildTableInfoVo buildTableInfoVo) {
        if(Common.HBASECDH.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.HBASEHUAWEI.equalsIgnoreCase(buildTableInfoVo.getDsType())||
                Common.HIVECDH.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.HIVEHUAWEI.equalsIgnoreCase(buildTableInfoVo.getDsType())||
                Common.CLICKHOUSE.equalsIgnoreCase(buildTableInfoVo.getDsType())){
            ObjectStoreInfo objectStoreInfo = new ObjectStoreInfo();
            //查询建表信息是否存在
            String objectStoreInfoId = objectStoreInfoDao.searchObjectStoreInfoId(buildTableInfoVo.getTableName(), buildTableInfoVo.getProjectName(),
                    buildTableInfoVo.getDataId());
            String uuid = UUIDUtil.getUUID();
            if(StringUtils.isNotBlank(objectStoreInfoId)){
                objectStoreInfo.setTableInfoId(objectStoreInfoId);
            }else{
                objectStoreInfo.setTableInfoId(uuid);
            }
            objectStoreInfo.setTableId(buildTableInfoVo.getTableId());
            objectStoreInfo.setTableName(buildTableInfoVo.getTableName());
            objectStoreInfo.setObjectName(buildTableInfoVo.getTableNameCH());
            objectStoreInfo.setStoreType(getStoreType(buildTableInfoVo.getDsType(),buildTableInfoVo.getProjectName()));
            if(StringUtils.isBlank(buildTableInfoVo.getProjectName())){
                objectStoreInfo.setProjectName(buildTableInfoVo.getSchemaName());
            }else {
                objectStoreInfo.setProjectName(buildTableInfoVo.getProjectName());
            }
            objectStoreInfo.setMemo(buildTableInfoVo.getTableNameCH());
            objectStoreInfo.setCreater(buildTableInfoVo.getUserName());
            objectStoreInfo.setCreaterId(buildTableInfoVo.getUserId());
            //存储分区信息、是否实时表、生命周期
            //建表过来的实时表默认为1
            objectStoreInfo.setIsActiveTable(1);
            //必填项 默认是分区表 0
            if (buildTableInfoVo.getIsPartition() == null){
                buildTableInfoVo.setIsPartition(0);
            }
            objectStoreInfo.setIsPartition(buildTableInfoVo.getIsPartition());
            if(0 == buildTableInfoVo.getIsPartition()){
                objectStoreInfo.setPartitionType(buildTableInfoVo.getPartitionType());
            }else {
                objectStoreInfo.setPartitionType(null);
            }
            objectStoreInfo.setPartitionCount(buildTableInfoVo.getPartitionCount());
            objectStoreInfo.setLifeCycle(buildTableInfoVo.getLifeCycle() == null?0:buildTableInfoVo.getLifeCycle());
            objectStoreInfo.setTableCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
            int tableCount = objectStoreInfoDao.queryCountByTableName(objectStoreInfo.getStoreType(),objectStoreInfo.getTableName());
            objectStoreInfo.setImportFlag(tableCount > 0 ? 0 : 1);
            objectStoreInfo.setDataId(StringUtils.isNotBlank(buildTableInfoVo.getDataId())?buildTableInfoVo.getDataId():" ");
            // 可能在插入时数据库中已经存在了这一条数据
            logger.info("表存储的信息为："+JSONObject.toJSONString(objectStoreInfo));
            int flag = objectStoreInfoDao.saveObjectStoreInfo(objectStoreInfo,1);
            logger.info("该条记录插入到数据库成功条数"+flag);

            logger.info("开始将字段信息插入object_store_fieldInfo表中");
            //2023-12-05 将字段信息插入object_store_fieldInfo表中
            insertObjectStoreFieldinfoByCreateTable(buildTableInfoVo, objectStoreInfo.getTableInfoId());
        }else{
            logger.info("数据库类型为"+buildTableInfoVo.getDsType()+"不需要将数据插入到表OBJECT_STORE_INFO中");
        }
    }

    public void insertObjectStoreFieldinfoByCreateTable(BuildTableInfoVo buildTableInfoVo, String tableInfoId){
        // 查询标准表信息
        ObjectPojo targetObjectPojo = resourceManageDao.searchObjectInfo(buildTableInfoVo.getTableName());
        if (targetObjectPojo == null){
            logger.info(String.format("表：%s，在object没有记录，略过",buildTableInfoVo.getTableName()));
            return;
        }
        // 构建字段信息
        List<ObjectField> standardObjectFields = resourceManageDao.searchObjectFieldByTableId(targetObjectPojo.getTableId());
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        buildTableInfoVo.getColumnData().stream().forEach( d -> {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setNo(d.getRecno());
            fieldInfo.setFieldName(d.getColumnName());
            fieldInfo.setType(d.getFieldType());
            fieldInfo.setLength(d.getFieldLen());
            if (d.getPartitionRecno() != null){
                if (d.getPartitionRecno() == 1){
                    fieldInfo.setPartitionLevel("一级分区");
                }else if (d.getPartitionRecno() == 2){
                    fieldInfo.setPartitionLevel("二级分区");
                }
            }
            fieldInfoList.add(fieldInfo);
        });
        List<ObjectStoreFieldInfo> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(standardObjectFields, fieldInfoList, tableInfoId, new DetectedTable());
        //保存信息
        //使用编程式事务，对表和字段的保存进行事务管理
        transactionTemplate.execute(transactionStatus -> {
            if (objectStoreFieldInfoList.size()>0){
                objectStoreInfoDao.deleteObjectStoreFieldInfo(tableInfoId);
                objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
            }
            return Boolean.TRUE;
        });
    }

    /**如果 updateFlag = 2 是否自动更新选择的是 1 则 其它的需要是 0
     * @param data
     * @param updateFlag 1:更新时间  2：更新自动更新
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateColumnToInfo(SaveColumnComparision data, int updateFlag) throws Exception{
        ObjectStoreInfo objectStoreInfo = new ObjectStoreInfo();
        objectStoreInfo.setTableId(data.getTableId());
        objectStoreInfo.setTableName(data.getCreatedTableData().getTableName());
        String objectName = StringUtils.isNotBlank(data.getCreatedTableData().getTableNameCh()) ? data.getCreatedTableData().getTableNameCh() : data.getCreatedTableData().getTableName();
        objectStoreInfo.setObjectName(objectName);
        objectStoreInfo.setStoreType(getStoreType(data.getCreatedTableData().getTableBase(),data.getCreatedTableData().getTableProject()));
        objectStoreInfo.setProjectName(data.getCreatedTableData().getTableProject());
        objectStoreInfo.setMemo(" ");
        objectStoreInfo.setCreater(data.getCreatedTableData().getCreateUser());
        objectStoreInfo.setCreaterId(data.getUserId());
        objectStoreInfo.setTableCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
        objectStoreInfo.setTableModTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
        objectStoreInfo.setImportFlag(data.getCreatedTableData().getImportFlag());
        // 注入数据源id、tableInfoId、用户信息
        objectStoreInfo.setDataId(data.getCreatedTableData().getDataId());
        objectStoreInfo.setTableInfoId(StringUtils.isNotBlank(objectStoreInfo.getTableInfoId()) ? objectStoreInfo.getTableInfoId() : UUIDUtil.getUUID());
        objectStoreInfo.setCreater(data.getCreatedTableData().getCreateUser());
        objectStoreInfo.setCreaterId(data.getUserId());
        objectStoreInfo.setIsPartition(0);
        objectStoreInfo.setSearchFlag(0);
        // 可能在插入时数据库中已经存在了这一条数据
        logger.info("表存储的信息为："+JSONObject.toJSONString(objectStoreInfo));
        if(2 == updateFlag){
            logger.info("将表名"+objectStoreInfo.getTableName()+"中对应的是否入库修改成：" + objectStoreInfo.getImportFlag());
            objectStoreInfoDao.updateImportFlagByName(objectStoreInfo.getStoreType(),objectStoreInfo.getTableName(),objectStoreInfo.getDataId(),objectStoreInfo.getImportFlag());
        }
        if(1 == updateFlag){
            int flag = objectStoreInfoDao.updateObjectStoreInfoTime(objectStoreInfo,updateFlag);
            logger.info("更新字段后插入到数据库成功条数"+flag);
        }
    }

    @Override
    public void refreshCreateTable(RefreshCreatedPojo refreshCreatedPojo) throws Exception {
        long startTime = System.currentTimeMillis();
        // 如果刷新数据  就是从数据仓库中获取指定表的数据，然后再把数据插入到指定表中
        // 20201130 需要从数据仓库接口获取所有的已建表信息
        List<DetectedTable> tableImformations = restTemplateHandle.getTableImformationList();
        if(tableImformations != null && !tableImformations.isEmpty()){
            // 删除object_store_fieldInfo中tableInfoId不存在于object_store_info中的无效数据
            objectStoreInfoDao.deleteObjectStoreFieldInfos();
            // 查询标准信息
            List<ObjectInfo> tableNameList = objectStoreInfoDao.getAllTableNameByObject();
            Map<String,List<ObjectInfo>> map =  tableNameList.stream().filter(d-> StringUtils.isNotBlank(d.getTableName()))
                    .collect(Collectors.groupingBy(d -> d.getTableName().toUpperCase()));
            tableImformations = tableImformations.stream().filter(d -> StringUtils.isNotBlank(d.getTableNameEN()) &&
                    map.containsKey(d.getTableNameEN().toUpperCase())).distinct().collect(Collectors.toList());
            // 先更新插入这个数据
            Map<String,Long> tableMapCount = tableImformations.stream().collect(
                    Collectors.groupingBy(DetectedTable::getTableNameEN,Collectors.counting())
            );
            // object_store_info已有数据列表
            List<ObjectStoreInfo> objectStoreInfoList = objectStoreInfoDao.searchAllData();
            List<String> objectStoreFieldInfoTableInfoId = objectStoreInfoDao.getFieldInfoTableInfoId();
            tableImformations.stream().forEach(item -> {
                List<ObjectInfo> data = map.getOrDefault(item.getTableNameEN().toUpperCase(),null);
                if(data != null && !data.isEmpty()){
                    //根据该表的英文名、项目空间名、数据源dataId去查询是否有该表
                    logger.info("开始查询object_store_info是否有该记录");
                    ObjectStoreInfo objectStoreColumn = objectStoreInfoList.stream().filter(d ->
                            StringUtils.equalsIgnoreCase(d.getTableName(), item.getTableNameEN()) &&
                                    StringUtils.equalsIgnoreCase(d.getProjectName(), item.getProjectName()) &&
                                    StringUtils.equalsIgnoreCase(d.getDataId(), item.getResId())).findFirst().orElse(null);
                    if(objectStoreColumn == null){
                        //新增 生成object_store_info
                        logger.info("开始新增object_store_info");
                        String infoId = UUIDUtil.getUUID();
                        ObjectStoreInfo objectStoreInfo = getObjectStore(item,refreshCreatedPojo,tableMapCount,
                                data.get(0).getTableId(),data.get(0).getTableName(),infoId);
                        //根据tableId查询objectField
                        List<ObjectField> objectFields = resourceManageDao.searchObjectFieldByTableId(data.get(0).getTableId());
                        List<ObjectStoreFieldInfo> objectStoreFieldInfoList = getObjectStoreFieldInfo(item,infoId,objectFields);
                        //保存信息
                        //使用编程式事务，对表和字段的保存进行事务管理
                        transactionTemplate.execute(transactionStatus -> {
                            objectStoreInfoDao.saveObjectStoreInfo(objectStoreInfo,2);
                            if (objectStoreFieldInfoList.size()>0){
                                objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                            }
                            return Boolean.TRUE;
                        });
                    }else{
                        logger.info("开始更新object_store_info");
                        ObjectStoreInfo objectStoreInfo = getObjectStore(item,refreshCreatedPojo,tableMapCount,
                                data.get(0).getTableId(),data.get(0).getTableName(),objectStoreColumn.getTableInfoId());
                        //根据tableId查询objectField
                        List<ObjectStoreFieldInfo> objectStoreFieldInfoList = new ArrayList<>();
                        if (!objectStoreFieldInfoTableInfoId.contains(objectStoreColumn.getTableInfoId())){
                            List<ObjectField> objectFields = resourceManageDao.searchObjectFieldByTableId(data.get(0).getTableId());
                            objectStoreFieldInfoList = getObjectStoreFieldInfo(item,objectStoreColumn.getTableInfoId(),objectFields);
                        }
                        //保存信息
                        //使用编程式事务，对表和字段的保存进行事务管理
                        List<ObjectStoreFieldInfo> finalObjectStoreFieldInfoList = objectStoreFieldInfoList;
                        transactionTemplate.execute(transactionStatus -> {
                            objectStoreInfoDao.saveObjectStoreInfo(objectStoreInfo,2);
                            if (finalObjectStoreFieldInfoList.size()>0){
                                objectStoreInfoDao.saveObjectStoreFieldInfo(finalObjectStoreFieldInfoList);
                            }
                            return Boolean.TRUE;
                        });
                    }
                }
            });
            long uploadFinish = System.currentTimeMillis();
            logger.info("入库完成，总用时：" + ((uploadFinish - startTime) / 1000 / 60) + "分钟");

//            // 删除操作屏蔽（姚秀梅20221014）
//            // 然后再删除掉不存在的数据
//            logger.info("开始删除掉数据库中不存在表的数据");
//            List<String> oldTableNameList = objectStoreInfoDao.getOldTableName(refreshCreatedPojo.getTableName());
//            if(oldTableNameList != null){
//                List<String> newTableList = tableImformations.stream().map(d -> (d.getProjectName()+"."+d.getTableNameEN()).toUpperCase()).collect(Collectors.toList());
//                for(String tableNameEn:oldTableNameList){
//                    if(!newTableList.contains(tableNameEn)){
//                        objectStoreInfoDao.deleteOldTableName(tableNameEn);
//                    }
//                }
//            }
        }else{
            throw new NullPointerException("从数据仓库中获取表信息报错");
        }
    }

    @Override
    public void refreshAllCreateTable(RefreshCreatedPojo refreshCreatedPojo) throws Exception {
        if(lock == null){
            lock = new ReentrantLock();
        }
        boolean isLocked = lock.isLocked();
        if(isLocked){
            throw new NullPointerException("正在刷新全部的建表信息，请等待别的页面刷新结束后再点击该按钮");
        }
        lock.lock();
        try{
            logger.info("刷新全部的建表信息，开始加锁");
            refreshTable(refreshCreatedPojo);
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public int getImportFlagByTableName(StandardTableCreated stc) {
        int storeType = getStoreType(stc.getTableBase(),stc.getTableProject());
        Integer num = objectStoreInfoDao.getImportFlagByTableName(storeType,stc.getTableName(),stc.getTableProject(), stc.getDataId());
        if(num == null){
            return 0;
        }else{
            return num;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RegisterTableInfo> searchOrCreateObjectStoreInfo(List<RegisterTableInfo> registerTableInfos) throws Exception {
        if(registerTableInfos.isEmpty()){
            return new ArrayList<>();
        }
        List<RegisterTableInfo> createList = new ArrayList<>();
        List<RegisterTableInfo> responseList = new ArrayList<>();
        //查询出已经存在的objectStoreInfo
        logger.info("开始查询数据库中object_store_info存在的记录");
        registerTableInfos.stream().forEach(item ->{
            if (StringUtils.isBlank(item.getResourceId()) && StringUtils.isNotBlank(item.getTableType())){
                item.setTableType(SysCodeEnum.getCodeByNameAndType(item.getTableType(), "TABLETYPE"));
            }
        });
        List<RegisterTableInfo> searchObjectStoreInfoList = objectStoreInfoDao.searchObjectStoreInfoList(registerTableInfos);

        // 处理已存在和需要创建的新记录
        handleExistingAndNewRecords(searchObjectStoreInfoList, registerTableInfos, responseList, createList);
        createList = createList.stream().distinct().collect(Collectors.toList());

        // 处理需要创建的新记录
        for (RegisterTableInfo data : createList) {
            handleNewObjectStoreInfoCreation(data, responseList);
        }
        return responseList.stream().distinct().collect(Collectors.toUnmodifiableList());
    }
    // 处理已存在和需要创建的新记录
    private void handleExistingAndNewRecords(List<RegisterTableInfo> searchObjectStoreInfoList, List<RegisterTableInfo> registerTableInfos,
                                             List<RegisterTableInfo> responseList, List<RegisterTableInfo> createList) {
        if (!searchObjectStoreInfoList.isEmpty()) {
            for (RegisterTableInfo searchData : searchObjectStoreInfoList) {
                for (RegisterTableInfo d : registerTableInfos) {
                    if (d.getTableNameEn().equalsIgnoreCase(searchData.getTableNameEn()) &&
                            d.getProjectName().equalsIgnoreCase(searchData.getProjectName()) &&
                            d.getResourceId().equalsIgnoreCase(searchData.getResourceId()) &&
                            StringUtils.isNotBlank(searchData.getTableInfoId())) {
                        d.setTableInfoId(searchData.getTableInfoId());
                        responseList.add(d);
                    } else {
                        createList.add(d);
                    }
                }
            }
        } else {
            createList.addAll(registerTableInfos);
        }
    }
    // 处理创建新的对象存储信息
    private void handleNewObjectStoreInfoCreation(RegisterTableInfo data, List<RegisterTableInfo> responseList) throws Exception {
        // 查询为空时要创建新的object_store_info和object_store_fieldinfo信息
        // 查询仓库已对标的表信息
        List<DetectedTable> detectedTableList = restTemplateHandle.getProjectStandardTables(data.getResourceId());
        DetectedTable detectedTable = detectedTableList.stream().filter(d -> d.getTableNameEN().equalsIgnoreCase(data.getTableNameEn())).findFirst().orElse(null);

        //要回填数据源类型，所以要调用getResourceById接口
        DataResource dataResource = restTemplateHandle.getResourceById(data.getResourceId());
        detectedTableList.stream().forEach(d->{
            d.setResType(dataResource.getResType());
        });

        if (detectedTable == null) {
            throw new RuntimeException("调用仓库接口getProjectStandardTables返回的结果为空");
        }

        // 查询标准信息
        ObjectPojo objectInfo = resourceManageDao.selectObjectPojoByTableId(data.getTableId());

        // 生成新的object_store_info信息
        String newTableInfoId = UUIDUtil.getUUID();
        ObjectStoreInfo objectStoreInfo = createNewObjectStoreInfo(newTableInfoId, objectInfo, detectedTable, data.getTableId());

        // 插入object_store_info
        int insertCount = objectStoreInfoDao.saveObjectStoreInfo(objectStoreInfo, 1);
        if (insertCount < 0) {
            throw new SQLException("往object_store_info表插入失败");
        }

        // 调用仓库接口获取字段信息
        List<FieldInfo> fieldInfoList = restTemplateHandle.requestGetTableStructure(data.getResourceId(), data.getProjectName(), data.getTableNameEn());
        String tableInfoId = objectStoreInfo.getTableInfoId();
        List<ObjectField> standFieldList = resourceManageDao.selectObjectFieldByObjectId(objectInfo.getObjectId());

        // 删除旧的object_store_fieldInfo
        if (StringUtils.isNotBlank(tableInfoId)) {
            objectStoreInfoDao.deleteObjectStoreFieldInfo(tableInfoId);
        } else {
            throw new NullPointerException("tableInfoId为空，无法删除和插入object_store_fieldInfo表中");
        }

        // 创建新的object_store_fieldInfo
        List<ObjectStoreFieldInfo> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(standFieldList, fieldInfoList, tableInfoId, detectedTable);

        // 插入新的object_store_fieldInfo
        int insertFieldCount = objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
        if (insertFieldCount < 0) {
            throw new SQLException("往object_store_fieldInfo表插入失败");
        } else {
            data.setTableInfoId(newTableInfoId);
            responseList.add(data);
        }
    }

    @Override
    public Map<String,Object> searchTableInfo(ObjectStoreInfoParameter objectStoreInfoParameter) {
        logger.info("开始获取标准建表信息管理的表格数据");
        if(StringUtils.isBlank(objectStoreInfoParameter.getSearchText())){
            objectStoreInfoParameter.setSearchText(null);
        }
        if (objectStoreInfoParameter.getSort().equalsIgnoreCase("storeTypeCh")){
            objectStoreInfoParameter.setSort("storeType");
        }
        Page<ObjectStoreInfo> page = PageHelper.startPage(objectStoreInfoParameter.getPageIndex(), objectStoreInfoParameter.getPageSize());
        List<ObjectStoreInfo> objectStoreInfoList = objectStoreInfoDao.searchTableInfo(objectStoreInfoParameter);
        objectStoreInfoList.stream().forEach(d -> {
            if (d.getResId() != null){
                DataResource dataResource = restTemplateHandle.getResourceById(d.getResId());
                if (dataResource != null && dataResource.getCenterId() != null){
                    d.setCenterId(dataResource.getCenterId());
                }
            }
            if(d.getOrganizationClassificationId() != null ){
                d.setOrganizationClassificationCh(OrganizationClassification.getValueById(d.getOrganizationClassificationId()));
            }
            d.setStoreTypeCh(ObjectStoreInfoStoreType.getStoreType(d.getStoreType()));
        });
        PageInfo<ObjectStoreInfo> pageInfo = new PageInfo<>(objectStoreInfoList);
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    @Override
    public void refreshTable(RefreshCreatedPojo refreshCreatedPojo) {
        long startTime = System.currentTimeMillis();
        //调用仓库接口获取当前数据源、项目空间下的表信息
//        List<DetectedTable> detectedTableList = restTemplateHandle.getTablesIncludeDetectedInfo(resId, project);
        List<DetectedTable> detectedTableList = restTemplateHandle.getTableImformationList();

        if(!detectedTableList.isEmpty() && detectedTableList.size() > 0){
            refreshTableHandler(refreshCreatedPojo, detectedTableList);
            long uploadFinish = System.currentTimeMillis();
            logger.info("入库完成,用时：" + ((uploadFinish - startTime) / 1000 / 60) + "分钟");
        }else{
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "从当前数据源获取到的表信息为空");
        }
    }

    @Override
    public void refreshTableSync(RefreshCreatedPojo refreshCreatedPojo) {
        long startTime = System.currentTimeMillis();
        //调用仓库接口获取当前数据源、项目空间下的表信息
        List<DetectedTable> detectedTableList = restTemplateHandle.getTablesIncludeDetectedInfo(refreshCreatedPojo.getResId(), refreshCreatedPojo.getProjectName());

        if(!detectedTableList.isEmpty() && detectedTableList.size() > 0){
            refreshTableHandler(refreshCreatedPojo, detectedTableList);
            long uploadFinish = System.currentTimeMillis();
            logger.info("入库完成,用时：" + ((uploadFinish - startTime) / 1000 / 60) + "分钟");
        }else{
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "从当前数据源获取到的表信息为空");
        }
    }

    @Override
    public void refreshCreateTableOneTable(RefreshCreatedPojo refreshCreatedPojo) {
        //调用仓库接口获取当前数据源、项目空间下的表信息
        List<DetectedTable> detectedTableList = restTemplateHandle.getTableImformationList();
        detectedTableList = detectedTableList.stream().filter(f -> f.getTableNameEN().equalsIgnoreCase(refreshCreatedPojo.getTableName())).collect(toList());
        if(!detectedTableList.isEmpty() && detectedTableList.size() > 0){
            refreshTableHandler(refreshCreatedPojo, detectedTableList);
        }else{
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "从当前数据源获取到的表信息为空");
        }
    }

    public void refreshTableHandler(RefreshCreatedPojo refreshCreatedPojo, List<DetectedTable> detectedTableList){
        // 删除object_store_fieldInfo中tableInfoId不存在于object_store_info中的无效数据
        objectStoreInfoDao.deleteObjectStoreFieldInfos();

        //查询标准所有表信息
        List<ObjectPojo> objectTableList = resourceManageDao.searchAllObjectTable();
        objectTableList.stream().forEach( d ->{
            detectedTableList.stream().filter(e -> StringUtils.equalsIgnoreCase(d.getTableName(),e.getTableNameEN())).collect(toList());
        });
        detectedTableList.stream().forEach( d ->{
            String tableInfoId = objectStoreInfoDao.searchObjectStoreInfoId(d.getTableNameEN(), d.getProjectName(), d.getResId());
            logger.info(String.format("开始处理：%s>>>%s>>>%s>>>%s>>>%s，tableInfoId：%s",d.getCenterName(),d.getResName(),d.getResId(),d.getProjectName(),d.getTableNameEN(),tableInfoId));
            ObjectPojo targetObjectPojo = resourceManageDao.searchObjectInfo(d.getTableNameEN());
            if (targetObjectPojo == null){
                logger.info(String.format("表：%s，在object没有记录，略过",d.getTableNameEN()));
                return;
            }
            List<FieldInfo> dataResourceFieldInfo = restTemplateHandle.requestGetTableStructure(d.getResId(), d.getProjectName(), d.getTableNameEN());
            List<ObjectField> standardObjectFields = resourceManageDao.searchObjectFieldByTableId(targetObjectPojo.getTableId());
            if(StringUtils.isBlank(tableInfoId)){
                //新增 生成object_store_info
                logger.info("开始新增object_store_info");
                String infoId = UUIDUtil.getUUID();
                ObjectStoreInfo objectStoreInfo = createNewObjectStoreInfo(infoId,targetObjectPojo,d,targetObjectPojo.getTableId());
                // 20250908：新疆需求，创建者置空
//                objectStoreInfo.setCreater(StringUtils.isBlank(refreshCreatedPojo.getUserName())? null : refreshCreatedPojo.getUserName());
//                objectStoreInfo.setCreaterId(StringUtils.isBlank(refreshCreatedPojo.getUserId())? null : refreshCreatedPojo.getUserId());
                //根据tableId查询objectField
                List<ObjectStoreFieldInfo> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(standardObjectFields,dataResourceFieldInfo,infoId, d);
                //保存信息
                //使用编程式事务，对表和字段的保存进行事务管理
                transactionTemplate.execute(transactionStatus -> {
                    int saveObjectStoreInfo = objectStoreInfoDao.saveObjectStoreInfo(objectStoreInfo, 1);
                    if(saveObjectStoreInfo == 1 && objectStoreFieldInfoList.size() != 0){
                        objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                    }
                    return Boolean.TRUE;
                });
            }else{
                if (refreshCreatedPojo.getIsOneTableRefresh()){
                    List<ObjectStoreFieldInfo> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(standardObjectFields,dataResourceFieldInfo,tableInfoId, d);
                    transactionTemplate.execute(transactionStatus -> {
                        if (objectStoreFieldInfoList.size()>0){
                            objectStoreInfoDao.deleteObjectStoreFieldInfo(tableInfoId);
                            objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                        }
                        return Boolean.TRUE;
                    });
                }else {
                    int oldObjectStoreFieldInfoSum = objectStoreInfoDao.getOldOSFInfoSum(tableInfoId);
                    ObjectStoreInfo objectStoreInfo = new ObjectStoreInfo();
                    objectStoreInfo.setTableInfoId(tableInfoId);
                    // 物理表字段个数与object_store_field_info中字段记录数不一致时，不再验证表的tableinfoid是否已注册（未注销）
                    if (oldObjectStoreFieldInfoSum != dataResourceFieldInfo.size()){
                        logger.info("开始更新object_store_fieldinfo");
                        List<ObjectStoreFieldInfo> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(standardObjectFields,dataResourceFieldInfo,tableInfoId, d);
                        //保存信息
                        //使用编程式事务，对表和字段的保存进行事务管理
                        transactionTemplate.execute(transactionStatus -> {
                            if (objectStoreFieldInfoList.size()>0){
                                objectStoreInfoDao.deleteObjectStoreFieldInfo(tableInfoId);
                                objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                            }
                            return Boolean.TRUE;
                        });
                    }
                }
            }
        });
    }

    @Override
    public void updateBuildTableShowField(String showField) {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        if (StringUtils.isBlank(showField)){
            showField = "notNull";
        }
        objectStoreInfoDao.updateBuildTableShowField(showField, authorizedUser.getUserName());
    }

    @Override
    public List<String> getBuildTableShowField() {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        List<String> listReturn = null;
        String result = objectStoreInfoDao.getBuildTableShowField(authorizedUser.getUserName());
        if (StringUtils.isNotEmpty(result) || result != null){
            listReturn = Arrays.asList(result.split(","));
        }
        return listReturn;
    }

    @Override
    public List<PageSelectOneValue> getStoreTypeList() {
        List<PageSelectOneValue> resultList = objectStoreInfoDao.getStoreTypeList();;
        return resultList;
    }

    @Override
    public ServerResponse deleteObjectStore(ObjectStoreInfo objectStoreInfo) {
        try {
            List<String> sjzybsfList = objectStoreInfoDao.getSjzybsfList(objectStoreInfo);
            if (sjzybsfList != null && sjzybsfList.size()>0){
                String sjzybsfStr = StringUtils.join(sjzybsfList,",");
                String msg = String.format("当前表已注册成数据资源目录【%s】不允许删除。请先在“服务工厂”系统完成资源注销后，再进行删除操作！",sjzybsfStr);
                return ServerResponse.asSucessResponse("false",msg);
            }else {
                objectStoreInfoDao.delObjectStoreInfoByTableInfoId(objectStoreInfo);
                objectStoreInfoDao.delObjectStoreFieldInfoByTableInfoId(objectStoreInfo);
                String resId = objectStoreInfo.getDataId() == null ? objectStoreInfo.getResId() : objectStoreInfo.getDataId();
                DataResource dataResource = restTemplateHandle.getResourceById(resId);
                objectStoreInfo.setResName(dataResource.getResName());
                operateLogServiceImpl.objectStoreInfoSuccessLog(OperateLogHandleTypeEnum.DELETE, "建表信息管理", objectStoreInfo);
                return ServerResponse.asSucessResponse("true","删除成功");
            }
        }catch (Exception e){
            logger.error("删除objectStoreInfo信息失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("删除objectStoreInfo信息失败");
        }
    }

    @Override
    public List<PageSelectOneValue> getDataResource(String dataCenterId, String storeType){
        List<PageSelectOneValue> resultList = new ArrayList<>();
        try {
            String storeTypeStr = SysCodeEnum.getNameByCodeAndType(storeType, "TABLETYPE");
            List<DataResource> dataResourceList = restTemplateHandle.getDataResourceByCenterId(dataCenterId, "0");
            dataResourceList.stream().forEach(d ->{
                if (StringUtils.isBlank(storeType)){
                    PageSelectOneValue pageSelectOneValue = new PageSelectOneValue(d.getResId(), d.getResName(),d.getResType());
                    resultList.add(pageSelectOneValue);
                }else {
                    if (storeTypeStr.toUpperCase().equalsIgnoreCase(d.getResType().toUpperCase())){
                        PageSelectOneValue pageSelectOneValue = new PageSelectOneValue(d.getResId(), d.getResName(),d.getResType());
                        resultList.add(pageSelectOneValue);
                    }
                }
            });

        } catch (Exception e) {

        }
        return resultList;
    }

    @Override
    public List<String> getProjectList(String resId) {
        List<ProjectInfo> projectList = restTemplateHandle.getProjectList(resId);
        if (projectList.isEmpty()) {
            logger.error("查询仓库项目空间信息出错");
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "查询项目空间为空");
        }
        ArrayList<String> list = new ArrayList<>();
        for (ProjectInfo data : projectList) {
            list.add(data.getProjectName());
        }
        return list;
    }

    @Override
    public BuildTableFilterObject getFilterInfo() {
        BuildTableFilterObject buildTableFilterObject = new BuildTableFilterObject();
        List<FilterObject> filterObjectList = objectStoreInfoDao.getFilterInfo();
        if(filterObjectList == null || filterObjectList.isEmpty()){
            buildTableFilterObject.setStoreTypeList(new ArrayList<>());
            buildTableFilterObject.setResNameList(new ArrayList<>());
            buildTableFilterObject.setProjectList(new ArrayList<>());
            buildTableFilterObject.setCreatorList(new ArrayList<>());
        }else{
            // text里面为 storeType 需要进行枚举类来筛选
            List<FilterObject> storeTypeList = new ArrayList<>();
            filterObjectList.stream().filter( d-> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getText(), Common.STORETYPE))
                    .forEach(d ->{
                        d.setValue(StringUtils.isBlank(d.getValue())?"":d.getValue());
                        d.setText(ObjectStoreInfoStoreType.getStoreType(Integer.valueOf(d.getValue())));
                        storeTypeList.add(d);
                    });
            buildTableFilterObject.setStoreTypeList(storeTypeList);

            //存储数据源的设置
            List<FilterObject> resNameList = new ArrayList<>();
            filterObjectList.stream().filter( d -> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getText(),Common.RESNAME))
                    .forEach( d ->{
                        DataResource dataResource = restTemplateHandle.getResourceById(d.getValue());
                        if (dataResource != null) {
                            d.setText(dataResource.getResName());
                            d.setValue(d.getValue());
                            resNameList.add(d);
                        }
                    });
            buildTableFilterObject.setResNameList(resNameList);
            // text里面为 project 的 直接获取就可以了
            List<FilterObject> projectList = new ArrayList<>();
            filterObjectList.stream().filter( d-> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getText(), Common.PROJECTNAME))
                    .forEach(d ->{
                        d.setValue(StringUtils.isBlank(d.getValue())?"":d.getValue());
                        d.setText(d.getValue());
                        projectList.add(d);
                    });
            buildTableFilterObject.setProjectList(projectList);
            // text里面为 创建人 的 直接获取就可以了
            List<FilterObject> creatorList = new ArrayList<>();
            filterObjectList.stream().filter( d-> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getText(), Common.CREATER))
                    .forEach(d ->{
                        d.setValue(StringUtils.isBlank(d.getValue())?"":d.getValue());
                        d.setText(d.getValue());
                        creatorList.add(d);
                    });
            buildTableFilterObject.setCreatorList(creatorList);

        }
        return buildTableFilterObject;
    }

    @Override
    public void updateImportFlag(ObjectStoreInfo objectStoreInfo) {
        objectStoreInfoDao.updateImportFlag(objectStoreInfo);
    }

    @Override
    public List<ObjectInfo> getStandardDataSet() {
        List<ObjectInfo> objectInfos = objectStoreInfoDao.getStandardDataSet();
        objectInfos.stream().forEach(item -> {
            if(item.getOrganizationClassificationId() != null ){
                item.setOrganizationClassificationCh(OrganizationClassification.getValueById(item.getOrganizationClassificationId()));
            }
        });
        return objectInfos;
    }

    @Override
    public List<DetectedTable> getDetectedTableList(String resId, String projectName) {
        List<DetectedTable> detectedTableList = restTemplateHandle.getTablesIncludeDetectedInfo(resId, projectName);
        return detectedTableList;
    }

    @Override
    public List<ObjectStoreFieldInfo> getStandardDataItem(String objectId) {
        List<ObjectStoreFieldInfo> objectStoreFieldInfos = objectStoreInfoDao.getStandardDataItem(objectId);
        objectStoreFieldInfos.stream().forEach(item ->{
            item.setFieldTypeCh(FieldType.getFieldType(item.getFieldType()));
        });
        return objectStoreFieldInfos;
    }

    @Override
    public List<FieldInfo> getDetectedFieldInfo(String resId, String projectName, String tableName) {
        List<FieldInfo> dataResourceFieldInfo = restTemplateHandle.requestGetTableStructure(resId, projectName, tableName);
        return dataResourceFieldInfo;
    }

    @Override
    public boolean existTableInfoId(String tableName, String projectName, String resId, String tableId) {
        String tableInfoId = objectStoreInfoDao.searchObjectStoreInfoId(tableName, projectName, resId);
        int createTableCount = objectStoreInfoDao.getCreateTableCount(tableId,tableName);
        if(StringUtils.isBlank(tableInfoId) && createTableCount<1){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public String saveCreateTableInfo(ObjectStoreInfo objectStoreInfo) {
//        /*后台自己计算保存信息*/
//        //调用仓库接口获取当前数据源、项目空间下的表信息
//        List<DetectedTable> detectedTableList = restTemplateHandle.getTablesIncludeDetectedInfo(objectStoreInfo.getDataId(), objectStoreInfo.getProjectName());
//        //查询标准所有表
//        信息
//        List<ObjectPojo> objectTableList = resourceManageDao.searchAllObjectTable();
//        if(!detectedTableList.isEmpty() && detectedTableList.size() > 0){
//            ObjectStoreInfo finalObjectStoreInfo = objectStoreInfo;
//            objectTableList.stream().filter(item -> StringUtils.equalsIgnoreCase(finalObjectStoreInfo.getTableName(), item.getTableName())).collect(toList());
//            detectedTableList.stream().filter(item -> StringUtils.equalsIgnoreCase(finalObjectStoreInfo.getTableName(), item.getTableNameEN())).collect(toList());
//            detectedTableList.stream().forEach( d ->{
//
//            });
//        }else{
//            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "从当前数据源获取到的表信息为空");
//        }

        // 前端传递所有保存信息
        String msg = "保存建表信息成功";
        String tableInfoId = "";
        if (objectStoreInfo.getStoreType() == 14 || objectStoreInfo.getStoreType() == 19){
            // 如果平台类型是kafka或ftp则验证 tableid+主题名/文件路径+dataID 的唯一性
            tableInfoId = objectStoreInfoDao.searchObjectStoreInfoIdByTableId(objectStoreInfo.getTableName(),objectStoreInfo.getTableId(),objectStoreInfo.getDataId());
        }else {
            // 否则验证tableName+projectName+dataId 的唯一性
            tableInfoId = objectStoreInfoDao.searchObjectStoreInfoId(objectStoreInfo.getTableName(), objectStoreInfo.getProjectName(), objectStoreInfo.getDataId());
        }
        if(StringUtils.isBlank(tableInfoId)){
            //新增
            logger.info("开始新增object_store_info");
            //生成object_store_info
            String infoId = UUIDUtil.getUUID();
            objectStoreInfo = buildObjectStoreInfo(objectStoreInfo, infoId);
            //根据tableId查询objectField
            List<ObjectStoreFieldInfo> objectStoreFieldInfoList = objectStoreInfo.getObjectStoreFieldInfos();
            objectStoreFieldInfoList = buildObjectStoreFieldInfo(objectStoreFieldInfoList, infoId, objectStoreInfo.getDataId());
            //保存信息
            //使用编程式事务，对表和字段的保存进行事务管理
            ObjectStoreInfo finalObjectStoreInfo = objectStoreInfo;
            List<ObjectStoreFieldInfo> finalObjectStoreFieldInfoList = objectStoreFieldInfoList;
            transactionTemplate.execute(transactionStatus -> {
                int saveObjectStoreInfo = objectStoreInfoDao.saveObjectStoreInfo(finalObjectStoreInfo, 1);
                if(saveObjectStoreInfo == 1 && finalObjectStoreFieldInfoList.size() != 0){
                    objectStoreInfoDao.saveObjectStoreFieldInfo(finalObjectStoreFieldInfoList);
                }
                return Boolean.TRUE;
            });
            operateLogServiceImpl.objectStoreInfoSuccessLog(OperateLogHandleTypeEnum.ADD, "建表信息管理", objectStoreInfo);
        }else{
            objectStoreInfo = buildObjectStoreInfo(objectStoreInfo, tableInfoId);
//            List<String> sjzybsfList = objectStoreInfoDao.getSjzybsfList(objectStoreInfo);
            // 不在验证表的tableinfoid是否已注册（未注销）
            logger.info("开始更新object_store_info");
            List<ObjectStoreFieldInfo> objectStoreFieldInfoList = objectStoreInfo.getObjectStoreFieldInfos();
            objectStoreFieldInfoList = buildObjectStoreFieldInfo(objectStoreFieldInfoList, tableInfoId, objectStoreInfo.getDataId());
            //保存信息
            //使用编程式事务，对表和字段的保存进行事务管理
            String finalTableInfoId = tableInfoId;
            ObjectStoreInfo finalObjectStoreInfo1 = objectStoreInfo;
            List<ObjectStoreFieldInfo> finalObjectStoreFieldInfoList = objectStoreFieldInfoList;
            transactionTemplate.execute(transactionStatus -> {
                objectStoreInfoDao.updateObjectStoreInfo(finalObjectStoreInfo1);
                objectStoreInfoDao.deleteObjectStoreFieldInfo(finalTableInfoId);
                objectStoreInfoDao.saveObjectStoreFieldInfo(finalObjectStoreFieldInfoList);
                return Boolean.TRUE;
            });
            operateLogServiceImpl.objectStoreInfoSuccessLog(OperateLogHandleTypeEnum.ALTER, "建表信息管理", objectStoreInfo);

//            if (!(sjzybsfList != null && sjzybsfList.size()>0)){
//            }else {
//                String sjzybsfStr = sjzybsfList.toString();
//                msg = String.format("当前表已注册成数据资源目录%s，编辑信息可能对应用造成影响。请先在“服务工厂”系统完成资源注销后，再进行编辑操作！", sjzybsfStr);
//            }
        }
        return msg;
    }

    @Override
    public ObjectStoreInfo getCreateTableInfo(ObjectStoreInfo objectStoreInfo){
        String tableInfoId = "";
        if (objectStoreInfo.getStoreType() == 14 || objectStoreInfo.getStoreType() == 19){
            // 如果平台类型是kafka或ftp则验证 tableid+主题名/文件路径+dataID 的唯一性
            tableInfoId = objectStoreInfoDao.searchObjectStoreInfoIdByTableId(objectStoreInfo.getTableName(),objectStoreInfo.getTableId(),objectStoreInfo.getDataId());
        }else {
            // 否则验证tableName+projectName+dataId 的唯一性
            tableInfoId = objectStoreInfoDao.searchObjectStoreInfoId(objectStoreInfo.getTableName(), objectStoreInfo.getProjectName(), objectStoreInfo.getDataId());
        }
        ObjectStoreInfo objectStoreInfoOld = objectStoreInfoDao.getObjectStoreInfo(tableInfoId);
        if (objectStoreInfoOld != null){
            objectStoreInfo.setTableId(objectStoreInfoOld.getTableId());
            objectStoreInfo.setIsPartition(objectStoreInfoOld.getIsPartition() == 1?0:1);
            objectStoreInfo.setPartitionCount(objectStoreInfoOld.getPartitionCount());
            objectStoreInfo.setLifeCycle(objectStoreInfoOld.getLifeCycle());
            objectStoreInfo.setPartitionType(objectStoreInfoOld.getPartitionType());
            objectStoreInfo.setImportFlag(objectStoreInfoOld.getImportFlag());
        }
        List<ObjectField> standardObjectFields = resourceManageDao.searchObjectFieldByTableId(objectStoreInfo.getTableId());
        List<ObjectStoreFieldInfo> objectStoreFieldInfos = objectStoreInfoDao.getObjectStoreFieldInfo(tableInfoId);
        objectStoreFieldInfos.stream().forEach(item ->{
            standardObjectFields.stream().forEach(stand ->{
                if (item.getFieldId() != null && stand.getFieldId() != null && item.getFieldId().equalsIgnoreCase(stand.getFieldId())){
                    item.setToStandardField(stand.getColumnName());
                }
            });
        });
        objectStoreInfo.setObjectStoreFieldInfos(objectStoreFieldInfos);
        return objectStoreInfo;
    }

    public ObjectStoreInfo buildObjectStoreInfo(ObjectStoreInfo objectStoreInfo, String tableInfoId){
        objectStoreInfo.setTableInfoId(tableInfoId);
        objectStoreInfo.setMemo("");
        if (objectStoreInfo.getIsPartition() == null){
            objectStoreInfo.setIsPartition(1);
        }else {
            objectStoreInfo.setIsPartition(objectStoreInfo.getIsPartition() == 1?0:1);
        }
        //是否实时表默认为1
        objectStoreInfo.setIsActiveTable(1);
//        if(objectStoreInfo.getIsPartition() == 1){
//            //是分区表，默认为1，按天分区
//            objectStoreInfo.setPartitionType(1);
//        }
        objectStoreInfo.setLifeCycle(StringUtils.isNotBlank(objectStoreInfo.getLifeCycleStr()) ? Integer.parseInt(objectStoreInfo.getLifeCycleStr()) : 0);
        objectStoreInfo.setTableCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
        //searchFlag字段写入默认值0
        objectStoreInfo.setSearchFlag(0);
        return objectStoreInfo;
    }

    public List<ObjectStoreFieldInfo> buildObjectStoreFieldInfo(List<ObjectStoreFieldInfo> objectStoreFieldInfos, String tableInfoId, String resId){
//        // 获取数据源类型
//        DataResource dataResource = restTemplateHandle.getResourceById(resId);
        objectStoreFieldInfos.stream().forEach(item ->{
            String id = UUIDUtil.getUUID();
            item.setId(id);
            item.setTableInfoId(tableInfoId);
            item.setFieldType(SynlteFieldType.getSynlteNumType(item.getFieldTypeCh()));
            if (item.getFieldLen() == 0){
                if (item.getFieldType() == 0){
                    item.setFieldLen(50);
                }else if (item.getFieldType() == 2){
                    item.setFieldLen(200);
                }
            }
            item.setDefaultValue(StringUtils.isNotBlank(item.getDefaultValue())?item.getDefaultValue():"");
            // 增加从仓库获取的字段类型、长度
            item.setColumnFieldType(item.getFieldTypeCh());
            if (item.getFieldLen() > 0){
                item.setColumnFieldLen(item.getFieldLen());
            }
//            if (dataResource != null && StringUtils.isNotBlank(dataResource.getResType()) && dataResource.getResType().equalsIgnoreCase("ODPS")){
//                int odpsPartition = StringUtils.isBlank(item.getOdpsPattition()) ? 0 : Integer.parseInt(item.getOdpsPattition());
//                item.setPartitionRecno(odpsPartition);
//            }
//            else {
//                item.setPartitionRecno(item.getPartitionRecno());
//            }
        });
        return objectStoreFieldInfos;

    }

//    public void deleteObjectStoreInfo(String tableInfoId){
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        String deleteSql = "delete from synlte.OBJECT_STORE_FIELDINFO where tableInfoId = ?";
//        jdbcTemplate.batchUpdate(deleteSql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setString(1,tableInfoId);
//            }
//
//            @Override
//            public int getBatchSize() {
//                return 0;
//            }
//        });
//
//    }

    /**
     * 生成Object_store_fieldInfo
     */
    private List<ObjectStoreFieldInfo> getObjectStoreFieldInfo(DetectedTable detectedTable, String tableInfoId, List<ObjectField> objectFields){
        //表信息存储成功之后，将更新或新增的字段存储到object_store_fieldInfo表中
        //调取仓库接口获取表结构信息
        List<FieldInfo> fieldInfoList = restTemplateHandle.requestGetTableStructure(detectedTable.getResId(),
                detectedTable.getProjectName(), detectedTable.getTableNameEN());
        //取到表字段信息
        List<ObjectStoreFieldInfo> list = new ArrayList<>();
        if(fieldInfoList != null && !fieldInfoList.isEmpty()){
            int i = 1;
            int flag = 0;
            for(FieldInfo fieldInfo:fieldInfoList){
                String id = UUIDUtil.getUUID();

                ObjectField field =  objectFields.stream().filter(d -> StringUtils.equalsIgnoreCase(d.getColumnName(),fieldInfo.getFieldName()))
                        .findFirst().orElse(null);
                ObjectStoreFieldInfo objectStoreFieldInfo = new ObjectStoreFieldInfo();
                if(field != null){
                    objectStoreFieldInfo.setId(id);
                    objectStoreFieldInfo.setTableInfoId(tableInfoId);
                    objectStoreFieldInfo.setFieldId(field.getFieldId());
                    objectStoreFieldInfo.setColumnName(fieldInfo.getFieldName());
                    if (StringUtils.isNotBlank(field.getFieldChineseName())){
                        objectStoreFieldInfo.setFieldChineeName(field.getFieldChineseName());
                    }else {
                        objectStoreFieldInfo.setFieldChineeName(field.getColumnName());
                    }
//                    // fieldLen、defaultValue废弃
//                    objectStoreFieldInfo.setFieldLen(field.getFieldLen());
//                    objectStoreFieldInfo.setDefaultValue(field.getDefaultValue());
                    objectStoreFieldInfo.setTableIndex(field.getTableIndex());
                    objectStoreFieldInfo.setIndexType(field.getIndexType());
                    objectStoreFieldInfo.setNeedValue(field.getNeedValue());
                    objectStoreFieldInfo.setRecno(i);
                    // 分区级别改为从仓库获取
                    if (StringUtils.isNotBlank(fieldInfo.getPartitionLevel())){
                        if (fieldInfo.getPartitionLevel().equalsIgnoreCase("一级分区")){
                            objectStoreFieldInfo.setPartitionRecno(1);
                        }else if (fieldInfo.getPartitionLevel().equalsIgnoreCase("二级分区")){
                            objectStoreFieldInfo.setPartitionRecno(2);
                        }
                    }
                    objectStoreFieldInfo.setClustRecno(field.getClustRecno());
                    objectStoreFieldInfo.setFieldType(SynlteFieldType.getSynlteNumType(fieldInfo.getType()));
                    // 增加从仓库获取的字段类型、长度
                    objectStoreFieldInfo.setColumnFieldType(fieldInfo.getType());
                    if (fieldInfo.getLength() > 0){
                        objectStoreFieldInfo.setColumnFieldLen(fieldInfo.getLength());
                    }
                }else{
                    objectStoreFieldInfo.setId(UUIDUtil.getUUID());
                    objectStoreFieldInfo.setTableInfoId(tableInfoId);
                    objectStoreFieldInfo.setColumnName(fieldInfo.getFieldName());
//                    objectStoreFieldInfo.setFieldLen(fieldInfo.getLength());
                    objectStoreFieldInfo.setNeedValue(fieldInfo.getNullAble());
                    objectStoreFieldInfo.setRecno(i);
                    // 分区级别改为从仓库获取
                    if (StringUtils.isNotBlank(fieldInfo.getPartitionLevel())){
                        if (fieldInfo.getPartitionLevel().equalsIgnoreCase("一级分区")){
                            objectStoreFieldInfo.setPartitionRecno(1);
                        }else if (fieldInfo.getPartitionLevel().equalsIgnoreCase("二级分区")){
                            objectStoreFieldInfo.setPartitionRecno(2);
                        }
                    }
                    objectStoreFieldInfo.setFieldType(SynlteFieldType.getSynlteNumType(fieldInfo.getType()));
                    // 增加从仓库获取的字段类型、长度
                    objectStoreFieldInfo.setColumnFieldType(fieldInfo.getType());
                    if (fieldInfo.getLength() > 0){
                        objectStoreFieldInfo.setColumnFieldLen(fieldInfo.getLength());
                    }
                }
                list.add(objectStoreFieldInfo);
                i++;
            }
        }
        return list;
    }

    /**
     * 获取 表 OBJECT_STORE_INFO 需要插入的数据
     * @param detectedTable
     * @param refreshCreatedPojo
     * @param tableMapCount
     * @param tableId
     * @param objectName
     * @return
     */
    private ObjectStoreInfo getObjectStore(DetectedTable detectedTable,
                                           RefreshCreatedPojo refreshCreatedPojo,
                                           Map<String,Long> tableMapCount,
                                           String tableId,
                                           String objectName,
                                           String tableInfoId){
        ObjectStoreInfo objectStoreInfo = new ObjectStoreInfo();
        objectStoreInfo.setTableInfoId(tableInfoId);
        objectStoreInfo.setTableId(tableId);
        objectStoreInfo.setTableName(detectedTable.getTableNameEN());
        objectStoreInfo.setObjectName(objectName);
        objectStoreInfo.setStoreType(getStoreType(String.valueOf(detectedTable.getResType()),detectedTable.getProjectName()));
        objectStoreInfo.setProjectName(detectedTable.getProjectName());
        objectStoreInfo.setMemo(" ");
        objectStoreInfo.setCreater(StringUtils.isBlank(refreshCreatedPojo.getUserName())?" ":refreshCreatedPojo.getUserName());
        objectStoreInfo.setCreaterId(StringUtils.isBlank(refreshCreatedPojo.getUserId())?" ":refreshCreatedPojo.getUserId());
        objectStoreInfo.setTableCreateTime(StringUtilsMatcher.isParseDate(DateUtil.formatDate(detectedTable.getCreateTime()))
                ? DateUtil.formatDate(detectedTable.getCreateTime()):"");
        if(tableMapCount.getOrDefault(detectedTable.getTableNameEN(),0L) >1 ){
            objectStoreInfo.setImportFlag(0);
        }else{
            objectStoreInfo.setImportFlag(1);
        }
        objectStoreInfo.setIsPartition(detectedTable.getIsPartitioned() == 1?0:1);
        //是否实时表默认为1
        objectStoreInfo.setIsActiveTable(1);
        objectStoreInfo.setLifeCycle(StringUtils.isNotBlank(detectedTable.getStoreCycle())?
                Integer.parseInt(detectedTable.getStoreCycle()):0);
        objectStoreInfo.setDataId(detectedTable.getResId());
        //searchFlag字段写入默认值0
        objectStoreInfo.setSearchFlag(0);
        if(detectedTable.getIsPartitioned() == 1){
            //是分区表，默认为1，按天分区
            objectStoreInfo.setPartitionType(1);
        }
        return objectStoreInfo;
    }

    /**
     *  资源注册时未查到相应的信息，新建并回填object_store_info信息
     * @param detectedTable 已探查表信息
     * @param tableId 协议ID
     * @param tableInfoId 表存储信息ID
     * @return
     */
    public ObjectStoreInfo createNewObjectStoreInfo(String tableInfoId, ObjectPojo objectPojo, DetectedTable detectedTable, String tableId){
        ObjectStoreInfo objectStoreInfo = new ObjectStoreInfo();
        objectStoreInfo.setTableInfoId(tableInfoId);
        objectStoreInfo.setTableId(tableId);
        String tableName = StringUtils.isNotBlank(detectedTable.getTableNameEN())?detectedTable.getTableNameEN():"";
//        tableName = tableName.length() > 50 ? tableName.substring(0,50) : tableName;
        objectStoreInfo.setTableName(tableName);
        String objectName = StringUtils.isNotBlank(objectPojo.getObjectName()) ? objectPojo.getObjectName() : objectPojo.getTableName();
//        objectName = objectName.length() > 50 ? objectName.substring(0,50) : objectName;
//        objectName = StringUtilsMatcher.getStringLength(objectName) > 32 && StringUtilsMatcher.getStringLength(objectName) > objectName.length() ? objectName.substring(0,16) : objectName;
        objectStoreInfo.setObjectName(objectName);
//        objectStoreInfo.setStoreType(getStoreType(detectedTable.getResType(),detectedTable.getProjectName()));
        objectStoreInfo.setStoreType(getStoreType(String.valueOf(detectedTable.getResType()),detectedTable.getProjectName()));
        String projectName = StringUtils.isNotBlank(detectedTable.getProjectName()) ? detectedTable.getProjectName() : "";
        projectName = projectName.length() > 50 ? projectName.substring(0,50) : projectName;
        objectStoreInfo.setProjectName(projectName);
        objectStoreInfo.setMemo("");
        // 先查询数据库中，如果数据库中这个表的数据量(IMPORT_FLAG = 1)为0，则这个值为1，如果为1 ，则该值为0
        int tableCount = objectStoreInfoDao.queryCountByTableName(objectStoreInfo.getStoreType(), objectStoreInfo.getTableName());
        objectStoreInfo.setImportFlag(tableCount > 0 ? 0 : 1);
        objectStoreInfo.setIsPartition(detectedTable.getIsPartitioned() == 1?0:1);
        //是否实时表默认为1
        objectStoreInfo.setIsActiveTable(1);
        if(detectedTable.getIsPartitioned() == 1){
            //是分区表，默认为1，按天分区
            objectStoreInfo.setPartitionType(1);
        }
        objectStoreInfo.setLifeCycle(StringUtils.isNotBlank(detectedTable.getStoreCycle())?
                Integer.parseInt(detectedTable.getStoreCycle()):0);
        objectStoreInfo.setDataId(detectedTable.getResId());
//        objectStoreInfo.setTableCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
        objectStoreInfo.setTableCreateTime(StringUtilsMatcher.isParseDate(DateUtil.formatDate(detectedTable.getCreateTime()))
                ? DateUtil.formatDate(detectedTable.getCreateTime()) : DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
        //searchFlag字段写入默认值0
        objectStoreInfo.setSearchFlag(0);
        if (objectStoreInfo.getPartitionCount() == null) objectStoreInfo.setPartitionCount(0);
        //TODO partitionCount字段未填写，改字段为KAFKA和datahub平台
        return objectStoreInfo;
    }

    /**
     *  资源注册时未查到相应的信息，新建并回填object_store_info信息
     * @param fieldInfoList 表字段信息
     * @param tableInfoId 表存储信息ID
     * @return
     */
    public List<ObjectStoreFieldInfo> createNewObjectStoreFieldInfo(List<ObjectField> standsFieldList, List<FieldInfo> fieldInfoList, String tableInfoId, DetectedTable detectedTable){
        List<ObjectStoreFieldInfo> responseList = new ArrayList<>();
        if(fieldInfoList != null && !fieldInfoList.isEmpty()){
//            // 获取数据源类型
//            DataResource dataResource = restTemplateHandle.getResourceById(detectedTable.getResId());
            // 处理字段信息
            fieldInfoList.stream().forEach( d ->{
                String id = UUIDUtil.getUUID();
                ObjectStoreFieldInfo objectStoreFieldInfo = new ObjectStoreFieldInfo();
                objectStoreFieldInfo.setRecno(d.getNo());
                objectStoreFieldInfo.setId(id);
                objectStoreFieldInfo.setTableInfoId(tableInfoId);
                objectStoreFieldInfo.setColumnName(d.getFieldName());
                // 分区级别改为从仓库获取
                if (StringUtils.isNotBlank(d.getPartitionLevel())){
                    if (d.getPartitionLevel().equalsIgnoreCase("一级分区")){
                        objectStoreFieldInfo.setPartitionRecno(1);
                    }else if (d.getPartitionLevel().equalsIgnoreCase("二级分区")){
                        objectStoreFieldInfo.setPartitionRecno(2);
                    }else {
                        objectStoreFieldInfo.setPartitionRecno(0);
                    }
                }
                objectStoreFieldInfo.setFieldType(SynlteFieldType.getSynlteNumType(d.getType()));
                // 增加从仓库获取的字段类型、长度
                objectStoreFieldInfo.setColumnFieldType(d.getType());
                if (d.getLength() > 0){
                    objectStoreFieldInfo.setColumnFieldLen(d.getLength());
                    objectStoreFieldInfo.setFieldLen(d.getLength());
                }else {
                    objectStoreFieldInfo.setFieldLen(0);
                }
                standsFieldList.stream().forEach( e ->{
                    if(d.getFieldName().equalsIgnoreCase(e.getColumnName())){
                        objectStoreFieldInfo.setFieldId(e.getFieldId());
                        if (StringUtils.isNotBlank(e.getFieldChineseName())){
                            objectStoreFieldInfo.setFieldChineeName(e.getFieldChineseName());
                        }else {
                            objectStoreFieldInfo.setFieldChineeName(e.getColumnName());
                        }
                        if (StringUtils.isNotBlank(e.getFieldType())){
                            objectStoreFieldInfo.setFieldType(Integer.valueOf(e.getFieldType()));
                        }
//                        // fieldLen、defaultValue废弃
//                        objectStoreFieldInfo.setFieldLen(e.getFieldLen());
//                        objectStoreFieldInfo.setDefaultValue(StringUtils.isNotBlank(e.getDefaultValue())?e.getDefaultValue():"");
                        objectStoreFieldInfo.setTableIndex(e.getIsIndex());
                        objectStoreFieldInfo.setNeedValue(e.getNeedValue());
                        objectStoreFieldInfo.setIndexType(e.getIndexType());
                        objectStoreFieldInfo.setClustRecno(e.getClustRecno());
                    }
                });
                responseList.add(objectStoreFieldInfo);
            });
        }
        return responseList;

    }

    /**
     *  获取平台的类型对应的数字类型
     * @param dsName   数据库类型
     * @param projectName  项目名
     * @return
     */
    public int getStoreType(String dsName,String projectName){
        if(Common.ODPS.equalsIgnoreCase(dsName)){
            return 1;
        }else if(Common.ADS.equalsIgnoreCase(dsName) && StringUtils.containsIgnoreCase(projectName, Common.HC)){
            return 2;
        }else if(Common.ADS.equalsIgnoreCase(dsName) && StringUtils.containsIgnoreCase(projectName, Common.HP)){
            return 3;
        }else if(Common.HBASEHUAWEI.equalsIgnoreCase(dsName)){
            return 4;
        }else if(Common.HIVEHUAWEI.equalsIgnoreCase(dsName)){
            return 5;
        }else if(Common.ES.equalsIgnoreCase(dsName)){
            return 6;
        }else if(Common.CLICKHOUSE.equalsIgnoreCase(dsName)){
            return 7;
        }else if(Common.LIBRA.equalsIgnoreCase(dsName)){
            return 8;
        }else if(Common.TRS.equalsIgnoreCase(dsName)){
            return 9;
        }else if(Common.ORACLE.equalsIgnoreCase(dsName)){
            return 10;
        }else if(Common.HBASECDH.equalsIgnoreCase(dsName)){
            return 11;
        }else if(Common.HIVECDH.equalsIgnoreCase(dsName)){
            return 12;
        }else if(Common.DATAHUB.equalsIgnoreCase(dsName)){
            return 13;
        }else if(Common.KAFKA.equalsIgnoreCase(dsName)){
            return 14;
        }else if(Common.MQ.equalsIgnoreCase(dsName)){
            return 15;
        }else if(Common.REDIS.equalsIgnoreCase(dsName)){
            return 16;
        }else if(Common.GBASE.equalsIgnoreCase(dsName)){
            return 17;
        }else if(Common.ADB.equalsIgnoreCase(dsName) && StringUtils.containsIgnoreCase(projectName, Common.HC)){
            return 18;
        }else if(Common.FTP.equalsIgnoreCase(dsName)){
            return 19;
        }else if(Common.ADB.equalsIgnoreCase(dsName) && StringUtils.containsIgnoreCase(projectName, Common.HP)){
            return 20;
        }else {
            logger.error("getStoreType方法中配置的数据库类型转换错误，没有对应的类型"+dsName);
            return 0;
        }


    }

    /**
     *  将字段信息插入object_store_fieldInfo表中
     * @param buildTableInfoVo
     * @param uuid  表存储信息ID
     * @return
     */
    public int saveObjectField(BuildTableInfoVo buildTableInfoVo, String uuid){
        //先删除object_store_fieldInfo的旧信息
        logger.info("开始删除object_store_fieldInfo信息");
        int deleteCount = objectStoreInfoDao.deleteObjectStoreFieldInfo(uuid);
        logger.info("删除object_storeInfo的条数为{}",deleteCount);
        //获取字段信息
        List<ObjectField> objectFields = buildTableInfoVo.getColumnData();
        ArrayList<ObjectStoreFieldInfo> objectStoreFieldInfos = new ArrayList<>();
        int i = 1;
        int flag = 0;
        for(ObjectField data : objectFields){
            //构建object_store_fieldInfo
            ObjectStoreFieldInfo objectStoreFieldInfo = new ObjectStoreFieldInfo();
            String id = UUIDUtil.getUUID();
            objectStoreFieldInfo.setId(id);
            objectStoreFieldInfo.setTableInfoId(uuid);
            objectStoreFieldInfo.setFieldId(data.getFieldId());
            objectStoreFieldInfo.setColumnName(data.getColumnName());
            objectStoreFieldInfo.setFieldChineeName(data.getFieldChineseName());
            objectStoreFieldInfo.setFieldType(Integer.valueOf(data.getFieldType()));
            objectStoreFieldInfo.setColumnFieldType(data.getFieldType());
            if (data.getFieldLen() > 0){
                objectStoreFieldInfo.setColumnFieldLen(data.getFieldLen());
            }
//            // fieldLen、defaultValue废弃
//            objectStoreFieldInfo.setFieldLen(data.getFieldLen());
//            objectStoreFieldInfo.setDefaultValue(data.getDefaultValue());
            if(data.getIndexType() == null){
                objectStoreFieldInfo.setIndexType(0);
            }else {
                objectStoreFieldInfo.setIndexType(data.getIndexType());
            }
            objectStoreFieldInfo.setNeedValue(data.getNeedValue());
            objectStoreFieldInfo.setRecno(i);
            objectStoreFieldInfo.setPartitionRecno(data.getPartitionRecno());
            objectStoreFieldInfo.setClustRecno(data.getClustRecno());
            i++;
            //插入object_store_fieldInfo表中
            objectStoreFieldInfos.add(objectStoreFieldInfo);

        }
        flag = objectStoreInfoDao.saveObjectStoreFieldInfo(objectStoreFieldInfos);
        if(flag == 0){
            logger.error("字段插入Object_store_fieldInfo条数为0");
        }
        return i-1;

    }
}
