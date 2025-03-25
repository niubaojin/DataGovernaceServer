package com.synway.datastandardmanager.pojo.ExternalInterfce;


import java.util.List;
import com.synway.datastandardmanager.pojo.ExternalInterfce.ResourceTable;
import com.synway.datastandardmanager.pojo.TableField;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;

public class TableAllInfo {
    private DetectedTable resourceTable;
    private List<FieldInfo> tableFields;

    public DetectedTable getResourceTable() {
        return resourceTable;
    }

    public void setResourceTable(DetectedTable resourceTable) {
        this.resourceTable = resourceTable;
    }

    public List<FieldInfo> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<FieldInfo> tableFields) {
        this.tableFields = tableFields;
    }
}
