package com.synway.governace.pojo.largeScreen;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据总资产的相关信息
 *     2)平台分类
 *         离线库：ODPS\Hive
 *         在线库：ads\hbase\ck
 *         非结构化：oss（是否展现非结构化信息，可配置）
 * 通过property获取
 * @author wangdongwei
 * @date 2021/4/25 14:59
 */
@Data
public class TotalDataProperty  implements Serializable {

    /**
     * 日增量
     * （t-1）数据总量- （t-2）数据总量
     */
    private String dailyIncrement;

    /**
     * 数据总量   单位是  亿 条
     * 离线库记录数+在线库记录数+非结构化记录数
     */
    private double dataTotalVolume;

    /**
     * 日增量占数据总量的百分比
     */
    private String ratioOfData;

    /**
     * 已用存储占总存储的百分比
     */
    private String ratioOfStorage;

    /**
     * 已用存储总量    单位是 TB
     * 离线库已用物理存储+在线库已用物理存储+非结构化已用物理存储
     */
    private double totalStorageUsed;

    /**
     * 总存储   单位是 TB
     * 离线库总物理存储+在线库总物理存储+非结构化总物理存储
     */
    private double totalStorage;

    /**
     * 是否展现非结构化信息
     */
    private boolean unstructuredShow;

    /**
     * 实时库 水位图 的相关信息
     */
    private DataBaseState realTimeLibrary;

    /**
     * 离线库 水位图 的相关信息
     */
    private DataBaseState offlineLibrary;

    /**
     * 非结构化 水位图 的相关信息
     */
    private DataBaseState unstructuredLibrary;


    public static class DataBaseState{

        /**
         * 实时库水位图的名字 ads/hbase
         */
        private String name="";
        /**
         * 数据量
         */
        private String tableCount="0";
        /**
         * 已用存储
         */
        private String usedCapacity="";

        /**
         * 总存储
         */
        private String bareCapacity="";

        /**
         *  已用存储使用情况
         *  已用存储/总存储
         */
        private String usageRate="";

        /**
         * 表数量   有多少个表
         */
        private String tableSum="0";


        public String getTableSum() {
            return tableSum;
        }

        public void setTableSum(String tableSum) {
            this.tableSum = tableSum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTableCount() {
            return tableCount;
        }

        public void setTableCount(String tableCount) {
            this.tableCount = tableCount;
        }

        public String getUsedCapacity() {
            return usedCapacity;
        }

        public void setUsedCapacity(String usedCapacity) {
            this.usedCapacity = usedCapacity;
        }

        public String getBareCapacity() {
            return bareCapacity;
        }

        public void setBareCapacity(String bareCapacity) {
            this.bareCapacity = bareCapacity;
        }

        public String getUsageRate() {
            return usageRate;
        }

        public void setUsageRate(String usageRate) {
            this.usageRate = usageRate;
        }
    }


}
