package com.synway.governace.controller.largeScreen;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;
import com.synway.governace.pojo.largeScreen.PropertyLargeScreenData;
import com.synway.governace.pojo.largeScreen.ningbo.PropertyLargeScreenNB;
import com.synway.governace.pojo.largeScreen.ProvinceCity;
import com.synway.governace.service.largeScreen.NBPropertyLargeScreenService;
import com.synway.governace.service.largeScreen.PropertyLargeScreenService;
import com.synway.governace.service.largeScreen.XJPropertyLargeScreenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *  数据资产大屏的相关接口 20210425
 * @author wangdongwei
 * @date 2021/4/25 9:47
 */
@Controller
@RequestMapping("/largeScreen")
public class PropertyLargeScreenController {
    private Logger logger = LoggerFactory.getLogger(PropertyLargeScreenController.class);
    @Autowired
    private PropertyLargeScreenService propertyLargeScreenService;
    @Autowired
    private XJPropertyLargeScreenService xjPropertyLargeScreenService;
    @Autowired
    private NBPropertyLargeScreenService nbPropertyLargeScreenService;

    /**
     * 获取配置中心中配置的 省 名称
     * @return
     */
    @RequestMapping(value = "/getProvinceName")
    @ResponseBody
    public ServerResponse<ProvinceCity> getProvinceCityName() {
        try {
            ProvinceCity provinceCity = propertyLargeScreenService.getProvinceCityName();
            return ServerResponse.asSucessResponse(provinceCity);
        } catch (Exception e) {
            logger.error("获取配置信息中省的名称错误:" , e);
            return ServerResponse.asErrorResponse("获取配置信息中省的名称错误" + e.getMessage());
        }
    }

    /**
     * 获取资产大屏的数据
     * @return
     */
    @RequestMapping(value = "/getPropertyLargeScreenData")
    @ResponseBody
    public ServerResponse<PropertyLargeScreenData> getPropertyLargeScreenData() {
        try {
            PropertyLargeScreenData result = propertyLargeScreenService.getPropertyLargeScreenDataPage();
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("页面上获取数据资产大屏的信息获取报错:" , e);
            return ServerResponse.asErrorResponse("取数据资产大屏信息获取报错" + e.getMessage());
        }
    }

    /**
     * 获取资产大屏的数据（宁波）
     */
    @RequestMapping(value = "/getPropertyLargeScreenDataForNB")
    @ResponseBody
    public ServerResponse<PropertyLargeScreenNB> getPropertyLargeScreenDataForNB(){
        ServerResponse<PropertyLargeScreenNB> serverResponse = null;
        try {
            logger.info("开始获取宁波资产大屏数据");
            PropertyLargeScreenNB result = nbPropertyLargeScreenService.getPropertyLargeScreenDataForNB();
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("获取宁波资产大屏数据出错：\n", e);
            return ServerResponse.asErrorResponse("取数据资产大屏信息获取报错" + e.getMessage());
        }
    }


    /**
     * 获取 原始库资产/资源库资产/主题库资产/对外共享 详细的二级分类信息
     * 1:原始库资产： 具体数据表资产信息，包括数据名称、表名、数据量
     * 2:资源库资产： 具体要素资产信息，包括要素内容、数据量
     * 3:主题库资产： 具体数据表资产信息，包括数据名称、表名、数据量
     * 4:对外共享：
     *     代码 1 鼠标停靠“共享数据种类”上方，显示悬浮框，显示数据名称+数据记录数。
     *     代码 2 鼠标停靠“共享数据量”上方，显示悬浮框，显示数据名称+数据记录数。
     *     代码 3 鼠标停靠“共享服务接口”上方，显示悬浮框，显示服务接口名称+调用次数。
     *     代码 4 鼠标停靠“共享服务调用次数”上方，显示悬浮框，显示服务接口名称+调用次数
     * @param moduleName  对应模块名称
     * @param searchName  二级分类的名称
     * @return
     */
    @RequestMapping(value = "/getPropertyLargeDetailed")
    @ResponseBody
    public ServerResponse<List<PropertyLargeDetailed>> getPropertyLargeDetailed(@RequestParam("moduleName") String moduleName,
                                                                                @RequestParam("searchName") String searchName){
        ServerResponse<List<PropertyLargeDetailed>> serverResponse = null;
        try{
            if(StringUtils.isBlank(moduleName)){
                serverResponse = ServerResponse.asErrorResponse("moduleName参数不能为空");
                return serverResponse;
            }
            if(StringUtils.isBlank(searchName)){
                serverResponse = ServerResponse.asErrorResponse("searchName参数不能为空");
                return serverResponse;
            }
            List<PropertyLargeDetailed> result = propertyLargeScreenService.getPropertyLargeDetailed(moduleName,searchName);
            serverResponse = ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取表详细信息报错：" + e.getMessage());
            logger.error("获取二级分类的详细信息报错", e);
        }
        return serverResponse;
    }

    /**
     * 数据全流程图：静态页面（宁波）
     */
    @RequestMapping(value = "/getG6MockData")
    @ResponseBody
    public ServerResponse<JSONObject> getG6MockData(){
        ServerResponse<JSONObject> serverResponse = null;
        try {
            logger.info("开始获取数据全流程图数据");
            JSONObject resulte = nbPropertyLargeScreenService.getG6MockData();
            serverResponse = ServerResponse.asSucessResponse(resulte);
        }catch (Exception e){
            logger.error("获取数据全流程图数据出错：\n", e);
            serverResponse = ServerResponse.asErrorResponse("获取数据全流程图出错:" + e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 数据大屏：静态页面（新疆）
     */
    @RequestMapping(value = "/getPropertyLargeScreenDataForXJ")
    @ResponseBody
    public ServerResponse<JSONObject> getPropertyLargeScreenDataForXJ(){
        ServerResponse<JSONObject> serverResponse = null;
        try {
            logger.info("开始获取新疆大屏数据");
            JSONObject resulte = xjPropertyLargeScreenService.getPropertyLargeScreenDataForXJ();
            serverResponse = ServerResponse.asSucessResponse(resulte);
        }catch (Exception e){
            logger.error("获取新疆大屏数据出错：\n" , e);
            serverResponse = ServerResponse.asErrorResponse("获取大屏数据出错" + e.getMessage());
        }
        return serverResponse;
    }


}
