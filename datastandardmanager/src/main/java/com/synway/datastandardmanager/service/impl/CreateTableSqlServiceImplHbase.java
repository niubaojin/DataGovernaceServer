package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableDataVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.EsTypeEnum;
import com.synway.datastandardmanager.enums.RegionTypeEnum;
import com.synway.datastandardmanager.mapper.ObjectMapper;
import com.synway.datastandardmanager.service.CreateTableSqlService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.SelectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName HbaseTableColumnHandle
 * @description TODO
 * @date 2020/9/26 13:37
 */
@Slf4j
public class CreateTableSqlServiceImplHbase implements CreateTableSqlService {

    @Override
    public String getAddColumnSql(CreateTableInfoDTO data) {
        log.info("开始拼接hbase新增字段的相关操作");
        return null;
    }

    @Override
    public String addColumnByData(CreateTableInfoDTO data) {
        return null;
    }

    @Override
    public String getCreateSql(BuildTableInfoVO buildTableInfoVo) {
        List columnList = Lists.newArrayList();
        final String[] esType = {"day"};
        final String[] regiontype = {"day"};
        Arrays.stream(EsTypeEnum.values()).forEach(ele -> {
            if (buildTableInfoVo.getEsSplitType() == ele.getType()) {
                esType[0] = ele.getName();
            }
        });
        Arrays.stream(RegionTypeEnum.values()).forEach(ele -> {
            if (buildTableInfoVo.getRegionType() == ele.getCode()) {
                regiontype[0] = ele.getName();
            }
        });
        String properties = String.format("(estype='%s',escount=%d,esshard=%d,regiontype='%s',regioncount=%d,lifecycle=%d,geohashredundant=%b)",
                esType[0], buildTableInfoVo.getEsSplitCount(), buildTableInfoVo.getEsShards(), regiontype[0], buildTableInfoVo.getRegionCount(),
                buildTableInfoVo.getLifeCycle(), buildTableInfoVo.getGeoRedundant());
        List<ObjectFieldEntity> columns = new ArrayList<>(buildTableInfoVo.getColumnData());
        columns.stream().forEach(col -> {
            if (col.getRecno() < 0) {
                throw new RuntimeException("column recno less 1");
            }
            if (!col.getColumnName().matches("[a-zA-Z]+.*")) {
                throw new RuntimeException("columnName must be start with letters");
            }
            String[] columnType = {"varchar"};
            columnType[0] = col.getCreateColumnType();
            String column;
            String columnCh = col.getFieldChineseName();
            if (StringUtils.isEmpty(columnCh)) {
                columnCh = " ";
            }
            if (columnType[0].equals("varchar")) {
                column = String.format("%s %s(%d) COMMENT '%s' with (rowkey=%b,index=%b,source=%b,store=%b,docvalue=%b,filter=%b,md5=%d)", col.getColumnName()
                        , columnType[0], col.getFieldLen(), columnCh, col.getIsRowkey() == null ? false : col.getIsRowkey() == 0 ? false : true, col.getIsIndexs(), col.getIsSource() == null ? false : col.getIsSource() == 0 ? false : true, col.getIsStore() == null ? false : col.getIsStore() == 0 ? false : true,
                        col.getIsDocval() == null ? false : col.getIsDocval() == 0 ? false : true, col.getIsFilter() == null ? false : col.getIsFilter() == 0 ? false : true, col.getMd5Index() == null ? 0 : col.getMd5Index());
            } else {
                column = String.format("%s %s COMMENT '%s' with (rowkey=%b,index=%b,source=%b,store=%b,docvalue=%b,filter=%b,md5=%d)", col.getColumnName()
                        , columnType[0], columnCh, col.getIsRowkey() == null ? false : col.getIsRowkey() == 0 ? false : true, col.getIsIndexs(), col.getIsSource() == null ? false : col.getIsSource() == 0 ? false : true, col.getIsStore() == null ? false : col.getIsStore() == 0 ? false : true,
                        col.getIsDocval() == null ? false : col.getIsDocval() == 0 ? false : true, col.getIsFilter() == null ? false : col.getIsFilter() == 0 ? false : true, col.getMd5Index() == null ? 0 : col.getMd5Index());
            }
            columnList.add(column);
        });
        String tableName = String.format("%s.%s", buildTableInfoVo.getSchema(), buildTableInfoVo.getTableName().toUpperCase());
        if (buildTableInfoVo.getIsCreatTableTool()) {
            tableName = buildTableInfoVo.getTableName().toUpperCase();
        }
        String sql = String.format("create table %s (%s) comment '%s' with %s", tableName,
                StringUtils.strip(columnList.toString(), "[]"),
                buildTableInfoVo.getPrestoMemo(), properties);
        return sql;
    }


    /**
     * 创建hbase平台的相关数据
     */
    @Override
    public String createTableByPage(BuildTableInfoVO buildTableInfoVo) {
        String message;
        try {
            log.info(">>>>>>HBASE建表的相关信息为：" + JSONObject.toJSONString(buildTableInfoVo));
            // 检查 建表的相关参数
            this.createTableBeforeCheck(buildTableInfoVo);
            // 生成建表语句
            String createSql = this.getCreateSql(buildTableInfoVo);
            log.info(">>>>>>HBASE生成的建表语句为： " + createSql);
            CreateTableDataVO huaWeiCreateTable = new CreateTableDataVO();
            huaWeiCreateTable.setData(createSql);
            if (buildTableInfoVo.getDsType().equalsIgnoreCase(Common.HBASECDH)) {
                huaWeiCreateTable.setType(Common.HBASECDH);
            } else {
                huaWeiCreateTable.setType(Common.HBASEHUAWEI);
            }
            String dataId = buildTableInfoVo.getDataId();
            huaWeiCreateTable.setResId(dataId);
            huaWeiCreateTable.setTableId(buildTableInfoVo.getTableId());
            huaWeiCreateTable.setTableName(buildTableInfoVo.getSchema() + "." + buildTableInfoVo.getTableName());
            RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
            message = restTemplateHandle.sendCreateTableInfo(huaWeiCreateTable);
            buildTableInfoVo.setLifeCycle(buildTableInfoVo.getLifeCycle());
//            TableOrganizationDao tableOrganizationDao = SpringBeanUtil.getBean(TableOrganizationDao.class);
//            tableOrganizationDao.insertInfo(buildTableInfoVo);
//            tableOrganizationDao.insertInfoTemp(buildTableInfoVo);
        } catch (Exception e) {
            log.error(">>>>>>hbase建表失败：", e);
            throw SystemException.asSystemException(ErrorCodeEnum.CREATE_TABLE_ERROR, e.getMessage());
        }
        return message;
    }


    /**
     * 建表时先检查相关参数是否正确
     */
    @Override
    public String createTableBeforeCheck(BuildTableInfoVO buildTableInfoVo) {
        // 在hbase建表中，如果不是 固定分区/总共一个索引，则字段中必须要有  CAPTURE_TIME 这个字段，否则会建表失败
        // 如果regionType的值不是1/-1则表示必须要有CAPTURE_TIME这个字段
        List<String> columnNameList = buildTableInfoVo.getColumnData().stream().map(e -> e.getColumnName().toUpperCase())
                .distinct().collect(Collectors.toList());
        if (buildTableInfoVo.getRegionType() == 0 || buildTableInfoVo.getRegionType() == 2 || buildTableInfoVo.getRegionType() == 3) {
            if (!columnNameList.contains("CAPTURE_TIME")) {
                throw new NullPointerException(" 当选择该【HBASE分区类型】时，需要有【CAPTURE_TIME】字段");
            }
        }
        if (buildTableInfoVo.getEsSplitType() == 0 || buildTableInfoVo.getEsSplitType() == 3) {
            if (!columnNameList.contains("CAPTURE_TIME")) {
                throw new NullPointerException(" 当选择该【ES索引类型】时，需要有【CAPTURE_TIME】字段");
            }
        }
        // 如果存在CAPTURE_TIME 这个字段 则必须是 bigint类型
        for (ObjectFieldEntity objectField : buildTableInfoVo.getColumnData()) {
            if ("CAPTURE_TIME".equalsIgnoreCase(objectField.getColumnName()) && !"bigint".equalsIgnoreCase(objectField.getCreateColumnType())) {
                throw new NullPointerException("【CAPTURE_TIME】字段的建表类型必须是bigint");
            }
        }
        ObjectMapper objectMapper = SpringBeanUtil.getBean(ObjectMapper.class);
        String tableId = buildTableInfoVo.getTableId();
        long temp = SelectUtil.countObjectByTableId(objectMapper, tableId, buildTableInfoVo.getTableName());
        if (temp == 0) {
            throw new NullPointerException("标准中无表名或协议编号信息!!!");
        }
        String dataId = buildTableInfoVo.getDataId();
        if (StringUtils.isEmpty(dataId)) {
            throw new NullPointerException("数据源ID为空，请选择具体的数据源信息");
        }
        return tableId;
    }

    /**
     * @param oldTableName 需要更新的表名
     * @param newTableName 更新后的表名
     */
    @Override
    public boolean updateTableName(String oldTableName, String newTableName) {
        return false;
    }

}
