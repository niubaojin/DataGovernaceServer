package com.synway.datastandardmanager.pojo.buildtable;


import lombok.Data;

/**
 * @author wangdongwei
 * @ClassName RefreshCreatedPojo
 * @description 刷新已建表需要的相关参数
 * @date 2021/1/28 15:00
 */
@Data
public class RefreshCreatedPojo {

    /**
     *  表英文名 不需要项目名 当为空时表示是刷新的全部标准表信息
     */
    private String tableName;

    /**
     * 表协议id  当为空时表示是刷新的全部标准表信息
     */
    private String tableId;

    /**
     * url地址栏里面的  userName=一级管理员
     */
    private String userName;

    /**
     * url地址栏里面的  userId=16472
     * 即用户的id值
     */
    private String userId;

    private String objectName;

    //数据中心
    private String dataCenter;

    //数据中心Id
    private String dataCenterId;

    //数据源
    private String resName;

    //数据源id
    private String resId;

    //项目空间
    private String projectName;

    //项目空间id
    private String projectId;

    //是否单表刷新
    private Boolean isOneTableRefresh = false;


}
