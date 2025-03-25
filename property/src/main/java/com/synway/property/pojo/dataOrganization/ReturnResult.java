package com.synway.property.pojo.dataOrganization;

import java.util.Set;

public class ReturnResult {
    public Set<DataOrganization> dataOrganizations;
    public int tableNums;

    public Set<DataOrganization> getDataOrganizations() {
        return dataOrganizations;
    }

    public void setDataOrganizations(Set<DataOrganization> dataOrganizations) {
        this.dataOrganizations = dataOrganizations;
    }

    public int getTableNums() {
        return tableNums;
    }

    public void setTableNums(int tableNums) {
        this.tableNums = tableNums;
    }
}
