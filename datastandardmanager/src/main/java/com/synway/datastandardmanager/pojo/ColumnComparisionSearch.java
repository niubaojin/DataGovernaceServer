package com.synway.datastandardmanager.pojo;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName ColumnComparisionSearch
 * @description 字段对比的查询参数
 * @date 2020/9/24 13:57
 */
public class ColumnComparisionSearch {
    private StandardTableCreated createdTableMessage;
    private List<ObjectField> leftStandardColumn;

    public StandardTableCreated getCreatedTableMessage() {
        return createdTableMessage;
    }

    public void setCreatedTableMessage(StandardTableCreated createdTableMessage) {
        this.createdTableMessage = createdTableMessage;
    }

    public List<ObjectField> getLeftStandardColumn() {
        return leftStandardColumn;
    }

    public void setLeftStandardColumn(List<ObjectField> leftStandardColumn) {
        this.leftStandardColumn = leftStandardColumn;
    }
}
