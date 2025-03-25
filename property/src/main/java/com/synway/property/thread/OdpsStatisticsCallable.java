package com.synway.property.thread;

import com.aliyun.odps.*;
import com.synway.property.pojo.aliAssetStatistics.SyndmgTableAll;
import com.synway.property.util.DateUtil;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.OdpsClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Callable;

@Component
public class OdpsStatisticsCallable implements Callable<List<SyndmgTableAll>> {
    private Logger logger = LoggerFactory.getLogger(OdpsStatisticsCallable.class);

    private final static String dt = "dt";
    private Table table;
    private OdpsClient useHeatClient;
    private Environment environment;
//    private AliAssetStatisticsDao aliAssetStatisticsDao;
//    private TransactionUtil transactionUtil;

    public OdpsStatisticsCallable(){}

    public OdpsStatisticsCallable(Table table,
                                  OdpsClient useHeatClient,
                                  Environment environment){
        this.table = table;
        this.useHeatClient = useHeatClient;
        this.environment = environment;
//        this.aliAssetStatisticsDao = aliAssetStatisticsDao;
//        this.transactionUtil = transactionUtil;
    }

    @Override
    public List<SyndmgTableAll> call() {
        List<SyndmgTableAll> syndmgTableAlls = odpsStatisticsByProject(table);
        return syndmgTableAlls;
    }

    public List<SyndmgTableAll> odpsStatisticsByProject(Table table){
        List<SyndmgTableAll> syndmgTableAlls = new ArrayList<>();
        try {
            try {
                table.isPartitioned();
            }catch (Exception e){
                logger.info("表：" + table.getName() + "，判断是否分区表时出错，继续下张表...");
                return syndmgTableAlls;
            }
            logger.info("开始统计表：" + table.getName());
            boolean isPartitionTable = table.isPartitioned();
            if (isPartitionTable) {// 分区表
                List<SyndmgTableAll> syndmgTableAllsTable = new ArrayList<>();
                boolean isFirstRun = StringUtils.equalsIgnoreCase(environment.getProperty("isFirstRun"), "true") ? true : false;
                syndmgTableAllsTable = getSynodsPart(table, isFirstRun, isPartitionTable);
                syndmgTableAlls.addAll(syndmgTableAllsTable);
            } else {// 非分区表
                List<SyndmgTableAll> syndmgTableAllsTable = getNotPartition(table, isPartitionTable);
                syndmgTableAlls.addAll(syndmgTableAllsTable);
            }
        } catch (Exception e) {
            logger.error("统计表：" + table.getName() + "时出错\n" + ExceptionUtil.getExceptionTrace(e));
        }
        return syndmgTableAlls;
    }

    /**
     * 获取odps分区表信息
     *
     * @param t
     * @return
     */
    public List<SyndmgTableAll> getSynodsPart(Table t, boolean isFirstRun, boolean isPartitionTable) {
        // 阿里资产统计信息对象
        List<SyndmgTableAll> syndmgTableAlls = new ArrayList<>();
        SyndmgTableAll syndmgTableAll = injectSyndmgTableAll(t, isPartitionTable);

        /*获取分区数据量及大小*/
        List<Partition> listP = t.getPartitions();
        if (listP.size() > 0) {
            syndmgTableAlls = getPartitionInfo(t, syndmgTableAll, isFirstRun, syndmgTableAlls);
        }else {
            syndmgTableAlls.add(syndmgTableAll);
        }
        return syndmgTableAlls;
    }

    public SyndmgTableAll injectSyndmgTableAll(Table t, boolean isPartitionTable) {
        // 参数注入
        SyndmgTableAll syndmgTableAll = new SyndmgTableAll();
        String sdBefore = DateUtil.addDayStr(new Date(), -1);  // 昨天分区
        syndmgTableAll.setTableName(t.getName());                   // 表名
        syndmgTableAll.setTableProject(t.getProject());             // 项目名
        syndmgTableAll.setTableComment(t.getComment());             // 表注释
        syndmgTableAll.setTableType(1);                             // 平台类型：1、odps, 2、hc, 3、hp
        syndmgTableAll.setLifeCycle(t.getLife());                   // 生命周期
        syndmgTableAll.setTableSize(t.getSize());                   // 表大小
        if (!isPartitionTable){
            syndmgTableAll.setPartitionSize(t.getSize());           // 分区存储量
        }
        syndmgTableAll.setIsPartition(isPartitionTable ? 0 : 1);    // 是否为分区列 0是 1不是
        syndmgTableAll.setPartitionData(sdBefore);                  // 昨天分区
        syndmgTableAll.setTableLastDataModifiedTime(DateUtil.formatDateTime(t.getLastDataModifiedTime()));  // 数据最后修改时间
        syndmgTableAll.setTableLastMetaModifiedTime(DateUtil.formatDateTime(t.getLastMetaModifiedTime()));  // 表最后修改时间(DDL最后变更时间)
        syndmgTableAll.setTableCreatedTime(DateUtil.formatDateTime(t.getCreatedTime()));                    // 表创建时间
        syndmgTableAll.setInsertDataTime(DateUtil.formatDateTime(new Date()));                              // 插入时间
        syndmgTableAll.setPartitionNum(isPartitionTable ? t.getPartitions().size() : 0);                    // 一级分区数
        return syndmgTableAll;
    }

    public List<SyndmgTableAll> getPartitionInfo(Table t, SyndmgTableAll syndmgTableAll, boolean isFirstRun, List<SyndmgTableAll> syndmgTableAlls) {
        try {
            List<Partition> listP = t.getPartitions();
            int length = listP.size() - 1;
            Partition p = listP.get(length);
            Set<String> set = p.getPartitionSpec().keys();
            if (set.size() == 1) {
                // 一级分区
                oneLevel(t, syndmgTableAll, isFirstRun, syndmgTableAlls, set);
            } else {
                // 多级分区
                multiLevel(t, syndmgTableAll, isFirstRun, syndmgTableAlls, set);
            }
            return syndmgTableAlls;
        } catch (Exception e) {
            logger.error("获取分区表信息失败：" + t.getProject() + "." + t.getName() + "\n" + ExceptionUtil.getExceptionTrace(e));
            return syndmgTableAlls;
        }
    }

    // 一级分区
    public void oneLevel(Table t,
                         SyndmgTableAll syndmgTableAll,
                         boolean isFirstRun,
                         List<SyndmgTableAll> syndmgTableAlls,
                         Set<String> set) throws Exception {

        String tableName = t.getName();                             // 表名
        String tableProject = t.getProject();                       // 项目名
        String sdBefore = DateUtil.addDayStr(new Date(), -1); // 昨天日期
        long partitionCount = 0l;                                   // 分区数据量
        long partitionSize = 0l;                                    // 分区大小
        String partition = "";
        List<Partition> listP = t.getPartitions();
        int length = listP.size() - 1;
        String sdt = set.iterator().next();
        for (int j = length; j >= 0; j--) {
            Partition p = listP.get(j);
            String partitionDate = p.getPartitionSpec().get(sdt);
            partition = sdt + "='" + partitionDate + "'";
            if (dt.equals(sdt)) { //分区=dt
                if (!isFirstRun) {
                    if (sdBefore.equals(partitionDate)) {   //dt=昨天
                        partitionCount = useHeatClient.getTableCount(tableProject, tableName, partition, true);
                        partitionSize = p.getSize();
                        syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                        syndmgTableAll.setPartitionSize(partitionSize);                         // 分区大小
                        syndmgTableAlls.add(syndmgTableAll);
                        logger.info(String.format("分区表：%s.%s, 数据量：%s, 数据大小：%s, 分区时间：%s, 实际入库分区：%s", t.getProject(), t.getName(), partitionCount, partitionSize, partition, syndmgTableAll.getPartitionData()));
                        break;
                    }
                } else {
                    partitionCount = useHeatClient.getTableCount(tableProject, tableName, partition, true);
                    try {
                        partitionSize = p.getSize();
                    }catch (Exception e){
                        logger.info(String.format("表：%s.%s获取分区大小失败，继续下个分区...",tableProject,tableName));
                        continue;
                    }
                    syndmgTableAll = injectSyndmgTableAll(t, true);
                    syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                    syndmgTableAll.setPartitionSize(partitionSize);                         // 分区大小
                    syndmgTableAll.setPartitionData(partitionDate);                         // 分区日期（全量时重新设置）
                    syndmgTableAlls.add(syndmgTableAll);
                    logger.info(String.format("分区表：%s.%s, 数据量：%s, 数据大小：%s, 分区时间：%s, 实际入库分区：%s", t.getProject(), t.getName(), partitionCount, partitionSize, partition, syndmgTableAll.getPartitionData()));
                }
            } else { //分区!=dt
                if (!isFirstRun) {
                    String plastmetadate = DateUtil.formatDateSimple(listP.get(length).getLastDataModifiedTime());
                    if (sdBefore.equals(plastmetadate)) {   //最后数据修改时间=昨天
                        partitionCount = useHeatClient.getTableCount(tableProject, tableName, partition, true);
                        partitionSize = p.getSize();
                        syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                        syndmgTableAll.setPartitionSize(partitionSize);                         // 分区大小
                        syndmgTableAlls.add(syndmgTableAll);
                        logger.info(String.format("分区表：%s.%s, 数据量：%s, 数据大小：%s, 分区时间：%s, 实际入库分区：%s", t.getProject(), t.getName(), partitionCount, partitionSize, partition, syndmgTableAll.getPartitionData()));
                        break;
                    }
                } else {
                    partitionCount = useHeatClient.getTableCount(tableProject, tableName, partition, true);
                    partitionSize = p.getSize();
                    syndmgTableAll = injectSyndmgTableAll(t, true);
                    syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                    syndmgTableAll.setPartitionSize(partitionSize);                         // 分区大小
                    syndmgTableAll.setPartitionData(partitionDate);                         // 分区日期（全量时重新设置）
                    syndmgTableAlls.add(syndmgTableAll);
                    logger.info(String.format("分区表：%s.%s, 数据量：%s, 数据大小：%s, 分区时间：%s, 实际入库分区：%s", t.getProject(), t.getName(), partitionCount, partitionSize, partition, syndmgTableAll.getPartitionData()));
                }
            }
        }
    }

    // 多级分区时，分区数量只获取第一级分区数
    public void multiLevel(Table t,
                           SyndmgTableAll syndmgTableAll,
                           boolean isFirstRun,
                           List<SyndmgTableAll> syndmgTableAlls,
                           Set<String> set) throws Exception {
        String tableName = t.getName();                             // 表名
        String tableProject = t.getProject();                       // 项目名
        String sdBefore = DateUtil.addDayStr(new Date(), -1); // 昨天日期
        long partitionCount = 0l;                                   // 分区数据量
        long partitionSize = 0l;                                    // 分区大小
        String partition = "";
        List<Partition> listP = t.getPartitions();
        int length = listP.size() - 1;

        Set partitionSpecSet = new HashSet();
        for (int i = length; i >= 0; i--) {
            String partitionSpec = listP.get(i).getPartitionSpec().toString().split(",")[0];
            partitionSpecSet.add(partitionSpec);
        }
        syndmgTableAll.setPartitionNum(partitionSpecSet.size());          // 多级分区数
        for (int j = length; j >= 0; j--) {
            Partition p = listP.get(j);
            StringBuilder sb = new StringBuilder();
            for (String key1 : set) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(key1);
                sb.append("='");
                sb.append(p.getPartitionSpec().get(key1));
                sb.append("'");
            }
            partition = sb.toString();
            if (!isFirstRun) {
                if (sb.toString().contains(sdBefore)) {
                    partitionCount = useHeatClient.getTableCount(tableProject, tableName, partition, true);
                    partitionSize = p.getSize();
                    syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                    syndmgTableAll.setPartitionSize(partitionSize);                         // 分区大小
                    syndmgTableAlls.add(syndmgTableAll);
                    logger.info(String.format("分区表：%s.%s, 数据量：%s, 数据大小：%s, 分区时间：%s, 实际入库分区：%s", t.getProject(), t.getName(), partitionCount, partitionSize, partition, syndmgTableAll.getPartitionData()));
                    break;
                }
            } else {
                partitionCount = useHeatClient.getTableCount(tableProject, tableName, partition, true);
                partitionSize = p.getSize();
                syndmgTableAll.setPartitionCount(partitionCount);                       // 分区数据量
                syndmgTableAll.setPartitionSize(partitionSize);                         // 分区大小
                syndmgTableAlls.add(syndmgTableAll);
                logger.info(String.format("分区表：%s.%s, 数据量：%s, 数据大小：%s, 分区时间：%s, 实际入库分区：%s", t.getProject(), t.getName(), partitionCount, partitionSize, partition, syndmgTableAll.getPartitionData()));
            }
        }
    }

    /**
     * 不是分区表
     *
     * @param t
     * @return
     */
    public List<SyndmgTableAll> getNotPartition(Table t, boolean isPartitionTable) {
        List<SyndmgTableAll> syndmgTableAlls = new ArrayList<>();
        SyndmgTableAll syndmgTableAll = injectSyndmgTableAll(t, isPartitionTable);
        long count = 0;
        try {
            logger.info("开始统计全量表数据量");
            count = useHeatClient.getTableCount(t.getProject(), t.getName(), "notPartiton", false);
        } catch (Exception e) {
            logger.error("全量表统计数据量失败：" + t.getName() + "\n" + ExceptionUtil.getExceptionTrace(e));
        }
        syndmgTableAll.setPartitionCount(count);    // 表分区行数
        syndmgTableAlls.add(syndmgTableAll);
        logger.info(String.format("全量表：%s.%s, 数据量：%s, 数据大小：%s, 实际入库分区：%s", t.getProject(), t.getName(), count, t.getSize(), syndmgTableAll.getPartitionData()));
        return syndmgTableAlls;
    }

//    /**
//     * 插入数据
//     *
//     * @param syndmgTableAlls
//     */
//    public void insertTabTnfo(List<SyndmgTableAll> syndmgTableAlls) {
//        try {
//            // 批量插入
//            transactionUtil.transaction(s -> {
//                SyndmgTableAll syndmgTableAll = syndmgTableAlls.get(0);
//                int deleteCount = aliAssetStatisticsDao.delTodayDataAllByOdps(syndmgTableAll);
//                logger.info(String.format("资产统计表删除昨日分区数据：%d条", deleteCount));
//                try {
//                    DAOHelper.insertDelList(syndmgTableAlls, aliAssetStatisticsDao, "insertTableInfo", 500);
//                    logger.info(String.format("资产统计表新插入分区数据：%d条", syndmgTableAlls.size()));
//                } catch (Exception e) {
//                    logger.error(ExceptionUtil.getExceptionTrace(e));
//                }
//            });
//
//            if (syndmgTableAlls.get(0).getTableType() == 1){
//                // 更新表的全量数据量
//                syndmgTableAlls.stream().forEach(syndmgTableAll -> {
//                    updateTabAllCount(syndmgTableAll);
//                });
//            }
//        } catch (Exception e) {
//            logger.error("保存统计信息出错：\n" + ExceptionUtil.getExceptionTrace(e));
//        }
//    }
//
//    /**
//     * 更改全表行数
//     *
//     * @param syndmgTableAll
//     */
//    private void updateTabAllCount(SyndmgTableAll syndmgTableAll) {
//        // TODO Auto-generated method stub
//        long tableAllCount = 0;
//        try {
//            tableAllCount = getTabAllCount(syndmgTableAll);
//            if (tableAllCount == -1) {
//                tableAllCount = 0;
//            }
//            syndmgTableAll.setTableAllCount(tableAllCount);
//            aliAssetStatisticsDao.updateSyndmgTableAll(syndmgTableAll);
//        } catch (Exception e) {
//            logger.error(String.format("表%s.%s更新总条数至table_syndmg_table失败", syndmgTableAll.getTableProject(), syndmgTableAll.getTableName()) + "\n" + ExceptionUtil.getExceptionTrace(e));
//        }
//    }
//
//    /**
//     * 根据 分区 生命周期获取全表行数
//     *
//     * @param syndmgTableAll
//     * @throws Exception
//     */
//    public Long getTabAllCount(SyndmgTableAll syndmgTableAll) {
//        long dBefore = dateBefore(1);   //昨天
//        List<SyndmgTableAll> listCount = new ArrayList<>();
//        long tableAllCount = 0;
//
//        listCount = aliAssetStatisticsDao.getTableAllCount(syndmgTableAll, dBefore);
//        if (listCount !=null && listCount.size() > 0) {
//            if (!"".equals(listCount.get(0).toString()))
//                tableAllCount = listCount.get(0).getPartitionCount();
//        }
//        return tableAllCount;
//    }
//
//    /**
//     * 删除ODPS历史数据
//     */
//    public void delHisData(SyndmgTableAll syndmgTableAll) {
//        try {
//            long daysAgo = 0;
//            if (syndmgTableAll.getLifeCycle() > 0 && syndmgTableAll.getIsPartition() == 0) {
//                daysAgo = dateBefore((int) syndmgTableAll.getLifeCycle());                              // 当前日期减去生命周期天数
//            } else if (syndmgTableAll.getIsPartition() == 1) {
//                daysAgo = dateBefore(Integer.parseInt(environment.getProperty("fullTableSaveDays")));   // 当前日期减去生命周期天数(全量表默认保留30天数据)
//            } else {
//                daysAgo = dateBefore(3600);                                                          // 当前日期减去生命周期天数（如果都没匹配到，默认3600）
//            }
//            String day = String.valueOf(daysAgo);                        // 分区日期
//            String minDelDate = day + "";                                // 分区日期
//            aliAssetStatisticsDao.delHisData(syndmgTableAll, minDelDate);
//        } catch (Exception e) {
//            logger.error("删除odps历史数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
//        }
//    }
//
//    /**
//     * 方法描述: 当前日期前n天
//     *
//     * @return
//     * @author:lhl
//     * @date: 2016年11月23日 下午3:15:52
//     */
//    public static long dateBefore(int i) {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, -i);//
//        long d = Integer.parseInt(sdf.format(calendar.getTime()));
//        return d;
//    }

}
