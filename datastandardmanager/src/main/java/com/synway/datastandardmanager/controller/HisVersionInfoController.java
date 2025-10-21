package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.HisVersionInfoDTO;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.FilterListVO;
import com.synway.datastandardmanager.service.HisVersionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 历史版本信息
 *
 * @author obito
 * @date 2025年8月27日16:14:39
 */
@RestController
@RequestMapping("/versionManage")
@Validated
public class HisVersionInfoController {

    @Autowired
    private HisVersionInfoService service;

    /**
     * 查询标准表版本的相关信息：使用前端分页
     */
    @RequestMapping(value = "/searchObjectTable")
    public ServerResponse<List<ObjectVersionEntity>> getObjectVersionTable(@RequestBody HisVersionInfoDTO hisVersionInfoDTO) {
        return ServerResponse.asSucessResponse(service.searchAllObjectVersion(hisVersionInfoDTO));
    }

    /**
     * 查询数据元版本的相关信息：使用前端分页
     */
    @RequestMapping(value = "/searchSynlteFieldTable")
    public ServerResponse<List<SynlteFieldVersionEntity>> getSynlteFieldVersionTable(@RequestBody HisVersionInfoDTO hisVersionInfoDTO) {
        return ServerResponse.asSucessResponse(service.searchAllSynlteFieldVersion(hisVersionInfoDTO));
    }

    /**
     * 查询限定词版本的相关信息：使用前端分页
     */
    @RequestMapping(value = "/searchDeterminerTable")
    public ServerResponse<List<FieldDeterminerVersionEntity>> getFieldDeterminerVersionTable(@RequestBody HisVersionInfoDTO hisVersionInfoDTO) {
        return ServerResponse.asSucessResponse(service.searchAllFieldDeterminerVersion(hisVersionInfoDTO));
    }

    /**
     * 数据集版本中点击查看跳转到数据定义编辑页面的回传信息
     */
    @RequestMapping(value = "/getOldObject")
    public ServerResponse<ObjectManageDTO> getOldObject(@RequestBody ObjectVersionEntity objectVersionVo) {
        return ServerResponse.asSucessResponse(service.searchOldObject(objectVersionVo));
    }

    /**
     * 数据元版本中点击查看跳转到编辑页面的回传信息
     */
    @RequestMapping(value = "/getOldSynlteField")
    public ServerResponse<SynlteFieldHisEntity> getOldSynlteField(@RequestBody SynlteFieldVersionEntity synlteFieldVersionVo) {
        return ServerResponse.asSucessResponse(service.searchOldSynlteField(synlteFieldVersionVo));
    }

    /**
     * 限定词版本中点击查看跳转到编辑页面的回传信息
     */
    @RequestMapping(value = "/getOldDeterminer")
    public ServerResponse<FieldDeterminerHisEntity> getOldDeterminer(@RequestBody FieldDeterminerVersionEntity fieldDeterminerVersionVo) {
        return ServerResponse.asSucessResponse(service.searchOldFieldDeterminer(fieldDeterminerVersionVo));
    }

    /**
     * 大版本号和修订人的筛选值
     *
     * @param table 值有: object synlteField determinerField
     */
    @RequestMapping(value = "/getFilterInfo")
    public ServerResponse<FilterListVO> getVersionAndAuthorInfo(@RequestParam("table") String table) {
        return ServerResponse.asSucessResponse(service.searchVersionAndAuthor(table));
    }

}
