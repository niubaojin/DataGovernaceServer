package com.synway.datarelation.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.CommonErrorCode;
import com.synway.common.exception.ExceptionUtil;
import com.synway.common.exception.SystemException;
import com.synway.datarelation.constant.UriConstant;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.access.RelationPage;
import com.synway.datarelation.pojo.databloodline.DataBloodlineQueryParams;
import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import com.synway.datarelation.pojo.databloodline.RelationInfoRestTemolate;
import com.synway.datarelation.pojo.databloodline.impactanalysis.DetailedTableByClassify;
import com.synway.datarelation.pojo.databloodline.impactanalysis.ReceiveTable;
import com.synway.datarelation.pojo.dataresource.DataResource;
import com.synway.datarelation.pojo.dataresource.FieldInfo;
import com.synway.datarelation.pojo.dataresource.PartitionField;
import com.synway.datarelation.pojo.dataresource.TableInfo;
import com.synway.datarelation.service.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * RestTempalte请求处理类
 * @author
 * @date 2018/12/4 14:25
 */
@Component
@CacheConfig(cacheNames = {"allTableStructinfo"})
public class RestTemplateHandle {
    private Logger logger = LoggerFactory.getLogger(RestTemplateHandle.class);
    @Autowired()
    private RestTemplate restTemplate;
    @Autowired Environment env;
    @Value("${resourceId}")
    private String resourceId;

    /**
     *  从标准化程序中获取到 血缘关系的信息
     *  根据协议名英文名（jz_xx）、协议中文名、表英文名(不包含项目名)、查询，
     *  返回对于匹配数据的血缘关系
     */
    public RelationInfoRestTemolate queryStandardRelationInfo(DataBloodlineQueryParams queryParams){
        logger.info("开始查询标准化那边的接口");
        RelationInfoRestTemolate result = new RelationInfoRestTemolate();
        try{
            String queryUrl = UriConstant.STANDARD_RELATION_URI + queryParams.getQueryinfo()
                    +"&queryType="+queryParams.getQuerytype();
            logger.info(queryUrl);
            JSONObject resultJson = restTemplate.getForObject(queryUrl,JSONObject.class);
            result = JSONObject.parseObject(JSONObject.toJSONString(resultJson),RelationInfoRestTemolate.class);
            // 需要在这里 筛选指定的数据库输出类型
            if(result.getReqInfo() != null){
                List<QueryBloodlineRelationInfo> data = result.getReqInfo().stream().filter(d -> StringUtils.isNotBlank(d.getTargetDBEngName())
                        && Constant.DATA_BASE_TYPE_LIST.contains(d.getTargetDBEngName().toUpperCase())).distinct().collect(Collectors.toList());
                result.setReqInfo(data);
            }
        }catch (Exception e){
            result.setError(e.getMessage());
            result.setReqRet("false");
            logger.error("获取标准血缘异常：", e);
        }
        return result;
    }


    /**
     *  wangdongwei
     *  查询所有数据接口血缘的信息 分页查询
     *  * 分页查询数据血缘数据
     *  这个是 1.7以前的接入血缘  1.8之后etl程序发生变化 如何做未知
     * * @return DataRelateReqInfoPage
     */
    /**
     * @author chenfei
     * @date 2024/6/6 14:31
     * @Description 1.8之后版本新开发
     */
    public  List<QueryBloodlineRelationInfo> queryAllAccessRelationInfo(){
        // 先查询一次，获取总数，判断是否需要继续查，然后获取继续查的数据，最后拼接成总的输出
        List<QueryBloodlineRelationInfo> relationInfos = new ArrayList<>();
        try{
            logger.info("开始查询所有的数据接入血缘关系");
            int pageNum = 1;
            RelationPage page = this.queryRelaitonPage(1);
            if (page != null && page.getRelationInfos()!=null && page.getRelationInfos().size()>0){
                relationInfos.addAll(page.getRelationInfos());
                long totalPage = page.getTotalPageNum();
                //查询剩余所有分页数据
                for (pageNum=2; pageNum<=totalPage; pageNum++){
                    page = this.queryRelaitonPage(pageNum);
                    if (page != null && page.getRelationInfos()!=null && page.getRelationInfos().size()>0){
                        relationInfos.addAll(page.getRelationInfos());
                    }
                }
            }
            logger.info("接入血缘接口查询完毕：共查出：{} 条数据", relationInfos.size());
        }catch (Exception e){
           logger.error("报错信息：", e);
        }
        return relationInfos;
    }

    private RelationPage queryRelaitonPage(int pageNum){
        String queryUrl = UriConstant.ACCESS_RELATION_URI+"?pageNum="+pageNum;
        String response = restTemplate.getForObject(queryUrl, String.class);
        ServerResponse serverResponse = JSON.parseObject(response, ServerResponse.class);
        if (serverResponse.isSuccess()){
            JSONObject jsonObject = (JSONObject) serverResponse.getData();
            RelationPage page = JSONObject.parseObject(jsonObject.toJSONString(), RelationPage.class);
            return page;
        }
        return null;
    }



    /**
     *  从数据仓库的接口中查询 本地数据库指定表的字段信息 getLocalTableStructinfo
     *  20210910 获取字段信息的接口发生变化 使用  getLocalTableStruct
     *  使用的参数信息是  String resourceId,String project,String tableName
     *  20210911 返回的数据增加了一层结构体
     *  20211018 返回的结构体发生变化
     * @param tableType  odps/ads/hive/hbase
     * @param tableNameEn  带项目名.表名 的表英文名
     * @return
     */
    public List<FieldInfo> getLocalTableStructinfo(String tableType, String tableNameEn) throws Exception{
        logger.info("开始查询数据数据库类型"+tableType+"表"+tableNameEn+"在数据仓库那边的字段信息");
        if(StringUtils.isBlank(tableNameEn)){
            throw new NullPointerException("表英文名不能为空");
        }
        JSONObject object =null;
        List<FieldInfo> list = null;
        String projectName = tableNameEn.contains(".")?tableNameEn.split("\\.")[0]:"";
        List<String> dataIdList = this.getDataIdList(projectName);
        for(String dataId:dataIdList){
            try{
                String requestUrl = UriConstant.RESOURCE_TABLE_STRUCTURE_URI + dataId +
                        "&project=" + projectName+"&tableNameEn="+(tableNameEn.replaceAll(projectName+".",""));
                logger.info("查询数据仓库那边的url为"+requestUrl);
                object = restTemplate.getForObject(requestUrl ,JSONObject.class);
                TableInfo data = JSONObject.parseObject(getDataByJson(object),TableInfo.class);
                list = data.getFieldInfos();
                if(list ==null || list.isEmpty()){
                    continue;
                }
//                requestUrl = "http://dataresource/dataresource/api/getPartitionInfo?resourceId=" + dataId +
                requestUrl = UriConstant.RESOURCE_TABLE_PARTITION_URI + dataId +
                        "&project=" + projectName+"&tableNameEN="+(tableNameEn.replaceAll(projectName+".",""));
                logger.info("获取表分区的url为："+requestUrl);
                object = restTemplate.getForObject(requestUrl ,JSONObject.class);
                List<PartitionField> partitionFields = JSONObject.parseObject(getDataByJson(object),TableInfo.class).getPartitionFields();
                if(partitionFields == null || partitionFields.isEmpty()){
                    return list;
                }
                for (FieldInfo fieldInfo : list) {
                    boolean flag = partitionFields.stream().anyMatch(d -> StringUtils.equalsIgnoreCase(d.getFieldName(),
                            fieldInfo.getFieldName()));
                    if (flag) {
                        fieldInfo.setIsPartition(true);
                    } else {
                        fieldInfo.setIsPartition(false);
                    }
                }
                return list;
            }catch (Exception e){
                logger.error(e.getMessage());
            }
            if(list !=null && !list.isEmpty()){
                return list;
            }
        }
        return new ArrayList<>();
    }

    @Autowired
    private ConcurrentMap<String,List<String>> dataIdProjectsMap;

    public List<String> getDataIdList(String projectName) {
        List<String> dataIdList = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry: dataIdProjectsMap.entrySet()){
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

    /**
     * 获取数据资产那边的接口，指定表的数据量
     * @param params
     * @return
     */
    public List<DetailedTableByClassify> getDetailedTable(List<ReceiveTable> params) throws Exception{
        logger.info("开始查询数据资产那边表的数据量信息");
//        String requestUrl = "http://property/interface/getTableOrganization";
        Map<String,List<ReceiveTable>> queryMap = new HashMap<>(1);
        queryMap.put("table",params);
        JSONObject response = restTemplate.postForObject(UriConstant.PROPERTY_TABLE_ORGANIZATION ,queryMap,JSONObject.class);
        return JSONObject.parseArray(getDataByJson(response),DetailedTableByClassify.class);
//        List<DetailedTableByClassify> resultList = null;
//        if(response == null){
//            logger.error("查询数据资产那边表的数据量信息报错, 接口返回信息为空");
//            throw new NullPointerException("查询数据资产那边表的数据量信息报错, 接口返回信息为空");
//        }
//        if(1 == response.getStatus()){
//            resultList =  JSONObject.parseArray(JSONObject.toJSONString(response.getData()),DetailedTableByClassify.class);
//            return resultList;
//        }else{
//            logger.info("查询数据资产那边表的数据量信息报错" +response.getMessage());
//            throw new NullPointerException("查询数据资产那边表的数据量信息报错" +response.getMessage());
//        }
    }


    /**
     * 获取本地仓的相关数据
     * 20211019 在1.8之后不再使用这个接口
     * @return
     * @throws Exception
     */
    public List<DataResource> getDataResource() throws Exception{
        logger.info("开始查询本地仓库的类型");
        if (StringUtils.isBlank(this.resourceId)){
//            String requestUrl = "http://dataresource/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0";
            JSONObject result = restTemplate.getForObject(UriConstant.RESOURCE_LOCAL_URI,JSONObject.class);
            return JSONObject.parseArray(getDataByJson(result),DataResource.class);
        }else {
            DataResource dataResource = this.getResourceById(this.resourceId);
            List<DataResource> dataResources = new ArrayList<>();
            dataResources.add(dataResource);
            return dataResources;
        }
    }

    private DataResource getResourceById(String resId){
        DataResource dataResource = null;
        try{
            String requestUrl = UriConstant.RESOURCE_GET_BY_ID_URI + resId;
            logger.info("开始调用仓库getResourceById接口，获取数据源信息,url:" + requestUrl);
            JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
            if(jsonObject == null){
                throw  new NullPointerException("从数据仓库获取数据源信息为空");
            }
            Integer status = jsonObject.getInteger("status");
            if(status == 1){
                dataResource = jsonObject.getObject("data", DataResource.class);
            }else{
                throw new Exception("从数据仓库调用接口获取表的探查信息报错:"+jsonObject.getString("message"));
            }
        }catch (Exception e ){

        }

        return dataResource;
    }

    /**
     * 从其它模块获取数据
     * @param jsonObject
     * @return
     */
    public String getDataByJson(JSONObject jsonObject){
        if(jsonObject == null){
            throw SystemException.asSystemException(CommonErrorCode.RUNTIME_ERROR," 调用接口返回的数据为空");
        }
        if(jsonObject.getIntValue(Common.STATUS) == 1){
            logger.info("请求成功，请求的id值:{}",jsonObject.getString(Common.REQUEST_UUID));
            return JSONObject.toJSONString(jsonObject.get(Common.DATA));
        }else{
            logger.error("请求报错，请求的id值:{}，错误原因:{}",jsonObject.getString(Common.REQUEST_UUID)
                    ,jsonObject.getString(Common.MESSAGE));
            throw SystemException.asSystemException(CommonErrorCode.RUNTIME_ERROR,jsonObject.getString(Common.MESSAGE));
        }
    }
}
