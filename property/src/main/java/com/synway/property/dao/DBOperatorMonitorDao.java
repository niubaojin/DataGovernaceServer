package com.synway.property.dao;

import com.synway.property.pojo.DBOperatorMonitor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author majia
 */
@Mapper
@Repository
public interface DBOperatorMonitorDao {

	public void insertDBOperatorRecord(List<DBOperatorMonitor> list);
	public void deleteDBOperatorRecord(String dt);
	public void deleteDBExeData();
}
