package com.synway.property.pojo;

import lombok.Data;

import java.io.Serializable;


/**
 *  根据分级分类查询表组织资产的汇总信息
 * @author 数据接入
 */
@Data
public class SummaryTableByClassify  implements Serializable {
    private String mainClassify;
    private String primaryClassifyCh;
    private String secondaryClassifyCh;
    private String threeClassifyCh;
    // 总数据量
    private long tableAllSum;
    // 总存储大小  单位  GB
    private double tableAllSize;
    //总日均数据量
    private long averageAllDailyVolume;
    private String averageAllDailyVolumeW;
    //总日均数据大小
    private double averageAllDailySize;
    // 总昨日分区数据量
    private long yesterdayAllSum;
    private String yesterdayAllSumW;
    private String yesterdayAllSumW_1;
    private String yesterdayAllSumW_2;
    private String yesterdayAllSumW_3;
    private String yesterdayAllSumW_4;
    private String yesterdayAllSumW_5;
    private String yesterdayAllSumW_6;
    //  表数
    private long tableNumberSum;
    // 统计时间
    private String statisticsDay;
    // 今日时间 yyyyMMdd
    private String todayStr;
    // ADS总数据量
    private long adsSum;
    private String adsSumW;
    // ADS总存储大小  单位  GB
    private double adsSize;
    // ODPS总数据量
    private long odpsSum;
    private String odpsSumW;
    // ODPS总存储大小  单位  GB
    private double odpsSize;
    // HBASE总数据量
    private long hbaseSum;
    private String hbaseSumW;
    // HBASE总存储大小  单位  GB
    private double hbaseSize;
    // HIVE总数据量
    private long hiveSum;
    private String hiveSumW;
    // HIVE总存储大小  单位  GB
    private double hiveSize;
    // CLICKHOUSE总数据量
    private long ckSum;
    private String ckSumW;
    // HIVE总存储大小  单位  GB
    private double ckSize;

}
