package com.synway.datastandardmanager.pojo;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName ColumnComparisionPage
 * @description 字段对比页面上的相关信息
 * @date 2020/9/24 14:11
 */
public class ColumnComparisionPage {
    private List<TableColumnPage> leftTableColumn;
    private  List<TableColumnPage> rightTableColumn;
    // 建表的表是否为 分区表
    private boolean tablePartition = false;

    public boolean isTablePartition() {
        return tablePartition;
    }

    public void setTablePartition(boolean tablePartition) {
        this.tablePartition = tablePartition;
    }

    public List<TableColumnPage> getRightTableColumn() {
        return rightTableColumn;
    }

    public void setRightTableColumn(List<TableColumnPage> rightTableColumn) {
        this.rightTableColumn = rightTableColumn;
    }

    public List<TableColumnPage> getLeftTableColumn() {
        return leftTableColumn;
    }

    public void setLeftTableColumn(List<TableColumnPage> leftTableColumn) {
        this.leftTableColumn = leftTableColumn;
    }


}
