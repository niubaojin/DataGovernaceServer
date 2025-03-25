package com.synway.datastandardmanager.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.datastandDictionary.DataStandardDictionaryParameter;
import com.synway.datastandardmanager.service.ElementCodeSetManageService;
import com.synway.datastandardmanager.util.ExcelHelper;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.ImportHelper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标准字典代码集管理
 * @author obito
 * @version 1.0
 * @date
 */
@Controller
//@RequestMapping("/dataStandardManager")
public class ElementCodeSetManageController {
    private Logger logger = LoggerFactory.getLogger(ElementCodeSetManageController.class);
	@Autowired
	private ElementCodeSetManageService elementCodeSetManageServiceImpl;

    /**
     * 标准字典代码集管理 表格数据
     * @param
     * @return
     */
//    @IgnoreSecurity
	@RequestMapping("codeTable")
	@ResponseBody
	public ServerResponse codeTable(int pageIndex,int pageSize,String codeName,String codeText,String codeId){
		PageInfo<Fieldcode> pageInfo=elementCodeSetManageServiceImpl.selectCodeTable(pageIndex,pageSize,codeName,codeText,codeId);
		Map<String,Object> map = new HashMap<>();
		map.put("total", pageInfo.getTotal());
	    map.put("rows", pageInfo.getList());
		return ServerResponse.asSucessResponse(map);
	}

    /**
     * @author chenfei
     * @date 2024/8/2 11:19
     * @Description 同codeValTableOld接口，规范命名
     */
    @RequestMapping("codeValTableOld")
    @ResponseBody
    public ServerResponse getCodeValTableOld(int pageSize,int pageIndex,String valValue,String valText,String codeId){
        logger.info("查询参数为pageSize："+pageSize+" pageIndex:"+pageIndex+" valValue:"+valValue+" valText:" +valText+" codeId:"+codeId);
        Map<String,Object> map = new HashMap<>();
        PageInfo<FieldCodeVal> pageInfo= elementCodeSetManageServiceImpl.selectCodeValTable(pageIndex,pageSize,valValue, valText,codeId);
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        logger.info("返回数据为"+JSONObject.toJSONString(map));
        return ServerResponse.asSucessResponse(map);
    }

//    @IgnoreSecurity
    @RequestMapping("codeValTable")
    @ResponseBody
    public ServerResponse<List<FieldCodeVal>> codeValTable(String codeId){
        ServerResponse<List<FieldCodeVal>> serverResponse = null;
        try{
            logger.info("查询的参数为："+codeId);
            List<FieldCodeVal> fieldCodeValList= elementCodeSetManageServiceImpl.selectCodeValTableNew(codeId);
            logger.info("返回的数据为："+JSONObject.toJSONString(fieldCodeValList));
            serverResponse = ServerResponse.asSucessResponse(fieldCodeValList);
        }catch (Exception e){
            logger.error("查询codeValTable值报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询codeValTable值报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     *  删除指定的代码集值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "delCodeValTable" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delCodeValTableController(@RequestBody FieldCodeVal deleteFieldCodeVal){
        logger.info("要删除的数据为:"+JSONObject.toJSONString(deleteFieldCodeVal));
        String message = "";
        ServerResponse<String> serverResponse = null;
        try{
            message = elementCodeSetManageServiceImpl.delCodeValTableServiceImpl(deleteFieldCodeVal);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "删除指定的代码集值信息报错："+e.getMessage();
            logger.error("删除指定的代码集值信息报错："+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        logger.info("=======================删除代码集值信息结束=======================");
        return serverResponse;
    }

    /**
     *  新增/修改 指定的代码集值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "addCodeValTable" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> addCodeValTableController(@RequestBody FieldCodeVal addFieldCodeVal){
        ServerResponse<String> serverResponse = null;
        String message = "";
        logger.info("新增/修改 代码集基本信息的数据为:"+JSONObject.toJSONString(addFieldCodeVal));
        try{
            message = elementCodeSetManageServiceImpl.addCodeValTableService(addFieldCodeVal);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "新增/修改 指定的代码集值信息报错："+e.getMessage();
            logger.error("新增/修改 指定的代码集值信息报错："+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        logger.info("=======================新增/修改 指定的代码集值信息结束=======================");
        return serverResponse;
    }

    /**
     *  获取代码集父ID/引用的代码集ID的suggest提示框值
     * @param condition
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getCodeValIdList" )
    @ResponseBody
    public ServerResponse<List<OneSuggestValue>> getCodeValIdList(String condition){
        try{
            logger.info("搜索"+condition+"对应的代码集父ID/引用的代码集Id");
            List<OneSuggestValue> list = elementCodeSetManageServiceImpl.getCodeValIdListService(condition);
            return ServerResponse.asSucessResponse(list);
        }catch (Exception e){
            logger.error("suggest提示信息报错", e);
            return ServerResponse.asErrorResponse("获取信码表息失败："+ e.getMessage());
        }
    }

    /**
     * 在fieldCode表中添加单个值
     * @param addFieldcode
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "addOneCodeMessage" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> addOneCodeMessage(@RequestBody Fieldcode addFieldcode){
        ServerResponse<String> serverResponse = null;
        String message = "";
        logger.info("向fieldCode表中添加的数据为："+JSONObject.toJSONString(addFieldcode));
        try{
            message = elementCodeSetManageServiceImpl.addOneCodeMessageService(addFieldcode);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "添加数据报错:"+e.getMessage();
            logger.error("添加数据报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }

        return serverResponse;
    }

    /**
     *  删除指定的代码值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "delCodeTable" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delCodeTableController(@RequestBody Fieldcode deleteFieldCode){
        logger.info("要删除的数据为:"+JSONObject.toJSONString(deleteFieldCode));
        String message = "";
        ServerResponse<String> serverResponse = null;
        try{
            message = elementCodeSetManageServiceImpl.delCodeTableServiceImpl(deleteFieldCode);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "删除指定的代码值信息报错："+e.getMessage();
            logger.error("删除指定的代码值信息报错："+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        logger.info("=======================删除代码值信息结束=======================");
        return serverResponse;
    }


    /**
     *  删除选定的code信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "delAllSelectCode" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delAllSelectCodeController(@RequestBody List<Fieldcode> deleteFieldCodeList){
        logger.info("要删除的数据为:"+JSONObject.toJSONString(deleteFieldCodeList));
        String message = "";
        ServerResponse<String> serverResponse = null;
        List<String> messageList = new ArrayList<>();
        try{
            deleteFieldCodeList.forEach(((element) -> {
                String delMessage = "";
                try {
                    delMessage = elementCodeSetManageServiceImpl.delCodeTableServiceImpl(element);
                } catch (Exception e) {
                    delMessage = e.getMessage();
                }
                messageList.add(delMessage);
            })
            );
            message = StringUtils.join(messageList,"    ");
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "删除代码值信息报错："+e.getMessage();
            logger.error("删除代码值信息报错："+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        logger.info("删除数据结束===="+JSONObject.toJSONString(serverResponse));
        return serverResponse;

    }

    /**
     *  导入的时候将数据返还到页面添加
     * @param codeValXlsFile
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/uploadCodeValXlsFile" , method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<List<FieldCodeVal>> uploadCodeValXlsFile(@RequestParam("codeValXlsFile") MultipartFile codeValXlsFile){
        ServerResponse<List<FieldCodeVal>> serverResponse = null;
        List<FieldCodeVal> returnMsg = null;
        try {
            String fileName = codeValXlsFile.getOriginalFilename();
            logger.info("上传的文件名为："+fileName);
            List<Map> list = ImportHelper.importExcel3(codeValXlsFile);
            logger.info("上传的文件里面信息为："+JSONObject.toJSONString(list));
            returnMsg = elementCodeSetManageServiceImpl.uploadCodeValXlsFile(list);
            serverResponse = ServerResponse.asSucessResponse(returnMsg);
        } catch (Exception e) {
            logger.error("导入文件报错"+ ExceptionUtil.getExceptionTrace(e));
            String message = "导入文件报错："+ ExceptionUtil.getExceptionTrace(e);
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        return serverResponse;

    }

    //下载元素代码集值管理的模板文件
//    @IgnoreSecurity
    @RequestMapping(value="/downElementCodeTemplateFile" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void downElementCodeTemplateFile(HttpServletResponse response){
        try {
            logger.info("开始导出模板文件报错");
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "元素代码值导入";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = { "主键ID*","代码集名*","代码集值*","代码值顺序","英文缩写","备注"};
            //列对应字段
            String[] fieldName = new String[]{};
            List<Object> listNew = new ArrayList<>();
            ExcelHelper.export(new TableInfo(),titles,"代码值信息",listNew,fieldName, out);
            logger.info("导出结束=====================");
        }catch (Exception e){
            logger.error("导出模板文件报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 下载标准字典数据项信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     * @param response
     * @param data     列表中的数据
     */
    @RequestMapping(value = "/downloadStandardDictionaryFieldExcel", produces = "application/json;charset=utf-8")
    public void downloadStandardDictionaryFieldExcel(HttpServletResponse response, @RequestBody DataStandardDictionaryParameter data) {
        logger.info("=======开始下载标准字典的相关信息=======");
        elementCodeSetManageServiceImpl.downloadStandardDictionaryFieldExcel(response, data, "标准字典管理", new FieldCodeVal());
        logger.info("=======下载标准字典的相关信息结束========");
    }

    /**
     *  删除指定的代码集值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "delBatchCodeValTable" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delBatchCodeValTable(@RequestBody List<FieldCodeVal> deleteFieldCodeValList){
        logger.info("批量删除的数据为:"+JSONObject.toJSONString(deleteFieldCodeValList));
        String message = "";
        ServerResponse<String> serverResponse = null;
        try{
            for(FieldCodeVal fieldCodeVal:deleteFieldCodeValList){
                message = message + elementCodeSetManageServiceImpl.delCodeValTableServiceImpl(fieldCodeVal);
            }
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "批量删除指定的代码集值信息报错："+e.getMessage();
            logger.error("批量删除指定的代码集值信息报错："+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        logger.info("=======================删除代码集值信息结束=======================");
        return serverResponse;
    }

    /**
     *  20200119
     *  新增的保存按钮，即保存元素代码集值信息，又保存代码集信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "addOneCodeValMessage" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> addOneCodeValMessage(@RequestBody FieldFullcode addFieldFullcode){
        ServerResponse<String> serverResponse = null;
        String message = "";
        logger.info("向代码集值相关表中添加的数据为："+JSONObject.toJSONString(addFieldFullcode));
        try{
            message = elementCodeSetManageServiceImpl.addOneFullCodeService(addFieldFullcode);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            message = "添加数据报错:"+e.getMessage();
            logger.error("添加数据报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        return serverResponse;
    }

    /**
     * 数据集对标时，数据字典下拉框
     * @param searchText 关键字模糊搜索
     */
    @RequestMapping("/searchDictionaryList")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> searchDictionaryList(String searchText){
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            List<PageSelectOneValue> list = elementCodeSetManageServiceImpl.searchDictionaryList(searchText);
            serverResponse = ServerResponse.asSucessResponse(list);
        }catch(Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取数据字典下拉框信息报错"+e.getMessage());
        }
        return serverResponse;
    }

}
