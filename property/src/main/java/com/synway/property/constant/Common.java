package com.synway.property.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangdongwei
 */
public class Common {

    public static final String SELECT = "select";

    public static final String ROOT_URL = "getDataResourceByisLocal";

    public static final List<String> DATA_TYPE_LIST = Arrays.asList("ODPS", "ADS", "DATAHUB", "HIVE-CDH", "HIVE-HUAWEI", "HBASE-CDH", "HBASE-HUAWEI", "CLICKHOUSE");

    /**
     * Ads资产统计相关sql
     */
    public static final String syndmgTablesByMetaSql = "select\n" +
            "    table_name as tableName,\n" +
            "    comments as tableComment,\n" +
            "    sub_partition_count as lifeCycle,\n" +
            "    update_time as tableLastMetaModifiedTime,\n" +
            "    create_time as tableCreatedTime,\n" +
            "    is_sub_partition as isPartitionStr,\n" +
            "    sub_partition_column as partColumn,\n" +
            "    table_schema as tableProject\n" +
            "from\n" +
            "    tables";

    public static final String dataStorageScaleSql = "select\n" +
            "    table_schema as tableProject,\n" +
            "    table_name as tableName,\n" +
            "    data_size as tableSize,\n" +
            "    record_num as tableAllcount\n" +
            "from\n" +
            "    table_data\n" +
            "where\n" +
            "    table_schema = '%s'\n" +
            "    and table_name = '%s'";

    public static final String lastDataModifiedTimeSql = "select\n" +
            "    create_time as lastDataModifiedTime\n" +
            "from\n" +
            "    table_data_loads\n" +
            "where\n" +
            "    table_name = '%s'\n" +
            "order by\n" +
            "    create_time desc\n" +
            "limit 1";

    /**
     * Adb资产统计相关sql
     */
    public static final String syndmgTablesByAdbMetaSql = "select\n" +
            "    table_name as tableName,\n" +
            "    table_comment as tableComment,\n" +
            "    partition_count as lifeCycle,\n" +
            "    update_time as tableLastMetaModifiedTime,\n" +
            "    create_time as tableCreatedTime,\n" +
            "    is_sub_partition as isPartitionStr,\n" +
            "    sub_partition_column as partColumn,\n" +
            "    table_schema as tableProject,\n" +
            "    table_rows as tableAllcount,\n" +
            "    data_length as tableSize\n" +
            "from\n" +
            "    information_schema.tables";

}
