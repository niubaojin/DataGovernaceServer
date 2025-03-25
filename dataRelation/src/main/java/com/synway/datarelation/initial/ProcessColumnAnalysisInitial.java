package com.synway.datarelation.initial;

import com.synway.datarelation.config.QueueBean;
import com.synway.datarelation.service.runnable.ProcessColumnAnalysisRunnable;
import com.synway.datarelation.service.datablood.LineageColumnParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangdongwei
 * @ClassName ProcessColumnAnalysisInitial
 * @description 启动字段血缘解析消费者的线程
 * @date 2020/9/18 12:01
 */
@Component
public class ProcessColumnAnalysisInitial implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ProcessColumnAnalysisInitial.class);
    @Autowired
    private QueueBean queueBean;
    @Autowired
    private LineageColumnParsingService lineageColumnParsingServiceImpl;

    @Override
    public void afterPropertiesSet() throws Exception {
        ProcessColumnAnalysisRunnable consumer = new ProcessColumnAnalysisRunnable(queueBean.getQueue(),lineageColumnParsingServiceImpl);
        for(int i = 0; i<4;i++){
            queueBean.getExecutorService().execute((new Thread(consumer)));
        }
        logger.info("-------------- 字段血缘的共有4个消费者，启动成功-----------------");
    }
}
