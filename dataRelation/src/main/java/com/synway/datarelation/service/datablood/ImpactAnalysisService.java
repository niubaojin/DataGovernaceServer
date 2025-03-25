package com.synway.datarelation.service.datablood;

import com.synway.datarelation.pojo.databloodline.impactanalysis.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 影响因素相关的接口信息
 * @author wangdongwei
 */
public interface ImpactAnalysisService {

    /**
     * 查询影响因素的信息
     * @param tableName
     * @return
     * @throws Exception
     */
    ImpactAnalysis searchImpactAnalysisByTableName(String tableName) throws Exception;

    /**
     * 导出excel数据
     * @param response
     * @param analysisExportData
     * @throws Exception
     */
    void downloadExcel(HttpServletResponse response, AnalysisExportData analysisExportData) throws Exception;

    /**
     * 生成excel文件的表头 等相关数据
     * @param analysisExportData
     * @param tableNameCh
     * @param list
     * @param dataList
     */
    void downloadExcel(AnalysisExportData analysisExportData,
                      List<String> tableNameCh,
                      List<List<String>> list,
                      List<Object> dataList);

    /**
     *  向左/右查询这张表的影响因素的
     * @param tableName  需要查询的表名
     * @param workFlowInformationList  存储的工作流信息 只有工作流信息，其它的需要去匹配
     * @param streamTableLevel 上游表/下级表的存储信息
     * @param flagNum  层级 当是0时表示为查询节点
     * @param queryType left:查询上游表的相关信息，right：查询下游表的相关信息
     * @param  tableMap  防止出现无限迭代的情况
     * @param  applicationBloodlines 如果为null 则不需要获取对应的应用血缘信息
     * @return
     */
    String queryImpactAnalysisCache(String tableName, List<WorkFlowInformation> workFlowInformationList,
                                           List<StreamTableLevel> streamTableLevel
            , int flagNum , String queryType, Map<String,Integer> tableMap, String dataBaseType,
                                    List<ApplicationBloodline> applicationBloodlines) throws Exception;


}
