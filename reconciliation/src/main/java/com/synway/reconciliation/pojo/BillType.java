package com.synway.reconciliation.pojo;

/**
 * 账单类型枚举
 * @author Administrator
 */
public enum BillType {

    /**
     * 接入接收方账单
     */
    ACCESS_ACCEPTOR_BILL(1, "access_acceptor_writer"),

    /**
     * 接入提供方账单
     */
    ACCESS_PROVIDER_BILL(2, "access_provider_writer"),

    /**
     * 标准化接收方账单
     */
    STANDARD_ACCEPTOR_BILL(3, "standard_acceptor_writer"),

    /**
     * 标准化提供方账单
     */
    STANDARD_PROVIDER_BILL(4, "standard_provider_writer"),

    /**
     * 入库接收方账单
     */
    STORAGE_ACCEPTOR_BILL(5, "storage_acceptor_writer"),

    /**
     * 入库提供方账单
     */
    STORAGE_PROVIDER_BILL(6, "storage_provider_writer");


    /**
     * 账单类型code
     */
    private int code;

    /**
     * 对应writerBeanName
     */
    private String value;

    BillType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据code获取
     * @param code
     * @return
     */
    public static String getValueByCode(int code) {
        for (BillType billType : BillType.values()) {
            if (billType.code == code) {
                return billType.value;
            }
        }
        return null;
    }
}
