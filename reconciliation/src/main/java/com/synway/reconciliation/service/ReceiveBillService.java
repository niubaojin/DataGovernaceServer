package com.synway.reconciliation.service;

import com.synway.reconciliation.pojo.BillTableType;
import com.synway.reconciliation.pojo.DacAcceptorBill;
import com.synway.reconciliation.pojo.DacProviderBill;

import java.util.List;

/**
 * @author DZH
 */
public interface ReceiveBillService {

    /**
     * 处理账单数据
     * @param data 账单数据
     * @param type 账单类型
     * @return
     */
    boolean handleBill(String data, String type);

    /**
     * 读账单文件到入库
     * @param billPath 账单根目录
     * @param dateDir  账单日期目录
     */
    void consumerBill(String billPath, String dateDir);

    /**
     * 插入提供方账单
     * @param acceptorBills 账单数据列表
     * @param billTableType 要插入的表的类型
     */
    void insertAcceptorBill(List<DacAcceptorBill> acceptorBills, BillTableType billTableType);

    /**
     * 插入提供方账单
     * @param providerBills 账单数据列表
     * @param billTableType 要插入的表的类型
     */
    void insertProviderBill(List<DacProviderBill> providerBills, BillTableType billTableType);
}
