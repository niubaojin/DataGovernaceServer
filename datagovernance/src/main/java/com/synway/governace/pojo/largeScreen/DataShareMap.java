package com.synway.governace.pojo.largeScreen;

/**
 *
 *  省级地图展现数据共享信息，省份和对应地图可配置，根据现场进行调整。
 *      总队下发给各地市数据种类和数据量。
 *      各地市上报到总队的数据种类和数据量。
 *      内蒙\广西：由标准化工具datadistribute实现下发和上报，与数据处理组对接。
 *      新疆：由实施人员统计下发、上报信息。
 * @author wangdongwei
 * @date 2021/4/26 9:11
 */
public class DataShareMap {
    /**
     * 城市代码  前端用这个匹配
     */
    private String cityCode;

    /**
     * 城市名称 与前端不一定准确
     */
    private String name;

    /**
     * 上报数据量
     */
    private String reportNumbers;

    /**
     * 上报数据量 带单位
     */
    private String reportNumbersStr;

    /**
     * 上报种类
     */
    private String reportTypes;

    /**
     * 下发数据量
     */
    private String sendNumbers;

    /**
     * 下发数据量 带单位
     */
    private String sendNumbersStr;

    /**
     * 下发种类
     */
    private String sendTypes;

    public String getReportNumbersStr() {
        return reportNumbersStr;
    }

    public void setReportNumbersStr(String reportNumbersStr) {
        this.reportNumbersStr = reportNumbersStr;
    }

    public String getSendNumbersStr() {
        return sendNumbersStr;
    }

    public void setSendNumbersStr(String sendNumbersStr) {
        this.sendNumbersStr = sendNumbersStr;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportNumbers() {
        return reportNumbers;
    }

    public void setReportNumbers(String reportNumbers) {
        this.reportNumbers = reportNumbers;
    }

    public String getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(String reportTypes) {
        this.reportTypes = reportTypes;
    }

    public String getSendNumbers() {
        return sendNumbers;
    }

    public void setSendNumbers(String sendNumbers) {
        this.sendNumbers = sendNumbers;
    }

    public String getSendTypes() {
        return sendTypes;
    }

    public void setSendTypes(String sendTypes) {
        this.sendTypes = sendTypes;
    }
}
