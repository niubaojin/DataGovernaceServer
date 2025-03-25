package com.synway.datarelation.pojo.databloodline.impactanalysis;

/**
 * @author wangdongwei
 * @ClassName ApplicationBloodline
 * @description 应用系统的血缘表信息
 * @date 2020/12/3 10:15
 */
public class ApplicationBloodline {

    // 序号  从1开始
    private int recno;
    // 层级
    private int levels;
    /**
     * 层级的状态 直接/间接
     */
    private String levelCh;

    // 应用系统名称
    private String applicationName;
    // 功能模块名称
    private String subModuleName;
    // 功能点名称（第三级别的模块名称）
    private String lowestModuleName;


    public String getLevelCh() {
        return levelCh;
    }

    public void setLevelCh(String levelCh) {
        this.levelCh = levelCh;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getRecno() {
        return recno;
    }

    public void setRecno(int recno) {
        this.recno = recno;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getLowestModuleName() {
        return lowestModuleName;
    }

    public void setLowestModuleName(String lowestModuleName) {
        this.lowestModuleName = lowestModuleName;
    }


}
