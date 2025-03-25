package com.synway.reconciliation.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 获取数据血缘的节点关系
 * 查询  数据接入/标准管理/数据加工(直接查询表)返回的结果
 * @author ywj
 */
public class QueryBloodlineRelationInfo {
    /**
     * standard:表示是从标准管理传递过来的数据  access: 表示是从数据接入传递过来的数据
     */
    @NotNull
    private String type;
    /**
     * 源系统代号、  124等代码
     */
    private String sourceSysId="";
    /**
     * 源系统代号中文名
     */
    private String sourceSysChiName="";
    /**
     * 源协议英文名
     */
    private String sourceEngName="";
    /**
     * 源协议厂商代码
     */
    private String sourceSupplierId="";
    /**
     * 源协议中文名
     */
    private String sourceChiName="";
    /**
     * 目标系统代号（数据接入时为空）
     */
    private String targetSysId="";
    /**
     * 目标系统中文名（数据接入时为空）
     */
    private String targetSysChiName="";
    /**
     * 目标协议中文名（数据接入时为空）
     */
    private String targetEngName="";
    /**
     * 目标协议中文名
     */
    private String targetChiName="";
    /**
     * 输出项目名（数据接入输出标准化时为空，入库ads、odps时填写）
     */
    private String targetProjectName="";
    /**
     * 输出表名（数据接入输出标准化时为空，入库ads、odps时填写）
     */
    private String targetTableName="";
    /**
     *  [{"sourceColumnName":"crsj","targetColumnName":"chu_ru_shi_jian"}, {"sourceColumnName":"sfzh","targetColumnName":"shen_fen_zheng_hao"}],       输入字段/输出字段、
     */
    private List<ColumnRelation> fieldsRelation;
    /**
     * 对接入就是任务id、对数据处理就是处理任务id
     */
    private String taskUUID="";
    /**
     * 数据仓库id（数据接入独有）
     */
    private String databaseId="";
    /**
     * 输出平台的英文名称 例如 ADS-HC、ADS-HP、ODPS、OSOS
     */
    private String targetDBEngName="";


    public static class  ColumnRelation{
        public ColumnRelation(){
            //
        }

        @NotNull
        public String sourceColumnName;
        @NotNull
        public String targetColumnName;

        public String getSourceColumnName() {
            return sourceColumnName;
        }

        public void setSourceColumnName(String sourceColumnName) {
            this.sourceColumnName = sourceColumnName;
        }

        public String getTargetColumnName() {
            return targetColumnName;
        }

        public void setTargetColumnName(String targetColumnName) {
            this.targetColumnName = targetColumnName;
        }
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceSysId() {
        return sourceSysId;
    }

    public void setSourceSysId(String sourceSysId) {
        this.sourceSysId = sourceSysId;
    }

    public String getSourceSysChiName() {
        return sourceSysChiName;
    }

    public void setSourceSysChiName(String sourceSysChiName) {
        this.sourceSysChiName = sourceSysChiName;
    }

    public String getSourceEngName() {
        return sourceEngName;
    }

    public void setSourceEngName(String sourceEngName) {
        this.sourceEngName = sourceEngName;
    }

    public String getSourceSupplierId() {
        return sourceSupplierId;
    }

    public void setSourceSupplierId(String sourceSupplierId) {
        this.sourceSupplierId = sourceSupplierId;
    }

    public String getSourceChiName() {
        return sourceChiName;
    }

    public void setSourceChiName(String sourceChiName) {
        this.sourceChiName = sourceChiName;
    }

    public String getTargetSysId() {
        return targetSysId;
    }

    public void setTargetSysId(String targetSysId) {
        this.targetSysId = targetSysId;
    }

    public String getTargetSysChiName() {
        return targetSysChiName;
    }

    public void setTargetSysChiName(String targetSysChiName) {
        this.targetSysChiName = targetSysChiName;
    }

    public String getTargetEngName() {
        return targetEngName;
    }

    public void setTargetEngName(String targetEngName) {
        this.targetEngName = targetEngName;
    }

    public String getTargetChiName() {
        return targetChiName;
    }

    public void setTargetChiName(String targetChiName) {
        this.targetChiName = targetChiName;
    }

    public String getTargetProjectName() {
        return targetProjectName;
    }

    public void setTargetProjectName(String targetProjectName) {
        this.targetProjectName = targetProjectName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public List<ColumnRelation> getFieldsRelation() {
        return fieldsRelation;
    }

    public void setFieldsRelation(List<ColumnRelation> fieldsRelation) {
        this.fieldsRelation = fieldsRelation;
    }

    public String getTaskUUID() {
        return taskUUID;
    }

    public void setTaskUUID(String taskUUID) {
        this.taskUUID = taskUUID;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public String getTargetDBEngName() {
        return targetDBEngName;
    }

    public void setTargetDBEngName(String targetDBEngName) {
        this.targetDBEngName = targetDBEngName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


