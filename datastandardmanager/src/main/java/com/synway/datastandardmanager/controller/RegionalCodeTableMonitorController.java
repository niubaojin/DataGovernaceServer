package com.synway.datastandardmanager.controller;


import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.RegionalCodeTable;
import com.synway.datastandardmanager.service.RegionalCodeTableMonitorService;
import com.synway.datastandardmanager.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应该是地标码表管理的控制层,没有注释,不知道是谁写的
 * @author obito
 * @date 2022/4/25 13:51
 */
@Controller
//@RequestMapping("/dataStandardManager")
public class RegionalCodeTableMonitorController {


    private Logger logger =  LoggerFactory.getLogger(RegionalCodeTableMonitorController.class);

    @Autowired
    private RegionalCodeTableMonitorService regionalCodeTableMonitorService;

//    @RequestMapping("/regionalCodeTableMonitor")
//    public String nationalCodeTableMonitor(){
//        return "regionalCodeTableMonitor.html";
//    }


//    @IgnoreSecurity
    @RequestMapping(value="/createDMZDZWMTree",produces="application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<String> createDMZDZWMTree(@RequestParam("dmzdzwm") String dmzdzwm){
        String result = regionalCodeTableMonitorService.createDMZDZWMTree(dmzdzwm);
        return ServerResponse.asSucessResponse(result);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getDmzdzwm",produces="application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<List<String>> getDmzdzwm(@RequestParam("dmzdzwm") String dmzdzwm){
        List<String> list = regionalCodeTableMonitorService.dmzdzwmQuery(dmzdzwm);
        return ServerResponse.asSucessResponse(list);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getCodeTableByDmzd",produces="application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<List<RegionalCodeTable>> getCodeTableByDmzd(@RequestParam("dmzd") String dmzd){
        List<RegionalCodeTable> list = regionalCodeTableMonitorService.CodeTableQuery(dmzd);
        return ServerResponse.asSucessResponse(list);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getCodeValTable" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse getCodeValTable( @RequestParam("pageNum") int pageNum,
                                     @RequestParam("pageSize") int pageSize,
                                     @RequestParam("dmzd") String dmzd,
                                     @RequestParam("dmmc") String dmmc){

        List<RegionalCodeTable> list = regionalCodeTableMonitorService.CodeValTable(pageNum,pageSize,dmzd,dmmc);
        PageInfo<RegionalCodeTable> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",total);
        return ServerResponse.asSucessResponse(map);
    }

//    @IgnoreSecurity
    @RequestMapping(value="/getDmmc" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse getDmmc(@RequestParam("dmzd") String dmzd,
                             @RequestParam("dmmc") String dmmc){
        List<String> list = regionalCodeTableMonitorService.dmmcQuery(dmzd,dmmc);
        return ServerResponse.asSucessResponse(list);
    }

    //导出
//    @IgnoreSecurity
    @RequestMapping(value="CodeValTableBtnExport" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void CodeValTableBtnExport(HttpServletResponse response,
                                      @RequestParam("dmzd") String dmzd,
                                      @RequestParam("dmmc") String dmmc) {
        response.setContentType("application/binary;charset=UTF-8");
        try{
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "地标码表管理";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = {"代码字段","代码字段中文名", "代码","代码名称"};
            //列对应字段
            String[] fieldName = new String[]{"dmzd","dmzdzwm","dm","dmmc"};
            //查询数据集
            List<RegionalCodeTable> list = regionalCodeTableMonitorService.CodeValTable(0,0,dmzd,dmmc);
            //数据字段处理
            List<Object> listNew=new ArrayList<Object>();
            listNew.addAll(list);
            ExcelHelper.export(new RegionalCodeTable(),titles,"地标码表管理",listNew,fieldName,out);
        }catch(Exception e){
            logger.error("地标码表管理"+ExceptionUtil.getExceptionTrace(e));
        }
    }

//    @IgnoreSecurity
    @RequestMapping(value="/updateDMAndDMMC" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse updateDMAndDMMC(@RequestParam("dmzd")String dmzd,
                                  @RequestParam("dm")String dm,
                                  @RequestParam("dmmc")String dmmc,
                                  @RequestParam("dmNew")String dmNew,
                                  @RequestParam("dmmcNew")String dmmcNew){
        try{
            regionalCodeTableMonitorService.updateDMAndDMMC(dmzd,dm,dmmc,dmNew,dmmcNew);
            return ServerResponse.asSucessResponse();
        }catch (Exception e){
            logger.error("更新地表数据出错", e);
            return ServerResponse.asErrorResponse("更新地表数据出错" + e.getMessage());
        }
    }


    //导出模板
//    @IgnoreSecurity
    @RequestMapping(value="exportFormwork" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public void exportFormwork(HttpServletResponse response,
                                      @RequestParam("dmzd") String dmzd,
                                      @RequestParam("dmmc") String dmmc) {
        response.setContentType("application/binary;charset=UTF-8");
        try{
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = "地标码表模板";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = {"代码字段*","代码字段中文名*", "代码*","代码名称*"};
            //列对应字段
            String[] fieldName = new String[]{"dmzd","dmzdzwm","dm","dmmc"};
            //查询数据集
            List<RegionalCodeTable> list = new ArrayList<>();
            //数据字段处理
            List<Object> listNew=new ArrayList<Object>();
            listNew.addAll(list);
            ExcelHelper.export(new RegionalCodeTable(),titles,"地标码表模板",listNew,fieldName,out);
        }catch(Exception e){
            logger.error("地标码表模板"+ ExceptionUtil.getExceptionTrace(e));
        }
    }


//    @IgnoreSecurity
    @RequestMapping("/importRegionalCodeData")
    @ResponseBody
    public ServerResponse importRegionalCodeData(@RequestParam("ruleFile") MultipartFile ruleFile){
        try {
//            CommonsMultipartFile multipartFile=(CommonsMultipartFile) ruleFile;
//            DiskFileItem diskFileItem=(DiskFileItem)multipartFile.getFileItem();
//            File file =diskFileItem.getStoreLocation();
            String fileName = ruleFile.getOriginalFilename();
            logger.info("上传的文件名为："+fileName);
            List<Map> list = ImportHelper.importExcel3(ruleFile);
            //加工数据
            List<RegionalCodeTable> lists = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                RegionalCodeTable regionalCodeTable = new RegionalCodeTable();
                Map map = list.get(i);
                regionalCodeTable.setDmzd(String.valueOf(map.getOrDefault("代码字段*"," ")));
                regionalCodeTable.setDmzdzwm(String.valueOf(map.getOrDefault("代码字段中文名*"," ")));
                regionalCodeTable.setDm(String.valueOf(map.getOrDefault("代码*"," ")));
                regionalCodeTable.setDmmc(String.valueOf(map.getOrDefault("代码名称*"," ")));
                ValidatorUtil.checkObjectValidator(regionalCodeTable);
                lists.add(regionalCodeTable);
            }
            if(lists.size()!=0){
                regionalCodeTableMonitorService.batchInsertionOfData(lists);
            }
            return ServerResponse.asSucessResponse();
        }catch (Exception e){
            logger.error("导入地表数据失败", e);
            return ServerResponse.asErrorResponse("导入地表数据失败：" + e.getMessage());
        }
    }

    public void dataProcess(File file){
        List<Map> list = new ArrayList<>();
        list = ExcelImpotHelper.importExcel(file);
        List<RegionalCodeTable> lists = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            RegionalCodeTable regionalCodeTable = new RegionalCodeTable();
            Map map = list.get(i);
            regionalCodeTable.setDmzd(String.valueOf(map.get("代码字段[DMZD]")));
            regionalCodeTable.setDmzdzwm(String.valueOf(map.get("代码字段中文名[DMZDZWM]")));
            regionalCodeTable.setDm(String.valueOf(map.get("代码[DM]")));
            regionalCodeTable.setDmmc(String.valueOf(map.get("代码名称[DMMC]")));
            lists.add(regionalCodeTable);
        }
        if(lists.size()!=0){
            regionalCodeTableMonitorService.batchInsertionOfData(lists);
        }
    }

//    @IgnoreSecurity
    @RequestMapping(value="/deleteCodeValTable" ,produces="application/json;charset=utf-8")
    @ResponseBody()
    public ServerResponse deleteCodeValTable(@RequestParam("dmzd")String dmzd,
                                     @RequestParam("dmzdzwm")String dmzdzwm,
                                     @RequestParam("dm")String dm,
                                     @RequestParam("dmmc")String dmmc){
        try{
            regionalCodeTableMonitorService.CodeValTableDelete(dmzd,dmzdzwm,dm,dmmc);
            return ServerResponse.asSucessResponse();
        }catch (Exception e){
            logger.error("删除地标数据出错", e);
            return ServerResponse.asErrorResponse("删除地标数据出错：" + e.getMessage());
        }
    }


}
