package com.synway.governace.service.largeScreen;

import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;

import java.util.List;

/**
 * 详细大屏的资产信息
 */
public interface DetailedLargeScreenService {

    /**
     对外共享：
     *     1 鼠标停靠“共享数据种类”上方，显示悬浮框，显示数据名称+数据记录数。
     *     2 鼠标停靠“共享数据量”上方，显示悬浮框，显示数据名称+数据记录数。
     *     3 鼠标停靠“共享服务接口”上方，显示悬浮框，显示服务接口名称+调用次数。
     *     4 鼠标停靠“共享服务调用次数”上方，显示悬浮框，显示服务接口名称+调用次数
     * @param searchName
     * @return
     */
    List<PropertyLargeDetailed> getPublicInfo(String searchName);


    /**
     *  获取原始库资产信息 的二级分类的详细表信息
     * @return
     */
    List<PropertyLargeDetailed> getDetailedOriginalData(String searchName);


    /**
     *  获取资源库资产信息的 分类的详细信息
     * @param searchName   查询内容
     * @param type   主题库资产/资源库资产
     * @return
     */
    List<PropertyLargeDetailed> getDetailedResourceThemeData(String searchName,String type);


    /**
     * 获取本地仓的 表数量
     * @param dbType  数据库类型
     * @return
     */
    String getLargeTableSum(String dbType);




}
