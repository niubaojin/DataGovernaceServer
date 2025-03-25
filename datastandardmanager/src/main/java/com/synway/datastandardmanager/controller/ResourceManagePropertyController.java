package com.synway.datastandardmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping("/dataStandardManager")
@Deprecated
public class ResourceManagePropertyController {
    @Autowired()
    private Environment env;
//    @Autowired()
//    private ResourceManagePropertyService resourceManagePropertyServiceImpl;
    private Logger logger = LoggerFactory.getLogger(ResourceManagePropertyController.class);

//    @Deprecated
//    @RequestMapping("/resourceManageProperty")
//    public String resourceManageProperty(){
//        return "resourceManageProperty.html";
//    }

//    /**
//     * 弃用
//     * @param objectId
//     * @return
//     */
//    @RequestMapping("/getClassifyOnclick")
//    @ResponseBody
//    public ServerResponse<String> getClassifyOnclick(String objectId){
//        logger.info("开始获取"+objectId+"资源服务平台对应的url");
//        ServerResponse<String> serverResponse = null;
//        try{
//            if(StringUtils.isEmpty(objectId)){
//                serverResponse = ServerResponse.asErrorResponse("传入的objectId为空");
//                return serverResponse;
//            }
//            String url = env.getProperty("dataDetailPageUrl");
//            if(StringUtils.isEmpty(url)){
//                serverResponse = ServerResponse.asErrorResponse("配置的数据资源服务页面");
//            }else{
//                serverResponse = ServerResponse.asSucessResponse(url+"?ID="+objectId,url+"?ID="+objectId);
//            }
//        }catch (Exception e){
//            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
//            logger.error(ExceptionUtil.getExceptionTrace(e));
//        }
//        return serverResponse;
//    }

//    @RequestMapping("/getAllExampleData")
//    @ResponseBody
//    public ServerResponse<ExampleDataColumn> getAllExampleData(@RequestParam("queryTableName")String queryTableName,
//                                                               @RequestParam("queryProjectName")String queryProjectName,
//                                                               @RequestParam("queryType")String queryType){
//        logger.info("开始获取 queryTableName："+queryTableName+" queryProjectName:"+
//                queryProjectName+" queryType:"+queryType+"对应的样例数据");
//        ServerResponse<ExampleDataColumn> serverResponse = null;
//        try{
//            serverResponse = resourceManagePropertyServiceImpl.getAllExampleData(queryTableName , queryProjectName ,queryType );
//        }catch (Exception e){
//            serverResponse = ServerResponse.asErrorResponse("获取样例数据报错"+e.getMessage());
//            logger.error("获取样例数据报错"+ExceptionUtil.getExceptionTrace(e));
//        }
//
//        return serverResponse;
//    }
}
