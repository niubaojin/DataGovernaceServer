package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class OrganizationInfo {
    private String resourceId;
    private String primaryDatasourceCh;
    private String secondaryDatasourceCh;
    private String primaryOrganizationCh;
    private String secondaryOrganizationCh;
    private String firstOrganizationCh;
}
