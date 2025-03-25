package com.synway.property.dao;

import com.synway.property.pojo.DataStoreAndUseHeat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 数据接入
 */
@Mapper
@Repository
public interface DataStoreAndUseHeatDao {

	List<DataStoreAndUseHeat> findAll();
	
	int deleteAll();

	int insertIntoStoreCycleAndUseHeat(List<DataStoreAndUseHeat> list);

	Long findUseHeatByTableNameList(@Param("map") Map<String, String> map);

	List<DataStoreAndUseHeat> findObjectExtend();

}

