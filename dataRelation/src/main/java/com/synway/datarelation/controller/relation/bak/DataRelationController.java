//package com.synway.datarelation.controller.datablood;
//
//import com.alibaba.fastjson.JSON;
//
//import com.synway.datarelation.pojo.databloodline.DataRelationVo;
//import com.synway.datarelation.pojo.databloodline.OdpsFiled;
//import com.synway.datarelation.service.datablood.impl.DataRelationServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
///**
// * @ClassName DataRelationController
// * @description 查询odps表字段和数据信息的接口
// * @author wangdongwei
// * @date 2020/8/19 17:33
// */
//@Controller
////@RequestMapping(value = "/getDataRelation")
//public class DataRelationController {
//    private Logger logger = LoggerFactory.getLogger(DataRelationController.class);
//
//    @Autowired
//    DataRelationServiceImpl dataRelationServiceImpl;
//
//    @RequestMapping(value = "/getOdpsFiledMsg",method = RequestMethod.POST)
//    @ResponseBody
//    public List<OdpsFiled> getOdpsFiledInfo(@RequestBody String tableName){
//        logger.info("查询Odps中["+tableName+"]表结构");
//        String[] obj=tableName.split("\\.");
//        if(obj.length==0){
//            logger.error("查询Odps中["+tableName+"]表结构的查询条件出错，请检查");
//            return null;
//        }
//        return dataRelationServiceImpl.getOdpsFiledMsg(obj[0],obj[1]);
//    }
//
//    @RequestMapping(value = "/getDataDetail",method = RequestMethod.POST)
//    @ResponseBody
//    public DataRelationVo getDataExist(@RequestBody String dataRelationVoStr){
//        logger.info("查询Odps中数据溯源方法...");
//        DataRelationVo dataRelationVo= JSON.parseObject(dataRelationVoStr,DataRelationVo.class);
//        logger.info("查询Odps中数据溯源方法，查询条件解析成功，进入sql拼接...");
//        return dataRelationServiceImpl.getDataDetail(dataRelationVo);
//    }
//
//}
