package com.synway.governace.pojo.largeScreen;

/**
 * @author wangdongwei
 * @date 2021/4/29 11:10
 */
public class UseHeatProperty {
    /**
     * 页面展示的名称
     */
    private String showName;
    /**
     * 表英文名 (应用使用热度为空)
     */
    private String tableNameEn;
    /**
     * 使用次数
     */
    private long useHeatCount;

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public long getUseHeatCount() {
        return useHeatCount;
    }

    public void setUseHeatCount(long useHeatCount) {
        this.useHeatCount = useHeatCount;
    }
}
