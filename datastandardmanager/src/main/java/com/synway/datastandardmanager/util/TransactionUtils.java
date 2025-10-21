package com.synway.datastandardmanager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * 事务的工具类
 *
 * @Author wdw
 * @Date 2021/5/31 13:34
 * @Version 1.0
 */
@Component
public class TransactionUtils {
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    public TransactionStatus begin() {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transactionStatus;
    }

    public void commit(TransactionStatus transactionStatus) {
        dataSourceTransactionManager.commit(transactionStatus);
    }

    public void rollback(TransactionStatus transactionStatus) {
        dataSourceTransactionManager.rollback(transactionStatus);
    }


}
