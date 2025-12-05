package com.synway.datastandardmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableDataVO;
import com.synway.datastandardmanager.mapper.ObjectMapper;
import com.synway.datastandardmanager.service.CreateTableSqlService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName AdsTableColumnHandle
 * @description ads数据库中建表以及新增字段的处理类
 * @date 2020/9/26 13:38
 */
@Slf4j
public class CreateTableSqlServiceImplAds implements CreateTableSqlService {

    private static final String ADD_COLUMN_SQL = "alter table %s add columns(%s);";

    /**
     * ads新增字段的语句为：
     * alter table db_name.tablename add columns(column1 string comment '1',column2 string comment '2')
     * 与odps的一样
     */
    @Override
    public String getAddColumnSql(CreateTableInfoDTO data) {
        log.info(">>>>>>开始拼接ads新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if (data.getColumnList().isEmpty()) {
            log.info(">>>>>>数据库中的新增的建表字段为空，无法生成新增字段语句");
            return Common.NULL;
        }
        List<String> list = data.getColumnList().stream()
                .filter(d -> !d.isPartition())
                .map(d -> d.getColumnEngname() + "  " + d.getColumnType() + "  comment '" + d.getColumnChinese() + "' ").collect(Collectors.toList());
        String sqlData = StringUtils.join(list, ", ").toLowerCase();
        return String.format(ADD_COLUMN_SQL, tableNameEn, sqlData);
    }

    @Override
    public String addColumnByData(CreateTableInfoDTO data) {
        String sqlStr = this.getAddColumnSql(data);
        log.info(">>>>>>在ads中生成的sql语句为：" + sqlStr);
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
        aliyunAddColumn.setType(data.getCreatedTableData().getTableBase());
        aliyunAddColumn.setData(sqlStr);
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        // sql语句发送给仓库执行
        return restTemplateHandle.sendAddColumnInfo(aliyunAddColumn);
    }

    /**
     * 获取ads的建表信息
     */
    @Override
    public String getCreateSql(BuildTableInfoVO buildTableInfoVo) {
        String sql = "";
        try {
            List<ObjectFieldEntity> columnList = buildTableInfoVo.getColumnData();
            String tableName = buildTableInfoVo.getTableName();
            String projectName = buildTableInfoVo.getProjectName();
            String partitionFirst = buildTableInfoVo.getPartitionFirst();
            String partitionFirstNum = buildTableInfoVo.getPartitionFirstNum();
            String partitionSecond = buildTableInfoVo.getPartitionSecond();
            String partitionSecondNum = buildTableInfoVo.getPartitionSecondNum();
            String tableNameCH = buildTableInfoVo.getTableNameCH();
            //从标准查找isActiveTable
            ObjectMapper objectMapper = SpringBeanUtil.getBean(ObjectMapper.class);
            LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectEntity::getRelateTableName, "OBJECTFIELD");
            wrapper.eq(ObjectEntity::getRealTablename, tableName);
            ObjectEntity objectEntity = objectMapper.selectOne(wrapper);
            Integer isActiveTable = objectEntity == null ? 0 : objectEntity.getIsActiveTable();
            if (buildTableInfoVo.getIsCreatTableTool()) {
                sql = "create table " + tableName + "(\n";
            }else {
                sql = "create table " + projectName + "." + tableName + "(\n";
            }
            //添加字段
            for (ObjectFieldEntity tc : columnList) {
                sql += "\t" + tc.getColumnName() + "   " + tc.getCreateColumnType();
                if (StringUtils.isEmpty(tc.getCreateColumnType())) {
                    throw new NullPointerException(String.format("字段[%s]对应的类型[%s]，请配置对应的建表类型", tc.getColumnName(), tc.getCreateColumnType()));
                }
                if (StringUtils.isNotEmpty(tc.getFieldChineseName())) {
                    sql += "   comment  '" + tc.getFieldChineseName() + "'," + "\n";
                } else {
                    sql += "," + "\n";
                }
            }
            //添加主键
            String keySql = "";
            String clustSql = "";
            for (ObjectFieldEntity tc : columnList) {
                if (tc.getPkRecno() != null && tc.getPkRecno() != 0) {
                    keySql += tc.getColumnName() + ",";
                }
                if (tc.getClustRecno() != null && tc.getClustRecno() != 0) {
                    clustSql += tc.getColumnName() + ",";
                }
            }
            if (!StringUtils.isEmpty(keySql)) {
                keySql = "\tprimary key(" + keySql.substring(0, keySql.length() - 1) + "))\n";
                sql = sql + keySql;
            } else {
                sql = sql.substring(0, sql.length() - 1) + ")\n";
            }
            //添加分区
            if (!StringUtils.isEmpty(partitionFirst)) {
                String partitionSql = " partition by hash key(" + partitionFirst + ")  partition num " + partitionFirstNum + "\n";
                if (!StringUtils.isEmpty(partitionSecond)) {
                    partitionSql = partitionSql + " subpartition by list key (" + partitionSecond + ")";
                }
                if (StringUtils.isNotBlank(partitionSecondNum)) {
                    partitionSql += "SUBPARTITION OPTIONS(available_Partition_Num=" + partitionSecondNum + ")";
                }
                sql = sql + partitionSql;
            }

            if (!StringUtils.isEmpty(clustSql)) {
                clustSql = "\tCLUSTERED BY (" + clustSql.substring(0, clustSql.length() - 1) + ")\n";
                sql = sql + clustSql;
            }
            if (isActiveTable == 0) {
                sql = sql + " tablegroup data_table options(updateType='batch') ";
            } else {
                sql = sql + " tablegroup data_table options(updateType='realtime') ";
            }
            if (StringUtils.isNotBlank(tableNameCH)) {
                sql = sql + " comment '" + tableNameCH + "' ;";
            } else {
                sql = sql + " ;";
            }
        } catch (Exception e) {
            log.error(">>>>>>构建ads建表语句出错：", e);
        }
        return sql;
    }

    /**
     * 更新表名
     *
     * @param oldTableName 需要更新的表名
     * @param newTableName 更新后的表名
     * @return 返回更新结果
     */
    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        boolean flag = true;
        return true;
    }

}
