package com.synway.datarelation.dao.taskinstance;


import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.datawork.DataReportPageReturn;
import com.synway.datarelation.pojo.datawork.DataSynchronizationReportPageParams;
import com.synway.datarelation.pojo.modelmonitor.ModelNodeInsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * datawork2.0版本
 */
@Mapper
@Repository
public interface TaskQueryV2Dao extends BaseDAO {

    /**
     * 删除指定时间和类型的实例信息
     */
    int deleteFlow(@Param("createTime") String createTime,
                   @Param("type") Integer type);


    int insertFlow(List<ModelNodeInsInfo> insertList);


    /**
     * 查询页面上的参数
     * @param queryParams
     * @return
     */
    List<DataReportPageReturn> getDataSynchronizationReportTwo(DataSynchronizationReportPageParams queryParams);

}
