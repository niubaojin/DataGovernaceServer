package com.synway.datarelation.pojo.lineage;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *   case when 这个类型的字段还没有解析 不知道怎么解析
 */
public class ColumnName implements Serializable{
    // 普通的字段信息
    public static final String SQL_PROPERTY = "sqlProperty";
    // 具有方法的字段
    public static final String SQL_METHOD = "sqlMethod";
    //表示该字段是 *
    public static final String SQL_ALL = "sqlAll";
    // 固定参数 这个字段是 固定值
    public static final String SQL_FIXED = "sqlFixed";
    // case类型的数据
    public static final String SQL_CASE = "sqlCase";
    // 所属
    private String owner;
    // 字段名
    @NotNull
    private String columnName;
    // 别名  as之后的名称
    private String aliasName;
    // 字段类型   sqlProperty/sqlMethod
    private String type;
    //  函数名称
    private String methodName;
    // 表名的类型 具体的表名(table)/表名的别名,临时表(alias)/插入的表名(insert)
    private String tableNameType;
    private String tableName;
    private String aliasTableName;
    // 如果这个是 case的类型 则需要存储具体的字段信息
    private String caseStr;

    public String getCaseStr() {
        return caseStr;
    }

    public void setCaseStr(String caseStr) {
        this.caseStr = caseStr;
    }

    public String getAliasTableName() {
        return aliasTableName;
    }

    public void setAliasTableName(String aliasTableName) {
        this.aliasTableName = aliasTableName;
    }

    public String getTableNameType() {
        return tableNameType;
    }

    public void setTableNameType(String tableNameType) {
        this.tableNameType = tableNameType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}
