package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.entity.dto.DataSynlteFieldDTO;
import com.synway.datastandardmanager.entity.dto.SynlteFieldStatusDTO;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.service.DataSynlteFieldService;
import com.synway.datastandardmanager.util.PinYinUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 数据元管理
 */
@RestController
@RequestMapping("/synlteField")
public class DataSynlteFieldController {

    @Autowired
    private DataSynlteFieldService synlteFieldService;

    /**
     * 数据元的表格搜索 获取表格中的数据
     *
     * @param synlteFieldDTO
     * @return
     */
    @PostMapping(value = "/searchAllTable")
    public ServerResponse<PageVO<SynlteFieldEntity>> querySynlteFieldList(@RequestBody @Valid DataSynlteFieldDTO synlteFieldDTO, BindingResult bindingResult) {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ServerResponse.asSucessResponse(synlteFieldService.querySynlteFieldList(synlteFieldDTO));
    }

    /**
     * 获取表格的筛选内容
     */
    @RequestMapping(value = "/getFilterObject")
    public ServerResponse<SynlteFieldFilterVO> getFilterObjectForSF() {
        return ServerResponse.asSucessResponse(synlteFieldService.getFilterObjectForSF());
    }

    /**
     * 获取输入框的提示信息,需要排序
     *
     * @return
     */
    @RequestMapping(value = "/getSearchNameSuggest")
    public ServerResponse<List<String>> getSearchNameSuggest(String searchName) {
        return ServerResponse.asSucessResponse(synlteFieldService.getSearchNameSuggest(searchName));
    }

    /**
     * 根据fieldId修改数据元状态
     *
     * @param synlteFieldStatusDTO
     */
    @RequestMapping(value = "/updateSynlteFieldStatus")
    public ServerResponse<String> updateSynlteFieldStatus(@RequestBody @Valid SynlteFieldStatusDTO synlteFieldStatusDTO) {
        String msg = synlteFieldService.updateSynlteFieldStatus(synlteFieldStatusDTO);
        return ServerResponse.asSucessResponse(msg, msg);
    }

    /**
     * 添加一条新的数据元信息
     *
     * @param synlteField
     */
    @RequestMapping(value = "/addObject")
    public ServerResponse<String> addSynlteField(@RequestBody SynlteFieldEntity synlteField) {
//        // 传入参数核验
//        if (bindingResult.hasErrors()) {
//            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
//                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//        }
        return ServerResponse.asSucessResponse(synlteFieldService.addSynlteField(synlteField));
    }

    /**
     * 编辑数据元信息
     *
     * @param synlteField
     */
    @RequestMapping(value = "/updateObject")
    public ServerResponse<String> updateSynlteField(@RequestBody SynlteFieldEntity synlteField) {
//        // 传入参数核验
//        if (bindingResult.hasErrors()) {
//            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
//                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//        }
        return ServerResponse.asSucessResponse(synlteFieldService.updateSynlteField(synlteField));
    }

    /**
     * 获取语义类型的相关下拉框信息
     *
     * @param searchName
     * @return
     */
    @RequestMapping(value = "/getSameWordList")
    public ServerResponse<List<KeyValueVO>> getSameWordList(String searchName) {
        return ServerResponse.asSucessResponse(synlteFieldService.getSameWordList(searchName));
    }

    /**
     * 根据中文名称 获取标识符信息
     *
     * @param name 字段中文名称
     * @return
     */
    @RequestMapping(value = "/getSimChinese")
    public ServerResponse<String> getSimChinese(String name) {
        String msg = synlteFieldService.getSimChinese(name, null);
        return ServerResponse.asSucessResponse(msg, msg);
    }

    /**
     * 一些选择框需要从码表库里面查询结构
     *
     * @param name EXPRESSION_WORD：表示词，SYNLTEFIELD_STATUS: 状态，field_type:字段类型
     */
    @RequestMapping(value = "/getSelectObjectByName")
    public ServerResponse<List<KeyValueVO>> getSelectObjectByName(String name) {
        return ServerResponse.asSucessResponse(synlteFieldService.getSelectObjectByName(name));
    }

    /**
     * 获取中文全拼
     *
     * @param name 中文名称
     */
    @RequestMapping(value = "/getFullChineseByName")
    public ServerResponse<String> getFullChineseByName(String name) {
        String data = PinYinUtil.getPySpell(name);
        return ServerResponse.asSucessResponse((StringUtils.isNotBlank(data) ? StringUtils.substring(data, 0, 200) : ""), data);
    }

    /**
     * 从码表查询数据安全分级的列表
     */
    @RequestMapping(value = "/searchDataSecurityLevelList")
    public ServerResponse<List<KeyValueVO>> searchDataSecurityLevelList() {
        return ServerResponse.asSucessResponse(synlteFieldService.searchDataSecurityLevel());
    }

    /**
     * 根据数据元id查询数据元相关信息
     *
     * @param fieldId 数据元id
     */
    @RequestMapping(value = "/searchSynlteFieldById")
    public ServerResponse<SynlteFieldEntity> searchSynlteFieldById(String fieldId) {
        return ServerResponse.asSucessResponse(synlteFieldService.searchSynlteFieldById(fieldId));
    }

    /**
     * 关键字搜索数据元下拉列表
     *
     * @param searchText 关键字
     */
    @RequestMapping(value = "/getGadsjFieldByText")
    public ServerResponse<List<KeyValueVO>> getGadsjFieldByText(String searchText, String fieldType) {
        return ServerResponse.asSucessResponse(synlteFieldService.getGadsjFieldByText(searchText, fieldType));
    }

    /**
     * 下载（导出）数据元信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param response
     * @param dto      列表中的数据
     */
    @RequestMapping(value = "/downloadSynlteFieldExcel", produces = "application/json;charset=utf-8")
    @ResponseBody
    public void downloadSynlteFieldExcel(HttpServletResponse response, @RequestBody DataSynlteFieldDTO dto) {
        synlteFieldService.downloadSynlteFieldExcel(response, dto, "数据元信息表", new SynlteFieldEntity());
    }

    /**
     * 追加导入数据元数据
     *
     * @param file
     */
    @RequestMapping(value = "/importSynlteFieldExcel")
    public ServerResponse<String> importSynlteFieldExcel(@RequestParam("file") MultipartFile file) {
        return ServerResponse.asSucessResponse(synlteFieldService.importSynlteFieldExcel(file));
    }

    /**
     * 先清空，导入数据元数据，1.9先不用
     *
     * @param file
     */
    @RequestMapping(value = "/clearImportSynlteFieldExcel")
    public ServerResponse<List<SynlteFieldEntity>> clearImportSynlteFieldExcel(@RequestParam("file") MultipartFile file) {
        return ServerResponse.asSucessResponse(synlteFieldService.clearImportSynlteFieldExcel(file));
    }

    /**
     * 导出数据元sql文件
     *
     * @param response
     */
    @RequestMapping(value = "/downloadSynlteFieldSql", produces = "application/json;charset=utf-8")
    public void downloadSynlteFieldSql(@RequestBody @Valid DataSynlteFieldDTO dto, HttpServletResponse response) {
        synlteFieldService.downloadSynlteFieldSql(dto, response, "数据元sql");
    }

    /**
     * 数据元管理页面 关联数据集
     *
     * @param pageIndex
     * @param pageSize
     * @param fieldId   数据元唯一标识
     */
    @RequestMapping("/getAllTableName")
    public ServerResponse<PageVO<TableInfoVO>> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId, String searchName) {
        PageVO<TableInfoVO> pageVO = synlteFieldService.getAllTableNameByFieldId(pageIndex, pageSize, fieldId, searchName);
        return ServerResponse.asSucessResponse(pageVO);
    }

    /**
     * 导出关联表的相关信息
     *
     * @param response
     * @param text
     * @param fieldId
     */
    @RequestMapping(value = "metadataExportTableNames", produces = "application/json;charset=utf-8")
    public void metadataExportTableNames(HttpServletResponse response, @RequestParam("text") String text, @RequestParam("fieldId") String fieldId) {
        synlteFieldService.metadataExportTableNames(response, fieldId, text);
    }


}
