package com.synway.datastandardmanager.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Sameword;
import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.TableInfo;
import com.synway.datastandardmanager.pojo.synlteelement.SynlteElement;
import com.synway.datastandardmanager.pojo.synlteelement.SynlteElementCode;
import com.synway.datastandardmanager.service.SemanticTableManageService;
import com.synway.datastandardmanager.util.ExcelHelper;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.ImportHelper;
import com.synway.common.bean.ServerResponse;
import org.apache.catalina.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据语义的相关接口
 *
 * @author wangdongwei
 * @ClassName SemanticTableManageController
 * @description 数据语义的相关接口
 *
 */
@Controller
public class SemanticTableManageController {
    private Logger logger = LoggerFactory.getLogger(SemanticTableManageController.class);
    @Autowired()
    private SemanticTableManageService semanticTableManageServiceImpl;
    @Autowired()
    private Environment env;

//    @IgnoreSecurity
//	@RequestMapping("/semanticTableManageHtml")
//	public String dataOperationMonitor(){
//		return "semanticTableManage.html";
//	}

    /**
     * 数据语义管理页面表数据
     * @param sameId 语义id
     * @param word 语义英文名称
     * @param wordName 语义中文名称
     * @return
     */
//    @IgnoreSecurity
	@RequestMapping("/semanticTableManage")
	@ResponseBody
	public ServerResponse<List<Sameword>> semanticTableManage(String sameId, String wordName, String word){
        List<Sameword> list = semanticTableManageServiceImpl.findByCondition(sameId, wordName, word);
        return ServerResponse.asSucessResponse(list);
	}

    /**
     * 下载(导出)数据数据语义excel文件
     *
     * @param response
     * @param sameId 主键id
     * @param wordName 语义中文名
     * @param word 语义英文名
     * @param elementObject 主体类型
     */
//    @IgnoreSecurity
    @RequestMapping(value="semanticTableExport" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void semanticTableExport(HttpServletResponse response, @RequestParam("sameId")String sameId
            , @RequestParam("wordName")String wordName, @RequestParam("word")String word,@RequestParam("elementObject") String elementObject) {
        logger.info("导出的参数为 sameId："+sameId+" wordName:"+wordName+" word:"+word + "elementObject"+ elementObject);
        try{
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "语义表";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = { "主键ID*", "中文语义*","英文语义*","主体类型*","备注"};
            //列对应字段
            String[] fieldName = new String[]{"id","wordname","word","elementObject","memo"};
            //查询数据集
            List<Sameword> samewordList = semanticTableManageServiceImpl.findByCondition(sameId, wordName, word);
            List<Object> listNew = new ArrayList<>();
            for(Sameword sameword:samewordList){
                if(!StringUtils.isBlank(sameword.getElementObject())){
                    sameword.setElementObjectVo(SynlteElementCode.getValueById("3_"+sameword.getElementObject()));
                }
                listNew.add(sameword);
            }
            ExcelHelper.export(new Sameword(),titles,"语义表信息",listNew,fieldName,out);
            logger.info("导出结束=====================");
        }catch (Exception e){
            logger.error("导出报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 添加新的语义信息
     * @param sameword
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/addOneSemanticManage" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addOneSemanticManage(@RequestBody @Valid Sameword sameword){
        String message = "";
        ServerResponse<String> serverResponse = null;
	    try{
	        logger.info("添加的数据为："+ JSONObject.toJSONString(sameword));
            message = semanticTableManageServiceImpl.addOneSemanticManage(sameword);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
	        if(e.getMessage().contains("违反唯一约束条件")){
                logger.error("添加一条新的数据报错"+e.getMessage());
	            return ServerResponse.asErrorResponse("中文语义/英文语义值已经存在，违反唯一性约束");
            }
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
	        logger.error("添加一条新的数据报错"+e.getMessage());
        }
	    return serverResponse;
    }

    /**
     * 批量删除语义信息
     * @param delSameword 数据语义中文名称列表
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delAllSemanticManage" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delAllSemanticManage(@RequestBody List<Sameword> delSameword){
        String message = "";
        ServerResponse<String> serverResponse = null;
        try{
            logger.info("要删除的数据为："+ JSONObject.toJSONString(delSameword));
            for(Sameword oneSameword:delSameword){
                message = message + semanticTableManageServiceImpl.delOneSemanticManage(oneSameword);
            }
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            logger.error("删除数据报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 删除一条语义信息
     * @param delSameword 数据语义中文名称
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delOneSemanticManage" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delOneSemanticManage(@RequestBody Sameword delSameword){
        String message = "";
        ServerResponse<String> serverResponse = null;
        try{
            logger.info("要删除的数据为："+ JSONObject.toJSONString(delSameword));
            message = semanticTableManageServiceImpl.delOneSemanticManage(delSameword);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            logger.error("删除数据报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 数据语义管理页面 关联元素集数据
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getMetadataDefineTableBySameid" )
    @ResponseBody
    public ServerResponse getMetadataDefineTableBySameid(int pageIndex,int pageSize,String sameId){
        Map<String,Object> map = new HashMap<>();
        try{
            logger.info("获取sameid："+ sameId+"对应的关联元素集信息");
            PageInfo<Synltefield> pageInfo= semanticTableManageServiceImpl.getMetadataDefineTableBySameidService(pageIndex,pageSize,sameId);
            map.put("total", pageInfo.getTotal());
            map.put("rows", pageInfo.getList());
            return ServerResponse.asSucessResponse(map);
        }catch (Exception e){
            logger.error("获取关联元素集信息报错", e);
            return ServerResponse.asErrorResponse("获取关联元素集信息报错：" + e.getMessage());
        }
    }

    /**
     * 数据语义管理页面 关联元素集数据
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/uploadSemanticFile" , method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> uploadSemanticFile(@RequestParam("semanticFile") MultipartFile semanticFile){
        ServerResponse<String> serverResponse = null;
        String message = "";
        try {
            String fileName = semanticFile.getOriginalFilename();
            logger.info("上传的文件名为："+fileName);
            List<Map> list = ImportHelper.importExcel3(semanticFile);
            logger.info("上传的文件里面信息为："+JSONObject.toJSONString(list));
            message = semanticTableManageServiceImpl.uploadSemanticFileService(list);
            serverResponse = ServerResponse.asSucessResponse(message,message);
        } catch (Exception e) {
            logger.error("导入文件报错", e);
            //unique constraint
            if(StringUtils.containsIgnoreCase(ExceptionUtil.getExceptionTrace(e),"违反唯一约束条件")
                    ||StringUtils.containsIgnoreCase(ExceptionUtil.getExceptionTrace(e),"unique constraint") ){
                message = "导入文件失败，存在重复数据";
            }else{
                message = "导入文件失败：" + e.getMessage();
            }
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        return serverResponse;

    }

    //下载语义表管理的模板文件
//    @IgnoreSecurity
    @RequestMapping(value="/downSemanticTableTemplateFile" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void downSemanticTableTemplateFile(HttpServletResponse response){

        try {
            logger.info("开始导出模板文件报错");
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "语义导入";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = { "主键ID*","中文语义*","英文语义*","主体类型*","备注","","主体类型的码表值:1.人员 2.物 3.组织 4.地 5.事 6.时间 7.信息"};
            //列对应字段
            String[] fieldName = new String[]{};
            List<Object> listNew = new ArrayList<>();
            ExcelHelper.export(new TableInfo(),titles,"语义表管理",listNew,fieldName,out);
            logger.info("导出结束=====================");
        }catch (Exception e){
            logger.error("导出模板文件报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getDataQuilatyUrl")
    @ResponseBody
    public ServerResponse<String> getDataQuilatyUrl(String sameId){
        ServerResponse<String> serverResponse = null;
        try{
            String dataGovernanceWebUrl = env.getProperty("nginxIp")+"/factorygateway/datagovernance/datagovernance/index.html";
            String dataQuiltyWebUrl = env.getProperty("nginxIp")+"/governance/detectionRule?sameId="+sameId+"&jumpType=datastandard";
            // 加上参数
            String url = dataGovernanceWebUrl+"?src="+URLEncoder.encode(dataQuiltyWebUrl,"UTF8");
            serverResponse = ServerResponse.asSucessResponse(url,url);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取跳转到质检规则页面url报错："+e.getMessage());
        }
        return serverResponse;
    }
  }
