package com.synway.reconciliation.pojo;

/**
 * 账单表名和账单英文名 枚举类
 * @author Administrator
 */
public enum BillTableType {

    /**
     * 接入接收方
     */
    ACCESS_ACCEPTOR(1, "DAC_ACCESS_ACCEPTOR_BILL", "接入接入方"),

    /**
     * 接入提供方
     */
    ACCESS_PROVIDER(1, "DAC_ACCESS_PROVIDER_BILL", "接入提供方"),

    /**
     * 标准化接收方
     */
    STANDARD_ACCEPTOR(2, "DAC_STANDARD_ACCEPTOR_BILL", "标准化接收方"),

    /**
     * 标准化提供方
     */
    STANDARD_PROVIDER(2, "DAC_STANDARD_PROVIDER_BILL", "标准化提供方"),

    /**
     * 入库接收方
     */
    STORAGE_ACCEPTOR(3, "DAC_STORAGE_ACCEPTOR_BILL", "入库接收方"),

    /**
     * 入库提供方
     */
    STORAGE_PROVIDER(3, "DAC_STORAGE_PROVIDER_BILL", "入库提供方");


    /**
     * 环节类型  接入1 标准化2 入库3
     */
    private int code;

    /**
     * 对应表名
     */
    private String tableName;

    /**
     * 对应中文名
     */
    private String tableNameCh;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    BillTableType(int code, String tableName, String tableNameCh) {
        this.code = code;
        this.tableName = tableName;
        this.tableNameCh = tableNameCh;
    }
}
