package com.synway.datastandardmanager.pojo.buildtable;

public class TableColumn {
    private String cloumnName;//字段名
    private String cloumnNameCn;//字段备注
    private String cloumnType;//字段类型
    private int cloumnLength;//字段长度
    private int no;//顺序
    private Boolean isKey;//主键

    public Boolean getKey() {
        return isKey;
    }

    public void setKey(Boolean key) {
        isKey = key;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getCloumnName() {
        return cloumnName;
    }

    public void setCloumnName(String cloumnName) {
        this.cloumnName = cloumnName;
    }

    public String getCloumnNameCn() {
        return cloumnNameCn;
    }

    public void setCloumnNameCn(String cloumnNameCn) {
        this.cloumnNameCn = cloumnNameCn;
    }

    public String getCloumnType() {
        return cloumnType;
    }

    public void setCloumnType(String cloumnType) {
        this.cloumnType = cloumnType;
    }

    public int getCloumnLength() {
        return cloumnLength;
    }

    public void setCloumnLength(int cloumnLength) {
        this.cloumnLength = cloumnLength;
    }
}
