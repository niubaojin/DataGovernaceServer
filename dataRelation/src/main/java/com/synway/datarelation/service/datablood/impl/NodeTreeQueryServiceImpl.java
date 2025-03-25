package com.synway.datarelation.service.datablood.impl;

import com.alibaba.fastjson.JSONObject;

import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.common.TreeNode;
import com.synway.datarelation.pojo.databloodline.DataBloodlineQueryParams;
import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import com.synway.datarelation.pojo.databloodline.QueryDataBloodlineTable;
import com.synway.datarelation.pojo.databloodline.RelationInfoRestTemolate;
import com.synway.datarelation.service.datablood.ApplicationSystemNodeService;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.NodeTreeQueryService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;

@Service
public class NodeTreeQueryServiceImpl implements NodeTreeQueryService {
    private Logger logger = LoggerFactory.getLogger(NodeTreeQueryServiceImpl.class);
    @Autowired
    DataBloodlineDao dataBloodlineDao;
    @Autowired
    ApplicationSystemNodeService applicationSystemNodeServiceImpl;
    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    private Environment environment;

    /**
     * 查询各个节点类型的返回数据
     * @param dataBloodlineQueryParams
     * @return
     */
    @Override
    public TreeNode getTreeNodeInfo(DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception {
        TreeNode treeNode = new TreeNode();
        switch (dataBloodlineQueryParams.getNodeQueryType()){
            case Constant.ACCESS:
                treeNode = getAccessTreeNode(dataBloodlineQueryParams);
                break;
            case Constant.STANDARD:
                treeNode = getStandardTreeNode(dataBloodlineQueryParams);
                break;
            case Constant.DATAPROCESS:
                treeNode = getDataProcessTreeNode(dataBloodlineQueryParams);
                break;
            case Constant.OPERATINGSYSTEM:
                treeNode = getDataOperatingSystemTreeNode(dataBloodlineQueryParams);
                break;
            default:
                throw new NullPointerException("没有编写该类型的查询方法");
        }
        return treeNode;
    }

    public TreeNode getDataOperatingSystemTreeNode(DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception{
        TreeNode result = applicationSystemNodeServiceImpl.getApplicationSystemNodeQuery(dataBloodlineQueryParams ,1 ,true);
        result.setErrorMessage("获取应用系统血缘成功");
        return result;
    }

    /**
     * 20210409  数据加工节点的锁定需要用到唯一的id 所以 每个节点的id不能变化
     * @param dataBloodlineQueryParams
     * @return
     * @throws Exception
     */
    public TreeNode getDataProcessTreeNode(DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception{
        TreeNode oneTreeNode = new TreeNode();
        try{
            int nodeId = 111;
            oneTreeNode.setNodeId(nodeId);
            // 查询缓存数据 如果是v3版本 则查询的是自己解析的表 如果是v2版本 则查询 陈亮组解析的表
            // 20201225 v2版本使用自己解析的表数据，不再使用陈亮组解析的数据
            // 20210312 新增了模糊查询还是精确查询的参数
            List<QueryDataBloodlineTable> queryDataBloodlineTableList = dataBloodlineDao.getDataProcessTableSearch(
                    dataBloodlineQueryParams.getQueryinfo(),StringUtils.isNotBlank(dataBloodlineQueryParams.getQuerytype())
                            ?dataBloodlineQueryParams.getQuerytype().toLowerCase():"like");
            oneTreeNode.setLabel("数据加工("+String.valueOf(queryDataBloodlineTableList.size())+")");
            List<TreeNode> oneChildTreeNode = new ArrayList<>();
            for(QueryDataBloodlineTable queryDataBloodlineTable:queryDataBloodlineTableList){
                TreeNode twoTreeNode = new TreeNode();
                String label = StringUtils.isNotEmpty(queryDataBloodlineTable.getTableNameCh())?
                        queryDataBloodlineTable.getTableNameCh()+"("+queryDataBloodlineTable.getTableNameEn()+")":queryDataBloodlineTable.getTableNameEn();
                twoTreeNode.setLabel(label);
                twoTreeNode.setNodeId(Math.abs(UUID.nameUUIDFromBytes(label.getBytes()).hashCode()));
                twoTreeNode.setQueryDataBloodlineTable(queryDataBloodlineTable);
                oneChildTreeNode.add(twoTreeNode);
            }
            oneTreeNode.setChildren(oneChildTreeNode);
            // 从数据加工中查询到的数据为
            logger.info("从数据加工中搜索到的数据为"+JSONObject.toJSONString(queryDataBloodlineTableList));
            oneTreeNode.setErrorMessage("查询数据加工血缘成功");
        }catch (Exception e){
            oneTreeNode.setLabel("数据加工(0)");
            oneTreeNode.setErrorMessage("从数据加工中拼接左侧树报错"+ e.getMessage());
            logger.error("从数据加工中拼接左侧树报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return oneTreeNode;
    }


    /**
     *  查询标准化血缘的左侧树展示
     * @param dataBloodlineQueryParams
     * @return
     * @throws Exception
     */
    public TreeNode getStandardTreeNode(DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception{
        int nodeId = 1;
        TreeNode twoTreeNode = new TreeNode();
        try{
            twoTreeNode.setNodeId(nodeId);
            RelationInfoRestTemolate result = restTemplateHandle.queryStandardRelationInfo(dataBloodlineQueryParams);
            if("ok".equalsIgnoreCase(result.getReqRet())){
                List<QueryBloodlineRelationInfo> relationInfoList = result.getReqInfo();
                // 20210304 进行二次筛选，只需要获取 筛选指定的数据
                relationInfoList= new ArrayList<>(relationInfoList.stream().filter(d -> {
                    return StringUtils.containsIgnoreCase(d.getTargetTableName(),dataBloodlineQueryParams.getQueryinfo())
                            || StringUtils.containsIgnoreCase(d.getTargetEngName(),dataBloodlineQueryParams.getQueryinfo())
                            || StringUtils.containsIgnoreCase(d.getTargetChiName(),dataBloodlineQueryParams.getQueryinfo());
                }).collect(toMap(d -> (d.getTargetEngName()+
                        "|"+d.getTargetSysId()+"|"+d.getTargetProjectName()+"."+d.getTargetTableName()),
                        e->e,(exists, replacement)-> exists)).values());
                List<TreeNode> twoChildTreeNode = new ArrayList<>();
                for(QueryBloodlineRelationInfo queryBloodlineRelationInfo : relationInfoList){
                    TreeNode twoTreeChildNode = new TreeNode();
                    QueryDataBloodlineTable  queryDataBloodlineTable = new QueryDataBloodlineTable();
                    queryDataBloodlineTable.setDataType(Constant.STANDARD);
                    if(StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetTableName())){
                        queryDataBloodlineTable.setTableNameEn(queryBloodlineRelationInfo.getTargetProjectName()+"."
                                +queryBloodlineRelationInfo.getTargetTableName());
                    }
                    String showName = "";
                    if(StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetChiName())){
                        showName = StringUtils.isNotBlank(queryDataBloodlineTable.getTableNameEn())?
                                queryBloodlineRelationInfo.getTargetChiName()+"|"+queryDataBloodlineTable.getTableNameEn():
                                queryBloodlineRelationInfo.getTargetChiName()+"|"+queryBloodlineRelationInfo.getSourceEngName();
                    }else{
                        showName =queryBloodlineRelationInfo.getTargetEngName();
                    }
                    queryDataBloodlineTable.setTableShowData(showName.toLowerCase());
                    queryDataBloodlineTable.setSourceId(queryBloodlineRelationInfo.getSourceEngName());
                    queryDataBloodlineTable.setSourceCode(queryBloodlineRelationInfo.getSourceSysId());
                    queryDataBloodlineTable.setSourceCodeCh(queryBloodlineRelationInfo.getSourceSysChiName());
                    queryDataBloodlineTable.setTableId(queryBloodlineRelationInfo.getTargetEngName());
                    queryDataBloodlineTable.setTargetCodeCh(queryBloodlineRelationInfo.getTargetSysChiName());
                    queryDataBloodlineTable.setTargetCode(queryBloodlineRelationInfo.getTargetSysId());

                    queryDataBloodlineTable.setTableNameCh(queryBloodlineRelationInfo.getTargetChiName());
                    twoTreeChildNode.setQueryDataBloodlineTable(queryDataBloodlineTable);
                    nodeId = nodeId+1;
                    twoTreeChildNode.setNodeId(nodeId);
                    twoTreeChildNode.setLabel(showName.toLowerCase());
                    twoChildTreeNode.add(twoTreeChildNode);
                }
                twoTreeNode.setLabel("数据处理("+relationInfoList.size()+")");
                twoTreeNode.setChildren(twoChildTreeNode);
                twoTreeNode.setErrorMessage(" 从标准化接口获取数据血缘成功");
            }else{
                twoTreeNode.setLabel("数据处理(0)");
                twoTreeNode.setErrorMessage(" 从标准化接口获取数据血缘报错："+result.getError());
            }
        }catch (Exception e){
            logger.error("获取标准化血缘信息报错"+ExceptionUtil.getExceptionTrace(e));
            twoTreeNode.setLabel("数据处理(0)");
            twoTreeNode.setErrorMessage(" 从标准化接口获取数据血缘报错："+e.getMessage());
        }
        return twoTreeNode;
    }


    /**
     *  开始查询数据接入血缘的左侧树展示
     * @param dataBloodlineQueryParams
     * @return
     */
    public TreeNode getAccessTreeNode(DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception{
        //  从数据接入中查询出数据
        TreeNode threeTreeNode = new TreeNode();
        int nodeId = 1;
        try{
            threeTreeNode.setNodeId(nodeId);
//            RelationInfoRestTemolate resultAccess = restTemplateHandle.queryAccessRelationInfo(dataBloodlineQueryParams);
            RelationInfoRestTemolate resultAccess = cacheManageDataBloodlineServiceImpl.getAccessRelationInfo(dataBloodlineQueryParams);
            if(resultAccess.getReqRet().equalsIgnoreCase("ok")){
                List<QueryBloodlineRelationInfo> relationInfoList = resultAccess.getReqInfo();
                List<TreeNode> threeChildTreeNode = new ArrayList<>();
                if(relationInfoList != null){
                    for(QueryBloodlineRelationInfo queryBloodlineRelationInfo : relationInfoList) {
                        if(StringUtils.isNumeric(queryBloodlineRelationInfo.getSourceChiName())
                                && !StringUtils.isNumeric(queryBloodlineRelationInfo.getSourceSysId())){
                            String name = ""+queryBloodlineRelationInfo.getSourceChiName();
                            queryBloodlineRelationInfo.setSourceChiName(queryBloodlineRelationInfo.getSourceSysId());
                            queryBloodlineRelationInfo.setSourceSysId(name);
                        }
                        TreeNode threeTreeChildNode = new TreeNode();
                        QueryDataBloodlineTable queryDataBloodlineTable = new QueryDataBloodlineTable();
                        queryDataBloodlineTable.setDataType(Constant.ACCESS);
                        String showName = "";
                        showName +=StringUtils.isNotEmpty(queryBloodlineRelationInfo.getSourceChiName())?
                                queryBloodlineRelationInfo.getSourceChiName(): "|"+queryBloodlineRelationInfo.getSourceEngName();
                        showName +=StringUtils.isNotBlank(queryBloodlineRelationInfo.getSourceEngName())?
                                "|"+queryBloodlineRelationInfo.getSourceEngName():"|"+queryBloodlineRelationInfo.getSourceSysId();
                        if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetTableName())){
                            showName += " -> "+queryBloodlineRelationInfo.getTargetTableName();
                        }
                        queryDataBloodlineTable.setTableShowData(showName.toUpperCase());
                        queryDataBloodlineTable.setSourceId(queryBloodlineRelationInfo.getSourceEngName());
                        queryDataBloodlineTable.setSourceCode(queryBloodlineRelationInfo.getSourceSysId());
                        if(StringUtils.isEmpty(queryDataBloodlineTable.getSourceId())||StringUtils.isEmpty(queryDataBloodlineTable.getSourceCode())){
                            continue;
                        }
                        queryDataBloodlineTable.setSourceCodeCh(queryBloodlineRelationInfo.getSourceSysChiName());
                        queryDataBloodlineTable.setTableId(queryBloodlineRelationInfo.getTargetEngName());
                        queryDataBloodlineTable.setTargetCodeCh(queryBloodlineRelationInfo.getTargetSysChiName());
                        queryDataBloodlineTable.setTargetCode(queryBloodlineRelationInfo.getTargetSysId());
                        if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetTableName())){
                            queryDataBloodlineTable.setTableNameEn(queryBloodlineRelationInfo.getTargetProjectName()+"."
                                    +queryBloodlineRelationInfo.getTargetTableName());
                        }
                        queryDataBloodlineTable.setTableNameCh(queryBloodlineRelationInfo.getTargetChiName());
                        threeTreeChildNode.setQueryDataBloodlineTable(queryDataBloodlineTable);
                        nodeId = nodeId+1;
                        threeTreeChildNode.setNodeId(nodeId);
                        threeTreeChildNode.setLabel(showName.toLowerCase());
                        threeChildTreeNode.add(threeTreeChildNode);
                    }
                }
                threeTreeNode.setLabel("数据接入("+threeChildTreeNode.size()+")");
                threeTreeNode.setChildren(threeChildTreeNode);
                threeTreeNode.setErrorMessage(" 从数据接入接口获取数据血缘成功");
            }else{
                threeTreeNode.setLabel("数据接入(0)");
                threeTreeNode.setErrorMessage(" 从数据接入接口获取数据血缘报错："+resultAccess.getError());
            }
        }catch (Exception e){
            threeTreeNode.setLabel("数据接入(0)");
            threeTreeNode.setErrorMessage(" 从数据接入接口获取数据血缘报错："+e.getMessage());
        }
        return threeTreeNode;
    }
}
