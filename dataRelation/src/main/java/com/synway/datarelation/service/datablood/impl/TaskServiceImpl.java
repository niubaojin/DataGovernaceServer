package com.synway.datarelation.service.datablood.impl;

import com.synway.datarelation.config.QueueBean;
import com.synway.datarelation.pojo.databloodline.ProcessColumnTask;
import com.synway.datarelation.service.runnable.ProcessColumnAnalysisRunnable;
import com.synway.datarelation.service.datablood.LineageColumnParsingService;
import com.synway.datarelation.service.datablood.TaskService;
import com.synway.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangdongwei
 * @ClassName TaskServiceImpl
 * @description TODO
 * @date 2020/9/18 13:41
 */
@Service
public class TaskServiceImpl implements TaskService {
    private static  final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private QueueBean queueBean;
    @Autowired
    private LineageColumnParsingService lineageColumnParsingServiceImpl;

    @Override
    public void insert(String sql,String dbType,String projectName,String nodeId) {
        try{
            ProcessColumnAnalysisRunnable task = new ProcessColumnAnalysisRunnable(queueBean.getQueue(),lineageColumnParsingServiceImpl);
            ProcessColumnTask processColumnTask = new ProcessColumnTask();
            processColumnTask.setDbType(dbType);
            processColumnTask.setNodeId(nodeId);
            processColumnTask.setProjectName(projectName);
            processColumnTask.setSqlCode(sql);
            task.setProcessColumnTask(processColumnTask);
            queueBean.getQueue().put(task);
        }catch (Exception e){
            logger.error("将节点数据插入到全局队列中报错"+ ExceptionUtil.getExceptionTrace(e));
        }

    }
}
