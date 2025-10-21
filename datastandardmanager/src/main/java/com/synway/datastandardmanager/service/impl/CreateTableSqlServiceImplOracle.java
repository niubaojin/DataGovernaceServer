package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CkColumnVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableDataVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.mapper.ObjectMapper;
import com.synway.datastandardmanager.service.CreateTableSqlService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.SelectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * oracle建表的相关接口信息
 *
 * @author niubaojin
 * @date 2023年4月6日09:32:56
 */
@Slf4j
public class CreateTableSqlServiceImplOracle implements CreateTableSqlService {

    /**
     * 相关的样例代码为
     */
    private static final String ADD_COLUMN_SQL = "alter table %s add (%s %s)";

    @Override
    public String getAddColumnSql(CreateTableInfoDTO data) {
        log.info(">>>>>>开始拼接oracle新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if (data.getColumnList().isEmpty()) {
            log.error(">>>>>>数据库中的建表字段为空，无法生成新增字段语句");
            throw new NullPointerException("新增字段信息为空，无法生成新增字段语句");
        }
        List<String> list = new ArrayList<>();
        data.getColumnList().forEach(d -> {
            String columnType = d.getColumnType();
            if (columnType.equalsIgnoreCase("varchar2") || columnType.equalsIgnoreCase("float")) {
                if (d.getColumnLen() != null) {
                    columnType = columnType + "(" + d.getColumnLen() + ")";
                }
            }
            list.add(String.format(ADD_COLUMN_SQL, tableNameEn, d.getColumnEngname(), columnType));
            list.add(String.format("comment on column %s.%s is '%s'", tableNameEn, d.getColumnEngname(), StringUtils.isBlank(d.getColumnChinese()) ? "" : d.getColumnChinese()));
        });
        return StringUtils.join(list, "; ");
    }

    @Override
    public String addColumnByData(CreateTableInfoDTO data) {
        String sqlStr = this.getAddColumnSql(data);
        log.info(">>>>>>生成oracle的sql语句为：" + sqlStr);
        if (StringUtils.isBlank(sqlStr)) {
            throw new NullPointerException("生成的sql语句为空");
        }
        if (StringUtils.isBlank(data.getCreatedTableData().getDataId())) {
            log.error(String.format("表[%s]对应的参数dataId为空，不能新增字段信息", data.getCreatedTableData().getTableNameEn()));
            throw new NullPointerException(String.format("表[%s]对应的参数dataId为空，不能新增字段信息", data.getCreatedTableData().getTableNameEn()));
        }
        CreateTableDataVO aliyunAddColumn = new CreateTableDataVO();
        aliyunAddColumn.setTableId(data.getCreatedTableData().getTableNameEn());
        aliyunAddColumn.setResId(data.getCreatedTableData().getDataId());
        aliyunAddColumn.setData(sqlStr);
        aliyunAddColumn.setType(data.getCreatedTableData().getTableBase());
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        // 发送建表语句给仓库
        return restTemplateHandle.sendAddColumnInfo(aliyunAddColumn);
    }

    /**
     * ck的建表格式 需要创建分布式的表
     */
    @Override
    public String getCreateSql(BuildTableInfoVO buildTableInfoVo) throws Exception {
        // 字段列表
        List<ObjectFieldEntity> columnList = buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        // 建表语句
        StringBuilder stringBuilder = new StringBuilder();
        if (buildTableInfoVo.getIsCreatTableTool()) {
            stringBuilder.append("create table ").append(tableName.toLowerCase()).append(" ( \n");
        } else {
            stringBuilder.append("create table ").append(projectName.toLowerCase()).append(".").append(tableName.toLowerCase()).append(" ( \n");
        }
        for (ObjectFieldEntity tc : columnList) {
            if (StringUtils.isBlank(tc.getColumnName())) {
                throw SystemException.asSystemException(ErrorCodeEnum.UNION_ERROR, "存在建表字段为空值的情况");
            }
            // 字段类型
            String createColumnType = tc.getCreateColumnType();
            // 字段长度
            int createColumnLen = tc.getCreateColumnLen();
            if (StringUtils.isBlank(createColumnType)) {
                throw SystemException.asSystemException(ErrorCodeEnum.UNION_ERROR, "字段" + tc.getColumnName() + "没有配置建表类型");
            }
            if (createColumnLen <= 0 && createColumnType.equalsIgnoreCase("varchar2")) {
                throw SystemException.asSystemException(ErrorCodeEnum.UNION_ERROR, "字段" + tc.getColumnName() + "没有配置字段长度");
            }
            // 字段类型
            stringBuilder.append("    ").append(tc.getColumnName().toLowerCase()).append("    ").append(createColumnType);
            if (createColumnType.equalsIgnoreCase("varchar2") || createColumnType.equalsIgnoreCase("float")) {
                stringBuilder.append("(").append(tc.getCreateColumnLen()).append(")");
            }
            // 是否必填
            if (tc.getNeedValue() == 1) {
                stringBuilder.append(" not null");
            }
            // 主键
            if (tc.getPkRecnoStatus() != null && tc.getPkRecnoStatus() && !stringBuilder.toString().contains("primary key")) {
                stringBuilder.append(" primary key");
            }
            stringBuilder.append(",\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("\n);\n");
        // 表注释
        if (StringUtils.isNotBlank(buildTableInfoVo.getTableNameCH())) {
            if (buildTableInfoVo.getIsCreatTableTool()) {
                stringBuilder.append(String.format("comment on table %s is '%s'", tableName, buildTableInfoVo.getTableNameCH())).append(";\n");
            } else {
                stringBuilder.append(String.format("comment on table %s.%s is '%s'", projectName, tableName, buildTableInfoVo.getTableNameCH())).append(";\n");
            }
        }
        // 字段注释、索引
        String indexSql = "";
        for (ObjectFieldEntity tc : columnList) {
            String fieldChineseName = tc.getFieldChineseName();
            if (StringUtils.isNotBlank(fieldChineseName)) {
                if (buildTableInfoVo.getIsCreatTableTool()) {
                    stringBuilder.append(String.format("comment on column %s.%s is '%s'", tableName, tc.getColumnName(), fieldChineseName)).append(";\n");
                } else {
                    stringBuilder.append(String.format("comment on column %s.%s.%s is '%s'", projectName, tableName, tc.getColumnName(), fieldChineseName)).append(";\n");
                }
            }
            if (tc.getIsIndex() != 0 && tc.getIndexType() != 0) {
                String indexSqlTemp = "";
                if (buildTableInfoVo.getIsCreatTableTool()) {
                    indexSqlTemp = String.format("create unique index %s_%s on %s.%s(%s);\n", tableName, tc.getColumnName(), projectName, tableName, tc.getColumnName());
                } else {
                    indexSqlTemp = String.format("create unique index %s.%s_%s on %s.%s(%s);\n", projectName, tableName, tc.getColumnName(), projectName, tableName, tc.getColumnName());
                }
                indexSql += indexSqlTemp;
            }
        }
        // 建立索引
        stringBuilder.append(indexSql);
        return stringBuilder.toString();
    }

    private List<String> getPkColumnList(List<CkColumnVO> list, int size, String type) {
        List<String> strList = new ArrayList<>(size);
        for (CkColumnVO ckColumn : list) {
            if (StringUtils.equalsIgnoreCase(ckColumn.getPartitionType(), Common.COLUMN)) {
                strList.add(ckColumn.getColumnName().toLowerCase());
            } else if (StringUtils.equalsIgnoreCase(ckColumn.getPartitionType(), Common.DATE_FUNCTION)) {
                strList.add(ckColumn.getPartitionFunction() + "(" + ckColumn.getColumnName().toLowerCase() + ")");
            } else {
                throw SystemException.asSystemException(ErrorCodeEnum.UNION_ERROR, String.format("%s字段[%s]类型为[%s]，没有配置处理类",
                        type, ckColumn.getColumnName(), ckColumn.getPartitionType()));
            }
        }
        return strList;
    }

    @Override
    public boolean updateTableName(String oldTableName, String newTableName) {
        return false;
    }

    /**
     * 创建oracle表信息
     */
    @Override
    public String createTableByPage(BuildTableInfoVO buildTableInfoVo) {
        String message;
        try {
            log.info(">>>>>>oracle建表的相关信息为：" + JSONObject.toJSONString(buildTableInfoVo));
            RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
            this.createTableBeforeCheck(buildTableInfoVo);
            String dataId = buildTableInfoVo.getDataId();
            String createSql = this.getCreateSql(buildTableInfoVo);
            log.info(">>>>>>生成的sql语句为：\n" + createSql);
            CreateTableDataVO huaWeiCreateTable = new CreateTableDataVO();
            huaWeiCreateTable.setData(createSql.replaceAll("\\n", " "));
            huaWeiCreateTable.setType("oracle");
            huaWeiCreateTable.setResId(dataId);
            huaWeiCreateTable.setTableId(buildTableInfoVo.getTableId());
            huaWeiCreateTable.setTableName(buildTableInfoVo.getSchema() + "." + buildTableInfoVo.getTableName());
            message = restTemplateHandle.sendCreateTableInfo(huaWeiCreateTable);
        } catch (Exception e) {
            log.error(">>>>>>oracle建表失败：", e);
            throw SystemException.asSystemException(ErrorCodeEnum.CREATE_TABLE_ERROR, e.getMessage());
        }
        return message;
    }

    /**
     * 建表之前的参数检查 并返回tableId
     */
    @Override
    public String createTableBeforeCheck(BuildTableInfoVO buildTableInfoVo) throws Exception {
        String tableId = buildTableInfoVo.getTableId();
        ObjectMapper objectMapper = SpringBeanUtil.getBean(ObjectMapper.class);
        long temp = SelectUtil.countObjectByTableId(objectMapper, tableId, buildTableInfoVo.getTableName());
        if (temp == 0) {
            throw new NullPointerException("标准中无表名或协议编号信息!!!");
        }
        String dataId = buildTableInfoVo.getDataId();
        if (StringUtils.isEmpty(dataId)) {
            throw new NullPointerException("数据源ID为空，请选择具体的数据源信息");
        }
        // 检查必填参数是否存在
        String createSql = this.getCreateSql(buildTableInfoVo);
        if (StringUtils.isBlank(createSql)) {
            throw new NullPointerException("生成的建表sql为空");
        }
        return tableId;
    }

}
