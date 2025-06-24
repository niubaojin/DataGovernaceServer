package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.FieldCodeValDao;
import com.synway.datastandardmanager.dao.master.FieldDeterminerDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.OperatorLog;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.*;
import com.synway.datastandardmanager.service.FieldDeterminerService;
import com.synway.datastandardmanager.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 限定词管理
 * @author wangdongwei
 * @date 2021/7/16 9:52
 */
@Service
@Slf4j
public class FieldDeterminerServiceImpl implements FieldDeterminerService {
    @Autowired
    private FieldDeterminerDao fieldDeterminerDao;

    @Autowired
    private FieldCodeValDao fieldCodeValDao;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Autowired
    private Environment env;

    private static final Lock lock = new ReentrantLock();


    @Override
    public  List<FieldDeterminer> searchTable(FieldDeterminerParameter parameter) {
        // 搜索内容如果存在空值，将其变为 null值
        if(StringUtils.isBlank(parameter.getSearchName())){
            parameter.setSearchName(null);
        }
        if(parameter.getVersionList() == null || parameter.getVersionList().isEmpty()){
            parameter.setVersionList(null);
        }
        if(parameter.getDeterminerStateList() == null || parameter.getDeterminerStateList().isEmpty()){
            parameter.setDeterminerStateList(null);
        }
        if(parameter.getDeterminerTypeList() == null || parameter.getDeterminerTypeList().isEmpty()){
            parameter.setDeterminerTypeList(null);
        }
        if(parameter.getRegOrgList() == null || parameter.getRegOrgList().isEmpty()){
            parameter.setRegOrgList(null);
        }
        if(parameter.getSort() == null || parameter.getSort().isEmpty()){
            parameter.setSort("modDate");
        }
        if (parameter.getSortOrder() == null || parameter.getSortOrder().isEmpty()){
            parameter.setSortOrder("desc");
        }
        List<FieldDeterminer> list =  fieldDeterminerDao.searchTable(parameter);
        if(list != null && !list.isEmpty()){
            int num = 1;
            for(FieldDeterminer data:list){
                // 码表值
                data.setDeterminerState(FieldDeterminerCode.getValueById(data.getDeterminerStateNum()));
                data.setDeterminerType(FieldDeterminerCode.getValueById("2_"+data.getDeterminerTypeNum()));
                data.setNum(num++);
            }
        }else{
            list = new ArrayList<>();
        }
        return list;
    }



    @Override
    public FieldDeterminerTable getFilterObject() {
        FieldDeterminerTable fieldDeterminerTable = new FieldDeterminerTable();
        List<FieldDeterminer> list =  fieldDeterminerDao.searchAllTable();
        // 进行筛选
        List<FilterObject> determinerStateFilter = new ArrayList<>();
        list.stream().map(FieldDeterminer::getDeterminerStateNum).distinct().forEach(d->
                determinerStateFilter.add(new FilterObject(FieldDeterminerCode.getValueById(d), String.valueOf(d)))
        );
        List<FilterObject> determinerTypeFilter = new ArrayList<>();
        list.stream().map(FieldDeterminer::getDeterminerTypeNum).distinct().forEach(d->
                determinerTypeFilter.add(new FilterObject(FieldDeterminerCode.getValueById("2_"+d), String.valueOf(d)))
        );
        List<FilterObject> versionFilter = new ArrayList<>();
        list.stream().map(FieldDeterminer::getVersions).distinct().forEach(d->
                versionFilter.add(new FilterObject(d,d))
        );
        List<FilterObject> regOrgFilter = new ArrayList<>();
        list.stream().map(FieldDeterminer::getRegOrg).distinct().forEach(d->
                regOrgFilter.add(new FilterObject(d,d))
        );
        // 排序后插入进去
        determinerStateFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        determinerTypeFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        versionFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        regOrgFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        fieldDeterminerTable.setDeterminerStateFilter(determinerStateFilter);
        fieldDeterminerTable.setDeterminerTypeFilter(determinerTypeFilter);
        fieldDeterminerTable.setRegOrgFilter(regOrgFilter);
        fieldDeterminerTable.setVersionFilter(versionFilter);
        return fieldDeterminerTable;
    }

    @Override
    public String addOneData(FieldDeterminer data){
        // 新增时 审核时间  和 修订时间 都由后台程序创建 插入数据的时候直接用数据库的时间

        // 限定词标示符 是中文名首字母，有重复的情况加1位流水号 此时这里需要加锁
        // 判断  determinerId 里面的值是否已经存在 如果存在 则报错
        int count = fieldDeterminerDao.findCountByDeterminerId(data.getDeterminerId());
        if(count > 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR,"内部标识符["+data.getDeterminerId()+"]重复");
        }
        String message = "添加数据成功";
        data.setDName(getDName(data.getDChinseName()));
        //大版本 从版本管理中读取
        String version = fieldCodeValDao.searchVersion();
        JSONObject parse = (JSONObject) JSON.parse(version);
        boolean isHailiang = env.getProperty("database.type").equalsIgnoreCase("hailiang");
        String versions = isHailiang ? parse.getString("fielddeterminerversions") : parse.getString("fieldDeterminerVersions");
        data.setVersions(versions);
        //  版本发布日期 date   YYYYMMDD
        Date date = null ;
        String todayStr = null;

        try{
            todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            date = DateUtil.parseDate(todayStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        }catch (Exception e){
            log.error("解析时间报错"+ExceptionUtil.getExceptionTrace(e));
            date = new Date();
        }
        Integer releaseDate = Integer.valueOf(todayStr);
        data.setReleaseDate(releaseDate);
        //   审核时间 date   YYYYMMDD
        data.setOnDate(date);
        log.info("插入到限定词的数据为{}", JSONObject.toJSONString(data));
        int num = fieldDeterminerDao.addOneData(data);
        log.info("数据插入到限定词表的数据量为{}",num);

        // 发送操作日志
        operateLogServiceImpl.fieldDeterminerSuccessLog(OperateLogHandleTypeEnum.ADD, "限定词管理", data);
        return message;
    }

    @Override
    public String upOneData(FieldDeterminer data) {
        // 检查这个id对应的数据 是否存在 如果不存在，说明该条记录在数据库中被删除，无法添加
        int tableCount = fieldDeterminerDao.getCountById(data.getDeterminerId());
        if(tableCount == 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"内部标识符["+data.getDeterminerId()
                    +"]在数据库中对应的数据不存在，更新失败");
        }
        // 判断是否修改限定词属性值，未做任何改动，直接返回，不更新数据库
        FieldDeterminer searchFieldDeterminer = fieldDeterminerDao.getFieldDeterminerById(data.getDeterminerId());
        searchFieldDeterminer.setDeterminerState(FieldDeterminerCode.getValueById(searchFieldDeterminer.getDeterminerStateNum()));
        searchFieldDeterminer.setDeterminerType(FieldDeterminerCode.getValueById("2_"+searchFieldDeterminer.getDeterminerTypeNum()));
        if(data.equals(searchFieldDeterminer)){
            return "限定词未做任何改动";
        }else{
            if(data.getModDate() == null){
                throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"更新时间字段的值不能为空");
            }

            // 先判断 限定词中文名称 是否已经修改 是则重新生成  限定词标示符
            // 如果 限定词标示符 也是空 也要重新获取
            int count = fieldDeterminerDao.getCountByIdAndChinesName(data.getDeterminerId(),data.getDChinseName());
            if(count == 0 || StringUtils.isBlank(data.getDName())){
                String dName = getDName(data.getDChinseName());
                data.setDName(dName);
            }
            Date date = null ;
            String todayStr = null;
            Date update = null ;
            String updateStr = null;
            try{
                todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                date = DateUtil.parseDate(todayStr,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                updateStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME);
                update = DateUtil.parseDate(updateStr,DateUtil.DEFAULT_PATTERN_DATETIME);
            }catch (Exception e){
                log.error("解析时间报错"+ExceptionUtil.getExceptionTrace(e));
                date = new Date();
            }
            if(data.getReleaseDate() == null){
                Integer releaseDate = Integer.valueOf(todayStr);
                data.setReleaseDate(releaseDate);
            }
            if(data.getOnDate() == null){
                data.setOnDate(date);
            }
            if(data.getVersions() == null){
                data.setVersions(searchFieldDeterminer.getVersions());
            }
            //如果这条数据的更新时间小于数据库中的数据 说明该条记录已经被别人更新，本次更新失败
            log.info("需要编辑的限定词数据为{}", JSONObject.toJSONString(data));

            //大版本号 从系统配置获取
            String version = fieldCodeValDao.searchVersion();
            JSONObject parse = (JSONObject) JSON.parse(version);
            boolean isHailiang = env.getProperty("database.type").equalsIgnoreCase("hailiang");
            String versions = isHailiang ? parse.getString("fielddeterminerversions") : parse.getString("fieldDeterminerVersions");
            data.setVersions(versions);

            int updateNum = fieldDeterminerDao.upOneData(data);
            if(updateNum == 0){
                throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,"数据已经被别的页面更新，本次更新失败，请刷新页面后重新修改");
            }
            if("05".equals(searchFieldDeterminer.getDeterminerStateNum())){
                log.info("===========开始生成限定词版本信息=========");
                //生成限定词版本信息
                FieldDeterminerVersion fieldDeterminerVersion = new FieldDeterminerVersion();
                fieldDeterminerVersion.setDChineseName(searchFieldDeterminer.getDChinseName());
                if(!searchFieldDeterminer.getDName().isEmpty()){
                    fieldDeterminerVersion.setDName(searchFieldDeterminer.getDName());
                }

                fieldDeterminerVersion.setVersion(searchFieldDeterminer.getReleaseDate());

                StringBuffer stringMemo = new StringBuffer();
                if(!(data.getRegOrg().equals(searchFieldDeterminer.getRegOrg()))){
                    stringMemo.append("提交机构属性,");
                }
                if(!(data.getFacturer().equals(searchFieldDeterminer.getFacturer()))){
                    stringMemo.append("制作厂商属性,");
                }
                if(data.getDeterminerTypeNum() != searchFieldDeterminer.getDeterminerTypeNum()){
                    stringMemo.append("部标标准属性,");
                }
                if(!(data.getAuthor().equals(searchFieldDeterminer.getAuthor()))){
                    stringMemo.append("修订人属性,");
                }
                if(!(data.getMemo().equals(searchFieldDeterminer.getMemo()))){
                    stringMemo.append("限定词描述属性,");
                }
                if(!data.getVersions().equals(searchFieldDeterminer.getVersions())){
                    stringMemo.append("大版本号属性,");
                }
                StringBuffer constantStr = null;
                if(stringMemo.length() != 0){
                    constantStr = new StringBuffer("修改了");
                    constantStr.append(stringMemo);
                    if(constantStr.indexOf(",",constantStr.length()-1) != -1){
                        fieldDeterminerVersion.setMemo(constantStr.substring(0,constantStr.length()-1));
                    }
                }
                fieldDeterminerVersion.setVersions(versions);

                fieldDeterminerVersion.setAuthor(searchFieldDeterminer.getAuthor());

                String uuid = UUIDUtil.getUUID();
                fieldDeterminerVersion.setFieldDeterminerVersion(uuid);

                fieldDeterminerVersion.setUpdateTime(update);

                fieldDeterminerDao.saveFieldDeterminerVersion(fieldDeterminerVersion);

                log.info("===========生成限定词版本信息结束=========");

                //保存至备份表SYNLTE. FIELDDETERMINER_HISTORY中
                log.info("===========开始生成限定词历史信息=========");
                searchFieldDeterminer.setFieldDeterminerVersion(uuid);
                searchFieldDeterminer.setModDate(update);
                searchFieldDeterminer.setDeterminerState("07");
                fieldDeterminerDao.saveOneOldData(searchFieldDeterminer);
                log.info("===========结束生成限定词历史信息=========");
            }
            // 发送操作日志
            operateLogServiceImpl.fieldDeterminerSuccessLog(OperateLogHandleTypeEnum.ALTER, "限定词管理", data);
            return "数据更新成功";
        }

    }

    @Override
    public String updateDeterminerState(String id, String state, String modDate) {
        checkDataExist(id,modDate);
        //生成版本日期值，为当前日期

        String todayStr = DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        Integer releaseDate = Integer.valueOf(todayStr);
        int updateNum = fieldDeterminerDao.updateDeterminerState(id,state,modDate,releaseDate);
        if(updateNum == 0){
            throw SystemException.asSystemException(ErrorCode.QUERY_SQL_ERROR,"数据已经被别的页面更新，本次更新失败，请刷新页面后重新修改");
        }
        return "数据更新成功";
    }


    @Override
    public String getDName(String dChinesName){
        lock.lock();
        try{
            // 中文转拼音
            String dName = PinYinUtil.getFirstSpell(dChinesName);
            if(StringUtils.isBlank(dName)){
                log.error("自动生成的限定词标示符为空，请查看后台日志");
                return "";
            }
            // 然后根据拼音在数据库中查询相同拼音的有几个 获取最大的那个
            List<String> list = fieldDeterminerDao.findAllDBNameList(dName);
            if(list == null || list.isEmpty()){
                // 为空，直接用拼音首字母
                return dName;
            }else{
                Optional<String> maxOptional = list.stream().filter(d ->{
                    if(StringUtils.isNotBlank(d)){
                        String[] list1 = d.toLowerCase().trim().split(dName.toLowerCase(),-1);
                        return (StringUtils.isBlank(list1[1]) ||
                                StringUtils.isNumeric(list1[1])) && list1.length == 2;
                    }else{
                        return false;
                    }
                }).map(d-> {
                    if(!StringUtils.equalsIgnoreCase(d,dName)){
                        return  d.toLowerCase().replaceAll(dName.toLowerCase(),"");
                    }else{
                        return "0";
                    }
                }).max(Comparator.comparingInt(Integer::parseInt));
                if(maxOptional.isPresent() && StringUtils.isNotBlank(maxOptional.get())){
                    int num = Integer.parseInt(maxOptional.get());
                    if(num < 9 ){
                        return (dName.toLowerCase()+"0"+(num+1));
                    }else{
                        return (dName.toLowerCase()+(num+1));
                    }
                }else{
                    return dName;
                }
            }
        }catch (Exception e){
            log.error("从数据库中获取顺序报错："+ ExceptionUtil.getExceptionTrace(e));
            throw  SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"从数据库中获取顺序报错："+ e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean checkIsDeactivate(String id, String modDate) {
        checkDataExist(id,modDate);
        // 检查表
        int tableCount = fieldDeterminerDao.checkIsDeactivate(id);
        log.info("查询出的数据为{}"+tableCount);
        return tableCount > 0;
    }


    @Override
    public String getDeterminerId() {
        // 找出数据库中 DQXXXXX，五位数字递增，可编辑。
        List<String> idList = fieldDeterminerDao.getDeterminerIdList();
        if(idList == null || idList.isEmpty()){
            return Common.COMMON_ID;
        }
        Optional<String> str = idList.stream().filter(d ->{
            if(StringUtils.isNotBlank(d) && StringUtils.containsIgnoreCase(d,Common.DQ)){
                String[] list1 = d.toLowerCase().trim().split(Common.DQ,-1);
                return (StringUtils.isBlank(list1[1]) ||
                        StringUtils.isNumeric(list1[1])) && list1.length == 2;
            }else{
                return false;
            }
        }).map(d-> {
            if(!StringUtils.equalsIgnoreCase(d,Common.DQ)){
                return  d.toLowerCase().replaceAll(Common.DQ,"").trim();
            }else{
                return "0";
            }
        }).max(Comparator.comparingInt(Integer::parseInt));
        if(str.isPresent() && StringUtils.isNotBlank(str.get())){
            int num = Integer.parseInt(str.get())+1;
            DecimalFormat format = new DecimalFormat("00000");
            return Common.DQ + format.format(num);
        }else{
            return Common.COMMON_ID;
        }
    }

    @Override
    public List<PageSelectOneValue> searchDeterminerNameList(String searchName) {
        if(StringUtils.isBlank(searchName)){
            searchName = null;
        }
        List<PageSelectOneValue> list = fieldDeterminerDao.searchFieldDeterminerNameList(searchName);
        if(list == null || list.isEmpty()){
            return new ArrayList<>();
        }else{
            list.sort((s1,s2)->Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(),s2.getLabel()));
        }
        return list;
    }


    /**
     * 更新前检查数据
     * @param id  限定词内部编码
     * @param modDate  修订时间
     */
    private void checkDataExist(String id,String modDate){
        // 检查时间格式
        try{
            DateUtil.parseDate(modDate,DateUtil.DEFAULT_PATTERN_DATETIME);
        }catch (ParseException e){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"参数modDate值为["+modDate+"]不是" +
                    "yyyy-MM-dd HH:mm:ss类型的时间字符串");
        }

        // 检查该条记录的数据是否已经存在
        // 检查这个id对应的数据 是否存在 如果不存在，说明该条记录在数据库中被删除，无法添加
        int tableCount = fieldDeterminerDao.getCountByIdModDate(id,modDate);
        if(tableCount == 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,"内部标识符["+id
                    +"]在数据库中不存在或该条记录已经被更新，请刷新页面");
        }


    }
}
