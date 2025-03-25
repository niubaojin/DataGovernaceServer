package com.synway.datarelation.controller.relation;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.pojo.databloodline.impactanalysis.AnalysisExportData;
import com.synway.datarelation.pojo.databloodline.impactanalysis.ImpactAnalysis;
import com.synway.datarelation.service.datablood.ImpactAnalysisService;
import com.synway.datarelation.util.DateUtil;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author wangdongwei
 * @ClassName ImpactAnalysisController
 * @description 影响因素的查询接口
 * @date 2020/12/3 10:29
 */
@Controller
@RequestMapping(value = "/impactAnalysis")
public class ImpactAnalysisController {
    private Logger logger = LoggerFactory.getLogger(ImpactAnalysisController.class);
    @Autowired
    private ImpactAnalysisService impactAnalysisServiceImpl;

    /**
     *  查询数据加工血缘中指定表的影响因素的数据
     * @param tableName  表名（包括项目名）
     * @return
     */
    @RequestMapping(value = "/searchByTableName")
    @ResponseBody
    public ServerResponse<ImpactAnalysis> searchByTableName(String tableName){
        logger.info("查询表"+tableName+"在数据加工血缘的影响因素信息");
        ServerResponse<ImpactAnalysis> serverResponse = null;
        try{
            if(StringUtils.isBlank(tableName)){
                throw new NullPointerException("查询条件为空，无法获取影响因素信息");
            }
            if(!tableName.contains(".") && tableName.indexOf(".") != 1){
                throw new NullPointerException("查询表名中未包含项目名");
            }
            ImpactAnalysis impactAnalysis = impactAnalysisServiceImpl.searchImpactAnalysisByTableName(tableName);
            serverResponse = ServerResponse.asSucessResponse(impactAnalysis);
        }catch (Exception e){
            logger.error("查询表"+tableName+"的影响因素信息报错："+ ExceptionUtil.getExceptionTrace(e));
            if(StringUtils.isNotBlank(e.getMessage()) && e.getMessage().contains("null")){
                serverResponse = ServerResponse.asErrorResponse("查询表"+tableName+"的影响因素信息报错：数据中存在空值");
            }else{
                serverResponse = ServerResponse.asErrorResponse("查询表"+tableName+"的影响因素信息报错："
                        +e.getMessage());
            }

        }
        return serverResponse;
    }


    @RequestMapping(value="/downloadExcel",produces="application/json;charset=utf-8")
    @ResponseBody()
    public void downloadExcel(HttpServletResponse response,@RequestBody AnalysisExportData analysisExportData)
                   throws IOException {
        try{
            logger.info("开始导出页面中的数据"+JSONObject.toJSONString(analysisExportData));
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = analysisExportData.getTableName()+"_%s_"
                    + DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE)+".xlsx";
            List<List<String>> list = new ArrayList<>();
            List<String> tableNameCh = new ArrayList<>();
            // 存储数据的列表
            List<Object> dataList = new ArrayList<>();
            impactAnalysisServiceImpl.downloadExcel(analysisExportData,tableNameCh,list,dataList);
            response.setHeader("Content-disposition","attachment;filename*=utf-8''"
                    +URLEncoder.encode(String.format(fileName,tableNameCh.get(0)),"UTF-8"));
            // 这个里面的数据是 linkedmap 需要转换成 object
            EasyExcel.write(response.getOutputStream()).head(list).autoCloseStream(Boolean.FALSE)
                    .sheet(tableNameCh.get(0))
                    .doWrite(dataList);
            logger.info("导出页面中的数据结束");
        }catch (Exception e){
            logger.error("下载excel文件报错"+ExceptionUtil.getExceptionTrace(e));
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String,String> map = new HashMap<>();
            map.put("status","failure");
            map.put("message","下载excel文件报错"+e.getMessage());
            response.getWriter().println(JSONObject.toJSONString(map));
        }
    }



}
