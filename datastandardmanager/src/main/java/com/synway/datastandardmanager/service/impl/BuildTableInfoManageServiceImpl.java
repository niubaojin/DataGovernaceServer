package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.CommonDTO;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.dto.ObjectStoreInfoDTO;
import com.synway.datastandardmanager.entity.dto.RefreshCreateTableDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.warehouse.DataResource;
import com.synway.datastandardmanager.entity.vo.warehouse.DetectedTable;
import com.synway.datastandardmanager.entity.vo.warehouse.FieldInfo;
import com.synway.datastandardmanager.entity.vo.warehouse.ProjectInfo;
import com.synway.datastandardmanager.enums.*;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.BuildTableInfoManageService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.SelectUtil;
import com.synway.datastandardmanager.util.UUIDUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class BuildTableInfoManageServiceImpl implements BuildTableInfoManageService {

    @Resource
    ObjectStoreInfoMapper objectStoreInfoMapper;
    @Resource
    ObjectStoreFieldInfoMapper objectStoreFieldInfoMapper;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    ObjectFieldMapper objectFieldMapper;
    @Resource
    DpZcConfigFieldControlMapper dpZcConfigFieldControlMapper;
    @Resource
    DsmAllCodeDataMapper allCodeDataMapper;
    @Resource
    PublicDataInfoMapper publicDataInfoMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateColumnToInfo(CreateTableInfoDTO data, int updateFlag) {
        try {
            ObjectStoreInfoEntity objectStoreInfo = new ObjectStoreInfoEntity();
            objectStoreInfo.setTableId(data.getTableId());
            objectStoreInfo.setTableName(data.getCreatedTableData().getTableName());
            String objectName = StringUtils.isNotBlank(data.getCreatedTableData().getTableNameCh()) ? data.getCreatedTableData().getTableNameCh() : data.getCreatedTableData().getTableName();
            objectStoreInfo.setObjectName(objectName);
            String tableBase = data.getCreatedTableData().getTableBase();
            if (tableBase.equalsIgnoreCase("ads") || tableBase.equalsIgnoreCase("adb")) {
                tableBase = tableBase + "-" + data.getCreatedTableData().getTableProject();
            }
            Integer storeType = KeyIntEnum.getKeyByNameAndType(tableBase, Common.DATASTORETYPE);
            objectStoreInfo.setStoreType(storeType);
            objectStoreInfo.setProjectName(data.getCreatedTableData().getTableProject());
            objectStoreInfo.setMemo("");
            objectStoreInfo.setTableCreateTime(new Date());
            objectStoreInfo.setTableModTime(new Date());
            objectStoreInfo.setImportFlag(data.getCreatedTableData().getImportFlag());
            objectStoreInfo.setDataId(data.getCreatedTableData().getDataId());
            objectStoreInfo.setTableInfoId(StringUtils.isNotBlank(objectStoreInfo.getTableInfoId()) ? objectStoreInfo.getTableInfoId() : UUIDUtil.getUUID());
            objectStoreInfo.setCreater(data.getCreatedTableData().getCreateUser());
            objectStoreInfo.setCreaterId(data.getUserId());
            objectStoreInfo.setIsPartition(0);
            objectStoreInfo.setSearchFlag(0);
            // 可能在插入时数据库中已经存在了这一条数据
            if (updateFlag == 2) {
                log.info(String.format(">>>>>>将表名：%s中importFlag修改为：", objectStoreInfo.getTableName(), objectStoreInfo.getImportFlag()));
                LambdaUpdateWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaUpdate();
                wrapper.set(ObjectStoreInfoEntity::getImportFlag, objectStoreInfo.getImportFlag())
                        .set(ObjectStoreInfoEntity::getTableModTime, new Date())
                        .eq(ObjectStoreInfoEntity::getStoreType, objectStoreInfo.getStoreType())
                        .eq(ObjectStoreInfoEntity::getTableName, objectStoreInfo.getTableName())
                        .eq(ObjectStoreInfoEntity::getDataId, objectStoreInfo.getDataId());
                objectStoreInfoMapper.update(wrapper);
            }
            if (updateFlag == 1) {
                log.info(">>>>>>修改是否自动入库的参数请求信息：" + JSONObject.toJSONString(objectStoreInfo));
                LambdaQueryWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(ObjectStoreInfoEntity::getTableId, objectStoreInfo.getTableId());
                wrapper.eq(ObjectStoreInfoEntity::getStoreType, objectStoreInfo.getStoreType());
                wrapper.eq(ObjectStoreInfoEntity::getTableName, objectStoreInfo.getTableName());
                wrapper.eq(ObjectStoreInfoEntity::getObjectName, objectStoreInfo.getObjectName());
                wrapper.eq(ObjectStoreInfoEntity::getProjectName, objectStoreInfo.getProjectName());
                wrapper.eq(ObjectStoreInfoEntity::getDataId, objectStoreInfo.getDataId());
                if (objectStoreInfoMapper.selectCount(wrapper) > 0){
                    LambdaUpdateWrapper<ObjectStoreInfoEntity> updateWrapper = Wrappers.lambdaUpdate();
                    updateWrapper.set(ObjectStoreInfoEntity::getTableModTime, objectStoreInfo.getTableModTime());
                    updateWrapper.eq(ObjectStoreInfoEntity::getTableId, objectStoreInfo.getTableId());
                    updateWrapper.eq(ObjectStoreInfoEntity::getStoreType, objectStoreInfo.getStoreType());
                    updateWrapper.eq(ObjectStoreInfoEntity::getTableName, objectStoreInfo.getTableName());
                    updateWrapper.eq(ObjectStoreInfoEntity::getObjectName, objectStoreInfo.getObjectName());
                    updateWrapper.eq(ObjectStoreInfoEntity::getProjectName, objectStoreInfo.getProjectName());
                    updateWrapper.eq(ObjectStoreInfoEntity::getDataId, objectStoreInfo.getDataId());
                    objectStoreInfoMapper.update(updateWrapper);
                }else{
                    objectStoreInfoMapper.insert(objectStoreInfo);
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>修改是否自动入库失败：", e);
        }
    }

    @Override
    public void refreshCreateTableAll(RefreshCreateTableDTO refreshCreateTableDTO) {
        if (lock == null) {
            lock = new ReentrantLock();
        }
        if (lock.isLocked()) {
            throw new NullPointerException("正在刷新全部的建表信息，请等待别的页面刷新结束后再点击该按钮");
        }
        lock.lock();
        try {
            log.info(">>>>>>刷新全部的建表信息，开始加锁...");
            long startTime = System.currentTimeMillis();
            refreshTableHandler(refreshCreateTableDTO);
            long endTime = System.currentTimeMillis();
            log.info(String.format(">>>>>>刷新所有表入库完成，用时：%d分钟", (endTime - startTime) / 1000 / 60));
        } catch (Exception e) {
            log.error(">>>>>>刷新全部的建表信息报错：", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void refreshCreateTableProject(RefreshCreateTableDTO refreshCreateTableDTO) throws Exception {
        long startTime = System.currentTimeMillis();
        refreshTableHandler(refreshCreateTableDTO);
        long endTime = System.currentTimeMillis();
        log.info(String.format(">>>>>>刷新%s入库完成，用时：%d分钟", refreshCreateTableDTO.getProjectName(), (endTime - startTime) / 1000 / 60));
    }

    @Override
    public void refreshCreateTableOneTable(RefreshCreateTableDTO refreshCreateTableDTO) {
        try {
            refreshCreateTableDTO.setIsOneTableRefresh(true);
            refreshTableHandler(refreshCreateTableDTO);
        } catch (Exception e) {
            log.error(">>>>>>刷新单个表失败：", e);
        }
    }

    public void refreshTableHandler(RefreshCreateTableDTO dto) throws Exception {
        List<DetectedTable> detectedTableList = new ArrayList<>();
        if (dto.getIsOneTableRefresh()) {
            detectedTableList = restTemplateHandle.getTableImformationList();
            detectedTableList = detectedTableList.stream().filter(f -> f.getTableNameEN().equalsIgnoreCase(dto.getTableName())).collect(toList());
        } else if (StringUtils.isBlank(dto.getDataCenterId()) && StringUtils.isBlank(dto.getResId())) {
            detectedTableList = restTemplateHandle.getTableImformationList();
            // 如果平台中物理表删除（以仓库为准），建表信息刷新时，同步删除表和字段
            deleteObjectStoreInfo(detectedTableList, null);
        } else {
            detectedTableList = restTemplateHandle.getTablesIncludeDetectedInfo(dto.getResId(), dto.getProjectName());
            // 如果平台中物理表删除（以仓库为准），建表信息刷新时，同步删除表和字段
            deleteObjectStoreInfo(detectedTableList, dto.getProjectName());
        }
        if (detectedTableList.isEmpty() || detectedTableList.size() == 0) {
            throw new Exception(String.format("%s：从当前数据源获取到的表信息为空", ErrorCodeEnum.DATA_IS_NULL));
        }
        //删除object_store_fieldInfo中tableInfoId不存在于object_store_info中的无效数据
        objectStoreFieldInfoMapper.deleteObjectStoreFieldInfos();

        //查询标准所有表信息
        List<ObjectEntity> objectTableList = objectMapper.selectList(Wrappers.lambdaQuery());
        List<String> objectTables = objectTableList.stream().map(obj -> {
            if (StringUtils.isNotBlank(obj.getRealTablename())) {
                return obj.getRealTablename().toLowerCase();
            } else {
                return "";
            }
        }).collect(toList());
        detectedTableList = detectedTableList.stream().filter(dt -> objectTables.contains(dt.getTableNameEN().toLowerCase())).collect(toList());

        detectedTableList.stream().forEach(d -> {
            String tableInfoId = objectStoreInfoMapper.searchObjectStoreInfoId(d.getTableNameEN(), d.getProjectName(), d.getResId());
            log.info(String.format(">>>>>>开始处理：%s->%s->%s->%s，tableInfoId：%s", d.getCenterName(), d.getResName(), d.getProjectName(), d.getTableNameEN(), tableInfoId));
            ObjectEntity objectEntity = objectTableList.stream().filter(obj -> {
                        if (d.getTableNameEN() != null
                                && obj.getRealTablename() != null
                                && obj.getRealTablename().equalsIgnoreCase(d.getTableNameEN())
                                && obj.getRelateTableName().equalsIgnoreCase("OBJECTFIELD")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
            ).findFirst().orElse(null);
            if (objectEntity == null) {
                log.info(String.format(">>>>>>表：%s，在object没有记录，略过", d.getTableNameEN()));
                return;
            }
            List<FieldInfo> dataResourceFieldInfo = restTemplateHandle.requestGetTableStructure(d.getResId(), d.getProjectName(), d.getTableNameEN());
            LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectFieldEntity::getDeleted, 0);
            wrapper.eq(ObjectFieldEntity::getObjectId, objectEntity.getObjectId());
            List<ObjectFieldEntity> objectFieldInfo = objectFieldMapper.selectList(wrapper);
            String comparison = String.format("%s/%s", dataResourceFieldInfo.size(), objectFieldInfo.size());
            if (StringUtils.isBlank(tableInfoId)) {
                log.info(">>>>>>开始新增object_store_info,object_store_fieldinfo");
                String tableInfoIdNew = UUIDUtil.getUUID();
                ObjectStoreInfoEntity objectStoreInfo = createNewObjectStoreInfo(tableInfoIdNew, objectEntity, d, objectEntity.getTableId());
//                // 20250908：新疆需求，创建者置空
//                objectStoreInfo.setCreater(StringUtils.isBlank(dto.getUserName()) ? null : dto.getUserName());
//                objectStoreInfo.setCreaterId(StringUtils.isBlank(dto.getUserId()) ? null : dto.getUserId());
                objectStoreInfo.setComparison(comparison);
                //根据tableId查询objectField
                List<ObjectStoreFieldInfoEntity> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(objectFieldInfo, dataResourceFieldInfo, tableInfoIdNew);
                //使用编程式事务，对表和字段的保存进行事务管理
                transactionTemplate.execute(transactionStatus -> {
                    int saveObjectStoreInfo = objectStoreInfoMapper.insert(objectStoreInfo);
                    if (saveObjectStoreInfo == 1 && objectStoreFieldInfoList.size() != 0) {
                        objectStoreFieldInfoMapper.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                    }
                    return Boolean.TRUE;
                });
            } else {
                // 物理表字段个数与object_store_field_info中字段记录数不一致时，不再验证表的tableinfoid是否已注册（未注销）
                // 3.6.4(毛加楠)：即使物理表字段个数与object_store_field_info中字段记录数一致，也要更新
                // 3.8  (毛加楠)：只有在单表刷新已建表信息时候才比对平台表与标准表的字段是否有变化，批量全部刷新时，只比对新加字段
                log.info(">>>>>>开始新增object_store_fieldinfo");
                List<ObjectStoreFieldInfoEntity> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(objectFieldInfo, dataResourceFieldInfo, tableInfoId);
                LambdaQueryWrapper<ObjectStoreFieldInfoEntity> wrapper1 = Wrappers.lambdaQuery();
                wrapper1.eq(ObjectStoreFieldInfoEntity::getTableInfoId, tableInfoId);
                if (dto.getIsOneTableRefresh()) {
                    transactionTemplate.execute(transactionStatus -> {
                        if (objectStoreFieldInfoList.size() > 0) {
                            objectStoreFieldInfoMapper.delete(wrapper1);
                            objectStoreFieldInfoMapper.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                        }
                        return Boolean.TRUE;
                    });
                } else {
                    long osfiCount = objectStoreFieldInfoMapper.selectCount(wrapper1);
                    if (osfiCount != dataResourceFieldInfo.size()) {
                        //使用编程式事务，对表和字段的保存进行事务管理
                        transactionTemplate.execute(transactionStatus -> {
                            if (objectStoreFieldInfoList.size() > 0) {
                                objectStoreFieldInfoMapper.delete(wrapper1);
                                objectStoreFieldInfoMapper.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
                            }
                            return Boolean.TRUE;
                        });
                    }
                }
            }
        });
    }

    public void deleteObjectStoreInfo(List<DetectedTable> detectedTableList, String projectName) {
        List<String> tableNameList = detectedTableList.stream().map(d -> d.getTableNameEN().toLowerCase()).collect(Collectors.toList());
        LambdaQueryWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(projectName)) {
            wrapper.eq(ObjectStoreInfoEntity::getProjectName, projectName);
        }
        List<ObjectStoreInfoEntity> objectStoreInfos = objectStoreInfoMapper.selectList(wrapper);
        wrapper = Wrappers.lambdaQuery();
        for (ObjectStoreInfoEntity data : objectStoreInfos) {
            if (!tableNameList.contains(data.getTableName().toLowerCase())) {
                wrapper.eq(ObjectStoreInfoEntity::getTableInfoId, data.getTableInfoId());
                objectStoreInfoMapper.delete(wrapper);
            }
        }
    }

    /**
     * 资源注册时未查到相应的信息，新建并回填object_store_info信息
     *
     * @param detectedTable 已探查表信息
     * @param tableId       协议ID
     * @param tableInfoId   表存储信息ID
     * @return
     */
    public ObjectStoreInfoEntity createNewObjectStoreInfo(String tableInfoId, ObjectEntity objectEntity, DetectedTable detectedTable, String tableId) {
        ObjectStoreInfoEntity objectStoreInfo = new ObjectStoreInfoEntity();
        objectStoreInfo.setTableInfoId(tableInfoId);
        objectStoreInfo.setTableId(tableId);
        String tableName = StringUtils.isNotBlank(detectedTable.getTableNameEN()) ? detectedTable.getTableNameEN() : "";
        objectStoreInfo.setTableName(tableName);
        String objectName = StringUtils.isNotBlank(objectEntity.getDataSourceName()) ? objectEntity.getDataSourceName() : objectEntity.getRealTablename();
        objectStoreInfo.setObjectName(objectName);
        //storetype
        String tableBase = detectedTable.getResType();
        if (tableBase.equalsIgnoreCase("ads") || tableBase.equalsIgnoreCase("adb")) {
            tableBase = tableBase + "-" + detectedTable.getProjectName();
        }
        objectStoreInfo.setStoreType(KeyIntEnum.getKeyByNameAndType(tableBase, Common.DATASTORETYPE));
        String projectName = StringUtils.isNotBlank(detectedTable.getProjectName()) ? detectedTable.getProjectName() : "";
        projectName = projectName.length() > 50 ? projectName.substring(0, 50) : projectName;
        objectStoreInfo.setProjectName(projectName);
        objectStoreInfo.setMemo("");
        // 先查询数据库中，如果数据库中这个表的数据量(IMPORT_FLAG = 1)为0，则这个值为1，如果为1 ，则该值为0
        long tableCount = getObjectStoreInfoCountByTableName(objectStoreInfo);
        objectStoreInfo.setImportFlag(tableCount > 0 ? 0 : 1);
        objectStoreInfo.setIsPartition(detectedTable.getIsPartitioned() == 1 ? 0 : 1);
        //是否实时表默认为1
        objectStoreInfo.setIsActiveTable(1);
        //是分区表，默认为1，按天分区
        if (detectedTable.getIsPartitioned() == 1) {
            objectStoreInfo.setPartitionType(1);
        }
        objectStoreInfo.setLifeCycle(StringUtils.isNotBlank(detectedTable.getStoreCycle()) ? Integer.parseInt(detectedTable.getStoreCycle()) : 0);
        objectStoreInfo.setDataId(detectedTable.getResId());
        objectStoreInfo.setTableCreateTime(detectedTable.getCreateTime() != null ? detectedTable.getCreateTime() : new Date());
        //searchFlag字段写入默认值0
        objectStoreInfo.setSearchFlag(0);
        if (objectStoreInfo.getPartitionCount() == null) objectStoreInfo.setPartitionCount(0);
        return objectStoreInfo;
    }

    /**
     * 资源注册时未查到相应的信息，新建并回填object_store_info信息
     *
     * @param fieldInfoList 表字段信息
     * @param tableInfoId   表存储信息ID
     */
    public List<ObjectStoreFieldInfoEntity> createNewObjectStoreFieldInfo(List<ObjectFieldEntity> objectFieldEntities, List<FieldInfo> fieldInfoList, String tableInfoId) {
        List<ObjectStoreFieldInfoEntity> objectStoreFieldInfoEntities = new ArrayList<>();
        if (fieldInfoList != null && !fieldInfoList.isEmpty()) {
            // 处理字段信息
            fieldInfoList.stream().forEach(fieldInfo -> {
                String id = UUIDUtil.getUUID();
                ObjectStoreFieldInfoEntity objectStoreFieldInfo = new ObjectStoreFieldInfoEntity();
                objectStoreFieldInfo.setRecno(fieldInfo.getNo());
                objectStoreFieldInfo.setId(id);
                objectStoreFieldInfo.setTableInfoId(tableInfoId);
                objectStoreFieldInfo.setColumnName(fieldInfo.getFieldName());
                // 分区级别改为从仓库获取
                if (StringUtils.isNotBlank(fieldInfo.getPartitionLevel())) {
                    if (fieldInfo.getPartitionLevel().equalsIgnoreCase("一级分区")) {
                        objectStoreFieldInfo.setPartitionRecno(1);
                    } else if (fieldInfo.getPartitionLevel().equalsIgnoreCase("二级分区")) {
                        objectStoreFieldInfo.setPartitionRecno(2);
                    } else {
                        objectStoreFieldInfo.setPartitionRecno(0);
                    }
                }
                objectStoreFieldInfo.setFieldType(SynlteFieldTypeEnum.getSynlteNumType(fieldInfo.getType()));
                // 增加从仓库获取的字段类型、长度
                objectStoreFieldInfo.setColumnFieldType(fieldInfo.getType());
                if (fieldInfo.getLength() > 0) {
                    objectStoreFieldInfo.setColumnFieldLen(fieldInfo.getLength());
                    objectStoreFieldInfo.setFieldLen(fieldInfo.getLength());
                } else {
                    objectStoreFieldInfo.setFieldLen(0);
                }
                objectFieldEntities.stream().forEach(objectField -> {
                    if (fieldInfo.getFieldName().equalsIgnoreCase(objectField.getColumnName())) {
                        objectStoreFieldInfo.setFieldId(objectField.getFieldId());
                        if (StringUtils.isNotBlank(objectField.getFieldChineseName())) {
                            objectStoreFieldInfo.setFieldChineeName(objectField.getFieldChineseName());
                        } else {
                            objectStoreFieldInfo.setFieldChineeName(objectField.getColumnName());
                        }
                        if (objectField.getFieldType() != null) {
                            objectStoreFieldInfo.setFieldType(Integer.valueOf(objectField.getFieldType()));
                        }
                        objectStoreFieldInfo.setTableIndex(objectField.getIsIndex());
                        objectStoreFieldInfo.setNeedValue(objectField.getNeedValue());
                        objectStoreFieldInfo.setIndexType(objectField.getIndexType());
                        objectStoreFieldInfo.setClustRecno(String.valueOf(objectField.getClustRecno()));
                    }
                });
                objectStoreFieldInfoEntities.add(objectStoreFieldInfo);
            });
        }
        return objectStoreFieldInfoEntities;
    }

    @Override
    public PageVO searchTableInfo(ObjectStoreInfoDTO objectStoreInfoDTO) {
        PageVO pageVO = new PageVO<>();
        try {
            log.info(">>>>>>开始获取标准建表信息管理表格数据");
            if (objectStoreInfoDTO.getSort().equalsIgnoreCase("storeTypeCh")) {
                objectStoreInfoDTO.setSort("storeType");
            }
            PageHelper.startPage(objectStoreInfoDTO.getPageIndex(), objectStoreInfoDTO.getPageSize(), true, true, false);
            List<ObjectStoreInfoEntity> objectStoreInfoList = objectStoreInfoMapper.searchTableInfo(objectStoreInfoDTO);
            objectStoreInfoList.stream().forEach(d -> {
                if (d.getResId() != null) {
                    DataResource dataResource = restTemplateHandle.getResourceById(d.getResId());
                    if (dataResource != null && dataResource.getCenterId() != null) {
                        d.setCenterId(dataResource.getCenterId());
                    }
                }
                if (d.getOrganizationClassificationId() != null) {
                    d.setOrganizationClassificationCh(KeyStrEnum.getValueByKeyAndType(d.getOrganizationClassificationId(), Common.ORGANIZATIONCLASS));
                }
                d.setStoreTypeCh(KeyIntEnum.getValueByKeyAndType(d.getStoreType(), Common.DATASTORETYPE));
            });
            PageInfo<ObjectStoreInfoEntity> pageInfo = new PageInfo<>(objectStoreInfoList);
            pageVO.setTotal(pageInfo.getTotal());
            pageVO.setRows(pageInfo.getList());
            return pageVO;
        } catch (Exception e) {
            log.error(">>>>>>获取标准建表信息管理表格数据出错：", e);
            return pageVO.emptyResult();
        }
    }

    @Override
    public List<KeyValueVO> getDataResource(String dataCenterId, String storeType) {
        List<KeyValueVO> resultList = new ArrayList<>();
        try {
            String storeTypeStr;
            if (storeType != null) {
                storeTypeStr = KeyIntEnum.getValueByKeyAndType(Integer.valueOf(storeType), Common.DATASTORETYPE);
            } else {
                storeTypeStr = "";
            }
            List<DataResource> dataResourceList = restTemplateHandle.getDataResourceByCenterId(dataCenterId, "0");
            dataResourceList.stream().forEach(d -> {
                if (StringUtils.isBlank(storeType)) {
                    KeyValueVO pageSelectOneValue = new KeyValueVO(d.getResId(), d.getResName(), d.getResType());
                    resultList.add(pageSelectOneValue);
                } else {
                    if (storeTypeStr.toUpperCase().contains(d.getResType().toUpperCase())) {
                        KeyValueVO pageSelectOneValue = new KeyValueVO(d.getResId(), d.getResName(), d.getResType());
                        resultList.add(pageSelectOneValue);
                    }
                }
            });

        } catch (Exception e) {
            log.error(">>>>>>数据中心id获取数据源信息：", e);
        }
        return resultList;
    }

    @Override
    public List<String> getProjectList(String resId) throws Exception {
        List<ProjectInfo> projectList = restTemplateHandle.getProjectList(resId);
        if (projectList.isEmpty()) {
            log.error(">>>>>>查询仓库项目空间信息出错");
            throw new Exception(String.format("%s：查询项目空间为空", ErrorCodeEnum.DATA_IS_NULL));
        }
        ArrayList<String> list = new ArrayList<>();
        for (ProjectInfo data : projectList) {
            list.add(data.getProjectName());
        }
        return list;
    }

    @Override
    public BuildTableFilterVO getFilterInfo() {
        BuildTableFilterVO buildTableFilterObject = new BuildTableFilterVO();
        List<ValueLabelVO> filterObjectList = objectStoreInfoMapper.getFilterInfo();
        if (filterObjectList == null || filterObjectList.isEmpty()) {
            buildTableFilterObject.setStoreTypeList(new ArrayList<>());
            buildTableFilterObject.setResNameList(new ArrayList<>());
            buildTableFilterObject.setProjectList(new ArrayList<>());
            buildTableFilterObject.setCreatorList(new ArrayList<>());
        } else {
            // label里面为storeType需要进行枚举类来筛选
            List<ValueLabelVO> storeTypeList = new ArrayList<>();
            filterObjectList.stream().filter(d -> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getLabel(), Common.STORETYPE))
                    .forEach(d -> {
                        d.setValue(StringUtils.isBlank(d.getValue()) ? "" : d.getValue());
                        String storeType = KeyIntEnum.getValueByKeyAndType(Integer.valueOf(d.getValue()), Common.DATASTORETYPE);
                        d.setLabel(storeType);
                        storeTypeList.add(d);
                    });
            buildTableFilterObject.setStoreTypeList(storeTypeList);

            //存储数据源的设置
            List<ValueLabelVO> resNameList = new ArrayList<>();
            filterObjectList.stream().filter(d -> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getLabel(), "dataid"))
                    .forEach(d -> {
                        DataResource dataResource = restTemplateHandle.getResourceById(d.getValue());
                        if (dataResource != null) {
                            d.setLabel(dataResource.getResName());
                            d.setValue(d.getValue());
                            resNameList.add(d);
                        }
                    });
            buildTableFilterObject.setResNameList(resNameList);
            //label里面为project的直接获取就可以了
            List<ValueLabelVO> projectList = new ArrayList<>();
            filterObjectList.stream().filter(d -> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getLabel(), "projectName"))
                    .forEach(d -> {
                        d.setValue(StringUtils.isBlank(d.getValue()) ? "" : d.getValue());
                        d.setLabel(d.getValue());
                        projectList.add(d);
                    });
            buildTableFilterObject.setProjectList(projectList);
            //label里面为创建人的直接获取就可以了
            List<ValueLabelVO> creatorList = new ArrayList<>();
            filterObjectList.stream().filter(d -> StringUtils.isNotBlank(d.getValue()) && StringUtils.equalsIgnoreCase(d.getLabel(), "creater"))
                    .forEach(d -> {
                        d.setValue(StringUtils.isBlank(d.getValue()) ? "" : d.getValue());
                        d.setLabel(d.getValue());
                        creatorList.add(d);
                    });
            buildTableFilterObject.setCreatorList(creatorList);
        }
        return buildTableFilterObject;
    }

    @Override
    public void updateImportFlag(ObjectStoreInfoEntity objectStoreInfo) {
        try {
            LambdaUpdateWrapper<ObjectStoreInfoEntity> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.set(ObjectStoreInfoEntity::getImportFlag, objectStoreInfo.getImportFlag())
                    .eq(ObjectStoreInfoEntity::getTableInfoId, objectStoreInfo.getTableInfoId());
            objectStoreInfoMapper.update(updateWrapper);
        } catch (Exception e) {
            log.error(">>>>>>更新是否输出入库状态失败：", e);
        }
    }

    @Override
    public List<ObjectStoreInfoEntity> getStandardDataSet() {
        List<ObjectStoreInfoEntity> objectInfos = new ArrayList<>();
        try {
            objectInfos = objectMapper.getStandardDataSet();
            objectInfos.stream().forEach(item -> {
                if (item.getOrganizationClassificationId() != null) {
                    item.setOrganizationClassificationCh(KeyStrEnum.getValueByKeyAndType(item.getOrganizationClassificationId(), Common.ORGANIZATIONCLASS));
                }
            });
        } catch (Exception e) {
            log.error(">>>>>>获取标准数据集失败：", e);
        }
        return objectInfos;
    }

    @Override
    public List<DetectedTable> getDetectedTableList(String resId, String projectName) {
        return restTemplateHandle.getTablesIncludeDetectedInfo(resId, projectName);
    }

    @Override
    public List<ObjectFieldEntity> getStandardDataItem(String objectId) {
        List<ObjectFieldEntity> objectStoreFieldInfos = new ArrayList<>();
        try {
            LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectFieldEntity::getObjectId, objectId);
            wrapper.orderByAsc(ObjectFieldEntity::getRecno);
            objectStoreFieldInfos = objectFieldMapper.selectList(wrapper);
            objectStoreFieldInfos.stream().forEach(item -> {
                item.setFieldTypeCh(KeyIntEnum.getValueByKeyAndType(item.getFieldType(), Common.BUILDTABLEFIELD));
            });
        } catch (Exception e) {
            log.error(">>>>>>获取标准数据项失败：", e);
        }
        return objectStoreFieldInfos;
    }

    @Override
    public DetectedFieldInfoVO getDetectedFieldInfo(ObjectStoreInfoEntity objectStoreInfo) {
        DetectedFieldInfoVO detectedFieldInfoVO = new DetectedFieldInfoVO();
        try {
            // 表字段信息
            String tableName = objectStoreInfo.getTableName();
            String projectName = objectStoreInfo.getProjectName();
            String resId = objectStoreInfo.getResId();
            String tableId = objectStoreInfo.getTableId();
            List<FieldInfo> dataResourceFieldInfo = restTemplateHandle.requestGetTableStructure(resId, projectName, tableName);
            detectedFieldInfoVO.setFieldInfos(dataResourceFieldInfo);
            // 表是否已创建
            String tableInfoId = objectStoreInfoMapper.searchObjectStoreInfoId(tableName, projectName, resId);
            LambdaQueryWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectStoreInfoEntity::getTableId, tableId);
            wrapper.eq(ObjectStoreInfoEntity::getTableName, tableName);
            long createTableCount = objectStoreInfoMapper.selectCount(wrapper);
            if (StringUtils.isBlank(tableInfoId) && createTableCount < 1) {
                detectedFieldInfoVO.setExistTableInfoId(false);
            } else {
                detectedFieldInfoVO.setExistTableInfoId(true);
            }
        } catch (Exception e) {
            log.error(">>>>>>获取仓库字段信息失败：", e);
        }
        return detectedFieldInfoVO;
    }

    @Override
    public String saveCreateTableInfo(ObjectStoreInfoEntity objectStoreInfo) {
        try {
            log.info(">>>>>>开始保存建表信息");
            String tableInfoId = "";
            if (objectStoreInfo.getStoreType() == 14 || objectStoreInfo.getStoreType() == 19) {
                // 如果平台类型是kafka或ftp则验证 tableid+主题名/文件路径+dataID 的唯一性
                tableInfoId = objectStoreInfoMapper.searchObjectStoreInfoIdByTableId(objectStoreInfo.getTableName(), objectStoreInfo.getTableId(), objectStoreInfo.getDataId());
            } else {
                // 否则验证tableName+projectName+dataId 的唯一性
                tableInfoId = objectStoreInfoMapper.searchObjectStoreInfoId(objectStoreInfo.getTableName(), objectStoreInfo.getProjectName(), objectStoreInfo.getDataId());
            }
            // 字段信息
            List<ObjectStoreFieldInfoEntity> objectStoreFieldInfoList = objectStoreInfo.getObjectStoreFieldInfos();
            if (StringUtils.isBlank(tableInfoId)) {
                log.info(">>>>>>开始新增object_store_info");
                //生成object_store_info
                String infoId = UUIDUtil.getUUID();
                objectStoreInfo = buildObjectStoreInfo(objectStoreInfo, infoId);
                objectStoreFieldInfoList = buildObjectStoreFieldInfo(objectStoreFieldInfoList, infoId);
                //使用编程式事务，对表和字段的保存进行事务管理
                ObjectStoreInfoEntity finalObjectStoreInfo = objectStoreInfo;
                List<ObjectStoreFieldInfoEntity> finalObjectStoreFieldInfoList = objectStoreFieldInfoList;
                transactionTemplate.execute(transactionStatus -> {
                    int saveObjectStoreInfo = objectStoreInfoMapper.insert(finalObjectStoreInfo);
                    if (saveObjectStoreInfo == 1 && finalObjectStoreFieldInfoList.size() != 0) {
                        objectStoreFieldInfoMapper.saveObjectStoreFieldInfo(finalObjectStoreFieldInfoList);
                    }
                    return Boolean.TRUE;
                });
                operateLogServiceImpl.objectStoreInfoSuccessLog(OperateLogHandleTypeEnum.ADD, "建表信息管理", objectStoreInfo);
            } else {
                log.info("开始更新object_store_info");
                objectStoreInfo = buildObjectStoreInfo(objectStoreInfo, tableInfoId);
                objectStoreFieldInfoList = buildObjectStoreFieldInfo(objectStoreFieldInfoList, tableInfoId);
                //使用编程式事务，对表和字段的保存进行事务管理
                String finalTableInfoId = tableInfoId;
                ObjectStoreInfoEntity finalObjectStoreInfo1 = objectStoreInfo;
                List<ObjectStoreFieldInfoEntity> finalObjectStoreFieldInfoList = objectStoreFieldInfoList;
                transactionTemplate.execute(transactionStatus -> {
                    objectStoreInfoMapper.updateObjectStoreInfo(finalObjectStoreInfo1);
                    LambdaQueryWrapper<ObjectStoreFieldInfoEntity> wrapper = Wrappers.lambdaQuery();
                    wrapper.eq(ObjectStoreFieldInfoEntity::getTableInfoId, finalTableInfoId);
                    objectStoreFieldInfoMapper.delete(wrapper);
                    objectStoreFieldInfoMapper.saveObjectStoreFieldInfo(finalObjectStoreFieldInfoList);
                    return Boolean.TRUE;
                });
                operateLogServiceImpl.objectStoreInfoSuccessLog(OperateLogHandleTypeEnum.ALTER, "建表信息管理", objectStoreInfo);
            }
        } catch (Exception e) {
            log.error(">>>>>>保存建表信息出错：", e);
            return Common.SAVE_FAIL;
        }
        return Common.SAVE_SUCCESS;
    }

    public ObjectStoreInfoEntity buildObjectStoreInfo(ObjectStoreInfoEntity objectStoreInfo, String tableInfoId) {
        objectStoreInfo.setTableInfoId(tableInfoId);
        objectStoreInfo.setMemo("");
        if (objectStoreInfo.getIsPartition() == null) {
            objectStoreInfo.setIsPartition(1);
        } else {
            objectStoreInfo.setIsPartition(objectStoreInfo.getIsPartition() == 1 ? 0 : 1);
        }
        //是否实时表默认为1
        objectStoreInfo.setIsActiveTable(1);
        objectStoreInfo.setLifeCycle(StringUtils.isNotBlank(objectStoreInfo.getLifeCycleStr()) ? Integer.parseInt(objectStoreInfo.getLifeCycleStr()) : 0);
        objectStoreInfo.setTableCreateTime(new Date());
        //searchFlag字段写入默认值0
        objectStoreInfo.setSearchFlag(0);
        return objectStoreInfo;
    }

    public List<ObjectStoreFieldInfoEntity> buildObjectStoreFieldInfo(List<ObjectStoreFieldInfoEntity> objectStoreFieldInfos, String tableInfoId) {
//        // 获取数据源类型
        objectStoreFieldInfos.stream().forEach(item -> {
            String id = UUIDUtil.getUUID();
            item.setId(id);
            item.setTableInfoId(tableInfoId);
            int fieldType = -1;
            if (item.getFieldTypeCh() != null && StringUtils.isNotBlank(item.getFieldTypeCh())) {
                fieldType = KeyIntEnum.getKeyByNameAndType(item.getFieldTypeCh(), Common.BUILDTABLEFIELD);
            }
            item.setFieldType(fieldType);
            if (item.getFieldLen() == 0) {
                if (item.getFieldType() == 0) {
                    item.setFieldLen(50);
                } else if (item.getFieldType() == 2) {
                    item.setFieldLen(200);
                }
            }
            item.setDefaultValue(StringUtils.isNotBlank(item.getDefaultValue()) ? item.getDefaultValue() : "");
            // 增加从仓库获取的字段类型、长度
            item.setColumnFieldType(item.getFieldTypeCh());
            if (item.getFieldLen() > 0) {
                item.setColumnFieldLen(item.getFieldLen());
            }
        });
        return objectStoreFieldInfos;
    }

    @Override
    public ObjectStoreInfoEntity getCreateTableInfo(ObjectStoreInfoEntity objectStoreInfo) {
        try {
            String tableInfoId = "";
            if (objectStoreInfo.getStoreType() == 14 || objectStoreInfo.getStoreType() == 19) {
                // 如果平台类型是kafka或ftp则验证 tableid+主题名/文件路径+dataID 的唯一性
                tableInfoId = objectStoreInfoMapper.searchObjectStoreInfoIdByTableId(objectStoreInfo.getTableName(), objectStoreInfo.getTableId(), objectStoreInfo.getDataId());
            } else {
                // 否则验证tableName+projectName+dataId 的唯一性
                tableInfoId = objectStoreInfoMapper.searchObjectStoreInfoId(objectStoreInfo.getTableName(), objectStoreInfo.getProjectName(), objectStoreInfo.getDataId());
            }
            LambdaQueryWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectStoreInfoEntity::getTableInfoId, tableInfoId);
            ObjectStoreInfoEntity objectStoreInfoOld = objectStoreInfoMapper.selectOne(wrapper);
            if (objectStoreInfoOld != null) {
                objectStoreInfo.setTableId(objectStoreInfoOld.getTableId());
                objectStoreInfo.setIsPartition(objectStoreInfoOld.getIsPartition() == 1 ? 0 : 1);
                objectStoreInfo.setPartitionCount(objectStoreInfoOld.getPartitionCount());
                objectStoreInfo.setLifeCycle(objectStoreInfoOld.getLifeCycle());
                objectStoreInfo.setPartitionType(objectStoreInfoOld.getPartitionType());
                objectStoreInfo.setImportFlag(objectStoreInfoOld.getImportFlag());
            }
            ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, objectStoreInfo.getTableId());
            LambdaQueryWrapper<ObjectFieldEntity> wrapperOF = Wrappers.lambdaQuery();
            wrapperOF.eq(ObjectFieldEntity::getDeleted, 0);
            wrapperOF.eq(ObjectFieldEntity::getObjectId, object.getTableId());
            List<ObjectFieldEntity> standardObjectFields = objectFieldMapper.selectList(wrapperOF);

            LambdaQueryWrapper<ObjectStoreFieldInfoEntity> wrapperOSF = Wrappers.lambdaQuery();
            wrapperOSF.eq(ObjectStoreFieldInfoEntity::getTableInfoId, tableInfoId);
            wrapperOSF.orderByAsc(ObjectStoreFieldInfoEntity::getRecno);
            List<ObjectStoreFieldInfoEntity> objectStoreFieldInfos = objectStoreFieldInfoMapper.selectList(wrapperOSF);
            objectStoreFieldInfos.stream().forEach(item -> {
                standardObjectFields.stream().forEach(stand -> {
                    if (item.getFieldId() != null && stand.getFieldId() != null && item.getFieldId().equalsIgnoreCase(stand.getFieldId())) {
                        item.setToStandardField(stand.getColumnName());
                    }
                });
            });
            objectStoreInfo.setObjectStoreFieldInfos(objectStoreFieldInfos);
        } catch (Exception e) {
            log.error(">>>>>>获取建表信息出错：", e);
        }
        return objectStoreInfo;
    }

    @Override
    public boolean updateBuildTableShowField(CommonDTO showField) {
        try {
            log.info(">>>>>>更新建表信息管理页面显示字段的参数为：" + JSONObject.toJSONString(showField));
            LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
            String showFieldStr = StringUtils.join(showField.getShowField().toArray(), ",");
            if (StringUtils.isBlank(showFieldStr)) {
                showFieldStr = "notNull";
            }
            DpZcConfigFieldControlEntity entity = new DpZcConfigFieldControlEntity();
            entity.setName("TABLE_BUILDTABLE");
            entity.setOverTimeDays(90);
            entity.setShowFieldList(showFieldStr);
            entity.setUserName(authorizedUser.getUserName());
            LambdaQueryWrapper<DpZcConfigFieldControlEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DpZcConfigFieldControlEntity::getName, entity.getName());
            wrapper.eq(DpZcConfigFieldControlEntity::getUserName, entity.getUserName());
            if (dpZcConfigFieldControlMapper.selectCount(wrapper) > 0) {
                LambdaUpdateWrapper<DpZcConfigFieldControlEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(DpZcConfigFieldControlEntity::getShowFieldList, entity.getShowFieldList())
                        .eq(DpZcConfigFieldControlEntity::getName, entity.getName())
                        .eq(DpZcConfigFieldControlEntity::getUserName, entity.getUserName());
                dpZcConfigFieldControlMapper.update(updateWrapper);
            } else {
                dpZcConfigFieldControlMapper.insert(entity);
            }
            return true;
        } catch (Exception e) {
            log.error(">>>>>>更新建表信息管理页面显示字段报错：", e);
            return false;
        }
    }

    @Override
    public List<String> getBuildTableShowField() {
        List<String> listReturn = new ArrayList<>(0);
        try {
            LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
            LambdaQueryWrapper<DpZcConfigFieldControlEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DpZcConfigFieldControlEntity::getName, "TABLE_BUILDTABLE");
            wrapper.eq(DpZcConfigFieldControlEntity::getUserName, authorizedUser.getUserName());
            DpZcConfigFieldControlEntity fieldControl = dpZcConfigFieldControlMapper.selectOne(wrapper);
            if (fieldControl != null && fieldControl.getShowFieldList() != null) {
                listReturn = Arrays.asList(fieldControl.getShowFieldList().split(","));
            }
        } catch (Exception e) {
            log.error(">>>>>>获取建表信息管理页面显示字段失败：", e);
        }
        return listReturn;
    }

    @Override
    public List<KeyValueVO> getStoreTypeList() {
        List<KeyValueVO> resultList = new ArrayList<>();
        try {
            LambdaQueryWrapper<DsmAllCodeDataEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.apply("lower(CODE_ID) = {0}", "store_type");
            List<DsmAllCodeDataEntity> dataEntities = allCodeDataMapper.selectList(wrapper);
            dataEntities.stream().forEach(data -> {
                resultList.add(new KeyValueVO(data.getCodeValue(), data.getCodeText()));
            });
        } catch (Exception e) {
            log.error(">>>>>>获取平台类型列表出错：", e);
        }
        return resultList;
    }

    @Override
    public String deleteObjectStore(ObjectStoreInfoEntity objectStoreInfo) {
        try {
            LambdaQueryWrapper<PublicDataInfoEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.ne(PublicDataInfoEntity::getZyzt, "2");
            wrapper.eq(PublicDataInfoEntity::getTableInfoId, objectStoreInfo.getTableInfoId());
            List<PublicDataInfoEntity> publicDataInfoEntities = publicDataInfoMapper.selectList(wrapper);
            if (publicDataInfoEntities != null && publicDataInfoEntities.size() > 0) {
                List<String> sjzybsfList = publicDataInfoEntities.stream().map(d -> d.getSjzybsf()).collect(toList());
                String sjzybsfStr = StringUtils.join(sjzybsfList, ",");
                return String.format("当前表已注册成数据资源目录【%s】不允许删除。请先在“服务工厂”系统完成资源注销后，再进行删除操作！", sjzybsfStr);
            } else {
                LambdaQueryWrapper<ObjectStoreInfoEntity> wrapperOSI = Wrappers.lambdaQuery();
                wrapperOSI.eq(ObjectStoreInfoEntity::getTableInfoId, objectStoreInfo.getTableInfoId());
                objectStoreInfoMapper.delete(wrapperOSI);
                LambdaQueryWrapper<ObjectStoreFieldInfoEntity> wrapperOSFI = Wrappers.lambdaQuery();
                wrapperOSFI.eq(ObjectStoreFieldInfoEntity::getTableInfoId, objectStoreInfo.getTableInfoId());
                objectStoreFieldInfoMapper.delete(wrapperOSFI);

                // 操作日志
                String resId = objectStoreInfo.getDataId() == null ? objectStoreInfo.getResId() : objectStoreInfo.getDataId();
                DataResource dataResource = restTemplateHandle.getResourceById(resId);
                objectStoreInfo.setResName(dataResource.getResName());
                operateLogServiceImpl.objectStoreInfoSuccessLog(OperateLogHandleTypeEnum.DELETE, "建表信息管理", objectStoreInfo);
                return Common.DEL_SUCCESS;
            }
        } catch (Exception e) {
            log.error(">>>>>>删除objectStoreInfo信息失败：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveObjectStoreInfoFromAliyun(BuildTableInfoVO buildTableInfoVo) {
        if (Common.ODPS.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.ADS.equalsIgnoreCase(buildTableInfoVo.getDsType())) {
            ObjectStoreInfoEntity objectStoreInfo = new ObjectStoreInfoEntity();
            //查询建表信息是否存在
            String objectStoreInfoId = objectStoreInfoMapper.searchObjectStoreInfoId(buildTableInfoVo.getTableName(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getDataId());
            String uuid = UUIDUtil.getUUID();
            if (StringUtils.isNotBlank(objectStoreInfoId)) {
                objectStoreInfo.setTableInfoId(objectStoreInfoId);
            } else {
                objectStoreInfo.setTableInfoId(uuid);
            }
            objectStoreInfo.setTableId(buildTableInfoVo.getTableId());
            objectStoreInfo.setTableName(buildTableInfoVo.getTableName());
            objectStoreInfo.setObjectName(buildTableInfoVo.getTableNameCH());
            boolean flag = buildTableInfoVo.getDsType().contains("ads") || buildTableInfoVo.getDsType().contains("adb") ? true : false;
            Integer storeType = flag
                    ? KeyIntEnum.getKeyByNameAndType(buildTableInfoVo.getDsType() + "-" + buildTableInfoVo.getProjectName(), Common.DATASTORETYPE)
                    : KeyIntEnum.getKeyByNameAndType(buildTableInfoVo.getDsType(), Common.DATASTORETYPE);
            objectStoreInfo.setStoreType(storeType);
            objectStoreInfo.setProjectName(buildTableInfoVo.getSchema());
            objectStoreInfo.setMemo(buildTableInfoVo.getCreateTableMemo());
            objectStoreInfo.setCreater(buildTableInfoVo.getUserName());
            objectStoreInfo.setCreaterId(buildTableInfoVo.getUserId());
            //存储分区信息、是否实时表、生命周期
            Integer isActiveTable = buildTableInfoVo.getIsActiveTable() == null || StringUtils.isBlank(buildTableInfoVo.getIsActiveTable()) ? 1 : Integer.valueOf(buildTableInfoVo.getIsActiveTable());
            objectStoreInfo.setIsActiveTable(isActiveTable);
            //必填项 默认是分区表 0
            if (buildTableInfoVo.getIsPartition() == null) {
                buildTableInfoVo.setIsPartition(0);
            }
            objectStoreInfo.setIsPartition(buildTableInfoVo.getIsPartition());
            objectStoreInfo.setPartitionType(0 == buildTableInfoVo.getIsPartition() ? buildTableInfoVo.getPartitionType() : null);
            objectStoreInfo.setPartitionCount(buildTableInfoVo.getPartitionCount());
            objectStoreInfo.setLifeCycle(buildTableInfoVo.getLifeCycle());
            objectStoreInfo.setDataId(StringUtils.isNotBlank(buildTableInfoVo.getDataId()) ? buildTableInfoVo.getDataId() : " ");
            objectStoreInfo.setTableCreateTime(new Date());
            long tableCount = getObjectStoreInfoCountByTableName(objectStoreInfo);
            objectStoreInfo.setImportFlag(tableCount > 0 ? 0 : 1);
            if (objectStoreInfo.getStoreType() == 2 || objectStoreInfo.getStoreType() == 3) {
                objectStoreInfo.setImportFlag(0);
            }
            // 可能在插入时数据库中已经存在了这一条数据
            log.info(">>>>>>表存储的信息为：" + JSONObject.toJSONString(objectStoreInfo));
            objectStoreInfoMapper.insert(objectStoreInfo);

            log.info(">>>>>>开始将字段信息插入object_store_fieldInfo表中");
            //2023-12-05 将字段信息插入object_store_fieldInfo表中
            insertObjectStoreFieldinfoByCreateTable(buildTableInfoVo, objectStoreInfo.getTableInfoId());
        } else {
            log.info(String.format(">>>>>>数据库类型为：%s不需要将数据插入到表OBJECT_STORE_INFO中", buildTableInfoVo.getDsType()));
        }
    }

    public long getObjectStoreInfoCountByTableName(ObjectStoreInfoEntity objectStoreInfo) {
        LambdaQueryWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ObjectStoreInfoEntity::getStoreType, objectStoreInfo.getStoreType());
        wrapper.eq(ObjectStoreInfoEntity::getTableName, objectStoreInfo.getTableName());
        wrapper.eq(ObjectStoreInfoEntity::getImportFlag, 1);
        return objectStoreInfoMapper.selectCount(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveObjectStoreInfoFromHuaWei(BuildTableInfoVO buildTableInfoVo) {
        if (Common.HBASECDH.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.HBASEHUAWEI.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.HIVECDH.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.HIVEHUAWEI.equalsIgnoreCase(buildTableInfoVo.getDsType()) ||
                Common.CLICKHOUSE.equalsIgnoreCase(buildTableInfoVo.getDsType())) {
            ObjectStoreInfoEntity objectStoreInfo = new ObjectStoreInfoEntity();
            //查询建表信息是否存在
            String objectStoreInfoId = objectStoreInfoMapper.searchObjectStoreInfoId(buildTableInfoVo.getTableName(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getDataId());
            String uuid = UUIDUtil.getUUID();
            if (StringUtils.isNotBlank(objectStoreInfoId)) {
                objectStoreInfo.setTableInfoId(objectStoreInfoId);
            } else {
                objectStoreInfo.setTableInfoId(uuid);
            }
            objectStoreInfo.setTableId(buildTableInfoVo.getTableId());
            objectStoreInfo.setTableName(buildTableInfoVo.getTableName());
            objectStoreInfo.setObjectName(buildTableInfoVo.getTableNameCH());
            boolean flag = buildTableInfoVo.getDsType().contains("ads") || buildTableInfoVo.getDsType().contains("adb") ? true : false;
            Integer storeType = flag
                    ? KeyIntEnum.getKeyByNameAndType(buildTableInfoVo.getDsType() + "-" + buildTableInfoVo.getProjectName(), Common.DATASTORETYPE)
                    : KeyIntEnum.getKeyByNameAndType(buildTableInfoVo.getDsType(), Common.DATASTORETYPE);
            objectStoreInfo.setStoreType(storeType);
            if (StringUtils.isBlank(buildTableInfoVo.getProjectName())) {
                objectStoreInfo.setProjectName(buildTableInfoVo.getSchemaName());
            } else {
                objectStoreInfo.setProjectName(buildTableInfoVo.getProjectName());
            }
            objectStoreInfo.setMemo(buildTableInfoVo.getTableNameCH());
            objectStoreInfo.setCreater(buildTableInfoVo.getUserName());
            objectStoreInfo.setCreaterId(buildTableInfoVo.getUserId());
            //存储分区信息、是否实时表、生命周期
            //建表过来的实时表默认为1
            objectStoreInfo.setIsActiveTable(1);
            //必填项 默认是分区表 0
            if (buildTableInfoVo.getIsPartition() == null) {
                buildTableInfoVo.setIsPartition(0);
            }
            objectStoreInfo.setIsPartition(buildTableInfoVo.getIsPartition());
            if (0 == buildTableInfoVo.getIsPartition()) {
                objectStoreInfo.setPartitionType(buildTableInfoVo.getPartitionType());
            } else {
                objectStoreInfo.setPartitionType(null);
            }
            objectStoreInfo.setPartitionCount(buildTableInfoVo.getPartitionCount());
            objectStoreInfo.setLifeCycle(buildTableInfoVo.getLifeCycle() == null ? 0 : buildTableInfoVo.getLifeCycle());
            objectStoreInfo.setTableCreateTime(new Date());
            long tableCount = getObjectStoreInfoCountByTableName(objectStoreInfo);
            objectStoreInfo.setImportFlag(tableCount > 0 ? 0 : 1);
            objectStoreInfo.setDataId(StringUtils.isNotBlank(buildTableInfoVo.getDataId()) ? buildTableInfoVo.getDataId() : " ");
            // 可能在插入时数据库中已经存在了这一条数据
            log.info(">>>>>>表存储的信息为：" + JSONObject.toJSONString(objectStoreInfo));
            objectStoreInfoMapper.insert(objectStoreInfo);

            log.info(">>>>>>开始将字段信息插入object_store_fieldInfo表中");
            //2023-12-05 将字段信息插入object_store_fieldInfo表中
            insertObjectStoreFieldinfoByCreateTable(buildTableInfoVo, objectStoreInfo.getTableInfoId());
        } else {
            log.info(String.format(">>>>>>数据库类型为：%s不需要将数据插入到表OBJECT_STORE_INFO中", buildTableInfoVo.getDsType()));
        }
    }

    public void insertObjectStoreFieldinfoByCreateTable(BuildTableInfoVO buildTableInfoVo, String tableInfoId) {
        // 查询标准表信息
        LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ObjectEntity::getRealTablename, buildTableInfoVo.getTableName());
        wrapper.eq(ObjectEntity::getRelateTableName, "OBJECTFIELD");
        ObjectEntity objectEntity = objectMapper.selectOne(wrapper);
        if (objectEntity == null) {
            log.info(String.format(">>>>>>表：%s，在object没有记录，略过", buildTableInfoVo.getTableName()));
            return;
        }
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        buildTableInfoVo.getColumnData().stream().forEach(d -> {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setNo(d.getRecno());
            fieldInfo.setFieldName(d.getColumnName());
            fieldInfo.setType(String.valueOf(d.getFieldType()));
            fieldInfo.setLength(d.getFieldLen());
            if (d.getPartitionRecno() != null) {
                if (d.getPartitionRecno() == 1) {
                    fieldInfo.setPartitionLevel("一级分区");
                } else if (d.getPartitionRecno() == 2) {
                    fieldInfo.setPartitionLevel("二级分区");
                }
            }
            fieldInfoList.add(fieldInfo);
        });
        // 构建字段信息
        LambdaQueryWrapper<ObjectFieldEntity> wrapperOF = Wrappers.lambdaQuery();
        wrapperOF.eq(ObjectFieldEntity::getDeleted, 0);
        wrapperOF.eq(ObjectFieldEntity::getObjectId, objectEntity.getObjectId());
        List<ObjectFieldEntity> objectFieldEntities = objectFieldMapper.selectList(wrapperOF);
        List<ObjectStoreFieldInfoEntity> objectStoreFieldInfoList = createNewObjectStoreFieldInfo(objectFieldEntities, fieldInfoList, tableInfoId);
        //保存信息，使用编程式事务，对表和字段的保存进行事务管理
        transactionTemplate.execute(transactionStatus -> {
            if (objectStoreFieldInfoList.size() > 0) {
                LambdaQueryWrapper<ObjectStoreFieldInfoEntity> wrapperOSFI = Wrappers.lambdaQuery();
                wrapperOSFI.eq(ObjectStoreFieldInfoEntity::getTableInfoId, tableInfoId);
                objectStoreFieldInfoMapper.delete(wrapperOSFI);
                objectStoreFieldInfoMapper.saveObjectStoreFieldInfo(objectStoreFieldInfoList);
            }
            return Boolean.TRUE;
        });
    }

    @Override
    public void downloadObjectStoreInfo(HttpServletResponse response) {
        try {
            ObjectStoreInfoDTO objectStoreInfoDTO = new ObjectStoreInfoDTO();
            if (objectStoreInfoDTO.getSort().equalsIgnoreCase("storeTypeCh")) {
                objectStoreInfoDTO.setSort("storeType");
            }
            List<ObjectStoreInfoEntity> objectStoreInfoList = objectStoreInfoMapper.searchTableInfo(objectStoreInfoDTO);
            objectStoreInfoList.stream().forEach(d -> {
                d.setStoreTypeCh(KeyIntEnum.getValueByKeyAndType(d.getStoreType(), Common.DATASTORETYPE));
            });
            ObjectStoreInfoEntity objectStoreInfo = new ObjectStoreInfoEntity();
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("建表信息管理管理", "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), objectStoreInfo.getClass()).autoCloseStream(Boolean.FALSE).sheet("建表信息管理管理").doWrite(objectStoreInfoList);
        } catch (Exception e) {
            log.error(">>>>>>下载建表信息管理出错：", e);
        }
    }


}
