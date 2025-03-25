package com.synway.datarelation.pojo.databloodline;

import java.util.List;

/**
 *  字段血缘的信息
 */
public class ColumnBloodlineNode {
    private List<ColumnNode> nodes;
    private List<ColumnEdges> edges;

    public List<ColumnNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ColumnNode> nodes) {
        this.nodes = nodes;
    }

    public List<ColumnEdges> getEdges() {
        return edges;
    }

    public void setEdges(List<ColumnEdges> edges) {
        this.edges = edges;
    }

    public static class ColumnNode{
        //  节点的id
        private String id = "";
        //  字段的展示名称 小写
        private String columnName = "";
        //  表英文名 如果存在表名 则展示表名  项目名.表名
        private String tableNameEn = "";
        //  表中文名 如果存在 则展示
        private String tableNameCh = "";
        //  表协议ID
        private String tableId = "";
        // 表协议中文名
        private String tableIdCh = "";
        // 如果有中文名  字段中文名
        private String columnNameCh= "";
        // 数据血缘的节点名
        private String nodeName = "";
        // 系统协议中文名
        private String sourceCode = "";
        // 系统协议英文名
        private String sourceCodeCh = "";
        // 节点的类型 是否为主节点  main
        private String dataType="";

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getSourceCode() {
            return sourceCode;
        }

        public void setSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
        }

        public String getSourceCodeCh() {
            return sourceCodeCh;
        }

        public void setSourceCodeCh(String sourceCodeCh) {
            this.sourceCodeCh = sourceCodeCh;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getColumnNameCh() {
            return columnNameCh;
        }

        public void setColumnNameCh(String columnNameCh) {
            this.columnNameCh = columnNameCh;
        }

        public String getTableIdCh() {
            return tableIdCh;
        }

        public void setTableIdCh(String tableIdCh) {
            this.tableIdCh = tableIdCh;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getTableNameEn() {
            return tableNameEn;
        }

        public void setTableNameEn(String tableNameEn) {
            this.tableNameEn = tableNameEn;
        }

        public String getTableNameCh() {
            return tableNameCh;
        }

        public void setTableNameCh(String tableNameCh) {
            this.tableNameCh = tableNameCh;
        }

        public String getTableId() {
            return tableId;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }
    }

    public static class ColumnEdges{
        // 节点之间连线的关系 是id对应的连接关系
        private String source="";
        private String target="";
        // 数据加工的字段血缘存在一个 caseStr 主要用于存储操作代码
        private String caseStr="";
        public  ColumnEdges(String source ,String target,String caseStr){
            this.source = source;
            this.target = target;
            this.caseStr = caseStr;
        }
        public  ColumnEdges(String source ,String target){
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public String getCaseStr() {
            return caseStr;
        }

        public void setCaseStr(String caseStr) {
            this.caseStr = caseStr;
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
}
