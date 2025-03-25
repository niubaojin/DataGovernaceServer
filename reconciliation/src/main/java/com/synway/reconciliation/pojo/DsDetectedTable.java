package com.synway.reconciliation.pojo;

import lombok.Data;

import java.io.Serializable;


/**
 * DS_DETECTED_TABLE  获取账单需要的数据
 * @author DZH
 */
@Data
public class DsDetectedTable implements Serializable {
    private String detectId;
    private String resId;
    private String projectName;
    private String tableNameEn;
    private Integer tableType;
    private Integer detectedType;
    private Integer isDeleted;
    private String tableNameCn;
    private String dataFormat;
    private String provideType;
    private String provider;
    private String provideUnit;
    private String provideTel;
    private String provideTime;
    private String inceptor;
    private String inceptUnit;
    private String inceptTel;
    private String inceptTime;
    private String inceptDesc;
    private String inceptType;
    private String dataLevel;
    private String tradeClassify;
    private String businessMark;
    private String routineUnit;
    private String routineCode;
    private String routineLinkman;
    private String routineTel;
    private String manageUnit;
    private String manageUnitCode;
    private String resPostion;
    private String postionDesc;
    private String appName;
    private String appCode;
    private String appManageUnit;
    private String appBuildUnit;
    private String sourceClassify;
    private String sourceCode;
    private String resDesc;
}
