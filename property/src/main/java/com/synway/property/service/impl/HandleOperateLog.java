package com.synway.property.service.impl;

import com.synway.property.dao.DBOperatorMonitorDao;
import com.synway.property.dao.OperatorLogDao;
import com.synway.property.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author 数据接入
 */
@Service
public class HandleOperateLog {

    private static Logger logger = LoggerFactory.getLogger(HandleOperateLog.class);
    @Autowired
    private DBOperatorMonitorDao dbOperatorMonitorDao;
    @Autowired
    private DBOperatorMonitorImpl dbomImpl;
    @Autowired
    private OperatorLogDao operatorLogDao;

    //TODO 1.8.0可删除这下面的所有对应代码,具体询问wdw
//    @Scheduled(cron = "${dbOperatorMonitor}")  //每隔30分钟执行一次
    public void operateMTaskData() {
        try {
            Date nowDate = new Date();
            long nowDateTime = nowDate.getTime();
            Date dbDate = operatorLogDao.getOperateTime("数据库操作监控");
            if (dbDate != null) {
                logger.info("获取的数据库操作监控日志记录的最近时间为" + DateUtil.formatDate(dbDate));
                Date lastDate = getLastDayTime();
                long dbDateTime = dbDate.getTime();
                long lastDateTime = lastDate.getTime();
                long daysBetween;
                String dateStr;
                Map sql;
                logger.info("需要插入最近" + (lastDateTime - dbDateTime) / (3600 * 24 * 1000) + "天的数据。");
                if (dbDateTime < lastDateTime) {
                    daysBetween = (lastDateTime - dbDateTime) / (3600 * 24 * 1000);
                    int db = Integer.parseInt(String.valueOf(daysBetween - 1));
                    for (int i = db; i >= 0; i--) {
                        dateStr = getDate(i);
                        sql = getSql(i);
                        dbOperatorMonitorDao.deleteDBOperatorRecord(dateStr);
                        logger.info("查询m_task表的sql为:" + sql);
                        boolean bool = dbomImpl.handleDBOperateMoniteDatas(sql, dateStr);
                    }
                }
            } else {
                logger.info("获取的operate_log中数据库操作监控日志记录的最近时间为空，开始初始化第一条数据。");
                String dateStrInit = getDate(0);
                Map sqlInit = getSql(0);
                dbOperatorMonitorDao.deleteDBOperatorRecord(dateStrInit);
                logger.info("查询m_task表的sql为:" + sqlInit);
                dbomImpl.handleDBOperateMoniteDatas(sqlInit, dateStrInit);
                logger.info("初始化第一条数据结束");
            }

        } catch (Exception e) {
            logger.error("数据库操作监控处理异常数据发生错误" + e);
        }
    }

    //昨日零时零分零秒
    public Date getLastDayTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date = c.getTime();
        return date;
    }

    public Map getSql(int day) throws ParseException {
        Map<String,Object> map = new HashMap<>();
        map.put("projectName","meta");
        map.put("tableName","m_task");
        String dataStr;
        if (day == 0) {
            dataStr = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -1)));
        } else {
            String time = String.valueOf(-1 - day);
            dataStr = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), Integer.parseInt(time))));
        }
        map.put("partitions","ds='"+dataStr+"'");
        List<String> columnNames = new ArrayList<>();
        columnNames.add("project_name");
        columnNames.add("status");
        columnNames.add("source_xml");
        columnNames.add("start_time");
        columnNames.add("end_time");
        columnNames.add("inst_owner_name");
        map.put("columnNames",columnNames);
        return map;
    }

    public String getDate(int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1 - day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date = c.getTime();
        String dateStr = DateUtil.formatDate(date);
        return dateStr;
    }

    public long getTime(int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, day);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date = c.getTime();
        return date.getTime();
    }

}
