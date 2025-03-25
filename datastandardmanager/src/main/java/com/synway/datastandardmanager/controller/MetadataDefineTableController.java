package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.dao.master.ElementCodeSetManageDao;
import com.synway.datastandardmanager.dao.master.MetadataDefineTableDao;
import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.pojo.relationTableInfo;
import com.synway.datastandardmanager.service.MetadataDefineTableService;
import com.synway.datastandardmanager.util.ExcelHelper;
import com.synway.datastandardmanager.util.ExceptionUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 元素集管理的接口
 * 20210721 被弃用
 * @author wangdongwei
 */
@Controller
@Deprecated
public class MetadataDefineTableController {
    private Logger logger = LoggerFactory.getLogger(MetadataDefineTableController.class);

    @Autowired()private Environment env;
	@Autowired()private MetadataDefineTableService metadataDefineTableServiceImpl;
    @Autowired
    private ElementCodeSetManageDao elementCodeSetManageDao;
    @Autowired
    private MetadataDefineTableDao metadataDefineTableDao;

//    @IgnoreSecurity
	@RequestMapping("/metadataDefineTableHtml")
	public String dataOperationMonitor(){
		return "metadataDefineTable.html";
	}

//    @IgnoreSecurity
	@RequestMapping("/getMetadataDefineTable")
	@ResponseBody
	public ServerResponse getMetadataDefineTable(int pageIndex, int pageSize, String fieldId, String columName
            , String fieldChineseName, String sort, String sortOrder){

		PageInfo<Synltefield> pageInfo = metadataDefineTableServiceImpl.findByCondition(pageIndex,pageSize,fieldId,
                columName, fieldChineseName,sort,sortOrder);
		Map<String,Object> map = new HashMap<>();
		map.put("total", pageInfo.getTotal());
	    map.put("rows", pageInfo.getList());
		return ServerResponse.asSucessResponse(map);
	}

    /**
     * 根据 fileid获取使用的表名信息
     * @param pageIndex
     * @param pageSize
     * @param fieldId
     * @return
     */
//    @IgnoreSecurity
	@RequestMapping("/getAllTableName")
	@ResponseBody
	public ServerResponse getAllTableNameByFieldId(int pageIndex,int pageSize,String fieldId){
		PageInfo<relationTableInfo> pageInfo = metadataDefineTableServiceImpl.getAllTableNameByFieldId(pageIndex,pageSize,fieldId);
		Map<String,Object> map = new HashMap<>();
		map.put("total",pageInfo.getTotal());
		map.put("rows",pageInfo.getList());
		return ServerResponse.asSucessResponse(map);
	}

//    @IgnoreSecurity
	@RequestMapping("/getAllClickUrl")
	@ResponseBody
	public Map<String,String> getAllClickUrl(){
        logger.info("开始获取对应的url信息");
        Map<String,String> urlMap = new HashMap<>();
        try{
            String dataGovernanceWebUrl = env.getProperty("nginxIp")+"/factorygateway/datagovernance/datagovernance/index.html";
            String dataQuiltyWebUrl = env.getProperty("nginxIp")+"/governance/detectionRule";
            urlMap.put("dataGovernanceWebUrl",dataGovernanceWebUrl);
            urlMap.put("dataQuiltyWebUrl",dataQuiltyWebUrl);
            logger.info("url信息为"+ JSONObject.toJSONString(urlMap));
        }catch (Exception e){
            urlMap = null;
            logger.error("获取对应URl信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return urlMap;
	}

    //导出
//    @IgnoreSecurity
    @RequestMapping(value="metadataDefineExport" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void dbOperationExport(HttpServletResponse response,
                                  @RequestParam("fieldId")String fieldId,
                                  @RequestParam("columnName")String columnName,
                                  @RequestParam("fieldChineseName")String fieldChineseName) {
        try{
            logger.info("导出的参数fieldId:"+fieldId+" columnName:"+columnName+" fieldChineseName:"+fieldChineseName);
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "元数据定义表";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = { "元素编码", "数据库列名","标准列名","中文名称","字段信息","字段分类","代码表ID","语义类型"};
            //列对应字段
            String[] fieldName = new String[]{"fieldid","columnname","fieldname","fieldchinesename","fieldMessage","fieldClassCh","codeText","wordName"};
            //查询数据集
            List<Synltefield> list = metadataDefineTableDao.findByCondition(fieldId, columnName, fieldChineseName,null,null);
            //数据字段处理
            List<Object> listNew = new ArrayList<>();
            for(Synltefield synltefield:list){
                String fieldTypeStr= SynlteFieldType.getSynlteFieldType(synltefield.getFieldtype());
                long fieldlLength=synltefield.getFieldlen();
                synltefield.setFieldMessage(fieldTypeStr+"("+fieldlLength+")");
                listNew.add(synltefield);
            }
            ExcelHelper.export(new Synltefield(),titles,"元数据定义表",listNew,fieldName,out);
            logger.info("导出结束=====================");
        }catch(Exception e){
            logger.error("元数据定义表导出异常："+ExceptionUtil.getExceptionTrace(e));
        }
    }

    //导出源数据定义表里面的代码值信息
//    @IgnoreSecurity
    @RequestMapping(value="metadataExportFieldCodeVal" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void metadataExportFieldCodeVal(HttpServletResponse response, @RequestParam("codeId")String codeId
                                     , @RequestParam("text")String text, @RequestParam("valValue")String valValue
                                     , @RequestParam("valText")String valText) {
        try{
            logger.info("要导入的代码表id为"+codeId+" text:"+text+" valValue:"+valValue+" valText:"+valText);
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = text;
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = {  "代码集名","代码集值","代码值顺序","备注","英文简介","外键"};
            //列对应字段
            String[] fieldName = new String[]{"valText","valValue","sortIndex","memo","valTextTitle","codeId"};
            List<FieldCodeVal> fieldCodeValList=elementCodeSetManageDao.selectCodeValTableByCodeId(codeId,valValue,valText);
            List<Object> listNew = new ArrayList<>();
            for(FieldCodeVal fieldCodeVal:fieldCodeValList){
                listNew.add(fieldCodeVal);
            }
            ExcelHelper.export(new FieldCodeVal(),titles,"代码值信息",listNew,fieldName,out);
            logger.info("导出结束=====================");
        }catch (Exception e){
            logger.error("导出代码表报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }



}
