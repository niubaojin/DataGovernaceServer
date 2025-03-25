package com.synway.datarelation.pojo.databloodline;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.synway.datarelation.constant.Common;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 数据血缘的节点信息
 * @author wangdongwei
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataBloodlineNode implements Serializable {
    private List<BloodNode> nodes;
    private List<Edges> edges;

    /**
     *  20210408 这个存储的是 节点显示名称
     */
    private List<TrackQueryData> nodeNameList;


    public List<TrackQueryData> getNodeNameList() {
        return nodeNameList;
    }

    public void setNodeNameList(List<TrackQueryData> nodeNameList) {
        this.nodeNameList = nodeNameList;
    }

    public List<BloodNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<BloodNode> nodes) {
        this.nodes = nodes;
    }

    public List<Edges> getEdges() {
        return edges;
    }

    public void setEdges(List<Edges> edges) {
        this.edges = edges;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Edges implements Serializable {
        // 节点之间连线的关系 是id对应的连接关系
        private String source="";
        private String target="";
        private Boolean visible = true;  // 节点是否隐藏/显示
        private String edgeType = "";
        private String workFlowNodeName = "";   // 数据加工中 工作流的节点名称
        private String workFlowName = "";      //  数据加工中 工作流的名称
        /**
         * 边的类型  虚线：dottedLine  实线：solidLine
         */
        private String lineType = Common.SOLID_LINE;

        public Edges(){

        }
        public   Edges(String source ,String target ,Boolean visible,String edgeType){
            this.source = source;
            this.target = target;
            this.visible = visible;
            this.edgeType = edgeType;
        }
        public   Edges(String source ,String target ,Boolean visible,String edgeType,String workFlowNodeName,String workFlowName){
            this.source = source;
            this.target = target;
            this.visible = visible;
            this.edgeType = edgeType;
            this.workFlowNodeName = workFlowNodeName;
            this.workFlowName = workFlowName;
        }

        public String getLineType() {
            return lineType;
        }

        public void setLineType(String lineType) {
            this.lineType = lineType;
        }

        public String getWorkFlowNodeName() {
            return workFlowNodeName;
        }

        public void setWorkFlowNodeName(String workFlowNodeName) {
            this.workFlowNodeName = workFlowNodeName;
        }

        public String getWorkFlowName() {
            return workFlowName;
        }

        public void setWorkFlowName(String workFlowName) {
            this.workFlowName = workFlowName;
        }

        public String getEdgeType() {
            return edgeType;
        }

        public void setEdgeType(String edgeType) {
            this.edgeType = edgeType;
        }

        public Boolean getVisible() {
            return visible;
        }

        public void setVisible(Boolean visible) {
            this.visible = visible;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static  class BloodNode implements Serializable {
        public static final String ORGANIZATION_CLASSIFY="组织分类";
        public static final String NON_ORGANIZATION_CLASSIFY="非组织分类";

        public static final String ORIGINAL_LIBRARY="原始库";
        public static final String BUSINESS_LIBRARY="业务库";
        public static final String RESOURCE_LIBRARY="资源库";
        public static final String THEME_LIBRARY="主题库";
        public static final String OTHER="其他";

        // 单个数据血缘节点的展示数据
        @NotNull
        private String dataType;    //  类型  用于区分颜色
        private String id;          // 节点id信息  唯一
        private String ip;          //  数据探查里面的  ip 地址  数据探查展示
        @NotNull
        private String typeName;    //  节点类型的中文名称
        private String dataBaseId="";  //  数据仓库对应的 id值   数据探查展示
        private String sourceIdCh="";  //源协议中文名    数据接入展示
        private String sourceId="";    // 源协议英文名   数据接入展示
        private String sourceCode="";
        private String sourceFactoryId="";  // 源协议厂商中文名  数据接入展示
        private String sourceFactoryCh="";  // 源协议厂商中文名  数据接入展示
        private String sourceCodeCh="";    // 源系统代号中文名  数据接入展示
        private String tableIdCh="";      // 目标 tableId 协议中文名    数据处理展示
        private String tableId="";        // 目标 tableId  数据处理展示  数据加工展示
        private String targetCodeCh="";  // 目标 系统代号中文名  数据处理展示
        private String targetCode="";    //  系统代号
        private String tableNameCh="";   //  表中文名  数据加工展示
        private String tableProject = "";
        private String tableNameEn="";   //  表项目名.表英文名  数据加工展示  应用系统 血缘关系展示
        @NotNull
        private int flagNum = -1;  //  节点是处于该类别的第几级 从数字1开始 因为当层级过多时 会隐藏
        private Boolean visible = true;  // 节点是否隐藏/显示
        private String nodeName = "";  // 节点的展示信息，目前只展示一项内容，后台获取
        @NotNull
        private String dataQueryType = "";  // 如果是 main 表示是查询的节点信息 用于和其它的节点做连接

        private String applicationSystem="";    //  应用系统 血缘关系展示  应用系统名
        private String subModuleName="";       // 应用系统 血缘关系展示 子模块名称
        private String moduleClassify="";  // 应用血缘展示发生变化 每个分类信息都要展示对应的类型
        private Integer moduleClassifyNum= -1;  // -1表示是到了表 0表示是应用系统名
        /**
         * 应用血缘/数据加工中的 数据库类型  odps/ads/hive/hbase
         */
        private String dataBaseType="";
        private String taskId="";      //对接入就是任务id、对数据处理就是处理任务id
        private String outputDatabaseType="";   // 标准化输出的数据库类型

        private String targetDBEngName="";   // 输出平台的英文名称 例如 ADS-HC、ADS-HP、ODPS、OSOS

        // 页面相关的内容
        private String pageId="";
        private String queryId="";
        // 是否可以点击之后查询这个节点所有血缘关系
        private Boolean clickShowAll = false;

        /**
         * 20210305 增加数据过滤信息 组织分类/非组织分类
         */
        private String  classifyFilter = BloodNode.NON_ORGANIZATION_CLASSIFY;

        /**
         * 只有当 classifyFilter 里面的值是组织分类的时候，这个里面的值才是正确的
         * 组织分类标注  原始库/业务库/资源库/主题库/其它
         */
        private String organizationClassifyName;

        /**
         * 日增数据量 数据加工使用资产信息的数据
         * 这个只有数据处理的节点有
         */
        private String dailyIncreaseCount ="0";

        private List<PreDataProcess> preDataProcessList;

        /**
         *  数据接入血缘 节点特有 存储该节点对应数据仓库上的表id
         *  如果为空，表示没有
         */
        private String databaseTableId;

        /**
         * ftp/oracle/mysql/hive 等
         */
        private String taskType;

        /**
         * 数据接入血缘节点特有  节点对应的任务名称
         */
        private String taskName;

        /**
         * 数据加工节点专用，该表在血缘中对应的工作流信息
         */
        private List<String> nodeFlowList;

        /**
         * 数据处理节点专用 用于跳转
         */
        private String objectId;
        /**
         * 表数据最新更新时间
         */
        private String updateTime=" ";

        /**
         * 表在数据平台是否存在
         */
        private boolean tableExist = false;

        public boolean isTableExist() {
            return tableExist;
        }

        public void setTableExist(boolean tableExist) {
            this.tableExist = tableExist;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getObjectId() {
            return objectId;
        }
        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public List<String> getNodeFlowList() {
            return nodeFlowList;
        }

        public void setNodeFlowList(List<String> nodeFlowList) {
            this.nodeFlowList = nodeFlowList;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getDatabaseTableId() {
            return databaseTableId;
        }

        public void setDatabaseTableId(String databaseTableId) {
            this.databaseTableId = databaseTableId;
        }

        public List<PreDataProcess> getPreDataProcessList() {
            return preDataProcessList;
        }

        public void setPreDataProcessList(List<PreDataProcess> preDataProcessList) {
            this.preDataProcessList = preDataProcessList;
        }

        public String getDailyIncreaseCount() {
            return dailyIncreaseCount;
        }

        public void setDailyIncreaseCount(String dailyIncreaseCount) {
            this.dailyIncreaseCount = dailyIncreaseCount;
        }

        public String getOrganizationClassifyName() {
            return organizationClassifyName;
        }

        public void setOrganizationClassifyName(String organizationClassifyName) {
            this.organizationClassifyName = organizationClassifyName;
        }

        public String getClassifyFilter() {
            return classifyFilter;
        }

        public void setClassifyFilter(String classifyFilter) {
            this.classifyFilter = classifyFilter;
        }

        public Boolean getClickShowAll() {
            return clickShowAll;
        }

        public void setClickShowAll(Boolean clickShowAll) {
            this.clickShowAll = clickShowAll;
        }

        public String getDataBaseType() {
            return dataBaseType;
        }

        public void setDataBaseType(String dataBaseType) {
            this.dataBaseType = dataBaseType;
        }

        public String getModuleClassify() {
            return moduleClassify;
        }

        public void setModuleClassify(String moduleClassify) {
            this.moduleClassify = moduleClassify;
        }

        public Integer getModuleClassifyNum() {
            return moduleClassifyNum;
        }

        public void setModuleClassifyNum(Integer moduleClassifyNum) {
            this.moduleClassifyNum = moduleClassifyNum;
        }

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String getQueryId() {
            return queryId;
        }

        public void setQueryId(String queryId) {
            this.queryId = queryId;
        }

        public String getTableProject() {
            return tableProject;
        }

        public void setTableProject(String tableProject) {
            this.tableProject = tableProject;
        }

        public String getTargetDBEngName() {
            return targetDBEngName;
        }

        public void setTargetDBEngName(String targetDBEngName) {
            this.targetDBEngName = targetDBEngName;
        }

        public String getSourceFactoryId() {
            return sourceFactoryId;
        }

        public void setSourceFactoryId(String sourceFactoryId) {
            this.sourceFactoryId = sourceFactoryId;
        }


        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public Boolean getVisible() {
            return visible;
        }

        public void setVisible(Boolean visible) {
            this.visible = visible;
        }

        public String getSourceCode() {
            return sourceCode;
        }

        public void setSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
        }

        public String getTargetCode() {
            return targetCode;
        }

        public void setTargetCode(String targetCode) {
            this.targetCode = targetCode;
        }

        public String getOutputDatabaseType() {
            return outputDatabaseType;
        }

        public void setOutputDatabaseType(String outputDatabaseType) {
            this.outputDatabaseType = outputDatabaseType;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }


        public String getSubModuleName() {
            return subModuleName;
        }

        public void setSubModuleName(String subModuleName) {
            this.subModuleName = subModuleName;
        }

        public String getApplicationSystem() {
            return applicationSystem;
        }

        public void setApplicationSystem(String applicationSystem) {
            this.applicationSystem = applicationSystem;
        }

        public String getDataQueryType() {
            return dataQueryType;
        }

        public void setDataQueryType(String dataQueryType) {
            this.dataQueryType = dataQueryType;
        }

        public int getFlagNum() {
            return flagNum;
        }

        public void setFlagNum(int flagNum) {
            this.flagNum = flagNum;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getDataBaseId() {
            return dataBaseId;
        }

        public void setDataBaseId(String dataBaseId) {
            this.dataBaseId = dataBaseId;
        }

        public String getSourceIdCh() {
            return sourceIdCh;
        }

        public void setSourceIdCh(String sourceIdCh) {
            this.sourceIdCh = sourceIdCh;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getSourceFactoryCh() {
            return sourceFactoryCh;
        }

        public void setSourceFactoryCh(String sourceFactoryCh) {
            this.sourceFactoryCh = sourceFactoryCh;
        }

        public String getSourceCodeCh() {
            return sourceCodeCh;
        }

        public void setSourceCodeCh(String sourceCodeCh) {
            this.sourceCodeCh = sourceCodeCh;
        }

        public String getTableIdCh() {
            return tableIdCh;
        }

        public void setTableIdCh(String tableIdCh) {
            this.tableIdCh = tableIdCh;
        }

        public String getTableId() {
            return tableId;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }

        public String getTargetCodeCh() {
            return targetCodeCh;
        }

        public void setTargetCodeCh(String targetCodeCh) {
            this.targetCodeCh = targetCodeCh;
        }

        public String getTableNameCh() {
            return tableNameCh;
        }

        public void setTableNameCh(String tableNameCh) {
            this.tableNameCh = tableNameCh;
        }

        public String getTableNameEn() {
            return tableNameEn;
        }

        public void setTableNameEn(String tableNameEn) {
            this.tableNameEn = tableNameEn;
        }

    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
