package com.synway.dataoperations.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.dao.AlarmMessageDao;
import com.synway.dataoperations.dao.DoAlarmMsgDao;
import com.synway.dataoperations.entity.pojo.DoAlarmMsgEntity;
import com.synway.dataoperations.entity.vo.PageVO;
import com.synway.dataoperations.enums.AlarmCodeEnum;
import com.synway.dataoperations.interceptor.AuthorizedUserUtils;
import com.synway.dataoperations.pojo.*;
import com.synway.dataoperations.service.AlarmCenterService;
import com.synway.dataoperations.service.AlarmMessageService;
import com.synway.dataoperations.util.DateUtil;
import com.synway.dataoperations.util.ExcelHelper;
import com.synway.dataoperations.util.ExportUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlarmCenterServiceImpl implements AlarmCenterService {
    public static final Logger logger = LoggerFactory.getLogger(AlarmCenterServiceImpl.class);

    @Autowired
    AlarmMessageDao alarmMessageDao;
    @Autowired
    AlarmMessageService alarmMessageService;
    @Resource
    private DoAlarmMsgDao doAlarmMsgDao;

    @Autowired()
    private Environment env;


    @Override
    public AlarmReturnResultMap getAlarmData(RequestParameter requestParameter){
        AlarmReturnResultMap alarmReturnResultMap = new AlarmReturnResultMap();
        try {
            // 请求参数
            String searchTime = requestParameter.getSearchTime().substring(0, 13);
            if (requestParameter.getPageNum() != null && requestParameter.getPageSize() != null){
                PageHelper.startPage(requestParameter.getPageNum(), requestParameter.getPageSize());
            }
            // 查询告警图表统计信息
            List<AlarmMessage> alarmStatisticsInfo = alarmMessageDao.getAlarmStatisticsInfo(searchTime);
            Map<String,List<AlarmMessage>> alarmModuleMap = new HashMap<>();
            Map<String,List<AlarmMessage>> alarmLevelMap = new HashMap<>();
            if (alarmStatisticsInfo.size()>0){
                alarmModuleMap = alarmStatisticsInfo.stream().collect(Collectors.groupingBy(d -> d.getAlarmmodule()));
                alarmLevelMap = alarmStatisticsInfo.stream().collect(Collectors.groupingBy(d -> d.getLevelName()));
            }
            // 图表
            List<Map<String,String>> alarmModuleChart = new ArrayList();
            List<Map<String,String>> alarmStatusChart = new ArrayList();
            // 过滤筛选列表
            List<Map<String,String>> moduleFilterList = new ArrayList();
            List<Map<String,String>> statusFilterList = new ArrayList();

            alarmModuleMap.forEach((k,v) ->{
                Map<String,String> hashMap = new HashMap<>();
                Integer count = v.stream().mapToInt(AlarmMessage::getAlarmmoduleCount).sum();
                hashMap.put("key", k);
                hashMap.put("value", String.valueOf(count));
                alarmModuleChart.add(hashMap);

                Map<String,String> hashMapModule = new HashMap<>();
                hashMapModule.put("key", k);
                hashMapModule.put("value", k);
                moduleFilterList.add(hashMapModule);
            });
            alarmLevelMap.forEach((k,v) ->{
                Map<String,String> hashMap = new HashMap<>();
                Integer count = v.stream().mapToInt(AlarmMessage::getLevelNameCount).sum();
                hashMap.put("key", k);
                hashMap.put("value", String.valueOf(count));
                alarmStatusChart.add(hashMap);

                Map<String,String> hashMapStatus = new HashMap<>();
                hashMapStatus.put("key", k);
                hashMapStatus.put("value", k);
                statusFilterList.add(hashMapStatus);
            });

            statusFilterList.sort(Comparator.comparing(o -> o.get("key")));
            moduleFilterList.sort(Comparator.comparing(o -> o.get("key")));
            alarmReturnResultMap.setAlarmStatusFilterList(statusFilterList);
            alarmReturnResultMap.setAlarmModuleFilterList(moduleFilterList);
            alarmReturnResultMap.setAlarmStatusChart(alarmStatusChart);
            alarmReturnResultMap.setAlarmModuleChart(alarmModuleChart);
        }catch (Exception e){
            logger.error("获取告警中心数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return alarmReturnResultMap;
        }
        return alarmReturnResultMap;
    }

    @Override
    public PageVO getAlarmList(RequestParameter requestParameter) {
        PageVO pageVO = new PageVO<>();
        try {
            // 查询条件
            LambdaQueryWrapper<DoAlarmMsgEntity> wrapper = Wrappers.lambdaQuery();
            if (StringUtils.isNotBlank(requestParameter.getSearchTime())){
                Date date = DateUtil.parseDate(requestParameter.getSearchTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
                wrapper.gt(DoAlarmMsgEntity::getAlarmtime, date);
            }
            if (StringUtils.isNotBlank(requestParameter.getSearchName())){
                String searchName = requestParameter.getSearchName();
                wrapper.nested(wrapper2 -> {
                    wrapper2.apply("lower(ALARM_MODULE) like lower({0})", "%" + searchName.toLowerCase() + "%");
                    wrapper2.or().apply("lower(ALARM_LEVEL) like lower({0})", "%" + searchName.toLowerCase() + "%");
                    wrapper2.or().apply("lower(TABLE_NAME_CH) like lower({0})", "%" + searchName.toLowerCase() + "%");
                    wrapper2.or().apply("lower(ALARM_CONTENT) like lower({0})", "%" + searchName.toLowerCase() + "%");
                });
            }
            if (StringUtils.isNotBlank(requestParameter.getAlarmModule())){
                wrapper.eq(DoAlarmMsgEntity::getAlarmmodule, requestParameter.getAlarmModule());
            }
            if (StringUtils.isNotBlank(requestParameter.getAlarmStatus())){
                wrapper.eq(DoAlarmMsgEntity::getLevelName, requestParameter.getAlarmStatus());
            }
            if (StringUtils.isNotBlank(requestParameter.getAlarmModuleFilter())){
                List<String> alarmModules = Arrays.stream(requestParameter.getAlarmModuleFilter().split(",")).toList();
                wrapper.in(DoAlarmMsgEntity::getAlarmmodule, alarmModules);
            }
            if (StringUtils.isNotBlank(requestParameter.getAlarmStatusFilter())){
                List<String> alarmStatus = Arrays.stream(requestParameter.getAlarmStatusFilter().split(",")).toList();
                wrapper.in(DoAlarmMsgEntity::getLevelName, alarmStatus);
            }
            if (requestParameter.getPageNum() != null && requestParameter.getPageSize() != null){
                PageHelper.startPage(requestParameter.getPageNum(), requestParameter.getPageSize());
                pageVO.setPageNum(requestParameter.getPageNum());
                pageVO.setPageSize(requestParameter.getPageSize());
            }
            List<DoAlarmMsgEntity> doAlarmMsgEntities = doAlarmMsgDao.selectList(wrapper);
            PageInfo pageInfo = new PageInfo<>(doAlarmMsgEntities);
            pageVO.setRows(pageInfo.getList());
            pageVO.setTotal(pageInfo.getTotal());
        }catch (Exception e){
            logger.error(">>>>>>获取告警信息列表报错：", e);
            return pageVO.emptyResult();
        }
        return pageVO;
    }

    @Override
    public DataGFReturnMap getDGFData(RequestParameter requestParameter){
        RequestParameter parameter = new RequestParameter();
        parameter.setPageNum(1);
        parameter.setPageSize(11);
        PageVO pageVO = getAlarmList(parameter);
        DataGFReturnMap dataGFReturnMap = new DataGFReturnMap();
        // 请求参数
        String searchName = requestParameter.getSearchName();
        String[] linkFilter = StringUtils.isBlank(requestParameter.getLinkFilter())?null: requestParameter.getLinkFilter().split(",");
        String[] sponsorFilter = StringUtils.isBlank(requestParameter.getSponsorFilter())?null: requestParameter.getSponsorFilter().split(",");
        String[] managerFilter = StringUtils.isBlank(requestParameter.getManagerFilter())?null: requestParameter.getManagerFilter().split(",");

        // 查询数据
        List<DataGFMsg> filterList = alarmMessageDao.getDGFData(searchName,linkFilter,sponsorFilter,managerFilter);

        Page<Object> page = PageHelper.startPage(requestParameter.getPageNum(), requestParameter.getPageSize());
//        String sort = "";
//        if (StringUtils.isNotEmpty(queryParams.getSortName()) && StringUtils.isNotEmpty(queryParams.getSortBy())) {
//            //默认有表名的排前面
//            String sortName = queryParams.getSortName();
//            if ("storageSize".equals(sortName) || "valDensity".equals(sortName) ||
//                    "useCountOfMonth".equals(sortName) || "partitionNum".equals(sortName)) {
//                sortName = "to_number(" + sortName + ")";
//            }
////            if ("lifeCycle".equals(sortName)){
////                sortName = new StringBuffer("NLSSORT(").append(sortName).append(",'NLS_SORT = SCHINESE_PINYIN_M')").toString();
////            }
//            sort = sortName + " " + queryParams.getSortBy() + ",";
//        }
//        page.setOrderBy(sort + "name");

        // 查询数据
        List<DataGFMsg> dataGFMsgs = alarmMessageDao.getDGFData(searchName,linkFilter,sponsorFilter,managerFilter);
        PageInfo<DataGFMsg> pageInfo = new PageInfo<>(dataGFMsgs);
        // 过滤筛选列表
        List<String> linkList = new ArrayList<>();
        List<String> sponsorList = new ArrayList<>();
        List<String> managerList = new ArrayList<>();
        filterList.forEach(dataGFMsg -> {
            if (!linkList.contains(dataGFMsg.getLink()) && dataGFMsg.getLink() != null){
                linkList.add(dataGFMsg.getLink());
            }
            if (!sponsorList.contains(dataGFMsg.getSponsor()) && dataGFMsg.getSponsor() != null){
                sponsorList.add(dataGFMsg.getSponsor());
            }
            if (!managerList.contains(dataGFMsg.getManager()) && dataGFMsg.getManager() != null){
                managerList.add(dataGFMsg.getManager());
            }
        });
        List<Map<String,String>> linkFilterList = new ArrayList();
        for (String s : linkList){
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("text",s);
            hashMap.put("value",s);
            linkFilterList.add(hashMap);
        }
        List<Map<String,String>> sponsorFilterList = new ArrayList();
        for (String s : sponsorList){
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("text",s);
            hashMap.put("value",s);
            sponsorFilterList.add(hashMap);
        }
        List<Map<String,String>> managerFilterList = new ArrayList();
        for (String s : managerList){
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("text",s);
            hashMap.put("value",s);
            managerFilterList.add(hashMap);
        }
        linkFilterList.sort(Comparator.comparing(o -> o.get("text")));
        sponsorFilterList.sort(Comparator.comparing(o -> o.get("text")));
        managerFilterList.sort(Comparator.comparing(o -> o.get("text")));
        dataGFReturnMap.setLinkFilterList(linkFilterList);
        dataGFReturnMap.setSponsorFilterList(sponsorFilterList);
        dataGFReturnMap.setManagerFilterList(managerFilterList);
        dataGFReturnMap.setRows(pageInfo.getList());
        dataGFReturnMap.setTotal(pageInfo.getTotal());
        return dataGFReturnMap;
    }

    /**
     * @description 获取治理跟踪下拉菜单
     * @param queryKey
     * @return
     */
    public JSONArray getDGFSelect(String queryKey){
        JSONArray array = new JSONArray();
        try {
            if (queryKey.equalsIgnoreCase("link")){
                List<String> strings = Arrays.asList("数据质量","数据对账","数据资产","其他");
                for (String string:strings){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("label",string);
                    jsonObject.put("value",string);
                    array.add(jsonObject);
                }
            }
            if (queryKey.equalsIgnoreCase("manager")){
//                String allUsers = licDubboService.findAllUser();
//                if (JSONObject.parseObject(allUsers).get("isSuccess").toString().equals("true")){
//                    JSONArray jsonArray = (JSONArray) JSONObject.parseObject(allUsers).get("data");
//                    for (int i=0; i<jsonArray.size(); i++){
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("label",JSONObject.parseObject(jsonArray.get(i).toString()).get("id"));
//                        jsonObject.put("value",JSONObject.parseObject(jsonArray.get(i).toString()).get("name"));
//                        array.add(jsonObject);
//                    }
//                }
            }
            return array;
        }catch (Exception e){
            logger.error("获取环节或治理人员的下拉菜单数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return array;
        }
    }

    /**
     * @description 保存治理跟踪数据
     * @param dataGFMsg
     */
    public void saveDGFData(DataGFMsg dataGFMsg){
        // 如果主键id为空则插入数据，否则更新数据
        if (StringUtils.isBlank(dataGFMsg.getId())){
            // 生成uuid用来做主键
            String id = UUID.randomUUID().toString().replace("-","");
            dataGFMsg.setId(id);
//            // 发起人为当前用户
            LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
            logger.info("###########测试，用户名：" + authorizedUser.getUserName());
            dataGFMsg.setSponsor(authorizedUser.getUserName());
            logger.info("开始新增治理跟踪数据");
            alarmMessageDao.insertDFGData(dataGFMsg);
            sendOperatorLogDGF(2, dataGFMsg);
            logger.info("新增治理跟踪数据成功");
        }else {
            logger.info("开始更新治理跟踪数据");
            alarmMessageDao.updateDFGData(dataGFMsg);
            sendOperatorLogDGF(3, dataGFMsg);
            logger.info("更新治理跟踪数据成功");
        }
    }

    public void sendOperatorLogDGF(int operateType, DataGFMsg dataGFMsg){
        try {
            OperatorLog operatorLog = new OperatorLog();
            LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
            String operateTypeStr = AlarmCodeEnum.getNameByCodeAndType(operateType, "OPERATORTYPE");
            String operatorCondition = String.format("%s治理跟踪[%s]", operateTypeStr, dataGFMsg.getDataName());
            operatorLog.setOperateCondition(operatorCondition);
            operatorLog.setOperateType(operateType);
            operatorLog.setOperateResult("1");
            operatorLog.setErrorCode("0000");
            operatorLog.setOperateName("数据治理跟踪");
            operatorLog.setOperateTime(DateUtil.formatDateTimeSimple(new Date()));
            operatorLog.setTerminalId(env.getProperty("server.address"));
            operatorLog.setDisplay("");
            operatorLog.setDataLevel(1);
            operatorLog.setUserName(loginUser.getUserName());
            operatorLog.setUserId(loginUser.getIdCard());
            operatorLog.setUserNum(loginUser.getUserId());

            List<OperatorLog> operatorLogList = new ArrayList<>();
            operatorLogList.add(operatorLog);
            alarmMessageService.insertOperatorLogs(operatorLogList);
            logger.info("===============数据治理跟踪操作日志发送成功");
        }catch (Exception e){
            logger.error("===============数据治理跟踪操作日志发送失败\n", e);
        }
    }

    /**
     * @description 更新结论信息
     * @param requestParameter
     * @return
     */
    public JSONObject updateConclusion(RequestParameter requestParameter){
        JSONObject jsonObject = new JSONObject();
        // 参数
        String id = requestParameter.getId();                 // 主键id
        String title = requestParameter.getTitle();           // 类型：治理操作/审核操作
        String pass = requestParameter.getPass();             // 是否通过
        String conclusion = requestParameter.getConclusion(); // 结论
        String status = requestParameter.getStatus();         // 状态
        // 获取所有结论
        String dataGFMsgConclusionOldStr = alarmMessageDao.getConclusion(id);
        List<DataGFMsgConclusion> dataGFMsgConclusions = new ArrayList<>();
        // 当前时间
        String nowTime = DateUtil.formatDateTime(new Date());
        if (StringUtils.isBlank(dataGFMsgConclusionOldStr)){
            DataGFMsgConclusion dataGFMsgConclusionNew = new DataGFMsgConclusion();
            dataGFMsgConclusionNew.setNumber("1");
            dataGFMsgConclusionNew.setOperationType(title);
            dataGFMsgConclusionNew.setPass(pass);
            dataGFMsgConclusionNew.setOperationTime(nowTime);
            String conclusionNew = pass + "," + conclusion;
            dataGFMsgConclusionNew.setConclusion(conclusionNew);
            dataGFMsgConclusions.add(dataGFMsgConclusionNew);
            jsonObject.put("data",dataGFMsgConclusions);
            jsonObject.put("sum",1);
        }else {
            String sum = JSONObject.parseObject(dataGFMsgConclusionOldStr).getString("sum");
            int sumNew = Integer.parseInt(sum) + 1;
            String data = JSONObject.parseObject(dataGFMsgConclusionOldStr).getString("data");
            dataGFMsgConclusions = JSONObject.parseArray(data,DataGFMsgConclusion.class);
            DataGFMsgConclusion dataGFMsgConclusionNew = new DataGFMsgConclusion();
            dataGFMsgConclusionNew.setNumber(String.valueOf(sumNew));
            dataGFMsgConclusionNew.setPass(pass);
            dataGFMsgConclusionNew.setOperationType(title);
            dataGFMsgConclusionNew.setOperationTime(nowTime);
            String conclusionNew = pass + "," + conclusion;
            dataGFMsgConclusionNew.setConclusion(conclusionNew);
            dataGFMsgConclusions.add(dataGFMsgConclusionNew);
            jsonObject.put("data",dataGFMsgConclusions);
            jsonObject.put("sum",sumNew);
        }
        alarmMessageDao.updateConclusion(id,title,nowTime,JSONObject.toJSONString(jsonObject),status);

        return jsonObject;
    }

    /**
     * @description 删除按钮
     * @param ids
     */
    public void deleteDFGData(JSONObject ids){
        List<DataGFMsg> dataGFMsgs = alarmMessageDao.getDGFData("", null, null, null);
        String idsStr = ids.getString("ids");
        String[] idList =  StringUtils.isBlank(idsStr) ? null: idsStr.split(",");
        logger.info(String.format("开始删除id为：%s 的数据",idsStr));
        alarmMessageDao.deleteDFGData(idList);

        List<DataGFMsg> dataGFMsgsDel = dataGFMsgs.stream().filter(d -> idsStr.contains(d.getId())).collect(Collectors.toList());
        List<String> strings = new ArrayList<>();
        dataGFMsgsDel.stream().forEach(d -> {
            strings.add(d.getDataName());
        });
        DataGFMsg dataGFMsg = new DataGFMsg();
        dataGFMsg.setDataName(String.join(",",strings));
        sendOperatorLogDGF(4, dataGFMsg);
    }

    public void exportDFGData(JSONObject jsonObject, HttpServletResponse response) {
        try {
            // 参数
            String idsStr = jsonObject.getString("ids");
            String type = jsonObject.getString("type");
            String[] idList =  StringUtils.isBlank(idsStr) ? null: idsStr.split(",");
            //文件名称
            String name = "治理跟踪数据";
            //表标题
            String[] titles = {"治理模块", "数据名称", "内容", "最新时间", "发起人员", "治理人员", "治理状态"};
            //列对应字段
            String[] fieldName = new String[]{"link", "dataName", "message", "updateTime", "sponsor", "manager", "status"};
            //查询数据集
            List<DataGFMsg> dataGFMsgs = alarmMessageDao.getExportData(idList);
            logger.info("需要导出的数据量为：" + dataGFMsgs.size());
            //响应
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/binary;charset=UTF-8");
            switch (type) {
                case "1":
                    // 导出excel
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xlsx", "UTF-8"));
                    List<Object> listNew = new ArrayList<>(dataGFMsgs);
                    ExcelHelper.export(new DataGFMsg(), titles, name, listNew, fieldName, out);
                    break;
                case "2":
                    // 导出csv
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".csv", "UTF-8"));
                    List<Object> csvList = new ArrayList<>(dataGFMsgs);
                    ExportUtil.exportToCsv(out, csvList, name, titles, fieldName);
                    break;
                case "3":
                    // 导出word
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".doc", "UTF-8"));
                    List<Object> wordList = new ArrayList<>(dataGFMsgs);
                    int[] colWidths = {1534, 1534, 1334, 1334, 1334, 1334, 1334};
                    ExportUtil.exportToWord(out, wordList, name, titles, fieldName, "A3", colWidths);
                    break;
                default:

            }
        }catch (Exception e){
            logger.error("导出治理数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
