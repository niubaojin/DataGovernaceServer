package com.synway.property.schedule;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.property.conditional.OdpsConditional;
import com.synway.property.config.AsyManager;
import com.synway.property.dao.DataStoreAndUseHeatDao;
import com.synway.property.pojo.DataStoreAndUseHeat;
import com.synway.property.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * 首页热度，以及月使用量
 * @author 数据接入
 */
@Service
@Conditional(OdpsConditional.class)
//@PropertySource("classpath:application.properties")
public class ODPSStoreCycleImpl {

    private static Logger logger = LoggerFactory.getLogger(ODPSStoreCycleImpl.class);

    @Autowired
    private AsyManager asyManager;

    @Autowired
    private DataStoreAndUseHeatDao dataStoreAndUseHeatDao;

    @Autowired
    private DruidDataSource dataSource;

    @Scheduled(cron = "${ODPSStoreCycle}")
    public void onTimeTask() {
        asyManager.addTask(() -> {
            logger.info("创建构造存储周期的OLAP表：");
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);

                logger.info("正在查询数据");
                //1、根据表名，查询最近一条数据
                List<DataStoreAndUseHeat> list = dataStoreAndUseHeatDao.findAll();
                logger.info("共查询到：" + list.size() + "条数据");

                if (list.size() == 0) {
                    return;
                }

                //2、清空现有的表中数据
                logger.info("正在清空前一天的数据!");
                long start = System.currentTimeMillis();

                boolean isdelete = deleteOldTable(connection, "StoreCycleAndUseHeat");
                logger.info("已清空前一天的数据！");

                //插数据入到OLAP表中
                logger.info("正在插入今日数据");
                boolean isInsert = batchInsert(list, connection);
                long end = System.currentTimeMillis();

                connection.commit();
                logger.info("插入到数据库完毕！共耗时[" + (end - start) / 1000 + "]秒");
            } catch (Exception e) {
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (Exception ignore) {
                    logger.error(ExceptionUtil.getExceptionTrace(ignore));
                }
                logger.error("构造OLAP表出错，已经回滚数据！错误信息如下：");
                logger.error(ExceptionUtil.getExceptionTrace(e));
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ignore) {
                        logger.error(ExceptionUtil.getExceptionTrace(ignore));
                    }
                }
            }
        });

    }

    public boolean deleteOldTable(Connection connection, String tableName) throws Exception {
        String sql = "delete from " + tableName;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            int count = pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public boolean batchInsert(List<DataStoreAndUseHeat> list, Connection connection) throws Exception {
        PreparedStatement pstmt = null;
        try {
            String SQLString = "insert into StoreCycleAndUseHeat(ID,lastDate,tableType,className,subclass,tableNameEN,tableNameZH," +
                    "lifeCycle,tableAllCount,tableSize,tableUseCount,tableUseCountOfDay,tableUseCountOfWeek,tableUseCountOfMonth)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(SQLString);
            int count = 0;
            for (DataStoreAndUseHeat entry : list) {
                pstmt.setString(1, entry.getId());
                pstmt.setString(2, entry.getLastDate());
                pstmt.setString(3, entry.getTableType());
                pstmt.setString(4, (entry.getClassName() == null || "".equals(entry.getClassName())) ? "其他" : entry.getClassName());
                pstmt.setString(5, (entry.getSubclass() == null || "".equals(entry.getSubclass())) ? "其他" : entry.getSubclass());
                pstmt.setString(6, entry.getTableNameEN());
                pstmt.setString(7, entry.getTableNameZH());
                pstmt.setInt(8, entry.getLifeCycle());
                pstmt.setLong(9, entry.getTableAllCount());
                pstmt.setLong(10, entry.getTableSize());
                pstmt.setLong(11, entry.getTableUseCount());
                pstmt.setLong(12, entry.getTableUseCountOfDay());
                pstmt.setLong(13, entry.getTableUseCountOfWeek());
                pstmt.setLong(14, entry.getTableUseCountOfMonth());

                pstmt.addBatch();
                count++;
                if (count % 1000 == 0) {//1000条提交一次
                    pstmt.executeBatch();
                    logger.info("已经插入[" + count + "]条记录");
                }
            }
            if (count % 1000 != 0) {
                pstmt.executeBatch();
                logger.info("共插入[" + count + "]条记录");
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return true;
    }

    //    @Scheduled(cron = "${doCreateObjectExtend}")
    public void doCreateObjectExtend() {
        asyManager.addTask(() -> {
            logger.info("创建构造objectExtend的OLAP表：");
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);

                logger.info("正在查询数据");
                List<DataStoreAndUseHeat> list = dataStoreAndUseHeatDao.findObjectExtend();
                logger.info("共查询到：" + list.size() + "条数据");

                //2、清空现有的表中数据
                logger.info("正在清空现有的数据!");
                long start = System.currentTimeMillis();

                boolean isdelete = deleteOldTable(connection, "objectextend");
                logger.info("已清空之前的数据！");

                //插数据入到OLAP表中
                logger.info("正在插入数据");
                boolean isInsert = batchInsertObjectExtend(list, connection);
                long end = System.currentTimeMillis();

                connection.commit();
                logger.info("插入到数据库完毕！共耗时[" + (end - start) / 1000 + "]秒");
            } catch (Exception e) {
                logger.error("构造OLAP表出错，已经回滚数据！错误信息如下：");
                logger.error(ExceptionUtil.getExceptionTrace(e));
//                e.printStackTrace();
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (Exception ignore) {
//                    ignore.printStackTrace();
                    logger.error(ExceptionUtil.getExceptionTrace(ignore));
                }

            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ignore) {
                        logger.error(ExceptionUtil.getExceptionTrace(ignore));
                    }
                }
            }
        });
    }

    public boolean batchInsertObjectExtend(List<DataStoreAndUseHeat> list, Connection connection) throws Exception {
        PreparedStatement pstmt = null;
        try {
            String SQLString = "insert into objectExtend(tableType,tableNameEN,tableSize,tablecreatedtime," +
                    "tableLastMetaModifiedTime,tableLastDataModifiedTime,partitiondate,partitionCount,tableproject)" +
                    " values(?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(SQLString);
            int count = 0;
            for (DataStoreAndUseHeat entry : list) {
                pstmt.setString(1, entry.getTableType());
                pstmt.setString(2, entry.getTableNameEN());
                pstmt.setLong(3, entry.getTableSize());
                pstmt.setString(4, entry.getTableCreatedTime());
                pstmt.setString(5, entry.getTableLastMetaModifiedTime());
                pstmt.setString(6, entry.getTableLastDataModifiedTime());
                pstmt.setString(7, entry.getPartitiondate());
                pstmt.setString(8, entry.getPartitionCount());
                pstmt.setString(9, entry.getTableProject());

                pstmt.addBatch();
                count++;
                if (count % 100 == 0) {//1000条提交一次
                    pstmt.executeBatch();
                    logger.info("已经插入[" + count + "]条记录");
                }
            }
            if (count % 100 != 0) {
                pstmt.executeBatch();
                logger.info("共插入[" + count + "]条记录");
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return true;
    }
}
