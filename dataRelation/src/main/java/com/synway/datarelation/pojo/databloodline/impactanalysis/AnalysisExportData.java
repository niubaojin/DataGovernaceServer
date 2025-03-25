package com.synway.datarelation.pojo.databloodline.impactanalysis;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName AnalysisExportData
 * @description TODO
 * @date 2020/12/4 9:42
 */
public class AnalysisExportData {
    // 上游表
    public static final  String UP_STREAM_TABLE="upstreamtable";
    // 下游表
    public static final  String DOWN_STREAM_TABLE="downstreamtable";
    // 工作流信息
    public static final  String WORK_FLOW_INFORMATION="workflowinformation";
    // 应用系统表
    public static final  String APPLICATION_BLOODLINE="applicationbloodline";

    // 查询的表名  项目名.表名
    @NotNull
    private String tableName;
    // 要导入数据的类型  upstreamtable:上游表  downstreamtable：下游表 workflowinformation：工作流信息
    // applicationbloodline: 应用系统
    private String dataType;
    // 需要导出的数据信息
    private List<Object> dataList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }
}
