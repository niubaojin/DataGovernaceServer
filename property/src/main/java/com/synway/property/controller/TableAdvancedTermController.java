package com.synway.property.controller;

import com.synway.property.pojo.RequestParameter;
import com.synway.property.service.TableAdvancedTermService;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/11/6 13:58
 */
@Controller
@RequestMapping("/dataOrganizationMonitoring/tableAdvancedTerm")
public class TableAdvancedTermController {
    private static Logger logger = LoggerFactory.getLogger(TableAdvancedTermController.class);

    @Autowired
    private TableAdvancedTermService termService;

    @RequestMapping("/loadFieldTerms")
    @ResponseBody
    public ServerResponse loadFieldTerms(@RequestParam("fieldTermType") String fieldTermType,
                                         @Nullable @RequestParam("query") String query) {
        ServerResponse serverResponse = null;
        try {
            if ("semantic".equals(fieldTermType) || "elementSet".equals(fieldTermType)) {
                serverResponse = ServerResponse.asSucessResponse(termService.loadFieldTerms(fieldTermType, query));
            } else {
                serverResponse = ServerResponse.asErrorResponse("加载表字段条件类型不匹配");
            }
        } catch (Exception e) {
            logger.error("加载表字段语义类型或元素集失败\n" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("加载表字段语义类型或元素集失败");
        }
        return serverResponse;
    }

    @RequestMapping("/loadFilteredData")
    @ResponseBody
    public ServerResponse loadFilteredData(@RequestBody RequestParameter parameter
    ) {
        ServerResponse serverResponse = null;
        String fieldTermType = parameter.getFieldTermType();
        List<String> fieldTermConfirmed = parameter.getFieldTermConfirmed();
        String composeTerm = parameter.getComposeTerm();
        try {
            if (("semantic".equals(fieldTermType) || "elementSet".equals(fieldTermType))
                    && fieldTermConfirmed.size() != 0
                    && StringUtils.isNotBlank(composeTerm)) {
                serverResponse = ServerResponse.asSucessResponse(termService.loadFilteredData(fieldTermType, fieldTermConfirmed, composeTerm));
            } else {
                serverResponse = ServerResponse.asErrorResponse("搜索条件缺失");
            }
        } catch (Exception e) {
            logger.error("筛选结果失败\n" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("筛选结果失败");
        }
        return serverResponse;
    }

}
