package com.synway.property.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.property.common.UrlConstants;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.dao.LifeCycleDao;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.DetailedTableByClassify;
import com.synway.property.pojo.LoginUser;
import com.synway.property.pojo.lifecycle.*;
import com.synway.property.service.LifeCycleService;
import com.synway.property.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author majia
 * @version 1.0
 * @date 2021/2/19 9:49
 */
@Service
public class LifeCycleServiceImpl implements LifeCycleService {

    private static Logger logger = LoggerFactory.getLogger(LifeCycleService.class);

    @Autowired
    private LifeCycleDao lifeCycleDao;

    @Autowired
    DataStorageMonitorDao dataStorageMonitorDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    /**
     * 生命周期页面
     * TODO 已建分区数量，最后访问时间未确定
     *
     * @param queryParams
     * @return
     */
    @Override
    public LifeCyclePageReturn getLifeCycleInfo(LifeCyclePageParams queryParams) {
        // 统计资产表今日数据量如果为0，则获取昨天数据
        int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
        int daysAgo = todayAssetsCount<100?1:0;
        LifeCyclePageReturn lifeCyclePageReturn = new LifeCyclePageReturn();

        String orderBy = "";
        if (StringUtils.isNotEmpty(queryParams.getSortName()) && StringUtils.isNotEmpty(queryParams.getSortBy())) {
            //默认有表名的排前面
            String sortName = queryParams.getSortName();
            if ("storageSize".equals(sortName) || "valDensity".equals(sortName) ||
                    "useCountOfMonth".equals(sortName) || "partitionNum".equals(sortName)) {
                sortName = "to_number(" + sortName + ")";
            }
            orderBy = sortName + " " + queryParams.getSortBy();
        }
        Page page = PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());
        page.setUnsafeOrderBy(orderBy);
        List<LifeCycleInfo> lists = lifeCycleDao.getLifeCycleInfo(queryParams,daysAgo);
        PageInfo<LifeCycleInfo> pageInfo = new PageInfo<>(lists);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
//        logger.info("生命周期查询到的数据为：" + JSONObject.toJSONString(map));
        lifeCyclePageReturn.setPageInfoMap(map);
//        logger.info("查询到的数据为：" + JSONObject.toJSONString(lifeCyclePageReturn));
        return lifeCyclePageReturn;
    }

    /**
     * 获取过滤项
     *
     * @param queryParams
     * @return
     */
    @Override
    public LifeCyclePageReturn getLifeCycleFilters(LifeCyclePageParams queryParams) {
        LifeCyclePageReturn lifeCyclePageReturn = new LifeCyclePageReturn();
        // 统计资产表今日数据量如果为0，则获取昨天数据
        int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
        int daysAgo = todayAssetsCount<100 ? 1:0;
        List<Map<String, String>> filterList = lifeCycleDao.getLifeCycleInfoFilter(queryParams,daysAgo);
        List<LifeCyclePageReturn.FilterObject> organizationClassifyList = new ArrayList<>();
        List<LifeCyclePageReturn.FilterObject> platformTypeList = new ArrayList<>();
        List<LifeCyclePageReturn.FilterObject> projectNameList = new ArrayList<>();
        List<LifeCyclePageReturn.FilterObject> updateTypeList = new ArrayList<>();
        List<LifeCyclePageReturn.FilterObject> partitionList = new ArrayList<>();
        String databaseType = environment.getProperty("database.type");
//        logger.info("数据库类型是：" + databaseType);
        for (Map<String, String> element : filterList) {
            LifeCyclePageReturn.FilterObject filterObject = new LifeCyclePageReturn.FilterObject();
            String text = element.getOrDefault("TYPEVALUE", null);
            if (StringUtils.isBlank(text)){
                text = element.getOrDefault("typeValue", null);
            }
//            logger.info("typeValue:" + text);
            if (StringUtils.isNotEmpty(text)) {
                filterObject.setText(text);
                filterObject.setValue(text);
            } else {
                continue;
            }
            switch (element.getOrDefault("TYPE", "")) {
                case "organizationClassify":
                    organizationClassifyList.add(filterObject);
                    break;
                case "platformType":
                    platformTypeList.add(filterObject);
                    break;
                case "projectName":
                    projectNameList.add(filterObject);
                    break;
                case "updateType":
                    updateTypeList.add(filterObject);
                    break;
                case "partition":
                    partitionList.add(filterObject);
                    break;
                default:
                    logger.error(JSONObject.toJSONString(element) + ":没有对应的switch类型");
            }
        }
        lifeCyclePageReturn.setOrganizationClassifyShowList(organizationClassifyList);
        lifeCyclePageReturn.setPlatformTypeShowList(platformTypeList);
        lifeCyclePageReturn.setProjectNameShowList(projectNameList);
        lifeCyclePageReturn.setUpdateTypeShowList(updateTypeList);
        lifeCyclePageReturn.setPartitionShowList(partitionList);
        return lifeCyclePageReturn;
    }

    @Override
    public Map getValDensity(ValDensityPageParam queryParams) {
        Map map = new HashMap<>();
        ValDensity oldValDensity = lifeCycleDao.getOldValDensity(queryParams);
        Map<String, String> classifyMap = lifeCycleDao.getOrganizationClassify(queryParams);
        queryParams.setPrimaryOrganizationCh(classifyMap.get("PRIMARY_ORGANIZATION_CH"));
        queryParams.setSecondaryOrganizationCh(classifyMap.get("SECONDARY_ORGANIZATION_CH"));
        ValDensity newValDensity = updateValDensity(oldValDensity, queryParams, false);
        boolean flag = false;
        if (oldValDensity == null ||
                newValDensity.getWorkflowUsed() > oldValDensity.getWorkflowUsed() ||
                newValDensity.getApplicationUsed() > oldValDensity.getApplicationUsed() ||
                newValDensity.getZhutikuUsed() > oldValDensity.getZhutikuUsed() ||
                newValDensity.getZiyuankuUsed() > oldValDensity.getZiyuankuUsed() ||
                newValDensity.getYaosukuUsed() > oldValDensity.getYaosukuUsed() ||
                newValDensity.getTagUsed() > oldValDensity.getTagUsed()) {
            flag = true;
        }
        map.put("updateStatus", flag);
        map.put("oldValDensity", oldValDensity);
        map.put("newValDensity", newValDensity);
        map.put("primaryOrganizationCh", classifyMap.get("PRIMARY_ORGANIZATION_CH"));
        map.put("secondaryOrganizationCh", classifyMap.get("SECONDARY_ORGANIZATION_CH"));
        return map;
    }

    private ValDensity updateValDensity(ValDensity oldValDensity, ValDensityPageParam queryParams, boolean updateFlag) {
        ValDensity density = new ValDensity();
        if (oldValDensity != null) {
            density.setTextHandle(oldValDensity.getTextHandle());
            density.setTwoHandle(oldValDensity.getTwoHandle());
            density.setUnstructedData(oldValDensity.getUnstructedData());
        }
        try {
            //请求参数
            JSONObject queryParam = new JSONObject();
            queryParam.put("platformType", queryParams.getPlatformType());
            queryParam.put("tableProject", queryParams.getTableProject());
            queryParam.put("tableNameEn", queryParams.getTableNameEn());

            //获取所有下游表，然后获取组织分类
            JSONObject targetTablesJson = restTemplate.postForObject(UrlConstants.DATARELATION_BASEURL + "/getTargetTables", queryParam, JSONObject.class);
            if (targetTablesJson != null && targetTablesJson.getBoolean("success")) {
                List<ValDensityPageParam> datas = targetTablesJson.getJSONArray("data").toJavaList(ValDensityPageParam.class);
                List<DetailedTableByClassify> tableByClassifies = new ArrayList<>();
                datas.stream().forEach(data ->{
                    DetailedTableByClassify table = new DetailedTableByClassify();
                    table.setTableProject(data.getTableProject());
                    table.setTableNameEn(data.getTableNameEn());
                    tableByClassifies.add(table);
                });
                //获取主题库，资源库，要素库分类
                if (tableByClassifies.size() > 0) {
                    List<Map> temp = lifeCycleDao.getClassifyNum(tableByClassifies);
                    for (Map map : temp) {
                        switch ((String) map.get("CLASSIFY")) {
                            case "主题库":
                                density.setZhutikuUsed(((BigDecimal) map.get("NUM")).intValue());
                                break;
                            case "资源库":
                                density.setZiyuankuUsed(((BigDecimal) map.get("NUM")).intValue());
                                break;
                            case "业务要素索引库":
                                density.setYaosukuUsed(((BigDecimal) map.get("NUM")).intValue());
                                break;
                            default:
                        }
                    }
                }
            }
            //获取被调用工作流和应用系统
            JSONObject impactStatisticJson = restTemplate.postForObject(UrlConstants.DATARELATION_BASEURL + "/getImpactStatistic", queryParam, JSONObject.class);
            if (impactStatisticJson != null && impactStatisticJson.getBoolean("success")) {
                ImpactAnalysisProperty property = impactStatisticJson.getJSONObject("data").toJavaObject(ImpactAnalysisProperty.class);
                if (property != null) {
                    density.setApplicationUsed(property.getAppCount());
                    density.setWorkflowUsed(property.getWorkFlowCount());
                }
            }

            //TODO 标签未确定
            double numerator = density.getTextHandle() + density.getTwoHandle() + density.getUnstructedData()
                    + density.getWorkflowUsed() + density.getApplicationUsed() + density.getZhutikuUsed() +
                    density.getZiyuankuUsed() + density.getYaosukuUsed() + density.getTagUsed();
            //数据信息价值,针对原始库、业务生产库有效，非这两种库的表不参与计算
            double part = 0;
            if ("原始库".equals(queryParams.getPrimaryOrganizationCh()) || "业务生产库".equals(queryParams.getSecondaryOrganizationCh())) {
                part = 1 + 1 + 1;
            }
            double denominator = part + (density.getWorkflowUsed() == 0 ? 1 : density.getWorkflowUsed()) +
                    (density.getApplicationUsed() == 0 ? 1 : density.getApplicationUsed()) +
                    (density.getZhutikuUsed() == 0 ? 1 : density.getZhutikuUsed()) +
                    (density.getZiyuankuUsed() == 0 ? 1 : density.getZiyuankuUsed()) +
                    (density.getYaosukuUsed() == 0 ? 1 : density.getYaosukuUsed()) +
                    (density.getTagUsed() == 0 ? 1 : density.getTagUsed());
            BigDecimal bg = BigDecimal.valueOf(numerator / denominator);
            double val = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            density.setValDensity(val);
            if(oldValDensity == null) {
                lifeCycleDao.insertValDensity(queryParams, density);
            } else if(updateFlag){
                lifeCycleDao.updateValDensity(queryParams, density);
            }
        } catch (Exception e) {
            logger.error("价值密度更新失败，调用血缘接口失败：\n", e);
        }
        return density;
    }

    @Override
    public void saveValDensity(ValDensityPageParam queryParams) {
        lifeCycleDao.updateValDensity(queryParams, queryParams.getValDensity());
    }

    @Override
    public void updateAllValDensity(boolean flag) {
        List<DetailedTableByClassify> tables = lifeCycleDao.getAssets();
        lifeCycleDao.changeValDensityStatus();
        tables.parallelStream().forEach(
                item -> {
                    ValDensityPageParam param = new ValDensityPageParam();
                    param.setTableNameEn(item.getTableNameEn());
                    param.setTableProject(item.getTableProject());
                    param.setPlatformType(item.getTableType());
                    param.setPrimaryOrganizationCh(item.getPrimaryOrganizationCh());
                    param.setSecondaryOrganizationCh(item.getSecondaryOrganizationCh());
                    ValDensity oldValDensity = lifeCycleDao.getOldValDensity(param);
                    updateValDensity(oldValDensity, param, flag);
                }
        );
    }

    @Override
    public List<Double> updateValDensity(LifeCyclePageParams queryParams) {
        List<LifeCycleInfo> infos = queryParams.getLifeCycleInfos();
        //需要按照顺序输出，所以直接顺序添加
        List<Future<ValDensity>> futures = new ArrayList<>();
        infos.forEach(
                item -> {
                    CompletableFuture<ValDensity> future = CompletableFuture.supplyAsync(() -> {
                        ValDensityPageParam param = new ValDensityPageParam();
                        param.setTableNameEn(item.getTableNameEn());
                        param.setTableProject(item.getProjectName());
                        param.setPlatformType(item.getPlatformType());
                        param.setPrimaryOrganizationCh(item.getOrganizationClassify());
                        param.setSecondaryOrganizationCh(item.getSecOrganizationClassify());
                        ValDensity oldValDensity = lifeCycleDao.getOldValDensity(param);
                        return updateValDensity(oldValDensity, param, true);
                    });
                    futures.add(future);
                }
        );
        List<Double> list = futures.parallelStream().map(doubleFuture -> {
            try {
                return doubleFuture.get().getValDensity();
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            return 0.0;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public void updateLifeCycleShowField(String lifeCycleShowField) {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        if (StringUtils.isBlank(lifeCycleShowField)){
            lifeCycleShowField = "notNull";
        }
        lifeCycleDao.updateLifeCycleShowField(lifeCycleShowField, authorizedUser.getUserName());
    }

    @Override
    public List<String> getLifeCycleShowField() {
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        List<String> listReturn = null;
        String result = lifeCycleDao.getLifeCycleShowField(loginUser.getUserName());
        if (StringUtils.isNotEmpty(result) || result != null){
            listReturn = Arrays.asList(result.split(","));
        }
        return listReturn;
    }
}
