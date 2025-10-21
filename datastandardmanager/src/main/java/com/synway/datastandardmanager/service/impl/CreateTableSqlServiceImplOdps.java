package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableDataVO;
import com.synway.datastandardmanager.service.CreateTableSqlService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName OdpsAddColumnHandle
 * @description odps里面新增字段的相关处理类
 * @date 2020/9/25 13:57
 */
@Slf4j
public class CreateTableSqlServiceImplOdps implements CreateTableSqlService {

    private static final String ADD_COLUMN_SQL = "alter table %s add columns(%s);";

    /**
     * odps中不管是分区字段还是非分区字段 都只需要使用该方式来新增字段
     */
    @Override
    public String getAddColumnSql(CreateTableInfoDTO data) {
        log.info(">>>>>>开始拼接odps新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if (data.getColumnList().isEmpty()) {
            log.info("数据库中的建表字段为空，无法生成新增字段语句");
            return Common.NULL;
        }
        List<String> list = data.getColumnList().stream().filter(d -> !d.isPartition()).map(d ->
                d.getColumnEngname() + "  " + d.getColumnType() + "  comment '" + d.getColumnChinese() + "' ").collect(Collectors.toList());
        String sqlData = StringUtils.join(list, ", ").toLowerCase();
        return String.format(ADD_COLUMN_SQL, tableNameEn, sqlData);
    }

    @Override
    public String addColumnByData(CreateTableInfoDTO data) {
        String sqlStr = this.getAddColumnSql(data);
        log.info(">>>>>>在odps中生成的sql语句为：" + sqlStr);
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
        return restTemplateHandle.sendAddColumnInfo(aliyunAddColumn);
    }

    /**
     * 创建建表sql的相关语句
     * 20200927 新增该内容 以前的弃用
     */
    @Override
    public String getCreateSql(BuildTableInfoVO buildTableInfoVo) {
        List<ObjectFieldEntity> columnList = buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        Integer lifeDays = buildTableInfoVo.getLifeCycle();
        Integer isPartition = buildTableInfoVo.getIsPartition(); // 0：分区表，1：全量表
        String odpsSql = "";
        if (buildTableInfoVo.getIsCreatTableTool()) {
            odpsSql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n";
        } else {
            odpsSql = "CREATE TABLE IF NOT EXISTS " + projectName + "." + tableName + "(\n";
        }
        //添加字段
        for (ObjectFieldEntity tc : columnList) {
            if (isPartition == 1) {
                odpsSql = buildField(odpsSql, tc);
            } else {
                if (!"dt".equalsIgnoreCase(tc.getColumnName().toUpperCase())) {
                    odpsSql = buildField(odpsSql, tc);
                }
            }
        }
        odpsSql = odpsSql.substring(0, odpsSql.trim().length() - 1) + ")\n";
        if (StringUtils.isNotBlank(buildTableInfoVo.getTableNameCH())) {
            odpsSql += "comment '" + buildTableInfoVo.getTableNameCH() + "'\n";
        }
        if (isPartition == 0) {
            odpsSql += "partitioned by (DT STRING  COMMENT '分区列')\n";
        }
        if (lifeDays != null && lifeDays > 0) {
            odpsSql += "lifecycle " + lifeDays + "\n";
        }
        odpsSql += ";";
        return odpsSql;
    }

    public String buildField(String odpsSql, ObjectFieldEntity tc) {
        odpsSql += "\t" + tc.getColumnName() + "   " + tc.getCreateColumnType();
        if (StringUtils.isEmpty(tc.getCreateColumnType())) {
            throw new NullPointerException(String.format("字段[%s]对应的类型[%s]，请配置对应的建表类型", tc.getColumnName(), tc.getCreateColumnType()));
        }
        if (StringUtils.isNotEmpty(tc.getFieldChineseName())) {
            odpsSql += "   comment  '" + tc.getFieldChineseName() + "'," + "\n";
        } else {
            odpsSql += "," + "\n";
        }
        return odpsSql;
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
