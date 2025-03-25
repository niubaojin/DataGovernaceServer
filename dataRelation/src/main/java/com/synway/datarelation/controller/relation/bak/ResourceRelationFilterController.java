//package com.synway.datarelation.controller.relation;
//
//import com.alibaba.fastjson.JSONObject;
//import com.synway.datarelation.constant.Constant;
//import com.synway.datarelation.pojo.databloodline.BloodlineFilterPage;
//import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
//import com.synway.common.bean.ServerResponse;
//import com.synway.datarelation.pojo.databloodline.TrackQueryData;
//import com.synway.datarelation.service.datablood.PageBloodlineFilterService;
//import com.synway.common.exception.ExceptionUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 数据血缘页面上的筛选信息，相关筛选之后修改页面上血缘的数据
// * @author wangdongwei
// * @date 2021/3/12 10:54
// */
//@Controller
//@RequestMapping(value = "/resource")
//public class ResourceRelationFilterController {
//    private static Logger logger = LoggerFactory.getLogger(ResourceRelationFilterController.class);
//
//    @Autowired
//    private PageBloodlineFilterService pageBloodlineFilterServiceImpl;
//
//    /**
//     * 数据过滤的相关信息
//     * @param bloodlineFilterPage
//     * @return 返回筛选之后的数据
//     */
//    @PostMapping(value = "/PageBloodlineFilter")
//    @ResponseBody
//    public ServerResponse<DataBloodlineNode> pageBloodlineFilter(@RequestBody BloodlineFilterPage bloodlineFilterPage){
//        logger.info("页面筛选的查询参数为 数据过滤信息:"+bloodlineFilterPage.getClassifyFilter()
//                +" 组织分类标注:"+ JSONObject.toJSONString(bloodlineFilterPage.getOrganizationClassifyList()));
//        try{
//            if(StringUtils.isNotBlank(bloodlineFilterPage.getDataTrackFilter())
//                    && !Constant.DATA_FLOW.equalsIgnoreCase(bloodlineFilterPage.getDataTrackFilter())
//                    && !Constant.PROCESS_FLOW.equalsIgnoreCase(bloodlineFilterPage.getDataTrackFilter())){
//                throw new Exception("dataTrackFilter的参数填写不正确，应为【dataFlow/processFlow】");
//            }
//            if(StringUtils.isNotBlank(bloodlineFilterPage.getPageDataStr())){
//                bloodlineFilterPage.setPageData(JSONObject.parseObject(bloodlineFilterPage.getPageDataStr(),DataBloodlineNode.class));
//            }
//            DataBloodlineNode dataBloodlineNode = pageBloodlineFilterServiceImpl.PageBloodlineFilter(bloodlineFilterPage);
//            // 20210409 需要在这里设置 nodeNameList
//            if(dataBloodlineNode.getNodes() != null &&
//                    !dataBloodlineNode.getNodes().isEmpty()){
//                // 获取所有的节点名称放在 tableNameList里面
//                List<TrackQueryData> nodeNameList = new ArrayList<>();
//                dataBloodlineNode.getNodes().stream().filter(d -> StringUtils.isNotBlank(d.getId()))
//                        .distinct().forEach(d ->{
//                    nodeNameList.add(new TrackQueryData(d.getId(),d.getNodeName()));
//                });
//                dataBloodlineNode.setNodeNameList(nodeNameList);
//            }
//            return ServerResponse.asSucessResponse(dataBloodlineNode);
//        }catch (Exception e){
//            logger.error("页面筛选数据报错："+ ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asErrorResponse("页面筛选数据报错："+e.getMessage());
//        }
//    }
//
//    /**
//     * 页面上隐藏应用血缘的节点信息，加工血缘的节点直接连接应用血缘模型的节点
//     * @param data 页面上的数据
//     * @return
//     */
//    @PostMapping(value = "/pageHiddenNode")
//    @ResponseBody
//    public ServerResponse<DataBloodlineNode> pageHiddenNode(@RequestBody DataBloodlineNode data){
//        ServerResponse<DataBloodlineNode> serverResponse = null;
//        try{
//            DataBloodlineNode dataBloodlineNode = pageBloodlineFilterServiceImpl.pageHiddenNode(data);
//            serverResponse = ServerResponse.asSucessResponse(dataBloodlineNode);
//        }catch (Exception e){
//            logger.error("隐藏应用血缘的节点信息报错："+ ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("隐藏应用血缘的节点信息报错："+e.getMessage());
//        }
//        return serverResponse;
//    }
//}
