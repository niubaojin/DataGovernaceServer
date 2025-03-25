package com.synway.reconciliation.pojo;

import java.math.BigDecimal;

/**
 * 数据接入账单
 *
 * @author
 */
public class AccessDataBill {
    private String dataNo;
    private String dataName;
    private String batchId;
    private String resourceId;
    private String dataTime;
    private String ipAddress;
    private String macAddress;
    private BigDecimal readNum;
    private BigDecimal readFailNum;
    private BigDecimal sendNum;
    private BigDecimal sendSize;
    private BigDecimal receiveNum;
    private BigDecimal receiveSize;
    private BigDecimal receiveFailNum;
    private BigDecimal receiveFailSize;
    private BigDecimal dataCount;
    private BigDecimal chainRatio;
    private String chainRatioLevel;
    private String chainRatioStr;
    private String status;
    private String state;

    public String getDataNo() {
        return dataNo;
    }

    public void setDataNo(String dataNo) {
        this.dataNo = dataNo;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public BigDecimal getReadNum() {
        return readNum;
    }

    public void setReadNum(BigDecimal readNum) {
        this.readNum = readNum;
    }

    public BigDecimal getReadFailNum() {
        return readFailNum;
    }

    public void setReadFailNum(BigDecimal readFailNum) {
        this.readFailNum = readFailNum;
    }

    public BigDecimal getSendNum() {
        return sendNum;
    }

    public void setSendNum(BigDecimal sendNum) {
        this.sendNum = sendNum;
    }

    public BigDecimal getSendSize() {
        return sendSize;
    }

    public void setSendSize(BigDecimal sendSize) {
        this.sendSize = sendSize;
    }

    public BigDecimal getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(BigDecimal receiveNum) {
        this.receiveNum = receiveNum;
    }

    public BigDecimal getReceiveSize() {
        return receiveSize;
    }

    public void setReceiveSize(BigDecimal receiveSize) {
        this.receiveSize = receiveSize;
    }

    public BigDecimal getReceiveFailNum() {
        return receiveFailNum;
    }

    public void setReceiveFailNum(BigDecimal receiveFailNum) {
        this.receiveFailNum = receiveFailNum;
    }

    public BigDecimal getReceiveFailSize() {
        return receiveFailSize;
    }

    public void setReceiveFailSize(BigDecimal receiveFailSize) {
        this.receiveFailSize = receiveFailSize;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public BigDecimal getChainRatio() {
        if (sendNum.compareTo(new BigDecimal(0)) > 0) {
            chainRatio = (receiveNum.subtract(sendNum)).multiply(new BigDecimal(100)).divide(sendNum, 2, BigDecimal.ROUND_HALF_DOWN);
        } else {
            chainRatio = new BigDecimal(0);
        }
        return chainRatio;
    }

    public void setChainRatio(BigDecimal chainRatio) {
        this.chainRatio = chainRatio;
    }

    public String getChainRatioLevel() {
        int level = Math.abs(getChainRatio().divide(new BigDecimal(10)).intValue());
        return level >= 10 ? "10" : level + "";
    }

    public void setChainRatioLevel(String chainRatioLevel) {
        this.chainRatioLevel = chainRatioLevel;
    }

    public void setChainRatioStr(String chainRatioStr) {
        this.chainRatioStr = chainRatioStr;
    }

    public String getChainRatioStr() {
        return getChainRatio().stripTrailingZeros().toPlainString() + "%";
    }

    public String getStatus() {
        if ((sendNum.compareTo(receiveNum) != 0 || sendSize.compareTo(receiveSize) != 0)) {
            return "异常";
        }
        return "正常";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
