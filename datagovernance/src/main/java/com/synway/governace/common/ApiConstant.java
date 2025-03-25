package com.synway.governace.common;

/**
 * @Author Administrator
 * @Data 2024/6/28 16:46
 * @Description
 */
public interface ApiConstant {

    /**
     * 调用仓库接口地址
     */
    public static final String RESOURCE_SERVICE_API_URL = "http://dataresource/dataresource/api";

    public static final String RESOURCE_GET_DATA_RESOURCE_URL = "http://dataresource/DataResource/getAllDataResource";

    public static final String RESOURCE_GET_LOCAL_DATA_RESOURCE = "http://dataresource/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0";

    public static final String PROPERTY_GET_DB_STATUS = "http://property/dataStorageMonitoring/getDataBaseStatus?platFormType=";

    public static final String DATAOPERATIONS_BASEURL = "http://dataoperations/dataoperations";

    /**
     * 调用资源服务平台接口
     */
    public static final String SERVICEFACSRV_BASEURL = "http://servicefacsrv";

}
