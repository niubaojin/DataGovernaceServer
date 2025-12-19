package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.StandardDictionaryDTO;
import com.synway.datastandardmanager.entity.pojo.FieldCodeEntity;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.service.StandardDictionaryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 标准字典代码集管理
 *
 * @author nbj
 * @date 2025年8月15日11:19:39
 */
@RestController
public class StandardDictionaryController {

    @Autowired
    private StandardDictionaryService service;


    /**
     * 标准字典代码集管理，表格数据
     */
//    @IgnoreSecurity
    @RequestMapping("/codeTable")
    @ResponseBody
    public ServerResponse<PageVO> codeTable(@RequestParam("pageIndex") int pageIndex,
                                            @RequestParam("pageSize") int pageSize,
                                            @RequestParam("codeName") String codeName,
                                            @RequestParam("codeText") String codeText,
                                            @RequestParam("codeId") String codeId) {
        return ServerResponse.asSucessResponse(service.selectCodeTable(pageIndex, pageSize, codeName, codeText, codeId));
    }

    /**
     * 获取代码集值信息
     *
     * @author nbj
     * @date 2025年8月18日10:40:24
     */
    @RequestMapping("codeValTableOld")
    @ResponseBody
    public ServerResponse getCodeValTableOld(@RequestParam("pageSize") int pageSize,
                                             @RequestParam("pageIndex") int pageIndex,
                                             @RequestParam("valValue") String valValue,
                                             @RequestParam("valText") String valText,
                                             @RequestParam("codeId") String codeId) {
        return ServerResponse.asSucessResponse(service.selectCodeValTable(pageIndex, pageSize, valValue, valText, codeId));
    }

    //    @IgnoreSecurity
    @RequestMapping("/codeValTable")
    @ResponseBody
    public ServerResponse<List<FieldCodeValEntity>> codeValTable(String codeId) {
        return ServerResponse.asSucessResponse(service.selectCodeValTableNew(codeId));
    }

    /**
     * 删除指定的代码集值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delCodeValTable", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delCodeValTable(@RequestBody FieldCodeValEntity deleteFieldCodeVal) {
        String message = service.delCodeValTableServiceImpl(deleteFieldCodeVal);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 新增/修改 指定的代码集值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "addCodeValTable", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> addCodeValTableController(@RequestBody FieldCodeValEntity addFieldCodeVal) {
        String message = service.addCodeValTableService(addFieldCodeVal);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 获取代码集父ID/引用的代码集ID的suggest提示框值
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getCodeValIdList")
    @ResponseBody
    public ServerResponse<List<SelectFieldVO>> getCodeValIdList(String condition) {
        return ServerResponse.asSucessResponse(service.getCodeValIdListService(condition));
    }

    /**
     * 在fieldCode表中添加单个值
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/addOneCodeMessage", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> addOneCodeMessage(@RequestBody FieldCodeEntity fieldCodeEntity) {
        String message = service.addOneCodeMessageService(fieldCodeEntity);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 删除指定的代码值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delCodeTable", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delCodeTable(@RequestBody FieldCodeEntity fieldCodeEntity) {
        String message = service.delCodeTableServiceImpl(fieldCodeEntity);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 删除选定的code信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delAllSelectCode", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delAllSelectCode(@RequestBody List<FieldCodeEntity> fieldCodeEntities) {
        String message = service.delAllSelectCode(fieldCodeEntities);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 导入的时候将数据返还到页面添加
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/uploadCodeValXlsFile", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<FieldCodeValEntity>> uploadCodeValXlsFile(@RequestParam("codeValXlsFile") MultipartFile codeValXlsFile) {
        return ServerResponse.asSucessResponse(service.uploadCodeValXlsFile(codeValXlsFile));
    }

    /**
     * 下载元素代码集值管理的模板文件
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/downElementCodeTemplateFile", produces = "application/json;charset=utf-8")
    @ResponseBody()
    public void downElementCodeTemplateFile(HttpServletResponse response) {
        service.downElementCodeTemplateFile(response);
    }

    /**
     * 下载标准字典数据项信息，未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param response
     * @param data：列表中的数据
     */
    @RequestMapping(value = "/downloadStandardDictionaryFieldExcel", produces = "application/json;charset=utf-8")
    public void downloadStandardDictionaryFieldExcel(HttpServletResponse response, @RequestBody StandardDictionaryDTO data) {
        service.downloadStandardDictionaryFieldExcel(response, data, "标准字典管理", new FieldCodeValEntity());
    }

    /**
     * 删除指定的代码集值信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delBatchCodeValTable", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> delBatchCodeValTable(@RequestBody List<FieldCodeValEntity> deleteFieldCodeValList) {
        String message = service.delBatchCodeValTable(deleteFieldCodeValList);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 20200119
     * 新增的保存按钮，即保存元素代码集值信息，又保存代码集信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/addOneCodeValMessage", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> addOneCodeValMessage(@RequestBody StandardDictionaryDTO addFieldFullcode) {
        String message = service.addOneCodeValMessage(addFieldFullcode);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 数据集对标时，数据字典下拉框
     *
     * @param searchText：关键字模糊搜索
     */
    @RequestMapping("/searchDictionaryList")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> searchDictionaryList(String searchText) {
        return ServerResponse.asSucessResponse(service.searchDictionaryList(searchText));
    }

}
