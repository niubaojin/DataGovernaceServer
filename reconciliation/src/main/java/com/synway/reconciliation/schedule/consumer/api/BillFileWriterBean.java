package com.synway.reconciliation.schedule.consumer.api;

import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.conditional.NonIssueCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author DZH
 */
@Configuration
@Conditional(NonIssueCondition.class)
public class BillFileWriterBean {

    @Value("${billPath}")
    private String billPath;

    @Bean(Constants.ACCESS_ACCEPTOR_WRITER)
    public BillFileWriter getAccessAcceptor() {
        return new BillFileWriter(billPath, Constants.ACCESS_ACCEPTOR);
    }

    @Bean(Constants.ACCESS_PROVIDER_WRITER)
    public BillFileWriter getAccessProvider() {
        return new BillFileWriter(billPath, Constants.ACCESS_PROVIDER);
    }

    @Bean(Constants.STANDARD_ACCEPTOR_WRITER)
    public BillFileWriter getStandardAcceptor() {
        return new BillFileWriter(billPath, Constants.STANDARD_ACCEPTOR);
    }

    @Bean(Constants.STANDARD_PROVIDER_WRITER)
    public BillFileWriter getStandardProvider() {
        return new BillFileWriter(billPath, Constants.STANDARD_PROVIDER);
    }

    @Bean(Constants.STORAGE_ACCEPTOR_WRITER)
    public BillFileWriter getStorageAcceptor() {
        return new BillFileWriter(billPath, Constants.STORAGE_ACCEPTOR);
    }

    @Bean(Constants.STORAGE_PROVIDER_WRITER)
    public BillFileWriter getStorageProvider() {
        return new BillFileWriter(billPath, Constants.STORAGE_PROVIDER);
    }

}
