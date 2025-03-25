package com.synway.datarelation.controller.relation;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.BloodlineNodeType;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.common.TreeNode;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.DataBloodlineService;
import com.synway.datarelation.service.datablood.FieldBloodlineService;
import com.synway.datarelation.service.datablood.PageBloodlineFilterService;
import com.synway.datarelation.util.DataBloodlineUtils;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
/**
 * @ClassName TableRelationController
 * @description 数据血缘的后台接口
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
@Controller
@RequestMapping(value = "/table")
public class TableRelationController {
    private Logger logger = LoggerFactory.getLogger(TableRelationController.class);
    @Autowired
    DataBloodlineService dataBloodlineServiceImpl;
    @Autowired
    FieldBloodlineService fieldBloodlineServiceImpl;
    @Autowired
    CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;
    @Autowired
    private PageBloodlineFilterService pageBloodlineFilterServiceImpl;

    /**
     * @author chenfei
     * @date 2024/6/13 17:30
     * @Description 接口测试用
     */
    @RequestMapping(value = "/accessRelation")
    @ResponseBody
    public void  accessRelation(){
        cacheManageDataBloodlineServiceImpl.getDataAcessDataBloodCache();
    }

    /**
     *  数据血缘页面中 左侧搜索 按钮的点击事件 查询出所有节点的信息
     * @20210312 需要新增模糊查询和精准查询的数据，默认是模糊查询
     * @param dataBloodlineQueryParams
     * @return
     */
    @RequestMapping(value = "/getAllBloodlineNodeUrl",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<TreeNode>> getAllBloodlineNode(@RequestBody DataBloodlineQueryParams dataBloodlineQueryParams){
        logger.info("getAllBloodlineNode 查询的参数为："+ JSONObject.toJSONString(dataBloodlineQueryParams));
        ServerResponse<List<TreeNode>>  serverResponse = null;
        try{
            // 防止sql注入 去除掉特殊字符
            String queryStr = dataBloodlineQueryParams.getQueryinfo();
            dataBloodlineQueryParams.setQueryinfo(queryStr.replaceAll("[%]",""));
            if(StringUtils.isEmpty(dataBloodlineQueryParams.getQueryinfo()) && !dataBloodlineQueryParams.getNodeQueryType().equalsIgnoreCase(Constant.OPERATINGSYSTEM)){
                serverResponse = ServerResponse.asErrorResponse("查询内容不能存在特殊字符");
            }else{
                //　这个会从三个地方查询　首先是　数据接入程序／标准化程序／数据处理的表
                serverResponse= dataBloodlineServiceImpl.queryDataBloodlineTable(dataBloodlineQueryParams);
            }
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     *  获取单个节点的血缘关系链路图
     * @param queryData
     * @return
     */
    @RequestMapping(value = "/getOneBloodlineNodeLinkUrl",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<DataBloodlineNode> getOneBloodlineNodeLink(@RequestBody QueryDataBloodlineTable queryData){
        logger.info("获取单个节点的血缘关系链路图的查询参数为："+ queryData.toString());
        ServerResponse<DataBloodlineNode>  serverResponse = null;
        try{
            DataBloodlineNode dataBloodlineNode = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,Constant.MAIN,"",true);
            // 清洗重复的边和节点
            try{
                if(dataBloodlineNode.getNodes() != null && !dataBloodlineNode.getNodes().isEmpty()){
                    // 20210617 数据接入血缘如果能查询到数据处理血缘，则需要把生成文件那个节点隐藏掉接入血缘直接
                    DataBloodlineUtils.hiddenNode(dataBloodlineNode);
                    // 如果是页面上点击查询下一个流程的节点信息，会将页面上的节点信息传递过来，用后台来去除重复值（与页面上现存的值做比较）
                    List<DataBloodlineNode.BloodNode> allNodeList= new ArrayList<>(
                            dataBloodlineNode.getNodes().stream().collect(toMap(d -> d.getId(),
                            e->e,(exists, replacement)-> exists)).values());
                    dataBloodlineNode.setNodes(allNodeList);
                }else{
                    logger.info("查询缓存中的数据为空，先清除缓存重新获取数据");
                }
                if(dataBloodlineNode.getEdges() != null && !dataBloodlineNode.getEdges().isEmpty()){
                    List<DataBloodlineNode.Edges> allEdgeList= new ArrayList<>(dataBloodlineNode.getEdges().stream()
                            .filter(d -> d != null && !StringUtils.equalsIgnoreCase(d.getSource(),d.getTarget()))
                            .collect(toMap(d -> (d.getSource()+
                                            "|"+d.getTarget()+"|"+d.getEdgeType()+"|"+d.getVisible()),
                                    e->e,(exists, replacement)-> exists)).values());
                    dataBloodlineNode.setEdges(allEdgeList);
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            serverResponse = ServerResponse.asSucessResponse(dataBloodlineNode);
        }catch (Exception e){
            logger.error("查询血缘异常：", e);
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 点击节点之后获取该节点对应其它类型的数据 根据传递的数据获取
     * 究竟是查询上一个流程的数据还是下一个流程的数据 通过页面修改对应的值
     * 还要添加上 查询节点和另一个流程中相关节点的边信息
     * @param clickData
     * @return
     */
    @RequestMapping(value = "/getNextProcessByClickNodeUrl",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<DataBloodlineNode> getNextProcessByClickNode(@RequestBody DataBloodlineNode.BloodNode clickData){
        logger.info("点击节点之后开始查询上一个流程/下一个流程的数据信息");
        logger.info("查询参数为："+JSONObject.toJSONString(clickData));
        ServerResponse<DataBloodlineNode> serverResponse = null;
//        // 查询返回的节点数据
        DataBloodlineNode resultData = new DataBloodlineNode();
        List<DataBloodlineNode.BloodNode> listNode = new ArrayList<>();
        List<DataBloodlineNode.Edges> edgesList = new ArrayList<>();
//        List<DataBloodlineNode> list = new ArrayList<>();
//        // 如果是 dataQueryType 是left 则查询上一个流程的节点信息  如果是 right则查询下一个流程的节点
//        //  如果是 main 则两个都要查询
//        // 如果是第一个流程/最后一个流程 则不需要查询节点信息
//        // 存储查询了哪些接口
        String message = "查询";
        String queryNodeId = "";
        try{
            QueryDataBloodlineTable queryData = new QueryDataBloodlineTable();
            queryData.setDataBaseId(clickData.getDataBaseId());
            queryData.setSourceCodeCh(clickData.getSourceCodeCh());
            queryData.setSourceId(clickData.getSourceId());
            queryData.setTableId(clickData.getTableId());
            queryData.setTableNameCh(clickData.getTableNameCh());
            queryData.setTableNameEn(clickData.getTableNameEn());
            queryData.setTargetCodeCh(clickData.getTargetCodeCh());
            queryNodeId = clickData.getId();
            int pageNum = BloodlineNodeType.getNumByCode(clickData.getDataType());
//            // 节点的查询参数
            if(clickData.getDataQueryType().equalsIgnoreCase(Constant.MAIN)){
                String queryDataType = BloodlineNodeType.getCodeByNum(pageNum -1 );
                if(StringUtils.isEmpty(queryDataType)){
                    message = "该节点是流程中第一个节点信息，不需要上一个流程的数据";
                    logger.info("是流程中是第一个节点信息，不需要查询");
                }else{
                    queryData.setDataType(queryDataType);
                    message += "【"+BloodlineNodeType.getNameByCode(queryDataType)+"】类型的节点信息  ";
                    DataBloodlineNode dataBloodlineNode = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,"left",queryNodeId,true);
                    if(dataBloodlineNode != null){
                        listNode.addAll(dataBloodlineNode.getNodes());
                        edgesList.addAll(dataBloodlineNode.getEdges());
                    }else{
                        message += " 数据量为空";
                    }
                }
            }
            String queryDataType = BloodlineNodeType.getCodeByNum(pageNum+(clickData.getDataQueryType().equalsIgnoreCase(Constant.LEFT)?-1:1));
            if(StringUtils.isEmpty(queryDataType)){
                logger.info("是流程中是最后一个/第一个节点信息，不需要查询");
                message = "该节点是流程中是最后一个/第一个节点信息，不需要查询";
            }else{
                queryData.setDataType(queryDataType);
                message += "【"+BloodlineNodeType.getNameByCode(queryDataType)+"】类型的节点信息 ";
                String type1 = clickData.getDataQueryType().equalsIgnoreCase("main")?"right":clickData.getDataQueryType();
                DataBloodlineNode dataBloodlineNode = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,type1,queryNodeId,true);
                if(dataBloodlineNode != null){
                    listNode.addAll(dataBloodlineNode.getNodes());
                    edgesList.addAll(dataBloodlineNode.getEdges());
                }else{
                    message += " 数据量为空";
                }
            }
            resultData.setNodes(listNode);
            // 存在输入边和输出边相同的情况
            edgesList = edgesList.stream().filter(d -> !StringUtils.equalsIgnoreCase(d.getSource(),d.getTarget())).collect(Collectors.toList());
            resultData.setEdges(edgesList);
            serverResponse = ServerResponse.asSucessResponse(message,resultData);
        }catch (Exception e){
            logger.error("点击之后获取节点信息报错"+e.getMessage());
            if(e.getMessage() == null){
                serverResponse = ServerResponse.asErrorResponse("从下一个/上一个流程中获取到的数据为空，报错原因："+e.getMessage());
            }else{
                serverResponse = ServerResponse.asErrorResponse("点击之后获取节点信息报错"+e.getMessage());
            }
        }
        return serverResponse;
    }


    /**
     * 获取下个流程的血缘信息
     * @param queryData
     * @return
     */
    @RequestMapping(value = "/getNextOneBloodlineNodeLinkUrl",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<DataBloodlineNode> getNextOneBloodlineNodeLinkUrl(@RequestBody QueryDataBloodlineTable queryData){
        logger.info("获取下一个流程中节点信息的查询参数为："+ queryData.toString());
        ServerResponse<DataBloodlineNode>  serverResponse = null;
        try{
            DataBloodlineNode dataBloodlineNode = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,queryData.getQueryType(),"",true);
            // 清洗重复的边和节点
            try{
                if(dataBloodlineNode.getNodes() != null && dataBloodlineNode.getNodes().size() >0){
                    DataBloodlineUtils.hiddenNode(dataBloodlineNode);
                    // 如果是页面上点击查询下一个流程的节点信息，会将页面上的节点信息传递过来，用后台来去除重复值（与页面上现存的值做比较）
                    List<DataBloodlineNode.BloodNode> allNodeList= new ArrayList<>(dataBloodlineNode.getNodes().stream().collect(toMap(d -> d.getId(),
                            e->e,(exists, replacement)-> exists)).values());
                    dataBloodlineNode.setNodes(allNodeList);
                }else{
                    logger.info("查询缓存中的数据为空，先清除缓存重新获取数据");
                }
                if(dataBloodlineNode.getEdges() != null && dataBloodlineNode.getEdges().size() > 0){
                    List<DataBloodlineNode.Edges> allEdgeList= new ArrayList<>(dataBloodlineNode.getEdges().stream()
                            .filter(d -> !StringUtils.equalsIgnoreCase(d.getSource(),d.getTarget()))
                            .collect(toMap(d -> (d.getSource()+
                                            "|"+d.getTarget()+"|"+d.getEdgeType()+"|"+d.getVisible()),
                                    e->e,(exists, replacement)-> exists)).values());
                    dataBloodlineNode.setEdges(allEdgeList);
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            serverResponse = ServerResponse.asSucessResponse(dataBloodlineNode);
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }


    /**
     * 判断本地的大数据平台是否为 华为/阿里云
     * @return   odps :阿里云平台 hive : 华为云平台
     */
    @GetMapping(value = "/getLocalDataBaseType")
    @ResponseBody
    public String getLocalDataBaseType(){
        String value= "";
        try{
            value = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,Constant.ALI_YUN);
            if(Constant.ALI_YUN.equalsIgnoreCase(value)){
                value = Common.ODPS;
            }else{
                value = Common.HIVE;
            }
        }catch (Exception e){
            value= Common.ODPS;
            logger.error("获取大数据平台的版本报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return value;
    }

    /**
     * 数据过滤的相关信息
     * @param bloodlineFilterPage
     * @return 返回筛选之后的数据
     */
    @PostMapping(value = "/PageBloodlineFilter")
    @ResponseBody
    public ServerResponse<DataBloodlineNode> pageBloodlineFilter(@RequestBody BloodlineFilterPage bloodlineFilterPage){
        logger.info("页面筛选的查询参数为 数据过滤信息:"+bloodlineFilterPage.getClassifyFilter()
                +" 组织分类标注:"+ JSONObject.toJSONString(bloodlineFilterPage.getOrganizationClassifyList()));
        try{
            if(StringUtils.isNotBlank(bloodlineFilterPage.getDataTrackFilter())
                    && !Constant.DATA_FLOW.equalsIgnoreCase(bloodlineFilterPage.getDataTrackFilter())
                    && !Constant.PROCESS_FLOW.equalsIgnoreCase(bloodlineFilterPage.getDataTrackFilter())){
                throw new Exception("dataTrackFilter的参数填写不正确，应为【dataFlow/processFlow】");
            }
            if(StringUtils.isNotBlank(bloodlineFilterPage.getPageDataStr())){
                bloodlineFilterPage.setPageData(JSONObject.parseObject(bloodlineFilterPage.getPageDataStr(),DataBloodlineNode.class));
            }
            DataBloodlineNode dataBloodlineNode = pageBloodlineFilterServiceImpl.PageBloodlineFilter(bloodlineFilterPage);
            // 20210409 需要在这里设置 nodeNameList
            if(dataBloodlineNode.getNodes() != null &&
                    !dataBloodlineNode.getNodes().isEmpty()){
                // 获取所有的节点名称放在 tableNameList里面
                List<TrackQueryData> nodeNameList = new ArrayList<>();
                dataBloodlineNode.getNodes().stream().filter(d -> StringUtils.isNotBlank(d.getId()))
                        .distinct().forEach(d ->{
                    nodeNameList.add(new TrackQueryData(d.getId(),d.getNodeName()));
                });
                dataBloodlineNode.setNodeNameList(nodeNameList);
            }
            return ServerResponse.asSucessResponse(dataBloodlineNode);
        }catch (Exception e){
            logger.error("页面筛选数据报错："+ ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("页面筛选数据报错："+e.getMessage());
        }
    }

    /**
     * 页面上隐藏应用血缘的节点信息，加工血缘的节点直接连接应用血缘模型的节点
     * @param data 页面上的数据
     * @return
     */
    @PostMapping(value = "/pageHiddenNode")
    @ResponseBody
    public ServerResponse<DataBloodlineNode> pageHiddenNode(@RequestBody DataBloodlineNode data){
        ServerResponse<DataBloodlineNode> serverResponse = null;
        try{
            DataBloodlineNode dataBloodlineNode = pageBloodlineFilterServiceImpl.pageHiddenNode(data);
            serverResponse = ServerResponse.asSucessResponse(dataBloodlineNode);
        }catch (Exception e){
            logger.error("隐藏应用血缘的节点信息报错："+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("隐藏应用血缘的节点信息报错："+e.getMessage());
        }
        return serverResponse;
    }

}
