package com.synway.reconciliation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * 账单详情
 * @author ym
 */
public class Detail {
    /**
     * 文件名
     */
    private String dataId;
    /**
     * 是否本地仓
     */
    private String isLocal;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 数据名称
     */
    private String dataName;
    /**
     * 账单类型
     */
    private int billType;
    /**
     * 账单编号
     */
    private String billNo;
    /**
     * 提供方编号或接入方编号
     */
    private String partyNo;
    /**
     * 数据资源编号
     */
    private String resourceId;
    /**
     * 数据条数
     */
    private long dataCount;
    /**
     * 数据指纹
     */
    private String dataFingerprint;
    /**
     * 数据指纹类型
     */
    private String fingerprintType;
    /**
     * 数据大小
     */
    private String dataSize;
    /**
     * 数据起始编号
     */
    private String startNo;
    /**
     * 数据结束编号
     */
    private String endNo;
    /**
     * 数据来源存储位置
     */
    private String sourceLocation;
    /**
     * 账单状态（0.未对账1.对账成功2.对账失败3.已销账）
     */
    private int billState;
    /**
     * 上次失败账单号
     */
    private String lastFailBill;
    /**
     * 对账方法（1.及时对账，2定时对账，3盘点对账）
     */
    private int checkMethod;
    /**
     * 数据发送时间或接受时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp dataTime;
    /**
     * 数据对账时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp dataCheckTime;
    /**
     * 数据来源名称
     */
    private String dataSourceName;
    /**
     * 对账结果描述
     */
    private String resultDes;
    /**
     * 数据来源类型（1.数据包2.数据文件3.数据库）
     */
    private int dataSourceType;
    /**
     * 数据管理员
     */
    private String dataAdministrator;
    /**
     * 管理员联系电话
     */
    private String administratorTel;
    /**
     * 对账ID
     */
    private String synInceptDataId;
    /**
     * 对账条件
     */
    private String checkCondition;

    public String getCheckCondition() {
        if (checkMethod == 3) {
            checkCondition = "INCEPT_DATA_ID=" + StringUtils.defaultIfBlank(synInceptDataId, "");
        }
        return checkCondition;
    }

    public void setCheckCondition(String checkCondition) {
        this.checkCondition = checkCondition;
    }

    public String getSynInceptDataId() {
        return synInceptDataId;
    }

    public void setSynInceptDataId(String synInceptDataId) {
        this.synInceptDataId = synInceptDataId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getPartyNo() {
        return partyNo;
    }

    public void setPartyNo(String partyNo) {
        this.partyNo = partyNo;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public String getDataFingerprint() {
        return dataFingerprint;
    }

    public void setDataFingerprint(String dataFingerprint) {
        this.dataFingerprint = dataFingerprint;
    }

    public String getFingerprintType() {
        return fingerprintType;
    }

    public void setFingerprintType(String fingerprintType) {
        this.fingerprintType = fingerprintType;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public int getBillState() {
        return billState;
    }

    public void setBillState(int billState) {
        this.billState = billState;
    }

    public String getLastFailBill() {
        return StringUtils.defaultIfBlank(lastFailBill, "NULL");
    }

    public void setLastFailBill(String lastFailBill) {
        this.lastFailBill = lastFailBill;
    }

    public int getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(int checkMethod) {
        this.checkMethod = checkMethod;
    }

    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }

    public Timestamp getDataCheckTime() {
        return dataCheckTime;
    }

    public void setDataCheckTime(Timestamp dataCheckTime) {
        this.dataCheckTime = dataCheckTime;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getResultDes() {
        return resultDes;
    }

    public void setResultDes(String resultDes) {
        this.resultDes = resultDes;
    }

    public int getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(int dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getDataAdministrator() {
        return dataAdministrator;
    }

    public void setDataAdministrator(String dataAdministrator) {
        this.dataAdministrator = dataAdministrator;
    }

    public String getAdministratorTel() {
        return administratorTel;
    }

    public void setAdministratorTel(String administratorTel) {
        this.administratorTel = administratorTel;
    }
}
