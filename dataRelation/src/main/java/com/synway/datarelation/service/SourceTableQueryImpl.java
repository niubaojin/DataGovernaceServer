package com.synway.datarelation.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.common.SourceTableExcel;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.pojo.databloodline.impactanalysis.StreamTableLevel;
import com.synway.datarelation.pojo.source_table_query.ApplicationSystemSource;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.DataBloodlineService;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName SourceTableQueryImpl
 * @description TODO
 * @date 2021/2/20 14:41
 */
@Service
public class SourceTableQueryImpl {
    private static Logger logger = LoggerFactory.getLogger(SourceTableQueryImpl.class);
    @Autowired
    private Environment env;
    @Autowired
    DataBloodlineService dataBloodlineServiceImpl;
    /**
     *     存储数据加工血缘信息
      */
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;


    public String start() throws Exception{
        ExcelWriter excelWriter = null;
        String userDir = env.getProperty("user.dir");
        logger.info("数据的地址为："+userDir);
        String file = userDir+"/data/txt";
        File excelFile = new File(userDir+File.separator+"data/应用血缘关系详细信息.xlsx");
        excelFile.deleteOnExit();
        excelWriter = EasyExcel.write(excelFile).build();
        File filePath = new File(file);
        if(filePath.exists()){
            String[] txtNameList = filePath.list();
            int num = 0;
            for(String txtName:txtNameList){
                List<ApplicationSystemSource> list = getData(new File(file+File.separator+txtName));
                // 然后再写excel文档
                WriteSheet writeSheetTwo = EasyExcel.writerSheet(num,txtName.replaceAll(".txt","")).head(ApplicationSystemSource.class)
                        .build();
                excelWriter.write(list,writeSheetTwo);
                num ++;
            }
        }
        if(excelWriter != null){
            excelWriter.finish();
        }
        return "下载完成";
    }

    private List<ApplicationSystemSource> getData(File file) throws Exception{
        List<String> dataList = FileUtils.readLines(file, StandardCharsets.UTF_8);
        List<ApplicationSystemSource> list = new ArrayList<>();
        dataList.forEach(data ->{
            String[] dataStr = data.split("\t");
            if(dataStr.length == 9){

                // 然后再一张表一张表的查询数据加工血缘信息
                // 现在23类型中获取到这张表的数据，然后存在，再获取这张表的影响分析数据，
                // 如果在23中没有，在输入表中存在这个表，说明这个是标准化直接写入，用该数据作为表的输入参数
                // 获取到上游表的所有数据
                String tableNameEn = "";
                List<RelationshipNode> relationshipNodes = cacheManageDataBloodlineServiceImpl.getProcessNodeByChild(dataStr[7]);
                if(!relationshipNodes.isEmpty()){
                    // 获取第一个的输出表名，获取该名字
                    tableNameEn = relationshipNodes.get(0).getChildrenTN();
                    try{
                        // 直接查询出最左端的表数据
                        Map<String,Integer> tableMap=new HashMap<>();
                        List<StreamTableLevel> streamTableLevelList = new ArrayList<>();
                        queryImpactAnalysisCache(tableNameEn,0,Constant.LEFT,tableMap, streamTableLevelList);
                        // 这个里面就是最上层的表信息，之后如何存储了
                        if(!streamTableLevelList.isEmpty()){
                            for(StreamTableLevel streamTableLevel:streamTableLevelList){
                                ApplicationSystemSource applicationSystemTable =
                                        new ApplicationSystemSource(dataStr[0],dataStr[1],dataStr[2],
                                                dataStr[3],dataStr[7],dataStr[8],dataStr[6]);
                                applicationSystemTable.setSourceId(streamTableLevel.getTableId());
                                applicationSystemTable.setSourceTableNameEn(streamTableLevel.getTableNameEn());
                                applicationSystemTable.setSourceTableNameCh(streamTableLevel.getTableNameCh());
                                list.add(applicationSystemTable);
                            }
                        }else{
                            // 没有源头也要插入进去
                            ApplicationSystemSource applicationSystemTable =
                                    new ApplicationSystemSource(dataStr[0],dataStr[1],dataStr[2],
                                            dataStr[3],dataStr[7],dataStr[8],dataStr[6]);
                            list.add(applicationSystemTable);
                        }
                    }catch (Exception e){
                        logger.error(ExceptionUtil.getExceptionTrace(e));
                    }
                }else{
                    // 查看输出表中是否有这个表 则做为标准化直接插入的数据 把那个表名做为原始表
                    // 就是看是否有同名的表名，去除项目名之后
                    List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(Constant.MAIN
                            ,dataStr[7]);
                    if(allDataProcessList.isEmpty()){
                        ApplicationSystemSource applicationSystemTable =
                                new ApplicationSystemSource(dataStr[0],dataStr[1],dataStr[2],
                                        dataStr[3],dataStr[7],dataStr[8],dataStr[6]);
                        list.add(applicationSystemTable);
                    }else{
                        Optional<RelationshipNode> data1 = allDataProcessList.stream().filter(d ->dataStr[7].equalsIgnoreCase(d.getParentTN().split("\\.")[1])
                                || dataStr[7].equalsIgnoreCase(d.getChildrenTN().split("\\.")[1])).distinct().findFirst();
                        if(data1.isPresent()){
                            ApplicationSystemSource applicationSystemTable =
                                    new ApplicationSystemSource(dataStr[0],dataStr[1],dataStr[2],
                                            dataStr[3],dataStr[7],dataStr[8],dataStr[6]);
                            if(dataStr[7].equalsIgnoreCase(data1.get().getParentTN().split("\\.")[1])){
                                applicationSystemTable.setSourceId(data1.get().getParentTableId());
                                applicationSystemTable.setSourceTableNameEn(data1.get().getParentTN());
                                applicationSystemTable.setSourceTableNameCh(data1.get().getParentTableNameCh());
                            }else{
                                applicationSystemTable.setSourceId(data1.get().getChildrenTableId());
                                applicationSystemTable.setSourceTableNameEn(data1.get().getChildrenTN());
                                applicationSystemTable.setSourceTableNameCh(data1.get().getChildrenTableNameCh());
                            }
                            list.add(applicationSystemTable);
                        }else{
                            ApplicationSystemSource applicationSystemTable =
                                    new ApplicationSystemSource(dataStr[0],dataStr[1],dataStr[2],
                                            dataStr[3],dataStr[7],dataStr[8],dataStr[6]);
                            list.add(applicationSystemTable);
                        }
                    }
                }
            }
        });
        return list;
    }


    /**
     * 获取指定表最上一层的表名称
     * @param tableName
     * @param flagNum
     * @param queryType
     * @param tableMap
     * @return
     */
    private String queryImpactAnalysisCache(String tableName,
                                            int flagNum ,
                                            String queryType,
                                            Map<String,Integer> tableMap,
                                            List<StreamTableLevel> streamTableLevelList){
        tableMap.put(tableName.toLowerCase(),1);
        List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(queryType
                ,tableName);
        if(allDataProcessList.isEmpty()){
            logger.info("查询到表"+tableName+"的子节点为空");
            return "";
        }
        Map<String ,List<RelationshipNode>> allDataProcessMap = new HashMap<>();
        if(queryType.equalsIgnoreCase(Constant.LEFT)){
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    (StringUtils.isNotEmpty(d.getParentTN()))
            ).collect(Collectors.groupingBy(
                    d->(d.getChildrenTN()+"&& "+d.getChildrenTableId()+"&& "+d.getChildrenTableNameCh()+"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType() )));
        }else{
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    StringUtils.isNotEmpty(d.getParentTN())
            ).collect(Collectors.groupingBy(
                    d->(d.getParentTN()+"&& "+d.getParentTableId()+"&& "+d.getParentTableNameCh() +"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType() )));
        }
        flagNum ++;;
        for(String parentNodeName:allDataProcessMap.keySet()){
            // 父节点不需要存储，直接拿子节点的表名
            List<RelationshipNode> oneRelationshipNode = allDataProcessMap.get(parentNodeName);
            for(RelationshipNode relationshipNode:oneRelationshipNode){
                String tableNameKey = Constant.RIGHT.equals(queryType)?
                        relationshipNode.getChildrenTN().trim().toLowerCase():relationshipNode.getParentTN().trim().toLowerCase();
                String tabNameChKey = Constant.RIGHT.equals(queryType)?
                        relationshipNode.getChildrenTableNameCh().trim():relationshipNode.getParentTableNameCh().trim();
                String tableId = Constant.RIGHT.equals(queryType)?
                        relationshipNode.getChildrenTableId():relationshipNode.getParentTableId();
                if(tableMap.getOrDefault(tableNameKey,0) != 0){
                    continue;
                }
                // 先存储上游/下游表的相关信息
                StreamTableLevel streamTableLevel1 = new StreamTableLevel();
                streamTableLevel1.setTableNameEn(tableNameKey);
                streamTableLevel1.setTableNameCh(tabNameChKey);
                streamTableLevel1.setTableId(tableId);
                int flagNumNew = flagNum;
                String  result = queryImpactAnalysisCache(tableNameKey, flagNumNew,queryType, tableMap,streamTableLevelList);
                if("".equals(result)){
                    streamTableLevelList.add(streamTableLevel1);
                }
            }
        }
        return null;
    }



    public void queryTable(String tableName){
        // 直接查询出最左端的表数据
        Map<String,Integer> tableMap=new HashMap<>();
        List<StreamTableLevel> streamTableLevelList = new ArrayList<>();
        queryImpactAnalysisCache(tableName,0,Constant.LEFT,tableMap, streamTableLevelList);
        // 这个里面就是最上层的表信息，之后如何存储了
        ExcelWriter excelWriter = null;
        String userDir = env.getProperty("user.dir");
//        String userDir =  "F:\\allSvn\\v1.7.0\\test11";
        logger.info("数据的地址为："+userDir);
        File excelFile = new File(userDir+File.separator+"应用血缘关系详细信息111.xlsx");
        excelWriter = EasyExcel.write(excelFile).build();

        if(!streamTableLevelList.isEmpty()){
            for(StreamTableLevel streamTableLevel:streamTableLevelList){
                // 查询出了这个，然后再查询出 数据处理那边的数据，获取最左边的源头
                try{
                    QueryDataBloodlineTable queryData = new QueryDataBloodlineTable();
                    queryData.setDataType(Constant.STANDARD);
                    List<SourceTableExcel>  sourceTableExcels = new ArrayList<>();
                    queryData.setTableNameEn(streamTableLevel.getTableNameEn().split("\\.")[1]);
                    DataBloodlineNode dataBloodlineNode = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,Constant.LEFT,"",true);
                    System.out.println(JSONObject.toJSONString(streamTableLevel));
                    List<String> list = new ArrayList<>();
                    for(DataBloodlineNode.Edges edge:dataBloodlineNode.getEdges()){
                        boolean flag = checkIsSourceId(edge.getSource(),dataBloodlineNode.getEdges(),list);
                        if(flag){
                            Optional<DataBloodlineNode.BloodNode> data = dataBloodlineNode.getNodes().stream()
                                    .filter( d->d.getId().equalsIgnoreCase(edge.getSource())).findFirst();
                            if(data.isPresent()){
                                SourceTableExcel sourceTableExcel = new SourceTableExcel();
                                sourceTableExcel.setSourceId(data.get().getSourceId());
                                sourceTableExcel.setSourceCodeCh(data.get().getSourceCodeCh());
                                sourceTableExcel.setSourceCode(data.get().getSourceCode());
                                sourceTableExcel.setNodeName(data.get().getNodeName());
                                sourceTableExcel.setSourceFactory(data.get().getSourceFactoryCh());
                                sourceTableExcels.add(sourceTableExcel);
                            }
                            list.add(edge.getSource());
                        }
                    }

                    // 然后再写excel文档
                    WriteSheet writeSheetTwo = EasyExcel.writerSheet(1,"测试数据").head(SourceTableExcel.class)
                            .build();
                    excelWriter.write(sourceTableExcels,writeSheetTwo);
                    if(excelWriter != null){
                        excelWriter.finish();
                    }
                }catch (Exception e){

                }

            }

        }
    }




    /**
     *
     * @param sourceId
     * @param edges
     * @param list
     */
    private boolean checkIsSourceId(String sourceId, List<DataBloodlineNode.Edges> edges,List<String> list){
        if(list.contains(sourceId)){
            return false;
        }
        Optional<DataBloodlineNode.Edges> data = edges.stream().filter(d -> d.getTarget().equalsIgnoreCase(sourceId)).findFirst();
        if(data.isPresent()){
            return false;
        }else{
            return true;
        }
    }
}
