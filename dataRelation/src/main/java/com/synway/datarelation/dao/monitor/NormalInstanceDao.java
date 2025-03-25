package com.synway.datarelation.dao.monitor;

import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.monitor.business.BusinessEntity;
import com.synway.datarelation.pojo.monitor.dag.DagEntity;
import com.synway.datarelation.pojo.monitor.page.NormalBusinessInfo;
import com.synway.datarelation.pojo.monitor.table.InOutTable;
import com.synway.datarelation.pojo.datawork.v3.TaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/17 16:05
 */
@Mapper
@Repository
public interface NormalInstanceDao extends BaseDAO {
    //Dag 实例的相关信息
    List<DagEntity> getDistinctDags();
    int insertDagList(@Param("list") List<DagEntity> dagEntityList);
    int deleteDagList(@Param("list") List<Long> dagIdList);

    int updateCpuAndMemoryConsumption(@Param("taskId") Long taskId,
                                      @Param("cpuConsumption") String cpuConsumption,
                                      @Param("memoryConsumption") String memoryConsumption);


    int insertBusiness(@Param("set")Set<BusinessEntity> entities);

    int deleteBusiness();

    List<BusinessEntity> getBusinesses();

    List<TaskEntity> getTaskInfoByFlowId(@Param("flowId") Long flowId);

    List<String> getTaskIdsByDagId(@Param("dagId") Long dagId);

    List<String> getTaskInfoByBizId(@Param("bizId")Integer bizId);

    TaskEntity getTaskInfoByDagIdAndBizId(@Param("dagId")String dagId,@Param("bizId") Integer bizId);

    List<String> getTaskIdsByDagIdAndBizId(@Param("dagId")String dagId,@Param("bizId") Integer bizId);

    List<TaskEntity> getTaskInfoByNodeId(@Param("nodeId")String nodeId);

    int insertBusinessInstance(@Param("list")List<NormalBusinessInfo> returns);

    int deleteBusinessInstance();

    int updateTaskBusinessName(@Param("list")List<NormalBusinessInfo> returns);

    int updateOutTableInfo(@Param("taskId")Long taskId, @Param("outNum")int outNum, @Param("outRecords") String outRecords);

    int deleteTaskInOutRecord(@Param("taskId")Long taskId);

    int insertTaskInOutRecord(@Param("list")List<InOutTable> resultList);


    void updateTaskStatus(@Param("taskId") Long taskId,@Param("entity") TaskEntity entity);

    List<String> getDagStatus(@Param("taskId")Long taskId);

    void updateBusinessStatus(@Param("taskId")Long taskId, @Param("status")String status);
}
