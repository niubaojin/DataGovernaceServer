package com.synway.datastandardmanager.controller;

import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.StandardObjectManage;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.versionManage.*;
import com.synway.datastandardmanager.service.VersionManageService;
import java.util.List;
import com.synway.common.bean.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;

/**
 * 版本管理
 * @author obito
 * @version 1.0
 * @date
 */
@RestController
@Slf4j
@RequestMapping("/versionManage")
@Validated
public class VersionManageController {

    @Autowired
    private VersionManageService versionManageService;

    /**
     * 查询标准表版本的相关信息  使用前端分页
     * @param versionManageParameter
     * @return
     */
    @RequestMapping(value = "/searchObjectTable")
    public ServerResponse<List<ObjectVersionVo>> getObjectVersionTable(@RequestBody VersionManageParameter versionManageParameter){
        log.info("查询标准版本表参数为{}",versionManageParameter);
        List<ObjectVersionVo> objectList = versionManageService.searchAllObjectVersion(versionManageParameter);
        log.info("===========查询标准版本表结束=======");
        return ServerResponse.asSucessResponse(objectList);
    }

    /**
     * 查询数据元版本的相关信息  使用前端分页
     * @param versionManageParameter
     * @return
     */
    @RequestMapping(value = "/searchSynlteFieldTable")
    public ServerResponse<List<SynlteFieldVersionVo>> getSynlteFieldVersionTable(@RequestBody VersionManageParameter versionManageParameter){
        log.info("查询数据元版本表参数为{}",versionManageParameter);
        List<SynlteFieldVersionVo> synlteFieldList = versionManageService.searchAllSynlteFieldVersion(versionManageParameter);
        log.info("===========查询数据元版本表结束=======");
        return ServerResponse.asSucessResponse(synlteFieldList);
    }

    /**
     * 查询限定词版本的相关信息  使用前端分页
     * @param versionManageParameter
     * @return
     */
    @RequestMapping(value = "/searchDeterminerTable")
    public ServerResponse<List<FieldDeterminerVersionVo>> getFieldDeterminerVersionTable(@RequestBody VersionManageParameter versionManageParameter){
        log.info("查询限定词版本表参数为{}",versionManageParameter);
        List<FieldDeterminerVersionVo> determinerList = versionManageService.searchAllFieldDeterminerVersion(versionManageParameter);
        log.info("===========查询限定词版本表结束=======");
        return ServerResponse.asSucessResponse(determinerList);
    }

    /**
     * 数据集版本中点击查看跳转到数据定义编辑页面的回传信息
     * @param objectVersionVo
     * @return
     */
    @RequestMapping(value = "/getOldObject")
    public ServerResponse<StandardObjectManage> getOldObject(@RequestBody ObjectVersionVo objectVersionVo){
        log.info("===========查询标准表历史信息=======");
        StandardObjectManage standardObjectManage = versionManageService.searchOldObject(objectVersionVo);
        if(standardObjectManage == null){
            log.info("=====查询标准表历史信息出错=======");
        }
        return ServerResponse.asSucessResponse(standardObjectManage);
    }

    /**
     * 数据元版本中点击查看跳转到编辑页面的回传信息
     * @param synlteFieldVersionVo
     * @return
     */
    @RequestMapping(value = "/getOldSynlteField")
    public ServerResponse<SynlteFieldObject> getOldSynlteField(@RequestBody SynlteFieldVersionVo synlteFieldVersionVo){
        log.info("传递的数据元版本信息为：{}",synlteFieldVersionVo);
        log.info("===========查询数据元历史信息=======");
        SynlteFieldObject synlteFieldObject = versionManageService.searchOldSynlteField(synlteFieldVersionVo);
        log.info("===========查询数据元历史信息结束=======");
        return ServerResponse.asSucessResponse(synlteFieldObject);
    }

    /**
     * 限定词版本中点击查看跳转到编辑页面的回传信息
     * @param fieldDeterminerVersionVo
     * @return
     */
    @RequestMapping(value = "/getOldDeterminer")
    public ServerResponse<FieldDeterminer> getOldDeterminer(@RequestBody FieldDeterminerVersionVo fieldDeterminerVersionVo){
        log.info("传递限定词版本信息为:{}",fieldDeterminerVersionVo);
        log.info("===========查询限定词历史信息=======");
        FieldDeterminer fieldDeterminer = versionManageService.searchOldFieldDeterminer(fieldDeterminerVersionVo);
        log.info("===========查询限定词历史信息结束=======");
        return ServerResponse.asSucessResponse(fieldDeterminer);
    }

    /**
     * 大版本号和修订人的筛选值
     * @param table 值有:object synlteField determinerField
     * @return
     */
    @RequestMapping(value = "/getFilterInfo")
    public ServerResponse<FilterList> getVersionAndAuthorInfo(@RequestParam("table") String table){
        FilterList list = versionManageService.searchVersionAndAuthor(table);
        return ServerResponse.asSucessResponse(list);
    }

}
