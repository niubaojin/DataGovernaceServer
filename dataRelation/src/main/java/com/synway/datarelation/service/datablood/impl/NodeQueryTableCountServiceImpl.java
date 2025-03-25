package com.synway.datarelation.service.datablood.impl;

import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
import com.synway.datarelation.pojo.databloodline.ObjectStoreColumn;
import com.synway.datarelation.pojo.databloodline.impactanalysis.DetailedTableByClassify;
import com.synway.datarelation.pojo.databloodline.impactanalysis.ReceiveTable;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName NodeQueryTableCountServiceImpl
 * @description TODO
 * @date 2021/3/13 15:06
 */
@Service
public class NodeQueryTableCountServiceImpl {
    private Logger logger = LoggerFactory.getLogger(NodeQueryTableCountServiceImpl.class);

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Autowired
    private ConcurrentHashMap<String,List<ObjectStoreColumn>> storeInfoMap;

    /**
     * 获取数据处理模块节点的 项目名
     * @param dataBloodlineNode
     */
    public void getStandTableProjectName(DataBloodlineNode dataBloodlineNode){
        try{
            List<DataBloodlineNode.BloodNode> nodeList = dataBloodlineNode.getNodes();
            if(nodeList == null || nodeList.isEmpty()){
                return;
            }
            nodeList.parallelStream().filter(d -> StringUtils.isNotBlank(d.getTableNameEn())
                    &&StringUtils.equalsIgnoreCase(d.getDataType(),Constant.STANDARD) &&
                    (StringUtils.equalsIgnoreCase(d.getTargetDBEngName(),Constant.ODPS)||
                            StringUtils.equalsIgnoreCase(d.getTargetDBEngName(),Constant.HIVE))).forEach( d->{
                String[] data = d.getTableNameEn().split("\\.");
                String tableNameEn = (data.length == 1)?data[0]:data[1];
                List<ObjectStoreColumn> dataList = storeInfoMap.getOrDefault(tableNameEn.toLowerCase(),null);
                if(dataList != null && !dataList.isEmpty()){
                    d.setTableProject(dataList.get(0).getProjectName());
                    d.setTableNameEn((dataList.get(0).getProjectName()+"."+dataList.get(0).getTableName()).toLowerCase());
                }
            });
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }

    }

    /**
     * 根据表英文名查询表昨日的数据量
     * 20210331 新增节点所属工作流信息
     * @param dataBloodlineNode
     */
    public void getTableCountByName(DataBloodlineNode dataBloodlineNode){
        // 20210313 增加表数据量的查询
        try{
            List<DataBloodlineNode.BloodNode> nodeList = dataBloodlineNode.getNodes();
            List<DataBloodlineNode.Edges> edges = dataBloodlineNode.getEdges();
            if(nodeList == null || nodeList.isEmpty()){
                return;
            }
            boolean flag = (edges != null && !edges.isEmpty());
            List<ReceiveTable> paramList = new ArrayList<>();
            nodeList.parallelStream().filter(d -> StringUtils.isNotBlank(d.getTableNameEn())).forEach(d -> {
                if(Constant.DATAPROCESS.equalsIgnoreCase(d.getDataType()) ||
                        StringUtils.equalsIgnoreCase(Constant.STANDARD, d.getDataType())){
                    ReceiveTable params = new ReceiveTable();
                    String[] data = d.getTableNameEn().split("\\.");
                    params.setTableName((data.length == 1)?data[0]:data[1]);
                    params.setTableType("");
                    params.setProjectName((data.length == 1)?"":data[0]);
                    paramList.add(params);
                }
                if(flag && StringUtils.equalsIgnoreCase(Constant.DATAPROCESS,d.getDataType())){
                    List<String> flowNames = edges.parallelStream().filter( d1-> (StringUtils.equalsIgnoreCase(d1.getSource(),d.getId())
                            || StringUtils.equalsIgnoreCase(d1.getTarget(),d.getId())) &&
                            StringUtils.isNotBlank(d1.getWorkFlowName())).map(DataBloodlineNode.Edges::getWorkFlowName)
                            .distinct().collect(Collectors.toList());
                    d.setNodeFlowList(flowNames);
                }
            });
            if(!paramList.isEmpty()){
                List<DetailedTableByClassify> data = restTemplateHandle.getDetailedTable(paramList);
                Map<String,List<DetailedTableByClassify>> dataMap = data.stream().collect(Collectors.groupingBy(d ->
                        (d.getTableProject().replaceAll(d.getTableType()+"->","")+"."+d.getTableNameEn()).toUpperCase()));
                nodeList.parallelStream().forEach(d -> {
                    List<DetailedTableByClassify> queryDataList = null;
                    if((Constant.DATAPROCESS.equalsIgnoreCase(d.getDataType())) ||
                            StringUtils.equalsIgnoreCase(Constant.STANDARD, d.getDataType())){
                        queryDataList = dataMap.getOrDefault(d.getTableNameEn().toUpperCase(),null);
                    }
                    if(queryDataList != null && !queryDataList.isEmpty()){
                        d.setDailyIncreaseCount(StringUtils.isNotBlank(queryDataList.get(0).getYesterdayCount())?
                                queryDataList.get(0).getYesterdayCount()+"万条":"0万条");
                        d.setDataBaseType(StringUtils.isNotBlank(queryDataList.get(0).getTableType())?queryDataList.get(0).getTableType():"");
                        d.setUpdateTime(StringUtils.isNotBlank(queryDataList.get(0).getUpdateDate())?queryDataList.get(0).getUpdateDate():"");
                        d.setTableExist(true);
                    }
                });
            }
        }catch (Exception e){
            logger.error("获取"+ ExceptionUtil.getExceptionTrace(e));
        }

    }
}
