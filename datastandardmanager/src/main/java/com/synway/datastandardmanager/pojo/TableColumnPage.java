package com.synway.datastandardmanager.pojo;

/**
 * @author wangdongwei
 * @ClassName TableColumnPage
 * @description TODO
 * @date 2020/9/24 21:05
 */
public class TableColumnPage {

    private String rowNum;
    private String columnEngname;
    private String columnChinese;
    // 建表字段类型
    private String columnType;
    private String columnLen;
    private boolean isPartition;
    private boolean needAdd = false;
    // left right
    private String type ;
    // 如果是分区字段，则需要涉及到如何新增分区
    // 分区字段不需要新增 20200925


    public String getColumnLen() {
        return columnLen;
    }

    public void setColumnLen(String columnLen) {
        this.columnLen = columnLen;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getPartition() {
        return isPartition;
    }

    public void setPartition(boolean partition) {
        isPartition = partition;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getColumnEngname() {
        return columnEngname;
    }

    public void setColumnEngname(String columnEngname) {
        this.columnEngname = columnEngname;
    }

    public String getColumnChinese() {
        return columnChinese;
    }

    public void setColumnChinese(String columnChinese) {
        this.columnChinese = columnChinese;
    }

    public boolean getNeedAdd() {
        return needAdd;
    }

    public void setNeedAdd(boolean needAdd) {
        this.needAdd = needAdd;
    }

}
