package com.synway.datarelation.pojo.lineage;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *   解析sql里面 字段 血缘关系需要的实体对象
 */
public class ColumnParseData implements Serializable{
    public static final String TABLE = "table";
    public static final String ALIAS = "alias";
    public static final String INSERT = "insert";
    //  主键ID
    @NotNull
    private String id;
    // 表名的类型 具体的表名(table)/表名的别名,临时表(alias)/插入的表名(insert)
    private String tableNameType;
    private String tableName;
    // 父节点的id
    private String parentId;
    // 该表名对应的 字段信息
    private List<ColumnName> columnList;
    // 别名
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<ColumnName> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnName> columnList) {
        this.columnList = columnList;
    }
}
