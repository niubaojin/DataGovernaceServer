package com.synway.governace.service.largeScreen.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.governace.common.Constant;
import com.synway.governace.dao.DetailedLargeScreenDao;
import com.synway.governace.pojo.largeScreen.DataResource;
import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;
import com.synway.governace.service.largeScreen.DetailedLargeScreenService;
import com.synway.governace.util.DateUtil;
import com.synway.governace.util.LargeScreenUtils;
import com.synway.governace.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * 大屏上详细信息的接口信息
 */
@Service
@Slf4j
public class DetailedLargeScreenServiceImpl implements DetailedLargeScreenService {

    @Autowired
    private DetailedLargeScreenDao detailedLargeScreenDao;
    @Autowired
    private RestTemplateHandle restTemplateHandle;
    @Resource
    private Environment environment;
    /**
     * @param searchName 1： 2： 3： 4：
     * @return
     */
    @Override
    public List<PropertyLargeDetailed> getPublicInfo(String searchName){
        //  资源服务平台 放在同一个 nginx里面
        String registerInterfaceUrl = environment.getRequiredProperty("nginxIp");
        final List<PropertyLargeDetailed> list = new ArrayList<>();
        if(StringUtils.equalsIgnoreCase(searchName,"1") ||StringUtils.equalsIgnoreCase(searchName,"2")){
            // [{"ID":"JZ_RESOURCE_98334","TABLENAME":"NB_TAB_XZQGLSJDHYXX","TABLECNNAME":"中文名","RECORDCOUNT":546}]
            List<JSONObject> tableArray = restTemplateHandle.getClassifyServerInterfance(registerInterfaceUrl+"/classifyserver/interface/publishDataInfo");
            if(tableArray == null){
                throw new NullPointerException("资源服务平台返回的数据为空");
            }
            tableArray.stream().filter(d -> StringUtils.isNotBlank(d.getString("RECORDCOUNT"))
                    && StringUtils.isNotBlank(d.getString("TABLENAME")) ).forEach(d->{
                PropertyLargeDetailed propertyLargeDetailed = new PropertyLargeDetailed();
                propertyLargeDetailed.setTableName(d.getString("TABLENAME"));
                propertyLargeDetailed.setDataName(d.getString("TABLECNNAME"));
                propertyLargeDetailed.setDataCount(d.getLong("RECORDCOUNT"));
                propertyLargeDetailed.setDataCountStr(LargeScreenUtils.getNumStrConversionStr(propertyLargeDetailed.getDataCount()));
                list.add(propertyLargeDetailed);
            });
        }else if(StringUtils.equalsIgnoreCase(searchName,"3") ||StringUtils.equalsIgnoreCase(searchName,"4")){
            //  [{"SERVICENAME":"similar_people_analysis","SERVICECNNAME":"类人分析","CALLCOUNT":1000}]
            List<JSONObject> serviceArray = restTemplateHandle.getClassifyServerInterfance(registerInterfaceUrl+"/classifyserver/interface/publishServiceInfo");
            if(serviceArray == null){
                throw new NullPointerException("资源服务平台返回的数据为空");
            }
            serviceArray.stream().filter(d -> StringUtils.isNotBlank(d.getString("SERVICENAME"))
                    && StringUtils.isNotBlank(d.getString("SERVICECNNAME")) ).forEach(d->{
                PropertyLargeDetailed propertyLargeDetailed = new PropertyLargeDetailed();
                propertyLargeDetailed.setTableName(d.getString("SERVICENAME"));
                propertyLargeDetailed.setDataName(d.getString("SERVICECNNAME"));
                propertyLargeDetailed.setDataCount(d.getLong("CALLCOUNT"));
                propertyLargeDetailed.setDataCountStr(LargeScreenUtils.getNumStrConversionStr(propertyLargeDetailed.getDataCount()));
                list.add(propertyLargeDetailed);
            });
        }else{
            throw new NullPointerException("请求参数只能为1,2,3,4 请确认前端参数是否正确");
        }
        // 按照表数据量进行排序和去重
        List<PropertyLargeDetailed> list1 = new ArrayList<>(list.stream()
                .collect(toMap(PropertyLargeDetailed::getTableName,
                e->e,(exists, replacement)-> exists)).values());
        list1.sort(Comparator.comparingLong(PropertyLargeDetailed::getDataCount).reversed());
        return list1;
    }

    @Override
    public List<PropertyLargeDetailed> getDetailedOriginalData(String searchName) {
        log.info("开始获取原始库指定二级分类{}的数据表资产信息",searchName);
        String dateStr = DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATE);
        List<PropertyLargeDetailed> list = detailedLargeScreenDao.getDetailedOriginalData(searchName,dateStr);
        list.forEach(d-> d.setDataCountStr(LargeScreenUtils.getNumStrConversionStr(d.getDataCount())));
        list.sort(Comparator.comparingLong(PropertyLargeDetailed::getDataCount).reversed());
        return list;
    }

    @Override
    public List<PropertyLargeDetailed> getDetailedResourceThemeData(String searchName, String type) {
        log.info("开始获取{}指定二级分类{}的数据表资产信息",type,searchName);
        List<PropertyLargeDetailed> list = detailedLargeScreenDao.getDetailedResourceThemeData(searchName,type);
        list.forEach(d-> d.setDataCountStr(LargeScreenUtils.getNumStrConversionStr(d.getDataCount())));
        list.sort(Comparator.comparingLong(PropertyLargeDetailed::getDataCount).reversed());
        return list;
    }

    /**
     * 获取本地仓的 连接信息
     * @param dbType 数据库类型
     * @return
     */
    @Override
    public String getLargeTableSum(String dbType) {
        try{
            if(StringUtils.equalsIgnoreCase(dbType, Constant.ODPS)
                    || StringUtils.equalsIgnoreCase(dbType, Constant.ADS) ){
                List<DataResource> dataResources = restTemplateHandle.getDataCenterVersion();
                dataResources = dataResources.stream().filter(d-> StringUtils.equalsIgnoreCase(d.getResType(),dbType) &&
                        d.getStatus() == 1)
                        .collect(Collectors.toList());
                if(dataResources.size() == 0){
                    log.info("数据仓库中未配置管理员权限的本地仓信息，获取表数量报错");
                    return "";
                }
                return dataResources.get(0).getConnectInfo();
            }
        }catch (Exception e){
            log.error("获取表数量报错：", e);
        }
        log.info("数据库类型为"+dbType+"不需要查询表数量");
        return "";
    }
}
