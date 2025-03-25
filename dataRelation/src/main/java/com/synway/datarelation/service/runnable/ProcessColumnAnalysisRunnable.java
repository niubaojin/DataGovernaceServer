package com.synway.datarelation.service.runnable;

import com.synway.datarelation.pojo.databloodline.ProcessColumnTask;
import com.synway.datarelation.service.datablood.LineageColumnParsingService;
import com.synway.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * @author wangdongwei
 * @ClassName ProcessColumnConsumer
 * @description  字段血缘解析的消费者 使用多线程来进行线程的解析
 * @date 2020/9/18 11:58
 */
public class ProcessColumnAnalysisRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ProcessColumnAnalysisRunnable.class);

    private ProcessColumnTask processColumnTask;
    private  BlockingQueue<Runnable> queue;
    private LineageColumnParsingService lineageColumnParsingServiceImpl;

    public ProcessColumnTask getProcessColumnTask() {
        return processColumnTask;
    }

    public void setProcessColumnTask(ProcessColumnTask processColumnTask) {
        this.processColumnTask = processColumnTask;
    }

    public ProcessColumnAnalysisRunnable(BlockingQueue<Runnable> queue, LineageColumnParsingService lineageColumnParsingServiceImpl){
        this.queue = queue;
        this.lineageColumnParsingServiceImpl = lineageColumnParsingServiceImpl;
    }

    /**
     * 消费者的具体代码
     */
    @Override
    public void run() {
        while (true){
            try{
                ProcessColumnAnalysisRunnable task = (ProcessColumnAnalysisRunnable) queue.take();

                ProcessColumnTask processColumnTask = task.getProcessColumnTask();
                logger.info(Thread.currentThread().getName()+"消费者开始解析节点id为："
                        +processColumnTask.getNodeId()+" 的字段血缘信息,队列中还剩下 "+queue.size()+" 条数据");
                lineageColumnParsingServiceImpl.parseLineageColumn(processColumnTask.getSqlCode(),processColumnTask.getDbType(),processColumnTask.getProjectName(),processColumnTask.getNodeId());
            }catch (InterruptedException e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
                Thread.currentThread().interrupt();
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
        }

    }

}
