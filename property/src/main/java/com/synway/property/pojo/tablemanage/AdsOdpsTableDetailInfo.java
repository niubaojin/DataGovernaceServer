package com.synway.property.pojo.tablemanage;

/**
 *
 * @author 数据接入
 */
public class AdsOdpsTableDetailInfo{
    private String id;//id外键，链接于AdsOdpsTableInfo的id
    private String seqID;
    private String name;//字段名
    private String type;//字段类型
    private String memo;//字段注释
    public AdsOdpsTableDetailInfo(){}
    public AdsOdpsTableDetailInfo(String seqID, String name, String type, String memo) {
        this.seqID = seqID;
        this.name = name;
        this.type = type;
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "AdsOdpsTableDetailInfo{" +
                "seqID='" + seqID + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
    /**getter and setter*/
    public void setId(String id) {this.id = id;}
    public String getId() {return id;}
    public String getSeqID() {return seqID;}
    public void setSeqID(String seqID) {this.seqID = seqID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}


