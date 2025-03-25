package com.synway.datastandardmanager.controller;

import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.ExternalInterfce.ResourceTable;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.common.bean.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 1.9版本未发现该接口的用途
 * @author wdw
 * @version 1.0
 * @date 2021/6/17 23:37
 */
@RestController
@Slf4j
@Deprecated
public class SourceRelationControllerShip {

    @Autowired
    RestTemplateHandle restTemplateHandle;


    /**
     * 增加来源关系中表英文名的后台可搜索接口
     * 每次最多返回200条记录
     * @param dataId  数据源id 不能为空
     * @param searchName  搜索提示名
     * @return
     */
    @GetMapping(value = "/getTableNameApprovedSearch")
    public ServerResponse<List<PageSelectOneValue>> getTableNameApprovaed(@RequestParam("dataId")String dataId,
                                                                         @RequestParam("searchName")String searchName){
        log.info("查询的参数dataId: {} ,searchName: {} ", dataId,searchName);
        if(StringUtils.isBlank(dataId)){
            throw new NullPointerException("传入参数dataId为空");
        }

        List<PageSelectOneValue> result = new ArrayList<>();
        try{
            List<DetectedTable> resourceTableList= restTemplateHandle.getTableImformationList();
            resourceTableList.parallelStream().filter(d ->
                StringUtils.isBlank(searchName)||StringUtils.containsIgnoreCase(d.getTableNameEN(),searchName)
            ).sorted(Comparator.comparing(DetectedTable::getTableNameEN)).limit(200).forEach(d->{
                PageSelectOneValue oneValue = new PageSelectOneValue(d.getTableNameEN(),d.getResId());
                result.add(oneValue);
                }
            );
            log.info("查询到的数据量为{}",result.size());
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            log.error("获取表名信息报错"+ ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取表名信息报错"+e.getMessage());
        }

    }


}
