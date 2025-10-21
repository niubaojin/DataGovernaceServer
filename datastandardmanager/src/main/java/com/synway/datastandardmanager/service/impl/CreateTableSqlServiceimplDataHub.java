package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.DatahubTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.TableColumnVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.service.CreateTableSqlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * datahub的相关操作信息
 * datahub新增和建表都不是使用sql语句 将对象转成string
 *
 * @author wangdongwei
 * @date 2021/9/23 16:32
 */
@Slf4j
public class CreateTableSqlServiceimplDataHub implements CreateTableSqlService {

    @Override
    public String getAddColumnSql(CreateTableInfoDTO data) {
        throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "datahub不支持新增字段");
    }

    @Override
    public String addColumnByData(CreateTableInfoDTO data) {
        throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "datahub不支持新增字段");
    }

    @Override
    public String getCreateSql(BuildTableInfoVO buildTableInfoVo) {
        List<ObjectFieldEntity> columnList = buildTableInfoVo.getColumnData();
        List<TableColumnVO> tableColumns = new ArrayList<>(columnList.size());
        for (ObjectFieldEntity objectField : columnList) {
            if (StringUtils.isBlank(objectField.getCreateColumnType())) {
                throw new NullPointerException("建表字段类型没有匹配成功，请点击高级按钮手动获取建表类型");
            }
            TableColumnVO tableColumn = new TableColumnVO();
            tableColumn.setCloumnLength(objectField.getCreateColumnLen());
            tableColumn.setCloumnName(objectField.getColumnName());
            tableColumn.setCloumnNameCn(objectField.getFieldChineseName());
            tableColumn.setIsKey(objectField.getPkRecno() >= 1);
            tableColumn.setNo(objectField.getStandardRecno());
            String fieldTypeStr = objectField.getCreateColumnType();
            if (StringUtils.isEmpty(fieldTypeStr)) {
                throw new NullPointerException(String.format("字段[%s]对应的类型[%s]为空，请配置对应的建表类型", objectField.getColumnName(), fieldTypeStr));
            }
            tableColumn.setCloumnType(fieldTypeStr);
            tableColumns.add(tableColumn);
        }
        DatahubTableInfoVO datahubTable = new DatahubTableInfoVO();
        datahubTable.setColumns(tableColumns);
        datahubTable.setComment(buildTableInfoVo.getComment());
        datahubTable.setLifeCycle(buildTableInfoVo.getLifeCycle());
        datahubTable.setShardCount(buildTableInfoVo.getShardCount());
        datahubTable.setTopicName(buildTableInfoVo.getTopicName());
        datahubTable.setTopicProjectName(buildTableInfoVo.getSchema());

        String data = JSONObject.toJSONString(datahubTable);
        log.info(">>>>>>创建datahub的相关测试数据为{}", data);
        return data;
    }

    @Override
    public boolean updateTableName(String oldTableName, String newTableName) {
        return false;
    }

}
