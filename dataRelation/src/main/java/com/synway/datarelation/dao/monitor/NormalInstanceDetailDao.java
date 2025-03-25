package com.synway.datarelation.dao.monitor;

import com.synway.datarelation.pojo.monitor.detail.NormalTaskInfo;
import com.synway.datarelation.pojo.monitor.detail.TaskNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.table.InOutTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/14 16:56
 */
@Mapper
@Repository
public interface NormalInstanceDetailDao {

    List<NormalTaskInfo> getNormalTask(@Param("queryParams") TaskNormalReportPageParams queryParams);

    List<Map<String, String>> getNormalTaskFilterList(@Param("queryParams") TaskNormalReportPageParams queryParams);

    List<InOutTable> getTaskInOutRecord(@Param("taskId") String taskId);


    Integer getPrgTypeByTaskId(String taskId);
}
