package com.synway.property.config;

import com.synway.property.controller.DataStorageMonitoringController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Consumer;

/**
 * @author majia
 * @version 1.0
 * @date 2020/4/15 15:07
 */
@Component
public class TransactionUtil {
    private static Logger logger = LoggerFactory.getLogger(TransactionUtil.class);

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    public <T> boolean transaction(Consumer<T> consumer) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            consumer.accept(null);
            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("编程式事务业务异常回滚",e);
            return false;
        }
    }


    public TransactionStatus begin(){
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transactionStatus;
    }

    public void commit(TransactionStatus transactionStatus){
        dataSourceTransactionManager.commit(transactionStatus);
    }

    public void rollback(TransactionStatus transactionStatus){
        dataSourceTransactionManager.rollback(transactionStatus);
    }

}
