package com.synway.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.organizationdetail.DataNum;
import com.synway.property.pojo.organizationdetail.DataResourceImformation;
import com.synway.property.pojo.organizationdetail.DataStatistics;
import com.synway.property.pojo.RequestParameter;
import com.synway.property.pojo.organizationdetail.ResourceRegisterInfo;
import com.synway.property.service.OrganizationDetailService;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @ClassName OrganizationDetailController
 * @Descroption 资产详情相关信息
 * @Author majia
 * @Date 2020/4/24 18:07
 * @Version 1.0
 **/
@Controller
@RequestMapping("/dataOrganizationMonitoring/organizationDetail")
public class OrganizationDetailController {

    private static Logger logger = LoggerFactory.getLogger(OrganizationDetailController.class);

    @Autowired
    private OrganizationDetailService organizationDetailService;

    @RequestMapping("/getTableExampleData")
    @ResponseBody
    public ServerResponse getTableExampleData(@RequestBody RequestParameter requestParameter) {
        List<JSONObject> returnList = null;
        try {
            returnList = organizationDetailService.getTableExampleData(
                    requestParameter.getTableProject(),
                    requestParameter.getTableNameEn(),
                    requestParameter.getTableType(),
                    requestParameter.getResourceId());
        } catch (Exception e) {
            logger.error("查询样例数据报错：", e);
            return ServerResponse.asErrorResponse("未查到样例数据，请确认表状态正常");
        }
        return ServerResponse.asSucessResponse(returnList);
    }

    @RequestMapping("/getTableStructure")
    @ResponseBody
    public ServerResponse getTableStructure(@RequestBody RequestParameter requestParameter) {
        List<JSONObject> returnList;
        try {
            returnList = organizationDetailService.getTableStructure(
                    requestParameter.getTableProject(),
                    requestParameter.getTableNameEn(),
                    requestParameter.getTableType(),
                    requestParameter.getResourceId());
        } catch (Exception e) {
            logger.error("查询表结构报错：", e);
            return ServerResponse.asErrorResponse("未查询到表结构，请确认表状态正常");
        }
        return ServerResponse.asSucessResponse(returnList);
    }

    @RequestMapping("/getDataNum")
    @ResponseBody
    public ServerResponse getDataNum(@RequestBody RequestParameter requestParameter) {
        DataNum returnObj = null;
        try {
            returnObj = organizationDetailService.getDataNum(
                    requestParameter.getTableProject(),
                    requestParameter.getTableNameEn(),
                    requestParameter.getTableType());
        } catch (Exception e) {
            logger.error("查询记录数据报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("未查到表记录数据，请确认表状态正常");
        }
        return ServerResponse.asSucessResponse(returnObj);
    }

    @RequestMapping("/getResourceId")
    @ResponseBody
    public ServerResponse getResourceId(@RequestBody RequestParameter requestParameter) {
        JSONObject jsonObject = new JSONObject();
        try {
            String resourceId = organizationDetailService.getResourceId(
                    requestParameter.getTableProject(),
                    requestParameter.getTableNameEn(),
                    requestParameter.getTableType());
            jsonObject.put("resourceId", resourceId);
        } catch (Exception e) {
            logger.error("查询resourceId报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("未查到数据源，请确认仓库中数据源正常");
        }
        return ServerResponse.asSucessResponse(jsonObject);
    }

    @RequestMapping("/getDataResource")
    @ResponseBody
    public ServerResponse getDataResourceInfo(@RequestBody RequestParameter requestParameter) {
        DataResourceImformation returnObj = null;
        try {
            returnObj = organizationDetailService.getDataResourceInfo(
                    requestParameter.getTableProject(),
                    requestParameter.getTableNameEn(),
                    requestParameter.getTableType(),
                    requestParameter.getResourceId());

        } catch (Exception e) {
            logger.error("查询记录数据报错：", e);
            return ServerResponse.asErrorResponse("未查到表元信息，请确认表状态正常");
        }
        return ServerResponse.asSucessResponse(returnObj);
    }

    @RequestMapping("/getTableOrganizationInfo")
    @ResponseBody
    public ServerResponse getTableOrganizationInfo(@RequestBody RequestParameter requestParameter) {
        DetailedTableByClassify returnObj = null;
        try {
            returnObj = organizationDetailService.getTableOrganizationInfo(
                    requestParameter.getTableProject(),
                    requestParameter.getTableNameEn(),
                    requestParameter.getTableType());
        } catch (Exception e) {
            logger.error("查询表组织数据报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("表组织信息获取失败");
        }
        return ServerResponse.asSucessResponse(returnObj);
    }

    /**
     * 数据量统计
     *
     * @return
     */
    @RequestMapping("/getDataCountStatistics")
    @ResponseBody
    public ServerResponse<List<DataStatistics>> getDataCountStatistics(@RequestBody RequestParameter requestParameter) {
        List<DataStatistics> list = null;
        try {
            list = organizationDetailService.getDataCountStatistics(requestParameter);
        } catch (Exception e) {
            logger.error("数据量统计报错：" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("数据量统计失败");
        }
        return ServerResponse.asSucessResponse(list);
    }
}
