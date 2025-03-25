package com.synway.property.service.impl;


import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.ResultSet;
import com.synway.property.dao.DBOperatorMonitorDao;
import com.synway.property.dao.OperatorLogDao;
import com.synway.property.pojo.DBOperatorMonitor;
import com.synway.property.pojo.OperationLog;
import com.synway.property.util.DateUtil;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.OdpsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 数据接入
 */
@Service
public class DBOperatorMonitorImpl {
	
	private static Logger logger = LoggerFactory.getLogger(DBOperatorMonitorImpl.class);
	
	@Autowired(required = false)
	private OdpsClient useHeatClient;
	@Autowired 
	private LinkedBlockingQueue<List<DBOperatorMonitor>> blockedQueueList;
	@Autowired 
	private DBOperatorMonitorDao dbOperatorMonitorDao;
	@Autowired 
	private OperatorLogDao operatorLogDao;


	public boolean handleDBOperateMoniteDatas(Map tableInfoMap, String dateStr) {
		if (useHeatClient == null) {
			return false;
		}

		try{
			List<DBOperatorMonitor> list = new ArrayList<>();
			try {
				int objectLen = ((List<String>)tableInfoMap.get("columnNames")).size();
					List<Record> rs = useHeatClient.downloadData((String)tableInfoMap.get("projectName"),
							(String)tableInfoMap.get("tableName"),
							(List<String>)tableInfoMap.get("columnNames"),
							(String)tableInfoMap.get("partitions")
							);
					logger.info("sql语句【"+tableInfoMap+"】查询出来的数据量为："+rs.size());
					for(Record r:rs){
						Object[] obj = new Object[objectLen];
						for(int col = 0;col <objectLen;col++){
							try{
								Object oneColumnData = r.get(col);
								if(oneColumnData == null ){
									obj[col] = null;
								}else{
									obj[col] = r.getString(col);
								}
							}catch (Exception e){
								obj[col] = r.get(col);
							}
						}
						if( obj.length != 6 ){

						}else{
							// 因为表里面有重复值，所以需要按照sql语句进行去重，以及去除sql语句为null的值
							// sql语句根据  select project_name,status,source_xml,start_time,end_time,inst_owner_name from m_task where type="SQL" and ds =
							// 其中 source_xml
							DBOperatorMonitor dbOperatorMonitor = getDBOperatorMonitor(obj,dateStr);
							list.add(dbOperatorMonitor);
						}
					}
			}catch (Exception e){
				logger.error(ExceptionUtil.getExceptionTrace(e));
			}
			if(list.size() > 0){
				logger.info("处理后的数据放入队列供使用热度调用。");
				blockedQueueList.add(list);
			}

			if(list.size() > 0){  //都已录入成功
				/**
				 *  记录日志，说明某日的数据已查询出
				 */
				try{
					OperationLog log = new OperationLog();
					log.setId(UUID.randomUUID().toString().replace("-", ""));
					log.setLogName("数据库操作监控");
					log.setLogTime(DateUtil.parseDate(dateStr));
					log.setDataCount(list.size());
					operatorLogDao.insertOperationLog(log);
					logger.info("数据库操作监控日志记录插入结束。");

				}catch(Exception e){
					logger.error("数据库操作监控日志记录插入失败"+e);
				}
				System.gc();
				return true;
			}else{
				System.gc();
				logger.info(dateStr+"有数据入库异常，数据库操作监控日志记录不再插入。");
				return false;
			}
		}catch(Exception e){
			logger.error("数据库操作监控处理数据时出现异常"+e);
			return false;
		}

	}


	public DBOperatorMonitor getDBOperatorMonitor(Object[] objects, String dateStr){
		DBOperatorMonitor dbCounter=new DBOperatorMonitor();
		/**
		 *    主键id
		 */
		dbCounter.setId(UUID.randomUUID().toString().replace("-", ""));

		/**
		 *    数据库类型。目前解析阿里日志只有ODPS库
		 */
		dbCounter.setDbType("odps");
		/**
		 *   项目名
		 */
		try{
			dbCounter.setProjectName((String)objects[0]);
		}catch(Exception e){
			dbCounter.setProjectName(null);
		}

		/**
		 *  解析source_xml判断SQL操作类型。标识传0 代表是要判断的sql类型
		 */
		try{
			String sqlType = praseSourceXml((String)objects[2],0);
			dbCounter.setSqlTyle(Integer.valueOf(sqlType));
		}catch(Exception e){
			dbCounter.setSqlTyle(0);
		}

		/**
		 *  解析判断任务执行状态。异常状态下设置为11 代表未知状态
		 */
		try{
			dbCounter.setExecutiveState((String)objects[1]);
		}catch(Exception e){
			dbCounter.setExecutiveState("11");
		}

		/**
		 *   解析source_xml提取SQL语句。 标识传1 代表是要取得sql语句
		 */
		try{
			String sql = praseSourceXml((String)objects[2],1);
			if("0".equals(sql)){
				dbCounter.setSql(null);
			}else{
				dbCounter.setSql(sql);
			}
		}catch(Exception e){
			dbCounter.setSql(null);
		}

		/**
		 *   解析任务的开始时间
		 */
		try{
			dbCounter.setStartTime(praseTime(String.valueOf(objects[3])+"000"));
		}catch(Exception e){
			dbCounter.setStartTime(null);
		}

		/**
		 *   解析任务的结束时间
		 */
		try{
			dbCounter.setEndTime(praseTime(String.valueOf(objects[4])+"000"));
		}catch(Exception e){
			dbCounter.setEndTime(null);
		}

		/**
		 *   解析任务执行时长
		 */
		try{
			dbCounter.setExcuteTime(calulatelExecuteTime(String.valueOf(objects[3])+"000",String.valueOf(objects[4])+"000"));
		}catch(Exception e){
			dbCounter.setExcuteTime(null);
		}

		/**
		 *   解析账号名称
		 */
		try{
			dbCounter.setOwnerName((String)objects[5]);
		}catch(Exception e){
			dbCounter.setOwnerName(null);
		}

		/**
		 *   录入统计时间
		 */
		dbCounter.setMonitorTime(dateStr);
		return dbCounter;
	}


	public String praseSourceXml(String sourceXml, int flag) {
		List<String> list = new ArrayList<>();
		String prefix = "<Query>";
	    String suffix = "</Query>";
	    String rgex = prefix+"(.*?)"+suffix; 
		Pattern p =Pattern.compile(rgex);
		Matcher m = p.matcher(sourceXml);
		while(m.find()){
			int i = 1;
			list.add(m.group(1));
			i++;
		}
		if(flag==0&&list.size()!=0){
			String sqlTyle =  praseSqlType(list.get(0));
            return sqlTyle;
		}
		if(flag==1&&list.size()!=0){
			return list.get(0);  //返回SQL语句
		}
		return "0";
	}
	
	public String praseSqlType(String line) {
		String str = getFirstStr(line);
		if(str==null || "".equals(str)){
			return "0";
		}else{
			String fCode = str.toUpperCase();  
			if("SELECT".equals(fCode)){
				return "1";
			}else if("INSERT".equals(fCode)){
				return "2";
			}else if("COUNT".equals(fCode)){
				return "3";
			}else if("DELETE".equals(fCode)){
				return "4";
			}else if("UPDATE".equals(fCode)){
				return "5";
			}else if("CREATE".equals(fCode)){
				return "6";
			}else if("DROP".equals(fCode)){
				return "7";
			}else if("ALTER".equals(fCode)){
				return "8";
			}else if("SHOW".equals(fCode)){
				return "9";
			}else if("ANALYZE".equals(fCode)){
				return "10";
			}else if("ADD".equals(fCode)){
				return "11";
			}else{
				return "0";
			}
		}
	}
	//时间格式 XX时XX分XX秒
	public String praseTime(String time){
		if("".equals(time) || time==null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(time));
		Date date = calendar.getTime();
		String s = DateUtil.formatTime(date);
		String[] targets = s.split(":");
	    s = targets[0]+"时"+targets[1]+"分"+targets[2]+"秒";
	    return s;
	}
	
	public String calulatelExecuteTime(String start,String end){
		if(start==null || "".equals(start) || end==null || "".equals(end)){
			return "0";
		}
		String exeTime = null;
		long l = Long.valueOf(end)-Long.valueOf(start);
		if(l<1000 && l>0){
			float f = (float)l/(float)1000;
			exeTime = String.valueOf(f);
		}else if(l>=1000){
			long count = l/1000;
			exeTime = String.valueOf(count);
		}else{
			exeTime = "0";
		}
		return exeTime;
	}
	
	//今日零时零分零秒
	public long getTodayTime(){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),0,0,0);
		Date date = c.getTime();
		return date.getTime();
	}
	
	//昨日零时零分零秒 
	public Date getLastDayTime(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DATE,c.get(Calendar.DATE)-1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		Date date = c.getTime();
		return date;
	}
	
	public boolean insertData(List<DBOperatorMonitor> list){
		boolean isExe = false;
		logger.info("查询出的数据总为量："+list.size());
		List<DBOperatorMonitor> lists = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			lists.add(list.get(i));
	        if(i%500==0 && i!=0){
	        	logger.info("开始向oracle第"+(i/500)+"次插入数据,数据量为："+lists.size());
	        	try{
	        		if(lists.size()!=0){
	        			dbOperatorMonitorDao.insertDBOperatorRecord(lists);
	        			logger.info("向oracle第"+(i/500)+"次插入数据结束");
	        		}
	        	}catch(Exception e){
	        		logger.error("向oracle第"+(i/500)+"次插入数据异常。"+e);
	        		isExe = true;
	        	}
	        	lists.clear();
	        }
		}
		//插入剩余数据量
		logger.info("向oracle插入剩余数据，剩余数据量为："+lists.size());
		try{
			if(lists.size()!=0){
				dbOperatorMonitorDao.insertDBOperatorRecord(lists);
				logger.info("剩余数据插入数据结束");
			}
		}catch(Exception e){
			logger.error("向oracle插入剩余数据异常。"+e);
			isExe = true;
		}
		lists.clear();
		return isExe;
	}

	  
	public String getFirstStr(String line){
		  if(line==null || "".equals(line)){
			  return null;
		  }
		  String pattern = "[a-zA-Z]+('?[a-zA-Z])?";
		  Pattern r =Pattern.compile(pattern);
		  Matcher m = r.matcher(line);
		  while(m.find()){
			  return m.group();
		  }
		  return null;
	}

	  
}


























