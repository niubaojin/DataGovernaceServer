package com.synway.datastandardmanager.entity.dto;


import lombok.Data;

/**
 * @author wangdongwei
 * @description 刷新已建表需要的相关参数
 * @date 2021/1/28 15:00
 */
@Data
public class RefreshCreateTableDTO {
    private String tableName;   //表英文名 不需要项目名 当为空时表示是刷新的全部标准表信息
    private String tableId;     //表协议id  当为空时表示是刷新的全部标准表信息
    private String userName;    //url地址栏里面的  userName=一级管理员
    private String userId;      //url地址栏里面的userId=16472，即用户的id值
    private String objectName;
    private String dataCenter;  //数据中心
    private String dataCenterId;//数据中心Id
    private String resName;     //数据源
    private String resId;       //数据源id
    private String projectName; //项目空间
    private String projectId;   //项目空间id
}
