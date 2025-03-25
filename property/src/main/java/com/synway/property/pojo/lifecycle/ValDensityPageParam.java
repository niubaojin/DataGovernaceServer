package com.synway.property.pojo.lifecycle;

import java.util.Objects;

/**
 * @author majia
 * @version 1.0
 * @date 2021/2/24 11:04
 */
public class ValDensityPageParam {
    private String tableNameEn;
    private String platformType;
    private String tableProject;
    private String primaryOrganizationCh;
    private String secondaryOrganizationCh;
    private ValDensity valDensity;

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

    public ValDensity getValDensity() {
        return valDensity;
    }

    public void setValDensity(ValDensity valDensity) {
        this.valDensity = valDensity;
    }

    public String getPrimaryOrganizationCh() {
        return primaryOrganizationCh;
    }

    public void setPrimaryOrganizationCh(String primaryOrganizationCh) {
        this.primaryOrganizationCh = primaryOrganizationCh;
    }

    public String getSecondaryOrganizationCh() {
        return secondaryOrganizationCh;
    }

    public void setSecondaryOrganizationCh(String secondaryOrganizationCh) {
        this.secondaryOrganizationCh = secondaryOrganizationCh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValDensityPageParam)) {
            return false;
        }
        ValDensityPageParam param = (ValDensityPageParam) o;
        return Objects.equals(getTableNameEn(), param.getTableNameEn()) &&
                Objects.equals(getPlatformType(), param.getPlatformType()) &&
                Objects.equals(getTableProject(), param.getTableProject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTableNameEn(), getPlatformType(), getTableProject());
    }
}
