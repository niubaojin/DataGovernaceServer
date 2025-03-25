package com.synway.datarelation.util;

import com.alibaba.excel.util.CollectionUtils;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
import com.synway.datarelation.pojo.databloodline.TrackQueryData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 一些能提出来的方法
 * @author wangdongwei
 * @date 2021/5/18 13:30
 */
@Slf4j
public class DataBloodlineUtils {

    /**
     * 获取所有节点数据中的表名信息，用于页面上的提示
     * @param nodeList
     * @return
     */
    public static List<TrackQueryData> getTrackQueryDataByNode(List<DataBloodlineNode.BloodNode> nodeList){
        // 获取所有的节点名称放在 tableNameList里面
        List<TrackQueryData> nodeNameList = new ArrayList<>();
        if(CollectionUtils.isEmpty(nodeList)){
            return nodeNameList;
        }
        nodeList.stream().filter(d -> StringUtils.isNotBlank(d.getId()))
                .distinct().sorted((s1, s2) ->
                Collator.getInstance(Locale.CHINA).compare(s1.getNodeName(), s2.getNodeName()))
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(t -> t.getNodeName() + t.getTableNameEn()))),ArrayList::new))
                .forEach(d ->{
                    if(StringUtils.isBlank(d.getTableNameEn()) ||
                            StringUtils.equalsIgnoreCase(d.getNodeName(),d.getTableNameEn())){
                        nodeNameList.add(new TrackQueryData(d.getId(),d.getNodeName()));
                    }else{
                        nodeNameList.add(new TrackQueryData(d.getId(),d.getNodeName()+"("+d.getTableNameEn()+")"));
                    }
                });
        return nodeNameList;
    }

    /**
     * 隐藏接入和处理之间的重复节点 生成文件的节点
     * @param dataBloodlineNode
     */
    public static void hiddenNode(DataBloodlineNode dataBloodlineNode ){
        try{
            if(dataBloodlineNode.getNodes()!= null && !dataBloodlineNode.getNodes().isEmpty()
                    && dataBloodlineNode.getEdges() != null && !dataBloodlineNode.getEdges().isEmpty()){
                // 隐藏接入和处理之间的重复节点
                hiddenAccessNode(dataBloodlineNode);
            }
        }catch (Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
    }


    /**
     * 如果存在数据接入血缘和数据处理血缘 则把生成文件那个节点隐藏掉
     * @param dataBloodlineNode
     */
    public static void hiddenAccessNode(DataBloodlineNode dataBloodlineNode ){
        // 1: 先找出 nodeName 为生成文件的这个接入节点信息，然后再根据tableid获取数据处理节点中相关信息，获取这两个节点的id信息，
        // 2: 删除 接入节点的血缘信息 再将删除后的节点重新连接起来
        // 3: 上一层已经做了是否为空的判断，这一层不需要做
        List<DataBloodlineNode.BloodNode> needDeletedList = dataBloodlineNode.getNodes().stream().filter(d->
                StringUtils.equalsIgnoreCase(Common.GENERATE_FILE,d.getNodeName())
                && StringUtils.equalsIgnoreCase(d.getDataType(),Constant.ACCESS)
                && StringUtils.isNotBlank(d.getTableId())).collect(Collectors.toList());
        boolean flag = dataBloodlineNode.getNodes().parallelStream().filter(d -> StringUtils.isNotBlank(d.getId()))
                .anyMatch(d -> StringUtils.equalsIgnoreCase(d.getDataType(),Constant.STANDARD));
        if(!needDeletedList.isEmpty() && flag){
            // 需要删除的节点信息  根据tableid去数据
            needDeletedList.forEach( d-> {
                // 获取这个节点的所有前置节点，用于新边的生成
                List<String> sourceIds = dataBloodlineNode.getEdges().parallelStream().filter(d1 -> StringUtils.equalsIgnoreCase(d1.getTarget(),d.getId()))
                        .map(DataBloodlineNode.Edges::getSource).collect(Collectors.toList());

                // 获取数据处理节点的数据 sourceId的值与 需删除节点的 tableid值相同
                Iterator<DataBloodlineNode.BloodNode> data = dataBloodlineNode.getNodes().iterator();
                while(data.hasNext()){
                    DataBloodlineNode.BloodNode d1 = data.next();
                    if(StringUtils.equalsIgnoreCase(d1.getSourceId(),d.getTableId())
                            && StringUtils.equalsIgnoreCase(d1.getSourceCode(),d.getTargetCode())
                            && StringUtils.equalsIgnoreCase(d1.getDataType(),Constant.STANDARD)){
                        // 此时需要删除边数据  删除接入与数据处理的边
                        dataBloodlineNode.getEdges().removeIf(d3->StringUtils.equalsIgnoreCase(d3.getSource(),d.getId())
                                &&StringUtils.equalsIgnoreCase(d3.getTarget(),d1.getId()));
                        // 将前置节点和目标边直接关联出来
                        sourceIds.forEach(sourceId ->{
                            dataBloodlineNode.getEdges().add(new DataBloodlineNode.Edges(sourceId,d1.getId(),true,Constant.ACCESS));
                        });
                    }
                }
                // 删除接入这边的边
                sourceIds.forEach(sourceId ->{
                    dataBloodlineNode.getEdges().removeIf(d4 -> StringUtils.equalsIgnoreCase(d4.getSource(),sourceId)
                            && StringUtils.equalsIgnoreCase(d4.getTarget(),d.getId()));
                });
                //删除节点
                dataBloodlineNode.getNodes().removeIf(d5 -> StringUtils.equalsIgnoreCase(d5.getId(),d.getId()));
                // 删除显示的节点名
                dataBloodlineNode.getNodeNameList().removeIf(d6 -> StringUtils.equalsIgnoreCase(d6.getNodeId(),d.getId()));
            });
        }
    }
}
