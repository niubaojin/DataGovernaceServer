package com.synway.datarelation.pojo.databloodline.impactanalysis;

/**
 * @author wangdongwei
 * @ClassName AnalysisResults
 * @description 最上面的汇总信息
 *     上游层级 下游层级 上游表 下游表  涉及工作流 涉及应用系统
 * @date 2020/12/2 21:48
 */
public class AnalysisResults {


    //上游层级 数量
    private int upStreamLevels;
    // 下游层级 数量
    private int downStreamLevels;
    // 上游表 数量
    private int upStreamTables;
    // 下游表  数量
    private int downStreamTables;
    // 直接涉及工作流 数量
    private int involvingWorkflows;
    // 直接涉及应用系统 数量
    private int involvingApplicationSystems;

    /**
     * 间接涉及的工作流 数量
     */
    private int indirectWorkflows;

    /**
     * 间接涉及的应用系统 数量
     */
    private int indirectApplicationSystems;


    public int getIndirectWorkflows() {
        return indirectWorkflows;
    }

    public void setIndirectWorkflows(int indirectWorkflows) {
        this.indirectWorkflows = indirectWorkflows;
    }

    public int getIndirectApplicationSystems() {
        return indirectApplicationSystems;
    }

    public void setIndirectApplicationSystems(int indirectApplicationSystems) {
        this.indirectApplicationSystems = indirectApplicationSystems;
    }

    public int getUpStreamLevels() {
        return upStreamLevels;
    }

    public void setUpStreamLevels(int upStreamLevels) {
        this.upStreamLevels = upStreamLevels;
    }

    public int getDownStreamLevels() {
        return downStreamLevels;
    }

    public void setDownStreamLevels(int downStreamLevels) {
        this.downStreamLevels = downStreamLevels;
    }

    public int getUpStreamTables() {
        return upStreamTables;
    }

    public void setUpStreamTables(int upStreamTables) {
        this.upStreamTables = upStreamTables;
    }

    public int getDownStreamTables() {
        return downStreamTables;
    }

    public void setDownStreamTables(int downStreamTables) {
        this.downStreamTables = downStreamTables;
    }

    public int getInvolvingWorkflows() {
        return involvingWorkflows;
    }

    public void setInvolvingWorkflows(int involvingWorkflows) {
        this.involvingWorkflows = involvingWorkflows;
    }

    public int getInvolvingApplicationSystems() {
        return involvingApplicationSystems;
    }

    public void setInvolvingApplicationSystems(int involvingApplicationSystems) {
        this.involvingApplicationSystems = involvingApplicationSystems;
    }
}
