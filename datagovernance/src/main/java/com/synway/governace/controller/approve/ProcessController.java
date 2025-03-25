package com.synway.governace.controller.approve;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.process.*;
import com.synway.governace.service.process.ProcessService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Map;

/**
 * @author ywj
 */
@Controller
@RequestMapping("/process")
public class ProcessController {
    private Logger logger = Logger.getLogger(ProcessController.class);

    @Autowired
    private ProcessService processService;

    @Value("${synwf.sysId}")
    private String sysId;

    @Autowired()
    private Environment env;

    @RequestMapping(value = "/approval")
    public String approvalPage(@QueryParam(value = "approvalId") String approvalId,
                               @QueryParam(value = "wf") boolean wf,
                               Model model) {
        model.addAttribute("approvalId", approvalId);
        model.addAttribute("wf", wf);
        model.addAttribute("sysId", sysId);
        return "process/approval";
    }

    @RequestMapping(value = "/waitList")
    public String waitListPage(Model model) {
        model.addAttribute("sysId", sysId);
        return "process/waitList";
    }

    @RequestMapping(value = "/myAppPortlet")
    public String myAppPortletPage(Model model) {
        model.addAttribute("sysId", sysId);
        return "process/myAppPortlet";
    }

    @RequestMapping(value = "/participantAffairPortlet")
    public String participantAffairPortletPage(Model model) {
        model.addAttribute("sysId", sysId);
        return "process/participantAffairPortlet";
    }

    /**
     * 审批申请信息新增/编辑保存
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateApprovalInfo")
    @ResponseBody
    public ServerResponse<String> saveOrUpdateApprovalInfo(@RequestBody String jsonString) {
        ServerResponse<String> resultMessage = null;
        try {
            ApprovalInfo info = JSONObject.parseObject(jsonString, ApprovalInfo.class);
            resultMessage = processService.saveOrUpdateApprovalInfo(info);
        } catch (Exception e) {
            logger.error("申请信息新增/编辑保存异常:" , e);
            resultMessage = ServerResponse.asErrorResponse("申请信息新增/编辑保存报错:" + e.getMessage());
        }
        return resultMessage;
    }

    /**
     * 申请信息详情获取
     *
     * @return
     */
    @RequestMapping(value = "/getApprovalDetail")
    @ResponseBody
    public ServerResponse<ApprovalInfo> getApprovalDetail(@RequestParam("approvalId") String approvalId) {
        try {
            ApprovalInfo result = processService.getApprovalInfoDetail(approvalId);
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.info("申请信息详情获取异常：", e);
            return ServerResponse.asErrorResponse("申请信息详情获取异常：" + e.getMessage());
        }
    }


    /**
     * 流程启动
     *
     * @return
     */
    @RequestMapping(value = "/start")
    @ResponseBody
    public ServerResponse<JSONObject> startWorkFlowForRoll(HttpServletRequest request, @RequestBody WorkFlowParams param) {
        try {
            String synwfUrl = env.getProperty("nginxIp");
            JSONObject resultJson = processService.startWorkFlowForRoll(param, synwfUrl);
            if (resultJson == null || resultJson.isEmpty()) {
                return ServerResponse.asErrorResponse("流程启动失败,未找到工作流服务");
            } else {
                if (resultJson.getBoolean("success")) {
                    return ServerResponse.asSucessResponse(resultJson);
                } else {
                    return ServerResponse.asSucessResponse(resultJson.getString("msg"));
                }
            }
        } catch (Exception e) {
            logger.info("流程启动异常：", e);
            return ServerResponse.asSucessResponse("流程启动异常：" + e.getMessage());
        }
    }

    /**
     * 流程启动并发送下一步
     *
     * @return
     */
    @RequestMapping(value = "/startAndSendNextStep")
    @ResponseBody
    public ServerResponse<JSONObject> startAndSendNextStep(HttpServletRequest request, @RequestBody WorkFlowParams param) {
        try {
            String synwfUrl = env.getProperty("nginxIp");
            JSONObject resultJson = processService.startAndSendNextStep(param, synwfUrl);
            if (resultJson == null || resultJson.isEmpty()) {
                return ServerResponse.asErrorResponse("流程启动失败,未找到工作流服务");
            } else {
                if (resultJson.getBoolean("success")) {
                    return ServerResponse.asSucessResponse(resultJson);
                } else {
                    return ServerResponse.asSucessResponse(resultJson.getString("msg"));
                }
            }
        } catch (Exception e) {
            logger.info("流程启动异常：", e);
            return ServerResponse.asSucessResponse("流程启动异常：" + e.getMessage());
        }
    }

    /**
     * 流程结束回调业务系统方法
     *
     * @return
     */
    @RequestMapping(value = "/end")
    @ResponseBody
    public JSONObject endProcess(@RequestBody String jsonStr) {
        JSONObject result = new JSONObject();
        try {
            ServerResponse<String> response = processService.endProcess(jsonStr);
            if (ProcessResponseCode.SUCCESS.getCode() == response.getStatus()) {
                result.put("msg", response.getData());
                result.put("success", true);
            } else {
                result.put("msg", response.getData());
                result.put("success", false);
            }
            return result;
        } catch (Exception e) {
            logger.info("流程停止异常：" , e);
            result.put("msg", "流程停止异常：" + e.getMessage());
            result.put("success", false);
            return result;
        }
    }

    /**
     * 标准管理页面获取
     *
     * @return
     */
    @RequestMapping(value = "/getStandardPageByApprovalInfo")
    @ResponseBody
    public ServerResponse<String> getStandardPageByApprovalInfo(HttpServletRequest request, @RequestBody ApprovalInfo info) {
        try {
            String url = env.getProperty("nginxIp");
            JSONObject obj = processService.getStandardPageByApprovalInfo(info, url, url+"/factorygateway/datastandardmanager");
            if(obj.getInteger("status") == 1){
                return ServerResponse.asSucessResponse(obj.getString("data"));
            }else{
                return ServerResponse.asSucessResponse(obj.getString("msg"));
            }
        } catch (Exception e) {
            logger.info("标准管理页面获取异常：" , e);
            return ServerResponse.asSucessResponse("标准管理页面获取异常：" + e.getMessage());
        }
    }

    /**
     * 审批流程信息查询
     *
     * @return
     */
    @RequestMapping(value = "/queryApprovalInfoForModule")
    @ResponseBody
    public ServerResponse<List<ApprovalInfo>> queryApprovalInfoForModule(@RequestBody ApprovalInfo info) {
        try {
            List<ApprovalInfo> list = processService.queryApprovalInfoForModule(info);
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.info("审批流程信息查询异常：", e);
            return ServerResponse.asSucessResponse("审批流程信息查询异常：" + e.getMessage());
        }
    }

    /**
     * 审批事务操作类型获取
     *
     * @return
     */
    @RequestMapping(value = "/getOperationTypeData")
    @ResponseBody
    public ServerResponse<List<String>> getOperationTypeData() {
        try {
            List<String> list = processService.getOperationTypeData();
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.info("审批事务操作类型获取异常：", e);
            return ServerResponse.asErrorResponse("审批事务操作类型获取异常：" + e.getMessage());
        }
    }

    /**
     * 在办事务列表获取
     * @param queryString
     * @return ProcessServerResponse<Map<String, Object>>
     */
    @RequestMapping(value = "/getDoingAffairList")
    @ResponseBody
    public ServerResponse<Map<String, Object>> getDoingAffairList(@RequestBody String queryString) {
        logger.info("在办事务列表参数："+ queryString);
        try {
            String url = env.getProperty("nginxIp");
            Map<String, Object> map = processService.getDoingAffairList(queryString, url);
            return ServerResponse.asSucessResponse(map);
        } catch (Exception e) {
            logger.info("在办事务列表获取异常：", e);
            return ServerResponse.asErrorResponse("在办事务列表获取异常：" + e.getMessage());
        }
    }

    /**
     * 待办事务列表获取
     * @param queryString
     * @return ProcessServerResponse<Map<String, Object>>
     */
    @RequestMapping(value = "/getToDoAffairList")
    @ResponseBody
    public ServerResponse<Map<String, Object>> getToDoAffairList(@RequestBody String queryString) {
        logger.info("待办事务列表参数："+ queryString);
        try {
            String url = env.getProperty("nginxIp");
            Map<String, Object> map = processService.getToDoAffairList(queryString, url);
            return ServerResponse.asSucessResponse(map);
        } catch (Exception e) {
            logger.info("待办事务列表获取异常：", e);
            return ServerResponse.asErrorResponse("待办事务列表获取异常：" + e.getMessage());
        }
    }

    /**
     * 已办事务列表获取
     * @param queryString
     * @return ServerResponse<Map<String, Object>>
     */
    @RequestMapping(value = "/getDoneAffairList")
    @ResponseBody
    public ServerResponse<Map<String, Object>> getDoneAffairList(@RequestBody String queryString) {
        logger.info("已办事务列表参数："+ queryString);
        try {
            String url = env.getProperty("nginxIp");
            Map<String, Object> map = processService.getDoneAffairList(queryString, url);
            return ServerResponse.asSucessResponse(map);
        } catch (Exception e) {
            logger.info("已办事务列表获取异常：", e);
            return ServerResponse.asErrorResponse("已办事务列表获取异常：" + e.getMessage());
        }
    }

    /**
     * 召回事务
     * @param params
     * @return JSONObject
     */
    @RequestMapping(value = "/callbackAffair")
    @ResponseBody
    public JSONObject callbackAffair(@RequestBody String params) {
        logger.info("召回事务传参："+ params);
        JSONObject result = new JSONObject();
        try {
            String url = env.getProperty("nginxIp");
            result = processService.callbackAffair(params, url);
            return result;
        } catch (Exception e) {
            logger.info("流程召回失败：", e);
            result.put("msg", "流程召回失败：" + e.getMessage());
            result.put("success", false);
            return result;
        }
    }

    /**
     * 终止事务
     * @param params
     * @return JSONObject
     */
    @RequestMapping(value = "/endAffair")
    @ResponseBody
    public JSONObject endAffair(@RequestBody String params) {
        logger.info("终止事务传参："+ params);
        JSONObject result = new JSONObject();
        try {
            String url = env.getProperty("nginxIp");
            result = processService.endAffair(params, url);
            return result;
        } catch (Exception e) {
            logger.info("流程终止失败：", e);
            result.put("msg", "流程终止失败：" + e.getMessage());
            result.put("success", false);
            return result;
        }
    }
}
