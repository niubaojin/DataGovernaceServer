package com.synway.datarelation.service.monitor.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datarelation.constant.UriConstant;
import com.synway.datarelation.dao.monitor.NormalInstanceDetailDao;
import com.synway.datarelation.service.workflow.impl.DataWorkV3Service;
import com.synway.datarelation.constant.PrgType;
import com.synway.datarelation.pojo.monitor.detail.NormalTaskInfo;
import com.synway.datarelation.pojo.monitor.detail.TaskNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.detail.TaskReportPageReturn;
import com.synway.datarelation.pojo.monitor.table.InOutTable;
import com.synway.datarelation.pojo.datawork.v3.TaskRunQueryParameters;
import com.synway.datarelation.constant.TaskStatus;
import com.synway.datarelation.service.monitor.WorkflowDetailService;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/16 11:09
 */
@Service
public class WorkflowDetailServiceImpl implements WorkflowDetailService {
    private static Logger logger = LoggerFactory.getLogger(WorkflowDetailServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NormalInstanceDetailDao detailDao;

    @Override
    public TaskReportPageReturn getTaskInfo(TaskNormalReportPageParams queryParams) {

        TaskReportPageReturn taskReportPageReturn = new TaskReportPageReturn();
        Page page = PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());
        String sort = "";
        if (StringUtils.isNotEmpty(queryParams.getSortName()) && StringUtils.isNotEmpty(queryParams.getSortBy())) {
            if ("DESC".equalsIgnoreCase(queryParams.getSortBy().toUpperCase())){
                sort = queryParams.getSortName() + " " + queryParams.getSortBy() + " nulls last,";
            }else{
                sort = queryParams.getSortName() + " " + queryParams.getSortBy() + " nulls first,";
            }
        }
        page.setOrderBy(sort+ " case when status = '5' then 1 " +
                "when status = '1' then 2 " +
                "when status = '4' then 3 " +
                "when status = '2' then 4 " +
                "when status = '3' then 4 " +
                "when status = '6' then 5 end");
//        String requestUrl = "http://DATARESOURCE/DataResource/outputProjectNamesByInterface";
        JSONObject returnObj =
                restTemplate.getForObject(UriConstant.RESOURCE_PROJECT_URI, JSONObject.class);
        if(returnObj!=null){
            JSONObject jsonObject = returnObj.getJSONObject("data");
            List<String> odpsProjectList = JSONArray.parseArray(jsonObject.getString("odps"), String.class);
            List<String> filterdList = queryParams.getProjectNameFilterList();
            if(filterdList==null||filterdList.size()==0) {
                queryParams.setProjectNameFilterList(odpsProjectList);
            }
        }
        List<NormalTaskInfo> lists = detailDao.getNormalTask(queryParams);
        lists.forEach(item -> {
            if (item.getStatus() != null) {
                //工作流状态即为这6种，如果不是，也需修改代码，这里不再验证状态是否有效
                item.setStatus(TaskStatus.getValue(Integer.valueOf(item.getStatus())));
            }
            if (item.getPrgType() != null) {
                item.setPrgType(PrgType.getValue(Integer.valueOf(item.getPrgType())));
            }
        });
        PageInfo<NormalTaskInfo> pageInfo = new PageInfo<>(lists);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        logger.info("监控工作流查询到的数据为：" + JSONObject.toJSONString(map));
        taskReportPageReturn.setPageInfoMap(map);
        List<Map<String, String>> filterList = detailDao.getNormalTaskFilterList(queryParams);
        List<TaskReportPageReturn.FilterObject> projectNameList = new ArrayList<>();
        List<TaskReportPageReturn.FilterObject> statusList = new ArrayList<>();
        List<TaskReportPageReturn.FilterObject> bizNameFilterList = new ArrayList<>();
        List<TaskReportPageReturn.FilterObject> prgTypeFilterList = new ArrayList<>();
        for (Map<String, String> element : filterList) {
            TaskReportPageReturn.FilterObject filterObject = new TaskReportPageReturn.FilterObject();
            String text = element.getOrDefault("TYPEVALUE", null);
            if (StringUtils.isNotEmpty(text)) {
                filterObject.setText(text);
                filterObject.setValue(text);
            } else {
                continue;
            }
            switch (element.getOrDefault("TYPE", "")) {
                case "status":
                    filterObject.setText(TaskStatus.getValue(Integer.valueOf(text)));
                    statusList.add(filterObject);
                    break;
                case "projectName":
                    projectNameList.add(filterObject);
                    break;
                case "businessName":
                    bizNameFilterList.add(filterObject);
                    break;
                case "prgType":
                    filterObject.setText(PrgType.getValue(Integer.valueOf(text)));
                    prgTypeFilterList.add(filterObject);
                    break;
                default:
                    logger.error(JSONObject.toJSONString(element) + ":没有对应的switch类型");
            }
        }
        taskReportPageReturn.setStatusShowList(statusList);
        taskReportPageReturn.setProjectNameShowList(projectNameList);
        taskReportPageReturn.setBusinessShowList(bizNameFilterList);
        taskReportPageReturn.setPrgTypeShowList(prgTypeFilterList);
        logger.info("查询到的数据为：" + JSONObject.toJSONString(taskReportPageReturn));
        return taskReportPageReturn;
    }


    @Override
    public String getTaskLog(String taskId) {
        DataWorkV3Service dataWorkV3ClientService = new DataWorkV3Service();
        TaskRunQueryParameters taskRunQueryParameters = new TaskRunQueryParameters();
        taskRunQueryParameters.setTaskId(Long.valueOf(taskId));
        String resultStr = "";
        try {
            int prgType = detailDao.getPrgTypeByTaskId(taskId);
            if (prgType == 23) {
                resultStr = dataWorkV3ClientService.getTaskRunLog(taskRunQueryParameters, false);
            } else {
                resultStr = dataWorkV3ClientService.getNormalTaskRunLog(taskRunQueryParameters, false);
            }
            logger.info("taskId为{}的日志为{}",taskId,resultStr);
        } catch (Exception e) {
            logger.error("查询失败："+ ExceptionUtil.getExceptionTrace(e));
        }
        return resultStr;
    }


    @Override
    public List<InOutTable> getTaskInOutRecord(String taskId) {
        List<InOutTable> record = null;
        try{
            record = detailDao.getTaskInOutRecord(taskId);
        }catch (Exception e) {
            logger.error("查询失败："+ ExceptionUtil.getExceptionTrace(e));
        }
        return record;
    }
}
