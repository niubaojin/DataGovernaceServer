package com.synway.governace.service.process.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.common.Constant;
import com.synway.governace.dao.ProcessDao;
import com.synway.governace.enums.SysCodeEnum;
import com.synway.governace.pojo.process.*;
import com.synway.governace.service.process.ProcessService;
import com.synway.governace.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 审批流程业务实现类
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessDao processDao;

    @Value("${synwf.sysId}")
    private String sysId;

    @Value("${synwf.moduleId}")
    private String moduleId;

    @Autowired
    RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(ProcessServiceImpl.class);

    @Override
    public ServerResponse<String> saveOrUpdateApprovalInfo(ApprovalInfo info) {
        logger.info(">>>>>>>>>>>>>approvalInfo:" + info.toString());
        ApprovalInfo approval = null;
        if (StringUtils.isNotBlank(info.getApprovalId())) {
            approval = processDao.findApprovalById(info.getApprovalId());
        }
        if (null == approval) {
            approval = new ApprovalInfo();
            BeanUtils.copyProperties(info, approval);
            approval.setApprovalId(UUIDUtil.getUUID());
            approval.setStatus("0");
            processDao.insertApprovalInfo(approval);
        } else {
            if (StringUtils.isNotBlank(info.getModuleName())) {
                approval.setModuleName(info.getModuleName());
            }
            if (StringUtils.isNotBlank(info.getOperationName())) {
                approval.setOperationName(info.getOperationName());
            }
            if (StringUtils.isNotBlank(info.getApplicationInfo())) {
                approval.setApplicationInfo(info.getApplicationInfo());
            }
            if (StringUtils.isNotBlank(info.getTaskId())) {
                approval.setTaskId(info.getTaskId());
            }
            if (StringUtils.isNotBlank(info.getOperatorId())) {
                approval.setOperatorId(info.getOperatorId());
            }
            if (StringUtils.isNotBlank(info.getStatus())) {
                approval.setStatus(info.getStatus());
            }
            if (StringUtils.isNotBlank(info.getCallbackData())) {
                approval.setCallbackData(info.getCallbackData());
            }
            if (StringUtils.isNotBlank(approval.getProcessinstanceid())) {
                approval.setProcessinstanceid(approval.getProcessinstanceid());
            } else {
                approval.setProcessinstanceid(info.getProcessinstanceid());
            }
            processDao.updateApprovalInfo(approval);
        }
        return ServerResponse.asSucessResponse("操作成功", approval.getApprovalId());
    }

    @Override
    public ApprovalInfo getApprovalInfoDetail(String approvalId) {
        return processDao.findApprovalById(approvalId);
    }

    @Override
    public JSONObject startWorkFlowForRoll(WorkFlowParams param, String serverUrl) {
        // 流程提交
        JSONObject p = new JSONObject();
        String result = null;
        JSONObject resultJson = null;
        //业务系统标识，【系统管理-业务系统设置】中定义的id
        p.put("sis_id", sysId);
        //【流程定义-业务流程关联】中的定义的【模块标识】
        p.put("censor_type", moduleId);
        //区域id,若地区差别时，该地区启动该地区指定的流程
        p.put("type", param.getAreaId());
        //url参数，拼接在业务流程管理url后
        String recordid = "&approvalId=" + param.getApprovalId();
        p.put("record_id", recordid);
        p.put("process_title", "关于\"" + param.getModuleName() + param.getOperationName() + "\"${process_title}[${datetime}]");
        //登录用户id
        p.put("user_id", param.getOperatorId());
        //登录用户名称
        p.put("user_name", param.getOperatorName());
        //登录用户机构id
        p.put("organ_id", param.getOperatorOrganId());
        //登录用户所在区域id
        p.put("area_id", param.getAreaId());
        String webServiceUrl = serverUrl + "/SynWF/wf/start";
        System.out.println(">>>>>>>>>>>>>流程发起，webServiceUrl:" + webServiceUrl);
        int i = 0;
        try {
            result = restTemplate.postForObject(webServiceUrl, p, String.class);
            System.out.println(">>>>>>>>>>>>>流程发起，result:" + result);
            resultJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            logger.error(e);
            return resultJson;
        }
        if (resultJson == null || resultJson.isEmpty()) {
            //只要超过了3次，或者有流程实例不为空了就退出循环
            while (i < 3) {
                if (resultJson == null || resultJson.isEmpty()) {
                    try {
                        result = restTemplate.postForObject(webServiceUrl, p.toString(), String.class);
                        System.out.println(">>>>>>>>>>>>>流程发起" + i + "，result:" + result);
                        resultJson = JSONObject.parseObject(result);
                    } catch (Exception e) {
                        logger.error("调用接口异常：",e);
                        return resultJson;
                    }
                    //延迟1s再继续执行
                    try {
                        if (resultJson == null || resultJson.isEmpty()) {
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException ie) {
                        logger.error("调用接口异常：", ie);
                        Thread.currentThread().interrupt();
                        return resultJson;
                    }
                }
                i++;
            }
        }
        if (resultJson != null && resultJson.getBoolean("success")) {
            // 申请信息编辑保存
            ApprovalInfo info = resultJson.getObject("processInstance", ApprovalInfo.class);
            if (null == info) {
                info = new ApprovalInfo();
            }
            String processInstanceId = info.getProcessinstanceid();
            BeanUtils.copyProperties(param, info);
            info.setProcessinstanceid(processInstanceId);
            saveOrUpdateApprovalInfo(info);
        }
        return resultJson;
    }

    @Override
    public JSONObject startAndSendNextStep(WorkFlowParams param, String serverUrl) {
        // 流程提交
        JSONObject p = new JSONObject();
        String result = null;
        JSONObject resultJson = null;
        //业务系统标识，【系统管理-业务系统设置】中定义的id
        p.put("sis_id", sysId);
        //【流程定义-业务流程关联】中的定义的【模块标识】
        p.put("censor_type", moduleId);
        //区域id,若地区差别时，该地区启动该地区指定的流程
        p.put("type", param.getAreaId());
        //url参数，拼接在业务流程管理url后
        String recordid = "&approvalId=" + param.getApprovalId();
        p.put("record_id", recordid);
        p.put("process_title", "关于\"" + param.getModuleName() + param.getOperationName() + "\"${process_title}[${datetime}]");
        //登录用户id
        p.put("user_id", param.getOperatorId());
        //登录用户名称
        p.put("user_name", param.getOperatorName());
        //登录用户机构id
        p.put("organ_id", param.getOperatorOrganId());
        //登录用户所在区域id
        p.put("area_id", param.getAreaId());
        String webServiceUrl = serverUrl + "/SynWF/wf/start";
        logger.info(">>>>>>>>>>>>>流程发起，webServiceUrl:" + webServiceUrl);
        int i = 0;
        try {
            result = restTemplate.postForObject(webServiceUrl, p, String.class);
            System.out.println(">>>>>>>>>>>>>流程发起，result:" + result);
            resultJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            logger.error("请求接口失败：",e);
            return resultJson;
        }
        if (resultJson == null || resultJson.isEmpty()) {
            //只要超过了3次，或者有流程实例不为空了就退出循环
            while (i < 3) {
                if (resultJson == null || resultJson.isEmpty()) {
                    try {
                        result = restTemplate.postForObject(webServiceUrl, p.toString(), String.class);
                        System.out.println(">>>>>>>>>>>>>流程发起" + i + "，result:" + result);
                        resultJson = JSONObject.parseObject(result);
                    } catch (Exception e) {
                        logger.error("请求接口失败：",e);
                        return resultJson;
                    }
                    //延迟1s再继续执行
                    try {
                        if (resultJson == null || resultJson.isEmpty()) {
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        logger.error("请求接口失败：",e);
                        Thread.currentThread().interrupt();
                        return resultJson;
                    }
                }
                i++;
            }
        }
        if (resultJson != null && resultJson.getBoolean("success")) {
            // 申请信息编辑保存
            ApprovalInfo info = resultJson.getObject("processInstance", ApprovalInfo.class);
            if (null == info) {
                info = new ApprovalInfo();
            }
            BeanUtils.copyProperties(param, info);
            saveOrUpdateApprovalInfo(info);
            JSONObject a = new JSONObject();
            a.put("taskid", info.getTaskId());
            a.put("user_id", param.getOperatorId());
            a.put("user_name", param.getOperatorName());
            a.put("organ_id", param.getOperatorOrganId());
            a.put("area_id", param.getAreaId());
            a.put("defaultCandidate", "");
            a.put("defaultOrgan", "");
            String nextActStr = restTemplate.postForObject(serverUrl + "/SynWF/wf/getNextAct", a, String.class);
            JSONObject nextAct = JSONObject.parseObject(nextActStr);
            if (nextAct != null && nextAct.getBoolean("success")) {
                if (nextAct.getJSONArray("sequenceFlow").size() > 0) {
                    JSONObject candidateObject = (JSONObject) nextAct.getJSONArray("sequenceFlow").get(0);
                    JSONArray candidatArray = candidateObject.getJSONArray("candidate");
                    StringBuffer sbf = new StringBuffer();
                    if (candidatArray.size() > 0) {
                        for (int j = 0; j < candidatArray.size(); j++) {
                            sbf.append(candidatArray.getJSONObject(j).getString("manid")).append(Constant.CHARACTER_COMMA);
                        }
                    }
                    JSONObject s = new JSONObject();
                    s.put("taskId", nextAct.getString("taskid"));
                    s.put("processInstanceId", nextAct.getString("processInstanceId"));
                    s.put("candidate", sbf.substring(0, sbf.length() - 1));
                    s.put("comment", "同意");
                    s.put("user_id", nextAct.getString("user_id"));
                    s.put("conditionexpression", candidateObject.getString("conditionexpression"));
                    String nextStepStr = restTemplate.postForObject(serverUrl + "/SynWF/wf/sendNextStep", s, String.class);
                    return JSONObject.parseObject(nextStepStr);
                }
            } else {
                return nextAct;
            }
        }
        return resultJson;
    }

    @Override
    public ServerResponse<String> endProcess(String jsonStr) {
        JSONObject obj = JSONObject.parseObject(jsonStr);
        String status = obj.getString("status");
        String approvalId = obj.getString("approval_id");
        String nextactorid = obj.getString("nextactorid");
        // 手动流程终止
        if (StringUtils.isBlank(status)) {
            if (StringUtils.isNotBlank(obj.getString("businesskey")) && obj.getString("businesskey").split("approvalId=").length > 1) {
                approvalId = obj.getString("businesskey").split("approvalId=")[1];
                status = Constant.STRING_FOUR;
            }
        }
        ApprovalInfo info = processDao.findApprovalById(approvalId);
        if (null == info) {
            return ServerResponse.asErrorResponse("未找到工作流");
        }
        // 更新记录
        processDao.updateApprovalStatus(approvalId, status);
        // 是否调用回调方法
        boolean isCallBack = StringUtils.equals(Constant.STRING_THREE, status) || (StringUtils.equals(Constant.STRING_FOUR, status) && StringUtils.equals(SysCodeEnum.register.getCode(), info.getModuleId()));
        if (isCallBack) {
            // 回调业务系统方法
            logger.info(">>>>>>>>>>>>>callbackData: " + info.getCallbackData());
            JSONObject requestObj = JSONObject.parseObject(info.getCallbackData());
            requestObj.put("status", status);
            requestObj.put("approvalId", approvalId);
            if(StringUtils.equals(SysCodeEnum.dataDefinition.getCode(), info.getModuleId())){
                requestObj.put("userId", obj.getString("userId"));
            }
            JSONObject result = restTemplate.postForObject(info.getCallbackUrl(), requestObj, JSONObject.class);
            if (null != result) {
                if (result.getInteger("status") == ProcessResponseCode.SUCCESS.getCode()) {
                    logger.info("回调业务系统方法成功");
                } else {
                    String message = result.getString("message");
                    logger.info(message);
                    // 失败信息保存
                    processDao.saveExecuteResult(message, approvalId);
                }
            } else {
                logger.info("回调业务系统方法失败");
            }
        }
        return ServerResponse.asSucessResponse(approvalId);
    }

    @Override
    public JSONObject getStandardPageByApprovalInfo(ApprovalInfo info, String serverUrl, String standardManageUrl) {
        saveOrUpdateApprovalInfo(info);
        return restTemplate.getForObject(standardManageUrl + "/dataStandardManager/getStandardPageByApprovalInfo?executeMethod=approvalInfo&dataId=" + info.getApprovalId() + "&moduleId=" + info.getModuleId() + "&turnIndex=" + serverUrl + "/datagovernance/process/myAppPortlet", JSONObject.class);
    }

    @Override
    public List<ApprovalInfo> queryApprovalInfoForModule(ApprovalInfo info) {
        List<ApprovalInfo> list = processDao.queryApprovalInfoForModule(info);
        return list;
    }

    @Override
    public List<String> getOperationTypeData() {
        List<String> list = processDao.getOperationTypeData();
        return list;
    }

    @Override
    public Map<String, Object> getDoingAffairList(String queryString, String url) {
        Map<String, Object> map = new HashMap<>();
        // 查询条件
        JSONObject params = JSONObject.parseObject(queryString);
        params.put("sys_id_", sysId);
        params.put("censor_", moduleId);
        params.put("rows", Integer.MAX_VALUE);
        params.put("page", 1);
        // 列表查询
        String result = restTemplate.postForObject(url + "/SynWF/wf/getMyAppList", params, String.class);
        logger.info("/SynWF/wf/getMyAppList在办事务列表返回:" + result);
        JSONObject resultJson = JSONObject.parseObject(result);
        JSONArray rows = resultJson.getJSONArray("rows");
        List<AffairResponseInfo> dataList = JSONObject.parseArray(rows.toJSONString(), AffairResponseInfo.class);
        // 操作类型
        String operationType = params.getString("operationType");
        if (StringUtils.isNotBlank(operationType)) {
            dataList = dataList.stream().filter(d -> d.getOperationType().indexOf(operationType) != -1).collect(Collectors.toList());
        }
        // 当前环节
        Map<String, List<AffairResponseInfo>> nameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getName_())).collect(Collectors.groupingBy(AffairResponseInfo::getName_));
        Set<String> nameSet = nameMap.keySet();
        map.put("nameSet", nameSet);
        // 发起人
        Map<String, List<AffairResponseInfo>> startUserNameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getStartusername())).collect(Collectors.groupingBy(AffairResponseInfo::getStartusername));
        Set<String> startUserNameSet = startUserNameMap.keySet();
        map.put("startUserNameSet", startUserNameSet);
        // 发起部门
        Map<String, List<AffairResponseInfo>> startOrganNameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getStart_organ_name())).collect(Collectors.groupingBy(AffairResponseInfo::getStart_organ_name));
        Set<String> startOrganNameSet = startOrganNameMap.keySet();
        map.put("startOrganNameSet", startOrganNameSet);
        // 过滤
        String name = params.getString("name");
        if (StringUtils.isNotBlank(name)) {
            List<String> names = Arrays.asList(name.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> names.contains(d.getName_())).collect(Collectors.toList());
        }
        String startUserName = params.getString("startUserName");
        if (StringUtils.isNotBlank(startUserName)) {
            List<String> startUserNames = Arrays.asList(startUserName.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> startUserNames.contains(d.getStartusername())).collect(Collectors.toList());
        }
        String startOrganName = params.getString("startOrganName");
        if (StringUtils.isNotBlank(startOrganName)) {
            List<String> startOrganNames = Arrays.asList(startOrganName.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> startOrganNames.contains(d.getStart_organ_name())).collect(Collectors.toList());
        }
        String status = params.getString("status");
        if (StringUtils.isNotBlank(status)) {
            List<String> statuses = Arrays.asList(status.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> statuses.contains(d.getStatus())).collect(Collectors.toList());
        }
        // 结果集
        map.put("rows", dataList);
        return map;
    }

    @Override
    public Map<String, Object> getToDoAffairList(String queryString, String url) {
        Map<String, Object> map = new HashMap<>();
        // 查询条件
        JSONObject params = JSONObject.parseObject(queryString);
        params.put("sys_id_", sysId);
        params.put("censor_", moduleId);
        params.put("rows", Integer.MAX_VALUE);
        params.put("page", 1);
        // 列表查询
        String result = restTemplate.postForObject(url + "/SynWF/wf/getWaitingListByProcess", params, String.class);
        logger.info("/SynWF/wf/getWaitingListByProcess待办事务列表返回:" + result);
        JSONObject resultJson = JSONObject.parseObject(result);
        JSONArray rows = resultJson.getJSONArray("rows");
        List<AffairResponseInfo> dataList = JSONObject.parseArray(rows.toJSONString(), AffairResponseInfo.class);
        // 操作类型
        String operationType = params.getString("operationType");
        if (StringUtils.isNotBlank(operationType)) {
            dataList = dataList.stream().filter(d -> d.getOperationType().indexOf(operationType) != -1).collect(Collectors.toList());
        }
        // 当前环节
        Map<String, List<AffairResponseInfo>> nameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getName_())).collect(Collectors.groupingBy(AffairResponseInfo::getName_));
        Set<String> nameSet = nameMap.keySet();
        map.put("nameSet", nameSet);
        // 发起人
        Map<String, List<AffairResponseInfo>> startUserNameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getStartusername())).collect(Collectors.groupingBy(AffairResponseInfo::getStartusername));
        Set<String> startUserNameSet = startUserNameMap.keySet();
        map.put("startUserNameSet", startUserNameSet);
        // 发起部门
        Map<String, List<AffairResponseInfo>> startOrganNameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getStart_organ_name())).collect(Collectors.groupingBy(AffairResponseInfo::getStart_organ_name));
        Set<String> startOrganNameSet = startOrganNameMap.keySet();
        map.put("startOrganNameSet", startOrganNameSet);
        // 过滤
        String name = params.getString("name");
        if (StringUtils.isNotBlank(name)) {
            List<String> names = Arrays.asList(name.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> names.contains(d.getName_())).collect(Collectors.toList());
        }
        String startUserName = params.getString("startUserName");
        if (StringUtils.isNotBlank(startUserName)) {
            List<String> startUserNames = Arrays.asList(startUserName.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> startUserNames.contains(d.getStartusername())).collect(Collectors.toList());
        }
        String startOrganName = params.getString("startOrganName");
        if (StringUtils.isNotBlank(startOrganName)) {
            List<String> startOrganNames = Arrays.asList(startOrganName.split(Constant.CHARACTER_COMMA));
            dataList = dataList.stream().filter(d -> startOrganNames.contains(d.getStart_organ_name())).collect(Collectors.toList());
        }
        String status = params.getString("status");
        if (StringUtils.isNotBlank(status)) {
            List<String> statuses = Arrays.asList(status.split(Constant.CHARACTER_COMMA));
            if (statuses.size() == 1) {
                // 挂起
                Predicate<AffairResponseInfo> upFilter = (d) -> (StringUtils.equals(Constant.STRING_TWO, d.getSuspension_state_()));
                // 待办
                Predicate<AffairResponseInfo> doneFilter = (d) -> (!StringUtils.equals(Constant.STRING_TWO, d.getSuspension_state_()));
                dataList = dataList.stream().filter(statuses.indexOf(Constant.STRING_TWO) != -1 ? upFilter : doneFilter).collect(Collectors.toList());
            }
        }
        // 结果集
        map.put("rows", dataList);
        return map;
    }

    @Override
    public Map<String, Object> getDoneAffairList(String queryString, String url) {
        Map<String, Object> map = new HashMap<>();
        // 查询条件
        JSONObject params = JSONObject.parseObject(queryString);
        params.put("sys_id_", sysId);
        params.put("censor_", moduleId);
        params.put("rows", Integer.MAX_VALUE);
        params.put("page", 1);
        // 列表查询
        String result = restTemplate.postForObject(url + "/SynWF/wf/getAffairList", params, String.class);
        logger.info("/SynWF/wf/getAffairList已办事务列表返回:" + result);
        JSONObject resultJson = JSONObject.parseObject(result);
        JSONArray rows = resultJson.getJSONArray("rows");
        List<AffairResponseInfo> dataList = JSONObject.parseArray(rows.toJSONString(), AffairResponseInfo.class);
        if (CollectionUtils.isNotEmpty(dataList)) {
            // 操作类型
            String operationType = params.getString("operationType");
            if (StringUtils.isNotBlank(operationType)) {
                dataList = dataList.stream().filter(d -> d.getOperationType().indexOf(operationType) != -1).collect(Collectors.toList());
            }
            // 回调执行情况
            Map<String, ApprovalInfo> executeInfoMap = processDao.getExecuteErrorInfo();
            // 办结事务procinst_status=1
            dataList.stream().filter(d -> StringUtils.equals(Constant.STRING_ONE, d.getProcinst_status())).collect(Collectors.toList()).forEach(item -> {
                item.setExecuteStatus(executeInfoMap.keySet().contains(item.getProc_inst_id_()) ? Constant.EXECUTE_STATUS_FAIL : Constant.EXECUTE_STATUS_SUCCESS);
                item.setExecuteMessage(null == executeInfoMap.get(item.getProc_inst_id_()) ? null : executeInfoMap.get(item.getProc_inst_id_()).getExecuteResult());
            });
            // 当前环节
            Map<String, List<AffairResponseInfo>> nameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getName_())).collect(Collectors.groupingBy(AffairResponseInfo::getName_));
            Set<String> nameSet = nameMap.keySet();
            map.put("nameSet", nameSet);
            // 发起人
            Map<String, List<AffairResponseInfo>> startUserNameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getStartusername())).collect(Collectors.groupingBy(AffairResponseInfo::getStartusername));
            Set<String> startUserNameSet = startUserNameMap.keySet();
            map.put("startUserNameSet", startUserNameSet);
            // 发起部门
            Map<String, List<AffairResponseInfo>> startOrganNameMap = dataList.stream().filter(d -> StringUtils.isNotBlank(d.getStart_organ_name())).collect(Collectors.groupingBy(AffairResponseInfo::getStart_organ_name));
            Set<String> startOrganNameSet = startOrganNameMap.keySet();
            map.put("startOrganNameSet", startOrganNameSet);
            // 过滤
            String name = params.getString("name");
            if (StringUtils.isNotBlank(name)) {
                List<String> names = Arrays.asList(name.split(Constant.CHARACTER_COMMA));
                dataList = dataList.stream().filter(d -> names.contains(d.getName_())).collect(Collectors.toList());
            }
            String startUserName = params.getString("startUserName");
            if (StringUtils.isNotBlank(startUserName)) {
                List<String> startUserNames = Arrays.asList(startUserName.split(Constant.CHARACTER_COMMA));
                dataList = dataList.stream().filter(d -> startUserNames.contains(d.getStartusername())).collect(Collectors.toList());
            }
            String startOrganName = params.getString("startOrganName");
            if (StringUtils.isNotBlank(startOrganName)) {
                List<String> startOrganNames = Arrays.asList(startOrganName.split(Constant.CHARACTER_COMMA));
                dataList = dataList.stream().filter(d -> startOrganNames.contains(d.getStart_organ_name())).collect(Collectors.toList());
            }
            String status = params.getString("status");
            if (StringUtils.isNotBlank(status)) {
                List<String> statuses = Arrays.asList(status.split(Constant.CHARACTER_COMMA));
                if (statuses.size() == 1) {
                    // 挂起
                    Predicate<AffairResponseInfo> upFilter = (d) -> (StringUtils.equals(Constant.STRING_TWO, d.getSuspension_state_()));
                    // 已办
                    Predicate<AffairResponseInfo> doneFilter = (d) -> (!StringUtils.equals(Constant.STRING_TWO, d.getSuspension_state_()));
                    dataList = dataList.stream().filter(statuses.indexOf(Constant.STRING_TWO) != -1 ? upFilter : doneFilter).collect(Collectors.toList());
                }
            }
            // 执行结果
            String executeStatus = params.getString("executeStatus");
            if (StringUtils.isNotBlank(executeStatus)) {
                List<String> executeStatuses = Arrays.asList(executeStatus.split(Constant.CHARACTER_COMMA));
                dataList = dataList.stream().filter(d -> executeStatuses.contains(d.getExecuteStatus())).collect(Collectors.toList());
            }
        }
        // 结果集
        map.put("rows", dataList);
        return map;
    }

    @Override
    public JSONObject callbackAffair(String paramsString, String url) {
        JSONObject params = JSONObject.parseObject(paramsString);
        String resultString = restTemplate.postForObject(url + "/SynWF/wf/callback", params, String.class);
        JSONObject result = JSONObject.parseObject(resultString);
        return result;
    }

    @Override
    public JSONObject endAffair(String paramsString, String url) {
        JSONObject params = JSONObject.parseObject(paramsString);
        String resultString = restTemplate.postForObject(url + "/SynWF/wf/end", params, String.class);
        JSONObject result = JSONObject.parseObject(resultString);
        return result;
    }
}