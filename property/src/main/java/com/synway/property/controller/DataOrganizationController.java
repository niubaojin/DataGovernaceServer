package com.synway.property.controller;

import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;
import com.synway.property.service.DataOrganizationService;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据组织页面接口
 * @author majia
 * @version 1.0
 * @date 2020/10/15 13:36
 */
@Controller
@RequestMapping("/dataOrganizationMonitoring/dataOrganization")
public class DataOrganizationController {
    private static Logger logger = LoggerFactory.getLogger(DataOrganizationController.class);

    @Autowired
    private DataOrganizationService service;

    /**
     * 根据数据组织一级分类获取二级分类中文名，用于展示数据组织页面左侧分类列表
     * @param dataOrganizationType 数据组织一级分类中文名
     * @return 二级分类中文名集合
     */
    @RequestMapping("/getSecondaryClassifyList")
    @ResponseBody
    public ServerResponse getSecondaryClassifyList(@RequestParam("dataOrganizationType") String dataOrganizationType) {
        List returnList;
        try {
            returnList = service.getPageSecondaryClassify(dataOrganizationType);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("查询" + dataOrganizationType + "二级分类失败");
        }
        return ServerResponse.asSucessResponse(returnList);
    }

    /**
     * 根据数据组织一级分类获取二级分类Tree，用于展示数据组织页面左侧分类列表
     * @param dataOrganizationType 数据组织一级分类中文名
     * @return 二级分类tree
     */
    @RequestMapping("/getSecondaryClassifyListTree")
    @ResponseBody
    public ServerResponse getSecondaryClassifyListTree(@RequestParam("dataOrganizationType") String dataOrganizationType) {
        List<ClassifyInfoTree> returnList;
        try {
            returnList = service.getPageSecondaryClassifyTree(dataOrganizationType);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("查询" + dataOrganizationType + "二级分类失败");
        }
        return ServerResponse.asSucessResponse(returnList);
    }

//    /**
//     * 获取所有来源厂商中文名，用于数据组织原始库页面右上筛选来源厂商
//     * @return 来源厂商中文名集合
//     */
//    @RequestMapping("/getManufacturers")
//    @ResponseBody
//    public ServerResponse getManufacturers() {
//        List returnList;
//        try {
//            returnList = service.getAllManufacturers();
//        } catch (Exception e) {
//            logger.error(ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asErrorResponse("查询来源厂商失败");
//        }
//        return ServerResponse.asSucessResponse(returnList);
//    }
//
//    /**
//     * 获取所有事权单位中文名，用于数据组织原始库页面右上筛选事权单位
//     * @return 事权单位中文名集合
//     */
//    @RequestMapping("/getAuthorities")
//    @ResponseBody
//    public ServerResponse getAuthorities() {
//        List returnList;
//        try {
//            returnList = service.getAuthorities();
//        } catch (Exception e) {
//            logger.error(ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asErrorResponse("查询事权单位失败");
//        }
//        return ServerResponse.asSucessResponse(returnList);
//    }

    /**
     * 获取所有数据组织，用于数据组织原始库页面卡片内容
     * @return 事权单位中文名集合
     */
    @RequestMapping("/getDataOrganization")
    @ResponseBody
    public ServerResponse getDataOrganization(@RequestParam("dataOrganizationType") String dataOrganizationType,
                                              @RequestParam("classify")String classify,
                                              @RequestParam("classifyid")String classifyid,
                                              @RequestParam("manufacturer")String manufacturer,
                                              @RequestParam("authority")String authority,
                                              @RequestParam("search")String search,
                                              @RequestParam("dataSet") String dataSet) {
        return ServerResponse.asSucessResponse(service.getDataOrganization(dataOrganizationType,classify,classifyid,manufacturer,authority,search,dataSet));
    }
}
