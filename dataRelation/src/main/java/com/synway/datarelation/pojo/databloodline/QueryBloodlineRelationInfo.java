package com.synway.datarelation.pojo.databloodline;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *   获取数据血缘的节点关系
 *   查询  数据接入/标准管理/数据加工(直接查询表)返回的结果
 * @author wangdongwei
 */
public class QueryBloodlineRelationInfo implements Serializable {
    public static final String DATAWARE = "dataware";
    public static final String ACCESS = "access";
    public static final String STANDARD = "standard";
    public static final String DATAPROCESS = "dataprocess";
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
     * 目标协议英文名（数据接入时为空）
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
     *  [{"sourceColumnName":"crsj","targetColumnName":"chu_ru_shi_jian"}, {"sourceColumnName":"sfzh","targetColumnName":"shen_fen_zheng_hao"}],       // 输入字段/输出字段、
     */
    private List<ColumnRelation> fieldsRelation;
    /**
     *     对接入就是任务id、对数据处理就是处理任务id
     */
    private String taskUUID="";
    /**
     * 数据仓库id（数据接入独有）
     */
    private String databaseId="";
    /**
     * 输出平台的英文名称 例如 ADS-HC、ADS-HP、ODPS、OSOS
     *     20200617 新增华为平台那边的输出平台   hive  hbase
      */
    private String targetDBEngName="";

    /**
     *20210329 数据仓库中源表的id值 数据接入独有 以下都为独有
     */
    private String sourceDatabaseTableId="";
    /**
     * 目标表的在数据仓库中的id值
     */
    private String targetDatabaseTableId="";
    /**
     * 源表的数据仓库id
     */
    private String sourceDatabaseId="";
    /**
     * 目标表的数据仓库id
     */
    private String targetDatabaseId="";

    /**
     * 数据接入任务名称
     */
    private String taskName ="";

    /**
     *  源的  ftp/oracle/mysql/hive 等
     */
    private String sourceDatabaseType="";
    /**
     * 目标的  ftp/oracle/mysql/hive 等
     */
    private String targetDatabaseType="";


    public class  ColumnRelation implements Serializable{
        @NotNull
        public String sourceColumnName="";
        @NotNull
        public String targetColumnName="";
        @NotNull
        public String sourceColumnChiName="";
        @NotNull
        public String targetColumnChiName="";

        public String getSourceColumnChiName() {
            return sourceColumnChiName;
        }

        public void setSourceColumnChiName(String sourceColumnChiName) {
            this.sourceColumnChiName = sourceColumnChiName;
        }

        public String getTargetColumnChiName() {
            return targetColumnChiName;
        }

        public void setTargetColumnChiName(String targetColumnChiName) {
            this.targetColumnChiName = targetColumnChiName;
        }

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

    public String getSourceDatabaseType() {
        return sourceDatabaseType;
    }

    public void setSourceDatabaseType(String sourceDatabaseType) {
        this.sourceDatabaseType = sourceDatabaseType;
    }

    public String getTargetDatabaseType() {
        return targetDatabaseType;
    }

    public void setTargetDatabaseType(String targetDatabaseType) {
        this.targetDatabaseType = targetDatabaseType;
    }

    public String getSourceDatabaseTableId() {
        return sourceDatabaseTableId;
    }

    public void setSourceDatabaseTableId(String sourceDatabaseTableId) {
        this.sourceDatabaseTableId = sourceDatabaseTableId;
    }

    public String getTargetDatabaseTableId() {
        return targetDatabaseTableId;
    }

    public void setTargetDatabaseTableId(String targetDatabaseTableId) {
        this.targetDatabaseTableId = targetDatabaseTableId;
    }

    public String getSourceDatabaseId() {
        return sourceDatabaseId;
    }

    public void setSourceDatabaseId(String sourceDatabaseId) {
        this.sourceDatabaseId = sourceDatabaseId;
    }

    public String getTargetDatabaseId() {
        return targetDatabaseId;
    }

    public void setTargetDatabaseId(String targetDatabaseId) {
        this.targetDatabaseId = targetDatabaseId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
