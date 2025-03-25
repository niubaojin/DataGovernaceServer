package com.synway.datarelation.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.CommonErrorCode;
import com.synway.common.exception.SystemException;
import com.synway.common.util.RetryUtil;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.dataresource.DataResource;
import com.synway.datarelation.service.common.CommonService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 1:获取本地仓库的相关信息，配置是否为 阿里云 还是 华为云
 * @author wangdongwei
 * @date 2021/4/8 10:10
 */
@Service
public class CommonServiceImpl implements CommonService {
    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private RestTemplateHandle restTemplateHandle;
    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;
    @Autowired
    private ConcurrentMap<String,List<String>> dataIdProjectsMap;
    @Value("${processType}")
    private String processType;

    /**
     *  调用 数据仓库的接口 获取 本地仓库的类型
     */
    @Override
    public void getDataResourceVersion() throws Exception{
        logger.info("开始从数据仓库中获取本地仓库的数据，然后获取本地仓库的类型");
        List<DataResource> dataResources = RetryUtil.executeWithRetry(new Callable<List<DataResource>>() {
            @Override
            public List<DataResource> call() throws Exception {
                List<DataResource> dataResources = restTemplateHandle.getDataResource();
                if(dataResources == null || dataResources.isEmpty()){
                    throw SystemException.asSystemException(CommonErrorCode.CONFIG_ERROR, "从数据仓库未查出托管数据源");
                }
                return dataResources;
            }
        },3,1,false);

        // 可能存在 odps hive  同时存在 则如果出现odps 就以odps为准 之后再找其它的
        Map<String, DataResource> resourceMap = dataResources.stream().filter(d ->
                StringUtils.equalsIgnoreCase(d.getResType(), Common.ODPS) ||
                        StringUtils.equalsIgnoreCase(d.getResType(), Common.HIVE) ||
                        StringUtils.equalsIgnoreCase(d.getResType(), Common.HIVE_CDH) ||
                        StringUtils.equalsIgnoreCase(d.getResType(), Common.HIVE_HUAWEI)
        ).collect(Collectors.toMap(
                k->{return k.getResType().toLowerCase();},v->v,(old,newValue)->newValue));

        this.getDataResourceProjects(resourceMap);

        String version = "3";
        if(resourceMap.get(Common.HIVE) != null){
            parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Constant.HUA_WEI_YUN);
            version = resourceMap.get(Common.HIVE).getVersion();
        }else if(resourceMap.get(Common.HIVE_HUAWEI) != null){
            parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Constant.HUA_WEI_YUN);
            version = resourceMap.get(Common.HIVE_HUAWEI).getVersion();
        }else if(resourceMap.get(Common.HIVE_CDH) != null){
            parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Constant.HUA_WEI_YUN);
            version = resourceMap.get(Common.HIVE_CDH).getVersion();
        } else if(resourceMap.get(Common.ODPS) != null){
            parameterMap.put(Common.DATA_PLAT_FORM_TYPE, Constant.ALI_YUN);
            version = resourceMap.get(Common.ODPS).getVersion();
        }else{
            throw new NullPointerException("数据仓库的本地仓中没有配置hive/odps的本地数据，请先配置");
        }
        if(StringUtils.isBlank(version)){
            version = "3";
        }else if(StringUtils.equalsIgnoreCase(Common.V2,version)){
            version = "2";
        }else if(StringUtils.equalsIgnoreCase(Common.V3,version)){
            version = "3";
        }else{
            version = "3";
        }
        parameterMap.put(Common.DATA_PLAT_FORM_VERSION, version);
        parameterMap.put(Common.PROCESS_PLAT_FORM_TYPE, processType);
        logger.info("本地仓的版本号为"+ JSONObject.toJSONString(parameterMap));
    }

    @Override
    public String getTableType(String nodeType) {
        try{
            if(StringUtils.equalsIgnoreCase(nodeType,"10")){
                return StringUtils.equalsIgnoreCase(parameterMap.get(Common.DATA_PLAT_FORM_TYPE),Constant.ALI_YUN)
                        ?Common.ODPS:Common.HIVE;
            }else if(StringUtils.equalsIgnoreCase(nodeType,"23")){
                return StringUtils.equalsIgnoreCase(parameterMap.get(Common.DATA_PLAT_FORM_TYPE),Constant.ALI_YUN)
                        ?Common.ADS:Common.HBASE;
            }else{
                return StringUtils.equalsIgnoreCase(parameterMap.get(Common.DATA_PLAT_FORM_TYPE),Constant.ALI_YUN)
                        ?Common.ODPS:Common.HIVE;
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return StringUtils.equalsIgnoreCase(parameterMap.get(Common.DATA_PLAT_FORM_TYPE),Constant.ALI_YUN)
                    ?Common.ODPS:Common.HIVE;
        }
    }

    @Override
    public List<String> getDataIdList(String projectName) {
        List<String> dataIdList = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry : dataIdProjectsMap.entrySet()){
            if(StringUtils.isBlank(projectName) || entry.getValue().isEmpty()){
                dataIdList.add(entry.getKey());
                continue;
            }
            if(entry.getValue().stream().anyMatch(d -> StringUtils.equalsIgnoreCase(d,projectName))){
                dataIdList.add(entry.getKey());
            }
        }
        if(dataIdList.isEmpty()){
            dataIdList.addAll(dataIdProjectsMap.keySet());
        }
        return dataIdList;
    }


//    /**
//     * 获取数据仓库id值 与项目名的相关关系
//     * @param dataResources
//     */
//    private void getDataIdAndProjects(List<DataResource> dataResources){
//        try{
//            logger.info("开始更新dataId和项目名的缓存信息");
//            dataIdProjectsMap.clear();
//            for(DataResource dataResource:dataResources){
//                // 只需要hive ck odps ads 的数据类型
//
//                if(StringUtils.isBlank(dataResource.getResId()) ||
//                        !Constant.DATA_TYPE_LIST.contains(dataResource.getResType())
//                    || dataResource.getProjectNames() ==null || dataResource.getProjectNames().isEmpty()){
//                    continue;
//                }
//                if(dataResource.getStatus() == 0){
//                    logger.error("数据源id{}的状态为无法连接,不能存放在缓存中",dataResource.getResId());
//                    continue;
//                }
//                dataIdProjectsMap.put(dataResource.getResId(),dataResource.getProjectNames() == null
//                        ?new ArrayList<>():dataResource.getProjectNames());
//            }
//            logger.info("更新dataId和项目名的缓存信息结束，" +
//                    "缓存中的dataId信息的数据量为{}",dataIdProjectsMap.size());
//        }catch (Exception e){
//            logger.error("更新dataId缓存的信息报错{}",ExceptionUtil.getExceptionTrace(e));
//        }
//    }

    private void getDataResourceProjects(Map<String,DataResource> resourceMap){
        try{
            logger.info("开始更新dataId和项目名的缓存信息");
            for(Map.Entry<String,DataResource> entry : resourceMap.entrySet()){
                // 只需要hive ck odps ads 的数据类型
                String resType = entry.getKey();
                DataResource dataResource = entry.getValue();
                if(StringUtils.isBlank(dataResource.getResId()) ||
                        !Constant.DATA_TYPE_LIST.contains(dataResource.getResType())
                        || dataResource.getProjectNames() ==null || dataResource.getProjectNames().isEmpty()){
                    continue;
                }
                if(dataResource.getStatus() == 0){
                    logger.error("数据源id{}的状态为无法连接,不能存放在缓存中",dataResource.getResId());
                    continue;
                }
                dataIdProjectsMap.put(dataResource.getResId(),dataResource.getProjectNames() == null
                        ?new ArrayList<>():dataResource.getProjectNames());
            }
            logger.info("更新dataId和项目名的缓存信息结束，" +
                    "缓存中的dataId信息的数据量为{}",dataIdProjectsMap.size());
        }catch (Exception e){
            logger.error("更新dataId缓存的信息报错{}",ExceptionUtil.getExceptionTrace(e));
        }
    }


}
