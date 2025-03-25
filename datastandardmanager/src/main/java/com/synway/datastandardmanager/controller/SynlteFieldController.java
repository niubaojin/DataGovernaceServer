package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.TableInfo;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerParameter;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataField;
import com.synway.datastandardmanager.pojo.relationTableInfo;
import com.synway.datastandardmanager.pojo.synltefield.*;
import com.synway.datastandardmanager.service.SynlteFieldService;
import com.synway.datastandardmanager.util.ExcelHelper;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.PinYinUtil;
import com.synway.common.bean.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.net.URLEncoder;
import java.util.*;

/**
 *  数据元管理的相关功能
 * @author wangdongwei
 * @date 2021/7/22 11:07
 */
@RestController
@Slf4j
@RequestMapping("/synlteField")
@Validated
public class SynlteFieldController {

    @Autowired
    private SynlteFieldService synlteFieldServiceImpl;


    /**
     * 数据元的表格搜索 获取表格中的数据
     * 20211020改为后端分页
     * @param parameter
     * @return
     */
    @RequestMapping(value = "/searchAllTable")
    public ServerResponse<Map<String,Object>> searchAllTable(@RequestBody  @Valid SynlteFieldParameter parameter,
                                                                  BindingResult bindingResult){
        log.info("查询数据元的请求参数为{}", JSONObject.toJSONString(parameter));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Map<String,Object> data = synlteFieldServiceImpl.searchAllTable(parameter);
        log.info("========查询数据元结束=========");
        return ServerResponse.asSucessResponse(data);
    }

    /**
     * 获取表格的筛选内容
     * @return
     */
    @RequestMapping(value = "/getFilterObject")
    public ServerResponse<SynlteFieldFilter> getFilterObject(){
        log.info("查询数据元的表格筛选内容");
        SynlteFieldFilter data = synlteFieldServiceImpl.getFilterObject();
        log.info("========查询数据元的筛选内容结束=========");
        return ServerResponse.asSucessResponse(data);
    }


    /**
     * 获取 请输入框 的提示信息
     * 需要排序
     * @return
     */
    @RequestMapping(value = "/getSearchNameSuggest")
    public ServerResponse<List<String>> getSearchNameSuggest(String searchName){
        List<String> data = synlteFieldServiceImpl.getSearchNameSuggest(searchName);
        return ServerResponse.asSucessResponse(data);
    }


    /**
     * 根据fieldId修改数据元状态
     * @param synlteFieldStatusChange
     * @return
     */
    @RequestMapping(value = "/updateSynlteFieldStatus")
    public ServerResponse<String> updateSynlteFieldStatus(@RequestBody @Valid SynlteFieldStatusChange synlteFieldStatusChange){
        log.info("开始更新数据元状态");
        String data = synlteFieldServiceImpl.updateSynlteFieldStatus(synlteFieldStatusChange.getFieldIdList(),synlteFieldStatusChange.getStatus());
        log.info("数据元状态更新完成");
        return ServerResponse.asSucessResponse(data,data);
    }



    /**
     * 添加一条新的数据元信息
     * @param synlteFieldObject
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addObject")
    public ServerResponse<String> addObject(@RequestBody  @Valid SynlteFieldObject synlteFieldObject,
                                            BindingResult bindingResult){
        log.info("添加新的数据元，相关信息为{}", JSONObject.toJSONString(synlteFieldObject));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String data = synlteFieldServiceImpl.addObject(synlteFieldObject);
        return ServerResponse.asSucessResponse(data,data);
    }


    /**
     * 编辑数据元信息
     * @param synlteFieldObject
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/updateObject")
    public ServerResponse<String> updateObject(@RequestBody  @Valid SynlteFieldObject synlteFieldObject,
                                            BindingResult bindingResult){
        log.info("对数据元信息进行编辑，相关信息为{}", JSONObject.toJSONString(synlteFieldObject));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String data = synlteFieldServiceImpl.updateObject(synlteFieldObject);
        return ServerResponse.asSucessResponse(data);
    }

    /**
     * 获取语义类型的相关下拉框信息
     * @param searchName
     * @return
     */
    @RequestMapping(value = "/getSameWordList")
    public ServerResponse<List<FilterObject>> getSameWordList(String searchName){
        List<FilterObject> data = synlteFieldServiceImpl.getSameWordList(searchName);
        return ServerResponse.asSucessResponse(data);
    }


    /**
     * 根据中文名称 获取标识符信息
     * @param name  字段中文名称
     * @return
     */
    @RequestMapping(value = "/getSimChinese")
    public ServerResponse<String> getSimChinese(@RequestParam("name") @NotBlank String name){
        String data = synlteFieldServiceImpl.getSimChinese(name,null);
        return ServerResponse.asSucessResponse(data,data);
    }


    /**
     *  一些选择框需要从码表库里面查询结构
     * @param name  EXPRESSION_WORD：表示词  SYNLTEFIELD_STATUS: 状态   field_type:字段类型
     * @return
     */
    @RequestMapping(value = "/getSelectObjectByName")
    public ServerResponse<List<FilterObject>> getSelectObjectByName(@RequestParam("name") @NotBlank String name){
        List<FilterObject> data = synlteFieldServiceImpl.getSelectObjectByName(name);
        return ServerResponse.asSucessResponse(data);
    }


    /**
     * 获取中文全拼
     * @param name  中文名称
     * @return
     */
    @RequestMapping(value = "/getFullChineseByName")
    public ServerResponse<String> getFullChineseByName(@RequestParam("name") @NotBlank String name){
        String data = PinYinUtil.getPySpell(name);
        return ServerResponse.asSucessResponse((StringUtils.isNotBlank(data)?StringUtils.substring(data,0,200):""),data);
    }

    /**
     * 从码表查询数据安全分级的列表
     * @return
     */
    @RequestMapping(value = "/searchDataSecurityLevelList")
    public ServerResponse<List<PageSelectOneValue>> searchDataSecurityLevelList(){
        log.info("========开始查询数据安全分级列表========");
        List<PageSelectOneValue> dataSecurityLevelList = synlteFieldServiceImpl.searchDataSecurityLevel();
        log.info("========查询数据安全分级列表结束========");
        return ServerResponse.asSucessResponse(dataSecurityLevelList);

    }

    /**
     * 根据数据元id查询数据元相关信息
     * @param fieldId 数据元id
     * @return
     */
    @RequestMapping(value = "/searchSynlteFieldById")
    public ServerResponse<SynlteFieldObject> searchSynlteFieldById(@RequestParam("fieldId") @NotBlank String fieldId){
        SynlteFieldObject synlteFieldObject = synlteFieldServiceImpl.searchSynlteFieldById(fieldId);
        return ServerResponse.asSucessResponse(synlteFieldObject);
    }

    /**
     * 关键字搜索数据元下拉列表
     * @param searchText 关键字
     * @return
     */
    @RequestMapping(value = "/getGadsjFieldByText")
    public ServerResponse<List<PageSelectOneValue>> getGadsjFieldByText(String searchText,String fieldType){
        List<PageSelectOneValue> responseList = synlteFieldServiceImpl.getGadsjFieldByText(searchText,fieldType);
        return ServerResponse.asSucessResponse(responseList);
    }

    /**
     * 下载（导出）数据元信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     * @param response
     * @param parameter  列表中的数据
     */
    @RequestMapping(value="/downloadSynlteFieldExcel",produces="application/json;charset=utf-8")
    public void downloadSynlteFieldExcel(HttpServletResponse response,@RequestBody SynlteFieldParameter parameter){
        log.info("=======开始下载数据元的相关信息=======");
        synlteFieldServiceImpl.downloadSynlteFieldExcel(response,parameter,"数据元信息表",new SynlteFieldExcel());
        log.info("=======下载数据元的相关信息结束========");
    }

    /**
     * 追加导入数据元数据
     * @param file
     */
    @RequestMapping(value="/importSynlteFieldExcel")
    public ServerResponse<String> importSynlteFieldExcel(@RequestParam("file") MultipartFile file) {
        log.info("=======开始导入数据元的相关信息=======");
        String resulteMessage = synlteFieldServiceImpl.importSynlteFieldExcel(file);
        log.info("=======导入数据元的相关信息结束======");
        return ServerResponse.asSucessResponse(resulteMessage,resulteMessage);
    }

    /**
     * 先清空，导入数据元数据
     * 1.9先不用
     * @param file
     */
    @RequestMapping(value = "/clearImportSynlteFieldExcel")
    public ServerResponse<List<SynlteFieldExcel>> clearImportSynlteFieldExcel(@RequestParam("file") MultipartFile file){
        log.info("开始清空所有数据元之后导入");
        List<SynlteFieldExcel> list = synlteFieldServiceImpl.clearImportSynlteFieldExcel(file);
        log.info("清空导入数据元结束");
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 导出数据元sql文件
     * @param response
     */
    @RequestMapping(value="/downloadSynlteFieldSql",produces="application/json;charset=utf-8")
    public void downloadSynlteFieldSql(@RequestBody  @Valid SynlteFieldParameter parameter,HttpServletResponse response){
        log.info("开始导出数据元sql文件");
        synlteFieldServiceImpl.downloadSynlteFieldSql(parameter,response,"数据元sql");
        log.info("导出数据元Sql文件结束");
    }

     /**
     * 数据元管理页面 关联数据集
     * @param pageIndex
     * @param pageSize
     * @param fieldId 数据元唯一标识
     * @return
     */
	@RequestMapping("/getAllTableName")
	public ServerResponse<Object> getAllTableNameByFieldId(int pageIndex,int pageSize,String fieldId,String searchName){
		PageInfo<relationTableInfo> pageInfo = synlteFieldServiceImpl.getAllTableNameByFieldId(pageIndex,pageSize,fieldId,searchName);
		Map<String,Object> map = new HashMap<>();
		map.put("total",pageInfo.getTotal());
		map.put("rows",pageInfo.getList());
		return ServerResponse.asSucessResponse(map);
	}

    //导出关联表的相关信息
    @RequestMapping(value="metadataExportTableNames" ,produces="application/json;charset=utf-8")
    public void metadataExportTableNames(HttpServletResponse response, @RequestParam("text")String text
            , @RequestParam("fieldId")String fieldId) {
        try{
            log.info("导出关联表的相关信息为：fieldId"+fieldId+" text:"+text);
            ServletOutputStream out=response.getOutputStream();
            //文件名称
            String name = text;
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            //表标题
            String[] titles = {"数据集中文名","数据集英文名", "数据集编码", };
            //列对应字段
            String[] fieldName = new String[]{"tableNameCh","tableName","tableId"};
            List<relationTableInfo> tableNameList = synlteFieldServiceImpl.getAllTableNameByFieldId(fieldId);
            List<Object> listNew = new ArrayList<>();
            for(relationTableInfo tableInfo:tableNameList){
                listNew.add(tableInfo);
            }
            ExcelHelper.export(new TableInfo(),titles,"关联表信息",listNew,fieldName,out);
            log.info("导出结束=====================");
        }catch (Exception e){
            log.error("导出关联表的相关信息"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

//    /**
//     * 更新所有的标识符，如果里面存在空值，则更新
//     * 非页面接口  1.9废止
//     * @return
//     */
//    @RequestMapping(value = "/updateSimChinese")
//    public ServerResponse<String> updateSimChinese(){
//        String data = synlteFieldServiceImpl.updateSimChinese();
//        return ServerResponse.asSucessResponse(data);
//    }
//
//    /**
//     * 删除指定fieldId的数据元信息
//     * @param fieldId 1.9废止
//     * @return
//     */
//    @RequestMapping(value = "/delObjectByFieldId")
//    public ServerResponse<String> delObjectByFieldId(@RequestParam("fieldId") @NotBlank String fieldId){
//        String data = synlteFieldServiceImpl.delObjectByFieldId(fieldId);
//        return ServerResponse.asSucessResponse(data);
//    }

}
