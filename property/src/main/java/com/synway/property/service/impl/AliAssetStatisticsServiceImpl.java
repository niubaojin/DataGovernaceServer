package com.synway.property.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.aliyun.odps.*;
import com.synway.property.config.TransactionUtil;
import com.synway.property.constant.Common;
import com.synway.property.dao.AliAssetStatisticsDao;
import com.synway.property.dao.DAOHelper;
import com.synway.property.pojo.aliAssetStatistics.SyndmgTableAll;
import com.synway.property.service.AliAssetStatisticsService;
import com.synway.property.thread.OdpsStatisticsCallable;
import com.synway.property.util.AdsOrMysqlClient;
import com.synway.property.util.DateUtil;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.OdpsClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
public class AliAssetStatisticsServiceImpl implements AliAssetStatisticsService {
    private static Logger logger = LoggerFactory.getLogger(AliAssetStatisticsService.class);

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier(value = "adsDataSourceMeta")
    private DruidDataSource adsDataSourceMeta;

    @Autowired
    @Qualifier(value = "adsDataSourceSys")
    private DruidDataSource adsDataSourceSys;

    @Autowired
    AliAssetStatisticsDao aliAssetStatisticsDao;

    @Autowired(required = false)
    @Qualifier(value = "odpsClient")
    private OdpsClient odpsClient;

    @Autowired(required = false)
    @Qualifier(value = "useHeatClient")
    private OdpsClient useHeatClient;

    @Autowired
    private TransactionUtil transactionUtil;

    @Override
    public void runAliAssetStatistics() {
        try {
            logger.info("====开始统计odps资产信息");
//            getOdpsStatisticsInfo();
            // 改为多线程执行（线程数为项目数）
            getOdpsStatisticsInfoThread();
            logger.info("====统计odps资产信息结束");
            logger.info("====开始统计ads资产信息");
            getAdsStatisticsInfo();
            logger.info("====统计ads资产信息结束");
        } catch (Exception e) {
            logger.error("统计odps资产信息报错:\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    public void getAdsStatisticsInfo() {
        long startTime = System.currentTimeMillis();
        AdsOrMysqlClient adsOrMysqlClient = new AdsOrMysqlClient();
        try {
            JSONArray jsonArray = adsOrMysqlClient.execBySql(adsDataSourceMeta, Common.syndmgTablesByMetaSql);
            List<SyndmgTableAll> allList = JSONArray.parseArray(jsonArray.toJSONString(), SyndmgTableAll.class);
            // ads统计列表
            List<SyndmgTableAll> syndmgTableAlls = createAdsTabInfo(allList, adsOrMysqlClient);

            // 插入数据
            if (syndmgTableAlls.size() > 0){
                logger.info("开始插入ads的统计数据");
                insertTabTnfo(syndmgTableAlls);
                // 删除过期的历史数据
                logger.info("开始删除ads的过期数据");
                syndmgTableAlls.stream().forEach(syndmgTableAll -> {
                    delHisData(syndmgTableAll);
                });
            }
            long endTime = System.currentTimeMillis();
            logger.info("ads全表数据插入成功,用时:" + ((endTime - startTime) / 1000 / 60) + "分");
        } catch (Exception e) {
            logger.error("统计ads出错：\n{}", e);
        }
    }

    /**
     * 方法描述:获取ads统计信息
     *
     * @param allList
     */
    public List<SyndmgTableAll> createAdsTabInfo(List<SyndmgTableAll> allList, AdsOrMysqlClient adsOrMysqlClient) {
        // 阿里资产统计信息对象
        List<SyndmgTableAll> syndmgTableAlls = new ArrayList<>();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String adsHc = environment.getProperty("hc_adsproject");
        String adsHp = environment.getProperty("hp_adsproject");
        long dBefore = dateBefore(1);                                // 昨天日期

        for (SyndmgTableAll syndmgTableAll : allList) {
            String tableName = syndmgTableAll.getTableName();           // 表名
            String tableProject = syndmgTableAll.getTableProject();     // 项目空间名称
            String isPartitionStr = syndmgTableAll.getIsPartitionStr(); // 表类型(是否分区表)
            String partColumn = syndmgTableAll.getPartColumn();         // 分区列

            // 平台类型
            int tableType = 2;
            if (adsHc.toLowerCase().contains(tableProject.toLowerCase())) {
                tableType = 2;
            } else if (adsHp.toLowerCase().contains(tableProject.toLowerCase())) {
                tableType = 3;
            }else {
                logger.info(String.format("ADS,表名：%s.%s,项目名异常不在统计范围内，略过...", tableProject, tableName));
                continue;
            }
            // 判断是否临时表
            if (getIsTemporaryTable(tableName)){
                logger.info(String.format("ADS,表名：%s.%s，临时表不在统计范围，略过...", tableProject, tableName));
                continue;
            }
            // 判断是否分区表
            if ("1".equals(isPartitionStr) || "true".equalsIgnoreCase(isPartitionStr)) {
                syndmgTableAll.setIsPartition(0);
            }else {
                syndmgTableAll.setIsPartition(1);
            }
            try {
                // 数据规模
                JSONArray dssArray = adsOrMysqlClient.execBySql(adsDataSourceMeta, String.format(Common.dataStorageScaleSql, tableProject, tableName));
                List<SyndmgTableAll> dataStorageScales = JSONArray.parseArray(dssArray.toJSONString(), SyndmgTableAll.class);
                SyndmgTableAll dataStorageScale = dataStorageScales.get(0);
                syndmgTableAll.setTableSize(dataStorageScale.getTableSize());
                syndmgTableAll.setTableAllCount(dataStorageScale.getTableAllCount());
                logger.info(String.format(String.format("表%s.%s，总大小为：%s，总数据量为：%s", tableProject, tableName, dataStorageScale.getTableSize(), dataStorageScale.getTableAllCount())));
                // 最后修改时间
                JSONArray lastDataTimeArray = adsOrMysqlClient.execBySql(adsDataSourceMeta, String.format(Common.lastDataModifiedTimeSql, tableName));
                String tableLastDataModifiedTime = lastDataTimeArray.getJSONObject(0).getString("lastDataModifiedTime");
                syndmgTableAll.setTableLastDataModifiedTime(tableLastDataModifiedTime); // 数据最后修改时间
                long partitionCount = 0;
                if (StringUtils.isNotBlank(tableLastDataModifiedTime)){
                    Date ld = sdformat.parse(tableLastDataModifiedTime);                // 最后修改时间
                    String ldmt = sdf.format(ld);                                       // 最后修改时间
                    String sBefore = String.valueOf(dBefore);
                    if (syndmgTableAll.getIsPartition() == 0 && StringUtils.isNotBlank(partColumn) && ldmt.compareTo(sBefore) >= 0){
                        // 切换项目空间
                        String partitionCountSql = String.format("select count(*) as partitionCount from %s.%s where %s=%d",tableProject, tableName, partColumn, dBefore);
                        adsOrMysqlClient.execBySql(adsDataSourceSys, "use " + tableProject);
                        JSONArray partitionCountArray = adsOrMysqlClient.execBySql(adsDataSourceSys, partitionCountSql);
                        partitionCount = partitionCountArray.getJSONObject(0).getInteger("partitionCount");
                    }else {
                        partitionCount = 0;
                    }
                }else {
                    partitionCount = 0;
                }
                logger.info(String.format("表%s.%s，分区数据量为：%s", tableProject, tableName, partitionCount));

                // 参数注入
                syndmgTableAll.setTableType(tableType);                                 // 平台类型：1、odps, 2、hc, 3、hp
                String partitionDate = String.valueOf(dBefore);                         // 分区日期列
                syndmgTableAll.setPartitionData(partitionDate);
                syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                syndmgTableAll.setPartitionSize(0);                                     // 分区大小
                syndmgTableAll.setPartitionNum(0);                                      // 分区数
                syndmgTableAll.setInsertDataTime(DateUtil.formatDateTime(new Date()));  // 插入时间
                syndmgTableAll.setDataId("ads");
                syndmgTableAlls.add(syndmgTableAll);
            } catch (Exception e) {
                logger.error("执行sql出错：\n{}", e);
            }
        }
        return syndmgTableAlls;
    }

    /*统计odps资产(多线程)*/
    public void getOdpsStatisticsInfoThread() {
        String projectsAll = environment.getProperty("odps_alltable");
        String[] projects = projectsAll.split(",");
        // 创建线程池
        int threadNum = Integer.parseInt(environment.getProperty("odpsStatiscThreadNum"));
        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < projects.length; i++) {
            Tables tables = odpsClient.getOdpsTables(projects[i]);
            for (Table table : tables) {
                if (getIsTemporaryTable(table.getName())){
                    logger.info(String.format("ODPS,临时表：%s，不在统计范围，略过...", table.getName()));
                    continue;
                }
                // 执行任务并获取Future 对象
                Callable callable = new OdpsStatisticsCallable(table, useHeatClient, environment);
                Future future = pool.submit(callable);
                list.add(future);
            }
        }
        // 关闭线程池
        pool.shutdown();
        // 获取所有并发任务的运行结果(统一入库、减少数据库操作)
        List<SyndmgTableAll> syndmgTableAlls = new ArrayList<>();
        for (Future<List<SyndmgTableAll>> future : list){
            try {
                List<SyndmgTableAll> syndmgTableAllsTemp = future.get();
                if (syndmgTableAllsTemp.size() > 0){
                    syndmgTableAlls.addAll(syndmgTableAllsTemp);
                }
            } catch (Exception e) {
                logger.error("获取future报错：\n" + ExceptionUtil.getExceptionTrace(e));
                continue;
            }
        }
        // 插入数据
        if (syndmgTableAlls.size() > 0){
            logger.info("开始插入odps的统计数据");
            insertTabTnfo(syndmgTableAlls);
            // 删除过期的历史数据
            logger.info("开始删除odps的过期数据");
            syndmgTableAlls.stream().forEach(syndmgTableAll -> {
                delHisData(syndmgTableAll);
            });
        }
        logger.info("odps预统计表信息完成,表总数:" + syndmgTableAlls.size());
    }

    // 根据配置判断是否临时表，如果是临时表则不统计
    public boolean getIsTemporaryTable(String tableName){
        boolean flag = false;
        String tempStartWith = environment.getProperty("tempStartWith");
        String tempEndWith = environment.getProperty("tempEndWith");
        String[] tempStartWiths = tempStartWith.split(",");
        String[] tempEndWiths = tempEndWith.split(",");
        for (String tempStart : tempStartWiths){
            if (tableName.startsWith(tempStart)){
                flag = true;
                break;
            }
        }
        for (String tempEnd : tempEndWiths){
            if (tableName.endsWith(tempEnd)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 插入数据
     *
     * @param syndmgTableAlls
     */
    public void insertTabTnfo(List<SyndmgTableAll> syndmgTableAlls) {
        try {
            // 批量插入
            transactionUtil.transaction(s -> {
                SyndmgTableAll syndmgTableAll = syndmgTableAlls.get(0);
                int deleteCount = aliAssetStatisticsDao.delTodayDataAllByOdps(syndmgTableAll);
                logger.info(String.format("资产统计表删除昨日分区数据：%d条", deleteCount));
                try {
                    DAOHelper.insertDelList(syndmgTableAlls, aliAssetStatisticsDao, "insertTableInfo", 200);
                    logger.info(String.format("资产统计表新插入昨日分区数据：%d条", syndmgTableAlls.size()));
                } catch (Exception e) {
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            });
            // 更新odps表的全量数据量
            if (syndmgTableAlls.get(0).getTableType() == 1){
                syndmgTableAlls.stream().forEach(syndmgTableAll -> {
                    updateTabAllCount(syndmgTableAll);
                });
            }
        } catch (Exception e) {
            logger.error("保存统计信息出错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 更改全表行数
     *
     * @param syndmgTableAll
     */
    private void updateTabAllCount(SyndmgTableAll syndmgTableAll) {
        // TODO Auto-generated method stub
        long tableAllCount = 0;
        try {
            tableAllCount = getTabAllCount(syndmgTableAll);
            if (tableAllCount == -1) {
                tableAllCount = 0;
            }
            syndmgTableAll.setTableAllCount(tableAllCount);
            aliAssetStatisticsDao.updateSyndmgTableAll(syndmgTableAll);
        } catch (Exception e) {
            logger.error(String.format("表%s.%s更新总条数至table_syndmg_table失败", syndmgTableAll.getTableProject(), syndmgTableAll.getTableName()) + "\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 根据 分区 生命周期获取全表行数
     *
     * @param syndmgTableAll
     * @throws Exception
     */
    public Long getTabAllCount(SyndmgTableAll syndmgTableAll) {
        long dBefore = dateBefore(1);   // 昨天
        List<SyndmgTableAll> listCount = new ArrayList<>();
        long tableAllCount = 0;

        listCount = aliAssetStatisticsDao.getTableAllCount(syndmgTableAll, dBefore);
        if (listCount !=null && listCount.size() > 0) {
            if (!"".equals(listCount.get(0).toString())){
                tableAllCount = listCount.get(0).getPartitionCount();
            }
        }
        return tableAllCount;
    }

    /**
     * 删除ODPS历史数据
     */
    public void delHisData(SyndmgTableAll syndmgTableAll) {
        long daysAgo = 0;
        if (syndmgTableAll.getLifeCycle() > 0 && syndmgTableAll.getIsPartition() == 0) {
            daysAgo = dateBefore((int) syndmgTableAll.getLifeCycle());                              // 当前日期减去生命周期天数
        } else if (syndmgTableAll.getIsPartition() == 1) {
            daysAgo = dateBefore(Integer.parseInt(environment.getProperty("fullTableSaveDays")));   // 当前日期减去生命周期天数(全量表默认保留30天数据)
        } else {
            daysAgo = dateBefore(3600);                                                          // 当前日期减去生命周期天数（如果都没匹配到，默认3600）
        }
        try {
            String day = String.valueOf(daysAgo);                        // 分区日期
            String minDelDate = day + "";                                // 分区日期
            aliAssetStatisticsDao.delHisData(syndmgTableAll, minDelDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述: 当前日期前n天
     *
     * @return
     * @author:lhl
     * @date: 2016年11月23日 下午3:15:52
     */
    public static long dateBefore(int i) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -i);//
        long d = Integer.parseInt(sdf.format(calendar.getTime()));
        return d;
    }

}
