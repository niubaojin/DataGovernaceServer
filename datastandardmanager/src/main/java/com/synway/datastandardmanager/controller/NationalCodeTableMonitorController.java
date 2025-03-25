package com.synway.datastandardmanager.controller;


import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Fieldcode;
import com.synway.datastandardmanager.service.NationalCodeTableMonitorService;
import com.synway.datastandardmanager.util.ExcelHelper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 国际码表管理的控制层,没有注释,不知道是谁写的
 * @author obito
 * @date 2022/4/25 13:51
 */
@Slf4j
@Controller
//@RequestMapping("/dataStandardManager")
public class NationalCodeTableMonitorController {


    @Autowired
    private NationalCodeTableMonitorService nationalCodeTableMonitorService;

//    @IgnoreSecurity
    @RequestMapping(value="/createNationalCodeTableTree",produces="application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<String> createNationalCodeTableTree(@RequestParam("codeText") String codeText){
        try {
            String result = nationalCodeTableMonitorService.codeTextQuery(codeText);
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            log.error("创建编码失败：", e);
            return ServerResponse.asErrorResponse("创建编码失败：" + e.getMessage());
        }
    }

    //数据名称
//    @IgnoreSecurity
    @RequestMapping(value="/getCodeTexts" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse getCodeTexts(@RequestParam("codeText")String codeText) {
        List<String> list = nationalCodeTableMonitorService.codeTextsQuery(codeText);
        return ServerResponse.asSucessResponse(list);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getFieldCodeTableByCodeId" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse getFieldCodeTableByCodeId(@RequestParam("codeId") String codeId){
        List<Fieldcode> list = nationalCodeTableMonitorService.fieldCodeTableQuery(codeId);
        return ServerResponse.asSucessResponse(list);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getFieldCodeValTable" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse getFieldCodeValTable( @RequestParam("pageNum") int pageNum,
                                       @RequestParam("pageSize") int pageSize,
                                       @RequestParam("codeId") String codeId,
                                       @RequestParam("valText") String valText){

        List<FieldCodeVal> list = nationalCodeTableMonitorService.fieldCodeValTable(pageNum,pageSize,codeId,valText);
        PageInfo<FieldCodeVal> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",total);
        return ServerResponse.asSucessResponse(map);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getValText" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse getValText(@RequestParam("codeId") String codeId,
                               @RequestParam("valText") String valText){
        List<String> list = nationalCodeTableMonitorService.valTextQuery(codeId,valText);
        return ServerResponse.asSucessResponse(list);
    }


    //导出
//    @IgnoreSecurity
    @RequestMapping(value="FieldCodeValBtnExport" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void FieldCodeValBtnExport(HttpServletResponse response,
                                      @RequestParam("codeId") String codeId,
                                      @RequestParam("valText")  String valText) {
        response.setContentType("application/binary;charset=UTF-8");
        try{
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "国标码表管理";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = {"序号","代码Id", "代码中文名称","代码名称","备注"};
            //列对应字段
            String[] fieldName = new String[]{"codeValId","codeId","valText","valValue","memo"};
            //查询数据集
            List<FieldCodeVal> list = nationalCodeTableMonitorService.fieldCodeValTable(0,0,codeId,valText);
            //数据字段处理
            List<Object> listNew=new ArrayList<Object>();
            listNew.addAll(list);
            ExcelHelper.export(new FieldCodeVal(),titles,"国标码表管理",listNew,fieldName,out);
        }catch(Exception e){
            System.out.println("国标码表管理"+e);
        }
    }




}
