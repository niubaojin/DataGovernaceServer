package com.synway.property.entity.dto;

import lombok.Data;

@Data
public class DataOrganizationDTO {
    private Integer daysAgo;            //daysAgo
    private Integer dataOrgLevel;       //
    private String primaryClassifyCh;   //一级组织分类中文名称
    private String secondaryClassifyCh; //二级组织分类中文名称
    private String search;              //搜索关键字
    private String manufacturer;        //来源厂商
    private String authority;           //事权单位
    private String classifyid;          //数据组织分类代码值
    private String sjzzflCodeId;        //数据组织分类代码（区分技侦JZ/公安GA）

    public DataOrganizationDTO() {
    }

    public DataOrganizationDTO(Integer daysAgo,
                               Integer dataOrgLevel,
                               String primaryClassifyCh,
                               String secondaryClassifyCh,
                               String search,
                               String manufacturer,
                               String authority,
                               String classifyid,
                               String sjzzflCodeId) {
        this.daysAgo = daysAgo;
        this.dataOrgLevel = dataOrgLevel;
        this.primaryClassifyCh = primaryClassifyCh;
        this.secondaryClassifyCh = secondaryClassifyCh;
        this.search = search;
        this.manufacturer = manufacturer;
        this.authority = authority;
        this.classifyid = classifyid;
        this.sjzzflCodeId = sjzzflCodeId;
    }

}
