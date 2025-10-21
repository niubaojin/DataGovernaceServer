package com.synway.datastandardmanager.enums;

/**
 * @author wangdongwei
 * @ClassName DataBaseType
 * @description 数据库新增字段的相关处理类
 * @date 2020/9/25 13:35
 */
public enum DataBaseTypeEnum {

    ADS_ADDCOLUMN("ads_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplAds"),
    CLICKHOUSE_ADDCOLUMN("clickhouse_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplCk"),
    DATAHUB_ADDCOLUMN("datahub_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceimplDataHub"),
    HBASE_CDH_ADDCOLUMN("hbase-cdh_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplHbase"),
    HBASE_HUAWEI_ADDCOLUMN("hbase-huawei_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplHbase"),
    HIVE_CDH_ADDCOLUMN("hive-cdh_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplHive"),
    HIVE_HUAWEI_ADDCOLUMN("hive-huawei_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplHive"),
    ODPS_ADDCOLUMN("odps_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplOdps"),
    ORACLE_ADDCOLUMN("oracle_addcolumn", "com.synway.datastandardmanager.service.impl.CreateTableSqlServiceImplOracle");

    private String type;
    private String cla;

    DataBaseTypeEnum(String type, String cla) {
        this.type = type;
        this.cla = cla;
    }

    /**
     * 根据类型名称返回对应的执行类路径，如果没有找到就返回默认的执行类
     *
     * @param type
     * @return
     */
    public static String getCla(String type) {
        DataBaseTypeEnum[] dataXTypes = DataBaseTypeEnum.values();
        DataBaseTypeEnum dataXType;
        for (int i = 0; i < dataXTypes.length; i++) {
            dataXType = dataXTypes[i];
            if (dataXType.type.equalsIgnoreCase(type)) {
                return dataXType.cla;
            }
        }
        return "";
    }

}
