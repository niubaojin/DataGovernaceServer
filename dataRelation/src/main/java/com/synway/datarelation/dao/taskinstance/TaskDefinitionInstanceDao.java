package com.synway.datarelation.dao.taskinstance;


import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.monitor.node.MaxVersionNodeId;
import com.synway.datarelation.pojo.datawork.TaskRunAnalyze;
import com.synway.datarelation.pojo.datawork.v3.NodeEntity;
import com.synway.datarelation.pojo.datawork.v3.TaskEntity;
import com.synway.datarelation.pojo.datawork.v3.TaskRunQueryParameters;
import com.synway.datarelation.pojo.modelmonitor.DfWorkModelNodeInsInfo;
import com.synway.datarelation.pojo.modelmonitor.ModelNodeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface TaskDefinitionInstanceDao extends BaseDAO {

    /**
     * 根据task中的 历史任务自增Id来删除相同的
     * @return
     */
    int delIdOldList(@Param("list") List<Long> delAllList);
    int delTaskOldList(@Param("list") List<Long> delAllList);

    int insertTaskList(@Param("list") List<TaskEntity> taskEntityList);

    //获取任务实例在数据库中的数据量
    int getTaskDefinitionCountDao(TaskRunQueryParameters taskRunQueryParameters);

    // 更新任务实例信息解析结果信息
    int updateTaskRunAnalyzeDao(TaskRunAnalyze taskRunAnalyze);
    //  插入任务实例信息
    int insertTaskRunAnalyzeDao(TaskRunAnalyze taskRunAnalyze);
    int getTaskLogCountDao(TaskRunQueryParameters taskRunQueryParameters);
    // 更新日志信息
    int updateTaskLogMessageDao(@Param("id") String id,
                                @Param("taskId") String taskId,
                                @Param("logStr") String logStr);
    //  插入日志信息
    int insertTaskLogMessageDao(@Param("id") String id,
                                @Param("taskId") String taskId,
                                @Param("logStr") String logStr);


    List<String> getNormalTask(@Param("list") List<String> taskIdList,
                               @Param("listId") List<String> idList);

    // node 节点的相关信息
    List<MaxVersionNodeId> getMaxVersionNodeByPrgtype(@Param("prgType") int prgType, @Param("list")Set<NodeEntity> allReturnValue);
    int delMaxVersionNode(@Param("list") List<MaxVersionNodeId> paramList);

    int delNodeByPrgtype(@Param("prgType") int prgType);

    int insertNodeList(@Param("list") List<NodeEntity> taskEntityList);


    //  华为云的任务实例相关数据插入到 m_flow_three中
    int insertDfWorkTaskList(@Param("list") List<DfWorkModelNodeInsInfo> taskEntityList);

//    int deleteDfWorkModelFlowIns();

    int getDfWorkModelFlowIns();

    void insertNodeWithChildNodes(@Param("list") List<NodeEntity> nodeWithChildNodes);

    List<Long> getNodeIdList(@Param("prgType") String prgType);

    String getLogIsParsing (@Param("taskId") Long taskId);

    List<TaskEntity> getTaskEntity();
}
