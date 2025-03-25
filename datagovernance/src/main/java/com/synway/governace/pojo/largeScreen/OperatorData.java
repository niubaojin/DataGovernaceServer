package com.synway.governace.pojo.largeScreen;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 电围数据、电查数据、4G分光数据：按照三种运营商统计最新分区(t-1)数据量，
 * 通过工作流模型统计出结果同步到oracle数据表中。
 * 统计具体包含数据种类根据现场数据进行调整。
 * 这个是 技侦版本
 * @author wangdongwei
 * @date 2021/4/26 9:51
 */
public class OperatorData implements Serializable {
    /**
     * 运营商中文名称  电信/移动/联通
     */
    private String name ;

    /**
     * 电围数据
     */
    private BigInteger memNumbers;
    /**
     * 电查数据
     */
    private BigInteger medNumbers;

    /**
     * 4G分光数据
     */
    private BigInteger smsNumbers;

    public OperatorData(){

    }

    public OperatorData(String name,BigInteger memNumbers,BigInteger medNumbers,BigInteger smsNumbers){
        this.name = name;
        this.memNumbers = memNumbers;
        this.medNumbers = medNumbers;
        this.smsNumbers = smsNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getMemNumbers() {
        return memNumbers;
    }

    public void setMemNumbers(BigInteger memNumbers) {
        this.memNumbers = memNumbers;
    }

    public BigInteger getMedNumbers() {
        return medNumbers;
    }

    public void setMedNumbers(BigInteger medNumbers) {
        this.medNumbers = medNumbers;
    }

    public BigInteger getSmsNumbers() {
        return smsNumbers;
    }

    public void setSmsNumbers(BigInteger smsNumbers) {
        this.smsNumbers = smsNumbers;
    }
}
