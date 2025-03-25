package com.synway.datarelation.dao.taskinstance;


import com.synway.datarelation.pojo.datawork.DataReportPageReturn;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageParams;
import com.synway.datarelation.pojo.datawork.v3.HistoryTaskQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TaskPageQueryDao{

    List<DataReportPageReturn> getDataSynchronizationReportDao(DataSynchronizationReportPageParams queryParams);

    List<Map<String,String>> getFilterList(DataSynchronizationReportPageParams queryParams);

    String queryDataSynchronizationLogDao(@Param("taskId") String taskId, @Param("id") String id);

    List<DataReportPageReturn> queryHistoryTaskLogDao(HistoryTaskQueryParams historyTaskQueryParams);

    /**
     *  第2版本的查询参数
     */
    List<DataReportPageReturn> queryHistoryTaskLogTwoDao(HistoryTaskQueryParams historyTaskQueryParams);

}
