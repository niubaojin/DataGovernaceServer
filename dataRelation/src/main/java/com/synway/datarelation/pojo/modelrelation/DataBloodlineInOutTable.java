package com.synway.datarelation.pojo.modelrelation;

/**
 *  解析数据血缘之后的结果
 * @author wangdongwei
 */
public class DataBloodlineInOutTable {
    private String nodeId;
    private String nodeType;
    private String nodeName;
    private String flowName;
    private String inputTableName = "";
    private String outputTableName ="";
    private int fileVersion = 1;
    private String inOutLocation;
    private int inOutLevel;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }


    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getInputTableName() {
        return inputTableName;
    }

    public void setInputTableName(String inputTableName) {
        this.inputTableName = inputTableName;
    }

    public String getOutputTableName() {
        return outputTableName;
    }

    public void setOutputTableName(String outputTableName) {
        this.outputTableName = outputTableName;
    }

    public int getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getInOutLocation() {
        return inOutLocation;
    }

    public void setInOutLocation(String inOutLocation) {
        this.inOutLocation = inOutLocation;
    }

    public int getInOutLevel() {
        return inOutLevel;
    }

    public void setInOutLevel(int inOutLevel) {
        this.inOutLevel = inOutLevel;
    }
}
