package com.synway.datarelation.initial;


import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.common.DataProcessBloodCache;
import com.synway.datarelation.scheduler.WorkFlowAnalysisSchedule;
import com.synway.datarelation.service.common.CommonService;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 程序启动类
 * @author wangdongwei
 */
@Component
public class Initialize implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(Initialize.class);

    @Autowired
    private WorkFlowAnalysisSchedule workFlowAnalysisSchedule;
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    private CommonService commonServiceImpl;
    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;

    @Override
    public void run(String... args) throws Exception {
        // 20210407 配置中心不再配置 dataPlatFormType，从数据仓库中获取本地仓的数据，然后再获取这块数据
//        commonServiceImpl.getDataResourceVersion();

        // 版本号是多少
//        String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION,"");
//        if(StringUtils.isEmpty(version)){
//            throw new NullPointerException("公共参数中未配置dataPlatFormVersion，请先去参数配置页面配置该参数");
//        }
        // 类型
//        String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
//        if(StringUtils.isEmpty(dataPlatFormType)){
//            throw new NullPointerException("公共参数中未配置dataPlatFormType，请先去参数配置页面配置该参数");
//        }

        parameterMap.put(Common.DATA_PLAT_FORM_VERSION, "3");
        parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Constant.ALI_YUN);

        cacheManageDataBloodlineServiceImpl.getSynlteObjectId();
        cacheManageDataBloodlineServiceImpl.getObjectStoreInfo();

        //先判断这个分布式缓存中是否存在数据，存在则不需要再查询
        DataProcessBloodCache dataProcessBloodCache = cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache();
        if(dataProcessBloodCache == null || dataProcessBloodCache.getChildKeyData() == null){
//             这个是模型血缘的相关程序
            workFlowAnalysisSchedule.getTableRelation();
            // 程序启动后的事件 这个是数据血缘的相关程序
            //接入
            cacheManageDataBloodlineServiceImpl.getDataAcessDataBloodCache();
            //标准化
            cacheManageDataBloodlineServiceImpl.getStandardRelationCache();
            //加工 表
            cacheManageDataBloodlineServiceImpl.getDataProcessDataBloodCache();
            //加工 字段
            cacheManageDataBloodlineServiceImpl.getDataProcessColumnCache();
            //应用
            cacheManageDataBloodlineServiceImpl.getApplicationDataBloodCache();
            // 获取所有表的分类信息
            cacheManageDataBloodlineServiceImpl.getAllTableClassifyCache();
        }else{
            logger.info("分布式缓存中已经存在相关数据，不需要再查询");
        }

    }

}
