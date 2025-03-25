package com.synway.datarelation.pojo.databloodline.impactanalysis;


import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName ImpactAnalysis
 * @description 数据加工血缘中 影响分析页面的所有结果
 * @date 2020/12/2 21:39
 */
public class ImpactAnalysis implements Serializable {

    /**
     *     最上面一层的汇总数据
     */
    private AnalysisResults analysisResults;
    //上游表信息
    private List<StreamTableLevel> upStreamTableLevel;
    //下游表信息
    private List<StreamTableLevel> downStreamTableLevel;
    // 工作流信息
    private List<WorkFlowInformation> workFlowInformation;
    // 应用系统表
    private List<ApplicationBloodline> applicationBloodline;

    // 表格筛选框需要的内容
    private AnalysisTableFilter analysisTableFilter;

    public AnalysisTableFilter getAnalysisTableFilter() {
        return analysisTableFilter;
    }

    public void setAnalysisTableFilter(AnalysisTableFilter analysisTableFilter) {
        this.analysisTableFilter = analysisTableFilter;
    }

    public AnalysisResults getAnalysisResults() {
        return analysisResults;
    }

    public void setAnalysisResults(AnalysisResults analysisResults) {
        this.analysisResults = analysisResults;
    }

    public List<StreamTableLevel> getUpStreamTableLevel() {
        return upStreamTableLevel;
    }

    public void setUpStreamTableLevel(List<StreamTableLevel> upStreamTableLevel) {
        this.upStreamTableLevel = upStreamTableLevel;
    }

    public List<StreamTableLevel> getDownStreamTableLevel() {
        return downStreamTableLevel;
    }

    public void setDownStreamTableLevel(List<StreamTableLevel> downStreamTableLevel) {
        this.downStreamTableLevel = downStreamTableLevel;
    }

    public List<WorkFlowInformation> getWorkFlowInformation() {
        return workFlowInformation;
    }

    public void setWorkFlowInformation(List<WorkFlowInformation> workFlowInformation) {
        this.workFlowInformation = workFlowInformation;
    }

    public List<ApplicationBloodline> getApplicationBloodline() {
        return applicationBloodline;
    }

    public void setApplicationBloodline(List<ApplicationBloodline> applicationBloodline) {
        this.applicationBloodline = applicationBloodline;
    }
}
