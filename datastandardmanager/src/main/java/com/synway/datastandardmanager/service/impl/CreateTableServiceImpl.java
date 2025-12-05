package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableDataVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableVO;
import com.synway.datastandardmanager.enums.*;
import com.synway.datastandardmanager.mapper.DsProjectTableMapper;
import com.synway.datastandardmanager.mapper.ObjectMapper;
import com.synway.datastandardmanager.mapper.ObjectStoreInfoMapper;
import com.synway.datastandardmanager.mapper.SynlteFieldMapper;
import com.synway.datastandardmanager.service.CreateTableService;
import com.synway.datastandardmanager.service.CreateTableSqlService;
import com.synway.datastandardmanager.util.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CreateTableServiceImpl implements CreateTableService {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectStoreInfoMapper objectStoreInfoMapper;
    @Resource
    private DsProjectTableMapper dsProjectTableMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    private Environment env;


    @Override
    public CreateTableVO buildAdsOrOdpsTable(BuildTableInfoVO buildTableInfoVo) {
        CreateTableVO createTableVO = new CreateTableVO();
        try {
            String tableId = buildTableInfoVo.getTableId();
            List<ObjectFieldEntity> objectFields = buildTableInfoVo.getColumnData();
            if (StringUtils.isBlank(tableId)) {
                throw new NullPointerException("需要指定协议编号！！！");
            }
            if (null == objectFields || objectFields.size() == 0) {
                throw new NullPointerException("字段信息不能为空！！！");
            }
            String tableName = buildTableInfoVo.getTableName();
            if (StringUtils.isBlank(tableName)) {
                throw new NullPointerException("需要指定表名信息！！！");
            }
            String tableNameCH = buildTableInfoVo.getTableNameCH();
            if (StringUtils.isBlank(tableNameCH)) {
                throw new NullPointerException("需要指定表中文名！！！");
            }
            String targetDbType = buildTableInfoVo.getDsType();
            if (StringUtils.isBlank(targetDbType)) {
                throw new NullPointerException("前端传输的目标库类型为空!!!");
            }
            if (SelectUtil.countObjectByTableId(objectMapper, tableId, tableName) == 0) {
                throw new NullPointerException("标准中无表名或协议编号信息!!!");
            }
            if (StringUtils.isBlank(buildTableInfoVo.getProjectName())) {
                buildTableInfoVo.setProjectName(buildTableInfoVo.getSchema());
            }
            int tableCount = dsProjectTableMapper.searchDataResourceTable(buildTableInfoVo.getDataId(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getTableName());
            if (tableCount == 1) {
                throw new Exception(String.format("%s：仓库已存在该表", ErrorCodeEnum.CHECK_UNION_ERROR));
            }
            for (ObjectFieldEntity objectField : objectFields) {
                if (StringUtils.isEmpty(objectField.getCreateColumnType())) {
                    throw new NullPointerException("建表字段类型没有匹配成功，请点击高级按钮手动获取建表类型");
                }
            }
            // 生成sql的相关信息
            String handleClaStr = DataBaseTypeEnum.getCla(buildTableInfoVo.getDsType() + "_addcolumn");
            CreateTableSqlService tableColumnHandle = (CreateTableSqlService) Class.forName(handleClaStr).newInstance();
            String sql = tableColumnHandle.getCreateSql(buildTableInfoVo);
            // 发送建表信息
            CreateTableDataVO createTableData = new CreateTableDataVO();
            createTableData.setData(sql);
            createTableData.setResId(buildTableInfoVo.getDataId());
            createTableData.setTableId(buildTableInfoVo.getTableId());
            createTableData.setTableName(String.format("%s.%s", buildTableInfoVo.getSchema(), buildTableInfoVo.getTableName()));
            createTableData.setType(buildTableInfoVo.getDsType());
            log.info(">>>>>>建表发送的信息为：" + JSONObject.toJSONString(createTableData));
            // 发送建表的相关sql
            String result = restTemplateHandle.sendCreateTableInfo(createTableData);
            createTableVO.setMessage(result);
//            // 建表成功之后将相关信息插入到资产表中
//            insertCreateTableInfo(buildTableInfoVo);
        } catch (Exception e) {
            log.error(">>>>>>建表失败：", e);
            createTableVO.setMessage(e.getMessage());
        }
        return createTableVO;
    }

    @Override
    public List<KeyValueVO> getColumnType(String dataBaseType) {
        List<KeyValueVO> resultList = new ArrayList<>();
        try {
            log.info(">>>>>>开始获取数据库【" + dataBaseType + "】的字段类型");
            Map<String, String> columnType = DataBaseColumnTypeUtil.getFieldTypeMap(dataBaseType);
            for (String keyName : columnType.keySet()) {
                KeyValueVO keyValueVO = new KeyValueVO(keyName, columnType.get(keyName));
                resultList.add(keyValueVO);
            }
        } catch (Exception e) {
            log.error(">>>>>>获取数据库字段类型失败：", e);
        }
        return resultList;
    }

    @Override
    public List<ObjectFieldEntity> columnCorrespondClick(JSONObject jsonObject) {
        List<ObjectFieldEntity> columnList = new ArrayList<>();
        try {
            log.info(">>>>>>开始对表字段进行一键映射");
            String dataBaseType = jsonObject.getString("dataBaseType");
            if (StringUtils.isEmpty(dataBaseType)) {
                log.error(">>>>>>传入的数据库类型为空");
                throw new Exception("传入的数据库类型为空");
            }
            columnList = jsonObject.getJSONArray("columnData").toJavaList(ObjectFieldEntity.class);
            Map<String, String> resultColumnMap = null;
            if ("ODPS".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("oracle", "odps");
            } else if ("ADS".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("oracle", "ads");
            } else if ("HBASE-CDH".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize", "HBASE-CDH");
            } else if ("HBASE-HUAWEI".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize", "HBASE-HUAWEI");
            } else if ("HIVE-CDH".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize", "HIVE-CDH");
            } else if ("HIVE-HUAWEI".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize", "HIVE-HUAWEI");
            } else if ("DATAHUB".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize", "datahub");
            } else if ("CLICKHOUSE".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize", "CLICKHOUSE");
            } else if ("ORACLE".equalsIgnoreCase(dataBaseType)) {
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("oracle", "oracle");
            } else {
                throw new Exception("传入的数据库类型不是ODPS/ADS/HBASE/hive/datahub/CLICKHOUSE/ORACLE");
            }
            for (ObjectFieldEntity oneObjectField : columnList) {
                String standardColumnType = "";
                if (oneObjectField.getFieldType() == null) {
                    standardColumnType = "";
                } else {
                    standardColumnType = SynlteFieldTypeEnum.getSynlteFieldType(oneObjectField.getFieldType());
                }
                if (StringUtils.isNotEmpty(oneObjectField.getCreateColumnType())) {
                    continue;
                }
                if (StringUtils.isNotEmpty(standardColumnType)) {
                    String createColumnType = resultColumnMap.get(standardColumnType);
                    oneObjectField.setCreateColumnType(createColumnType);
                    oneObjectField.setCreateColumnLen(oneObjectField.getFieldLen());
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>获取数据库报错：", e);
        }
        return columnList;
    }

    @Override
    public String showCreateTableSql(BuildTableInfoVO buildTableInfoVo) {
        String sql = "";
        try {
            log.info(">>>>>>开始创建表SQL");
            if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")){
                buildTableInfoVo.setIsCreatTableTool(true);
            }else {
                buildTableInfoVo.setIsCreatTableTool(false);
            }
            String dataBaseType = buildTableInfoVo.getDsType();
            String handleClaStr = DataBaseTypeEnum.getCla(dataBaseType + "_addcolumn");
            CreateTableSqlService tableColumnHandle = (CreateTableSqlService) Class.forName(handleClaStr).newInstance();
            sql = tableColumnHandle.getCreateSql(buildTableInfoVo);
//            // 格式化sql
//            sql = SqlFormatterUtil.formatSql(sql);
        } catch (Exception e) {
            log.error(">>>>>>创建建表SQL报错：", e);
        }
        return sql;
    }

    @Override
    public List<DsmStandardTableCreatedEntity> getAllStandardTableCreatedList(String tableId) {
        List<DsmStandardTableCreatedEntity> resultAll = new ArrayList<>();
        try {
            log.info(">>>>>>开始获取已经创建的表信息");
            if (StringUtils.isEmpty(tableId)) {
                throw new Exception("传入的表协议ID为空，获取已建表信息报错");
            }
            // 先根据tableId获取对应的表名
            ObjectEntity objectEntity = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
            if (objectEntity == null || StringUtils.isEmpty(objectEntity.getRealTablename())) {
                throw new Exception(String.format("传入的tableId：%s,从OBJECT表中获取到的英文表名为空", tableId));
            }
            String objectId = String.valueOf(objectEntity.getObjectId());
            LambdaQueryWrapper<ObjectStoreInfoEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectStoreInfoEntity::getTableId, tableId);
            wrapper.orderByDesc(ObjectStoreInfoEntity::getStoreType);
            List<ObjectStoreInfoEntity> objectStoreInfoEntities = objectStoreInfoMapper.selectList(wrapper);
            for (ObjectStoreInfoEntity objectStoreInfo : objectStoreInfoEntities) {
                DsmStandardTableCreatedEntity createdEntity = new DsmStandardTableCreatedEntity();
                String tableType = KeyIntEnum.getValueByKeyAndType(objectStoreInfo.getStoreType(), Common.DATASTORETYPE);
                createdEntity.setObjectId(objectId);
                createdEntity.setTableBase(StringUtils.isNotBlank(tableType) ? tableType : "");
                createdEntity.setTableId(objectStoreInfo.getTableId());
                createdEntity.setTableName(objectStoreInfo.getTableName());
                createdEntity.setTableNameEn(objectStoreInfo.getProjectName() + "." + objectStoreInfo.getTableName());
                createdEntity.setTableNameCh(objectStoreInfo.getObjectName());
                createdEntity.setTableProject(objectStoreInfo.getProjectName());
                createdEntity.setCreateUser(objectStoreInfo.getCreater());
                createdEntity.setDataId(objectStoreInfo.getDataId());
                createdEntity.setCreateTime(objectStoreInfo.getTableCreateTime());
                createdEntity.setImportFlag(objectStoreInfo.getImportFlag());
                resultAll.add(createdEntity);
            }
        } catch (Exception e) {
            log.error(">>>>>>获取已经创建的表信息报错：", e);
        }
        return resultAll;
    }

    @Override
    public CreateTableVO createHuaWeiTableService(BuildTableInfoVO buildTableInfoVo) {
        CreateTableVO createTableVO = new CreateTableVO();
        try {
            if (StringUtils.isBlank(buildTableInfoVo.getProjectName())) {
                buildTableInfoVo.setProjectName(buildTableInfoVo.getSchema());
            }
            int tableCount = dsProjectTableMapper.searchDataResourceTable(buildTableInfoVo.getDataId(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getTableName());
            if (tableCount == 1) {
                throw new Exception(String.format("%s：仓库已存在该表", ErrorCodeEnum.CHECK_UNION_ERROR));
            }
            String dataBaseType = buildTableInfoVo.getDsType();
            String handleClaStr = DataBaseTypeEnum.getCla(dataBaseType + "_addcolumn");
            if (StringUtils.isBlank(handleClaStr)) {
                throw new NullPointerException("没有编写" + dataBaseType + "类型的建表处理类");
            }
            CreateTableSqlService tableColumnHandle = (CreateTableSqlService) Class.forName(handleClaStr).newInstance();
            String message = tableColumnHandle.createTableByPage(buildTableInfoVo);
            createTableVO.setMessage(message);
        } catch (Exception e) {
            log.error(">>>>>>创建华为平台相关表报错：", e);
            createTableVO.setMessage(e.getMessage());
        }
        return createTableVO;
    }

    @Override
    public List<ObjectFieldEntity> getCommonColumnService(List<ObjectFieldEntity> objectFieldEntities) {
        List<ObjectFieldEntity> needColumnList = new ArrayList<>();
        try {
            log.info(">>>>>>传入的所有字段信息为：" + JSONObject.toJSONString(objectFieldEntities));
            //  先获取页面中字段最大字段序号
            int maxRecnoInt = -1;
            if (objectFieldEntities != null && !objectFieldEntities.isEmpty()) {
                Optional<Integer> maxRecno = objectFieldEntities.stream().map(ObjectFieldEntity::getRecno).distinct().collect(Collectors.toList()).stream().reduce(Integer::max);
                if (maxRecno.isPresent()) {
                    maxRecnoInt = maxRecno.get();
                }
            }
            // 获取字段的信息
            // 20200605 字段从数据库中获取
            List<String> fieldIdList = Arrays.asList(new String[]{"03J0002", "09E0009", "08A0007", "03S0005", "08A0034", "09A0009", "08A0014", "08A0001", "08A0005", "08A0006", "18A0903", "08A0060"});
            List<SynlteFieldEntity> allCommonList = new ArrayList<>();
            for (String fieldId : fieldIdList) {
                LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(SynlteFieldEntity::getFieldId, fieldId);
                SynlteFieldEntity objectFieldStandard = synlteFieldMapper.selectOne(wrapper);
                if (objectFieldStandard != null) {
                    allCommonList.add(objectFieldStandard);
                }
            }
            // 拼接一个查询信息
            List<String> queryList = allCommonList.stream().filter(Objects::nonNull).map(SynlteFieldEntity::getFieldId).distinct().collect(Collectors.toList());
            Map<String, List<SynlteFieldEntity>> allCoumnMap = allCommonList.stream().collect(Collectors.groupingBy(SynlteFieldEntity::getFieldId));
            //  判断指定objectId里面是否有公共字段
            if (objectFieldEntities != null) {
                objectFieldEntities.forEach((element) -> {
                    if (StringUtils.isNotEmpty(element.getFieldId()) &&
                            queryList.contains(element.getFieldId().toUpperCase())) {
                        allCoumnMap.remove(element.getFieldId());
                    }
                });
            }
            // 存储需要添加的公共字段信息
            for (String onekey : allCoumnMap.keySet()) {
                SynlteFieldEntity oneObjectField = allCoumnMap.get(onekey).get(0);
                maxRecnoInt++;
                ObjectFieldEntity objectField = JSONObject.parseObject(JSONObject.toJSONString(oneObjectField), ObjectFieldEntity.class);
                objectField.setRecno(maxRecnoInt);
                objectField.setStandardRecno(maxRecnoInt);
                objectField.setUpdateStatus(1);
                objectField.setMd5IndexStatus(false);
                objectField.setPkRecnoStatus(false);
                objectField.setClustRecnoStatus(false);
                if (onekey.equalsIgnoreCase("09A0009") || onekey.equalsIgnoreCase("08A0014")
                        || onekey.equalsIgnoreCase("08A0006") || onekey.equalsIgnoreCase("08A0005")
                        || onekey.equalsIgnoreCase("08A0001")) {
                    objectField.setNeedValue(0);
                    objectField.setNeedv("0");
                }
                needColumnList.add(objectField);
            }
            log.info(">>>>>>需要添加的公共字段信息为：" + JSONObject.toJSONString(needColumnList));
        } catch (Exception e) {
            log.error(">>>>>>获取公共字段信息失败：", e);
        }
        return needColumnList;
    }

    @Override
    public List<KeyValueVO> getPartitionType() {
        List<KeyValueVO> result = new ArrayList<>();
        result.add(new KeyValueVO("1", "按天分区"));
        result.add(new KeyValueVO("2", "按周分区"));
        result.add(new KeyValueVO("3", "按月分区"));
        result.add(new KeyValueVO("0", "其它"));
        return result;
    }

}
