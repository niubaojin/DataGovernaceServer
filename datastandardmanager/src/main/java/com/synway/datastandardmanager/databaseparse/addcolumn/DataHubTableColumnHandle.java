package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.buildtable.TableColumn;
import com.synway.datastandardmanager.pojo.dataresource.DatahubTableInformation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * datahub的相关操作信息
 * datahub新增和建表都不是使用sql语句 将对象转成string
 * @author wangdongwei
 * @date 2021/9/23 16:32
 */
public class DataHubTableColumnHandle implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(DataHubTableColumnHandle.class);

    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"datahub不支持新增字段");
    }

    @Override
    public String addColumnByData(SaveColumnComparision data) throws Exception {
        throw SystemException.asSystemException(ErrorCode.CHECK_ERROR,"datahub不支持新增字段");
    }

    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception {
        logger.info("开始获取创建dataHub的相关实体类");
        List<ObjectField> columnList = buildTableInfoVo.getColumnData();
        List<TableColumn> tableColumns = new ArrayList<>(columnList.size());
        for (ObjectField objectField:columnList) {
            if(StringUtils.isBlank(objectField.getCreateColumnType())){
                throw new NullPointerException("建表字段类型没有匹配成功，请点击高级按钮手动获取建表类型");
            }
            TableColumn tableColumn = new TableColumn();
            tableColumn.setCloumnLength(objectField.getCreateColumnLen());
            tableColumn.setCloumnName(objectField.getColumnName());
            tableColumn.setCloumnNameCn(objectField.getFieldChineseName());
            tableColumn.setKey(objectField.getPkRecno() >= 1);
            tableColumn.setNo(objectField.getStandardRecno());
            String fieldTypeStr = objectField.getCreateColumnType();
            if(StringUtils.isEmpty(fieldTypeStr)){
                throw new NullPointerException(String.format("字段[%s]对应的类型[%s]为空，请配置对应的建表类型",objectField.getColumnName(),fieldTypeStr));
            }
            tableColumn.setCloumnType(fieldTypeStr);
            tableColumns.add(tableColumn);
        }
        DatahubTableInformation datahubTable = new DatahubTableInformation();
        datahubTable.setColumns(tableColumns);
        datahubTable.setComment(buildTableInfoVo.getComment());
        datahubTable.setLifeCycle(buildTableInfoVo.getLifeCycle());
        datahubTable.setShardCount(buildTableInfoVo.getShardCount());
        datahubTable.setTopicName(buildTableInfoVo.getTopicName());
        datahubTable.setTopicProjectName(buildTableInfoVo.getSchema());

        String data = JSONObject.toJSONString(datahubTable);
        logger.info("创建datahub的相关测试数据为{}",data);
        return data;
    }

    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        return false;
    }
}
