//package com.synway.datarelation.controller.datablood;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 数据血缘非节点信息的请求路径
// * @author wdw
// * @version 1.0
// * @date 2021/6/4 19:22
// */
//@Slf4j
//@RestController
//@RequestMapping(value = "/dataBloodline")
//public class BloodParameterController {
//
//    @Autowired
//    BloodParameterService bloodParameterServiceImpl;
//
//    /**
//     * 检查表在本地仓中是否已经存在
//     * @param tableName 项目名.表名
//     * @param baseType  数据库类型
//     * @return
//     */
//    @RequestMapping(value = "/checkTableIsExist")
//    public ServerResponse<Boolean> checkTableIsExist(String tableName, String baseType){
//        ServerResponse<Boolean> serverResponse = null;
//        try{
//            boolean flag = bloodParameterServiceImpl.checkTableIsExist(tableName,baseType);
//            serverResponse = ServerResponse.asSucessResponse(flag);
//        }catch (Exception e){
//            serverResponse = ServerResponse.asErrorResponse("检查表信息是否已创建报错："+e.getMessage());
//            log.error("检查表信息是否已创建报错："+ ExceptionUtil.getExceptionTrace(e));
//        }
//        return serverResponse;
//    }
//}
