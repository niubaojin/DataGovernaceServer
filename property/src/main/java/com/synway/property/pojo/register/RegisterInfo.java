package com.synway.property.pojo.register;

/**
 * @ClassName RegisterInfo
 * @Author majia
 * @Date 2020/4/20 18:54
 * @Version 1.0
 **/
public class RegisterInfo {
    private String tableid; //协议编号
    private String symbol;  //数据资源管理单位代码
    private String userid;  // 用户id

    public String getUserid() { return userid; }

    public void setUserid(String userid) { this.userid = userid; }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }
}
