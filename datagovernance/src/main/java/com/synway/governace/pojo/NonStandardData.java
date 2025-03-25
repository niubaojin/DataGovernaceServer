package com.synway.governace.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 非标准数据接入监控
 */
public class NonStandardData implements Serializable {

    private String id;
    private Date statisticTime;//数据记录统计时间
    private String dateStr;    //日期字符串
    private String subClass;   //数据名称
    private long accessOneDayBeforeTotal;   //接入的昨日源数据
    private long accessTwoDayBeforeTotal;   //接入的前日源数据
    private double accessSequential;        //源数据环比
    private long standardOneDayBeforeTotal;   
    private long standardTwoDayBeforeTotal;
    private double standardSequential;
    
    private String adsProjectName;  //ads项目名
    private String adsTableName;   //ads表名
    private long adsOneDayBeforeTotal;  //ads昨日入库数据量
    private long adsTwoDayBeforeTotal;  //ads前日入库数据量
    private double adsSequential;       //ads同比
    
    private String odpsProjectName;     //odps项目名
    private String odpsTableName;       //odps表名
    private long odpsOneDayBeforeTotal; //odps昨日入库数据量
    private long odpsTwoDayBeforeTotal; //odps前日入库数据量
    private double odpsSequential;      //odps同比
    private int abNormalCount;//异常表数量
    private int isNormal;//标识表是否异常，-1异常，1正常

    private String isStandardData; //是否是标准数据   （1：标准数据，0：非标准数据）
   
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStatisticTime() {
        return statisticTime;
    }

    public void setStatisticTime(Date statisticTime) {
        this.statisticTime = statisticTime;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public long getAccessOneDayBeforeTotal() {
        return accessOneDayBeforeTotal;
    }

    public void setAccessOneDayBeforeTotal(long accessOneDayBeforeTotal) {
        this.accessOneDayBeforeTotal = accessOneDayBeforeTotal;
    }

    public long getAccessTwoDayBeforeTotal() {
        return accessTwoDayBeforeTotal;
    }

    public void setAccessTwoDayBeforeTotal(long accessTwoDayBeforeTotal) {
        this.accessTwoDayBeforeTotal = accessTwoDayBeforeTotal;
    }

    public double getAccessSequential() {
        return accessSequential;
    }

    public void setAccessSequential(double accessSequential) {
        this.accessSequential = accessSequential;
    }

    public long getStandardOneDayBeforeTotal() {
        return standardOneDayBeforeTotal;
    }

    public void setStandardOneDayBeforeTotal(long standardOneDayBeforeTotal) {
        this.standardOneDayBeforeTotal = standardOneDayBeforeTotal;
    }

    public long getStandardTwoDayBeforeTotal() {
        return standardTwoDayBeforeTotal;
    }

    public void setStandardTwoDayBeforeTotal(long standardTwoDayBeforeTotal) {
        this.standardTwoDayBeforeTotal = standardTwoDayBeforeTotal;
    }

    public double getStandardSequential() {
        return standardSequential;
    }

    public void setStandardSequential(double standardSequential) {
        this.standardSequential = standardSequential;
    }

    public long getAdsOneDayBeforeTotal() {
        return adsOneDayBeforeTotal;
    }

    public void setAdsOneDayBeforeTotal(long adsOneDayBeforeTotal) {
        this.adsOneDayBeforeTotal = adsOneDayBeforeTotal;
    }

    public long getAdsTwoDayBeforeTotal() {
        return adsTwoDayBeforeTotal;
    }

    public void setAdsTwoDayBeforeTotal(long adsTwoDayBeforeTotal) {
        this.adsTwoDayBeforeTotal = adsTwoDayBeforeTotal;
    }

    public double getAdsSequential() {
        return adsSequential;
    }

    public void setAdsSequential(double adsSequential) {
        this.adsSequential = adsSequential;
    }

    public long getOdpsOneDayBeforeTotal() {
        return odpsOneDayBeforeTotal;
    }

    public void setOdpsOneDayBeforeTotal(long odpsOneDayBeforeTotal) {
        this.odpsOneDayBeforeTotal = odpsOneDayBeforeTotal;
    }

    public long getOdpsTwoDayBeforeTotal() {
        return odpsTwoDayBeforeTotal;
    }

    public void setOdpsTwoDayBeforeTotal(long odpsTwoDayBeforeTotal) {
        this.odpsTwoDayBeforeTotal = odpsTwoDayBeforeTotal;
    }

    public double getOdpsSequential() {
        return odpsSequential;
    }

    public void setOdpsSequential(double odpsSequential) {
        this.odpsSequential = odpsSequential;
    }

    public int getAbNormalCount() {
        return abNormalCount;
    }

    public void setAbNormalCount(int abNormalCount) {
        this.abNormalCount = abNormalCount;
    }

    public int getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(int isNormal) {
        this.isNormal = isNormal;
    }
    
    public String getAdsProjectName() {
		return adsProjectName;
	}

	public void setAdsProjectName(String adsProjectName) {
		this.adsProjectName = adsProjectName;
	}

	public String getAdsTableName() {
		return adsTableName;
	}

	public void setAdsTableName(String adsTableName) {
		this.adsTableName = adsTableName;
	}

	public String getOdpsProjectName() {
		return odpsProjectName;
	}

	public void setOdpsProjectName(String odpsProjectName) {
		this.odpsProjectName = odpsProjectName;
	}

	public String getOdpsTableName() {
		return odpsTableName;
	}

	public void setOdpsTableName(String odpsTableName) {
		this.odpsTableName = odpsTableName;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getIsStandardData() {
		return isStandardData;
	}

	public void setIsStandardData(String isStandardData) {
		this.isStandardData = isStandardData;
	}
	
	
    
}
