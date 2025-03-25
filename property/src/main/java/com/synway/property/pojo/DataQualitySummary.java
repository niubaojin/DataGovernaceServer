package com.synway.property.pojo;

import java.io.Serializable;

/**

 */
/**
 * 数据资源报表中右上角的汇总表格信息
 * @Add 20190715
 *
 * @author majia
 * @date 2020/06/02
 */
public class DataQualitySummary implements Serializable {
    // 质量检测总表数
    private String dataQualityAllTablesSum = "0";
    // 活表率
    private String liveTableRote = "0";
    // 质量检测总字段数
    private String dataQualityAllColumnsSum = "0";
    // 国标码表数
    private String nationalStandardTablesSum = "0";
    // 数据异常表数
    private String dataAbnormalTablesSum = "0";
    // 延迟表数
    private String delayTablesSum = "0";
    // 异常数据流数
    private String abnormalDataFlowsSum = "0";
    // 异常模型数
    private String abnormalModelsSum = "0";
    //异常账单数
    private String abnormalBillsSum = "0";
    //异常账单统计时间
    private String abnormalBillsTime = "";
    // 地标码表数
    private String landMarkTablesSum = "0";

    @Override
    public String toString() {
        return "DataQualitySummary{" +
                "dataQualityAllTablesSum='" + dataQualityAllTablesSum + '\'' +
                ", liveTableRote='" + liveTableRote + '\'' +
                ", dataQualityAllColumnsSum='" + dataQualityAllColumnsSum + '\'' +
                ", nationalStandardTablesSum='" + nationalStandardTablesSum + '\'' +
                ", dataAbnormalTablesSum='" + dataAbnormalTablesSum + '\'' +
                ", delayTablesSum='" + delayTablesSum + '\'' +
                ", abnormalDataFlowsSum='" + abnormalDataFlowsSum + '\'' +
                ", abnormalModelsSum='" + abnormalModelsSum + '\'' +
                ", abnormalBillsSum='" + abnormalBillsSum + '\'' +
                ", landMarkTablesSum='" + landMarkTablesSum + '\'' +
                '}';
    }

    public String getNationalStandardTablesSum() {
        return nationalStandardTablesSum;
    }

    public void setNationalStandardTablesSum(String nationalStandardTablesSum) {
        this.nationalStandardTablesSum = nationalStandardTablesSum;
    }

    public String getLandMarkTablesSum() {
        return landMarkTablesSum;
    }

    public void setLandMarkTablesSum(String landMarkTablesSum) {
        this.landMarkTablesSum = landMarkTablesSum;
    }

    public String getDataQualityAllTablesSum() {
        return dataQualityAllTablesSum;
    }

    public void setDataQualityAllTablesSum(String dataQualityAllTablesSum) {
        this.dataQualityAllTablesSum = dataQualityAllTablesSum;
    }

    public String getLiveTableRote() {
        return liveTableRote;
    }

    public void setLiveTableRote(String liveTableRote) {
        this.liveTableRote = liveTableRote;
    }

    public String getDataQualityAllColumnsSum() {
        return dataQualityAllColumnsSum;
    }

    public void setDataQualityAllColumnsSum(String dataQualityAllColumnsSum) {
        this.dataQualityAllColumnsSum = dataQualityAllColumnsSum;
    }



    public String getDataAbnormalTablesSum() {
        return dataAbnormalTablesSum;
    }

    public void setDataAbnormalTablesSum(String dataAbnormalTablesSum) {
        this.dataAbnormalTablesSum = dataAbnormalTablesSum;
    }

    public String getDelayTablesSum() {
        return delayTablesSum;
    }

    public void setDelayTablesSum(String delayTablesSum) {
        this.delayTablesSum = delayTablesSum;
    }

    public String getAbnormalDataFlowsSum() {
        return abnormalDataFlowsSum;
    }

    public void setAbnormalDataFlowsSum(String abnormalDataFlowsSum) {
        this.abnormalDataFlowsSum = abnormalDataFlowsSum;
    }

    public String getAbnormalModelsSum() {
        return abnormalModelsSum;
    }

    public void setAbnormalModelsSum(String abnormalModelsSum) {
        this.abnormalModelsSum = abnormalModelsSum;
    }

    public String getAbnormalBillsSum() {
        return abnormalBillsSum;
    }

    public void setAbnormalBillsSum(String abnormalBillsSum) {
        this.abnormalBillsSum = abnormalBillsSum;
    }

    public String getAbnormalBillsTime() {
        return abnormalBillsTime;
    }

    public void setAbnormalBillsTime(String abnormalBillsTime) {
        this.abnormalBillsTime = abnormalBillsTime;
    }
}
