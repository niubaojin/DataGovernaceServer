package com.synway.datarelation.pojo.databloodline;

/**
 * 汇总信息
 * @author wangdongwei
 * @ClassName SummaryData
 * @description TODO
 * @date 2020/12/17 9:44
 */
public class SummaryData {

    /**
     *  应用系统个数
     */
    private int applicationNameSum;

    /**
     * 涉及表数量
     */
    private int involvedTableSum;

    public int getApplicationNameSum() {
        return applicationNameSum;
    }

    public void setApplicationNameSum(int applicationNameSum) {
        this.applicationNameSum = applicationNameSum;
    }

    public int getInvolvedTableSum() {
        return involvedTableSum;
    }

    public void setInvolvedTableSum(int involvedTableSum) {
        this.involvedTableSum = involvedTableSum;
    }
}
