package com.synway.property.dao;

import com.synway.property.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author majia
 */
@Mapper
@Repository
public interface OperatorLogDao {

	void insertOperationLog(OperationLog log);
	
	Date getOperateTime(@Param("logname") String logname);
	
	OperationLog getOperateLog(@Param("logname") String logname);
	
	void updateOperateLog(OperationLog log);

	OperationLog getOperateLogMin(@Param("logname") String logname);

	List<OperationLog> getOperateLogAll(@Param("logname") String logname);
	
}
