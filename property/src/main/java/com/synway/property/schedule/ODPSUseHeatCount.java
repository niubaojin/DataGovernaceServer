package com.synway.property.schedule;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.property.conditional.OdpsConditional;
import com.synway.property.config.AsyManager;
import com.synway.property.dao.DataStoreAndUseHeatDao;
import com.synway.property.pojo.CountTableUse;
import com.synway.property.pojo.DBOperatorMonitor;
import com.synway.property.pojo.ODPSSQLAnalyze;
import com.synway.property.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO 1.8.0可删除，具体询问wdw
 * @author 数据接入
 */
@Service
@Conditional(OdpsConditional.class)
public class ODPSUseHeatCount {
    private static Logger logger = LoggerFactory.getLogger(ODPSUseHeatCount.class);

    @Autowired
    private DataStoreAndUseHeatDao dataStoreAndUseHeatDao;

    public Long findUseHeatByTableNameList(List<String> tableNameList) {
        Map<String, String> map = new HashMap<String, String>();
        for (String tableName : tableNameList) {
            //如果表名中带有#和$就过滤掉
            if (StringUtils.isBlank(tableName) || tableName.contains("$") || tableName.contains("#")) {
                continue;
            }
            String projectName = "";
            if (tableName.contains(".")) {//如果带项目名称，则需要将项目名称去掉
                String[] tempStr = tableName.trim().split("\\.");
                if (tempStr.length == 2) {
                    projectName = tempStr[0].toUpperCase();
                    tableName = tempStr[1].toUpperCase();
                    tableName = tableName.replace(";", "");
                }
            }
            map.put(tableName, projectName);
        }
        return dataStoreAndUseHeatDao.findUseHeatByTableNameList(map);
    }


    @Autowired
    public ODPSUseHeatCount(AsyManager asyManager,final LinkedBlockingQueue<List<DBOperatorMonitor>> blockedQueue, final DruidDataSource dataSource) {
        asyManager.addTask(() -> {
            try {
                logger.info("进入ODPSUseHeatCount处理线程");
                logger.info("线程启动完毕！");
                while (true) {
                    List<DBOperatorMonitor> monitorList = blockedQueue.take();
                    logger.info("本次接受到[" + monitorList.size() + "]条记录");
                    ArrayList<CountTableUse> odpsTableTypeList = new ArrayList<>();
                    HashMap<Integer, CountTableUse> resultDict = new HashMap<>();
                    for (DBOperatorMonitor monitor : monitorList) {
                        //进行SQL语句的分析
                        odpsTableTypeList.addAll(ODPSSQLAnalyze.analyzeSQL(monitor));
                    }
                    //合并操作，将相同的id、tablename、projectname记录合并
                    for (CountTableUse tableUse : odpsTableTypeList) {
                        CountTableUse oldValue = resultDict.get(tableUse.hashCode());
                        if (oldValue != null) {
                            oldValue.incrementsCount(1);//将使用个数+1
                        } else {
                            tableUse.incrementsCount(1);
                            resultDict.put(tableUse.hashCode(), tableUse);
                        }
                    }
//							logger.info(resultDict);
                    //合并完之后，遍历此列表进行插入数据库操作
                    logger.info("合并完之后，遍历此列表进行插入数据库操作");
                    logger.info("共有[" + resultDict.size() + "]条记录需要插入");
                    batchInsert(resultDict, dataSource);
                    logger.info("本次插入完成！");
                    monitorList.clear();
                    odpsTableTypeList.clear();
                    resultDict.clear();
                    System.gc();
                }

            } catch (Exception e) {
                logger.info(ExceptionUtil.getExceptionTrace(e));
            }
        });
    }

    public void batchInsert(HashMap<Integer, CountTableUse> resultDict, DruidDataSource dataSource) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = dataSource.getConnection();
            String SQLString = "insert into DP_UT_ODPSSQLCOUNT(ID,TABLENAME,TABLE_USE_COUNT,PROJECT,SQLType,DT) values(?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = 0;
            for (Entry<Integer, CountTableUse> entry : resultDict.entrySet()) {
                CountTableUse ctu = entry.getValue();
                pstmt.setString(1, ctu.getId());
                pstmt.setString(2, ctu.getTableName());
                pstmt.setInt(3, ctu.getUseCount());
                pstmt.setString(4, ctu.getProjectName());
                pstmt.setInt(5, ctu.getSQLType());
                pstmt.setString(6, ctu.getDT());

                pstmt.addBatch();
                count++;
                if (count % 1000 == 0) {
                    //1000条提交一次
                    pstmt.executeBatch();
                }
            }
            if (count % 1000 != 0) {
                pstmt.executeBatch();
                logger.info("已经插入[" + count + "]条记录");
            }
            connection.commit();

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception e2) {
                logger.error(ExceptionUtil.getExceptionTrace(e2));
            }
            logger.error(ExceptionUtil.getExceptionTrace(e));
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
        }
    }

    public static void main(String[] args) {
//		ODPSUseHeatCount useHeatCount = new ODPSUseHeatCount();
//		LinkedBlockingQueue<List<DBOperatorMonitor>> blockedQueueList2 = useHeatCount.blockedQueueList;
//
//		List<DBOperatorMonitor> list=new ArrayList<DBOperatorMonitor>();
//		DBOperatorMonitor operatorMonitor=new DBOperatorMonitor();
//		operatorMonitor.setId("21321321");
//		operatorMonitor.setProjectName("project");
//		operatorMonitor.setSqlTyle(1);
//		operatorMonitor.setSql("select a.fhf,a.disjf,b.dskjff from test1 a,test2 b");
//		StringBuffer sb=new StringBuffer();
//		sb.append(" select max(lastdate) as lastdate,tabletype,tablenameen,tablenamezh,max(lifecycle) as lifecycle,sum(tableallcount) as tableallcount,sum(tablesize) as tablesize,subclass,classname,tableUseCountOfDay,tableUseCountOfWeek,tableUseCountOfMonth,tableUseCount from(")
//		.append(" select s.PARTITIONDATE as lastDate,s.TABLETYPE,s.TABLENAME as tableNameEN,s.TABLECOMMENT as tableNameZH,s.LIFECYCLE,s.TABLEALLCOUNT,s.TABLESIZE,c.SUBCLASS,c.CLASSNAME,")
//		.append(" u.tableUseCountOfDay,u.tableUseCountOfWeek,u.tableUseCountOfMonth,u.tableUseCount")
//		.append(" from SYNDMG_TABLE_ALL s ")
//		.append(" left join classified_table c on(c.tablename_en=s.tablename) ")
//		.append(" left join (")
//		.append(" select t3.tablename,t3.project,t3.tableUseCountOfMonth,t2.tableUseCountOfWeek,t1.tableUseCountOfDay,t0.tableUseCount from(")
//		.append(" select  sum(table_Use_Count) as tableUseCount,tablename,project from DP_UT_ODPSSQLCOUNT t group by tablename,project) t0")
//		.append(" left join")
//		.append(" (select sum(table_Use_Count) as tableUseCountOfMonth,tablename,project from DP_UT_ODPSSQLCOUNT t")
//		.append(" where dt between trunc(sysdate - interval '31' day) and trunc(sysdate - interval '1' day) group by tablename,project) t3 on (t0.tablename=t3.tablename)")
//		.append(" left join")
//		.append(" (select sum(table_Use_Count) as tableUseCountOfWeek,tablename,project from DP_UT_ODPSSQLCOUNT t")
//		.append(" where dt between trunc(sysdate - interval '7' day) and trunc(sysdate - interval '1' day) group by tablename,project) t2 on (t0.tablename=t2.tablename)")
//		.append(" left join")
//		.append(" (select sum(table_Use_Count) as tableUseCountOfDay,tablename,project from DP_UT_ODPSSQLCOUNT t")
//		.append(" where dt=trunc(sysdate - interval '1' day) group by tablename,project) t1 on (t0.tablename=t1.tablename)")
//		.append(" ) u on (u.tablename=s.tablename and s.tableproject=u.project)")
//		.append(") )");

//		System.out.println(sb.toString());
//		operatorMonitor.setSql(sb.toString());
//		list.add(operatorMonitor);
//		blockedQueueList2.add(list);
//
//		useHeatCount.dataDelayStatistic();
    }

}
