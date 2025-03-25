package com.synway.datarelation.service.monitor.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datarelation.constant.UriConstant;
import com.synway.datarelation.dao.monitor.NormaInstancePageDao;
import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageReturn;
import com.synway.datarelation.pojo.monitor.page.NormalBusinessInfo;
import com.synway.datarelation.service.monitor.WorkflowMonitorService;
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
 * @date 2020/12/14 16:57
 */
@Service
public class WorkflowMonitorServiceImpl implements WorkflowMonitorService {
    private static Logger logger = LoggerFactory.getLogger(WorkflowMonitorServiceImpl.class);

    @Autowired
    private NormaInstancePageDao normaInstancePageDao;


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public BusinessNormalReportPageReturn getBusinessNormalTaskReport(BusinessNormalReportPageParams queryParams) {
        BusinessNormalReportPageReturn dataNormalReportPageReturn = new BusinessNormalReportPageReturn();
        Page page=  PageHelper.startPage(queryParams.getPageNum(),queryParams.getPageSize());
        String sort = "";
        //排序注入
        // TODO 这里排序会有问题 只支持oracle数据库
        if(StringUtils.isNotEmpty(queryParams.getSortName())&&StringUtils.isNotEmpty(queryParams.getSortBy())){
            String sortName = queryParams.getSortName();
            if ( "runningTime".equals(sortName) || "cpuConsumption".equals(sortName) || "memoryConsumption".equals(sortName) ){
                sortName = "to_number(" + sortName + ")";
            }
            sort = sortName + " " + queryParams.getSortBy();

            if ("DESC".equals(queryParams.getSortBy().toUpperCase())){
                sort += "  nulls last,";
            }else {
                sort += "  nulls first,";
            }

//            page.setOrderBy(queryParams.getSortName()+" "+queryParams.getSortBy());
        }
        page.setOrderBy(sort+ " case when status = '运行失败' then 1 " +
                "when status = '未运行' then 2 " +
                "when status = '运行中' then 3 " +
                "when status = '等待时间' then 4 " +
                "when status = '等待资源' then 5 " +
                "when status = '运行成功' then 6 end");

        //根据仓库接口过滤项目名
        List<String> odpsProjectList = null;
        try{
//            String requestUrl = "http://DATARESOURCE/DataResource/outputProjectNamesByInterface";
            JSONObject returnObj =
                    restTemplate.getForObject(UriConstant.RESOURCE_PROJECT_URI, JSONObject.class);
            if(returnObj!=null){
                JSONObject jsonObject = returnObj.getJSONObject("data");
                odpsProjectList = JSONArray.parseArray(jsonObject.getString("odps"), String.class);
                List<String> filterdList = queryParams.getProjectNameFilterList();
                if(filterdList==null||filterdList.size()==0) {
                    queryParams.setProjectNameFilterList(odpsProjectList);
                }
            }
        } catch (Exception e) {
            logger.error("仓库过滤项目名接口报错");
        }
        //查找数据
        List<NormalBusinessInfo> lists = normaInstancePageDao.getNormalBusiness(queryParams);
        PageInfo<NormalBusinessInfo> pageInfo = new PageInfo<>(lists);
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        logger.info("监控工作流查询到的数据为："+ JSONObject.toJSONString(map));
        dataNormalReportPageReturn.setPageInfoMap(map);
        List<Map<String,String>> filterList = normaInstancePageDao.getNormalBusinessFilterList(queryParams);
        List<BusinessNormalReportPageReturn.FilterObject> projectNameList = new ArrayList<>();
        List<BusinessNormalReportPageReturn.FilterObject> statusList = new ArrayList<>();
        for(Map<String,String> element:filterList){
            BusinessNormalReportPageReturn.FilterObject filterObject = new BusinessNormalReportPageReturn.FilterObject();
            String text = element.getOrDefault("TYPEVALUE",null);
            if(StringUtils.isNotEmpty(text)){
                filterObject.setText(text);
                filterObject.setValue(text);
            }else{
                continue;
            }
            switch (element.getOrDefault("TYPE","")){
                case "status":
                    statusList.add(filterObject);
                    break;
                case "projectName":
                    boolean flag = true;
                    if(odpsProjectList!=null){
                        flag = odpsProjectList.contains(filterObject.getText());
                    }
                    if(flag) {
                        projectNameList.add(filterObject);
                    }
                    break;
                default:
                    logger.error(JSONObject.toJSONString(element)+":没有对应的switch类型");
            }
        }
        dataNormalReportPageReturn.setStatusShowList(statusList);
        dataNormalReportPageReturn.setProjectNameShowList(projectNameList);
        logger.info("查询到的数据为："+JSONObject.toJSONString(dataNormalReportPageReturn));
        return dataNormalReportPageReturn;
    }
}
