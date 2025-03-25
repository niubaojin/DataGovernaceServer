package com.synway.reconciliation.util;

import com.synway.reconciliation.schedule.consumer.api.BillFileWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class SpringContextUtil implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringContextUtil.beanFactory = beanFactory;
    }

    /**
     * 根据beanName 获取对应的BillFileWriter
     * @param beanName
     * @return
     */
    public static BillFileWriter getBillFileWriterBean(String beanName) {
        if (null != beanFactory) {
            return (BillFileWriter) beanFactory.getBean(beanName);
        }
        return null;
    }

    /**
     * 获取某种类型bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }
}
