package com.synway.datarelation.service.monitor.impl;


import com.synway.datarelation.pojo.datawork.v3.TaskQueryParameters;
import com.synway.datarelation.service.monitor.WorkFlowInstanceService;
import com.synway.datarelation.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/1/13 19:28
 */
@Service("WorkFlowInstanceV3ServiceImp")
public class WorkFlowInstanceV3ServiceImp implements WorkFlowInstanceService {
    private static Logger LOG = LoggerFactory.getLogger(WorkFlowInstanceV3ServiceImp.class);

//    @Autowired
//    @Qualifier(value = "TaskDefinitionInstanceServiceImpl")
//    private TaskDefinitionInstanceService taskDefinitionInstanceService;

    @Override
    public void statisticProject() throws Exception {
        //do nothing
    }

    @Override
    public void statisticNode() throws Exception {
//        NodeQueryParameters nodeQueryParameters = new NodeQueryParameters();
//        nodeQueryParameters.setPageStart(1);
//        nodeQueryParameters.setPageSize(1);
//        nodeQueryParameters.setPrgType(98);
//        List<Long> nodeIdList =  taskDefinitionInstanceService.getNode(nodeQueryParameters,true);
//        if(null==nodeIdList){
//            LOG.error("查询节点数据为空");
//            return;
//        }
//        LOG.info(String.format("查询到node的数量：[%s]",nodeIdList.size()));
//        if(nodeIdList.size()>0){
//            /**开始统计实例*/
//            statisticFlowIns(nodeIdList);
//        }

    }

    /**
     * 根据节点ID查询运行nodeType为98的实例
     * @param nodeIdList
     * @throws Exception
     */
    private void statisticFlowIns(List<Long> nodeIdList)throws Exception{
        String createTime = DateUtil.formatDate(DateUtil.addDay(new Date(),-1),"yyyy-MM-dd");
        String createTimeTo = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
        int nodeIdSize = nodeIdList.size();
        /**接口bug：实例每次查询数量只能查出500数据量，可以考虑把step的值设置的小一点*/
        int step = 100;
        int queryCount = (int) Math.ceil((double) nodeIdSize/step);
        int fromIndex;
        int toIndex;
        List<Long> queryIdList;
        for (int i = 0; i < queryCount; i++) {
            fromIndex = i*step;
            toIndex = (i == (queryCount-1) ? nodeIdSize : (i+1)*step);
            queryIdList = nodeIdList.subList(fromIndex,toIndex);

            String nodeIds = StringUtils.join(queryIdList,",");
            TaskQueryParameters taskQueryParameters = new TaskQueryParameters();
            taskQueryParameters.setPageStart(0);
            taskQueryParameters.setPageSize(500);
//            taskQueryParameters.setCreateTime(createTime);
            taskQueryParameters.setCreateTimeTo(createTimeTo+" 23:59:59");
            taskQueryParameters.setCreateTimeFrom(createTime+" 00:00:00");
            taskQueryParameters.setNodeIds(nodeIds);
            /**统计98类型的任务实例*/
            taskQueryParameters.setPrgType(98);
//            taskDefinitionInstanceService.getTask(taskQueryParameters,false);
        }
    }

    @Override
    public void statisticFlowIns() throws Exception {
        //do nothing
    }
}
