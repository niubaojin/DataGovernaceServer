package com.synway.datarelation.controller.relation.api;

import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.pojo.databloodline.impactanalysis.StreamTableLevel;
import com.synway.datarelation.pojo.databloodline.impactanalysis.WorkFlowInformation;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.DataBloodlineService;
import com.synway.datarelation.service.datablood.ExternalInterfceService;
import com.synway.datarelation.service.datablood.ImpactAnalysisService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 血缘关系的对外接口
 */
@Controller
@RequestMapping(value = "/api/externalInterfce")
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Autowired
    DataBloodlineService dataBloodlineServiceImpl;

    @Autowired
    ExternalInterfceService externalInterfceServiceImpl;

    @Autowired
    private ImpactAnalysisService impactAnalysisServiceImpl;

    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;

    /**
     * 获取标准化血缘的所有表血缘关系
     */
    @RequestMapping(value = "/getAllStandardBlood")
    @ResponseBody
    public RelationInfoRestTemolate getAllStandardBlood(){
        logger.info("开始查询数据预处理那边所有的表血缘关系，不包括字段血缘");
        DataBloodlineQueryParams queryParams = new DataBloodlineQueryParams();
        queryParams.setQuerytype("like");
        queryParams.setQueryinfo("");
        RelationInfoRestTemolate restTemolate = restTemplateHandle.queryStandardRelationInfo(queryParams);
        logger.info("查询数据预处理血缘结束");
        return restTemolate;
    }


    /**
     *  数据大屏需要用到这个 获取数据接入/标准化管理/数据处理/应用血缘的 数据总量
     */
    @RequestMapping(value = "/getAllBloodCount")
    @ResponseBody
    public AllBloodCount getAllBloodCount(){
        return dataBloodlineServiceImpl.getAllBloodCount();
    }


    /**
     *  根据输出血缘信息 查询出对应的接入血缘的信息
     * @param type 1：数据处理
     * @param searchName 查询名称
     * @return
     */
    @RequestMapping(value = "/getStandardBloodByName")
    @ResponseBody
    public List<String> getStandardBloodByName(String type, String searchName){
        logger.info("查询参数的信息为：type "+type + " searchName  "+searchName);
        List<String> result = null;
        try{
            result = externalInterfceServiceImpl.getStandardBloodByName(type,searchName);
        }catch (Exception e){
            logger.error("获取血缘信息报错"+ ExceptionUtil.getExceptionTrace(e));
            result = null;
        }
        return result;
    }




    /**
     * 获取hive/opds指定表的所有下游表信息
     * @param tableName   表英文名
     * @param projectName  表项目名
     * @return  {"status":1,"message":"","result":["syndm.nb_tab_mee","syndw.ck_ceshi1"]}
     */
    @RequestMapping(value="/getUpStreamTables",method={RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<String>> getUpStreamTables(String tableName, String projectName){
        try{
            if(StringUtils.isBlank(tableName)){
                logger.error("【获取指定表的下游表】报错，表英文名不能为空");
                return ServerResponse.asErrorResponse("表英文名不能为空");
            }
            if(StringUtils.isBlank(projectName)){
                logger.error("【获取指定表的下游表】报错，项目名不能为空");
                return ServerResponse.asErrorResponse("表项目名不能为空");
            }
            List<WorkFlowInformation> workFlowInformation = new ArrayList<>();
            List<StreamTableLevel> upStreamTableLevel = new ArrayList<>();
            Map<String,Integer> tableExitMap = new HashMap<>();
            impactAnalysisServiceImpl.queryImpactAnalysisCache(projectName+"."+tableName,
                    workFlowInformation,upStreamTableLevel,0, Constant.RIGHT,tableExitMap,"hive",null);
            List<String> tableNameList = upStreamTableLevel.stream().map(d ->(d.getProjectName()+"."+d.getTableNameEn()).toLowerCase())
                    .distinct().collect(Collectors.toList());
            return ServerResponse.asSucessResponse(tableNameList);
        }catch (Exception e){
            logger.error("【获取指定表的下游表】报错："+ ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取数据报错："+e.getMessage());
        }
    }


    /**
     *   根据表名获取到 这张表涉及到的 应用血缘的信息和工作流节点信息
     * @param tableName  表名
     * @param projectName  项目名
     * @return
     */
    @RequestMapping(value="/getImpactAnalysisByTableName",method={RequestMethod.GET})
    @ResponseBody
    public ServerResponse<ImpactAnalysisProperty> getImpactAnalysisPropertyByTableName(String tableName,
                                                                                       String projectName){
        ServerResponse<ImpactAnalysisProperty> serverResponse = null;
        try{
            if(StringUtils.isBlank(tableName)){
                logger.error("【获取应用血缘的信息和工作流节点信息】报错，表英文名不能为空");
                return ServerResponse.asErrorResponse("表英文名不能为空");
            }
            if(StringUtils.isBlank(projectName)){
                logger.error("【获取应用血缘的信息和工作流节点信息】报错，项目名不能为空");
                return ServerResponse.asErrorResponse("表项目名不能为空");
            }
            ImpactAnalysisProperty impactAnalysisProperty = new ImpactAnalysisProperty();
            // 获取数据加工血缘的被使用的工作流信息 需要去重后的工作流名称
            List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(Constant.MAIN
                    ,projectName+"."+tableName);
            long flowCount = allDataProcessList.stream().map(RelationshipNode::getNodeName).distinct().count();
            impactAnalysisProperty.setWorkFlowCount(flowCount);
            List<ApplicationSystem> list = cacheManageDataBloodlineServiceImpl.getApplicationSystemCache(tableName);
            impactAnalysisProperty.setApplicationBloodlineCount(list== null?0:list.size());
            serverResponse = ServerResponse.asSucessResponse(impactAnalysisProperty);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取应用血缘的信息和工作流节点信息报错:"+e.getMessage());
            logger.error("根据表名获取具体的应用血缘信息和工作流节点信息报错："+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


}
