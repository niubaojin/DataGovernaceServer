package com.synway.datarelation.constant;

/**
 * @Author chenfei
 * @Data 2024/5/24 14:50
 * @Description
 */
public interface UriConstant {

    // ------------ 数据仓库接口-----------
    public static final String RESOURCE_LOCAL_URI = "http://dataresource/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0";

    public static final  String RESOURCE_PROJECT_URI = "http://DATARESOURCE/DataResource/outputProjectNamesByInterface";

    public static final  String RESOURCE_TABLE_STRUCTURE_URI = "http://dataresource/dataresource/api/getTableStructure?resourceId=";

    public static final  String RESOURCE_TABLE_PARTITION_URI = "http://dataresource/dataresource/api/getPartitionInfo?resourceId=";

    public static final  String RESOURCE_GET_BY_ID_URI = "http://dataresource/dataresource/api/getResourceById?resId=";

    // ------------ 数据接入接口-----------
    public static final String ACCESS_RELATION_URI = "http://taskmanager/taskmanager/relation/queryAccessRelations";

    // ------------ 数据处理接口-----------
    public static final String STANDARD_RELATION_URI = "http://standardizeconfig/queryRelationInfo/get?queryInfo=";

    // ------------ 数据资产接口-----------
    public static final String PROPERTY_TABLE_ORGANIZATION = "http://property/interface/getTableOrganization";

}
