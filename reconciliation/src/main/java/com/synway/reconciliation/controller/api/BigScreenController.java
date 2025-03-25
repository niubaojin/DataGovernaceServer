package com.synway.reconciliation.controller.api;

import com.synway.common.bean.ServerResponse;
import com.synway.reconciliation.conditional.IssueCondition;
import com.synway.reconciliation.interceptor.IgnoreSecurity;
import com.synway.reconciliation.pojo.IssueAccessCount;
import com.synway.reconciliation.pojo.RestReconInfo;
import com.synway.reconciliation.service.IssueReconciliationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据对账 对外接口
 * @author DZH
 */
@Slf4j
@Controller
@Conditional(IssueCondition.class)
@RequestMapping("/reconciliation/external")
public class BigScreenController {

    @Autowired
    private IssueReconciliationService issueReconciliationService;

    /**
     * 查询对账信息
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/getReconInfo", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<RestReconInfo> getReconInfo() {
        try {
            RestReconInfo reconInfo = issueReconciliationService.getReconInfo();
            return ServerResponse.asSucessResponse(reconInfo);
        } catch (Exception e) {
            String message = "查询对账信息报错";
            log.info(message);
            return ServerResponse.asErrorResponse(message);
        }
    }

    /**
     * 查询对账接入信息
     * @return
     */
    @IgnoreSecurity
    @RequestMapping(value = "/getIssueAccessCount", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<IssueAccessCount>> getIssueAccessCount() {
        try {
            List<IssueAccessCount> issueAccessCount = issueReconciliationService.getIssueAccessCount();
            return ServerResponse.asSucessResponse(issueAccessCount);
        } catch (Exception e) {
            String message = "查询对账接入信息报错";
            log.info(message);
            return ServerResponse.asErrorResponse(message);
        }
    }
}
