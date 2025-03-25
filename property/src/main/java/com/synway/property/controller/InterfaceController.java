package com.synway.property.controller;

import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.RequestParameter;
import com.synway.property.pojo.datastoragemonitor.TableOrganizationData;
import com.synway.property.pojo.interfacePojo.ReceiveTable;
import com.synway.property.service.InterfaceService;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 对外接口
 *
 * @author majia
 * @version 1.0
 * @date 2020/6/21 9:22
 */
@RequestMapping("/interface")
@Controller
public class InterfaceController {

    private Logger logger = LoggerFactory.getLogger(InterfaceController.class);

    @Autowired
    private InterfaceService interfaceService;
	
	@RequestMapping("/updateDataResource")
    @ResponseBody
    public ServerResponse updateDataResource(@Nullable @RequestParam("dataCenterId") String dataCenterId,
                                             @Nullable @RequestParam("dataCenterName") String dataCenterName,
                                             @Nullable @RequestParam("dataSourceId") String dataSourceId,
                                             @Nullable @RequestParam("dataSourceName") String dataSourceName) {
        boolean flag = true;
        if (StringUtils.isNotBlank(dataCenterId)) {
            if (StringUtils.isNotBlank(dataCenterName)) {
                interfaceService.updateDataCenterName(dataCenterId, dataCenterName);
                flag = false;
                logger.info("实时监控已控数据中心名修改为{}" + dataCenterName);
            } else {
                return ServerResponse.asErrorResponse("数据中心名为空");
            }
        }
        if (StringUtils.isNotBlank(dataSourceId)) {
            if (StringUtils.isNotBlank(dataSourceName)) {
                interfaceService.updateDataSourceName(dataSourceId, dataSourceName);
                flag = false;
                logger.info("实时监控已控数据源名修改为{}" + dataSourceName);
            } else {
                return ServerResponse.asErrorResponse("数据源名为空");
            }
        }
        if (flag) {
            return ServerResponse.asSucessResponse("未修改任何项");
        } else {
            return ServerResponse.asSucessResponse("首页实时监控数据源已修改");
        }
    }

    @RequestMapping("/updateTableOrganization")
    @ResponseBody
    /**
     * TODO 更新资产信息，待完成
     */
    public ServerResponse updateTableOrganization(@RequestBody RequestParameter requestParameter) {
        TableOrganizationData oldT = requestParameter.getOldTableOrganizationData();
        TableOrganizationData newT = requestParameter.getNewTableOrganizationData();
        try {
            if (oldT != null && newT != null) {
                interfaceService.updateTableOrganization(oldT, newT);
            }else {
                return ServerResponse.asErrorResponse("传递参数为空");
            }
        } catch (Exception e) {
            logger.error("更新失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("更新失败");
        }
        return ServerResponse.asSucessResponse("更新成功");
    }

    @RequestMapping("/getTableOrganization")
    @ResponseBody
    /**
     * 获取资产信息
     */
    public ServerResponse getTableOrganization(@RequestBody RequestParameter requestParameter) {
        List<ReceiveTable> tables = requestParameter.getTable();
        List<DetailedTableByClassify> list;
        try {
            if (tables != null&&tables.size() != 0) {
                list = interfaceService.getTableOrganization(tables);
            }else {
                return ServerResponse.asErrorResponse("传递参数为空");
            }
        } catch (Exception e) {
            logger.error("查询失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("查询失败");
        }
        return ServerResponse.asSucessResponse(list);
    }
}
