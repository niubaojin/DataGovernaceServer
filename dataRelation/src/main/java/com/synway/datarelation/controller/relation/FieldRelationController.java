package com.synway.datarelation.controller.relation;

import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.pojo.databloodline.ColumnBloodlineNode;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
import com.synway.datarelation.pojo.databloodline.QueryColumnTable;
import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.service.datablood.FieldBloodlineService;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 字段血缘的查询信息
 */
@Controller
@RequestMapping(value = "/field")
public class FieldRelationController {
    private Logger logger = LoggerFactory.getLogger(FieldRelationController.class);

    @Autowired
    private FieldBloodlineService fieldBloodlineService;

    @RequestMapping(value = "/getAllColumnByIdsUrl")
    @ResponseBody
    public ServerResponse<List<QueryColumnTable>> getAllColumnByIdsUrl(@RequestBody DataBloodlineNode.BloodNode node){
        ServerResponse<List<QueryColumnTable>> serverResponse = null;
        try{
            logger.info("查询指定id列表的所有字段信息"+JSONObject.toJSONString(node));
            if(StringUtils.isEmpty(node.getId())){
                logger.error("查询的节点id信息为空，不能查询所有的字段信息");
                return ServerResponse.asErrorResponse("查询的节点id信息为空，不能查询所有的字段信息");
            }
            List<QueryColumnTable> queryColumnTableList = fieldBloodlineService.getAllColumnByIdsService(node);
            serverResponse = ServerResponse.asSucessResponse(queryColumnTableList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询字段信息报错："+e.getMessage());
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }

        return serverResponse;
    }

    @RequestMapping(value = "/getFieldRelationNodeUrl")
    @ResponseBody
    public ServerResponse<ColumnBloodlineNode> getFieldRelationNodeUrl(@RequestBody QueryColumnTable oneColumn){
        ServerResponse<ColumnBloodlineNode> serverResponse = null;
        try{
            logger.info("查询指定字段的血缘信息"+JSONObject.toJSONString(oneColumn));
            ColumnBloodlineNode columnBloodlineNode = fieldBloodlineService.getFieldRelationNode(oneColumn);
            serverResponse = ServerResponse.asSucessResponse(columnBloodlineNode);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询指定字段的血缘信息："+e.getMessage());
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     *  数据加工字段血缘的
     * @param jsonString {"outputTableNameEn":"[\"syndw.m_mee_compression_ex\"]","inputTableNameEn":"[]","clickTableNameEn":"synods.nb_tab_mee"}
     */
    @RequestMapping(value = "/getAllProcessColumnById",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<QueryColumnTable>> getAllProcessColumnById(@RequestBody JSONObject jsonString){
        logger.info("数据加工字段血缘的查询参数为："+JSONObject.toJSONString(jsonString));
        ServerResponse<List<QueryColumnTable>> serverResponse = null;
        try{
//            List<String> outPutTableNameList = JSONObject.parseArray(jsonString.getString("outputTableNameEn"),String.class);
//            List<String> inputTableNameList = JSONObject.parseArray(jsonString.getString("inputTableNameEn"),String.class);
            String clickTableName = jsonString.getString("clickTableNameEn");
            String dbType = jsonString.getString("dbType");
            List<QueryColumnTable> queryColumnTables = fieldBloodlineService.getAllProcessColumnById(dbType , clickTableName);
            serverResponse = ServerResponse.asSucessResponse(queryColumnTables);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询数据加工字段信息报错："+e.getMessage());
            logger.error("查询数据加工字段信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     * 获取 数据加工字段血缘信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getDataProcessColumnLinkUrl")
    @ResponseBody
    public ServerResponse<ColumnBloodlineNode> getDataProcessColumnLinkUrl(@RequestBody JSONObject jsonObject){
        ServerResponse<ColumnBloodlineNode> serverResponse = null;
        try{
            logger.info("查询数据加工类型指定字段的血缘信息的查询参数："+JSONObject.toJSONString(jsonObject));
            List<QueryColumnTable.Edges> edgeIds = jsonObject.getJSONArray("edgeIds").toJavaList(QueryColumnTable.Edges.class);
            List<String> nodeIds = jsonObject.getJSONArray("nodeIds").toJavaList(String.class);
            QueryColumnTable queryColumnTable = jsonObject.getObject("oneColumn",QueryColumnTable.class);
            String clickTableName = jsonObject.getString("clickTableName");
            String columnName = jsonObject.getString("columnName");
            ColumnBloodlineNode columnBloodlineNode = fieldBloodlineService.getDataProcessColumnLink(edgeIds,
                    nodeIds,queryColumnTable,clickTableName,columnName);
            serverResponse = ServerResponse.asSucessResponse(columnBloodlineNode);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询数据加工类型指定字段的血缘信息："+e.getMessage());
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("查询数据加工类型指定字段的血缘信息结束："+JSONObject.toJSONString(serverResponse));
        return serverResponse;
    }

}
