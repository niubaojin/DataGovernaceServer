package com.synway.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.property.dao.DataProcessDao;
import com.synway.property.pojo.DataProcess.*;
import com.synway.property.service.DataProcessService;
import com.synway.property.util.DateUtil;
import com.synway.property.util.ExceptionUtil;
import com.synway.property.util.RetryUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


/**
 * @author 数据接入
 */
@Service
public class DataProcessServiceImpl implements DataProcessService {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessServiceImpl.class);

    @Autowired()
    private DataProcessDao dataProcessDao;

//    @Resource(name = "referenceConfig")
//    private OrganUserService referenceConfig;

    @Autowired()
    @Qualifier(value ="restTemplateBalanced")
    private RestTemplate restTemplateBalanced;

    private static ThreadPoolExecutor asyncExecutor = RetryUtil.createThreadPoolExecutor();


    @Override
    @Transactional
    public Boolean saveDataProcess(DataProcess dataProcess) throws Exception {
        Integer num = 1;
        Boolean flag = false;
        Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getModuleId().trim()),"【模块代码】不能为空");
        Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getIp().trim()),"【IP地址/操作设备地址】不能为空");
        Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getLogType().trim()),"【操作类型代码】不能为空");
        Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getDigest().trim()),"【操作行为的简要说明】不能为空");
        Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getTableNameEn())||StringUtils.isNotEmpty(dataProcess.getTableId()),
                "请求参数中【tableId和表英文名】至少有一个不为空");
//        Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getTableNameEn().trim()),"【表名】不能为空");
        if(dataProcess.getUserId() != null || dataProcess.getUserId() != 0){
            OrganUser organUser = getUserMesageByDubbo(dataProcess.getUserId());
            logger.info("从dubbo接口获取到数据为："+JSONObject.toJSONString(organUser));
            if(organUser != null && organUser.getSuccess() && organUser.getData().size() >0){
                dataProcess.setAreaId(organUser.getData().get(0).getOrgan().getSymbol12());
                dataProcess.setDept(organUser.getData().get(0).getOrgan().getOrganName());
                dataProcess.setOperator(organUser.getData().get(0).getUser().getName());
                dataProcess.setPoliceno(organUser.getData().get(0).getUser().getPoliceNo());
            }
        }
        dataProcess.setAreaId(StringUtils.isNotEmpty(dataProcess.getAreaId())?dataProcess.getAreaId():"  ");
        dataProcess.setDept(StringUtils.isNotEmpty(dataProcess.getDept())?dataProcess.getDept():"  ");
        dataProcess.setOperator(StringUtils.isNotEmpty(dataProcess.getOperator())?dataProcess.getOperator():"  ");
        dataProcess.setPoliceno(StringUtils.isNotEmpty(dataProcess.getPoliceno())?dataProcess.getPoliceno():"  ");
        dataProcess.setDataBaseType(StringUtils.isNotEmpty(dataProcess.getDataBaseType())?dataProcess.getDataBaseType():" ");
        if(StringUtils.isEmpty(dataProcess.getOperateTime())){
            dataProcess.setOperateTime(DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
        }
        do {
            logger.info("[数据历程]第"+num+"次需要保存的数据为：" + JSONObject.toJSONString(dataProcess));
            Validate.isTrue(StringUtils.isNotEmpty(dataProcess.getOperateTime()), "【操作时间】参数不能为空");
            Validate.isTrue(DateUtil.isValidDate(dataProcess.getOperateTime(), DateUtil.DEFAULT_PATTERN_DATETIME), "【操作时间】参数不符合格式要求，时间格式为YYYY-MM-DD HH:mm:ss");
            dataProcess.setId(new Random().nextInt());
            int insertNum = dataProcessDao.saveDataProcess(dataProcess);
            if (insertNum >= 1) {
                flag  = true;
            }
            num++;
        }while (!flag && num <=3);

        return flag;
    }

//    @Override
//    public ProcessPage searchDataProcess(DataProcessRequest request) throws Exception {
//        logger.info("[数据历程]的查询参数信息为："+JSONObject.toJSONString(request));
////        Validate.isTrue(StringUtils.isNotEmpty(request.getTableNameEn()),"请求参数中【表英文名】不能为空");
//        // 判断参数中查询时间的格式是否争取
//        if(StringUtils.isNotEmpty(request.getStartTime())){
//            Validate.isTrue(DateUtil.isValidDate(request.getStartTime(),DateUtil.DEFAULT_PATTERN_DATETIME),"请求参数中【查询的开始时间】不符合格式要求");
//        }
//        if(StringUtils.isNotEmpty(request.getEndTime())){
//            Validate.isTrue(DateUtil.isValidDate(request.getEndTime(),DateUtil.DEFAULT_PATTERN_DATETIME),"请求参数中【查询的结束时间】不符合格式要求");
//        }
//        Validate.isTrue(StringUtils.isNotEmpty(request.getTableNameEn())||StringUtils.isNotEmpty(request.getTableId()),
//                "请求参数中【tableId和表英文名】至少有一个不为空");
//        Validate.isTrue(StringUtils.isNotEmpty(request.getModuleId()),
//                "请求参数中【操作模块的代码值】不能为空");
//        Validate.isTrue(!(StringUtils.isNotEmpty(request.getTableNameEn())&& StringUtils.isEmpty(request.getDataBaseType()))
//                ,"如果表名不为空，则数据库类型也不能为空");
//        // 1: 其中 tableID 是目标id，需要从 数据血缘接口 获取到血缘 通过tableId/tableName 获取输入血缘的的输入id
//        List<DataProcessRequest> requestList = new ArrayList<>();
//        List<String> sourceIdBloodList = null;
//        try{
//            String httpUrl = "http://datarelation/datarelation/api/externalInterfce/getStandardBloodByName?type=1&searchName="
//                    +(StringUtils.isNotEmpty(request.getTableId())?request.getTableId():request.getTableNameEn());
//            String[] result = restTemplateBalanced.getForObject(httpUrl,String[].class);
//            if(result != null){
//                sourceIdBloodList = Arrays.asList(result);
//                for(String sourceId:result){
////                    DataProcessRequest requestNew = ObjectUtils.clone(request);
//                    DataProcessRequest requestNew = new DataProcessRequest();
//                    BeanUtils.copyProperties(requestNew,request);
//                    requestNew.setTableId(sourceId);
//                    requestNew.setTableNameEn("");
//                    requestList.add(requestNew);
//                }
//            }else{
//                logger.error("获取到的数据血缘原始数据报错");
//            }
//        }catch (Exception e){
//            logger.error("从数据血缘接口获取数据报错"+ExceptionUtil.getExceptionTrace(e));
//        }
//        requestList.add(request);
//
//        // 2: 标准血缘接口中可能存在数据少的情况，再查询 标准管理页面中的 数据来源 表，找到数据仓库中来源协议
//        //  通过这个 获取数据探查历程和数据接入历程  理论上应该查询 标准管理接口 目前直接查询表
//        // 根据tableid直接查询 数据探查那边的sourceId
//        try{
//            if(StringUtils.isNotBlank(request.getTableId())){
//                List<String> sourceIds = dataProcessDao.getInputSourceIdByTableId(request.getTableId());
//                if(!sourceIds.isEmpty()){
//                    for(String sourceId:sourceIds){
//                        if(sourceIdBloodList == null || !sourceIdBloodList.contains(sourceId.toLowerCase())){
////                            DataProcessRequest requestNew = ObjectUtils.clone(request);
//                            DataProcessRequest requestNew = new DataProcessRequest();
//                            BeanUtils.copyProperties(requestNew,request);
//                            requestNew.setTableId(sourceId);
//                            requestNew.setTableNameEn("");
//                            requestNew.setDataBaseType("");
//                            requestList.add(requestNew);
//                        }
//                    }
//                }
//            }
//        }catch (Exception e){
//            logger.error("根据tableid获取sourceid报错"+ExceptionUtil.getExceptionTrace(e));
//        }
//        logger.info("查询的最终参数为"+JSONObject.toJSONString(requestList));
//
//        // 从数据库中根据表英文名/数据库类型/tableId 找到对应的数据
//        List<ProcessPageShow>  dataProcessList = new ArrayList<>();
//        for(DataProcessRequest dataProcessRequest:requestList){
//            try{
//                dataProcessList.addAll(dataProcessDao.searchDataProcess(dataProcessRequest));
//            }catch (Exception e){
//                logger.error("查询数据历程的参数为["+JSONObject.toJSONString(dataProcessRequest)+"]数据报错"+ExceptionUtil.getExceptionTrace(e));
//            }
//        }
//
//        List<String> moduleIdList = Arrays.asList(request.getModuleId().toLowerCase().split(","));
//        // 获取 本周的数据历程的数据
//        // 需要根据时间来排序 从大到小
//        Date nowDate = new Date();
//        Map<String, List<ProcessPageShow>> map = dataProcessList.stream().filter( d ->{
//                    // 在数据建表模块和登记模块中，可能会存在根据tableid查询出 hbase hive 多种数据库类型的情况
//                    if(StringUtils.isNotBlank(request.getDataBaseType()) &&
//                            ("标准建表".equals(d.getAccessType())|| "标准登记".equals(d.getAccessType()))){
//                        if(!request.getDataBaseType().equalsIgnoreCase(d.getDataBaseType())) {
//                            return false;
//                        }
//                    }
//                    if(StringUtils.isNotEmpty(request.getModuleId())){
//                        if(moduleIdList.contains("all")){
//                            return true;
//                        }else if(moduleIdList.contains(d.getModuleId().toLowerCase())){
//                            return true;
//                        }else{
//                            return false;
//                        }
//                    }else{
//                        return true;
//                    }
//                }
//        ).sorted((x,y) ->{
//            try{
//                if(DateUtil.parseDate(x.getOperateTime(),DateUtil.DEFAULT_PATTERN_DATETIME)
//                        .before(DateUtil.parseDate(y.getOperateTime(),DateUtil.DEFAULT_PATTERN_DATETIME))){
//                    return 1;
//                }else{
//                    return -1;
//                }
//            }catch (Exception e){
//                return 0;
//            }
//        }).collect(Collectors.groupingBy( dataProcess-> {
//            try{
//                Date operateTimeDate = DateUtil.parseDate(dataProcess.getOperateTime(),DateUtil.DEFAULT_PATTERN_DATETIME);
//                if(operateTimeDate.after(DateUtil.addDay(nowDate,-7))){
//                    return "week";
//                }else if(operateTimeDate.before(DateUtil.addDay(nowDate,-7))&& operateTimeDate.after(DateUtil.addDay(nowDate,-14))){
//                    return "lastweek";
//                }else if(operateTimeDate.before(DateUtil.addDay(nowDate,-14))&& operateTimeDate.after(DateUtil.addMonth(nowDate,-1))){
//                    return "month";
//                }else{
//                    return "longago";
//                }
//            }catch (Exception e){
//                logger.error("解析数据报错"+ ExceptionUtil.getExceptionTrace(e));
//                return "longago";
//            }
//        }));
//        ProcessPage processPage = new ProcessPage();
//        BeanUtils.populate(processPage,map);
//        return processPage;
//    }

    @Override
    public List<ModuleIdSelect> getAllModuleId() throws Exception {
        logger.info("开始获取操作模块的信息");
        List<ModuleIdSelect> moduleIdSelectList = dataProcessDao.getAllModuleId();
        if(moduleIdSelectList == null){
            moduleIdSelectList = new ArrayList<>();
        }
        ModuleIdSelect moduleIdSelect = new ModuleIdSelect();
        moduleIdSelect.setCodeId("all");
        moduleIdSelect.setCodeValue("全部");
        moduleIdSelectList.add(moduleIdSelect);
        return moduleIdSelectList;
    }

    @Override
    public List<String> searchValuePrompt(DataProcessRequest request) throws Exception {
        Validate.isTrue(StringUtils.isNotEmpty(request.getTableNameEn())||StringUtils.isNotEmpty(request.getTableId()),
                "请求参数中【tableId和 表英文名】至少有一个不为空");
        Validate.isTrue(StringUtils.isNotEmpty(request.getModuleId()),
                "请求参数中【操作模块的代码值】不能为空");
        logger.info("搜索提示框的信息为【"+JSONObject.toJSONString(request)+"】");
        List<PromptValue> resultPrompt = dataProcessDao.searchValuePrompt(request);
        List<String> result = new ArrayList<>();
        List<String> moduleIdList = null;
        if(StringUtils.isNotEmpty(request.getModuleId())){
            moduleIdList = Arrays.asList(request.getModuleId().toLowerCase().split(","));
        }
        for(PromptValue promptValue :resultPrompt){
            if(moduleIdList != null && moduleIdList.contains("all")){
                result.add(promptValue.getTextVlaue());
            }else if(moduleIdList != null && moduleIdList.contains(promptValue.getModuleId().toLowerCase())){
                result.add(promptValue.getTextVlaue());
            }else if(moduleIdList == null){
                result.add(promptValue.getTextVlaue());
            }
        }
        result = result.stream().distinct().collect(Collectors.toList());
        return result;
    }

    @Override
    public OrganUser getUserMesageByDubbo(Integer userId) throws Exception {
        OrganUser organUser = null;
//        try {
//            organUser =  RetryUtil.asyncExecuteWithRetry(new Callable<OrganUser>() {
//                @Override
//                public OrganUser call() throws Exception {
//                    logger.info("从门户的dubbo接口调用数据的 userId:"+userId);
//                    OrganUser organUser1 = JSONObject.parseObject(referenceConfig.getUserOrganByUserId(userId),OrganUser.class);
//                    if(organUser1 != null && organUser1.getSuccess()){
//                        return organUser1;
//                    }else{
//                        throw new Exception("接口报错");
//                    }
//                }
//            }, 2, 10, false, 6000, asyncExecutor);
//        } catch (Exception e) {
//            organUser = null;
//            logger.error("从门户的dubbo接口调用数据报错"+ExceptionUtil.getExceptionTrace(e));
//        }
        return organUser;
    }


}
