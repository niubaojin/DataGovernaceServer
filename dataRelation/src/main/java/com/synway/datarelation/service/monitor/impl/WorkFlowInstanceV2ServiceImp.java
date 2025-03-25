package com.synway.datarelation.service.monitor.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.synway.datarelation.dao.model.ModelStatisticDao;
import com.synway.datarelation.pojo.modelmonitor.*;
import com.synway.datarelation.service.monitor.WorkFlowInstanceService;
import com.synway.datarelation.util.DataIdeUtil;
import com.synway.datarelation.util.DateUtil;
import com.synway.datarelation.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模型监控V2版本
 * @author
 * @date 2020/1/13 19:28
 */
@Service("WorkFlowInstanceV2ServiceImp")
public class WorkFlowInstanceV2ServiceImp implements WorkFlowInstanceService {
    private static Logger logger = LoggerFactory.getLogger(WorkFlowInstanceV2ServiceImp.class);

    @Autowired
    private DataIdeUtil dataIdeUtil;
    @Autowired
    private ModelStatisticDao modelStatisticDao;

    /**
     * 统计项目
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void statisticProject()throws Exception{
        logger.info("开始查询所有的项目名");
        String resultStr = dataIdeUtil.findAllProjects();
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("没有统计出项目信息+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        List<ModelProject> delModelProjects = new ArrayList<>();
        List<ModelProject> newModelProjects = JSON.parseArray(resultObj.getReturnValue(),ModelProject.class);
        List<ModelProject> oldModelProjects = modelStatisticDao.selectModelProject();
        for (ModelProject tmpOldModelProject:oldModelProjects) {
            if(newModelProjects.contains(tmpOldModelProject)){
                newModelProjects.remove(tmpOldModelProject);
            }else{
                delModelProjects.add(tmpOldModelProject);
            }
        }
        int delCount = delModelProjects.size();
        for (int i = 0; i < delCount; i++) {
            modelStatisticDao.deleteModelProject(delModelProjects.get(i));
        }
        if(newModelProjects.size()>0){
            modelStatisticDao.insertModelProject(newModelProjects);
        }
        logger.info("统计项目数据成功，共删除旧项目【"+delModelProjects.size()+"】,新增新项目【"+newModelProjects.size()+"】");
    }

    /**
     * 统计节点
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void statisticNode()throws Exception{
        String resultStr = dataIdeUtil.findNodes(null);
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API获取节点信出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        //获取总的数据量
        int count = resultObj.getCount();
        int start=0;
        int limit = 0;
        List<ModelNodeInfo> newModelNodeInfos = new ArrayList<>();
        NodeQueryParam nodeQueryParam = new NodeQueryParam();
        //设置包含层级数据
        nodeQueryParam.setIncludeRelation(false);
        //如果阿里接口不能一下子获取那么多的数据，则使用分批次获取
        int queryBatch = count/8000;
//        for (int i = 0; i < queryBatch+1; i++) {
//            start = i*8000;
//            limit = i*8000+8000;
//            if(limit>count){
//                limit = count;
//            }
//            nodeQueryParam.setStart(start);
//            nodeQueryParam.setLimit(limit);
//            resultStr = dataIdeUtil.findNodes(nodeQueryParam);
//            resultObj = JSON.parseObject(resultStr,ResultObj.class);
//            returnCode = resultObj.getReturnCode();
//            if(!"0".equals(returnCode)){
//                logger.info("根据阿里API获取节点信息出错+【"+resultObj.getReturnMessage()+"】");
//                return;
//            }
//            newModelNodeInfos.addAll(JSON.parseArray(resultObj.getReturnValue(),ModelNodeInfo.class));
//        }
        start = 0;
        limit = count;
        nodeQueryParam.setStart(start);
        nodeQueryParam.setLimit(limit);
        resultStr = dataIdeUtil.findNodes(nodeQueryParam);
        resultObj = JSON.parseObject(resultStr,ResultObj.class);
        returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API获取节点信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        newModelNodeInfos.addAll(JSON.parseArray(resultObj.getReturnValue(),ModelNodeInfo.class));

        int removeCount=0;
        int addNewCount=0;
        List<ModelNodeInfo> delModelNodeInfos = new ArrayList<>();
        List<ModelNodeInfo> oldModelNodeInfos = modelStatisticDao.selectModelNodeInfo();
        for (ModelNodeInfo tmpOldModelNodeInfo:oldModelNodeInfos) {
            if(newModelNodeInfos.contains(tmpOldModelNodeInfo)){
                newModelNodeInfos.remove(tmpOldModelNodeInfo);
                removeCount++;
            }else{
                delModelNodeInfos.add(tmpOldModelNodeInfo);
                addNewCount++;
            }
        }
        //删除节点数据
        int delCount = delModelNodeInfos.size();
        for (int i = 0; i < delCount; i++) {
            modelStatisticDao.deleteModelNode(delModelNodeInfos.get(i));
        }
        List<ModelNodeInfo> tmp;
        count = newModelNodeInfos.size();
        int batch = count/500+1;
        int fromIndex;
        int toIndex;
        for (int i = 0; i < batch; i++) {
            fromIndex = i*500;
            toIndex = (i+1)*500;
            if(i==batch-1){
                toIndex = count;
            }
            tmp = newModelNodeInfos.subList(fromIndex,toIndex);
            if(tmp.size()>0){
                modelStatisticDao.insertModelNodeInfo(tmp);
            }
        }
        logger.info(String.format("统计节点数据成功，共删除旧节点[%s],新增新节点[%s]",removeCount,addNewCount));
    }

    /**
     * 统计实例
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void statisticFlowIns()throws Exception{
        //第一次统计
        if(firstStatisticFlag==0){
            firstStatisticFlowIns();
            return;
        }

        //查当天类型是工作流（98）的实例
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        nodeInsQueryParam.setNodeType(98);
        nodeInsQueryParam.setCreateTime(DateUtil.formatDateTime(DateUtil.getDayBegin(new Date()),DateUtil.DEFAULT_PATTERN_DATETIME));
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API获取工作流实例信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        int start = 0;
        int limit = resultObj.getCount();
        nodeInsQueryParam.setLimit(limit);
        nodeInsQueryParam.setStart(start);
        resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        resultObj = JSON.parseObject(resultStr,ResultObj.class);
        returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API获取工作流实例信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }

        List<ModelNodeInsInfo> newModelNodeInsInfos = JSON.parseArray(resultObj.getReturnValue(),ModelNodeInsInfo.class);
        //按projectName、flowName(nodeName)分组,根据instanceId获取今天最新的工作流实例集合
        newModelNodeInsInfos = new ArrayList<>(newModelNodeInsInfos.stream().collect(Collectors.groupingBy(e->{
                    //去除项目名中的prod
                    String projectName = e.getProjectName().replaceAll("_prod","");
                    e.setProjectName(projectName);
                    String flowName = e.getNodeName();
                    return projectName+"#"+flowName;
                },
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(ModelNodeInsInfo::getInstanceId)),Optional::get)))
                .values());

//        int updateCount=0;
//        int addNewCount=0;
//        List<ModelNodeInsInfo> insertModelNodeInsInfos = new ArrayList<>();
//        List<ModelNodeInsInfo> oldModelNodeInsInfos = modelStatisticDao.selectModelFlowNodeIns();
//        //如果projectName、flowName（nodeName）相同就更新，否则新增
//        for (ModelNodeInsInfo tmpOldModelNodeInsInfo:oldModelNodeInsInfos) {
//            if(newModelNodeInsInfos.contains(tmpOldModelNodeInsInfo)){
//                modelStatisticDao.updateFlowIns(tmpOldModelNodeInsInfo);
//                updateCount++;
//            }else{
//                insertModelNodeInsInfos.add(tmpOldModelNodeInsInfo);
//                addNewCount++;
//            }
//            // 当某个节点的status的状态为异常时，发送异常信息
////            errorSendRocketMq(tmpOldModelNodeInsInfo);
//        }
        modelStatisticDao.deleteModelFlowIns();
        List<ModelNodeInsInfo> tmpInsertModelNodeInsInfos;
        int insertCount = newModelNodeInsInfos.size();
        logger.info(String.format("工作流实例应该插入[%s]",insertCount));
        //如果入库数据量超过300，则进行批量入库
        if(insertCount>300){
            int batch = insertCount/300+1;
            int fromIndex;
            int toIndex;
            //按批次入库
            for (int i = 0; i < batch; i++) {
                fromIndex = i*300;
                toIndex = (i+1)*300;
                if(i==batch-1){
                    toIndex = insertCount;
                }
                tmpInsertModelNodeInsInfos = newModelNodeInsInfos.subList(fromIndex,toIndex);
                if(tmpInsertModelNodeInsInfos.size()>0){
                    modelStatisticDao.insertModelFlowNodeIns(tmpInsertModelNodeInsInfos);
                }
            }
        }else{
            if(null!=newModelNodeInsInfos&&newModelNodeInsInfos.size()>0){
                modelStatisticDao.insertModelFlowNodeIns(newModelNodeInsInfos);
            }
        }
    }

    /**用来标识程序第一次启动获取数据是否成功*/
    private static int firstStatisticFlag = 0;
    @Transactional(rollbackFor = Exception.class)
    public void firstStatisticFlowIns()throws Exception{
        logger.info("工作流开始第一次统计");
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        //节点类型为98的是工作流数据,当天的
        nodeInsQueryParam.setNodeType(98);
        nodeInsQueryParam.setCreateTime(DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API第一次获取工作流实例信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        int start = 0;
        int limit = resultObj.getCount();
        nodeInsQueryParam.setLimit(limit);
        nodeInsQueryParam.setStart(start);
        resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        resultObj = JSON.parseObject(resultStr,ResultObj.class);
        returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("根据阿里API第一次获取工作流实例信息出错+【"+resultObj.getReturnMessage()+"】");
            return;
        }

        List<ModelNodeInsInfo> newModelNodeInsInfos = JSON.parseArray(resultObj.getReturnValue(),ModelNodeInsInfo.class);
        //按projectName、flowName(nodeName)分组,根据instanceId获取今天最新的工作流实例集合
        newModelNodeInsInfos = new ArrayList<>(newModelNodeInsInfos.stream().collect(Collectors.groupingBy(e->{
//            String projectName = e.getProjectName();
            //去除项目名中的prod
            String projectName = e.getProjectName().replaceAll("_prod","");
            e.setProjectName(projectName);
            String flowName = e.getNodeName();
            return projectName+"#"+flowName;
        }, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(ModelNodeInsInfo::getInstanceId)),Optional::get)))
                .values());
        //删除数据
        modelStatisticDao.deleteModelFlowIns();
        List<ModelNodeInsInfo> tmpInsertModelNodeInsInfos;
        int insertCount = newModelNodeInsInfos.size();
        int batch = insertCount/300+1;
        int fromIndex;
        int toIndex;
        //按批次入库
        for (int i = 0; i < batch; i++) {
            fromIndex = i*300;
            toIndex = (i+1)*300;
            if(i==batch-1){
                toIndex = insertCount;
            }
            tmpInsertModelNodeInsInfos = newModelNodeInsInfos.subList(fromIndex,toIndex);
            if(tmpInsertModelNodeInsInfos.size()>0){
                modelStatisticDao.insertModelFlowNodeIns(tmpInsertModelNodeInsInfos);
            }
        }
        firstStatisticFlag = 1;
    }


    //    @Scheduled(cron = "0/10 * * * * ?")
    public void getNode()throws Exception{
        String resultStr = dataIdeUtil.findNodes(null);
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("没有统计出节点信息+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        //获取总的数据量
        int count = resultObj.getCount();
        int batch = count/5000+1;
        int start;
        int limit = 5000;
        NodeQueryParam nodeQueryParam = new NodeQueryParam();
        //设置包含层级数据
        nodeQueryParam.setIncludeRelation(true);
        //批量查询并入库
        for (int i = 0; i < batch; i++) {
            start = i*5000;
            if(i==batch-1){
                limit = count - start;
            }
            nodeQueryParam.setStart(start);
            nodeQueryParam.setLimit(limit);
            resultStr = dataIdeUtil.findNodes(nodeQueryParam);
            resultObj = JSON.parseObject(resultStr,ResultObj.class);
            if(!"0".equals(returnCode)){
                throw new Exception("在获取第【"+i+"】批节点数据报错，总共的数据量为【"+count+"】，报错原因【"+resultObj.getReturnMessage()+"】");
            }
            FileUtil.writeToFile("D://node/node_"+i+".txt",resultStr,false);
        }
    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void getNodeIns()throws Exception{
        String resultStr = dataIdeUtil.findNodeIns(null);
        ResultObj resultObj = JSON.parseObject(resultStr,ResultObj.class);
        String returnCode = resultObj.getReturnCode();
        if(!"0".equals(returnCode)){
            logger.info("没有统计出节点信息+【"+resultObj.getReturnMessage()+"】");
            return;
        }
        //获取总的数据量
        int count = resultObj.getCount();
        int batch = count/5000+1;
        int start;
        int limit = 5000;
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        List<ModelNodeInsInfo> modelNodeInsInfos;
        //批量查询并入库
        for (int i = 0; i < batch; i++) {
            start = i*5000;
            if(i==batch-1){
                limit = count - start;
            }
            nodeInsQueryParam.setStart(start);
            nodeInsQueryParam.setLimit(limit);
            resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
            resultObj = JSON.parseObject(resultStr,ResultObj.class);
            if(!"0".equals(returnCode)){
                throw new Exception("在获取第【"+i+"】批节点实例数据报错，总共的数据量为【"+count+"】，报错原因【"+resultObj.getReturnMessage()+"】");
            }
            modelNodeInsInfos = JSONObject.parseArray(resultObj.getReturnValue(),ModelNodeInsInfo.class);
            FileUtil.writeToFile("D://nodeIns/ins_"+i+".txt",resultStr,false);
        }
    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void getNodeInsByFlowName()throws Exception {
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        nodeInsQueryParam.setFlowName("$atcloud_flow");
        nodeInsQueryParam.setSearchText("种子和标签整合");
        nodeInsQueryParam.setStart(0);
        nodeInsQueryParam.setLimit(1000);
        List<ModelNodeInsInfo> modelNodeInsInfos;
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr, ResultObj.class);
        modelNodeInsInfos = JSONObject.parseArray(resultObj.getReturnValue(), ModelNodeInsInfo.class);
        System.out.println(modelNodeInsInfos.get(0).getInstanceId());
        Arrays.sort(modelNodeInsInfos.toArray(new ModelNodeInsInfo[]{}), new Comparator<ModelNodeInsInfo>() {
            @Override
            public int compare(ModelNodeInsInfo o1, ModelNodeInsInfo o2) {
                int flag = 0;
                if(o1.getInstanceId()>o2.getInstanceId()){
                    flag = 1;
                }
                if(o1.getInstanceId()<o2.getInstanceId()){
                    flag = -1;
                }
                return flag;
            }

        });
        System.out.println(modelNodeInsInfos.get(0).getInstanceId());
        System.out.println(modelNodeInsInfos);

    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void getNodeInsByInsId()throws Exception {
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        String instanceIds = "44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487,44335273,44329323,44282872,44288821,44184542,44190487";
        nodeInsQueryParam.setInstanceIds(instanceIds);
        nodeInsQueryParam.setStart(0);
        nodeInsQueryParam.setLimit(1000);
        List<ModelNodeInsInfo> modelNodeInsInfos;
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr, ResultObj.class);
        modelNodeInsInfos = JSONObject.parseArray(resultObj.getReturnValue(), ModelNodeInsInfo.class);
        System.out.println(modelNodeInsInfos);

    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void getNodeInsTodayAll()throws Exception {
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        nodeInsQueryParam.setCreateTime(DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
        nodeInsQueryParam.setStart(0);
        nodeInsQueryParam.setLimit(45000);
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr, ResultObj.class);
        List<ModelNodeInsInfo> modelNodeInsInfos = JSONObject.parseArray(resultObj.getReturnValue(), ModelNodeInsInfo.class);
        int aa = modelNodeInsInfos.size();

        boolean x1 = true;
        ModelNodeInsInfo tmpI;
        ModelNodeInsInfo tmpJ;
        for (int i = 0; i < aa; i++) {
            tmpI = modelNodeInsInfos.get(i);
            for (int j = i+1; j < aa; j++) {
                tmpJ = modelNodeInsInfos.get(j);
                if(tmpI.getInstanceId()>tmpJ.getInstanceId()){
                    System.out.println("xxxxxxxxxxxxxxxxxx");
                    x1 = false;
                    break;
                }
            }
            if(!x1){
                break;
            }
        }

        ModelNodeInsInfo[] modelNodeInsInfos1 = modelNodeInsInfos.toArray(new ModelNodeInsInfo[]{});
        Arrays.sort(modelNodeInsInfos1,new NodeInsComporator());

        boolean x2 = true;
        for (int i = 0; i < aa; i++) {
            tmpI = modelNodeInsInfos1[i];
            for (int j = i+1; j < aa; j++) {
                tmpJ = modelNodeInsInfos1[j];
                if(tmpI.getInstanceId()<tmpJ.getInstanceId()){
                    System.out.println("xxxxxxxxxxxxxxxxxx");
                    x2 = false;
                    break;
                }
            }
            if(!x2){
                break;
            }
        }


        System.out.println(modelNodeInsInfos);

    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void getNodeInsByStatus()throws Exception {
        NodeInsQueryParam nodeInsQueryParam = new NodeInsQueryParam();
        nodeInsQueryParam.setStatuses("1");
//        nodeInsQueryParam.setCreateTime(DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
        nodeInsQueryParam.setStart(0);
        nodeInsQueryParam.setLimit(20000);
        List<ModelNodeInsInfo> modelNodeInsInfos;
        String resultStr = dataIdeUtil.findNodeIns(nodeInsQueryParam);
        ResultObj resultObj = JSON.parseObject(resultStr, ResultObj.class);
        modelNodeInsInfos = JSONObject.parseArray(resultObj.getReturnValue(), ModelNodeInsInfo.class);
        System.out.println(modelNodeInsInfos);

//        1234  156889
//        123  156692
//        12 156644
//        1 156264

    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    @Transactional
    public void insertNode()throws Exception {
        File[] files = new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\node\\").listFiles();
        System.out.println(files);
        String fileContent;
        ResultObj resultObj;
        List<ModelNodeInfo> modelNodeInfos;
        List<ModelNodeInfo> tmp;
        for (int i = 0; i < files.length; i++) {
            fileContent = FileUtil.readFirstLine(files[i].getAbsolutePath());
            resultObj = JSON.parseObject(fileContent,ResultObj.class);
            modelNodeInfos = JSON.parseArray(resultObj.getReturnValue(),ModelNodeInfo.class);
            int count = modelNodeInfos.size();
            int batch = count/500+1;
            int fromIndex;
            int toIndex;
            for (int j = 0; j < batch; j++) {
                fromIndex = j*500;
                toIndex = (j+1)*500;
                if(j==batch-1){
                    toIndex = count;
                }
                tmp = modelNodeInfos.subList(fromIndex,toIndex);
                if(tmp.size()>0){
                    modelStatisticDao.insertModelNodeInfo(tmp);
                }
            }
        }
    }

    //    @Scheduled(cron = "0/10 * * * * ?")
    @Transactional
    public void insertNodeIns()throws Exception {
        try {
            File[] files = new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\nodeIns\\").listFiles();
            System.out.println(files);
            String fileContent;
            ResultObj resultObj;
            List<ModelNodeInsInfo> modelNodeInsInfos;
            List<ModelNodeInsInfo> tmp;
            for (int i = 0; i < files.length; i++) {
                fileContent = FileUtil.readFirstLine(files[i].getAbsolutePath());
                resultObj = JSON.parseObject(fileContent,ResultObj.class);
                modelNodeInsInfos = JSON.parseArray(resultObj.getReturnValue(),ModelNodeInsInfo.class);
                int count = modelNodeInsInfos.size();
                int batch = count/300+1;
                int fromIndex;
                int toIndex;
                for (int j = 0; j < batch; j++) {
                    fromIndex = j*300;
                    toIndex = (j+1)*300;
                    if(j==batch-1){
                        toIndex = count;
                    }
                    tmp = modelNodeInsInfos.subList(fromIndex,toIndex);
                    if(tmp.size()>0){
                        modelStatisticDao.insertModelFlowNodeIns(tmp);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("1111111111111");
            throw new Exception(e);
        }

    }

    /**
     * 将节点状态为异常的数据发送到rocketmq中
     * @param tmpOldModelNodeInsInfo
     */
    private void errorSendRocketMq(ModelNodeInsInfo tmpOldModelNodeInsInfo){
//        try{
//            Integer status = tmpOldModelNodeInsInfo.getStatus();
//            if(status == 5){
//                String errorMessage = "项目名为【"+tmpOldModelNodeInsInfo.getProjectName()+"】，工作流名为【"+tmpOldModelNodeInsInfo.getNodeName()+"】对应的节点运行失败";
//                messageSendToRocketMqServiceImpl.sendErrorMessage(errorMessage , "modelRelationAlarm");
//            }
//        }catch(Exception e){
//            logger.error("模型加工监控异常信息发送到rocketmq报错"+ ExceptionUtil.getExceptionTrace(e));
//        }
    }


}
