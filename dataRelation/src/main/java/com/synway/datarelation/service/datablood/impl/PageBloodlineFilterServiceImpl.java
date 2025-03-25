package com.synway.datarelation.service.datablood.impl;

import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.databloodline.BloodlineFilterPage;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
import com.synway.datarelation.service.datablood.PageBloodlineFilterService;
import com.synway.datarelation.util.DataBloodlineUtils;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author wangdongwei
 * @date 2021/3/12 11:27
 */
@Service
public class PageBloodlineFilterServiceImpl implements PageBloodlineFilterService {

    private static Logger logger = LoggerFactory.getLogger(PageBloodlineFilterServiceImpl.class);


    /**
     * 1：先筛选节点数组中的所有符合规则的节点信息
     * 2：获取不符合规则的节点id信息，然后再修改边信息
     * @param bloodlineFilterPage
     * @return
     */
    @Override
    public DataBloodlineNode PageBloodlineFilter(BloodlineFilterPage bloodlineFilterPage) {
        if(StringUtils.isBlank(bloodlineFilterPage.getClassifyFilter())
                &&  bloodlineFilterPage.getPageData() == null
                &&  StringUtils.isBlank(bloodlineFilterPage.getDataTrackFilter())){
            return bloodlineFilterPage.getPageData();
        }
        List<DataBloodlineNode.BloodNode> nodeList = bloodlineFilterPage.getPageData().getNodes();
        if(nodeList == null ||nodeList.isEmpty()){
            return bloodlineFilterPage.getPageData();
        }
        DataBloodlineNode data = bloodlineFilterPage.getPageData();
        // 筛选数据轨迹的相关信息 如果是加工流向 ，则需要筛选以应用血缘为主干的，如果所有节点中没有应用的数据，则不筛选
        // 如果是数据流向 则不需要筛选
        if(StringUtils.equalsIgnoreCase(Constant.PROCESS_FLOW,bloodlineFilterPage.getDataTrackFilter())){
            nodeList = filterProcessFlowNode(data);
        }
        // 如果classifyFilter 是 全部，则不进行筛选
        if(StringUtils.equalsIgnoreCase(Constant.ALL_FILTER,bloodlineFilterPage.getClassifyFilter())){
            return bloodlineFilterPage.getPageData();
        }
        // 筛选节点信息  数据过滤  如果数据过滤是组织分类 则而需要二级筛选，再筛选一次
        nodeList = nodeList.stream().filter(d ->{
            if(!Constant.DATAPROCESS.equalsIgnoreCase(d.getDataType())){
                return true;
            }
            if(DataBloodlineNode.BloodNode.NON_ORGANIZATION_CLASSIFY
                    .equalsIgnoreCase(bloodlineFilterPage.getClassifyFilter())){
                return StringUtils.equalsIgnoreCase(
                        bloodlineFilterPage.getClassifyFilter(),d.getClassifyFilter());
            }else{
                boolean flag = StringUtils.equalsIgnoreCase(
                        bloodlineFilterPage.getClassifyFilter(),d.getClassifyFilter());
                if(!flag){
                    return false;
                }
                if(bloodlineFilterPage.getOrganizationClassifyList() == null ||
                        bloodlineFilterPage.getOrganizationClassifyList().isEmpty()){
                    return true;
                }else{
                    return bloodlineFilterPage.getOrganizationClassifyList().contains(d.getOrganizationClassifyName());
                }
            }
        }).collect(Collectors.toList());


        // 如果是空，则返回空
        if(nodeList.isEmpty()){
            data.setNodes(new ArrayList<>());
            data.setEdges(new ArrayList<>());
            return data;
        }
        // 需要去除掉到的节点信息 2个列表中的筛选，获取差异的
        List<String> nodePageList = nodeList.parallelStream().map(DataBloodlineNode.BloodNode::getId).collect(Collectors.toList());
        List<String> idFilterList = bloodlineFilterPage.getPageData().getNodes().parallelStream()
                .filter(d -> !nodePageList.contains(d.getId())).map(DataBloodlineNode.BloodNode::getId)
                .collect(Collectors.toList());
        // 筛选边的相关信息
        data.setNodes(nodeList);
        List<DataBloodlineNode.Edges> edgesList = data.getEdges().stream().filter(d ->
                !StringUtils.equalsIgnoreCase(d.getSource(),d.getTarget())).collect(Collectors.toList());
        if(edgesList == null || edgesList.isEmpty() || idFilterList.isEmpty()){
            return data;
        }
        List<DataBloodlineNode.Edges> edgesNewList = new ArrayList<>();
        List<DataBloodlineNode.Edges> list = null;
        List<String> oldList = null;
        for(DataBloodlineNode.Edges edges:edgesList){
            if(idFilterList.contains(edges.getSource()) && idFilterList.contains(edges.getTarget())){
                continue;
            }
            if(StringUtils.equalsIgnoreCase(edges.getSource(),edges.getTarget())){
                continue;
            }
            if(idFilterList.contains(edges.getSource())){
                // 获取 以edges.getSource() 这个目标节点的 输入节点id信息，然后再生成新的边信息\
                list = new ArrayList<>();
                oldList = new ArrayList<>();
                getNewEdge(edges.getSource(),edgesList,idFilterList,Constant.RIGHT,list,edges.getTarget(),oldList);
                edgesNewList.addAll(list);
            }else if(idFilterList.contains(edges.getTarget())){
                list = new ArrayList<>();
                oldList = new ArrayList<>();
                getNewEdge(edges.getTarget(),edgesList,idFilterList,Constant.LEFT,list,edges.getSource(),oldList);
                edgesNewList.addAll(list);
            }else{
                edgesNewList.add(edges);
            }
        }
        // 需要去重相关数据 边的数据
        // 20210329 如果变的source和target相同，则需要去除掉
        data.setEdges(new ArrayList<>(edgesNewList.stream().filter(d ->
                !StringUtils.equalsIgnoreCase(d.getSource(),d.getTarget()))
                .collect(toMap(d -> (d.getSource()+
                                "|"+d.getTarget()+"|"+d.getEdgeType()),
                        e->e,(exists, replacement)-> exists)).values()));
        return data;
    }

    @Override
    public DataBloodlineNode pageHiddenNode(DataBloodlineNode data) {
        if(data == null || CollectionUtils.isEmpty(data.getNodes())){
            logger.error("传入的节点信息为空，无法进行隐藏操作");
            throw new NullPointerException("传入的节点信息为空，无法进行隐藏操作");
        }
        /**
         * 先找出 应用系统名 中主节点的id信息，以它为根节点向前查询找到存在表名的应用血缘节点
         * 删除中间过程的边和节点信息，然后再以查询到的节点和根节点来创建新的边
         * moduleClassifyNum 这个参数为0表示是根节点信息
         */
        List<String> rootNodeIds = data.getNodes().stream().filter( d -> d.getModuleClassifyNum() == 0
                && StringUtils.equalsIgnoreCase(d.getDataType(),Constant.OPERATINGSYSTEM))
                .map(DataBloodlineNode.BloodNode::getId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(rootNodeIds)){
            logger.info("没有根节点信息");
        }
        List<DataBloodlineNode.BloodNode> oldNodeList = data.getNodes();
        List<DataBloodlineNode.Edges> oldEdgeList = data.getEdges();
        List<DataBloodlineNode.Edges> edges = new ArrayList<>();
        List<DataBloodlineNode.BloodNode> delNodeList = new ArrayList<>();
        List<DataBloodlineNode.Edges> delEdgeList = new ArrayList<>();
        List<String> leftNodeList = null;
        for(String rootNodeId:rootNodeIds){
            leftNodeList = new ArrayList<>();
            hiddenNodeIteration(oldNodeList,oldEdgeList,delEdgeList,delNodeList,leftNodeList,rootNodeId);
            // 通过获取到的节点信息
            leftNodeList.stream().distinct().forEach(nodeId ->{
                DataBloodlineNode.Edges edge = new DataBloodlineNode.Edges(nodeId,rootNodeId,true,
                        Constant.OPERATINGSYSTEM);
                edge.setLineType(Common.DOTTED_LINE);
                edges.add(edge);
            });
        };
        oldEdgeList.removeAll(delEdgeList);
        oldNodeList.removeAll(delNodeList);
        oldEdgeList.addAll(edges);
        // 之后再合并出最新的页面信息
        DataBloodlineNode dataNew = new DataBloodlineNode();
        dataNew.setNodes(oldNodeList);
        dataNew.setEdges(oldEdgeList);
        dataNew.setNodeNameList(DataBloodlineUtils.getTrackQueryDataByNode(oldNodeList));
        return dataNew;
    }

    /**
     *  迭代获取这个根节点对应的左侧到表的应用血缘节点信息
     *  不需要的边和节点都需要删除掉
     * @param oldNodeList   所有的节点信息
     * @param oldEdgeList    所有的边信息
     * @param leftNodeList   获取到的左侧节点id信息
     * @param rootNodeId     根节点信息
     */
    private void hiddenNodeIteration(List<DataBloodlineNode.BloodNode> oldNodeList,
                                     List<DataBloodlineNode.Edges> oldEdgeList,
                                     List<DataBloodlineNode.Edges> delEdgeList,
                                     List<DataBloodlineNode.BloodNode> delNodeList,
                                     List<String> leftNodeList,
                                     String rootNodeId){
        oldEdgeList.stream().filter( d-> StringUtils.equalsIgnoreCase(d.getTarget(),rootNodeId))
                .forEach(d ->{
                    DataBloodlineNode.BloodNode leftNode = getNodeById(d.getSource(),oldNodeList);
                    if(StringUtils.equalsIgnoreCase(d.getEdgeType(),Constant.OPERATINGSYSTEM)
                            && StringUtils.isBlank(leftNode.getTableNameEn())){
                        // 这个说明是 应用血缘模块之间的边，需要进行删除并且再继续查询下一个
                        delEdgeList.add(d);
                        delNodeList.add(leftNode);
                        hiddenNodeIteration(oldNodeList,oldEdgeList,delEdgeList,delNodeList,leftNodeList,d.getSource());
                    }else if(StringUtils.equalsIgnoreCase(d.getEdgeType(),Constant.OPERATINGSYSTEM)
                            && StringUtils.isNotBlank(leftNode.getTableNameEn())){
                        delEdgeList.add(d);
                        leftNodeList.add(d.getSource());
                    }
                });
    }


    /**
     *  存在一种情况，什么时候截止，如果最左边或者最右边不存在了节点，则会无限循环查询
     * @param id
     * @param allEdges
     * @param idFilterList
     * @param type   left 这个表示id的值是输出的id  right表示id的值是输入的id
     * @param idOldList 存储已经运行过的id信息
     * @return
     */
    private void getNewEdge(String id,List<DataBloodlineNode.Edges> allEdges,
                            List<String> idFilterList,
                            String type,
                            List<DataBloodlineNode.Edges> list,
                            String queryId,
                            List<String> idOldList){
        for(DataBloodlineNode.Edges edges:allEdges){
            if(Constant.RIGHT.equalsIgnoreCase(type) &&
                    StringUtils.equalsIgnoreCase(edges.getTarget(),id)){
                if(!idFilterList.contains(edges.getSource())){
                    DataBloodlineNode.Edges edges1 = new DataBloodlineNode.Edges(edges.getSource(),queryId,true,
                            edges.getEdgeType());
                    edges1.setLineType(Common.DOTTED_LINE);
                    list.add(edges1);
                }else if(!idOldList.contains(edges.getSource())){
                    idOldList.add(edges.getSource());
                    getNewEdge(edges.getSource(),allEdges,idFilterList,type,list,queryId,idOldList);
                }
            }else if(Constant.LEFT.equalsIgnoreCase(type) &&
                    StringUtils.equalsIgnoreCase(edges.getSource(),id)){
                if(!idFilterList.contains(edges.getTarget())){
                    DataBloodlineNode.Edges edges1 = new DataBloodlineNode.Edges(queryId,edges.getTarget(),true,
                            edges.getEdgeType());
                    edges1.setLineType(Common.DOTTED_LINE);
                    list.add(edges1);
                }else if(!idOldList.contains(edges.getTarget())){
                    idOldList.add(edges.getTarget());
                    getNewEdge(edges.getTarget(),allEdges,idFilterList,type,list,queryId,idOldList);
                }
            }
        }
    }

    /**
     * 筛选出加工流向的血缘信息
     * @param data
     * @return
     */
    private List<DataBloodlineNode.BloodNode> filterProcessFlowNode(DataBloodlineNode data){
        // 获取节点中应用血缘信息的所有节点
        List<String> opterationSystemIds=data.getNodes().stream().filter(d-> StringUtils.isNotBlank(d.getTableNameEn()) &&
                Constant.OPERATINGSYSTEM.equalsIgnoreCase(d.getDataType())).map(DataBloodlineNode.BloodNode::getId)
                .collect(Collectors.toList());
        if(opterationSystemIds.isEmpty()){
            return data.getNodes();
        }
        // 然后再遍历获取所有的前置节点信息，筛选出符合条件的节点
        List<DataBloodlineNode.BloodNode> nodes = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        opterationSystemIds.forEach(d ->{
            try{
                getLeftNodeEdge(d,data, nodes,map);
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
        });
        List<DataBloodlineNode.BloodNode> nodes1 = data.getNodes().stream().filter(d-> StringUtils.isBlank(d.getTableNameEn()) &&
                Constant.OPERATINGSYSTEM.equalsIgnoreCase(d.getDataType())).collect(Collectors.toList());
        nodes.addAll(nodes1);
        return nodes;
    }


    /**
     * 根据 id值获取左侧的的节点和边信息
     * @param id
     * @param data
     * @param nodes
     */
    private void getLeftNodeEdge(String id, DataBloodlineNode data, List<DataBloodlineNode.BloodNode> nodes,
                                 Map<String,Integer> map){
        map.putIfAbsent(id,1);
        for(DataBloodlineNode.Edges edge :data.getEdges()){
            if(!StringUtils.equalsIgnoreCase(edge.getSource(),edge.getTarget()) &&
                    StringUtils.equalsIgnoreCase(id, edge.getTarget())){
                if(map.getOrDefault(edge.getSource(),-1) == -1){
                    getLeftNodeEdge(edge.getSource(),data,nodes,map);
                }
            }
        }
        DataBloodlineNode.BloodNode rootNode = getNodeById(id,data);
        if(rootNode != null && !nodes.contains(rootNode)){
            nodes.add(rootNode);
        }
    }

    /**
     * 根据id值获取节点信息
     * @param id
     * @param data
     * @return
     */
    private DataBloodlineNode.BloodNode getNodeById(String id, DataBloodlineNode data){
        Optional<DataBloodlineNode.BloodNode> dataQuery = data.getNodes().parallelStream().filter(d->
                StringUtils.equalsIgnoreCase(d.getId(),id)).findFirst();
        return dataQuery.orElse(null);
    }

    /**
     * 根据id值获取节点信息
     * @param id
     * @param nodeList
     * @return
     */
    private DataBloodlineNode.BloodNode getNodeById(String id, List<DataBloodlineNode.BloodNode> nodeList){
        Optional<DataBloodlineNode.BloodNode> dataQuery = nodeList.parallelStream().filter(d->
                StringUtils.equalsIgnoreCase(d.getId(),id)).findFirst();
        return dataQuery.orElse(null);
    }


}
