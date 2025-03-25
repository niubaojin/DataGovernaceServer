package com.synway.dataoperations.dao;

import com.synway.dataoperations.pojo.dataPiledMonitor.DataPiledSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DataPiledMonitorDao {
    /*堆积监控数据入库*/
    void insertDataPiledMonitor(@Param("dataPiledSetting") DataPiledSetting dataPiledSetting);

    /*删除历史数据*/
    int delDataPiledMonitors();

    /*获取数据堆积监控数据*/
    List<DataPiledSetting> getDataPiledMonitors(@Param("days") Integer days);
}
