package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.standard.ObjectDao;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.entity.CkColumn;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ck建表的相关接口信息
 *
 * @author wangdongwei
 * @date 2021/9/23 17:39
 */
public class CkTableColumnHandle implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(CkTableColumnHandle.class);
    /**
     * 相关的样例代码为
     * alter table tableName add column newColumn String after col1;
     */
    private static final String ADD_COLUMN_SQL = "alter table %s add column %s %s  comment '%s'";

    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        logger.info("开始拼接clickhouse新增字段的相关操作");
        String tableNameEn = data.getCreatedTableData().getTableNameEn().toLowerCase();
        if (data.getColumnList().isEmpty()) {
            logger.info("数据库中的建表字段为空，无法生成新增字段语句");
            throw new NullPointerException("新增字段信息为空，无法生成新增字段语句");
        }
        List<String> list = new ArrayList<>();
        data.getColumnList().forEach(d -> {
            list.add(String.format(ADD_COLUMN_SQL, tableNameEn, d.getColumnEngname(),
                    d.getColumnType(), StringUtils.isBlank(d.getColumnChinese()) ? "" : d.getColumnChinese()));
        });
        return StringUtils.join(list, "; ");
    }

    @Override
    public String addColumnByData(SaveColumnComparision data) throws Exception {
        String sqlStr = this.getAddColumnSql(data);
        logger.info("在clickhouse中生成的sql语句为：" + sqlStr);
        if (StringUtils.isBlank(sqlStr)) {
            throw new NullPointerException("生成的sql语句为空");
        }
        if (StringUtils.isBlank(data.getCreatedTableData().getDataId())) {
            logger.info("表[" + data.getCreatedTableData().getTableNameEn() + "]对应的参数dataId为空，不能新增字段信息");
            logger.error("表[" + data.getCreatedTableData().getTableNameEn() + "]对应的参数dataId为空，不能新增字段信息");
            throw new NullPointerException("表[" + data.getCreatedTableData().getTableNameEn() + "]" +
                    "对应的参数dataId为空，不能新增字段信息");
        }
        CreateTableData aliyunAddColumn = new CreateTableData();
        aliyunAddColumn.setTableId(data.getCreatedTableData().getTableNameEn());
        aliyunAddColumn.setResId(data.getCreatedTableData().getDataId());
        aliyunAddColumn.setData(sqlStr);
        aliyunAddColumn.setType(data.getCreatedTableData().getTableBase());
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        //  返回结果是咋样 之后再判定    "创建"+tableId+"表的字段成功"
        return restTemplateHandle.sendAddColumnInfo(aliyunAddColumn);
    }

    /**
     * ck的建表格式 需要创建分布式的表
     *
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception {
        List<ObjectField> columnList = buildTableInfoVo.getColumnData();
        String tableName = buildTableInfoVo.getTableName();
        String projectName = buildTableInfoVo.getProjectName();
        String tableNameLocal = projectName.toLowerCase() + "." + tableName.toLowerCase() + "_local";
        String tableNameCluster = projectName.toLowerCase() + "." + tableName.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists ")
                .append(tableNameLocal)
                .append(" on cluster ck_cluster ")
                .append("(\n");
        for (ObjectField tc : columnList) {
            if (StringUtils.isBlank(tc.getColumnName())) {
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR, "存在建表字段为空值的情况");
            }
            stringBuilder.append("\t").append(tc.getColumnName().toLowerCase()).append("\t").append(tc.getCreateColumnType());
            if (StringUtils.isBlank(tc.getCreateColumnType())) {
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR, "字段" + tc.getColumnName() + "没有配置建表类型");
            }
            if (StringUtils.isNotBlank(tc.getFieldChineseName())) {
                stringBuilder.append("\tcomment\t'").append(tc.getFieldChineseName()).append("',\n");
            } else {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(")\n");
        stringBuilder.append("engine = ").append(buildTableInfoVo.getEngine()).append("\n");
        // 排序主键
        stringBuilder.append("order by ");
        int orderSize = buildTableInfoVo.getCkOrderByColumn().size();
        if (orderSize > 1) {
            stringBuilder.append("(");
        }
        List<String> listOrder = getPkColumnList(buildTableInfoVo.getCkOrderByColumn(), orderSize, "排序主键");
        stringBuilder.append(StringUtils.join(listOrder, ", "));
        if (orderSize > 1) {
            stringBuilder.append(")");
        }
        // 如果存在生命周期
        if (buildTableInfoVo.getLifeCycle() != null && buildTableInfoVo.getLifeCycle() > 0) {
            stringBuilder.append("\nTTL  toDate(capture_time)  +  toIntervalDay(").append(buildTableInfoVo.getLifeCycle()).append(")");
        }
        // 分区主键
        if (buildTableInfoVo.getCkPartitionColumn() != null && !buildTableInfoVo.getCkPartitionColumn().isEmpty()) {
            stringBuilder.append("\npartition by ");
            int size = buildTableInfoVo.getCkPartitionColumn().size();
            if (size > 1) {
                stringBuilder.append("( ");
            }
            List<String> list = getPkColumnList(buildTableInfoVo.getCkPartitionColumn(), size, "分区主键");
            stringBuilder.append(StringUtils.join(list, ", "));
            if (size > 1) {
                stringBuilder.append(" )");
            }
        }
        stringBuilder.append(";\n");
        // 集群表
        stringBuilder.append("create table if not exists ")
                .append(tableNameCluster)
                .append(" on cluster ck_cluster as ")
                .append(tableNameLocal)
                .append(" engine = Distributed(ck_cluster, ")
                .append(projectName.toLowerCase()).append(", ").append(tableName.toLowerCase()).append("_local")
                .append(", rand());");
        return stringBuilder.toString();
    }

    private List<String> getPkColumnList(List<CkColumn> list, int size, String type) {
        List<String> strList = new ArrayList<>(size);
        for (CkColumn ckColumn : list) {
            if (StringUtils.equalsIgnoreCase(ckColumn.getPartitionType(), Common.COLUMN)) {
                strList.add(ckColumn.getColumnName().toLowerCase());
            } else if (StringUtils.equalsIgnoreCase(ckColumn.getPartitionType(), Common.DATE_FUNCTION)) {
                strList.add(ckColumn.getPartitionFunction() + "(" + ckColumn.getColumnName().toLowerCase() + ")");
            } else {
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR, type + " 字段[" + ckColumn.getColumnName()
                        + "]类型为[" + ckColumn.getPartitionType() + "]，没有配置处理类");
            }

        }
        return strList;
    }

    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        return false;
    }

    /**
     * 创建clickhouse平台的表信息
     * 相关资料请查询  http://svn.repo.s/datagovernace/trunk/DataGovernace/03设计阶段/外部资料/clickhouse建表/SCK查询指南(SQL大全).pdf
     *
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String createTableByPage(BuildTableInfoVo buildTableInfoVo) throws Exception {
        logger.info("clickhouse建表的相关信息为：" + JSONObject.toJSONString(buildTableInfoVo));
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        this.createTableBeforeCheck(buildTableInfoVo);
        String dataId = buildTableInfoVo.getDataId();
        String createSql = this.getCreateSql(buildTableInfoVo);
        logger.info("生成的sql语句为：" + createSql.replaceAll("\\n", "  "));
        String sqlLocal = createSql.split(";")[0];
        String sqlCluster = createSql.split(";")[1];
        // 本地表sql
        CreateTableData createTableDataLocal = new CreateTableData();
        createTableDataLocal.setData(sqlLocal.replaceAll("\\n", "  "));
        createTableDataLocal.setType(Common.CLICKHOUSE);
        createTableDataLocal.setResId(dataId);
        createTableDataLocal.setTableId(buildTableInfoVo.getTableId());
        createTableDataLocal.setTableName(buildTableInfoVo.getSchema() + "." + buildTableInfoVo.getTableName());
        // 集群表sql
        CreateTableData createTableDataCluster = new CreateTableData();
        createTableDataCluster.setData(sqlCluster.replaceAll("\\n", "  "));
        createTableDataCluster.setType(Common.CLICKHOUSE);
        createTableDataCluster.setResId(dataId);
        createTableDataCluster.setTableId(buildTableInfoVo.getTableId());
        createTableDataCluster.setTableName(buildTableInfoVo.getSchema() + "." + buildTableInfoVo.getTableName());

        String message;
        try {
            message = restTemplateHandle.sendCreateTableInfo(createTableDataLocal);
            message = restTemplateHandle.sendCreateTableInfo(createTableDataCluster);
            logger.info("----------------建表结束---------------");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw SystemException.asSystemException(ErrorCode.CREATE_TABLE_ERROR, e.getMessage());
        }
        return message;
    }

    /**
     * 建表之前的参数检查 并返回tableId
     *
     * @param buildTableInfoVo
     * @throws Exception
     */
    @Override
    public String createTableBeforeCheck(BuildTableInfoVo buildTableInfoVo) throws Exception {
        String tableId = buildTableInfoVo.getTableId();
        ObjectDao objectDao = SpringBeanUtil.getBean(ObjectDao.class);
        int temp = objectDao.countObjectByTableId(tableId, buildTableInfoVo.getTableName());
        if (temp == 0) {
            throw new NullPointerException("标准中无表名或协议编号信息!!!");
        }
        String dataId = buildTableInfoVo.getDataId();
        if (StringUtils.isEmpty(dataId)) {
            throw new NullPointerException("数据源ID为空，请选择具体的数据源信息");
        }
        // 检查必填参数是否存在
        if (buildTableInfoVo.getCkOrderByColumn() == null ||
                buildTableInfoVo.getCkOrderByColumn().isEmpty()) {
            throw SystemException.asSystemException(ErrorCode.UNION_ERROR, "排序字段是必填的");
        }
        // ck表无论分区与否都可设置生命周期值，前提条件是存在标准日期格式字段（2021-09-24 10:00:01），
        // 如果调用公司封装接口需要存在captruetime字段，
        if (buildTableInfoVo.getColumnData() == null || buildTableInfoVo.getColumnData().isEmpty()) {
            throw SystemException.asSystemException(ErrorCode.UNION_ERROR, "建表字段不能为空");
        }
        if (buildTableInfoVo.getLifeCycle() == null) {
            throw SystemException.asSystemException(ErrorCode.UNION_ERROR, " 生命周期不能设置为0");
        }
        if (buildTableInfoVo.getLifeCycle() != null) {
            boolean flag = buildTableInfoVo.getColumnData().stream().anyMatch(d -> StringUtils.equalsIgnoreCase(
                    d.getColumnName(), Common.CAPTURE_TIME));
            if (!flag) {
                throw SystemException.asSystemException(ErrorCode.UNION_ERROR, " 当表设置了生命周期时，capture_time字段必须存在");
            }
        }
        //  需要验证  分区字段 和 排序字段是否在 建表字段中
        List<String> columnList = buildTableInfoVo.getColumnData().stream().filter(d -> StringUtils.isNotBlank(d.getColumnName()))
                .map(d -> d.getColumnName().toUpperCase())
                .collect(Collectors.toList());

        // 检查排序主键
        for (CkColumn ckColumn : buildTableInfoVo.getCkOrderByColumn()) {
            if (StringUtils.isBlank(ckColumn.getColumnName())) {
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "排序主键的字段不能为空");
            }
            if (!columnList.contains(ckColumn.getColumnName().toUpperCase())) {
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "排序主键的字段[" + ckColumn.getColumnName()
                        + "]不在建表字段中");
            }
        }

        // 检查分区主键
        if (buildTableInfoVo.getCkPartitionColumn() != null) {
            for (CkColumn ckColumn : buildTableInfoVo.getCkPartitionColumn()) {
                if (StringUtils.isBlank(ckColumn.getColumnName())) {
                    throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "分区主键的字段不能为空");
                }
                if (!columnList.contains(ckColumn.getColumnName().toUpperCase())) {
                    throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "分区主键的字段[" + ckColumn.getColumnName()
                            + "]不在建表字段中");
                }
            }
        }
        String createSql = this.getCreateSql(buildTableInfoVo);
        if (StringUtils.isBlank(createSql)) {
            throw new NullPointerException("生成的建表sql为空");
        }

        return tableId;
    }


}
