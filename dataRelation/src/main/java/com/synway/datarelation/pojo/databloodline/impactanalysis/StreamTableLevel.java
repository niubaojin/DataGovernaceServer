package com.synway.datarelation.pojo.databloodline.impactanalysis;

/**
 * @author wangdongwei
 * @ClassName StreamTableLevel
 * @description TODO
 * @date 2020/12/2 22:00
 */
public class StreamTableLevel {
    public static final String DATAPROCESS ="数据加工";
    public static final String STANDARD ="数据处理";

    /**
     * 序号  从1开始
     */
    private int recno;
    // 层级
    private int levels;
    /**
     * 生产阶段 目前包括 数据加工 ，数据处理
     */
    private String stage;

    // 平台类型   odps/ads/hive/hbase
    private String dataBaseType;
    // 项目名
    private String projectName;
    // 英文名
    private String tableNameEn;
    // 中文名
    private String tableNameCh;
    // 表协议id
    private String tableId="";

    /**
     * 日增数据量 数据加工使用资产信息的数据 数据处理使用对账统计的数据
     */
    private String dailyIncreaseCount = "0万条";


    private String objectId ="";



    public String getDailyIncreaseCount() {
        return dailyIncreaseCount;
    }

    public void setDailyIncreaseCount(String dailyIncreaseCount) {
        this.dailyIncreaseCount = dailyIncreaseCount;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getRecno() {
        return recno;
    }

    public void setRecno(int recno) {
        this.recno = recno;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }
}
