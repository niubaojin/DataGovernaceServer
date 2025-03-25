package com.synway.datastandardmanager.scheduler;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.config.CacheManager;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.AuthorizedUser;
import com.synway.datastandardmanager.pojo.DataResource;
import com.synway.datastandardmanager.pojo.LoginUser;
import com.synway.datastandardmanager.pojo.buildtable.TableImformation;
import com.synway.datastandardmanager.pojo.summaryobjectpage.LocalTableInfo;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *  开关数据的相关查询
 */
@Component
public class SwitchFlagQuery {
    private Logger logger = LoggerFactory.getLogger(SwitchFlagQuery.class);
    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    ConcurrentHashMap<String,Boolean> switchHashMap;
    @Autowired
    ConcurrentHashMap<String,String> parameterMap;
    @Resource
    CacheManager cacheManager;


    /**
     * 定时任务触发查询 是否启用 审批流程
     */
    public void getApprovalInfoSwitchFlag(){
        logger.info("开始查询审批中心是否需要被启用");
        boolean flag = restTemplateHandle.getNavStatusByName("审批中心");
        switchHashMap.put("approvalInfo",flag);
        logger.info("审批中心是否被启用的状态为："+flag+" 缓存中数据为："+ JSONObject.toJSONString(switchHashMap));
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void scheduledTask(){
        getApprovalInfoSwitchFlag();
    }


    @Scheduled(cron = "0 */60 * * * ?")
    public void scheduledDataCenterTask(){
        getDataCenterVersion();
    }



    @Scheduled(cron = "20 */50 * * * ?")
    public void updateLocalTableTask(){
        List<TableImformation> list1 = null;
        List<LocalTableInfo> list2 = null;
        try{
            logger.info("--------开始定时获取本地仓的数据--------");
            list1 = (List<TableImformation>) cacheManager.getValue(Common.CACHE_LOCAL_TABLE_INFORMATION);
            list2 = (List<LocalTableInfo>) cacheManager.getValue(Common.CACHE_ALL_LOCAL_TABLE);
            cacheManager.evictCache(Common.CACHE_LOCAL_TABLE_INFORMATION);
            cacheManager.evictCache(Common.CACHE_ALL_LOCAL_TABLE);
            LoginUser authorizedUser = new LoginUser();
            authorizedUser.setUserId("-1");
            authorizedUser.setUserName("测试用户");
            authorizedUser.setUserLevel(1);
            AuthorizedUserUtils.getInstance().setAuthor(authorizedUser);
            restTemplateHandle.getTableImformationList();
//            restTemplateHandle.findAllLocalTableInformation();
        }catch (Exception e){
            logger.error("定时任务获取缓存中的数据报错"+ExceptionUtil.getExceptionTrace(e));
            if(list1 != null){
                cacheManager.addOrUpdateCache(Common.CACHE_LOCAL_TABLE_INFORMATION,list1);
            }
            if(list2 != null){
                cacheManager.addOrUpdateCache(Common.CACHE_ALL_LOCAL_TABLE,list2);
            }
        }finally {
            AuthorizedUserUtils.getInstance().removeAuthor();
        }
    }




    /**
     *  调用 数据仓库的接口 获取 本地仓库的类型
     */
    public void getDataCenterVersion(){
        try{
            Map<String,DataResource> map = getLocalMap();
            if(map.get(Common.HIVEHUAWEI) != null){
                parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Common.HUA_WEI_YUN);
            }else if(map.get(Common.HIVECDH) != null){
                parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Common.HUA_WEI_YUN);
            }else if(map.get(Common.ODPS) != null){
                parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Common.ALI_YUN);
            }else{
                throw new NullPointerException("数据仓库的本地仓中没有配置hive/odps的本地数据，请先配置");
            }
            logger.info("本地数据仓库的类型为"+JSONObject.toJSONString(parameterMap));
        }catch (Exception e){
            parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Common.ALI_YUN);
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

    public Map<String, DataResource> getLocalMap() throws Exception{
        Map<String,DataResource> map = new HashMap<>();
        logger.info("开始从数据仓库中获取本地仓库的数据，然后获取本地仓库的类型");
        List<DataResource> dataResources = restTemplateHandle.getDataCenterVersion("2","0");
        if(dataResources == null || dataResources.isEmpty()){
            logger.error("==========从数据仓库中获取到本地仓的数据为空");
            return map;
        }
        // 可能存在 odps hive  同时存在 则如果出现odps 就以odps为准 之后再找其它的
        map = dataResources.stream().filter(d ->
                StringUtils.equalsIgnoreCase(d.getResType(), Common.ODPS) ||
                        StringUtils.equalsIgnoreCase(d.getResType(), Common.HIVECDH) ||
                        StringUtils.equalsIgnoreCase(d.getResType(),Common.HIVEHUAWEI)).collect(Collectors.toMap(
                k->{return k.getResType().toLowerCase();},v->v,(old,newValue)->newValue));
        return map;
    }
}
